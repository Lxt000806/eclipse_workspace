<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>电子合同机构管理</title>
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
	td{
		vertical-align:middle !important;
	}
	</style>
<script type="text/javascript">
	/**导出EXCEL*/
	function doExcel() {
		var url ="${ctx}/admin/taxPayeeESign/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function() {
		Global.JqGrid.initJqGrid("dataTable", {
			url:"${ctx}/admin/taxPayeeESign/goJqGrid",
			height: $(document).height() - $("#content-list").offset().top-70,
			styleUI:"Bootstrap",
			colModel: [
				{name:"pk", index:"pk", label:"pk", width:80, sortable: true, align:"center",hidden:true}, 
				{name:"payeedescr", index:"payeedescr", label:"收款单位", width:90, sortable: true, align:"center"}, 
				{name:"orgname", index:"orgname", label:"e签宝机构", width:180, sortable: true, align:"center",}, 
				{name:"orgid", index:"orgid", label:"e签宝机构账号", width:200, sortable: true, align:"center",hidden:true}, 
				{name:"url", index:"url", label:"印章", width:100, sortable: true, align:"center",formatter:sealBtn}, 
				{name:"sealid", index:"sealid", label:"印章Id", width:100, sortable: true, align:"center",hidden:true}, 
				{name:"lastupdate", index:"lastupdate", label:"最后修改时间", sortable: true, width:120, align:"center",formatter:formatTime}, 
				{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"center"}, 
				{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"center"}, 
				{name:"actionlog", index:"actionlog", label:"操作代码", width:100, sortable: true, align:"center"}, 
			]
		});
		
	});
	
	function sealBtn(v,x,r){
		return "<img style='cursor:pointer' title='点击查看大图' width='80px' height='80px' src='"+v+"' onclick='viewSealPic("+x.rowId+")'></img>";
	}  
	
	function save(){
		Global.Dialog.showDialog("save",{
			title:"电子合同机构管理——新增",
			postData:{},
			url:"${ctx}/admin/taxPayeeESign/goSave",
			height:250,
			width:400,
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
		Global.Dialog.showDialog("save",{
			title:"电子合同机构管理——编辑",
			postData:{pk: ret.pk},
			url:"${ctx}/admin/taxPayeeESign/goUpdate",
			height:250,
			width:400,
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
		Global.Dialog.showDialog("save",{
			title:"电子合同机构管理——查看",
			postData:{pk: ret.pk},
			url:"${ctx}/admin/taxPayeeESign/goView",
			height:250,
			width:400,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function viewSealPic(id){
		var ret = $("#dataTable").jqGrid('getRowData', id);
		Global.Dialog.showDialog("viewSealPic",{ 
	  		title:"查看印章图片",
	  		url:"${ctx}/admin/taxPayeeESign/goViewSealPic",
	  		postData: {
	  		    orgId:ret.orgid,sealId:ret.sealid
	  		},
	  		height:600,
	  		width:600,
	  		returnFun:goto_query
	    });	
	}
	
	function orgManage(){
		Global.Dialog.showDialog("OrgManage",{
			title:"机构管理",
			postData:{},
			url:"${ctx}/admin/taxPayeeESign/goOrgManage",
			height:600,
			width:1100,
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
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/taxPayeeESign/doDelete",
					type:"post",
					data:{deleteIds:ret.pk},
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
</script>
</head>

<body style="scrollOffset: 0">
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
                        <label>收款单位</label>
                        <house:dict id="payeeCode" dictCode="" sql="select code Code,rtrim(code)+' '+descr Descr from tTaxPayee where Expired = 'F' "
                                    sqlValueKey="Code" sqlLableKey="Descr"></house:dict>
                    </li>
					<li class="search-group">
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="TAXPAYEEESIGN_SAVE">
					<button type="button" class="btn btn-system" onclick="save()">
						新增
					</button>
				</house:authorize>
				<house:authorize authCode="TAXPAYEEESIGN_UPDATE">
					<button type="button" class="btn btn-system" onclick="update()">
						编辑
					</button>
				</house:authorize>
				<house:authorize authCode="TAXPAYEEESIGN_VIEW">
					<button type="button" class="btn btn-system" onclick="view()">
						查看
					</button>
				</house:authorize>
				<house:authorize authCode="TAXPAYEEESIGN_ORGMANAGE">
					<button type="button" class="btn btn-system" onclick="orgManage()">
						机构管理
					</button>
				</house:authorize>
				<house:authorize authCode="TAXPAYEEESIGN_DELETE">
					<button type="button" class="btn btn-system" onclick="delDetail()">
						删除
					</button>
				</house:authorize>
				<house:authorize authCode="TAXPAYEEESIGN_EXCEL">
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
