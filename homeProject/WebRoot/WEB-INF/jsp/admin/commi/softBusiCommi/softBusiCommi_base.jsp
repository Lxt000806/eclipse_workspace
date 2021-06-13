<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>提成材料信息</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_roll.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_itemCommiCycle.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	
	var costRight = "${softBusiCommi.costRight}";
	var isHidden = costRight == "1" ? false : true;
	//清空
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("select").val("");
	} 
	/**初始化表格*/
	$(function(){
		$("#commiNo").openComponent_itemCommiCycle({
			showValue:"${softBusiCommi.commiNo}"
		});
		
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/softBusiCommi/goBaseJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			postData:$("#page_form").jsonForm(),
			colModel : [
				{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
				{name: "busitypedescr", index: "busitypedescr", width: 70, label: "业务类型", sortable: true, align: "left"},
				{name: "itemchgno", index: "itemchgno", width: 80, label: "单号", sortable: true, align: "left"},
				{name: "fixareadescr", index: "fixareadescr", width: 80, label: "装修区域", sortable: true, align: "left"},
				{name: "itemcode", index: "itemcode", width: 70, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 150, label: "材料名称", sortable: true, align: "left"},
				{name: "isoutsetdescr", index: "isoutsetdescr", width: 70, label: "套餐外材料", sortable: true, align: "left"},
				{name: "isgiftdescr", index: "isgiftdescr", width: 70, label: "赠送项目", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 70, label: "数量", sortable: true, align: "right"},
				{name: "price", index: "price", width: 70, label: "单价", sortable: true, align: "right"},
				{name: "processcost", index: "processcost", width: 70, label: "其他费用", sortable: true, align: "right"},
				{name: "markup", index: "markup", width: 60, label: "折扣", sortable: true, align: "right"},
				{name: "beflineamount", index: "beflineamount", width: 80, label: "折扣前金额", sortable: true, align: "right"},
				{name: "lineamount", index: "lineamount", width: 70, label: "总价", sortable: true, align: "right",sum:true},
				{name: "cost", index: "cost", width: 70, label: "成本单价", sortable: true, align: "right",hidden:isHidden },
				{name: "disccost", index: "disccost", width: 90, label: "优惠金额分摊", sortable: true, align: "right",hidden:"${softBusiCommi.isInd }" == "1"?false:true,sum:true},
				{name: "amount", index: "amount", width: 70, label: "成本总价", sortable: true, align: "right",sum:true,hidden:isHidden},
				{name: "profit", index: "profit", width: 70, label: "毛利", sortable: true, align: "right",hidden:isHidden},
				{name: "profitper", index: "profitper", width: 70, label: "毛利率", sortable: true, align: "right",hidden:isHidden,formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2, suffix: "%"}},
				{name: "commiper", index: "commiper", width: 70, label: "提成点", sortable: true, align: "right"},
				{name: "commiamount", index: "commiamount", width: 70, label: "提成额", sortable: true, align: "right",sum:true},
				{name: "crtmon", index: "crtmon", width: 70, label: "生成月份", sortable: true, align: "left",hidden:"${softBusiCommi.isInd }" == "1"?true:false},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"}
			],
		});
	});
	
	function doExcel(){
		var url = "${ctx}/admin/softBusiCommi/doBaseExcel";
		doExcelAll(url);
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<c:if test="${softBusiCommi.chgNo != '' && softBusiCommi.chgNo != null}">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" name="chgNo" value="${softBusiCommi.chgNo }" />
				<input type="hidden" name="isInd" value="${softBusiCommi.isInd }" />
			</form>
		</c:if>
		<c:if test="${softBusiCommi.chgNo == '' || softBusiCommi.chgNo eq null}">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" onclick="doExcel()">
							<span>导出Excel</span>
						</button>
					</div>
				</div>
			</div>
			<div class="query-form">
				<form role="form" class="form-search" id="page_form" action=""
					method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="chgNo" value="${softBusiCommi.chgNo }" />
					<input type="hidden" name="isInd" value="${softBusiCommi.isInd }" />
					<ul class="ul-form">
						<c:if test="${softBusiCommi.crtMon != null}">
							<li>
								<label>生成月份</label>
								<input type="text" id="crtMon" name="crtMon" class="i-date" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})" value="${softBusiCommi.crtMon }"/>
							</li>
						</c:if>
						<c:if test="${softBusiCommi.commiNo != null}">
							<li>
								<label>提成周期</label>
								<input type="text" id="commiNo" name="commiNo" />
							</li>
						</c:if>
						<li>
							<label>楼盘</label> 
							<input type="text" id="address" name="address" />
						</li>
						<li>
							<button type="button" class="btn btn-sm btn-system"
								onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system"
								onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</c:if>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
