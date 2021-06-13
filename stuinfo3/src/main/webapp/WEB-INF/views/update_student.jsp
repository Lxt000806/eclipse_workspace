<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/resources/vendor/bootstrap-4.4.1-dist/css/bootstrap.min.css"/>" rel="stylesheet">
<script type="text/javascript" src="<c:url value="/resources/js/common.js"/>"></script>
<title>Insert title here</title>
</head>
<body>
   <div class="container">
       <h3 class="my-5">学生注册信息修改</h3>
       <div class="row">
	       <form action="<c:url value="/students"/>/${stu.stuNo}" class="col-md-6" method="post" enctype="multipart/form-data">
			  <div class="form-group">
			    <label>学生学号: ${stu.stuNo}</label>
			  </div>
			  <div class="form-group">
			    <label>学生姓名</label>
			    <input type="text" class="form-control" name="stuName" value="${stu.stuName}">
			  </div>
              <div class="form-group">
			    <label>学生性别</label><br>
			    <div class="form-check form-check-inline">
				  <input class="form-check-input" type="radio" name="stuSex"value="M"
				   <c:if test="${stu.stuSex=='M'}"> checked</c:if>>
				  <label class="form-check-label">男</label>
				</div>
				<div class="form-check form-check-inline">
				  <input class="form-check-input" type="radio" name="stuSex"value="F" <c:if test="${stu.stuSex=='F'}"> checked</c:if>>
				  <label class="form-check-label">女</label>
				</div>
			  </div>
              <div class="form-group">
			    <label>学生爱好</label><br>
			    <div class="form-check form-check-inline">
				  <input class="form-check-input" name="stuHobby" type="checkbox" value="RD"
				     <c:forEach var="hobby" items="${stu.stuHobby}">
				       <c:if test="${hobby=='RD'}"> checked</c:if>
				     </c:forEach>
				  >
				  <label class="form-check-label">阅读</label>
				</div>
			    <div class="form-check form-check-inline">
				  <input class="form-check-input" name="stuHobby" type="checkbox" value="RN"
				     <c:forEach var="hobby" items="${stu.stuHobby}">
				       <c:if test="${hobby=='RN'}"> checked</c:if>
				     </c:forEach>				  
				  >
				  <label class="form-check-label">跑步</label>
				</div>
			    <div class="form-check form-check-inline">
				  <input class="form-check-input" name="stuHobby" type="checkbox" value="CM"
				     <c:forEach var="hobby" items="${stu.stuHobby}">
				       <c:if test="${hobby=='CM'}"> checked</c:if>
				     </c:forEach>				  
				  >
				  <label class="form-check-label">爬山</label>
				</div>
			    <div class="form-check form-check-inline">
				  <input class="form-check-input" name="stuHobby" type="checkbox" value="SW"
				     <c:forEach var="hobby" items="${stu.stuHobby}">
				       <c:if test="${hobby=='SW'}"> checked</c:if>
				     </c:forEach>				  
				  >
				  <label class="form-check-label">游泳</label>
				</div>								
			  </div>
			  <div class="form-group">
			    <label>学生籍贯</label><br>
			    <select class="form-control" name="stuOrigin">
			      <option value="">--请选择--</option>
				  <option value="FZ" <c:if test="${stu.stuOrigin=='FZ'}">selected</c:if>>福州</option>
				  <option value="XM" <c:if test="${stu.stuOrigin=='XM'}">selected</c:if>>厦门</option>
				  <option value="QZ" <c:if test="${stu.stuOrigin=='QZ'}">selected</c:if>>泉州</option>
				  <option value="NP" <c:if test="${stu.stuOrigin=='NP'}">selected</c:if>>南平</option>
				</select>
		      </div>			  			  			  
			  <img id="picImg" class="my-2" src="<c:url value="/students"/>/${stu.stuNo}/photo" width="150px" height="150px"/>
			  <div class="form-group">
			    <label>学生照片</label>
			    <input type="file" class="form-control-file" name="stuPhoto" onchange="previewImage(this)">
			  </div>			  
			  <div class="form-group">
			    <label>学生成绩</label>
			    <input type="text" class="form-control" name="stuMark" value="${stu.stuMark}">
			  </div> 
			  <button type="submit" class="btn btn-primary mt-3">修改数据</button>
			</form>
		</div>
   </div>
</body>
</html>