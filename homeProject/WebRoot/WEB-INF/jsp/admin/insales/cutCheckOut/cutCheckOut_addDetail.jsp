<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>瓷砖加工管理-新增明细</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
		var selectRows = [];
		/**初始化表格*/
		$(function(){
			Global.JqGrid.initJqGrid("addDetailDataTable",{
				url:"${ctx}/admin/cutCheckOut/goAddDetailJqGrid",
				postData:{
					refpks:$("#refpks").val(),sendType:"2"
				},
				height:450,
	        	styleUI: "Bootstrap",
				multiselect: true,
				colModel : [
					{name: "ordername", index: "ordername", width: 220, label: "排序", sortable: true, align: "left",hidden:true},
					{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
					{name: "iano", index: "iano", width: 80, label: "领料单号", sortable: true, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 180, label: "材料名称", sortable: true, align: "left"},
					{name: "itemcode", index: "itemcode", width: 80, label: "材料编号", sortable: true, align: "left"},
					{name: "fixareadescr", index: "fixareadescr", width: 107, label: "装修区域", sortable: true, align: "left"},
					{name: "fixareapk", index: "fixareapk", width: 98, label: "装修区域", sortable: true, align: "left",hidden:true},
					{name: "qty", index: "qty", width: 60, label: "数量", sortable: true, align: "right",},
					{name: "cuttype", index: "cuttype", width: 55, label: "切割方式", sortable: false, align: "left",hidden:true},
					{name: "cuttypedescr", index: "cuttypedescr", width: 85, label: "切割方式", sortable: true, align: "left",},
					{name: "cutfee", index: "cutfee", width: 72, label: "加工单价", sortable: true, align: "right",hidden:true},
					{name: "itemappremarks", index: "itemappremarks", width: 267, label: "备注", sortable: true, align: "left"},
					{name: "itemreqremarks", index: "itemreqremarks", width: 267, label: "材料需求备注", sortable: true, align: "left",hidden:true},
					{name: "oldcuttypedescr", index: "oldcuttypedescr", width: 85, label: "原切割方式", sortable: true, align: "left",hidden:true},
					{name: "confirmdate", index: "confirmdate", width: 140, label: "领料审核日期", sortable: true, align: "left", formatter: formatTime},
					{name: "refpk", index: "refpk", width: 70, label: "领料明细pk", sortable: true, align: "left", hidden: true},
					{name: "total", index: "total", width: 60, label: "总价", sortable: true, align: "right",hidden:true},
					{name: "iscompletedescr", index: "iscompletedescr", width: 55, label: "是否完成", sortable: false, align: "left",hidden:true},
					{name: "iscomplete", index: "iscomplete", width: 55, label: "是否完成", sortable: false, align: "left",hidden:true},
					{name: "iaqty", index: "iaqty", width: 55, label: "数量", sortable: true, align: "right",hidden:true},
					{name: "size", index: "size", width: 55, label: "规格", sortable: true, align: "right",hidden:true},
					{name: "allowmodify", index: "allowmodify", width: 60, label: "是否允许修改单价", sortable: true, align: "left",hidden:true},
	            ],
			});
			
			replaceCloseEvt("add", closeAndReturn);
		});
		function save(){
			var ids=$("#addDetailDataTable").jqGrid("getGridParam", "selarrrow");
			var refpks=$("#refpks").val();
			if(ids.length == 0){
				art.dialog({
					content:"请选择数据后保存"
				});				
				return ;
			}
			$.each(ids,function(i,id){
				var rowData = $("#addDetailDataTable").jqGrid("getRowData", id);
				selectRows.push(rowData);
				if(refpks != ""){
					refpks += ",";
				}
				refpks += rowData.refpk;
			}); 
			$("#refpks").val(refpks);
    		art.dialog({content: "添加成功！",width: 200,time:800});
    		$("#addDetailDataTable").jqGrid("setGridParam",{
	    		postData:{refpks:refpks},
	    		page:1,
	    		sortname:''
    		}).trigger("reloadGrid");
		}
		function closeAndReturn(){
			Global.Dialog.returnData = selectRows;
	    	closeWin();
		}
		function tableSelected(flag){
			selectAll("addDetailDataTable", flag);
		}
		
	</script>
	</head>
	    
	<body>
		<div class="body-box-list">
	 		<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs" style="float:left">
	    				<button id="saveBut" type="button" class="btn btn-system" onclick="save()">保存</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeAndReturn()">关闭</button>
					</div>
					<input hidden="true" id="dataTableExists_selectRowAll"
							name="dataTableExists_selectRowAll" value="">
					<input hidden="true" id="refpks" name="refpks" value="${cutCheckOut.refpks}">
				</div>
			</div> 
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search" >
						<ul class="ul-form">
						<li>
							<label>发货类型</label>
							<house:xtdm id="sendType" dictCode="ITEMAPPSENDTYPE" value="2"/>
						</li>
						<li>
							<label>领料单号</label>
							<input type="text" id="iano" name="iano"/>
						</li>
						<li>
							<label>领料明细备注</label>
							<input type="text" id="remarks" name="remarks"/>
						</li>
						<li>
							<label>是否切割</label> 
							<house:xtdm id="isCut" dictCode="YESNO" style="width:160px;"/>
						</li>
						<li>
							<label>审核日期从</label>
							<input type="text" id="confirmDateFrom" name="confirmDateFrom" class="i-date" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="confirmDateTo" name="confirmDateTo" class="i-date" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li class="search-group" >
								<button type="button" class="btn btn-sm btn-system" onclick="goto_query('addDetailDataTable');">查询</button>
								<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
							</li>
						</ul>
				</form>
			</div>
			<div id="content-list">
				<table id="addDetailDataTable"></table>
				<div id="addDetailDataTablePager"></div>
			</div>
		</div>
	</body>
</html>
