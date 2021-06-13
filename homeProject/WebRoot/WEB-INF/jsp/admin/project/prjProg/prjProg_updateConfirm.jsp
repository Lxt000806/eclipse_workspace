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
	<title>编辑验收</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
function UpadteIsPass(){
	 isPass = $.trim($("#isPass").val());
	if($("#isPass").val()=='0'){
		$("#prjLevel").attr("disabled","disabled");
		$("#prjLevel").val("");
	}
	if($("#isPass").val()=='1'){
		$("#prjLevel").removeAttr("disabled","disabled");
	}
}

$(function() {

	$("#saveBtn").on("click",function(){
		var datas = $("#dataForm").serialize();
		$.ajax({
			url:'${ctx}/admin/prjProg/doUpdateConfirm',
			type: 'post',
			data: datas,
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin();
					    }
					});
		    	}else{
		    		$("#_form_token_uniq_id").val(obj.token.token);
		    		art.dialog({
						content: obj.msg,
						width: 200
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
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value="" />
							<ul class="ul-form">
								<li >
									<label >验收编号</label>
									<input type="text" id="no" name="no" style="width:160px;" value="${prjProgConfirm.no }"  readonly="readonly"/>
								</li>
								<li >
									<label >客户编号</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;" value="${prjProgConfirm.custCode }"  readonly="readonly"/>
								</li>
								<li>
									<label>楼盘</label>
									<input type="text" id="address" name="address" style="width:160px;" value="${prjProgConfirm.address }"  readonly="readonly"/>
								</li>
								<li>
									<label>施工项目</label>
									<house:xtdm id="prjItem" dictCode="PRJITEM"  value="${prjProgConfirm.prjItem}" disabled="true"></house:xtdm>
								</li>
								<li>
									<label>验收通过</label>
									<house:xtdm id="isPass" dictCode="YESNO" onchange="UpadteIsPass()"  value="${prjProgConfirm.isPass}"></house:xtdm>
								</li>
								<li>
									<label>验收评级</label>
									<house:xtdm id="prjLevel" dictCode="PRJLEVEL"  value="${prjProgConfirm.isPass}"></house:xtdm>
								</li>
								<li>
									<label>客户评分</label>
									<input type="text" id="custScoreComfirm" name="custScoreComfirm" style="width:160px;" value="${prjProgConfirm.custScoreComfirm }"  readonly="readonly"/>
								</li>
								<li>
									<label class="control-textarea">客户评价</label>
									<textarea id="custRemarks" name="custRemarks" rows="2" readonly="true">${prjProgConfirm.custRemarks }</textarea>
	 							</li>
	 							<li>
  								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2">${prjProgConfirm.remarks }</textarea>
  							</li>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
