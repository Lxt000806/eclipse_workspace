<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改预算价格</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
  </head>
  <body>
  	 <div class="body-box-form">
  		<div class="content-form">
  			<div class="panel panel-system" >
   				<div class="panel-body" >
      				<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn" onclick="save()">
							<span>保存</span>
						</button>
						<house:authorize authCode="ITEM_UPDATE">
							<button type="button" class="btn btn-system " onclick="doExcel()">导出Excel</button>
						</house:authorize>
						<button type="button" class="btn btn-system "  onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
  			<div class="infoBox" id="infoBoxDiv"></div>
  			<div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<div class="validate-group row" >
							<li class="form-validate">
								<label>材料编号</label>
									<input type="text" id="code" name="code" style="width:180px;"   value="${item.code}" readonly="readonly"/>                                             
							</li>
							<li class="form-validate">
								<label>材料名称</label>
									<input type="text" id="descr" name="descr" style="width:180px;"   value="${item.descr }" readonly="readonly"/>                                             
							</li>
							<li class="form-validate">
								<label>现价</label>
									<input type="text" id="price" name="price" style="width:180px;"   value="${item.price }"/>                                             
							</li>
							
							</div>
						</ul>
  				</form>
  				</div>
  			</div>
			<ul class="nav nav-tabs" >
	      	<li class="active"><a data-toggle="tab">主项目</a></li>
	   	
	   	 </ul>
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>	
  		</div>
  	</div> 
<script type="text/javascript">
function doExcel(){
	var url = "${ctx}/admin/item/doExcel_prePrice";
	doExcelAll(url);
}
function save(){
	var datas = $("#page_form").serialize();
	if ($("#price").val()==""){
		art.dialog({
			content: "价格不能为空！！",
			width: 200
		});
		return false;
	}
	$.ajax({
		url:'${ctx}/admin/item/doUpdatePrePrice',
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
$(function(){
	var gridOption = {
		url:"${ctx}/admin/item/goJqGridUpdatePrePrice",
		postData:{code:'${item.code}'},
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000000,
		styleUI: 'Bootstrap', 
		cellEdit:true,	
		colModel : [
			{name: "pk", index: "pk", width: 247, label: "pk", sortable: true, align: "left", hidden: true},
			{name: "address", index: "address", width: 209, label: "楼盘地址",  align: "left", count: true},
			{name: "fixareadescr", index: "fixareadescr", width: 140, label: "装修区域", sortable: true, align: "left"},
			{name: "baseitemdescr", index: "baseitemdescr", width: 253, label: "基础项目", sortable: true, align: "left", hidden: true},
			{name: "qty", index: "qty", width: 59, label: "数量", editable:true,editrules: {number:true,required:true},sortable: true, align: "left", sum: true},
			{name: "uom", index: "uom", width: 40, label: "单位", sortable: true, align: "left", hidden: true},
			{name: "unitprice", index: "unitprice", width: 67, label: "单价", sortable: true, align: "left"},
			{name: "beflineamount", index: "beflineamount", width: 75, label: "折扣前金额", sortable: true, align: "left", sum: true},
			{name: "markup", index: "markup", width: 55, label: "折扣", sortable: true, align: "left"},
			{name: "tmplineamount", index: "tmplineamount", width: 70, label: "小计", sortable: true, align: "left", sum: true},
			{name: "processcost", index: "processcost", width: 70, label: "其他费用",editable:true,editrules: {number:true,required:true}, sortable: true, align: "left", sum: true},
			{name: "lineamount", index: "lineamount", width: 70, label: "总价", sortable: true, align: "left", sum: true},
			{name: "remark", index: "remark", width: 247, label: "备注", sortable: true, align: "left", hidden: true}
		],
		beforeSaveCell:function(rowId,name,val,iRow,iCol){
			lastCellRowId = rowId;		
		},
		afterSaveCell:function(rowId,name,val,iRow,iCol){
		    var rowData = $("#dataTable").jqGrid("getRowData",rowId);
		    $.ajax({
				url:'${ctx}/admin/item/doUpdatePreValue',
				type: 'post',
				data:{pk:rowData.pk,qty:rowData.qty,processCost:rowData.processcost},
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
		   
			Global.JqGrid.updRowData("dataTable",rowId,rowData);	
   		 },
   		 beforeSelectRow:function(id){
        	   relocate(id,"dataTable");
		  }
		   
 		}; 
		Global.JqGrid.initEditJqGrid("dataTable",gridOption);
		
	//校验函数
 	$("#page_form").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			 price: {
		         validators: { 
		            notEmpty: { 
		            	message: '价格不能为空'  
		            },
		             numeric: {
		            	message: '价格只能是数字' 
		            },
	        	}
      	    },
		},
		submitButtons : 'input[type="submit"]'
	});	
		
});
	
</script>
  </body>
</html>

















