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
	<title>固定资产管理主页</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
	$("#department").openComponent_department();	
	$("#assetType").openComponent_assetType();
	$("#useMan").openComponent_employee();
	var data=$("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url : "${ctx}/admin/asset/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',
		postData:data,
		//rowNum:100000,
		colModel : [
			{name: "code", index: "code", width: 70, label: "资产编号", sortable: true, align: "left",},
			{name: "descr", index: "descr", width: 150, label: "资产名称", sortable: true, align: "left"},
			{name: "model", index: "model", width: 180, label: "规格型号", sortable: true, align: "left"},
			{name: "typedescr", index: "typedescr", width: 70, label: "类别名称", sortable: true, align: "left",},
			{name: "uomdescr", index: "uomdescr", width: 50, label: "单位", sortable: true, align: "left"},
			{name: "addtypedescr", index: "addtypedescr", width: 70, label: "增加方式", sortable: true, align: "left"},
			{name: "deptdescr", index: "deptdescr", width: 60, label: "部门", sortable: true, align: "left",},
			{name: "usemandescr", index: "usemandescr", width: 60, label: "使用人", sortable: true, align: "left",},
			{name: "status", index: "status", width: 60, label: "状态编码", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 60, label: "状态", sortable: true, align: "left"},
			{name: "address", index: "address", width: 180, label: "存放地点", sortable: true, align: "left"},
			{name: "qty", index: "qty", width: 50, label: "数量", sortable: true, align: "right",sum:true},
			{name: "originalvalue", index: "originalvalue", width: 50, label: "原值", sortable: true, align: "right",sum:true},
			{name: "begindate", index: "begindate", width: 110, label: "开始使用日期", sortable: true, align: "left",formatter: formatDate},
			{name: "totaldepramount", index: "totaldepramount", width: 80, label: "累计折旧额", sortable: true, align: "right",sum:true},
			{name: "useyear", index: "useyear", width: 70, label: "使用年限", sortable: true, align: "right"},
			{name: "deprtypedescr", index: "deprtypedescr", width: 68, label: "折旧方式", sortable: true, align: "left"},
			{name: "deprmonth", index: "deprmonth", width: 80, label: "已计提月份", sortable: true, align: "right"},
			{name: "remaindeprmonth", index: "remaindeprmonth", width: 83, label: "剩余折旧月份", sortable: true, align: "right"},
			{name: "netvalue", index: "netvalue", width: 55, label: "净值", sortable: true, align: "right",sum:true},
			{name: "depramountpermonth", index: "depramountpermonth", width: 68, label: "月折旧额", sortable: true, align: "right"},
			{name: "crtczydescr", index: "crtczydescr", width: 68, label: "录入人", sortable: true, align: "left"},
			{name: "createdate", index: "createdate", width: 110, label: "录入时间", sortable: true, align: "left", formatter: formatDate},
			{name: "dectypedescr", index: "dectypedescr", width: 68, label: "减少方式", sortable: true, align: "left"},
			{name: "decczydescr", index: "decczydescr", width: 60, label: "注销人", sortable: true, align: "left"},
			{name: "decdate", index: "decdate", width: 110, label: "注销时间", sortable: true, align: "left",formatter: formatDate},
			{name: "decremarks", index: "decremarks", width: 200, label: "注销说明", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 200, label: "备注", sortable: true, align: "left"},
		],
	});
	
	$("#save").on("click",function(){
		Global.Dialog.showDialog("goSave",{
			title:"固定资产管理——新增",
			url:"${ctx}/admin/asset/goSave",
			height:540,
			width:700,
			returnFun:goto_query
		});
	});
	
	$("#update").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录进行编辑",
			});
			
			return;
		}
		if(ret.decdate != ""){
			art.dialog({
				content:"该资产已减少，无法编辑"
			});
			
			return;
		}
		
		Global.Dialog.showDialog("goUpdate",{
			title:"固定资产管理——编辑",
			url:"${ctx}/admin/asset/goUpdate",
			postData:{code:ret.code},
			height:540,
			width:700,
			returnFun:goto_query
		});
	});
	
	$("#dec").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录进行操作",
			});
			
			return;
		}
		if(ret.decdate != ""){
			art.dialog({
				content:"该资产已减少，无法继续减少"
			});
			
			return;
		}
		
		Global.Dialog.showDialog("goDec",{
			title:"固定资产管理——资产减少",
			url:"${ctx}/admin/asset/goDec",
			postData:{code:ret.code},
			height:310,
			width:700,
			returnFun:goto_query
		});
	});
	
	$("#decReturn").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录进行操作",
			});
			
			return;
		}
		if(ret.decdate == ""){
			art.dialog({
				content:"该资产不是减少状态，无法撤销减少"
			});
			
			return;
		}
		art.dialog({
			content:"是否确定撤销减少",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/asset/doDecReturn",
					type:"post",
					data:{code: ret.code},
					dataType:"json",
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
					},
					success:function(obj){
						art.dialog({
							content: obj.msg,
						});
						$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
					}
				});
			},
			cancel: function () {
				return true;
			}
		});	
	});
	
	$("#originalValueChg").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录进行操作",
			});
			
			return;
		}
		if(ret.decdate != ""){
			art.dialog({
				content:"该资产已减少，无法调整"
			});
			
			return;
		}
		
		Global.Dialog.showDialog("goOriginalValueChg",{
			title:"固定资产管理——原值调整",
			url:"${ctx}/admin/asset/goOriginalValueChg",
			postData:{code:ret.code,chgType:"1"},
			height:350,
			width:700,
			returnFun:goto_query
		});
	});
	
	$("#totalDeprAmountChg").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录进行操作",
			});
			
			return;
		}
		if(ret.decdate != ""){
			art.dialog({
				content:"该资产已减少，无法调整"
			});
			
			return;
		}
		
		Global.Dialog.showDialog("goTotalDeprAmountChg",{
			title:"固定资产管理——累计折旧调整",
			url:"${ctx}/admin/asset/goTotalDeprAmountChg",
			postData:{code:ret.code,chgType:"2"},
			height:350,
			width:700,
			returnFun:goto_query
		});
	});
	
	$("#useYearChg").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录进行操作",
			});
			
			return;
		}
		if(ret.decdate != ""){
			art.dialog({
				content:"该资产已减少，无法调整"
			});
			
			return;
		}
		
		Global.Dialog.showDialog("goUseYearChg",{
			title:"固定资产管理——使用年限调整",
			url:"${ctx}/admin/asset/goUseYearChg",
			postData:{code:ret.code,chgType:"3"},
			height:350,
			width:700,
			returnFun:goto_query
		});
	});
	
	$("#deprTypeChg").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录进行操作",
			});
			
			return;
		}
		if(ret.decdate != ""){
			art.dialog({
				content:"该资产已减少，无法调整"
			});
			
			return;
		}
		
		Global.Dialog.showDialog("goDeprTypeChg",{
			title:"固定资产管理——折旧类型调整",
			url:"${ctx}/admin/asset/goDeprTypeChg",
			postData:{code:ret.code,chgType:"4"},
			height:350,
			width:700,
			returnFun:goto_query
		});
	});
	
	$("#departmentChg").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条记录进行操作",
			});
			
			return;
		}
		if(ret.decdate != ""){
			art.dialog({
				content:"该资产已减少，无法调整"
			});
			
			return;
		}
		
		Global.Dialog.showDialog("goDepartmentChg",{
			title:"固定资产管理——部门转移",
			url:"${ctx}/admin/asset/goDepartmentChg",
			postData:{code:ret.code,chgType:"5"},
			height:450,
			width:700,
			returnFun:goto_query
		});
	});
	
	$("#calcDepr").on("click",function(){
		Global.Dialog.showDialog("CalcDepr",{
			title:"固定资产管理——计提折旧",
			url:"${ctx}/admin/asset/goCalcDepr",
			postData:{},
			height:650,
			width:920,
			returnFun:goto_query
		});
	});
	
	$("#chgDetail").on("click",function(){
		Global.Dialog.showDialog("goChgDetail",{
			title:"固定资产管理——资产变动查询",
			url:"${ctx}/admin/asset/goChgDetail",
			postData:{},
			height:650,
			width:1050,
			returnFun:goto_query
		});
	});
	
	$("#deprDetail").on("click",function(){
		Global.Dialog.showDialog("goDeprDetail",{
			title:"固定资产管理——计提折旧查询",
			url:"${ctx}/admin/asset/goDeprDetail",
			postData:{},
			height:650,
			width:920,
			returnFun:goto_query
		});
	});
	
	$("#deprGroupList").on("click",function(){
		Global.Dialog.showDialog("DeprGroupList",{
			title:"固定资产管理——折旧分配查询",
			url:"${ctx}/admin/asset/goDeprGroupList",
			postData:{},
			height:650,
			width:920,
			returnFun:goto_query
		});
	});
	
	$("#view").on("click",function(){
		var ret = selectDataTableRow();
		if(!ret){
			aet.dialog({
				content:"请选择一条数据进行查看",
			});
			
			return;
		}
		Global.Dialog.showDialog("View",{
			title:"固定资产管理——查看",
			url:"${ctx}/admin/asset/goView",
			postData:{code:ret.code},
			height:650,
			width:920,
			returnFun:goto_query
		});
	});
	
	$("#batchChg").on("click",function(){
		Global.Dialog.showDialog("goBatchChg",{
			title:"固定资产管理——批量变动",
			url:"${ctx}/admin/asset/goBatchChg",
			postData:{},
			height:750,
			width:1150,
			returnFun:goto_query
		});
	});
});

function doExcel(){
	var url = "${ctx}/admin/asset/doExcel";
	doExcelAll(url);
}
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>公司</label>
							<house:dict id="cmpCode" dictCode="" sql="select Code,Code+' '+Desc2 Descr from tCompany where expired = 'F' "  
									sqlValueKey="Code" sqlLableKey="Descr" ></house:dict>  
						</li>
						<li>
							<label>部门</label>
							<input type="text" id="department" name="department" style="width:160px;"/>
						</li>
						<li>
							<label>资产类别</label>
							<input type="text" id="assetType" name="assetType" style="width:160px;"/>
						</li>
						<li>
							<label>员工姓名</label>
							<input type="text" id="useMan" name="useMan" style="width:160px;"/>
						</li>
						<li>
							<label>使用日期从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
								style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" 
								style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label>减少日期从</label>
							<input type="text" id="decDateFrom" name="decDateFrom" class="i-date" 
								style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="decDateTo" name="decDateTo" class="i-date" 
								style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label>状态</label>
							<house:xtdm id="status" dictCode="ASSETSTATUS" value="1"></house:xtdm>
						</li>
						<li>
							<label>资产编号</label>
							<input type="text" id="code" name="code" style="width:160px;"/>
						</li>
						<li >
							<label class="control-textarea"> 备注</label>
							<textarea id="remarks" name="remarks" rows="2"></textarea>
						</li>
						<li>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm" >
					<house:authorize authCode="ASSET_SAVE" >
						<button type="button" class="btn btn-system" style="margin-top:5px" id="save">
							<span>新增</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ASSET_UPDATE">
						<button type="button" class="btn btn-system" style="margin-top:5px" id="update">
							<span>编辑</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ASSET_DEC">
						<button type="button" class="btn btn-system" style="margin-top:5px" id="dec">
							<span>资产减少</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ASSET_ORIGINALVALUECHG">
						<button type="button" class="btn btn-system" style="margin-top:5px" id="originalValueChg">
							<span>原值调整</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ASSET_TOTALDEPRAMOUNTCHG">
						<button type="button" class="btn btn-system" style="margin-top:5px" id="totalDeprAmountChg">
							<span>累计折旧调整</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ASSET_USEYEARCHG">
						<button type="button" class="btn btn-system" style="margin-top:5px" id="useYearChg">
							<span>使用年限调整</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ASSET_DEPRTYPECHG">
						<button type="button" class="btn btn-system" style="margin-top:5px" id="deprTypeChg">
							<span>折旧方法调整</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ASSET_DEPARTMENTCHG">
						<button type="button" class="btn btn-system" style="margin-top:5px" id="departmentChg">
							<span>部门转移</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ASSET_CALCDEPR">
						<button type="button" class="btn btn-system" style="margin-top:5px" id="calcDepr">
							<span>计提折旧</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ASSET_DECRETURN">
						<button type="button" class="btn btn-system" style="margin-top:5px" id="decReturn">
							<span>撤销减少</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ASSET_BATCHCHG">
						<button type="button" class="btn btn-system" style="margin-top:5px" id="batchChg">
							<span>批量变动</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ASSET_VIEW">
						<button type="button" class="btn btn-system" style="margin-top:5px" id="view">
							<span>查看</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ASSET_CHGLIST">
						<button type="button" class="btn btn-system" style="margin-top:5px" id="chgDetail">
							<span>资产变动查询</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ASSET_DEPRLIST">
						<button type="button" class="btn btn-system" style="margin-top:5px" id="deprDetail">
							<span>计提折旧查询</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ASSET_DEPRGROUPLIST">
						<button type="button" class="btn btn-system" style="margin-top:5px" id="deprGroupList">
							<span>折旧分配查询</span>
						</button>
					</house:authorize>
					<house:authorize authCode="ASSET_EXCEL" >
						<button type="button" class="btn btn-system" style="margin-top:5px" onclick="doExcel()" title="导出检索条件数据">
							<span>导出excel</span>
						</button>
					</house:authorize>
				</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</body>	
</html>
