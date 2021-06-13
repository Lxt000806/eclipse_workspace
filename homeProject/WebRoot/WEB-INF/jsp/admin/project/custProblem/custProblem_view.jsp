<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>客户投诉处理——查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript"> 
	$(function(){
	$("#rcv").on("click", function() {
			$("#dataForm").bootstrapValidator('validate');
			if (!$("#dataForm").data('bootstrapValidator').isValid())
				return;
			var datas = $("#dataForm").serialize();
			$.ajax({
				url : "${ctx}/admin/custProblem/doRcv",
				type : "post",
				data : datas,
				dataType : "json",
				cache : false,
				error : function(obj) {
					showAjaxHtml({
						"hidden" : false,
						"msg" : "保存数据出错~"
					});
					closeWin();
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
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
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
							<li class="form-validate"><label>问题分类</label> <house:dict
									id="promType1" dictCode=""
									sql="select a.Code,a.code+' '+a.descr descr  from tPromType1 a  where  a.Expired='F'  order By Code "
									sqlValueKey="Code" sqlLableKey="Descr"
									value="${custProblem.promType1}" disabled="true">
								</house:dict></li>
							<li class="form-validate"><label>创建时间</label> <input
								type="text" id="crtDate" name="CrtDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								value="<fmt:formatDate value='${custProblem.crtDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
								disabled="true" /></li>
							<li class="form-validate"><label>计划处理时间</label> <input
								type="text" id="planDealDate" name="planDealDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custProblem.planDealDate}' pattern='yyyy-MM-dd '/>"
								disabled="true" /></li>
						</div>
						<div class="validate-group row">
							<c:if test="${custProblem.supplCode!=''}">
								<li class="form-validate"><label>供应商</label> <house:dict
										id="supplCode" dictCode=""
										sql="select a.Code,a.code+' '+a.descr descr  from tSupplier a  where  a.Expired='F'  order By Code "
										sqlValueKey="Code" sqlLableKey="Descr"
										value="${custProblem.supplCode}" disabled="true">
									</house:dict>
								</li>
							</c:if>
							<c:if test="${custProblem.supplCode==''}">
								<li><label>供应商</label> <input type="text" id="supplCode"
									name="supplCode" style="width:160px;" value="" disabled="true" />
								</li>
							</c:if>
							<li><label>通知时间</label> <input type="text" id="infoDate"
								name="infoDate" class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custProblem.infoDate}' pattern='yyyy-MM-dd '/>"
								disabled="true" /></li>
							<li class="form-validate"><label>实际处理时间</label> <input
								type="text" id="dealDate" name="dealDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custProblem.dealDate}' pattern='yyyy-MM-dd '/>"
								disabled="true" /></li>
						</div>
						<div class="validate-group row">
							<c:if test="${custProblem.promType2!=''}">
								<li class="form-validate"><label>材料分类</label> <house:dict
										id="promType2" dictCode=""
										sql="select a.Code,a.code+' '+a.descr descr  from tPromType2 a  where  a.Expired='F'  order By Code "
										sqlValueKey="Code" sqlLableKey="Descr"
										value="${custProblem.promType2}" disabled="true">
									</house:dict>
								</li>
							</c:if>
							<c:if test="${custProblem.promType2==''}">
								<li><label>材料分类</label> <input type="text" id="promType2"
									name="promType2" style="width:160px;" value="" disabled="true" />
								</li>
							</c:if>
							<c:if test="${custProblem.dealCZY!=''}">
								<li class="form-validate"><label>接收人</label> <house:dict
										id="dealCZY" dictCode=""
										sql="select a.Number,a.Number+' '+a.NameChi NameChi  from tEmployee a   order By Number "
										sqlValueKey="Number" sqlLableKey="NameChi"
										value="${custProblem.dealCZY}" disabled="true">
									</house:dict>
								</li>
							</c:if>
							<c:if test="${custProblem.dealCZY==''}">
								<li><label>接收人</label> <input type="text" id="dealCZY"
									name="dealCZY" style="width:160px;" value="" disabled="true" />
							</c:if>
							<li><label>处理状态</label> <house:xtdm id="status"
									dictCode="PROMSTATUS" value="${custProblem.status}"
									disabled="true"></house:xtdm></li>
						</div>
						<div class="validate-group row">
							<c:if test="${custProblem.promRsn!=''}">
								<li class="form-validate"><label>问题原因</label> <house:dict
										id="promRsn" dictCode=""
										sql="select a.Code,a.code+' '+a.descr descr  from tPromRsn a  where  a.Expired='F'  order By Code "
										sqlValueKey="Code" sqlLableKey="Descr"
										value="${custProblem.promRsn}" disabled="true">
									</house:dict></li>
							</c:if>
							<c:if test="${custProblem.promRsn==''}">
								<li><label>问题原因</label> <input type="text" id="promType2"
									name="promRsn" style="width:160px;" value="" disabled="true" />
								</li>
							</c:if>
							<li class="form-validate"><label>接收时间</label> <input
								type="text" id="rcvDate" name="rcvDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								value="<fmt:formatDate value='${custProblem.rcvDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
								disabled="true" /></li>
							<li class="form-validate"><label class="control-textarea">处理结果</label>
								<textarea id="dealRemarks" name="dealRemarks" style="overflow-y:scroll; overflow-x:hidden; height:75px; "
								 disabled="true" />${custProblem.dealRemarks
								}</textarea></li>
						</div>
						<div class="validate-group row">
							<li><label>投诉编号</label> <input type="text" id="no" name="no"
								style="width:160px;" value="${custProblem.no}" disabled="true" />
								<input type="hidden" id="pk" name="pk" style="width:160px;"
								value="${custProblem.pk}" /></li>
							<li><label>客户编号</label> <input type="text" id="custCode"
								name="custCode" style="width:160px;"
								value="${custProblem.custCode}" disabled="true" /></li>
							<li><label>楼盘</label> <input type="text" id="address"
								name="address" style="width:160px;"
								value="${custProblem.address}" disabled="true" /></li>
						</div>
						<div class="validate-group row">

							<li><label>楼盘状态</label> <house:xtdm id="addressStatus"
									dictCode="CUSTOMERSTATUS" value="${custProblem.addessStatus}"
									disabled="true"></house:xtdm></li>
							<li><label>业主姓名</label> <input type="text" id="custDescr"
								name="custDescr" style="width:160px;"
								value="${custProblem.custDescr}" disabled="true" /></li>
							<li><label>业主电话</label> <input type="text" id="mobile1"
								name="mobile1" style="width:160px;"
								value="${custProblem.mobile1}" disabled="true" /></li>
						</div>
						<div class="validate-group row">
							<li><label>设计师</label> <input type="text"
								id="designManDescr" name="designManDescr" style="width:160px;"
								value="${custProblem.designManDescr}" disabled="true" /></li>
							<li><label>设计师电话</label> <input type="text"
								id="designManPhone" name="designManPhone" style="width:160px;"
								value="${custProblem.designManPhone}" disabled="true" /></li>
							<li><label>设计部</label> <input type="text" id="projectDept3"
								name="projectDept3" style="width:160px;"
								value="${custProblem.projectDept3}" disabled="true" /></li>
						</div>
						<div class="validate-group row">

							<li><label>项目经理</label> <input type="text"
								id="projectManDescr" name="projectManDescr" style="width:160px;"
								value="${custProblem.projectManDescr}" disabled="true" /></li>
							<li><label>项目经理电话</label> <input type="text"
								id="projectManPhone" name="projectManPhone" style="width:160px;"
								value="${custProblem.projectManPhone}" disabled="true" /></li>
							<li><label>工程部</label> <input type="text" id="projectDept2"
								name="projectDept2" style="width:160px;"
								value="${custProblem.projectDept2}" disabled="true" /></li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label>实际结算时间</label> <input
								type="text" id="checkOutDate" name="checkOutDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								value="<fmt:formatDate value='${custProblem.checkOutDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
								disabled="true" /></li>
							<li class="form-validate"><label>客户类型</label> <house:dict
									id="custType" dictCode=""
									sql="select a.IBM,a.NOTE NOTE  from tXTDM a  where  a.ID='CUSTTYPE'  order By IBM "
									sqlValueKey="IBM" sqlLableKey="NOTE"
									value="${custProblem.custType}" disabled="true">
								</house:dict>
							</li>
							<li class="form-validate"><label>状态</label> <house:dict
									id="custType" dictCode=""
									sql="select a.IBM,a.NOTE NOTE  from tXTDM a  where  a.ID='COMPSTATUS'  order By IBM "
									sqlValueKey="IBM" sqlLableKey="NOTE"
									value="${custProblem.compStatus}" disabled="true">
								</house:dict>
							</li>
						</div>

						<div class="validate-group row">
							<li><label>问题来源</label> <input type="text" id="source"
								name="source" style="width:160px;" value="${custProblem.source}"
								disabled="true" /></li>
							<li><label>登记人</label> <house:dict id="crtCZY" dictCode=""
									sql="select a.Number,a.Number+' '+a.NameChi NameChi  from tEmployee a   order By Number "
									sqlValueKey="Number" sqlLableKey="NameChi"
									value="${custProblem.crtCZY}" disabled="true">
								</house:dict></li>
							<li class="form-validate"><label>投诉时间</label> <input
								type="text" id="jsCrtDate" name="jsCrtDate" class="i-date"
								style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								value="<fmt:formatDate value='${custProblem.jsCrtDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
								disabled="true" /></li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label class="control-textarea">投诉内容</label>
								<textarea id="remarks" name="remarks"
									style="overflow-y:scroll; overflow-x:hidden; height:80px; "
									disabled="true" />${custProblem.remarks }</textarea></li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
