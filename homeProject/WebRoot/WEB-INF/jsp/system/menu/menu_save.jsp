<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>添加菜单</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
<script type="text/javascript">
var needUrl = false;

$(document).ready(function(){
	
	if('${pMenuType}' == '${tabMenuType}' || '${pMenuType}' == '${folderMenuType}'){
		$("#menuType option[value='${tabMenuType}']").remove(); 
	} else {
		$("#menuType option[value='${folderMenuType }']").remove();
		$("#menuType option[value='${urlMenuType }']").remove();
	}
	
	
	$("#menuType").change(function(){
		if(this.value == '${urlMenuType}'){
			$("#urlSign").css("visibility", "visible");
			needUrl = true;
		}else{
			$("#urlSign").css("visibility", "hidden");
			needUrl = false;
		}
	})
});

//保存数据到服务端
function save(){
	if(needUrl && $.trim($("#menuUrl").val()) == ""){
		art.dialog({
			content: '链接菜单不能为空',
			lock: true
		});
		$("#menuUrl")[0].focus();
		return ;
	}
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/menu/doSave',
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
}
//是否可订制
function showAndHidden1(sValue){
	if(sValue==1){
	   document.getElementById("showCoupon").style.display="";
	   document.getElementById("menu_code").style.display="";
	   document.getElementById("showtl").style.display="none";
	   document.getElementById("showtv").style.display="none";
	}else{
	   document.getElementById("showCoupon").style.display="none";
	   document.getElementById("menu_code").style.display="none";
	   document.getElementById("showtl").style.display="";
	   document.getElementById("showtv").style.display="";
	   
	}
}
//校验函数
$(function() {
	$("#dataForm").bootstrapValidator({
        message : 'This value is not valid',
        feedbackIcons : {/*input状态样式图片*/
            validating : 'glyphicon glyphicon-refresh'
        },
        fields: {
        menuName: {
	        validators: { 
	            notEmpty: { 
	            	message: '菜单名称不能为空'  
	            },
	            stringLength:{
               	 	min: 0,
          			max: 100,
               		message:'菜单名称不能超过100字' 
               	}
	        }
      	},
      	menuUrl: {
	        validators: {
	            stringLength:{
               	 	min: 0,
          			max: 200,
               		message:'操作链接不能超过500字' 
               	}
	        }
      	},
      	orderNo: {
	        validators: { 
	        	digits: { 
	            	message: '排序只能输入数字'  
	            }
	        }
      	},
      	sysCode: {
	        validators: { 
	            notEmpty: { 
	            	message: '平台类型不能为空'  
	            }
	        }
      	},
      	openType: {
	        validators: { 
	            notEmpty: { 
	            	message: '打开方式不能为空'  
	            }
	        }
      	},
      	menuType: {
	        validators: { 
	            notEmpty: { 
	            	message: '菜单类型不能为空'  
	            }
	        }
      	},
      	remark: {
	        validators: { 
	        	stringLength:{
               	 	min: 0,
          			max: 500,
               		message:'备注不能超过500字' 
               	}
	        }
      	}
        },
        submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
});
</script>
</head>
<body onunload="closeWin()">
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" class="btn btn-system" onclick="save()">保存</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
            	<house:token></house:token>
				<input type="hidden" id="parentId" name="parentId" value="${parentId }"/>
				<ul class="ul-form">
				<div class="validate-group row">
					<div class="col-sm-4">
					<li class="form-validate">
						<label><span class="required">*</span>菜单名称</label>
						<input type="text" style="width:160px;" id="menuName" name="menuName"/>
					</li>
					</div>
					<div class="col-sm-4">
					<li class="form-validate">
						<label><span class="required">*</span>菜单类型</label>
						<house:dict id="menuType" dictCode="ABSTRACT_DICT_MENU_TYPE"></house:dict>
					</li>
					</div>
					<div class="col-sm-4">
					<li class="form-validate">
						<label>父菜单</label>
						<input type="text" style="width:160px;" id="pName" name="pName" value="${pName }" readonly="readonly"/>
					</li>
					</div>
				</div>
				<div class="validate-group row">
					<div class="col-sm-4">
					<li class="form-validate">
						<label><span class="required">*</span>打开方式</label>
						<house:dict id="openType" dictCode="ABSTRACT_DICT_MENU_OPEN" value="1"></house:dict>
					</li>
					</div>
					<div class="col-sm-4">
					<li class="form-validate">
						<label><span class="required" id="urlSign" style="visibility:hidden;">*</span>操作链接</label>
						<input type="text" style="width:160px;" id="menuUrl" name="menuUrl"/>
					</li>
					</div>
					<div class="col-sm-4">
					<li class="form-validate">
						<label>排列顺序</label>
						<input type="text" style="width:160px;" id="orderNo" name="orderNo" value="${nextNum }"/>
					</li>
					</div>
				</div>
				<div class="validate-group row">
					<div class="col-sm-4">
					<li class="form-validate">
						<label><span class="required">*</span>平台类型</label>
						<house:dict id="sysCode" dictCode="ptdm"></house:dict>
					</li>
					</div>
					<div class="col-sm-8">
					<li class="form-validate">
						<label class="control-textarea">备注</label>
						<textarea id="remark" name="remark"></textarea>
					</li>
					</div>
				</div>
				</ul>
			</form>
		</div>
	</div>
</div>
</body>
</html>

