<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>客户付款批量打印</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">

</script>
</head>

<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired" name="expired" value="${giftApp.expired }" />	
				<input type="hidden" name="m_umState" id="m_umState" value="${giftApp.m_umState}"/>		
				<input type="hidden" name="jsonString" value="" />		
			<ul class="ul-form">
			  			<li>
							<label >档案编号从</label>
							<input type="text" id="documentNoFrom" name="documentNoFrom" style="width:160px;" />    
						</li>
						<li>
							<label >到</label>
							<input type="text" id="documentNoTo" name="documentNoTo" style="width:160px;" />    
						</li>
						<li>
							<label>客户类型</label>
							<house:custTypeMulit id="custType" afterLoadTree="selectCustTypes"></house:custTypeMulit>
						</li>
						<li>
							<label>客户状态</label> 
							<house:xtdmMulit id="status" dictCode="CUSTOMERSTATUS" selectedValue="4,5"></house:xtdmMulit>
						</li>
						<li>
							<label >转施工日期从</label>
							<input type="text" style="width:160px;" id="toConstructDateFrom" name="toConstructDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>
							<label >到</label>
							<input type="text" style="width:160px;" id="toConstructDateTo" name="toConstructDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
					    <li>
						    <label>签订日期从</label> 
						    <input type="text" id="signDateFrom" name="signDateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>
							<label>至</label> 
							<input type="text" id="signDateTo" name="signDateTo" class="i-date" style="width:160px;"onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label >楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;" />    
						</li>
						<li>
						    <label>公司</label>
						    <house:dict id="company" dictCode=""
						        sql="select Descr SQL_LABEL_KEY, Code SQL_VALUE_KEY from tTaxPayee where Expired = 'F'"></house:dict>
						</li>
						<li id="loadMore" >
								<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>	
						</li>
					</ul>
			</form>
		</div>
		<div class="btn-panel" >
	     <div class="btn-group-sm"  >
		      <button type="button" class="btn btn-system " id="print" >打印</button>
		      <button type="button" class="btn btn-system " id="receivableBtn" >打印应收记账表</button>
		      <button type="button" class="btn btn-system " id="preferentialBtn" >打印优惠承担表</button>
		      <button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">关闭</button>
		      <button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
	     </div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
	
	<script>
        var parentWin=window.opener;
		$(function(){
	        //初始化表格
	        Global.JqGrid.initJqGrid("dataTable",{
	            url:"${ctx}/admin/custPay/goQPrintJqGrid",
	            multiselect: true,
	            postData:{
	                status:"4,5",
	                custType: $("#custType").val()
	            },
	            height:$(document).height()-$("#content-list").offset().top-110,
	            styleUI: 'Bootstrap', 
	            colModel : [
	              {name : 'custcode',index : 'custcode',width : 70,label:'客户编号',sortable : true,align : "left"},
	              {name : 'documentno',index : 'documentno',width : 70,label:'档案编号',sortable : true,align : "left"},
	              {name : 'custdescr',index : 'custdescr',width : 70,label:'客户名称',sortable : true,align : "left"},
	              {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left"},
	              {name : 'custtypedescr',index : 'custtypedescr',width : 70,label:'客户类型',sortable : true,align : "left"},
	              {name : 'statusdescr',index : 'statusdescr',width : 80,label:'客户状态',sortable : true,align : "left"},
	              {name : 'endcodedescr',index : 'endcodedescr',width : 70,label:'结束代码',sortable : true,align : "left"},
	              {name : 'toconstructdate',index : 'toconstructdate',width : 90,label:'转施工日期',sortable : true,align : "left",formatter:formatDate},
	              {name : 'signdate',index : 'signdate',width : 90,label:'签订日期',sortable : true,align : "left",formatter:formatDate},
	            ]
	       
	        });
	       //打印
	        $("#print").on("click",function(){      
	            var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
	            if(ids.length==0){
	                Global.Dialog.infoDialog("请选择一条或多条记录进行打印！");
	                return;
	            }
	            var custCodes= "";
	            $.each(ids,function(k,id){
	                var row = $("#dataTable").jqGrid('getRowData', id);
	                custCodes = custCodes + "'" + $.trim(row.custcode) + "',";      
	                    
	            });
	            if (custCodes != "") {
	                custCodes = custCodes.substring(0,custCodes.length-1);
	            }
	            var reportName = "custPay_main.jasper";
	            Global.Print.showPrint(reportName, {
	                custCodes:custCodes,
	                LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
	                SUBREPORT_DIR: "<%=jasperPath%>" 
	            });
	        }); 
	        
	        $("#receivableBtn").on("click", function() {
	            var grid = $("#dataTable")    
	            var ids = grid.getGridParam('selarrrow')
	            
	            if (!ids.length) {
	                Global.Dialog.infoDialog("请选择一条或多条记录进行打印！")
	                return
	            }
	            
	            var custCodes = []
	            for (var i = 0; i < ids.length; i ++)
	                custCodes.push(grid.getRowData(ids[i]).custcode)
	            
	            Global.Print.showPrint("custPay_receivableTable.jasper", {
	                custCodes: "'" + custCodes.join("', '") + "'",
	                LogoFile: "<%=basePath%>jasperlogo/logo.jpg",
	                SUBREPORT_DIR: "<%=jasperPath%>" 
	            })
	        })
	        
	        $("#preferentialBtn").on("click", function() {
	            var grid = $("#dataTable")    
	            var ids = grid.getGridParam('selarrrow')
	            
	            if (!ids.length) {
	                Global.Dialog.infoDialog("请选择一条或多条记录进行打印！")
	                return
	            }
	            
	            var custCodes = []
	            for (var i = 0; i < ids.length; i ++)
	                custCodes.push(grid.getRowData(ids[i]).custcode)
	            
	            Global.Print.showPrint("custPay_preferentialTable.jasper", {
	                custCodes: "'" + custCodes.join("', '") + "'",
	                LogoFile: "<%=basePath%>jasperlogo/logo.jpg",
	                SUBREPORT_DIR: "<%=jasperPath%>" 
	            })
	        })
		        
		});

		function clearForm(){
		    $("#page_form input[type='text']").val('');
		    $("#page_form select").val('');
		    $("#custType").val('');
		    $.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
		    $("#status").val('');
		    $.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
		}
	
	    function selectCustTypes() {
		    var custTypeTree = $.fn.zTree.getZTreeObj("tree_custType")
		    var custTypeNodes = custTypeTree.getNodes()[0].children
		    var checkedIds = [], checkedNames = []
		    
		    for (var i = 0; i < custTypeNodes.length; i++) {
		    
		        // 选中非精装且非独立销售客户类型
		        if (custTypeNodes[i].isPartDecorate !== '2'
		            && custTypeNodes[i].isPartDecorate !== '3') {
		            
		            custTypeTree.checkNode(custTypeNodes[i], true)
		            checkedIds.push(custTypeNodes[i].id)
		            checkedNames.push(custTypeNodes[i].name)
		        }
		    }
		    
		    $("#custType").val(checkedIds.join(","))
		    $("#custType_NAME").val(checkedNames.join(","))
		}
		function doExcel(){
			var url = "${ctx}/admin/custPay/doQPrintExcel";
	        var rows=selectDataTableRows();
	        console.log(rows);
	 		if(rows.length!=0){
	 			doExcelNow("客户付款批量打印","dataTable","page_form",'',rows);
	 		}else{
	 			doExcelAll(url);
	 		}
		}
	
	</script>
</body>
</html>
