<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>批量发货领用单</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_cmpactivity.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<ul class="ul-form">
	
					 <li>
						<label>开单时间从</label>
						<input type="text" style="width:160px;" id="dateFrom" name="dateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.dateFrom}' pattern='yyyy-MM-dd'/>"/>
					</li>														
					 <li>
						<label>到</label>
						<input type="text" style="width:160px;" id="dateTo" name="dateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.dateTo }' pattern='yyyy-MM-dd'/>"/>
					</li>		 
					<li>
						<label>活动编号</label>
						<input type="text" id="actNo" name="actNo"  style="width:160px;" value='${giftApp.actNo }'/>
					</li>
					<li>
						<label></label>						
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
					</li>
					<li>
						<label>仓库编号</label>
						<input type="text" id="whCode" name="whCode"  style="width:160px;" value='${giftApp.whCode }'/>
					</li>
					<li >
						<label> 领用类型</label>
						<house:xtdm id="type" dictCode="GIFTAPPTYPE"  value="${giftApp.type }" ></house:xtdm>
					</li>
				</ul>

			</form>
		</div>
		<div class="clear_float"> </div>
		<div class="btn-panel">
			<div class="btn-group-sm">
                <button type="button" class="btn btn-system " id="saveBtn">发货</button>
                <button type="button" class="btn btn-system " id="selectallBtn">全选</button>
                <button type="button" class="btn btn-system " id="unselectallBtn">不选</button>
                <button type="button" class="btn btn-system " id="closeBut" onclick="closeWin()">关闭</button>
			</div>
		</div>
		
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</div>
<script type="text/javascript">
/**初始化表格*/
$(function(){
		
	$("#actNo").openComponent_cmpactivity();
	$("#whCode").openComponent_wareHouse();

        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/giftApp/goJqGrid",
		    postData:{status:"OPEN",outType:"1"},
			height:$(document).height()-$("#content-list").offset().top-120,
			multiselect:true,
			styleUI: 'Bootstrap',
			colModel : [
			 {name : 'no',index : 'no',width : 80,label:'领用单号',sortable : true,align : "left"},
			  {name : 'type',index : 'type',width : 70,label:'领用类型',sortable : true,align : "left",hidden:true},
		      {name : 'typedescr',index : 'typedescr',width : 75,label:'领用类型',sortable : true,align : "left",count:true},
		      {name : 'outtype',index : 'outtype',width :  65,label:'出库类型',sortable : true,align : "left",hidden:true},
		      {name : 'outtypedescr',index : 'outtypedescr',width : 65,label:'出库类型',sortable : true,align : "left",count:true},
		      {name : 'custcode',index : 'custCode',width :  65,label:'客户编号',sortable : true,align : "left"},
		      {name : 'custdescr',index : 'custdescr',width :  65,label:'客户名称',sortable : true,align : "left"},
		      {name : 'address',index : 'address',width : 120,label:'楼盘',sortable : true,align : "left"},
		      {name : 'setdate',index : 'setdate',width : 80,label:'下定时间',sortable : true,align : "left",formatter:formatDate},
	          {name : 'signdate',index : 'signdate',width : 80,label:'签订时间',sortable : true,align : "left",formatter:formatDate},
		      {name : 'useman',index : 'useman',width : 100,label:'领用人',sortable : true,align : "left",hidden:true},
		      {name : 'usemandescr',index : 'usemandescr',width : 65,label:'领用人',sortable : true,align : "left"},
		      {name : 'phone',index : 'a.Phone',width : 100,label:'领用人电话',sortable : true,align : "left"},
		      {name : 'status',index : 'status',width : 100,label:'状态',sortable : true,align : "left",hidden:true},
		      {name : 'statusdescr',index : 'statusdescr',width : 60,label:'状态',sortable : true,align : "left"},
		      {name : 'sendtype',index : 'sendType',width : 100,label:'发货类型',sortable : true,align : "left",hidden:true},
		      {name : 'sendtypedescr',index : 'sendtype',width : 70,label:'发货类型',sortable : true,align : "left"},
		      {name : 'actdescr',index : 'actdescr',width : 100,label:'活动名称',sortable : true,align : "left"},
		      {name : 'supplcode',index : 'supplcode',width : 100,label:'供应商',sortable : true,align : "left",hidden:true},
		      {name : 'suppldescr',index : 'suppldescr',width : 100,label:'供应商',sortable : true,align : "left"},
		      {name : 'whcode',index : 'whcode',width : 100,label:'仓库编号',sortable : true,align : "left",hidden:true},
		      {name : 'whdescr',index : 'whdescr',width : 80,label:'仓库名称',sortable : true,align : "left"},
		      {name : 'date',index : 'date',width : 80,label:'开单时间',sortable : true,align : "left",formatter:formatDate},
		      {name : 'appczydescr',index : 'appczydescr',width : 65,label:'开单人',sortable : true,align : "left"},
		      {name : 'remarks',index : 'remarks',width : 180,label:'备注',sortable : true,align : "left"},
		      {name: 'lastupdate', index: 'lastupdate', width: 135, label: '最后修改时间', sortable: true, align: 'left', formatter: formatTime},
		      {name : 'lastupdatedby',index : 'a.LastUpdatedBy',width : 100,label:'最后更新人员',sortable : true,align : "left"},
		      {name : 'expired',index : 'a.Expired',width : 100,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'a.ActionLog',width : 100,label:'操作',sortable : true,align : "left"}
		      
            ]
		});

        //发货
        $("#saveBtn").on("click",function(){
        	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
        	if(ids.length==0){
        		Global.Dialog.infoDialog("请选择一条或多条记录进行发货操作！");
        		return;
        	}
        	var idsNo=[];
        	for (var ia in ids){
        		var ret = $("#dataTable").jqGrid('getRowData', ids[ia]);
        		idsNo.push(ret.no);
        	}
    		$.ajax({
    			url:'${ctx}/admin/giftApp/doSendBatch?ids='+idsNo.join(','),
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
    		    				goto_query();
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


