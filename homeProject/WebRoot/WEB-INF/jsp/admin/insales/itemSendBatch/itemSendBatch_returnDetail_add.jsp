<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增退货明细</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_itemApp.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
		var selectRows = [];
		/**初始化表格*/
		$(function(){
			Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1");
			Global.LinkSelect.setSelect({firstSelect:'itemType1',firstValue:'${itemAppSend.itemType1}'}); 
			Global.JqGrid.initJqGrid("addReturnDetailDataTable",{
				url:"${ctx}/admin/itemReturn/goReturnDetailAddJqGrid",
				postData:{
					nos:"${itemReturn.nos}",
				},
				height:300,
	        	styleUI: "Bootstrap",
				multiselect: true,
				colModel : [
					{name: "isps", index: "isps", width: 82, label: "是否配送", sortable: true, align: "left",hidden:true},
					{name: "no", index: "no", width: 114, label: "退货单号", sortable: true, align: "left"},
					{name: "statusdescr", index: "statusdescr", width: 74, label: "退货状态", sortable: true, align: "left",hidden:true},
					{name: "appczydescr", index: "appczydescr", width: 78, label: "申请人", sortable: true, align: "left"},
					{name: "phone", index: "phone", width: 112, label: "电话", sortable: true, align: "left"},
					{name: "date", index: "date", width: 122, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
					{name: "custcode", index: "custcode", width: 105, label: "客户编号", sortable: true, align: "left"},
					{name: "address", index: "address", width: 183, label: "楼盘", sortable: true, align: "left"},
					{name: "itemtype1descr", index: "itemtype1descr", width: 95, label: "材料类型1", sortable: true, align: "left"},
					{name: "remarks", index: "remarks", width: 160, label: "备注", sortable: true, align: "left"}
	            ],
	         gridComplete:function (){
				dataTableCheckBox("addReturnDetailDataTable","no");
				dataTableCheckBox("addReturnDetailDataTable","appczydescr");
				dataTableCheckBox("addReturnDetailDataTable","phone");
				dataTableCheckBox("addReturnDetailDataTable","date");
				dataTableCheckBox("addReturnDetailDataTable","custcode");
				dataTableCheckBox("addReturnDetailDataTable","address");
				dataTableCheckBox("addReturnDetailDataTable","itemtype1descr");
				dataTableCheckBox("addReturnDetailDataTable","remarks");
				},
			});
		});
		function save(){
			var ids=$("#addReturnDetailDataTable").jqGrid("getGridParam", "selarrrow");
			if(ids.length == 0){
				art.dialog({
					content:"请选择数据后保存"
				});				
				return ;
			}
			$.each(ids,function(i,id){
				var rowData = $("#addReturnDetailDataTable").jqGrid("getRowData", id);
				//保存前初始化状态为提交申请
				rowData['status']='2';
				rowData['statusdescr']='提交申请';
				selectRows.push(rowData);
			});
			var len = ids.length;  
			for(var i = 0;i < len ;i ++) {  
				$("#addReturnDetailDataTable").jqGrid("delRowData", ids[0]);  
			} 
			$("#dataTableExists_selectRowAll").val(JSON.stringify(selectRows));
    		art.dialog({content: "添加成功！",width: 200});
    		Global.JqGrid.jqGridSelectAll("addReturnDetailDataTable",false);//全不选
		}
		function closeAndReturn(){
			Global.Dialog.returnData = selectRows;
	    	closeWin();
		}
		function query(){
			var ids=$("#addReturnDetailDataTable").jqGrid("getGridParam", "selarrrow");
			if(ids.length > 0){
				art.dialog({
					 content:"有已选择记录，未确认更新，是否继续？",
					 lock: true,
					 width: 200,
					 height: 100,
					 ok: function () {
						goto_query('addReturnDetailDataTable');
					 },
					 cancel: function () {
							return true;
					 }
				}); 
			}else{
				goto_query('addReturnDetailDataTable');
			}
		}
	//回车键搜索
	function keyQuery(){
	 	if (event.keyCode==13)  //回车键的键值为13
	  	$("#r_qr").click(); //调用登录按钮的登录事件
	}
	</script>
	</head>
<body onkeydown="keyQuery()">
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs" style="float:left">
					<button id="saveBut" type="button" class="btn btn-system"
						onclick="save()">确认新增</button>
					<button id="closeBut" type="button" class="btn btn-system"
						onclick="closeAndReturn()">关闭</button>
				</div>
				<input hidden="true" id="dataTableExists_selectRowAll"
					name="dataTableExists_selectRowAll" value="">
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>退货单号</label> <input type="text" id="no" name="no"
						value="${itemReturn.no}" />
					</li>
					<li><label>楼盘</label> <input type="text" id="address"
						name="address" value="${itemReturn.address}" />
					</li>
					<li><label>申请日期从</label> <input type="text" id="dateFrom"
						name="dateFrom" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>

					<li><label>至</label> <input type="text" id="dateTo"
						name="dateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>材料类型1</label> <select id="itemType1"
						name="itemType1" style="width:160px;"></select>
					</li>
					<li class="search-group"><input type="checkbox" id="expired"
						name="expired" value="${itemReturn.expired}"
						onclick="hideExpired(this)" ${itemReturn.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" id="r_qr" class="btn  btn-sm btn-system "
							onclick="query('addReturnDetailDataTable');">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button></li>
				</ul>
			</form>
		</div>
		<div id="content-list">
			<table id="addReturnDetailDataTable"></table>
			<div id="addReturnDetailDataTablePager"></div>
		</div>
</body>
</html>
