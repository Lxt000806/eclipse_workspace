<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>材料结算确认</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		p.Remarks {color:red}
	</style>
	<script type="text/javascript">
	var parentWin=window.opener;
	/**初始化表格*/
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
		var dataSet = {
			firstSelect:"itemType1",
			firstValue:"${itemCheck.itemType1}",
			disabled:"true"
		};
		Global.LinkSelect.setSelect(dataSet);
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/cljsgl/goJqGridCljsqr",
			postData:{custCode:"${itemCheck.custCode}",itemType1:"${itemCheck.itemType1}"},
            styleUI: "Bootstrap",   
			height:$(document).height()-$("#content-list").offset().top-80,
			colModel : [
			  	{name: "itemtype1descr", index: "itemtype1descr", width: 140, label: "材料类型1", sortable: true,align:"left",count:true},
				{name: "itemcode", index: "itemcode", width: 84, label: "材料编码", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 227, label: "材料名称", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 118, label: "需求数量", sortable: true, align: "right", sum: true},
				{name: "sendqty", index: "sendqty", width: 118, label: "发货数量", sortable: true, align: "right", sum: true}
            ],
            loadonce: true,
        	rowNum:100000,  
			pager :"1", 
		});
	});  	

	function save(){
		var datas=$("#page_form").jsonForm();
		$.ajax({
			url:"${ctx}/admin/cljsgl/doCljsglUpdate",
			type: "post",
			data: datas,
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if(obj.rs){
			    	var selectRows = [];		 
					selectRows.push("1");		
					Global.Dialog.returnData = selectRows;	
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin();
					    }
					});
		    	}else{
		    		showTime(),
					art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
			}
		});
	}
	function showTime() 
	{ 	
		setInterval ("closeCljsgl()", 2000); 	
	}
	function closeCljsgl(){
		closeWin();
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
		      		<button type="button" id="saveBtn" class="btn btn-system" onclick="save()">确认结算</button>
					<button type="button" class="btn btn-system" onclick="closeWin(false)">取消</button>
		    	</div>
		   	</div>
		</div>
        <form hidden="true" role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="exportData" id="exportData">
				<ul class="ul-form">
					<div class="validate-group row" >
					<li>
						<label>结算单号</label>
						<input type="text" id="no" name="no" value="${itemCheck.no}" readonly="true" />
					</li>
					</div>
					<div class="validate-group row" >
					<li >
						<label >客户编号</label>
						<input type="text" id="custCode" name="custCode" style="width:160px;" value="${itemCheck.custCode }" />
					</li>
					<li >
						<label >楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" value="${itemCheck.address }" readonly="readonly"/>
					</li>	
					</div>
					<div class="validate-group row" >		
					<li>		
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1" value="${itemCheck.itemType1 }" ></select>
					</li>
					<li>
						<label>结算状态</label>
						<house:xtdm id="status" dictCode="ITEMCHECKSTATUS" value="${itemCheck.status }" disabled="true"></house:xtdm>
					</li>	
					</div>
					<div class="validate-group row" >				
					<li>
						<label>申请日期</label>
						<input type="text" id="appdate" name="appdate" value="${itemCheck.appdate}" disabled="true"/>	
					</li>
					<li>
						<label>审核日期</label>
						<input type="text" id="confirmDate" name="confirmDate" value="${itemCheck.confirmDate}" disabled="true"/>	
					</li>
					</div>
					<div class="validate-group row" >
					<li>	
						<label>申请人</label>
						<input type="text" id="appEmp" name="appEmp" style="width:79px;" value="${itemCheck.appEmp}" disabled="true"/> 
						<input type="text" id="appEmpDescr" name="appEmpDescr" style="width:79px;" value="${itemCheck.appEmpDescr}" disabled="true"/> 
					</li>
					<li>	
						<label>审核人</label>
						<input type="text" id="confirmEmp" name="confirmEmp" style="width:79px;" value="${itemCheck.confirmEmp}" disabled="true"/> 
						<input type="text" id="confirmEmpDescr" name="confirmEmpDescr" style="width:79px;" value="${itemCheck.confirmEmpDescr}" disabled="true"/> 
					</li>
					</div>
					<div class="validate-group row" >					
					<li>
						<label class="control-textarea" >申请说明</label>
						<textarea id="appRemark" name="appRemark" style="width:160px">${itemCheck.appRemark }</textarea>
					</li>
					<li>
						<label class="control-textarea" >审核说明</label>
						<textarea id="confirmRemark" name="confirmRemark" style="width:160px">${itemCheck.confirmRemark }</textarea>
					</li>
					</div>
				</ul>
			</form>
		<li>
			<p class="Remarks">存在需求数量<>发货数量，详见列表：</p>
		</li>
		<div class="pageContent">			
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


