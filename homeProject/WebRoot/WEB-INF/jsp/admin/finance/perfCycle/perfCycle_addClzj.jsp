<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>业绩计算--材料增减新增</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
		var selectRows = [];
		/**初始化表格*/
		$(function(){
			Global.JqGrid.initJqGrid("addDetailDataTable",{
				url:"${ctx}/admin/perfCycle/goClzjJqGrid",
				postData:{
					chgNos:$("#chgNos").val(),custCode:"${perfCycle.custCode}",isAddAllInfo:"${perfCycle.isAddAllInfo}"
				},
				height:330,
				multiselect: true,
				colModel : [
					{name: "documentno", index: "documentno", width: 100, label: "档案编号", sortable: true, align: "left"},
					{name: "no", index: "no", width: 100, label: "增减单号", sortable: true, align: "left"},
					{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
					{name: "custcode", index: "custcode", width: 90, label: "客户编号", sortable: true, align: "left"},
					{name: "customerdescr", index: "customerdescr", width: 80, label: "客户名称", sortable: true, align: "left"},
					{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
					{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left"},
					{name: "statusdescr", index: "statusdescr", width: 95, label: "材料增减状态", sortable: true, align: "left"},
					{name: "isservicedescr", index: "isservicedescr", width: 112, label: "是否服务性产品", sortable: true, align: "left"},
					{name: "iscupboarddescr", index: "iscupboarddescr", width: 83, label: "是否橱柜", sortable: true, align: "left"},
					{name: "date", index: "date", width: 130, label: "变更日期", sortable: true, align: "left", formatter: formatTime},
					{name: "befamount", index: "befamount", width: 85, label: "优惠前金额", sortable: true, align: "right",sorttype:"number", sum: true},
					{name: "discamount", index: "discamount", width: 85, label: "优惠金额", sortable: true, align: "right",sorttype:"number", sum: true},
					{name: "amount", index: "amount", width: 85, label: "实际总价", sortable: true, align: "right",sorttype:"number", sum: true},
					{name: "managefee", index: "managefee", width: 85, label: "管理费", sortable: true, align: "right",sorttype:"number"},
					{name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
					{name: "appczydescr", index: "appczydescr", width: 103, label: "申请人", sortable: true, align: "left"},
					{name: "confirmczydescr", index: "confirmczydescr", width: 106, label: "审核人", sortable: true, align: "left"},
					{name: "confirmdate", index: "confirmdate", width: 107, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "修改人", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 80, label: "操作", sortable: true, align: "left"},
					{name: "itemtype1", index: "itemtype1", width: 80, label: "材料类型1", sortable: true, align: "left",hidden:true},
					{name: "isservice", index: "isservice", width: 112, label: "是否服务性产品", sortable: true, align: "left",hidden:true},
					{name: "iscupboard", index: "iscupboard", width: 83, label: "是否橱柜", sortable: true, align: "left",hidden:true},
					{name: "disccost", index: "disccost", width: 83, label: "优惠分摊成本", sortable: true, align: "left",hidden:true},
					{name: "chgstakeholder", index: "chgstakeholder", width: 80, label: "干系人", sortable: true, align: "left",hidden:true},
	            ],
	            loadonce:true
			});
			//全选
			$("#selAll").on("click",function(){
				Global.JqGrid.jqGridSelectAll("addDetailDataTable",true);
			});
			//全不选
			$("#selNone").on("click",function(){
				Global.JqGrid.jqGridSelectAll("addDetailDataTable",false);
			});
		});
		function save(){
			var ids=$("#addDetailDataTable").jqGrid("getGridParam", "selarrrow");
			var chgNos=$("#chgNos").val();
			if(ids.length == 0){
				art.dialog({
					content:"请选择数据后保存"
				});				
				return ;
			}
			var chgStakeholder="${perfCycle.chgStakeholder}"; 
			if("${perfCycle.isAddAllInfo}"=="0"){
				if(chgStakeholder==""){
					var rowData = $("#addDetailDataTable").jqGrid("getRowData", ids[0]);
					chgStakeholder=rowData.chgstakeholder;	
				}
				var sErrChgNo="";
				$.each(ids,function(i,id){
					var rowData = $("#addDetailDataTable").jqGrid("getRowData", id);
					if(chgStakeholder!=rowData.chgstakeholder){
						sErrChgNo=rowData.no;
						return false;
					}
				}); 
				if(sErrChgNo!=""){
					art.dialog({
						content:"增减单号："+sErrChgNo+"干系人与其他添加不一致！"
					});				
					return ;
				} 
			};
			$.each(ids,function(i,id){
				var rowData = $("#addDetailDataTable").jqGrid("getRowData", id);
				selectRows.push(rowData);
				if(chgNos != ""){
					chgNos += ",";
				}
				chgNos += rowData.no;
			}); 
			$("#chgNos").val(chgNos);
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
					<input hidden="true" id="chgNos"
							name="chgNos" value="${perfCycle.chgNos}">
					<input hidden="true" id="custCode"
							name="custCode" value="${perfCycle.custCode}">
					<ul class="ul-form">
					<li><label>增减单号</label> <input type="text" id="chgNo"
						name="chgNo" style="width:160px;" />
					</li>
					<li><label>变更日期从</label> <input type="text"
						id="dateFrom" name="dateFrom" class="i-date"
						style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>至</label> <input type="text" id="dateTo"
						name="dateTo" class="i-date" style="width:160px;"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
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
