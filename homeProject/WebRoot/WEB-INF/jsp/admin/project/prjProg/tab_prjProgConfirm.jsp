<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>工地验收</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript"> 
function doExcel(){
	var url = "${ctx}/admin/prjProgConfirm/doConfirmExcel";
	doExcelAll(url,'dataTable_confirm','dataForm');
}
$(function(){

	$("#lastUpdatedBy").openComponent_employee();	
	$("#lastUpdatedBy1").openComponent_employee();	

	var lastCellRowId;
	/**初始化表格*/
	Global.JqGrid.initJqGrid("dataTable_confirm",{
				styleUI: 'Bootstrap', 
				height:415,
				sortable: true,
			colModel : [
				{name: "address", index: "address", width: 131, label: "楼盘地址", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 100, label: "客户类型", sortable: true, align: "left"},
				{name: "area", index: "area", width: 80, label: "面积", sortable: true, align: "right",},
				{name: "projectmandescr", index: "projectmandescr", width: 70, label: "项目经理", sortable: true, align: "left"},
				{name: "department1descr", index: "department1descr", width: 70, label: "工程部", sortable: true, align: "left"},
				{name: "prjitemdescr", index: "prjitemdescr", width: 120, label: "施工项目", sortable: true, align: "left"},
				{name: "date", index: "date", width: 120, label: "验收日期", sortable: true, align: "left", formatter: formatTime},
				{name: "confirmmen", index: "confirmmen", width: 60, label: "验收人", sortable: true, align: "left"},
				{name: "ispassdescr", index: "ispassdescr", width: 70, label: "验收通过", sortable: true, align: "left"},
				{name: "prjleveldescr", index: "prjleveldescr", width: 70, label: "验收评级", sortable: true, align: "left"},
				{name: "prjworkabledescr", index: "prjworkabledescr", width: 70, label: "工艺落实", sortable: true, align: "left"},
				{name: "workerdescr", index: "workerdescr", width: 70, label: "班组姓名", sortable: true, align: "left"},
				{name: "workercomedate",  index: "workercomedate", width: 80, label: "进场时间", sortable: true, align: "left", formatter: formatDate},
				{name: "remarks", index: "remarks", width: 130, label: "备注", sortable: true, align: "left"},
				{name: "errposidescr", index: "errposidescr", width: 80, label: "位置异常", sortable: true, align: "left"},
				{name: "gpsaddress", index: "gpsaddress", width: 303, label: "定位地点", sortable: true, align: "left"},
				{name: "custscore", index: "custscore", width: 70, label: "客户评分", sortable: true, align: "left"},
				{name: "custremarks", index: "custremarks", width: 224, label: "客户评价", sortable: true, align: "left"},
				{name: "no", index: "no", width: 82, label: "编号", sortable: true, align: "left",},
				{name: "custcode", index: "custcode", width: 74, label: "客户编号", sortable: true, align: "left"}
			], 
			ondblClickRow: function (rowid, status) {
	            var rowData = $("#dataTable_confirm").jqGrid("getRowData",rowid);
	            $("#dataForm")[0][2].value=rowData.address;
	           	$("#dataTable_confirm").jqGrid("setGridParam",{postData:$("#dataForm").jsonForm(),page:1,url:"${ctx}/admin/prjProgConfirm/goConfirmJqGrid"}).trigger("reloadGrid");
			    $("#dataForm")[0][2].value="";
		
			},
 		});
       //初始化送货申请明细
		document.body.onunload = closeWin;
		$('input','#dataForm').keydown(function(e){
			if(e.keyCode==13){
				query();
			}
		});
	
		//查看
		$("#viewGDYS").on("click",function(){
			var ret = selectDataTableRow("dataTable_confirm");
         	  if (ret) {	
             	Global.Dialog.showDialog("ViewGDYS",{ 
              	  title:"工地验收查看",
              	  url:"${ctx}/admin/prjProg/goGDYS",
              	  postData:{no:ret.no},
              	  height: 600,
              	  width:1000,
              	  returnFun:goto_query
              	});
            } else {
            	art.dialog({
        			content: "请选择一条记录"
        		});
            }
		});
		
		$("#viewAll").on("click",function(){
			var ret = selectDataTableRow("dataTable_confirm");
         	  if (ret) {	
             	Global.Dialog.showDialog("ViewAllGDYS",{ 
              	  title:"图片审核",	
              	  url:"${ctx}/admin/prjProg/goViewAllPic",
              	  postData:{no:ret.no},
              	  height: 800,
              	  width:1200,
              	  returnFun:query
              	});
            } else {
            	art.dialog({
        			content: "请选择一条记录"
        		});
            }
		});
		
		$("#update").on("click",function(){
			var ret = selectDataTableRow("dataTable_confirm");
         	  if (ret) {	
             	Global.Dialog.showDialog("update",{ 
              	  title:"验收编辑",
              	  url:"${ctx}/admin/prjProg/goUpdateConfirm",
              	  postData:{no:ret.no},
              	  height: 600,
              	  width:1000,
              	  returnFun:query
              	});
            } else {
            	art.dialog({
        			content: "请选择一条记录"
        		});
            }
		});
		
		window.query = function(){
			$("#dataTable_confirm").jqGrid("setGridParam",{postData:$("#dataForm").jsonForm(),page:1,url:"${ctx}/admin/prjProgConfirm/goConfirmJqGrid"}).trigger("reloadGrid");
		}
 		$('#dataTable_confirmPager_left').attr('width',90);
          $('#dataTable_confirmPager_center').attr('width','900px');
          $('#dataTable_confirmPager_right').attr('align','left');
});
function query(){
	$("#dataTable_confirm").jqGrid("setGridParam",{postData:$("#dataForm").jsonForm(),page:1,url:"${ctx}/admin/prjProgConfirm/goConfirmJqGrid"}).trigger("reloadGrid");
}
function changeIsPrjConfirm(obj){
	if ($(obj).is(':checked')){
		$('#isPrjConfirm').val('1');
	}else{
		$('#isPrjConfirm').val('');
	}
}   

function flash(){
	$("#dataForm")[0][2].value="";
	$("#dataTable_confirm").jqGrid("setGridParam",{postData:$("#dataForm").jsonForm(),page:1,url:"${ctx}/admin/prjProgConfirm/goConfirmJqGrid"}).trigger("reloadGrid");
}   

</script>
</head>
	<body>
		<div class="body-box-list" style="margin-top: 0px;">
			<div class="panel-body">
		<div class="query-form">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;"  value="${prjProgConfirm.address}" />
							<li>
								<label>验收人</label>
								<input type="text" id="lastUpdatedBy1" name="lastUpdatedBy" style="width:160px;"  value="${prjProgConfirm.lastUpdatedBy}" />
							<li>
								<label>工程部</label>
							<house:department2 id="Department2" dictCode="03"  depType="3"  ></house:department2>
							<li>
								<label>验收日期</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							<li>
								<label>施工项目</label>
								<house:DictMulitSelect id="prjItem1" dictCode="" sql="select code,descr from tPrjItem1 where IsConfirm='1' order by seq " 
								sqlLableKey="descr" sqlValueKey="code" selectedValue="${prjProgConfirm.prjItem1}" ></house:DictMulitSelect>
							<li>
								<label>验收通过</label>
								<house:xtdm id="isPass" dictCode="YESNO"  style="width:160px;" ></house:xtdm>
							</li>
							<li>
								<label>施工方式</label>
								<house:xtdm id="constructType" dictCode="CONSTRUCTTYPE"  ></house:xtdm>
							</li>
							<li>
								<label>客户评分</label>
								<house:DictMulitSelect id="custScoreComfirm" dictCode="" 
								sql="select p.number code ,cast (p.number as char(1))+'星' descr from master..spt_values p 
										where p.type = 'p' and p.number between 1 and 5 " 
								sqlLableKey="descr" sqlValueKey="code" selectedValue="${customer.prjItem}" ></house:DictMulitSelect>
							</li>
 							<li>
								<label>工艺落实</label>
								<house:xtdm id="prjWorkable" dictCode="YESNO"  style="width:160px;" ></house:xtdm>
							</li>						
							<li style="width:150px">	
								<input type="checkbox" id="isPrjConfirm" name="isPrjConfirm" value="" onclick="changeIsPrjConfirm(this)" ${prjProg=='1'?'checked':''  }/>项目经理验收&nbsp;
 							</li>	
							<li>
								<button type="button" class="btn  btn-sm btn-system " onclick="query();">查询</button>
							</li>
						</ul>
				</form>
				</div>
		</div>
</div>
		<div class="btn-panel" >
    		<div class="btn-group-sm"  >
				<button type="button" class="btn btn-system " id="viewAll" style="float:left">
					<span>图片审核</span>
				</button>
				<button type="button" class="btn btn-system " id="viewGDYS" style="float:left">
					<span>查看</span>
				</button>
				<house:authorize authCode="PRJPROG_UPDATECONFIRM">
				<button type="button" class="btn btn-system " id="update" style="float:left">
					<span>编辑</span>
				</button>
				</house:authorize>
				<button type="button" class="btn btn-system " onclick="doExcel()" title="导出当前excel数据" >
					<span>导出excel</span>
				</button>	
			</div>
		</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent" >
				<div id="content-list" >
					<table id= "dataTable_confirm"   ></table>
					<div id="dataTable_confirmPager"  ></div>
				</div>
			</div>
	</body>	
</html>
