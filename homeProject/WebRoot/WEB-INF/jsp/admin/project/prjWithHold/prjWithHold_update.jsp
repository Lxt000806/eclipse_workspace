 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>添加CustLoan</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
 //校验函数
$(function() {	
	Global.LinkSelect.initSelect("${ctx}/admin/workType1/workTypeAll","workType1","workType2");
	Global.LinkSelect.setSelect({firstSelect:'workType1',
								firstValue:'${prjWithHold.workType1}',
								secondSelect:'workType2',
								secondValue:'${prjWithHold.workType2}'
	});														
	$("#custCode").openComponent_customer({showLabel:"${prjWithHold.custDescr}",showValue:"${prjWithHold.custCode}" ,readonly:"true"});
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			custCode:{  
				validators: {  
					 notEmpty: {  
						message: '客户编号不能为空'  
					},
					stringLength: {
                           max: 20,
                           message: '长度不超过20个字符'
                    }
				}  
			},	
			type:{  
				validators: {  
					 notEmpty: {  
						message: '类型不能为空'  
					},
				}  
			},	
			workType2:{  
				validators: {  
					 notEmpty: {  
						message: '工种类型2不能为空'  
					},
				}  
			},	 
			amount:{  
				validators: { 
					 notEmpty: {  
						message: '金额不能为空'  
					},
					numeric: {		
		            	message: '金额只能输入数字' 
		            },
		            between:{
	               	 	min: 0.01,
	          			max: '9999999999',
	               		message:'金额必须大于0' 
               		},
               		stringLength: {
                           max: 10,
                           message: '长度不超过10个字符'
                    }	
				}  
			},	
			
      		remarks: {
		        validators: { 
		        	stringLength:{
	               	 	min: 0,
	          			max: 400,
	               		message:'退件拒批长度必须在0-400之间' 
	               	}
		        }
      		},	
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});
function save(){
	if ($("#workType2").val()==""){
		art.dialog({content: "工种分类2不能为空",width: 200});
     	return false;
	}
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	$.ajax({                           
		url: "${ctx}/admin/prjWithHold/goVerifyPrjWithHold",//防水不发包的工地，不生成防水材料的质保预扣
		type: "post",
		data: {custCode :$("#custCode").val(),workType2:$("#workType2").val(),type:$("#type").val() },
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "请求数据出错"});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		doSave();	
	    	}else{
	    		art.dialog({
					content: obj.msg,
					width: 200,
				});
				return ;
	    	}
    	}
	});
}


function doSave(){
 	var datas = $("#dataForm").serialize();	
	$.ajax({
		url:'${ctx}/admin/prjWithHold/doUpdate',
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
					time: 3000,
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
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button id="saveBut" type="button" class="btn btn-system " onclick="save()">保存</button>
					<button id="closeBut" type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input hidden="true" type="text" id="pk" name="pk" value="${prjWithHold.pk}" />
					<input hidden="true" type="text" id="isCreate" name="isCreate" value="${prjWithHold.isCreate}" />
					<input hidden="true" type="text" id="itemAppNo" name="itemAppNo" value="${prjWithHold.itemAppNo}" /> 
					<ul class="ul-form">
						<div class="validate-group">
							<li class="form-validate"><label style="color:#777777"><span class="required">*</span>客户编号</label> 
							<input type="text" id="custCode" name="custCode" value="${prjWithHold.custCode}" />
							</li>
							<li class="form-validate"><label style="color:#777777">楼盘</label> <input type="text" id="address"
								name="address" value="${prjWithHold.address}" readonly="readonly" />
							</li>
						</div>
						
						<div class="validate-group">
							<li class="form-validate"><label>工种分类1</label> 
								<select id="workType1" name="workType1"  ></select>
							</li>
							<li class="form-validate"><label>工种分类2</label> 
								<select id="workType2" name="workType2"  ></select>
							</li>
						</div>
						<div class="validate-group">
							<li class="form-validate"><label>类型</label>
									<house:xtdm  id="type" dictCode="PrjWithHoldType"   value="${prjWithHold.type}"></house:xtdm>
							</li>
							<li class="form-validate"><label>金额</label> <input type="text" id="amount"
								name="amount"  onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
								value="${prjWithHold.amount}" />
							</li>
						</div>
						<li class="form-validate"><label class="control-textarea">备注</label> <textarea id="remarks"
								name="remarks">${prjWithHold.remarks}</textarea>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>

