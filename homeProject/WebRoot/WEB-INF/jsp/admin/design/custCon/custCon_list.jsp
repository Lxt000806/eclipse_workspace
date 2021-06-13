<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>CustCon列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	$("#department").val('');
	$.fn.zTree.getZTreeObj("tree_department").checkAllNodes(false);
	
}
function add(){
	Global.Dialog.showDialog("custConAdd",{
		  title:"添加客户接触跟踪信息",
		  url:"${ctx}/admin/custCon/goSave",
		  height: 600,
		  width:1000,
		  resizable:false, 
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("custConUpdate",{
		  title:"修改客户接触跟踪信息",
		  url:"${ctx}/admin/custCon/goUpdate?id="+ret.pk,
		  height:600,
		  width:1000,
		  resizable:false, 
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function copy(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("custConCopy",{
		  title:"复制客户接触跟踪信息",
		  url:"${ctx}/admin/custCon/goSave?id="+ret.pk,
		  height:600,
		  width:1000,
		  resizable:false, 
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
      Global.Dialog.showDialog("custConView",{
		  title:"查看客户接触跟踪信息",
		  url:"${ctx}/admin/custCon/goDetail?id="+ret.pk,
		  height:600,
		  width:1000,
		  resizable:false, 
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var url = "${ctx}/admin/custCon/doDelete";
	beforeDel(url);
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/custCon/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
	var postData = $("#page_form").jsonForm();
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/custCon/goBsJqGrid",
			postData:postData,
			styleUI: 'Bootstrap',
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
				{name : 'pk',index : 'pk',width : 100,label:'pk',sortable : true,align : "left",key : true,hidden:true},
				{name : 'typedescr',index : 'typedescr',width : 100,label:'跟踪类型',sortable : true,align : "left"},
				{name : 'custcode',index : 'custcode',width : 100,label:'客户编号',sortable : true,align : "left"},
				{name : 'descr',index : 'descr',width : 100,label:'客户名称',sortable : true,align : "left"},
				{name : 'custaddress',index : 'custaddress',width : 200,label:'楼盘',sortable : true,align : "left"},
				{name : 'statusdescr',index : 'statusdescr',width : 100,label:'意向客户状态',sortable : true,align : "left"},
				{name : 'condate',index : 'condate',width : 150,label:'联系时间',sortable : true,align : "left",formatter: formatTime},
				{name : 'conmandescr',index : 'conmandescr',width : 100,label:'联系人',sortable : true,align : "left"} ,
				{name : 'nextcondate',index : 'nextcondate',width : 140,label:'下次联系日期',sortable : true,align : "left",formatter: formatDate} ,
				{name : 'conway',index : 'conway',width : 100,label:'跟踪方式',sortable : true,align : "left",hidden:true} ,
				{name : 'conwaydescr',index : 'conwaydescr',width : 100,label:'跟踪方式',sortable : true,align : "left"} ,
				{name : 'conduration',index : 'conduration',width : 100,label:'跟踪时长（秒）',sortable : true,align : "right"} ,
				{name : 'remarks',index : 'remarks',width : 150,label:'联系内容',sortable : true,align : "left"},
				{name : 'designmandescr',index : 'designmandescr',width : 100,label:'设计师',sortable : true,align : "left"}  ,
				{name : 'businessmandescr',index : 'businessmandescr',width : 100,label:'业务员',sortable : true,align : "left"} ,
				{name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime} ,
				{name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'修改人',sortable : true,align : "left"},
				{name : 'expired',index : 'expired',width : 100,label:'是否过期',sortable : true,align : "left"},
				{name : 'actionlog',index : 'actionlog',width : 100,label:'操作',sortable : true,align : "left"}
            ]
		});
		$("#conMan").openComponent_employee();
		$("#designMan").openComponent_employee();
		$("#businessMan").openComponent_employee();
		$("#custCode").openComponent_customer();
});

function changeMethod(){
	var method=$("#statistcsMethod").val();
	if(method=="1"){
		$("#status,#status_NAME,#designMan").val("");
		$("#status,#designMan").parent().addClass("hidden");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	}else{
		$("#status,#designMan").parent().removeClass("hidden");
	}
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
								<label>楼盘</label>
								<input type="text" id="custAddress" name="custAddress"  />
							</li>
							<li>
							<label>联系人 </label>
							<input type="text" id="conMan" name="conMan"  />
							</li>
							<li>
								<label>部门</label>
								<house:MulitSelectDepatment id="department" appendAllDom="true"></house:MulitSelectDepatment>
							</li>
							<li>
								<label>联系时间从</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${dateFrom}' pattern='yyyy-MM-dd'/>"
								 />
							</li>
							<li>
								<label>到</label>
								<input type="text" id="dateTo" name="dateTo" class="i-date" 
									onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
									value="<fmt:formatDate value='${dateTo}' pattern='yyyy-MM-dd'/>"
								/>
							</li>
							<li>
							<label>跟踪类型</label>
							<house:xtdm id="type" dictCode="CUSTCONTYPE" ></house:xtdm>
							</li>
							<li>		
							<label>统计方式</label>
								<select id="statistcsMethod" name="statistcsMethod" onchange="changeMethod()">
									<option value="0" selected>全部</option>
									<option value="1">资源客户</option>
									<option value="2">意向客户</option>
								</select>
							</li>
							<li>
							<label>意向客户状态</label>
							<house:xtdmMulit id="status" dictCode="CUSTOMERSTATUS" ></house:xtdmMulit>
							</li>
							<li>
							<label>业务员编号 </label>
							<input type="text" id="businessMan" name="businessMan"  />
							</li>
							<li>
								<label>设计师编号</label>
								<input type="text" id="designMan" name="designMan" />
							</li>
							<li class="search-group" >
								<input type="checkbox"  id="expired" name="expired"  value="${custCon.expired=='T'?'T':'F'}" onclick="changeExpired(this)" ${custCon.expired=='T'?'':'checked' }/><p>隐藏过期</p>
								<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
							</li>
					
						</ul>
					
		            </form>
                </div>  
     <div class="btn-panel" >
   
      <div class="btn-group-sm"  >
    <house:authorize authCode="CUSTCON_ADD">
      <button type="button" class="btn btn-system " onclick="add()">添加</button>
      </house:authorize>
     <house:authorize authCode="CUSTCON_COPY">
      <button type="button" class="btn btn-system "  onclick="copy()">复制</button>
     </house:authorize>
     <house:authorize authCode="CUSTCON_UPDATE">
      <button type="button" class="btn btn-system " onclick="update()">修改</button>
       </house:authorize>
      <house:authorize authCode="CUSTCON_VIEW">
      <button type="button" class="btn btn-system "  onclick="view()">查看</button>
      </house:authorize>
        <house:authorize authCode="CUSTCON_EXCEL">
      <button type="button" class="btn btn-system "  onclick="doExcel()">导出excel</button>
      </house:authorize>
      </div>
       
  
   
	</div>
       <div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
      
		</div>
</body>
</html>


