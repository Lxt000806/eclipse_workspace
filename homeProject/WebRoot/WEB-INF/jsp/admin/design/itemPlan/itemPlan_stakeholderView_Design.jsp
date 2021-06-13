<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>添加干系人</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var canInsertMapper=false;
var canInsertSketcher=false;
var canInsertDeepDesignMan=false;
var canInsertMeasureMan=false;
var canInsertDesignMan=false;
//校验函数
$(function() {
	if('${customer.mapper}'.trim())
	 $("#mapper").openComponent_employee({showValue:"${customer.mapper}",showLabel:"${customer.mapperName}",readonly:true});
	 else  $("#mapper").openComponent_employee({showValue:"${customer.mapper}",showLabel:"${customer.mapperName}",callBack:function(data){
	 		canInsertMapper=true;
	 }});
	if('${customer.sketcher}'.trim())
	 $("#sketcher").openComponent_employee({showValue:"${customer.sketcher}",showLabel:"${customer.sketcherName}",readonly:true});
   	else  $("#sketcher").openComponent_employee({showValue:"${customer.sketcher}",showLabel:"${customer.sketcherName}",callBack:function(data){
   			canInsertSketcher=true;
   	
   	}});
   	if('${customer.deepDesignMan}'.trim())
	 $("#deepDesignMan").openComponent_employee({showValue:"${customer.deepDesignMan}",showLabel:"${customer.deepDesignManDescr}",readonly:true});
   	else  $("#deepDesignMan").openComponent_employee({showValue:"${customer.deepDesignMan}",showLabel:"${customer.deepDesignManDescr}",callBack:function(data){
   			canInsertDeepDesignMan=true;
   	
   	}});
   	if('${customer.designMan}'.trim())
	 $("#designMan").openComponent_employee({showValue:"${customer.designMan}",showLabel:"${customer.designManDescr}",readonly:true, disabled: true});
   	else  $("#designMan").openComponent_employee({showValue:"${customer.designMan}",showLabel:"${customer.designManDescr}",readonly:true, disabled: true,callBack:function(data){
   			canInsertDesignMan=true;
   	
   	}});
   	if('${customer.measureMan}'.trim())
	 $("#measureMan").openComponent_employee({showValue:"${customer.measureMan}",showLabel:"${customer.measureManDescr}",readonly:true, disabled: "${customer.status}" != "4" && "${customer.status}" != "5" ? false : true});
   	else  $("#measureMan").openComponent_employee({showValue:"${customer.measureMan}",showLabel:"${customer.measureManDescr}",readonly: "${customer.status}" != "4" && "${customer.status}" != "5" ? false : true, disabled: "${customer.status}" != "4" && "${customer.status}" != "5" ? false : true,callBack:function(data){
   			canInsertMeasureMan=true;
   	
   	}});
   	if(!'${customer.mapper}'.trim()||!'${customer.sketcher}'.trim()||!'${customer.deepDesignMan}'.trim()|| !'${customer.designMan}'.trim()||!'${customer.measureMan}'.trim())	$("#saveBtn").removeAttr("disabled");
   		$("#dataForm").bootstrapValidator({
		excluded:[":disabled"],
        message : 'This value is not valid',
        feedbackIcons : {/*input状态样式图片*/
            validating : 'glyphicon glyphicon-refresh'
        },
        fields: {
	      drawFee: {
		        validators: { 
		            notEmpty: { 
		            	message: '制图费不能为空'  
		            },
		             numeric: {
		            	message: '制图费只能是数字' 
		            },
		        }
	      	},
			colorDrawFee: {
		        validators: { 
		            notEmpty: { 
		            	message: '效果图费不能为空'  
		            },
		             numeric: {
		            	message: '效果图费只能是数字' 
		            },
		        }
	      	},
	      	openComponent_employee_sketcher: {
	      		validators: {
	                remote: {
	    	            message: '',
	    	            url: '${ctx}/admin/employee/getEmployee',
	    	            data: getValidateVal,  
	    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	    	        }
	            } 
	      	}, 
	      	openComponent_employee_designMan: {
	      		validators: {
	                remote: {
	    	            message: '',
	    	            url: '${ctx}/admin/employee/getEmployee',
	    	            data: getValidateVal,  
	    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	    	        }
	            } 
	      	}, 
	      	openComponent_employee_mapper: {
	      		validators: {
	                remote: {
	    	            message: '',
	    	            url: '${ctx}/admin/employee/getEmployee',
	    	            data: getValidateVal,  
	    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	    	        }
	            } 
	      	}, 
	      	openComponent_employee_deepDesignMan: {
	      		validators: {
	                remote: {
	    	            message: '',
	    	            url: '${ctx}/admin/employee/getEmployee',
	    	            data: getValidateVal,  
	    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	    	        }
	            } 
	      	}, 
	      	openComponent_employee_measureMan: {
	      		validators: {
	                remote: {
	    	            message: '',
	    	            url: '${ctx}/admin/employee/getEmployee',
	    	            data: getValidateVal,  
	    	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	    	        }
	            } 
	      	}, 
		},
    	submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
});
function save(){
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	//if(canInsertMapper||canInsertSketcher||canInsertDesignMan || canInsertDeepDesignMan || canInsertMeasureMan){
		var datas = $("#dataForm").jsonForm();
		if(!canInsertMapper) datas.mapper='';
		if(!canInsertSketcher) datas.sketcher='';
		if(!canInsertDesignMan) datas.designMan='';
		if(!canInsertDeepDesignMan) datas.deepDesignMan='';
		if(!canInsertMeasureMan) datas.measureMan='';
  	$.ajax({
		url:'${ctx}/admin/itemPlan/doUpdateDesignCustStakeholder',
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
  	
  	/*
	}else{
		art.dialog({
				content: "请选择相关干系人！"
			});
		}
   */
}

</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button id="saveBtn" type="button" class="btn btn-system" disabled="disabled" onclick="save()">保存</button>
					<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" id="code" name="code" value="${customer.code}" />
					<ul class="ul-form">
						<li><label>客户编号</label> <input type="text" id="custCode" name="custCode" value="${customer.code}"
							disabled="disabled" />
						</li>
						<li><label>客户名称</label> <input type="text" id="descr" name="descr" value="${customer.descr}"
							disabled="disabled" />
						</li>
						<li><label>楼盘</label> <input type="text" id="address" name="address" value="${customer.address }"
							disabled="disabled" />
						</li>
						<li class="form-validate"><label>绘图员</label> <input type="text" id="mapper" name="mapper" />
						</li>
						<li class="form-validate"><label>效果绘图员</label> <input type="text" id="sketcher" name="sketcher" />
						</li>
						<li class="form-validate"><label>深化设计师</label> <input type="text" id="deepDesignMan" name="deepDesignMan" />
						</li>
						<li class="form-validate"><label>设计师</label> <input type="text" id="designMan" name="designMan" />
						</li>
						<li class="form-validate"><label>量房员</label> <input type="text" id="measureMan" name="measureMan" />
						</li>
						<li class="form-validate">
							<label>制图费</label>
							<input type="text" style="width:160px;" id="drawFee" name="drawFee" value="${customer.drawFee }" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" ${customer.status != '4' && customer.status != '5' ?'': 'readonly'}/>
						</li>
						
						<li class="form-validate">
							<label>效果图费</label>
							<input type="text" id="colorDrawFee" name="colorDrawFee" value="${customer.colorDrawFee }" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" ${customer.status != '4' && customer.status != '5' ?'': 'readonly'}/>
						</li>

					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
