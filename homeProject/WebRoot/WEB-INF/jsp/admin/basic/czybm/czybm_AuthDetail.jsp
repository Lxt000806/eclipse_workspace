<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
String czylb=uc.getCzylb();
String czybh="";
if("2".equals(czylb)) czybh=uc.getCzybh();
%>
<!DOCTYPE html>
<html>
<head>
	<title>WHCHeckOutDetail列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
<script type="text/javascript">

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
}

/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/whCheckOut/goDetailJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
        	styleUI: 'Bootstrap',
			colModel : [
		      {name : 'czybh',index : 'czybh',width : 80,label:'操作员编号',sortable : true,align : "left"},
		      {name : 'emnum',index : 'emnum',width : 80,label:'员工编号',sortable : true,align : "left"},
		      {name : 'zwxm',index : 'zwxm',width : 100,label:'中文姓名',sortable : true,align : "left"},
		      {name : 'custrightdescr',index : 'custrightdescr',width : 100,label:'客户权限',sortable : true,align : "left"},
		      {name : 'costrightdescr',index : 'costrightdescr',width : 100,label:'查看成本',sortable : true,align : "left"},
		      {name : 'itemrightdescr',index : 'itemrightdescr',width : 150,label:'材料权限',sortable : true,align : "left"},
		      {name : 'custtypedescr',index : 'custtypedescr',width : 100,label:'操作客户类型',sortable : true,align : "left"},
		      {name : 'saletypedescr',index : 'saletypedescr',width : 100,label:'销售类型权限',sortable : true,align : "left"},
		      {name : 'department1descr',index : 'department1descr',width : 100,label:'一级部门',sortable : true,align : "left"},
		      {name : 'department2descr',index : 'department2descr',width : 100,label:'二级部门',sortable : true,align : "left"},
		      {name : 'department3descr',index : 'department3descr',width : 100,label:'三级部门',sortable : true,align : "left"},
		      {name : 'leavedate',index : 'leavedate',width : 100,label:'离开日期',sortable : true,align : "left",formatter:formatDate},
		      {name : 'statusdescr',index : 'statusdescr',width : 60,label:'状态',sortable : true,align : "left"},
		      {name : 'isleaddescr',index : 'isleaddescr',width : 70,label:'部门领导',sortable : true,align : "left"},
		      {name : 'positiondescr',index : 'positiondescr',width : 100,label:'职位',sortable : true,align : "left"},
		      {name : 'dhhm',index : 'dhhm',width : 100,label:'电话号码',sortable : true,align : "left"},
		      {name : 'sj',index : 'sj',width : 100,label:'手机',sortable : true,align : "left"},
		      {name : 'khrq',index : 'khrq',width : 100,label:'开户日期',sortable : true,align : "left"},
		      {name : 'jslx',index : 'jslx',width : 100,label:'操作员类型',sortable : true,align : "left"},
		      {name : 'zfbz',index : 'zfbz',width : 100,label:'销户标志',sortable : true,align : "left"},
		      {name : 'czylbdescr',index : 'czylbdescr',width : 100,label:'平台类型',sortable : true,align : "left"},
		      {name : 'isoutuserdescr',index : 'isoutuserdescr',width : 100,label:'是否外网用户',sortable : true,align : "left"},
          	  {name : 'prjroledescr',index : 'prjroledescr',width : 100,label:'工程角色',sortable : true,align : "left"}
            ],
           
		});
		 
});
</script>
</head> 
<body>
	<div class="body-box-list">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
		    		<house:authorize authCode="WHCHECKOUT_EXCEL">
						<button id="whCheckOutDetailExcel" type="button" class="btn btn-system ">输出至excel</button>
					</house:authorize>
					<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
<!-- 		<div class="panel-info" >  
			<div class="panel-body"> -->
		<div>  
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" id="expired" name="expired" value="${whCheckOut.expired}" />
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>记账单号</label>
							<input type="text" id="no" name="no" />
						</li>
						<li>
							<label>申请日期</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>					
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>
							<label>仓库编号</label>
							<input type="text" id="whCode" name="whCode" />
						</li>
						<li>
							<label>记账日期</label>
							<input type="text" id="checkDateFrom" name="checkDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
						</li>
						<li>				
							<label>至</label>
							<input type="text" id="checkDateTo" name="checkDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
						</li>
						<li>
							<label>发货单号</label>
							<input type="text" id="itemAppSendNo" name="itemAppSendNo" />
						</li>
						<li>
							<label>账单状态</label>
							<house:xtdmMulit id="status" dictCode="" sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='WHChkOutStatus' and (CBM='1' or CBM='2'or CBM='3')" selectedValue="1,2,3"></house:xtdmMulit>
						</li>
						<li>
							<label>材料类型1</label>
							<select id="itemType1" name="itemType1"></select>
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" />
						</li>
						<li>
							<label>领料单号</label>
							<input type="text" id="iaNo" name="iaNo" />
						</li>
						<li>
							<label><div class="labeldocumentNo">凭证号</div></label>
							<input type="text" id="documentNo" name="documentNo" value="${whCheckOut.documentNo}" />
						</li>
						<li class="search-group">					
							<input type="checkbox" id="expired_show" name="expired_show"
								value="${whCheckOut.expired}" onclick="hideExpired(this)"
								${whCheckOut.expired!='T' ?'checked':'' }/><p>隐藏过期</p> 
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		
		<div class="clear_float"> </div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</div>
</body>
</html>


