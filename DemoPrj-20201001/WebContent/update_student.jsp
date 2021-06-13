<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./vendor/bootstrap-4.5.2-dist/css/bootstrap.min.css">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<h3 class="my-5">学生注册信息修改</h3>
		<form class="col-md-6" action="<c:url value="/StuMgr"/>" method="post">
			<input type="hidden" name="task" value="updateStu"/>	
			<div class="form-group">
				<label>学生学号：${stu.stuNo}</label>
				<input type="hidden" name="stuno" value="${stu.stuNo}"/>	
			</div>
			<div class="form-group">
				<label>学生姓名：</label> 
				<input type="text" class="form-control" name="stuname" value="${stu.stuName}">
			</div>
			<div class="form-group">
				<label>学生成绩：</label> 
				<input type="text" class="form-control" name="stumark" value="${stu.stuMark}">
			</div>
			<button type="submit" class="btn btn-primary mt-3">修改数据</button>
		</form>
	</div>

</body>
</html>