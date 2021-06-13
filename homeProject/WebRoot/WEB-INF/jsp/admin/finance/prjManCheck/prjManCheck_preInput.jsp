<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
	<title>项目经理结算</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "   id="btnUpdateQualityFee" onclick="updateQualityFee()">质保金录入</button>
				<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin()">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" name="m_umState" id="m_umState" />
				<input type="hidden" name="remarks" id="${prjCheck.remarks}" />
				<house:token></house:token>
				<ul class="ul-form">
						<li class="form-validate">
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode"   value="${prjCheck.custCode}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>客户名称</label>
							<input type="text" id="custDescr" name="custDescr"   value="${prjCheck.custDescr}" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>楼盘</label>
							<input type="text" id="address" name="address"   value="${prjCheck.address}" readonly="readonly"/>                                             
						</li>
					</ul>
			</form>
		</div>
	</div>
	
	<div  class="container-fluid" >  
	     <ul class="nav nav-tabs" >
	        <li id="tabPrjWithHold" class=""><a href="#tab_prjWithHold" data-toggle="tab">预扣录入</a></li>
	    </ul>  
	    <div class="tab-content">      
			<div id="tab_tabPrjWithHold"  class="tab-pane fade in active "> 
	         	<jsp:include page="tab_prjWithHold.jsp"></jsp:include>
			</div> 
		</div>	
	</div>
	
		
<script type="text/javascript">	
//校验函数 
function updateQualityFee(){
    Global.Dialog.showDialog("itemPlan_custTypeView",{
		title:"质保金录入",
		url : "${ctx}/admin/prjManCheck/goQualityFeeSave",
		postData:{custCode:"${prjCheck.custCode}",custDescr :"${prjCheck.custDescr}",address:"${prjCheck.address}",m_umState:"A"},
		height: 330,
		width:700,
		returnFun:function(){
			$("#isYklrMainExit").val("1");	
		}					
	});
}
function closeWin(){
 	if($("#isYklrMainExit").val()=="1" && "${prjCheck.appCzy}"!="" ){
 		art.dialog({
       		content: '是否重新生成项目经理结算数据？',
	        lock: true,
	        width: 260,
	        height: 100,
	        ok: function () {
	       		doSave();
	       		
	        },
		    cancel: function () {
	      		Global.Dialog.closeDialog(false);
		    }
	   	});
 	}else{
 		Global.Dialog.closeDialog(false);
 	}	
} 
function doSave(){
    var datas=$("#dataForm").jsonForm();
    datas.m_umState="G";	
	$.ajax({
		url:'${ctx}/admin/prjManCheck/doCheck',
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
	    				Global.Dialog.closeDialog(true);
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
}
</script>
</body>
</html>
