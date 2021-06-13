<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
String czybh=uc.getCzybh();
%>
<!DOCTYPE html>
<html>
<head>
<title>材料模板</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_itemBatchHeader.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function goto_query(table,form){
	$("#"+table).jqGrid("setGridParam",{postData:$("#"+form).jsonForm(),page:1}).trigger("reloadGrid");
}
function searchTemp() {
    var datas = $("#temp_page_form").jsonForm();
    $("#itemTempdataTable").jqGrid("setGridParam", {
       url:"${ctx}/admin/itemPlanTemp/goJqGrid",
       postData: datas,
       page: 1
    }).trigger("reloadGrid");
 }
//tab分页
$(document).ready(function() {  
	var id_detailW=window.parent.document.getElementById("id_detail").style.width.substring(0,4);
	 $("#temp").css('width',id_detailW-10); 
    //初始化表格
	Global.JqGrid.initJqGrid("itemTempdataTable",{
		url:"${ctx}/admin/itemPlanTemp/goJqGrid",
		height:200,
		colModel : [
				{name: "no", index: "no", width: 110, label: "模板编号", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 191, label: "材料模板名称", sortable: true, align: "left"},
				{name: "remark", index: "remark", width: 237, label: "备注", sortable: true, align: "left"}	
         ],
         ondblClickRow: function (rowid, status) {
         	var areaId= $(window.parent.document.getElementById("dataTable")).jqGrid('getGridParam', 'selrow');
           	var intProdId= $(window.parent.document.getElementById("intProdDataTable")).jqGrid('getGridParam', 'selrow');
           	if(!areaId){
	           	art.dialog({
					content: "请先选择区域!"
				});
	           	return;
           	}
           	if(!intProdId){
           	art.dialog({
				content: "请先选择集成成品!"
			});
           		return;
           	}
           	var itemTempData =$("#itemTempdataTable").jqGrid('getRowData',rowid);
           	$("#itemTempDetaildataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlanTempDetail/goJqGrid?no="+itemTempData.no+"&custCode=${itemPlanTemp.custCode}&custType=${itemPlanTemp.custType}",page:1}).trigger("reloadGrid");    
        }
	});
	Global.JqGrid.initJqGrid("itemTempDetaildataTable",{
		height:300,
		rowNum:10000,
		colModel : [
			{name: "fixareapk", index: "fixareapk", width: 80, label: "区域编号", sortable: true, align: "left", hidden: true},
			{name: "intprodpk", index: "intprodpk", width: 90, label: "集成成品编号", sortable: true, align: "left", hidden: true},
			{name: "fixareadescr", index: "fixareadescr", width: 100, label: "区域名称", sortable: true, align: "left"},
			{name: "intproddescr", index: "intproddescr", width: 90, label: "集成成品", sortable: true, align: "left", hidden: true},
			{name: "itemcode", index: "itemcode", width: 272, label: "材料编号", sortable: true, align: "left", hidden: true},
			{name: "itemdescr", index: "itemdescr", width: 160, label: "材料名称", sortable: true, align: "left"},
			{name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
			{name: "projectqty", index: "projectqty", width: 50, label: "预估数量", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "qty", index: "qty", width: 50, label: "数量", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "uomdescr", index: "uomdescr", width: 40, label: "单位", sortable: true, align: "left"},
			{name: "unitprice", index: "unitprice", width: 50, label: "单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "beflineamount", index: "beflineamount", width: 80, label: "折扣前金额", sortable: true, align: "right", sum: true},
			{name: "markup", index: "markup", width: 40, label: "折扣", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true,integer:false,minValue:1}},
			{name: "tmplineamount", index: "tmplineamount", width: 77, label: "小计", sortable: true, align: "right", sum: true},
			{name: "processcost", index: "processcost", width: 60, label: "其他费用", sortable: true, align: "right", sum: true,editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "lineamount", index: "lineamount", width: 80, label: "总价", sortable: true, align: "right", sum: true},
			{name: "remarks", index: "remarks", width: 200, label: "材料描述", sortable: true, align: "left",editable:true,edittype:'textarea'},
			{name: "isfixprice", index: "isfixprice", width: 85, label: "是否固定价", sortable: true, align: "left", hidden: true},
			{name: "cost", index: "cost", width: 20, label: "成本", sortable: true, align: "left", hidden: true},
			{name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
			{name: "commitype", index: "commitype", width: 84, label: "commitype", sortable: true, align: "left", hidden: true},
			{name: "projectcost", index: "projectcost", width: 90, label: "项目经理结算价", sortable:false, align: "right", hidden: true},
           	
        ],
        gridComplete:function(){
         	 var areaId= $(window.parent.document.getElementById("dataTable")).jqGrid('getGridParam', 'selrow');
           	 var intProdId= $(window.parent.document.getElementById("intProdDataTable")).jqGrid('getGridParam', 'selrow');
             var areaData =$(window.parent.document.getElementById("dataTable")).jqGrid('getRowData',areaId);
             var intProdData =$(window.parent.document.getElementById("intProdDataTable")).jqGrid('getRowData',intProdId);
             // var rowNo=$(window.parent.document.getElementById("itemChgDetailDataTable")).jqGrid('getGridParam','records')+1; 
             var rowNo=window.parent.document.getElementById("rowNo").value
           	 var fixAreaPk=areaData.PK;
           	 var fixAreaDescr=areaData.Descr;
           	 var intProdPk=intProdData.PK;
           	 var intProdDescr=intProdData.Descr;
           	 var isCupboard=intProdData.IsCupboard;
      		 var rowData = $("#itemTempDetaildataTable").jqGrid("getRowData");
           	 $.each(rowData,function(i,v){
           	 	v.fixareapk=fixAreaPk;
           	    v.fixareadescr=fixAreaDescr;
         	    	v.intprodpk=intProdPk;
           	    v.intproddescr=intProdDescr;
           	    v.iscupboard=isCupboard;
           	    //默认套餐外材料
             	v.isoutsetdescr="是";
	    		v.isoutset="1";  
	    		v.oldprojectcost=v.projectcost
           	  	 //$(window.parent.document.getElementById("itemChgDetailDataTable")).addRowData(rowNo+i, v, "last"); 
           	  	 $(window.parent.document.getElementById("itemChgDetailDataTable")).addRowData(rowNo+i, v, "last");
           	  	 window.parent.document.getElementById("rowNo").value=rowNo+i+1;
           	 })	
         }
	});
});
	
	</script>
</head>
<body>
	<div>
		<div id="temp" style="float: right;">
	
				<div id="tab1" class="tab_content" style="display: block; ">
					<div class="edit-form">
						<form role="form" class="form-search" action="" method="post" id="temp_page_form">
							<house:token></house:token>
							<ul class="ul-form">
								<li><label>模板名称</label> <input type="text" id="descr" name="descr"></input>
								<li id="loadMore">
									<button type="button" class="btn btn-sm btn-system " onclick="searchTemp()">查询</button>
								</li>

							</ul>
						</form>
					</div>
					<div class="clear_float"></div>
					<!--query-form-->
					<div class="pageContent">
						<div id="content-list">
							<table id="itemTempdataTable"></table>
							<div id="itemTempdataTablePager"></div>
						</div>
						<div style="display:none">
							<table id="itemTempDetaildataTable"></table>
						</div>
					</div>
				</div
			</div>
	
	</div>
</body>
</html>
