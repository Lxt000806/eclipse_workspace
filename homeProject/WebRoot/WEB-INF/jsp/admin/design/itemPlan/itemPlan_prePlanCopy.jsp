<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--客户编号</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
$(function(){

	/* $.ajax({
		url:"${ctx}/admin/itemPlan/doCopyPlanArea",
		type: "post",
		data: {copyCustCode:ret.code,custCode:"${prePlanArea.custCode}"},
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
			if(obj.rs){
	    		closeWin();
	    	}else{
	    		$("#_form_token_uniq_id").val(obj.token.token);
	    		art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	}); */

	var postData = $("#page_form").jsonForm();
	Global.JqGrid.initJqGrid("dataTable",{
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',   
		colModel : [
			{name : 'code',index : 'code',width : 200,label:'code',sortable : true,align : "left",hidden:true},
			{name : 'address',index : 'address',width : 200,label:'楼盘',sortable : true,align : "left"},
			{name : 'area',index : 'area',width : 50,label:'面积',sortable : true,align : "right"},
		], 
        onSelectRow : function(id) {
         	var row = selectDataTableRow("dataTable");
         	$("#dataTableDetail").jqGrid('setGridParam',{url : "${ctx}/admin/itemPlan/getAddPlanAreaJqgrid?custCode="+row.code});
         	$("#dataTableDetail").jqGrid().trigger('reloadGrid');
  	   },
  	   ondblClickRow:function(rowid,iRow,iCol,e){
			var ret = selectDataTableRow();
			$.ajax({
				url:"${ctx}/admin/itemPlan/doCopyPlanArea",
				type: "post",
				data: {copyCustCode:ret.code,custCode:"${prePlanArea.custCode}"},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
					if(obj.rs){
			    		closeWin();
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
	});
	Global.JqGrid.initJqGrid("dataTableDetail",{
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum: 10000,
		colModel : [
				{name: "fixareatypedescr", index: "fixareatypedescr", width: 80, label: "空间类型", sortable: true, align: "left",},
				{name: "preplanareadescr", index: "preplanareadescr", width: 80, label: "空间名称", sortable: true, align: "left", },
				{name: "area", index: "area", width: 58, label: "面积", sortable: true, align: "right", sum:true,},
				{name: "perimeter", index: "perimeter", width: 58, label: "周长", sortable: true, align: "right", sum:true,},
				{name: "height", index: "height", width: 58, label: "高度", sortable: true, align: "right",editable:true, },
				{name: "length1", index: "length1", width: 58, label: "长", sortable: true, align: "right",editable:true ,},
				{name: "height1", index: "height1", width: 58, label: "高", sortable: true, align: "right",editable:true ,},
				{name: "length2", index: "length2", width: 58, label: "长", sortable: true, align: "right",editable:true ,},
				{name: "height2", index: "height2", width: 58, label: "高", sortable: true, align: "right",editable:true ,},
           ]
	});
});
function goto_query(){
	var postData = $("#page_form").jsonForm();
	$("#dataTable").jqGrid("setGridParam", {
    	url: "${ctx}/admin/customer/goJqGrid",
    	postData: postData
  	}).trigger("reloadGrid");
}

function copy(){
	var ret = selectDataTableRow();
	$.ajax({
		url:"${ctx}/admin/itemPlan/doCopyPlanArea",
		type: "post",
		data: {copyCustCode:ret.code,custCode:"${prePlanArea.custCode}"},
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
			if(obj.rs){
	    		closeWin();
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
	<div class="body-box-list" >
		<div class="query-form" >
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
				<input type="hidden" id=existsPrePlan name="existsPrePlan" value="1" />
				<ul class="ul-form" style="margin-left:-80px">
					<li >
						<label>楼盘</label>
						<input type="text" id="address" name="address"/>
					</li>
					<li>
						<button type="button" class="btn btn-system btn-sm" onclick="goto_query();"  >查询</button>
					</li>
					<li style="float:right">
						<button type="button" class="btn btn-system btn-sm" onclick="copy();"  >复制</button>
					</li>
					
				</ul>
			</form>
		</div>
		
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list" style="width:320px;float:left">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
			<div id="content-list" style="width:680px;float:right">
				<table id= "dataTableDetail"></table>
			</div>
		</div> 
	</div>
</body>
</html>


