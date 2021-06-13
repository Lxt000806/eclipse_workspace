<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemApp列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
var parentWin=window.opener;

$(function(){
	$document = $(document);
	$('div.panelBar', $document).panelBar();
	$("#whcode").openComponent_wareHouse({
		showLabel:"${itemApp.whcodeDescr}",
		showValue:"${itemApp.whcode}",
		condition: {czybh:'${czybh}'},
		callBack: validateRefresh
	});
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/supplierItemApp/goJqGridDetail?id=${itemApp.no}",
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
		  {name : 'itemcode',index : 'a.itemcode',width : 100,label:'材料编号',align : "left",count:true},
		  {name : 'itemdescr',index : 'a.itemdescr',width : 220,label:'材料名称',align : "left"},
		  {name : "fixareadescr",index : "fixareadescr",width : 100,label:"区域",align : "left"},
		  {name : "intproddescr",index : "intproddescr",width : 100,label:"集成产品",align : "left",hidden:true},
		  {name : 'qty',index : 'a.qty',width : 100,label:'数量',align : "right",sum:true},
		  {name : 'uomdescr',index : 'a.uomdescr',width : 100,label:'单位',align : "left"},
		  {name : 'remarks',index : 'a.remarks',width : 350,label:'备注',align : "left"}
           ],
          gridComplete:function(){
			if ($.trim("${itemApp.itemType1}") == "JC") {
				$("#dataTable").jqGrid("showCol", "intproddescr");
			}
          }
	});      
});

function send(){
	//验证
	//if(!$("#dataForm").valid()) {return false;}//表单校验
	//if($("#infoBoxDiv").css("display")!='none'){return false;}
	//$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;

	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/supplierItemApp/doSendByWh',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			//showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
			art.dialog({content: '保存数据出错~', width: 200});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
						if(parentWin != null)
				        	parentWin.goto_query();
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

//校验函数
$(function() {
	/*
	$("#dataForm").validate({
		rules: {
				"whcode": {
				required: true
				},
				"sendDate": {
				required: true
				}
		}
	});
	*/
	$("#dataForm").bootstrapValidator({
		message: "This value is not valid",
        feedbackIcons: { /*input状态样式图片*/
			/* valid: "glyphicon glyphicon-ok",
			invalid: "glyphicon glyphicon-remove", */
			validating: "glyphicon glyphicon-refresh"
        },
        fields: {  
	    	openComponent_wareHouse_whcode: {  
	        	validators: {  
	            	notEmpty: {  
	            		message: "必填字段"  
	            	},
	             	remote: {
		            	message: '',
		            	url: '${ctx}/admin/wareHouse/getWareHouse',
		            	data: getValidateVal,  
		            	delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
		        	}  
	        	}  
	        }   
    	},
		submitButtons : 'input[type="submit"]' /*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	}).on('success.form.bv', function (e) {
   		 e.preventDefault();
   		 send();
	});
});

function validateRefresh(){
	$('#dataForm').data('bootstrapValidator')  
                  .updateStatus('openComponent_wareHouse_whcode', 'NOT_VALIDATED',null)  
                  .validateField('openComponent_wareHouse_whcode');                   
}
</script>
</head>
    
<body onunload="closeWin()">
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
	      	<button type="button" class="btn btn-system " id="saveBut" onclick="validateDataForm()">发货</button>
	      	<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      	</div>
	   </div>
	</div>
	
	<div class="panel panel-info"> 
		<div class="panel-body">
			<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="S"/>
				<input type="hidden" id="no" name="no" value="${itemApp.no }" />
				<input type="hidden" id="czybh" name="czybh" value="${itemApp.czybh}" />
				
				<ul class="ul-form">
					<div class="validate-group row" >
						<div class="col-sm-6 form-validate" >
							<li>
								<label>仓库编号</label>
								<input type="text" id="whcode" name="whcode" value="${itemApp.whcode}" readonly="readonly"/>
							</li>
						</div>
						<div class="col-sm-6" >
							<li>
								<label>发货日期 </label>
								<input type="text" style="width:160px;" id="sendDate" name="sendDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.sendDate }' pattern='yyyy-MM-dd'/>" disabled="true"/>
							</li>
						</div>
					</div>
				</ul>
			</form>
		</div>
	</div>

	<div id="content-list">
		<table id= "dataTable"></table> 
	</div> 
</div>
</body>
</html>


