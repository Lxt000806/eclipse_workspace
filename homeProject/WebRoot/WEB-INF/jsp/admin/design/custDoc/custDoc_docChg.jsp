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
	<title>项目资料管理——图纸变更</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	$("#workerCode").openComponent_worker();
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/custDoc/goChgJqGrid",
		postData:{type:"2",status:"2,3"},
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'PK',	index:'PK',	width:90,	label:'PK',	sortable:true,align:"left",hidden:true},
			{name:'CustCode',	index:'CustCode',	width:90,	label:'CustCode',	sortable:true,align:"left",hidden:true},
			{name:'address',	index:'address',	width:200,	label:'楼盘',	sortable:true,align:"left",},
			{name:'Descr',	index:'Descr',	width:90,	label:'资料名称',	sortable:true,align:"left",editable:true,},
			{name:'DocName',	index:'DocName',	width:90,	label:'资料名称',	sortable:true,align:"left",hidden:true},
			{name:'statusdescr',	index:'statusdescr',	width:60,	label:'状态',	sortable:true,align:"left" ,},
			{name:'typedescr',	index:'typedescr',	width:90,	label:'资料类型',	sortable:true,align:"left" ,},
			{name:'Status',	index:'Status',	width:60,	label:'状态',	sortable:true,align:"left" ,hidden:true},
			{name:'custdoctypedescr',	index:'custdoctypedescr',	width:60,	label:'类型',	sortable:true,align:"left" ,},
			{name:'confirmczydescr',	index:'confirmczydescr',	width:70,	label:'审核人员',	sortable:true,align:"left"},
			{name:'ConfirmRemark',	index:'ConfirmRemark',	width:200,	label:'变更审核说明',	sortable:true,align:"left"},
			{name:'Status',	index:'Status',	width:120,	label:'状态',	sortable:true,align:"left" ,hidden:true},
			{name:'uploaddescr',	index:'uploaddescr',	width:80,	label:'上传人员',	sortable:true,align:"left" ,},
			{name:'UploadDate',	index:'UploadDate',	width:130,	label:'上传时间',	sortable:true,align:"left" ,formatter:formatTime},
			{name:'Remark',	index:'Remark',	width:175,	label:'备注',	sortable:true,align:"left" ,},
			{name:'UploadCZY',	index:'UploadCZY',	width:80,	label:'上传人员',	sortable:true,align:"left" ,hidden:true},
			{name:'LastUpdate',	index:'LastUpdate',	width:95,	label:'最后修改时间',	sortable:true,align:"left" ,formatter:formatTime},
			{name:'LastUpdatedBy',	index:'LastUpdatedBy',	width:110,	label:'最后修改人员',	sortable:true,align:"left" ,hidden:true},
			{name:'updatedescr',	index:'updatedescr',	width:95,	label:'最后修改人员',	sortable:true,align:"left" ,},
		],
		ondblClickRow:function(rowid,iRow,iCol,e){

        }
	});
	
	$("#view").on("click",function(){
		var ret = selectDataTableRow();
		if(ret){
			Global.Dialog.showDialog("View",{
				title:"在建工地——查看",
				url:"${ctx}/admin/worker/goViewOnDoDetail",
				postData:{code:ret.workercode},
				height:600,
				width:850,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
	$("#add").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("docChgAdd",{
			title:"图纸变更——添加",
			url:"${ctx}/admin/custDoc/goDocChgAdd",
			postData:{code:"00000"},
			height:700,
			width:1250,
			returnFun:goto_query,
		});
	});
	
	$("#viewChg").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("viewChg",{
			title:"图纸变更——查看",
			url:"${ctx}/admin/custDoc/goViewChg",
			postData:{pk:ret.PK},
			height:700,
			width:1050,
			returnFun:goto_query,
		});
	});
	
	$("#del").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam", "selrow");
		var docName = Global.JqGrid.allToJson("dataTable","DocName");
		var arr = new Array();
			arr = docName.fieldJson.split(",");
		var ret=selectDataTableRow();
		var lastUpdatedBy=$.trim("${customer.lastUpdatedBy}");
		var hasAuthByCzybh=$.trim("${hasAuthByCzybh}");
		var updatedescr=$.trim(ret.UploadCZY);
		if(updatedescr!=lastUpdatedBy&&hasAuthByCzybh!='true'){
			art.dialog({
				content:'只能删除本人上传的图片',
			});
			return false;
		}
		if(!ret){
			art.dialog({
				content:"请选择一条数据进行删除",
			});
			return;
		}
		if($.trim(ret.Status)=="2"||$.trim(ret.Status)=="4"){
			art.dialog({
				content:'该图片为待审核或已审核状态，不允许删除！',
			});
			return false;
		}
		
		var path="D:/homePhoto/custDoc/"+ret.CustCode+"/"+arr[id-1];

		art.dialog({
			content:"是否确定删除",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				$.ajax({
					url:'${ctx}/admin/custDoc/doDeleteDoc',
					type: 'post',
					data:{custCode:ret.CustCode,path:path,docName:ret.DocName},
					dataType: 'json',
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
				    },
				    success: function(obj){
						if(obj){
							art.dialog({
								content:'删除成功',
								time:500,
							});
							$("#dataTable").jqGrid("setGridParam",{postData:{custCode:$.trim($("#code").val()),docType1:'2'},page:1,sortname:''}).trigger("reloadGrid");
						}else{
							art.dialog({
								content:'操作失败',
								time:500,
							});
						}
				    }
				});
			},
			cancel: function () {
				return true;
			}
		});	
	});
	
	$("#commitCheck").on("click",function(){
		var ret = selectDataTableRow("dataTable");	
		var mistake= true;
		if(!ret){
			art.dialog({
				content:"请选择一条数据进行提交",
			});
			return;
		}
		if($.trim("${type }")=="2"){
			$.ajax({
				url:'${ctx}/admin/custDoc/getIsAllowCommi',//判断是否 有上传状态 审核退回状态 的 是obj：true
				type: 'post',
				data:{custCode:ret.CustCode},
				dataType: 'json',
				cache: false,
				async: false, 
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
			    },
			    success: function(obj){
					if(obj){
						mistake= false;
					}else{
						mistake= true;
					}
			    }
			 });
			if(mistake){
				art.dialog({
					content:"不存在上传中，或审核退回的图片，不允许提交审核！",
				});
				return;
			}
		}
		$.ajax({
			url:'${ctx}/admin/custDoc/doCommiCheck',
			type: 'post',
			data:{custCode:ret.CustCode,type:$.trim("${type }")},
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
				art.dialog({
					content:"提交成功",
					time:500,
					beforeunload:function(){
						goto_query();
					}
				});
			}	
		});
	});
	
});
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#workType12_NAME").val('');
	$("#department1_NAME").val('');
	$("#workType12").val('');
	$("#department1").val('');
	
	$("#dateTo").val('');
	$("#dateFrom").val('');
	$("#address").val('');
	$.fn.zTree.getZTreeObj("tree_workType12").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_department1").checkAllNodes(false);
} 


</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
    			<div class="panel-body" >
      				<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>	
			</div>
	        <div class="body-box-list">
				<div class="query-form">
				  	<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address"  style="width:160px;"/>
							</li>
							<li>
								<label>状态</label>
								<house:DictMulitSelect id="status" dictCode=""
										sql="select IBM Code,note Descr from txtdm where id ='PICPRGSTS'
											 and cbm in ('1','2','3','4')  " 
								sqlValueKey="Code" sqlLableKey="Descr" selectedValue="2,3"></house:DictMulitSelect>
							</li>
							
							<li >
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
						</ul>
					</form>
				</div>
			</div>
			<div class="btn-panel" >
    			<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " id="add" >
						<span>添加</span>
					</button>
					<button type="button" class="btn btn-system " id="viewChg" >
						<span>查看</span>
					</button>
					<button type="button" class="btn btn-system " id="del">
						<span>删除</span>
					</button>
					<button type="button" class="btn btn-system " id="commitCheck">
						<span>提交审核</span>
					</button>
				</div>	
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</body>	
</html>
