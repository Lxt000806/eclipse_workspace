<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>人工费用管理导入Excel</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_driver.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
		var selectRows = [];
		/**初始化表格*/
		$(function(){
			$("#driver").openComponent_driver();
			Global.JqGrid.initJqGrid("addDetailDataTable",{
				url:"${ctx}/admin/laborFee/goSendFeeJqGrid",
				postData:{
					nos:$("#nos").val(),no:"${laborFee.no}",itemType1:"${laborFee.itemType1}"
				},
				height:330,
				multiselect: true,
				colModel : [
					{name: "address", index: "address", width: 100, label: "楼盘", sortable: true, align: "left"},
					{name: "regiondescr", index: "regiondescr", width: 200, label: "片区", sortable: true, align: "left", hidden: true},
					{name: "appsendno", index: "appsendno", width: 100, label: "发货单号", sortable: true, align: "left"},
					{name: "iano", index: "iano", width: 90, label: "领料单号", sortable: true, align: "left"},
					{name: "senddate", index: "senddate", width: 90, label: "发货日期", sortable: true, align: "left", formatter: formatDate},
					{name: "transfee", index: "transfee", width: 85, label: "车费", sortable: true, align: "right",sorttype:"number"},
					{name: "carryfee", index: "carryfee", width: 85, label: "搬运费", sortable: true, align: "right",sorttype:"number"},
					{name: "sendfee", index: "sendfee", width: 85, label: "配送费", sortable: true, align: "right",sorttype:"number"},
					{name: "transfeeadj", index: "transfeeadj", width: 85, label: "车费补贴", sortable: true, align: "right",sorttype:"number"},
					{name: "manysendremarks", index: "manysendremarks", width: 150, label: "多次配送说明", sortable: true, align: "left"},
					{name: "custcode", index: "custcode", width: 65, label: "客户编号", sortable: true, align: "left",hidden:true},
					{name: "documentno", index: "documentno", width: 60, label: "档案号", sortable: true, align: "left",hidden:true},
					{name: "checkstatusdescr", index: "checkstatusdescr", width: 80, label: "客户结算状态", sortable: true, align: "left",hidden:true},
					{name: "custcheckdate", index: "custcheckdate", width: 110, label: "客户结算日期", sortable: true, align: "left", formatter: formatTime, hidden: true},
					{name: "feetypedescr", index: "feetypedescr", width: 75, label: "费用类型", sortable: true, align: "left",hidden:true},
					{name: "feetype", index: "feetype", width: 75, label: "费用类型", sortable: true, align: "left",hidden:true},
					{name: "issetitem", index: "issetitem", width: 75, label: "是否套餐", sortable: true, align: "left",hidden:true},
					{name: "issetitemdescr", index: "issetitemdescr", width: 75, label: "是否套餐", sortable: true, align: "left",hidden:true},
					{name: "amount", index: "amount", width: 65, label: "金额", sortable: true, align: "right", sum: true,hidden:true},
					{name: "amount1", index: "amount1", width: 60, label: "配送费", sortable: true, align: "right",hidden:true},
					{name: "amount2", index: "amount2", width:65, label: "配送费（自动生成）", sortable: true, align: "right",hidden:true},
					{name: "hadamount", index: "hadamount", width: 75, label: "已报销金额", sortable: true, align: "right", sum: true,hidden:true},
					{name: "sendnohaveamount", index: "sendnohaveamount", width: 75, label: "本单已报销", sortable: true, align: "right",hidden:true},
					{name: "actionlog", index: "actionlog", width: 68, label: "操作标志", sortable: true, align: "left",hidden:true},
					{name: "expired", index: "expired", width: 65, label: "是否过期", sortable: true, align: "left", hidden: true},
					{name: "refcustcode", index: "refcustcode", width: 65, label: "关联客户", sortable: true, align: "left", hidden: true}
	            ],
			});
			//全选
			$("#selAll").on("click",function(){
				Global.JqGrid.jqGridSelectAll("addDetailDataTable",true);
			});
			//全不选
			$("#selNone").on("click",function(){
				Global.JqGrid.jqGridSelectAll("addDetailDataTable",false);
			});
			//改写窗口右上角关闭按钮事件
			replaceCloseEvt("sendFeeImport", closeAndReturn);
		});
		function save(){
			var ids=$("#addDetailDataTable").jqGrid("getGridParam", "selarrrow");
			var nos=$("#nos").val();
			if(ids.length == 0){
				art.dialog({
					content:"请选择数据后保存"
				});				
				return ;
			}
			$.each(ids,function(i,id){
				var rowData = $("#addDetailDataTable").jqGrid("getRowData", id);
				selectRows.push(rowData);
				if(nos != ""){
					nos += ",";
				}
				nos += rowData.appsendno;
			}); 
			$("#nos").val(nos);
    		art.dialog({content: "添加成功！",width: 200,time:800});
    		doQuery();
		}
		function closeAndReturn(){
			Global.Dialog.returnData = selectRows;
	    	closeWin();
		}
		function tableSelected(flag){
			selectAll("addDetailDataTable", flag);
		}
		function doQuery(){
			$("#addDetailDataTable").jqGrid("setGridParam",{
				datatype:"json",
	    		postData:$("#page_form").jsonForm(),
	    		page:1,
	    		sortname:''
    		}).trigger("reloadGrid");
		}
	</script>
	</head>
	    
	<body>
		<div class="body-box-list">
	 		<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs" style="float:left">
	    				<button id="saveBut" type="button" class="btn btn-system" onclick="save()">保存</button>
	    				<button id="selAll" type="button" class="btn btn-system" >全选</button>
	    				<button id="selNone" type="button" class="btn btn-system" >全不选</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeAndReturn()">关闭</button>
					</div>
					<input hidden="true" id="dataTableExists_selectRowAll"
							name="dataTableExists_selectRowAll" value="">
				</div>
			</div> 
			<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
					<input hidden="true" id="nos"
							name="nos" value="${laborFee.nos}">
					<ul class="ul-form">
					<li><label>司机</label> <input type="text" id="driver"
						name="driver" style="width:160px;" />
					</li>
					<li>
						<label>批次生成日期从 </label>
						<input type="text" style="width:160px;" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" style="width:160px;" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li class="search-group" >
							<button type="button" class="btn btn-sm btn-system" onclick="doQuery()">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
					</ul>
			</form>
		</div>
			<table id="addDetailDataTable"></table>
			<div id="addDetailDataTablePager"></div>
		</div>
	</body>
</html>
