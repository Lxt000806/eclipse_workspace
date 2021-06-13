<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="./vendor/bootstrap-4.5.2-dist/css/bootstrap.css">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<h3 class="my-5">新生登记注册</h3>
		<form class="col-md-6" action="StuMgr" method="post">
			<input type="hidden" name="task" value="createStu"/>
			<div class="form-group">
				<label>学生学号：</label> <input type="text" class="form-control"
					name="stuno">
			</div>
			<div class="form-group">
				<label>学生姓名：</label> <input type="text" class="form-control"
					name="stuname">
			</div>
			<div class="form-group">
				<label>学生成绩：</label> <input type="text" class="form-control"
					name="stumark">
			</div>
			<button type="submit" class="btn btn-primary mt-3">确认注册</button>
		</form>
	</div>

</body>
</html>