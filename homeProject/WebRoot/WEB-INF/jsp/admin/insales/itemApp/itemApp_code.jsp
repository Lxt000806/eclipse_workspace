<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemApp列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
/**初始化表格*/
$(function(){
	    $("#supplCode").openComponent_supplier();
	    $("#supplCode").setComponent_supplier({showValue:"${itemApp.supplCode}",
	    	showLabel:"${itemApp.supplCodeDescr}",readonly:true});
	    $("#itemCode").openComponent_item({condition: {supplCode:"${itemApp.supplCode}"}});
	    //$("#itemCode").setComponent_item({showValue:'${itemApp.itemCode}'});
	    $("#whcode").openComponent_wareHouse({condition: {czybh:"${itemApp.czybh}"}});
		
		var postData = $("#page_form").jsonForm();
		
		var colModel;
		if("${itemApp.openFrom}" == "itemApp_llth"){
			colModel = [
			  {name : "no",index : "no",width : 100,label:"领料单号",sortable : true,align : "left"},
		      {name : "type",index : "type",width : 100,label:"领料类型",sortable : true,align : "left",hidden:true},
		      {name : "typedescr",index : "typedescr",width : 100,label:"领料类型",sortable : true,align : "left",count:true},
		      {name : "itemtype1",index : "itemtype1",width : 100,label:"材料类型1",sortable : true,align : "left",hidden:true},
		      {name : "itemtype1descr",index : "itemtype1descr",width : 100,label:"材料类型1",sortable : true,align : "left"},
		      {name : "itemtype2",index : "itemtype2",width : 100,label:"材料类型2",sortable : true,align : "left",hidden:true},
		      {name : "itemtype2descr",index : "itemtype2descr",width : 100,label:"材料类型2",sortable : true,align : "left"},
		      {name : "address",index : "address",width : 200,label:"楼盘",sortable : true,align : "left"},
		      {name : "custcode",index : "custcode",width : 100,label:"客户编号",sortable : true,align : "left",hidden:true},
		      {name : "custdescr",index : "custdescr",width : 100,label:"客户名称",sortable : true,align : "left"},
		      {name : "status",index : "status",width : 100,label:"领料单状态",sortable : true,align : "left",hidden:true},
		      {name : "statusdescr",index : "statusdescr",width : 100,label:"领料单状态",sortable : true,align : "left"},
		      {name : "remarks",index : "remarks",width : 200,label:"备注",sortable : true,align : "left"},
		      {name : "sendtype",index : "sendtype",width : 100,label:"发货类型",sortable : true,align : "left",hidden:true},
		      {name : "sendtypedescr",index : "sendtypedescr",width : 100,label:"发货类型",sortable : true,align : "left"},
		      {name : "delivtype",index : "delivtype",width : 100,label:"配送方式",sortable : true,align : "left",hidden:true},
		      {name : "delivtypedescr",index : "delivtypedescr",width : 100,label:"配送方式",sortable : true,align : "left"},
		      {name : "appczy",index : "appczy",width : 100,label:"申请人员",sortable : true,align : "left",hidden:true},
		      {name : "appczydescr",index : "appczydescr",width : 100,label:"申请人员",sortable : true,align : "left"},
		      {name : "date",index : "date",width : 100,label:"申请日期",sortable : true,align : "left",formatter:formatDate},
		      {name : "confirmczy",index : "confirmczy",width : 100,label:"审批人员",sortable : true,align : "left",hidden:true},
		      {name : "confirmczydescr",index : "confirmczydescr",width : 100,label:"审批人员",sortable : true,align : "left"},
		      {name : "confirmdate",index : "confirmdate",width : 100,label:"审批日期",sortable : true,align : "left",formatter:formatDate},
		      {name : "sendczy",index : "sendczy",width : 100,label:"发货人员",sortable : true,align : "left",hidden:true},
		      {name : "sendczydescr",index : "sendczydescr",width : 100,label:"发货人员",sortable : true,align : "left"},
			  {name : "senddate",index : "senddate",width : 100,label:"发货日期",sortable : true,align : "left",formatter:formatDate},
		      {name : "supplcode",index : "supplcode",width : 100,label:"供应商",sortable : true,align : "left",hidden:true},
		      {name : "supplcodedescr",index : "supplcodedescr",width : 200,label:"供应商",sortable : true,align : "left"},
		      {name : "puno",index : "puno",width : 100,label:"采购单号",sortable : true,align : "left"},
		      {name : "whcode",index : "whcode",width : 100,label:"仓库编号",sortable : true,align : "left",hidden:true},
		      {name : "whcodedescr",index : "whcodedescr",width : 100,label:"仓库名称",sortable : true,align : "left"},
		      {name : "projectman",index : "projectman",width : 100,label:"项目经理",sortable : true,align : "left"},
		      {name : "phone",index : "phone",width : 100,label:"电话",sortable : true,align : "left"},
		      {name : "lastupdate",index : "lastupdate",width : 100,label:"最后更新日期",sortable : true,align : "left",formatter:formatDate},
		      {name : "lastupdatedby",index : "lastupdatedby",width : 100,label:"最后更新人员",sortable : true,align : "left"},
		      {name : "expired",index : "expired",width : 100,label:"是否过期",sortable : true,align : "left"},
		      {name : "actionlog",index : "actionlog",width : 100,label:"操作",sortable : true,align : "left"},
		      {name : "issetitem",index : "issetitem",width : 100,label:"是否套餐材料",sortable : true,align : "left",hidden:true},
		      {name : "isservice",index : "isservice",width : 100,label:"是否服务性产品",sortable : true,align : "left",hidden:true},
		      {name : "supplcode",index : "supplcode",width : 100,label:"供应商",sortable : true,align : "left",hidden:true},
		      {name : "supplcodedescr",index : "supplcodedescr",width : 100,label:"供应商",sortable : true,align : "left",hidden:true},
		      {name : "checkstatus",index : "checkstatus",width : 100,label:"结算状态",sortable : true,align : "left",hidden:true},
		      {name : "documentno",index : "documentno",width : 100,label:"档案号",sortable : true,align : "left",hidden:true},
		      {name : "iscupboard",index : "iscupboard",width : 100,label:"是否橱柜",sortable : true,align : "left",hidden:true},
		     
            ];
            $("#whcode").parent().attr("hidden",true);
            $("#itemType1").parent().removeAttr("hidden");
		}else{
			colModel = [
			  {name : "no",index : "no",width : 75,label:"领料单号",sortable : true,align : "left"},
			  {name : "senddate",index : "senddate",width : 75,label:"发货日期",sortable : true,align : "left",formatter:formatDate},
		      {name : "type",index : "type",width : 75,label:"领料类型",sortable : true,align : "left",hidden:true},
		      {name : "typedescr",index : "typedescr",width : 75,label:"领料类型",sortable : true,align : "left",count:true},
		      {name : "itemtype1",index : "itemtype1",width : 75,label:"材料类型1",sortable : true,align : "left",hidden:true},
		      {name : "itemtype1descr",index : "itemtype1descr",width : 75,label:"材料类型1",sortable : true,align : "left"},
		      {name : "itemtype2",index : "itemtype2",width : 100,label:"材料类型2",sortable : true,align : "left",hidden:true},
		      {name : "itemtype2descr",index : "itemtype2descr",width : 75,label:"材料类型2",sortable : true,align : "left"},
		      {name : "address",index : "address",width : 120,label:"楼盘",sortable : true,align : "left"},
		      {name : "custcode",index : "custcode",width : 100,label:"客户编号",sortable : true,align : "left",hidden:true},
		      {name : "custdescr",index : "custdescr",width : 75,label:"客户名称",sortable : true,align : "left"},
		      {name : "status",index : "status",width : 100,label:"领料单状态",sortable : true,align : "left",hidden:true},
		      {name : "statusdescr",index : "statusdescr",width : 90,label:"领料单状态",sortable : true,align : "left"},
		      {name : "remarks",index : "remarks",width : 170,label:"备注",sortable : true,align : "left"},
		      {name : "sendtype",index : "sendtype",width : 100,label:"发货类型",sortable : true,align : "left",hidden:true},
		      {name : "sendtypedescr",index : "sendtypedescr",width : 75,label:"发货类型",sortable : true,align : "left"},
		      {name : "delivtype",index : "delivtype",width : 75,label:"配送方式",sortable : true,align : "left",hidden:true},
		      {name : "delivtypedescr",index : "delivtypedescr",width : 75,label:"配送方式",sortable : true,align : "left"},
		      {name : "appczy",index : "appczy",width : 75,label:"申请人员",sortable : true,align : "left",hidden:true},
		      {name : "appczydescr",index : "appczydescr",width : 75,label:"申请人员",sortable : true,align : "left"},
		      {name : "date",index : "date",width : 75,label:"申请日期",sortable : true,align : "left",formatter:formatDate},
		      {name : "confirmczy",index : "confirmczy",width : 75,label:"审批人员",sortable : true,align : "left",hidden:true},
		      {name : "confirmczydescr",index : "confirmczydescr",width : 75,label:"审批人员",sortable : true,align : "left"},
		      {name : "confirmdate",index : "confirmdate",width : 75,label:"审批日期",sortable : true,align : "left",formatter:formatDate},
		      {name : "sendczy",index : "sendczy",width : 75,label:"发货人员",sortable : true,align : "left",hidden:true},
		      {name : "sendczydescr",index : "sendczydescr",width : 75,label:"发货人员",sortable : true,align : "left"},
		      {name : "supplcode",index : "supplcode",width : 75,label:"供应商",sortable : true,align : "left",hidden:true},
		      {name : "supplcodedescr",index : "supplcodedescr",width : 75,label:"供应商",sortable : true,align : "left"},
		      {name : "puno",index : "puno",width : 75,label:"采购单号",sortable : true,align : "left"},
		      {name : "whcode",index : "whcode",width : 75,label:"仓库编号",sortable : true,align : "left",hidden:true},
		      {name : "whcodedescr",index : "whcodedescr",width : 75,label:"仓库名称",sortable : true,align : "left"},
		      {name : "projectman",index : "projectman",width : 75,label:"项目经理",sortable : true,align : "left"},
		      {name : "phone",index : "phone",width : 90,label:"电话",sortable : true,align : "left"},
		      {name : "lastupdate",index : "lastupdate",width : 120,label:"最后更新日期",sortable : true,align : "left",formatter:formatDate},
		      {name : "lastupdatedby",index : "lastupdatedby",width : 90,label:"最后更新人员",sortable : true,align : "left"},
		      {name : "expired",index : "expired",width : 70,label:"是否过期",sortable : true,align : "left"},
		      {name : "actionlog",index : "actionlog",width : 60,label:"操作",sortable : true,align : "left"},
		      {name : "issetitem",index : "issetitem",width : 100,label:"是否套餐材料",sortable : true,align : "left",hidden:true},
		      {name : "isservice",index : "isservice",width : 100,label:"是否服务性产品",sortable : true,align : "left",hidden:true},
		      {name : "supplcode",index : "supplcode",width : 100,label:"供应商",sortable : true,align : "left",hidden:true},
		      {name : "supplcodedescr",index : "supplcodedescr",width : 100,label:"供应商",sortable : true,align : "left",hidden:true},
		      {name : "iscupboard",index : "iscupboard",width : 100,label:"是否橱柜",sortable : true,align : "left",hidden:true},
			  {name : "documentno",index : "documentno",width : 100,label:"档案号",sortable : true,align : "left",hidden:true}, //获取档案号 add by zb on 20190409
            ];
		}
		
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			height:$(document).height()-$("#content-list").offset().top-105,
			styleUI: "Bootstrap",   
			colModel : colModel,
            ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid("getRowData", rowid);
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            }
		});
		// 当有材料权限控制时，对材料类型1控制显示 modify by zb on 20190429
		if ("${itemApp.itemRight}") {
			var itemRights = "${itemApp.itemRight}".split(",");
			$.each($("#itemType1").children(), function (i, v) {
				var have = false, itemType1 = $(v).val();
				if (itemType1 == "") return;
				$.each(itemRights, function (j, val) {
					if (val == itemType1) have = true;
				});
				if (!have) $(v).remove();
			});
		}
});
function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{
		url:"${ctx}/admin/itemApp/goJqGridCode",
		postData:$("#page_form").jsonForm(),
		page:1,
		sortname:""
	}).trigger("reloadGrid");
}
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form"  class="form-search">
				<input type="hidden" id="expired" name="expired" value="${itemApp.expired}" />
				<input type="hidden" id="sendType" name="sendType" value="${itemApp.sendType}" />
				<input type="hidden" id="czybh" name="czybh" value="${itemApp.czybh}" />
				<input type="hidden" id="itemRight" name="itemRight" value="${itemApp.itemRight}" />
				<input type="hidden" id="supplCodeDescr" name="supplCodeDescr" value="${itemApp.supplCodeDescr}" />
				<input type="hidden" id="isAllApp" name="isAllApp" value="${itemApp.isAllApp}" />
						<ul class="ul-form">
							<li> 
							<label>楼盘地址</label>
							
							<input type="text" id="custAddress" name="custAddress"  value="${itemApp.custAddress}" />
							</li>
							<li> 
							<label>材料名称</label>
							
							<input type="text" id="itemCodeDescr" name="itemCodeDescr"   value="${itemApp.itemCodeDescr}" />
							</li>
							<li> 
							<label>领料单号</label>
							
							<input type="text" id="no" name="no"   value="${itemApp.no}" />
							</li>
					
							<li> 
							<label>材料编号</label>
							
							<input type="text" id="itemCode" name="itemCode"   value="${itemApp.itemCode}" />
							</li>
							<li> 
							<label>仓库编号</label>
							
							<input type="text" id="whcode" name="whcode"   value="${itemApp.whcode}" />
							</li>
							<li>
								<label>材料类型1</label>
								<house:dict id="itemType1" dictCode="" 
									sql="select Code,(Code+' '+Descr) fd from tItemType1 where expired='F' order by DispSeq asc " 
									sqlValueKey="Code" sqlLableKey="fd"  value="${itemApp.itemType1}"></house:dict>
							</li>
						
					
							<li style="display: none"> 
							<label>供应商编号</label>
							
							<input type="text" id="supplCode" name="supplCode"  value="${itemApp.supplCode}" />
							</li>
							<li style="display: none"> 
							<label>领料类型</label>
							
							<house:xtdm id="type" dictCode="ITEMAPPTYPE" value="${itemApp.type}" disabled="true"></house:xtdm>
							</li>
							<li style="display: none"> 
							<label>领料单状态</label>
							<house:xtdm id="status" dictCode="ITEMAPPSTATUS"  value="${itemApp.status }" disabled="true"></house:xtdm>
							</li>									
						<li>
							<label></label>
						<input type="checkbox" id="expired_show" name="expired_show"
										value="${itemApp.expired}" onclick="hideExpired(this)"
										${itemApp.expired!='T' ?'checked':'' }/>隐藏过期记录 
						</li>
							<li id="loadMore">
						<button type="button" class="btn btn-system btn-sm"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-system btn-sm" style="display: none"
							onclick="clearForm();">清空</button>
					</li>
				</ul>	
			</form>
		</div>
		
		<!--query-form-->
		<div class="pageContent">
		
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</div>	
</body>
</html>


