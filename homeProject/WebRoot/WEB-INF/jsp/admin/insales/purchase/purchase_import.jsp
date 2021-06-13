<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Item列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
/**初始化表格*/
$(function(){
			
	//初始化材料类型1，2，3三级联动
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.initOption("sqlCode","${ctx}/admin/brand/getBrandList");
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.setSelect({firstSelect:'itemType1',
		firstValue:'${itemAppDetail.itemType1 }',
	});
	var postData = $("#page_form").jsonForm();
    //初始化表格
    var custCode = $.trim($("#custCode").val());
    var puno = $.trim($("#puno").val());
    var arr = $.trim($("#arr").val());
	Global.JqGrid.initJqGrid("dataTable",{
		url: "#",// "${ctx}/admin/itemReq/goImportJqGrid",
		postData:{custCode:custCode,itemType1:'${itemAppDetail.itemType1 }',puno:puno,arr:arr},
		//postData: postData,
		rowNum:100000,
		height:$(document).height()-$("#content-list").offset().top-115,
		multiselect:true,		
		colModel : [
			{name : 'purqty',index : 'purqty',width : 100,label:'purqty',sortable : true,align : "left",hidden:true},
			{name : 'useqty',index : 'useqty',width : 100,label:'useqty',sortable : true,align : "left",hidden:true},
			{name: "puno", index: "puno", width: 70, label: "puno", sortable: true, align: "left",hidden:true},
			{name: "itemcode", index: "itemcode", width: 70, label: "itcode", sortable: true, align: "left",hidden:true},
			{name: "pk", index: "pk", width: 70, label: "编号", sortable: true, align: "left",hidden:true},
			{name: "fixareadescr", index: "fixareadescr", width: 136, label: "区域", sortable: true, align: "left"},
			{name: "intprodpk", index: "intprodpk", width: 100, label: "集成成品", sortable: true, align: "left",hidden:true},
			{name: "intproddescr", index: "intproddescr", width: 100, label: "集成成品", sortable: true, align: "left"},
			{name: "itemtype2", index: "itemtype2", width: 100, label: "材料类型2", sortable: true, align: "left",hidden:true},
			{name: "itemdescr2", index: "itemdescr2", width: 100, label: "材料类型2", sortable: true, align: "left"},
			{name: "itemcode", index: "itemcode", width: 100, label: "材料编号", sortable: true, align: "left"},
			{name: "itemdescr", index: "itemdescr", width: 260, label: "材料名称", sortable: true, align: "left"},
			{name: "sqlcodedescr", index: "sqlcodedescr", width: 111, label: "品牌", sortable: true, align: "left"},
			{name: "suppldescr", index: "suppldescr", width: 170, label: "供应商", sortable: true, align: "left"},
			{name: "itemtype1", index: "itemtype1", width: 100, label: "类型编号", sortable: true, align: "left", hidden: true},
			{name: "typedescr", index: "typedescr", width: 100, label: "材料类型", sortable: true, align: "left", hidden: true},
			{name: "purchaseqty", index: "purchaseqty", width: 100, label: "此类型材料已采购数量", sortable: true, align: "left", hidden: true},
			{name: "qty", index: "qty", width: 100, label: "需求数量", sortable: true, align: "left"},
			{name: "sendqty", index: "sendqty", width: 100, label: "已发货数量", sortable: true, align: "left"},
			{name: "itemchgno", index: "itemchgno", width: 120, label: "增减单号", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 173, label: "备注", sortable: true, align: "left"} ,
			{name: "allqty", index: "allqty", width: 173, label: "库存", sortable: true, align: "left",hidden:true}, 
			{name: "price", index: "price", width: 173, label: "单价", sortable: true, align: "left",hidden:true} ,
			{name: "beflineamount", index: "beflineamount", width: 173, label: "总价", sortable: true, align: "left",hidden:true} ,
      		{name: "uomdescr", index: "uomdescr", width: 173, label: "单位", sortable: true, align: "left",hidden:true} ,
      		{name: "color", index: "color", width: 173, label: "颜色", sortable: true, align: "left",hidden:true}, 
      		{name: "custcode", index: "custcode", width: 173, label: "客户编号", sortable: true, align: "left",hidden:true} ,
      		{name: "itemtype1", index: "itemtype1", width: 173, label: "材料类型1", sortable: true, align: "left",hidden:true} ,
      		{name: "cost", index: "cost", width: 173, label: "进价", sortable: true, align: "left",hidden:true} ,
      		{name: "projectcost", index: "projectcost", width: 173, label: "项目经理结算价", sortable: true, align: "left",hidden:true} ,
      		
        ],
        gridComplete: function() {
		    var ids = $("#dataTable").jqGrid("getDataIDs")
		    if (ids) {
		        for (var i = 0; i < ids.length; i++) {
		            var rowData = $("#dataTable").jqGrid("getRowData", ids[i])
		            if (rowData.purchaseqty) {
		                $("#dataTable").find('#' + ids[i]).css("color", "red")
		            }
		        }
		    }
		}
	});
	 //保存
        $("#saveBtn").on("click",function(){
        	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
        	if(ids.length==0){
        		Global.Dialog.infoDialog("请选择一条或多条记录进行新增操作！");	
        		return;
        	}
        	var selectRows = [];
    		$.each(ids,function(k,id){
    			var row = $("#dataTable").jqGrid('getRowData', id);
    			selectRows.push(row);
    		});
    		Global.Dialog.returnData = selectRows;
    		closeWin();
        });
});


</script>
</head>
    
<body>
	<div class="body-box-list">
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
								<button type="button" class="btn btn-system " id="saveBtn">
									<span>保存</span>
								</button>
								<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
									<span>关闭</span>
								</button>
					</div>
				</div>
			</div>
		<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" readonly="true" id="arr" name="arr" style="width:160px;" value="${arr}"/>
						<ul class="ul-form">
							<li hidden="true">
								<label>客户编号</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;"   value="${itemAppDetail.custCode }" readonly="readonly"/>                                             
							</li>	
							<li hidden="true" >
								<label>材料1</label>
									<input type="text" id="itemType1" name="itemType1" style="width:160px;"  value="${itemAppDetail.itemType1}" readonly="readonly"/>                                             
							</li>	
							<li>
								<label>材料类型2</label>
								<house:DictMulitSelect id="itemType2" dictCode="" sql=" select rtrim(code) code,descr from titemType2 where Code in (
 					select i.itemType2 from titemreq a left join titem  i on  i.code=a.itemcode  where custcode='${itemAppDetail.custCode }' and a.itemType1='${itemAppDetail.itemType1}')" 
								sqlLableKey="descr" sqlValueKey="code" ></house:DictMulitSelect>
							</li>
							<li>
								<label>供应商编号</label>
								<house:DictMulitSelect id="supplierCode" dictCode="" sql=" select rtrim(code) code,descr from tsupplier where Code in (
 																							select i.supplCode from titemreq a left join titem  i on  i.code=a.itemcode  
 																							where custcode='${itemAppDetail.custCode }' and a.itemType1='${itemAppDetail.itemType1}')" 
								sqlLableKey="descr" sqlValueKey="code" ></house:DictMulitSelect>
							</li>
							<li>
								<label>增减单号</label>
								<house:DictMulitSelect id="itemChgNo" width="180px" dictCode="" 
								sql=" select icd.No Code,'' as descr from titemreq a 
									left join (select MAX(no) no,ReqPK from tItemChgDetail a 
									left join titemreq b on b.PK=a.ReqPK group by ReqPK)icd on icd.ReqPK=a.PK
									left join titemchg ic on ic.No= icd.no 
								where a.CustCode='${itemAppDetail.custCode }' and a.itemType1='${itemAppDetail.itemType1 }'
								 and icd.no is not null" 
								sqlLableKey="descr" sqlValueKey="code" ></house:DictMulitSelect>
							</li>
							
							<li hidden="true">
								<label>puno</label>
									<input type="text" id="puno" name="puno" style="width:160px;"  value="${itemAppDetail.puno }" readonly="readonly"/>                                             
							</li>
							<li> 
								<button type="button" class="btn  btn-sm btn-system " onclick="query();"  >查询</button>
							</li>
							<!-- <li> 
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							</li> -->
						</ul>	
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
		    <p style="color: red">注：标红的记录为此楼盘此种材料已部分采购</p>
			<div id="content-list">
				<table id= "dataTable"></table>
			</div> 
		</div>
<script type="text/javascript">

function query () {
	$("#dataTable").jqGrid("setGridParam",{url: "${ctx}/admin/itemReq/goImportJqGrid", postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}

$(function(){
    var itemType2 = $.trim($("#itemType2").val());
    var supplierCode = $.trim($("#supplierCode").val());
	$.ajax({
		url:'${ctx}/admin/purchase/getChangeParameter2',
		type: 'post',
		data: {custCode:'${itemAppDetail.custCode }',itemType1:'${itemAppDetail.itemType1}',itemType2:itemType2},
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
		    supplierCode2(obj);
	    }
	});
	$.ajax({
		url:'${ctx}/admin/purchase/getChangeParameter',
		type: 'post',
		data: {custCode:'${itemAppDetail.custCode }',itemType1:'${itemAppDetail.itemType1}',itemType2:itemType2},
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
		    itemType(obj);
	    }
	});
	$.ajax({
		url:'${ctx}/admin/purchase/getChangeParameter3',
		type: 'post',
		data: {custCode:'${itemAppDetail.custCode }',itemType1:'${itemAppDetail.itemType1}',itemType2:itemType2,supplierCode:supplierCode},
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
		    itemChgNo(obj);
	    }
	});
function supplierCode2(obj){
		var zTree_supplierCode = null;
		var setting_supplierCode = {
			check: {
				enable: true,
				chkboxType: {"Y":"ps", "N":"ps"}
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true,
				}
			},
			callback: {
				beforeCheck: beforeCheck_supplierCode,
				onCheck: onCheck_supplierCode
			}
		};
		var zNodes_supplierCode = [{"id":"_VIRTUAL_RO0T_ID_","isParent":true,"name":"请选择","open":true,"pId":""}
		   ];
		   for(var i=0;i<obj.length;i++){
				zNodes_supplierCode.push({"id":obj[i].code,"name":obj[i].code+" "+obj[i].descr,"pId":"_VIRTUAL_RO0T_ID_"});
		   }
		function beforeCheck_supplierCode(treeId, treeNode) {
			return true;
		}
		function onCheck_supplierCode(e, treeId, treeNode) {
			var zTree = zTree_supplierCode;
			var ids = [];
			var names = [];
			var nodes = zTree.getCheckedNodes(true);
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].isParent){
					continue;
				}
				ids.push(nodes[i]['id']);
				names.push(nodes[i]['name']);
			}
			$("#supplierCode").val(ids.join(','));
			
			$("#supplierCode_NAME").val(names.join(','));
			supplierChange();
			// $("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
		}
		function showMenu_supplierCode() {
			var voffset = $("#supplierCode_NAME").offset();
			var bodyHeight = parseInt(window.document.body.clientHeight);
			var realHeight = voffset.top+parseInt('250px')+$("#supplierCode_NAME").outerHeight();
			if(realHeight > bodyHeight){
				$("body").height(realHeight+5);
			}
			$("#menuContent_supplierCode").css({left:voffset.left + "px", top:voffset.top + $("#supplierCode_NAME").outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDown_supplierCode);
		}
		function hideMenu_supplierCode() {
			$("#menuContent_supplierCode").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown_supplierCode);
			
		}
		function onBodyDown_supplierCode(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_supplierCode" || $(event.target).parents("#menuContent_supplierCode").length>0)) {
				hideMenu_supplierCode();
			}
		}
		$(document).ready(function(){
			document.execCommand("BackgroundImageCache",false,true);
		
			$("body").append('<div id="menuContent_supplierCode" style="display:none; position: absolute;z-index:9999;"><ul id="tree_supplierCode" class="ztree" style="margin-top: 0px;border: 1px solid #617775;background: white;overflow-y:auto;overflow-x:auto; width: 160px;height: 250px;"></ul></div>');
		
			$.fn.zTree.init($("#tree_supplierCode"), setting_supplierCode, zNodes_supplierCode);
			zTree_supplierCode = $.fn.zTree.getZTreeObj("tree_supplierCode");
		});
	}	
//itemChgNo
function itemChgNo(obj){		
		var zTree_itemChgNo = null;
		var setting_itemChgNo = {
			check: {
				enable: true,
				chkboxType: {"Y":"ps", "N":"ps"}
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true,
				}
			},
			callback: {
				beforeCheck: beforeCheck_itemChgNo,
				onCheck: onCheck_itemChgNo
			}
		};
		var zNodes_itemChgNo = [{"id":"_VIRTUAL_RO0T_ID_","isParent":true,"name":"请选择","open":true,"pId":""}
		   ];
		   for(var i=0;i<obj.length;i++){
				zNodes_itemChgNo.push({"id":obj[i].chgcode,"name":obj[i].chgcode,"pId":"_VIRTUAL_RO0T_ID_"});
		   }
		function beforeCheck_itemChgNo(treeId, treeNode) {
			return true;
		}
		function onCheck_itemChgNo(e, treeId, treeNode) {
			var zTree = zTree_itemChgNo;
			var ids = [];
			var names = [];
			var nodes = zTree.getCheckedNodes(true);
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].isParent){
					continue;
				}
				ids.push(nodes[i]['id']);
				names.push(nodes[i]['name']);
			}
			$("#itemChgNo").val(ids.join(','));
			
			$("#itemChgNo_NAME").val(names.join(','));
			// $("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
		}
		function showMenu_itemChgNo() {
			var voffset = $("#itemChgNo_NAME").offset();
			var bodyHeight = parseInt(window.document.body.clientHeight);
			var realHeight = voffset.top+parseInt('250px')+$("#itemChgNo_NAME").outerHeight();
			if(realHeight > bodyHeight){
				$("body").height(realHeight+5);
			}
			$("#menuContent_itemChgNo").css({left:voffset.left + "px", top:voffset.top + $("#itemChgNo_NAME").outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDown_itemChgNo);
		}
		function hideMenu_itemChgNo() {
			$("#menuContent_itemChgNo").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown_itemChgNo);
			
		}
		function onBodyDown_itemChgNo(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_itemChgNo" || $(event.target).parents("#menuContent_itemChgNo").length>0)) {
				hideMenu_itemChgNo();
			}
		}
		$(document).ready(function(){
			document.execCommand("BackgroundImageCache",false,true);
		
			$("body").append('<div id="menuContent_itemChgNo" style="display:none; position: absolute;z-index:9999;"><ul id="tree_itemChgNo" class="ztree" style="margin-top: 0px;border: 1px solid #617775;background: white;overflow-y:auto;overflow-x:auto; width: 160px;height: 250px;"></ul></div>');
		
			$.fn.zTree.init($("#tree_itemChgNo"), setting_itemChgNo, zNodes_itemChgNo);
			zTree_itemChgNo = $.fn.zTree.getZTreeObj("tree_itemChgNo");
		});
	}	
//itemType2
	function itemType(obj){		
		var zTree_itemType2 = null;
		var setting_itemType2 = {
			check: {
				enable: true,
				chkboxType: {"Y":"ps", "N":"ps"}
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true,
				}
			},
			callback: {
				beforeCheck: beforeCheck_itemType2,
				onCheck: onCheck_itemType2
			}
		};
		var zNodes_itemType2 = [{"id":"_VIRTUAL_RO0T_ID_","isParent":true,"name":"请选择","open":true,"pId":""}
		   ];
		   for(var i=0;i<obj.length;i++){
				zNodes_itemType2.push({"id":obj[i].item2code,"name":obj[i].item2code+" "+obj[i].item2descr,"pId":"_VIRTUAL_RO0T_ID_"});
		   }
		function beforeCheck_itemType2(treeId, treeNode) {
			return true;
		}
		function onCheck_itemType2(e, treeId, treeNode) {
			var zTree = zTree_itemType2;
			var ids = [];
			var names = [];
			var nodes = zTree.getCheckedNodes(true);
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].isParent){
					continue;
				}
				ids.push(nodes[i]['id']);
				names.push(nodes[i]['name']);
			}
			$("#itemType2").val(ids.join(','));
			
			$("#itemType2_NAME").val(names.join(','));
			item2Change();
			// $("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
		}
		function showMenu_itemType2() {
			var voffset = $("#itemType2_NAME").offset();
			var bodyHeight = parseInt(window.document.body.clientHeight);
			var realHeight = voffset.top+parseInt('250px')+$("#itemType2_NAME").outerHeight();
			if(realHeight > bodyHeight){
				$("body").height(realHeight+5);
			}
			$("#menuContent_itemType2").css({left:voffset.left + "px", top:voffset.top + $("#itemType2_NAME").outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDown_itemType2);
		}
		function hideMenu_itemType2() {
			$("#menuContent_itemType2").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown_itemType2);
			
		}
		function onBodyDown_itemType2(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_itemType2" || $(event.target).parents("#menuContent_itemType2").length>0)) {
				hideMenu_itemType2();
			}
		}
		$(document).ready(function(){
			document.execCommand("BackgroundImageCache",false,true);
		
			$("body").append('<div id="menuContent_itemType2" style="display:none; position: absolute;z-index:9999;"><ul id="tree_itemType2" class="ztree" style="margin-top: 0px;border: 1px solid #617775;background: white;overflow-y:auto;overflow-x:auto; width: 160px;height: 250px;"></ul></div>');
		
			$.fn.zTree.init($("#tree_itemType2"), setting_itemType2, zNodes_itemType2);
			zTree_itemType2 = $.fn.zTree.getZTreeObj("tree_itemType2");
		});	
	}
	function item2Change(){
		    var itemt2 = $.trim($("#itemType2").val());
		
				$("#supplierCode").val("");
				$("#supplierCode_NAME").val("");
				$("#itemChgNo").val("");
				$("#itemChgNo_NAME").val("");
		$.ajax({
			url:'${ctx}/admin/purchase/getChangeParameter2',
			type: 'post',
			data: {custCode:'${itemAppDetail.custCode }',itemType1:'${itemAppDetail.itemType1}',itemType2:itemt2},
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	var supplierCode = $.trim($("#supplierCode").val());
				supplierCode2(obj);
				$.ajax({
					url:'${ctx}/admin/purchase/getChangeParameter3',
					type: 'post',
					data: {custCode:'${itemAppDetail.custCode }',itemType1:'${itemAppDetail.itemType1}',itemType2:itemt2,supplierCode:supplierCode},
					dataType: 'json',
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
				    },
				    success: function(obj){
						itemChgNo(obj);
				    }
			 	});
		    }
	 	});
	}
	function supplierChange(){
		    var itemt2 = $.trim($("#itemType2").val());
		    var supplierCode = $.trim($("#supplierCode").val());
		    	$("#itemChgNo").val("");
				$("#itemChgNo_NAME").val("");
		$.ajax({
			url:'${ctx}/admin/purchase/getChangeParameter3',
			type: 'post',
			data: {custCode:'${itemAppDetail.custCode }',itemType1:'${itemAppDetail.itemType1}',itemType2:itemt2,supplierCode:supplierCode},
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
				itemChgNo(obj);
		    }
	 	});
	}	
});
</script>		
</body>
</html>


