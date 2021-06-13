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
	<title>工地整改</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript"> 
$(function(){
		function goto_query(){
				$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
		}

	$("#code").openComponent_customer({condition:{status:'1,2,3,4'}});
	$("#projectMan").openComponent_employee();
	$("#businessMan").openComponent_employee();
	$("#designMan").openComponent_employee();


	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjProgCheck/goConfirmJqGrid",
		postData:{statusZG:'1'},	
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
				{name: "no", index: "no", width: 84, label: "no", sortable: true, align: "left", hidden:true},
				{name: "custcode", index: "custcode", width: 84, label: "custcode", sortable: true, align: "left",hidden:true },
				{name: "address", index: "address", width: 131, label: "楼盘地址", sortable: true, align: "left"},
				{name: "projectman", index: "projectman", width: 73, label: "项目经理", sortable: true, align: "left",hidden:true},
				{name: "projectmandescr", index: "projectmandescr", width: 70, label: "项目经理", sortable: true, align: "left"},
				{name: "departmentdescr", index: "departmentdescr", width: 60, label: "部门", sortable: true, align: "left"},
				{name: "confirmbegin", index: "confirmbegin", width: 100, label: "实际开工时间", sortable: true, align: "left", formatter: formatDate},
				{name: "sjprjitem", index: "sjprjitem", width: 120, label: "当前进度", sortable: true, align: "left",hidden:true},
				{name: "nowspeeddescr", index: "nowspeeddescr", width: 120, label: "当前进度", sortable: true, align: "left"},
				{name: "prjitemdescr", index: "prjitemdescr", width: 120, label: "巡检进度", sortable: true, align: "left"},
				{name: "buildstatusdescr", index: "buildstatusdescr", width: 65, label: "工地状态", sortable: true, align: "left"},
				{name: "ismodify", index: "ismodify", width: 80, label: "是否整改", sortable: true, align: "left" ,hidden:true},
				{name: "ismodifydescr", index: "ismodifydescr", width: 80, label: "是否整改", sortable: true, align: "left"},
				{name: "safepromdescr", index: "safepromdescr", width: 80, label: "安全问题", sortable: true, align: "left"},
				{name: "visualpromdescr", index: "visualpromdescr", width: 80, label: "形象问题", sortable: true, align: "left"},
				{name: "artpromdescr", index: "artpromdescr", width: 80, label: "工艺问题", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 110, label: "备注", sortable: true, align: "left"},
				{name: "modifytime", index: "modifytime", width: 60, label: "整改时限", sortable: true, align: "right"},
				{name: "remainmodifytime", index: "remainmodifytime", width: 95, label: "整改剩余时限", sortable: true, align: "right"},
				{name: "modifycomplete", index: "modifycomplete", width: 70, label: "整改完成", sortable: true, align: "left",hidden:true},
				{name: "modifycompletedescr", index: "modifycompletedescr", width: 70, label: "整改完成", sortable: true, align: "left"},
				{name: "compczydescr", index: "compczydescr", width: 70, label: "整改人", sortable: true, align: "left"},
				{name: "confirmczydescr", index: "confirmczydescr", width: 70, label: "验收人", sortable: true, align: "left"},
				{name: "appczydescr", index: "appczydescr", width: 60, label: "巡检人", sortable: true, align: "left"},
				{name: "date", index: "date", width: 120, label: "巡检时间", sortable: true, align: "left", formatter: formatTime},
				{name: "gpsaddress", index: "gpsaddress", width: 400, label: "定位信息", sortable: true, align: "left"} 
				],
			});
		
		$("#check").on("click",function(){
			var ret = selectDataTableRow();
			if(ret.modifycomplete=='0'){
				art.dialog({
					content:"只有整改完成的图片从才能验收",
				});
				return false;
			}
			if(ret.confirmczydescr!=''){
				art.dialog({
					content:"该项目已验收",
				});
				return false;
			}
         	  if (ret) {	
             	Global.Dialog.showDialog("check",{ 
              	  title:"整改验收确认",
              	  url:"${ctx}/admin/prjProg/goConfirmCheck",
              	  postData:{no:ret.no,prjDescr:ret.prjitemdescr,projectMan:ret.projectman,remainModifyTime:ret.remainmodifytime},
              	  height: 650,
              	  width:1000,
              	  returnFun:goto_query
              	});
            } else {
            	art.dialog({
        			content: "请选择一条记录"
        		});
              }
		});
		
		$("#view").on("click",function(){
			var ret = selectDataTableRow();
         	  if (ret) {	
             	Global.Dialog.showDialog("View",{ 
              	  title:"整改验收确认",
              	  url:"${ctx}/admin/prjProg/goViewGDXJ?id="+ret.no+"&a="+ret.remainmodifytime,
              	  height: 650,
              	  width:1000,
              	  returnFun:goto_query
              	});
            } else {
            	art.dialog({
        			content: "请选择一条记录"
        		});
            }
		});
		
		
});
 function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$("#remarks").val('');
	$("#address").val('');
	$("#page_form input[type='text']").val('');
	$("#splStatusDescr").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_splStatusDescr").checkAllNodes(false);
} 
</script>
</head>
	<body>
		<div class="body-box-list">
				<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="check">
							<span>验收</span>
						</button>
						<button type="button" class="btn btn-system " id="view">
							<span>查看</span>
						</button>
	               	<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>	   
				</div>
			</div>
			</div>
			<div class="query-form">
			<div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
						<ul class="ul-form">
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${prjProgCheck.address}" />
							</li>
							<li>
								<label>工程部</label>
							<house:department2 id="Department2" dictCode="03"  depType="3"  ></house:department2>
							</li>
							<li>
								<label>整改状态</label>
								<select id="statusZG" name="statusZG" style="width:160px" select="1" value="${prjProgCheck.statusZG}" >
									<option   value="0">0&nbsp&nbsp待整改</option>
									<option selected = "selected"  value="1">1&nbsp&nbsp待验收</option>
									<option value="2">2&nbsp&nbsp已验收</option>
								</select>                  
							</li>
							<li>
								<label>项目经理</label>
							<input type="text" id="projectMan" name="projectMan" style="width:160px;" value="${prjProgCheck.projectMan}" />
							</li>
							<li>
								<label>巡检日期从</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
							<li >
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
				</form>
			</div>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
			<!-- panelBar -->
					<!-- panelBar End -->
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
			</div>
	</body>	
</html>
