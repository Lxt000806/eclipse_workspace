<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
var isCopy=false;
$(function(){
	Global.JqGrid.initJqGrid("detailDataTable",{
			url:"${ctx}/admin/prePlanTempDetail/goJqGrid",
			postData:{no:"${prePlanTemp.no}"},
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: 'Bootstrap', 
			rowNum:10000000,
			height:380,
			searchBtn:true,
			colModel : [
				{name: "pk", index: "pk", width: 94, label: "pk", sortable: true, align: "left", hidden: true},
				{name: "fixareatype", index: "fixareatype", width: 106, label: "区域类型", sortable: true, align: "left",hidden:true},
				{name: "fixareatypedescr", index: "fixareatypedescr", width: 106, label: "区域类型", sortable: true, align: "left"},
				{name: "itemtypedescr", index: "itemtypedescr", width: 124, label: "材料类型说明", sortable: true, align: "left"},
				{name: "algorithm", index: "algorithm", width: 167, label: "算法", sortable: true, align: "left",hidden:true},
				{name: "algorithmdescr", index: "algorithmdescr", width: 167, label: "算法", sortable: true, align: "left"},
				{name: "algorithmper", index: "algorithmper", width: 70, label: "算法系数", sortable: true, align: "right"},
				{name: "algorithmdeduct", index: "algorithmdeduct", width: 80, label: "算法扣减数", sortable: true, align: "right"},
				{name: "isoutset", index: "isoutset", width: 120, label: "是否套餐外材料", sortable: true, align: "left", hidden: true},
				{name: "isoutsetdescr", index: "isoutsetdescr", width: 120, label: "是否套餐外材料", sortable: true, align: "left"},
				{name: "custtypeitempk", index: "custtypeitempk", width: 95, label: "套餐材料pk", sortable: true, align: "left"},
				{name: "itemcode", index: "itemcode", width: 88, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 220, label: "材料", sortable: true, align: "left"},
				{name: "isservice", index: "isservice", width: 120, label: "是否服务性产品", sortable: true, align: "left", hidden: true},
				{name: "isservicedescr", index: "isservicedescr", width: 112, label: "是否服务性产品", sortable: true, align: "left"},
				{name: "qty", index: "qty", width: 98, label: "数量", sortable: true, align: "right", sum: true},
				{name: "markup", index: "markup", width: 112, label: "折扣", sortable: true, align: "right"},
				{name: "processcost", index: "processcost", width: 98, label: "其他费用", sortable: true, align: "right", sum: true},
				{name: "cuttype", index: "cuttype", width: 80, label: "切割类型", sortable: true, align: "left",hidden:true},
				{name: "cuttypedescr", index: "cuttypedescr", width: 80, label: "切割类型", sortable: true, align: "left"},
				{name: "pavetype", index: "pavetype", width: 80, label: "铺贴类型", sortable: true, align: "left",hidden:true },
				{name: "pavetypedescr", index: "pavetypedescr", width: 70, label: "铺贴类型", sortable: true, align: "left" },
				{name: "canmodiqty", index: "canmodiqty", width: 220, label: "数量可修改", sortable: true, align: "left",hidden:true},
				{name: "canmodiqtydescr", index: "canmodiqtydescr", width: 85, label: "数量可修改", sortable: true, align: "left"},
				{name: "remark", index: "remark", width: 208, label: "材料备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 147, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 99, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 76, label: "过期标志", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 94, label: "操作标志", sortable: true, align: "left"},
				{name: "dispseq", index: "dispseq", width: 84, label: "显示顺序", sortable: true, align: "left"},
				{name: "itemexpired", index: "itemexpired", width: 84, label: "材料过期", sortable: true, align: "left",hidden:true},
				{name: "custtypeitemexpired", index: "custtypeitemexpired", width: 84, label: "套餐材料过期", sortable: true, align: "left",hidden:true}
			],
			loadonce:true,
			gridComplete:function (){
				var rowNum = $("#detailDataTable").jqGrid("getGridParam","records");
				if(rowNum>0 &&!isCopy){
					$("#custType").attr("disabled",true);
				}
			    changeColor();
			},
		});
	});
	//新增
	function add(){
		$("#custType").removeAttr("disabled");
		var ids=$("#detailDataTable").jqGrid("getDataIDs");
		var ret = $("#detailDataTable").jqGrid('getRowData',ids[ids.length-1]);
		var dispseq=0;
		var newId=1;
		if(ids.length>0){
			dispseq=parseInt(ret.dispseq,0)+1;
			newId=parseInt(ids[ids.length-1],0)+1;
		} 
		var custType=$("#custType").val();
		if(custType==""){
			art.dialog({content: "请先选择客户类型！",width: 200});
			return false;
		}
		Global.Dialog.showDialog("save", {
			title : "快速预报价模板明细--增加",
			url : "${ctx}/admin/prePlanTempDetail/goAdd?custType="+custType+"&m_umState=A",
		    height:500,
		    width:750,
			returnFun : function(v) {
						var json = {
							fixareatypedescr : v.fixAreaTypeDescr,itemtypedescr : v.itemTypeDescr,algorithmdescr : v.algorithmDescr,
							isoutset : v.isOutSet,custtypeitempk : v.custTypeItemPk,remark : v.remark,
							itemcode : v.itemCode,itemdescr : v.itemDescr,isservicedescr : v.isServiceDescr,
							qty : parseFloat(v.qty,0),markup : parseFloat(v.markup,0),actionlog : "ADD",fixareatype : v.fixAreaType,
							processcost : parseFloat(v.processCost,0),algorithm: v.algorithm,cuttypedescr:v.cutTypeDescr,
							lastupdate : new Date().getTime(),expired : "F",lastupdatedby : "${prePlanTemp.lastUpdatedBy}",
							isservice : v.isService,isoutsetdescr:v.isOutSetDescr,dispseq:dispseq,cuttype:v.cutType,itemexpired:v.itemExpired,
							pavetype:v.paveType,pavetypedescr:v.paveTypeDescr,algorithmper:v.algorithmPer, algorithmdeduct:v.algorithmDeduct,
							canmodiqty : v.canModiQty, canmodiqtydescr : v.canModiQtyDescr,
						};
						$("#detailDataTable").jqGrid("addRowData", newId, json);
						$("#custType").attr("disabled",true);
						$(".copy").hide();
						$("#dataChanged").val("1");
						changeColor();
			}
		});
	}
	//编辑
	function update(){
		$("#custType").removeAttr("disabled");
		var custType=$("#custType").val();
		if(custType==""){
			art.dialog({content: "请先选择客户类型！",width: 200});
			return false;
		}
		$("#custType").attr("disabled",true);
		var id = $("#detailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#detailDataTable").jqGrid('getRowData',id);
	 	if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		} 

		Global.Dialog.showDialog("save", {
			title : "快速预报价模板明细--编辑",
			url : "${ctx}/admin/prePlanTempDetail/goAdd?custType="+custType,
		    height:500,
		    width:750,
		    postData:{
		    		fixAreaTypeDescr : ret.fixareatypedescr,itemTypeDescr : ret.itemtypedescr,algorithmDescr : ret.algorithmdescr,
					isOutSet : ret.isoutset,custTypeItemPk : ret.custtypeitempk,remark : ret.remark,
					itemCode : ret.itemcode,itemDescr : ret.itemdescr,isServiceDescr : ret.isservicedescr,
					qty : ret.qty,markup : ret.markup,fixAreaType : ret.fixareatype,
					processCost : ret.processcost,dispSeq : ret.dispseq,algorithm: ret.algorithm,
					isService : ret.isservice,isOutSetDescr:ret.issetoutdescr,m_umState:"M",cutType:ret.cuttype,itemExpired:ret.itemexpired,
					paveType:ret.pavetype,algorithmPer:ret.algorithmper, algorithmDeduct:ret.algorithmdeduct,
					canModiQty : ret.canmodiqty,
		    },
			returnFun : function(v) {
						var json = {
							fixareatypedescr : v.fixAreaTypeDescr,itemtypedescr : v.itemTypeDescr,algorithmdescr : v.algorithmDescr,
							isoutset : v.isOutSet,custtypeitempk : v.custTypeItemPk,remark : v.remark,cuttypedescr:v.cutTypeDescr,
							itemcode : v.itemCode,itemdescr : v.itemDescr,isservicedescr : v.isServiceDescr,
							qty : parseFloat(v.qty,0),markup : parseFloat(v.markup,0),actionlog : "ADD",fixareatype : v.fixAreaType,
							processcost : parseFloat(v.processCost,0),dispseq : v.dispSeq,algorithm: v.algorithm,
							lastupdate : new Date().getTime(),expired : "F",lastupdatedby : "${prePlanTemp.lastUpdatedBy}",
							isservice : v.isService,isoutsetdescr:v.isOutSetDescr,itemexpired:v.itemExpired,
							pavetype:v.paveType,pavetypedescr:v.paveTypeDescr,cuttype:v.cutType,algorithmper:v.algorithmPer, 
							algorithmdeduct:v.algorithmDeduct,
							canmodiqty : v.canModiQty,canmodiqtydescr : v.canModiQtyDescr,
						};
						console.log(json);
						$("#detailDataTable").setRowData(id, json);
						$("#dataChanged").val("1");
						changeColor();
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
			title : "快速预报价模板明细--查看",
			url : "${ctx}/admin/prePlanTempDetail/goAdd",
		    height:500,
		    width:750,
		    postData:{
		    		fixAreaTypeDescr : ret.fixareatypedescr,itemTypeDescr : ret.itemtypedescr,algorithmDescr : ret.algorithmdescr,
					isOutSet : ret.isoutset,custTypeItemPk : ret.custtypeitempk,remark : ret.remark,
					itemCode : ret.itemcode,itemDescr : ret.itemdescr,isServiceDescr : ret.isservicedescr,
					qty : ret.qty,markup : ret.markup,fixAreaType : ret.fixareatype,
					processCost : ret.processcost,dispSeq : ret.dispseq,algorithm: ret.algorithm,
					isService : ret.isservice,isOutSetDescr:ret.issetoutdescr,m_umState:"V",cutType:ret.cuttype,
					paveType:ret.pavetype,algorithmPer:ret.algorithmper, algorithmDeduct:ret.algorithmdeduct,
					canModiQty : ret.canmodiqty,
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
				$("#dataChanged").val("1");
				if(rowNum==0){
					$("#custType").removeAttr("disabled");
					$(".copy").show();
				}
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
		Global.Dialog.showDialog("save", {
			title : "快速预报价模板明细--复制",
			url : "${ctx}/admin/prePlanTempDetail/goCopy",
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
						$("#dataChanged").val("1");
					}
			}
		});
	}
	//过期材料字体显示为红色
	function changeColor(){
		var ids = $("#detailDataTable").getDataIDs();
			for(var i=0;i<ids.length;i++){
			var rowData = $("#detailDataTable").getRowData(ids[i]);
			console.log(rowData);
			if(rowData.itemexpired=='T'|| rowData.custtypeitemexpired=='T' || rowData.custtypeitemexpired=='t'  ){
			    $('#'+ids[i]).find("td").css("color","red");
			}else{
			    $('#'+ids[i]).find("td").css("color","");
			}
		}
	}
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="form" method="post">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="dataChanged" >
			
			<div class="btn-group-xs">
				<button style="align:left" type="button" class="btn btn-system view" 
					onclick="add()">
					<span>新增 </span>
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
					<button style="align:left"  type="button" class="btn btn-system copy"
						onclick="copy()">
						<span>复制</span>
					</button>
				<button style="align:left" type="button" class="btn btn-system "
					 onclick="doExcelNow('快速预报价模板明细','detailDataTable','dataForm')">
					<span>导出excel</span>
				</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="detailDataTable"></table>
	</div>
	</form>
</div>



