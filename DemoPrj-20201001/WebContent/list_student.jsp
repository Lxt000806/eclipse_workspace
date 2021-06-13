<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./vendor/bootstrap-4.5.2-dist/css/bootstrap.min.css">
<script type="text/javascript" src="<c:url value="./vendor/bootstrap-4.5.2-dist/js/jquery.js"/>"></script>
<script type="text/javascript" src="<c:url value="./vendor/popper.js/popper.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="./vendor/bootstrap-4.5.2-dist/js/bootstrap.min.js"/>"></script>
<title>Insert title here</title>
<script type="text/javascript">
    var stuNo;

    //显示要删除学生的信息
    function showRemoveDlg(stuno,stuname){
	     stuNo = stuno;
	     $("#msg").text("您确认要删除学生["+stuname+"]的信息吗？");
	     $('#removeStuModal').modal('show');
    }

    function removeStu(){
	    location.href='<c:url value="/StuMgr"/>?task=removeStu&stuno='+stuNo;
    }
    
    //修改学生信息
    function updateStu(stuNo){
    	//$('#updateStuModal').modal('show');
    	location.href='<c:url value="/StuMgr"/>?task=preUpdate&stuno='+stuNo;
    }
    
    function regStu(){
    	document.forms['stuFrm'].submit();
    	$('#regStuModal').modal('hide');
    	
    }
</script>
</head>
<body>
	<div class="container">
		<h3 class="my-5 mb-3">学生信息列表</h3>
		<div class="text-right">
		<button class="btn btn-primary mb-2"  data-toggle="modal" data-target="#regStuModal">新生登记</button>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th scope="col">学号</th>
					<th scope="col">姓名</th>
					<th scope="col">成绩</th>
					<th scope="col">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="stu" items="${stuList}">
					<tr>
						<th scope="row">${stu.stuNo}</th>
						<td>${stu.stuName}</td>
						<td>${stu.stuMark}</td>
						<td>
							<button class="btn btn-primary btn-sm" onclick="updateStu(${stu.stuNo})">修改</button>
							<button class="btn btn-danger btn-sm"
								onclick="showRemoveDlg(${stu.stuNo},'${stu.stuName}');">删除</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<!-- 删除学生信息模态提示窗口 -->
	<div class="modal" id="removeStuModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
				    <h4 class="modal-title">操作提示</h4>
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true" aria-label="Close">&times;</button>				
				</div>
				<div class="modal-body">
					<p id="msg"></p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="removeStu()">确认删除</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改学生信息模态窗口 -->
   <div class="modal fade" tabindex="-1" id="updateStuModal" >
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">操作提示</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <form name="" action="<c:url value="/StuMgr"/>" class="col-12" method="post">
			  <input type="hidden" name="task" value="updateStu"/>
			  <div class="form-group">
			    <label>学生学号: ${stu.stuNo}</label>
			    <input type="hidden" class="form-control" name="stuno" value="${stu.stuNo}"/>
			  </div>
			  <div class="form-group">
			    <label>学生姓名</label>
			    <input type="text" class="form-control" name="stuname" value="${stu.stuName}">
			  </div>
			  <div class="form-group">
			    <label>学生成绩</label>
			    <input type="text" class="form-control" name="stumark" value="${stu.stuMark}">
			  </div>
			</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
	        <button type="submit" class="btn btn-primary">修改数据</button>
	      </div>
	    </div>
	  </div>
   </div>
	
	
	<!-- 新生注册注册模态窗口 -->
   <div class="modal fade" tabindex="-1" id="regStuModal" >
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">操作提示</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <form name="stuFrm" action="<c:url value="/StuMgr"/>" class="col-12" method="post">
			  <input type="hidden" name="task" value="createStu"/>
			  <div class="form-group">
			    <label>学生学号</label>
			    <input type="text" class="form-control" name="stuno">
			  </div>
			  <div class="form-group">
			    <label>学生姓名</label>
			    <input type="text" class="form-control" name="stuname">
			  </div>
			  <div class="form-group">
			    <label>学生成绩</label>
			    <input type="text" class="form-control" name="stumark">
			  </div>
			</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
	        <button type="button" class="btn btn-primary" onclick="regStu()">新生注册</button>
	      </div>
	    </div>
	  </div>
   </div>
</body>
</html>