<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>批量打印prjManCheck</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){
	   /*  if("${customer.isOutSet}"=="1"){
	    	$("#isOutSet option[value='1']").attr("selected", "selected"); 
	    }else{
	    	$("#isOutSet option[value='2']").attr("selected", "selected");
	    } */
        //初始化表格
        var postData=$("#page_form").jsonForm();
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/prjManCheck/goJqGrid",
			postData:{isOutSet:"${customer.isOutSet}"},
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-110,
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "code", index: "code", width: 70, label: "客户编号", sortable: true, align: "left", count: true},
				{name: "documentno", index: "documentno", width: 70, label: "档案编号", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 70, label: "客户名称", sortable: true, align: "left"},
				{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 70, label: "客户状态", sortable: true, align: "left"},
				{name: "checkstatusdescr", index: "checkstatusdescr", width: 80, label: "结算状态", sortable: true, align: "left"},
				{name: "projectmandescr", index: "projectmandescr", width: 70, label: "项目经理", sortable: true, align: "left"},
				{name: "designmandescr", index: "designmandescr", width: 70, label: "设计师", sortable: true, align: "left"},
				{name: "businessmandescr", index: "businessmandescr", width: 70, label: "业务员", sortable: true, align: "left"},
				/*  项目经理结算数据 */
				{name: "basefee_dirct", index: "basefee_dirct", width: 80, label: "基础直接费", sortable: true, align: "right", sum: true},
				{name: "basechg", index: "basechg", width: 120, label: "基础增减（优惠前）", sortable: true, align: "right", sum: true},
				{name: "mainamount", index: "mainamount", width: 65, label: "主材预算", sortable: true, align: "right", sum: true},
				{name: "chgmainamount", index: "chgmainamount", width: 65, label: "主材增减", sortable: true, align: "right", sum: true},
				{name: "maincoopfee", index: "maincoopfee", width: 75, label: "主材配合费", sortable: true, align: "right", sum: true},
				/*  项目经理结算数据end  */
				/* 省到家项目经理结算数据begin  */
				{name: "innerarea", index: "innerarea", width: 53, label: "套内面积", sortable: true, align: "right"},
				{name: "mainsetfee", index: "mainsetfee", width: 75, label: "套餐费", sortable: true, align: "right", sum: true},
				{name: "longfee", index: "longfee", width: 75, label: "远程费", sortable: true, align: "right", sum: true},
				{name: "allsetadd", index: "allsetadd", width: 97, label: "套餐外基础增项", sortable: true, align: "right", sum: true},
				{name: "allsetminus", index: "allsetminus", width: 76, label: "套餐内减项", sortable: true, align: "right", sum: true},
				{name: "allitemamount", index: "allitemamount", width: 65, label: "材料预算", sortable: true, align: "right", sum: true},
				{name: "allmanagefee_base", index: "allmanagefee_base", width: 72, label: "基础管理费", sortable: true, align: "right", sum: true},
				{name: "upgwithhold", index: "upgwithhold", width: 60, label: "升级扣项", sortable: true, align: "right", sum: true},
				{name: "basecost", index: "basecost", width: 60, label: "基础支出", sortable: true, align: "right", sum: true},
				{name: "maincost_js", index: "maincost_js", width: 93, label: "主材支出(结算)", sortable: true, align: "right", sum: true},
				{name: "isitemupdescr", index: "isitemupdescr", width: 90, label: "材料免费升级", sortable: true, align: "right"},
				/*  省到家项目经理结算数据end */ 
				{name: "basectrlamt", index: "basectrlamt", width: 65, label: "发包总价", sortable: true, align: "right", sum: true},
				{name: "projectctrladj", index: "projectctrladj", width: 65, label: "发包补贴", sortable: true, align: "right"},
				{name: "cost", index: "cost", width: 60, label: "支出", sortable: true, align: "right", sum: true},
				{name: "withhold", index: "withhold", width: 60, label: "预扣", sortable: true, align: "right", sum: true},
				{name: "recvfee", index: "recvfee", width: 60, label: "已领", sortable: true, align: "right", sum: true},
				{name: "qualityfee", index: "qualityfee", width: 60, label: "质保金", sortable: true, align: "right", sum: true},
				{name: "accidentfee", index: "accidentfee", width: 65, label: "意外险", sortable: true, align: "right", sum: true},
				{name: "mustamount", index: "mustamount", width: 65, label: "应发金额", sortable: true, align: "right", sum: true},
				{name: "realamount", index: "realamount", width: 65, label: "实发金额", sortable: true, align: "right", sum: true},
				{name: "isprovide", index: "isprovide", width: 65, label: "是否发放", sortable: true, align: "left"},
				{name: "provideno", index: "provideno", width: 80, label: "发放编号", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 140, label: "备注", sortable: true, align: "left"},
				{name: "appczydescr", index: "appczydescr", width: 60, label: "申请人", sortable: true, align: "left"},
				{name: "date", index: "date", width: 120, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
				{name: "confirmczydescr", index: "confirmczydescr", width: 70, label: "确认人", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 120, label: "确认日期", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 80, label: "操作", sortable: true, align: "left"},
				{name: "basefee", index: "basefee", width: 80, label: "基础预算", sortable: true, align: "left",hidden: true},
				{name: "isitemup", index: "isitemup", width: 80, label: "免费升级", sortable: true, align: "left",hidden: true},
				{name: "custtypetype", index: "custtypetype", width: 80, label: "客户类型", sortable: true, align:"left",hidden:true}
            ],
            sortname: 'projectmandescr', 
            sortorder: 'asc', 
            gridComplete:function(){
	  		 	if ($("#isOutSet").val() == '1') {
		        	$("#btnWithhold").show(); 
		        	$("#btnItemUp").hide();	
		        	$("#dataTable").jqGrid('showCol', "basefee_dirct");
		        	$("#dataTable").jqGrid('showCol', "basechg");
		        	$("#dataTable").jqGrid('showCol', "mainamount");
		        	$("#dataTable").jqGrid('showCol', "chgmainamount");
		        	$("#dataTable").jqGrid('showCol', "maincoopfee");
		        	
		        	$("#dataTable").jqGrid('hideCol', "innerarea");
		        	$("#dataTable").jqGrid('hideCol', "mainsetfee");
		        	$("#dataTable").jqGrid('hideCol', "longfee");
		        	$("#dataTable").jqGrid('hideCol', "allsetadd");
		        	$("#dataTable").jqGrid('hideCol', "allsetminus");
		        	$("#dataTable").jqGrid('hideCol', "allitemamount");
		        	$("#dataTable").jqGrid('hideCol', "allmanagefee_base");
		        	$("#dataTable").jqGrid('hideCol', "upgwithhold");
		        	$("#dataTable").jqGrid('hideCol', "basecost");
		        	$("#dataTable").jqGrid('hideCol', "maincost_js");
		        	$("#dataTable").jqGrid('hideCol', "isitemupdescr");	
		         } else if ($("#isOutSet").val() == '2'){
		        	$("#dataTable").jqGrid('hideCol', "basefee_dirct");
		        	$("#dataTable").jqGrid('hideCol', "basechg");
		        	$("#dataTable").jqGrid('hideCol', "mainamount");
		        	$("#dataTable").jqGrid('hideCol', "chgmainamount");
		        	$("#dataTable").jqGrid('hideCol', "maincoopfee");
		        	
		        	$("#dataTable").jqGrid('showCol', "innerarea");
		        	$("#dataTable").jqGrid('showCol', "mainsetfee");
		        	$("#dataTable").jqGrid('showCol', "longfee");
		        	$("#dataTable").jqGrid('showCol', "allsetadd");
		        	$("#dataTable").jqGrid('showCol', "allsetminus");
		        	$("#dataTable").jqGrid('showCol', "allitemamount");
		        	$("#dataTable").jqGrid('showCol', "allmanagefee_base");
		        	$("#dataTable").jqGrid('showCol', "upgwithhold");
		        	$("#dataTable").jqGrid('showCol', "basecost");
		        	$("#dataTable").jqGrid('showCol', "maincost_js");
		        	$("#dataTable").jqGrid('showCol', "isitemupdescr");
		         }else{
		         	$("#dataTable").jqGrid('showCol', "basefee_dirct");
		        	$("#dataTable").jqGrid('showCol', "basechg");
		        	$("#dataTable").jqGrid('showCol', "mainamount");
		        	$("#dataTable").jqGrid('showCol', "chgmainamount");
		        	$("#dataTable").jqGrid('showCol', "maincoopfee");
		        	$("#dataTable").jqGrid('showCol', "innerarea");
		        	$("#dataTable").jqGrid('showCol', "mainsetfee");
		        	$("#dataTable").jqGrid('showCol', "longfee");
		        	$("#dataTable").jqGrid('showCol', "allsetadd");
		        	$("#dataTable").jqGrid('showCol', "allsetminus");
		        	$("#dataTable").jqGrid('showCol', "allitemamount");
		        	$("#dataTable").jqGrid('showCol', "allmanagefee_base");
		        	$("#dataTable").jqGrid('showCol', "upgwithhold");
		        	$("#dataTable").jqGrid('showCol', "basecost");
		        	$("#dataTable").jqGrid('showCol', "maincost_js");
		        	$("#dataTable").jqGrid('showCol', "isitemupdescr");
		       } 
			},
       
		});
		
       //打印
        $("#printBtn").on("click",function(){		
			var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
        	if(ids.length==0){
        		Global.Dialog.infoDialog("请选择需要打印结算单！");
        		return;
        	}
        	var custCodes = "";
        	$.each(ids,function(k,id){
    			var row = $("#dataTable").jqGrid('getRowData', id);
    			custCodes = custCodes + "'" + $.trim(row.code) + "'," ;
    				
    		});
    		if (custCodes != "") {
    			custCodes = custCodes.substring(0,custCodes.length-1);
    		}
        	var reportName = "prjManCheckBatch_main.jasper";
        	//var sortname = $("#dataTable").getGridParam("sortname"); // sidx
			//var sortorder = $("#dataTable").getGridParam("sortorder"); // sord
        	Global.Print.showPrint(reportName, {
				CustCode: custCodes,
				LogoPath : "<%=basePath%>jasperlogo/",
				SUBREPORT_DIR: "<%=jasperPath%>" 
			});
        }); 
         
});

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#checkStatus").val('');
	$.fn.zTree.getZTreeObj("tree_checkStatus").checkAllNodes(false);
}

function goto_query(){
	var postData=$("#page_form").jsonForm();
	if (!postData.sortname){
		postData.sortname='projectmandescr';
		postData.sortorder='asc';
	}
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/prjManCheck/goJqGrid",datatype:'json',postData:postData,page:1,fromServer: true}).trigger("reloadGrid");
}

</script>
</head>

<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">	
			<ul class="ul-form">
						<li>
							<label >申请日期从</label>
							<input type="text" style="width:160px;" id="dateFrom" name="dateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.dateFrom}' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label >到</label>
							<input type="text" style="width:160px;" id="dateTo" name="dateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.dateTo }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label >确认日期从</label>                     
							<input type="text" style="width:160px;" id="confirmDateFrom" name="confirmDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.dateFrom}' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label >到</label>
							<input type="text" style="width:160px;" id="confirmDateTo" name="confirmDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.dateTo }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>客户结算状态</label> <house:xtdmMulit id="checkStatus" dictCode="CheckStatus" unShowValue="1"></house:xtdmMulit>
						</li>
						<li>
							<label>客户类型</label> 
							<house:DictMulitSelect id="custType" dictCode="" 
								sql="select Code,Code +' '+Desc1 Descr from tCusttype where Expired ='F' order by dispSeq "  onCheck="loadWorkType12Dept()"
							sqlValueKey="Code" sqlLableKey="Descr"></house:DictMulitSelect>
						</li>
						<li> <label>套餐类型</label>
							<select id="isOutSet" name="isOutSet" >
								<option value=""></option>
								<option value="1">清单</option>
								<option value="2">套餐</option>
							</select>
						</li>
						<li><label></label> <input type="checkbox" id="expired_show" name="expired_show"
							value="${cutomer.expired}" onclick="hideExpired(this)" ${cutomer.expired!='T' ?'checked':'' }/>隐藏过期&nbsp;
							<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
						</li>	
					</ul>
			</form>
		</div>
	<div class="btn-panel" >

      <div class="btn-group-sm"  >
	      <button type="button" class="btn btn-system " id="printBtn" >打印</button>
	        <button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</bu
     </div>
	</div>
		<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
	</div>
</body>

</html>
