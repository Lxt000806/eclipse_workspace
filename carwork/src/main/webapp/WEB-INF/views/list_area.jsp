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
   
    var areaNo;
    
    function showRemoveDlg(areano,areaname){
    	areaNo = areano;
    	$("#msg").text("您确认要删除小区 [ "+areaname+" ] 的信息吗?");
    	$('#removeAreaModal').modal('show');
    }
    
    function showLogoutDlg(){
    	$("#exitMsg").text("用户[${loginedUser.userName}], 您确认要退出系统吗?");
    	$('#logoutModal').modal('show');
    }
    
    function removeArea(){
    	//location.href='<c:url value="/stuMgr"/>?task=removeStu&stuno='+stuNo;
    	var frm = document.forms['removeAreaFrm'];
    	frm.action += "/"+areaNo;
    	frm.submit();
    }
    
    function updateArea(areaNo){
    	location.href='<c:url value="/areas"/>/'+areaNo;
    }
    
    function regArea(){
    	document.forms['areaFrm'].submit();
    	$('#regAreaModal').modal('hide');
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
 	         document.forms['areaQryFrm'].pageNo.value=''+pageNo;
 	         document.forms['areaQryFrm'].submit();
 	    }
 	   
    }      
           
    
</script>
</head>
<body>
	<form name="removeAreaFrm" action="<c:url value="/areas"/>" method="post">
		<input type="hidden" name="_method" value="DELETE" />
	</form>
	<div class="container">
	   <nav class="navbar navbar-expand-lg navbar-light bg-light mt-3">
		  <div class="container-fluid">
		    <a class="navbar-brand" href="#">菜单栏：</a>
		    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
		      <span class="navbar-toggler-icon"></span>
		    </button>
		    <div class="collapse navbar-collapse" id="navbarNavDropdown">
		      <ul class="navbar-nav">
		        <li class="nav-item">
		          <a class="nav-link " aria-current="page" href="<c:url value="/cars"/>">车辆管理</a>
		        </li>
		        <li class="nav-item">
		          <a class="nav-link" href="<c:url value="/areas"/>">小区管理</a>
		        </li>
		      </ul>
		    </div>
		  </div>
		</nav>
		<h3 class="mt-5 mb-5">
			小区信息列表&nbsp;&nbsp;| <small class="ml-3">操作员:${loginedUser.userName}</small>
			<button class="btn btn-primary btn-sm ml-2"
				onclick="showLogoutDlg();">退出系统</button>
		</h3>
		
		<button class="btn btn-primary mb-2" style="float: right"
			data-toggle="modal" data-target="#regAreaModal">小区登记</button>
			
        <form name="areaQryFrm" action="<c:url value="/areas"/>" method="get">
	       <div class="row g-3 align-items-center my-2">
	              <input type="hidden" name="pageNo" value="1"/>
				  <div class="col-auto">
				    <label class="col-form-label">小区名称:</label>
				  </div>
				  <div class="col-2">
				    <input type="text" name="qryAreaName" 
				           class="form-control" placeholder="支持模糊查询" value="${helper.qryAreaName}">
				  </div>
				  <div class="col-auto">
				    <label class="col-form-label">小区地址:</label>
				  </div>
				  <div class="col-2">
					  <select class="form-control" name="qryAreaAddress">
					      <option value="">-所属区域-</option>
						  <option value="福州市">福州市</option>
						  <option value="厦门市">厦门市</option>
						  <option value="泉州市">泉州市</option>
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
					    <th scope="col">小区编号</th>
						<th scope="col">小区名称</th>
						<th scope="col">小区地址</th>
						<th scope="col">小区电话</th>
						<th scope="col">车辆总数</th>
						<th scope="col">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="area" items="${page.pageContent}">
						<tr>
						    <th scope="row">${area.areaNo}</th>
							<th scope="row">${area.areaName}</th>
							<th scope="row">${area.areaAddress}</th>
							<th scope="row">${area.areaTel}</th>
							<th scope="row">${area.carNum}</th>
							<td>
								<button class="btn btn-primary btn-sm"
									onclick="updateArea(${area.areaNo})">修改</button>
								<button class="btn btn-danger btn-sm"
									onclick="showRemoveDlg(${area.areaNo},'${area.areaName}');">删除</button>
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
					  <h4>没有符合查询条件的小区信息被找到!</h4>
			   </div>	        
	         </c:if> 					      
	   </div>
   </div>   	 					      

	<!-- 删除车辆提示模态窗口 -->
	<div class="modal fade" tabindex="-1" id="removeAreaModal">
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
					<button type="button" class="btn btn-primary" onclick="removeArea()">确认删除</button>
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

	<!-- 小区登记模态窗口 -->
	<div class="modal fade" tabindex="-1" id="regAreaModal">
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
					<form name="areaFrm" action="<c:url value="/areas"/>" class="col-12" method="post" enctype="multipart/form-data">
						<div class="form-group">
							<label>小区编号</label> 
							<input type="text" class="form-control" name="areaNo">
						</div>
						<div class="form-group">
							<label>小区名称</label> 
							<input type="text" class="form-control" name="areaName">
						</div>
						<img id="picImg" src="<c:url value="./resources/pics/default.png"/>" width="150px" height="150px"/>
			            <div class="form-group">
			              <label>小区照片</label>
			              <input type="file" class="form-control-file" name="areaPhoto" onchange="previewImage(this)">
			            </div>
						<div class="form-group">
							<label>小区地址</label> 
							<input type="text" class="form-control" name="areaAddress">
						</div>
						<div class="form-group">
							<label>小区电话</label> 
							<input type="text" class="form-control" name="areaTel">
						</div>
						<div class="form-group">
							<label>车辆总数</label> 
							<input type="text" class="form-control" name="carNum">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="regArea()">小区登记</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>