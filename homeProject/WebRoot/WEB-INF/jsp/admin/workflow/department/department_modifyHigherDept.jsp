<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改上级部门</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
		<div class="panel-body" >
			<div class="btn-group-xs" >
		      	<button type="button" id="saveBtn" class="btn btn-system "  >保存</button>
				<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
             	<house:token></house:token>
             	<input type="hidden" style="width:160px;" id="m_umState" name="m_umState" value="MH"/>
				<ul class="ul-form">
					<div class="validate-group row" >
						<li>
							<label>部门编号</label>
							<input type="text" id="code" name="code" value="${map.code}" readonly="readonly" />	
						</li>
						<li><label>上级部门</label> <input type="text"
								style="width:160px;" id="higherDep" name="higherDep" />
						</li>
					</div>
				</ul>
			</form>
		</div>
	</div>
</div>	
</body>
<script type="text/javascript">
	$(function(){
		var level="${map.path}".length-"${map.path}".replace(new RegExp("\/","gm"),"").length;
		$("#code").openComponent_department({
			showLabel:"${map.desc2}",
			showValue:"${map.code}",
		});
		$("#openComponent_department_code").attr("readonly",true).next("button").attr("disabled",true);
		$("#higherDep").openComponent_department({
			showLabel:"${map.higherdepdescr}",
			showValue:"${map.higherdep}",
			condition:{code:$("#code").val(),higherLevel:level},
			readonly:true,
			disabled:false
		});
		$("#saveBtn").on("click",function(){
			var datas = $("#dataForm").serialize();
			$.ajax({
				url:"${ctx}/admin/department/doSave",
				type: 'post',
				data: datas,
				dataType: 'json',
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    		art.dialog({
							content: obj.msg,
							time: 1000,
							beforeunload: function () {
			    				closeWin();
						    }
						});
			    	}else{
			    		$("#_form_token_uniq_id").val(obj.token.token);
			    		art.dialog({
							content: obj.msg,
							width: 200
						});
			    	}
			    }
			 });
		});
	});
</script>
</html>
