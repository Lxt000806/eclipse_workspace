<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@page import="com.house.framework.commons.conf.SystemConfig"%>
<%
	String itemCode = SystemConfig.getProperty("itemCode","0", "ptdm");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统登录</title>
<link href="${resourceRoot}/css/login.css" rel="stylesheet" type="text/css" />
<script src="${resourceRoot}/jquery/jquery-1.11.3.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${resourceRoot}/js/iss.core.js"></script>
<script src="${resourceRoot}/jsencrypt/jsencrypt.min.js"></script>
</head>
<script type="text/javascript">
	function findPassword(){
		$.fnShowWindow_small("${ctx}/admin/czybm/goFindPwd");
	}
	function loginOut(){
		$.ajax({
			    url: "${ctx}/admin/loginOut",
			    type: 'post',
			    data: {},
			    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			   	dataType: 'json',
			    error: function(){
			        window.location.href="${ctx}";
			    },
			    success: function(obj){
			    	window.location.href="${ctx}";
				}
			});
	}
</script>
<body class="login_body">
<div class="log_con">
	<form id="loginForm">
	<house:token></house:token>
	<table cellpadding="0" cellspacing="0" width="100%">
    	<tr>
    		<td colspan="2" height="20" align="center">
    			<span class="error" id="errorInfoSpan"></span>
    		</td>
    	</tr>
    	<tr>
    		<th height="35" width="60" align="right">用户名：</th>
    		<td>
    			<input type="text" class="txt" id="loginName"  name="loginName" maxlength="20" value="" onblur="changePt()"/>
    		</td>
    	</tr>
        <tr>
        	<th height="35" align="right">密&nbsp;码：</th>
        	<td>
        		<input type="password" class="txt"  id="password" name="password" maxlength="12" value="" />
        	</td>
        </tr>
        <tr>
        	<th height="35" align="right">平&nbsp;台：</th>
        	<td>
        		<%
        			if ("1".equals(itemCode)){%>
        			<house:dict id="czylb" dictCode="ptdm" classStyle="txt" style="width:179px" unShowValue="0,2"></house:dict>
        		<%
        			}else if ("2".equals(itemCode)){%>
        			<house:dict id="czylb" dictCode="ptdm" classStyle="txt" style="width:179px" unShowValue="0,1"></house:dict>
        		<%
        			}else{%>
        			<house:dict id="czylb" dictCode="ptdm" classStyle="txt" style="width:179px" unShowValue="0"></house:dict>
        		<%
        			}
        		%>
        	</td>
        </tr>
    	<tr>
    		<th height="35" align="right">验证码：</th>
    		<td>
    			<input type="text" class="txt_s" id="verifycode" name="verifycode" maxlength="4" />
    			<img id="secimg" src="" align="absmiddle"/>&nbsp;&nbsp;
    			<a href="#" onclick="createCode()" tabindex="-1">换一张</a>
    		</td>
    	</tr>
    	<tr>
    		<td height="30">&nbsp;</td>
    		<td>
    			<input type="button" value="" id="subBut" class="log_bt" onclick="login();" />
    		</td>
    	</tr>
    </table>
    </form>
    <form id="logonForm" action="${ctx}/admin/main" method="post">
    <input type="hidden" id="loginFlag" name="loginFlag" value="login">
    </form>
</div>
</body>
<script type="text/javascript">
String.prototype.trim = function() {
    return this.replace(/(^\s+)|(\s+$)/g, "");
}

var Verify=0
function sendForm(){
	var s,p,v,c;
	s=document.getElementById("loginName").value.trim();
	p=document.getElementById("password").value.trim();
	v=document.getElementById("verifycode").value.trim();
	c=document.getElementById("czylb").value.trim();
	
	if(Verify == 1 && v==""){
		$("#errorInfoSpan").html("请输入验证码");
		return false;
	}
	if(s==""||p==""){
		$("#errorInfoSpan").html("请输入用户名密码");
		return false;
	}
	if(c==""||c==""){
		$("#errorInfoSpan").html("请选择平台");
		return false;
	}
	return true;
}

function login(){
	if(!sendForm()) return false;
	$("#subBut").attr("disabled","disabled");
	var dates={
		_form_token_uniq_id: $("#_form_token_uniq_id").val(),
		loginName: $("#loginName").val(),
		password: $("#password").val(),
		czylb: $("#czylb").val(),
		verifycode: $("#verifycode").val()
	};

	var rsaEncrypt = new JSEncrypt();
	rsaEncrypt.setPublicKey("${publicKey}");
	dates.password = rsaEncrypt.encrypt(dates.password);

	$.ajax({
		    url: "${ctx}/login",
		    type: 'post',
		    data: dates,
		    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		   	timeout: 20000,
		   	dataType: 'json',
		   	cache: false,
		    error: function(){
		        alert('用户登录网络连接出错~');
		        $("#subBut").removeAttr("disabled");
		        //window.location.reload();
		        loginOut();
		    },
		    success: function(obj){
		    	//重启服务器，令牌失效，重新获取令牌
		    	if(typeof(obj) == 'undefined' || obj == null){
		    		window.location.reload();
		    	}
		    	if(obj.rs){
		    		//window.location.href="${ctx}/admin/main";
		    		$("#logonForm").submit();
		    	}else{
		    		createCode();
		    		$("#errorInfoSpan").html(obj.msg);
		    		$("#subBut").removeAttr("disabled");
		    		$("#_form_token_uniq_id").val(obj.token.token);
		    	}
			}
		});
}
function changePt(){
	var loginName = $.trim($("#loginName").val());
	if (loginName.length==0){
		return;
	}
	var datas = {
		loginName : loginName
	}
	$.ajax({
		url:'${ctx}/changePt',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '切换平台出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		if (obj.msg=='0' || obj.msg=='1'){
	    			if ($("#czylb option[value='1']").html()){
	    				$("#czylb").val("1");
	    			}else{
	    				$("#czylb").val("");
	    			}
	    			return;
	    		}
				if (obj.msg=='2'){
					if ($("#czylb option[value='2']").html()){
	    				$("#czylb").val("2");
	    			}else{
	    				$("#czylb").val("");
	    			}
					return;
	    		}
	    	}
	    }
	 });
}
function createCode(){
	$("#secimg").attr("src","${ctx}/resources-code.jpg?"+new Date().getTime());
}


$(document).ready(function(){
	//防止session过期时，index页面在子页面中打开
	if(self != top){
		window.top.location="${ctx}/admin/";
	}
	
	createCode();
	$("#loginName").keydown(function(event){
		if(event.keyCode == 13){
			$("#password")[0].focus();
		}
	})
	
	$("#password").keydown(function(event){
		if(event.keyCode == 13){
			$("#verifycode")[0].focus();
		}
	})
	
	$("#verifycode").keydown(function(event){
		if(event.keyCode == 13){
			login();
		}
	})
	
	$("#loginName")[0].focus();
});

</script>
