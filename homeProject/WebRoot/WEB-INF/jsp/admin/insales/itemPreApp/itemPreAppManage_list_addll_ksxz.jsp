<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
	UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
	String costRight = uc.getCostRight();
%>
<!DOCTYPE html>
<html>
<head>
	<title>新增领料 快速新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
		var selectRows = [];
		function goto_query(){
			$("#dataTable_ksxz").jqGrid("setGridParam", {
				url:"${ctx}/admin/itemPreApp/goKsxzJqGrid",
				postData:$("#page_form").jsonForm(),
				page:1
			}).trigger("reloadGrid");
		}
		$(function(){
			Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "itemType1", "itemType2", "itemType3");
			Global.LinkSelect.setSelect({
				firstSelect:"itemType1",
				firstValue:"${data.itemType1}",
				secondSelect:"itemType2",
				secondValue:"${data.itemType2}",
				thirdSelect:"itemType3",
				thirdValue:""
			});
			$("#itemType1").attr("disabled", true);		
			$("#itemType2").attr("disabled", true);							
			Global.JqGrid.initJqGrid("dataTable_ksxz", {
				height:400,
				styleUI:"Bootstrap",
				url:"${ctx}/admin/itemPreApp/goKsxzJqGrid",
				postData:{
					itemType1:$("#itemType1Def").val(),
					itemType2:$("#itemType2Def").val(),
					isSetItem:$("#isSetItem").val(),
					custCode:$("#custCode").val(),
					appNo:$("#appNo").val(),
					itemCodes:$("#itemCodes").val()
				},
				multiselect:true,
				colModel:[
					{name:"itemcode", index:"itemcode", width:70, label:"材料编号", sortable:true, align:"left"},
					{name:"itemdescr", index:"itemdescr", width:284, label:"名称", sortable:true, align:"left"},
					{name:"cost", index:"cost", width:50, label:"成本", sortable:true, align:"left", hidden:true},
					{name:"projectcost", index:"projectcost", width:100, label:"项目经理结算价", sortable:true, align:"left", hidden:true},
					{name:"uomdescr", index:"uomdescr", width:60, label:"单位", sortable:true, align:"left"},
					{name:"itemtype1descr", index:"itemtype1descr", width:74.5, label:"材料类型1", sortable:true, align:"left"},
					{name:"itemtype2descr", index:"itemtype2descr", width:74.5, label:"材料类型2", sortable:true, align:"left"},
					{name:"itemtype3descr", index:"itemtype3descr", width:74.5, label:"材料类型3", sortable:true, align:"left"},
					{name:"sqlcodedescr", index:"sqlcodedescr", width:70, label:"品牌", sortable:true, align:"left"},
					{name:"sendtypedescr", index:"sendtypedescr", width:70, label:"发货类型", sortable:true, align:"left"},
					{name:"color", index:"color", width:50, label:"颜色", sortable:true, align:"left"},
					{name:"model", index:"model", width:70, label:"型号", sortable:true, align:"left"},
					{name:"sizedesc", index:"sizedesc", width:70, label:"规格", sortable:true, align:"left"},
					{name:"barcode", index:"barcode", width:50, label:"条码", sortable:true, align:"left"},
					{name:"isfixpricedescr", index:"isfixpricedescr", width:80, label:"是否固定价", sortable:true, align:"left"},
					{name:"price", index:"price", width:50, label:"单价", sortable:true, align:"left"},
					{name:"pk", index:"pk", width:70, label:"编号", sortable:true, align:"left", hidden:true},
					{name:"custcode", index:"custcode", width:100, label:"客户编号", sortable:true, align:"left", hidden:true},
					{name:"fixareadescr", index:"fixareadescr", width:136, label:"区域", sortable:true, align:"left", hidden:true},
					{name:"intproddescr", index:"intproddescr", width:100, label:"集成成品", sortable:true, align:"left", hidden:true},
					{name:"suppldescr", index:"suppldescr", width:154, label:"供应商", sortable:true, align:"left", hidden:true},
					{name:"itemtype1", index:"itemtype1", width:100, label:"类型编号", sortable:true, align:"left", hidden:true},
					{name:"typedescr", index:"typedescr", width:100, label:"材料类型", sortable:true, align:"left", hidden:true},
					{name:"qty", index:"qty", width:100, label:"需求数量", sortable:true, align:"left", hidden:true},
					{name:"sendqty", index:"sendqty", width:110, label:"已发货数量", sortable:true, align:"left", hidden:true},
					{name:"itemchgno", index:"itemchgno", width:120, label:"增减单号", sortable:true, align:"left", hidden:true},
					{name:"remarks", index:"remarks", width:173, label:"备注", sortable:true, align:"left", hidden:true},
					{name:"reqpk", index:"reqpk", width:70, label:"领料标识", sortable:true, align:"left", hidden:true},
					{name:"reqremarks", index:"reqremarks", width:60, label:"预算备注", sortable:true, align:"left", hidden:true},
					{name:"no", index:"no", width:80, label:"批次号", sortable:true, align:"left", hidden:true},
					{name:"fixareapk", index:"fixareapk", width:84, label:"fixareapk", sortable:true, align:"left", hidden:true},
					{name:"intprodpk", index:"intprodpk", width:84, label:"intprodpk", sortable:true, align:"left", hidden:true},
					{name:"supplcodedescr", index:"supplcodedescr", width:160, label:"供应商", sortable:true, align:"left", hidden:true},
					{name:"declareqty", index:"declareqty", width:76, label:"申报数量", sortable:true, align:"left",hidden:true},
					{name:"shortqty", index:"shortqty", width:89, label:"缺货数量", sortable:true, align:"left", hidden:true},
					{name:"reqqty", index:"reqqty", width:79, label:"预算数量", sortable:true, align:"left", sum:true, hidden:true},
					{name:"sendedqty", index:"sendedqty", width:89, label:"总共已发货", sortable:true, align:"left", sum:true, hidden:true},
					{name:"applyqty", index:"applyqty", width:89, label:"已申请数量", sortable:true, align:"left", hidden:true},
					{name:"confirmedqty", index:"confirmedqty", width:89, label:"已审核数量", sortable:true, align:"left", sum:true, hidden:true},
				 	{name: "allqty", index: "allqty", width: 80, label: "库存数量", sortable: true, align: "right", sum: true,hidden: true},
					{name: "userqty", index: "userqty", width: 80, label: "可用数量", sortable: true, align: "right", sum: true,hidden: true},
					{name:"allcost", index:"allcost", width:90, label:"成本总价", sortable:true, align:"left", sum:true, hidden:true},
					{name:"allprojectcost", index:"allprojectcost", width:120, label:"项目经理结算总价", sortable:true, align:"left", sum:true, hidden:true},
					{name:"processcost", index:"processcost", width:80, label:"其它费用", sortable:true, align:"left", sum:true, hidden:true},
					{name:"differences", index:"differences", width:89, label:"成本差异额", sortable:true, align:"left", sum:true, hidden:true},
					{name:"pricedifferences", index:"pricedifferences", width:90, label:"销售差异额", sortable:true, align:"left", hidden:true},
					{name:"weight", index:"weight", width:85, label:"总重量", sortable:true, align:"left", sum:true, hidden:true},
					{name:"numdescr", index:"numdescr", width:85, label:"总片数", sortable:true, align:"left", hidden:true},
					{name:"reqprocesscost", index:"reqprocesscost", width:156, label:"reqprocesscost", sortable:true, align:"left", hidden:true},
					{name:"perweight", index:"perweight", width:156, label:"perweight", sortable:true, align:"left", hidden:true},
					{name:"aftqty", index:"aftqty", width:90, label:"aftqty", sortable:true, align:"left", hidden:true},
					{name:"aftcost", index:"aftcost", width:90, label:"aftcost", sortable:true, align:"left", hidden:true},
					{name:"whcode", index:"whcode", width:90, label:"whcode", sortable:true, align:"left", hidden:true},
					{name:"whdescr", index:"whdescr", width:90, label:"whdescr", sortable:true, align:"left", hidden:true},
					{name:"supplcode", index:"supplcode", width:90, label:"supplcode", sortable:true, align:"left", hidden:true},
					{name:"sendtype", index:"sendtype", width:90, label:"sendtype", sortable:true, align:"left", hidden:true},
					{name:"itemtype2", index:"itemtype2", width:90, label:"itemtype2", sortable:true, align:"left", hidden:true}
				]
			});
			if($("#costRight").val() == "1"){
				$("#dataTable_ksxz").jqGrid("showCol", "cost");
				$("#dataTable_ksxz").jqGrid("showCol", "projectcost");
			}else{
				$("#dataTable_ksxz").jqGrid("hideCol", "cost");
				$("#dataTable_ksxz").jqGrid("hideCol", "projectcost");
			}
   			$("#supplCode").openComponent_supplier({
   				condition:{
   					itemType1:$("#itemType1").val(),
   					itemRight:"${data.itemRight}"
   				}
   			});
   			
   			//改写窗口右上角关闭按钮事件
   			var titleCloseEle = parent.$("div[aria-describedby=dialog_ksxz]").children(".ui-dialog-titlebar");
   			$(titleCloseEle[0]).children("button").remove();
   			$(titleCloseEle[0]).append("<button type=\"button\" class=\"ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close\" role=\"button\" "
   										+"title=\"Close\"><span class=\"ui-button-icon-primary ui-icon ui-icon-closethick\"></span><span class=\"ui-button-text\">Close</span></button>");
   			$(titleCloseEle[0]).children("button").on("click", ksxzClose); 
		});
		function save(){
			var ids=$("#dataTable_ksxz").jqGrid("getGridParam", "selarrrow");
			if(ids.length == 0){
				art.dialog({
					content:"请选择数据后保存"
				});				
				return ;
			}
			var itemCodes = $("#itemCodes").val();
			$.each(ids,function(i,id){
				var rowData = $("#dataTable_ksxz").jqGrid("getRowData", id);
				selectRows.push(rowData);
				if(itemCodes != ""){
					itemCodes += ",";
				}
				itemCodes += rowData.itemcode;
			});
			$("#itemCodes").val(itemCodes);
			goto_query();
		}
		function tableSelected(flag){
			Global.JqGrid.jqGridSelectAll("dataTable_ksxz", flag);
		}
		function ksxzClose(){
		    Global.Dialog.returnData = selectRows;
    		closeWin();
		}
	</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
	   				<button id="saveBut" type="button" class="btn btn-system" onclick="save()">保存</button>
	   				<button id="allSelectBut" type="button" class="btn btn-system" onclick="tableSelected(true)">全选</button>
	   				<button id="nonSelectBut" type="button" class="btn btn-system" onclick="tableSelected(false)">不选</button>
	   				<button id="closeBut" type="button" class="btn btn-system" onclick="ksxzClose()">关闭</button>
				</div>
			</div>
		</div>
		
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" id="itemType1Def" name="itemType1Def" value="${data.itemType1}"/>
				<input type="hidden" id="itemType2Def" name="itemType2Def" value="${data.itemType2}"/>
				<input type="hidden" id="isSetItem" name="isSetItem" value="${data.isSetItem}"/>
				<input type="hidden" id="custCode" name="custCode" value="${data.custCode}"/>
				<input type="hidden" id="appNo" name="appNo" value="${data.appNo}"/>
				<input type="hidden" id="itemCodes" name="itemCodes" value="${data.itemCodes}"/>
				<input type="hidden" name="costRight" id="costRight" value="<%=costRight%>"/>
				<ul class="ul-form">
					<li>
						<label>材料编码</label>
						<input type="text" id="itemCode" name="itemCode"/>
					</li>
					<li>
						<label>品牌</label>
						<house:dict id="brand" dictCode="" 
								    sql="SELECT (rtrim(Code)+' '+Descr) Descr,Code FROM dbo.tBrand WHERE ItemType2='${data.itemType2}' ORDER BY Code" 
								    sqlValueKey="Code" sqlLableKey="Descr">
						</house:dict>
					</li>
					<li>
						<label>条码</label>
						<input type="text" id="barCode" name="barCode"/>
					</li>
					<li>
						<label>供应商</label>
						<input type="text" id="supplCode" name="supplCode"/>
					</li>
					<li>
						<label>材料名称</label>
						<input type="text" id="itemDescr" name="itemDescr"/>
					</li>
					<li>
						<label>规格</label>
						<input type="text" id="sizeDesc" name="sizeDesc"/>
					</li>
					<li>
						<label>型号</label>
						<input type="text" id="model" name="model"/>
					</li>
					<li>
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1"></select>
					</li>
					<li>
						<label>材料类型2</label>
						<select id="itemType2" name="itemType2"></select>
					</li>
					<li>
						<label>材料类型3</label>
						<select id="itemType3" name="itemType3"></select>
					</li>							
					<li class="search-group">
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query()"  >查询</button>
					</li>
				</ul>
			</form>
		</div>
	  	
		<div class="clear_float"></div>
		
		<div id="content-list">
			<table id="dataTable_ksxz"></table>
			<div id="dataTable_ksxzPager"></div>
		</div>
	</div>
</body>
</html>


