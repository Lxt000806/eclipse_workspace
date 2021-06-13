<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>集成下单月报表</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
		/**初始化表格*/
		$(function(){
			/* 添加项目名称查询组件 */
			Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType","department1","department2","department3");
			$("#empCode").openComponent_employee();
			Global.JqGrid.initJqGrid("dataTable",{
				url:"${ctx}/admin/jcxdybb/goJqGrid",
				postData:$("#page_form").jsonForm(),
				height:$(document).height()-$("#content-list").offset().top-100,
	        	styleUI: "Bootstrap",
				onSortColEndFlag:true,
				rowNum : 10000000,
				pager:'1',
				colModel : [
				  {name : "code",index : "code",width : 80,label:"code",sortable : true,align : "left", hidden : true},
				  {name : "descr",index : "descr",width : 80,label:"descr",sortable : true,align : "left", hidden : true},
				  {name : "documentno",index : "documentno",width : 80,label:"档案号",sortable : true,align : "left"},
				  {name : "address",index : "address",width : 140,label:"楼盘地址",sortable : true,align : "left"},
				  {name : "integratefee",index : "integratefee",width : 80,label:"integratefee",sortable : true,align : "left", hidden : true},
				  {name : "integratedisc",index : "integratedisc",width : 80,label:"integratedisc",sortable : true,align : "left", hidden : true},
				  {name : "cupboardfee",index : "cupboardfee",width : 80,label:"cupboardfee",sortable : true,align : "left", hidden : true},
				  {name : "cupboarddisc",index : "cupboarddisc",width : 80,label:"cupboarddisc",sortable : true,align : "left", hidden : true},
				  {name : "custtype",index : "custtype",width : 80,label:"custtype",sortable : true,align : "left", hidden : true},
				  {name : "custtypedescr",index : "custtypedescr",width : 80,label:"客户类型",sortable : true,align : "left"},
				  {name : "ysjcje",index : "ysjcje",width : 80,label:"集成金额",sortable : true,align : "right",sum:true,excelLabel:"预算-集成金额"},
				  {name : "yscgje",index : "yscgje",width : 80,label:"橱柜金额",sortable : true,align : "right",sum:true,excelLabel:"预算-橱柜金额"},
				  {name : "ysyhje",index : "ysyhje",width : 80,label:"优惠金额",sortable : true,align : "right",sum:true,excelLabel:"预算-优惠金额"},
				  {name : "ysallje",index : "ysallje",width : 80,label:"总金额",sortable : true,align : "right",sum:true,excelLabel:"预算-总金额"},
				  {name : "zjjcje",index : "zjjcje",width : 80,label:"集成金额",sortable : true,align : "right",sum:true,excelLabel:"增减(时间段内)-集成金额"},
				  {name : "zjcgje",index : "zjcgje",width : 80,label:"橱柜金额",sortable : true,align : "right",sum:true,excelLabel:"增减(时间段内)-橱柜金额"},
				  {name : "zjyhje",index : "zjyhje",width : 80,label:"优惠金额",sortable : true,align : "right",sum:true,excelLabel:"增减(时间段内)-优惠金额"},
				  {name : "zjallje",index : "zjallje",width : 80,label:"总金额",sortable : true,align : "right",sum:true,excelLabel:"增减(时间段内)-集成金额"},
				  {name : "zjljjcje",index : "zjljjcje",width : 80,label:"集成金额",sortable : true,align : "right",sum:true,excelLabel:"增减(累计)-集成金额"},
				  {name : "zjljcgje",index : "zjljcgje",width : 80,label:"橱柜金额",sortable : true,align : "right",sum:true,excelLabel:"增减(累计)-橱柜金额"},
				  {name : "zjljyhje",index : "zjljyhje",width : 80,label:"优惠金额",sortable : true,align : "right",sum:true,excelLabel:"增减(累计)-优惠金额"},
				  {name : "zjljallje",index : "zjljallje",width : 80,label:"总金额",sortable : true,align : "right",sum:true,excelLabel:"增减(累计)-总金额"},
				  {name : "jsjcje",index : "jsjcje",width : 80,label:"集成金额",sortable : true,align : "right",sum:true,excelLabel:"增减(累计)-集成金额"},
				  {name : "jscgje",index : "jscgje",width : 80,label:"橱柜金额",sortable : true,align : "right",sum:true,excelLabel:"增减(累计)-橱柜金额"},
				  {name : "jsyhje",index : "jsyhje",width : 80,label:"优惠估算",sortable : true,align : "right",sum:true,excelLabel:"增减(累计)-优惠估算"},
				  {name : "jsallje",index : "jsallje",width : 80,label:"总金额",sortable : true,align : "right",sum:true,excelLabel:"增减(累计)-总金额"},
				  {name : "jsljjcje",index : "jsljjcje",width : 80,label:"集成金额",sortable : true,align : "right",sum:true,excelLabel:"决算(累计)-集成金额"},
				  {name : "jsljcgje",index : "jsljcgje",width : 80,label:"橱柜金额",sortable : true,align : "right",sum:true,excelLabel:"决算(累计)-橱柜金额"},
				  {name : "jsljyhje",index : "jsljyhje",width : 80,label:"优惠估算",sortable : true,align : "right",sum:true,excelLabel:"决算(累计)-优惠金额"},
				  {name : "jsljallje",index : "jsljallje",width : 80,label:"总金额",sortable : true,align : "right",sum:true,excelLabel:"决算(累计)-总金额"},
				  {name : "jcsjs",index : "jcsjs",width : 80,label:"集成设计师",sortable : true,align : "left"},
				  {name : "cupmandescr",index : "cupmandescr",width : 80,label:"橱柜设计师",sortable : true,align : "left"},
				  {name : "intmandescr",index : "intmandescr",width : 80,label:"集成预算员",sortable : true,align : "left"}
	            ]
			});
	        
			$("#dataTable").jqGrid("setGroupHeaders", {
				useColSpanStyle: true, 
				groupHeaders:[
					{startColumnName: "ysjcje", numberOfColumns:4, titleText:"预算"},
					{startColumnName: "zjjcje", numberOfColumns:4, titleText:"增减(时间段内)"},
					{startColumnName: "zjljjcje", numberOfColumns:4, titleText:"增减(累计)"},
					{startColumnName: "jsjcje", numberOfColumns:4, titleText: "决算(时间段内)"},
					{startColumnName: "jsljjcje", numberOfColumns:4, titleText: "决算(累计)"}
				] 
			});
		});
		
		function goto_query(){
			if ($.trim($("#address").val())==''){	
			    if ($.trim($("#dateFrom").val())==''){
			    	art.dialog({content: "审核开始日期不能为空！",width: 200});
					return false;
			    }
			    if ($.trim($("#dateTo").val())==''){
			    	art.dialog({content: "审核结束日期不能为空！",width: 200});
					return false;
			    }
				var begindate=$("#dateFrom").val();
				var endDate=$("#dateTo").val();
				if (begindate>endDate){
					art.dialog({content: "审核开始日期不能大于结束日期！",width: 200});
					return false;
				}
			}
			removecss();//移除掉上一次的css样式，否则只有对上次有效
		    changecss();
		    $("#dataTable").jqGrid('destroyFrozenColumns');
			$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/jcxdybb/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
			
		}
		
		function removecss(){
			$("div.frozen-div").remove();
		}
		//frozen并且分组的时候,有冻结列和未冻结列的高度不同。
		function changecss(){  
			$(".frozen-div table thead tr:eq(1) th:eq(0) div").css({"height":"46.5px"});	
			
		}	
		
		function clearForm(){
			$("#page_form input[type='text']").val('');
			$("#page_form select[id!=sType]").val('');
			$("#custType").val("");
			$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
		}
		function doExcel(){
			var url = "${ctx}/admin/jcxdybb/doExcel";
			doExcelAll(url);
		}
		function view(){
			var data = $("#page_form").jsonForm();
			var ret = selectDataTableRow();
			if(ret){
	        	Global.Dialog.showDialog("jcxdybbView",{
	        		title:"查看下单月信息",
	        	  	url:"${ctx}/admin/jcxdybb/goView",
	        	  	postData:{
	        	  		code:ret.code,
						address:ret.address,
						descr:ret.descr,
						dateFrom:data.dateFrom,
						dateTo:data.dateTo
	        	  	},
	        	  	height: 750,
	        	  	width:1200
	        	});
			}else{
				art.dialog({
					content:"请选择一条记录"
				});
			}
		}
	</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>							
							<label>审核日期从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
								   onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								   value="<fmt:formatDate value='${customer.dateFrom }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>								
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" 
								   onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								   value="<fmt:formatDate value='${customer.dateTo }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>集成设计师</label>
							<input type="text" id="empCode" name="empCode" style="width:160px;" value="${customer.empCode }"/>
						</li>
						<li>
							<label>客户类型</label>
							<house:custTypeMulit id="custType"  ></house:custTypeMulit>
						</li>
						<li>
							<label for="department1">一级部门</label>
							<select id="department1" name="department1"></select>
						</li>
						<li>
							<label for="department2">二级部门</label>
							<select id="department2" name="department2"></select>
						</li>
						<li>
							<label for="department3">三级部门</label>
							<select id="department3" name="department3"></select>
						</li>
						<li>
							<label>楼盘地址</label>
							<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }"/>
						</li>
						<li class="search-group">
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
						</li>
					</ul>
				</form>
			</div>
			
			<div class="clear_float"> </div>
			
			<div class="btn-panel" >
				<div class="btn-group-sm"  >
					<house:authorize authCode="JCXDYBB_VIEW">
						<button type="button" class="btn btn-system" onclick="view()">查看</button>
	                </house:authorize>
					<button type="button" class="btn btn-system" onclick="doExcel()">输出至Excel</button>
			    </div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</body>
</html>
