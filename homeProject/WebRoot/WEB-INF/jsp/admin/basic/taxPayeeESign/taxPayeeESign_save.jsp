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
	<title>电子合同机构管理新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_organization.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_orgSeal.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
<script type="text/javascript"> 
$(function() {
	$("#orgId").openComponent_organization({
		showValue:"${taxPayeeESign.orgId}",
		callBack:function(data){
			$("#sealId,#openComponent_orgSeal_sealId").val("");
			$("#sealId").openComponent_orgSeal({
				condition:{
					orgId:data.orgid
				},
				callBack:function(data){
					validateRefresh("openComponent_orgSeal_sealId","","dataForm");//刷新校验
				},
				preconditions:data.orgid == "",
				preconditionsFun:alertFunc
			});	
			validateRefresh("openComponent_organization_orgId","","dataForm");//刷新校验
		}
	});	
	$("#sealId").openComponent_orgSeal({
		showValue:$("#sealId").val(),
		condition:{
			orgId:$("#orgId").val()
		},
		callBack:function(data){
			validateRefresh("openComponent_orgSeal_sealId","","dataForm");//刷新校验
		},
		preconditions:$("#orgId").val() == "",
		preconditionsFun:alertFunc
	});	
	$("#openComponent_orgSeal_sealId,#openComponent_organization_orgId").attr("readonly",true);
	
	parent.$("#iframe_save").attr("height","98%"); //消灭掉无用的滑动条
	
	if("${taxPayeeESign.m_umState}"=="V"){
		$("#saveBtn,input,textarea,select").attr("disabled",true);
	}
	if("${taxPayeeESign.m_umState}"=="M"){
		$("#payeeCode").attr("disabled",true);
	}
	
	//选择印章的前置条件
	function alertFunc(){
		art.dialog({    	
			content: "请先选择机构账号！"
		});
	}
	
	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			openComponent_organization_orgId:{
				validators:{
					notEmpty:{
						message:"机构账号不能为空"
					}
				}
			},
			orgId:{
				validators:{
					notEmpty:{
						message:"机构账号不能为空"
					}
				}
			},
			openComponent_orgSeal_sealId:{
				validators:{
					notEmpty:{
						message:"印章Id不能为空"
					}
				}
			},
			sealId:{
				validators:{
					notEmpty:{
						message:"印章Id不能为空"
					}
				}
			},
			payeeCode:{
				validators:{
					notEmpty:{
						message:"收款单位不能为空"
					}
				}
			},
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
	
});

function doSave(){
	$("#dataForm").bootstrapValidator("validate");
    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
	var m_umState = "${taxPayeeESign.m_umState}";
	var resource = "doSave";
	$("#payeeCode").removeAttr("disabled");
    var datas = $("#dataForm").serialize();
    if(m_umState == "M"){
    	resource = "doUpdate";
    }
	$.ajax({
		type: "post",
		url:"${ctx}/admin/taxPayeeESign/"+resource,
		data: datas,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin(true);
				    }
				});
	    	}else{
	    		$("#_form_token_uniq_id").val(obj.token.token);
	    		art.dialog({
					content: obj.msg,
					width: 200
				});
				$("#payeeCode").attr("disabled",true);
	    	}
	    }
	});
}
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" name="pk" value="${taxPayeeESign.pk }"/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
			                        <label>收款单位</label>
			                        <house:dict id="payeeCode" dictCode="" sql="select code Code,rtrim(code)+' '+descr Descr from tTaxPayee where Expired = 'F' "
			                                    sqlValueKey="Code" sqlLableKey="Descr" value="${taxPayeeESign.payeeCode.trim() }"></house:dict>
			                    </li>
			                </div>
			                <div class="validate-group row">
								<li class="form-validate">
									<label>机构账号</label>
									<input type="text" id="orgId" name="orgId" onchange="changeOrg()" style="width:160px;" value="${taxPayeeESign.orgId }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>印章Id</label>
									<input type="text" id="sealId" name="sealId" style="width:160px;" value="${taxPayeeESign.sealId }"/>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
