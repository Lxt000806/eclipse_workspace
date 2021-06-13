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
	<title>文档管理查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_docFolder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}

	</style>
<script type="text/javascript"> 
$(function() {
	function getFolderData(data){
		if(!data) return;
		// $("#path").val(data.path);
		$("#docCode").val(data.path.replace(/\//g,'_'));
	}
	$("#folderPK").openComponent_docFolder({showValue:"${doc.folderPK}",readonly:true});
	$("#openComponent_docFolder_folderPK").blur();
	$("#openComponent_docFolder_folderPK").attr("readonly",true);
	$("#drawUpCZY").openComponent_employee({showValue:"${doc.drawUpCZY}",readonly:true});
	$("#confirmCZY").openComponent_employee({showValue:"${doc.confirmCZY}",readonly:true});
	$("#approveCZY").openComponent_employee({showValue:"${doc.approveCZY}",readonly:true});
	$("#openComponent_employee_drawUpCZY").blur();
	$("#openComponent_employee_confirmCZY").blur();
	$("#openComponent_employee_approveCZY").blur();
	
	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator("validate");
	    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
		var datas = $("#dataForm").serialize();
		$.ajax({
			url:"${ctx}/admin/doc/doUpdate",
			type: "post",
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
		    	}
		    }
		});
	});
});

function chgIsForRegular(obj){
	if ($(obj).is(":checked")){
		$("#isForRegular").val("1");
	}else{
		$("#isForRegular").val("0");
	}
}

function chgIsNeedNotice(obj){
	if ($(obj).is(":checked")){
		$("#isNeedNotice").val("1");
	}else{
		$("#isNeedNotice").val("0");
	}
}

</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      	<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(true)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" id="isForRegular" name="isForRegular" value="0"/>
						<input type="hidden" id="isNeedNotice" name="isNeedNotice" value="0"/>
						<input type="hidden" id="dateStr" name="dateStr" value="${doc.dateStr }"/>
						<input type="hidden" id="pk" name="pk" value="${doc.pk }"/>
						<input type="hidden" id="path" name="path" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li>
									<label>目录</label>
									<input type="text" id="folderPK" name=folderPK style="width:160px;"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>文档名称</label>
									<input type="text" id="docName" name="docName" style="width:450px;" value="${doc.docName }" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label>文档编号</label>
									<input type="text" id="docCode" name="docCode" 
										style="width:160px;" value="${doc.docCode }" readonly="true"/>
								</li>
								<li>
									<label>版本号</label>
									<input type="text" id="docVersion" name="docVersion" style="width:160px;"
									 value="${doc.docVersion }" placeholder="例：1.0" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>生效日期</label>
									<input type="text" id="enableDate" name="enableDate" class="i-date" 
									style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
									value="<fmt:formatDate value='${doc.enableDate }' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
								<li class="form-validate">
									<label>失效时间</label>
									<input type="text" id="expiredDate" name="expiredDate" class="i-date" 
									style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
									value="<fmt:formatDate value='${doc.expiredDate }' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>拟定人</label>
									<input type="text" id="drawUpCZY" name="drawUpCZY" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label>拟定日期</label>
									<input type="text" id="drawUpDate" name="drawUpDate" class="i-date" 
										style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${doc.drawUpDate }' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>审核人</label>
									<input type="text" id="confirmCZY" name="confirmCZY" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label>审核日期</label>
									<input type="text" id="confirmDate" name="confirmDate" class="i-date" 
										style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${doc.confirmDate }' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>审批人</label>
									<input type="text" id="approveCZY" name="approveCZY" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label>审批日期</label>
									<input type="text" id="approveDate" name="approveDate" class="i-date" 
										style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
										value="<fmt:formatDate value='${doc.approveDate }' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">内容简介</label>
									<textarea id="briefDesc" name="briefDesc" readonly="true">${doc.briefDesc }</textarea>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>关键字</label>
									<input type="text" id="keyWords" name="keyWords" style="width:448px;" 
											value="${doc.keyWords }" placeholder="支持多个关键字，空格隔开" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li style="padding-left:130px">
									<input type="checkbox" id="isForRegular_chg" name="isForRegular_chg"
							 			${doc.isForRegular == 1 ? 'checked':'' } disabled="true"/>
							 		<p style="float:right;margin-top:5px">员工可看</p>
								</li>
								<%--<li>	
									<input type="checkbox" id="isNeedNotice_chg" name="isNeedNotice_chg"
							 			 ${doc.isNeedNotice == 1 ? 'checked':'' } disabled="true"/>
							 		<p style="float:right;margin-top:5px">消息通告</p>
								</li>	
							--%></div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
