<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>升级扣项规则</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
        //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/upgWithhold/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',
		colModel : [
			{name : "code",index : "code",width : 70,label:"编号",sortable : true,align : "left"},
	        {name : "descr",index : "descr",width : 120,label:"名称",sortable : true,align : "left"},
	        {name : "existcal",index : "existcal",width : 70,label:"是否存在计算",sortable : true,align : "left",hidden:true},
	        {name : "existcaldescr",index : "existcaldescr",width : 100,label:"是否存在计算",sortable : true,align : "left"},
	        {name : "itemtype1",index : "itemtype1",width : 70,label:"材料类型1编号",sortable : true,align : "left",hidden:true},
	        {name : "itemtype1descr",index : "itemtype1descr",width : 70,label:"材料类型1",sortable : true,align : "left"},
	        {name : "caltype",index : "caltype",width : 70,label:"计算类型",sortable : true,align : "left",hidden:true},
	        {name : "caltypedescr",index : "caltypedescr",width : 70,label:"计算类型",sortable : true,align : "left"},
	        {name : "calamount",index : "calamount",width : 70,label:"计算金额",sortable : true,align : "right"},	
	        {name : "custtypedescr",index : "custtypedescr",width : 70,label:"客户类型",sortable : true,align : "left"},
	        {name : "custtype",index : "custtype",width : 70,label:"客户类型编号",sortable : true,align : "left",hidden:true},
	        {name : "area",index : "area",width : 150,label:"面积大于等于",sortable : true,align : "right"},
	        {name : "areato",index : "areato",width : 150,label:"面积小于",sortable : true,align : "right"},
	        {name : "begindate",index : "begindate",width : 150,label:"开始时间",sortable : true,align : "left",formatter: formatTime},
	        {name : "enddate",index : "enddate",width : 150,label:"结束时间",sortable : true,align : "left",formatter: formatTime},
	        {name : "layoutdescr",index : "layoutdescr",width : 50,label:"户型",sortable : true,align : "left"},
	        {name : "lastupdate",index : "lastupdate",width : 150,label:"最后修改时间",sortable : true,align : "left",formatter: formatTime},
	        {name : "lastupdatedby",index : "lastupdatedby",width : 70,label:"修改人",sortable : true,align : "left"},
	        {name : "expired",index : "expired",width : 70,label:"是否过期",sortable : true,align : "left"},
	        {name : "actionlog",index : "actionlog",width : 70,label:"操作日志",sortable : true,align : "left"}
         ],
	});
});

function add(){	
	Global.Dialog.showDialog("upgWithholdAdd",{			
		title:"升级扣项规则-新增",
		url:"${ctx}/admin/upgWithhold/goSave",
		postData:{
			m_umState:"A"
		},
		height: 700,
		width:1000,
		returnFun: goto_query
	});
}

function update(m_umState){
	var ret = selectDataTableRow();
	var title="升级扣项规则--";
	if(m_umState=="M") {
		title=title+"修改"
	}else{
		title=title+"复制"
	}
    if (ret) {    	
	    Global.Dialog.showDialog("upgWithholdUpdate",{
			title:title,
			url:"${ctx}/admin/upgWithhold/goSave",
			postData:{
				m_umState:m_umState,
				code:$.trim(ret.code)
			},
			height:700,
			width:1000,
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
    	Global.Dialog.showDialog("upgWithholdview",{
			title:"升级扣项规则-查看",
			url:"${ctx}/admin/upgWithhold/goSave",
			postData:{
				m_umState:"V",
				code:$.trim(ret.code)
			},
			height:700,
			width:1000,
			returnFun: goto_query
		});
    } else {
   		art.dialog({
			content: "请选择一条记录"
		});
    }
}

function doExcel(){
	var url = "${ctx}/admin/upgWithhold/doExcel";
	doExcelAll(url);
}
</script>
</head>
    
<body>
	<div class="body-box-list">
        <div class="query-form"  >  
	        <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
	        	<input type="hidden" id="expired" name="expired" value="${upgWithhold.expired }"/>
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" name="exportData" id="exportData">
				<ul class="ul-form">
				<li>
					<label>编号</label>
					<input type="text" id="code" name="code"  value="${upgWithhold.code }" />
				</li>
				<li>
					<label>名称</label>
					<input type="text" id="descr" name="descr"  value="${upgWithhold.descr }" />
				</li>
				<li>
					<label>客户类型</label>
					<house:xtdm id="custType" dictCode="CUSTTYPE" style="width:160px;" value=""/>
				</li>
				<li>
					<label>计算类型</label>
					<house:xtdm id="calType" dictCode="UPGCALTYPE" style="width:160px;" value=""/>
				</li>
				<li>
					<label>材料类型1</label>
					<select id="itemType1" name="itemType1"   ></select>
				</li>
				
				<li id="loadMore" >
					<input type="checkbox" id="expired_show" name="expired_show" value="${upgWithhold.expired }" onclick="hideExpired(this)" ${upgWithhold.expired!='T' ?'checked':'' }/>
					<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
					<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
					<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
				</li>
				</ul>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<div class="btn-panel" >
		    	<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " onclick="add()">新增</button>
					<button type="button" class="btn btn-system " onclick="update('M')">编辑</button>
					<button type="button" class="btn btn-system " onclick="update('C')">复制</button>
					<house:authorize authCode="UPGWITHHOLD_VIEW">
						<button type="button" class="btn btn-system " onclick="view()">查看</button>
					</house:authorize>
					<button type="button" class="btn btn-system" onclick="doExcel()">输出到excel</button>
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


