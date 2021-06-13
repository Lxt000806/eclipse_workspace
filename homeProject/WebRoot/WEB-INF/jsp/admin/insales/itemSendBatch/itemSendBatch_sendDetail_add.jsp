<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增发货</title>
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
			$("#whCode").openComponent_wareHouse();
			$("#iaNo").openComponent_itemApp();
			Global.JqGrid.initJqGrid("addSendDetailDataTable",{
				url:"${ctx}/admin/itemAppSend/goSendDetailAddJqGrid",
				postData:{
					nos:"${itemAppSend.nos}",
				},
				height:300,
	        	styleUI: "Bootstrap",
				multiselect: true,
				colModel : [
					{name: "isps", index: "isps", width: 72, label: "是否送货", sortable: true, align: "left",hidden:true},
					{name: "no", index: "no", width: 101, label: "发货单号", sortable: true, align: "left"},
					{name: "iano", index: "iano", width: 100, label: "领料单号", sortable: true, align: "left"},
					{name: "confirmdate", index: "confirmdate", width: 120, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
					{name: "date", index: "date", width: 120, label: "发货日期", sortable: true, align: "left", formatter: formatTime},
					{name: "sendtype", index: "sendtype", width: 110, label: "发货类型", sortable: true, align: "left"},
					{name: "address", index: "address", width: 175, label: "楼盘(盘送货地址)", sortable: true, align: "left"},
					{name: "takeaddress", index: "takeaddress", width: 142, label: "取货地址", sortable: true, align: "left"},
					{name: "supplierdescr", index: "supplierdescr", width: 117, label: "供应商", sortable: true, align: "left"},
					{name: "whdescr", index: "whdescr", width: 122, label: "仓库", sortable: true, align: "left"},
					{name: "itemtype1descr", index: "itemtype1descr", width: 110, label: "材料类型1", sortable: true, align: "left"},
					{name: "itemtype2descr", index: "itemtype2descr", width: 110, label: "材料类型2", sortable: true, align: "left"},
					{name: "carryfee", index: "carryfee", width: 74, label: "搬运费", sortable: true, align: "left", sum: true,hidden:true},
					{name: "sendstatusdescr", index: "sendstatusdescr", width: 72, label: "送货状态", sortable: true, align: "left",hidden:true},
	            ],
	         gridComplete:function (){
				dataTableCheckBox("addSendDetailDataTable","no");
				dataTableCheckBox("addSendDetailDataTable","iano");
				dataTableCheckBox("addSendDetailDataTable","confirmdate");
				dataTableCheckBox("addSendDetailDataTable","date");
				dataTableCheckBox("addSendDetailDataTable","sendtype");
				dataTableCheckBox("addSendDetailDataTable","address");
				dataTableCheckBox("addSendDetailDataTable","takeaddress");
				dataTableCheckBox("addSendDetailDataTable","supplierdescr");
				dataTableCheckBox("addSendDetailDataTable","whdescr");
				dataTableCheckBox("addSendDetailDataTable","itemtype1descr");
				dataTableCheckBox("addSendDetailDataTable","itemtype2descr");
				},
			});
		});
		function save(){
			var ids=$("#addSendDetailDataTable").jqGrid("getGridParam", "selarrrow");
			if(ids.length == 0){
				art.dialog({
					content:"请选择数据后保存"
				});				
				return ;
			}
			$.each(ids,function(i,id){
				var rowData = $("#addSendDetailDataTable").jqGrid("getRowData", id);
				//保存前初始化搬运费为0
				rowData['carryfee']='0';
				rowData['sendstatusdescr']='未指定';
				selectRows.push(rowData);
			});
			var len = ids.length;  
			for(var i = 0;i < len ;i ++) {  
				$("#addSendDetailDataTable").jqGrid("delRowData", ids[0]);  
			} 
			$("#dataTableExists_selectRowAll").val(JSON.stringify(selectRows));
    		art.dialog({content: "添加成功！",width: 200});
    		Global.JqGrid.jqGridSelectAll("addSendDetailDataTable",false);//全不选
		}
		function closeAndReturn(){
			Global.Dialog.returnData = selectRows;
	    	closeWin();
		}
		function query(){
		var ids=$("#addSendDetailDataTable").jqGrid("getGridParam", "selarrrow");
			if(ids.length > 0){
				art.dialog({
					 content:"有已选择记录，未确认更新，是否继续？",
					 lock: true,
					 width: 200,
					 height: 100,
					 ok: function () {
						goto_query('addSendDetailDataTable');
					 },
					 cancel: function () {
							return true;
					 }
				}); 
			}else{
				goto_query('addSendDetailDataTable');
			}
		}
	//回车键搜索
	function keyQuery(){
	 	if (event.keyCode==13)  //回车键的键值为13
	  	$("#s_qr").click(); //调用登录按钮的登录事件
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
					<li><label>领料发货单号</label> <input type="text" id="no" name="no"
						value="${itemAppSend.no}" />
					</li>
					<li><label>领料单号</label> <input type="text" id="iaNo"
						name="iaNo" value="${itemAppSend.iaNo}" />
					</li>
					<li><label>楼盘</label> <input type="text" id="address"
						name="address" value="${itemAppSend.address}" />
					</li>
					<li><label>仓库</label> <input type="text" id="whCode"
						name="whCode" value="${itemAppSend.whCode}" />
					</li>
					<li><label>发货日期从</label> <input type="text" id="dateFrom"
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
					<li><label>发货类型</label> <house:xtdm id="sendType"
							dictCode="ITEMAPPSENDTYPE" value="${itemAppSend.sendType}"></house:xtdm>
					</li>
					<li class="search-group"><input type="checkbox" id="expired"
						name="expired" value="${itemAppSend.expired}"
						onclick="hideExpired(this)" ${itemAppSend.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" id="s_qr" class="btn  btn-sm btn-system "
							onclick="query('addSendDetailDataTable');">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button></li>
				</ul>
			</form>
		</div>
		<div id="content-list">
			<table id="addSendDetailDataTable"></table>
			<div id="addSendDetailDataTablePager"></div>
		</div>
</body>
</html>
