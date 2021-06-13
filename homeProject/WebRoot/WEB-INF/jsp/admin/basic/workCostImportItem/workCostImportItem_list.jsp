<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>人工成本导入项目管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>

</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
				  <input type="hidden" id="expired" name="expired"/>
				<ul class="ul-form">
					<li>
						<label>人工成本项目名称</label> 
						<input type="text" id="descr" name="descr"  />
					</li>
					<li>
						<label >申请类型</label>
						<house:xtdm id="workCostType" dictCode="WorkCostType"></house:xtdm>
					</li>
					<li>
						<label >申请节点类型</label>
						<house:xtdm id="type" dictCode="WORKCOSTIMPTYPE"></house:xtdm>
					</li>
					<li>
						<label>工资备注</label> 
						<input type="text" id="descr" name="remark"  />
					</li>
					<li>
					 	<input type="checkbox" id="expired_show" name="expired_show" onclick="hideExpired(this)" checked/>
					 	<label for="expired_show" style="margin-left: -3px;text-align: left;">隐藏过期</label>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="WORKCOSTIMPORTITEM_SAVE">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="WORKCOSTIMPORTITEM_UPDATE">
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
				</house:authorize>
				<house:authorize authCode="WORKCOSTIMPORTITEM_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<house:authorize authCode="WORKCOSTIMPORTITEM_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">
						<span>输出到Excel</span>
					</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
<script type="text/javascript">
var postData = $("#page_form").jsonForm();
$(function(){
	Global.JqGrid.initJqGrid("dataTable", {
		url: "${ctx}/admin/workCostImportItem/goJqGrid",
		postData:postData ,
		sortable: true,
		height: $(document).height() - $("#content-list").offset().top - 70,
		styleUI: "Bootstrap", 
		colModel: [
		 		{name: "no", index: "no", width: 70, label: "编码", sortable: true, align: "left"},
                {name: "descr", index: "descr", width: 100, label: "人工成本项目名称", sortable: true, align: "left",  },
                {name: "workcosttypedescr", index: "workcosttypedescr", width: 70, label: "申请类型", sortable: true, align: "left",  },
                {name: "typedescr", index: "typedescr", width: 90, label: "申请节点类型", sortable: true, align: "left",  },
                {name: "prjitemdescr", index: "prjitemdescr", width: 70, label: "申请节点", sortable: true, align: "left",  },
                {name: "worktype12descr", index: "worktype12descr", width: 95, label: "申请工种分类12", sortable: true, align: "left",  },
                {name: "calctypedescr", index: "calctypedescr", width: 70, label: "计算类型", sortable: true, align: "left",  },
                {name: "price", index: "price", width: 70, label: "单价", sortable: true, align: "right",  },
                {name: "worktype2descr", index: "worktype2descr", width: 80, label: "工种分类2", sortable: true, align: "left",  },
                {name: "offerworktype12descr", index: "offerworktype12descr", width: 95, label: "工人工种分类12", sortable: true, align: "left",  },
                {name: "remark", index: "remark", width: 120, label: "工资备注", sortable: true, align: "left",  },
                {name: "isrepeatimport", index: "isrepeatimport", width: 80, label: "可重复导入", sortable: true, align: "left",  },
                {name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
                {name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "最后修改人员", sortable: true, align: "left"},
                {name: "expired", index: "expired", width: 50, label: "过期", sortable: true, align: "left"},
                {name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"},
		],
		ondblClickRow: function(){
			view();
		}
	});
	
	$("#save").on("click", function() {
		Global.Dialog.showDialog("save", {
			title : "人工成本导入项目管理——新增",
			url : "${ctx}/admin/workCostImportItem/goSave",
			height : 450,
			width : 700,
			returnFun : goto_query
		});
	});
	
	$("#update").on("click",function(){
		var ret=selectDataTableRow();
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"人工成本导入项目管理——编辑",
			url:"${ctx}/admin/workCostImportItem/goUpdate",
			postData:{
				id:ret.no,
			},
			height : 450,
			width : 700,
			returnFun:goto_query
		});
	});

});

function view(){
	var ret=selectDataTableRow();
	if (!ret) {
		art.dialog({
			content: "请选择一条记录"
		});
		return;
	}
	Global.Dialog.showDialog("view",{
		title:"人工成本导入项目管理——查看",
		url:"${ctx}/admin/workCostImportItem/goView",
		postData:{
			id:ret.no,
		},
		height : 450,
		width : 700,
		returnFun:goto_query
	});
}

function doExcel(){
	var url = "${ctx}/admin/workCostImportItem/doExcel";
	doExcelAll(url);
}

function clearForm(){
	$("#page_form input[type='text'] ").val('');
	$("#page_form select").val('');
} 

</script>
</body>
</html>
