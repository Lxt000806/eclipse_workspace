<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>操作员列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("czybmAdd",{
		  title:"添加操作员",
		  url:"${ctx}/admin/czybm/goSave",
		  height: 600,
		  width:1000,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("czybmUpdate",{
		  title:"修改操作员",
		  url:"${ctx}/admin/czybm/goUpdate?id="+ret.czybh,
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
function copy(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("czybmUpdate",{
		  title:"复制操作员",
		  url:"${ctx}/admin/czybm/goCopy?id="+ret.czybh,
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

function assign(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("czybmCopy",{
		  title:"分配角色",
		  url:"${ctx}/admin/czybm/goAssign?id="+ret.czybh,
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

function authority(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("czybmAuth",{
		  title:"选择权限",
		  url:"${ctx}/admin/czybm/goCzybmAuthority?id="+ret.czybh,
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
function appAuthority(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("AppAuth",{
		  title:"app权限",
		  url:"${ctx}/admin/czybm/goAppAuthority?id="+ret.czybh,
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
function copyAuthority(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("AppAuth",{
		  title:"复制权限",
		  url:"${ctx}/admin/czybm/goCopyAuthority?id="+ret.czybh,
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
      Global.Dialog.showDialog("czybmView",{
		  title:"查看操作员",
		  url:"${ctx}/admin/czybm/goDetail?id="+ret.czybh,
		  height:600,
		  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var url = "${ctx}/admin/czybm/doDelete";
	beforeDel(url,"czybh");
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/czybm/doExcel";
	doExcelAll(url);
}
function hideZfbz(obj){
	if ($(obj).is(':checked')){
		$('#zfbz').val(false);
	}else{
		$('#zfbz').val(true);
	}
}
function mtRegion(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("czybmCopy",{
		  title:"麦田区域配置",
		  url:"${ctx}/admin/czybm/goMtRegion?id="+ret.czybh,
		  height:600,
		  width:700,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#auth").val('');
	$.fn.zTree.getZTreeObj("tree_auth").checkAllNodes(false);
	$("#empStatus").val('');
	$.fn.zTree.getZTreeObj("tree_empStatus").checkAllNodes(false);
	$("#userRole").val('');
	$.fn.zTree.getZTreeObj("tree_userRole").checkAllNodes(false);
}

$(function(){
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/czybm/goJqGrid",
		postData:{empStatus:"ACT,OTH,WRT"},
		multiselect:true,
		height:$(document).height()-$("#content-list").offset().top-75,
		styleUI: 'Bootstrap',
		colModel : [
		  {name : 'czybh',index : 'czybh',width : 80,label:'操作员编号',sortable : true,align : "left"},
	      {name : 'emnum',index : 'emnum',width : 80,label:'员工编号',sortable : true,align : "left"},
	      {name : 'zwxm',index : 'zwxm',width : 100,label:'中文姓名',sortable : true,align : "left"},
	      {name : 'custrightdescr',index : 'custrightdescr',width : 100,label:'客户权限',sortable : true,align : "left"},
	      {name : 'costrightdescr',index : 'costrightdescr',width : 100,label:'查看成本',sortable : true,align : "left"},
	      {name : 'itemrightdescr',index : 'itemrightdescr',width : 150,label:'材料权限',sortable : true,align : "left"},
	      {name : 'custtypedescr',index : 'custtypedescr',width : 100,label:'操作客户类型',sortable : true,align : "left"},
	      {name : 'saletypedescr',index : 'saletypedescr',width : 100,label:'销售类型权限',sortable : true,align : "left"},
	      {name : 'rolename',index : 'rolename',width : 100,label:'角色',sortable : true,align : "left"},
	      {name : 'department1descr',index : 'department1descr',width : 100,label:'一级部门',sortable : true,align : "left"},
	      {name : 'department2descr',index : 'department2descr',width : 100,label:'二级部门',sortable : true,align : "left"},
	      {name : 'department3descr',index : 'department3descr',width : 100,label:'三级部门',sortable : true,align : "left"},
	      {name : 'leavedate',index : 'leavedate',width : 100,label:'离开日期',sortable : true,align : "left",formatter:formatDate},
	      {name : 'statusdescr',index : 'statusdescr',width : 60,label:'状态',sortable : true,align : "left"},
	      {name : 'isleaddescr',index : 'isleaddescr',width : 70,label:'部门领导',sortable : true,align : "left"},
	      {name : 'positiondescr',index : 'positiondescr',width : 100,label:'职位',sortable : true,align : "left"},
	      {name : 'dhhm',index : 'dhhm',width : 100,label:'电话号码',sortable : true,align : "left"},
	      {name : 'sj',index : 'sj',width : 100,label:'手机',sortable : true,align : "left"},
	      {name : 'khrq',index : 'khrq',width : 100,label:'开户日期',sortable : true,align : "left"},
	      {name : 'jslx',index : 'jslx',width : 100,label:'操作员类型',sortable : true,align : "left"},
	      {name : 'zfbz',index : 'zfbz',width : 100,label:'销户标志',sortable : true,align : "left"},
	      {name : 'czylbdescr',index : 'czylbdescr',width : 100,label:'平台类型',sortable : true,align : "left"},
	      {name : 'isoutuserdescr',index : 'isoutuserdescr',width : 100,label:'是否外网用户',sortable : true,align : "left"},
          {name : 'prjroledescr',index : 'prjroledescr',width : 100,label:'工程角色',sortable : true,align : "left"}
        ]
	});
	$("#emnum").openComponent_employee();
});
function builderSet(){
     Global.Dialog.showDialog("czybmBuilder",{
	  title:"专盘配置列表",
	  url:"${ctx}/admin/czybm/goCzybmBuilder",
	  height:600,
	  width:1000
	});
}

/**
 * 角色联动多选
 */
function checkCzylb(){
	$.ajax({
		url:ctx+'/admin/czybm/changeCzylb',
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		type: 'post',
		data: {"code" : $("#czylb").val()},
		dataType: 'json',
		cache: false,
		error: function(obj){

	    },
	    success: function(obj){
	    	setMulitOption("userRole", obj.strSelect);
	    }
	 });
}
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" id="zfbz" name="zfbz" value="${czybm.zfbz }" />	
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>操作员</label> <input type="text" id="czybh" name="czybh" value="${czybm.czybh }" />
					</li>
					<li><label>员工编号</label> <input type="text" id="emnum" name="emnum" value="${czybm.emnum}" />
					</li>
					<li><label>平台类型</label> <house:dict id="czylb" dictCode="ptdm" value="${czybm.czylb }"
							onchange="checkCzylb()" ></house:dict>
					</li>
					<li>
						<label for="empStatus">状态</label> <!-- 增加状态，默认为在岗 add by zb on 20200328 -->
						<house:xtdmMulit id="empStatus" dictCode="EMPSTS" selectedValue="ACT,OTH,WRT"/>
					</li>
					<li><label>一级部门</label> <house:department1 id="department1"
							onchange="changeDepartment1(this,'department2','${ctx }')" value="${czybm.department1 }"></house:department1>
					</li>
					<li><label>二级部门</label> <house:department2 id="department2" dictCode="${czybm.department1 }"
							value="${czybm.department2 }" onchange="changeDepartment2(this,'department3','${ctx }')"></house:department2>
					</li>
					<li><label>三级部门</label> <house:department3 id="department3" dictCode="${czybm.department2 }"
							value="${czybm.department3 }"></house:department3>
					</li>
					<li><label>部门领导</label> <house:xtdm id="isLead" dictCode="YESNO" value="${czybm.isLead }"></house:xtdm>
					</li>
					<li><label>查看成本</label> <house:xtdm id="costRight" dictCode="COSTRIGHT" value="${czybm.costRight }"></house:xtdm>
					</li>
					<li id="authLi"><label>权限</label> <house:MulitSelectAuthorize id="auth" appendAllDom="true"
							czylb="1"></house:MulitSelectAuthorize>
					</li>
					<li><label>客户权限</label> <house:xtdm id="custRight" dictCode="CUSTRIGHT" value="${czybm.custRight }"></house:xtdm>
					</li>
					<li>
						<label>角色</label>
						<house:roleMulit id="userRole" selectedValue="${czybm.userRole }" sysCode="${czybm.czylb }" width="160px"></house:roleMulit>
					</li>
					<li class="search-group"><input type="checkbox" id="zfbz_show" name="zfbz_show"
						value="${czybm.zfbz }" onclick="hideZfbz(this)" ${czybm.zfbz!=true? 'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		  <div class="btn-panel" >
   
      <div class="btn-group-sm"  >
   	<house:authorize authCode="CZYBM_SAVE">
      <button type="button" class="btn btn-system " onclick="add()">添加</button>
      </house:authorize>
    <house:authorize authCode="CZYBM_SAVE">
      <button type="button" class="btn btn-system "   onclick="copy()">复制</button>
     </house:authorize>
    <house:authorize authCode="CZYBM_UPDATE">
      <button type="button" class="btn btn-system "   onclick="update()">编辑</button>
     </house:authorize>
      <house:authorize authCode="CZYBM_DELETE">
      <button type="button" class="btn btn-system " onclick="del()">删除</button>
       </house:authorize>
     <house:authorize authCode="CZYBM_VIEW">
      <button type="button" class="btn btn-system "  onclick="view()">查看</button>
      </house:authorize>
      <house:authorize authCode="CZYBM_ASSIGN">
     	 <button type="button" class="btn btn-system "  onclick="assign()">分配角色</button>
      </house:authorize>
     
      <button type="button" class="btn btn-system " onclick="authority()">分配权限</button>
      <button type="button" class="btn btn-system " onclick="appAuthority()">App权限</button>
      <button type="button" class="btn btn-system " onclick="copyAuthority()">复制权限</button>
      <button type="button" class="btn btn-system " onclick="builderSet()">专盘配置</button>
      <house:authorize authCode=" CZYBM_MTREGION">
      <button type="button" class="btn btn-system "  onclick="mtRegion()">麦田区域</button>
      </house:authorize>
      <button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
   
      </div>
	</div>
		<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>
