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
<title>赠送项目</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_baseItem.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_custTypeItem.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
   function searchGift() {
	    var datas = $("#gift_page_form").jsonForm();
	    if("${itemPlan.itemType1}"=="JC"){
	    	var intProdId= $(window.parent.document.getElementById("intProdDataTable")).jqGrid('getGridParam', 'selrow');
	    	if(!intProdId){
	    		art.dialog({
					 content: "请先选择集成成品!"
				});
	           	return;		
	    	}
	    	var intProdData =$(window.parent.document.getElementById("intProdDataTable")).jqGrid('getRowData',intProdId);
    		datas.isCupboard=intProdData.IsCupboard;
	    }
	    $("#itemGiftDataTable").jqGrid("setGridParam", {
	       url: "${ctx}/admin/itemPlan/goGiftByDescrJqGrid",
	       postData: datas,
	       page: 1
	    }).trigger("reloadGrid");
    }
    //tab分页
    $(document).ready(function () {
        var itemType1 = '${itemPlan.itemType1}'.trim();
        var id_detailW = window.parent.document.getElementById("id_detail").style.width.substring(0, 4);
        $("#itemGift").css('width', id_detailW);
        //初始化表格
		Global.JqGrid.initJqGrid("itemGiftDataTable", {
	        height: 185,
	        styleUI: 'Bootstrap',
		    colModel : [
			    {name: "pk", index: "pk", width: 118, label: "pK ", sortable: true, align: "left", hidden: true},
			    {name: "descr", index: "descr", width: 150, label: "优惠项目标题 ", sortable: true, align: "left"},
			    {name: "type", index: "type", width: 80, label: "优惠类型 ", sortable: true, align: "left", hidden: true},
			    {name: "typedescr", index: "typedescr", width: 100, label: "优惠类型 ", sortable: true, align: "left"},
			    {name: "disctype", index: "disctype", width: 80, label: "优惠折扣类型 ", sortable: true, align: "left", hidden: true},
			    {name: "isservicedescr", index: "isservicedescr", width: 80, label: "服务性产品 ", sortable: true, align: "left"},
			    {name: "islimititem", index: "islimititem", width: 90, label: "限定报价项目 ", sortable: true, align: "left", hidden: true},
			    {name: "islimititemdescr", index: "islimititemdescr", width: 100, label: "限定报价项目 ", sortable: true, align: "left"},
			    {name: "discper", index: "discper", width: 90, label: "优惠比例 ", sortable: true, align: "left", hidden: true},
		    	{name: "isoutsetdescr", index: "isoutsetdescr", width: 84, label: "是否套餐外项目", sortable: true, align: "left", hidden: true},
	       		{name: "isoutset", index: "isoutset", width: 84, label: "是否套餐外项目", sortable: true, align: "left",hidden: true},	
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
		        if(!intProdId&&"${itemPlan.itemType1}"=="JC"){
		           	art.dialog({
						 content: "请先选择集成成品!"
					});
		           	return;
		        }
		        var itemGiftData =$("#itemGiftDataTable").jqGrid('getRowData',rowid);
		        if (itemGiftData.islimititem=="0"){
		        	if("${itemPlan.itemType1}"=="JZ"){
		        	 	 $("#openComponent_baseItem_baseitemcode").next().click();
		        	}else{
		        		 if(itemGiftData.isoutset=='0'){
		        			 $("#openComponent_custTypeItem_custtypeitempk").next().click(); 
		        		 }else{
		        			 $("#openComponent_item_itemCode").next().click();  
		        		 }
		        	} 	 
		        }else{
		        	var datas = $("#gift_page_form").jsonForm();
		        	datas.giftPK=itemGiftData.pk;
		        	datas.isOutSet=itemGiftData.isoutset;
		        	datas.custType='${itemPlan.custType}';
					$("#itemGiftDetailDataTable").jqGrid("setGridParam", {
					    url: "${ctx}/admin/itemPlan/goGiftItemJqGrid",
					    postData: datas,
					    page: 1,
					}).trigger("reloadGrid");	
		        } 	
	       }
	   });
		Global.JqGrid.initJqGrid("itemGiftDetailDataTable",{
			height: 185,
			colModel : [
				{name: "giftpk", index: "giftpk", width: 110, label: "赠送项目pk", sortable: true, align: "left"},
				{name: "giftdescr", index: "giftdescr", width: 110, label: "赠送项目", sortable: true, align: "left"},
				{name: "itemcode", index: "itemcode", width: 272, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 160, label: "材料名称", sortable: true, align: "left"},
				{name: "baseitemcode", index: "baseitemcode", width: 84, label: "基础项目编号", sortable: true, align: "left"},
				{name: "baseitemdescr", index: "baseitemdescr", width: 84, label: "基础项目名称", sortable: true, align: "left"},
	       		{name: "uomdescr", index: "uomdescr", width: 50, label: "单位", sortable: true, align: "left"},
	       		{name: "price", index: "price", width: 84, label: "单价", sortable: true, align: "left"},
	       		{name: "remark", index: "remark", width: 84, label: "材料说明", sortable: true, align: "left"},
	       		{name: "isoutsetdescr", index: "isoutsetdescr", width: 84, label: "是否套餐外项目", sortable: true, align: "left"},
	       		{name: "isoutset", index: "isoutset", width: 84, label: "是否套餐外项目", sortable: true, align: "left"},	
	       		{name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable: true, align: "left"},
				{name: "itemtype3descr", index: "itemtype3descr", width: 85, label: "材料类型3", sortable: true, align: "left"},
				{name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable: true, align: "left"},
				{name: "itemtype3", index: "itemtype3", width: 85, label: "材料类型3", sortable: true, align: "left"},
	        	{name: "isfixprice", index: "isfixprice", width: 85, label: "是否固定价", sortable: true, align: "left"},
				{name: "cost", index: "cost", width: 20, label: "成本", sortable: true, align: "left"},	
				{name: "baseitemtype1", index: "baseitemtype1", width: 100, label: "基础材料类型1", sortable: true, align: "left"},
				{name: "baseitemtype2", index: "baseitemtype2", width: 84, label: "baseitemtype2", sortable: true, align: "left"},
				{name: "category", index: "category", width: 88, label: "项目类型", sortable: true, align: "left"},
				{name: "categorydescr", index: "categorydescr", width: 87, label: "项目分类", sortable: true, align: "left", hidden: true},
				{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left"},
				{name: "unitprice", index: "unitprice", width: 90, label: "人工单价", sortable: true, align: "left"},
				{name: "material", index: "material", width: 90, label: "材料单价", sortable: true, align: "left"},
				{name: "marketprice", index: "marketprice", width: 90, label: "市场价", sortable: true, align: "left"},
				{name: "remark", index: "remark", width: 304, label: "备注", sortable: true, align: "left"},
				{name: "iscalmangefee", index: "iscalmangefee", width: 80, label: "是否计算管理费", sortable: true, align: "left"},
				{name: "allowpricerise", index: "allowpricerise", width: 68, label: "价格上浮", sortable: true, align: "left"},
				{name: "oldprojectcost", index: "oldprojectcost", width: 84, label: "项目经理结算价", sortable: true, align: "left", hidden: true},
				{name: "projectcost", index: "projectcost", width: 84, label: "升级项目经理结算价", sortable: true, align: "left", hidden: true},
				{name: "custtypeitempk", index: "custtypeitempk", width: 0.5, label: "套餐材料信息编号", sortable:false, align: "left"},
			],
	        gridComplete:function(){
	        	 var rowid=$("#itemGiftDataTable").jqGrid("getGridParam","selrow");
	        	 var itemGiftData =$("#itemGiftDataTable").jqGrid('getRowData',rowid);
	        	 if(!itemGiftData){
	        		 return;
	        	 }
	         	 var areaId= $(window.parent.document.getElementById("dataTable")).jqGrid('getGridParam', 'selrow');
	           	 var intProdId= $(window.parent.document.getElementById("intProdDataTable")).jqGrid('getGridParam', 'selrow');
	             var areaData =$(window.parent.document.getElementById("dataTable")).jqGrid('getRowData',areaId);
	             var intProdData =$(window.parent.document.getElementById("intProdDataTable")).jqGrid('getRowData',intProdId);
	           	 var fixAreaPk=areaData.PK;
	           	 var fixAreaDescr=areaData.Descr;
	           	 var intProdPk=intProdData.PK;
	           	 var intProdDescr=intProdData.Descr;
	           	 var isCupboard=intProdData.IsCupboard;
	           	 var prePlanAreaPk=areaData.PrePlanAreaPK;
	          	 var prePlanAreaDescr=areaData.preplanareadescr;
	           	 var rowNo=window.parent.document.getElementById("rowNo").value;
	           	 var markup=100;
	           	 if (itemGiftData.disctype=="1"){
		        	markup=100-itemGiftData.discper*100;
		         }
	      		 var rowData = $("#itemGiftDetailDataTable").jqGrid("getRowData");
	      		 if("${itemPlan.itemType1}"=="JZ"){
		        	 $.each(rowData,function(i,v){  
		        	 	 v.fixareapk=fixAreaPk;
             			 v.fixareadescr=fixAreaDescr;
             			 v.preplanareapk=prePlanAreaPk;
             			 v.preplanareadescr=prePlanAreaDescr;
             			 v.isgift='1';
             			 v.qty=0;
		             	 v.lineamount=0;  
		             	 if("${baseItemPlan.isOutSet}"=="2"&&itemGiftData.isoutset=="0"){
		             	   v.unitprice=0;	
		             	   v.material=0;
		             	   v.Cost=0;	
		             	 } 	
		          	  	 $(window.parent.document.getElementById("baseItemPlanDetailDataTable")).addRowData(rowNo+i, v, "last");
		           	  	 window.parent.document.getElementById("rowNo").value=rowNo+i+1;
		          	 })	
	        	 }else{
	        		$.each(rowData,function(i,v){
		           	 	v.fixareapk=fixAreaPk;
		           	    v.fixareadescr=fixAreaDescr;
		           	 	v.preplanareapk=prePlanAreaPk;
         				v.preplanareadescr=prePlanAreaDescr;
		         	    v.intprodpk=intProdPk;
		           	    v.intproddescr=intProdDescr;
		           	    v.iscupboard=isCupboard;
		      			v.qty=0;
		      			v.unitprice=v.price;
		      			v.beflineamount=0;
		             	v.markup=markup;
		             	v.tmplineamount=0;
		             	v.processcost=0;
		             	v.lineamount=0;
		             	v.projectqty=0; 	
		             	v.isgift='1';
		             	v.algorithmdeduct=0;
		      	      	v.algorithmper=1.0;
		           	  	$(window.parent.document.getElementById("itemChgDetailDataTable")).addRowData(rowNo+i, v, "last");
		           	  	window.parent.document.getElementById("rowNo").value=rowNo+i+1;
	           		})	
	        	}   	  	
	       	}
		});
    });
    $(function(){
		$("#giftDescr").keydown(function(event){
			if(event.keyCode == 13){
				searchGift();
			}
		});
		$("#itemCode").openComponent_item({
			condition:{'itemType1':'${itemPlan.itemType1}','disabledEle':'itemType1',custCode:'${itemPlan.custCode}','custType':'${itemPlan.custType}',custCode:'${itemPlan.custCode}'},
			callBack:function(data){  
			 	selectItem(data);
	     	}
	    });
		$("#custtypeitempk").openComponent_custTypeItem({
			condition:{'itemType1':'${itemPlan.itemType1}','disabledEle':'itemType1',custCode:'${itemPlan.custCode}','custType':'${itemPlan.custType}',custCode:'${itemPlan.custCode}'},
			callBack:function(data){  
			 	selectItem(data);
	     	}
	    });
	    $("#baseitemcode").openComponent_baseItem({
			condition: {isOutSet: "${itemPlan.isOutSet}",custType:"${itemPlan.custType}",canUseComBaseItem:"${itemPlan.canUseComBaseItem}"},
			callBack:function(data){
				selectBaseItem(data);
			}   	
		});
	});
	function selectItem(data){
	    var areaId= $(window.parent.document.getElementById("dataTable")).jqGrid('getGridParam', 'selrow');
	    var intProdId= $(window.parent.document.getElementById("intProdDataTable")).jqGrid('getGridParam', 'selrow');
	    var areaData =$(window.parent.document.getElementById("dataTable")).jqGrid('getRowData',areaId);
	    var intProdData =$(window.parent.document.getElementById("intProdDataTable")).jqGrid('getRowData',intProdId);
	    var fixAreaPk=areaData.PK;
	 	var fixAreaDescr=areaData.Descr;
	    var intProdPk=intProdData.PK;
	    var intProdDescr=intProdData.Descr;
	    var isCupboard=intProdData.IsCupboard;
	 	var prePlanAreaPk=areaData.PrePlanAreaPK;
        var prePlanAreaDescr=areaData.preplanareadescr;
	    var rowNo=window.parent.document.getElementById("rowNo").value;
		var rowid=$("#itemGiftDataTable").jqGrid("getGridParam","selrow");
		var rowData =$("#itemGiftDataTable").jqGrid('getRowData',rowid);
		var markup=100;
		if (rowData.disctype=="1"){
	    	markup=100-rowData.discper*100;
	    }
	    rowData.markup=markup;
		rowData.fixareapk=fixAreaPk;
		rowData.fixareadescr=fixAreaDescr;
		rowData.intprodpk=intProdPk;
		rowData.intproddescr=intProdDescr;
		rowData.iscupboard=isCupboard;
		rowData.preplanareapk=prePlanAreaPk;
		rowData.preplanareadescr=prePlanAreaDescr;
		rowData.itemcode=data.code;
	    rowData.itemdescr=data.descr;
	    rowData.itemtype2descr=data.itemtype2descr;
	    rowData.itemtype2=data.itemtype2;
	    rowData.itemtype3descr=data.itemtype3descr;
	    rowData.unitprice=data.price;
	    rowData.cost=data.cost;
	    rowData.uomdescr=data.uomdescr;
	    rowData.isfixprice=data.isfixprice;
	    rowData.remarks=data.remark;
	    rowData.qty=0;
	 	rowData.beflineamount=0;
	 	rowData.tmplineamount=0;
	 	rowData.processcost=0;
	 	rowData.lineamount=0;
	 	rowData.projectqty=0;
	 	rowData.isgift='1';
	 	rowData.giftpk=rowData.pk;
	 	rowData.giftdescr=rowData.descr;
		rowData.projectcost=data.projectcost;
		rowData.oldprojectcost=data.oldprojectcost;
		rowData.custtypeitempk=data.pk;
		rowData.algorithmdeduct=0;
	    rowData.algorithmper=1.0;
	    $(window.parent.document.getElementById("itemChgDetailDataTable")).addRowData(rowNo+1, rowData, "last");
		window.parent.document.getElementById("rowNo").value=rowNo+1;         	
	}
	function selectBaseItem(data){
	    var areaId= $(window.parent.document.getElementById("dataTable")).jqGrid('getGridParam', 'selrow');
	    var areaData =$(window.parent.document.getElementById("dataTable")).jqGrid('getRowData',areaId);
	    var fixAreaPk=areaData.PK;
	 	var fixAreaDescr=areaData.Descr;
		var rowid=$("#itemGiftDataTable").jqGrid("getGridParam","selrow");
		var rowData =$("#itemGiftDataTable").jqGrid('getRowData',rowid);
		var rowNo=window.parent.document.getElementById("rowNo").value;
		var prePlanAreaPk=areaData.PrePlanAreaPK;
        var prePlanAreaDescr=areaData.preplanareadescr;
		rowData.fixareapk=fixAreaPk;
    	rowData.fixareadescr=fixAreaDescr;
    	rowData.baseitemcode=data.code;
	    rowData.baseitemdescr=data.descr;
	    rowData.preplanareapk=prePlanAreaPk;
		rowData.preplanareadescr=prePlanAreaDescr;
    	rowData.qty=0;
      	rowData.lineamount=0;   	
	   	rowData.uom=data.uom;
	   	rowData.category=data.category;
	   	rowData.categorydescr=data.categorydescr;
	   	rowData.baseitemtype1=data.baseitemtype1;
	   	rowData.iscalmangefee=data.iscalmangefee;
	   	rowData.allowpricerise=data.allowpricerise;
		rowData.remark=data.remark;
		rowData.isgift='1';
	 	rowData.giftpk=rowData.pk;
	 	rowData.giftdescr=rowData.descr;
	 	if("${baseItemPlan.isOutSet}"=="2"&&itemGiftData.isoutset=="0"){
	        rowData.unitprice=0;	
	        rowData.material=0;	
	        rowData.Cost=0;
        }else{
        	rowData.material=data.material;
	   		rowData.cost=data.cost;
	        rowData.unitprice=data.offerpri;       
        } 	
		rowData.isfixprice=data.isfixprice;
		$(window.parent.document.getElementById("baseItemPlanDetailDataTable")).addRowData(rowNo+1, rowData, "last");
		window.parent.document.getElementById("rowNo").value=rowNo+1;        
	}
  </script>
</head>
<body>
	<div>
		<div id="itemGift" style="float: right;">
			<div>
				<div id="tab1" class="tab_content" style="display: block; ">
					<div class="edit-form">
						<form role="form" class="form-search" action="" method="post" id="gift_page_form">
							<house:token></house:token>
							<input type="hidden" id="custType" name="custType" value="${itemPlan.custType}"></input>
							<input type="hidden" id="itemType1" name="itemType1" value="${itemPlan.itemType1}"></input>
							<input type="hidden"  id="isService" name="isService" value="${itemPlan.isService}"></input>
							<input type="hidden"  id="custCode" name="custCode" value="${itemPlan.custCode}"></input>
							<ul class="ul-form">
								<li><label>优惠项目标题</label> <input type="text" id="giftDescr" name="giftDescr"></input>
								<li id="loadMore">
									<button type="button" class="btn btn-sm btn-system " onclick="searchGift()">查询</button>
								</li>
								<li hidden="true" >
									<input id="itemCode" name="itemCode" />
									<input id="baseitemcode" name="baseitemcode"/>
									<input id="custtypeitempk" name="custtypeitempk" />
							</ul>
						</form>
					</div>
					<div class="clear_float"></div>
					<!--query-form-->
					<div class="pageContent">
						<div id="content-list1">
							<table id="itemGiftDataTable"></table>
							<div id="itemGiftDataTablePager"></div>
						</div>
						<div style="display: none" >
							<table id="itemGiftDetailDataTable"></table>
						</div>
					</div>
				</div
			</div>
		</div>
	</div>
</body>
</html>

		 
		
