<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>材料类型2--列表</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
$(function(){ 
	Global.LinkSelect.initSelect("${ctx}/admin/itemType1/byItemType1","itemType1","itemType12");
});
$(function() {
  //初始化表格
  Global.JqGrid.initJqGrid("dataTable",{
        url : "${ctx}/admin/itemType2/goJqGrid",
		height : $(document).height()- $("#content-list").offset().top - 70,
		styleUI : "Bootstrap",
		colModel : [
						{name : "code",index : "code",width : 100,label : "编号",sortable : true,lign : "left"},
						{name : "descr",index : "descr",width : 100,label : "名称",sortable : true,align : "left"}, 
						{name : "itemtype1descr",index : "itemtype1descr",width : 100,label : "材料类型1",sortable : true,align : "left"}, 
						{name : "itemtype12descr",index : "itemtype12descr",width : 100,label : "材料分类",sortable : true,align : "left"},
						{name : "worktype1",index : "worktype1",width : 100,label : "材料工种分类1",sortable : true,align : "left"} ,
					    {name : "worktype2",index : "worktype2",width : 100,label : "材料工种分类2",sortable : true,align : "left"} ,
						{name : "prjdescr",index : "prjdescr",width : 100,label : "订货验收结点",sortable : true,align : "left"},
						{name : "othercostper_sale",index : "othercostper_sale",width : 150,label : "其他成本占销售额比例",sortable : true,align : "right"}, 
					    {name : "othercostper_cost",index : "othercostper_cost",width : 150,label : "其他成本占成本比例",sortable : true,align : "right"} ,					    
						{name : "arriveday",index : "arriveday",width : 100,label : "到货天数",sortable : true,align : "right"}, 
						{name : "dispseq",index : "dispseq",width : 100,label : "显示顺序",sortable : true,align : "right"}, 
						{name : "lastupdate",index : "lastupdate",width : 100,label : "最后修改时间",sortable : true,align : "left", formatter: formatTime},
						{name : "lastupdatedby",index : "lastupdatedby",width : 100,label : "修改人",sortable : true,align : "right"},
					    {name : "expired",index : "expired",width : 100,label : "是否过期",sortable : true,align : "left"}, 
					    {name : "actionlog",index : "actionlog",width : 100,label : "操作",sortable : true,align : "left"}, 				    
				    ]
       });
	});
	/**查看详细信息*/
function view() {
	var ret = selectDataTableRow();
	if (ret) {
		Global.Dialog.showDialog("itemType2", {
			    title : "材料类型2--查看",
			    url : "${ctx}/admin/itemType2/goDetail",
 				postData : {
 				    code:ret.code,
					itemType12Descr : ret.itemtype12descr,
					itemType1Descr: ret.itemtype1descr,	
					workType1: ret.worktype1,
					workType2: ret.worktype2,
					prjDescr: ret.prjdescr,			
				},
				height : 400,
				width : 800
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	}	
/**添加*/	
function add(){
	     Global.Dialog.showDialog("itemType2",{			
				title:"材料类型2--添加",
				url:"${ctx}/admin/itemType2/goSave",
				height: 400,
				width:800,
				returnFun: goto_query
	});
}	
function update() {
		 var ret = selectDataTableRow();
		 if (ret) {
				Global.Dialog.showDialog("itemType2", {
					title : "材料类型2--编辑",
					url : "${ctx}/admin/itemType2/goUpdate" ,
					postData : {
 				    code:ret.code,
 				    workType1:ret.worktype1,workType2:ret.worktype2,},
					height : 400,
					width : 800,
					returnFun : goto_query
				});
		} else {
			    art.dialog({
				content : "请选择一条记录"
			});
		}
	}
/**导出EXCEL*/
function doExcel() {
		var url = "${ctx}/admin/itemType2/doExcel";
		doExcelAll(url);
	}	
/**复制 */
function copy() {
		var ret = selectDataTableRow();
		if (ret) {
			Global.Dialog.showDialog("bankPosCopy", {
				title : "材料类型2--复制",
				url : "${ctx}/admin/itemType2/goCopy" ,
				postData : {
 				    code:ret.code,
 				    workType1:ret.worktype1,
 				    workType2:ret.worktype2,
					itemType12 : ret.itemtype12descr,
					itemType1: ret.itemtype1descr,
					prjDescr: ret.prjdescr,
					},
				height : 400,
				width : 800,
				returnFun : goto_query
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired"  name="expired" value="${itemType2.expired }"/>
				<ul class="ul-form">
					<li><label>编号</label> <input type="text" id="code" name="code"
						style="width:160px;" />
					</li>
					<li><label>名称</label> <input type="text" id="descr"
						name="descr" style="width:160px;" />
					</li>
					<li><label>材料类型1</label><select id="itemType1"
						name="itemType1" style="width: 160px;"></select>
					</li>
					<li><label>材料分类</label> <select id="itemType12"
						name="itemType12" style="width: 160px;"></select>
					</li>
					
					<li class="search-group-shrink">
					    <%--       初步理解为${itemType2.expired }的值会赋给value 然后再传给object 之后调用onclick函数 --%> 
					    <input type="checkbox"  id="expired_show" name="expired_show" onclick="hideExpired(this)" 
						       value="${itemType2.expired }" ${itemType2!='T'?'checked':''} />   
						<P>隐藏过期</p>
						<button type="summit" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div class="pageContent">
		<!-- panelBar -->
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
					<button type="button" class="btn btn-system " onclick="view()"">
						<span>查看</span>
					</button>
					<button type="button" class="btn btn-system " id="view">
						<span>打印</span>
					</button>
				<button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>
			</div>
		</div>
		<!-- panelBar End -->
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
</body>
</html>



