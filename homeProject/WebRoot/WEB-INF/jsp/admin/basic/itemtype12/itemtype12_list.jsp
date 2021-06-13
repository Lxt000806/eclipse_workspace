<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>材料分类</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("itemType12",{			
		title:"添加材料分类",
		url:"${ctx}/admin/itemType12/goSave",
		height: 600,
		width:1000,
		returnFun: goto_query
	});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {    	
    Global.Dialog.showDialog("itemType12",{
		title:"修改材料分类",
		url:"${ctx}/admin/itemType12/goUpdate?id="+ret.code,
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
    	Global.Dialog.showDialog("itemType12",{
			title:"查看材料分类",
		    url:"${ctx}/admin/itemType12/goView?id="+ret.code,
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
	var url = "${ctx}/admin/itemType12/doDelete";
	beforeDel(url,"code");	
}


/**初始化表格*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");

	$(function(){ //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemType12/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap',
			colModel : [
				{name : 'code',index : 'code',width : 70,label:'编号',sortable : true,align : "left",formatter:cutStr},		      
		        {name : 'itemtype1descr',index : 'itemtype1descr',width : 80,label:'材料类型1',sortable : true,align : "left",frozen: true},
		        {name : 'itemtype1',index : 'itemtype1',width : 110,label:'材料类型1编号',sortable : true,align : "left",frozen: true,hidden:true},
		        {name : 'descr',index : 'descr',width : 120,label:'名称',sortable : true,align : "left",frozen: true},
		        {name : 'installfeetypedescr',index : 'installfeetypedescr',width : 120,label:'安装费类型',sortable : true,align : "left",frozen: true},	
		        {name : 'transfeetypedescr',index : 'transfeetypedescr',width : 120,label:'搬运费类型',sortable : true,align : "left",frozen: true},			
		        {name : 'dispseq',index : 'dispseq',width : 70,label:'显示顺序',sortable : true,align : "left",formatter:cutStr},
		        {name : 'proper',index : 'proper',width : 100,label:'毛利率控制线',sortable : true,align : "left",formatter:cutStr},	    			  
		        {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
		        {name : 'lastupdatedby',index : 'lastupdatedby',width : 70,label:'修改人',sortable : true,align : "left"},
		        {name : 'expired',index : 'expired',width : 70,label:'是否过期',sortable : true,align : "left"},
		        {name : 'actionlog',index : 'actionlog',width : 70,label:'操作日志',sortable : true,align : "left"}
		    ]
		});
	});  
});
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
					<input type="text" id="code" name="code"  value="${itemtype12.code }" />
				</li>
				<li>
					<label>材料类型1</label>
					<select id="itemType1" name="itemType1"  ></select>
				</li>
				<li>
					<label>名称</label>
					<input type="text" id="descr" name="descr"  value="${itemtype12.descr }" />
				</li>
				<li id="loadMore" >
					<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
					<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
				</li>
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel" >
		    	<div class="btn-group-sm"  >
					<house:authorize authCode="ITEMTYPE12_SAVE">
						<button type="button" class="btn btn-system " onclick="add()">添加</button>
					</house:authorize>
					<house:authorize authCode="ITEMTYPE12_UPDATE">
						<button type="button" class="btn btn-system "  onclick="update()">编辑</button>
					</house:authorize>
					<house:authorize authCode="ITEMTYPE12_DELETE">
						<button type="button" class="btn btn-system " onclick="del()">删除</button>
					</house:authorize>
					<house:authorize authCode="ITEMTYPE12_VIEW">
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


