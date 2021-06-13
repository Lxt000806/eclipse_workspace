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
<script type="text/javascript">
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2",null,true,true);
});
function save(){
	$("#dataForm").bootstrapValidator("validate");
	if(!$("#dataForm").data("bootstrapValidator").isValid()) return;//表单校验
	//验证
	var dispSeq= $("#dispSeq").val();
	if (Math.round(dispSeq)!=dispSeq) {
   		art.dialog({
			content: "显示顺序数据有错,请重新输入",
		});
		return;
	}
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/itemType3/doSave',
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
			descr : {
				validators : {
					notEmpty : {
						message : '名称不能为空',
					}
				}
			},
			intInstallFee : {
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
			}
		},
		submitButtons : 'input[type="submit"]'
	}); 
});
</script>
</head>   
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
    		<div class="btn-group-xs" >
    			<button type="submit" class="btn btn-system" onclick="save()">保存</button>
   				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
				<ul class="ul-form">					  
					<li>
						<label>编码</label>
						<input type="text" id="code" name="code" placeholder="保存自动生成" readonly="readonly" />
					</li>
					<li class="form-validate">
						<label>名称</label>
						<input type="text" id="descr" name="descr" />
					</li>
					<li>
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1"></select>
					</li>
					<li>
						<label>材料类型2</label>
						<select id="itemType2" name="itemType2"></select>
					</li>
					<li class="form-validate">
						<label>安装费用</label>
						<input type="text" id="intInstallFee" name="intInstallFee" value="0"/>
					</li>
					<li class="form-validate">
						<label>显示顺序</label>
						<input type="text" id="dispSeq" name="dispSeq" value="0"/>
					</li>
					<li>
						<label>拆单类型</label>
						<house:xtdm id="orderType" dictCode="ORDERTYPE" value="0"></house:xtdm>				
					</li> 
				</ul>		
			</form>
		</div>
  	</div>
</div>
</body>
</html>
