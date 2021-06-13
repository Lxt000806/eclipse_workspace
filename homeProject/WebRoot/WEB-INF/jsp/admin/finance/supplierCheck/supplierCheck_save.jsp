<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增结算</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		
		<script type="text/javascript">
			function save(){
				var ids = $("#mainItemDataTable").jqGrid("getDataIDs");
				if(ids.length == 0){
					art.dialog({
						content:"请先增加要结算的采购单相应信息"
					});
					return;
				}
				var rets = $("#mainItemDataTable").jqGrid("getRowData");
				
				var params = {"detailJson": JSON.stringify(rets)};//Global.JqGrid.allToJson("mainItemDataTable");
				
				Global.Form.submit("dataForm", "${ctx}/admin/supplierCheck/doSave", params, function(ret){
					art.dialog({
						content:ret.msg,
						ok:function(){
							if(ret.rs){
								$("#isSaveOk").val("T");
								closeWin();
							}else{
								$("#_form_token_uniq_id").val(ret.token.token);
							}
						}
					});		
				});
			}
			function pass(){
				var ids = $("#mainItemDataTable").jqGrid("getDataIDs");
				
				art.dialog({
					content:"是否确认要审核通过供应商["+$("#splCodeDescr").val()+"]的["+ids.length+"]条采购单!应付金额为["+$("#payAmount").val()+"]",
					ok:function(){
					
					    var rets = $("#mainItemDataTable").jqGrid("getRowData");
						var params = {"detailJson": JSON.stringify(rets)};//Global.JqGrid.allToJson("mainItemDataTable");
						
						Global.Form.submit("dataForm", "${ctx}/admin/supplierCheck/doSavePass", params, function(ret){
							art.dialog({
								content:ret.msg,
								ok:function(){
									if(ret.rs){
										$("#isSaveOk").val("T");
										closeWin();
									}else{
										$("#_form_token_uniq_id").val(ret.token.token);
									}
								}
							});		
						});
					},
					cancel:function(){
						
					}
				});
			}
			function cancel(){
				art.dialog({
					content:"是否确认取消结算单",
					ok:function(){
					    
					    var rets = $("#mainItemDataTable").jqGrid("getRowData");
                        var params = {"detailJson": JSON.stringify(rets)};//Global.JqGrid.allToJson("mainItemDataTable");
						
						Global.Form.submit("dataForm", "${ctx}/admin/supplierCheck/doSaveCancel", params, function(ret){
							art.dialog({
								content:ret.msg,
								ok:function(){
									if(ret.rs){
										$("#isSaveOk").val("T");
										closeWin();
									}else{
										$("#_form_token_uniq_id").val(ret.token.token);
									}
								}
							});		
						});
					},
					cancel:function(){
						
					}
				});
			}
			function passBack(){
				var ids = $("#mainItemDataTable").jqGrid("getDataIDs");
				
				art.dialog({
					content:"是否确认要审核通过供应商["+$("#splCodeDescr").val()+"]的["+ids.length+"]条采购单!应付金额为["+$("#payAmount").val()+"]",
					ok:function(){
					    
					    var rets = $("#mainItemDataTable").jqGrid("getRowData");
                        var params = {"detailJson": JSON.stringify(rets)};//Global.JqGrid.allToJson("mainItemDataTable");
						
						Global.Form.submit("dataForm", "${ctx}/admin/supplierCheck/doSavePassBack", params, function(ret){
							art.dialog({
								content:ret.msg,
								ok:function(){
									if(ret.rs){
										$("#isSaveOk").val("T");
										closeWin();
									}else{
										$("#_form_token_uniq_id").val(ret.token.token);
									}
								}
							});		
						});
					},
					cancel:function(){
						
					}
				});
			}
			function doExcel(){
				var activeAId = $("#splCheckSaveUl li[class=active] a")[0].id;
				
				if(activeAId == "a_tabView_mainItem"){
					doExcelNow($("#splCodeDescr").val()+"(主项目)", "mainItemDataTable", "dataForm");
				}else if(activeAId == "a_tabView_excess"){
					doExcelNow($("#splCodeDescr").val()+"(超出额)", "excessDataTable", "dataForm");
				}else if(activeAId == "a_tabView_byCompany"){
					doExcelNow($("#splCodeDescr").val()+"(按公司汇总)", "dataTableByCompany", "dataForm");
				}else if(activeAId == "a_tabView_byItemType3"){
					doExcelNow($("#splCodeDescr").val()+"(按材料类型3汇总)", "dataTableByItemType3", "dataForm");
				}else if(activeAId == "a_tabView_byCustDept"){
					doExcelNow($("#splCodeDescr").val()+"(按部门楼盘汇总)", "dataTableByCustDept", "dataForm");
				}else{
					doExcelNow($("#splCodeDescr").val()+"(预扣单)", "withHoldDataTable", "dataForm");
				}
			}
			$(function(){
				$("#splCode").openComponent_supplier({
					showValue:"${data.splCode}",
					showLabel:"${data.splCodeDescr}",
					callBack:function (ret){
						$("#splCodeDescr").val(ret.Descr);
						$("#cardId").val(ret.CardId);
						$("#actName").val(ret.ActName);
						$("#bank").val(ret.Bank);
						if(ret.ItemType1.trim() == "JC"){
    						$("#mainItemDataTable").jqGrid("showCol", "intinstallfee");
						}else{
    						$("#mainItemDataTable").jqGrid("hideCol", "intinstallfee");
						}
					}
				});
			});
			//formShow
			window.onload = function (){
				$("#isSaveOk").val("F");
				if($("#m_umState").val() == "A"){
					$("#a_tabView_byCompany").css("display", "none");
					$("#a_tabView_byItemType3").css("display", "none");
					$("#a_tabView_withHold").css("display", "none");
					$("#a_tabView_excess").css("display", "none");
					$("#a_tabView_byCustDept").css("display", "none");
				    $("#passBut").attr("disabled", true);
				    $("#cancelBut").attr("disabled", true);
				    $("#passBackBut").attr("disabled", true);
				    
				    $("#payType_disabled").removeAttr("disabled").removeClass("disabled");
    				setDBGrid();
    				return;
				}

    			if("${data.itemType1}".trim() != "JC"){
    				$("#mainItemDataTable").jqGrid("hideCol", "intinstallfee");
    				$("#viewIntInstallBtn").attr("disabled", true);
    			}
    			if("${data.itemType1}".trim() != "LP"){
    				$("#a_tabView_byCustDept").css("display", "none");
    			}
    			setDBGrid();
    			if($("#m_umState").val() == "M"){
				    $("#passBut").attr("disabled", true);
				    $("#cancelBut").attr("disabled", true);
				    $("#passBackBut").attr("disabled", true);
    			}else if($("#m_umState").val() == "L"){
    				$("#addBtn").attr("disabled", true);
    				$("#delBtn").attr("disabled", true);
    				$("#passBut").attr("disabled", true);
    				$("#cancelBut").attr("disabled", true);
    				$("#passBackBut").attr("disabled", true);
    				$("#documentNo").removeAttr("readonly");
    				$("#documentNo").parent().children("label").css("color", "blue");
    				$("#remark").attr("readonly", true);
    			}else if($("#m_umState").val() == "C"){
    				$("#addBtn").attr("disabled", true);
    				$("#delBtn").attr("disabled", true);
    				$("#saveBut").attr("disabled", true);
    				$("#passBackBut").attr("disabled", true);
    				$("#documentNo").removeAttr("readonly");
    				$("#confirmDate").val(new Date().format("yyyy-MM-dd"));
    				$("#genOthercost").removeAttr("disabled");
    				if(parseFloat("${data.noChecAppReturnNum}"==""?0:"${data.noChecAppReturnNum}")>0){
    					$("#tips").text("当前还有"+"${data.noChecAppReturnNum}"+"单未结算的退货单");
    				}	
    			}else if($("#m_umState").val() == "B"){  		
    				$("#addBtn").attr("disabled", true);
    				$("#delBtn").attr("disabled", true);
    				$("#saveBut").attr("disabled", true);
    				$("#passBut").attr("disabled", true);
    				$("#cancelBut").attr("disabled", true);	
    			}else{
    				$("#addBtn").attr("disabled", true);
    				$("#delBtn").attr("disabled", true);
    				$("#saveBut").attr("disabled", true);
    				$("#passBut").attr("disabled", true);
    				$("#cancelBut").attr("disabled", true);
    				$("#passBackBut").attr("disabled", true);
    			}
	   			$("#openComponent_supplier_splCode").attr("readonly", true);
				$("#openComponent_supplier_splCode").next().attr("data-disabled", true).css({
					"color":"#888"
				});
			};
			function setPayAmount(){
				var ret = $("#mainItemDataTable").jqGrid("footerData");
				$("#payAmount").val(myRound(ret.sumamount-ret.firstamount, 2));

			}
			function setDBGrid(){
				var nos = $("#mainItemDataTable").getCol("no", false).join(",");

				$("#excessDataTable").jqGrid("setGridParam",{
					url:"${ctx}/admin/supplierCheck/goJqGridExcess",
					postData:{
						nos:nos, 
						checkOutNo:$("#no").val(),
						splCode:$("#splCode").val()
					},
					page:1
				}).trigger("reloadGrid");
				$("#withHoldDataTable").jqGrid("setGridParam",{
					url:"${ctx}/admin/supplierCheck/goJqGridWithHold",
					postData:{
						nos:nos,
						checkOutNo:$("#no").val(),
						splCode:$("#splCode").val()
					},
					page:1
				}).trigger("reloadGrid");
			}
			function dateWdatePicker(){
				if($("#m_umState").val() == "A"){
					WdatePicker({
						skin:"whyGreen", 
						dateFmt:"yyyy-MM-dd"
					});
				}
			}
			function showHideForm(flag){//true 显示 false 隐藏
				var nowShow = $("#splCheckSaveUl li[class='active']").children().attr("id");
				
				
		    	if(flag){
		    		$("#showHidePart").css("display", "");
		    		$("#showButton").attr("hidden", true);
		    		$("#hideButton").removeAttr("hidden");
		    	}else{
		    		$("#showHidePart").css("display", "none");
		    		$("#hideButton").attr("hidden", true);
		    		$("#showButton").removeAttr("hidden");
		    	}
		    	
		    	var display = $("#tabView_mainItem").css("display");
		    	
				$("#withHoldDataTable").setGridHeight(flag?230:330);
				$("#excessDataTable").setGridHeight(flag?230:330);
				//由于setFrozenColumns操作需要获取列名行的高度,而tab未选中时,display为none无法获取真实高度,影响到固定列设置,因此先设置display为block
				$("#tabView_mainItem").css("display", "block");
				$("#mainItemDataTable").jqGrid("destroyFrozenColumns");	
				$("#mainItemDataTable").setGridHeight(flag?200:280);
				$("#mainItemDataTable").jqGrid("setFrozenColumns");
				//设置好固定列后移除display的style
				$("#tabView_mainItem").css("display", "");
		    }
		</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
	    				<button id="saveBut" type="button" class="btn btn-system" onclick="save()">保存</button>
	    				<button id="passBut" type="button" class="btn btn-system" onclick="pass()">审核通过</button>
	    				<button id="cancelBut" type="button" class="btn btn-system" onclick="cancel()">审核取消</button>
	    				<button id="passBackBut" type="button" class="btn btn-system" onclick="passBack()">反审核</button>
	    				<button id="doExcelBut" type="button" class="btn btn-system" onclick="doExcel()">输出到Excel</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
	    				&nbsp;&nbsp;&nbsp;&nbsp;
						<font color="red" ><span id="tips"></span></font>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div id="supplCheckSaveDiv" class="panel-body" style="padding:0px;">
					<form action="" method="post" id="dataForm" class="form-search">
						<house:token></house:token>
						<input type="hidden" id="jsonString" name="jsonString" value=""/>
						<input type="hidden" id="m_umState" name="m_umState" value="${data.m_umState}"/>
						<input type="hidden" id="isSaveOk" name="isSaveOk" value="F"/>
						<input type="hidden" id="splCodeDescr" name="splCodeDescr" value="${data.splCodeDescr }"/>
						<ul class="ul-form">
							<li>
								<label>结算流水</label>
								<input type="text" id="no" name="no" value="${data.no}" readonly/>
							</li>
							<li>
								<label>结算日期</label>
								<input type="text" id="date" name="date" class="i-date" 
									   onFocus="dateWdatePicker()"
								       value="<fmt:formatDate value='${data.date}' pattern='yyyy-MM-dd'/>" readonly/>
							</li>
							<li>
								<label>应付金额</label>
								<input type="text" id="payAmount" name="payAmount" value="${data.payAmount}" readonly/>
							</li>
							<li>
								<label>已付金额</label>
								<input type="text" id="paidAmount" name="paidAmount" value="${data.paidAmount}" readonly/>
							</li>
							<li>
								<label>供应商编号</label>
								<input type="text" id="splCode" name="splCode" value="${data.splCode}"/>
							</li>
							<li>
								<label>审核日期</label>
								<input type="text" id="confirmDate" name="confirmDate" class="i-date" 
								       value="<fmt:formatDate value='${data.confirmDate}' pattern='yyyy-MM-dd'/>" readonly/>
							</li>
							<li>
								<label>现付金额</label>
								<input type="text" id="nowAmount" name="nowAmount" value="${data.nowAmount}" readonly/>
							</li>
							<li>
								<label>预付款支付</label>
								<input type="text" id="preAmount" name="preAmount" value="${data.preAmount}" ${data.m_umState != 'C' ? 'readonly' : ''}/>
							</li>
							<div id="showHidePart">
								<li>
									<label>其他费用</label>
									<input type="text" id="otherCost" name="otherCost" value="${data.otherCost}" readonly/>
								</li>
								<li>
									<label>结算单状态</label>
									<house:xtdm id="status" dictCode="SPLCKOTSTATUS" value="${data.status}" disabled="true"></house:xtdm>
								</li>
								<li>
									<label>付款方式</label>
									<house:xtdm id="payType" dictCode="PAYTYPE" value="${data.payType}" headerLabel="" disabled="true"></house:xtdm>
								</li>
								<li>
									<label>凭证号</label>
									<input type="text" id="documentNo" name="documentNo" value="${data.documentNo}" readonly/>
								</li>
								<li>
									<label>户名</label>
									<input type="text" id="actName" name="actName" value="${data.actName}" readonly/>
								</li>
								<li>
									<label>开户行</label>
									<input type="text" id="bank" name="bank" value="${data.bank}" readonly/>
								</li>
								<li>
									<label>账号</label>
									<input type="text" id="cardId" name="cardId" value="${data.cardId}" style="width:452px" readonly/>
								</li>
								<li>
									<label class="control-textarea" style="top:-30px">备注</label>
									<textarea id="remark" name="remark" style="height:78px">${data.remark}</textarea>
								</li>
							</div>
						</ul>
						<div id="showButton" style="display:position;color:blue;cursor:pointer" hidden onclick="showHideForm(true)">展开</div>
						<div id="hideButton" style="display:position;color:blue;cursor:pointer" onclick="showHideForm(false)">隐藏</div>
					</form>
				</div>
			</div>
			<div class="container-fluid" >  
				<ul id="splCheckSaveUl" class="nav nav-tabs" >
				    <li class="active"><a id="a_tabView_mainItem" href="#tabView_mainItem" data-toggle="tab">主项目</a></li>  
				    <li><a id="a_tabView_excess" href="#tabView_excess" data-toggle="tab">超出额</a></li>  
				    <li><a id="a_tabView_withHold" href="#tabView_withHold" data-toggle="tab">预扣单</a></li>  
				    <li><a id="a_tabView_byCompany" href="#tabView_byCompany" data-toggle="tab">按公司汇总</a></li>  
				    <li><a id="a_tabView_byItemType3" href="#tabView_byItemType3" data-toggle="tab">按材料类型3汇总</a></li>  
				    <li><a id="a_tabView_byCustDept" href="#tabView_byCustDept" data-toggle="tab">按楼盘部门汇总</a></li>  
				</ul>  
			    <div class="tab-content">  
					<div id="tabView_excess"  class="tab-pane fade"> 
			         	<jsp:include page="tabView_excess.jsp">
			         		<jsp:param value="" name=""/>
			         	</jsp:include>
					</div> 
					<div id="tabView_withHold"  class="tab-pane fade"> 
			         	<jsp:include page="tabView_withHold.jsp">
			         		<jsp:param value="" name=""/>
			         	</jsp:include>
					</div> 
					<div id="tabView_mainItem"  class="tab-pane fade in active"> 
			         	<jsp:include page="tabView_mainItem.jsp">
			         		<jsp:param value="" name=""/>
			         	</jsp:include>
					</div> 
					<div id="tabView_byCompany"  class="tab-pane fade"> 
			         	<jsp:include page="tabView_byCompany.jsp">
			         		<jsp:param value="" name=""/>
			         	</jsp:include>
					</div> 
					<div id="tabView_byItemType3"  class="tab-pane fade"> 
			         	<jsp:include page="tabView_byItemType3.jsp">
			         		<jsp:param value="" name=""/>
			         	</jsp:include>
					</div> 
					<div id="tabView_byCustDept"  class="tab-pane fade"> 
			         	<jsp:include page="tabView_byCustDept.jsp">
			         		<jsp:param value="" name=""/>
			         	</jsp:include>
					</div> 
				</div>	
			</div>	
		</div>
	</body>
</html>


