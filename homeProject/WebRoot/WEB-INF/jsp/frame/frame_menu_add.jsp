<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html>
<head>
	<title>添加盖章申请表 </title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	
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

function goto_query(){
	alert("123");
	$.form_submit($("#page_form").get(0), {
		"action" : "${ctx}/sealApply/goList",
		"pageNo" : "1"
	})
}
function save(){

	//验证
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/frame/doAdd',
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
							parentWin.location.reload();
						closeWin();
							
	    				 
				    }
				});
	    	}else{
	    		showAjaxHtml({"hidden": false, "msg": obj.msg});
	    	}
	    }
	 });
}
//校验函数
$(function() {
	
	$("#dataForm").validate({
		rules: {
			 "apply_time": {
				maxlength: 20,
				required: true
			}, 
			"seal_type": {
				maxlength: 10,
				required: true
			},
			"reason": {
				validIllegalChar: true,
				required: true,
				maxlength: 1000
			}			
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
					<a href="javascript:void(0)" class="a1" id="save" onclick="save()">
					   <span>添加</span>
					</a>	
				</li>
				
			
				<li id="closeBut" onclick="closeWin(false)">
					<a href="javascript:void(0)" class="a2">
						<span>关闭</span>
					</a>
				</li>
				<li class="line"></li>
			</ul>
			<div class="clear_float"> </div>
		</div>

		<div class="infoBox" id="infoBoxDiv"></div>
		<div class="edit-form">
			<form action="" method="post" id="dataForm">
				<input type="hidden" id="businessKey" name="businessKey" value="${businessKey }"/>
				<table cellspacing="0" cellpadding="0" width="100%">
					<col  width="72"/>
					<col  width="250"/>
					<col  width="72"/>
					<col  width="250"/>
					<tbody>
						<tr>
							
							 <td class="td-label"><span class="required">*</span>我的工作模块</td>
							<td class="td-value">
								<select style="width:160px;" id="menuName" name="menuName" >
								      <option value="">请选择...</option>
								       <c:forEach var="it" items="${menu1}">
								        <option value="${it.menuName}">${it.menuName}</option>
								      </c:forEach>				
								 </select>
							<%-- <c:if test="${os.os==it.itemValue}">selected="selected"</c:if> >${it.itemLabel} --%>
						</td> 
						
						</tr>
						
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>
