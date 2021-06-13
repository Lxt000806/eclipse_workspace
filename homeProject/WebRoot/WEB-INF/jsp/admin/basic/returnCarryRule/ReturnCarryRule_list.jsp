<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>客户列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("returnCarryRule",{			
   		title:"添加退货搬运费规则管理",
	    url:"${ctx}/admin/ReturnCarryRule/goSave",
	    height: 600,
	    width:1000,
	    returnFun: goto_query
	});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {    	
    	Global.Dialog.showDialog("returnCarryRule",{
			title:"修改退货搬运费规则管理",
			url:"${ctx}/admin/ReturnCarryRule/goUpdate?id="+ret.no,
		    height:600,
		    width:1000,
		    returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function view(){
	var ret = selectDataTableRow();
    if (ret) {    	
    	Global.Dialog.showDialog("returnCarryRule",{
			title:"查看退货搬运费规则管理",
		    url:"${ctx}/admin/ReturnCarryRule/goView?id="+ret.no,
		    height:600,
		    width:1000,
		    returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var url = "${ctx}/admin/ReturnCarryRule/doDelete";
	beforeDel(url,"no");	
}

function goto_query(tableId){	
	var bV = $.trim($("#beginValue").val());	
	if (bV==""){
		$("#beginValue").val("0");
	}
	if (!tableId || typeof(tableId)!="string"){
		tableId = "dataTable";
	}	
	$("#"+tableId).jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}


/**初始化表格*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");

	$(function(){
	        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/ReturnCarryRule/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap',
			colModel : [
				{name : 'no',index : 'no',width : 70,label:'编号',sortable : true,align : "left",formatter:cutStr},		      
				{name : 'beginvalue',index : 'beginvalue',width : 70,label:'起始重量',sortable : true,align : "left",frozen: true},
				{name : 'endvalue',index : 'endvalue',width : 70,label:'截止重量',sortable : true,align : "left",frozen: true},
				{name : 'pricetypedescr',index : 'pricetypedescr',width : 120,label:'价格类型',sortable : true,align : "left",frozen: true},		
				{name : 'price',index : 'price',width : 70,label:'价格',sortable : true,align : "left",formatter:cutStr},
				
				{name : 'cardamount',index : 'cardamount',width : 70,label:'起始值',sortable : true,align : "left",formatter:cutStr},
				{name : 'incvalue',index : 'incvalue',width : 70,label:'递增值',sortable : true,align : "left",formatter:cutStr},
				{name : 'caltypedescr',index : 'caltypedescr',width : 70,label:'计算方式',sortable : true,align : "left",formatter:cutStr},
				
				{name : 'remarks',index : 'remarks',width : 90,label:'备注',sortable : true,align : "left",formatter:cutStr},	    			  
				{name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
				{name : 'lastupdatedby',index : 'lastupdatedby',width : 70,label:'修改人',sortable : true,align : "left"},
				{name : 'expired',index : 'expired',width : 70,label:'是否过期',sortable : true,align : "left"},
				{name : 'actionlog',index : 'actionlog',width : 70,label:'操作日志',sortable : true,align : "left"}
	        ]
		});
	});  
});
</script>
</script>
</head>
    
<body>
	<div class="body-box-list">
        <div class="query-form"  >  
	        <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="exportData" id="exportData">
				<ul class="ul-form">
				<li>
					<label>编号</label>
					<input type="text" id="no" name="no" style="width:160px;" value="${returnCarryRule.no }" />
				</li>
				<li>
					<label>价格类型</label>
						<house:xtdm id="priceType" dictCode="PriceType" value="${returnCarryRule.priceType} }"></house:xtdm>													
				</li>
				<li hidden="true">
					<label>起始重量</label>
						<input type="text" id="beginValue" name="beginValue" style="width:160px;" value="${returnCarryRule.beginValue }" />
				</li>
				<li id="loadMore" >
					<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
					<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
				</li>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<div class="btn-panel" >
		    	<div class="btn-group-sm"  >
					<house:authorize authCode="RETURNCARRYRULE_SAVE">
						<button type="button" class="btn btn-system " onclick="add()">添加</button>
					</house:authorize>
					<house:authorize authCode="RETURNCARRYRULE_UPDATE">
						<button type="button" class="btn btn-system "  onclick="update()">编辑</button>
					</house:authorize>
					<house:authorize authCode="RETURNCARRYRULE_DELETE">
						<button type="button" class="btn btn-system " onclick="del()">删除</button>
					</house:authorize>
					<house:authorize authCode="RETURNCARRYRULE_VIEW">
						<button type="button" class="btn btn-system "  onclick="view()">查看</button>
					</house:authorize>
		    	</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


