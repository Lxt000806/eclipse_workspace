<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>材料销售分析—按品类</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_brand.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department2.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
	var countWay="";
	var countType="";
	var	countW="";
	var	countT="";
	var itemRight="";
	$(function(){
		itemRight="${USER_CONTEXT_KEY.itemRight}";
		countWay=$.trim($("#countWay").val());
		countType=$.trim($("#countType").val());
	  	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
		if(itemRight.trim().length==2){//只有一个材料类型1时默认选中
			 Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:itemRight.trim(),
								secondSelect:'itemType2',
								secondValue:''
								});	 
		}
		$("#supplCode").openComponent_supplier();
		$("#brandCode").openComponent_brand();
		$("#buyer").openComponent_employee();
		$("#department2").openComponent_department2();
		$("#empCode").openComponent_employee();
		
		var postData = $("#page_form").jsonForm();
		
		var gridOption ={
			postData:postData ,
			height:$(document).height()-$("#content-list").offset().top-102,
			rowNum:1000000,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "code", index: "code", width: 80, label: "客户编号", sortable: true, align: "left",hidden:true},
				{name: "itemtype2", index: "itemtype2", width: 120, label: "材料类型2", sortable: true, align: "left",hidden:true},
				{name: "itemtype2descr", index: "itemtype2descr", width: 120, label: "材料类型2", sortable: true, align: "left"},
				{name: "sqlcode", index: "sqlcode", width: 120, label: "品牌", sortable: true, align: "left",hidden:true},
				{name: "sqldescr", index: "sqldescr", width: 120, label: "品牌", sortable: true, align: "left"},
				{name: "xslx", index: "xslx", width: 100, label: "销售类型", sortable: true, align: "left",count:true},
				{name: "saletypedescr", index: "saletypedescr", width: 100, label: "销售类型", sortable: true, align: "left",count:true},
				{name: "address", index: "address", width: 214, label: "楼盘", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 77, label: "客户类型", sortable: true, align: "left"},
				{name: "softdesigndescr", index: "softdesigndescr", width: 110, label: "软装设计师", sortable: true, align: "left"},
				{name: "dept2descr", index: "dept2descr", width: 120, label: "部门", sortable: true, align: "left"},
				{name: "softposition", index: "softposition", width: 77, label: "职位", sortable: true, align: "left"},
				{name: "againmandescr", index: "againmandescr", width: 77, label: "翻单员", sortable: true, align: "left"},
				{name: "businessmandescr", index: "businessmandescr", width: 77, label: "业务员", sortable: true, align: "left"},
				{name: "businessmandeptdescr", index: "businessmandeptdescr", width: 120, label: "部门", sortable: true, align: "left"},
				{name: "businessmanposition", index: "businessmanposition", width: 77, label: "职位", sortable: true, align: "left"},
				{name: "chgamount", index: "chgamount", width: 73, label: "增减金额", sortable: true, align: "right",sum:true},
				{name: "chgdiscamount", index: "chgdiscamount", width: 80, label: "优惠金额", sortable: true, align: "right",sum:true},
				{name: "chgdisccost", index: "chgdisccost", width: 80, label: "产品优惠", sortable: true, align: "right",sum:true},
				{name: "planamount", index: "planamount", width: 70, label: "预算", sortable: true, align: "right",sum:true},
				{name: "plandisc", index: "plandisc", width: 80, label: "预算优惠", sortable: true, align: "right",sum:true},
				{name: "xse", index: "xse", width: 100, label: "销售额", sortable: true, align: "right", sum: true},
				{name: "cost", index: "cost", width: 100, label: "成本", sortable: true, align: "right", sum: true},
				{name: "mlv", index: "mlv", width: 100, label: "毛利率", sortable: true, align: "right",formatter:DiyFmatter}
			],
			gridComplete:function(){
				var ret = selectDataTableRow();
				var postData = $("#page_form").jsonForm();
				if(ret){
					 $("#itemType2").val(ret.itemtype2);
					  var sumxse=myRound($("#dataTable").getCol('xse',false,'sum'));  
		              var sumcost=myRound($("#dataTable").getCol('cost',false,'sum'));   
		              var summlv=myRound((sumxse-sumcost)*100/sumxse,2);
		              $("#dataTable").footerData('set', {'mlv': summlv});
				}			
	        },
	        ondblClickRow: function(){
	        	view();
	       	}
		};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
		
		function DiyFmatter (cellvalue, options, rowObject){ 
		   	if(cellvalue==null){
		   		return " ";
		   	}else{
			    return cellvalue.toFixed(2)+"%";
		   	}
		}
		//查看明细
		$("#detail").on("click",function(){
			var postData = $("#page_form").jsonForm();
			var ret = selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请先检索出数据",
				});
			}
			if(countW=="0"){
				$("#countType").val(countT);
				$("#saleType").val(ret.xslx);
				$("#itemType2").val($.trim(ret.itemtype2));
				postData.itemType2=$.trim(ret.itemtype2);
				$.ajax({
					url:"${ctx}/admin/rzxscx/getItem1ByItem2",
					type: "get",
					data:{itemType2:$.trim(ret.itemtype2)},
					dataType: "text",
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
				    },
				    success: function(obj){
						postData.itemType1=$.trim(obj);
						Global.Dialog.showDialog("detail",{
							title:"销售明细",
							url:"${ctx}/admin/rzxscx/goDetail",
							postData:postData,
							height:750,
							width:1300,
							returnFun:goto_query
						});
					}
				});
			}else if(countW=="1"){
				postData.itemType1="";
				postData.itemType2="";
				postData.brandCode=$.trim(ret.sqlcode);
				Global.Dialog.showDialog("detail",{
					title:"销售明细",
					url:"${ctx}/admin/rzxscx/goDetail",
					postData:postData,
					height:750,
					width:1300,
					returnFun:goto_query
				});
			}else if(countW="2"){
				postData.itemType2="";
				postData.brandCode="";
				postData.custCode=$.trim(ret.code);
				Global.Dialog.showDialog("detail",{
					title:"销售明细",
					url:"${ctx}/admin/rzxscx/goDetail",
					postData:postData,
					height:750,
					width:1300,
					returnFun:goto_query
				});
			}
		});
		window.goto_query = function(){
			var itemType1=$.trim($("#itemType1").val());
			if("ZC,RZ,JC,CG,JZ"!=$.trim("${itemRight}")){
				if(itemType1==""){
					art.dialog({
						content:"请选择材料类型1",
					});
					return;
				}
			}
			var dateFrom=new Date($.trim($("#dateFrom").val()));
			var dateTo=new Date($.trim($("#dateTo").val()));
			if(dateFrom>dateTo){
				art.dialog({
					content: "开始日期不能大于结束日期",
				});
				return false;
			}
			if($.trim($("#dateFrom").val())==""||$.trim($("#dateTo").val())==""){
				art.dialog({
					content: "时间查询条件不能为空",
				});
				return false;
			}	
			$("#dataTable").jqGrid("setGridParam",{datatype:"json",postData:$("#page_form").jsonForm(),page:1,url:"${ctx}/admin/rzxscx/goJqGrid",sortname:""}).trigger("reloadGrid");
			$.ajax({
				url:"${ctx}/admin/rzxscx/getDiscounts",
				type: "get",
				data:$("#page_form").jsonForm(),
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
					$("#chgYH").val(obj[0]);
					$("#planYH").val(obj[2]);
					$("#saleYH").val(obj[1]);
				}
			});
			
			countW=countWay;
			countT=countType;
			if(countType=="0"&&countWay=="0"){
				jQuery("#dataTable").setGridParam().hideCol("code").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("itemtype2descr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("sqldescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("xslx").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("address").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("softdesigndescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("againmandescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("businessmandescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("dept2descr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("chgamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("chgdiscamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("chgdisccost").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("planamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("plandisc").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("xse").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("cost").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("mlv").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("custtypedescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("businessmanposition").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("businessmandeptdescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("softposition").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("saletypedescr").trigger("reloadGrid");
				if("${costRight}"!="1"){
					jQuery("#dataTable").setGridParam().hideCol("cost").trigger("reloadGrid");
					jQuery("#dataTable").setGridParam().hideCol("mlv").trigger("reloadGrid");
				}
			}else if(countType=="0"&&countWay=="1"){
				jQuery("#dataTable").setGridParam().hideCol("code").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("itemtype2descr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("sqldescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("xslx").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("address").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("softdesigndescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("againmandescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("businessmandescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("dept2descr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("chgamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("chgdiscamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("chgdisccost").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("planamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("plandisc").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("xse").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("cost").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("mlv").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("custtypedescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("businessmanposition").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("businessmandeptdescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("softposition").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("saletypedescr").trigger("reloadGrid");
				if("${costRight}"!="1"){
					jQuery("#dataTable").setGridParam().hideCol("cost").trigger("reloadGrid");
					jQuery("#dataTable").setGridParam().hideCol("mlv").trigger("reloadGrid");
				}
			}else if(countType=="0"&&countWay=="2"){
				jQuery("#dataTable").setGridParam().showCol("code").trigger("reloadGrid");//显示客户编号列 --zb
				jQuery("#dataTable").setGridParam().hideCol("itemtype2descr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("sqldescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("xslx").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("address").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("softdesigndescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("againmandescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("businessmandescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("dept2descr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("chgamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("chgdiscamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("chgdisccost").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("planamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("plandisc").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("xse").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("cost").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("mlv").trigger("reloadGrid");//chgdisccost chgdiscamount plandisc
				jQuery("#dataTable").setGridParam().showCol("custtypedescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("businessmanposition").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("businessmandeptdescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("softposition").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("saletypedescr").trigger("reloadGrid");
				if("${costRight}"!="1"){
					jQuery("#dataTable").setGridParam().hideCol("cost").trigger("reloadGrid");
					jQuery("#dataTable").setGridParam().hideCol("mlv").trigger("reloadGrid");
				}
			}else if(countType=="1"&&countWay=="0"){
				jQuery("#dataTable").setGridParam().hideCol("code").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("itemtype2descr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("sqldescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("xslx").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("address").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("softdesigndescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("againmandescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("businessmandescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("dept2descr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("chgamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("chgdiscamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("chgdisccost").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("planamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("plandisc").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("xse").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("cost").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("mlv").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("custtypedescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("businessmanposition").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("businessmandeptdescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("softposition").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("saletypedescr").trigger("reloadGrid");
			}else if(countType=="1"&&countWay=="1"){
				jQuery("#dataTable").setGridParam().hideCol("code").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("itemtype2descr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("sqldescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("xslx").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("address").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("softdesigndescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("againmandescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("businessmandescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("dept2descr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("chgamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("chgdiscamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("chgdisccost").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("planamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("plandisc").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("xse").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("cost").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("mlv").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("custtypedescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("businessmanposition").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("businessmandeptdescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("softposition").trigger("reloadGrid");	
				jQuery("#dataTable").setGridParam().hiedCol("saletypedescr").trigger("reloadGrid");

				if("${costRight}"!="1"){
					jQuery("#dataTable").setGridParam().hideCol("cost").trigger("reloadGrid");
					jQuery("#dataTable").setGridParam().hideCol("mlv").trigger("reloadGrid");
				}
			}else if(countType=="1"&&countWay=="2"){
				jQuery("#dataTable").setGridParam().showCol("businessmanposition").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("businessmandeptdescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("softposition").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("custtypedescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("code").trigger("reloadGrid");//显示客户编号列 --zb
				jQuery("#dataTable").setGridParam().hideCol("itemtype2descr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("sqldescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("xslx").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("address").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("softdesigndescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("againmandescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("businessmandescr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("dept2descr").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("chgamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("chgdiscamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("chgdisccost").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("planamount").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("plandisc").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("xse").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("cost").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().hideCol("mlv").trigger("reloadGrid");
				jQuery("#dataTable").setGridParam().showCol("saletypedescr").trigger("reloadGrid");
				if("${costRight}"!="1"){
					jQuery("#dataTable").setGridParam().hideCol("cost").trigger("reloadGrid");
					jQuery("#dataTable").setGridParam().hideCol("mlv").trigger("reloadGrid");
				}
			}
		}
	});
	function view(){
		var postData = $("#page_form").jsonForm();
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请先检索出数据",
			});
		}
		if(countW=="0"){
			$("#countType").val(countT);
			$("#saleType").val(ret.xslx);
			$("#itemType2").val($.trim(ret.itemtype2));
			postData.itemType2=$.trim(ret.itemtype2);
			$.ajax({
				url:"${ctx}/admin/rzxscx/getItem1ByItem2",
				type: "get",
				data:{itemType2:$.trim(ret.itemtype2)},
				dataType: "text",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
					postData.itemType1=$.trim(obj);
					Global.Dialog.showDialog("detail",{
						title:"销售明细",
						url:"${ctx}/admin/rzxscx/goDetail",
						postData:postData,
						height:750,
						width:1300,
						returnFun:goto_query
					});
				}
			});
		}else if(countW=="1"){
			postData.itemType1="";
			postData.itemType2="";
			postData.brandCode=$.trim(ret.sqlcode);
			Global.Dialog.showDialog("detail",{
				title:"销售明细",
				url:"${ctx}/admin/rzxscx/goDetail",
				postData:postData,
				height:750,
				width:1300,
				returnFun:goto_query
			});
		}else if(countW="2"){
			postData.itemType2="";
			postData.brandCode="";
			postData.custCode=$.trim(ret.code);
			Global.Dialog.showDialog("detail",{
				title:"销售明细",
				url:"${ctx}/admin/rzxscx/goDetail",
				postData:postData,
				height:700,
				width:1300,
				returnFun:goto_query
			});
		}
	}
	function doExcel(){
		var url = "${ctx}/admin/rzxscx/doExcel";
		doExcelAll(url);
	}
	function changeWay(){
		countWay=$.trim($("#countWay").val());
	} 
	
	function changeType(){
		countType=$.trim($("#countType").val());
	} 
	function containCmp(obj){
		if ($(obj).is(":checked")){
			$("#containCmpCust").val("1");
		}else{
			$("#containCmpCust").val("");
		}
	}
	function containPlan(obj){
		if ($(obj).is(":checked")){
			$("#notContainPlan").val("1");
		}else{
			$("#notContainPlan").val("");
		}
	}
	function isExpired(obj){
		if ($(obj).is(":checked")){
			$("#expired").val("1");
		}else{
			$("#expired").val("");
		}
	}
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#openComponent_supplier_supplCode").val('');
		$("#openComponent_brand_brandCode").val('');
		$("#openComponent_employee_buyer").val('');
		$("#openComponent_department2_department2").val('');
		$("#page_form select").val('');
		$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
	}
	</script>	
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" id="itemType2" name="itemType2" value="${itemChg.itemType2 }"/>
					<input type="hidden" id="code" name="code"/>
					<input type="hidden" id="saleType" name="saleType"/>
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<li class="form-validate">
							<label>材料类型1</label>
							<select id="itemType1" name="itemType1" style="width:160px" style="width:166px;"></select>
						</li>
						<li>
							<label>客户类型</label>
							<house:custTypeMulit id="custType"  selectedValue="${isAddCustType }"></house:custTypeMulit>
						</li>
						<li>	
							<label>统计时间从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemChg.dateFrom}' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemChg.dateTo}' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>供应商</label>
							<input type="text" id="supplCode" name="supplCode" style="width:160px;"/>
						</li>
						<li>
							<label>品牌</label>
							<input type="text" id="brandCode" name="brandCode" style="width:160px;"/>
						</li>
						<li>
							<label>归属买手</label>
							<input type="text" id="buyer" name="buyer" style="width:160px;"/>
						</li>
						<li>
							<label>归属部门</label>
							<input type="text" id="department2" name="department2" style="width:160px;" value="${itemChe.department2 }"/>
						</li>
						<li>
							<label>统计方式</label>
							<select id="countWay" name="countWay" onChange="changeWay()" style="width:160px;">
								<option value="0">0 按材料类型2汇总</option>
								<option value="1">1 按品牌汇总</option>
								<option value="2">2 按楼盘汇总</option>
							</select>
						</li>
						<li>
							<label>统计类型</label>
							<select id="countType" name="countType" onChange="changeType()" style="width:160px;">
								<option value="0">0 按增减预算统计</option>
								<option value="1">1 按结算时间统计需求</option>
							</select>
						</li>
						<li>
							<label>角色</label>
							<select name="role">
								<option value="">请选择角色</option>
							    <option value="01">01 业务员</option>
							    <option value="24">24 翻单员</option>
							    <option value="50">50 软装设计师</option>
							</select>
						</li>
						<li>
							<label>干系人</label>
							<input type="text" id="empCode" name="empCode" style="width:160px;"/>
						</li>
						<li>
							<label>发货类型</label>
							<house:xtdm id="sendType" dictCode="ITEMAPPSENDTYPE"/>
						</li>
						<li>
							<label style="width:140px;vertical-align:middle;margin-left:-130px;"></label>
							<input type="checkbox" id="containCmpCust" name="containCmpCust" value=" " onclick="containCmp(this)"/>包含公司内部&nbsp;
						</li>
						<li>	
							<label style="width:120px;vertical-align:middle;margin-left:-110px;"></label>
							<input type="checkbox" id="notContainPlan" name="notContainPlan" value=" " onclick="containPlan(this)"/>不包含预估&nbsp;
						</li>
						
						<li>
							<label>套餐材料</label>
							<select id="isOutSet" name="isOutSet"  style="width:160px;">
								<option value=" "> 请选择</option>
								<option value="0">是</option>
								<option value="1">否</option>
							</select>
						</li>
						<li>
							<label style="width:120px;vertical-align:middle;margin-left:-100px;"></label>
							<input type="checkbox" id="expired" name="expired" value="1" onclick="isExpired(this)" ${itemChg!='1'?'checked':''}/>隐藏过期&nbsp; 
						</li>
						<li class="search-group" style="vertical-align:middle;margin-left:-40px;">
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="btn-panel">
	  		<div class="btn-group-sm">
	  			<house:authorize authCode="RZXSCX_DETAIL">
					<button type="button" class="btn btn-system" id="detail">
						<span>查看</span>
					</button>
 			  	</house:authorize>
 			  	<house:authorize authCode="RZXSCX_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">
						<span>导出excel</span>
					</button>
				</house:authorize>
	      		&nbsp&nbsp&nbsp&nbsp&nbsp
	      		<span>增减优惠：</span>
				<input type="text" id="chgYH" name="chgYH" style="width:100px; outline:none; background:transparent; 
					border:none;margin-left:-10px;" disabled="true"/>
	      		<span>预算优惠：</span>
				<input type="text" id="planYH" name="planYH" style="width:100px; outline:none; background:transparent; 
					border:none;margin-left:-5px;" disabled="true"/>
	      		<span>独立销售优惠：</span>
				<input type="text" id="saleYH" name="saleYH" style="width:100px; outline:none; background:transparent; 
					border:none;margin-left:-10px;" disabled="true"/>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
		</div> 
	</body>	
</html>
