<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/resources/vendor/bootstrap-4.4.1-dist/css/bootstrap.min.css"/>" rel="stylesheet">
<script type="text/javascript" src="<c:url value="/resources/js/common.js"/>"></script>
<title>Insert title here</title>
<script type="text/javascript">

    function Cancel(){
    	location.href='<c:url value="/areas"/>';
    }
</script>
</head>
<body>
   <div class="container">
       <h3 class="my-5">小区信息修改</h3>
       <div class="row">
	       <form action="<c:url value="/areas"/>/${area.areaNo}" class="col-md-6" method="post" enctype="multipart/form-data">
			  <div class="form-group">
			    <label>小区编号: ${area.areaNo}</label>
			  </div>
			  <div class="form-group">
			    <label>小区名称</label>
			    <input type="text" class="form-control" name="areaName" value="${area.areaName}">
			  </div>  			  
			  <img id="picImg" class="my-2" src="<c:url value="/areas"/>/${area.areaNo}/photo" width="150px" height="150px"/>
			  <div class="form-group">
			    <label>小区照片</label>
			    <input type="file" class="form-control-file" name="areaPhoto" onchange="previewImage(this)">
			  </div>			  
			  <div class="form-group">
			    <label>小区地址</label>
			    <input type="text" class="form-control" name="areaAddress" value="${area.areaAddress}">
			  </div>
			  <div class="form-group">
			    <label>小区电话</label>
			    <input type="text" class="form-control" name="areaTel" value="${area.areaTel}">
			  </div> 
			  <div class="form-group">
			    <label>车辆总数</label>
			    <input type="text" class="form-control" name="carNum" value="${area.carNum}">
			  </div>  
			  <button type="button" class="btn btn-secondary mt-3" onclick="Cancel()">取消</button>
			  <button type="submit" class="btn btn-primary mt-3">修改数据</button>
			</form>
		</div>
   </div>
</body>
</html>