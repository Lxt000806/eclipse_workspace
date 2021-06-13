<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<ul class="ul-form">
						<div class="row">
							<li class="form-validate">
								<label for="intspl"><span class="required">*</span>供应商</label>
								<input type="text" id="intspl" name="intspl">
							</li>
							<li class="form-validate">
								<label for="type"><span class="required">*</span>补货类型</label>
								<house:xtdm id="type" dictCode="IntRepType" style="width:160px;"
									value="${intReplenishDT.type}"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>补货时间</label> 
								<input type="text" id="date" name="date" class="i-date"
									onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',readOnly:true})" 
									/>
							</li>
							<li class="form-validate">
								<label for="respart">责任人</label>
								<house:xtdm id="respart" dictCode="IntRepResPart" style="width:160px;"
									value="${intReplenishDT.resPart}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label for="underTaker">承担方</label>
								<house:xtdm id="undertaker" dictCode="IntRepResPart" style="width:160px;"
									value="${intReplenishDT.undertaker}"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate" style="max-height: 120px;">
								<label class="control-textarea" style="top: -60px;">
									<span class="required">*</span>补货详情
								</label>
								<textarea id="remarks" name="remarks" 
									style="height: 88px;">${intReplenishDT.remarks}</textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript"> 
		$(function() {
			switch ("${intReplenishDT.m_umState}") {
			case "M"://编辑中的编辑
				$("#date").val(formatDate("${intReplenishDT.date}"));
				$("#intspl").openComponent_supplier({
					showValue: "${intReplenishDT.intSpl}",
					showLabel: "${intReplenishDT.intSplDescr}",
					condition: {itemType1:"JC",isDisabled:"1"},
					callBack: validateRefresh_local
				});
				break;
			default:
				$("#intspl").openComponent_supplier({
					condition: {itemType1:"JC",isDisabled:"1"},
					callBack: validateRefresh_local
				});
				break;
			}
			
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {/*input状态样式图片*/
					validating : "glyphicon glyphicon-refresh"
				},
				fields: {  
					openComponent_supplier_intspl:{  
						validators: {  
							notEmpty: {  
								message: "供应商不能为空"  
							}
						}  
					},
					type:{  
						validators: {  
							notEmpty: {  
								message: "补货类型不能为空"  
							}
						}  
					},
					date:{  
						validators: {  
							notEmpty: {  
								message: "补货时间不能为空"  
							}
						}  
					},
					remarks:{  
						validators: {  
							notEmpty: {  
								message: "补货详情不能为空"  
							}
						}  
					}
				},
				submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
			});
			$("#saveBtn").on("click",function(){
				$("#page_form").bootstrapValidator("validate");
				if(!$("#page_form").data("bootstrapValidator").isValid()) return;
				if (""==$("#date").val()) {
					art.dialog({content: "无法保存，请输入补货时间",width: 220});
					return;
				}
				
				var arr2 = $("#openComponent_supplier_intspl").val().split("|");
				var componentDescr=arr2[arr2.length-1];
				// 将page_form json化后赋给selectRows 
		 		var selectRows = [];
				var datas=$("#page_form").jsonForm();
				datas.typedescr=getSelectDescr("type");
				datas.intspldescr=componentDescr;
				datas.respartdescr=getSelectDescr("respart");
				datas.undertakerdescr=getSelectDescr("undertaker");
			 	selectRows.push(datas);
			 	//console.log(selectRows);
				Global.Dialog.returnData = selectRows;
				
				closeWin();
			});
		});
		//获取选择组件中的描述
		function getSelectDescr(name) {
			var selectedText=$("#"+name).find("option:selected").text();
			var arr = selectedText.split(" ");
			var selectedDescr=arr[arr.length-1];
			return selectedDescr=="请选择..."?"":selectedDescr;
		}
		function validateRefresh_local() {
			$("#page_form").data("bootstrapValidator")  
				.updateStatus("openComponent_supplier_intspl", "NOT_VALIDATED", null)
				.validateField("openComponent_supplier_intspl");
		}
	</script>
</body>	
</html>
