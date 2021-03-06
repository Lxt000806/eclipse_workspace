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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript" defer></script>

	<script type="text/javascript" defer>
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
			// $("#itemType2").attr("disabled", true);							
			Global.JqGrid.initJqGrid("dataTable_ksxz", {
				height:400,
				styleUI:"Bootstrap",
				url:"${ctx}/admin/itemPreApp/goKsxzJqGrid",
				postData:{
					itemType1:$("#itemType1Def").val(),
					itemType2:$("#itemType2Def").val(),
					isSetItem:$("#isSetItem").val(),
					//custCode:$("#custCode").val(),
					appNo:$("#appNo").val(),
					itemCodes:$("#itemCodes").val()
				},
				multiselect:true,
				colModel:[
					{name:"itemcode", index:"itemcode", width:70, label:"ææįžå·", sortable:true, align:"left"},
					{name:"itemdescr", index:"itemdescr", width:284, label:"åį§°", sortable:true, align:"left"},
					{name:"cost", index:"cost", width:50, label:"ææŽ", sortable:true, align:"left", hidden:true},
					{name:"projectcost", index:"projectcost", width:120, label:"éĄđįŪįŧįįŧįŪäŧ·", sortable:true, align:"left", hidden:true},
					{name:"uomdescr", index:"uomdescr", width:60, label:"åä―", sortable:true, align:"left"},
					{name:"itemtype1descr", index:"itemtype1descr", width:74.5, label:"ææįąŧå1", sortable:true, align:"left"},
					{name:"itemtype2descr", index:"itemtype2descr", width:74.5, label:"ææįąŧå2", sortable:true, align:"left"},
					{name:"itemtype3descr", index:"itemtype3descr", width:74.5, label:"ææįąŧå3", sortable:true, align:"left"},
					{name:"sqlcodedescr", index:"sqlcodedescr", width:70, label:"åį", sortable:true, align:"left"},
					{name:"sendtypedescr", index:"sendtypedescr", width:70, label:"åčī§įąŧå", sortable:true, align:"left"},
					{name:"color", index:"color", width:50, label:"éĒčē", sortable:true, align:"left"},
					{name:"model", index:"model", width:70, label:"åå·", sortable:true, align:"left"},
					{name:"sizedesc", index:"sizedesc", width:70, label:"č§æ ž", sortable:true, align:"left"},
					{name:"barcode", index:"barcode", width:50, label:"æĄį ", sortable:true, align:"left"},
					{name:"isfixpricedescr", index:"isfixpricedescr", width:80, label:"æŊåĶåšåŪäŧ·", sortable:true, align:"left"},
					{name:"price", index:"price", width:50, label:"åäŧ·", sortable:true, align:"left"},
					{name:"pk", index:"pk", width:70, label:"įžå·", sortable:true, align:"left", hidden:true},
					{name:"custcode", index:"custcode", width:100, label:"åŪĒæ·įžå·", sortable:true, align:"left", hidden:true},
					{name:"fixareadescr", index:"fixareadescr", width:136, label:"åšå", sortable:true, align:"left", hidden:true},
					{name:"intproddescr", index:"intproddescr", width:100, label:"éææå", sortable:true, align:"left", hidden:true},
					{name:"suppldescr", index:"suppldescr", width:154, label:"äūåšå", sortable:true, align:"left", hidden:true},
					{name:"itemtype1", index:"itemtype1", width:100, label:"įąŧåįžå·", sortable:true, align:"left", hidden:true},
					{name:"typedescr", index:"typedescr", width:100, label:"ææįąŧå", sortable:true, align:"left", hidden:true},
					{name:"qty", index:"qty", width:100, label:"éæąæ°é", sortable:true, align:"left", hidden:true},
					{name:"sendqty", index:"sendqty", width:110, label:"å·ēåčī§æ°é", sortable:true, align:"left", hidden:true},
					{name:"itemchgno", index:"itemchgno", width:120, label:"åĒååå·", sortable:true, align:"left", hidden:true},
					{name:"remarks", index:"remarks", width:173, label:"åĪæģĻ", sortable:true, align:"left", hidden:true},
					{name:"reqpk", index:"reqpk", width:70, label:"éĒææ čŊ", sortable:true, align:"left", hidden:true},
					{name:"reqremarks", index:"reqremarks", width:60, label:"éĒįŪåĪæģĻ", sortable:true, align:"left", hidden:true},
					{name:"no", index:"no", width:80, label:"æđæŽĄå·", sortable:true, align:"left", hidden:true},
					{name:"fixareapk", index:"fixareapk", width:84, label:"fixareapk", sortable:true, align:"left", hidden:true},
					// {name:"intprodpk", index:"intprodpk", width:84, label:"intprodpk", sortable:true, align:"left", hidden:true},
					{name:"supplcodedescr", index:"supplcodedescr", width:160, label:"äūåšå", sortable:true, align:"left", hidden:true},
					{name:"declareqty", index:"declareqty", width:76, label:"įģæĨæ°é", sortable:true, align:"left",hidden:true},
					{name:"shortqty", index:"shortqty", width:89, label:"įžščī§æ°é", sortable:true, align:"left", hidden:true},
					{name:"reqqty", index:"reqqty", width:79, label:"éĒįŪæ°é", sortable:true, align:"left", sum:true, hidden:true},
					{name:"sendedqty", index:"sendedqty", width:89, label:"æŧåąå·ēåčī§", sortable:true, align:"left", sum:true, hidden:true},
					{name:"applyqty", index:"applyqty", width:89, label:"å·ēįģčŊ·æ°é", sortable:true, align:"left", hidden:true},
					{name:"confirmedqty", index:"confirmedqty", width:89, label:"å·ēåŪĄæ ļæ°é", sortable:true, align:"left", sum:true, hidden:true},
					{name:"allcost", index:"allcost", width:90, label:"ææŽæŧäŧ·", sortable:true, align:"left", sum:true, hidden:true},
					{name:"allprojectcost", index:"allprojectcost", width:120, label:"éĄđįŪįŧįįŧįŪæŧäŧ·", sortable:true, align:"left", sum:true, hidden:true},
					{name:"processcost", index:"processcost", width:80, label:"åķåŪčīđįĻ", sortable:true, align:"left", sum:true, hidden:true},
					{name:"differences", index:"differences", width:89, label:"ææŽå·ŪåžéĒ", sortable:true, align:"left", sum:true, hidden:true},
					{name:"pricedifferences", index:"pricedifferences", width:90, label:"éåŪå·ŪåžéĒ", sortable:true, align:"left", hidden:true},
					{name:"weight", index:"weight", width:85, label:"æŧéé", sortable:true, align:"left", sum:true, hidden:true},
					{name:"numdescr", index:"numdescr", width:85, label:"æŧįæ°", sortable:true, align:"left", hidden:true},
					{name:"reqprocesscost", index:"reqprocesscost", width:156, label:"reqprocesscost", sortable:true, align:"left", hidden:true},
					{name:"perweight", index:"perweight", width:156, label:"perweight", sortable:true, align:"left", hidden:true},
					{name:"aftqty", index:"aftqty", width:90, label:"aftqty", sortable:true, align:"left", hidden:true},
					{name:"aftcost", index:"aftcost", width:90, label:"aftcost", sortable:true, align:"left", hidden:true},
					{name:"whcode", index:"whcode", width:90, label:"whcode", sortable:true, align:"left", hidden:true},
					{name:"whdescr", index:"whdescr", width:90, label:"whdescr", sortable:true, align:"left", hidden:true},
					{name:"supplcode", index:"supplcode", width:90, label:"supplcode", sortable:true, align:"left", hidden:true},
					{name:"sendtype", index:"sendtype", width:90, label:"sendtype", sortable:true, align:"left", hidden:true},
					{name:"itemtype2", index:"itemtype2", width:90, label:"itemtype2", sortable:true, align:"left", hidden:true},
					{name:"avgcost", index:"avgcost", width:90, label:"avgcost", sortable:true, align:"left", hidden:true},
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
   			
   			//æđåįŠåĢåģäļč§åģé­æéŪäšäŧķ
   			var titleCloseEle = parent.$("div[aria-describedby=dialog_quicklySave]").children(".ui-dialog-titlebar");
   			$(titleCloseEle[0]).children("button").remove();
   			$(titleCloseEle[0]).append("<button type=\"button\" class=\"ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close\" role=\"button\" "
   										+"title=\"Close\"><span class=\"ui-button-icon-primary ui-icon ui-icon-closethick\"></span><span class=\"ui-button-text\">Close</span></button>");
   			$(titleCloseEle[0]).on("click", ksxzClose); 
		});
		function save(){
			var ids=$("#dataTable_ksxz").jqGrid("getGridParam", "selarrrow");
			if(ids.length == 0){
				art.dialog({
					content:"čŊ·éæĐæ°æŪåäŋå­"
				});				
				return ;
			}
			var itemCodes = $("#itemCodes").val();
			$.each(ids,function(i,id){
				var rowData = $("#dataTable_ksxz").jqGrid("getRowData", id);
				/*$.ajax({
					url:"${ctx}/admin/specItemReq/getAppQty",
					type:"post",
					data:{custCode:"${data.custCode}",itemCode:rowData.itemcode},
					dataType:"json",
					cache:false,
					async:false,// (éŧčŪĪ: true) éŧčŪĪčŪūį―ŪäļïžææčŊ·æąåäļšåžæ­ĨčŊ·æąã
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": "č·åå·ēäļåæ°æŪåšé"});
				    },
					success:function(obj){
						rowData.appqty = obj.AppQty;
						rowData.isexist = obj.isExist;
						rowData.qty = obj.AppQty;
						rowData.totalcost = Math.round(parseFloat(rowData.cost) * parseFloat(obj.AppQty) * 100) / 100;
					}
				});*/
				rowData.qty = 0;
				rowData.appqty = 0;
				rowData.totalcost = 0;

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
	   				<button id="saveBut" type="button" class="btn btn-system" onclick="save()">äŋå­</button>
	   				<button id="allSelectBut" type="button" class="btn btn-system" onclick="tableSelected(true)">åĻé</button>
	   				<button id="nonSelectBut" type="button" class="btn btn-system" onclick="tableSelected(false)">äļé</button>
	   				<button id="closeBut" type="button" class="btn btn-system" onclick="ksxzClose()">åģé­</button>
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
				<!-- <input type="hidden" id="itemCodes" name="itemCodes" value="${data.itemCodes}"/> -->
				<input type="hidden" id="itemCodes" name="itemCodes" value=""/>
				<input type="hidden" name="costRight" id="costRight" value="<%=costRight%>"/>
				<ul class="ul-form">
					<li>
						<label>ææįžį </label>
						<input type="text" id="itemCode" name="itemCode"/>
					</li>
					<li>
						<label>åį</label>
						<house:dict id="brand" dictCode="" 
						    sql="SELECT (rtrim(Code)+' '+Descr) Descr,Code FROM dbo.tBrand WHERE ItemType2='${data.itemType2}' ORDER BY Code" 
						    sqlValueKey="Code" sqlLableKey="Descr">
						</house:dict>
					</li>
					<li>
						<label>æĄį </label>
						<input type="text" id="barCode" name="barCode"/>
					</li>
					<li>
						<label>äūåšå</label>
						<input type="text" id="supplCode" name="supplCode"/>
					</li>
					<li>
						<label>ææåį§°</label>
						<input type="text" id="itemDescr" name="itemDescr"/>
					</li>
					<li>
						<label>č§æ ž</label>
						<input type="text" id="sizeDesc" name="sizeDesc"/>
					</li>
					<li>
						<label>åå·</label>
						<input type="text" id="model" name="model"/>
					</li>
					<li>
						<label>ææįąŧå1</label>
						<select id="itemType1" name="itemType1"></select>
					</li>
					<li>
						<label>ææįąŧå2</label>
						<select id="itemType2" name="itemType2"></select>
					</li>
					<li>
						<label>ææįąŧå3</label>
						<select id="itemType3" name="itemType3"></select>
					</li>							
					<li class="search-group">
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query()">æĨčŊĒ</button>
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
