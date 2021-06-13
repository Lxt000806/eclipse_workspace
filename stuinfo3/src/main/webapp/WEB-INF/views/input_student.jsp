<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/resources/vendor/bootstrap-4.4.1-dist/css/bootstrap.min.css"/>" rel="stylesheet">
<title>Insert title here</title>
</head>
<body>
   <div class="container">
       <h3 class="my-5">学生注册登记</h3>
       <div class="row">
	       <form action="<c:url value="/students"/>" class="col-md-6" method="post">
			  <div class="form-group">
			    <label>学生学号</label>
			    <input type="text" class="form-control" name="stuNo">
			  </div>
			  <div class="form-group">
			    <label>学生姓名</label>
			    <input type="text" class="form-control" name="stuName">
			  </div>
			  <div class="form-group">
			    <label>学生成绩</label>
			    <input type="text" class="form-control" name="stuMark">
			  </div> 
			  <button type="submit" class="btn btn-primary mt-3">确认注册</button>
			</form>
		</div>
   </div>
</body>
</html>