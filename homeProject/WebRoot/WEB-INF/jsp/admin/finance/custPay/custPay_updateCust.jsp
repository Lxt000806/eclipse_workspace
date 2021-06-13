<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>客户资料</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#designMan").openComponent_employee({showValue:"${map.DesignMan}",showLabel:"${map.DesignManDescr}"});
		$("#businessMan").openComponent_employee({showValue:"${map.BusinessMan}",showLabel:"${map.BusinessManDescr}"});
		$("#againMan").openComponent_employee({showValue:"${map.AgainMan}",showLabel:"${map.AgainManDescr}"});
		var custStatus="${map.Status}";
		if(custStatus=="3" || custStatus=="4"){
			$("#setDate").removeAttr("disabled");
			$("#designMan").setComponent_employee({readonly:true});
			$("#businessMan").setComponent_employee({readonly:true});
			$("#againMan").setComponent_employee({readonly:true});
			if(custStatus=="4"){
				$("#status").attr("disabled",true);
			}
		}else{
			$("#setDate").attr("disabled",true);
		}
		$("#saveBtn").on("click", function() {
			var setDate=$("#setDate").val();
			var custStatus=$("#status").val();
			var canCancel=$("#canCancel").val();
			if(custStatus==""){
				art.dialog({
					content : "请输入客户状态！",
					width : 200
				});
				return;
			}
			if(custStatus=="3" && setDate==""){
				art.dialog({
					content : "请输入下定时间！",
					width : 200
				});
				return;
			}
			if(canCancel==""){
				art.dialog({
					content : "请输入是否可退订！",
					width : 200
				});
				return;
			}
			doSave();
		});
	});	
	
	function doSave(){
		$("#setDate").removeAttr("disabled");
		$("#status").removeAttr("disabled");
		var datas = $("#dataForm").serialize();
		$.ajax({
				url : "${ctx}/admin/custPay/doUpdateCust",
				type : "post",
				data : datas,
				dataType : "json",
				cache : false,
				async:false,
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
	}
	function changeStatus(){
		var custStatus=$("#status").val();
		if(custStatus=="3"){
			$("#setDate").removeAttr("disabled");
			if($("#setDate").val()==""){
				$("#setDate").val("${map.minPayDate}".substring(0, 10));//切换成订单跟踪状态时，该楼盘下定时间为空，则填入付款信息里最早一条的付款日期
			}
			$("#canCancel").val("0");
		}else{
			$("#setDate").val("");
			$("#setDate").attr("disabled",true);
		}
	}
</script>
</head>
<body>
	<form action="" method="post" id="dataForm" class="form-search">
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut"
							onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-body">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="oldDesignMan" value="${map.DesignMan }" />
					<input type="hidden" name="oldBusinessMan" value="${map.BusinessMan }" />
					<input type="hidden" name="oldAgainman" value="${map.AgainMan}" />
					<input type="hidden" name="fromStatus" value="${map.Status}" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li><label style="width:185px;">客户编号</label>
								<input type="text" id="code" maxlength="10" name="code"
								style="width:160px;" value="${map.Code }" readonly="readonly"/></li>
						</div>
					</ul>
				</div>
			</div>
			<div class="container-fluid" id="id_detail">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tab_info" data-toggle="tab">主项目</a>
					</li>
				</ul>
				<ul class="ul-form">
					<div class="tab-content">
						<div id="tab_info" class="tab-pane fade in active">
							<div class="body-box-list"><br>
								<div class="validate-group row">
									<li><label>客户名称</label> <input type="text" id="descr"
										name="descr" style="width:160px;"
										value="${map.Descr }" readonly="readonly" /></li>
									<li><label><font color="blue">设计师</font></label> <input type="text" id="designMan"
										name="designMan" style="width:160px;"
										value="${map.DesignMan }" /></li>
								</div>
								<div class="validate-group row">
									<li><label>楼盘</label> <input type="text" id="address"
										name="address" style="width:160px;"
										value="${map.Address }" readonly="readonly" /></li>
									<li><label><font color="blue">业务员</font></label> <input type="text" id="businessMan"
										name="businessMan" style="width:160px;"
										value="${map.BusinessMan }" /></li>
								</div>
								<div class="validate-group row">
									<li><label><font color="blue">客户状态</font></label> <house:xtdm id="status"
									dictCode="CUSTOMERSTATUS" value="${map.Status }" unShowValue="${map.unShowValue}" onchange="changeStatus()"></house:xtdm> 
									</li>
							<li><label><font color="blue">翻单员</font></label> <input type="text" id="againMan"
										name="againMan" style="width:160px;"
										value="${map.AgainMan}" /></li>
								</div>
								<div class="validate-group row">
									<li><label><font color="blue">下定时间</font></label> <input type="text" id="setDate"
										name="setDate" class="i-date" style="width:160px;"
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${map.SetDate}' pattern='yyyy-MM-dd'/>"/>
									</li>
											<li><label><font color="blue">创建时间</font></label> <input type="text" id="crtDate"
										name="crtDate" class="i-date" style="width:160px;"
										value="<fmt:formatDate value='${map.CrtDate}' pattern='yyyy-MM-dd'/>" readonly="readonly"/>
									</li>
								</div>
 								<div class="validate-group row">
									<li><label><font color="blue">是否可退订</font></label> <house:xtdm id="canCancel"
									dictCode="YESNO" value="${map.CanCancel }"></house:xtdm> 
									</li>
								</div>
							</div>
						</div>
					</div>
				</ul>
			</div>
		</div>
	</form>
</body>
</html>
