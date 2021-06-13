<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link
	href="<c:url value="/resources/vendor/bootstrap-4.4.1-dist/css/bootstrap.min.css"/>"
	rel="stylesheet">
<script type="text/javascript"
	src="<c:url value="/resources/vendor/jquery/jquery-3.3.1.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/vendor/popper.js/popper.min.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/vendor/bootstrap-4.4.1-dist/js/bootstrap.min.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/common.js"/>"></script>
<title>Insert title here</title>
<script type="text/javascript">
   //$(function(){
	//   alert('its ok!'); 
  // });
   
    var carNo;
    
    function showRemoveDlg(carno,areaname){
    	carNo = carno;
    	$("#msg").text("您确认要删除[ "+areaname+" ]车辆 [ "+carno+" ] 的信息吗?");
    	$('#removeCarModal').modal('show');
    }
    
    function showLogoutDlg(){
    	$("#exitMsg").text("用户[${loginedUser.userName}], 您确认要退出系统吗?");
    	$('#logoutModal').modal('show');
    }
    
    function removeCar(){
    	//location.href='<c:url value="/stuMgr"/>?task=removeStu&stuno='+stuNo;
    	var frm = document.forms['removeCarFrm'];
    	frm.action += "/"+carNo;
    	frm.submit();
    }
    
    function updateCar(carNo){
    	location.href='<c:url value="/cars"/>/'+carNo;
    }
    
    function regCar(){
    	document.forms['carFrm'].submit();
    	$('#regCarModal').modal('hide');
    }
        
    function logout(){
    	location.href='<c:url value="/logout"/>';
    }
    
    //查询第几页
    function doQuery(pageNo){	    
    	
 	   if(pageNo<1 || pageNo>${page.totalPageNum})
        {
           alert('页号超出范围，有效范围：[1-${page.totalPageNum}]!');
           $('#pageNo').select();
           return;
        }
        else
        {
 	         document.forms['carQryFrm'].pageNo.value=''+pageNo;
 	         document.forms['carQryFrm'].submit();
 	    }
 	   
    }      
    
</script>
</head>
<body>
	<form name="removeCarFrm" action="<c:url value="/cars"/>" method="post">
		<input type="hidden" name="_method" value="DELETE" />
	</form>
	<div class="container">
		<h3 class="mt-5 mb-5">
			车辆信息列表&nbsp;&nbsp;| <small class="ml-3">操作员:${loginedUser.userName}</small>
			<button class="btn btn-primary btn-sm ml-2"
				onclick="showLogoutDlg();">退出系统</button>
		</h3>
		<button class="btn btn-primary mb-2" style="float: right"
			data-toggle="modal" data-target="#regCarModal">车辆登记</button>
			
       <form name="carQryFrm" action="<c:url value="/cars"/>" method="get">
	       <div class="row g-3 align-items-center my-2">
	              <input type="hidden" name="pageNo" value="1"/>				  
				  <div class="col-2">
					  <select class="form-control" name="qryAreaName">
					      <option value="">-小区名称-</option>
						  <option value="欣欣小区">欣欣小区</option>
						  <option value="阳光小区">阳光小区</option>
						  <option value="东海豪庭">东海豪庭</option>
						  <option value="世纪金源">世纪金源</option>
						  <option value="阳光凡尔赛宫">阳光凡尔赛宫</option>	  
					  </select>
				  </div>
				  <div class="col-2">
					  <select class="form-control" name="qryCarType">
					      <option value="">-车辆类型-</option>
						  <option value="轿车">轿车</option>
						  <option value="SUV">SUV</option>
						  <option value="工具车">工具车</option>
						  <option value="货车">货车</option>
					  </select>
				  </div>
				  <div class="col-2">
					  <select class="form-control" name="qryCarStatus">
					      <option value="">-车辆状态-</option>
						  <option value="外出">外出</option>
						  <option value="在地库">在地库</option>
						  <option value="在地面停车位">在地面停车位</option>
					  </select>
				  </div>
				  <div class="col-2">
				    <button class="btn btn-primary"> 查 询 </button>
				  </div>
		   </div>
	   </form>
	   
		<div class="row">
			<table class="table">
				<thead>
					<tr>
						<th scope="col">小区名称</th>
						<th scope="col">车牌号码</th>
						<th scope="col">类型</th>
						<th scope="col">品牌</th>
						<th scope="col">特征</th>
						<th scope="col">状态</th>
						<th scope="col">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="car" items="${page.pageContent}">
						<tr>
							<th scope="row">${car.areaName}</th>
							<th scope="row">${car.carNo}</th>
							<th scope="row">${car.carType}</th>
							<th scope="row">${car.carBrand}</th>
							<th scope="row">
							   <c:forEach var="feature" items="${car.carFeature}">
									<c:choose>
										<c:when test="${feature=='运动轮毂'}">运动轮毂&nbsp;</c:when>
										<c:when test="${feature=='高估值豪车'}">高估值豪车&nbsp;</c:when>
										<c:when test="${feature=='豪华天窗'}">豪华天窗&nbsp;</c:when>
										<c:when test="${feature=='漆面镀晶'}">漆面镀晶&nbsp;</c:when>
									</c:choose>
								</c:forEach>
							</th>
							<th scope="row">${car.carStatus}</th>
							<td>
								<button class="btn btn-primary btn-sm"
									onclick="updateCar(${car.carNo})">修改</button>
								<button class="btn btn-danger btn-sm"
									onclick="showRemoveDlg(${car.carNo},'${car.areaName}');">删除</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
  					      
<!-- 页面情况信息 -->
            <div class="col-12 text-right">
		               共${page.totalRecNum}条, 当前显示${page.startIndex+1}-${page.endIndex}条, 第${page.pageNo}/${page.totalPageNum}页
		            |
		           <c:if test="${page.pageNo>1}">
		             <button class="btn btn-sm btn-outline-info" onclick="doQuery(1)">首页</button>&nbsp;
		           </c:if>
		           <c:if test="${page.prePage}">
		             <button class="btn btn-sm btn-outline-info" onclick="doQuery(${page.pageNo-1})">上一页</button>&nbsp;
		           </c:if>
		           <c:if test="${page.nextPage}">
		             <button class="btn btn-sm btn-outline-info" onclick="doQuery(${page.pageNo+1})">下一页</button>&nbsp;
		           </c:if>
		           <c:if test="${page.pageNo!=page.totalPageNum}">
		             <button class="btn btn-sm btn-outline-info" onclick="doQuery(${page.totalPageNum})">末页</button>&nbsp;
		           </c:if>
		           |
		                      到&nbsp;<input type="text" class="text-center" id="pageNo" size=4 style="text-align:right;"/>&nbsp;页
		           <button class="btn btn-sm btn-success" onclick="doQuery(parseInt($('#pageNo').val()));"> 跳 转 </button>	
	            </div>
	            
	         <c:if test="${empty page.pageContent}">
               <div class="alert alert-primary col-6 py-5 text-center mt-5 offset-md-3" role="alert">
					  <h4>没有符合查询条件的车辆信息被找到!</h4>
			   </div>	        
	         </c:if>					      
	   </div>
   </div>   					      

	<!-- 删除车辆提示模态窗口 -->
	<div class="modal fade" tabindex="-1" id="removeCarModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">操作提示</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p id="msg"></p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="removeCar()">确认删除</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 退出系统模态窗口 -->
	<div class="modal fade" tabindex="-1" id="logoutModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">操作提示</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p id="exitMsg"></p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="logout()">确认退出</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 车辆登记模态窗口 -->
	<div class="modal fade" tabindex="-1" id="regCarModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">操作提示</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form name="carFrm" action="<c:url value="/cars"/>" class="col-12"
						method="post">
						<div class="form-group">
							<label>所属小区</label> 
							<select class="form-control" name="areaName">
								<option value="">--请选择--</option>
								<option value="欣欣小区">欣欣小区</option>
								<option value="阳光小区">阳光小区</option>
								<option value="东海豪庭">东海豪庭</option>
						        <option value="世纪金源">世纪金源</option>
						        <option value="阳光凡尔赛宫">阳光凡尔赛宫</option>	 
							</select>
						</div>
						<div class="form-group">
							<label>车牌号</label> <input type="text" class="form-control"
								name="carNo">
						</div>
						<div class="form-group">
							<label>类型</label><br>
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="carType"
									value="轿车"> <label class="form-check-label">轿车</label>
							</div>
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="carType"
									value="SUV"> <label class="form-check-label">SUV</label>
							</div>
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="carType"
									value="工具车"> <label class="form-check-label">工具车</label>
							</div>
							<div class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="carType"
									value="货车"> <label class="form-check-label">货车</label>
							</div>
						</div>
						<div class="form-group">
							<label>品牌</label> <select class="form-control" name="carBrand">
								<option value="">--请选择--</option>
								<option value="宝马">宝马</option>
								<option value="保时捷">保时捷</option>
								<option value="大众">大众</option>
								<option value="奥迪">奥迪</option>
								<option value="GTR">GTR</option>
							</select>
						</div>
						<div class="form-group">
							<label>颜色</label><br> <select class="form-control"
								name="carColor">
								<option value="">--请选择--</option>
								<option value="白色">白色</option>
								<option value="黑色">黑色</option>
								<option value="红色">红色</option>
								<option value="其他">其他</option>
							</select>
						</div>
						<div class="form-group">
							<label>车辆估值</label> <input type="text" class="form-control"
								name="carvalue">
						</div>
						<div class="form-group">
							<label>特征</label><br>
							<div class="form-check form-check-inline">
								<input class="form-check-input" name="carFeature"
									type="checkbox" value="运动轮毂"> <label
									class="form-check-label">运动轮毂</label>
							</div>
							<div class="form-check form-check-inline">
								<input class="form-check-input" name="carFeature"
									type="checkbox" value="高估值豪车" checked> <label
									class="form-check-label">高估值豪车</label>
							</div>
							<div class="form-check form-check-inline">
								<input class="form-check-input" name="carFeature"
									type="checkbox" value="豪华天窗" checked> <label
									class="form-check-label">豪华天窗</label>
							</div>
							<div class="form-check form-check-inline">
								<input class="form-check-input" name="carFeature"
									type="checkbox" value="漆面镀晶"> <label
									class="form-check-label">漆面镀晶</label>
							</div>
						</div>
						<div class="form-group">
							<label>当前状态</label> <select class="form-control" name="carStatus">
								<option value="外出" checked>外出</option>
								<option value="在地库">在地库</option>
								<option value="在地面停车位">在地面停车位</option>
							</select>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="regCar()">车辆登记</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>