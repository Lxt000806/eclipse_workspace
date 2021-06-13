<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Item列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
/**初始化表格*/
$(function(){

	$("#nameChi").openComponent_worker({
		callBack : function(ret) {
			$("#workerCode").val(ret.Code);
		}
	});
	var postData = $("#page_form").jsonForm();
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/custWorker/goCodeJqGrid",
		postData: postData,
		height:385,
		styleUI: 'Bootstrap', 
		colModel : [
		  {name : "pk",index : "pk",width : 100,label:"安排pk",sortable : true,align : "left"},
		  {name : "workercode",index : "workercode",width : 100,label:"工人编号",sortable : true,align : "left",hidden:true},
		  {name : "workername",index : "workername",width : 100,label:"工人姓名",sortable : true,align : "left"},
		  {name : "custcode",index : "custcode",width : 100,label:"客户编号",sortable : true,align : "left",hidden:true},
		  {name : "address",index : "address",width : 200,label:"楼盘",sortable : true,align : "left"},
		  {name : "worktype12",index : "worktype12",width : 90,label:"工人工种",sortable : true,align : "left",hidden:true},
		  {name : "worktype12descr",index : "worktype12descr",width : 90,label:"工人工种",sortable : true,align : "left"},
        ],
        ondblClickRow:function(rowid,iRow,iCol,e){
			var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
        	Global.Dialog.returnData = selectRow;
        	Global.Dialog.closeDialog();
        }
	});
	
	$("#address").focus();
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
					<input type="hidden" id="expired" name="expired" value="${custWorker.expired}"/>
					<input type="hidden" id="workerCode" name="workerCode" value="${custWorker.workerCode}"/>
						<ul class="ul-form">
							<li> 
								<label >楼盘</label>
								<input type="text" id="address" style="width:160px" name="address"  value="${custWorker.address}" />
							</li>
							<li>
								<label>工种分类12</label>
								<house:dict id="workType12" dictCode="" sql="select code,code+' '+ descr descr from tWorkType12"
								sqlLableKey="descr" sqlValueKey="code"></house:dict>
							</li>	
							<li> 
								<label >工人名称</label>
								<input type="text" id="nameChi" style="width:160px" name="nameChi"  value="${custWorker.workerName}" />
							</li>
						<li id="loadMore">
							<input type="checkbox" id="expired_show" name="expired_show" value="${custWorker.expired}" 
								onclick="hideExpired(this)" ${custWorker.expired!='T' ?'checked':'' }/>
							<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label><!-- 增加隐藏选项--add by zb -->
							<button type="button" class="btn btn-system btn-sm"
								onclick="goto_query();">查询</button>
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


