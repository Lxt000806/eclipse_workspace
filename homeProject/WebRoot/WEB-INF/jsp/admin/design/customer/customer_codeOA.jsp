<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--OA客户编号</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
$(function(){
if('${customer.disabledEle}'){
	var arr='${customer.disabledEle}'.split(",");
	$.each(arr,function(k,v){
		$("#"+v).attr("disabled","disabled");
	});
}

//mobileHidden 存在时  能根据电话查询
if('${customer.mobileHidden}'==''||'${customer.mobileHidden}'==null){
	$("#mobile1").css("display","none");
	$("#mobile").css("display","none");
}
if('${customer.mobileHidden}'=='4'){
	document.getElementById("status").disabled=true;
}


var postData=$("#page_form").jsonForm();
postData.status="${customer.status}";
	
	//适配供货管理——生产进度使用 modify by zb on 20200316
	var colModel_base = [
		{name : 'code',index : 'code',width : 80,label:'客户编号',sortable : true,align : "left"},
		{name : 'descr',index : 'descr',width : 80,label:'客户名称',sortable : true,align : "left"},
		{name : 'custtypedescr',index : 'custtypedescr',width : 80,label:'客户类型',sortable : true,align : "left"},
		{name : 'address',index : 'address',width : 170,label:'楼盘',sortable : true,align : "left"},
		{name : 'buildernum',index : 'buildernum',width : 80,label:'楼栋号',sortable : true,align : "left"},
		{name : 'realaddress',index : 'realaddress',width : 170,label:'实际地址',sortable : true,align : "left"},
		{name : 'layoutdescr',index : 'layoutdescr',width : 70,label:'户型',sortable : true,align : "left"},
		{name : 'area',index : 'area',width : 70,label:'面积',sortable : true,align : "left"},
		{name : 'statusdescr',index : 'statusdescr',width : 80,label:'客户状态',sortable : true,align : "left"},
	],
	colModel_extra = [
		{name : 'checkstatusdescr',index : 'checkstatusdescr',width : 90,label:'客户结算状态',sortable : true,align : "left"},
		{name : 'custcheckdate',index : 'custcheckdate',width : 85,label:'客户结算时间',sortable : true,align : "left",formatter: formatTime},
		{name : 'designman',index : 'designman',width : 80,label:'设计师',sortable : true,align : "left",hidden:true},
		{name : 'contractfee',index : 'contractfee',width : 100,label:'contractfee',sortable : true,align : "left",hidden:true},
		{name : 'designmandescr',index : 'designmandescr',width : 90,label:'设计师',sortable : true,align : "left"},
		{name : 'businessmandescr',index : 'businessmandescr',width : 90,label:'业务员',sortable : true,align : "left"},
		{name : 'layout',index : 'layout',width : 100,label:'户型编号',sortable : true,align : "left",hidden:true},
		{name : 'ispushcust',index : 'ispushcust',width : 100,label:'isPushCust',sortable : true,align : "left",hidden:true},
		{name : 'designstyle',index : 'designstyle',width : 100,label:'风格编号',sortable : true,align : "left",hidden:true},
		{name : 'buildercode',index : 'buildercode',width : 100,label:'项目编号',sortable : true,align : "left",hidden:true},
		{name : 'buildercodedescr',index : 'buildercodedescr',width : 90,label:'项目名称',sortable : true,align : "left",hidden:true},
		{name : 'projectman',index : 'projectman',width : 200,label:'项目经理编号',sortable : true,align : "left",hidden:true},
		{name : 'projectmandescr',index : 'projectmandescr',width : 100,label:'项目经理',sortable : true,align : "left"},
		{name : 'projectmanphone',index : 'projectmanphone',width : 200,label:'项目经理电话',sortable : true,align : "left",hidden:true},
		{name : 'documentno',index : 'documentno',width : 200,label:'档案号',sortable : true,align : "left",hidden:true	},
		{name : 'constructstatus',index :'constructstatus',width : 200,label:'施工状态',sortable : true,align : "left" ,hidden:true},	
		{name : 'setdate',index : 'setdate',width : 80,label:'下定时间',sortable : true,align : "left",formatter:formatDate,hidden:true},
		{name : 'signdate',index : 'signdate',width : 80,label:'签订时间',sortable : true,align : "left",formatter:formatDate,hidden:true},
		{name : 'dept1descr',index : 'dept1descr',width : 80,label:'项目经理一级部门',sortable : true,align : "left",hidden:true},
		{name : 'dept2descr',index : 'dept2descr',width : 80,label:'项目经理二级部门',sortable : true,align : "left",hidden:true},
		{name : "custtype",index : "custtype",width : 80,label:"custtype",sortable : true,align : "left",hidden:true},
		{name : "isaddallinfo",index : "isaddallinfo",width : 80,label:"isaddallinfo",sortable : true,align : "left",hidden:true},
		{name : "mobile1",index : "mobile1",width : 80,label:"mobile1",sortable : true,align : "left",hidden:true},
		{name : "checkstatus",index : "checkstatus",width : 80,label:"checkstatus",sortable : true,align : "left",hidden:true},
		{name : "iswateritemctrl",index : "iswateritemctrl",width : 80,label:"iswateritemctrl",sortable : true,align : "left",hidden:true},
		{name : "iswateritemctrldescr",index : "iswateritemctrldescr",width : 80,label:"iswateritemctrldescr",sortable : true,align : "left",hidden:true},
		{name : "status",index : "status",width : 80,label:"客户状态",sortable : true,align : "left",hidden:true},
		{name : "perfpk",index : "perfpk",width : 80,label:"业绩pk",sortable : true,align : "left",hidden:true},
		{name : "statusdescr",index : "statusdescr",width : 80,label:"客户状态描述",sortable : true,align : "left",hidden:true},
		{name : "enddescr",index : "enddescr",width : 80,label:"结束代码描述",sortable : true,align : "left",hidden:true},
		{name : "ispubreturndescr",index : "ispubreturndescr",width : 80,label:"是否对公退款",sortable : true,align : "left",hidden:true},
		{name : "returnamount",index : "returnamount",width : 80,label:"退款金额",sortable : true,align : "left",hidden:true},
		{name : "iswateritemctrl",index : "iswateritemctrl",width : 80,label:"是否水电发包",sortable : true,align : "left",hidden:true},
		{name : "company",index : "company",width : 80,label:"公司代码",sortable : true,align : "left",hidden:true},
		{name : "designfee",index : "designfee",width : 80,label:"设计费",sortable : true,align : "left",hidden:true},
		{name : "realdesignfee",index : "realdesignfee",width : 80,label:"实收设计费",sortable : true,align : "left",hidden:true},
		{name : "tax",index : "tax",width : 80,label:"税金",sortable : true,align : "left",hidden:true},
		{name : "companydescr",index : "companydescr",width : 230,label:"公司名称",sortable : true,align : "left",},
		{name : "endcode",index : "endcode",width : 230,label:"结束代码",sortable : true,align : "left",},
		{name : "confirmbegin",index : "confirmbegin",width : 130,label:"开工时间",sortable : true,align : "left",formatter:formatDate},
	      {name : "isinitsign",index : "isinitsign",width : 80,label:"草签",sortable : true,align : "left", hidden: true},
	];
	var colModel = colModel_base.concat(colModel_extra);
	if ("0" == "${customer.showAll}") colModel = colModel_base;

    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		//url:"${ctx}/admin/customer/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',   
		colModel :  colModel,
        ondblClickRow:function(rowid,iRow,iCol,e){
			var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
        	Global.Dialog.returnData = selectRow;
        	Global.Dialog.closeDialog();
        }
	});
	$("#address").focus();
});

function clearForm(){
	if('${customer.disabledEle}'.indexOf('status_NAME')==-1){
		$("#page_form input[type='text'][id!='endCode']").val('');
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	} 
	else $("#page_form input[type='text'][id!='status_NAME'][id!='endCode']").val('');
	$("#page_form select").val('');
}
function goto_query(){
	var postData = $("#page_form").jsonForm();
	$("#dataTable").jqGrid("setGridParam", {
    	url: "${ctx}/admin/customer/goJqGridForOA",
    	postData: postData
  	}).trigger("reloadGrid");
}

</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
				<input type="hidden" id="constructStatus" name="constructStatus" value="${customer.constructStatus }" />
				<input type="hidden" id="purchStatus" name="purchStatus" value="${customer.purchStatus }" />
				<input type="hidden" id="custWorkApp" name="custWorkApp" value="${customer.custWorkApp }" />
				<input type="hidden" id="mobileHidden" name="mobileHidden"  value="${customer.mobileHidden }" />
				<input type="hidden" id="ignoreCustRight" name="ignoreCustRight"  value="${customer.ignoreCustRight }" />
				<input type="hidden" id="designDemo" name="designDemo"  value="${customer.designDemo }" />
				<input type="hidden" id="empCode" name="empCode"  value="${customer.empCode }" />
				<input type="hidden" id="authorityCtrl" name="authorityCtrl"  value="${customer.authorityCtrl }" />
				<input type="hidden" id="designMan" name="designMan"  value="${customer.designMan }" />
				<input type="hidden" id="endCode" name="endCode"  value="${customer.endCode}" />
				<input type="hidden" id="isAddAllInfo" name="isAddAllInfo"  value="${customer.isAddAllInfo}" />
					<ul class="ul-form">
						<li>
							<label>客户编号</label>
							<input type="text" id="code" name="code"  value="${customer.code }" />
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address"  value="${customer.address }" />
						</li>
						<li id="loadMore" >
							<button type="button" class="btn btn-system btn-sm" onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
						</li>
				</ul>
			</form>
		</div>
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


