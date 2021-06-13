<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>三级部门列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">

$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType","department1","department2","department3");
	});
function add(){
	Global.Dialog.showDialog("department3Add",{
		  title:"添加三级部门",
		  url:"${ctx}/admin/department3/goSave",
		  postData:{
			m_umState:"A",
			},
		  height: 450,
		  width:415,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("department3Update",{
		  title:"修改三级部门",
		  url:"${ctx}/admin/department3/goSave",
		  postData:{
			m_umState:"U",
			code:$.trim(ret.code),
			department1:ret.department1,
			},
		  height:450,
		  width:415,
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
      Global.Dialog.showDialog("department3Copy",{
		  title:"复制三级部门",
		  url:"${ctx}/admin/department3/goSave",
		  postData:{
			m_umState:"C",
			code:$.trim(ret.code),
			department1:ret.department1,
			},
		  height:450,
		  width:415,
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
      Global.Dialog.showDialog("department3View",{
		  title:"查看三级部门",
		  url:"${ctx}/admin/department3/goSave",
		  postData:{
			m_umState:"V",
			code:$.trim(ret.code),
			department1:ret.department1,
			},
		  height:415,
		  width:415
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var url = "${ctx}/admin/department3/doDelete";
	beforeDel(url);
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/department3/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/department3/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : "code",index : "code",width : 100,label:"部门编号",sortable : true,align : "left"},
		      {name : "desc2",index : "desc2",width : 100,label:"部门名称",sortable : true,align : "left"},
		      {name : "deptdescr2",index : "deptdescr2",width : 100,label:"二级部门",sortable : true,align : "left"},
		      {name : "bdeptype",index : "bdeptype ",width : 100,label:"部门类型",sortable : true,align : "left"},
		      {name : "plannum",index : "plannum",width : 100,label:"编制数",sortable : true,align : "right"},
		      {name : 'isoutchanneldescr',index : 'isoutchanneldescr',width : 80,label:'外部渠道',sortable : true,align : "left"},
		      {name : "lastupdate",index : "lastupdate",width : 150,label:"最后修改时间",sortable : true,align : "left",formatter: formatTime},
		      {name : "lastupdatedby",index : "lastupdatedby",width : 100,label:"修改人",sortable : true,align : "left"},
		      {name : "expired",index : "expired",width : 100,label:"过期标志",sortable : true,align : "left"},
		      {name : "actionlog",index : "actionlog",width : 100,label:"操作日志",sortable : true,align : "left"},
		      {name : "department1",index : "department1",width : 100,label:"一级级部门",sortable : true,align : "left",hidden: true}
            ]
		});
});
</script>
</head>
    
<body>
	<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${department2.expired }" />	
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
                    <li>
                        <label>部门名称</label> 
                        <input type="text" name="desc2"/>
                    </li>
					<li>
						<label>部门编号</label> 
						<input type="text" id="code" name="code" style="width:160px;" value="${department2.code }"/>
					</li>
					<li>
						<label>部门类型</label> 
						<house:xtdm id="DepType" dictCode="DEPTYPE" style="width:160px;" value=""/>
					</li>
					<li>
						<label>一级部门</label> 
						<select id="department1" name="department1"></select>
					</li>
					<li>
						<label>二级部门</label> 
						<select id="department2" name="department2"></select>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${department2.expired }" onclick="hideExpired(this)" ${department2.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" id="save" onclick="add()">
					<span>新增</span>
				</button>
				<button type="button" class="btn btn-system" id="copy" onclick="copy()">
					<span>复制</span>
				</button>
				<button type="button" class="btn btn-system" id="update" onclick="update()">
					<span>编辑</span>
				</button>
				<house:authorize authCode="DEPARTMENT3_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>导出Excel</span>
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</body>
</html>


