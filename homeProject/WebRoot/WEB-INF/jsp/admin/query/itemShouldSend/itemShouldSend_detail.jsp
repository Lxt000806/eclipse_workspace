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
	<title>查看明细</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemShouldSend/goDetailJqGrid",
			postData:{
				confItemType:"${customer.confItemType}",no:"${customer.no}",
			},
			height:300,
			colModel : [
			   {name: "fixareadescr",index : "fixareadescr",width : 150,label:"区域",sortable : true,align : "left"},
		       {name: "itemdescr",index : "itemdescr",width : 250,label:"材料名称",sortable : true,align : "left"},
		       {name: "qty", index: "qty", width: 70, label: "领料数量", sortable: true, align: "right",sum:true},
            ] ,
            rowNum:100000,  
	   		pager :'1',
		}); 
});

</script>
</head>
    
<body>
	<div class="panel panel-system">
		<div class="panel-body">
			<div class="btn-group-xs">
			<button type="button" class="btn btn-system "
					onclick="doExcelNow('应送货材料明细','dataTable', 'page_form')">导出excel</button>
			<button type="button" class="btn btn-system "
				onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
		<div class="panel panel-info" >  
        <div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<div class="validate-group row" >
							<li>
								<label>领料单号</label>
								<input type="text" style="width:160px;" readonly value="${map.No }"/>
							</li>
							<li>
								<label>楼盘地址</label>
								<input type="text" style="width:160px;" readonly value="${map.Address }"/>
							</li>
							<li>
								<label>状态</label>
								<input type="text" style="width:160px;" readonly value="${map.StatusDescr }"/>
							</li>
							<li>
								<label>发货类型</label>
								<input type="text" style="width:160px;" readonly value="${map.SendTypeDescr }"/>
							</li>
							<li>
								<label>申请时间</label>
								<input type="text" class="i-date" value="<fmt:formatDate value="${map.Date }" pattern='yyyy-MM-dd HH:mm:ss'/>" readonly/>
							</li>
							<li>
								<label>审核时间</label>
								<input type="text" class="i-date" value="<fmt:formatDate value="${map.ConfirmDate }" pattern='yyyy-MM-dd HH:mm:ss'/>" readonly/>
							</li>
							<li>
								<label>供应商</label>
								<input type="text" style="width:160px;" readonly value="${map.SupplDescr }"/>
							</li>
							<li>
								<label>仓库</label>
								<input type="text" style="width:160px;" readonly value="${map.WHDescr}"/>
							</li>
						</div>
					</ul>
			  </form>
	   </div>
   </div>
	<div class="pageContent">
		<div id="content-list">
			<table id="dataTable"></table>
		</div>
	</div>
</body>
</html>


