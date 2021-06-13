<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
var isCopy=false;
$(function(){
	Global.JqGrid.initJqGrid("detailDataTable",{
			url:"${ctx}/admin/basePlanTempDetail/goJqGrid",
			postData:{no:"${basePlanTemp.no}"},
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: 'Bootstrap', 
			rowNum:10000000,
			searchBtn:true,
			onSortColEndFlag:true,
			height:380,
			colModel : [
				{name: "pk", index: "pk", width: 94, label: "pk", sortable: true, align: "left", hidden: true},
				{name: "fixareatype", index: "fixareatype", width: 106, label: "区域类型", sortable: true, align: "left",hidden:true},
				{name: "fixareatypedescr", index: "fixareatypedescr", width: 80, label: "区域类型", sortable: true, align: "left"},
				{name: "baseitemcode", index: "baseitemcode", width: 100, label: "基础项目编号", sortable: true, align: "left"},
				{name: "baseitemdescr", index: "baseitemdescr", width: 100, label: "基础项目名称", sortable: true, align: "left"},
				{name: "basealgorithm", index: "basealgorithm", width: 150, label: "基础项目算法", sortable: true, align: "left",hidden:true},
				{name: "basealgorithmdescr", index: "basealgorithmdescr", width: 110, label: "基础项目算法", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 98, label: "数量", sortable: true, align: "right", sum: true},
				{name: "isoutset", index: "isoutset", width: 100, label: "是否套餐外项目", sortable: true, align: "left",hidden:true},
				{name: "isoutsetdescr", index: "isoutsetdescr", width: 110, label: "是否套餐外项目", sortable: true, align: "left"},
				{name: "isrequired", index: "isrequired", width: 95, label: "必选项", sortable: true, align: "left",hidden:true},
				{name: "isrequireddescr", index: "isrequireddescr", width: 65, label: "必选项", sortable: true, align: "left"},
				{name: "canreplace", index: "canreplace", width: 88, label: "可替换", sortable: true, align: "left",hidden:true},
				{name: "canreplacedescr", index: "canreplacedescr", width: 65, label: "可替换", sortable: true, align: "left"},
				{name: "canmodiqty", index: "canmodiqty", width: 220, label: "数量可修改", sortable: true, align: "left",hidden:true},
				{name: "canmodiqtydescr", index: "canmodiqtydescr", width: 85, label: "数量可修改", sortable: true, align: "left"},
				{name: "pavetile", index: "pavetile", width: 120, label: "铺瓷砖", sortable: true, align: "left",hidden:true},
				{name: "pavetiledescr", index: "pavetiledescr", width: 65, label: "铺瓷砖", sortable: true, align: "left"},
				{name: "pavefloor", index: "pavefloor", width: 112, label: "铺实木地板", sortable: true, align: "left",hidden:true},
				{name: "pavefloordescr", index: "pavefloordescr", width: 80, label: "铺实木地板", sortable: true, align: "left"},
				{name: "pavediamondfloor", index: "pavediamondfloor", width: 112, label: "铺金刚板", sortable: true, align: "left",hidden:true},
				{name: "pavediamondfloordescr", index: "pavediamondfloordescr", width: 80, label: "铺金刚板", sortable: true, align: "left"},
				{name: "dispseq", index: "dispseq", width: 80, label: "显示顺序", sortable: true, align: "right"},
				{name: "remark", index: "remark", width: 208, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 76, label: "过期标志", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 76, label: "操作标志", sortable: true, align: "left"}
			],
			onSortCol:function(index, iCol, sortorder){
					var rows = $("#detailDataTable").jqGrid("getRowData");
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("detailDataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("detailDataTable", v);
					});
			},
		});
	});
	//新增
	function add(){
		var ids=$("#detailDataTable").jqGrid("getDataIDs");
		var ret = $("#detailDataTable").jqGrid('getRowData',ids[ids.length-1]);
		var dispseq=0;
		var newId=1;
		if(ids.length>0){
			dispseq=parseInt(ret.dispseq,0)+1;
			newId=parseInt(ids[ids.length-1],0)+1;
		} 
		Global.Dialog.showDialog("save", {
			title : "基础算量模板明细--增加",
			url : "${ctx}/admin/basePlanTempDetail/goAdd?m_umState=A&custType="+$("#custType").val(),
		    height:470,
		    width:750,
			returnFun : function(v) {
						var json = {
							fixareatypedescr : v.fixAreaTypeDescr,baseitemcode : v.baseItemCode,basealgorithmdescr : v.baseAlgorithmDescr,
							isoutset : v.isOutSet,isrequired : v.isRequired,isrequireddescr : v.isRequiredDescr,
							baseitemdescr : v.baseItemDescr,canreplacedescr : v.canReplaceDescr,
							qty : parseFloat(v.qty,0),canreplace : v.canReplace,actionlog : "ADD",fixareatype : v.fixAreaType,
							canmodiqty : v.canModiQty,basealgorithm: v.baseAlgorithm,
							lastupdate : new Date().getTime(),expired : "F",lastupdatedby : "${basePlanTemp.lastUpdatedBy}",
							canmodiqtydescr : v.canModiQtyDescr,isoutsetdescr:v.isOutSetDescr,dispseq:dispseq,
							pavetiledescr : v.paveTileDescr,pavetile:v.paveTile,remark:v.remark,
							pavefloordescr : v.paveFloorDescr,pavefloor:v.paveFloor,pavediamondfloor:v.paveDiamondFloor,
							pavediamondfloordescr:v.paveDiamondFloorDescr
						};
						$("#detailDataTable").jqGrid("addRowData", newId, json);
						$(".copy").hide();
						$(".copyOne").show();
						$("#dataChanged").val("1");
			}
		});
	}
	//插入
	function insert(){
		var id = $("#detailDataTable").jqGrid("getGridParam","selrow");
		var posi=id-1;
		var rowData = $("#detailDataTable").jqGrid('getRowData');
		var ids=$("#detailDataTable").jqGrid("getDataIDs");
		var ret = $("#detailDataTable").jqGrid('getRowData',ids[ids.length-1]);
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		} 
		Global.Dialog.showDialog("insert", {
			title : "基础算量模板明细--插入",
			url : "${ctx}/admin/basePlanTempDetail/goAdd?m_umState=A&custType="+$("#custType").val(),
		    height:470,
		    width:750,
			returnFun : function(v) {
						var json = {
							fixareatypedescr : v.fixAreaTypeDescr,baseitemcode : v.baseItemCode,basealgorithmdescr : v.baseAlgorithmDescr,
							isoutset : v.isOutSet,isrequired : v.isRequired,isrequireddescr : v.isRequiredDescr,
							baseitemdescr : v.baseItemDescr,canreplacedescr : v.canReplaceDescr,
							qty : parseFloat(v.qty,0),canreplace : v.canReplace,actionlog : "ADD",fixareatype : v.fixAreaType,
							canmodiqty : v.canModiQty,basealgorithm: v.baseAlgorithm,
							lastupdate : new Date().getTime(),expired : "F",lastupdatedby : "${basePlanTemp.lastUpdatedBy}",
							canmodiqtydescr : v.canModiQtyDescr,isoutsetdescr:v.isOutSetDescr,
							pavetiledescr : v.paveTileDescr,pavetile:v.paveTile,remark:v.remark,
							pavefloordescr : v.paveFloorDescr,pavefloor:v.paveFloor,pavediamondfloor:v.paveDiamondFloor,
							pavediamondfloordescr:v.paveDiamondFloorDescr
						};
						rowData.splice(posi++,0,json);	
						$("#detailDataTable").jqGrid("clearGridData");
						$.each(rowData, function(i,r){
							r['dispseq']=i;//设置顺序
							$("#detailDataTable").jqGrid("addRowData", i+1, r);
						});
						$("#detailDataTable").jqGrid("setSelection",id);//选中此行
						$('.ui-jqgrid .ui-jqgrid-bdiv').scrollTop(24*id);//滚动条滚动
						$(".copy").hide();
						$(".copyOne").show();
						$("#dataChanged").val("1");
			}
		});
	}
	//编辑
	function update(){
		var id = $("#detailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#detailDataTable").jqGrid('getRowData',id);
	 	if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		} 
		Global.Dialog.showDialog("save", {
			title : "基础算量模板明细--编辑",
			url : "${ctx}/admin/basePlanTempDetail/goAdd",
		    height:470,
		    width:750,
		    postData:{
		    	fixAreatypedescr:ret.fixareatypedescr, baseItemCode: ret.baseitemcode,
				isOutSet:ret.isoutset, isRequired: ret.isrequired,baseItemDescr : ret.baseitemdescr,
				qty : ret.qty, canReplace: ret.canreplace, fixAreaType: ret.fixareatype,m_umState:"M",
				canModiQty : ret.canmodiqty,baseAlgorithm: ret.basealgorithm,remark:ret.remark,
				paveTile:ret.pavetile,paveFloor:ret.pavefloor,paveDiamondFloor:ret.pavediamondfloor,
				custType:$("#custType").val()
		    },
			returnFun : function(v) {
						var json = {
							fixareatypedescr : v.fixAreaTypeDescr,baseitemcode : v.baseItemCode,basealgorithmdescr : v.baseAlgorithmDescr,
							isoutset : v.isOutSet,isrequired : v.isRequired,isrequireddescr : v.isRequiredDescr,
							baseitemdescr : v.baseItemDescr,canreplacedescr : v.canReplaceDescr,
							qty :parseFloat(v.qty,0),canreplace : v.canReplace,actionlog : "ADD",fixareatype : v.fixAreaType,
							canmodiqty : v.canModiQty,dispseq : v.dispSeq,basealgorithm: v.baseAlgorithm,
							lastupdate : new Date().getTime(),expired : "F",lastupdatedby : "${basePlanTemp.lastUpdatedBy}",
							canmodiqtydescr : v.canModiQtyDescr,isoutsetdescr:v.isOutSetDescr,
							pavetiledescr : v.paveTileDescr,pavetile:v.paveTile,
							pavefloordescr : v.paveFloorDescr,pavefloor:v.paveFloor,pavediamondfloor:v.paveDiamondFloor,
							pavediamondfloordescr:v.paveDiamondFloorDescr,remark:v.remark
						};
						console.log(json);
						$("#detailDataTable").setRowData(id, json);
						$("#dataChanged").val("1");
			}
		});
	}
	//查看
	function view(){
		$("#custType").removeAttr("disabled");
		var custType=$("#custType").val();
		$("#custType").attr("disabled",true);
		var id = $("#detailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#detailDataTable").jqGrid('getRowData',id);
	 	if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		} 
		Global.Dialog.showDialog("save", {
			title : "基础算量模板明细--查看",
			url : "${ctx}/admin/basePlanTempDetail/goAdd",
		    height:470,
		    width:750,
		    postData:{
		    	fixAreatypedescr:ret.fixareatypedescr, baseItemCode: ret.baseitemcode,
				isOutSet:ret.isoutset, isRequired: ret.isrequired,baseItemDescr : ret.baseitemdescr,
				qty : ret.qty, canReplace: ret.canreplace, fixAreaType: ret.fixareatype,m_umState:"V",
				canModiQty : ret.canmodiqty,baseAlgorithm: ret.basealgorithm,remark:ret.remark,
				paveTile:ret.pavetile,paveFloor:ret.pavefloor,paveDiamondFloor:ret.pavediamondfloor,
		    },
		    
		});
	}
	//删除
	function del(){
	 	var id = $("#detailDataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		art.dialog({
			 content:"是否确认要删除",
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
				Global.JqGrid.delRowData("detailDataTable",id);
				var rowData = $("#detailDataTable").jqGrid('getRowData');
				$("#detailDataTable").jqGrid("clearGridData");
				$.each(rowData, function(i,r){
					r['dispseq']=i;//设置顺序
					$("#detailDataTable").jqGrid("addRowData", i+1, r);
				});
				var rowNum = $("#detailDataTable").jqGrid("getGridParam","records");
				if(rowNum==0){
					$("#custType").removeAttr("disabled");
					$(".copy").show();
					$(".copyOne").hide();
				}else{
					$(".copyOne").show();
					$(".copy").hide();
				}
				$("#dataChanged").val("1");
			},
			cancel: function () {
					return true;
			}
		}); 
	}
	//向上
	function up(){
		var rowId = $("#detailDataTable").jqGrid("getGridParam", "selrow");
		var replaceId = parseInt(rowId)-1;
		if(rowId){
			if(rowId>1){
			    var ret1 = $("#detailDataTable").jqGrid("getRowData", rowId);
				var ret2 = $("#detailDataTable").jqGrid("getRowData", replaceId);
				replace(rowId,replaceId,"detailDataTable");
				scrollToPosi(replaceId,"detailDataTable");
				$("#detailDataTable").setCell(rowId,"dispseq",ret1.dispseq);
				$("#detailDataTable").setCell(replaceId,"dispseq",ret2.dispseq);
				$("#dataChanged").val("1");
			}
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	//向下
	function down(){
		var rowId = $("#detailDataTable").jqGrid("getGridParam", "selrow");
		var replaceId = parseInt(rowId)+1;
		var rowNum = $("#detailDataTable").jqGrid("getGridParam","records");
		if(rowId){
			if(rowId<rowNum){
				var ret1 = $("#detailDataTable").jqGrid("getRowData", rowId);
				var ret2 = $("#detailDataTable").jqGrid("getRowData", replaceId);
				scrollToPosi(replaceId,"detailDataTable");
				replace(rowId,replaceId,"detailDataTable");
				$("#detailDataTable").setCell(rowId,"dispseq",ret1.dispseq);
				$("#detailDataTable").setCell(replaceId,"dispseq",ret2.dispseq);
				$("#dataChanged").val("1");
			}
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	//复制
	function copy(){
		Global.Dialog.showDialog("copy", {
			title : "基础算量模板明细--增加",
			url : "${ctx}/admin/basePlanTempDetail/goCopy",
		    height:700,
		    width:1000,
			returnFun : function(data) {
					if(data.length > 0){
						isCopy=true;
						var ids = $("#detailDataTable").jqGrid("getDataIDs");
						$.each(data, function(i,rowData){
							$("#detailDataTable").jqGrid("addRowData", (i+1+ids.length), rowData);
						});
						$(".copy").hide();
						$(".copyOne").show();
						$("#dataChanged").val("1");
					}
			}
		});
	}
	//复制一个
	function copyOne() {
		var ids=$("#detailDataTable").jqGrid("getDataIDs");
		var ret = $("#detailDataTable").jqGrid('getRowData',ids[ids.length-1]);
		var dispseq=0;
		var newId=1;
		if(ids.length>0){
			dispseq=parseInt(ret.dispseq,0)+1;
			newId=parseInt(ids[ids.length-1],0)+1;
		} 
		var id = $("#detailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#detailDataTable").jqGrid('getRowData',id);
	 	if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		} 
		Global.Dialog.showDialog("save", {
			title : "基础算量模板明细--复制",
			url : "${ctx}/admin/basePlanTempDetail/goAdd",
		    height:470,
		    width:750,
		    postData:{
		    	fixAreatypedescr:ret.fixareatypedescr, baseItemCode: ret.baseitemcode,
				isOutSet:ret.isoutset, isRequired: ret.isrequired,baseItemDescr : ret.baseitemdescr,
				qty : ret.qty, canReplace: ret.canreplace, fixAreaType: ret.fixareatype,m_umState:"C",
				canModiQty : ret.canmodiqty,baseAlgorithm: ret.basealgorithm,remark:ret.remark,
				paveTile:ret.pavetile,paveFloor:ret.pavefloor,paveDiamondFloor:ret.pavediamondfloor,
				custType:$("#custType").val()
		    },
			returnFun : function(v) {
						var json = {
							fixareatypedescr : v.fixAreaTypeDescr,baseitemcode : v.baseItemCode,basealgorithmdescr : v.baseAlgorithmDescr,
							isoutset : v.isOutSet,isrequired : v.isRequired,isrequireddescr : v.isRequiredDescr,
							baseitemdescr : v.baseItemDescr,canreplacedescr : v.canReplaceDescr,
							qty :parseFloat(v.qty,0),canreplace : v.canReplace,actionlog : "ADD",fixareatype : v.fixAreaType,
							canmodiqty : v.canModiQty,dispseq :dispseq,basealgorithm: v.baseAlgorithm,
							lastupdate : new Date().getTime(),expired : "F",lastupdatedby : "${basePlanTemp.lastUpdatedBy}",
							canmodiqtydescr : v.canModiQtyDescr,isoutsetdescr:v.isOutSetDescr,
							pavetiledescr : v.paveTileDescr,pavetile:v.paveTile,
							pavefloordescr : v.paveFloorDescr,pavefloor:v.paveFloor,pavediamondfloor:v.paveDiamondFloor,
							pavediamondfloordescr:v.paveDiamondFloorDescr,remark:v.remark
						};
						$("#detailDataTable").jqGrid("addRowData", newId, json);
						$(".copy").hide();
						$(".copyOne").show();
						$("#dataChanged").val("1");
			}
		});
	}
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="form" method="post">
				<input type="hidden" name="jsonString" value="" />
			</form>
			<div class="btn-group-xs">
				<button style="align:left" type="button" class="btn btn-system view" 
					onclick="add()">
					<span>新增 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system view" 
					onclick="insert()">
					<span>插入 </span>
				</button>
				<button style="align:left"  type="button" class="btn btn-system copy"
						onclick="copy()">
						<span>复制</span>
				</button>
				<button style="align:left" type="button" class="btn btn-system copyOne"
					onclick="copyOne()">
					<span>复制 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system view"
					onclick="update()">
					<span>编辑 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system view"
					onclick="del()">
					<span>删除 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					onclick="view()">
					<span>查看 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					 onclick="up()">
					<span>向上 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					 onclick="down()">
					<span>向下 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					 onclick="doExcelNow('基础算量模板明细','detailDataTable','form')">
					<span>导出excel </span>
				</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="detailDataTable"></table>
	</div>
</div>



