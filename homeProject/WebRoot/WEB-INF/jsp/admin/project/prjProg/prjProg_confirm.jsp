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
	<title>工地验收</title>
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
		url:"${ctx}/admin/prjProg/goConfirmJqGrid",
		postData:{notCompleted:'1',notConfirm:'1',notSign:'1'},
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
				{name: "pk", index: "pk", width: 175, label: "custcode", sortable: true, align: "left",hidden:true},
				{name: "custcode", index: "custcode", width: 175, label: "custcode", sortable: true, align: "left",hidden:true},
				{name: "address", index: "address", width: 145, label: "楼盘地址", sortable: true, align: "left"},
				{name: "projectman", index: "projectman", width: 70, label: "项目经理", sortable: true, align: "left",hidden:true},
				{name: "projectmandescr", index: "projectmandescr", width: 60, label: "项目经理", sortable: true, align: "left"},
				{name: "departmentdescr", index: "departmentdescr", width: 55, label: "部门", sortable: true, align: "left"},
				{name: "prjitem", index: "prjitem", width: 175, label: "施工项目", sortable: true, align: "left",hidden:true},
				{name: "prjitemdescr", index: "prjitemdescr", width: 145, label: "施工项目", sortable: true, align: "left"},
				{name: "planbegin", index: "planbegin", width: 90, label: "计划开始时间", sortable: true, align: "left", formatter: formatDate},
				{name: "begindate", index: "begindate", width: 90, label: "实际开始时间", sortable: true, align: "left", formatter: formatDate},
				{name: "planend", index: "planend", width: 90, label: "计划结束时间", sortable: true, align: "left", formatter: formatDate},
				{name: "enddate", index: "enddate", width: 90, label: "实际结束时间", sortable: true, align: "left", formatter: formatDate},
				{name: "photocount", index: "photocount", width: 80, label: "上传图片数", sortable: true, align: "right"},
				{name: "photolastupdate", index: "photolastupdate", width: 135, label: "图片最后上传时间", sortable: true, align: "left", formatter: formatTime},
				{name: "confirmczydescr", index: "confirmczydescr", width: 60, label: "验收人", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 80, label: "验收日期", sortable: true, align: "left", formatter: formatTime},
				{name: "confirmdesc", index: "confirmdesc", width: 170, label: "验收说明", sortable: true, align: "left"}
			],
		});
		
		//验收
		$("#check").on("click",function(){
			var ret = selectDataTableRow();
			if(ret.confirmczydescr!=''){
				art.dialog({
					content:"该项目已验收",
				});
				return false;
			}
         	  if (ret) {	
             	Global.Dialog.showDialog("Update",{ 
              	  title:"验收确认",
              	  url:"${ctx}/admin/prjProg/goCheck",
              	  postData:{pk:ret.pk,photoLastUpDate:ret.photolastupdate,prjDescr:ret.prjitemdescr,projectMan:ret.projectman},
              	  height: 700,
              	  width:1050,
              	  returnFun:goto_query
              	});
            } else {
            	art.dialog({
        			content: "请选择一条记录"
        		});
            }
		});
		
		//查看
		$("#view").on("click",function(){
			var ret = selectDataTableRow();
         	  if (ret) {	
             	Global.Dialog.showDialog("ViewGDYS",{ 
              	  title:"验收确认",
              	  url:"${ctx}/admin/prjProg/goViewGDYS",
              	  postData:{pk:ret.pk,photoLastUpDate:ret.photolastupdate},
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
	
});


//巡检未巡检
function confirm(obj){
	if ($(obj).is(':checked')){
		$('#notConfirm').val('1');
	}else{
		$('#notConfirm').val('');
	}
}
//显示不显示签约班组
function completed(obj){
	if ($(obj).is(':checked')){
		$('#notCompleted').val('1');
	}else{
		$('#notCompleted').val('');
	}
}
//竣工未竣工
function sign(obj){
	if ($(obj).is(':checked')){
		$('#notSign').val('1');
	}else{
		$('#notSign').val('');
	}
}

 function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$("#prjItem").val('');
	$("#remarks").val('');
	$("#address").val('');
	$("#splStatusDescr").val('');
	$.fn.zTree.getZTreeObj("tree_prjItem").checkAllNodes(false);
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
								<input type="text" id="address" name="address" style="width:160px;" value="${prjProg.address}" />
							</li>
							<li>
								<label>工程部</label>
								<house:department2 id="Department2" dictCode="03"  depType="3"  ></house:department2>
							</li>
							<li>
								<label>项目经理</label>
								<input type="text" id="projectMan" name="projectMan" style="width:160px;" value="${prjProg.projectMan}" />
							</li>
							<li>
								<label>施工项目</label>
								<house:xtdmMulit  id="prjItem" dictCode="PRJITEM" selectedValue="${prjProg.prjItem}"></house:xtdmMulit>                     
							</li>
							<li>	
								<input type="checkbox" id="notSign" name="notSign" value="1" onclick="sign(this)" ${prjProg!='1'?'checked':''  }/>不包含签约班组工地&nbsp;
								<input type="checkbox" id="notConfirm" name="notConfirm" value="1" onclick="confirm(this)" ${prjProg!='1'?'checked':''  }/>只显示验收&nbsp;
								<input type="checkbox" id="notCompleted" name="notCompleted" value="1" onclick="completed(this)" ${prjProg!='1'?'checked':''  }/>不显示竣工&nbsp;
 							</li>
 							<li >
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
					</ul>
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
