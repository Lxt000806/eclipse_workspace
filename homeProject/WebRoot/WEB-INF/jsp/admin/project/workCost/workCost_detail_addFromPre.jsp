<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>基础人工成本管理-从预申请导入</title>
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
			$("#workerCode").openComponent_worker();
			Global.LinkSelect.initSelect("${ctx}/admin/workCostDetail/workTypeByAuthority","workType1","workType2");
			Global.JqGrid.initJqGrid("addDetailDataTable",{
				url:"${ctx}/admin/preWorkCostDetail/goJqGrid2",
				postData:{
					refPrePks:$("#refPrePks").val(),
					appNo:"${preWorkCostDetail.appNo}"
				},
				height:330,
	        	styleUI: "Bootstrap",
				multiselect: true,
				rowNum:100000,  
    			pager :'1',
				colModel : [
					{name: "pk", index: "pk", width: 70, label: "编号", sortable: true, align: "left", hidden: true},
					{name: "custstatus", index: "custstatus", width: 100, label: "客户状态", sortable: true, align: "left", hidden: true},
					{name: "iswithhold", index: "iswithhold", width: 100, label: "是否预扣", sortable: true, align: "left", hidden: true},
					{name: "documentno", index: "documentno", width: 120, label: "档案号", sortable: true, align: "left"},
					{name: "address", index: "address", width: 220, label: "楼盘", sortable: true, align: "left"},
					{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
					{name: "salarytypedescr", index: "salarytypedescr", width: 80, label: "工资类型", sortable: true, align: "left"},
					{name: "worktype1descr", index: "worktype1descr", width: 80, label: "工种类型1", sortable: true, align: "left"},
					{name: "worktype2descr", index: "worktype2descr", width: 80, label: "工种类型2", sortable: true, align: "left"},
					{name: "iswithholddescr", index: "iswithholddescr", width: 80, label: "是否预扣", sortable: true, align: "left"},
					{name: "withholdno", index: "withholdno", width: 80, label: "预扣单号", sortable: true, align: "left"},
					{name: "workercode", index: "workercode", width: 80, label: "工人编号", sortable: true, align: "left"},
					{name: "actname", index: "actname", width: 80, label: "工人姓名", sortable: true, align: "left"},
					{name: "cardid", index: "cardid", width: 170, label: "卡号", sortable: true, align: "left"},
					{name: "cardid2", index: "cardid2", width: 170, label: "卡号2", sortable: true, align: "left"},
					{name: "appamount", index: "appamount", width: 80, label: "申请金额", sortable: true, align: "right"},
					{name: "cfmamount", index: "cfmamount", width: 80, label: "审核金额", sortable: true, align: "right"},
					{name: "iswateritemctrldescr", index: "iswateritemctrldescr", width: 120, label: "水电材料发包工人", sortable: true, align: "left"},
					{name: "applyman", index: "applyman", width: 80, label: "申请人", sortable: true, align: "left"},
					{name: "applydate", index: "applydate", width: 140, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
					{name: "remarks", index: "remarks", width: 170, label: "请款说明", sortable: true, align: "left"},
					{name: "confirmassistdescr", index: "confirmassistdescr", width: 100, label: "助理审核", sortable: true, align: "left"},
					{name: "assistconfirmdate", index: "assistconfirmdate", width: 140, label: "助理审核日期", sortable: true, align: "left", formatter: formatTime},
					{name: "confirmmanagerdescr", index: "confirmmanagerdescr", width: 100, label: "经理审核", sortable: true, align: "left"},
					{name: "managerconfirmdate", index: "managerconfirmdate", width: 140, label: "经理审核日期", sortable: true, align: "left", formatter: formatTime},
					{name: "checkstatusdescr", index: "checkstatusdescr", width: 100, label: "客户结算状态", sortable: true, align: "left", hidden: true},
					{name: "applymandescr", index: "applymandescr", width: 72, label: "申请人", sortable: true, align: "left", hidden: true},
					{name: "statusdescr", index: "statusdescr", width: 61, label: "状态", sortable: true, align: "left", hidden: true},
					{name: "confirmamount", index: "confirmamount", width: 96, label: "审批金额", sortable: true, align: "right", sum: true, hidden: true},
					{name: "issigndescr", index: "issigndescr", width: 80, label: "是否签约", sortable: true, align: "left", hidden: true},
					{name: "custctrl", index: "custctrl", width: 100, label: "人工发包额", sortable: true, align: "right", sum: true, hidden: true},
					{name: "custctrl_kzx", index: "custctrl_kzx", width: 110, label: "人工发包控制", sortable: true, align: "right", sum: true, hidden: true},
					{name: "custcost", index: "custcost", width: 90, label: "人工总成本", sortable: true, align: "right", sum: true, hidden: true},
					{name: "leavecustcost", index: "leavecustcost", width: 100, label: "单项工种余额", sortable: true, align: "right", hidden: true},
					{name: "allleavecustcost", index: "allleavecustcost", width: 100, label: "总发包余额", sortable: true, align: "right", hidden: true},
					{name: "allcustctrl", index: "allcustctrl", width: 90, label: "总发包额", sortable: true, align: "right", sum: true, hidden: true},
					{name: "allcustcost", index: "allcustcost", width: 90, label: "总成本", sortable: true, align: "right", sum: true, hidden: true},
					{name: "refprepk", index: "refprepk", width: 80, label: "预申请PK", sortable: true, align: "left", hidden: true},
					{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "最后修改人", sortable: true, align: "left", hidden: true},
					{name: "lastupdate", index: "lastupdate", width: 140, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
					//{name: "workercode", index: "workercode", width: 84, label: "工人编号", sortable: true, align: "left", hidden: true},
					{name: "qualityfeebegin", index: "qualityfeebegin", width: 120, label: "质保金起始金额", sortable: true, align: "right", hidden: true},
					{name: "isconfirm", index: "isconfirm", width: 80, label: "审核标志", sortable: true, align: "left", hidden: true},
					{name: "custcode", index: "custcode", width: 100, label: "客户编号", sortable: true, align: "left", hidden: true},
					{name: "salarytype", index: "salarytype", width: 100, label: "工资类型", sortable: true, align: "left", hidden: true},
					{name: "worktype2", index: "worktype2", width: 100, label: "工资类型2", sortable: true, align: "left", hidden: true},
					{name: "worktype1", index: "worktype1", width: 100, label: "工资类型1", sortable: true, align: "left", hidden: true},
					{name: "status", index: "status", width: 100, label: "状态", sortable: true, align: "left", hidden: true},
					{name: "type", index: "type", width: 100, label: "申请类型", sortable: true, align: "left", hidden: true},
					{name: "prjitem", index: "prjitem", width: 100, label: "项目名", sortable: true, align: "left", hidden: true},
					{name: "enddate", index: "enddate", width: 140, label: "完成时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
					{name: "confirmdate", index: "confirmdate", width: 140, label: "验收时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
					{name: "rcvcost", index: "rcvcost", width: 100, label: "领取金额", sortable: true, align: "right", hidden: true},
					{name: "withholdcost", index: "withholdcost", width: 100, label: "预扣金额", sortable: true, align: "right", hidden: true},
					{name: "gotamount", index: "gotamount", width: 100, label: "已领工资", sortable: true, align: "right", hidden: true},
					{name: "totalamount", index: "totalamount", width: 100, label: "合同总价", sortable: true, align: "right", hidden: true},
					{name: "confirmczy", index: "confirmczy", width: 100, label: "验收人", sortable: true, align: "left", hidden: true},
					{name: "confirmczydescr", index: "confirmczydescr", width: 100, label: "验收人", sortable: true, align: "left", hidden: true},
					{name: "signdate", index: "signdate", width: 100, label: "开工日期", sortable: true, align: "left", formatter: formatTime, hidden: true},
					{name: "iswateritemctrl", index: "iswateritemctrl", width: 120, label: "是否水电发包", sortable: true,align: "left", hidden: true},
					{name: "iswateritemctrldescr", index: "iswateritemctrldescr", width: 90, label: "水电发包工人", sortable: true,align: "left", hidden: true},
					{name: "workerplanoffer", index: "workerplanoffer", width: 90, label: "定额工资", sortable: true,align: "left", hidden: true},
					{name: "idnum", index: "idnum", width: 90, label: "工人身份证号", sortable: true,align: "left", hidden: true},
					
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
		});
		function save(){
			var ids=$("#addDetailDataTable").jqGrid("getGridParam", "selarrrow");
			var refPrePks=$("#refPrePks").val();
			if(ids.length == 0){
				art.dialog({
					content:"请选择数据后保存"
				});				
				return ;
			}
			$.each(ids,function(i,id){
				var rowData = $("#addDetailDataTable").jqGrid("getRowData", id);
				//保存前设置最后修改人和修改时间
				rowData['lastupdatedby']='${preWorkCostDetail.lastUpdatedBy}';
				rowData['lastupdate']=new Date().getTime();
				rowData['isconfirm']='0';
				rowData['appamount']=rowData['cfmamount'];
				selectRows.push(rowData);
				if(refPrePks != ""){
					refPrePks += ",";
				}
				refPrePks += rowData.refprepk;
			}); 
			$("#refPrePks").val(refPrePks);
			/* var len = ids.length;  
			for(var i = 0;i < len ;i ++) {  
				$("#addDetailDataTable").jqGrid("delRowData", ids[0]);  
			}   */
    		art.dialog({content: "添加成功！",width: 200,time:800});
    		$("#addDetailDataTable").jqGrid("setGridParam",{
	    		postData:{refPrePks:refPrePks,appNo:"${preWorkCostDetail.appNo}"},
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
	    				<button id="selAll" type="button" class="btn btn-system" >全选</button>
	    				<button id="selNone" type="button" class="btn btn-system" >全不选</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeAndReturn()">关闭</button>
					</div>
					<input hidden="true" id="dataTableExists_selectRowAll"
							name="dataTableExists_selectRowAll" value="">
					<input hidden="true" id="refPrePks"
							name="refPrePks" value="${preWorkCostDetail.refPrePks}">		
				</div>
			</div> 
			<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
					<ul class="ul-form">
					<li><label>楼盘</label> <input type="text" id="address"
						name="address" style="width:160px;" />
					</li>
					<li><label>工人编号</label> <input type="text" id="workerCode"
						name="workerCode" style="width:160px;" />
					</li>
					<li>
					<li><label>工种类型1</label> <select id="workType1"
						name="workType1"></select>
					</li>
					<li><label>工种类型2</label> <select id="workType2"
						name="workType2"></select>
					</li>
					<li class="search-group" >
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query('addDetailDataTable');">查询</button>
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
