<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/resources/vendor/bootstrap-4.4.1-dist/css/bootstrap.min.css"/>" rel="stylesheet">
<script type="text/javascript" src="<c:url value="/resources/vendor/jquery/jquery-3.3.1.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/vendor/popper.js/popper.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/vendor/bootstrap-4.4.1-dist/js/bootstrap.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/common.js"/>"></script>
<title>Insert title here</title>
<script type="text/javascript">
   //$(function(){
	//   alert('its ok!'); 
  // });
   
    var stuNo;
    
    function showRemoveDlg(stuno,stuname){
    	stuNo = stuno;
    	$("#msg").text("您确认要删除学生 [ "+stuname+" ] 的信息吗?");
    	$('#removeStuModal').modal('show');
    }
    
    function showLogoutDlg(){
    	$("#exitMsg").text("用户[${loginedUser.userName}], 您确认要退出系统吗?");
    	$('#logoutModal').modal('show');
    }
    
    function removeStu(){
    	//location.href='<c:url value="/stuMgr"/>?task=removeStu&stuno='+stuNo;
    	var frm = document.forms['removeStuFrm'];
    	frm.action += "/"+stuNo;
    	frm.submit();
    }
    
    function updateStu(stuNo){
    	location.href='<c:url value="/students"/>/'+stuNo;
    }
    
    function regStu(){
    	document.forms['stuFrm'].submit();
    	$('#regStuModal').modal('hide');
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
 	         document.forms['stuQryFrm'].pageNo.value=''+pageNo;
 	         document.forms['stuQryFrm'].submit();
 	    }
 	   
    }      
    
</script>
</head>
<body>
   <form name="removeStuFrm" action="<c:url value="/students"/>" method="post">
     <input type="hidden" name="_method" value="DELETE"/>
   </form>
   <div class="container">
       <h3 class="mt-5 mb-5">学生信息列表&nbsp;&nbsp;|
        <small class="ml-3">操作员:${loginedUser.userName}</small>
        <button class="btn btn-primary btn-sm ml-2" onclick="showLogoutDlg();">退出系统</button>
       </h3>
       <button class="btn btn-primary mb-2" 
               style="float:right" data-toggle="modal" data-target="#regStuModal">新生登记</button>
               
       <form name="stuQryFrm" action="<c:url value="/students"/>" method="get">
	       <div class="row g-3 align-items-center my-2">
	              <input type="hidden" name="pageNo" value="1"/>
				  <div class="col-auto">
				    <label class="col-form-label">姓名:</label>
				  </div>
				  <div class="col-2">
				    <input type="text" 
				           name="qryStuName" 
				           class="form-control" placeholder="支持模糊查询" value="${helper.qryStuName}">
				  </div>
				  <div class="col-auto">
				    <label class="col-form-label">成绩范围:</label>
				  </div>
				  <div class="col-1">
				    <input type="text" name="qryBeginMark" class="form-control" value="${helper.qryBeginMark}">
				  </div>
				  -
				  <div class="col-1">
				    <input type="text" name="qryEndMark" class="form-control" value="${helper.qryEndMark}">
				  </div>
				  <div class="col-2">
					  <select class="form-control" name="qryStuSex">
					      <option value="">-选择性别-</option>
						  <option value="M" <c:if test="${helper.qryStuSex=='M'}">selected</c:if>>男</option>
						  <option value="F" <c:if test="${helper.qryStuSex=='F'}">selected</c:if>>女</option>						  
					  </select>
				  </div>				  
				  <div class="col-2">
					  <select class="form-control" name="qryStuOrigin">
					      <option value="">-选择籍贯-</option>
						  <option value="FZ" <c:if test="${helper.qryStuOrigin=='FZ'}">selected</c:if>>福州</option>
						  <option value="XM" <c:if test="${helper.qryStuOrigin=='XM'}">selected</c:if>>厦门</option>
						  <option value="QZ" <c:if test="${helper.qryStuOrigin=='QZ'}">selected</c:if>>泉州</option>
						  <option value="NP" <c:if test="${helper.qryStuOrigin=='NP'}">selected</c:if>>南平</option>
					  </select>
				  </div>
				  <div class="col-2">
				    <button class="btn btn-primary"> 查 询 </button>
				  </div>
		   </div>
	   </form>
       <div class="row">
            <c:if test="${not empty page.pageContent}">
			<table class="table">
			  <thead>
			    <tr>
			      <th scope="col">学号</th>
			      <th scope="col">姓名</th>
			      <th scope="col">性别</th>
			      <th scope="col">籍贯</th>
			      <th scope="col">爱好</th>
			      <th scope="col">成绩</th>
			      <th scope="col">操作</th>
			    </tr>
			  </thead>
			  <tbody>
			    <c:forEach var="stu" items="${page.pageContent}">
			    <tr>
			      <th scope="row">${stu.stuNo}</th>
			      <td>${stu.stuName}<br>
			       <img id="picImg" src="<c:url value="/students"/>/${stu.stuNo}/photo" width="80px" height="80px"/>
			      </td>
			      <th scope="row">
			        <c:if test="${stu.stuSex=='M'}">男</c:if>
			        <c:if test="${stu.stuSex=='F'}">女</c:if>
			      </th>
			      <th scope="row">
			        <c:choose>
			          <c:when test="${stu.stuOrigin=='FZ'}">福州</c:when>
			          <c:when test="${stu.stuOrigin=='XM'}">厦门</c:when>
			          <c:when test="${stu.stuOrigin=='QZ'}">泉州</c:when>
			          <c:when test="${stu.stuOrigin=='NP'}">南平</c:when>
			          <c:otherwise>---</c:otherwise>
			        </c:choose>	      
			      </th>
			      <th scope="row">
			        <c:forEach var="hobby" items="${stu.stuHobby}">
			           <c:choose>
			              <c:when test="${hobby=='RN'}">跑步&nbsp;</c:when>
			              <c:when test="${hobby=='RD'}">阅读&nbsp;</c:when>
			              <c:when test="${hobby=='CM'}">爬山&nbsp;</c:when>
			              <c:when test="${hobby=='SW'}">游泳&nbsp;</c:when>
			           </c:choose>
			        </c:forEach>
			      </th>
			      <td>${stu.stuMark}</td>
			      <td>
			        <button class="btn btn-primary btn-sm" onclick="updateStu(${stu.stuNo})">修改</button>
			        <button class="btn btn-danger btn-sm" onclick="showRemoveDlg(${stu.stuNo},'${stu.stuName}');">删除</button>
			      </td>
			    </tr>
			    </c:forEach>
			  </tbody>
			</table>
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
	        </c:if>
	        <c:if test="${empty page.pageContent}">
               <div class="alert alert-primary col-6 py-5 text-center mt-5 offset-md-3" role="alert">
					  <h4>没有符合查询条件的学生信息被找到!</h4>
			   </div>	        
	        </c:if>    					      
	   </div>
   </div>
   <!-- 删除学生提示模态窗口 -->
   <div class="modal fade" tabindex="-1" id="removeStuModal" >
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">操作提示</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <p id="msg"></p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
	        <button type="button" class="btn btn-primary" onclick="removeStu()">确认删除</button>
	      </div>
	    </div>
	  </div>
   </div>

   <!-- 退出系统模态窗口 -->
   <div class="modal fade" tabindex="-1" id="logoutModal" >
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">操作提示</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <p id="exitMsg"></p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
	        <button type="button" class="btn btn-primary" onclick="logout()">确认退出</button>
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
	        <form name="stuFrm" action="<c:url value="/students"/>" class="col-12" method="post" enctype="multipart/form-data">
			  <div class="form-group">
			    <label>学生学号</label>
			    <input type="text" class="form-control" name="stuNo">
			  </div>
			  <div class="form-group">
			    <label>学生姓名</label>
			    <input type="text" class="form-control" name="stuName">
			  </div>
			  <div class="form-group">
			    <label>学生性别</label><br>
			    <div class="form-check form-check-inline">
				  <input class="form-check-input" type="radio" name="stuSex"value="M">
				  <label class="form-check-label">男</label>
				</div>
				<div class="form-check form-check-inline">
				  <input class="form-check-input" type="radio" name="stuSex"value="F" checked>
				  <label class="form-check-label">女</label>
				</div>
			  </div>
			  <div class="form-group">
			    <label>学生爱好</label><br>
			    <div class="form-check form-check-inline">
				  <input class="form-check-input" name="stuHobby" type="checkbox" value="RD" checked>
				  <label class="form-check-label">阅读</label>
				</div>
			    <div class="form-check form-check-inline">
				  <input class="form-check-input" name="stuHobby" type="checkbox" value="RN">
				  <label class="form-check-label">跑步</label>
				</div>
			    <div class="form-check form-check-inline">
				  <input class="form-check-input" name="stuHobby" type="checkbox" value="CM">
				  <label class="form-check-label">爬山</label>
				</div>
			    <div class="form-check form-check-inline">
				  <input class="form-check-input" name="stuHobby" type="checkbox" value="SW" checked>
				  <label class="form-check-label">游泳</label>
				</div>								
			  </div>
			  <div class="form-group">
			    <label>学生籍贯</label><br>
			    <select class="form-control" name="stuOrigin">
			      <option value="">--请选择--</option>
				  <option value="FZ">福州</option>
				  <option value="XM">厦门</option>
				  <option value="QZ">泉州</option>
				  <option value="NP">南平</option>
				</select>
		      </div>			  
			  <img id="picImg" src="<c:url value="/resources/pics/default.png"/>" width="150px" height="150px"/>
			  <div class="form-group">
			    <label>学生照片</label>
			    <input type="file" class="form-control-file" name="stuPhoto" onchange="previewImage(this)">
			  </div>
			  <div class="form-group">
			    <label>学生成绩</label>
			    <input type="text" class="form-control" name="stuMark">
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