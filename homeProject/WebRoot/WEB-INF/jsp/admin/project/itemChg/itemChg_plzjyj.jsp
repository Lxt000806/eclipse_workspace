<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  	<title>批量增减业绩</title>
  	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  	<META HTTP-EQUIV="expires" CONTENT="0"/>
  	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
	/**初始化表格*/
	$(function () {
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1");
	
		Global.JqGrid.initJqGrid("dataTable", { 
			height: $(document).height() - $("#content-list").offset().top - 82,
			multiselect: true,
			rowNum: 10000,
			colModel : [
			    {name: "isservice", index: "isservice", width: 200, label: "isservice", sortable: true, align: "left",count:true,hidden:true},
			    {name: "iscupboard", index: "iscupboard", width: 200, label: "iscupboard", sortable: true, align: "left",count:true,hidden:true},
			    {name: "itemtype1", index: "itemtype1", width: 200, label: "itemtype1", sortable: true, align: "left",count:true,hidden:true},
			    {name: "code", index: "code", width: 200, label: "code", sortable: true, align: "left",count:true,hidden:true},
			    {name: "documentno", index: "documentno", width: 90, label: "档案号", sortable: true, align: "left",},
			    {name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left",},
			    {name: "address", index: "address", width: 200, label: "楼盘", sortable: true, align: "left",count:true},
			    {name: "itemdescr", index: "itemdescr", width: 90, label: "材料类型1", sortable: true, align: "left",},
		  		{name: "isservicedescr", index: "isservicedescr", width: 105, label: "是否服务性产品", sortable: true, align: "left",},
		  		{name: "iscupboarddescr", index: "iscupboarddescr", width: 85, label: "是否橱柜", sortable: true, align: "left",},
		  		{name: "planfee", index: "planfee", width: 85, label: "预算金额", sortable: true, align: "left",sum:true,},
		  		{name: "amount", index: "amount", width: 95, label: "累计增减金额", sortable: true, align: "left",sum:true,},
		  		{name: "feeper", index: "feeper", width: 70, label: "占比", sortable: true, align: "left",formatter:divFormat,},
			],
      	});
      	function divFormat (cellvalue, options, rowObject){ 
		    return myRound(myRound(cellvalue,4)*100,4)+"%";
		}	
      	window.goto_query = function(){
			if($("#confirmDateFrom").val()=="" || $("#confirmDateTo").val() ==""){
				art.dialog({
					content:"请选择完整审核时间段",
				});
				return;
			}
			$("#dataTable").jqGrid("setGridParam",{datatype:'json',postData:$("#page_form").jsonForm(),page:1,
						url:"${ctx}/admin/itemChg/goPlzjyjJqgrid",}).trigger("reloadGrid");
		}
      	//全选
		$("#selectallBtn").on("click", function () {
			Global.JqGrid.jqGridSelectAll("dataTable", true);
		});
      	//不选
		$("#unselectallBtn").on("click", function () {
			Global.JqGrid.jqGridSelectAll("dataTable", false);
		});
		
		$("#saveBtn").on("click",function(){
			var ids = $("#dataTable").jqGrid('getGridParam','selarrrow');
			if( ids.length==0){
				Global.Dialog.infoDialog("请选择设置增减业绩明细");
	   		   	return false;
			}
			var param= Global.JqGrid.selectToJson("dataTable");
			Global.Form.submit("page_form","${ctx}/admin/itemChg/doPlzjyj",param,function(ret){
				if(ret.rs==true){
					art.dialog({
						content:ret.msg,
						time:500,
						beforeunload:function(){
							$("#dataTable").jqGrid("setGridParam",{datatype:'json',postData:$("#page_form").jsonForm(),page:1,
									url:"${ctx}/admin/itemChg/goPlzjyjJqgrid",}).trigger("reloadGrid");
						}
					}); 
					$("#_form_token_uniq_id").val(ret.token.token);
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
  </script>
</head>
<body>
<div class="body-box-list">
  	<div class="panel panel-system">
    	<div class="panel-body">
      		<div class="btn-group-xs">
        		<button type="button" class="btn btn-system" id="saveBtn">批量修改</button>
        		<button type="button" class="btn btn-system" id="selectallBtn">全选</button>
        		<button type="button" class="btn btn-system" id="unselectallBtn">不选</button>
        		<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
        		<button type="button" class="btn btn-system " onclick="doExcelNow('批量增减业绩', null, 'page_form')" title="导出当前excel数据" >
									<span>导出excel</span></button>
      		</div>
    	</div>
  	</div>
  	<div class="query-form">
    	<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
	    	<house:token></house:token>
			<input type="hidden" name="jsonString" value="" />
	      	<ul class="ul-form">
	      		<li>
					<label>材料类型1</label>
					<select id="itemType1" name="itemType1" style="width: 160px;"></select>
				</li>
				<li>
             	 <label>累计增减金额超过</label>
	              <input type="text" id="amount" name="amount"/>
	            </li>
	            <li>
	              <label>占比超过</label>
	              <input type="text"  id="chgPer" name="chgPer"/>
	            </li>
				<li>
					<label>审核时间</label>
					<input type="text" id="confirmDateFrom" name="confirmDateFrom" class="i-date" style="width:160px;" 
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
				</li>
				<li>
					<label>至</label>
					<input type="text" id="confirmDateTo" name="confirmDateTo" class="i-date" style="width:160px;" 
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
				</li>
	        	<li>
					<label>服务性产品</label>
					<house:xtdm id="isService" dictCode="YESNO" style="width:160px;"></house:xtdm>
				</li>
				<li>
					<label>是否橱柜</label>
					<house:xtdm id="isCupboard" dictCode="YESNO" style="width:160px;"></house:xtdm>
				</li>
				<li>
					<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
					<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
				</li>
	      	</ul>
    	</form>
  	</div>
  	<div id="content-list">
    	<table id="dataTable"></table>
  	</div>
</div>
</body>
</html>


