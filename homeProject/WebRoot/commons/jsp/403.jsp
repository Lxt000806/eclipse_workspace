<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<title>403 - 缺少权限</title>

<script type="text/javascript">

function loginOut(){
	$.ajax({
		    url: "${ctx}/loginOut",
		    type: 'post',
		    data: null,
		    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		   	dataType: 'json',
		    error: function(){
		        alert('用户退出系统出错~');
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		window.top.location.href="${ctx}";
		    	}else{
		    		 alert('用户退出系统出错~');
		    	}
			}
		});
}

</script>
</head>

<body>
<div>
	<div><h1>你没有访问该页面的权限.</h1></div>
	<div><a href="${ctx }">返回首页</a> <a href= "#" onclick="loginOut(); return false;"/>退出登录</a></div>
</div>
</body>
</html>
