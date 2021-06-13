<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--员工卡号</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
$(function(){
	var postData=$("#page_form").jsonForm();
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/wfProcInst/getEmpAccountJqGrid",
		postData:{czybh:"${czybh }",actName:$("#actName").val()},
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',   
		colModel : [
		  {name : 'empcode',index : 'empcode',width : 80,label:'员工编号',sortable : true,align : "left",hidden:true},
	      {name : 'actname',index : 'actname',width : 80,label:'户名',sortable : true,align : "left"},
	      {name : 'cardid',index : 'cardid',width : 250,label:'卡号',sortable : true,align : "left"},
	      {name : 'bank',index : 'bank',width : 170,label:'开户行',sortable : true,align : "left"},
	      {name : 'subbranch',index : 'subbranch',width : 170,label:'支行',sortable : true,align : "left"},
	      {name : 'type',index : 'type',width : 80,label:'银行卡类型',sortable : true,align : "left"},
        ], 
        ondblClickRow:function(rowid,iRow,iCol,e){
			var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
        	Global.Dialog.returnData = selectRow;
        	Global.Dialog.closeDialog();
        }
	});
	
	$("#delDetail").on("click", function(){
		var ret = selectDataTableRow();
		
		if(!ret){
			art.dialog({
				content:"请选择要删除的数据",
			});
			return;
		}
		if(ret.type == "薪酬卡号"){
			art.dialog({
				content:"薪酬卡号无法删除",
			});
			return;
		}
		art.dialog({
			content:"是否确定删除该记录",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/wfProcInst/doDelEmpCard",
					type:"post",
					data:{actName: ret.actname, cardId: ret.cardid},
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
		
	});
	
});


</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
				<input type="hidden" id="czybh" name="czybh" value="${czybh }" />
				<ul class="ul-form" id="detail_ul">
					<div class="validate-group row" >
						<li>		
							<label>户名</label>
							<input type="text" id="actName" name="actName"/>
						</li>
						<li class="search-group" >
							<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
						</li>					
					</div>
				</ul>
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel" >
				<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " id="delDetail">
						<span>删除</span>
					</button>
				</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


