<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>赠品管理选择提交审批</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_docFolder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
<script type="text/javascript"> 
$(function() {
	var gridOption = {	
		height:$(document).height()-$("#content-list").offset().top-100,
		rowNum:500,
		multiselect:true,
		colModel : [
			{name: "pk", index: "pk", width: 90, label: "pk", sortable: true, align: "left",hidden:true},
			{name: "giftpk", index: "giftpk", width: 90, label: "赠送项目PK", sortable: true, align: "left",hidden:true},
			{name: "custdescr", index: "custdescr", width: 90, label: "客户名称", sortable: true, align: "left",hidden:true},
			{name: "custcode", index: "custcode", width: 90, label: "客户编号", sortable: true, align: "left",hidden:true},
			{name: "typedescr", index: "typedescr", width: 90, label: "优惠类型", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 150, label: "优惠项目标题", sortable: true, align: "left"},
			{name: "discamttypedescr", index: "discamttypedescr", width: 90, label: "优惠金额分类", sortable: true, align: "left"},
			{name: "type", index: "type", width: 90, label: "类型", sortable: true, align: "left",hidden:true},
			{name: "discamount", index: "discamount", width: 90, label: "实际优惠额", sortable: true, align: "right",},
			{name: "calcdiscctrlper", index: "calcdiscctrlper", width: 105, label: "优惠额度控制比例", sortable: true, align: "right"},
			{name: "coinamount", index: "coinamount", width: 70, label: "通用币额", sortable: true, align: "right"},
			{name: "bjyhje", index: "bjyhje", width: 90, label: "单品报价优惠金额", sortable: true, align: "right",},
			{name: "maxdiscamt", index: "maxdiscamt", width: 90, label: "最高可优惠金额", sortable: true, align: "right",},
			{name: "isoutdisc", index: "isoutdisc", width: 90, label: "是否超优惠", sortable: true, align: "left",},
			{name: "perfdiscamount", index: "perfdiscamount", width: 90, label: "业绩扣减金额", sortable: true, align: "right"},
			{name: "issofttokendescr", index: "issofttokendescr", width: 90, label: "是否软装券", sortable: true, align: "left"},
			{name: "zssamebj", index: "zssamebj", width: 120, label: "赠送与报价一致", sortable: true, align: "left"},
			{name: "disctypedescr", index: "disctypedescr", width: 90, label: "优惠折扣类型", sortable: true, align: "left"},
			{name: "giftremarks", index: "giftremarks", width: 190, label: "优惠说明", sortable: true, align: "left"},
			{name: "issofttoken", index: "issofttoken", width: 90, label: "是否软装券", sortable: true, align: "left",hidden:true},
			{name: "quotemodule", index: "quotemodule", width: 90, label: "报价模块", sortable: true, align: "left",hidden:true},
			{name: "quotemoduledescr", index: "quotemoduledescr", width: 90, label: "报价模块", sortable: true, align: "left",hidden:true},
			{name: "saleamount", index: "saleamount", width: 90, label: "销售额", sortable: true, align: "left",hidden:true},
			{name: "totalcost", index: "totalcost", width: 90, label: "成本额", sortable: true, align: "left",hidden:true},
			{name: "discamttype", index: "discamttype", width: 90, label: "优惠金额分类", sortable: true, align: "left",hidden:true},
			{name: "perfdisctype", index: "perfdisctype", width: 90, label: "业绩折扣类型", sortable: true, align: "left",hidden:true},
			{name: "perfdisctypedescr", index: "perfdisctypedescr", width: 90, label: "业绩折扣类型", sortable: true, align: "left",hidden:true},
			{name: "perfdiscper", index: "perfdiscper", width: 90, label: "业绩折扣比例", sortable: true, align: "left",hidden:true},
			{name: "dispseq", index: "dispseq", width: 90, label: "序号", sortable: true, align: "left",hidden:true},
			{name: "lastupdate", index: "lastupdate", width: 90, label: "最后修改时间", sortable: true, align: "left",formatter:formatDate},
			{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "最后修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 90, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 90, label: "操作日志", sortable: true, align: "left"},
			{name: "disctype", index: "disctype", width: 90, label: "赠送类型", sortable: true, align: "left",hidden:true},
			{name: "area", index: "area", width: 90, label: "面积", sortable: true, align: "left",hidden:true},
			{name: "wallarea", index: "wallarea", width: 90, label: "砌墙面积", sortable: true, align: "left",hidden:true},
			{name: "custtype", index: "custtype", width: 90, label: "客户类型", sortable: true, align: "left",hidden:true},
			{name: "maxdiscamount", index: "maxdiscamount", width: 90, label: "分段控制金额", sortable: true, align: "left",hidden:true},
			{name: "maxdiscamtexpr", index: "maxdiscamtexpr", width: 290, label: "最高金额公式", sortable: true, align: "left",hidden:true},
			{name: "basefeedirct", index: "basefeedirct", width: 90, label: "基础直接费", sortable: true, align: "left",hidden:true},
			{name: "dispseq", index: "dispseq", width: 90, label: "显示顺序", sortable: true, align: "left"},
		],
	}; 
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	var detailObj = ${custGift.detailJson};
	if(detailObj){
		$("#dataTable").jqGrid('setGridParam',{
		      datatype:'local',
		      data : detailObj,  
		      page:1,
		}).trigger("reloadGrid");
	}
});

function commitOA(){
	var json = Global.JqGrid.selectToJson("dataTable");

	if(json == {}){
		art.dialog({
			content: "请选择需要提交的数据",
		});
		return;
	}	
	
	Global.Dialog.showDialog("custGiftApp",{ 
		title:"选择项目",
		url:"${ctx}/admin/itemPlan/goWfProcApply",
		postData:{custCode: "${custGift.custCode }", detailJson: JSON.stringify(json)},
		height: 650,
		width:1250,
		returnFun:goto_query
	});
}


</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			      <div class="btn-group-xs">
						<button type="button" class="btn btn-system " id="saveBut" onclick="commitOA()">
							<span>提交</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="container-fluid">  
				<div id="content-list">
					<div id="tab_detail" class="tab-pane fade in active">
						<div class="pageContent">
							<div class="panel panel-system" >
							</div>
							<div id="content-list">
								<table id="dataTable"></table>
							</div>
						</div>
					</div>
				</div>	
			</div>	
		</div>
	</body>	
</html>
