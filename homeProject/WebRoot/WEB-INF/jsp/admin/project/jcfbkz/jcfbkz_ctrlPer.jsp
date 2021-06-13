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
	<title>分包比例</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

</head>	
	<body>
		<div class="body-box-list">
  			<div class="content-form">
				<div class="panel panel-system" >
					<div class="panel-body" >
						<div class="btn-group-xs" >
							<button type="button" class="btn btn-system " id="saveBtn">
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
						<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
							<ul class="ul-form">
							<div class="validate-group row" >
								<li class="form-validate">
									<label>客户编号</label>
									<input type="text" id="code" name="code" style="width:160px;" value="${customer.code}"/>
								</li>
								<li>
									<label>楼盘</label>
									<input type="text" id="address" name="address" style="width:160px;" value="${customer.address}" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row" >
								<li class="form-validate">
									<label>指定发包</label>
									<house:xtdm  id="specifyBaseCtrl" dictCode="YESNO"   style="width:160px;" value="${customer.specifyBaseCtrl}"></house:xtdm>
								</li>
								<li class="form-validate">
									<label>分包比例</label>
									<input type="text" id="baseCtrlPer" name="baseCtrlPer" style="width:160px;"
									 	onkeyup="value=value.replace(/[^\-?\d.]/g,'')" readonly="true" value="${customer.baseCtrlPer}"/>
								</li>
							</div>
							<div class="validate-group row" >
								<li class="form-validate">
									<label>分包金额</label>
									<input type="text" id="baseCtrlAmount" name="baseCtrlAmount" style="width:160px;" 
										onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="${customer.baseCtrlAmount}"/>
								</li>
							</div>
							</ul>	
						</form>
					</div>
				</div>
			</div>
		</div>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$("#page_form").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
			specifyBaseCtrl:{  
				validators: {  
					notEmpty: {  
						message: "指定发包不能为空"  
					}
				}  
			},
			baseCtrlPer:{  
				validators: {  
					notEmpty: {  
						message: "分包比例不能为空"  
					},
					numeric: {
		            	message: "分包比例只能是数字" 
		            },
				}  
			},
			baseCtrlAmount:{  
				validators: {  
					notEmpty: {  
						message: "分包金额不能为空"  
					},
					numeric: {
		            	message: "分包金额只能是数字" 
		            },
				}  
			},
		},
		submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});
$(function(){
	$("#code").openComponent_customer({showValue:"${customer.code}",showLabel:"${customer.descr}",readonly:true});	

	$("#saveBtn").on("click",function(){
		$("#page_form").bootstrapValidator('validate');
		if(!$("#page_form").data('bootstrapValidator').isValid()) return;
		var datas = $("#page_form").serialize();
		$.ajax({
            url : "${ctx}/admin/jcfbkz/doSaveCtrlPer",
            type : "post",
            data : datas,
            dataType : "json",
            cache : false,
            error : function(obj) {
                showAjaxHtml({
                    "hidden" : false,
                    "msg" : "保存数据出错~"
                });
            },
            success : function(obj) {
                if (obj.rs) {
                    art.dialog({
                        content : obj.msg,
                        time : 1000,
                        beforeunload : function() {
                            closeWin();
                        }
                    });
                } else {
                    $("#_form_token_uniq_id").val(obj.token.token);
                    art.dialog({
                        content : obj.msg,
                        width : 200
                    });
                }
            }
        });
	}); 
});	
</script>
	</body>
</html>
