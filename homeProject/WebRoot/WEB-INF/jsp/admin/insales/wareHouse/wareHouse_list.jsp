<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>仓库信息--列表</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp"%>
		<script type="text/javascript">
		$(function() {
		  //初始化表格
		  Global.JqGrid.initJqGrid("dataTable",{
		        url : "${ctx}/admin/wareHouse/goJqGrid",
				height : $(document).height()- $("#content-list").offset().top - 70,
				styleUI : "Bootstrap",
				colModel : [
					{name: "code", index: "code", width: 93, label: "仓库编号", sortable: true, align: "left"},
					{name: "desc1", index: "desc1", width: 135, label: "仓库名称", sortable: true, align: "left"},
					{name: "waretypedescr", index: "waretypedescr", width: 99, label: "仓库类型", sortable: true, align: "left"},
					{name: "taxpayeedescr", index: "taxpayeedescr", width: 99, label: "归属公司", sortable: true, align: "left"},
					{name: "itemtype1descr", index: "itemtype1descr", width: 99, label: "材料类型1", sortable: true, align: "left"},
					{name: "ismanageposidescr", index: "ismanageposidescr", width: 98, label: "是否管理货位", sortable: true, align: "left"},
					{name: "iswhfeedescr", index: "iswhfeedescr", width: 107, label: "是否计算仓储费", sortable: true, align: "left"},
					{name: "whfeeper", index: "whfeeper", width: 84, label: "仓储费比率", sortable: true, align: "left"},
					{name: "address", index: "address", width: 120, label: "地址", sortable: true, align: "left"},
					{name: "delivtypedescr", index: "delivtypedescr", width: 100, label: "配送方式", sortable: true, align: "left"},
					{name: "lastupdate", index: "lastupdate", width: 170, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 76, label: "过期标志", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 84, label: "操作日志", sortable: true, align: "left"}
			    ]
		     });
		     });
		   /**查看详细信息*/
		 function view() {
			var ret = selectDataTableRow();
			if (ret) {
				Global.Dialog.showDialog("wareHouse", {
			    	title : "仓库信息--查看",
					url : "${ctx}/admin/wareHouse/goDetail?code=" + ret.code,
					postData: {m_umState: "V"},
				    height : 550,
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
		 	Global.Dialog.showDialog("wareHouse",{			
				title:"仓库信息--添加",
				url:"${ctx}/admin/wareHouse/goSave",
				postData: {m_umState: "A"},
				height: 500,
				width:800,
				returnFun: goto_query
			});
		}
		function update() {
			var ret = selectDataTableRow();
			if (ret) {
				Global.Dialog.showDialog("wareHouse", {
					title : "仓库信息--编辑",
					url : "${ctx}/admin/wareHouse/goUpdate?code="+ ret.code,
					postData: {m_umState: "M"},
					height : 500,
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
			var url = "${ctx}/admin/wareHouse/doExcel";
			doExcelAll(url);
		} 	
		/**复制 */
		function copy() {
			var ret = selectDataTableRow();
			if (ret) {
				Global.Dialog.showDialog("bankPosCopy", {
				title : "仓库信息--复制",
				url : "${ctx}/admin/wareHouse/goCopy?code=" + ret.code,
				postData: {m_umState: "C"},
				height : 500,
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
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="expired"  name="expired" value="${wareHouse.expired }"/>
					<ul class="ul-form">
						<li><label>仓库编号</label> <input type="text" id="code" name="code" style="width:160px;" /></li>
				    	<li class="search-group-shrink">
				    		<input type="checkbox"  id="expired_show" name="expired_show" onclick="hideExpired(this)" 
						     ${wareHouse.expired!='T'?'checked':''} />   
						    <P>隐藏过期</p>
							<button type="summit" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
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
						<house:authorize authCode="wareHouse_VIEW">
							<button type="button" class="btn btn-system " onclick="view()">
							<span>查看</span>
							</button>
						</house:authorize>
						<button type="button" class="btn btn-system " onclick="doExcel()">导出Excel</button>
					</div>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
	</body>
</html>



