<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>主材提成规则列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
	$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/mainCommiRule/goJqGrid",
            styleUI: 'Bootstrap',
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : "no",index : "no",width : 70,label:"编号",sortable : true,align : "left"},
			  {name : "commitype",index : "commitype",width : 70,label:"提成类型",sortable : true,align : "left",hidden:true},
		      {name : "commitypedescr",index : "commitypedescr",width : 100,label:"提成类型",sortable : true,align : "left"},
		      {name : "fromprofit",index : "fromprofit",width : 90,label:"毛利率从",sortable : true,align : "right"},
		      {name : "toprofit",index : "toprofit",width : 90,label:"毛利率至",sortable : true,align : "right"},			    
		      {name : "commiperc",index : "commiperc",width : 90,label:"提成比例",sortable : true,align : "right"},
		      {name : "remarks",index : "remarks",width : 150,label:"备注",sortable : true,align : "left",},
		      {name : "lastupdate",index : "lastupdate",width : 150,label:"最后修改时间",sortable : true,align : "left",formatter: formatTime},
		      {name : "lastupdatedby",index : "lastupdatedby",width : 70,label:"修改人",sortable : true,align : "left"},
		      {name : "expired",index : "expired",width : 70,label:"是否过期",sortable : true,align : "left"},
		      {name : "actionlog",index : "actionlog",width : 70,label:"操作日志",sortable : true,align : "left"}
            ]                        
		});
	});  
});

function add(){	
	Global.Dialog.showDialog("CarryRuleAdd",{			
	  title:"添加主材提成规则",
	  url:"${ctx}/admin/mainCommiRule/goSave",
	  postData:{
	  		commiType:'1',
			m_umState:"A"
		},
	  height: 600,
	  width:900,
	  returnFun: goto_query
	});
}


function update(){
	var ret = selectDataTableRow();
    if (ret) {    	
    	Global.Dialog.showDialog("CarryRuleUpdate",{
		    title:"修改主材提成规则",
		    url:"${ctx}/admin/mainCommiRule/goSave",
		    postData:{
				m_umState:"M",
				no:ret.no
			},
		    height:600,
		 	width:900,
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
        Global.Dialog.showDialog("CarryRuleview",{
		    title:"查看主材提成规则",
		  	url:"${ctx}/admin/mainCommiRule/goSave",
		  	postData:{
				m_umState:"V",
				no:ret.no
			},
		  	height:600,
		  	width:900,
		  	returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var url = "${ctx}/admin/mainCommiRule/doDelete";
	beforeDel(url,"no");
	returnFun: goto_query;	
}

</script>
</head>
    
<body>
	<div class="body-box-list">
        <div class="query-form"  >  
	        <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="exportData" id="exportData">
				<ul class="ul-form">
					<li>						
						<label>提成类型</label>
						<house:xtdm id="commiType" dictCode="COMMITYPE" value="${mainCommiRule.commiType} }"></house:xtdm>													
					</li>
					<li>							
						<label>毛利率从</label>
						<input type="text" id="fromProfit" name="fromProfit" style="width:160px;" onkeyup="value=value.replace(/[^(\-|\+)?\d+(\.\d+)?$]/g,'')"  value="${camainCommiRulerryRule.fromProfit }" />
					</li>
					<li>							
						<label>毛利率至</label>
						<input type="text" id="toProfit" name="toProfit" style="width:160px;" onkeyup="value=value.replace(/[^(\-|\+)?\d+(\.\d+)?$]/g,'')"  value="${mainCommiRule.toProfit }" />
					</li>
					<li>							
						<label>提成比例</label>
						<input type="text" id="toProfit" name="toProfit" style="width:160px;" onkeyup="value=value.replace(/[^(\-|\+)?\d+(\.\d+)?$]/g,'')"  value="${mainCommiRule.commiPerc }" />
					</li>
					<li id="loadMore" >
						<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
					</li>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<div class="btn-panel" >
		    	<div class="btn-group-sm"  >
					<house:authorize authCode="MAINCOMMIRULE_ADD">
						<button type="button" class="btn btn-system " onclick="add()">添加</button>
					</house:authorize>
					<house:authorize authCode="MAINCOMMIRULE_UPDATE">
						<button type="button" class="btn btn-system "  onclick="update()">编辑</button>
					</house:authorize>
					<house:authorize authCode="MAINCOMMIRULE_DELETE">
						<button type="button" class="btn btn-system " onclick="del()">删除</button>
					</house:authorize>
					<house:authorize authCode="MAINCOMMIRULE_VIEW">
						<button type="button" class="btn btn-system "  onclick="view()">查看</button>
					</house:authorize>
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


