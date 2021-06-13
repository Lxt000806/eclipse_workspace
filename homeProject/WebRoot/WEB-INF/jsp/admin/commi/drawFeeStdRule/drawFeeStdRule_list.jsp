<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>绘图费公司标准规则管理列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/drawFeeStdRule/goJqGrid",
		ondblClickRow: function(){
           	view();
        },
        styleUI: 'Bootstrap',
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
		  {name : 'pk',index : 'pk',width : 70,label:'pk',sortable : true,align : "left",hidden:true},
		  {name : 'payeedescr',index : 'payeedescr',width : 70,label:'收款单位',sortable : true,align : "left"},
		  {name : 'drawprice',index : 'drawprice',width : 100,label:'每平米绘图费',sortable : true,align : "right"},
	      {name : 'drawfeemin',index : 'drawfeemin',width : 90,label:'打底绘图费',sortable : true,align : "right"},
	      {name : 'mustdesignpiccfm',index : 'mustdesignpiccfm',width : 145,label:'必须图纸审核才发提成',sortable : true,align : "left"},
	      {name : 'prior',index : 'prior',width : 75,label:'优先级',sortable : true,align : "left"},
	      {name : 'commistddesignruledescr',index : 'commistddesignruledescr',width : 120,label:'提成标准设计费',sortable : true,align : "left"},
	      {name : 'remarks',index : 'remarks',width : 150,label:'备注',sortable : true,align : "left",},
	      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
	      {name : 'lastupdatedby',index : 'lastupdatedby',width : 70,label:'修改人',sortable : true,align : "left"},
	      {name : 'expired',index : 'expired',width : 70,label:'是否过期',sortable : true,align : "left"},
	      {name : 'actionlog',index : 'actionlog',width : 70,label:'操作日志',sortable : true,align : "left"}
        ]                        
	});
});  

function add(){	
	Global.Dialog.showDialog("drawFeeStdRuleAdd",{			
	  title:"绘图费公司标准规则管理--新增",
	  url:"${ctx}/admin/drawFeeStdRule/goSave",
	  height: 600,
	  width:950,
	  returnFun: goto_query
	});
}

function copy(){
	var ret = selectDataTableRow();
    if (ret) {    	
    	Global.Dialog.showDialog("drawFeeStdRuleCopy",{
		    title:"绘图费公司标准规则管理--复制",
		    url:"${ctx}/admin/drawFeeStdRule/goCopy?pk="+ret.pk,
		    height:600,
		 	width:950,
		  	returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {    	
    	Global.Dialog.showDialog("drawFeeStdRuleUpdate",{
		    title:"绘图费公司标准规则管理--编辑",
		    url:"${ctx}/admin/drawFeeStdRule/goUpdate?pk="+ret.pk,
		    height:600,
		 	width:950,
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
        Global.Dialog.showDialog("drawFeeStdRuleview",{
		    title:"绘图费公司标准规则管理--查看",
		  	url:"${ctx}/admin/drawFeeStdRule/goView?pk="+ret.pk,
		  	height:600,
		  	width:950,
		  	returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function goCommiStdDesignRule(){
     Global.Dialog.showDialog("goCommiStdDesignRule",{
	    title:"绘图费公司标准规则管理--提成标准设计费管理",
	  	url:"${ctx}/admin/drawFeeStdRule/goCommiStdDesignRule",
	  	height:650,
		width:1130,
	}); 
}

function del(){
	var url = "${ctx}/admin/drawFeeStdRule/doDelete";
	beforeDel(url,"pk");
	returnFun: goto_query;	
}

function doExcel() {
	var url ="${ctx}/admin/drawFeeStdRule/doExcel";
	doExcelAll(url);
}
</script>
</head>
    
<body>
	<div class="body-box-list">
        <div class="query-form"  >  
	        <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="exportData" id="exportData">
				<ul class="ul-form">
					<li>
                        <label>收款单位</label>
                        <house:dict id="payeeCode" dictCode="" sql="select code Code,rtrim(code)+' '+descr Descr from tTaxPayee where Expired = 'F' "
                                    sqlValueKey="Code" sqlLableKey="Descr"></house:dict>
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
					<house:authorize authCode="DRAWFEESTDRULE_SAVE">
						<button type="button" class="btn btn-system " onclick="add()">新增</button>
					</house:authorize>
					<house:authorize authCode="DRAWFEESTDRULE_COPY">
						<button type="button" class="btn btn-system " onclick="copy()">复制</button>
					</house:authorize>
					<house:authorize authCode="DRAWFEESTDRULE_UPDATE">
						<button type="button" class="btn btn-system " onclick="update()">编辑</button>
					</house:authorize>
					<house:authorize authCode="DRAWFEESTDRULE_COMMISTDDESIGNRULE">
						<button type="button" class="btn btn-system " onclick="goCommiStdDesignRule()">提成标准设计费管理</button>
					</house:authorize>
					<house:authorize authCode="DRAWFEESTDRULE_DELETE">
						<button type="button" class="btn btn-system " onclick="del()">删除</button>
					</house:authorize>
					<house:authorize authCode="DRAWFEESTDRULE_VIEW">
						<button type="button" class="btn btn-system " onclick="view()">查看</button>
					</house:authorize>
					<house:authorize authCode="DRAWFEESTDRULE_EXCEL">
						<button type="button" class="btn btn-system" onclick="doExcel()">导出到Excel</button>
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


