<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>

<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>定责单明细新增</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_baseCheckItem.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_baseItem.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	$(function(){
	
	    if ("${fixDutyDetail.fromPage}" === "designer") {
	        $("#baseItemLi").show()
	        $("#baseCheckItemLi").hide()
	    }
	
		if("V"=="${fixDutyDetail.m_umState}"){
			$("#saveBtn").remove();
			disabledForm("dataForm");
			$("#baseCheckItem").openComponent_baseCheckItem({
				showValue:"${fixDutyDetail.baseCheckItemCode}",
				showLabel:"${fixDutyDetail.baseCheckItemDescr}",
				disabled:true,
			});
			$("#baseItem").openComponent_baseItem({
				showValue:"${fixDutyDetail.baseItemCode}",
				showLabel:"${fixDutyDetail.baseItemDescr}",
				disabled:true,
			});
		}else if("M"=="${fixDutyDetail.m_umState}"){
			$("#baseCheckItem").openComponent_baseCheckItem({
				showValue:"${fixDutyDetail.baseCheckItemCode}",
				showLabel:"${fixDutyDetail.baseCheckItemDescr}",
				condition:{workType12:"${fixDutyDetail.workType12}",
				custCode:"${fixDutyDetail.custCode}"},
				callBack:function(ret){
					$("#offerPri").val(ret.offerpri);
					$("#material").val(ret.material);
					$("#baseCheckItemCode").val(ret.code);
					$("#baseCheckItemDescr").val(ret.descr);
				}
			});
			
			$("#baseItem").openComponent_baseItem({
                showValue:"${fixDutyDetail.baseItemCode}",
                showLabel:"${fixDutyDetail.baseItemDescr}",
                condition:{custCode:"${fixDutyDetail.custCode}"},
                callBack:function(ret){
                    $("#offerPri").val(ret.offerpri);
                    $("#material").val(ret.material);
                    $("#baseItemCode").val(ret.code);
                    $("#baseItemDescr").val(ret.descr);
                }
            });
		}else{
			$("#baseCheckItem").openComponent_baseCheckItem({
				condition:{workType12:"${fixDutyDetail.workType12}",
				custCode:"${fixDutyDetail.custCode}"},
				callBack:function(ret){
					$("#offerPri").val(ret.offerpri);
					$("#material").val(ret.material);
					$("#baseCheckItemCode").val(ret.code);
					$("#baseCheckItemDescr").val(ret.descr);
				}
			})
			
			$("#baseItem").openComponent_baseItem({
                condition:{custCode:"${fixDutyDetail.custCode}"},
                callBack:function(ret){
                    $("#offerPri").val(ret.offerpri);
                    $("#material").val(ret.material);
                    $("#baseItemCode").val(ret.code);
                    $("#baseItemDescr").val(ret.descr);
                }
            });
		}
		
		$("#qty").keyup(function(e) {  
			if (e.which == 13) {// 判断所按是否回车键  
		       $("#remarks").focus();
		    } else if(e.which>57||e.which<48){
		    	$("#qty")[0].value=$("#qty")[0].value.replace(/[^\-?\d.]/g,'')
		    }
	    });  
		
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator("validate");/* 提交验证 */
			if(!$("#dataForm").data("bootstrapValidator").isValid()) return; 
			doSave();
		});	
		
		$("#dataForm").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: { 
				openComponent_baseCheckItem_code:{  
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}              
					}  
				},
				qty:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}, 
					}
				},
			}
		});
	});	
	
	
	

	
	
	function doSave(){
	 		var selectRows = [];
			var datas=$("#dataForm").jsonForm();
		 	selectRows.push(datas);
			Global.Dialog.returnData = selectRows;
			closeWin();
	}
		
	</script>
</head>
<body>
	<div class="body-box-form">
        <div class="panel panel-system">
	    <div class="panel-body">
	      <div class="btn-group-xs">
				<button type="button" class="btn btn-system" id="saveBtn" >
					<span>保存</span>
				</button>
				<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
					<span>关闭</span>
				</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info">  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
				<house:token></house:token>
				<input type="hidden" id="offerPri" name="offerPri" value="${fixDutyDetail.offerPri }" />
				<input type="hidden" id="material" name="material" value="${fixDutyDetail.material }" />
				<input type="hidden" id="custCode" name="custCode" value="${fixDutyDetail.custCode }" />
				<input type="hidden" id="workType12" name="workType12" value="${fixDutyDetail.workType12 }" />
				<input type="hidden" id="baseItemCode" name="baseItemCode" value="${fixDutyDetail.baseItemCode}" />
				<input type="hidden" id="baseItemDescr" name="baseItemDescr" value="${fixDutyDetail.baseItemDescr}" />
				<input type="hidden" id="baseCheckItemCode" name="baseCheckItemCode" value="${fixDutyDetail.baseCheckItemCode }" />
				<input type="hidden" id="baseCheckItemDescr" name="baseCheckItemDescr" value="${fixDutyDetail.baseCheckItemDescr }" />
				<input type="hidden" id="pk" name="pk"value="${fixDutyDetail.pk }">
				<ul class="ul-form">	
					<div class="validate-group row">
						<li id="baseCheckItemLi">
							<label>结算项目</label>
							<input type="text" id="baseCheckItem" name="baseCheckItem" style="width:160px;" value="${fixDutyDetail.baseCheckItemDescr }"  readonly="readonly"/>
						</li>
						<li id="baseItemLi" style="display: none">
                            <label>基础项目</label>
                            <input type="text" id="baseItem" name="baseItem" style="width:160px;" value="${fixDutyDetail.baseItemDescr}"  readonly="readonly"/>
                        </li>
						<li class="form-validate">
							<label ><span class="required">*</span>数量</label>
							<input type="text" id="qty" name="qty" style="width:160px;" value="${fixDutyDetail.qty}"/>
						</li>
					</div>
					<div class="validate-group row">
						<li >
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" rows="4" >${fixDutyDetail.remarks }</textarea>
						</li>
					</div>
				</ul>	
			</form>
		</div>
	</div>
</div>
</body>
</html>


