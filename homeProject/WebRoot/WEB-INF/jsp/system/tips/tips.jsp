<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>小贴士设置</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<link href="${resourceRoot}/css/iss.core.css" rel="stylesheet" />
	<link href="${resourceRoot}/artDialog/skins/chrome.css" rel="stylesheet" />
	<link href="${resourceRoot}/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet"  />
	<script type="text/javascript" src="${resourceRoot}/js/jquery.js"></script>
	<script type="text/javascript" src="${resourceRoot}/js/iss.core.js"></script>
	<script type="text/javascript" src="${resourceRoot}/js/iss.panelBar.js"></script>
	<script type="text/javascript" src="${resourceRoot}/artDialog/source/artDialog.js"></script>
	<script type="text/javascript" src="${resourceRoot}/js/jquery.validate.js"></script>
	<script type="text/javascript" src="${resourceRoot}/js/additional-methods.js"></script>
	<script type="text/javascript" src="${resourceRoot}/datePicker/WdatePicker.js" ></script>
	<script type="text/javascript" src="${resourceRoot}/js/messages_cn.js"></script>
	<script type="text/javascript" src="${resourceRoot}/zTree/js/jquery.ztree.all-3.1.js"></script>

<script type="text/javascript">
var parentWin=window.opener;
$(function(){
	$document = $(document);
	$('div.panelBar', $document).panelBar();
	
});
function save(){
	if(!$("#dataForm").valid()) {return false;}//表单校验
	if($("#infoBoxDiv").css("display")!='none'){return false;}

	//验证
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/tips/doUpdate',
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
						if(parentWin != null)
						{
							parentWin.location.reload();
							//window.parent.frames["mainFrame"].location.reload();
							window.close();
						}
						//window.parent.frames["mainFrame"].location.reload();
						window.location.reload();
				    }
				});
	    	}else{
	    		art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	 });
}
function showAndHide(value){
	if(value==1){
		document.getElementById("back_message").style.display="";
	}
	if(value==2){
		document.getElemnetById("back_message").style.display="none";
	}
	
}
//校验函数
$(function() {
	$("#dataForm").validate({
		rules: {
			"tipscycle": {
				digits : true,
				max: 60000,
				min:5000
			},
			"stoptime":{
				digits : true,
				max: 60000,
				min:2000
			},
		}

	});
});

</script>

</head>
    
<body>
<div class="body-box-form" >
	<div class="content-form">
		<!--panelBar-->
		<div class="panelBar">
			<ul>
				<li >
					<a href="javascript:void(0)" class="a1" id="saveBut" onclick="save()">
					   <span>保存</span>
					</a>	
				</li>
				
				
				<li class="line"></li>
			</ul>
			<div class="clear_float"> </div>
		</div>
		<div class="infoBox" id="infoBoxDiv"></div>
		<div class="edit-form">
			<form action="" method="post" id="dataForm" enctype="multipart/form-data" >
			<input type="hidden"  name = "id"  value="${page.ID}" />
				<table cellpadding="0" cellspacing="0" style="width:99.9%">
				<col  width="100"/>
				<col  />
				<col  />
				<col  />
				<col  />
				<col  />
				<tbody>
					<tr >
							<td class="td-label">是否显示贴士</td>
							<td class="td-value">
								<c:if test="${page.TIPSSHOW==0 }"> 
									<label><input type="radio" id = "tipsshow" name = "tipsshow"  value="0"  checked="checked"/>不显示</label>
									<label><input type="radio" id = "tipsshow" name = "tipsshow"  value="1"  />显示</label>
								</c:if>
                             	<c:if test="${page.TIPSSHOW==1}">
                             		 <label><input type="radio" id = "tipsshow" name = "tipsshow"  value="0" />不显示</label>
                             		 <label><input type="radio" id = "tipsshow" name = "tipsshow"  value="1" checked="checked"/>设定时间显示</label>
                             	</c:if>
							</td>
							<td class="td-label">设定显示时间(以毫秒为单位)</td>
							<td class="td-value"><input type="text" id="tipscycle" name= "tipscycle"  value="${page.TIPSCYCLE }"/></td>
							<td class="td-label">设定停留时间(以毫秒为单位)</td>
							<td class="td-value"><input type="text" id="stoptime" name= "stoptime"  value="${page.STOPTIME }"/></td>
						</tr>
						<!--<tr>
							 <td class="td-label">是否显示邮件提醒</td>
							<td class="td-value">
								<c:if test="${page.TIPSEMAIL==1}">
									<label><input type="radio" id = "tipsdisplayemail" name = "tipsdisplayemail"  value="1"  checked="checked"/>提醒</label>
									<label><input type="radio" id = "tipsdisplayemail" name = "tipsdisplayemail"  value="0" />不提醒</label>
								</c:if>
                         		<c:if test="${page.TIPSEMAIL==0}">
                         			<label><input type="radio" id = "tipsdisplayemail" name = "tipsdisplayemail"  value="1" />提醒</label>
                         			<label><input type="radio" id = "tipsdisplayemail" name = "tipsdisplayemail"  value="0" checked="checked" />不提醒</label>
                         		</c:if>
							</td> 
							<td class="td-label">设定停留时间(以毫秒为单位)</td>
							<td class="td-value" colspan="3"><input type="text" name= "stoptime"  value="${page.STOPTIME }"/></td>  
						</tr>-->
					</tbody>	
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>
