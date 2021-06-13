<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>设计提成规则管理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
<META HTTP-EQUIV="expires" CONTENT="0"/>
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
<%@ include file="/commons/jsp/common.jsp"%>
<style type="text/css">
	::-webkit-input-placeholder { /* Chrome */
	  color: #cccccc;
	}

	</style>
<script type="text/javascript">
	/**导出EXCEL*/
	function doExcel() {
		var url ="${ctx}/admin/designCommiRule/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function() {
		Global.JqGrid.initJqGrid("dataTable", {
			url:"${ctx}/admin/designCommiRule/goJqGrid",
			height: $(document).height() - $("#content-list").offset().top-70,
			styleUI:"Bootstrap",
			colModel: [
				{name:"pk", index:"pk", label:"pk", width:80, sortable: true, align:"left",hidden:true}, 
				{name:"role", index:"role", label:"角色", width:80, sortable: true, align:"left",hidden:true}, 
				{name:"roledescr", index:"roledescr", label:"角色", width:80, sortable: true, align:"left",}, 
				{name:"department", index:"department", label:"部门", sortable: true, width:80,align:"left",hidden:true}, 
				{name:"deptdescr", index:"deptdescr", label:"部门", sortable: true, width:80,align:"left",}, 
				{name:"empcode", index:"empcode", label:"员工编号", sortable: true, width:60,align:"left",hidden:true}, 
				{name:"namechi", index:"namechi", label:"员工姓名", sortable: true, width:80,align:"left",}, 
				{name:"prior", index:"prior", label:"优先级", width:60, sortable: true, align:"right",}, 
				{name:"type", index:"type", label:"类型", sortable: true,width:80, align:"left",hidden:true}, 
				{name:"typedescr", index:"typedescr", label:"类型", sortable: true,width:80, align:"left",}, 
				{name:"precommiper", index:"precommiper", label:"预发提成点", width:80, sortable: true, align:"right"}, 
				{name:"checkcommiper", index:"checkcommiper", label:"结算提成点", width:80, sortable: true, align:"right"}, 
				{name:"subsidyper", index:"subsidyper", label:"补贴点",width:75, sortable: true, align:"right",}, 
				{name:"floatrulepk", index:"floatrulepk", label:"设计提成浮动规则", sortable: true, width:75, align:"left",hidden:true}, 
				{name:"floatruledescr", index:"floatruledescr", label:"设计提成浮动规则", sortable: true, width:175, align:"left", formatter: formatFloatRule}, 
				{name:"remarks", index:"remarks", label:"备注", sortable: true, width:175, align:"left", }, 
				{name:"lastupdate", index:"lastupdate", label:"最后修改时间", sortable: true, width:100, align:"left",formatter:formatDate}, 
				{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"left"}, 
				{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"left"}, 
				{name:"actionlog", index:"actionlog", label:"操作代码", width:100, sortable: true, align:"left"}, 
			]
		});
	});
	
	function formatFloatRule(cellvalue, options, rowObject){
      	if(rowObject==null){
        	return '';
		}
		if(cellvalue == null){
			return "";
		}

      	return "<a href=\"javascript:void(0)\" style=\"\" onclick=\"goFloatRuleDetail('"
      			+rowObject.floatrulepk+"')\">"+cellvalue+"</a>";
   	}
	
	function goFloatRuleDetail(pk){
		var ret = selectDataTableRow();
		Global.Dialog.showDialog("floatRuleView",{
			title:"设计提成规则管理--浮动规则查看",
			postData:{pk: pk},
			url:"${ctx}/admin/designCommiFloatRule/goView",
			height:548,
			width:1058,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function save(){
		
		Global.Dialog.showDialog("save",{
			title:"设计提成规则——新增",
			postData:{},
			url:"${ctx}/admin/designCommiRule/goSave",
			height:420,
			width:755,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function update(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content: "请选择一条数据进行编辑",
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"设计提成规则——编辑",
			postData:{pk: ret.pk},
			url:"${ctx}/admin/designCommiRule/goUpdate",
			height:420,
			width:755,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function copy(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content: "请选择一条数据进行复制",
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"设计提成规则——复制",
			postData:{pk: ret.pk},
			url:"${ctx}/admin/designCommiRule/goCopy",
			height:420,
			width:755,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function view(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content: "请选择一条数据进行查看",
			});
			return;
		}
		Global.Dialog.showDialog("view",{
			title:"设计提成规则——查看",
			postData:{pk: ret.pk},
			url:"${ctx}/admin/designCommiRule/goView",
			height:420,
			width:755,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	
	function goFloatRule(){
		Global.Dialog.showDialog("floatRule",{
			title:"浮动规则管理",
			postData:{},
			url:"${ctx}/admin/designCommiRule/goFloatRule",
			height:650,
			width:1130,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function delDetail(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content: "请选择一条数据进行删除操作",
			});
			return;
		} 
		art.dialog({
			content:"是否确定删除该记录？",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/designCommiRule/doDel",
					type:"post",
					data:{pk:ret.pk},
					dataType:"json",
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
					},
					success:function(obj){
						art.dialog({
							content: obj.msg,
						});
						$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
					}
				});
			},
			cancel: function () {
				return true;
			}
		});
	}
	
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#page_form select").val('');
		$.fn.zTree.getZTreeObj("tree_department").checkAllNodes(false);
		$.fn.zTree.getZTreeObj("tree_role").checkAllNodes(false);
		$.fn.zTree.getZTreeObj("tree_type").checkAllNodes(false);

	}
</script>
</head>

<body style="scrollOffset: 0">
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" id="expired"  name="expired" value=""/>
				<ul class="ul-form">
					<li class="form-validate">
						<label>角色</label>
						<house:dict id="role" dictCode="" sqlValueKey="Code" sqlLableKey="Descr"
						    sql="select Code,code+' '+Descr Descr from tRoll where Expired = 'F' and Code in ('00','63')"></house:dict>
					</li>
					<li>
						<label>部门</label>
						<house:DictMulitSelect id="department" dictCode="" 
						sql=" select Code, desc1 Descr from tDepartment where Expired = 'F' "
						sqlValueKey="Code" sqlLableKey="Descr"></house:DictMulitSelect>
					</li>
					<li class="form-validate">
						<label>类型</label>
						<house:xtdm id="checkCommiType" dictCode="COMMIRULETYPE" ></house:xtdm>                     
					</li>
					<li class="search-group">
						<input type="checkbox"  id="expired_show" name="expired_show"
								 value="${designCommiRule.expired }" onclick="hideExpired(this)" 
							 ${designCommiRule.expired!='T'?'checked':'' } /><p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="DESIGNCOMMIRULE_SAVE">
					<button type="button" class="btn btn-system" onclick="save()">
						新增
					</button>
				</house:authorize>
				<house:authorize authCode="DESIGNCOMMIRULE_COPY">
					<button type="button" class="btn btn-system" onclick="copy()">
						复制
					</button>
				</house:authorize>
				<house:authorize authCode="DESIGNCOMMIRULE_UPDATE">
					<button type="button" class="btn btn-system" onclick="update()">
						编辑
					</button>
				</house:authorize>
				<house:authorize authCode="DESIGNCOMMIRULE_VIEW">
					<button type="button" class="btn btn-system" onclick="view()">
						查看
					</button>
				</house:authorize>
				<house:authorize authCode="DESIGNCOMMIRULE_FLOATRULE">
					<button type="button" class="btn btn-system" onclick="goFloatRule()">
						浮动规则管理
					</button>
				</house:authorize>
				<house:authorize authCode="DESIGNCOMMIRULE_DEL">
					<button type="button" class="btn btn-system" onclick="delDetail()">
						删除
					</button>
				</house:authorize>
				<house:authorize authCode="DESIGNCOMMIRULE_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">
						导出到Excel
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
