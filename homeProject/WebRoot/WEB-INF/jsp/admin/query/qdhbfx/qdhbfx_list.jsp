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
	<title>签单环比分析</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	var indexString="lastSignCount",
		colindexString="",
		sortorderString="";
	
	var postData = $("#page_form").jsonForm();
	$("#content-list-department2").attr("hidden","true");
	
	var gridOption_personal ={
		height:$(document).height()-$("#content-list").offset().top-102,
		styleUI: 'Bootstrap', 
		sortable: true,
		colModel : [
			{name: "Number", index: "Number", width: 80, label: "员工编号", sortable: true, align: "left",},
			{name: "Namechi", index: "Namechi", width: 65, label: "设计师", sortable: true, align: "left"},
			{name: "Depart1Descr", index: "Depart1Descr", width: 77, label: "一级部门", sortable: true, align: "left"},
			{name: "Depart2Descr", index: "Depart1Descr", width: 73, label: "二级部门", sortable: true, align: "left"},
			{name: "Depart3Descr", index: "Depart3Descr", width: 87, label: "三级部门", sortable: true, align: "left"},
			{name: "TeamDescr", index: "TeamDescr", width: 87, label: "团队", sortable: true, align: "left"},
			{name: "BuilderDescr", index: "BuilderDescr", width: 130, label: "项目名称", sortable: true, align: "left"},
			{name: "CustTypeDescr", index: "CustTypeDescr", width: 87, label: "客户类型", sortable: true, align: "left"},
			{name: "SignCount", index: "SignCount", width: 90, label: "本月签单量", sortable: true, align: "right", sum: true},
			{name: "lastSignCount", index: "lastSignCount", width: 97, label: "上月签单量", sortable: true, align: "right", sum: true},
			{name: "SignPer", index: "SignPer", width: 80, label: "签单量环比", sortable: true, align: "right",formatter : DiyFmatter},
			{name: "CrtCount", index: "CrtCount", width:90, label: "本月来客量", sortable: true, align: "right", sum: true},
			{name: "lastCrtCount", index: "lastCrtCount", width: 87, label: "上月来客量", sortable: true, align: "right", sum: true},
			{name: "CrtPer", index: "CrtPer", width: 85, label: "来客量环比", sortable: true, align: "right",formatter : DiyFmatter},
			{name: "SetCount", index: "SetCount", width: 90, label: "本月下定量", sortable: true, align: "right", sum: true},
			{name: "lastSetCount", index: "lastSetCount", width: 93, label: "上月下定量", sortable: true, align: "right", sum: true},
			{name: "SetPer", index: "SetPer", width: 85, label: "下定量环比", sortable: true, align: "right",formatter : DiyFmatter},
			{name: "AchieveFee", index: "AchieveFee", width: 95, label: "本月业绩金额", sortable: true, align: "right", sum: true},
			{name: "lastAchieveFee", index: "lastAchieveFee", width: 95, label: "上月业绩金额", sortable: true, align: "right", sum: true}		
		],
		    loadonce: true,
	};
	
		   Global.JqGrid.initJqGrid("dataTable",gridOption_personal);
	
	function DiyFmatter (cellvalue, options, rowObject){ 
	    return parseInt(cellvalue)+"%";
	}

	window.goto_query = function(){
		var dateFrom=new Date($.trim($("#dateFrom").val()));
		var dateTo=new Date($.trim($("#dateTo").val()));
		var mouthFrom=dateFrom.getMonth()+1;
		var yearFrom=dateFrom.getYear()+1;
		var mouthTo=dateTo.getMonth()+1;
		var yearTo=dateTo.getYear()+1;
		if(dateFrom>dateTo){
			art.dialog({
				content: "开始日期不能大于结束日期",
			});
			return false;
		}
		
		if($.trim($("#dateFrom").val())==''||$.trim($("#dateTo").val())==''){
			art.dialog({
				content: "时间查询条件不能为空",
			});
			return false;
		}		
		if(mouthFrom!=mouthTo||yearTo!=yearFrom){
			art.dialog({
				content: "不能跨月统计",
			});
			return false;
		}
		var statistcsMethod=$.trim($("#statistcsMethod").val());
		$("#dataTable").jqGrid("setGridParam",{datatype:'json',postData:$("#page_form").jsonForm(),page:1,url:"${ctx}/admin/qdhbfx/goJqGrid",}).trigger("reloadGrid");
		var role=$.trim($("#role").val());
		if(role=='00'){
			document.getElementById('jqgh_dataTable_Namechi').innerHTML="设计师";
		}else if(role=='01'){
			document.getElementById('jqgh_dataTable_Namechi').innerHTML="业务员";
		}else{
			document.getElementById('jqgh_dataTable_Namechi').innerHTML="翻单员";
		}
		if(statistcsMethod=='1'){
			jQuery("#dataTable").setGridParam().showCol("Number").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().showCol("Namechi").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().showCol("Depart1Descr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().showCol("Depart2Descr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().showCol("Depart3Descr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("TeamDescr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("BuilderDescr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("CustTypeDescr").trigger("reloadGrid");
		}else if(statistcsMethod=='2'){//一级部门
			jQuery("#dataTable").setGridParam().hideCol("Number").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("Namechi").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().showCol("Depart1Descr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("Depart2Descr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("Depart3Descr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("TeamDescr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("BuilderDescr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("CustTypeDescr").trigger("reloadGrid");
		}else if(statistcsMethod=='3'){//二级部门
			jQuery("#dataTable").setGridParam().hideCol("Number").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("Namechi").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().showCol("Depart1Descr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().showCol("Depart2Descr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("Depart3Descr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("TeamDescr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("BuilderDescr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("CustTypeDescr").trigger("reloadGrid");
		}else if(statistcsMethod=='4'){//团队
			jQuery("#dataTable").setGridParam().hideCol("Number").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("Namechi").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("Depart1Descr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("Depart2Descr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("Depart3Descr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().showCol("TeamDescr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("BuilderDescr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("CustTypeDescr").trigger("reloadGrid");
		}else if(statistcsMethod=='5'){//客户类型
			jQuery("#dataTable").setGridParam().hideCol("Number").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("Namechi").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("Depart1Descr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("Depart2Descr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("Depart3Descr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("TeamDescr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("BuilderDescr").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().showCol("CustTypeDescr").trigger("reloadGrid");
		}
	}
});

function doExcel(){
	var url = "${ctx}/admin/qdhbfx/doExcel";
	doExcelAll(url);
}

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" id="expired"  name="expired" value="${customer.expired }"/>
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>一级部门</label>
								<house:DictMulitSelect id="department1" dictCode="" sql="select code,desc1 from tDepartment1 where Expired='F'" 
								sqlLableKey="desc1" sqlValueKey="code" selectedValue="${customer.department1 }" onCheck="checkDepartment1()"></house:DictMulitSelect>
							</li>
							<li>
								<label>二级部门</label>
								<house:DictMulitSelect id="department2" dictCode="" sql="select code,desc1 from tDepartment2 where 1=2" 
								sqlLableKey="desc1" sqlValueKey="code" selectedValue="${customer.department2 }" onCheck="checkDepartment2()"></house:DictMulitSelect>
							</li>
							<li>
								<label>三级部门</label>
								<house:DictMulitSelect id="department3" dictCode="" sql="select code,desc1 from tDepartment3 where 1=2" sqlLableKey="desc1" sqlValueKey="code" selectedValue="${customer.department3 }"></house:DictMulitSelect>
							</li>
							<li>
							<label>客户类型</label>
								<house:custTypeMulit id="custType"  selectedValue="${customer.custType}"></house:custTypeMulit>
							</li>
							<li >
								<label>团队</label>
								<house:dict id="team" dictCode="" sql="select rtrim(Code)Code,rtrim(Code)+' '+Desc1 Desc1 from tTeam where Expired='F' order by Code" 
									sqlValueKey="Code" sqlLableKey="Desc1"  ></house:dict>
							</li>
							<li>
								<label>统计角色</label>
								<select id="role" name="role" style="width: 160px;" >
									<option value="00">按设计员统计</option>
									<option value="01">按业务员统计</option>
									<option value="24">按翻单员统计</option>
								</select>
							</li>
							<li>	
								<label>统计开始日期</label>
								<input type="text" id="dateFrom" name="dateFrom"  class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd '/>" />
							</li>
							<li>
								<label>统计结束日期</label>
								<input type="text" id="dateTo" name="dateTo"  class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd '/>"  />
							</li>
							<li>
								<label>统计方式</label>
 								<select id="statistcsMethod" name="statistcsMethod" style="width: 160px;" >
 									<option value="3">按二级部门</option>
 									<option value="1">按个人</option>
 									<option value="2">按一级部门</option>
 									<option value="4">按团队</option>
 									<option value="5">按客户类型</option>
 								</select>
							</li>
							<li>
							   <label>一级区域</label>
							   <house:xtdmMulit id="region" dictCode="" sql="select code SQL_VALUE_KEY,descr SQL_LABEL_KEY  from tRegion a where a.expired='F' " ></house:xtdmMulit>
							</li>	
							<li class="search-group">					
								<input type="checkbox"  id="expired_show" name="expired_show"
								 value="${customer.expired }" onclick="hideExpired(this)" 
								 ${purchase.expired!='T'?'checked':'' } /><p>隐藏过期</p>
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
						</ul>
				</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<!-- panelBar -->
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
							<button type="button" class="btn btn-system "  onclick="doExcel()" >
								<span>导出excel</span>
							</button>
					</div>
				</div>
					<!-- panelBar End -->
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
					
				</div> 
	</body>	
</html>
