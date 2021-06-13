<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>CustLoan列表</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#statusDescr").val('');
	$.fn.zTree.getZTreeObj("tree_statusDescr").checkAllNodes(false);
}
function add(){
	Global.Dialog.showDialog("custLoanAdd",{
		  title:"添加贷款信息",
		  url:"${ctx}/admin/custLoan/goSave",
		  height: 600,
		  width:800,
		  returnFun: goto_query
	});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("custLoanUpdate",{
		  title:"修改贷款信息",
		  url:"${ctx}/admin/custLoan/goUpdate?id="+ret.custcode,
		  height:600,
		  width:800,
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
       Global.Dialog.showDialog("custLoanView",{
		  title:"查看贷款信息",
		  url:"${ctx}/admin/custLoan/goDetail?id="+ret.custcode,
		  height:600,
		  width:800
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function importExcel(){	
	Global.Dialog.showDialog("Add",{			
		  title:"贷款信息--从execl导入",
		  url:"${ctx}/admin/custLoan/goLoad",
		  height: 700,
		  width:1200,
		  returnFun: goto_query
	});
}
function del(){
	var ret = selectDataTableRow();
    if (ret) {
    	art.dialog({
    		content:"确认删除该记录",
    		ok:function(){
    			$.ajax({
    				url:"${ctx}/admin/custLoan/doDelete?id="+ret.custcode,
    				type:"post",
    				dataType:"json",
    				cache:false,
    				error:function(obj){
    					art.dialog({
    						content:"删除出错,请重试",							
    						time: 1000,
							beforeunload: function () {
								goto_query();
							}
    					});
    				},
    				success:function(obj){
    					if(obj.rs){
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
    		cancel:function(){
    			goto_query();
    		}
    	});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/custLoan/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
	$("#custCode").openComponent_customer({condition:{status:"3,4,5"}});
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/custLoan/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-75,
		colModel : [
		  {name : 'custcode',index : 'custcode',width : 70,label:'客户编号',sortable : true,align : "left",key : true},
		  {name : 'custdescr',index : 'custdescr',width : 70,label:'客户名称',sortable : true,align : "left"},
		  {name : 'address',index : 'address',width : 120,label:'楼盘',sortable : true,align : "left"},
		  {name : 'statusdescr',index : 'statusdescr',width : 70,label:'客户状态',sortable : true,align : "left"},
		  {name : 'businessmandescr',index : 'businessmandescr',width : 70,label:'业务员',sortable : true,align : "left"},
	      {name : 'designmandescr',index : 'designmandescr',width : 70,label:'设计师 ',sortable : true,align : "left"},
	      {name : 'bank',index : 'bank',width : 100,label:'贷款银行',sortable : true,align : "left"},
	      {name : 'agreedate',index : 'agreedate',width : 92,label:'协议提交日期',sortable : true,align : "left", formatter: formatTime},
	   	  {name : 'amount',index : 'amount',width : 110,label:'贷款总额/万元',sortable : true,align : "left"},
	   	  {name : 'firstamount',index : 'firstamount',width : 110,label:'首次放款金额/万元',sortable : true,align : "left"},
	      {name : 'firstdate',index : 'firstdate',width : 100,label:'首次放款时间',sortable : true,align : "首次放款时间", formatter: formatTime},
	      {name : 'secondamount',index : 'secondamount',width : 110,label:'二次放款金额/万元',sortable : true,align : "left"},
	      {name : 'seconddate',index : 'seconddate',width : 100,label:'二次放款时间',sortable : true,align : "left", formatter: formatTime},
	      {name : 'signremark',index : 'signremark',width : 100,label:'已签约待放款',sortable : true,align : "left"},
	      {name : 'confuseremark',index : 'confuseremark',width : 100,label:'退件拒批',sortable : true,align : "left"},	      
	      {name : 'followremark',index : 'followremark',width : 100,label:'需跟踪',sortable : true,align : "left"},
		  {name : 'remark',index : 'remark',width : 100,label:'备注',sortable : true,align : "left"},
	      {name : 'lastupdate',index : 'lastupdate',width : 100,label:'最后更新时间',sortable : true,align : "left", formatter: formatTime},
	      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'最后更新人',sortable : true,align : "left"},
	      {name : 'expired',index : 'expired',width : 100,label:'是否过期',sortable : true,align : "left"},
	      {name : 'actionlog',index : 'actionlog',width : 100,label:'操作日志',sortable : true,align : "left"}
       ]
	});
});
</script>
</head>

<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>客户编号</label> <input type="text" id="custCode" name="custCode" />
					</li>
					<li><label>客户状态</label> <house:xtdmMulit id="statusDescr" dictCode="CUSTOMERSTATUS"></house:xtdmMulit>
					</li>
					<li class="form-validate"><label>贷款银行</label> <input type="text" id="bank" name="bank" />
					</li>
					<li class="search-group"><input type="checkbox" id="expired" name="expired"
						onclick="hideExpired(this)" ${custLoan.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button></li>
				</ul>
			</form>
		</div>
		<div class="clear_float"></div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="CUSTLOAN_ADD">
					<button type="button" class="btn btn-system " onclick="add()">新增</button>
				</house:authorize>
				<house:authorize authCode="CUSTLOAN_UPDATE">
					<button type="button" class="btn btn-system " onclick="update()">编辑</button>
				</house:authorize>
				<house:authorize authCode="CUSTLOAN_UPDATE">
					<button id="btnDel" type="button" class="btn btn-system " onclick="del()">删除</button>
				</house:authorize>
				<house:authorize authCode="CUSTLOAN_LOADFROMEXCL">
					<button type="button" class="btn btn-system " onclick="importExcel()">从Excel导入</button>
				</house:authorize>
				<house:authorize authCode="	CUSTLOAN_EXCEL">
					<button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>
				</house:authorize>
				<house:authorize authCode="CUSTLOAN_VIEW">
					<button id="btnView" type="button" class="btn btn-system " onclick="view()">查看</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>
