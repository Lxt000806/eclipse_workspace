<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>员工证书--修改</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){
	$("#certification").val("${map.certification[0]}");
});
function saveBtn(){
	var selectRows = [];
	var datas=$("#dataForm").jsonForm();
	if(""!=$.trim($("#certification").val())){
		datas.certification=$("#certification").val();
	}
 	selectRows.push(datas);
	Global.Dialog.returnData = selectRows;
	closeWin();
};
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
		    	<div class="panel-body" >
		    		<div class="btn-group-xs" >
						<button type="button" class="btn btn-system" onclick="saveBtn()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" style="margin-bottom: 10px;margin-right:10px; height:200px;position: relative;">  
				<div style="float: left; width: 30px; margin-top: 10px;margin-left: 5px;" >
					<h5>证书</h5>
				</div>
				<div class="panel-body" style="float: right; width: 570px;">
					<form action="" method="post" class="form-search" id="dataForm">	
						<textarea id="certification" name="certification" rows="10" cols="6">${map.certification[0]}</textarea>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
