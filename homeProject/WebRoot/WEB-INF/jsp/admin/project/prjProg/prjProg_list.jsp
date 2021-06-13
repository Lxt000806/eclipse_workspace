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
	<title>工程进度列表</title>
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
        .SelectBG{
            background-color:red;
            }
    </style>
<script type="text/javascript"> 
$(function(){
	function goto_query(){
		$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
	}
	
	$("#code").openComponent_customer({condition:{status:'1,2,3,4',mobileHidden:"1"}});
	$("#projectMan").openComponent_employee();
	$("#businessMan").openComponent_employee();
	$("#designMan").openComponent_employee();
	//$("#address").openComponent_item();

	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/customer/goPrjProgJqGrid",
		postData:{status:"4"},
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "address", index: "address", width: 140, label: "楼盘名称", align: "left",frozen:true},
			{name: "projectmandescr", index: "projectmandescr", width: 80, label: "项目经理", align: "left",frozen:true},
			{name: "relcustdescr", index: "relcustdescr", width: 75, label: "关系客户", sortable: true, align: "left"},
			{name: "confirmbegin", index: "confirmbegin", width: 93, label: "实际开工时间", sortable: true, align: "left", formatter: formatDate},
			{name: "comedate", index: "comedate", width: 93, label: "考核开始日期", sortable: true, align: "left", formatter: formatDate},
			{name: "delay", index: "delay", width: 75, label: "总拖期", sortable: true, align: "right"},
			{name: "planspeeddescr", index: "planspeeddescr", width: 100, label: "计划进度", sortable: true, align: "left"},
			{name: "nowspeeddescr", index: "nowspeeddescr", width: 100, label: "实际进度", sortable: true, align: "left"},
			{name: "lastsignworktype12descr", index: "lastsignworktype12descr", width: 95, label: "最后签到工种", sortable: true, align: "left"},
			{name: "lastsigndate", index: "lastsigndate", width: 95, label: "最后签到日期", sortable: true, align: "left", formatter: formatDate},
			{name: "constructstatus", index: "constructstatus", width: 95, label: "施工状态", sortable: true, align: "left",hidden:true},
			{name: "constructstatusdescr", index: "constructstatusdescr", width: 95, label: "施工状态", sortable: true, align: "left"},
			{name: "buildstadescr", index: "buildstadescr", width: 75, label: "工地状况", sortable: true, align: "left"},
			{name: "complainttimes", index: "complainttimes", width: 75, label: "投诉次数", sortable: true, align: "right"},
			//{name: "更新时间", index: "更新时间", width: 95, label: "更新时间", sortable: true, align: "left", formatter: formatDate},
			//{name: "deldays", index: "deldays", width: 75, label: "停工天数", sortable: true, align: "right"},
			//{name: "reprate", index: "reprate", width: 75, label: "停工占比", sortable: true, align: "right"},
			//{name: "checktimes", index: "checktimes", width: 75, label: "巡检次数", sortable: true, align: "right"},
			//{name: "modifytimes", index: "modifytimes", width: 75, label: "整改次数", sortable: true, align: "right"},
			//{name: "waitmodify", index: "waitmodify", width: 75, label: "待整改", sortable: true, align: "right"},
			//{name: "waitcheck", index: "waitcheck", width: 75, label: "待验收", sortable: true, align: "right"},
			{name: "code", index: "code", width: 90, label: "客户编号", sortable: true, align: "left"},
			{name: "pkkk", index: "pkkk", width: 60, label: "pkkk", sortable: true, align: "left",hidden:true},
			{name: "descr", index: "descr", width: 95, label: "客户名称", sortable: true, align: "left"},
			{name: "mobile1", index: "mobile1", width: 116, label: "手机号码", sortable: true, align: "left", hidden: true},
			{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
			{name: "工程部", index: "工程部", width: 75, label: "工程部", sortable: true, align: "left"},
			{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "right"},
			{name: "contractfee", index: "contractfee", width: 95, label: "工程总造价", sortable: true, align: "right"},
			{name: "jhprjitem", index: "jhprjitem", width: 100, label: "计划进度", sortable: true, align: "left",hidden:true},
			{name: "delaytime", index: "delaytime", width: 95, label: "节点拖期", sortable: true, align: "right"},
			{name: "prgremark", index: "prgremark", width: 128, label: "延误说明", sortable: true, align: "left"},
			{name: "begincomdate", index: "begincomdate", width: 88, label: "开工令时间", sortable: true, align: "left", formatter: formatDate},
			{name: "constructday", index: "constructday", width: 80, label: "标准工期", sortable: true, align: "right"},
			{name: "plannedcompletiondate", index: "plannedcompletiondate", width: 100, label: "计划完工时间", sortable: true, align: "left", formatter: formatDate},
			{name: "completiondate", index: "completiondate", width: 100, label: "实际完工时间", sortable: true, align: "left", formatter: formatDate},
			{name: "合同完工时间", index: "合同完工时间", width: 100, label: "合同完工时间", sortable: true, align: "left", formatter: formatTime},
			{name: "实际结算时间", index: "实际结算时间", width: 95, label: "实际结算时间", sortable: true, align: "left", formatter: formatDate},
			{name: "designmandescr", index: "designmandescr", width: 66, label: "设计师", sortable: true, align: "left"},
			{name: "department3descr", index: "department3descr", width: 106, label: "设计师三级部门", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 83, label: "客户状态", sortable: true, align: "left"},
			{name: "businessmandescr", index: "businessmandescr", width: 93, label: "业务员", sortable: true, align: "left", hidden: true},
			{name: "planset", index: "planset", width: 135, label: "计划下定", sortable: true, align: "left", hidden: true},
			{name: "setdate", index: "setdate", width: 135, label: "下定时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
			{name: "planmeasure", index: "planmeasure", width: 135, label: "计划量房", sortable: true, align: "left", hidden: true},
			{name: "measuredate", index: "measuredate", width: 135, label: "量房时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
			{name: "planplane", index: "planplane", width: 135, label: "计划平面", sortable: true, align: "left", hidden: true},
			{name: "planedate", index: "planedate", width: 135, label: "平面时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
			{name: "planfacade", index: "planfacade", width: 135, label: "计划立面", sortable: true, align: "left", hidden: true},
			{name: "facadedate", index: "facadedate", width: 135, label: "立面时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
			{name: "planprice", index: "planprice", width: 135, label: "计划报价", sortable: true, align: "left", hidden: true},
			{name: "pricedate", index: "pricedate", width: 135, label: "报价时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
			{name: "plandraw", index: "plandraw", width: 135, label: "计划效果图", sortable: true, align: "left", hidden: true},
			{name: "drawdate", index: "drawdate", width: 135, label: "效果图时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
			{name: "planprev", index: "planprev", width: 135, label: "计划放样", sortable: true, align: "left", hidden: true},
			{name: "prevdate", index: "prevdate", width: 135, label: "放样时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
			{name: "plansign", index: "plansign", width: 135, label: "计划签合同", sortable: true, align: "left", hidden: true},
			{name: "signdate", index: "signdate", width: 105, label: "合同签订时间", sortable: true, align: "left", formatter: formatDate},
			{name: "lastupdate", index: "lastupdate", width: 133, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 50, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 40, label: "操作", sortable: true, align: "left"},
			{name: "prjprogtempno", index: "prjprogtempno", width: 98, label: "prjprogtempno", sortable: true, align: "left",hidden:true},
			{name: "ispartdecorate", index: "ispartdecorate", width: 98, label: "ispartdecorate", sortable: true, align: "left",hidden:true}
			
		],
		ondblClickRow: function(){
           	view();
           },
           gridComplete:function(){
           	$(".ui-jqgrid-bdiv").scrollTop(0);
            frozenHeightReset("dataTable");
		},
	});
		
	$("#dataTable").jqGrid("setFrozenColumns");
	//模板设定
	$("#setTemplate").on("click",function(){
		var ret = selectDataTableRow();
		if (ret) {	
        	Global.Dialog.showDialog("Update",{ 
				title:"工程进度模板设定",
            	url:"${ctx}/admin/prjProg/goSave",
            	postData:{code:ret.code,isPrjProgTemp:ret.pkkk,projectManDescr:ret.projectmandescr},
            	height: 430,
              	width:850,
              	returnFun:goto_query
			});
		} else {
	        art.dialog({
	        	content: "请选择一条记录"
	        });
        }
	});
		
	//编辑
	$("#update").on("click",function(){
		var ret = selectDataTableRow();
		if (ret) {	
			Global.Dialog.showDialog("Update",{ 
				title:"编辑客户工程进度",
             	url:"${ctx}/admin/prjProg/goUpdate?id="+ret.code,
             	height: 690,
             	width:1050,
             	returnFun:goto_query
			});
		} else {
			art.dialog({
       			content: "请选择一条记录"
       		});
		}
	});
		
	//计划进度编排
	$("#progArrange").on("click",function(){
		var ret = selectDataTableRow();
		if(ret.ispartdecorate!="1" || ret.confirmbegin.trim()!=""){
			art.dialog({
       			content: "只允许修改局装未开工工地！"
       		});
       		return;
		}  
        if (ret) {	
        	Global.Dialog.showDialog("ProgArrange",{ 
            	title:"计划进度编排",
              	url:"${ctx}/admin/prjProg/goProgArrange?id="+ret.code,
              	height: 690,
              	width:1050,
              	returnFun:goto_query
            });
        } else {
        	art.dialog({
        		content: "请选择一条记录"
        	});
        }
	});
		
		//工地验收
		$("#confirm").on("click",function(){
           	Global.Dialog.showDialog("confirm",{ 
           	  title:"工地验收",
           	  url:"${ctx}/admin/prjProg/goConfirm",
           	  height: 800,
           	  width:1050,
           	  returnFun:goto_query
            });
		});
		
		//整改验收
		$("#modifyConfirm").on("click",function(){
           	Global.Dialog.showDialog("modifyConfirm",{ 
           	  title:"整改验收",
           	  url:"${ctx}/admin/prjProg/goModifyConfirm",
           	  height: 800,
           	  width:1050,
           	  returnFun:goto_query
            });
		});
		
		//查看
		//有两个view，去掉一个 modify by zb on 20191121
		/*$("#view").on("click",function(){
			var ret = selectDataTableRow();
         	  if (ret) {	
             	Global.Dialog.showDialog("Update",{ 
              	  title:"查看客户工程进度",
              	  url:"${ctx}/admin/prjProg/goView?id="+ret.code,
              	  height: 715,
              	  width:1100,
              	  returnFun:goto_query
              	});
            } else {
            	art.dialog({
        			content: "请选择一条记录"
        		});
            }
		});*/
		
		$("#updateStop").on("click",function(){
			var ret = selectDataTableRow();
         	 	
           	Global.Dialog.showDialog("updateStop",{ 
           		title:"停工标记",
           		url:"${ctx}/admin/prjProg/goUpdateStop",
           		height: 715,
           		width:1350,
           	  	returnFun:goto_query
           	});
            
		});
		
		//按项目经理查看
		$("#projectManView").on("click",function(){
           	Global.Dialog.showDialog("Update",{ 
           	  title:"工程进度——按项目经理查看",
           	  url:"${ctx}/admin/prjProg/goProjectManView",
           	  height: 750,
           	  width:1070,
           	  returnFun:goto_query
            });
		});
		
		//延误备注
		$("#prgRemark").on("click",function(){
					var ret = selectDataTableRow();
		
           	Global.Dialog.showDialog("prgRemark",{ 
           	  title:"修改工地备注",
           	  url:"${ctx}/admin/prjProg/goPrgRemark?id="+ret.code,
           	  height: 440,
           	  width:690,
           	  returnFun:goto_query
            });
		});
		
		//巡检与验收查询
		$("#checkAndConfirm").on("click",function(){
           	Global.Dialog.showDialog("checkAndConfirm",{ 
           	  title:"验收巡检",
           	  url:"${ctx}/admin/prjProg/goCheckAndConfirm",
           	  height: 800,
           	  width:1290,
           	  returnFun:goto_query
            });
		});
		
		//巡检与验收查询
		$("#prjLog").on("click",function(){
			var ret = selectDataTableRow();
		
           	Global.Dialog.showDialog("prjLog",{ 
           	  title:"施工日志——"+ret.address,
           	  url:"${ctx}/admin/prjProg/goPrjLog",
           	  postData:{custCode:ret.code},
           	  height: 700,
           	  width:1150,
           	  returnFun:goto_query
            });
		});
		
		
			
		$("#print").on("click",function(){
			var ret = selectDataTableRow();
			if(ret){
				$.ajax({
					url:'${ctx}/admin/prjProg/getPrjProgPK',
					type: 'post',
					data: {code:ret.code},
					dataType: 'json',
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
				    },
				    success: function(obj){
				    	if(obj==true){
							var reportName = "report4.jasper";
				   			Global.Print.showPrint(reportName, {
								CustCode:ret.code,
								SUBREPORT_DIR: "<%=jasperPath%>" 
							});
				    	}else{
							art.dialog({
							 content:"该楼盘不存在计划进度模板"
							});		
				    	}
				    }
				 });
			}		
		});	
		
		$("#abnormalSites").on("click",function(){
			Global.Dialog.showDialog("abnormalSites",{ 
       	  		title:"异常工地管理",
       	  		url:"${ctx}/admin/prjProg/goAbnormalSites",
       	  		postData:{department2:$("#department2").val(),projectMan:$("#projectMan").val()},
       	  		height: 800,
       	  		width:1290,
       	  		returnFun:goto_query
            });
		})
		
		onCollapse(0)
			
}); // end of $(function() {})


 function clearForm(){
 	$("#openComponent_customer_code").val('');
 	$("#openComponent_employee_designMan").val('');
 	$("#openComponent_employee_businessMan").val('');
 	$("#openComponent_employee_projectMan").val('');
 	$("#code").val('');
 	$("#designMan").val('');
 	$("#businessMan").val('');
 	$("#projectMan").val('');
 	$("#prjProgTempNo").val('');
 	$("#prjProgTempNo_NAME").val('');
 	$("#custType_NAME").val('');
 	$("#department2").val('');
 	$("#department2_NAME").val('');
 
	$("#page_form input[type='text']  ").val('');
	$("#page_form select").val('');
	$("#prjProgTempNo").val('');
	$("#address").val('');
	$("#code").val('');
	$("#custType").val('');
	$.fn.zTree.getZTreeObj("tree_prjProgTempNo").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes();
	$.fn.zTree.getZTreeObj("tree_constructStatus").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
	
} 
function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}
 
function view(){
	var ret = selectDataTableRow();
	if (ret) {	
   		/*Global.Dialog.showDialog("Update",{ 
			title:"查看客户工程进度",
			url:"${ctx}/admin/prjProg/goView?id="+ret.code,
			height: 795,
			width:1100,
			returnFun:goto_query
    	});*/
    	// 统一调用工程进度拖延分析，不再调用原来prjProg_view了 modify by zb on 20191121
    	Global.Dialog.showDialog("Update",{
			title:"查看客户工程进度",
			url:"${ctx}/admin/prjDelayAnaly/goView",
			postData:{code:ret.code},
			width:1100,
			height:715,
			// returnFun:goto_query
		});
  	} else {
  		art.dialog({
			content: "请选择一条记录"
		});
  	}
} 

//导出Excel
		function doExcel(){
			var url = "${ctx}/admin/prjProg/doExcel";
			doExcelAll(url);
		}

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="expired" name="expired" value="${customer.expired}" />
					<ul class="ul-form">
						<li>
							<label>客户编号</label>
						<input type="text" id="code" name="code" style="width:160px;" value="${customer.code }" />
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;" value="${customer.address}" />
						</li>
						<li>
							<label>工程部</label>
							<house:DictMulitSelect id="department2" dictCode=""
								sql="select a.Code, a.desc1+' '+isnull(e.NameChi,'') desc1  from dbo.tDepartment2 a
										left join tEmployee e on e.Department2=a.Code and e.IsLead='1' and e.LeadLevel='1' and e.expired='F'
										 where  a.deptype='3' and a.Expired='F' order By dispSeq " 
								sqlValueKey="Code" sqlLableKey="Desc1">
							</house:DictMulitSelect>
						</li>
						<li>
							<label>实际进度</label>
							<house:xtdmMulit id="prjProgTempNo" dictCode="" sql="select Code SQL_VALUE_KEY, Descr SQL_LABEL_KEY from tPrjItem1 where Expired='F'" selectedValue="${customer.prjProgTempNo }"></house:xtdmMulit>
						</li>
						<li>
							<label>客户类型</label>
							<house:custTypeMulit id="custType"  selectedValue="${customer.custType }"></house:custTypeMulit>
						</li>
						<li>
							<label>客户状态</label>
							<house:xtdmMulit id="status" dictCode="" sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='CUSTOMERSTATUS' and (CBM='4' or CBM = '5')  " selectedValue="4"></house:xtdmMulit>
						</li>
						<li>
							<label>项目经理</label>
							<input type="text" id="projectMan" name="projectMan" style="width:160px;" value="${customer.projectMan}" />
						</li>
						<li>
							<label>施工状态</label>
							<house:xtdmMulit id="constructStatus" dictCode="CONSTRUCTSTATUS" selectedValue="0,1,2,3,4,5,6,7"></house:xtdmMulit>
						</li>
						<li>
							<label>开工日期从</label>
							<input type="text" id="ConfirmBeginFrom" name="ConfirmBeginFrom" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value=""/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="ConfirmBeginTo" name="ConfirmBeginTo" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value=""/>
						</li>
						<li id="loadMore" style="width: 40%">
						    <button data-toggle="collapse" class="btn btn-sm btn-link" data-target="#collapse">更多</button>
							<input type="checkbox" id="expired_show" name="expired_show" onclick="hideExpired(this)"/>不显示非本人专盘&nbsp				
							<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
						</li>
						<div class="collapse" id="collapse">
                            <ul>
								<li>
									<label>设计师</label>
								    <input type="text" id="designMan" name="designMan" style="width:160px;" value="${customer.designMan }" />
								</li>
								<li>
									<label>业务员</label>
								    <input type="text" id="businessMan" name="businessMan" style="width:160px;" value="${customer.businessMan }" />
								</li>
								<li>
									<label>工地状态</label>
									<house:xtdmMulit id="buildStatus" dictCode="BUILDSTATUS"  ></house:xtdmMulit>
								</li>
								<li>
									<label>施工方式</label>
									<house:xtdm id="constructType" dictCode="CONSTRUCTTYPE"></house:xtdm>
								</li>
								<li>
		                            <label>已验收节点</label>
		                            <house:dict id="acceptedPrjItem" dictCode=""
		                                sql="select Code SQL_VALUE_KEY, Descr SQL_LABEL_KEY from tPrjItem1 where IsConfirm = '1' and Expired = 'F' order by Seq"></house:dict>
		                        </li>
								<li>
		                            <label>未验收节点</label>
		                            <house:dict id="notAcceptedPrjItem" dictCode=""
		                                sql="select Code SQL_VALUE_KEY, Descr SQL_LABEL_KEY from tPrjItem1 where IsConfirm = '1' and Expired = 'F' order by Seq"></house:dict>
		                        </li>
                                <li style="width: 40%">
                                    <button data-toggle="collapse" class="btn btn-sm btn-link" data-target="#collapse">收起</button>
                                    <input type="checkbox" id="expired_show" name="expired_show" onclick="hideExpired(this)"/>不显示非本人专盘&nbsp
                                    <button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
                                    <button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
                                </li>
                            </ul>
                        </div>
					</ul>	
				</form>
			</div>
		</div>
		<div class="clear_float"></div>
		<div class="pageContent">
			<div class="btn-panel" >
    			<div class="btn-group-sm"  >
					<house:authorize authCode="	PRJPROG_SAVE">
						<button type="button" class="btn btn-system " id="setTemplate"><span>模板设定</span>
						</button>
					</house:authorize>	
					<house:authorize authCode="	PRJPROG_UPDATE">
						<button type="button" class="btn btn-system " id="update"><span>编辑</span>
						</button>	
					</house:authorize>	
					<house:authorize authCode="	PRJPROG_PROGARRANGE">
						<button type="button" class="btn btn-system " id="progArrange"><span>计划进度编排</span>
						</button>	
					</house:authorize>	
					<house:authorize authCode="PRJPROG_CONFIRM">
						<button type="button" class="btn btn-system " id="confirm"><span>工地验收</span>
						</button>
					</house:authorize>	
					
					<house:authorize authCode="PRJPROG_MODIFY">	
						<button type="button" class="btn btn-system " id="modifyConfirm"><span>整改验收</span>
						</button>	
					</house:authorize>	
					
					<house:authorize authCode="PRJPROG_PRGREMARK">
						<button type="button" class="btn btn-system " id="prgRemark"><span>工地备注</span>
						</button>	
					</house:authorize>	
					
					<house:authorize authCode="PRJPROG_UPDATESTOP">
						<button type="button" class="btn btn-system " id="updateStop"><span>停工标记</span>
						</button>	
					</house:authorize>
					
					<house:authorize authCode="	PRJPROG_CONFIRMANDCHECK">
						<button type="button" class="btn btn-system " id="checkAndConfirm"><span>验收巡检查询</span>
						</button>
					</house:authorize>	
					
					<house:authorize authCode="PRJPROG_VIEW">
						<button type="button" class="btn btn-system " id="view" onclick="view()"><span>查看</span>
						</button>	
					</house:authorize>
					
					<house:authorize authCode="PRJPROG_PRJLOG">
						<button type="button" class="btn btn-system " id="prjLog"><span>施工日志</span>
						</button>	
					</house:authorize>
					
					<house:authorize authCode="	PRJPROG_PRINT">
						<button type="button" class="btn btn-system " id="print"><span>打印报表进度</span>
						</button>
					</house:authorize>	
					
					<house:authorize authCode="PRJPROG_ABNORMALSITES">
						<button type="button" class="btn btn-system " id="abnormalSites"><span>异常工地管理</span>
						</button>	
					</house:authorize>
					
					<house:authorize authCode="	PRJPROG_EXPORT">
						<button type="button" class="btn btn-system " onclick="doExcel()" title="导出当前excel数据" >
							<span>导出excel</span>
						</button>	
					</house:authorize>	
				</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</body>	
</html>
