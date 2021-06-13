<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>SignIn列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
function goto_query(){
	var tableId;
	 if ($("#statistcsMethod").val() == null){
	 		art.dialog({content: "请选择统计方式",width: 200});
			return false;
	 }
	else if ($("#statistcsMethod").val() == '1') {
		tableId = 'dataTable';
		$("#content-list-groupBySignCZY").hide();
		$("#content-list-groupBySignInType2").hide();
		$("#content-list").show();
	} else if ($("#statistcsMethod").val() == '2'){
		tableId = 'dataTableGroupBySignCZY';
		$("#content-list").hide();
		$("#content-list-groupBySignInType2").hide();
		$("#content-list-groupBySignCZY").show();
	} else if ($("#statistcsMethod").val() == "3"){
		tableId = "dataTableGroupBySignInType2";
		$("#content-list").hide();
		$("#content-list-groupBySignCZY").hide();
		$("#content-list-groupBySignInType2").show();
	}
	var dateStart = Date.parse($.trim($("#dateFrom").val()));
    var dateEnd = Date.parse($.trim($("#dateTo").val()));
    if(dateStart>dateEnd){
    	 art.dialog({content: "签到查询起始日期不能大于末日期",width: 200});
			return false;
    } 
     
	$("#"+tableId).jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,}).trigger("reloadGrid");
}

function view() {
    var dataTable = "";
    var statistcsMethod = $("#statistcsMethod").val();
    if (statistcsMethod == "3") {
        dataTable = "dataTableGroupBySignInType2";
    } else if (statistcsMethod == "2") {
        dataTable = "dataTableGroupBySignCZY";
    } else {
        dataTable = "dataTable";
    }
    var ret = selectDataTableRow(dataTable);
    if (!ret) {
        art.dialog({content: "请选择一条记录"});
        return;
    }
    var url = "";
    var postData = {};
    var height = 600;
    var width = 1000;
    if (statistcsMethod == "3") {
        url = "${ctx}/admin/signIn/goDetailGroupBySignInType2";
        $.extend(postData, {
            department1: $("#department1").val(),
            department2: $("#department2").val(),
            errPosi: $("#errPosi").val(),
            signCzy: $("#signCzy").val(),
            dateFrom: $("#dateFrom").val(),
            dateTo: $("#dateTo").val(),
            signInType2: ret.signintype2 == "" ? "null" : ret.signintype2
        });
        height = 700;
        width = 1200;
    } else if (statistcsMethod == "2") {
        url = "${ctx}/admin/signIn/goDetailGroupBySignCZY";
        $.extend(postData, {
            department1: $("#department1").val(),
            department2: $("#department2").val(),
            errPosi: $("#errPosi").val(),
            signCzy: ret.signczy,
            dateFrom: $("#dateFrom").val(),
            dateTo: $("#dateTo").val(),
            signInType2: $("#signInType2").val()
        });
        height = 700;
        width = 1200;
    } else {
        url = "${ctx}/admin/signIn/goDetail";
        $.extend(postData, {
            pk: ret.pk,
            custAddress: ret.custaddress,
            department1: ret.department1,
            department2: ret.department2,
            dateFrom: ret.dateFrom,
            dateTo: ret.dateTo,
            signInType2: ret.signintype2 == "" ? "null" : ret.signintype2
        });
    }

    Global.Dialog.showDialog("signInView", {
        title: "查看签到信息",
        url: url,
        postData: postData,
        height: height,
        width: width
    });
}

//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/signIn/doExcel";
	if ($("#statistcsMethod").val() == '1') {
		doExcelAll(url,"dataTable");
	} else if ($("#statistcsMethod").val() == '2'){
		doExcelNow("工人签到_按签到人汇总","dataTableGroupBySignCZY");
	} else if ($("#statistcsMethod").val() == "3"){
		doExcelNow("工人签到_按服务类型汇总","dataTableGroupBySignInType2");
	}

} 

function clearForm(){
	$("#department1").val('');
	$("#department2").val('');
	$("#dateFrom").val('');
	$("#dateTo").val('');
	$("#custScoreSignIn").val('');
	$("#signCzy").val(''); 
	$("#errPosi").val('');
	$("#page_form select").val('');
	$.fn.zTree.getZTreeObj("tree_custScoreSignIn").checkAllNodes(false);
	$("#custScoreSignIn_NAME").val('');
	
	
}
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/signIn/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-90,
			styleUI: 'Bootstrap',
			colModel : [
			  {name : 'pk',index : 'pk',width : 50,label:'pk',sortable : true,align : "left", hidden:true},
		      {name : 'custcode',index : 'custcode',width : 70,label:'客户编号',sortable : true,align : "left"},
		      {name : 'custaddress',index : 'custaddress',width : 165,label:'楼盘',sortable : true,align : "left"},
		      {name : 'crtdate',index : 'crtdate',width : 140,label:'签到日期',sortable : true,align : "left",formatter:formatTime},
		      {name : 'signczydescr',index : 'signczydescr',width : 70,label:'签到人',sortable : true,align : "left"},
		      {name : 'department1',index : 'department1',width : 75,label:'一级部门',sortable : true,align : "left"},
		      {name : 'department2',index : 'department2',width : 75,label:'二级部门',sortable : true,align : "left"},
		      {name : 'descr',index : 'descr',width : 75,label:'施工节点',sortable : true,align : "left"},
		      {name : 'address',index : 'address',width : 418,label:'地址',sortable : true,align : "left"},
		      {name : 'errposidescr',index : 'errposidescr',width :75 ,label:'位置异常',sortable : true,align : "left"},
		      {name : "signintype2descr",index : "signintype2descr",width :75 ,label:"服务类型",sortable : true,align : "left"},
		      {name : "worktype12descr",index : "worktype12descr",width :75 ,label:"工种分类12",sortable : true,align : "left"},
		      {name : 'ispass',index : 'ispass',width : 75,label:'初检通过',sortable : true,align : "left"},
		      {name : 'remarks',index : 'remarks',width : 218,label:'备注',sortable : true,align : "left"},
		      {name : 'custscore',index : 'custscore',width : 78,label:'客户评分',sortable : true,align : "left"},
		      {name : 'custremarks',index : 'custremarks',width : 218,label:'客户评价',sortable : true,align : "left"},
            ]
		});
		
		Global.JqGrid.initJqGrid("dataTableGroupBySignCZY",{
			url:"${ctx}/admin/signIn/goJqGrid_CZY",
			height:$(document).height()-$("#content-list").offset().top-69,
			width:$(document).width()-$("#content-list").offset().right-60,
			styleUI: 'Bootstrap',
			colModel : [
		      {name : 'signczy',index : 'signczy',width : 130,label:'签到人',sortable : true,align : "left"},
		      {name : 'signczydescr',index : 'signczydescr',width : 130,label:'签到人',sortable : true,align : "left"},
		      {name : 'department1',index : 'department1',width : 130,label:'一级部门',sortable : true,align : "left"},
		      {name : 'department2',index : 'department2',width : 130,label:'二级部门',sortable : true,align : "left"},
		      {name : 'constructioncount',index : 'constructioncount',width : 130,label:'在建套数',sortable : true,align : "left"},
		      {name : 'num',index : 'num',width : 130,label:'签到套数',sortable : true,align : "left"},
		      {name : 'days',index : 'days',width : 130,label:'签到天数',sortable : true,align : "left"},
		      {name : 'totalsignindescr',index : 'totalsignindescr',width : 130,label:'签到次数',sortable : true,align : "left"},
            ]
		});
		
		Global.JqGrid.initJqGrid("dataTableGroupBySignInType2",{
			url:"${ctx}/admin/signIn/goJqGrid_SignInType2",
			height:$(document).height()-$("#content-list-groupBySignCZY").offset().top-69,
			width:$(document).width()-$("#content-list-groupBySignCZY").offset().right-60,
			styleUI: 'Bootstrap',
			colModel : [
		      {name : "signintype2",index : "signintype2",width : 130,label:"服务类型编码",sortable : true,align : "left"},//, hidden:true
		      {name : "signintype2descr",index : "signintype2descr",width : 130,label:"服务类型",sortable : true,align : "left"},
		      {name : "signincount",index : "signincount",width : 130,label:"签到次数",sortable : true,align : "left"}
            ]
		});
		
		$("#content-list-groupBySignCZY").hide();
		$("#content-list-groupBySignInType2").hide();
		$("#signCzy").openComponent_employee();
		
});
</script>
</head>
    
<body style="overflow:-Scroll" overflow-y="hidden" >  
	<div class="body-box-list">
        <div class="query-form"  >  
	        <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
				<li>
					<label>一级部门</label>
						<house:department1 id="department1" onchange="changeDepartment1(this,'department2','${ctx }')" value="${signIn.department1 }"></house:department1>
					</label>
				</li>
				<li>
					<label>二级部门</label>
						<house:department2 id="department2" dictCode="${signIn.department1 }" value="${signIn.department2 }"></house:department2>
					</label>
				</li>
				
				<li>	
					<label>位置异常</label>
						<house:xtdm id="errPosi" dictCode="YESNO" value="${signIn.errPosi }"></house:xtdm>
					</label>
				</li>
				<li>		
					<label>统计方式</label>
						<select id="statistcsMethod" name="statistcsMethod" >
							<option value="1">明细</option>
							<option value="2">按签到人汇总</option>
							<option value="3">按服务类型汇总</option>
						</select>
					</label>	
				</li>
				<li>
					<label>签到人</label>
						<input type="text" id="signCzy" name="signCzy"   value="${signIn.signCzy }" />
					</label>	
				</li>
				<li>		
					<label>签到时间从</label>
						<input type="text" id="dateFrom"
						name="dateFrom" class="i-date" 
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${signIn.dateFrom}' pattern='yyyy-MM-dd'/>" />								
					</label>
				</li>
				<li>
					<label>至</label>
						<input type="text" id="dateTo"
						name="dateTo" class="i-date" 
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${signIn.dateTo}' pattern='yyyy-MM-dd'/>" />									
					</label>
				</li>
				<li>
					<label>服务类型</label>
					<house:dict id="signInType2" dictCode="" 
								sql="select Code,(Code+' '+Descr) Descr from tSignInType2 where Expired='F' order by Code"
								sqlValueKey="Code" sqlLableKey="Descr" value="${signIn.signInType2 }"></house:dict>
				</li>
				<li>
					<label>楼盘</label>
					<input type="text" id="address"
					name="address" value='${signIn.address}'>
				</li>
				<li>
					<label>初检是否通过</label>
						<select id="isPass" name="isPass" >
							<option value="">请选择</option>
							<option value="1">是</option>
							<option value="0">否</option>
							
						</select>
					</label>	
				</li>
				<li>
					<label>客户评分</label>
					<house:DictMulitSelect id="custScoreSignIn" dictCode="" 
					sql="select p.number code ,cast (p.number as char(1))+'星' descr from master..spt_values p 
							where p.type = 'p' and p.number between 1 and 5 " 
					sqlLableKey="descr" sqlValueKey="code" selectedValue="${customer.prjItem}" ></house:DictMulitSelect>
				</li>
				<li id="loadMore" >
					<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
					<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
				</li>
				</ul>		
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<div class="btn-panel" >
			     <div class="btn-group-sm"  >
			      		<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
			      		<button type="button" class="btn btn-system " id="btnView" onclick="view()">查看</button>
			     </div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
			<div id="content-list-groupBySignCZY">
				<table id= "dataTableGroupBySignCZY"></table> 
				<div id="dataTableGroupBySignCZYPager"></div>
			</div>
			<div id="content-list-groupBySignInType2">
				<table id= "dataTableGroupBySignInType2"></table> 
				<div id="dataTableGroupBySignInType2Pager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
