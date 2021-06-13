<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
	<title>材料发货分析-按品类查询</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_brand.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department2.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	/* 全局设置现在表格显示数据为哪种分组条件 */
	var groupTypeNow = $("#groupType").val();
	/* 相差天数 */
	var differDate;
    /**初始化表格*/
    $(function () {
      	//初始化材料类型1，2，3三级联动
    	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "itemType1", "itemType2", "itemType3");
    	
		Global.JqGrid.initJqGrid("dataTable", {
	        height: $(document).height() - $("#content-list").offset().top - 70,
	        styleUI: "Bootstrap",
	        rowNum:1000000,
			colModel : [
			  	{name: "code", index: "code", width: 75, label: "编号", sortable: true, align: "left"},
			  	{name: "descr", index: "descr", width: 120, label: "名称", sortable: true, align: "left"},
			  	{name: "saleqty", index: "saleqty", width: 70, label: "销售量", sortable: true, align: "right",sum:true},
			  	{name: "saleamount", index: "saleamount", width: 70, label: "销售额", sortable: true, align: "right", sum: true},
			  	{name: "purchamount", index: "purchamount", width: 70, label: "结算", sortable: true, align: "right", sum: true},
			  	{name: "profit", index: "profit", width: 80, label: "毛利额", sortable: true, align: "right", sum: true},
			  	{name: "profitpercent", index: "profitpercent", width: 85, label: "毛利率(%)", sortable: true, align: "right", 
			  		formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2}, sum: true},
			  	{name: "saleshare", index: "saleshare", width: 90, label: "销售占比(%)", sortable: true, align: "right",
			  		formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2}},
			  	{name: "profitshare", index: "profitshare", width: 90, label: "利润占比(%)", sortable: true, align: "right",
			  		formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2}},
			  	{name: "area", index: "area", width: 70, label: "总面积", sortable: true, align: "right"},
			  	{name: "unitprofit", index: "unitprofit", width: 120, label: "单位平方利润额", sortable: true, align: "right",
			  		formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2}},
			  	{name: "unitsale", index: "unitsale", width: 120, label: "单位平方销售额", sortable: true, align: "right",
			  		formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2}},//formatter:formatterNumber
			  	{name: "unitpurch", index: "unitpurch", width: 120, label: "单位平方结算额", sortable: true, align: "right",
			  		formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2}},
			  	{name: "qtycal", index:"qtycal", width: 70, label: "订货量", sortable: true, align: "right", sum:true},
			  	{name: "arriveamount", index: "arriveamount", width: 70, label: "订货额", sortable: true, align: "right"}
			],
			ondblClickRow: function(){/* 双击触发事件 */
				view();
			}
		});
		
		$("#sendCzy").openComponent_employee();
		$("#sqlCode").openComponent_brand();
		$("#supplCode").openComponent_supplier();
		$("#itemCode").openComponent_item();
		$("#department2").openComponent_department2();
		
		//材料类型1只显示主材，软装
		$("#itemType1 option").each(function(){
			var value=$(this).val();
			if(value!="ZC" && value!="RZ"){
				$(this).remove();
			}
		});
		$("#itemType1").searchableSelect();
		
    });
    
    //计算毛利率平均值
    function calGrossProfitMargin() {
    	var profit = $("#dataTable").getCol("profit",false,"sum");
		var saleamount = $("#dataTable").getCol("saleamount",false,"sum");
		var profitpercent = saleamount==0?0:myRound(profit/saleamount*100, 2);
		$("#dataTable").footerData("set", {"profitpercent": profitpercent});
    }

    function doExcel() {
	   /*  var url = "${ctx}/admin/itemSendAnalyse/doExcel";
	    doExcelAll(url); */
	    doExcelNow("材料发货分析-按品类","dataTable","page_form");
    }
    
    /* 根据分组条件不同，对colModel进行设置 */
    function goto_query() {
    	var postData = $("#page_form").jsonForm();
    	//判断开始时间和结束时间
        var strStartTime = $("#sendDateFrom").val();
        var endTime = $("#sendDateTo").val();
 
        if (strStartTime == "" || endTime == "") {
            differDate = 0;
        }
        else {
            var startNum = parseInt(strStartTime.replace(/-/g, ""), 10);
            var endNum = parseInt(endTime.replace(/-/g, ""), 10);
            if (startNum > endNum) {
                alert("结束时间不能在开始时间之前");
                differDate = 101;//无法查询
            } else {
                differDate = DateDiff(endTime, strStartTime);  //调用计算两个日期天数差的函数，通用
                if (differDate > 100) {
					alert("查询时间间隔为"+differDate+"天，不能超过100天");
				}
            }
        }
    	if (differDate > 100) {
    		return; 
   		}
    	var colModel;
    	if ("5" == $("#groupType").val()) {
    		groupTypeNow = $("#groupType").val();
			colModel = [
				{name: "sendtypedescr", index: "sendtypedescr", width: 70, label: "发货类型", sortable: true, align: "left"},
				{name: "tileweight", index: "tileweight", width: 70, label: "瓷砖重量", sortable: true, align: "right", sum: true, formatter:"number"},
				{name: "tilepercent", index: "tilepercent", width: 70, label: "占比", sortable: true, align: "right", formatter: percentFmatter},
				{name: "flooramount", index: "flooramount", width: 70, label: "地板数量", sortable: true, align: "right", sum: true, formatter:"number"},
				{name: "floorpercent", index: "floorpercent", width: 70, label: "占比", sortable: true, align: "right", formatter: percentFmatter},
				{name: "cabinetamount", index: "cabinetamount", width: 90, label: "浴室柜数量", sortable: true, align: "right", sum: true, formatter:"number"},
				{name: "cabinetpercent", index: "cabinetpercent", width: 70, label: "占比", sortable: true, align: "right", formatter: percentFmatter},
				{name: "toiletamount", index: "toiletamount", width: 70, label: "马桶数量", sortable: true, align: "right", sum: true, formatter:"number"},
				{name: "toiletpercent", index: "toiletpercent", width: 70, label: "占比", sortable: true, align: "right", formatter: percentFmatter},
			];
		} else {
			groupTypeNow = $("#groupType").val();
			colModel = [
			  	{name: "code", index: "code", width: 75, label: "编号", sortable: true, align: "left"},
			  	{name: "descr", index: "descr", width: 120, label: "名称", sortable: true, align: "left"},
			  	{name: "saleqty", index: "saleqty", width: 70, label: "销售量", sortable: true, align: "right",sum: true},
			  	{name: "saleamount", index: "saleamount", width: 70, label: "销售额", sortable: true, align: "right", sum: true},
			  	{name: "purchamount", index: "purchamount", width: 70, label: "结算", sortable: true, align: "right", sum: true},
			  	{name: "profit", index: "profit", width: 80, label: "毛利额", sortable: true, align: "right", sum: true},
			  	{name: "profitpercent", index: "profitpercent", width: 85, label: "毛利率(%)", sortable: true, align: "right",
		  			formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2}, sum: true},
			  	{name: "saleshare", index: "saleshare", width: 85, label: "销售占比(%)", sortable: true, align: "right",
			  		formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2}},
			  	{name: "profitshare", index: "profitshare", width: 85, label: "利润占比(%)", sortable: true, align: "right",
			  		formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2}},
			  	{name: "area", index: "area", width: 70, label: "总面积", sortable: true, align: "right"},
			  	{name: "unitprofit", index: "unitprofit", width: 120, label: "单位平方利润额", sortable: true, align: "right",
			  		formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2}},
			  	{name: "unitsale", index: "unitsale", width: 120, label: "单位平方销售额", sortable: true, align: "right",
			  		formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2}},
			  	{name: "unitpurch", index: "unitpurch", width: 120, label: "单位平方结算额", sortable: true, align: "right",
			  		formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2}},
			  	{name: "qtycal", index:"qtycal", width: 70, label: "订货量", sortable: true, align: "right", sum:true},
			  	{name: "arriveamount", index: "arriveamount", width: 70, label: "订货额", sortable: true, align: "right"}
			  	
			];
		}
    	
    	$.jgrid.gridUnload("dataTable");/* 从dom上删除此grid,跟GridDestroy不同的是table对象跟pager对象并不会被删除，以便下次使用 */
    	Global.JqGrid.initJqGrid("dataTable", {
    		url: "${ctx}/admin/itemSendAnalyse/goJqGrid",
	        postData: postData,
	        page: 1,
	        height: $(document).height() - $("#content-list").offset().top - 70,
	        styleUI: "Bootstrap",
	        rowNum:1000000,
			colModel : colModel,
			ondblClickRow: function(){
				view();
			},
			gridComplete:function(){
				/* 获取列中sum，avg，count值的方法 */
				//var tileweightSum = parseFloat($("#dataTable").getCol("tileweight",false,"sum")).toFixed(4);
				var needSum1 = $("#dataTable").getCol("tileweight",false,"sum");
				var needSum2 = $("#dataTable").getCol("flooramount",false,"sum");
				var needSum3 = $("#dataTable").getCol("cabinetamount",false,"sum");
				var needSum4 = $("#dataTable").getCol("toiletamount",false,"sum");
				/* footerData-设置或者取得底部数据。myRound-四舍五入,修改负数规则。 */
				$("#dataTable").footerData("set",{"tileweight":myRound(needSum1,2)});
				$("#dataTable").footerData("set",{"flooramount":myRound(needSum2,2)});
				$("#dataTable").footerData("set",{"cabinetamount":myRound(needSum3,2)});
				$("#dataTable").footerData("set",{"toiletamount":myRound(needSum4,2)});
			},
			loadComplete: function () {
				calGrossProfitMargin();
			}
		});
		
    }

    /* 自定义formatter在数据后面加% */
	function percentFmatter(cellvalue, options, rowObject){ 
	    return cellvalue+"%";
	}
    
    function change(e) {
	    if ($(e).is(":checked")) $(e).val("1");
	    else $(e).val("0");
    }
    function view() {
    	var ret = selectDataTableRow();
        if (ret) {
        	var postData = $("#page_form").jsonForm();
        	postData.custCode = ret.code;
        	if (typeof(ret.sendtypedesc) != "undefined") { 
   				postData.sendType = ret.sendtypedescr;
			}  
        	postData.groupType = groupTypeNow;
        	Global.Dialog.showDialog("itemSendAnalyseView", {
	            title: "查看明细",
	            url: "${ctx}/admin/itemSendAnalyse/goDetail",
	            postData: postData,
	            height: 600,
	            width: 1140
    		});
      } else {
	        art.dialog({
	        	content: "请选择一条记录"
	        });
		}
    }

  	</script>
	</head>
	<body>
		<div class="body-box-list">
  			<div class="query-form">
    			<form action="" method="post" id="page_form" class="form-search">
     				<input type="hidden" id="expired" name="expired" value="${customer.expired}"/>
      				<input type="hidden" name="jsonString" value=""/>
      				<ul class="ul-form">
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
        				<li>
          					<label>品牌</label>
          					<input type="text" id="sqlCode" name="sqlCode"/>
        				</li>
        				<li>
          					<label>供应商</label>
          					<input type="text" id="supplCode" name="supplCode"/>
        				</li>
       					<li>
			          		<label>材料编号</label>
				          	<input type="text" id="itemCode" name="itemCode"/>
				        </li>
        				<li>
				          	<label>归属买手</label>
				          	<input type="text" id="sendCzy" name="sendCzy"/>
        				</li>
        				<li>
          					<label>开始日期</label>
          					<input type="text" id="sendDateFrom" name="sendDateFrom" class="i-date"
                 				   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                 				   value="<fmt:formatDate value='${itemApp.sendDateFrom}' pattern='yyyy-MM-dd'/>"/>
        				</li>
        				<li>
          					<label>至</label>
				          	<input type="text" id="sendDateTo" name="sendDateTo" class="i-date"
				                   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
				                   value="<fmt:formatDate value='${itemApp.sendDateTo}' pattern='yyyy-MM-dd'/>"/>
       					</li>
        				<li>
          					<label>分组条件</label>
				          	<select id="groupType" name="groupType">
				            	<option value="1">按品牌</option>
				            	<option value="2">按材料类型2</option>
				            	<option value="3">按材料类型3</option>
				            	<option value="4">按材料名称</option>
				            	<option value="5">按发货类型</option>
				          	</select>
        				</li>
        				<li>
          					<label>归属部门</label>
          					<input type="text" id="department2" name="department2"/>
        				</li>
        				<li>
							<label>客户类型</label>
							<house:custTypeMulit id="custType" selectedValue="${itemApp.custType}" ></house:custTypeMulit>
						</li>
						<li>
							<label>发货类型</label>
							<house:xtdm id="sendType" value="${itemApp.sendType}" dictCode="ITEMAPPSENDTYPE"/>
						</li>
						<li>
							<label>套餐材料</label>
							<house:xtdm id="isSetItem" dictCode="YESNO"/>
						</li>
						<li>
          					<label>   </label>
          					<input type="checkbox" id="containCmpCust" name="containCmpCust"
                 				   value="${itemApp.containCmpCust=='1'?'1':'0'}" onclick="change(this)"
                 				   ${itemApp.containCmpCust=='1'?'checked':'' }/>包含内部
        				</li>
        				<li>
          					<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
        				</li>
   					</ul>
   				</form>
  			</div>
  			<div class="btn-panel">
    			<div class="btn-group-sm">
      				<house:authorize authCode="ITEMSENDANALYSE_VIEW">
        				<button type="button" class="btn btn-system" onclick="view()">查看</button>
      				</house:authorize>
      				<house:authorize authCode="ITEMSENDANALYSE_EXCEL">
        				<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
      				</house:authorize>
    			</div>
  			</div>
  			<div class="pageContent">
    			<div id="content-list">
      				<table id="dataTable"></table>
    			</div>
  			</div>
		</div>
	</body>
</html>


