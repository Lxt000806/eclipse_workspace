<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>工地报备_分析</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	function view(){
		var ret = selectDataTableRow();
		var beginDate = $.trim($("#beginDate").val());
		var endDate = $.trim($("#endDate").val());
		var tgyy=$.trim($("#tgyy").val());
	    if (ret) {    	
	      Global.Dialog.showDialog("builderAnalysisView",{
			  title:"查看工地报备管理——停工排行",
			  url:"${ctx}/admin/builderAnalysis/goView",
			  postData:{beginDate:beginDate,code:ret.custcode,endDate:endDate,tgyy:tgyy},		 
			  height:400,
			  width:800,
			  returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	//导出EXCEL
	function doExcel(){
		if($.trim($("#statistcsMethod").val())=='1'){
			doExcelNow("工地报备——停工排行","dataTable");
		}else if ($.trim($("#statistcsMethod").val())=='2'){
			doExcelNow("工地报备——本日报备","dataTableGroupByBrbb"); 
		}else if ($.trim($("#statistcsMethod").val())=='3'){
		doExcelNow("工地报备——停工分析","dataTableGroupByTgfx"); }
	}      
	
	function goto_query(){
		if($.trim($("#beginDate").val())==''){
				art.dialog({content: "开始日期不能为空",width: 200});
				return false;
		} 
		if($.trim($("#endDate").val())==''){
				art.dialog({content: "结束日期不能为空",width: 200});
				return false;
		}
	     var dateStart = Date.parse($.trim($("#beginDate").val()));
	     var dateEnd = Date.parse($.trim($("#endDate").val()));
	     if(dateStart>dateEnd){
	    	 art.dialog({content: "开始日期不能大于结束日期",width: 200});
				return false;
	     } 
	     
	     if ($("#expired_show").get(0).checked) {
		 	$("#expired").val("F");  
		 }else{
		 	$("#expired").val("T"); 
		 }
	     if ($("#statistcsMethod").val() == '1') {		//  content-list-groupByTgfx 是表工地停工原因分析
			$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/builderAnalysis/builderJqGridgdtgap",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
		} else if ($("#statistcsMethod").val() == '2'){
			$("#dataTableGroupByBrbb").jqGrid("setGridParam",{url:"${ctx}/admin/builderAnalysis/builderJqGridbrbb",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
		} else if ($("#statistcsMethod").val() == '3'){
			$("#dataTableGroupByTgfx").jqGrid("setGridParam",{url:"${ctx}/admin/builderAnalysis/builderJqGridtgfx",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
		}	
	}
		
			
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{    					     //工地停工排行
			url:"${ctx}/admin/builderAnalysis/builderJqGridgdtgap",
			height:$(document).height()-$("#content-list").offset().top-100,
			width:$(document).width()-$("#content-list").offset().right-600,
			styleUI: 'Bootstrap',
			colModel : [
					{name: "custcode", index: "custcode", width: 180, label: "客户编号", sortable: true, align: "left", count: true},
			   		{name: "address", index: "address", width: 180, label: "楼盘", sortable: true, align: "left", count: true},
					{name: "projectmandescr", index: "projectmandescr", width: 80, label: "项目经理", sortable: true, align: "left"},
					{name: "department2descr", index: "department2descr", width: 90, label: "工程部", sortable: true, align: "left"},
		       	    {name: "downday", index: "downday", width: 100, label: "累计停工天数", sortable: true, align: "left",sum: true},
		       	    {name : "enddate",index : "enddate",width : 150,label:"最后停工报备日",sortable : true,align : "left",formatter: formatTime}
		          ],
		          loadonce: true,
			      rowNum:100000,  
			      pager :'1'
		});
	    Global.JqGrid.initJqGrid("dataTableGroupByBrbb",{   			//本日报备情况分析
			url:"${ctx}/admin/builderAnalysis/builderJqGridbrbb",
			height:$(document).height()-$("#content-list").offset().top-100,
			width:$(document).width()-$("#content-list").offset().right-600,
			styleUI: 'Bootstrap',
			colModel : [
					{name: "projectmandescr", index: "projectmandescr", width: 80, label: "项目经理", sortable: true, align: "left",count:true},
					{name: "department2", index: "department2", width: 90, label: "工程部", sortable: true, align: "left"},
					{name: "buildnum", index: "buildnum", width: 100, label: "在建套数", sortable: true, align: "left", sum: true},
		       	    {name: "repnum", index: "repnum", width: 100, label: "报备套数", sortable: true, align: "left",sum: true},
		       	    {name: "norepnum", index: "norepnum", width: 100, label: "未报备套数", sortable: true, align: "left",sum: true},
		       	    {name: "repper", index: "repper", width: 100, label: "报备百分比", sortable: true, align: "left"}
	           ],
	           loadonce: true,
	           rowNum:100000
		}); 
		Global.JqGrid.initJqGrid("dataTableGroupByTgfx",{   			//工地停工原因分析
			url:"${ctx}/admin/builderAnalysis/builderJqGridtgfx",
			height:$(document).height()-$("#content-list").offset().top-550,
			styleUI: 'Bootstrap',
			colModel : [
					{name: "tgyy", index: "tgyy", width: 110, label: "停工原因", sortable: true, align: "left"},
					{name: "downday", index: "downday", width: 110, label: "累计停工天数", sortable: true,align: "left",sum: true},
					{name: "dayper", index: "dayper", width: 110, label: "原因占比", sortable: true, align: "left"},
		       ],
		        loadonce: true
		});
		$("#content-list-groupByBrbb").hide();
		$("#content-list-groupByTgfx").hide(); 
	});
	
	function change(){    								//  content-list 是表工地停工排行
		var tableId;									//  content-list-groupByBrbb 是表本日报备情况分析
		if ($("#statistcsMethod").val() == '1') {		//  content-list-groupByTgfx 是表工地停工原因分析
			tableId = 'dataTable';
			$("#content-list").show();
			$("#content-list-groupByBrbb").hide();
			$("#content-list-groupByTgfx").hide();
			$("#beginDate").show();
			$("#endDate").show();
			$("#beginDatelabel").show();
			$("#endDatelabel").show();
			$("#btnview").show();
			$("#builderday").removeAttr("hidden","true");
		} else if ($("#statistcsMethod").val() == '2'){ 
			tableId = 'dataTableGroupBySignCZY';
			$("#content-list").hide();
			$("#content-list-groupByBrbb").show();
			$("#content-list-groupByTgfx").hide();
			$("#beginDate").hide();
			$("#endDate").hide();
			$("#beginDatelabel").hide();
			$("#endDatelabel").hide();
			$("#btnview").hide();
			$("#builderday").attr("hidden","true");
		} else if ($("#statistcsMethod").val() == '3'){
			tableId = 'dataTableGroupBySignCZY';
			$("#content-list").hide();
			$("#content-list-groupByBrbb").hide();
			$("#content-list-groupByTgfx").show();
			$("#beginDate").show();
			$("#endDate").show();
			$("#beginDatelabel").show();
			$("#endDatelabel").show();
			$("#btnview").hide();
			$("#builderday").attr("hidden","true");
		}	
		goto_query();
	}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" id="expired" name="expired" value="F" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
						<div class="validate-group">
						<li>
							<label>工程部</label>
							<house:department2 id="Department2" dictCode="03"  depType="3"  ></house:department2>
						</li>						
						<li>
							<label id="beginDatelabel">开始时间</label>
							<input type="text" id="beginDate" name="beginDate" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${builderRep.beginDate}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label id="endDatelabel">结束时间</label>
							<input type="text" id="endDate" name="endDate" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${builderRep.endDate}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>停工原因</label>
							<house:xtdmMulit id="tgyy" dictCode="" sql="select Cbm Code,Note Descr from tXTDM where id='BUILDSTATUS' and cbm not in ('1','2') order by ibm" 
								sqlValueKey="Code" sqlLableKey="Descr"   selectedValue='${builderRep.tgyy}'></house:xtdmMulit>
						</li>
						</div>
						<li> 
						    <label>统计方式</label>
							<select id="statistcsMethod" name="statistcsMethod" onchange="change()">
								<option value="1">工地停工排行</option>
								<option value="2">本日报备情况分析</option>
								<option value="3">工地停工原因分析</option>
							</select>
						</li>	
						<li id="builderday">
							<input type="checkbox"  id="expired_show" name="expired_show"  />只显示最后停工报备日为本日&nbsp
						</li>
					</ul>		
					<ul class="ul-form">
					<li id="loadMore" >
							<button type="button"  class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
					</li>
				
				</ul>
			</form>
		</div>
		<div class="btn-panel" >
		       <div class="btn-group-sm"  >
		     	    <house:authorize authCode="builderAnalysis_VIEW">
						<button type="button" id = "btnview" class="btn btn-system "  onclick="view()">查看</button>
					</house:authorize>
	                    <button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
				</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
			<div id="content-list-groupByBrbb">
				<table id= "dataTableGroupByBrbb"></table> 
				<div id="dataTableGroupByBrbb"></div>
			</div>
			<div id="content-list-groupByTgfx">
				<table id= "dataTableGroupByTgfx"></table> 
				<div id="dataTableGroupByTgfx"></div>
			</div>
		</div> 
	</div>
</body>
</html>


