<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>SignIn查询code</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/signIn/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
		      {name : 'PK',index : 'PK',width : 50,label:'PK',sortable : true,align : "left", hidden:true},
		      {name : 'CustCode',index : 'CustCode',width : 70,label:'客户编号',sortable : true,align : "left"},
		      {name : 'Custaddress',index : 'Custaddress',width : 225,label:'楼盘',sortable : true,align : "left"},
		      {name : 'CrtDate',index : 'CrtDate',width : 75,label:'签到日期',sortable : true,align : "left",formatter:formatDate},
		      {name : 'czydescr',index : 'czydescr',width : 80,label:'签到人',sortable : true,align : "left"},
		      {name : 'Department1',index : 'Department1',width : 110,label:'一级部门',sortable : true,align : "left"},
		      {name : 'Department2',index : 'Department2',width : 110,label:'二级部门',sortable : true,align : "left"},
		      {name : 'Address',index : 'Address',width : 300,label:'地址',sortable : true,align : "left"},
		      {name : 'ErrPosi',index : 'ErrPosi',width : 50,label:'位置异常',sortable : true,align : "left"}
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
			<form action="" method="post" id="page_form" >
				<table cellpadding="0" cellspacing="0" width="100%">
						<col  width="82" />
						<col  width="25.4%"/>
						<col  width="82"/>
						<col  width="25.3%"/>
						<col  width="82" />
						<col  width="25.4%"/>
					<tbody>
						<tr>	
							<td class="td-label">客户编号</td>
							<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${signIn.custCode}" />
							</td>
							<td class="td-label">楼盘</td>
							<td class="td-value">
							<input type="text" id="custAddress" name="custAddress" style="width:160px;"  value="${signIn.custAddress}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">签到日期</td>
							<td class="td-value">
							<input type="text" id="crtDate" name="crtDate" style="width:160px;"  value="<fmt:formatDate value='${signIn.crtDate}'/>" />
							</td>
							<td class="td-label">一级部门</td>
							<td class="td-value">
							<input type="text" id="department1" name="department1" style="width:160px;"  value="${signIn.department1}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">位置异常</td>
							<td class="td-value">
							<house:xtdm id="errPosi" dictCode="YESNO" value="${signIn.errPosi }"></house:xtdm>
							</td>
							<td class="td-label">二级部门</td>
							<td class="td-value">
							<input type="text" id="department2" name="department2" style="width:160px;"  value="${signIn.department2}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">地址</td>
							<td class="td-value" colspan='3'>
							<input type="text" id="address" name="address" style="width:800px;"  value="${signIn.address}" />
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
