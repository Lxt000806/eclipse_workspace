<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>更改ERP楼盘</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
    <div class="query-form" style="border-bottom:0px;margin-top:0px">
    	<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
    		<input id="processDefinitionId" name="processDefinitionId" value="${processDefinitionKey }" 
    			hidden="true"/>
    		<input id="wfProcNo" name="wfProcNo" value="${wfProcNo}" hidden="true"/>
    		<input id="taskId" name="taskId" value="${taskId}"  hidden="true"/>
    		<input id="department" name="department" hidden="true" />
	   		<input id="comment" name="comment" value="" hidden=""/>
	   		<input id="empComment" name="empComment" value="" hidden=""/><!-- 评论   -->
	   		<input id="wfProcInstNo" name="wfProcInstNo" value="${wfProcInstNo }" hidden=""/>
	   		<input id="processInstId" name="processInstId" value="${piId }" hidden=""/>
	   		<div id="detail_rel" hidden="true"></div>
    		<house:token></house:token>
    		<ul class="ul-form" id="detail_ul">
    			<div class="validate-group row" >	
    				<li class="form-validate">
    					<label class="control-textarea"><span class="required">*</span>调整原因</label>
    					<textarea id="fp__tWfCust_ChangeAddress__0__Reason" 
    						name="fp__tWfCust_ChangeAddress__0__Reason" 
    						rows="2">${datas.fp__tWfCust_ChangeAddress__0__Reason}</textarea>
    				</li>
    			</div>
				<div id="tWfCust_ChangeAddressDtl">
					<div id="group_temp">
						<div class="validate-group row" id="tWfCust_ChangeAddressDtl_div" 
							style="border-top:1px solid #dfdfdf" hidden="true" disabled="true">
							<div style="margin-top:15px">
								<input id="fp__tWfCust_ChangeAddressDtl__temInx__OldCustCode" 
									name="fp__tWfCust_ChangeAddressDtl__temInx__OldCustCode" 
									value="${datas.fp__tWfCust_ChangeAddressDtl__temInx__OldCustCode}" hidden="true"/>
					    		<input id="fp__tWfCust_ChangeAddressDtl__temInx__OldDescr" 
					    			name="fp__tWfCust_ChangeAddressDtl__temInx__OldDescr"  
					    			value="${datas.fp__tWfCust_ChangeAddressDtl__temInx__OldDescr}" hidden="true"/>
				         		<li class="form-validate">
							        <label><span class="required">*</span>调整前</label>
									<input type="text" id="fp__tWfCust_ChangeAddressDtl__temInx__OldAddress" 
										name="fp__tWfCust_ChangeAddressDtl__temInx__OldAddress"  
										style="width:160px;"
										value="${datas.fp__tWfCust_ChangeAddressDtl__temInx__OldAddress}"/>
								</li>
								<li class="form-validate">
									<input type="text" id="fp__tWfCust_ChangeAddressDtl__temInx__OldAddressDescr" 
										name="fp__tWfCust_ChangeAddressDtl__temInx__OldAddress"  
										style="width:160px;"
										value="${datas.fp__tWfCust_ChangeAddressDtl__temInx__OldAddress}"
										readonly />
								</li>
				         		<li class="form-validate">
									<label><span class="required">*</span>调整后楼盘</label>
									<input type="text" id="fp__tWfCust_ChangeAddressDtl__temInx__Address" 
										name="fp__tWfCust_ChangeAddressDtl__temInx__Address"  style="width:160px;"
										value="${datas.fp__tWfCust_ChangeAddressDtl__temInx__Address}"/>
								</li>
								<button type="button" class="btn btn-sm btn-system" style="height:25px" 
									onclick="addDetail_('add',-1,'tWfCust_ChangeAddressDtl')" 
									id="btn_temInx">增加明细</button>
							</div>	
						</div>	
					</div>
				</div>
				<div id="tips" style="border-top:1px solid #dfdfdf" >
					<div style="margin-left:60px;margin-top:8px">
						${wfProcess.remarks}
					</div>
				</div>
        	</ul>
        </form>
    </div>
    <script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
		var html_ = $("#detail_div").html();
		var detailJson=${detailJson};
		$(function(){
			// 如果有明细 则添加明细 ，如果没有 则判断页面是否有明细模板 有的话添加 第一个模板
			var detailList = ${detailList}
			if(detailList != ""){
				for (var tableName in detailList) {
					if($("#"+tableName).find("#group_temp").html()){
				       	addDetail_("",detailList[tableName]==0?0:detailList[tableName]-1,tableName);
					}
			    }
			}
			$("#dataForm").bootstrapValidator({
				excluded:["excluded"],
				message : "This value is not valid",
				feedbackIcons : {/*input状态样式图片*/
					validating : "glyphicon glyphicon-refresh"
				},
				fields: {  
					fp__tWfCust_ChangeAddress__0__Reason:{  
						validators: {  
							notEmpty: {  
								message: "调整原因不能为空"  ,
							},
						}  
					},
					openComponent_customer_fp__tWfCust_ChangeAddressDtl__0__OldAddress:{  
						validators: {  
							notEmpty: {  
								message: "调整前不能为空"  ,
							},
						}  
					},   
					fp__tWfCust_ChangeAddressDtl__0__Address:{  
						validators: {  
							notEmpty: {  
								message: "调整后不能为空"  ,
							},
						}  
					},
				},
				/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
				submitButtons : "input[type='submit']"
			});
			if("${m_umState}" == "A"){
				getOperator_();
			}
		});
		function getOperator_(flag){ 
			var elMap = {};
			getOperator(flag,elMap);
		} 
		function addDetail_(flag,inx,groupId){
			var html = $("#"+groupId).find("#group_temp").html();
			addDetail(flag,html,inx,"returnFn",groupId);
		}
		function returnFn(flag,i){
			if(flag != "add"){
				$("#fp__tWfCust_ChangeAddressDtl__"+i+"__OldCustCode").
					val(detailJson["fp__tWfCust_ChangeAddressDtl__"+i+"__OldCustCode"]);
				$("#fp__tWfCust_ChangeAddressDtl__"+i+"__OldDescr").
					val(detailJson["fp__tWfCust_ChangeAddressDtl__"+i+"__OldDescr"]);
				$("#fp__tWfCust_ChangeAddressDtl__"+i+"__OldAddress").
					val(detailJson["fp__tWfCust_ChangeAddressDtl__"+i+"__OldAddress"]);
				$("#fp__tWfCust_ChangeAddressDtl__"+i+"__Address").
					val(detailJson["fp__tWfCust_ChangeAddressDtl__"+i+"__Address"]);
				$("#fp__tWfCust_ChangeAddressDtl__"+i+"__OldAddress").openComponent_customer({
					showValue:detailJson["fp__tWfCust_ChangeAddressDtl__"+i+"__OldCustCode"],
					showLabel:detailJson["fp__tWfCust_ChangeAddressDtl__"+i+"__OldDescr"],
					condition:{status: "2,3,4,5",disabledEle:"status_NAME"},
					callBack: setOldAddress
				});
				$("#fp__tWfCust_ChangeAddressDtl__"+i+"__OldAddressDescr").
					val(detailJson["fp__tWfCust_ChangeAddressDtl__"+i+"__OldAddress"]);
			} else {
				$("#fp__tWfCust_ChangeAddressDtl__"+i+"__OldAddress").openComponent_customer({
					condition:{status: "2,3,4,5",disabledEle:"status_NAME"},
					callBack: setOldAddress
				});
			}
		}
		function setOldAddress(data) {
			var id = this.id.split("openComponent_customer_fp__tWfCust_ChangeAddressDtl__")[1].split("__OldAddress")[0];
			$("#fp__tWfCust_ChangeAddressDtl__"+id+"__OldAddress").val(data.address);
			$("#fp__tWfCust_ChangeAddressDtl__"+id+"__OldAddressDescr").val(data.address);
			$("#fp__tWfCust_ChangeAddressDtl__"+id+"__OldCustCode").val(data.code);
			$("#fp__tWfCust_ChangeAddressDtl__"+id+"__OldDescr").val(data.descr);
		}
		function clearDetail(){
			$("#fp__tWfCust_ChangeAddressDtl__0__OldAddress").val("");
			$("#fp__tWfCust_ChangeAddressDtl__0__OldAddressDescr").val("");
			$("#fp__tWfCust_ChangeAddressDtl__0__Address").val("");
		}
	</script>
</body>
</html>

