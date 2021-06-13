<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>材料类型3--列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
/**初始化表格*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2");
});
function add(){
	Global.Dialog.showDialog("itemType3",{
		title:"材料类型3--添加",
		url:"${ctx}/admin/itemType3/goSave",
		width:400,
		height:420,
		returnFun:goto_query
	});
}
function copy() {
	var ret = selectDataTableRow();
	if (ret) {
		Global.Dialog.showDialog("itemType3", {
			title : "材料类型3--复制",
			url : "${ctx}/admin/itemType3/goCopy?id="+ ret.code,
			width : 400,
			height : 420,
			returnFun : goto_query
			});
	} else {
		art.dialog({
		content : "请选择一条记录"
			});
		}
}
function update() {
	var ret = selectDataTableRow();
	if (ret) {
		Global.Dialog.showDialog("itemType3", {
			title : "材料类型3--编辑",
			url : "${ctx}/admin/itemType3/goUpdate?id="+ ret.code,
			width : 400,
			height : 420,
			returnFun : goto_query
			});
	} else {
		art.dialog({
		content : "请选择一条记录"
			});
	}
}
function view() {
	var ret = selectDataTableRow();
	if (ret) {
		Global.Dialog.showDialog("itemType3",{
	    	title : "材料类型3--查看",
			url : "${ctx}/admin/itemType3/goDetail?id=" + ret.code,
		    width : 400,
		    height : 420,
			returnFun : goto_query
		});
	} else {
		art.dialog({
		content : "请选择一条记录"
		});
	}
}	

function doExcel() {
	var url = "${ctx}/admin/itemType3/doExcel";
	doExcelAll(url);
} 	
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemType3/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : 'code',index : 'code',width : 100,label:'编码',sortable : true,align : "left"},
		      {name : 'descr',index : 'descr',width : 100,label:'名称',sortable : true,align : "left"},
		      {name : 'itemtype1descr',index : 'itemtype1descr',width : 100,label:'材料类型1',sortable : true,align : "left"},
		      {name : 'itemtype2descr',index : 'itemtype2descr',width : 100,label:'材料类型2',sortable : true,align : "left"},
		      {name : 'intinstallfee',index : 'intinstallfee',width : 100,label:'安装费用',sortable : true,align : "right"},
		      {name : 'dispseq',index : 'dispseq',width : 100,label:'显示顺序',sortable : true,align : "right"},
		      {name : "ordertypedescr",index : "ordertypedescr",width : 80,label : "拆单类型",sortable : true,align : "left"}, 				    	    	           
		      {name : 'lastupdate',index : 'lastupdate',width : 120,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 100,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'操作',sortable : true,align : "left"},
		      ]
		});
});
</script>
</head>  
<body>
	<div class="body-box-list">
  		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired"  name="expired" value="${itemType3.expired }"/>
				<ul class="ul-form">
					<li><label>编码</label> <input type="text" id="code" name="code" /></li>
					<li><label>名称</label> <input type="text" id="descr" name="descr" /></li>
					<li>
						<label>材料类型1</label> 
						<select id="itemType1" name="itemType1"></select>
					</li>
					<li>
						<label>材料类型2</label> 
						<select id="itemType2" name="itemType2"></select>
					</li>
				    <li class="search-group-shrink">
						<input type="checkbox"  id="expired_show" name="expired_show" onclick="hideExpired(this)" ${activity.expired!='T'?'checked':''} />   
						<P>隐藏过期</p>
						<button type="summit" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm">
					<button type="button" class="btn btn-system " onclick="add()">
					<span>新增</span>
					</button>
					<button type="button" class="btn btn-system " onclick="copy()">
					<span>复制</span>
					</button>
					<button type="button" class="btn btn-system " onclick="update()">
					<span>编辑</span>
					</button>
					<house:authorize authCode="itemType3_VIEW">
					<button type="button" class="btn btn-system " onclick="view()">
					<span>查看</span>
					</button>
					</house:authorize>
					<button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>
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


