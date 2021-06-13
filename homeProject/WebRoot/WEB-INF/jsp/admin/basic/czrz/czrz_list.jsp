<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>操作日志列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function view(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("czrzView",{
		  title:"查看操作日志",
		  url:"${ctx}/admin/czrz/goDetail?id="+ret.CID,
		  height:600,
		  width:700
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/czrz/goJqGrid",
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-80,
			styleUI: 'Bootstrap',
			colModel : [
			  {name : 'CID',index : 'CID',width : 115,label:'操作日志编号',sortable : true,align : "left"},
		      {name : 'CZDate',index : 'CZDATE',width : 150,label:'操作日期',sortable : true,align : "left",formatter:formatTime},
		      {name : 'CZYBH',index : 'CZYBH',width : 105,label:'操作员编号',sortable : true,align : "left"},
		      {name : 'MKDM',index : 'MKDM',width : 100,label:'模块代码',sortable : true,align : "left"},
		      {name : 'RefPK',index : 'REFPK',width : 100,label:'相关主键',sortable : true,align : "left"},
		      {name : 'CZLX',index : 'CZLX',width : 100,label:'操作类型',sortable : true,align : "left"},
		      {name : 'ZY',index : 'ZY',width : 300,label:'摘要',sortable : true,align : "left"}
            ]
		});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<ul class="ul-form">
					<li>
						<label>操作日志编号</label>
						<input type="text" id="cid" name="cid" value="${czrz.cid}" />
					</li>
					<li>
						<label>操作日期从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" value="${czrz.dateFrom}" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'dateTo\')}'})"/>
					</li>
					<li>
						<label>操作日期至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" value="${czrz.dateTo}" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'dateFrom\')}'})"/>
					</li>
					<li>
						<label>模块代码</label>
						<input type="text" id="mkdm" name="mkdm" value="${czrz.mkdm}" />
					</li>
					<li>
						<label>相关主键</label>
						<input type="text" id="refPk" name="refPk" value="${czrz.refPk}" />
					</li>
					<li>
						<label>操作类型</label>
						<input type="text" id="czlx" name="czlx" value="${czrz.czlx}" />
					</li>
					<li>
						<label>操作员编号</label>
						<input type="text" id="czybh" name="czybh" value="${czrz.czybh}" />
					</li>
					<li>
						<label>摘要</label>
						<input type="text" id="zy" name="zy" value="${czrz.zy}" />
					</li>
					<li class="search-group">
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
					</li>
					
				</ul>
			</form>
		</div>
		
		<div class="clear_float"> </div>
		
		<div class="btn-panel" >
			<div class="btn-group-sm"  >
            	<house:authorize authCode="CZRZ_SAVE">
					<button type="button" class="btn btn-system " onclick="view()">查看</button>
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


