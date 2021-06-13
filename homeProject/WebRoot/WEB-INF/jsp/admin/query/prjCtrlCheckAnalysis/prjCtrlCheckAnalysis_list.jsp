<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<title>工地发包结算分析</title>
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<style type="text/css">
.panelBar {
	height: 26px;
}
</style>
<script type="text/javascript">
 $(function(){
       //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',
		rowNum:100000,  
		pager :'1',
		loadonce: true,
		colModel : [
			{name: "客户号", index: "客户号", width: 65, label: "客户号", sortable: true, align: "left",count:true,frozen : true},
			{name: "楼盘", index: "楼盘", width: 140, label: "楼盘", sortable: true, align: "left",frozen : true},
			{name :"签单时间",index : "签单时间",width : 80,label:"签单时间",sortable : true,align : "left",formatter: formatDate},
			{name: "客户类型", index: "客户类型", width: 65, label: "客户类型", sortable: true, align: "left",count:true},
			{name: "套内面积", index: "套内面积", width: 65, label: "套内面积", sortable: true, align: "right",sum: true},
			{name: "发包补贴", index: "发包补贴", width: 65, label: "发包补贴", sortable: true, align: "right", sum: true},
			{name: "发包总价", index: "发包总价", width: 65, label: "发包总价", sortable: true, align: "right", sum: true},
			{name: "预扣", index: "预扣", width: 80, label: "预扣", sortable: true, align: "right", sum: true},
			{name: "支出", index: "支出", width: 65, label: "支出", sortable: true, align: "right", sum: true},
			{name: "应发金额", index: "应发金额", width: 65, label: "应发金额", sortable: true, align: "right", sum: true},
			{name: "每平米提成", index: "每平米提成", width: 75, label: "每平米提成", sortable: true, align: "right"},
			{name: "基础发包单价", index: "基础发包单价", width: 90, label: "基础发包单价", sortable: true, align: "right"},
			{name: "现场管理费", index: "现场管理费", width: 80, label: "现场管理费", sortable: true, align: "right", sum: true},
			{name: "套餐外发包", index: "套餐外发包", width: 80, label: "套餐外发包", sortable: true, align: "right", sum: true},
			{name: "套内每平米成本", index: "套内每平米成本", width: 100, label: "套内每平米成本", sortable: true, align: "right"},
			{name: "主材奖惩", index: "主材奖惩", width: 65, label: "主材奖惩", sortable: true, align: "right", sum: true},
			{name: "水电每平米单价", index: "水电每平米单价", width: 100, label: "水电每平米单价", sortable: true, align: "right"},
			{name: "土建每平米单价砌墙不算", index: "土建每平米单价砌墙不算", width: 150, label: "土建每平米单价砌墙不算", sortable: true, align: "right", sum: true},
			{name: "油漆每平米单价", index: "油漆每平米单价", width: 100, label: "油漆每平米单价", sortable: true, align: "right"},
			{name: "拆除每平米单价", index: "拆除每平米单价", width: 100, label: "拆除每平米单价", sortable: true, align: "right"},
			{name: "砌墙面积", index: "砌墙面积", width: 65, label: "砌墙面积", sortable: true, align: "right", sum: true},
			{name: "水电材料成本", index: "水电材料成本", width: 85, label: "水电材料成本", sortable: true, align: "right",sum: true},
			{name: "水电工资", index: "水电工资", width: 65, label: "水电工资", sortable: true, align: "right", sum: true},
			{name: "后期安装工资", index: "后期安装工资", width: 100, label: "后期安装工资", sortable: true, align: "right", sum: true},
			{name: "水电材料奖惩", index: "水电材料奖惩", width: 100, label: "水电材料奖惩", sortable: true, align: "right", sum: true},
			{name: "土建材料成本", index: "土建材料成本", width: 100, label: "土建材料成本", sortable: true, align: "right", sum: true},
			{name: "砌墙人工", index: " 砌墙人工", width: 65, label: "砌墙人工", sortable: true, align: "right", sum: true},
        	{name: "饰面人工", index: " 饰面人工", width: 65, label: "饰面人工", sortable: true, align: "right", sum: true},
        	{name: "防水人工", index: "防水人工", width: 80, label: "防水人工", sortable: true, align: "right", sum: true},
        	{name: "找平人工", index: "找平人工", width: 80, label: "找平人工", sortable: true, align: "right", sum: true},
        	{name: "油漆材料成本", index: "油漆材料成本", width: 100, label: "油漆材料成本", sortable: true, align: "right"},
        	{name: "油漆人工", index: "油漆人工", width: 65, label: "油漆人工", sortable: true, align: "right", sum: true},
        	{name: "木作材料成本", index: "木作材料成本", width: 100, label: "木作材料成本", sortable: true, align: "right",sum: true},
        	{name: "木作人工", index: "木作人工", width: 65, label: "木作人工", sortable: true, align: "right", sum: true},
        	{name: "拆除及其它材料成本", index: "拆除及其它材料成本", width: 125, label: "拆除及其它材料成本", sortable: true, align: "right",sum: true},
        	{name: "拆除及其它工资", index: "拆除及其它工资", width: 100, label: "拆除及其它工资", sortable: true, align: "right", sum: true},
        	{name: "防水面积", index: "防水面积", width: 65, label: "防水面积", sortable: true, align: "right", sum: true},
        	{name: "卫生间数", index: "卫生间数", width: 65, label: "卫生间数", sortable: true, align: "right", sum: true},
       		{name: "水电发包", index: "水电发包", width: 65, label: "水电发包", sortable: true, align: "leftt"},
       		{name: "防水发包", index: "防水发包", width: 65, label: " 防水发包", sortable: true, align: "leftt"},
       		{name: "套内成本", index: "套内成本", width: 65, label: "套内成本", sortable: true, align: "right", hidden: true},
        ],    
        gridComplete:function(){
        	 var sumYfjeper=0,sumAvgCost=0; sumSdAvg=0,sumTjAvg=0,sumYqAvg=0,sumCcAvg=0;
		     var sumYfje=myRound($("#dataTable").getCol('应发金额',false,'sum')); 
		     var sumTnmj=myRound($("#dataTable").getCol('套内面积',false,'sum'));
             if(sumTnmj!=0){
               sumYfjeper=myRound(sumYfje/sumTnmj,0);//每平米提成
               sumAvgCost=myRound($("#dataTable").getCol('套内成本',false,'sum')/sumTnmj,2);//套内每平米成本汇总=支出-套餐外发包*0.9/套内面积
               sumSdAvg=myRound((myRound($("#dataTable").getCol('水电材料成本',false,'sum'))+myRound($("#dataTable").getCol('水电工资',false,'sum'))
               			+myRound($("#dataTable").getCol('后期安装工资',false,'sum'))+myRound($("#dataTable").getCol('水电材料奖惩',false,'sum')))/sumTnmj);
               sumTjAvg=myRound((myRound($("#dataTable").getCol('土建材料成本',false,'sum'))+myRound($("#dataTable").getCol('饰面人工',false,'sum'))
               			+myRound($("#dataTable").getCol('防水人工',false,'sum'))+myRound($("#dataTable").getCol('找平人工',false,'sum'))
           				-myRound($("#dataTable").getCol('砌墙面积',false,'sum'))*60)/sumTnmj);
			   sumYqAvg=myRound((myRound($("#dataTable").getCol('油漆材料成本',false,'sum'))+myRound($("#dataTable").getCol('油漆人工',false,'sum')))/sumTnmj);
               sumCcAvg=myRound((myRound($("#dataTable").getCol('拆除及其它材料成本',false,'sum'))+myRound($("#dataTable").getCol('拆除及其它工资',false,'sum')))/sumTnmj);
             }
             $("#dataTable").footerData('set', {'每平米提成': sumYfjeper});	
             $("#dataTable").footerData('set', {'套内每平米成本': sumAvgCost});	
             $("#dataTable").footerData('set', {'水电每平米单价': sumSdAvg});
             $("#dataTable").footerData('set', {'土建每平米单价砌墙不算': sumTjAvg});
             $("#dataTable").footerData('set', {'油漆每平米单价': sumYqAvg});
             $("#dataTable").footerData('set', {'拆除每平米单价': sumCcAvg});
	     },  
	     loadComplete: function(){
              	$('.ui-jqgrid-bdiv').scrollTop(0);
              	frozenHeightReset("dataTable");
         },           
	 });
	 $("#projectMan").openComponent_employee();
	 $("#dataTable").jqGrid("setFrozenColumns");
});
function goto_query(){
	if($.trim($("#ConfirmDateFrom").val())==''){
			art.dialog({content: "结算开始日期不能为空",width: 200});
			return false;
	} 
	if($.trim($("#ConfirmDateTo").val())==''){
			art.dialog({content: "结算结束日期不能为空",width: 200});
			return false;
	}
     var dateStart = Date.parse($.trim($("#ConfirmDateFrom").val()));
     var dateEnd = Date.parse($.trim($("#ConfirmDateTo").val()));
     if(dateStart>dateEnd){
    	 art.dialog({content: "结算开始日期不能大于结束日期",width: 200});
			return false;
     }
      
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/prjCtrlCheckAnalysis/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
}  

function Jcszxx(){
  	var ret = selectDataTableRow("dataTable");
	if(ret) {
		Global.Dialog.showDialog("jcszxx",{
				title:"基础收支信息【"+ret.楼盘+"】",
				url:"${ctx}/admin/itemSzQuery/goJcszxx?code="+ret.客户号,
				height:730,
				width:1350
		});
	}else{
		art.dialog({
			content: "请选择一条记录"
		});
		return;
	}	
}

//导出EXCEL
function doExcel(){
  	doExcelNow("工地发包结算分析","dataTable","page_form");
}
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#custType").val('');
	$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
}

function changeContainOilPaint(obj){
	if ($(obj).is(":checked")){
		$("#containOilPaint").val("1");
	}else{
		$("#containOilPaint").val("0");
	}
}
</script>
</head>

<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" /> <input type="hidden" id="expired" name="expired"
					 />
				<ul class="ul-form">
					<li><label>客户类型</label> <house:DictMulitSelect id="custType" dictCode=""
							sql="select Code,  Desc1 Descr from tcustType  where IsAddAllInfo='1' order by  dispSeq " sqlValueKey="Code"
							sqlLableKey="Descr"></house:DictMulitSelect>
					</li>
					<li>
						<label>项目经理</label>
						<input type="text" id="projectMan" name="projectMan" style="width:160px;" />
					</li>
					<li>
						<label>结算日期从</label>
						<input type="text" id="ConfirmDateFrom" name="ConfirmDateFrom" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						 value="<fmt:formatDate value='${customer.confirmDateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>至</label>
						<input type="text" id="ConfirmDateTo" name="ConfirmDateTo" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customer.confirmDateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label></label> 
						<input type="checkbox" id="containOilPaint" name="containOilPaint" checked="checked" value="1"  onclick="changeContainOilPaint(this)" />包含油漆未做&nbsp;
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>

		<div class="clear_float"></div>

		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system " id="JcszxxBtn" onclick="Jcszxx()" >基础收支信息</button>
				<button type="button" class="btn btn-system " onclick="doExcel()">输出至excel</button>				
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>

</html>


