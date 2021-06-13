<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
	<head>
		<title>合同管理-提交审核</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp"%>
		<script type="text/javascript">
		
		var baseFee=0;
		var itemFee=0;
		
		$(function(){
		    parent.$("#iframe_custContractSubmit").attr("height","98%");
		    
			calBaseFeeAndItemFee();
			
			if("${custType.type }" == "1"){
				if("${customer.containSoft}" == "1" && "${hasItemPlanMap.hasRZ}" == "1"){
				    $("input:radio:first").attr("checked", "checked");
				}
			}else{
				var reportCode = "${defaultRep}";
				$("input[data-report-code=" + reportCode + "]").attr("checked","checked");
			}
			
		});
		
		//计算基础费和材料费
		function calBaseFeeAndItemFee(){
			manageFee=myRound(${customer.manageFeeBase}*${custProfit.containBase}+${customer.manageFeeSoft}*${custProfit.containSoft}
          				 +${customer.manageFeeMain}*${custProfit.containMain}+${customer.manageFeeServ}*${custProfit.containMainServ}
          				 +${customer.manageFeeInt}*${custProfit.containInt}+${customer.manageFeeCup}*${custProfit.containCup});
			baseFee=(myRound(${customer.baseFeeDirct}*${custProfit.baseDiscPer})+parseFloat(${customer.baseFeeComp})+parseFloat(manageFee)-parseFloat(${custProfit.baseDisc1})-parseFloat(${custProfit.baseDisc2}))*parseInt(${custProfit.containBase});  			
			itemFee=(parseFloat(${customer.mainFee})-parseFloat(${customer.mainDisc}))*parseInt(${custProfit.containMain})+${customer.mainServFee}*parseInt(${custProfit.containMainServ})
					+(parseFloat(${customer.softFee})-parseFloat(${customer.softDisc}))*parseInt(${custProfit.containSoft})
					+(parseFloat(${customer.integrateFee})-parseFloat(${customer.integrateDisc}))*parseInt(${custProfit.containInt})
					+(parseFloat(${customer.cupBoardFee})-parseFloat(${customer.cupBoardDisc}))*parseInt(${custProfit.containCup});
			designFee=parseInt(${custProfit.designFee});
		}
		
		function getPostData(){
			var reportName = "";
			var contractFeeRepType = "${contractFeeRepType}";
			var logoFile="<%=basePath%>jasperlogo/";
			logoFile=logoFile+$.trim("${logoFile}"); 
			var data = {
				code : "${custContract.custCode}",
				IdNum : "${custContract.partyAid}",
				SignDate : formatDate(new Date()),
				LogoFile :logoFile,
				SUBREPORT_DIR: "<%=jasperPath%>",
			};
			
			//补充协议
			if($("#bcxy").prop("checked")){
				reportName += "itemPlan_bcxy.jasper,";
			}
			//造价分析
			if (contractFeeRepType == '1') {
				reportName += "itemPlan_mlfx.jasper,";
			}else if (contractFeeRepType == '2'){
				reportName += "itemPlan_mlfx_1.jasper,";
			}else if (contractFeeRepType == '3'){
				reportName += "itemPlan_mlfx_2.jasper,";
			}else{
				reportName += "itemPlan_mlfx_3.jasper,";
			}
			//套餐
			if("${custType.type}" == "2"){
				reportName += "itemPlan_TC_main_cover.jasper,";
			    if ($("#individulPlan").is(':checked')){
					reportName += "itemPlan_basicPrj.jasper,itemPlan_individul.jasper,";
				}else{
					reportName += "itemPlan_TC.jasper,";	
				}
				//是否打印套餐
				data.printTC = $("input[name='main']:checked").val();
			}else{//非套餐
				//基础预算
				if($("#jcys").prop("checked")){
					if("${customer.custType}" != "2"){
						reportName = reportName + "itemPlan_JZ_baseItemTypeGroup_main_cover.jasper,";
					}
					reportName = reportName + "itemPlan_JZ.jasper,";
				}
				//主材预算
				if($("#zcys").prop("checked")){
					if("${customer.custType}" != "2"){
						reportName = reportName + "itemPlan_ZC_cover.jasper,";
					}
					reportName = reportName + "itemPlan_ZC.jasper,";
				}
				//服务产品预算
				if($("#fwcpys").prop("checked")){
					if("${customer.custType}" != "2"){
						reportName = reportName + "itemPlan_ZC_service_cover.jasper,";
					}
					reportName = reportName + "itemPlan_ZC_service.jasper,";
				}
				//集成衣柜橱柜
				if($("#jcygcg").prop("checked")){
					if("${customer.custType}" != "2"){
						reportName = reportName + "itemPlan_JC_cover.jasper,";
					}
					reportName = reportName + "itemPlan_JC.jasper,";
				}
				//软装预算报表
				if($("#rzys").prop("checked")){
					if("${customer.custType}" != "2"){
						reportName = reportName + "itemPlan_RZ_cover.jasper,";
					}
					reportName = reportName + "itemPlan_RZ.jasper,";
				}
				//软装预算报表（按套餐包汇总）
				if($("#rzystcbhz").prop("checked")){
					if("${customer.custType}" != "2"){
						reportName = reportName + "itemPlan_RZ_itemSet_cover.jasper,";
					}
					reportName = reportName + "itemPlan_RZ_itemSet.jasper,";
				}
				
			}
			
			//去掉最后一个逗号
			reportName = reportName.substring(0, reportName.length-1);
			
			data.report = reportName;
			var postData = {
				data:JSON.stringify(data),
				pk:"${custContract.pk}",
				code : "${custContract.custCode}",
			};
			
			return postData;
		}
		
		function submit(){
			var contractFeeRepType = "${contractFeeRepType}";
			if(contractFeeRepType == ""){
				art.dialog({
					content: "没有造价分析，不允许提交审核",
				});
				return;
			}
	 		var result = isEqual();
			if (!result) {
				if (contractFeeRepType == '1') {
					art.dialog({
						content : "付款计划总和不等于工程总价+税金,不允许打印！"
					});
				}else if (contractFeeRepType == '2' || contractFeeRepType == '4'){
					art.dialog({
						content : "付款计划、设计费返还总和不等于工程总价+税金,不允许打印！"
					});
				}else{
					art.dialog({
						content : "付款计划总和不等于工程总价+设计费+税金,不允许打印！"
					});	
				}	
				return ;
			} 
			
			var postData = getPostData();
			
		 	var waitDialog=art.dialog({
				content: "合同生成中，请稍候...", 
				lock: true,
				esc: false,
				unShowOkButton: true
			}); 
			$.ajax({
				url:'${ctx}/admin/custContract/doSubmit',
				type: 'post',
				data: postData,
				dataType: 'json',
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
			    },
			    success: function(obj){
			    	waitDialog.close();
			    	if(obj.rs){
			    		art.dialog({
							content: obj.msg,
							time: 1000,
							beforeunload: function () {
	                            closeWin();
	                        }
						});
			    	}else{
			    		$("#_form_token_uniq_id").val(obj.token.token);
			    		art.dialog({
							content: obj.msg,
							width: 200
						});
			    	}
			    }
			}); 
		}
		
		function preView(){
			var postData = getPostData();
			var waitDialog=art.dialog({
				content: "合同生成中，请稍候...", 
				lock: true,
				esc: false,
				unShowOkButton: true
			});
			$.ajax({
		         url : '${ctx}/admin/custContract/beforePrint',
		         type : 'post',
		         data : postData,
		         dataType : 'json',
		         cache : false,
		         error : function(obj) {
		             showAjaxHtml({
	                    "hidden" : false,
	                    "msg" : '保存数据出错~'
	              	 });
		          	waitDialog.close();
	              	 art.dialog({
						content: "预览失败，请检查是否上传模板",
						width: 200
					});
		         },
		         success : function(obj) {
		          	waitDialog.close();
		          	if(obj.rs){
		          		postData.tempFile = obj.msg;
		          		Global.Dialog.showDialog("doPrint",{ 
				   	  		title:"打印合同",
				   	  		url:"${ctx}/admin/custContract/doPrint?#toolbar=0",
				   	  		postData: postData,
				   	  		height:755,
				   	  		width:937,
				   	  		shade:true
				        });
		          	}else{
		          		art.dialog({
							content: obj.msg,
							width: 200
						});
		          	}
		         }
		    }); 
		}
		
		function isEqual(){
			var designFeeReturn=parseFloat($("#returnDesignFee").val());
			var basePaySum=parseFloat($("#basePaySum").val());
			var tax=parseFloat($("#tax").val());
			if ("${payRemark.DesignFeeType}"=="2"){   //1:实收设计师 ,2标准设计费
				if((parseFloat(baseFee)+parseFloat(itemFee)+parseFloat(tax))!=parseFloat(basePaySum)+parseFloat(designFeeReturn)){
					return false;
				}else{
					return true;
				}
			}else if ("${payRemark.DesignFeeType}"=="1"){
				if((parseFloat(baseFee)+parseFloat(itemFee)+parseFloat(tax))!=parseFloat(basePaySum)){
					return false;
				}else{
					return true;
				}
			}else{
				if((parseFloat(baseFee)+parseFloat(itemFee)+parseFloat(designFee)+parseFloat(tax))!=parseFloat(basePaySum)){
					return false;
				}else{
					return true;	
				}
			}      			
		}
		</script>
	</head>
	
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<c:if test="${custContract.isPreView == '1' }">
							<button type="button" class="btn btn-system " onclick="preView()">预览合同</button>
						</c:if>
						<c:if test="${custContract.isPreView != '1' }">
							<button type="button" class="btn btn-system " onclick="submit()">提交审核</button>
						</c:if>
						<button type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
	
			<div class="query-form">
				<form action="" method="post" id="page_form">
					<input type="hidden" id="expired" name="expired" value="${itemPlan.expired}" /> <input type="hidden"
						name="jsonString" value="" />
						<input type="hidden" id="basePaySum" name="basePaySum" 
						value="${basePaySum }">
						<input type="hidden" id="returnDesignFee" name="returnDesignFee"
						value="${custProfit.returnDesignFee}" disabled="disabled" />
						<input type="hidden" id="tax" name="tax" readonly="true" 
						value="${custProfit.tax}" />
					<table id="selectTable" cellpadding="0" cellspacing="0" width="100%">
						<tbody>
							<c:if test="${custType.type == '1'}">
								<tr style="border: none">
									<td style="width: 20px;border:none;padding-bottom: 10px">
										<input type="checkbox" id="zht"  checked onclick="return false"/>
									</td>
									<td class="td-value" style="border:none" nowrap>主合同<span style="color:blue">&nbsp${mainConTemp }</span></td>
									
									<td style="width: 20px;border:none;padding-bottom: 10px"></td>
									<td class="td-value" style="border:none"></td>
									<td style="width: 20px;border:none;padding-bottom: 10px">
										<input type="checkbox" id="bcxy"   onclick="return false"
										${hasGiftItem != '1' && empty(customer.repClause) && empty(customer.oldRepClause) ? '' : 'checked' }/>
									</td>
									<td class="td-value" style="border:none">补充协议</td>
									
									<td style="width: 20px;border:none;padding-bottom: 10px">
										<input type="checkbox" id="zzfx"  onclick="return false" 
										${contractFeeRepType == '' ? '' : 'checked' }/>
									</td>
									<td class="td-value" style="border:none">造价分析</td>
								</tr>
								<tr style="border: none">
									<td style="width: 20px;border:none;padding-bottom: 10px">
										<input type="checkbox" id="jcys"  onclick="return false" ${customer.containBase == '1' && hasItemPlanMap.hasJZ == '1' ? 'checked' : '' }/>
									</td>
									<td class="td-value" style="border:none">基础预算报表</td>
									
									<td style="width: 20px;border:none;padding-bottom: 10px">
										<input type="checkbox" id="zcys" name="printIdHo" onclick="return false" 
										${customer.containMain == '1' && hasItemPlanMap.hasZC == '1' ? 'checked' : '' }/>
									</td>
									<td class="td-value" style="border:none">主材预算报表</td>
									
									<td style="width: 20px;border:none;padding-bottom: 10px">
										<input type="checkbox" id="fwcpys" name="printIdHo" onclick="return false" 
										${customer.containMainServ == '1' && hasItemPlanMap.hasSev == '1' ? 'checked' : '' }/>
									</td>
									<td class="td-value" style="border:none">服务产品预算报表</td>
									
									<td style="width: 20px;border:none;padding-bottom: 10px">
										<input type="checkbox" id="jcygcg"  onclick="return false" 
										${(customer.containInt == '1' || customer.containCup == '1') && hasItemPlanMap.hasJC == '1' ? 'checked' : '' }/>
									</td>
									<td class="td-value" style="border:none">集成衣柜橱柜报表</td>
								</tr> 
								<tr style="border: none">
									<td style="width: 20px;border:none;padding-bottom: 10px">
										<input type="radio" id="rzys" name="rzys"  onclick="${customer.containSoft == '1' && hasItemPlanMap.hasRZ == '1' ? '' : 'return false'}"/>
									</td>
									<td class="td-value" style="border:none">软装预算报表</td>
									
									<td style="width: 20px;border:none;padding-bottom: 10px">
										<input type="radio" id="rzystcbhz" name="rzys"  onclick="${customer.containSoft == '1' && hasItemPlanMap.hasRZ == '1' ? '' : 'return false'}"/>
									</td>
									<td class="td-value" style="border:none" nowrap>软装预算报表（按套餐包汇总）</td>
								</tr>
							</c:if>
							<c:if test="${custType.type == '2'}">
								<tr style="border: none">
									<td style="width: 20px;border:none;padding-bottom: 10px">
										<input type="checkbox" id="zht"  checked onclick="return false" />
									</td>
									<td class="td-value" style="border:none">主合同<span style="color:blue">&nbsp${mainConTemp }</span></td>
									
									<td style="width: 20px;border:none;padding-bottom: 10px">
										<input type="checkbox" id="htfj"  checked onclick="return false" />
									</td>
									<td class="td-value" style="border:none">合同附件<span style="color:blue">&nbsp${appendConTemp }</span></td>
								</tr>
								<tr style="border: none">
									<td style="width: 20px;border:none;padding-bottom: 10px">
										<input type="checkbox" id="bcxy"   onclick="return false"
										${hasGiftItem != '1' && empty(customer.repClause) && empty(customer.oldRepClause) ? '' : 'checked' }/>
									</td>
									<td class="td-value" style="border:none">补充协议</td>
									
									<td style="width: 20px;border:none;padding-bottom: 10px">
										<input type="checkbox" id="zzfx"  onclick="return false"  
										${contractFeeRepType == '' ? '' : 'checked' }/>
									</td>
									<td class="td-value" style="border:none">造价分析</td>
								</tr>
								<tr class="td-btn">
									<td style="width: 20px;border:none;padding-bottom: 10px">
										<input type="radio" name="main" id="mainAndSetPlan" value="1"  data-report-code="01" />
									</td>
									<td class="td-value" style="border:none" >预算报价表（主套餐和套餐内材料）</td>
									<td style="width: 20px;border:none;padding-bottom: 10px">
										<input type="radio" name="main" id="mainPlan" value="0" data-report-code="02" />
									</td>
									<td class="td-value" style="border:none" >预算报价表（主套餐报价单列）</td>
								</tr>
								<tr class="td-btn">
									<td style="width: 20px;border:none;padding-bottom: 10px">
										<input type="radio" name="main" id="individulPlan" value="1" data-report-code="03"/>
									</td>
									<td class="td-value" style="border:none" >预算报价表（基础工程+个性化）</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</body>
</html>


