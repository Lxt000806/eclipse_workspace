<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>工地验收</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

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
	var lastCellRowId;
    //初始化送货申请明细
	Global.JqGrid.initJqGrid("dataTableAll",{
		url:"${ctx}/admin/itemPlan/goCustGiftAllJqGrid",
		postData:{custCode:"${custCode }"},
		height:485,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "itemType1", index: "itemType1", width: 75, label: "优惠分类", sortable: true, align: "left",hidden:true},
			{name: "descr", index: "descr", width: 70, label: "优惠分类", sortable: true, align: "left"},
			{name: "isservice", index: "isservice", width: 100, label: "服务性产品", sortable: true, align: "left",formatter:diyMatter,hidden:true},
			{name: "iscupboard", index: "iscupboard", width: 80, label: "橱柜", sortable: true, align: "left",formatter:diyMatter,hidden:true},
			{name: "zsdpyh", index: "zsdpyh", width: 90, label: "赠送单品优惠", sortable: true, align: "right"},
			{name: "bjdpyh", index: "bjdpyh", width: 90, label: "报价单品优惠", sortable: true, align: "right"},
			{name: "zszeyh", index: "zszeyh", width: 90, label: "赠送总额优惠", sortable: true, align: "right"},
			{name: "bjzeyh", index: "bjzeyh", width: 90, label: "报价总额优惠", sortable: true, align: "right"},
			{name: "zskzsjyh", index: "zskzsjyh", width: 110, label: "使用优惠额度", sortable: true, align: "right"},
			{name: "zskzzgyh", index: "zskzzgyh", width: 110, label: "最高优惠额度", sortable: true, align: "right"},
			{name: "sjcyh", index: "sjcyh", width: 85, label: "优惠额度超支", sortable: true, align: "left"},
			{name: "dpcyh", index: "dpcyh", width: 90, label: "单品优惠异常", sortable: true, align: "left"},
			{name: "zecyh", index: "zecyh", width: 85, label: "总额超优惠", sortable: true, align: "left",hidden:true},
			{name: "yjkje", index: "yjkje", width: 85, label: "业绩扣减额", sortable: true, align: "right"},
			{name: "directordiscamount", index: "directordiscamount", width: 85, label: "总监最高优惠金额", sortable: true, align: "right",hidden:true},
		], 
		gridComplete:function(){
			var detailJson = Global.JqGrid.allToJson("dataTableAll");
			
			var totolRowNum = 7; //合计行号
			$.each(detailJson.datas,function(k,v){
			 	if(v.descr == "合计"){
			 		totolRowNum = k;
			 		return false;
			 	} 	
		  	});
			
			$.each(detailJson.datas,function(k,v){
			 	if(v.descr != "合计" && v.descr != "基础协议优惠" && (Math.abs(v.zsdpyh-v.bjdpyh)>1 || (v.zskzsjyh-v.zskzzgyh>0 && $.trim(v.zskzzgyh) != "" ))){
			 		$("#dataTableAll").find("#"+myRound(k+1)).addClass("redColor");
			 	} 
			 	if(k == totolRowNum){
			 		$("#zskzsjyh").val($.trim(v.zskzsjyh))
			 		zskzsjyh=v.zskzsjyh;
			 	}
				if(k== totolRowNum + 1){
					$("#jcxyyh").val($.trim(v.zsdpyh));
					$("#swzsyjkj").val($.trim(v.zszeyh));
					$("#rzqyjkj").val($.trim(v.zskzsjyh));
					$("#designerMaxDiscAmount").val($.trim(v.zskzzgyh));
					$("#directorDiscAmount").val($.trim(v.directordiscamount));
					$("#dataTableAll").setRowData(totolRowNum + 2 , null, {
						display : 'none'
					});
				} 
		  	});
			var zecyh=0;
			//总额超优惠=(最高可优惠金额+总监优惠金额+设计师风控基金)-优惠额度使用。
			zecyh=myRound(parseFloat($("#designerMaxDiscAmount").val()==""?0:$("#designerMaxDiscAmount").val())
			      +parseFloat($("#directorDiscAmount").val()==""?0:$("#directorDiscAmount").val())
			      +parseFloat($("#designRiskFund").val()==""?0:$("#designRiskFund").val())
			      -parseFloat($("#zskzsjyh").val()==""?0:$("#zskzsjyh").val()),2);
			$("#zecyh").val(zecyh);
			if(zecyh<0){
				$("#zecyh").addClass("redColor");
				$("#zecyhLi").addClass("redColor");
			}
		},
	});
	function diyMatter (cellvalue, options, rowObject){ 
		var returnValue= "";
		if(cellvalue=="1"){
			returnValue ="是";
		}
		if(cellvalue=="0"){
			returnValue ="否";
		}
	    return returnValue;
	}
});
</script>
</head>
	<body>
		<div class="panel-body">
			<div class="query-form">
				<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>基础协议优惠</label>
							<input type="text" id="jcxyyh" name="jcxyyh" style="width:160px;"/>
						</li>
						<li>
							<label>实物赠送业绩扣减</label>
							<input type="text" id="swzsyjkj" name="swzsyjkj" style="width:160px;" readonly="readonly"/>
						</li>
						<li>
							<label>软装券业绩扣减</label>
							<input type="text" id="rzqyjkj" name="rzqyjkj" style="width:160px;" readonly="readonly"/>
						</li>
						<li>
							<label>使用优惠额度</label>
							<input type="text" id="zskzsjyh" name="zskzsjyh" style="width:160px;" readonly="readonly"/>
						</li>
						<li>
							<label>最高优惠额度</label>
							<input type="text" id="designerMaxDiscAmount" name="designerMaxDiscAmount" style="width:160px;" readonly="readonly"/>
						</li>
						<li id='zecyhLi'>
							<label>剩余优惠额度</label>
							<input type="text" id="zecyh" name="zecyh" style="width:160px;" readonly="readonly"/>
						</li>
						<li>
							<label>总监最高优惠金额</label>
							<input type="text" id="directorDiscAmount" name="directorDiscAmount" style="width:160px;" readonly="readonly"/>
						</li>
						<li>
							<label>风控基金</label>
							<input type="text" id="designRiskFund" name="designRiskFund" value="${custType.designRiskFund}" readonly="readonly"/>
						</li>
						<li>
							<label>单品超优惠</label>
							<input type="text" id="singleOverDisAmount" name="singleOverDisAmount" style="width:160px;" readonly="readonly"/>
						</li>
					</ul>
				</form>
			</div>
		</div>
		
		<div class="pageContent" style="border-top:1.5px solid rgb(236,236,236)">
			<div id="content-list" >
				<table id="dataTableAll" ></table>
			</div>
		</div>
	</body>	
</html>
