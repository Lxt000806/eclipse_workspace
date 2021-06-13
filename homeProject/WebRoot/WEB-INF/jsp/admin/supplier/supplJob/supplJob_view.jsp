<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>供应商任务处理——查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript"> 
	$(function(){
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
	});
	//查看图片
	function viewPhoto(){
		Global.Dialog.showDialog("cupboardSend",{
			title:"查看图片",
			url:"${ctx}/admin/supplJob/goViewPhoto?no="+$("#no").val(),
			height:600,
			width:1100,
			returnFun:goto_query
		});
	}
	</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="viewPhoto"
						onclick="viewPhoto()">
						<span>查看图片</span>
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
				<form action="" method="post" id="dataForm" class="form-search">
					<ul class="ul-form">
						<div class="validate-group row">
							<li><label>任务单号</label> <input type="text" id="no" name="no"
								style="width:160px;" value="${supplJob.no}" disabled="true" />
							</li>
							<li><label>楼盘</label> <input type="text" id="address"
								name="address" style="width:160px;" value="${supplJob.address}"
								disabled="true" />
							</li>
						</div>
						<div class="validate-group row">
							<li>
							    <label>客户姓名</label>
							    <input type="text" id="custName" name="custName" value="${supplJob.custName}" readonly/>
							</li>
							<li>
							    <label>客户电话</label>
							    <input type="text" id="custMobile1" name="custMobile1" value="${supplJob.custMobile1}" readonly/>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>任务类型</label> <input type="text" id="jobTypeDescr"
								name="jobTypeDescr" style="width:160px;"
								value="${supplJob.jobTypeDescr}" disabled="true" />
							</li>
							<li>
                                <label>工地密码</label>
                                <input type="text" id="buildPass" name="buildPass" value="${supplJob.buildPass}" readonly/>
                            </li>
							<li><label>状态</label> <house:xtdm id="status"
									dictCode="SUPPLJOBSTS" value="${supplJob.status }" disabled="true" ></house:xtdm>
							</li>

						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>指派时间</label> <input
								type="text" id="date" name="date" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								value="<fmt:formatDate value='${supplJob.date}' pattern='yyyy-MM-dd HH:mm:ss'/>"
								disabled="true" />
							</li>
							<li class="form-validate"><label>接收时间</label> <input
								type="text" id="recvDate" name="recvDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								value="<fmt:formatDate value='${supplJob.recvDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
								disabled="true" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>完成时间</label> <input
								type="text" id="completeDate" name="completeDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								value="<fmt:formatDate value='${supplJob.completeDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
								disabled="true" />
							</li>
							<li><label>计划处理时间</label> <input
								type="text" id="planDate" name="planDate" class="i-date"
								style="width:160px;" onchange="validateRefresh()" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${supplJob.planDate}' pattern='yyyy-MM-dd '/>" disabled="true" />
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label
								class="control-textarea">任务备注</label> <textarea
									id="rwRemarks" name="rwRemarks"  disabled="true" />${supplJob.rwRemarks
								}</textarea>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label
								class="control-textarea">指派备注</label> <textarea
									id="remarks" name="remarks"  disabled="true" />${supplJob.remarks
								}</textarea>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label
								class="control-textarea">供应商备注</label> <textarea
									id="supplRemarks" name="supplRemarks"  disabled="true" />${supplJob.supplRemarks
								}</textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
