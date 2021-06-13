<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>批量发货领料单</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" id="wareHouseSendAuth" name="wareHouseSendAuth" value="1"/>
				<ul class="ul-form">
					<div class="validate-group row" >
						<div class="col-sm-4" >
							<li>
								<label>供应商状态 </label>
								<input type="text" value="${itemApp.splStatusDescr}" readonly="readonly"/>
							</li>
						</div>
						<div class="col-sm-8" >
							<div class="btn-group-sm">
				                <button type="button" class="btn btn-system " onclick="goto_query();">查询</button>
							</div>
						</div>
					</div>
				</ul>

			</form>
		</div>
		
		<div class="btn-panel">
			<div class="btn-group-sm">
                <button type="button" class="btn btn-system " id="saveBtn">发货</button>
                <button type="button" class="btn btn-system " id="selectallBtn">全选</button>
                <button type="button" class="btn btn-system " id="unselectallBtn">不选</button>
                <button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
		
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</div>
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/supplierItemApp/goJqGrid",
			postData:{
				splStatus:$.trim("${itemApp.splStatus}"),
				wareHouseSendAuth:"1"
			},
			height:$(document).height()-$("#content-list").offset().top-120,
			multiselect:true,
			rowNum:10000,
			styleUI: 'Bootstrap',
			colModel : [
			  {name: 'no', index: 'a.no', width: 100, label: '领料单号', sortable: true, align: "left", count: true},
		      {name: 'address', index: 'b.Address', width: 160, label: '楼盘', sortable: true, align: "left"},
		      {name: 'custdescr', index: 'c.Descr', width: 60, label: '客户名称', sortable: true, align: "left"},
		      {name: 'status', index: 'a.Status', width: 70, label: '领料单状态', sortable: true, align: "left", hidden: true},
		      {name: 'statusdescr', index: 'a.Status', width: 70, label: '领料单状态', sortable: true, align: "left"},
		      {name: 'appczydescr', index: 'a.AppCZYDescr', width: 70, label: '申请人', sortable: true, align: "left"},
		      {name: 'confirmdate', index: 'a.ConfirmDate', width: 72, label: '审核日期', sortable: true, align: "left", formatter: formatTime},
		      {name: 'senddate', index: 'a.SendDate', width: 74, label: '发货日期', sortable: true, align: "left", formatter: formatTime},
		      {name: 'delivtypedescr', index: 'delivtypedescr', width: 70, label: '配送方式', sortable: true, align: "left"},
		      {name: 'remarks', index: 'a.Remarks', width: 180, label: '备注', sortable: true, align: "left"},
		      {name: 'whcodedescr', index: 'a.WHCodeDescr', width: 100, label: '仓库名称', sortable: true, align: "left"},
		      {name: 'projectman', index: 'a.ProjectMan', width: 70, label: '项目经理', sortable: true, align: "left"},
		      {name: 'phone', index: 'a.Phone', width: 100, label:'电话', sortable: true, align: "left"},
		      {name: 'splstatus', index: 'a.splstatus', width: 70, label: '供应商状态', sortable: true, align: "left", hidden: true},
		      {name: 'splstatusdescr', index: 'a.splstatus', width: 70, label: '供应商状态', sortable: true, align: "left"},
		      {name: 'arrivedate', index: 'a.arrivedate', width: 100, label: '预计到货日期', sortable: true, align: "left", formatter: formatTime},
		      {name: 'splremark', index: 'a.splremark', width: 180, label: '供应商备注', sortable: true, align: "left"},
		      {name: 'lastupdate', index: 'a.LastUpdate', width: 100, label: '最后更新日期', sortable: true, align: "left", formatter: formatTime},
		      {name: 'lastupdatedby', index: 'a.LastUpdatedBy', width: 100, label: '最后更新人员', sortable: true, align : "left"},
		      {name: 'expired', index: 'a.Expired', width: 100, label: '是否过期', sortable: true, align: "left"},
		      {name: 'actionlog', index: 'a.ActionLog', width: 100, label: '操作', sortable: true, align: "left"}
            ]
		});
		$("#supplier").openComponent_supplier();
		$("#supplier").setComponent_supplier({showValue:'${purchase.supplier}',showLabel:'${purchase.supplierDescr}',readonly: true});
        //发货
        $("#saveBtn").on("click",function(){
        	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
        	if(ids.length==0){
        		Global.Dialog.infoDialog("请选择一条或多条记录进行发货操作！");
        		return;
        	}
        	var infoDialog = Global.Dialog.infoDialog("发货中...",true);
        	var idsNo=[];
        	for (var ia in ids){
        		var ret = $("#dataTable").jqGrid('getRowData', ids[ia]);
        		idsNo.push(ret.no);
        	}
    		$.ajax({
    			url:'${ctx}/admin/supplierItemApp/doSendBatch?ids='+idsNo.join(','),
    			type: 'post',
    			data: {},
    			dataType: 'json',
    			cache: false,
    			error: function(obj){
    				showAjaxHtml({"hidden": false, "msg": '发货出错~'});
    		    },
    		    success: function(obj){
    		    	if(obj.rs){
    		    		art.dialog({
    						content: obj.msg,
    						//time: 1000,
    						width: 400,
    						beforeunload: function () {
    		    				closeWin();
    					    }
    					});
    		    	}else{
    		    		art.dialog({
    						content: obj.msg,
    						width: 200,
    					});
    					infoDialog.close();
    		    	}
    		    }
    		 });
        });
        
        //全选
        $("#selectallBtn").on("click",function(){
        	Global.JqGrid.jqGridSelectAll("dataTable",true);
        });
        
        //不选
        $("#unselectallBtn").on("click",function(){
        	Global.JqGrid.jqGridSelectAll("dataTable",false);
        });

});
</script>
</body>
</html>


