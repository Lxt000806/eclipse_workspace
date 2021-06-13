<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>部门列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("departmentAdd",{
		  title:"添加部门",
		  url:"${ctx}/admin/department/goSave",
		  postData:{
		  	m_umState:"A",
		  },
		  height: 400,
		  width:700,
		  returnFun: goto_query
	});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("departmentUpdate",{
		  title:"修改部门",
		  url:"${ctx}/admin/department/goUpdate",
		  postData:{
			m_umState:"M",
			map:JSON.stringify(ret)
		  },
		  height:400,
		  width:700,
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
      Global.Dialog.showDialog("departmentView",{
		  title:"查看部门",
		  url:"${ctx}/admin/department/goView",
		  postData:{
			m_umState:"V",
			map:JSON.stringify(ret)
		  },
		  height:400,
		  width:700
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function depEmp(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("depEmp",{
		  title:"部门人员",
		  url:"${ctx}/admin/department/goDepEmp",
		  postData:{
			code:ret.code
		  },
		  height:700,
		  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function modifyHigherDept(){
	var ret = selectDataTableRow();
    if (ret) {
    	if(ret.higherdepdescr==""){
    		art.dialog({
				content: "最高级部门无法修改上级部门！",
			});
			return
    	}
      Global.Dialog.showDialog("modifyHigherDept",{
		  title:"修改上级部门",
		  url:"${ctx}/admin/department/goModifyHigherDept",
		  postData:{
			map:JSON.stringify(ret)
		  },
		  height:300,
		  width:400,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function deptExpired(){
	var ret = selectDataTableRow();
    if (ret) {
      var expired=ret.expired;
      var content="";
      if(expired=="F"){
      	content="非过期改为过期";
      }else{
      	content="过期改为非过期";
      }
      Global.Dialog.showDialog("deptExpired",{
		  title:"部门过期--"+content,
		  url:"${ctx}/admin/department/goDeptExpired",
		  postData:{
			map:JSON.stringify(ret)
		  },
		  height:600,
		  width:800,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
/**初始化表格*/
$(function(){
		$("#empCode").openComponent_employee();
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/department/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			postData:{isActual:"1"},
			colModel : [
			  {name : 'code',index : 'code',width : 80,label:'部门编号',sortable : true,align : "left"},
			  {name : 'cmpcode',index : 'cmpcode',width : 80,label:'公司编号',sortable : true,align : "left"},
		      {name : 'desc2',index : 'desc2',width : 100,label:'部门名称',sortable : true,align : "left"},
		      {name : 'higherdepdescr',index : 'higherdepdescr ',width : 100,label:'上级部门',sortable : true,align : "left"},
		      {name : 'deptypedescr',index : 'deptypedescr ',width : 80,label:'部门类型',sortable : true,align : "left"},
		      {name : 'leadername',index : 'leadername ',width : 80,label:'部门领导',sortable : true,align : "left"},
		      {name : 'dispseq',index : 'dispseq',width : 70,label:'显示顺序',sortable : true,align : "right"},
		      {name : 'plannum',index : 'plannum',width : 70,label:'编制数',sortable : true,align : "right"},
		      {name : 'busitypedescr',index : 'busitypedescr',width : 100,label:'业务类型',sortable : true,align : "left"},
		      {name : 'path',index : 'path',width : 120,label:'路径',sortable : true,align : "left"},
		      {name : 'isactualdescr',index : 'isactualdescr',width : 100,label:'是否实际部门',sortable : true,align : "left"},
		      {name : 'isprocdeptdescr',index : 'isprocdeptdescr',width : 100,label:'是否流程部门',sortable : true,align : "left"},
		      {name : 'isoutchanneldescr',index : 'isoutchanneldescr',width : 80,label:'外部渠道',sortable : true,align : "left"},
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 100,label:'过期标志',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'操作日志',sortable : true,align : "left"},
		      {name : 'cmpdescr',index : 'cmpdescr',width : 80,label:'公司名称',sortable : true,align : "left",hidden:true},
		      {name : 'leadercode',index : 'leadercode',width : 80,label:'leadercode',sortable : true,align : "left",hidden:true},
		      {name : 'deptype',index : 'deptype',width : 80,label:'deptype',sortable : true,align : "left",hidden:true},
		      {name : 'higherdep',index : 'higherdep',width : 80,label:'higherdep',sortable : true,align : "left",hidden:true},
		      {name : 'busitype',index : 'busitype',width : 80,label:'busitype',sortable : true,align : "left",hidden:true},
		      {name : 'isactual',index : 'isactual',width : 80,label:'isactual',sortable : true,align : "left",hidden:true},
		      {name : 'isprocdept',index : 'isprocdept',width : 100,label:'isprocdept',sortable : true,align : "left",hidden:true},
		      {name : 'isoutchannel',index : 'isoutchannel',width : 100,label:'isprocdept',sortable : true,align : "left",hidden:true},
            ]
		});
		$("#level").append($("<option/>").text("").attr("value",""));
		for(var i=1;i<=9;i++){
			$("#level").append($("<option/>").text(i).attr("value",i));
		}
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${department.expired }" />	
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>部门名称</label> 
						<input type="text" id="desc2" name="desc2" style="width:160px;" value="${department.desc2 }"/>
					</li>
					<li>
						<label>父部门名称</label> 
						<input type="text" id="parentDep" name="parentDep" style="width:160px;" value="${department.parentDep }"/>
					</li>
					<li>
						<label>部门级别</label> 
						<select id="level" name="level"></select>
					</li>
					<li>
						<label>是否实际部门</label>
						<house:xtdm id="isActual" dictCode="YESNO" style="width:160px;" value="1" />
					</li>
					<li>
						<label>员工编号</label> 
						<input type="text" id="empCode" name="empCode" value="${department.empCode}"/>
					</li>
					<li class="search-group">
						<input type="checkbox" class="btn btn-sm btn-system " id="expired_show" name="expired_show" 
							value="${department.expired}" onclick="hideExpired(this)" ${department.expired!='T'?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="DEPARTMENT_ADD">
					<button type="button" class="btn btn-system" id="save"
						onclick="add()">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="DEPARTMENT_EDIT">
					<button type="button" class="btn btn-system" id="update"
						onclick="update()">
						<span>编辑</span>
					</button>
				</house:authorize>
				<house:authorize authCode="DEPARTMENT_VIEW">
					<button type="button" class="btn btn-system" id="view"
						onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<house:authorize authCode="DEPARTMENT_DEPEMP">
					<button type="button" class="btn btn-system" id="depEmp"
						onclick="depEmp()">
						<span>部门人员</span>
					</button>
				</house:authorize>
				<house:authorize authCode="DEPARTMENT_MODIFYHIGHERDEPT">
					<button type="button" class="btn btn-system" 
						onclick="modifyHigherDept()">
						<span>修改上级部门</span>
					</button>
				</house:authorize>
				<house:authorize authCode="DEPARTMENT_DEPTEXPIRED">
					<button type="button" class="btn btn-system" 
						onclick="deptExpired()">
						<span>部门过期</span>
					</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>


