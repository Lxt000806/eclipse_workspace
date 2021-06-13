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
<title>项目经理结算</title>
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
		url:"${ctx}/admin/prjManCheck/goJqGrid",
		postData:{isOutSet:"1"},
		ondblClickRow: function(){
             goCheck('V');
        },
		height:$(document).height()-$("#content-list").offset().top-100,
		styleUI: 'Bootstrap',
		colModel : [
		    {name: "code", index: "code", width: 70, label: "客户编号", sortable: true, align: "left", count: true},
			{name: "documentno", index: "documentno", width: 70, label: "档案编号", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 70, label: "客户名称", sortable: true, align: "left"},
			{name: "custtypetype", index: "custtypetype", width: 80, label: "客户类型", sortable: true, align:"left",hidden:true},
			{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
			{name: "layoutdescr", index: "layoutdescr", width: 50, label: "户型", sortable: true, align: "left"},
			{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align:"left"},
			{name: "statusdescr", index: "statusdescr", width: 70, label: "客户状态", sortable: true, align: "left"},
			{name: "checkstatusdescr", index: "checkstatusdescr", width: 80, label: "结算状态", sortable: true, align: "left"},
			{name: "projectmandescr", index: "projectmandescr", width: 70, label: "项目经理", sortable: true, align: "left"},
			{name: "haschangedsupervisor", index: "haschangedsupervisor", width: 70, label: "更换监理", sortable: true, align: "left"},
			{name: "designmandescr", index: "designmandescr", width: 70, label: "设计师", sortable: true, align: "left"},
			{name: "businessmandescr", index: "businessmandescr", width: 70, label: "业务员", sortable: true, align: "left"},
			{name: "iswateritemctrldescr", index: "iswateritemctrldescr", width: 90, label: "水电发包工人", sortable: true, align: "left"},
		    {name: "iswaterctrldescr", index: "iswaterctrldescr", width: 80, label: "防水发包工人", sortable: true, align: "left"},
			/*  项目经理结算数据 */
			{name: "basefee_dirct", index: "basefee_dirct", width: 80, label: "基础直接费", sortable: true, align: "right", sum: true},
			{name: "basechg", index: "basechg", width: 120, label: "基础增减（优惠前）", sortable: true, align: "right", sum: true},
			{name: "mainamount", index: "mainamount", width: 65, label: "主材预算", sortable: true, align: "right", sum: true},
			{name: "chgmainamount", index: "chgmainamount", width: 65, label: "主材增减", sortable: true, align: "right", sum: true},
			{name: "maincoopfee", index: "maincoopfee", width: 75, label: "主材配合费", sortable: true, align: "right", sum: true},
			/*  项目经理结算数据end  */
			/* 省到家项目经理结算数据begin  */
			{name: "innerarea", index: "innerarea", width: 53, label: "套内面积", sortable: true, align: "right"},
			{name: "mainsetfee", index: "mainsetfee", width: 75, label: "套餐费", sortable: true, align: "right", sum: true},
			{name: "longfee", index: "longfee", width: 75, label: "远程费", sortable: true, align: "right", sum: true},
			{name: "allsetadd", index: "allsetadd", width: 97, label: "套餐外基础增项", sortable: true, align: "right", sum: true},
			{name: "allsetminus", index: "allsetminus", width: 76, label: "套餐内减项", sortable: true, align: "right", sum: true},
			{name: "allitemamount", index: "allitemamount", width: 65, label: "材料预算", sortable: true, align: "right", sum: true},
			{name: "allmanagefee_base", index: "allmanagefee_base", width: 72, label: "基础管理费", sortable: true, align: "right", sum: true},
			{name: "upgwithhold", index: "upgwithhold", width: 72, label: "升级扣项", sortable: true, align: "right", sum: true},
			{name: "basecost", index: "basecost", width: 65, label: "基础支出", sortable: true, align: "right", sum: true},
			{name: "maincost_js", index: "maincost_js", width: 103, label: "主材支出(结算)", sortable: true, align: "right", sum: true},
			/*  省到家项目经理结算数据end */ 
			{name: "basectrlamt", index: "basectrlamt", width: 65, label: "发包总价", sortable: true, align: "right", sum: true},
			{name: "projectctrladj", index: "projectctrladj", width: 65, label: "发包补贴", sortable: true, align: "right"},
			{name: "cost", index: "cost", width: 60, label: "支出", sortable: true, align: "right", sum: true},
			{name: "withhold", index: "withhold", width: 60, label: "预扣", sortable: true, align: "right", sum: true},
			{name: "recvfee", index: "recvfee", width: 60, label: "已领", sortable: true, align: "right", sum: true},
			{name: "qualityfee", index: "qualityfee", width: 60, label: "质保金", sortable: true, align: "right", sum: true},
			{name: "accidentfee", index: "accidentfee", width: 65, label: "意外险", sortable: true, align: "right", sum: true},
			{name: "mustamount", index: "mustamount", width: 65, label: "应发金额", sortable: true, align: "right", sum: true},
			{name: "realamount", index: "realamount", width: 65, label: "实发金额", sortable: true, align: "right", sum: true},
			{name: "isprovide", index: "isprovide", width: 65, label: "是否发放", sortable: true, align: "left"},
			{name: "provideno", index: "provideno", width: 80, label: "发放编号", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 140, label: "备注", sortable: true, align: "left"},
			{name: "appczydescr", index: "appczydescr", width: 60, label: "申请人", sortable: true, align: "left"},
			{name: "date", index: "date", width: 120, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
			{name: "confirmczydescr", index: "confirmczydescr", width: 70, label: "确认人", sortable: true, align: "left"},
			{name: "confirmdate", index: "confirmdate", width: 120, label: "确认日期", sortable: true, align: "left", formatter: formatTime},
			{name: "isitemupdescr", index: "isitemupdescr", width: 90, label: "材料免费升级", sortable: true, align: "left"},
			{name: "issalaryconfirmdescr", index: "issalaryconfirmdescr", width: 80, label: "工资确认", sortable: true, align: "left"},
			{name: "salaryconfirmdate", index: "salaryconfirmdate", width: 120, label: "工资确认日期", sortable: true, align: "left", formatter: formatTime},
			{name: "custcheckdate", index: "custcheckdate", width: 120, label: "客户结算日期", sortable: true, align: "left", formatter: formatDate}, // checkoutdate工地结算时间改成custcheckdate
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 80, label: "操作", sortable: true, align: "left"},
			{name: "basefee", index: "basefee", width: 80, label: "基础预算", sortable: true, align: "left",hidden: true},
			{name: "isitemup", index: "isitemup", width: 80, label: "免费升级", sortable: true, align: "left",hidden: true},
			{name: "prjctrltype", index: "prjctrltype", width: 80, label: "发包方式", sortable: true, align:"left",hidden: true},
			//{name: "checkoutdate", index: "checkoutdate", width: 120, label: "客户结算日期", sortable: true, align: "left", formatter: formatTime},
			{name: "designfixdutyamount", index: "designfixdutyamount", width: 120, label: "设计定制金额", sortable: true, align: "right", hidden: true}
        ],
        gridComplete:function(){
	  		 if ($("#isOutSet").val() == '1') {
	        	$("#btnWithhold").show(); 
	        	$("#btnItemUp").hide();	
	        	$("#dataTable").jqGrid('showCol', "basefee_dirct");
	        	$("#dataTable").jqGrid('showCol', "basechg");
	        	$("#dataTable").jqGrid('showCol', "mainamount");
	        	$("#dataTable").jqGrid('showCol', "chgmainamount");
	        	$("#dataTable").jqGrid('showCol', "maincoopfee");
	        	
	        	$("#dataTable").jqGrid('hideCol', "innerarea");
	        	$("#dataTable").jqGrid('hideCol', "mainsetfee");
	        	$("#dataTable").jqGrid('hideCol', "longfee");
	        	$("#dataTable").jqGrid('hideCol', "allsetadd");
	        	$("#dataTable").jqGrid('hideCol', "allsetminus");
	        	$("#dataTable").jqGrid('hideCol', "allitemamount");
	        	$("#dataTable").jqGrid('hideCol', "allmanagefee_base");
	        	$("#dataTable").jqGrid('hideCol', "upgwithhold");
	        	$("#dataTable").jqGrid('hideCol', "basecost");
	        	$("#dataTable").jqGrid('hideCol', "maincost_js");
	        	$("#dataTable").jqGrid('hideCol', "isitemupdescr");	
	        	
	         } else if ($("#isOutSet").val() == '2'){
	        	$("#dataTable").jqGrid('hideCol', "basefee_dirct");
	        	$("#dataTable").jqGrid('hideCol', "basechg");
	        	$("#dataTable").jqGrid('hideCol', "mainamount");
	        	$("#dataTable").jqGrid('hideCol', "chgmainamount");
	        	$("#dataTable").jqGrid('hideCol', "maincoopfee");
	        	
	        	$("#dataTable").jqGrid('showCol', "innerarea");
	        	$("#dataTable").jqGrid('showCol', "mainsetfee");
	        	$("#dataTable").jqGrid('showCol', "longfee");
	        	$("#dataTable").jqGrid('showCol', "allsetadd");
	        	$("#dataTable").jqGrid('showCol', "allsetminus");
	        	$("#dataTable").jqGrid('showCol', "allitemamount");
	        	$("#dataTable").jqGrid('showCol', "allmanagefee_base");
	        	$("#dataTable").jqGrid('showCol', "upgwithhold");
	        	$("#dataTable").jqGrid('showCol', "basecost");
	        	$("#dataTable").jqGrid('showCol', "maincost_js");
	        	$("#dataTable").jqGrid('showCol', "isitemupdescr");
	         }else{
	         	$("#dataTable").jqGrid('showCol', "basefee_dirct");
	        	$("#dataTable").jqGrid('showCol', "basechg");
	        	$("#dataTable").jqGrid('showCol', "mainamount");
	        	$("#dataTable").jqGrid('showCol', "chgmainamount");
	        	$("#dataTable").jqGrid('showCol', "maincoopfee");
	        	$("#dataTable").jqGrid('showCol', "innerarea");
	        	$("#dataTable").jqGrid('showCol', "mainsetfee");
	        	$("#dataTable").jqGrid('showCol', "longfee");
	        	$("#dataTable").jqGrid('showCol', "allsetadd");
	        	$("#dataTable").jqGrid('showCol', "allsetminus");
	        	$("#dataTable").jqGrid('showCol', "allitemamount");
	        	$("#dataTable").jqGrid('showCol', "allmanagefee_base");
	        	$("#dataTable").jqGrid('showCol', "upgwithhold");
	        	$("#dataTable").jqGrid('showCol', "basecost");
	        	$("#dataTable").jqGrid('showCol', "maincost_js");
	        	$("#dataTable").jqGrid('showCol', "isitemupdescr");
	         } 

		},
		onSelectRow: function(id){ 
			var rowData = $("#dataTable").jqGrid('getRowData',id);
		 	  if (rowData.prjctrltype== '1'){
		 	  	$("#btnWithhold").show(); 
	        	$("#btnItemUp").hide();
		 	  }else{
		 	  	$("#btnWithhold").hide();
	        	$("#btnItemUp").show();	
		 	  }   
  		},                         
	 });
	 $("#code").openComponent_customer(); 
	 $("#projectMan").openComponent_employee();
});  
function goCheck(sState){	
   	var row = selectDataTableRow();
   	var sStateDescr="结算";
   	if(sState=="GC"){
   		sStateDescr="结算完成";
   	}
   	if(sState=="GB"){
   		sStateDescr="结算退回";
   	}
   	if(sState=="V"){
   		sStateDescr="查看";
   	}
	if(!row){
		art.dialog({content: "请选择一条记录进行修改操作！",width: 200});
		return false;
	}
	$.ajax({
		url : "${ctx}/admin/prjManCheck/goVerifyCheck",
		data : {code: row.code,m_umState:sState},
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		dataType: "json",
		type: "post",
		cache: false,
		error: function(){
	       	art.dialog({
				content: '出现异常，无法修改'
			});
	    },
	    success: function(obj){
	   		if(obj.rs){
	        	Global.Dialog.showDialog("prjCheck", {
	        	 	title: "项目经理结算--"+sStateDescr,
	        	    url: "${ctx}/admin/prjManCheck/goCheck",
	        	    height: 700,
	        	    width: 1250,               
	        	    postData:{custCode:row.code,baseFeeDirct:row.basefee_dirct,
	        	   		mainAmount:row.mainamount,baseFee:row.basefee,chgMainAmount:row.chgmainamount,m_umState:sState,
	        			projectCtrlAdj:row.projectctrladj,mainCost:row.maincost_js,designFixDutyAmount:row.designfixdutyamount
	        	    }, 
					close:function(){
				  	 	goto_query();
				    },
	        	}); 			    		
	    	}else {
	    		art.dialog({
					content: obj.msg
				});
	    	}
		}
	});
}
function goWithhold(){	
   	var row = selectDataTableRow();
    if (row) {    	
	    $.ajax({
			url : "${ctx}/admin/prjManCheck/goVerifyCheck",
			data : {code: row.code,m_umState:"A"},
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			dataType: "json",
			type: "post",
			cache: false,
			error: function(){
		       	art.dialog({
					content: '出现异常，无法修改'
				});
		    },
		    success: function(obj){
		   		if(obj.rs){
		        	  Global.Dialog.showDialog("goPreInput",{
						title:"项目经理结算--预扣录入",
						url:"${ctx}/admin/prjManCheck/goPreInput",
						postData:{custCode:row.code,baseFeeDirct:row.basefee_dirct ,
				        	   		mainAmount:row.mainamount,baseFee:row.basefee,chgMainAmount:row.chgmainamount,m_umState:"A"
				        	    },
						height: 450,
						width:950,
						returnFun:function(){
							 goto_query();
						}
						
					});			    		
		    	}else {
		    		art.dialog({
						content: obj.msg
					});
		    	}
			}
		});  
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function goItemUp(){	
   	var row = selectDataTableRow();
	if(!row){
		art.dialog({content: "请选择一条记录进行修改操作！",width: 200});
		return false;
	}
	$.ajax({
		url : "${ctx}/admin/prjManCheck/goVerifyItemUp",
		data : {code: row.code,isItemUp:row.isitemup},
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		dataType: "json",
		type: "post",
		cache: false,
		error: function(){
	       	art.dialog({
				content: '出现异常，无法修改'
			});
	    },
	    success: function(obj){
	   		if(obj.rs){
	        	Global.Dialog.showDialog("updateWHCheckOut", {
	        	 	title: "项目经理结算--材料升级标记",
	        	    url: "${ctx}/admin/prjManCheck/goItemUp",
	        	    height: 270,
	        	    width: 400,               
	        	    postData:{code:row.code,isItemUp:row.isitemup},
	        	    returnFun: goto_query
	        	}); 			    		
	    	}else {
	    		art.dialog({
					content: obj.msg
				});
	    	}
		}
	});
}
function totalQualityFee(){	
	Global.Dialog.showDialog("DetailView",{			
		title:"质保金汇总",
		url:"${ctx}/admin/prjManCheck/goTotalQualityFee",
		height: 700,
		width:900,
	});
}
 //批量打印
function qPrint(){
	Global.Dialog.showDialog("goQPrint",{
 	  	title: "批量打印",
 	  	url: "${ctx}/admin/prjManCheck/goQPrint",
 	  	postData: {isOutSet: $("#isOutSet").val()},
 	    height: 720,
		width:1200,
	});       	
}
//打印
function print(){
	var row = selectDataTableRow();
	if(!row){
		art.dialog({content: "请选择一条记录进行打印操作！",width: 200});
		return false;
	} 
// 	var	reportName="prjManCheck.jasper";
// 	if (row.custtypetype=="2"){
// 		reportName="prjManCheck_sdj.jasper";
// 	}
    Global.Print.showPrint("prjManCheck_sdj.jasper", {
		CustCode: $.trim(row.code) ,
		LogoPath : "<%=basePath%>jasperlogo/",
		SUBREPORT_DIR: "<%=jasperPath%>" 
	});
}
//导出EXCEL
function doExcel(){
  	var url = "${ctx}/admin/prjManCheck/doExcel";
  	doExcelAll(url);
}
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#checkStatus").val('');
	$.fn.zTree.getZTreeObj("tree_checkStatus").checkAllNodes(false);
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
					<li><label>客户编号</label> <input type="text" id="code" name="code" />
					</li>
					<li><label>楼盘</label> <input type="text" id="address" name="address" />
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType" selectedValue="${customer.custType }"></house:custTypeMulit>
					</li>
					<li>
						<label>项目经理</label>
						<input type="text" id="projectMan" name="projectMan" style="width:160px;" />
					</li>
					<li><label>客户结算状态</label> <house:xtdmMulit id="checkStatus" dictCode="CheckStatus" unShowValue="1"></house:xtdmMulit>
					</li>
					<li> <label>发包方式</label>
						 <house:xtdm id="isOutSet" dictCode="CustPrjCtrlType"></house:xtdm>
					</li>
					<li>
						<label>结算日期从</label>
						<input type="text" id="custCheckDateFrom" name="custCheckDateFrom" class="i-date" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="custCheckDateTo" name="custCheckDateTo" class="i-date" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>工资发放确认</label>
						<house:xtdm id="isSalaryConfirm" dictCode="YESNO" ></house:xtdm>
					</li>
					<li><label></label> <input type="checkbox" id="expired_show" name="expired_show"
						value="${cutomer.expired}" onclick="hideExpired(this)" ${cutomer.expired!='T' ?'checked':'' }/>隐藏过期&nbsp;
						<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>

		<div class="clear_float"></div>

		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="PRJMANCHECK_PREINPUT">
					<button  id="btnWithhold" type="button" class="btn btn-system " onclick="goWithhold()">预扣录入</button>
				</house:authorize>
				<house:authorize authCode="PRJMANCHECK_CHECK">
					<button type="button" class="btn btn-system " onclick="goCheck('G')">结算</button>
				</house:authorize>         
				<house:authorize authCode="PRJMANCHECK_CHECKCOMPLETE">
					<button type="button" class="btn btn-system " onclick="goCheck('GC')">结算完成</button>
				</house:authorize>
				<house:authorize authCode="PRJMANCHECK_CHECKRETURN">
					<button type="button" class="btn btn-system " onclick="goCheck('GB')">结算退回</button>
				</house:authorize>
				<house:authorize authCode="PRJMANCHECK_ITEMMARK">
					<button id="btnItemUp"  type="button" class="btn btn-system " onclick="goItemUp()">材料升级标记</button>
				</house:authorize>
				<button type="button" class="btn btn-system " onclick="totalQualityFee()">质保金汇总</button>
				<house:authorize authCode="PRJMANCHECK_VIEW">
					<button type="button" class="btn btn-system " onclick="goCheck('V')">查看</button>
				</house:authorize>
			    <button id="Print" type="button" class="btn btn-system " onclick="print()">打印</button>
				<button type="button" class="btn btn-system " onclick="qPrint()">批量打印</button>
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


