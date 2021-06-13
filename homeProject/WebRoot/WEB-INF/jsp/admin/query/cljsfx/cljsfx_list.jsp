<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>材料结算分析</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		p.Remarks {color:red}
	</style>
	<script type="text/javascript">
	function View(){
		var ret=selectDataTableRow();
		var dateFrom=$("#dateFrom").val();
		var dateTo=$("#dateTo").val();
		var custType=$("#custType").val();	
		var itemType1=$("#itemType1").val();	
	    if (ret) {  
	    	var dept1code=ret.dept1code;
			var dept2code=ret.dept2code;  	
	     	Global.Dialog.showDialog("cljsfxView",{
				title:"销售明细",
			  	url:"${ctx}/admin/cljsfx/goView?",
			  	postData:{custCheckDateFrom:dateFrom,custCheckDateTo:dateTo,custType:custType,
			  			  department1:dept1code,department2:dept2code,itemType1:itemType1},			  	
			  	height:600,
			  	width:1000,
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
		var url = "${ctx}/admin/cljsfx/doExcel";
		var tableId="dataTable";
		doExcelAll(url, tableId);
	}    
				
	/**初始化表格*/
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
		var dataSet = {
			firstSelect:"itemType1",
			firstValue:"${customer.itemType1}",
			disabled:"true"
		};
		Global.LinkSelect.setSelect(dataSet);		
		$("#JCDesigner").openComponent_employee();
		Global.JqGrid.initJqGrid("dataTable",{    					     
			height:$(document).height()-$("#content-list").offset().top-100,			
			styleUI: "Bootstrap",
			colModel : [
				{name: "dept1descr", index: "dept1descr", width: 121, label: "事业部", sortable: true, align: "left",count:true},
				{name: "dept1code", index: "dept1code", width: 121, label: "事业部", sortable: true, align: "left",hidden:true},
				{name: "dept2descr", index: "dept2descr", width: 121, label: "二级部门", sortable: true, align: "left"},
				{name: "dept2code", index: "dept2code", width: 121, label: "二级部门", sortable: true, align: "left",hidden:true},
				{name: "custcount", index: "custcount", width: 90, label: "结算套数", sortable: true, align: "right", sum: true},
				{name: "area", index: "area", width: 90, label: "结算面积", sortable: true, align: "right", sum: true},
				{name: "lineamount", index: "lineamount", width: 120, label: "结算金额", sortable: true, align: "right", sum: true},
				{name: "allcost", index: "allcost", width: 120, label: "结算成本", sortable: true, align: "right", sum: true},
				{name: "profit", index: "profit", width: 120, label: "结算利润", sortable: true, align: "right", sum: true},
				{name: "grossprofit", index: "grossprofit", width: 120, label: "结算每平方毛利", sortable: true, align: "right"},
				{name: "netprofit", index: "netprofit", width: 120, label: "结算每平方利润", sortable: true, align: "right"}
			],
	        onSortColEndFlag:true,
			rowNum:100000,  
			pager :"1", 
	        gridComplete:function(){	
	        	var sumgrossprofit = 0;
	          	var avggrossprofit = 0;       
	          	var grossprofit = Global.JqGrid.allToJson("dataTable","grossprofit"); //结算每平方毛利
	          	arr = grossprofit.fieldJson.split(",");
	          	var sumnetprofit = 0;
	          	var avgnetprofit = 0;       
	          	var netprofit = Global.JqGrid.allToJson("dataTable","netprofit"); //结算每平方毛利
	          	arry = netprofit.fieldJson.split(",");
	          	for(var i = 0;i < arr.length; i++){			
					sumgrossprofit=sumgrossprofit+parseFloat(arr[i]);
					sumnetprofit=sumnetprofit+parseFloat(arry[i]);									
				}	
				avggrossprofit =sumgrossprofit/arr.length;
				avgnetprofit =sumnetprofit/arry.length;
				if (isNaN(avggrossprofit)){
					avggrossprofit=0;
				}
				if (isNaN(avgnetprofit)){
					avgnetprofit=0;
				}  	
				var statistcsMethod=$("#statistcsMethod").val();
				if (statistcsMethod=="1"){
					if ((avggrossprofit.toFixed(3)*1000)%10==0){
						if((avggrossprofit.toFixed(2)*100)%10==0){
							if((avggrossprofit.toFixed(1)*10)%10==0){
								$("#dataTable").footerData("set",{"grossprofit":avggrossprofit},false);
							}else{
								$("#dataTable").footerData("set",{"grossprofit":avggrossprofit.toFixed(1)},false);
							}
						}
						else{
							$("#dataTable").footerData("set",{"grossprofit":avggrossprofit.toFixed(2)},false);
						}
					}else{
						$("#dataTable").footerData("set",{"grossprofit":avggrossprofit.toFixed(3)},false);
					}
					if ((avgnetprofit.toFixed(3)*1000)%10==0){
						if ((avgnetprofit.toFixed(2)*100)%10==0){
							if((avgnetprofit.toFixed(1)*10)%10==0){
								$("#dataTable").footerData("set",{"grossprofit":avgnetprofit},false);
							}else{
								$("#dataTable").footerData("set",{"netprofit":avgnetprofit.toFixed(1)},false);
							}
						}
						else{
							$("#dataTable").footerData("set",{"netprofit":avgnetprofit.toFixed(2)},false);
						}
					}else{
						$("#dataTable").footerData("set",{"netprofit":avgnetprofit.toFixed(3)},false);
					}
				}else if(statistcsMethod=="2"){
					if ((avggrossprofit.toFixed(4)*10000)%10==0){
						if ((avggrossprofit.toFixed(3)*1000)%10==0){
							if((avggrossprofit.toFixed(2)*100)%10==0){
								if((avggrossprofit.toFixed(1)*10)%10==0){
									$("#dataTable").footerData("set",{"grossprofit":avggrossprofit},false);
								}else{
									$("#dataTable").footerData("set",{"grossprofit":avggrossprofit.toFixed(1)},false);
								}
							}
							else{
								$("#dataTable").footerData("set",{"grossprofit":avggrossprofit.toFixed(2)},false);
							}
						}else{
							$("#dataTable").footerData("set",{"grossprofit":avggrossprofit.toFixed(3)},false);
						}
					}else{
						$("#dataTable").footerData("set",{"grossprofit":avggrossprofit.toFixed(4)},false);
					}
					if ((avgnetprofit.toFixed(4)*10000)%10==0){
						if ((avgnetprofit.toFixed(3)*1000)%10==0){
							if ((avgnetprofit.toFixed(2)*100)%10==0){
								if((avgnetprofit.toFixed(1)*10)%10==0){
									$("#dataTable").footerData("set",{"grossprofit":avgnetprofit},false);
								}else{
								$("#dataTable").footerData("set",{"netprofit":avgnetprofit.toFixed(1)},false);
								}
							}
							else{
								$("#dataTable").footerData("set",{"netprofit":avgnetprofit.toFixed(2)},false);
							}
						}else{
							$("#dataTable").footerData("set",{"netprofit":avgnetprofit.toFixed(3)},false);
						}
					}else{
						$("#dataTable").footerData("set",{"netprofit":avgnetprofit.toFixed(4)},false);
					}
				}
			},
			ondblClickRow: function(){
	        	View();
	        },
	        onSortCol:function(index,iCol,sortorder){
				if(index!=""){
					var rows = $("#dataTable").jqGrid("getRowData");
		   			rows.sort(function (a, b) {
		   				if(index=="area"){
		   					return (a.area-b.area)*(sortorder=="desc"?1:-1);
		   				}else if(index=="dept1descr"){
		   					return (a.dept1code-b.dept1code)*(sortorder=="desc"?1:-1);
		   				} 
		   				else if(index=="dept2descr"){
		   					return (a.dept2code-b.dept2code)*(sortorder=="desc"?1:-1);
		   				} 
		   				else if(index=="custcount"){
		   					return (a.custcount-b.custcount)*(sortorder=="desc"?1:-1);
		   				} 
		   				else if(index=="lineamount"){
		   					return (a.lineamount-b.lineamount)*(sortorder=="desc"?1:-1);
		   				} 
		   				else if(index=="allcost"){
		   					return (a.allcost-b.allcost)*(sortorder=="desc"?1:-1);
		   				} 
		   				else if(index=="profit"){
		   					return (a.profit-b.profit)*(sortorder=="desc"?1:-1);
		   				} 
		   				else if(index=="netprofit"){
		   					return (a.netprofit-b.netprofit)*(sortorder=="desc"?1:-1);
		   				} 
		   				else if(index=="grossprofit"){
		   					return (a.grossprofit-b.grossprofit)*(sortorder=="desc"?1:-1);
		   				} 
		   			});    
		   			Global.JqGrid.clearJqGrid("dataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("dataTable",v);
					});
				}
			}     
		});
		window.goto_query = function(){	
			var itemType1 = $("#itemType1").val();
			var dateFrom = $("#dateFrom").val();
			var dateTo = $("#dateTo").val();
			if (itemType1 == ""){
				art.dialog({content: "请选择材料类型1",width: 200});
				return false;
			}
			if (dateFrom == ""){
				art.dialog({content: "请选择起始客户结算时间！",width: 200});
				return false;
			}
			if (dateTo == ""){
				art.dialog({content: "请选择截止客户结算时间！",width: 200});
				return false;
			}
			if ($("#statistcsMethod").val()==1){
		    	$("#dataTable").jqGrid("hideCol", "dept2descr");
		    }else{
		    	$("#dataTable").jqGrid("showCol", "dept2descr");
		    }
		    $("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/cljsfx/goJqGrid",datatype:"json",postData:$("#page_form").jsonForm(),
		    page:1,fromServer: true,}).trigger("reloadGrid");
		};
	});
	
	</script>	
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" id="expired" name="expired" value="F" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1" ></select>
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType"  selectedValue="1"></house:custTypeMulit>
					</li>
					<li>
						<label>客户结算时间从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd'/>" />
					</li>				
												
					<li> 
					    <label>统计方式</label>
						<select id="statistcsMethod" name="statistcsMethod" >								
							<option value="1">按一级部门统计</option>
							<option value="2">按二级部门统计</option>								
						</select>
					</li>	
					<li id="loadMore" >
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();" >查询</button>
					</li>
					<li>
						<p class="Remarks">说明：主材不包含服务性产品</p>
					</li>					
				</ul>
			</form>
		</div>
		<div class="btn-panel" >
			<div class="btn-group-sm">
			<house:authorize authCode="CLJSFX_VIEW">
				<button type="button" id ="btnview" class="btn btn-system" onclick="View()">查看</button>
			</house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
			</div>
			<div id="content-list">               
				<table id= "dataTable"></table>  
				<div id="dataTablePager"></div>
			</div>			
		</div> 
	</div>
</body>
</html>


