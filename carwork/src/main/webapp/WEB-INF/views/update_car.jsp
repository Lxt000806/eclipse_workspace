<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/resources/vendor/bootstrap-4.4.1-dist/css/bootstrap.min.css"/>" rel="stylesheet">
<title>Insert title here</title>
<script type="text/javascript">

    function Cancel(){
    	location.href='<c:url value="/cars"/>';
    }
</script>
</head>
<body>
   <div class="container">
       <h3 class="my-5">车辆信息修改</h3>
       <div class="row">
	       <form action="<c:url value="/cars/${car.carNo}"/>" class="col-md-6" method="post">
	          <input type="hidden" name="_method" value="PUT"/>
			  <input type="hidden" name="carNo" value="${car.carNo}" />
			  <div class="form-group">
			    <label>车牌号: ${car.carNo}</label>
			  </div>
			  <div class="form-group">
				<label>所属小区</label> 
				<select class="form-control" name="areaName">
			        <option value="">--请选择--</option>
				    <option value="欣欣小区" <c:if test="${car.areaName=='欣欣小区'}">selected</c:if>>欣欣小区</option>
				    <option value="阳光小区" <c:if test="${car.areaName=='阳光小区'}">selected</c:if>>阳光小区</option>
				    <option value="东海豪庭" <c:if test="${car.areaName=='东海豪庭'}">selected</c:if>>东海豪庭</option>
					<option value="世纪金源" <c:if test="${car.areaName=='世纪金源'}">selected</c:if>>世纪金源</option>
					<option value="阳光凡尔赛宫" <c:if test="${car.areaName=='阳光凡尔赛宫'}">selected</c:if>>阳光凡尔赛宫</option>	 
				</select>
			  </div>
			  <div class="form-group">
				<label>类型</label><br>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="carType" value="轿车" <c:if test="${car.carType=='轿车'}">checked</c:if>> 
					<label class="form-check-label">轿车</label>
				</div>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="carType" value="SUV" <c:if test="${car.carType=='SUV'}">checked</c:if>> 
					<label class="form-check-label">SUV</label>
				</div>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="carType" value="工具车" <c:if test="${car.carType=='工具车'}">checked</c:if>> 
					<label class="form-check-label">工具车</label>
				</div>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="carType" value="货车" <c:if test="${car.carType=='货车'}">checked</c:if>> 
					<label class="form-check-label">货车</label>
				</div>
			  </div>
				<div class="form-group">
					<label>品牌</label> <select class="form-control" name="carBrand">
						<option value="">--请选择--</option>
						<option value="宝马" <c:if test="${car.carBrand=='宝马'}">selected</c:if>>宝马</option>
						<option value="保时捷" <c:if test="${car.carBrand=='保时捷'}">selected</c:if>>保时捷</option>
						<option value="大众" <c:if test="${car.carBrand=='大众'}">selected</c:if>>大众</option>
						<option value="奥迪" <c:if test="${car.carBrand=='奥迪'}">selected</c:if>>奥迪</option>
						<option value="GTR" <c:if test="${car.carBrand=='GTR'}">selected</c:if>>GTR</option>
					</select>
				</div>
				<div class="form-group">
					<label>颜色</label><br> <select class="form-control" name="carColor">
						<option value="">--请选择--</option>
						<option value="白色" <c:if test="${car.carColor=='白色'}">selected</c:if>>白色</option>
						<option value="黑色" <c:if test="${car.carColor=='黑色'}">selected</c:if>>黑色</option>
						<option value="红色" <c:if test="${car.carColor=='红色'}">selected</c:if>>红色</option>
						<option value="其他" <c:if test="${car.carColor=='其他'}">selected</c:if>>其他</option>
					</select>
				</div>
				<div class="form-group">
					<label>车辆估值</label> 
					<input type="text" class="form-control" name="carValue" value="${car.carValue}">
				</div>
				<div class="form-group">
					<label>特征</label><br>
					<div class="form-check form-check-inline">
						<input class="form-check-input" name="carFeature" type="checkbox" value="运动轮毂"
						<c:forEach var="feature" items="${car.carFeature}">
				          <c:if test="${feature=='运动轮毂'}"> checked</c:if>
				        </c:forEach>> 
						<label class="form-check-label">运动轮毂</label>
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" name="carFeature" type="checkbox" value="高估值豪车" checked
						<c:forEach var="feature" items="${car.carFeature}">
				          <c:if test="${feature=='高估值豪车'}"> checked</c:if>
				        </c:forEach>> 
						<label class="form-check-label">高估值豪车</label>
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" name="carFeature" type="checkbox" value="豪华天窗" checked
						<c:forEach var="feature" items="${car.carFeature}">
				          <c:if test="${feature=='豪华天窗'}"> checked</c:if>
				        </c:forEach>> 
						<label class="form-check-label">豪华天窗</label>
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" name="carFeature" type="checkbox" value="漆面镀晶"
						<c:forEach var="feature" items="${car.carFeature}">
				          <c:if test="${feature=='漆面镀晶'}"> checked</c:if>
				        </c:forEach>> 
						<label class="form-check-label">漆面镀晶</label>
					</div>
				</div>
				<div class="form-group">
					<label>当前状态</label> <select class="form-control" name="carStatus">
						<option value="外出" <c:if test="${car.carStatus=='外出'}">selected</c:if>>外出</option>
						<option value="在地库" <c:if test="${car.carStatus=='在地库'}">selected</c:if>>在地库</option>
						<option value="在地面停车位" <c:if test="${car.carStatus=='在地面停车位'}">selected</c:if>>在地面停车位</option>
					</select>
				</div>
			  <button type="button" class="btn btn-secondary mt-3" onclick="Cancel()">取消</button>
			  <button type="submit" class="btn btn-primary mt-3">修改数据</button>
			</form>
		</div>
   </div>
</body>
</html>