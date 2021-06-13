<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>wareHouseCx列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
}

function add(){
	openShadow();
	$.fnShowWindow_min("${ctx}/admin/wareHouseCx/goSave");
}

function copy(id){
	openShadow();
	$.fnShowWindow_min("${ctx}/admin/wareHouseCx/goSave?id="+id);
}

function update(id){
	openShadow();
	$.fnShowWindow_min("${ctx}/admin/wareHouseCx/goUpdate?id="+id);
}

function view(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("wareHouseView",{
		  title:"查看仓库--"+ret.desc1,
		  url:"${ctx}/admin/wareHouseCx/goDetail?id="+ret.code,
		  height:800,
		  width:1200
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var ids = [];
	$("div.content-list").find("table>tbody").find("input[type='checkbox']").each(function(){
		if(this.checked){
			ids.push(this.value);
		}
	})
	if(ids.length <1){
		 art.dialog({
			content: '请选择需要删除的记录',
			lock: true
		 });
		return ;
	}
	art.dialog({
		 content:'您确定要删除记录吗？',
		 lock: true,
		 width: 260,
		 height: 100,
		 ok: function () {
	        $.ajax({
				url : "${ctx}/admin/wareHouseCx/doDelete",
				data : "deleteIds="+ids.join(','),
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: 'json',
				type: 'post',
				cache: false,
				error: function(){
			        art.dialog({
						content: '删除记录出现异常'
					});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    		art.dialog({
							content: obj.msg,
							time: 1000,
							beforeunload: function () {
								goto_query();
						    }
						});
			    	}else{
			    		art.dialog({
							content: obj.msg
						});
			    	}
				}
			});
	    	return true;
		},
		cancel: function () {
			return true;
		}
	});
}

function doExcel(){
	var url = "${ctx}/admin/wareHouseCx/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/wareHouseCx/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: 'Bootstrap',
			colModel : [
			  {name : 'code',index : 'code',width : 100,label:'仓库编号',sortable : true,align : "left"},
		      {name : 'desc1',index : 'desc1',width : 200,label:'仓库名称',sortable : true,align : "left"},
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后更新时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'最后更新人员',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 100,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'操作',sortable : true,align : "left"}
            ],sortorder:"desc",sortname:'lastupdate'
            ,
        	ondblClickRow:function(){
         	view();
         }
		});

});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" method="post" id="page_form">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
				<li>
					<label>仓库编号</label>
					<input type="text" id="code" name="code" style="width:160px;"  value="${wareHouse.code}" />
					</li>
					<li>
					<label>仓库名称</label>
					<input type="text" id="desc1" name="desc1" style="width:160px;"  value="${wareHouse.desc1}" />
					</li>
					<li>
							<label></label>
							<input type="checkbox"  id="expired" name="expired"  value="${wareHouse.expired=='T'?'T':'F'}" onclick="changeExpired(this)" ${wareHouse.expired=='T'?'':'checked' }/>隐藏过期&nbsp;
					</li>
					<li><button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button></li>
					<li><button type="reset" class="btn btn-system btn-sm" >清空</button></li>
				</ul>
			</form>
		</div>
		 
	<div class="btn-panel" >
				<div class="btn-group-sm">
				 <house:authorize authCode="WAREHOUSECX_VIEW">
					<button type="button" class="btn btn-system"  onclick="view()">查看</button>
				</house:authorize>
				  <house:authorize authCode="WAREHOUSECX_EXCEL">
				  	<button type="button" class="btn btn-system "  onclick="doExcel()">导出excel</button>
				  </house:authorize>
				</div>
			
		</div>
				<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
		
</body>

</html>


