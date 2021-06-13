<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	Global.LinkSelect.initSelect("${ctx}/admin/workCostDetail/workTypeByAuthority","workType1","workType2");
	Global.LinkSelect.setSelect({firstSelect:'workType1',
								firstValue:"${workCon.workType1}",
								secondSelect:'workType2',
								secondValue:"${workCon.workType2}"
								});	
	$("#custCode").openComponent_customer({
		showLabel:"${workCon.custDescr}",
		showValue:"${workCon.custCode}",
		callBack:function(data){
			$("#address").html(data.address);
		}
	});
	$("#saveBtn").on("click", function() {
		var custCode=$("#custCode").val();
		var workName=$("#workName").val();
		var workType1=$("#workType1").val();
		var workType2=$("#workType2").val();
		var amount=$("#amount").val();
		var area=$("#area").val();
		var constructDay=$("#constructDay").val();
		var workType2Descr=$("#workType2Descr").val();
		
		if(custCode==""){
			art.dialog({
				content : "请选择客户编号！",
			});
			return;
		}
		if(workName==""){
			art.dialog({
				content : "请填写工人姓名！",
			});
			return;
		}
		if(workType1==""){
			art.dialog({
				content : "请填写工种分类1！",
			});
			return;
		}
		if(workType2==""){
			art.dialog({
				content : "请填写工种分类2！",
			});
			return;
		}
		if(amount==""){
			art.dialog({
				content : "请填写金额！",
			});
			return;
		}
		if(area==""){
			art.dialog({
				content : "请填写面积！",
			});
			return;
		}
		if(constructDay==""){
			art.dialog({
				content : "请填写施工工期！",
			});
			return;
		}
		var result=checkExist(custCode,workType2);
		if(result){
			art.dialog({
				content : "此工种分类2【"+workType2Descr+"】已录入，不允许插入！",
			});
			return;
		}
		var datas = $("#dataForm").serialize();
		$.ajax({
			url : "${ctx}/admin/workCon/doSave",
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
function checkExist(custCode,workType2){
	var result=false;
	$.ajax({
		url : '${ctx}/admin/workCon/checkExist',
		type : 'post',
		data : {
			'custCode' :custCode,'workType2':workType2
		},
		async:false,
		dataType : 'json',
		cache : false,
		error : function(obj) {
			showAjaxHtml({
				"hidden" : false,
				"msg" : '保存数据出错~'
			});
		},
		success : function(obj) {
			if(obj.length>0){
				result=true;
			}
		}
	});
	return result;
}
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system "
						onclick="save()">保存</button>
					<button type="button" class="btn btn-system "
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<div class="body-box-form">
					<form role="form" class="form-search" id="dataForm" action=""
						method="post" target="targetFrame">
						<input type="hidden" id="workType2Descr" name="workType2Descr" value="${workCon.workType2Descr}" />
						<house:token></house:token>
						<ul class="ul-form">
							<li><label style="color:blue">客户编号</label> <input type="text" id="custCode"
								name="custCode" value="${workCon.custCode}" /> <span
								id="address"
								style="position: absolute;left:295px;width: 150px;top:3px;">${workCon.address}</span>
							</li>
							<li><label style="color:blue">工人姓名</label> <input type="text" id="workName"
								name="workName" value="${workCon.workName}" />
							</li>
							<li><label style="color:blue">工种分类1</label> <select id="workType1"
								name="workType1"></select>
							</li>
							<li><label style="color:blue">工种分类2</label> <select id="workType2"
								name="workType2" onchange="getDescrByCode('workType2','workType2Descr')"></select>
							</li>
							<li><label style="color:blue">金额</label> <input type="text" id="amount"
								name="amount" value="${workCon.amount}"
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" />
							</li>
							<li><label style="color:blue">面积</label> <input type="text" id="area"
								name="area" value="${workCon.area}"
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');" />
							</li>
							<li><label style="color:blue">施工工期</label> <input type="text" id="constructDay"
								name="constructDay"
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\.\d]/g,'');"
								value="${workCon.constructDay}" />
							</li>
							<li><label class="control-textarea">备注</label> <textarea
									id="remarks" name="remarks"
									style="overflow-y:scroll; overflow-x:hidden; height:60px; width:250px" />${workCon.remarks
								}</textarea>
							</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
</body>
</html>
