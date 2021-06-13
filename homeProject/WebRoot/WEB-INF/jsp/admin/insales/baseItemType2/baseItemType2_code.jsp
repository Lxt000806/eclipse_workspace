<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>BaseItemType2查询code</title>
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
			url:"${ctx}/admin/baseItemType2/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
		      {name : 'Code',index : 'Code',width : 100,label:'code',sortable : true,align : "left"}
			  ,
		      {name : 'Descr',index : 'Descr',width : 100,label:'descr',sortable : true,align : "left"}
			  ,
		      {name : 'BaseItemType1',index : 'BaseItemType1',width : 100,label:'baseItemType1',sortable : true,align : "left"}
			  ,
		      {name : 'DispSeq',index : 'DispSeq',width : 100,label:'dispSeq',sortable : true,align : "left"}
			  ,
		      {name : 'LastUpdate',index : 'LastUpdate',width : 100,label:'lastUpdate',sortable : true,align : "left"}
			  ,
		      {name : 'LastUpdatedBy',index : 'LastUpdatedBy',width : 100,label:'lastUpdatedBy',sortable : true,align : "left"}
			  ,
		      {name : 'Expired',index : 'Expired',width : 100,label:'expired',sortable : true,align : "left"}
			  ,
		      {name : 'ActionLog',index : 'ActionLog',width : 100,label:'actionLog',sortable : true,align : "left"}
			  ,
		      {name : 'OfferWorkType2',index : 'OfferWorkType2',width : 100,label:'offerWorkType2',sortable : true,align : "left"}
			  ,
		      {name : 'MaterWorkType2',index : 'MaterWorkType2',width : 100,label:'materWorkType2',sortable : true,align : "left"}
			  
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
						<tr class="td-btn">	
							<td class="td-label">code</td>
							<td class="td-value">
							<input type="text" id="code" name="code" style="width:160px;"  value="${baseItemType2.code}" />
							</td>
							<td class="td-label">descr</td>
							<td class="td-value">
							<input type="text" id="descr" name="descr" style="width:160px;"  value="${baseItemType2.descr}" />
							</td>
							<td class="td-label">baseItemType1</td>
							<td class="td-value">
							<input type="text" id="baseItemType1" name="baseItemType1" style="width:160px;"  value="${baseItemType2.baseItemType1}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">dispSeq</td>
							<td class="td-value">
							<input type="text" id="dispSeq" name="dispSeq" style="width:160px;"  value="${baseItemType2.dispSeq}" />
							</td>
							<td class="td-label">lastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${baseItemType2.lastUpdate}" />
							</td>
							<td class="td-label">lastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${baseItemType2.lastUpdatedBy}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${baseItemType2.expired}" />
							</td>
							<td class="td-label">actionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${baseItemType2.actionLog}" />
							</td>
							<td class="td-label">offerWorkType2</td>
							<td class="td-value">
							<input type="text" id="offerWorkType2" name="offerWorkType2" style="width:160px;"  value="${baseItemType2.offerWorkType2}" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">materWorkType2</td>
							<td class="td-value" colspan="5">
							<input type="text" id="materWorkType2" name="materWorkType2" style="width:160px;"  value="${baseItemType2.materWorkType2}" />
							</td>
						</tr>
						<tr>
							<td colspan="6" class="td-btn">
								<div class="sch_qk_con"> 
									<input onclick="goto_query();"  class="i-btn-s" type="button"  value="检索"/>
									<input onclick="clearForm();"  class="i-btn-s" type="button"  value="清空"/>
								</div>
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


