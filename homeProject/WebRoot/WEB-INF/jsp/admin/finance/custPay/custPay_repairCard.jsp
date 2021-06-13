<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>付款计划</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#projectMan").openComponent_employee({showValue:"${map.ProjectMan}",showLabel:"${map.ProjectManDescr}",readonly:true});
		$("#repairCardMan").openComponent_employee({showValue:"${map.RepairCardMan}",showLabel:"${map.RepairCardManDescr}"});
		$("#cdman").openComponent_employee({showValue:"${map.CDMan}",showLabel:"${map.CDManDescr}"});
		$("#saveBtn").on("click", function() {
			var isUpPosiPic=$("#isUpPosiPic").val();
			if(isUpPosiPic==""){
				art.dialog({
					content : "请填写水电定位图是否上传！",
					width : 200
				});
				return;
			}
			doSave();
		});
	});	
	
	function doSave(){
		var datas = $("#dataForm").serialize();
		$.ajax({
				url : "${ctx}/admin/custPay/updateRepairCard",
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
					<input type="hidden" name="jsonString" value="" /> <input
						type="hidden" name="oldDesignMan" value="${map.DesignMan }" /> <input
						type="hidden" name="oldBusinessMan" value="${map.BusinessMan }" />
					<input type="hidden" name="oldAgainman" value="${map.AgainMan}" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li><label style="width:185px;">客户编号</label> <input
								type="text" id="code" maxlength="10" name="code"
								style="width:160px;" value="${map.Code }" readonly="readonly" />
							</li>
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
							<div class="body-box-list">
								<br>
								<div class="validate-group row">
									<li><label><font color="blue">竣工日期</font> </label> <input
										type="text" id="consEndDate" name="consEndDate" class="i-date"
										style="width:160px;"
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${map.ConsEndDate}' pattern='yyyy-MM-dd'/>" />
									</li>
									<li><label>客户名称</label> <input type="text" id="descr"
										name="descr" style="width:160px;" value="${map.Descr }"
										readonly="readonly" /></li>
								</div>
								<div class="validate-group row">
									<li><label><font color="blue">保修卡领取人</font> </label> <input
										type="text" id="repairCardMan" name="repairCardMan"
										style="width:160px;" value="${map.RepairCardMan }" /></li>
									<li><label>楼盘</label> <input type="text" id="address"
										name="address" style="width:160px;" value="${map.Address }"
										readonly="readonly" /></li>
								</div>
								<div class="validate-group row">
									<li><label><font color="blue">保修卡审核时间</font> </label> <input
										type="text" id="repairCardDate" name="repairCardDate"
										class="i-date" style="width:160px;"
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${map.RepairCardDate}' pattern='yyyy-MM-dd'/>" />
									</li>
									<li><label>项目经理</label> <input type="text" id="projectMan"
										name="projectMan" style="width:160px;"
										value="${map.ProjectMan }" /><span id="projectManDept2"
								style="position: absolute;left:290px;width: 70px;top:3px;">${map.ProjectManDept2}</span></li>
								</div>
								<div class="validate-group row">
									<li><label><font color="blue">水电图片领取人</font> </label> <input
										type="text" id="cdman" name="cdman" style="width:160px;"
										value="${map.CDMan}" /></li>
									<li><label>实际开工日期</label> <input type="text"
										id="confirmBegin" name="confirmBegin" class="i-date"
										style="width:160px;"
										value="<fmt:formatDate value='${map.ConfirmBegin}' pattern='yyyy-MM-dd'/>" readonly="readonly"/>
									</li>
								</div>
								<div class="validate-group row">
									<li><label><font color="blue">水电图片审核时间</font> </label> <input
										type="text" id="cddate" name="cddate" class="i-date"
										style="width:160px;"
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${map.CDDate}' pattern='yyyy-MM-dd'/>" />
									</li>
									<li><label>实际结算日期</label> <input type="text"
										id="checkOutDate" name="checkOutDate" class="i-date"
										style="width:160px;" readonly="readonly"
										value="<fmt:formatDate value='${map.CheckOutDate}' pattern='yyyy-MM-dd'/>" />
									</li>
								</div>
								<div class="validate-group row">
									<li><label><font color="blue">水电位图是否上传</font> </label> <house:xtdm
											id="isUpPosiPic" dictCode="YESNO" value="${map.IsUpPosiPic }"></house:xtdm>
									</li>
								</div>
								<div class="validate-group row">
									<li><label class="control-textarea"><font color="blue">备注</font></label> <textarea
											id="repairRemark" name="repairRemark"
											style="overflow-y:scroll; overflow-x:hidden; height:75px; " />${map.RepairRemark
										}</textarea>
									</li>
								</div>
								<div class="validate-group row">
									<li><label class="control-textarea">优惠政策</label> <textarea
											id="discRemark" name="discRemark" readonly="readonly"
											style="overflow-y:scroll; overflow-x:hidden; height:75px; " />${map.DiscRemark
										}</textarea>
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
