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
	<title>新增通知</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/sendNotice/prjTypeByItemType1Auth", "itemType1", "jobType");
	$("#custCode").openComponent_customer({
		showValue:"${sendNotice.custCode}",
		showLabel:"${sendNotice.custDescr}",
		callBack:function(data){
			$("#address").val(data.address);
		}
	});	

	$("#saveBtn").on("click",function(){
		if($.trim($("#custCode").val())==""){
			art.dialog({
				content:"请先选择客户编号！",
			});
			return;
		}
		if($.trim($("#itemType1").val())==""){
			art.dialog({
				content:"请先选择材料类型1！",
			});
			return;
		}
		if($.trim($("#jobType").val())==""){
			art.dialog({
				content:"请先选择任务类型！",
			});
			return;
		}

		doSave();
	});
});

function doSave(){
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:"${ctx}/admin/sendNotice/doAddNotice",
		type: "post",
		data:datas,
		dataType: "json",
		async:false,
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
			if(obj.rs){
				art.dialog({
					content:obj.msg,
					time:1000,
					beforeunload:function(){
						closeWin();
					}
				});
			}else{
				art.dialog({
					content:obj.msg,
				});
			}
	    }
	 });  
		
}
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			      <div class="btn-group-xs">
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" style="margin: 0px;">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<house:token></house:token><!-- 按钮只能点一次 -->
						<ul class="ul-form">
							<div class="row">
								<li class="form-validate">
									<label><span class="required">*</span>楼盘</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;"/>
								</li>
							</div>
							<div class="row">
								<li class="form-validate">
			      					<label><span class="required">*</span>材料类型1</label>
			      					<select id="itemType1" name="itemType1"></select>
			       				</li>
							</div>
							<div class="row">
								<li class="form-validate">
			       				    <label><span class="required">*</span>项目任务</label>
			       				    <house:dict id="jobType" dictCode="" sql="select rtrim(Code)Code,Descr from tJobType where Expired='F' order by Code " 
											sqlValueKey="Code" sqlLableKey="Descr"></house:dict>   
			       				</li>
							</div>
							<div class="row" >
								<li>
									<label class="control-textarea">发货备注</label>
									<textarea id="remarks" name="remarks" style="width: 320px"></textarea>
								</li>
							</div>
						</ul>	
					</form>
				</div>
			</div>
		</div>
	
	</body>	
</html>
