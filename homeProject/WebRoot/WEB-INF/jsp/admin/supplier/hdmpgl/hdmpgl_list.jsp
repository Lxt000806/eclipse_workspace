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
	<title>活动门票管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_activity.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	$("#actNo").openComponent_activity({showValue:'${hdmpgl.actNo }',showLabel:'${hdmpgl.actDescr }'});
	//alert('${hdmpgl.actNo }');
	$("#businessMan").openComponent_employee();
	$("#provideCZY").openComponent_czybm({condition:{forTicket:'1'}});
	var czylb=$.trim($("#czylb").val());
	var hasAuthByCzybh=$.trim($("#hasAuthByCzybh").val());
	if(czylb=='2'){
		$('#businessMan_li').hide();
		$('#provideMan_li').hide();
		$('#department1_li').hide();
		$('#department2_li').hide();
	}
	/* if(hasAuthByCzybh!='true'){
		$("#provideType").attr("disabledp","disabled");
	} */
	
	

	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/supplierHdmpgl/goJqGrid",
		postData:postData ,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
		{name: "pk", index: "pk", width: 40, label: "pk", sortable: true, align: "left",hidden:true},
		{name: "actno", index: "actno", width: 70, label: "活动编号", sortable: true, align: "left",hidden:true},
		{name: "actname", index: "actname", width: 130, label: "活动名称", sortable: true, align: "left"},
		{name: "ticketno", index: "ticketno", width: 80, label: "门票号", sortable: true, align: "left"},
		{name: "status", index: "status", width: 60, label: "状态", sortable: true, align: "left",hidden:true},
		{name: "statusdescr", index: "statusdescr", width: 60, label: "状态", sortable: true, align: "left"},
		{name: "descr", index: "descr", width: 80, label: "业主姓名", sortable: true, align: "left"},
		{name: "address", index: "address", width: 170, label: "楼盘", sortable: true, align: "left"},
		{name: "designmandescr", index: "designmandescr", width: 80, label: "设计师", sortable: true, align: "left",},
		{name: "desingmandept2descr", index: "desingmandept2descr", width: 90, label: "设计师部门", sortable: true, align: "left",},
		{name: "realbusimanname", index: "realbusimanname", width: 80, label: "业务员", sortable: true, align: "left"},
		{name: "businessman", index: "businessman", width: 80, label: "业务员", sortable: true, align: "left",hidden:true},
		{name: "businessmandept2descr", index: "businessmandept2descr", width: 80, label: "业务员部门", sortable: true, align: "left",},
		{name: "providetypedescr", index: "providetypedescr", width: 80, label: "发放类型", sortable: true, align: "left"},
		{name: "provideczydescr", index: "provideczydescr", width: 85, label: "发放操作员", sortable: true, align: "left"},
		{name: "department2descr", index: "department2descr", width: 80, label: "二级部门", sortable: true, align: "left"},
		{name: "providedate", index: "providedate", width: 110, label: "发放时间", sortable: true, align: "left", formatter: formatTime},
		{name: "signdate", index: "signdate", width: 110, label: "签到时间", sortable: true, align: "left", formatter: formatTime,},
		{name: "lastupdate", index: "lastupdate", width: 110, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
		{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "最后更新人员", sortable: true, align: "left"},
		{name: "expired", index: "expired", width: 90, label: "过期标志", sortable: true, align: "left"},
		{name: "actionlog", index: "actionlog", width: 90, label: "操作日志", sortable: true, align: "left"}
		],
		loadComplete:function(){
			
	    },
	});
	if(czylb=='2'){
		jQuery("#dataTable").setGridParam().hideCol("designmandescr").trigger("reloadGrid");
		jQuery("#dataTable").setGridParam().hideCol("desingmandept2descr").trigger("reloadGrid");
		jQuery("#dataTable").setGridParam().hideCol("businessmandept2descr").trigger("reloadGrid");
		jQuery("#dataTable").setGridParam().hideCol("department2descr").trigger("reloadGrid");
	}
	
	$("#provide").on("click",function(){
		var ret = selectDataTableRow();
		Global.Dialog.showDialog("provide",{
			title:"门票——发放",
			url:"${ctx}/admin/supplierHdmpgl/goProvide",
			postData:{actNo:ret.actno,ticketNo:ret.ticketno,pk:ret.pk},
			height:600,
			width:750,
			returnFun:goto_query,
			close:function(){
				$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
			}
		});
	});
	
	$("#create").on("click",function(){
		Global.Dialog.showDialog("create",{
			title:"门票——生成",
			url:"${ctx}/admin/hdmpgl/goCreate",
			//postData:{actNo:ret.actno,ticketNo:ret.ticketno,pk:ret.pk},
			height:600,
			width:750,
			returnFun:goto_query
		});
	});
	
	$("#del").on("click",function(){
		Global.Dialog.showDialog("del",{
			title:"门票——删除",
			url:"${ctx}/admin/hdmpgl/goDelete",
			//postData:{actNo:ret.actno,ticketNo:ret.ticketno,pk:ret.pk},
			height:600,
			width:750,
			returnFun:goto_query
		});
	});
	
	
	$("#update").on("click",function(){
		var ret = selectDataTableRow();
		var hasAuthByCzybh=$.trim($("#hasAuthByCzybh").val());
		var czybh=$.trim($("#czybh").val());
		if(ret.status!='2'){
			art.dialog({
				content:'只有已发放的门票才能修改',
			});
			return false;
		}
		if(hasAuthByCzybh!="true"){
			if(czybh!=$.trim(ret.businessman)&&czybh!=$.trim(ret.lastupdatedby)){
				art.dialog({
					content:"您没有权限修改这张门票！",
				});
				return false;
			}
		}
		
		Global.Dialog.showDialog("update",{
			title:"门票——修改",
			url:"${ctx}/admin/supplierHdmpgl/goUpdate",
			postData:{pk:ret.pk},
			height:600,
			width:750,
			returnFun:goto_query
		});
	});
	
	$("#view").on("click",function(){
		var ret = selectDataTableRow();
		Global.Dialog.showDialog("view",{
			title:"门票——查看",
			url:"${ctx}/admin/supplierHdmpgl/goView",
			postData:{pk:ret.pk},
			height:650,
			width:900,
			returnFun:goto_query
		});
	});
	
	$("#orderDetail").on("click",function(){
		var ret = selectDataTableRow();
		Global.Dialog.showDialog("view",{
			title:"门票——下定明细",
			url:"${ctx}/admin/hdmpgl/goOrderDetail",
			height:750,
			width:1200,
			returnFun:goto_query
		});
	});
	
	$("#giftDetail").on("click",function(){
		var ret = selectDataTableRow();
		Global.Dialog.showDialog("view",{
			title:"门票——礼品发放明细",
			url:"${ctx}/admin/hdmpgl/goGiftDetail",
			height:750,
			width:1200,
			returnFun:goto_query
		});
	});
	
	$("#callBack").on("click",function(){
		var datas = $("#dataForm").serialize();
		var ret = selectDataTableRow();
		if(ret.status!='2'){
			art.dialog({
				content:'已发放的门票才能收回',
			});
			return false;
		}
		art.dialog({
			content:"确定要对["+ret.ticketno+"]门票进行收回操作吗？",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
		    	$.ajax({
					url:'${ctx}/admin/supplierHdmpgl/doCallback',
					type: 'post',
					data: {pk:ret.pk},
					dataType: 'json',
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
				    },
				    success: function(obj){
				    	goto_query();
				    }
				 });
		    	return true;
			},
			cancel: function () {
				return true;
			}
		});
	});
	
	$("#sign").on("click",function(){
		var datas = $("#dataForm").serialize();
		var ret = selectDataTableRow();
		if(ret.status!='2'){
			art.dialog({
				content:'已发放的门票才能进行签到',
			});
			return false;
		}
		art.dialog({
			content:"确定要对["+ret.ticketno+"]门票进行签到操作吗？",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
		    	$.ajax({
					url:'${ctx}/admin/hdmpgl/doSign',
					type: 'post',
					data: {pk:ret.pk,status:ret.status},
					dataType: 'json',
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
				    },
				    success: function(obj){
				    	goto_query();
				    }
				 });
		    	return true;
			},
			cancel: function () {
				return true;
			}
		});	
	});
	
	$("#print").on("click",function(){
		Global.Dialog.showDialog("print",{
			title:"门票——打印",
			url:"${ctx}/admin/hdmpgl/goPrint",
			//postData:{actNo:ret.actno,ticketNo:ret.ticketno,pk:ret.pk},
			height:600,
			width:750,
			returnFun:goto_query
		});
	});
	
});
function doExcel(){
	var url = "${ctx}/admin/supplierHdmpgl/doExcel";
	doExcelAll(url);
}


</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="hasAuthByCzybh" name="hasAuthByCzybh" value="${hasAuthByCzybh}" />
					<input type="hidden" id="czybh" name="czybh" value="${czybh}" />
					<input type="hidden" id="czylb" name="czylb" value="${czylb}" />
						<ul class="ul-form">
							<li>
								<label>活动名称</label>
								<input type="text" id="actNo" name="actNo" style="width:160px;" value="${hdmpgl.actNo }"/>
							</li>
							<li id="businessMan_li">
								<label>业务员</label>
								<input type="text" id="businessMan" name="businessMan" style="width:160px;" value="${hdmpgl.businessMan }"/>
							</li>
							<li id="provideMan_li">
								<label>发放操作员</label>
								<input type="text" id="provideCZY" name="provideCZY" style="width:160px;" value="${hdmpgl.provideCZY }"/>
							</li>
							<%-- <li>
								<label>发放类型</label>
								<house:xtdm id="provideType" dictCode="TICKETPROVIDE" value="${provideType}"></house:xtdm>                     
							</li> --%>
							<li class="form-validate">
								<label>发放类型</label>
								<house:dict id="provideType" dictCode="" sql="select cbm,cbm+' '+note note from txtdm where id='TICKETPROVIDE'  and ('${hasAuthByCzybh}'='true' or cbm='${provideType}' );  " 
								sqlValueKey="cbm" sqlLableKey="note"  value="${prjConfirmApp.workType12 }"></house:dict>
							</li>
							<li>
								<label>门票状态</label>
								<house:xtdm id="status" dictCode="TICKETSTATUS" ></house:xtdm>                     
							</li>
							<li>
								<label>门票号</label>
								<input type="text" id="ticketNo" name="ticketNo" style="width:160px;  value="${hdmpgl.ticketNo }"/>
							</li>
							<li>
								<label>发放时间从</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
							<li id="department1_li">
								<label>一级部门</label>
								<house:department1 id="department1" onchange="changeDepartment1(this,'department2','${ctx }')" value="${hdmpgl.department1 }"></house:department1>
							</li>
							<li id="department2_li">
								<label>二级部门</label>
								<house:department2 id="department2" dictCode="${hdmpgl.department1 }" value="${hdmpgl.department2 }" onchange="changeDepartment2(this,'department3','${ctx }')"></house:department2>
							</li>
							<li>
								<label>签到时间从</label>
								<input type="text" id="signDateFrom" name="signDateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="signDateTo" name="signDateTo" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
							<li>
								<label>楼盘地址</label>
								<input type="text" id="address" name="address" style="width:160px;"  value="${hdmpgl.address }"/>
							</li>
							<li>
								<label>业主电话</label>
								<input type="text" id="phone" name="phone" style="width:160px;"  value="${hdmpgl.phone }"/>
							</li>
							<li>
								<label>购买品类意向</label>
								<house:xtdm id="planSupplType" dictCode="ACTSPLTYPE"  style="width:160px;" ></house:xtdm>
							</li>
						 	<li class="search-group-shrink">
								<input type="checkbox"
								id="expired_show" name="expired_show"
								value="${hdmpgl.expired}" onclick="hideExpired(this)"
								${hdmpgl.expired!='T' ?'checked':'' }/>
								<p>隐藏过期</p>
								<button type="button" class="btn  btn-sm btn-system "
									onclick="goto_query();">查询</button>
								<button type="button" class="btn btn-sm btn-system "
									onclick="clearForm();">清空</button>
							</li>
						</ul>
				</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
			<!-- panelBar -->
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
						<house:authorize authCode="HDMPGL_CREATE">
							<button type="button" class="btn btn-system " id="create"><span>生成</span> 
							</button>
						</house:authorize>	
						
						<house:authorize authCode="SUPPLIER_HDMPGL_PROVIDE">
							<button type="button" class="btn btn-system "  id="provide"><span>发放</span> 
								</button>
						</house:authorize>
						
						<house:authorize authCode="SUPPLIER_HDMPGL_CALLBACK">
							<button type="button" class="btn btn-system "  id="callBack"><span>收回</span> 
								</button>
						</house:authorize>
						
						<house:authorize authCode="SUPPLIER_HDMPGL_UPDATE">
							<button type="button" class="btn btn-system "  id="update"><span>修改</span>
								</button>
						</house:authorize>
						
						<house:authorize authCode="HDMPGL_SIGN">
							<button type="button" class="btn btn-system "  id="sign"><span>签到</span> 
								</button>
						</house:authorize>
						
						<house:authorize authCode="HDMPGL_DELETE">
							<button type="button" class="btn btn-system " id="del"><span>删除</span> 
							</button>
						</house:authorize>	
						
    			  		<house:authorize authCode="SUPPLIER_HDMPGL_VIEW">
							<button type="button" class="btn btn-system " id="view"><span>查看</span> 
							</button>
						</house:authorize>
						
						<house:authorize authCode="HDMPGL_ORDER_DETAIL">
							<button type="button" class="btn btn-system " id="orderDetail"><span>下定明细查询</span> 
							</button>
						</house:authorize>
						
						<house:authorize authCode="HDMPGL_GIFT_DETAIL">
							<button type="button" class="btn btn-system " id="giftDetail"><span>礼品发放明细查询</span> 
							</button>
						</house:authorize>
						
						<house:authorize authCode="HDMPGL_PRINT">
							<button type="button" class="btn btn-system " id="print"><span>打印</span> 
							</button>
						</house:authorize>
						
						<house:authorize authCode="SUPPLIER_HDMPGL_EXCEL">
							<button type="button" class="btn btn-system "  onclick="doExcel()" >
								<span>导出excel</span>
							</button>
						</house:authorize>	
					</div>
				</div>
					<!-- panelBar End -->
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
	</body>	
</html>
