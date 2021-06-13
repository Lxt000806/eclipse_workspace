<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>CommiStdDesignRule列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("commiStdDesignRuleAdd",{
		  title:"添加提成标准设计费规则",
		  url:"${ctx}/admin/commiStdDesignRule/goSave",
		  height: 300,
		  width:500,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("commiStdDesignRuleUpdate",{
		  title:"修改提成标准设计费规则",
		  url:"${ctx}/admin/commiStdDesignRule/goUpdate?id="+ret.pk,
		  height: 300,
		  width:500,
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
      Global.Dialog.showDialog("commiStdDesignRuleView",{
		  title:"查看提成标准设计费规则",
		  url:"${ctx}/admin/commiStdDesignRule/goDetail?id="+ret.pk,
		  height: 300,
		  width:500,
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var ret = selectDataTableRow("dataTable");
	if(!ret){
		art.dialog({
			content:"请选择一条数据 进行删除",
		});
		return;
	}
	art.dialog({
		content: '是删除该记录？',
		lock: true,
		width: 160,
		height: 50,
		ok: function () {
			$.ajax({
				url:"${ctx}/admin/commiStdDesignRule/doDelete?id="+ret.pk,
				type: "post",
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
				},
				success: function(obj){
					if(obj.rs){
						art.dialog({
							content: "删除成功",
							time: 1000,
						});
						goto_query();
					}else{
						$("#_form_token_uniq_id").val(obj.token.token);
						art.dialog({
							content: obj.msg,
							width: 200
						});
					}
				}
			});
			
		},
		cancel: function () {
			return;
		}
	});
	
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/commiStdDesignRule/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/commiStdDesignRule/goJqGrid",
			multiselect: false,
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : 'pk',index : 'pk',width : 100,label:'pk',sortable : true,align : 'left', hidden:true},
		      {name : 'descr',index : 'descr',width : 100,label:'名称',sortable : true,align : 'left'},
		      {name : 'stddesignfeeamount',index : 'stddesignfeeamount',width : 150,label:'收设计费标准-总金额',sortable : true,align : 'left'},
		      {name : 'stddesignfeeprice',index : 'stddesignfeeprice',width : 150,label:'收设计费标准-每平米',sortable : true,align : 'left'},
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 70,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 70,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 70,label:'操作日志',sortable : true,align : "left"}
            ]
		});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired" name="expired" value="${commiStdDesignRule.expired}" />
				<input type="hidden" id="pk" name="pk" style="width:160px;"  value="${commiStdDesignRule.pk}" />	
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>名称</label> 
						<input type="text" id="descr" name="descr" style="width:160px;"  value="${commiStdDesignRule.descr}" />	
					</li>
					<li class="search-group"><input type="checkbox" id="expired_show" name="expired_show"
						value="${commiStdDesignRule.expired}" onclick="hideExpired(this)" ${commiStdDesignRule.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>	
				</ul>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="btn-panel">
			<!--panelBar-->
			<div class="btn-group-sm">
            	<ul>
					<button type="button" class="btn btn-system " onclick="add()">新增</button>
					<button type="button" class="btn btn-system " onclick="update()">修改</button>
					<button type="button" class="btn btn-system " onclick="del()">删除</button>
					<button type="button" class="btn btn-system " onclick="view()">查看</button>
				</ul>
			 </div>
		</div><!--panelBar end-->
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>

</body>
</html>


