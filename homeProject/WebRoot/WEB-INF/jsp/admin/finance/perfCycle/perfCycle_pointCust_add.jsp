<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>指定客户--增加</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#custCode").openComponent_customer({
			showValue:"${perfCycle.custCode}",
			showLabel:"${perfCycle.custDescr}",
			callBack:function(data){
				$("#address").html(data.address);
				$("#custStatus").val(data.status);
			},
			condition:{status:"4,5",laborFeeCustStatus:"1,2,3",mobileHidden:"1"}
		});
		if("${perfCycle.m_umState }"!="A"){
			$("#custCode").setComponent_customer({readonly:true});
			$("#openComponent_customer_custCode").attr("readonly",true);
		}
		if("${perfCycle.m_umState }"=="V"){
			$("#achieveDate").attr("disabled",true);
			$("#remarks").attr("readonly",true);
			$("#saveBtn").attr("disabled",true);
		}
		$("#saveBtn").on("click", function() {
			var custCode=$("#custCode").val();
			var achieveDate=$("#achieveDate").val();
			var isCalcPerf=checkIsCalcPerf(custCode);//是否计算业绩
			if(custCode==""){
				art.dialog({
					content : "请选择客户编号！",
				});
				return;
			}
			if("${perfCycle.isCalcPerf }"=="1" && achieveDate==""){
				art.dialog({
					content : "请选择业绩达标日期！",
				});
				return;
			}
			if(isCalcPerf=="1"){
				art.dialog({
					content : "已指定该客户参与本期业绩计算！",
				});
				return;
			}else if(isCalcPerf=="0"){
				art.dialog({
					content : "已指定该客户不参与本期业绩计算！",
				});
				return;
			}
			var url="${ctx}/admin/manualPerfCust/";
			if("${perfCycle.m_umState }"=="A"){
				url+="doSave";
			}else if("${perfCycle.m_umState }"=="M"){
				url+="doUpdate";
			}
			var datas = $("#dataForm").serialize();
			$.ajax({
				url : url,
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
	//是否计算业绩
	function checkIsCalcPerf(custCode){
		var isCalcPerf="";
		$.ajax({
			url:"${ctx}/admin/perfCycle/checkIsCalcPerf",
			type:"post",
			data:{custCode:custCode,no:"${perfCycle.no }",pk:"${perfCycle.manualPerfCustPk }"},
			dataType:"json",
			cache:false,
			async:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
			},
			success:function(obj){
				if(obj.length>0){
					isCalcPerf=obj[0].IsCalcPerf;
				}
			}
		});
		return isCalcPerf;
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
						type="hidden" name="m_umState" value="${perfCycle.m_umState }" />
					<input type="hidden" name="pk" value="${perfCycle.manualPerfCustPk }" />
					<input type="hidden" name="perfCycleNo" value="${perfCycle.no }" />
					<input type="hidden" name="isCalcPerf" value="${perfCycle.isCalcPerf }" />
					<ul class="ul-form">
						<div class="validate-group row">
							<li><label>客户编号</label> <input type="text" id="custCode"
								name="custCode" value="${perfCycle.custCode }" />
							</li><span id="address" style="position:absolute;top:54px;left:310px">${perfCycle.address }</span>
						</div>
						<c:if test="${perfCycle.selectedTab=='cyyjjsDataTable'}">
							<div class="validate-group row">
								<li><label>业绩达标日期</label> <input type="text"
									id="achieveDate" name="achieveDate" class="i-date"
									style="width:160px;"
									value="<fmt:formatDate value='${perfCycle.achieveDate }' pattern='yyyy-MM-dd'/>"
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
								</li>
							</div>
						</c:if>
						<div class="validate-group row" style="height:150px">
							<li class="form-validate"><label class="control-textarea">说明</label>
								<textarea id="remarks" name="remarks"
									style="overflow-y:scroll; overflow-x:hidden; height:100px; " />${perfCycle.remarks
								}</textarea>
							</li>
						</div>
					</ul>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
