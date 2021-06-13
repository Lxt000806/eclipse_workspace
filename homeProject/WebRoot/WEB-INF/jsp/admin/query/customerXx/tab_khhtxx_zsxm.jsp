<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>赠送项目</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%-- <%@ include file="/commons/jsp/common.jsp" %> --%>

	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		.redColor{
			color:red!important;
		}
	</style>
<script type="text/javascript"> 
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","discAmtType");
	var lastCellRowId;
	/**初始化表格*/
	Global.JqGrid.initJqGrid("dataTable",{
		height:270,
		url:"${ctx}/admin/itemPlan/goCustGiftJqGrid",
		postData:{custCode:"${customer.code}",hasGiftAppDetail:"1"},
		styleUI: "Bootstrap", 
		colModel : [
			{name: "pk", index: "pk", width: 90, label: "pk", sortable: true, align: "left",hidden:true},
			{name: "giftpk", index: "giftpk", width: 90, label: "赠送项目PK", sortable: true, align: "left",hidden:true},
			{name: "custdescr", index: "custdescr", width: 90, label: "客户名称", sortable: true, align: "left",hidden:true},
			{name: "custcode", index: "custcode", width: 90, label: "客户编号", sortable: true, align: "left",hidden:true},
			{name: "typedescr", index: "typedescr", width: 90, label: "优惠类型", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 150, label: "优惠项目标题", sortable: true, align: "left"},
			{name: "discamttypedescr", index: "discamttypedescr", width: 90, label: "优惠金额分类", sortable: true, align: "left"},
			{name: "type", index: "type", width: 90, label: "类型", sortable: true, align: "left",hidden:true},
			{name: "discamount", index: "discamount", width: 90, label: "实际优惠额", sortable: true, align: "right",},
			{name: "bjyhje", index: "bjyhje", width: 90, label: "单品报价优惠金额", sortable: true, align: "right",},
			{name: "maxdiscamt", index: "maxdiscamt", width: 90, label: "最高可优惠金额", sortable: true, align: "right",},
			{name: "isoutdisc", index: "isoutdisc", width: 90, label: "是否超优惠", sortable: true, align: "left",},
			{name: "perfdiscamount", index: "perfdiscamount", width: 90, label: "业绩扣减金额", sortable: true, align: "right"},
			{name: "issofttokendescr", index: "issofttokendescr", width: 90, label: "是否软装券", sortable: true, align: "left"},
			{name: "zssamebj", index: "zssamebj", width: 120, label: "赠送与报价一致", sortable: true, align: "left"},
			{name: "disctypedescr", index: "disctypedescr", width: 90, label: "优惠折扣类型", sortable: true, align: "left"},
			{name: "calcdiscctrlper", index: "calcdiscctrlper", width: 90, label: "计入优惠额度控制", sortable: true, align: "left"},
			{name: "giftremarks", index: "giftremarks", width: 190, label: "优惠说明", sortable: true, align: "left"},
			{name: "discconfirmremarks", index: "discconfirmremarks", width: 190, label: "优惠审批说明", sortable: true, align: "left"},
			{name: "issofttoken", index: "issofttoken", width: 90, label: "是否软装券", sortable: true, align: "left",hidden:true},
			{name: "quotemodule", index: "quotemodule", width: 90, label: "报价模块", sortable: true, align: "left",hidden:true},
			{name: "quotemoduledescr", index: "quotemoduledescr", width: 90, label: "报价模块", sortable: true, align: "left",hidden:true},
			{name: "saleamount", index: "saleamount", width: 90, label: "销售额", sortable: true, align: "left",hidden:true},
			{name: "totalcost", index: "totalcost", width: 90, label: "成本额", sortable: true, align: "left",hidden:true},
			{name: "discamttype", index: "discamttype", width: 90, label: "优惠金额分类", sortable: true, align: "left",hidden:true},
			{name: "perfdisctype", index: "perfdisctype", width: 90, label: "业绩折扣类型", sortable: true, align: "left",hidden:true},
			{name: "perfdisctypedescr", index: "perfdisctypedescr", width: 90, label: "业绩折扣类型", sortable: true, align: "left",hidden:true},
			{name: "perfdiscper", index: "perfdiscper", width: 90, label: "业绩折扣比例", sortable: true, align: "left",hidden:true},
			{name: "dispseq", index: "dispseq", width: 90, label: "序号", sortable: true, align: "left",hidden:true},
			{name: "lastupdate", index: "lastupdate", width: 90, label: "最后修改时间", sortable: true, align: "left",formatter:formatDate},
			{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "最后修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 90, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 90, label: "操作日志", sortable: true, align: "left"},
			{name: "disctype", index: "disctype", width: 90, label: "赠送类型", sortable: true, align: "left",hidden:true},
			{name: "area", index: "area", width: 90, label: "面积", sortable: true, align: "left",hidden:true},
			{name: "wallarea", index: "wallarea", width: 90, label: "砌墙面积", sortable: true, align: "left",hidden:true},
			{name: "custtype", index: "custtype", width: 90, label: "客户类型", sortable: true, align: "left",hidden:true},
			{name: "maxdiscamount", index: "maxdiscamount", width: 90, label: "分段控制金额", sortable: true, align: "left",hidden:true},
			{name: "maxdiscamtexpr", index: "maxdiscamtexpr", width: 290, label: "最高金额公式", sortable: true, align: "left",hidden:true},
			{name: "basefeedirct", index: "basefeedirct", width: 90, label: "基础直接费", sortable: true, align: "left",hidden:true},
			{name: "dispseq", index: "dispseq", width: 90, label: "显示顺序", sortable: true, align: "left"},
		], 
		gridComplete:function(){
			Global.JqGrid.jqGridSelectAll("dataTable",false);
			var detailJson = Global.JqGrid.allToJson("dataTable");
			$.each(detailJson.datas,function(k,v){
		 		var expr = $.trim(v.maxdiscamtexpr);
				expr = expr.replace(/@MainSegDiscAmount@/g,v.maxdiscamount); 
				expr = expr.replace(/@SoftSegDiscAmount@/g,v.maxdiscamount); 
				expr = expr.replace(/@BaseSegDiscAmount@/g,v.maxdiscamount); 
				expr = expr.replace(/@Area@/g,v.area);
				expr = expr.replace(/@MainSetFee@/g, "${customer.mainSetFee}");
				expr = expr.replace(/@WallArea@/g, v.wallarea)
				
				$("#dataTable").jqGrid('setCell',k+1,"maxdiscamt",eval(expr));			 		
			 	if(v.disctype == "1" && Math.abs(myRound(v.bjyhje,4)-myRound(v.discamount,4))>1){
			 		$("#dataTable").find("#"+myRound(k+1)).addClass("redColor");//是否一致
					$("#dataTable").jqGrid('setCell',k+1,"zssamebj","否");			 		
			 	} else{
					$("#dataTable").jqGrid('setCell',k+1,"zssamebj","是");			 		
			 	}
			 	if(v.discamount-eval(expr)>0 && eval(expr)!=""){
			 		$("#dataTable").find("#"+myRound(k+1)).addClass("redColor");
					$("#dataTable").jqGrid('setCell',k+1,"isoutdisc","是");			 		
			 	}else{
					$("#dataTable").jqGrid('setCell',k+1,"isoutdisc","否");			 		
			 	}
		  	});
		},
	});

	$("#save").on("click",function(){
		var ret = selectDataTableRow();
		var pk = 0;
		if(ret){
			pk= ret.giftpk;
		}
		Global.Dialog.showDialog("save",{ 
			title:"赠品项目——新增",
			url:"${ctx}/admin/itemPlan/goCustGiftSave",
			postData:{giftPK:pk,custCode:"${customer.code }" },
			height: 650,
			width:1250,
			returnFun:goto_query
        });
	});
	
	$("#update").on("click",function(){
		var ret = selectDataTableRow();
		if (ret) {	
			Global.Dialog.showDialog("update",{ 
				title:"赠品项目——编辑",
				url:"${ctx}/admin/itemPlan/goCustGiftUpdate",
				postData:{pK:ret.pk,custCode:"${customer.code }" },
				height: 650,
				width:1250,
				returnFun:goto_query
            });
		} else {
			art.dialog({
				content: "请选择一条记录"
			});
		}
	});
	
	$("#view").on("click",function(){
		var ret = selectDataTableRow();
		if (ret) {	
			Global.Dialog.showDialog("view",{ 
				title:"赠品项目——查看",
				url:"${ctx}/admin/itemPlan/goCustGiftView",
				postData:{pK:ret.pk,custCode:"${customer.code}" },
				height: 650,
				width:1250,
				returnFun:goto_query
            });
		} else {
			art.dialog({
				content: "请选择一条记录"
			});
		}
	});
	
	$("#del").on("click",function(){
		var ret = selectDataTableRow();
		art.dialog({
			content:"是否删除",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/itemPlan/doCustGiftDel",
					type:"post",
					data:{pK:ret.pk},
					dataType:"json",
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
					},
					success:function(obj){
						art.dialog({
							content:"删除成功",
							time:500,
						});
						$("#dataTableAll").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goCustGiftAllJqGrid",postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
						$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
					}
				});
			},
			cancel: function () {
					return true;
			}
		});	
	});
	
	$("#import").on("click",function(){
		art.dialog({
			content:"此操作将从预算导入该客户的赠送项目，是否继续？",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/itemPlan/doImportCustGift",
					type:"post",
					data:{custCode:"${customer.code}"},
					dataType:"json",
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
					},
					success:function(obj){
						if(obj.rs){
							art.dialog({
								content:"导入成功",
								time:500,
							});
							$("#dataTableAll").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goCustGiftAllJqGrid",postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
							$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
						}else{
							art.dialog({
								content:"导入失败",
								time:500,
								
							});
							return;
						}
					}
				});
			},
			cancel: function () {
					return true;
			}
		});
	});
	
	window.goto_query = function(){
		$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,url:"${ctx}/admin/itemPlan/goCustGiftJqGrid"}).trigger("reloadGrid");
		$("#dataTableAll").jqGrid("setGridParam",{url:"${ctx}/admin/itemPlan/goCustGiftAllJqGrid",postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
	}
	
	$("#upward").on("click",function(){
		var ret = selectDataTableRow("dataTable");
		if(!ret){
			art.dialog({
				content:"请选择一条明细",
			});
			return;
		}
		var rowIds =$("#dataTable").jqGrid('getDataIDs');
		var rowId=$("#dataTable").jqGrid("getGridParam","selrow");
		if(rowId =="1"){
			return;
		}
		$("#upward").attr("disabled",true);
		var thisRow =$("#dataTable").jqGrid("getRowData",rowId);
		var targeRow =$("#dataTable").jqGrid("getRowData",parseInt(rowId)-1);

		$.ajax({
			url:"${ctx}/admin/itemPlan/doCustGiftUpward",
			type: "post",
			data: {pK:ret.pk,custCode:"${customer.code}"},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if(obj.rs){
					var ids = $("#dataTable").jqGrid("getDataIDs");  
					thisRow.dispseq=targeRow.dispseq;
					targeRow.dispseq=ret.dispreq;
					$("#dataTable").setRowData(rowId,targeRow);
					$("#dataTable").setRowData(parseInt(rowId)-1,thisRow);
					for(var i=0;i<=ids.length;i++){
						$('#'+ids[i]).find("td").removeClass("SelectBG");
					}
					Global.JqGrid.jqGridSelectAll("dataTable",false);
					$("#dataTable").setSelection(parseInt(rowId)-1);
		    	}
				$("#upward").removeAttr("disabled",true);
		    }
		});
		
	});
		
	$("#downward").on("click",function(){
		var ret = selectDataTableRow("dataTable");
		if(!ret){
			art.dialog({
				content:"请选择一条明细",
			});
			return;
		}
		var rowIds =$("#dataTable").jqGrid('getDataIDs');
		var rowId=$("#dataTable").jqGrid("getGridParam","selrow");
		if(rowId>=rowIds.length){
			return;
		}
		$("#downward").attr("disabled",true);
		var thisRow =$("#dataTable").jqGrid("getRowData",rowId);
		var targeRow =$("#dataTable").jqGrid("getRowData",parseInt(rowId)+1);
		$.ajax({
			url:"${ctx}/admin/itemPlan/doCustGiftDownward",
			type: "post",
			data: {pK:ret.pk,custCode:"${customer.code}"},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if(obj.rs){
	    			var ids = $("#dataTable").jqGrid("getDataIDs");  
		    		thisRow.dispseq=targeRow.dispseq;
					targeRow.dispseq=ret.dispreq;
					$("#dataTable").setRowData(rowId,targeRow);
					$("#dataTable").setRowData(parseInt(rowId)+1,thisRow);
					for(var i=0;i<=ids.length;i++){
						$('#'+ids[i]).find("td").removeClass("SelectBG");
					}
					Global.JqGrid.jqGridSelectAll("dataTable",false);
					$("#dataTable").setSelection(parseInt(rowId)+1);
		    	}
				$("#downward").removeAttr("disabled",true);
		    }
		});
	});
		
});
</script>
</head>
	<body>
		<div class="panel-body">
			<div class="query-form">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<input type="hidden" name="custCode" id="custCode" value="${customer.code }"/>
					<input type="hidden" name="HasGiftAppDetail" id="HasGiftAppDetail" value="1"/>
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>优惠金额分类</label>
							<select type="text" id="discAmtType" name="discAmtType" style="width:160px;"></select>
						</li>
						<li>
							<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
						</li>
						<li>
						    <button type="button" class="btn btn-sm btn-system" id="view" >查看</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="pageContent">
			<div id="content-list" >
				<table id="dataTable" ></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body" style="">
				<form action="" method="post" id="dataForm" class="form-search">
					<ul class="ul-form">
						<li class="form-validate">
							<label class="control-textarea" >补充条款</label>
							<textarea id="repClause" rows="5" name="repClause" 
								style="margin: 0px; height: 80px; width: 900px;" readonly>${customer.repClause}&#10;${customer.oldRepClause }</textarea>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</body>	
</html>
