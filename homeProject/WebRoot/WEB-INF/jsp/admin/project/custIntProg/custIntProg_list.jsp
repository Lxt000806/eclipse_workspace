<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>custIntProg列表</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}"
	type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}"
	type="text/javascript"></script>
<script type="text/javascript">
	function goto_query() {
    	var postData = $("#page_form").jsonForm();
        $("#dataTable").jqGrid("setGridParam", {
	        url: "${ctx}/admin/custIntProg/goJqGrid",
	        postData: postData,
	        page: 1,
	        sortname: ''
    	}).trigger("reloadGrid");
    }
    function clearForm() {
      	$("#page_form input[type='text']").val('');
      	$("#page_form select").val('');
      	$("#remarks").val('');
      	$("#status").val('');
      	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
      	$("#custType").val('');
      	$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
    }
    function addinfo() {
	    var ret = selectDataTableRow();
	    if(ret){
		    Global.Dialog.showDialog("custIntProgAddinfo", {
		         title: "集成进度信息管理--信息录入",
		         url: "${ctx}/admin/custIntProg/goAddinfo",
		         postData:{map:JSON.stringify(ret)},
		         height: 600,
		         width: 1000,
		         returnFun: goto_query
		    });
	    }else{
	        art.dialog({
	          content: "请先查询出记录"
	        });
	    }
    }
    
    function view() {
	     var ret = selectDataTableRow();
	     if(ret){
	       Global.Dialog.showDialog("custIntProgAddinfo", {
	          title: "集成进度信息管理--查看",
	          url: "${ctx}/admin/custIntProg/goView",
	 		  postData:{map:JSON.stringify(ret)},
	          height: 600,
	          width: 1000,
	          returnFun: goto_query
	       });
	     }else{
	        art.dialog({
	          content: "请先查询出记录"
	        });
	     }
    }
    
    //导出EXCEL
    function doExcel() {
       var url = "${ctx}/admin/custIntProg/doExcel";
       doExcelAll(url);
    }
  
    /**初始化表格*/
    $(function () {
      Global.JqGrid.initJqGrid("dataTable", {
        height: $(document).height() - $("#content-list").offset().top - 100,
        styleUI: 'Bootstrap',
		colModel : [
			{name: "custcode", index: "custcode", width: 86, label: "客户编号", sortable: true, align: "left"},
			{name: "custtypedescr", index: "custtypedescr", width: 90, label: "客户类型", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 92, label: "客户状态", sortable: true, align: "left"},
			{name: "address", index: "address", width: 171, label: "楼盘地址", sortable: true, align: "left"},
			{name: "designmandescr", index: "designmandescr", width: 74, label: "设计师", sortable: true, align: "left"},
			{name: "designdept1descr", index: "designdept1descr", width: 97, label: "事业部", sortable: true, align: "left"},
			{name: "projectmandescr", index: "projectmandescr", width: 93, label: "项目经理", sortable: true, align: "left"},
			{name: "prjdept1descr", index: "prjdept1descr", width: 93, label: "工程部", sortable: true, align: "left"},
			{name: "cupdesigndescr", index: "cupdesigndescr", width: 95, label: "橱柜设计师", sortable: true, align: "left"},
			{name: "intdesigndescr", index: "intdesigndescr", width: 95, label: "集成设计师", sortable: true, align: "left"},
			{name: "intplanmandescr", index: "intplanmandescr", width: 89, label: "集成预算员", sortable: true, align: "left"},
			{name: "intdept2descr", index: "intdept2descr", width: 91, label: "集成部", sortable: true, align: "left"},
			{name: "nowintprogdescr", index: "nowintprogdescr", width: 109, label: "集成当前进度", sortable: true, align: "left"},
			{name: "nowcupprogdescr", index: "nowcupprogdescr", width: 101, label: "橱柜当前进度", sortable: true, align: "left"},
			{name: "measureappdate", index: "measureappdate", width: 140, label: "集成测量申请日期", sortable: true, align: "left", formatter: formatTime},
			{name: "measurecupappdate", index: "measurecupappdate", width: 137, label: "橱柜测量申请日期", sortable: true, align: "left", formatter: formatTime},
			{name: "dealdate", index: "dealdate", width: 130, label: "集成测量达标日期", sortable: true, align: "left", formatter: formatTime},
			{name: "cupdealdate", index: "cupdealdate", width: 131, label: "橱柜测量达标日期", sortable: true, align: "left", formatter: formatTime},
			{name: "confirmbegin", index: "confirmbegin", width: 98, label: "实际开工日期", sortable: true, align: "left", formatter: formatDate},
			{name: "intdesigndate", index: "intdesigndate", width: 98, label: "衣柜设计完成日期", sortable: true, align: "left", formatter: formatDate},
			{name: "cupdesigndate", index: "cupdesigndate", width: 98, label: "橱柜设计完成日期", sortable: true, align: "left", formatter: formatDate},
			{name: "cupappdate", index: "cupappdate", width: 106, label: "橱柜下单日期", sortable: true, align: "left", formatter: formatDate},
			{name: "intappdate", index: "intappdate", width: 104, label: "集成下单日期", sortable: true, align: "left", formatter: formatDate},
			{name: "doorappdate", index: "doorappdate", width: 111, label: "推拉门下单日期", sortable: true, align: "left", formatter: formatDate},
			{name: "tableappdate", index: "tableappdate", width: 120, label: "台面下单日期", sortable: true, align: "left", formatter: formatDate},
			{name: "intsenddate", index: "intsenddate", width: 125, label: "集成实际出货日期", sortable: true, align: "left", formatter: formatDate},
			{name: "cupsenddate", index: "cupsenddate", width: 125, label: "橱柜实际出货日期", sortable: true, align: "left", formatter: formatDate},
			{name: "intcheckdate", index: "intcheckdate", width: 125, label: "集成质检日期", sortable: true, align: "left", formatter: formatTime},
			{name: "cupcheckdate", index: "cupcheckdate", width: 125, label: "橱柜质检日期", sortable: true, align: "left", formatter: formatTime},
			{name: "intinstalldate", index: "intinstalldate", width: 125, label: "集成安装日期", sortable: true, align: "left", formatter: formatDate},
			{name: "cupinstalldate", index: "cupinstalldate", width: 125, label: "橱柜安装日期", sortable: true, align: "left", formatter: formatDate},
			{name: "tableinstalldate", index: "tableinstalldate", width: 125, label: "台面安装日期", sortable: true, align: "left", formatter: formatDate},
			{name: "intdeliverdate", index: "intdeliverdate", width: 125, label: "集成交付日期", sortable: true, align: "left", formatter: formatDate},
			{name: "cupdeliverdate", index: "cupdeliverdate", width: 125, label: "橱柜交付日期", sortable: true, align: "left", formatter: formatDate},
			{name: "intdesigndate", index: "intdesigndate", width: 130, label: "衣柜设计完成日期", sortable: true, align: "left", formatter: formatDate},
			{name: "cupdesigndate", index: "cupdesigndate", width: 130, label: "橱柜设计完成日期", sortable: true, align: "left", formatter: formatDate},
			{name: "cupappdays", index: "cupappdays", width: 113, label: "橱柜制单时间A", sortable: true, align: "right"},
			{name: "intappdays", index: "intappdays", width: 105, label: "集成制单时间A", sortable: true, align: "right"},
			{name: "appdelayddays", index: "appdelayddays", width: 112, label: "集成制单拖期", sortable: true, align: "right",cellattr: addCellAttr},
			{name: "cupappdelayddays", index: "cupappdelayddays", width: 104, label: "橱柜制单拖期", sortable: true, align: "right",cellattr: addCellAttr},
			{name: "delayremarks", index: "delayremarks", width: 108, label: "拖期说明", sortable: true, align: "left"},
			{name: "tablesenddate", index: "tablesenddate", width: 115, label: "台面出货日期", sortable: true, align: "left", formatter: formatDate},
			{name: "intproducedays", index: "intproducedays", width: 105, label: "集成生产用时B", sortable: true, align: "right"},
			{name: "intsenddelaydays", index: "intsenddelaydays", width: 105, label: "集成出货拖期", sortable: true, align: "right",cellattr: addCellAttr},
			{name: "cupproducedays", index: "cupproducedays", width: 105, label: "橱柜生产用时B", sortable: true, align: "right"},
			{name: "cupsenddelaydays", index: "cupsenddelaydays", width: 105, label: "橱柜出货拖期", sortable: true, align: "right",cellattr: addCellAttr},
			{name: "intdeliverdays", index: "intdeliverdays", width: 105, label: "集成交付用时C", sortable: true, align: "right"},
			{name: "intinstalldelaydays", index: "intinstalldelaydays", width: 105, label: "集成安装拖期", sortable: true, align: "right",cellattr: addCellAttr},
			{name: "cupdeliverdays", index: "cupdeliverdays", width: 105, label: "橱柜交付用时C", sortable: true, align: "right"},
			{name: "cupinstalldelaydays", index: "cupinstalldelaydays", width: 105, label: "橱柜安装拖期", sortable: true, align: "right",cellattr: addCellAttr},
			{name: "intalldays", index: "intalldays", width: 98, label: "集成总用时", sortable: true, align: "right"},
			{name: "cupalldays", index: "cupalldays", width: 99, label: "橱柜总用时", sortable: true, align: "right"},
			{name: "ecoarea", index: "ecoarea", width: 90, label: "实木生态板", sortable: true, align: "right", sum: true},
			{name: "mdfarea", index: "mdfarea", width: 90, label: "实木颗粒板", sortable: true, align: "right", sum: true},
			{name: "otherarea", index: "otherarea", width: 90, label: "其他板材", sortable: true, align: "right", sum: true},
			{name: "cupys", index: "cupys", width: 90, label: "橱柜预算金额", sortable: true, align: "right"},
			{name: "intys", index: "intys", width: 90, label: "集成预算金额", sortable: true, align: "right"},
			{name: "allplan", index: "allplan", width: 85, label: "预算总额", sortable: true, align: "right", sum: true},
			{name: "cupxd", index: "cupxd", width: 90, label: "橱柜下单成本", sortable: true, align: "right",hidden:${custIntProg.costRight=="1"?false:true}},
			{name: "intxd", index: "intxd", width: 90, label: "集成下单成本", sortable: true, align: "right",hidden:${custIntProg.costRight=="1"?false:true}},
			{name: "orderamount", index: "orderamount", width: 85, label: "下单总成本", sortable: true, align: "right", sum: true,hidden:${custIntProg.costRight=="1"?false:true}},
			{name: "cupzj", index: "cupzj", width: 70, label: "橱柜增减", sortable: true, align: "right"},
			{name: "intzj", index: "intzj", width: 70, label: "集成增减", sortable: true, align: "right"},
			{name: "addreduceall", index: "addreduceall", width: 85, label: "增减总额", sortable: true, align: "right", sum: true},
			{name: "iserrordescr", index: "iserrordescr", width: 50, label: "出错", sortable: true, align: "left"},
			{name: "noinstalldescr", index: "noinstalldescr", width: 78, label: "不能安装", sortable: true, align: "left"},
			{name: "ispaydescr", index: "ispaydescr", width: 50, label: "赔付", sortable: true, align: "left"},
			{name: "isbusidescr", index: "isbusidescr", width: 50, label: "加急", sortable: true, align: "left"},
			{name: "isreturndescr", index: "isreturndescr", width: 50, label: "退单", sortable: true, align: "left"},
			{name: "iscmpldescr", index: "iscmpldescr", width: 48, label: "投诉", sortable: true, align: "left"},
			{name: "issaledescr", index: "issaledescr", width: 90, label: "售中/售后", sortable: true, align: "left"},
			{name: "area", index: "area", width: 50, label: "面积", sortable: true, align: "left",hidden:true},
			{name: "layoutdescr", index: "layoutdescr", width: 50, label: "户型", sortable: true, align: "left",hidden:true},
			{name: "cupspl", index: "cupspl", width: 90, label: "橱柜供应商", sortable: true, align: "left",hidden:true},
			{name: "intspl", index: "intspl", width: 50, label: "集成供应商", sortable: true, align: "left",hidden:true},
			{name: "doorspl", index: "doorspl", width: 50, label: "推拉门供应商", sortable: true, align: "left",hidden:true},
			{name: "tablespl", index: "tablespl", width: 48, label: "台面供应商", sortable: true, align: "left",hidden:true},
			{name: "cupmaterial", index: "cupmaterial", width: 50, label: "橱柜材质", sortable: true, align: "left",hidden:true},
			{name: "intmaterial", index: "intmaterial", width: 48, label: "集成材质", sortable: true, align: "left",hidden:true},
			{name: "cupspldescr", index: "cupspldescr", width: 90, label: "橱柜供应商名称", sortable: true, align: "left",hidden:true},
			{name: "intspldescr", index: "intspldescr", width: 50, label: "集成供应商名称", sortable: true, align: "left",hidden:true},
			{name: "doorspldescr", index: "doorspldescr", width: 50, label: "推拉门供应商名称", sortable: true, align: "left",hidden:true},
			{name: "tablespldescr", index: "tablespldescr", width: 48, label: "台面供应商名称", sortable: true, align: "left",hidden:true},
			{name: "intmeasurestandarddays", index: "intmeasurestandarddays", width: 48, label: "集成测量达标天数", sortable: true, align: "left",hidden:true},
		    {name: "cupmeasurestandarddays", index: "cupmeasurestandarddays", width: 48, label: "橱柜测量达标天数", sortable: true, align: "left",hidden:true},
		    {name: "intmeasuredays", index: "intmeasuredays", width: 48, label: "集成测量申报天数", sortable: true, align: "left",hidden:true},
		    {name: "cupmeasuredays", index: "cupmeasuredays", width: 48, label: "橱柜测量申报天数", sortable: true, align: "left",hidden:true},
		    {name: "remarks", index: "remarks", width: 48, label: "备注", sortable: true, align: "left",hidden:true},
		],

        ondblClickRow: function () {
          view();
        }
      });
      onCollapse(44);
      	$("#tableSpl").openComponent_supplier();
      	$("#cupSpl").openComponent_supplier();
      	$("#intSpl").openComponent_supplier();
      	$("#doorSpl").openComponent_supplier();
      	$("#intPlanManDescr").openComponent_employee();
      	$("#intDesignDescr").openComponent_employee();
      	$("#cupDesignDescr").openComponent_employee();

      	// 生产进度管理 add by zb on 20200304
      	$("#intProduceView").on("click", function () {
      		Global.Dialog.showDialog("intProduceAdd",{
				title:"生产进度管理",
				url:"${ctx}/admin/custIntProg/goIntProduceView",
				height:716,
				width:1174,
			});
      	});
    });

	function delayAdd() {
		var ret = selectDataTableRow();
		if(ret){
			$.ajax({
				url : "${ctx}/admin/custIntProg/checkDelayed",
				type : 'post',
				data : {
					'custCode' : ret.custcode
				},
				dataType : 'json',
				cache : false,
				error : function(obj) {
					showAjaxHtml({
						"hidden" : false,
						"msg" : '保存数据出错~'
					});
				},
				success : function(obj) {
					if (obj.result != "yes") {
						flag1 = false;
						art.dialog({
							content : "该客户未做信息录入,无法进行制单拖期录入！"
						});
					} else {
						$.ajax({
							url : "${ctx}/admin/custIntProg/checkDelayed",
							type : 'post',
							data : {
								'custCode' : ret.custcode
							},
							dataType : 'json',
							cache : false,
							error : function(obj) {
								showAjaxHtml({
									"hidden" : false,
									"msg" : '保存数据出错~'
								});
							},
							success : function(obj) {
								if (obj.result != "yes") {
									art.dialog({
										content : "该客户集成或橱柜未拖期,无法进行制单拖期录入！"
									});
								} else {
									if (ret) {
										Global.Dialog.showDialog("custIntProgAddinfo",{
												title : "集成进度信息管理--制单拖期录入",
												url : "${ctx}/admin/custIntProg/goDelayAdd",
												postData:{map:JSON.stringify(ret)},
												height : 420,
												width : 720,
												returnFun : goto_query
										});
									} else {
										art.dialog({
											content : "请先查询出记录"
										});
									}
								}
							}
						});
					}
				}
			});
		}else{
			art.dialog({
				content: "请先查询出记录"
			});
      	}

	}
	function addCellAttr(rowId, val, rawObject, cm, rdata) {
		if (cm.name=="appdelayddays" || cm.name=="cupappdelayddays" || cm.name=="intsenddelaydays"
			   || cm.name=="cupsenddelaydays" || cm.name=="intinstalldelaydays" || cm.name=="cupinstalldelaydays") {
			if(parseFloat(val)>0){
				 return "style='color:red'";
	        }
		}
	}

</script>
</head>

<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired"
					value="${itemChg.expired}" /> <input type="hidden"
					name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>楼盘</label> <input type="text" id="address"
						style="width:448px;" name="address" value="${custIntProg.address}" />
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType"  selectedValue="${custIntProg.custType}" ></house:custTypeMulit>
					</li>
					</li>
					<li><label>橱柜设计师</label> <input type="text"
						id="cupDesignDescr" name="cupDesignDescr" style="width:160px;"
						value="${custIntProg.cupDesignDescr }" />
					</li>
					<li><label>橱柜供应商</label> <input type="text" id="cupSpl"
						name="cupSpl" style="width:160px;" value="${custIntProg.cupSpl }" />
					</li>
					<li><label>集成供应商</label> <input type="text" id="intSpl"
						name="intSpl" style="width:160px;" value="${custIntProg.intSpl }" />
					</li>
					<li><label>推拉门供应商</label> <input type="text" id="doorSpl"
						name="doorSpl" style="width:160px;"
						value="${custIntProg.doorSpl }" />
					</li>
					<li><label>台面供应商</label> <input type="text" id="tableSpl"
						name="tableSpl" style="width:160px;"
						value="${custIntProg.tableSpl }" />
					</li>
					<li><label>事业部</label> <house:department1
							id="designDept1Descr" depType="0"></house:department1>
					</li>
					<li><label>工程部</label> <house:department1 id="prjDept1Descr"
							depType="3"></house:department1>
					</li>
					<li><label>集成部</label> <house:dict id="intDept2Descr"
							dictCode=""
							sql="select a.code, a.code + '' + a.desc1 fd from tDepartment2 a, tDepartment1 b 
										where a.Department1=b.Code and a.Expired ='F'
										 and a.DepType='6' and b.DepType='6' order by a.DispSeq"
							sqlValueKey="code" sqlLableKey="fd">
						</house:dict>
					</li>
					<li><label>出错</label> <house:xtdm id="isErrorDescr"
							dictCode="YESNO" value="${custIntProg.isErrorDescr}"></house:xtdm>
					</li>
					<li><label>加急</label> <house:xtdm id="isBusiDescr"
							dictCode="YESNO" value="${custIntProg.isBusiDescr}"></house:xtdm>
					</li>
					<li><label>赔付</label> <house:xtdm id="isPayDescr"
							dictCode="YESNO" value="${custIntProg.isPayDescr}"></house:xtdm>
					</li>
					<li><label>售中/售后</label> <house:xtdm id="isSaleDescr"
							dictCode="YESNO" value="${custIntProg.isSaleDescr}"></house:xtdm>
					</li>
					<li><label>不能安装</label> <house:xtdm id="noInstallDescr"
							dictCode="YESNO" value="${custIntProg.noInstallDescr}"></house:xtdm>
					</li>
					<li id="loadMore">
						<button data-toggle="collapse" class="btn btn-sm btn-link "
							data-target="#collapse">更多</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
					<div class="collapse " id="collapse">
						<ul>
							<li><label>设计完成日期从</label> 
								<input type="text" id="designDateFrom" name="designDateFrom" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custIntProg.designDateFrom}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>至</label> 
								<input type="text" id="designDateTo" name="designDateTo" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custIntProg.buyDateFrom}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>下单日期从</label> <input type="text" id="buyDateFrom"
								name="buyDateFrom" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custIntProg.buyDateFrom}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>至</label> <input type="text" id="buyDateTo"
								name="buyDateTo" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custIntProg.buyDateFrom}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>出货日期从</label> <input type="text"
								id="sendDateFrom" name="sendDateFrom" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custIntProg.sendDateFrom}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>至</label> <input type="text" id="sendDateTo"
								name="sendDateTo" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custIntProg.sendDateTo}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>安装日期从</label> <input type="text"
								id="installDateFrom" name="installDateFrom" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custIntProg.installDateFrom}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>至</label> <input type="text" id="installDateTo"
								name="installDateTo" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custIntProg.installDateTo}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>交付日期从</label> <input type="text"
								id="deliverDateFrom" name="deliverDateFrom" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custIntProg.deliverDateFrom}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>至</label> <input type="text" id="deliverDateTo"
								name="deliverDateTo" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custIntProg.deliverDateTo}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>质检日期从</label> <input type="text"
								id="checkDateFrom" name="checkDateFrom" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custIntProg.checkDateFrom}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>至</label> <input type="text" id="checkDateTo"
								name="checkDateTo" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custIntProg.checkDateTo}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>集成设计师</label> <input type="text"
								id="intDesignDescr" name="intDesignDescr" style="width:160px;"
								value="${custIntProg.intDesignDescr }" />
							<li><label>集成预算员</label> <input type="text"
								id="intPlanManDescr" name="intPlanManDescr" style="width:160px;"
								value="${custIntProg.intPlanManDescr }" />
							</li>
							<li><label>测量申报日期从</label> <input type="text"
								id="measureAppDateFrom" name="measureAppDateFrom" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custIntProg.measureAppDateFrom}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>至</label> <input type="text"
								id="measureAppDateTo" name="measureAppDateTo" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custIntProg.measureAppDateTo}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>测量达标日期从</label> <input type="text"
								id="dealDateFrom" name="dealDateFrom" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custIntProg.dealDateFrom}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>至</label> <input type="text" id="dealDateTo"
								name="dealDateTo" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custIntProg.dealDateTo}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>开工日期从</label> <input type="text"
								id="confirmBeginFrom" name="confirmBeginFrom" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custIntProg.confirmBeginFrom}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>至</label> <input type="text" id="confirmBeginTo"
								name="confirmBeginTo" class="i-date"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value='${custIntProg.confirmBeginTo}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li><label>客户状态</label> <house:xtdmMulit id="status"
									dictCode="CUSTOMERSTATUS" selectedValue="${custIntProg.status}"
									unShowValue="1,2,3"></house:xtdmMulit>
							</li>
							<li><label>制单拖期</label> <house:xtdm id="checkDelayDays"
									dictCode="YESNO"></house:xtdm>
							</li>
							<li class="search-group-shrink">
								<button data-toggle="collapse" class="btn btn-sm btn-link "
									data-target="#collapse">收起</button> <input type="checkbox"
								id="expired_show" name="expired_show"
								value="${custIntProg.expired}" onclick="hideExpired(this)"
								${custIntProg.expired!='T' ?'checked':'' }/>
								<p>隐藏过期</p>
								<button type="button" class="btn  btn-sm btn-system "
									onclick="goto_query();">查询</button>
								<button type="button" class="btn btn-sm btn-system "
									onclick="clearForm();">清空</button>
							</li>
						</ul>
					</div>
				</ul>
			</form>
		</div>
		<div class="btn-panel">

			<div class="btn-group-sm">
				<house:authorize authCode="CUSTINTPROG_ADDINFO">
					<button type="button" class="btn btn-system " onclick="addinfo()">信息录入</button>
				</house:authorize>
				<house:authorize authCode="CUSTINTPROG_DELAYADD">
					<button type="button" class="btn btn-system " onclick="delayAdd()">制单拖期录入</button>
				</house:authorize>
				<house:authorize authCode="CUSTINTPROG_VIEW">
					<button type="button" class="btn btn-system " onclick="view()">查看</button>
				</house:authorize>
				<house:authorize authCode="CUSTINTPROG_INTPRODUCEVIEW">
					<button type="button" class="btn btn-system " id="intProduceView">生产进度管理</button>
				</house:authorize>
				<house:authorize authCode="CUSTINTPROG_EXCEL">
					<button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>


