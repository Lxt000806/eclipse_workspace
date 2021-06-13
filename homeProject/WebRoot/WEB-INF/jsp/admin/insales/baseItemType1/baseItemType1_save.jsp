<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>基装类型1--增加</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			code : {
				validators : {
					notEmpty : {
						message : '基装类型1编号不能为空'
					}
				}
			},
			descr : {
				validators : {
					notEmpty : {
						message : '基装类型1名称不能为空'
					}
				}
			},
		},
		submitButtons : 'input[type="submit"]'
	}); 
});

function save(){
	$("#dataForm").bootstrapValidator("validate");
	if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/baseItemType1/doSave',
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
				art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	});
}
</script>
</head>
	<body>
	 	<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
			    		<button type="submit" class="btn btn-system" id="saveBut" onclick="save()">保存</button>
	     				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<ul class="ul-form">					  
							<li class="form-validate">
								<label>基装类型1编号</label>
								<input id="code" name="code" type="text"/>
							</li>
							<li class="form-validate" >	
								<label>基装类型1名称</label>
								<input type="text" id="descr" name="descr"/>
							</li>
							<li class="form-validate" >	
								<label>显示顺序</label>
								<input type="text" id="dispSeq" name="dispSeq" value="0"/>
							</li>
						</ul>		
					</form>
				</div>
	  		</div>
	  	</div>
	 </body>
</html>


