<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>施工任务监控</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
      .SelectBG{
          background-color:#FF7575!important;
            }    
 	</style>
<script type="text/javascript">
function Complete(){
	var ret = selectDataTableRow();
	var status=ret.rcvstatus;
	if(status!=0){
	art.dialog({content: "只能对接收状态为未读的信息操作!",width: 200});
		return false;
	}
    if (ret) {    	
      Global.Dialog.showDialog("goComplete",{
		  title:"施工任务监控完成",
		  url:"${ctx}/admin/prjMsg/goComplete?id="+ret.pk,
		  height:600,
		  width:1000,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function update(){
	var ret = selectDataTableRow();
	var status=ret.rcvstatus;
	if(status!=0){
	art.dialog({content: "只能对接收状态为未读的信息操作!",width: 200});
		return false;
	}
    if (ret) {    	
      Global.Dialog.showDialog("goUpSendDate",{
		  title:"施工任务监控延后执行",
		  url:"${ctx}/admin/prjMsg/goUpSendDate?id="+ret.pk,
		  height:600,
		  width:1000,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
$(function() {
	$("#dataForm").validate({
		rules: {
			"desc2": {
				validIllegalChar: true,
				maxlength: 60,
				required: true
			},
			"department2": {
				validIllegalChar: true,
				required: true
			}
		}
	});
});
function view(){
	var ret = selectDataTableRow();
	var status=ret.rcvstatus;

    if (ret) {    	
      Global.Dialog.showDialog("goUpSendDate",{
		  title:"查看施工任务监控",
		  url:"${ctx}/admin/prjMsg/goView?id="+ret.pk,
		  height:600,
		  width:1000,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function del(){
	var url = "${ctx}/admin/itemType12/doDelete";
	beforeDel(url,"code");	
}

 function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#openComponent_employee_rcvCzy").val('');
	$("#page_form select").val('');
	$("#department2").val('');
	$("#address").val('');
	$("#deal").val('');
	$("#RcvStatus").val('');
	$("#sendDate").val('');
	$("#sendDate1").val('');
	$("#rcvCzy").val('');
	$.fn.zTree.getZTreeObj("tree_RcvStatus").checkAllNodes(false);
	
} 
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#address").val('');
	$("#rcvCzy").val('');
	$("#Department2").val('');
	$("#deal").val('');
	$("#rcvStatus").val('');
	$.fn.zTree.getZTreeObj("tree_rcvStatus").checkAllNodes(false);
	$("#custType").val('');
	$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
	$("#prjItem").val('');
	$.fn.zTree.getZTreeObj("tree_prjItem").checkAllNodes(false);
} 

/**初始化表格*/
$(function(){
	var pageNo = 1;
	var rowNum = 1;
	var records = 0;
	var oldSelRowNum = 0;
	var chgPage = true;
	window.goto_query = function(flag){
		if(flag==true){//flag =true  点击查询按钮 恢复到第一页
			 pageNo = 1;
			 rowNum = 1;
			 records = 0;
			 oldSelRowNum = 0;
		}else{
			chgPage = false;
			oldSelRowNum = $("#dataTable").jqGrid('getGridParam','selrow');
			pageNo = $("#dataTable").jqGrid()[0].p.page;
		}
		$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:pageNo}).trigger("reloadGrid");
		
	}
    //初始化表格
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemType", "itemType1");
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjMsg/goJqGrid",
		postData:{rcvStatus:"0",sendDate1:"1970-01-01",Department1:"${personMessage.department1}"},
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',
		colModel : [
		  		  {name : 'pk',index : 'pk',width : 120,label:'pk',sortable : true,align : "left",formatter:cutStr,hidden:true},	
				  {name : 'custcode',index : 'custcode',width : 120,label:'custcode',sortable : true,align : "left",hidden:true},	
		  {name : 'address',index : 'address',width : 160,label:'楼盘',sortable : true,align : "left",formatter:cutStr},	
		  {name : 'prjitemdescr',index : 'prjitemdescr',width : 120,label:'触发节点',sortable : true,align : "left",formatter:cutStr},
		  {name : 'senddate',index : 'senddate',width : 90,label:'触发时间',sortable : true,align : "left",formatter: formatDate},
		  {name : 'rcvdate',index : 'rcvdate',width : 90,label:'处理日期',sortable : true,align : "left",formatter: formatDate},
		  {name : 'plandealdate',index : 'plandealdate',width : 100,label:'计划处理时间',sortable : true,align : "left",formatter: formatDate},
		  {name : 'planarrdate',index : 'planarrdate',width : 100,label:'计划完成时间',sortable : true,align : "left",formatter: formatDate},
		  {name : 'title',index : 'title',width : 120,label:'任务标题',sortable : true,align : "left",formatter:cutStr},
		  {name : 'msgtext',index : 'msgtext',width : 280,label:'任务描述',sortable : true,align : "left"},
		  {name : 'delayremarks',index : 'delayremarks',width : 280,label:'延后原因',sortable : true,align : "left"},
		  {name : 'brcvczy',index : 'brcvczy',width : 80,label:'执行人',sortable : true,align : "left",formatter:cutStr},
		  {name : 'department2descr',index : 'department2descr',width : 80,label:'二级部门',sortable : true,align : "left",formatter:cutStr},
		  {name : 'brcvstatus',index : 'brcvstatus',width : 70,label:'接收状态',sortable : true,align : "left",formatter:cutStr},
		  {name : 'rcvstatus',index : 'rcvstatus',width : 70,label:'接收状态',sortable : true,align : "left",formatter:cutStr,hidden:true},
		  {name : 'deal',index : 'deal',width : 70,label:'是否超时',sortable : true,align : "left",formatter:cutStr},
	      {name : 'itemtype1descr',index : 'itemtype1descr',width : 73,label:'材料类型1',sortable : true,align : "left",frozen: true},
	      {name : 'prjmandescr',index : 'prjmandescr',width : 73,label:'项目经理',sortable : true,align : "left",frozen: true},
	      {name : 'pajmandept2',index : 'pajmandept2',width : 73,label:'工程部',sortable : true,align : "left",frozen: true},
	      {name : 'prjmanphone',index : 'prjmanphone',width : 100,label:'项目经理手机',sortable : true,align : "left",frozen: true},
	      {name : 'itemtype2',index : 'itemtype2',width : 70,label:'材料类型2编号',sortable : true,align : "left",frozen: true,hidden:true},
           ],
			gridComplete:function(){
           		var ids = $("#dataTable").getDataIDs();
           	 	for(var i=0;i<ids.length;i++){
					var rowData = $("#dataTable").getRowData(ids[i]);
					if(rowData.deal=="是"){
						$('#'+ids[i]).find("td").addClass("SelectBG");
					}                   
           	 	}
           	 	if(!chgPage){
	           		$("#dataTable").jqGrid('setSelection', oldSelRowNum, true);
	           		chgPage = true;
           	 	}
        	}
	});
	$("#rcvCzy").openComponent_employee({showValue:"${personMessage.rcvCzy}"});
});  

function changeSysTrigger(obj){
	if ($(obj).is(':checked')){
		$('#sysTrigger').val('1');
	}else{
		$('#sysTrigger').val('0');
	}
	console.log($('#sysTrigger').val())
}  

function changeBefTaskCompleted(obj){
	if ($(obj).is(':checked')){
		$('#befTaskCompleted').val('1');
	}else{
		$('#befTaskCompleted').val('0');
	}
	console.log($('#befTaskCompleted').val())

}  

function prjProgView(){
	var ret = selectDataTableRow();
	if (ret) {	
    	Global.Dialog.showDialog("prjProgView",{
			title:"查看客户工程进度",
			url:"${ctx}/admin/prjMsg/goPrjProgView",
			postData:{code:ret.custcode,pk:ret.pk},
			width:1100,
			height:715,
		});
  	} else {
  		art.dialog({
			content: "请选择一条记录"
		});
  	}
} 

//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/prjMsg/doExcel";
	doExcelAll(url);
}
</script>
</head>
<body >
	<div class="body-box-list">
        <div class="query-form"  >  
	        <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
				<li>
					<label>一级部门</label>
					<house:department1 id="department1"  onchange="changeDepartment1(this,'department2','${ctx }')"></house:department1>
				</li>
				<li>
					<label>二级部门</label>
					<house:department2 id="department2" dictCode="${personMessage.department1 }" ></house:department2>
				</li>
				<li>
					<label>工程部</label>
					<house:department2 id="prjDept" dictCode=""  depType="3"  ></house:department2>
				</li>	
				<li>
					<label>执行人</label>
					<input type="text" id="rcvCzy" name="rcvCzy"  value="${personMessage.rcvCzy}" />
				</li>
				<li>
					<label>楼盘</label>
					<input type="text"  id="address" name="address" value="${personMessage.address}" />
				</li>
				<li>	
					<label >是否超时</label>
						<house:xtdm  id="deal" dictCode="YESNO"    value="${personMessage.deal}" ></house:xtdm>
				</li>
				<li>	
					<label>状态</label>
						<house:xtdmMulit id="rcvStatus" dictCode="PERSRCVSTATUS"   selectedValue="0"></house:xtdmMulit>
				</li>
				<li>	
					<label>触发时间从</label>
					<input type="text" id="sendDate" name="sendDate" class="i-date"  
					 onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
				</li>
				<li>	
					<label>至</label>
					<input type="text" id="sendDate1"
						name="sendDate1" class="i-date" 
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${personMessage.sendDate}' pattern='yyyy-MM-dd'/>" />
				</li>
				<li>
					<label>材料类型1</label>
					<select id="itemType1" name="itemType1"></select>			
				</li>
				<li>
					<label>任务标题</label>
					<input type="text"  id=title name="title"/>
				</li>
				<li>	
					<label >前置任务完成</label>
					<house:xtdm  id="befTaskCompleted" dictCode="YESNO"></house:xtdm>
				</li>
				<li>
					<label>客户类型</label>
					<house:custTypeMulit id="custType" ></house:custTypeMulit>
				</li>
				<li>
					<label>触发节点</label>
					<house:DictMulitSelect id="prjItem" dictCode="" 
					sql="select a.Code,a.Descr from tPrjItem1 a  " 
					sqlValueKey="Code" sqlLableKey="Descr"  ></house:DictMulitSelect>
				</li>
				<li style="width:150px">	
					<input type="checkbox" id="sysTrigger" name="sysTrigger" value="1" onclick="changeSysTrigger(this)" checked/>包含自动触发任务&nbsp;
				</li>
				<li id="loadMore" >
					<button type="button" class="btn  btn-system btn-sm" onclick="goto_query(true);"  >查询</button>
					<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
				</li>
				</ul>
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel" >
		    	<div class="btn-group-sm"  >
					<house:authorize authCode="PRJMSG_COMPLETE">
						<button type="button" class="btn btn-system " onclick="Complete()">完成</button>
					</house:authorize>
					<house:authorize authCode="PRJMSG_UPSENDDATE">
						<button type="button" class="btn btn-system "  onclick="update()">延后执行</button>
					</house:authorize>					
					<house:authorize authCode="PRJMSG_VIEW">
						<button type="button" class="btn btn-system "  onclick="view()">查看</button>
					</house:authorize>
					<house:authorize authCode="PRJMSG_PRJPROGVIEW">
						<button type="button" class="btn btn-system "  onclick="prjProgView()">查看工程进度</button>
					</house:authorize>
					<house:authorize authCode="PRJMSG_EXCEL">
						<button type="button" class="btn btn-system "  onclick="doExcel()">输出到Excel</button>
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


