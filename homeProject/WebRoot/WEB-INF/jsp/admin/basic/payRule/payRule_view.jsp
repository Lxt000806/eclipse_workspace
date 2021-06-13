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
	<title>付款规则管理编辑</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 

/* $(function() {
	$("#leaderCode").openComponent_employee();	
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			descr:{  
				validators: {  
					notEmpty: {  
						message: '专盘名称不能为空'  
					}
				}  
			},
		},
		submitButtons : 'input[type="submit"]'
	});
});	 */ 
$(function() {
	var lastCellRowId;
	var gridOption = {
		url:"${ctx}/admin/payRule/goDetailJqGrid",
		postData:{no:"${payRule.no}"},
		height:$(document).height()-$("#content-list").offset().top-80,
		rowNum:10000000,
		colModel : [
			{name:'paynum', index:'paynum', width:80, label:'付款期数', sortable:true ,align:"left"},
			{name:'payexpr', index:'payexpr', width:270, label:'付款金额公式', sortable:true ,align:"left"},
			{name:'isrcvdesignfee', index:'isrcvdesignfee', width:80, label:'是否受设计费', sortable:true ,align:"left",hidden:true},
			{name:'isrcvdesignfeedescr', index:'isrcvdesignfeedescr', width:95, label:'是否收设计费', sortable:true ,align:"left"},
			{name:'chgper', index:'chgper', width:90, label:'增减计算比例', sortable:true ,align:"left"},
			{name:'tiptypedescr', index:'tiptypedescr', width:100, label:'缴款提醒类型', sortable:true ,align:"left"},
            {name:'tiptype', index:'tiptype', width:80, label:'缴款提醒类型', sortable:true ,align:"left",hidden:true},
            {name:'confirmprjitem', index:'confirmprjitem', width:80, label:'验收项目', sortable:true ,align:"left",hidden:true},
            {name:'confirmprjitemdescr', index:'confirmprjitemdescr', width:130, label:'验收项目', sortable:true ,align:"left"},
            {name:'worktype12descr', index:'worktype12descr', width:100, label:'提醒工种', sortable:true ,align:"left"},
            {name:'worktype12', index:'worktype12', width:100, label:'提醒工种', sortable:true ,align:"left", hidden: true},
            {name:'tipadddays', index:'tipadddays', width:100, label:'提醒增加天数', sortable:true ,align:"left"},
            {name:'payremark', index:'payremark', width:150, label:'付款说明', sortable:true ,align:"left"},
            {name:'paytip', index:'paytip', width:150, label:'缴款提示', sortable:true ,align:"left"},
            {name:'iscalccommi', index:'iscalccommi', width:100, label:'业绩提成标记', sortable:true ,align:"left", hidden: true},
            {name:'iscalccommidescr', index:'iscalccommidescr', width:100, label:'业绩提成标记', sortable:true ,align:"left"},
            {name:'businesscommiprovideper', index:'businesscommiprovideper', width:120, label:'业务提成发放比例', sortable:true ,align:"right"},
            {name:'businesssubsidyprovideper', index:'businesssubsidyprovideper', width:120, label:'业务补贴发放比例', sortable:true ,align:"right"},
            {name:'businessmultipleprovideper', index:'businessmultipleprovideper', width:120, label:'业务倍数发放比例', sortable:true ,align:"right"},
            {name:'designcommiprovideper', index:'designcommiprovideper', width:120, label:'设计提成发放比例', sortable:true ,align:"right"},
            {name:'newdesigncommiprovideper', index:'newdesigncommiprovideper', width:145, label:'新设计师发放比例', sortable:true ,align:"right"},       
            {name:'designsubsidyprovideper', index:'designsubsidyprovideper', width:120, label:'设计补贴发放比例', sortable:true ,align:"right"},
            {name:'designmultipleprovideper', index:'designmultipleprovideper', width:120, label:'设计倍数发放比例', sortable:true ,align:"right"},
            {name:'iscalcscenedesigncommi', index:'iscalcscenedesigncommi', width:130, label:'现场设计师提成计算', sortable:true ,align:"left", hidden: true},
            {name:'iscalcscenedesigncommidescr', index:'iscalcscenedesigncommidescr', width:120, label:'现场设计师提成计算', sortable:true ,align:"left"},
            {name:'scenedesignprovideper', index:'scenedesignprovideper', width:140, label:'现场设计师发放比例', sortable:true ,align:"right"},
            {name:'iscalcdesignfeecommi', index:'iscalcdesignfeecommi', width:110, label:'设计费提成计算', sortable:true ,align:"left", hidden: true},
            {name:'designfeecommiprovideper', index:'designfeecommiprovideper', width:145, label:'设计费提成发放比例', sortable:true ,align:"right"},
            {name:'iscalcdesignfeecommidescr', index:'iscalcdesignfeecommi', width:110, label:'设计费提成计算', sortable:true ,align:"left"},
            {name:'iscalcdrawfeecommi', index:'iscalcdrawfeecommi', width:100, label:'绘图费提成计算', sortable:true ,align:"left", hidden: true},
            {name:'iscalcdrawfeecommidescr', index:'iscalcdrawfeecommidescr', width:100, label:'绘图费提成计算', sortable:true ,align:"left"},
            {name:'drawfeecommiprovideper', index:'drawfeecommiprovideper', width:145, label:'绘图费提成发放比例', sortable:true ,align:"right"},
            {name:'iscalcmaincommi', index:'iscalcmaincommi', width:120, label:'主材提成计算', sortable:true ,align:"left", hidden: true},
            {name:'iscalcmaincommidescr', index:'iscalcmaincommidescr', width:100, label:'主材提成计算', sortable:true ,align:"left"},
            {name:'businessmaincommiprovideper', index:'businessmaincommiprovideper', width:145, label:'业务主材提成发放比例', sortable:true ,align:"right"},
            {name:'iscalcintcommi', index:'iscalcintcommi', width:120, label:'集成提成计算', sortable:true ,align:"left", hidden: true},
            {name:'iscalcintcommidescr', index:'iscalcintcommidescr', width:90, label:'集成提成计算', sortable:true ,align:"left"},
            {name:'businessintcommiprovideper', index:'businessintcommiprovideper', width:145, label:'集成提成发放比例', sortable:true ,align:"right"},  
            {name:'iscalcsoftcommi', index:'iscalcsoftcommi', width:120, label:'软装提成计算', sortable:true ,align:"left", hidden: true},
            {name:'iscalcsoftcommidescr', index:'iscalcsoftcommidescr', width:90, label:'软装提成计算', sortable:true ,align:"left"},
            {name:'businesssoftcommiprovideper', index:'businesssoftcommiprovideper', width:145, label:'软装提成发放比例', sortable:true ,align:"right"},  
            {name:'iscalcbasepersonalcommi', index:'iscalcbasepersonalcommi', width:145, label:'计算基础个性化项目提成', sortable:true ,align:"left", hidden: true}, 
            {name:'iscalcbasepersonalcommidescr', index:'iscalcbasepersonalcommidescr', width:145, label:'计算基础个性化项目提成', sortable:true ,align:"left"}, 
            {name:'basepersonalcommiprovideper', index:'basepersonalcommiprovideper', width:145, label:'基础个性化项目发放比例', sortable:true ,align:"right"}, 
		],
		loadonce: true,
	};
		var detailJson = Global.JqGrid.allToJson("dataTable","itcode");
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	
	$("#addView").on("click",function(){
		var ids = $("#dataTable").jqGrid('getDataIDs');
		var rowId=$('#dataTable').jqGrid('getGridParam','selrow');
		var param =$("#dataTable").jqGrid('getRowData',rowId);
		Global.Dialog.showDialog("addView",{
			title:"付款规则——查看",
		  	url:"${ctx}/admin/payRule/goAddView",
		  	postData:{detailXml:JSON.stringify(param)},
			height: 670,
		  	width:700,
		});
	});	
});
</script>
</head>
	<body>
<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="page_form" class="form-search">
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li >
								<label>规则编号</label>
								<input type="text" id="code" name="code" style="width:160px;" value="${payRule.no }" readonly="readonly"/>
							</li>
							<li>
								<label>客户类型</label>
								<house:DataSelect id="custType" className="CustType" keyColumn="code" valueColumn="desc1"
								 value="${payRule.custType }" ></house:DataSelect>
							</li>
							<li><label>合同造价表类型</label> <house:xtdm
								id="contractFeeRepType" dictCode="FEEREPTYPE" value="${payRule.contractFeeRepType }"
								></house:xtdm>
							</li>
							<li class="form-validate">
								<label>分期付款方式</label>
								<house:xtdm id="payType" dictCode="TIMEPAYTYPE" value="${payRule.payType }"></house:xtdm>                     
							</li>
							<li class="form-validate">
								<label>设计费类型</label>
								<house:xtdm id="designFeeType" dictCode="DESIGNFEETYPE" value="${payRule.designFeeType }"></house:xtdm>                     
							</li>
							<li>
								<label>过期</label>
								<input type="checkbox" id="expired_show" name="expired_show"
						 		 onclick="checkExpired(this)" ${payRule.expired=="T"?"checked":""}/>
						 	</li>	
						</ul>	
					</form>
				</div>
			</div>
			<div class="btn-panel" >
    			<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " id="addView" >
						<span>查看</span>
					</button>
					<button type="button" class="btn btn-system" onclick="doExcelNow('付款规则明细')">输出到excel</button>
				</div>
			</div>	
			<div class="container-fluid" >  
			<ul class="nav nav-tabs" > 
		        <li class="active"><a href="#tab_detail" data-toggle="tab">规则明细</a></li>
		    </ul> 
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>	
			</div>
		</div>
	</body>	
</html>
