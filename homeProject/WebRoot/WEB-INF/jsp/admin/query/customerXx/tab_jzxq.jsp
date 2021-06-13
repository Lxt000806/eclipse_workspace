<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>

<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_jzxq",{
			url:"${ctx}/admin/customerXx/goJqGrid_jzxq",
			postData:{custCode:'${customer.code}'},
			rowNum: 10000,
			height:490,
			colModel : [
			  {name : 'fixareadescr',index : 'fixareadescr',width : 100,label:'区域',sortable : true,align : "left",count:true},
			  {name : 'baseitemdescr',index : 'baseitemdescr',width : 200,label:'基础项目',sortable : true,align : "left"},
		      {name : 'qty',index : 'qty',width : 60,label:'数量',sortable : true,align : "right",sum:true},
		      {name : 'uom',index : 'uom',width : 60,label:'单位',sortable : true,align : "left"},
		      {name : 'unitprice',index : 'unitprice',width : 70,label:'人工单价',sortable : true,align : "right"},
		      {name : 'material',index : 'material',width : 70,label:'材料单价',sortable : true,align : "right"},
			  {name: 'isoutsetdescr', index: 'isoutsetdescr', width: 80, label: '套餐外项目', sortable: true, align: "left"},
		      {name : 'lineamount',index : 'lineamount',width : 70,label:'总价',sortable : true,align : "right",sum:true},
		      {name : 'remark',index : 'remark',width : 190,label:'备注',sortable : true,align : "left"},
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后更新时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'最后更新人员',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 100,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'操作日志',sortable : true,align : "left"}
            ],
            grouping: true,
            groupingView: {
                groupField: ["fixareadescr"],
                groupColumnShow: [true],
                groupText: ["<b title=\"{0}({1})\">{0}({1})</b>"],
                groupOrder: ["asc"],
                groupSummary: [false],
                groupCollapse: false
            }
		});
});
</script>
<div class="body-box-form">
	<div class="body-box-list">
		<div class="query-form">
			<form class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<ul class="ul-form">
					<li>
						<label>工种分类1</label>
						<house:dict id="workType1" dictCode="" 
							sql="select rtrim(Code) c,Code+' '+Descr cd from tWorkType1 where Expired = 'F' order by DispSeq" 
							sqlValueKey="c" sqlLableKey="cd"/>
					</li>
					<li>
						<label>基础项目名称</label> 
						<input type="text" id="baseItemDescr" name="baseItemDescr" style="width:160px;"/>
					</li>
					<li>
						<button type="button" class="btn  btn-sm btn-system"
							onclick="goto_query('dataTable_jzxq');">查询</button>
						<button type="button" class="btn btn-sm btn-system" id="clear" 
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="pageContent" style="padding-top: 10px;">
			<div id="content-list">
				<table id="dataTable_jzxq"></table>
			</div>
		</div>
	</div>
</div>
