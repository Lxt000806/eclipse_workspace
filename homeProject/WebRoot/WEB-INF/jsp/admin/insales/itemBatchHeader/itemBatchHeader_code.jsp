<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemBatchHeader查询code</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
/**初始化表格*/

$(function(){
		var postData = $("#page_form").jsonForm();
		if('${itemBatchHeader.disabledEle}'){
		var arr='${itemBatchHeader.disabledEle}'.split(",");
			$.each(arr,function(k,v){
				$("#"+v).attr("disabled","disabled");
			})
		}
		var multiselect=false;
		
		if("${itemBatchHeader.isMultiselect}"=="1"){
			multiselect=true;
			$("#buttonView").show();;	
		}else{
			$("#buttonView").hide();
		}
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemBatchHeader/goJqGrid",
			postData: postData,
			height:$(document).height()-$("#content-list").offset().top-70,
			multiselect:multiselect,
			styleUI: 'Bootstrap',
			colModel : [
		      {name : 'no',index : 'no',width : 100,label:'批次编号',sortable : true,align : "left"}
			  ,
		      {name : 'date',index : 'date',width : 150,label:'创建日期',sortable : true,align : "left", formatter: formatTime}
			  ,
		      {name : 'crtczydescr',index : 'crtczydescr',width : 100,label:'创建人员',sortable : false,align : "left"}
			  ,
		      {name : 'itemtype1',index : 'itemtype1',width : 100,label:'材料类型1',sortable : true,align : "left",hidden:true}
			  ,
			  {name : 'itemtype1descr',index : 'itemtype1descr',width : 100,label:'材料类型1',sortable : false,align : "left"}
			  ,
		      {name : 'remarks',index : 'remarks',width : 200,label:'名称',sortable : true,align : "left"}
			  ,
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left", formatter: formatTime}
			  ,
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'修改人',sortable : false,align : "left"}
			  ,
		      {name : 'expired',index : 'expired',width : 100,label:'是否过期',sortable : true,align : "left"}
			  ,
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'操作',sortable : true,align : "left"}
			  ,
		      {name : 'batchtype',index : 'batchtype',width : 100,label:'batchType',sortable : true,align : "left",hidden:true}
			  
            ],
			ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            }
		});
});
function clearForm(){
		$("#page_form input[type='text']").val('');
	    if('${itemBatchHeader.disabledEle}'.indexOf('itemType1')==-1)  $("#page_form select").val('');
}
//确定
function save(){
   	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
   	if(ids.length==0){
   		Global.Dialog.infoDialog("请选择一条或多条记录！");	
   		return;
   	}
   	var selectRows = [];	
	$.each(ids,function(k,id){
		var row = $("#dataTable").jqGrid('getRowData', id);
		selectRows.push(row);
	});
	Global.Dialog.returnData = selectRows;
	Global.Dialog.closeDialog();
 }
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div id="buttonView" class="panel panel-system"  >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
						<button type="button" class="btn btn-system "  id="saveBtn" onclick="save()">确定</button>
						<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
					</div>
				</div>
		</div>
		<div class="query-form">
		  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
			<input type="hidden"  name="itemType1" style="width:160px;" value="${itemBatchHeader.itemType1}" />
			<input type="hidden"  name="batchType" style="width:160px;" value="${itemBatchHeader.batchType}" />
			<input type="hidden"  name="crtCzy" style="width:160px;" value="${itemBatchHeader.crtCzy}" />
			<input type="hidden"  name="isMultiselect" style="width:160px;" value="${itemBatchHeader.isMultiselect}" />
				<ul class="ul-form">
						<li>	
						
							<label>批次编号</label>
							
							<input type="text" id="no" name="no"   value="${itemBatchHeader.no}" />
						</li>
						<li>
							<label>名称</label>
							
							<input type="text" id="remarks" name="remarks"   value="${itemBatchHeader.remarks}" />
						</li>
						<li>	
								<label>材料类型1</label>
							
							<house:DataSelect id="itemType1"
							className="ItemType1" keyColumn="code" valueColumn="descr" value="${itemBatchHeader.itemType1}"></house:DataSelect>
						</li>	
						<li>
							<label>创建日期</label>
						<input type="text" id="dateFrom"
								name="dateFrom" class="i-date" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${itemBatchHeader.dateFrom}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>	
							<label>至</label>
							<input type="text" id="dateTo"
								name="dateTo" class="i-date" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${itemBatchHeader.dateTo}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li id="loadMore" >
								<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
						</li>
						
							
				</ul>
			</form>
		</div>
		
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


