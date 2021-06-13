<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>客户信息查询</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_roll.js?v=${v}" type="text/javascript"></script>
<style type="text/css">

</style>
<script type="text/javascript">
function view(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("customerXxView",{
		  title:"查看客户【"+ret.address+"】",
		  url:"${ctx}/admin/customerXx/goDetail?id="+ret.code,
		  height:750,
		  width:1430
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function perfEstimate(){
	var ret = selectDataTableRow();
    if (ret) {
    	$.ajax({
    		url : "${ctx}/admin/customerXx/goVerifyPerfEstimate",
    		data : {custCode: ret.code},
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
	    	   		 Global.Dialog.showDialog("customerXxPerfEstimate",{
		    	   		  title:"客户信息签单业绩预估【"+ret.address+"】",
		    	   		  url:"${ctx}/admin/customerXx/goPerfEstimate?custCode="+ret.code,
		    	   		  height:700,
		    	   		  width:1260
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
function xjd(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("customerXxXjd",{
		  title:"巡检单",
		  url:"${ctx}/admin/customerXx/goXjd?id="+ret.code,
		  height:300,
		  width:500,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function updateQdjl(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("customerXxUpdateQdjl",{
		  title:"客户签单奖励",
		  url:"${ctx}/admin/customerXx/goUpdateQdjl?id="+ret.code,
		  height:300,
		  width:700
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
//导出EXCEL
function doExcel(){
	var datas = $("#page_form").serialize();
	$.ajax({
		url:'${ctx}/admin/customerXx/doExcelBefore',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '导出数据出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		var url = "${ctx}/admin/customerXx/doExcel";
	    		doExcelAll(url);
	    	}else{
	    		art.dialog({
	    			content: obj.msg
	    		});
	    	}
	    }
	 });
	
}
function clearForm(){
	
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	$("#endCode").val('');
	$.fn.zTree.getZTreeObj("tree_endCode").checkAllNodes(false);
	$("#custType").val('');
	$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
	$("#region").val('');
	$.fn.zTree.getZTreeObj("tree_region").checkAllNodes(false);
	
	$("#department1").val('');
	$("#department1"+"_NAME").val('');
	$.fn.zTree.getZTreeObj("tree_department1").checkAllNodes(false);
	$("#department2").val('');
	$("#department2"+"_NAME").val('');
	$.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
	$.fn.zTree.init($("#tree_department2"), {}, []);
	$("#department3").val('');
	$("#department3"+"_NAME").val('');
	$.fn.zTree.getZTreeObj("tree_department3").checkAllNodes(false);
	$.fn.zTree.init($("#tree_department3"), {}, []);
	$("#relCust").val('');
	$.fn.zTree.getZTreeObj("tree_relCust").checkAllNodes(false);
	
}

/**初始化表格*/
$(function(){
	var gridOption = {
  		height:$(document).height()-$("#content-list").offset().top-100,
		colModel: eval('${colModel}'),
        loadComplete: function(){
	       	$('.ui-jqgrid-bdiv').scrollTop(0);
	       	frozenHeightReset();
   		},
        ondblClickRow: function(){
       		view();
        }
	}; 
	Global.JqGrid.initJqGrid("dataTable", gridOption);
	$("#dataTable").jqGrid('setFrozenColumns');
	
	Global.LinkSelect.initSelect("${ctx}/admin/xtdm/sourcesOrChannels", "source", "netChanel")
	  
	$("#code").openComponent_customer();
	$("#groupCode").openComponent_builderGroup();
	$("#role").openComponent_roll();
	$("#empCode").openComponent_employee();
	$("#builderCode").openComponent_builder();
	$("#page_form table tr td input[type='text']").width(120); 
	$("#page_form table tr td select").width(120);
		
	window.goto_query = function(){
		$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,url: "${ctx}/admin/customerXx/goJqGrid"}).trigger("reloadGrid");
	}
	onCollapse(0);
});

</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" id="expired" name="expired" value="${customer.expired }" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
						<li>
					        <label>客户编号</label>
					        <input type="text" id="code" name="code" value="${customer.code}"/>
				        </li>
						<li>
							<label>客户名称</label>
							<input type="text" id="descr" name="descr" style="width:160px;" value="${customer.descr }" />
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }" />
						</li>
						<li>
							<label>客户状态</label>
							<house:xtdmMulit id="status" dictCode="CUSTOMERSTATUS" selectedValue="${customer.status }"></house:xtdmMulit>
						</li>
						<li>
							<label>客户类型</label>
							<house:custTypeMulit id="custType"  selectedValue="${customer.custType}"></house:custTypeMulit>
						</li>
						<li>
							<label>角色</label>
							<input type="text" id="role" name="role" style="width:160px;" value="${customer.role }" />
						</li>
						<li>
							<label>干系人编号</label>
							<input type="text" id="empCode" name="empCode" style="width:160px;" value="${customer.empCode }" />
						</li>
						<li>
							<label>客户来源</label>
							<select id="source" name="source"></select>
						</li>
						<li>
                            <label>渠道</label>
                            <select id="netChanel" name="netChanel"></select>
                        </li>
						<li>
							<label>项目名称</label>
							<input type="text" id="builderCode" name="builderCode" style="width:160px;" value="${customer.builderCode }" />
						</li>
						<li id="loadMore">
							<button data-toggle="collapse" class="btn btn-sm btn-link" data-target="#collapse">更多</button>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
						<div class="collapse" id="collapse">
						<ul>
						<li>
							<label>一级部门</label>
							<house:DictMulitSelect id="department1" dictCode="" sql="select code,desc1 from tDepartment1 where Expired='F'" 
							sqlLableKey="desc1" sqlValueKey="code" selectedValue="${customer.department1 }" onCheck="checkDepartment1()"></house:DictMulitSelect>
						</li>
						<li>
							<label>二级部门</label>
							<house:DictMulitSelect id="department2" dictCode="" sql="select code,desc1 from tDepartment2 where 1=2" 
							sqlLableKey="desc1" sqlValueKey="code" selectedValue="${customer.department2 }" onCheck="checkDepartment2()"></house:DictMulitSelect>
						</li>
						<li>
							<label>三级部门</label>
							<house:DictMulitSelect id="department3" dictCode="" sql="select code,desc1 from tDepartment3 where 1=2" sqlLableKey="desc1" sqlValueKey="code" selectedValue="${customer.department3 }"></house:DictMulitSelect>
						</li>
						<li>
							<label>结束代码</label>
							<house:xtdmMulit id="endCode" dictCode="CUSTOMERENDCODE" selectedValue="${customer.endCode }"></house:xtdmMulit>
						</li>
						<li>
							<label>下定时间从</label>
							<input type="text" style="width:160px;" id="setDateFrom" name="setDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.setDateFrom }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>到</label>
							<input type="text" style="width:160px;" id="setDateTo" name="setDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.setDateTo }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>项目大类</label>
							<input type="text" id="groupCode" name="groupCode" style="width:160px;" value="${customer.groupCode }"/>
						</li>
						<li>
							<label>创建时间从</label>
							<input type="text" style="width:160px;" id="crtDateFrom" name="crtDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.crtDateFrom }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>到</label>
							<input type="text" style="width:160px;" id="crtDateTo" name="crtDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.crtDateTo }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>结束时间从</label>
							<input type="text" style="width:160px;" id="endDateFrom" name="endDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.endDateFrom }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>到</label>
							<input type="text" style="width:160px;" id="endDateTo" name="endDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.endDateTo }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>签订时间从</label>
							<input type="text" style="width:160px;" id="signDateFrom" name="signDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.signDateFrom }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>到</label>
							<input type="text" style="width:160px;" id="signDateTo" name="signDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.signDateTo }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>施工方式</label>
							<house:xtdm id="constructType" dictCode="CONSTRUCTTYPE" value="${customer.constructType }"></house:xtdm>
						</li>
						<li>
							<label>客户结算日期从</label>
							<input type="text" style="width:160px;" id="custCheckDateFrom" name="custCheckDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.custCheckDateFrom }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>到</label>
							<input type="text" style="width:160px;" id="custCheckDateTo" name="custCheckDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.custCheckDateTo }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>可退订</label>
							<house:xtdm id="canCancel" dictCode="YESNO" value="${customer.canCancel }"></house:xtdm>
						</li>
						<li>
							<label>片区</label>
							<house:DictMulitSelect id="region" dictCode="" sql="SELECT Code,Descr FROM tRegion WHERE 1=1 and expired='F' " sqlLableKey="Descr" sqlValueKey="Code" selectedValue="${prjConfirmApp.prjItem }"></house:DictMulitSelect>
						</li>
						<li>
							<label>楼盘性质</label>
							<house:xtdm id="houseType" dictCode="HOUSETYPE" value="${customer.houseType }"></house:xtdm>
						</li>
						<li>
							<label>软装券</label>
							<input type="text"  id="softTokenNo" name="softTokenNo" value="${customer.softTokenNo }"/>
						</li>
						<li>
                            <label>到店日期从</label>
                            <input type="text" style="width:160px;" id="visitDateFrom" name="visitDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.custCheckDateFrom}' pattern='yyyy-MM-dd'/>"/>
                        </li>
                        <li>
                            <label>到</label>
                            <input type="text" style="width:160px;" id="visitDateTo" name="visitDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.custCheckDateTo}' pattern='yyyy-MM-dd'/>"/>
                        </li>
						<li>
							<label>关系客户</label>
							<house:xtdmMulit id="relCust" dictCode="RELCUST" selectedValue="${customer.relCust }"></house:xtdmMulit>
						</li>
						<li>
                            <label>首次签单周期从</label>
                            <input type="text" id="signDateFirstFrom" name="signDateFirstFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" value="<fmt:formatDate value='${customer.custCheckDateFrom}' pattern='yyyy-MM-dd'/>"/>
                        </li>
                        <li>
                            <label>到</label>
                            <input type="text" id="signDateFirstTo" name="signDateFirstTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" value="<fmt:formatDate value='${customer.custCheckDateTo}' pattern='yyyy-MM-dd'/>"/>
                        </li>
                        <li>
                            <label>首次签单时间从</label>
                            <input type="text" id="firstSignDateFrom" name="firstSignDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
                        </li>
                        <li>
                            <label>到</label>
                            <input type="text" id="firstSignDateTo" name="firstSignDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
                        </li>
						<li class="search-group-shrink">
							<button data-toggle="collapse" class="btn btn-sm btn-link" data-target="#collapse">收起</button>
							<input type="checkbox" id="expired_show" name="expired_show" value="${customer.expired }" onclick="hideExpired(this)" ${customer.expired!='T'?'checked':'' }/><p>隐藏过期</p>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
					</ul>
					</div>
				</ul>
			</form>
		</div>
		<!--query-form-->
		<div class="btn-panel">
			 <div class="btn-group-sm">
            	<house:authorize authCode="CUSTOMERXX_VIEW">
                	<button type="button" class="btn btn-system" onclick="view()">查看</button>
                </house:authorize>
                <house:authorize authCode="CUSTOMERXX_VIEW">
               	 	<button type="button" class="btn btn-system" onclick="perfEstimate()">签单业绩预估</button>
             	</house:authorize>
            	<house:authorize authCode="CUSTOMERXX_XJD">
            	<button type="button" class="btn btn-system" onclick="xjd()">巡检单</button>
                </house:authorize>
                
                <house:authorize authCode="CUSTOMERXX_EXCEL">
                <button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
				</house:authorize>
				
				<house:authorize authCode="CUSTOMERXX_QDJL">
				<button type="button" class="btn btn-system" onclick="updateQdjl()">开工红包</button>
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
