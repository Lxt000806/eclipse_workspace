<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--赠送项目</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	
<script type="text/javascript">
 $(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/gift/goJqGrid",
			postData:{custCode:"${gift.custCode}"},
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap', 
			colModel:[
			{name:'pk',	  index:'pk',		width:80, 	label:'pk',		sortable: true,align:"left",hidden:true},
			{name:'descr',	  index:'descr',	width:150, 	label:'优惠标题',		sortable: true,align:"left"},
			{name:'actname',  index:'remarks',	width:150, 	label:'活动名称',		sortable: true,align:"left"},
			{name:'typedescr',  index:'typedescr',	width:100, 	label:'类型',		sortable: true,align:"left"},
			{name:'type',  index:'type',	width:100, 	label:'类型',		sortable: true,align:"left",hidden:true},
			{name:'remarks',  index:'remarks',	width:200, 	label:'说明',		sortable: true,align:"left"},
			{name: "quotemoduledescr", index: "quotemoduledescr", width: 90, label: "报价模块", sortable: true, align: "left"},
			{name:'quotemodule',  index:'quotemodule',	width:100, 	label:'报价模块',		sortable: true,align:"left",hidden:true},
			{name:'disctype',  index:'disctype',	width:100, 	label:'优惠折扣类型',		sortable: true,align:"left",hidden:true},
			{name:'begindate',  index:'begindate',	width:100, 	label:'开始时间',		sortable: true,align:"left",formatter:formatDate},
			{name:'enddate',  index:'enddate',	width:100, 	label:'结束时间',		sortable: true,align:"left",formatter:formatDate},
			{name:'minarea',  index:'minarea',	width:100, 	label:'适用最小面积',		sortable: true,align:"left"},
			{name:'maxarea',  index:'maxarea',	width:100, 	label:'适用最大面积',		sortable: true,align:"left"},
			{name:'discper',  index:'discper',	width:100, 	label:'优惠折扣比例',		sortable: true,align:"left"},
			{name:'discamttypedescr',  index:'discamttypedescr',	width:100, 	label:'优惠金额分类',		sortable: true,align:"left"},
			{name:'discamttype',  index:'discamttype',	width:100, 	label:'优惠金额分类',		sortable: true,align:"left",hidden:true},
			{name:'maxdiscamtexpr',  index:'maxdiscamtexpr',	width:100, 	label:'最大优惠金额公式',		sortable: true,align:"left"},
			{name:'perfdisctypedescr',  index:'perfdisctypedescr',	width:100, 	label:'业绩扣减类型',		sortable: true,align:"left"},
			{name:'perfdisctype',  index:'perfdisctype',	width:100, 	label:'业绩扣减类型',		sortable: true,align:"left",hidden:true},
			{name:'perfdiscper',  index:'perfdiscper',	width:100, 	label:'业绩扣减比例',		sortable: true,align:"left"},
			{name:'calcdiscctrlper',  index:'calcdiscctrlper',	width:100, 	label:'计入优惠额度控制比例',		sortable: true,align:"left",hidden:true},
			{name:'issofttokendescr',  index:'issofttokendescr',	width:100, 	label:'是否软装券',		sortable: true,align:"left"},
			{name:'islimititem',  index:'islimititem',	width:100, 	label:'是否限定报价项目',		sortable: true,align:"left",hidden:true},
			{name:'issofttoken',  index:'issofttoken',	width:100, 	label:'issofttoken',		sortable: true,align:"left",hidden:true},
			{name:'discseq',  index:'discseq',	width:100, 	label:'排序',		sortable: true,align:"left",hidden:true},
			{name:'discamtcalctype',  index:'discamtcalctype',width:100, label:'优惠额度计算方式',	sortable: true,align:"left",hidden:true},
			{name:'isadvance',  index:'isadvance',	width:100, 	label:'额度预支项目',sortable: true,align:"left",hidden:true},			
			{name:'expired',  index:'expired',	width:100, 	label:'是否过期',sortable: true,align:"left",},			
	 ],
	 		 ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            }
	});
});
</script>
	</head>

<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action=""  method="post" id="page_form" class="form-search">
				<input type="hidden" id="custCode" name="custCode" value="${gift.custCode}"/>                                  
				<ul class="ul-form">
					<li> 
					 	<label>优惠标题</label>
						<input type="text" id="descr" name="descr"/>                                  
					</li>
					<li> 
					 	<label>活动名称</label>
						<input type="text" id="actName" name="actName" value="${gift.actName}"/>                                  
					</li>
					<li id="loadMore">
						<button type="button" class="btn btn-system btn-sm"
						onclick="goto_query();">查询</button>
					</li>
				</ul>	
			</form>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>
