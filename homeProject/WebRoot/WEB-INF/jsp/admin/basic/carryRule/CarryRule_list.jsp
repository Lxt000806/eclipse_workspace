<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搬运费规则列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/CarryRule/goJqGrid",
			ondblClickRow: function(){
            	view();
            },
            styleUI: 'Bootstrap',
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : 'no',index : 'no',width : 70,label:'编号',sortable : true,align : "left"},
		      {name : 'itemtype1descr',index : 'itemtype1descr',width : 70,label:'材料类型1',sortable : true,align : "left"},
		      {name : 'itemtype2descr',index : 'itemtype2descr',width : 70,label:'材料类型2',sortable : true,align : "left"},
		      {name : 'carrytypedescr',index : 'carrytypedescr',width : 70,label:'搬运类型',sortable : true,align : "left"},			    
		      
		      {name : 'distancetypedescr',index : 'distancetypedescr',width : 70,label:'距离类型',sortable : true,align : "left"},
		      {name : 'caltypedescr',index : 'caltypedescr',width : 70,label:'计算方式',sortable : true,align : "left"},
		      {name : 'sendtypedescr',index : 'sendtypedescr',width : 70,label:'发货类型',sortable : true,align : "left"},
		      {name : 'suppldescr',index : 'suppldescr',width : 100,label:'供应商',sortable : true,align : "left"},
		      {name : 'remarks',index : 'remarks',width : 150,label:'备注',sortable : true,align : "left",},
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 70,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 70,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 70,label:'操作日志',sortable : true,align : "left"}
            ]                        
		});
	});  
});

function addFloor(){	
	Global.Dialog.showDialog("CarryRuleAdd",{			
	  title:"添加搬运费规则",
	  url:"${ctx}/admin/CarryRule/goSave",
	  height: 700,
	  width:900,
	  returnFun: goto_query
	});
}

function additem3(){	
	var ret = selectDataTableRow();
    if (ret) {    	
    	Global.Dialog.showDialog("CarryRuleUpdate",{
			title:"修改材料类型",
			url:"${ctx}/admin/CarryRule/goitem3?id="+ret.no,
		  	height:600,
		  	width:900,
		  	returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function copy(){
    var ret = selectDataTableRow();
    if (ret) {      
        Global.Dialog.showDialog("CarryRuleCopy",{
            title:"复制搬运费规则",
            url:"${ctx}/admin/CarryRule/goCopy?id="+ret.no,
            height:700,
            width:900,
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
    	Global.Dialog.showDialog("CarryRuleUpdate",{
		    title:"修改搬运费规则",
		    url:"${ctx}/admin/CarryRule/goUpdate?id="+ret.no,
		    height:700,
		 	width:900,
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
        Global.Dialog.showDialog("CarryRuleview",{
		    title:"查看搬运费规则",
		  	url:"${ctx}/admin/CarryRule/goview?id="+ret.no,
		  	height:700,
		  	width:900,
		  	returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var url = "${ctx}/admin/CarryRule/doDelete";
	beforeDel(url,"no");
	returnFun: goto_query;	
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
						<label>搬运类型</label>
						<house:xtdm id="carryType" dictCode="CarType" value="${carryRule.carryType} }"></house:xtdm>													
					</li>
					<li>
						<label>距离类型</label>
						<house:xtdm id="DistanceType" dictCode="DistanceType" value="${carryRule.distanceType} }"></house:xtdm>													
					</li>
					<li>
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1"   ></select>
					</li>
					<li>
						<label>材料类型2</label>
						<select id="itemType2" name="itemType2"   ></select>
					</li>
					<li hidden="true">							
						<label>备注信息</label>
						<input type="text" id="remarks" name="remarks" style="width:160px;"  value="${carryRule.remarks }" />
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
					<house:authorize authCode="CARRYRULE_SAVE">
						<button type="button" class="btn btn-system " onclick="addFloor()">添加</button>
					</house:authorize>
					<house:authorize authCode="CARRYRULE_UPDATE">
						<button type="button" class="btn btn-system " onclick="copy()">复制</button>
					</house:authorize>
					<house:authorize authCode="CARRYRULE_UPDATE">
						<button type="button" class="btn btn-system " onclick="update()">编辑</button>
					</house:authorize>
					<house:authorize authCode="CARRYRULE_DELETE">
						<button type="button" class="btn btn-system " onclick="del()">删除</button>
					</house:authorize>
					<house:authorize authCode="CARRYRULE_VIEW">
						<button type="button" class="btn btn-system " onclick="view()">查看</button>
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


