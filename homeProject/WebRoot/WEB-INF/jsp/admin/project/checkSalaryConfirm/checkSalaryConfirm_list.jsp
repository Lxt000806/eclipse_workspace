<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>

<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>结算工资发放</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/checkSalaryConfirm/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function(){
        //初始化表格
        var postData = $("#page_form").jsonForm();
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/checkSalaryConfirm/goJqGrid",
			postData:postData,
            styleUI: 'Bootstrap',   
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
				{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
			  	{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
			  	{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
			  	{name: "area", index: "area", width: 70, label: "面积", sortable: true, align: "right"},
			  	{name: "projectmandescr", index: "projectmandescr", width: 109, label: "项目经理", sortable: true, align: "left"},
				{name: "prjdeptdescr", index: "prjdeptdescr", width: 109, label: "工程部", sortable: true, align: "left"},
				{name: "appdate", index: "appdate", width: 140, label: "结算申请日期", sortable: true, align: "left",formatter: formatTime},
				{name: "custcheckdate", index: "custcheckdate", width: 100, label: "客户结算日期", sortable: true, align: "left",formatter: formatDate},
				{name: "issalaryconfirm", index: "issalaryconfirm", width: 80, label: "是否确认", sortable: true, align: "left",hidden: true},
				{name: "issalaryconfirmdescr", index: "issalaryconfirmdescr", width: 80, label: "是否确认", sortable: true, align: "left"},
				{name: "salaryconfirmczy", index: "salaryconfirmczy", width: 80, label: "工资确认人", sortable: true, align: "left",hidden:true},
				{name: "salaryconfirmczydescr", index: "salaryconfirmczydescr", width: 80, label: "工资确认人", sortable: true, align: "left"},
				{name: "salaryconfirmdate", index: "salaryconfirmdate", width: 140, label: "工资确认日期", sortable: true, align: "left",formatter: formatTime},
            ],
            ondblClickRow:function(rowid,iRow,iCol,e){	
				view();
            }
		});
	});  
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#page_form select").val("");
		$("#custType").val('');
		$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
	} 
	function confirm(){	
		var ret = selectDataTableRow();		
		if (ret) {	
			if(ret.issalaryconfirm=="1"){
				art.dialog({
					content: "工资已确认，无须重复确认！"
				});
				return;
			}	
			Global.Dialog.showDialog("checkSalaryConfirmConfirm",{			
				title:"结算工资发放确认--确认",
				url:"${ctx}/admin/checkSalaryConfirm/goConfirm",
				postData:{
					custCode:ret.custcode,
					address:ret.address,
					salaryConfirmCZY:ret.salaryconfirmczy,
					salaryConfirmCZYDescr:ret.salaryconfirmczydescr,
					isSalaryConfirm:ret.issalaryconfirm,
					salaryConfirmDate:ret.salaryconfirmdate,
					m_umState:'C'
				},
				height: 650,
				width:800,
				returnFun: goto_query
			});
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	function view(){	
		var ret = selectDataTableRow();		
		if (ret) {			
			Global.Dialog.showDialog("checkSalaryConfirmConfirm",{			
				title:"结算工资发放确认--查看",
				url:"${ctx}/admin/checkSalaryConfirm/goView",
				postData:{
					custCode:ret.custcode,
					address:ret.address,
					salaryConfirmCZY:ret.salaryconfirmczy,
					salaryConfirmCZYDescr:ret.salaryconfirmczydescr,
					isSalaryConfirm:ret.issalaryconfirm,
					salaryConfirmDate:ret.salaryconfirmdate,
					m_umState:'V'
				},
				height: 650,
				width:800,
			});
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
	}
	</script>
</head>
<body>
	<div class="body-box-list">
        <div class="query-form"  >  
	        <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label >客户结算日期从</label>
						<input type="text" id="custCheckDateFrom" name="custCheckDateFrom" class="i-date"  
						       onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label >至</label>
						<input type="text" id="custCheckDateTo" name="custCheckDateTo" class="i-date"  
						       onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label >确认日期从</label>
						<input type="text" id="salaryConfirmDateFrom" name="salaryConfirmDateFrom" class="i-date"  
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${custCheck.salaryConfirmDateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label >至</label>
						<input type="text" id="salaryConfirmDateTo" name="salaryConfirmDateTo" class="i-date"  
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${custCheck.dateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" />
					</li>
					<li>
						<label>是否确认</label>
						<house:xtdm id="isSalaryConfirm" dictCode="YESNO" value="0"></house:xtdm>
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType"  selectedValue="${custCheck.custType}"></house:custTypeMulit>
					</li>
					<li>
						<label>工程部</label>
						<house:DictMulitSelect id="prjDepartment2" dictCode="" sql="select a.Code, a.desc1+' '+isnull(e.NameChi,'') desc1  from dbo.tDepartment2 a
								left join tEmployee e on e.Department2=a.Code and e.IsLead='1' and e.LeadLevel='1' and e.expired='F'
								 where  a.deptype='3' and a.Expired='F' order By dispSeq " 
						sqlLableKey="desc1" sqlValueKey="code"   ></house:DictMulitSelect>
					</li>
					<li >																	
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
					<house:authorize authCode="CHECKSALARYCONFIRM_CONFIRM">
						<button type="button" class="btn btn-system " onclick="confirm()">确认</button>
					</house:authorize>
					<house:authorize authCode="CHECKSALARYCONFIRM_VIEW">
						<button type="button" class="btn btn-system " onclick="view()">查看</button>
					</house:authorize>
					<house:authorize authCode="CHECKSALARYCONFIRM_EXCEL">
					<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
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


