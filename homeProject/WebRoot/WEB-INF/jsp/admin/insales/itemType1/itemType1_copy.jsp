<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加ItemType1</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function save(){
	if(!$("#dataForm").valid()) {return false;}//表单校验
	//验证
	var datas = $("#dataForm").serialize();
	var dispSeq= $("#dispSeq").val();
	if (Math.round(dispSeq)!=dispSeq) {
   		art.dialog({
			content: "显示顺序数据有错,请重新输入",
		});
		return;
	}
	$.ajax({
		url:'${ctx}/admin/itemType1/doSave',
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

//校验函数
$(function() {
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			code : {
				validators : {
					notEmpty : {
						message : '编码不能为空'
					}
				}
			},
			descr : {
				validators : {
					notEmpty : {
						message : '名称不能为空',
					}
				}
			},
			profitPer : {
				validators : {
					numeric: {
	                    message:'只能输入数字'
	                }
				}
			},
			dispSeq : {
				validators : {
					numeric: {
	                    message:'只能输入数字'
	                }
				}
			},
		},
		submitButtons : 'input[type="submit"]'
	}); 
	$("#whCode").openComponent_wareHouse({
		showValue:"${itemType1.whCode}",
		showLabel:"${itemType1.whDescr}",
	});
});
</script>
</head>   
<body>
<div class="body-box-form" >
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
				<input type="text" id="expired" name="expired" value="${itemType1.expired}" hidden="true"/>
				<input type="text" id="actionLog" name="actionLog" value="${itemType1.actionLog}" hidden="true" />
				<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" value="${itemType1.lastUpdatedBy}" hidden="true"/>		
				<ul class="ul-form">		  
					<li class="form-validate">
						<label>编码</label>
						<input type="text" id="code" name="code" value="${itemType1.code}" />
					</li>
					<li class="form-validate">	
						<label>名称</label>
						<input type="text" id="descr" name="descr" value="${itemType1.descr}" />
					</li>
					<li class="form-validate">	
						<label>营销费用点数</label>
						<input type="text" id="profitPer" name="profitPer" value="${itemType1.profitPer}" />
					</li>
					<li class="form-validate">	
						<label>是否解单领料</label>
						<house:xtdm id="isSpecReq" dictCode="YESNO" value="${itemType1.isSpecReq}"></house:xtdm>
					</li>
					<li class="form-validate">	
						<label>显示顺序</label>
						<input type="text" id="dispSeq" name="dispSeq" value="${itemType1.dispSeq}" />
					</li>
					<li class="form-validate">
						<label >发货公司仓</label>
						<input type="text" id="whCode" name="whCode"   value="${itemType1.whCode}"/>    
					</li>
				</ul>		
			</form>
		</div>
  	</div>
</div>
</body>
</html>
