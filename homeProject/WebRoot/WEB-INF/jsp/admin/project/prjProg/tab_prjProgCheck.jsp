<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>工地验收</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript"> 
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			height:415,
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "no", index: "no", width: 147, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "address", index: "address", width: 130, label: "楼盘地址", sortable: true, align: "left"},
				{name: "projectmandescr", index: "projectmandescr", width: 60, label: "项目经理", sortable: true, align: "left"},
				{name: "prjitemdescr", index: "prjitemdescr", width: 120, label: "施工项目", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 114, label: "备注", sortable: true, align: "left"},
				{name: "checkmen", index: "checkmen", width: 80, label: "巡检人员", sortable: true, align: "left"},
				{name: "lastupdatedby", index: "lastupdatedby", width: 60, hidden:true,label: "巡检人员", sortable: true, align: "left"},
				{name: "date", index: "date", width: 120, label: "巡检日期", sortable: true, align: "left", formatter: formatTime},
				{name: "ismodifydescr", index: "ismodifydescr", width: 80, label: "是否整改", sortable: true, align: "left"},
				{name: "modifytime", index: "modifytime", width: 90, label: "整改时限", sortable: true, align: "right"},
				{name: "remainmodifytime", index: "remainmodifytime", width: 95, label: "整改剩余时限", sortable: true, align: "right"},
				{name: "modifycompletedescr", index: "modifycompletedescr", width: 70, label: "整改完成", sortable: true, align: "left"},
				{name: "compremark", index: "compremark", width: 120, label: "完成说明", sortable: true, align: "left"},
				{name: "compczydescr", index: "compczydescr", width: 63, label: "整改人", sortable: true, align: "left"},
				{name: "compdate", index: "compdate", width: 83, label: "完成时间", sortable: true, align: "left", formatter: formatTime},
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left",hidden:true},
				{name: "no", index: "no", width: 83, label: "编号", sortable: true, align: "left" ,hidden:true},
				{name: "safepromdescr", index: "safepromdescr", width: 75, label: "安全问题", sortable: true, align: "left"} 	,		
				{name: "visualpromdescr", index: "visualpromdescr", width: 75, label: "形象问题", sortable: true, align: "left"} ,			
				{name: "artpromdescr", index: "artpromdescr", width: 75, label: "工艺问题", sortable: true, align: "left"} ,
				{name: "errposidescr", index: "errposidescr", width: 75, label: "位置异常", sortable: true, align: "left"},
				{name: "isupprjprogdescr", index: "isupprjprogdescr", width: 75, label: "更新进度", sortable: true, align: "left"},
				{name: "gpsaddress", index: "gpsaddress", width: 224, label: "定位信息", sortable: true, align: "left"},
				{name: "custscore", index: "custscore", width: 70, label: "客户评分", sortable: true, align: "left"},
				{name: "custremarks", index: "custremarks", width: 224, label: "客户评价", sortable: true, align: "left"},
			], 
 		};
       //初始化送货申请明细
	   Global.JqGrid.initJqGrid("dataTable",gridOption);


	$("#detail").on("click",function(){
			var ret = selectDataTableRow();
         	  if (ret) {	
             	Global.Dialog.showDialog("View",{ 
              	  title:"巡检单查看",
              	  url:"${ctx}/admin/prjProg/goViewGDXJ?id="+ret.no+"&a="+ret.remainmodifytime,
              	  height: 650,
              	  width:1000,
              	  returnFun:goto_query
              	});
            } else {
            	art.dialog({
        			content: "请选择一条记录"
        		});
            }
		});
		window.goto_query = function(){
			$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,url:"${ctx}/admin/prjProgCheck/goCheckJqGrid"}).trigger("reloadGrid");
		}
		
});


function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$("#remarks").val('');
	$("#address").val('');
	$("#page_form input[type='text']").val('');
	$("#splStatusDescr").val('');
	$.fn.zTree.getZTreeObj("tree_prjItem").checkAllNodes(false);
} 


</script>
</head>
	<body>
			<div class="panel-body">
					<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;"  value="${prjProgCheck.address}" />
							</li>
							<li>
								<label>巡检人</label>
								<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${prjProgCheck.lastUpdatedBy}" />
							</li>
							<li>
								<label>工程部</label>
							<house:department2 id="Department2" dictCode="03"  depType="3"  ></house:department2>
							</li>
							<li>
								<label>巡检日期</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
							<li>
								<label>是否整改</label>
								<house:xtdm id="isModify" dictCode="YESNO"  style="width:160px;" value="${prjProg.isModify }" ></house:xtdm>
							</li>
							<li>
								<label>施工项目</label>
								<house:DictMulitSelect id="prjItem" dictCode="" sql="select code,descr from tPrjItem1 where IsConfirm='1' order by seq " 
								sqlLableKey="descr" sqlValueKey="code" selectedValue="${customer.prjItem}" ></house:DictMulitSelect>
							</li>
							<li>
								<label>异常位置</label>
								<house:xtdm id="errPosi" dictCode="YESNO"  style="width:160px;" value="${prjprogCheck.errPosi }" ></house:xtdm>
							</li>
							<li>
								<label>更新进度</label>
								<house:xtdm id="isUpPrjProg" dictCode="YESNO"  style="width:160px;" value="${prjprogCheck.isUpPrjProg }" ></house:xtdm>
							</li>
							<li>
								<label>客户评分</label>
								<house:DictMulitSelect id="custScoreCheck" dictCode="" 
								sql="select p.number code ,cast (p.number as char(1))+'星' descr from master..spt_values p 
										where p.type = 'p' and p.number between 1 and 5 " 
								sqlLableKey="descr" sqlValueKey="code" selectedValue="${customer.prjItem}" ></house:DictMulitSelect>
							</li>
							<li>
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							</li>
						</ul>
			</form>
			</div>
			</div>
		<div class="btn-panel" >
    		<div class="btn-group-sm"  >
			<button type="button" class="btn btn-system " id="detail" style="float:left">
				<span>查看</span>
				</button>
			<button type="button" class="btn btn-system " onclick="doExcelNow('工地巡检','dataTable');" >
				<span>导出Excel</span>
			</button>
		</div>
		</div>
		<div class="pageContent">
						<div id="content-list" >
			<table id="dataTable" ></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
	</body>	
</html>
