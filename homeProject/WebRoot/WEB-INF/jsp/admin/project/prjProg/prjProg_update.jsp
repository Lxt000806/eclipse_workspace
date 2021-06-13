<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html>
<head>
	<title>修改PrjProg</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	

<script type="text/javascript"> 
$(function(){

	var custCode=$.trim($("#code").val());
	var lastUpdatedBy=$.trim($("#lastUpdatedBy").val());

	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/prjProg/goJqGrid",
			postData:{custCode:"${customer.code}"},
		    rowNum:10000000,
			height:380,
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "PK", index: "PK", width: 170, label: "施工项目", sortable: true, align: "left",hidden:true},
				{name: "CustCode", index: "CustCode", width: 170, label: "custcode", sortable: true, align: "left",hidden:true},
				{name: "PrjItem", index: "PrjItem", width: 170, label: "施工项目", sortable: true, align: "left", hidden:true},
				{name: "prjdescr", index: "prjdescr", width: 120, label: "施工项目", sortable: true, align: "left"},
				{name: "PlanBegin", index: "PlanBegin", width: 80, label: "计划开始日", sortable: true, align: "left",formatter: formatDate},
				{name: "BeginDate", index: "BeginDate", width: 80, label: "开始日期", sortable: true, align: "left", formatter: formatDate},
				{name: "PlanEnd", index: "planEnd", width: 80, label: "计划结束日", sortable: true, align: "left",formatter: formatDate},
				{name: "EndDate", index: "EndDate", width: 80, label: "结束日期", sortable: true, align: "left", formatter: formatDate},
				{name: "ConfirmCZY", index: "ConfirmCZY", width: 60, label: "验收人", sortable: true, align: "left",hidden:true},
				{name: "confirmdescr", index: "confirmdescr", width: 60, label: "验收人", sortable: true, align: "left"},
				{name: "ConfirmDate", index: "ConfirmDate", width: 80, label: "验收日期", sortable: true, align: "left", formatter: formatDate},
				{name: "prjLevel", index: "prjLevel", width: 78, label: "验收评级", sortable: true, align: "left",hidden:true},
				{name: "prjleveldescr", index: "prjleveldescr", width: 78, label: "验收评级", sortable: true, align: "left"},
				{name: "ConfirmDesc", index: "ConfirmDesc", width: 190, label: "验收说明", sortable: true, align: "left"},
				{name: "LastUpdate", index: "LastUpdate", width: 143, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "LastUpdatedBy", index: "LastUpdatedBy", width: 80, label: "最后修改人员", sortable: true, align: "left", formatter: formatTime,hidden:true},
				{name: "LastUpdatedBy", index: "LastUpdatedby", width: 60, label: "操作人员", sortable: true, align: "left"},
				{name: "Expired", index: "Expired", width: 60, label: "是否过期", sortable: true, align: "left"},
				{name: "ActionLog", index: "ActionLog", width: 60, label: "操作代码", sortable: true, align: "left"}				
			], 
 		};
	   Global.JqGrid.initJqGrid("dataTable",gridOption);
	
	//增加
	$("#add").on("click",function(){
		var prjitem = Global.JqGrid.allToJson("dataTable","PrjItem");
		arry = prjitem.fieldJson.split(",");
			
       	Global.Dialog.showDialog("Add",{ 
       	  title:"工程进度——增加",
       	  url:"${ctx}/admin/prjProg/goAdd",
       	  postData:{hasPrjItem:arry,custCode:custCode,actCode:'1'},
       	  height: 450,
       	  width:650,
       	  returnFun:query
        });
	});
		
	//复制
	$("#copy").on("click",function(){
		var prjitem = Global.JqGrid.allToJson("dataTable","PrjItem");
		arry = prjitem.fieldJson.split(",");
		var ret= selectDataTableRow('dataTable');
		if (ret==null){
			art.dialog({
			 content:"请选择一条数据",
			 });
			 return false;
		}
		var prjitem = Global.JqGrid.allToJson("dataTable","PrjItem");
				arry = prjitem.fieldJson.split(",");
	       	Global.Dialog.showDialog("Add",{ 
	       	  title:"工程进度——复制",
	       	  url:"${ctx}/admin/prjProg/goCopy",
	        	 	  postData:{prjItem:ret.PrjItem,planBegin:ret.PlanBegin,
	        	 	 			beginDate:ret.BeginDate,endDate:ret.EndDate,
	        	 	  			planEnd:ret.PlanEnd,custCode:custCode,actCode:'1',hasPrjItem:arry},
	       	  height: 450,
	       	  width:650,
	       	  returnFun:query
	        });
		});
	
	//编辑
	$("#update").on("click",function(){
		var ret= selectDataTableRow('dataTable');
		if (ret==null){
			art.dialog({
			 content:"请选择一条数据",
			 });
			 return false;
		}
		var prjitem = Global.JqGrid.allToJson("dataTable","PrjItem");
				arry = prjitem.fieldJson.split(",");
	       	Global.Dialog.showDialog("Add",{ 
	       	  title:"工程进度——编辑",
	       	  url:"${ctx}/admin/prjProg/goUpdateUpdate",
	         	 	  postData:{pk:ret.PK,prjItem:ret.PrjItem,
	         	  			planBegin:ret.PlanBegin,beginDate:ret.BeginDate,
	         	  			planEnd:ret.PlanEnd,endDate:ret.EndDate,expired:ret.Expired,
	         	  			hasPrjItem:arry,custCode:custCode,actCode:'2'},
	       	  height: 450,
	       	  width:650,
	       	  returnFun:query
	        });
		});
	
	//查看
	$("#addView").on("click",function(){
		var ret= selectDataTableRow('dataTable');
		if(ret==null){
			art.dialog({
			content:'请选择数据',
			});
		}
       	Global.Dialog.showDialog("copy",{ 
			title:"工程进度——查看",
			url:"${ctx}/admin/prjProg/goAddView",
			postData:{pk:ret.PK,
				prjItem:ret.PrjItem,
   	  			planBegin:ret.PlanBegin,
   	  			beginDate:ret.BeginDate,
   	  			planEnd:ret.PlanEnd,
   	  			endDate:ret.EndDate,
   	  			lastUpdate:ret.LastUpdate,
   	  			lastUpdatedBy:ret.LastUpdatedBy,
   	  			expired:ret.Expired
   	  			},
			height: 450,
			width:650,
			returnFun:query
        });
	});
	
	//删除
	$("#del").on("click",function(){
		art.dialog({
			 content:"是否要删除",
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
				doDelete();
		    	return true;
				},
			cancel: function () {
				return true;
				}
		});
	});	
	
	//顺延按钮
	$("#bttn").on("click",function(){
		var postPoneDate = $.trim($("#postPoneDate").val());
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:'请选择顺延开始日期',
			});
			return false;
		}
		if(postPoneDate==''){
			art.dialog({
				content:'请填写顺延天数',
			});
			return false;
		}
		$.ajax({
			url:'${ctx}/admin/prjProg/doPostPone',
			type: 'post',
			data: {planBegin:ret.PlanBegin,custCode:custCode,postPoneDate:postPoneDate},
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
					query();
		    }
		 });
		
	});
		
	$("#returnCheck").on("click",function(){
		art.dialog({
			 content:"是否要撤回验收",
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
				doReturn();
		    	return true;
				},
			cancel: function () {
				return true;
				}
		});
	});
		
	//保存
	$("#saveBtn").on("click",function(){
			var param= Global.JqGrid.allToJson("dataTable");
			Global.Form.submit("page_form","${ctx}/admin/prjProg/doUpdate",param,function(ret){
				if(ret.rs==true){
					art.dialog({
						content:ret.msg,
						time:1000,
						beforeunload:function(){
							closeWin();
						}
					});				
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content:ret.msg,
						width:200
					});
				}
			});
		});
			
});
function query(){
	$("#dataTable").jqGrid("setGridParam",{postData:{custCode:"${customer.code}"},page:1}).trigger("reloadGrid");
}

function doReturn(){
		var ret = selectDataTableRow();
	$.ajax({
		url:'${ctx}/admin/prjProg/doReturnCheck',
		type: 'post',
		data: {pk:ret.PK},
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
				query();
	    }
	 });
}		 
function doDelete(){
	var ret= selectDataTableRow('dataTable');
	$.ajax({
		url:'${ctx}/admin/prjProg/doUpdateDelete',
		type: 'post',
		data: {pk:ret.PK},
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
			if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
				    	query();
				    }
				});
	    	}else{
	    		$("#_form_token_uniq_id").val(obj.token.token);
	    		art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	 });
}


</script>

</head>
    
<body onunload="closeWin()">
<div class="body-box-form" >
	<div class="content-form">
		<!--panelBar-->
			<div class="panel panel-system" >
    			<div class="panel-body" >
    				 <div class="btn-group-xs" >
						<!-- <button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button> -->
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
		<div class="infoBox" id="infoBoxDiv"></div>
        <div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
						<ul class="ul-form">
							<li>
								<label>客户编号</label>
								<input type="text" id="code" name="code" style="width:160px;"  value="${customer.code}" readonly="readonly"/>
							</li>
							<li>
								<label>客户名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;"  value="${customer.descr}" readonly="readonly"/>
							</li>
							<li>
								<label>客户状态</label>
								<house:xtdm id="status" dictCode="CUSTOMERSTATUS" value="${customer.status}" disabled="true" readonly="readonly"></house:xtdm>	
							</li>
							<li>
								<label>设计师</label>
								<input type="text" id="designManDescr" name="designManDescr" style="width:160px;"  value="${customer.designManDescr}" readonly="readonly"/>
							</li>
							<li>
								<label>模板编号</label>
								<input type="text" id="prjProgTempNo" name="prjProgTempNo" style="width:160px;"  value="${customer.prjProgTempNo}"  readonly="readonly"/>
							</li>
							<li>
								<label>模板名称</label>
								<input type="text" id="prjProgTempNoDescr" name="prjProgTempNoDescr" style="width:160px;"  value="${customer.prjProgTempNoDescr}" readonly="readonly"/>
							</li>
							<li>
								<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;"  value="${customer.address}" readonly="readonly"/>
							</li>
							<li hidden="true">
								<label>lastUpdatedBy</label>
								<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${customer.lastUpdatedBy}" readonly="readonly"/>
							</li>
							<li hidden="true">
								<label>actionLog</label>
								<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${customer.actionLog}" />
							</li>
						</ul>	
			</form>
			</div>
			</div>
		<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
				<button type="button" class="btn btn-system " id="add">
					<span>新增</span></button>
				<button type="button" class="btn btn-system " id="copy">
					<span>复制</span></button>
				<button type="button" class="btn btn-system " id="update">
					<span>编辑</span></button>
				<button type="button" class="btn btn-system " id="del">
					<span>删除</span></button>
				<!-- <button type="button" class="btn btn-system " id="returnCheck">
					<span>撤回验收</span></button> -->
				<button type="button" class="btn btn-system " id="addView">
					<span>查看</span>
				</button>
					<li  style="float:right"> 
					 &nbsp&nbsp<span>天</span>
					 </li>
					<li style="float:right">
							<input type="text" class="form-control" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"  style="width:40px;height:22px" id="postPoneDate" name="postPoneDate"  value="${prjProg.postPoneDate }">
					</li>
				<li style="float:right">
				 	<a>
						<span>
						&nbsp&nbsp
						</span>
				 	</a>
				</li>
				<li style="float:right">
				 	<a>
						<button type="button"  class="btn btn-system " id="bttn" name="bttn"  style="width:40px" >顺延</button>
				 	</a>
				</li>
				<li style="float:right"></li>
		</div>
	</div>
	<div class="pageContent">
		<div id="content-list">
			<table id= "dataTable"></table>
		</div> 
	</div>
	</div>
</div>
</body>
</html>
