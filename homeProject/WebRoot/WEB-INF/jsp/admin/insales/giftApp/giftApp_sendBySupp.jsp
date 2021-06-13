<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>GiftApp_sendBySupp供应商直送列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var parentWin=window.opener;
var docHeight=0;
$(function(){
	$document = $(document);
	docHeight = $document.height();
	$('div.panelBar', $document).panelBar();

    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/giftApp/goJqGridGiftAppDetail?no="+"${giftApp.no }",
		height:docHeight-$("#content-list").offset().top-85,
		styleUI: 'Bootstrap',  
		colModel : [
		      {name : 'pk',index : 'pk',width : 70,label:'pk',sortable : true,align : "left",frozen: true,hidden:true},
		      {name : 'itemcode',index : 'itemcode',width : 70,label:'礼品编号',sortable : true,align : "left"},
		      {name : 'itemdescr',index : 'itemdescr',width : 120,label:'礼品名称',sortable : true,align : "left"},
		      {name : 'tokenpk',index : 'tokenpk',width : 60,label:'券号',sortable : true,align : "left",hidden:true},
		      {name : 'tokenno',index : 'tokenno',width : 60,label:'券号',sortable : true,align : "left"},		
		      {name : 'qty',index : 'qty',width : 50,label:'数量',sortable : true,align : "right"},	
		      {name : 'uomdescr',index : 'uomdescr',width : 50,label:'单位',sortable : true,align : "right"},	
		      {name : 'price',index : 'price',width : 50,label:'单价',sortable : true,align : "right",hidden:true},	
		      {name : 'sendqty',index : 'sendqty',width : 50,label:'送货数量',sortable : true,align : "right",hidden:true},
		      {name : 'usediscamount',index : 'usediscamount',width : 80,label:'使用优惠额度',sortable : true,align : "right"},
		      // {name : 'usediscamountdescr',index : 'usediscamountdescr',width : 80,label:'使用优惠额度',sortable : true,align : "left"},
		      {name : 'remarks',index : 'remarks',width : 200,label:'备注',sortable : true,align : "left"},	  
           ]
	}); 

	$("#supplCode").openComponent_supplier({showValue:'${giftApp.supplCode}',showLabel:'${giftApp.supplDescr}',readonly:true });
});

function send(){
	if ($.trim($("#supplCode").val())==''){
			art.dialog({content: "供应商不能为空",width: 200});
			return false;
	}
	if(!$("#dataForm").valid()) { return false;}//表单校验
	//验证
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/giftApp/doSendBySupp',
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

$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			openComponent_supplier_supplCode:{  
				validators: {  
					notEmpty: {  
						message: '仓库不能为空'  
					},
					stringLength: {
						max: 15,
						message: '长度不超过15个字符'
					},	             
					remote: {
			            message: '',
			            url: '${ctx}/admin/supplier/getSupplier',
			            data: getValidateVal,  
			            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
			        }
				}  
			},
			sendDate:{
				validators:{
					notEmpty: {  
						message: '发货时间不能为空'  
					},
				}
			}
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
});	
	



function setGridHeight() { //设置表格高度
	$("#dataTable").setGridHeight(docHeight-$("#content-list").offset().top-85+"px");	
}
</script>
</head>
    
<body >
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "  onclick="send()">发货</button>
				<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="P"/>
				<input type="hidden" id="no" name="no" value="${giftApp.no }" />
				<ul class="ul-form">
						<li class="form-validate">
							<label ><span class="required">*</span>供应商编号</label>
							<input type="text" id="supplCode" name="supplCode" style="width:160px;"  value="${giftApp.supplCode}" readonly="readonly"/>  
						</li>
						<li class="form-validate">
							<label ><span class="required">*</span>发货日期</label>
							<input type="text"  id="sendDate" name="sendDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.sendDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
						</li>
						<li class="form-validate" hidden="true">
							<label >供应商名称</label>
							<input type="text" id="supplDescr" name="supplDescr" style="width:160px;"  value="${giftApp.supplDescr}" readonly="readonly"/>
						</li>
					</ul>
			</form>
		</div>
	</div>			
</div>
  </div>
		<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
	</div>
</body>
</html>

