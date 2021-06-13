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
	<title>工地工人安排</title>
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
		<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	$(function(){
		onCollapse(0);
	
		$("#workerCode").openComponent_worker();	
		$("#projectMan").openComponent_employee();	
		var postData = $("#page_form").jsonForm();
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/custWorker/goJqGrid",
			postData:{
				status:"0",
			},
			height:$(document).height()-$("#content-list").offset().top-102,
			styleUI:"Bootstrap", 
			colModel : [
				{name: "pk", index: "pk", width: 75, label: "pk", sortable: true, align: "left",hidden:true,frozen : true},
				{name: "workercodedescr", index: "workercodedescr", width: 75, label: "工人", sortable: true, align: "left",frozen : true},
				{name: "worktype12descr", index: "worktype12descr", width: 75, label: "工种", sortable: true, align: "left",frozen : true},
				{name: "leveldescr", index: "leveldescr", width: 75, label: "工人等级", sortable: true, align: "left",frozen : true},
				{name: "address", index: "address", width: 160, label: "楼盘", sortable: true, align: "left",frozen : true},
				{name: "relcustdescr",	index:"relcustdescr",	width:75,	label:"关系客户",	sortable:true,align:"left", },
				{name: "endstatusdescr", index: "endstatusdescr", width: 60, label: "状态", sortable: true, align: "left"},
				{name: "projectmandescr", index: "projectmandescr", width: 75, label: "项目经理", sortable: true, align: "left"},
				{name: "projectdept", index: "projectdept", width: 75, label: "工程部", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 75, label: "客户类型", sortable: true, align: "left"},
				{name: "layoutdescr", index: "layoutdescr", width: 75, label: "户型", sortable: true, align: "left"},
				{name: "area", index: "area", width: 65, label: "面积", sortable: true, align: "right"},
				{name: "confirmbegin", index: "confirmbegin", width: 100, label: "开工时间", sortable: true, align: "left", formatter: formatDate},
				{name: "regioncodedescr", index: "regioncodedescr", width: 85, label: "楼盘片区", sortable: true, align: "left"},
				{name: "appdate", index: "appdate", width: 110, label: "申请时间", sortable: true, align: "left", formatter: formatDate},
				{name: "appcomedate", index: "appcomedate", width: 110, label: "申请进场时间", sortable: true, align: "left", formatter: formatDate},
				{name: "comedate", index: "comedate", width: 110, label: "工种进场时间", sortable: true, align: "left", formatter: formatDate},
				{name: "mincrtdate", index: "mincrtdate", width: 106, label: "最早签到时间", sortable: true, align: "left", formatter: formatTime},
				{name: "maxcrtdate", index: "maxcrtdate", width: 105, label: "最迟签到时间", sortable: true, align: "left", formatter: formatTime},
				{name: "signprjitem2", index: "signprjitem2", width: 100, label: "签到阶段", sortable: true, align: "left"},
				{name: "constructday", index: "constructday", width: 65, label: "工期", sortable: true, align: "right"},
				{name: "planend", index: "planend", width: 100, label: "计划完工时间", sortable: true, align: "left", formatter: formatDate},
				{name: "conplanend", index: "conplanend", width: 100, label: "完工报备时间", sortable: true, align: "left", formatter: formatDate},
				{name: "enddate", index: "enddate", width: 100, label: "实际完工时间", sortable: true, align: "left", formatter: formatTime},
				{name: "actualdays", index: "actualdays", width: 70, label: "实际工期", sortable: true, align: "left"},
				{name: "worksigndate", index: "worksigndate", width: 100, label: "工人完成日期", sortable: true, align: "left", formatter: formatTime},
				{name: "prjpassdate", index: "prjpassdate", width: 100, label: "初检日期", sortable: true, align: "left", formatter: formatTime},
				{name: "isintimecomplete", index: "isintimecomplete", width: 93, label: "是否及时完成", sortable: true, align: "left"},
				{name: "iscompletedescr", index: "iscompletedescr", width: 70, label: "阶段完成", sortable: true, align: "left"},
				{name: "confirmamount", index: "confirmamount", width: 80, label: "已发工资", sortable: true, align: "right", sum: true},
				{name: "appamount", index: "appamount", width: 80, label: "已申请工资", sortable: true, align: "right", sum: true},
				{name: "confirmdate", index: "confirmdate", width: 110, label: "验收时间", sortable: true, align: "left", formatter: formatTime},
				{name: "yqconfirmdate", index: "yqconfirmdate", width: 110, label: "底漆验收时间", sortable: true, align: "left", formatter: formatTime},
				{name: "confirmczydescr", index: "confirmczydescr", width: 75, label: "验收人", sortable: true, align: "left"},
				{name: "prjleveldescr", index: "prjleveldescr", width: 60, label: "评级", sortable: true, align: "left"},
				{name: "issigndescr", index: "issigndescr", width: 75, label: "签约类型", sortable: true, align: "left"},
				{name: "issysarrangedescr", index: "issysarrangedescr", width: 93, label: "是否系统安排", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 75, label: "是否停工", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 160, label: "备注", sortable: true, align: "left"},
				{name: "comedelaydescr", index: "comedelaydescr", width: 100, label: "进场延误原因", sortable: true, align: "left"},
				{name: "enddelaydescr", index: "enddelaydescr", width: 100, label: "完工延误原因", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 75, label: "客户编号", sortable: true, align: "left"},
				{name: "lastupdatedby", index: "lastupdatedby", width: 110, label: "最后修改人", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 122, label: "修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "custworkpk", index: "custworkpk", width: 122, label: "custworkpk", sortable: true, align: "left",hidden:true},
				{name: "appstatus", index: "appstatus", width: 122, label: "appstatus", sortable: true, align: "left",hidden:true},
				{name: "apppk", index: "apppk", width: 122, label: "apppk", sortable: true, align: "left",hidden:true},
				{name: "prjitme2", index: "prjitme2", width: 122, label: "prjitme2", sortable: true, align: "left",hidden:true},
				{name: "worktype12", index: "worktype12",width: 122, label: "worktype12", sortable: true, align: "left",hidden:true },
				{name: "custcode", index: "custcode",width: 122, label: "custcode", sortable: true, align: "left",hidden:true },
				{name: "workercode", index: "workercode",width: 122, label: "workercode", sortable: true, align: "left",hidden:true }
			],
			loadComplete: function(){
	        	frozenHeightReset("dataTable");
	        },
		});
		$("#dataTable").jqGrid('setFrozenColumns');

		$("#save").on("click",function(){
			Global.Dialog.showDialog("Save",{
				title:"工地工人安排——新增",
				url:"${ctx}/admin/custWorker/goSave",
				height:600,
				width:750,
				returnFun:goto_query
			});
		});
		$("#update").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if("${hasAuthByCzybh}"!="true"&&"${czybm}"!=$.trim(ret.lastupdatedby)){
				art.dialog({
	       			content: "您没有修改的权限！",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("Update",{
				title:"工地工人安排——编辑",
				url:"${ctx}/admin/custWorker/goUpdate",
				postData:{pk:ret.pk,apppk:ret.apppk},
				height:600,
				width:750,
				returnFun:goto_query
			});
		});
		
		$("#modifyWater").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("Update",{
				title:"工地工人安排——修改水电发包",
				url:"${ctx}/admin/custWorker/goModifyWater",
				postData:{pk:ret.pk,apppk:ret.apppk},
				height:600,
				width:750,
				returnFun:goto_query
			});
		});
		
		//查看
		$("#view").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("view",{
				title:"工地工人安排——查看",
				url:"${ctx}/admin/custWorker/goView",
				postData:{pk:ret.pk,actualDays:ret.actualdays},
				height:500,
				width:750,
				returnFun:goto_query
			});
		});
		
		$("#viewSign").on("click",function(){
			ret=selectDataTableRow();
			Global.Dialog.showDialog("view",{
				title:"工地工人安排——签到明细",
				url:"${ctx}/admin/custWorker/goViewSign",
				//postData:{},
				height:700,
				width:1030,
				returnFun:goto_query
			});
		});
		
		$("#del").on("click",function(){
			var ret = selectDataTableRow();
			if(ret.status=="2"){
				art.dialog({
					content:"已安排状态不能删除",
				});
				return false;
			}	
			if(ret.custworkpk!="" && ret.appstatus=="2"){
				art.dialog({
					content:"存在相应的工人申请记录，不允许删除",
				});
				return false;
			}	
			art.dialog({
				content:"是否要删除工地工人安排["+ret.pk+"]的信息",
				lock: true,
				width: 200,
				height: 100,
				ok: function () {
					$.ajax({
						url:"${ctx}/admin/custWorker/doDel",
						type:"post",
						data:{pk:ret.pk},
						dataType:"json",
						cache:false,
						error:function(obj){
							showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
						},
						success:function(obj){
							art.dialog({
								content: obj.msg,
							});
							$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
						}
					});
				},
				cancel: function () {
					return true;
				}
			});	
		});
		
		$("#waterAftInsItemApp").on("click",function(){
			var ret = selectDataTableRow();
			if(ret){
				console.log(ret);
				if(ret.worktype12.trim()!="02"){
					art.dialog({
						content:"只有工种为水电的才可以查看水电安装后期材料",
					});
					return false;
				}
				Global.Dialog.showDialog("waterAftInsItemApp",{
					title:"工地工人安排——查看水电安装后期材料",
					url:"${ctx}/admin/custWorker/goWaterAftInsItemApp",
					postData:{custCode:ret.custcode,workerCode:ret.workercode,workerName:ret.workercodedescr,address:ret.address},
					height:700,
					width:1030,
					returnFun:goto_query
				});
			
			}else{
				art.dialog({content:"请选择一条记录！"});
			}
		});
		
		$("#workerDetail").on("click",function(){
			var ret = selectDataTableRow();
			Global.Dialog.showDialog("workerDetail",{
				title:"工人汇总分析",
				url:"${ctx}/admin/CustWorkerApp/goWorkerDetail",
				height:750,
				width:1000,
				returnFun:goto_query
			});
		});
		
		$("#signComp").on("click",function(){
			var ret = selectDataTableRow();
			if(ret){
				art.dialog({
					content:"是否确定工地工人安排["+ret.pk+"]签到完成",
					lock: true,
					width: 200,
					height: 100,
					ok: function () {
						$.ajax({
							url:"${ctx}/admin/custWorker/doSignComp",
							type:"post",
							data:{pk:ret.pk},
							dataType:"json",
							cache:false,
							error:function(obj){
								showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
							},
							success:function(obj){
								art.dialog({
									content:"签到完成成功！",
								});
							}
						});
					},
					cancel: function () {
						return true;
					}
				});
			}else{
				art.dialog({content:"请选择一条记录！"});
			}
		});
		//查看签到
		$("#viewSignDetail").on("click", function () {
			var ret = selectDataTableRow();
			if(ret){
				Global.Dialog.showDialog("viewSignDetail",{
					title:"工地工人安排——查看该工地签到明细",
					url:"${ctx}/admin/custWorker/goViewSignDetail",
					postData:{custCode: ret.custcode, workType12: ret.worktype12},
					height:539,
					width:1054,
				});
			}else{
				art.dialog({
					content:"请选择一条数据",
				});
			}
		});
		
		$("#viewPrjprog").on("click", function(){
			var ret = selectDataTableRow();
			if(ret){
				Global.Dialog.showDialog("viewPrjProg",{
					title:"工地工人安排——查看进度",
					url:"${ctx}/admin/custWorker/goViewPrjProg",
					postData:{code:ret.custcode},
					width:1100,
					height:715
				});
			}else{
				art.dialog({
					content:"请选择一条数据",
				});
			}
		});
		
		//查看
		$("#delayRemark").on("click",function(){
			ret=selectDataTableRow();
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			Global.Dialog.showDialog("delayRemark",{
				title:"工地工人安排——延误备注",
				url:"${ctx}/admin/custWorker/goDelayRemark",
				postData:{pk:ret.pk},
				height:325,
				width:530,
				returnFun:goto_query
			});
		});
	});
	function loadWorkType12Dept(){
		//$("#prjRegionCode").val("0001");
		
		$("#workType12Dept").find("option").remove();
		$("#workType12Dept").append("<option value=''''>"+"请选择..."+"</option>");
		$.ajax({
			url:"${ctx}/admin/custWorker/getWorkType12Dept",
			type:"post",
			data:{workType12:$("#workType12").val()},
			dataType:"json",
			cache:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
			},
			success:function(obj){
				if(obj.length>0){
				    for(var i=0;i<obj.length;i++){
						$("#workType12Dept").append("<option value='"+obj[i].Code+"'>"+obj[i].Descr+"</option>");
				    }
				    $("#workType12Dept").searchableSelect();
				}
			}
		});
	}
	function doExcel(){
		var url = "${ctx}/admin/custWorker/doExcel";
		doExcelAll(url);
	}
	
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#openComponent_worker_workerCode").val('');
		$("#openComponent_enployee_projectMan").val('');
		$("#page_form select").val('');
		$("#workerCode").val('');
		$("#projectMan").val('');
		$("#workType12").val('');
		$("#department2").val('');
		$("#region").val('');
		
		$("#workType12_NAME").val('');
		$("#department2_NAME").val('');
		$("#region_NAME").val('');
		$("#workerClassify").val('');
		
		$.fn.zTree.getZTreeObj("tree_workType12").checkAllNodes(false);
		$.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
		$.fn.zTree.getZTreeObj("tree_region").checkAllNodes(false);
		$.fn.zTree.getZTreeObj("tree_workerClassify").checkAllNodes(false);
	} 
	function chgSearchType(){
		if($.trim($("#searchType").val())=="1" || $.trim($("#searchType").val())==""){
			$("#status").removeAttr("disabled","true");
		}else{
			$("#status").val("0").change();
			$("#status").attr("disabled","true");
		}
	}
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<li>
							<label>查询方式</label>
							<select id = "searchType" name = "searchType" style="width:160px" onchange="chgSearchType()" value="1">
								<option value="">请选择...</option>
								<option value="1" selected>所有在建</option>
								<option value="2">超期在建</option>
								<option value="3">首日未进场</option>
								<option value="4">今日未签到</option>
							</select>
						</li>
						<li>
							<label>工种</label>
							<house:DictMulitSelect id="workType12" dictCode="" sql="select a.* from tWorkType12 a where  (
							(select PrjRole from tCZYBM where CZYBH='${czybm }') is null 
							or( select PrjRole from tCZYBM where CZYBH='${czybm }') ='' ) or  Code in(
								select WorkType12 From tprjroleworktype12 pr where pr.prjrole = 
								(select PrjRole from tCZYBM where CZYBH='${czybm }') or pr.prjrole = ''
								 )  order by dispSeq "  onCheck="loadWorkType12Dept()"
							sqlValueKey="Code" sqlLableKey="Descr"></house:DictMulitSelect>
						</li>
						<li>
							<label>工人编号</label>
							<input type="text" id="workerCode" name="workerCode"/>
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address"/>
						</li>
						<li>
							<label>状态</label>
							<select id="status" name="status" value="0">
								<option value="">请选择...</option>
								<option value="0" selected="selected">0 施工中</option>
								<option value="1">1 完成</option>
							</select>
						</li>
						<li>
							<label>工程大区</label>
							<house:dict id="prjRegionCode" dictCode="" sql="select  Code,code+' '+descr Descr from tPrjRegion where expired ='F' " 
							sqlValueKey="Code" sqlLableKey="Descr" ></house:dict>
						</li>
						<li>
							<label>工人分类</label>
							<house:xtdmMulit id="workerClassify" dictCode="WORKERCLASSIFY"/>
						</li>
						<li>
							<label>工程部</label>
							<house:DictMulitSelect id="department2" dictCode="" sql="select a.Code, a.desc1+' '+isnull(e.NameChi,'') desc1  from dbo.tDepartment2 a
									left join tEmployee e on e.Department2=a.Code and e.IsLead='1' and e.LeadLevel='1' and e.expired='F'
									 where  a.deptype='3' and a.Expired='F' order By dispSeq " 
							sqlLableKey="desc1" sqlValueKey="code"></house:DictMulitSelect>
						</li>
						<li >
							<label>分组</label>
							<select id="workType12Dept" name="workType12Dept"> 
								<option value="">请选择...</option>
							</select>
							<%-- <house:dict id="workType12Dept" dictCode="" sql="select Code,Code+' '+Descr Descr from tWorkType12Dept where expired='F' "
							 sqlValueKey="Code" sqlLableKey="Descr"></house:dict> --%>
						</li>
						<li id="loadMore" >
							<button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">更多</button>
							<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
						</li>
						<div  class="collapse " id="collapse" >
							<ul>
								<li>
									<label>进场时间从</label>
									<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
								<li>
									<label>至</label>
									<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
								<li>
									<label>计划完成时间从</label>
									<input type="text" id="planEndDateFrom" name="planEndDateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
								<li>
									<label>至</label>
									<input type="text" id="planEndDateTo" name="planEndDateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
								<li>
									<label>结束时间从</label>
									<input type="text" id="endDateFrom" name="endDateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
								<li>
									<label>至</label>
									<input type="text" id="endDateTo" name="endDateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
								<li>
									<label>监理</label>
									<input type="text" id="projectMan" name="projectMan"/>
								</li>
								<li>
									<label>是否停工</label>
									<house:xtdm id="m_umState" dictCode="CUSTWKSTATUS" style="width:160px;"></house:xtdm>
								</li>
								<li>
									<label>片区</label>
									<house:DictMulitSelect id="region" dictCode="" 
									sql="select code,descr from tRegion where 1=1 and expired='F' " sqlLableKey="Descr" sqlValueKey="Code"></house:DictMulitSelect>
								</li>
								<li>
									<label>客户类型</label>
									<house:xtdmMulit id="custType" dictCode="" sql="select code,desc1 descr from tcustType where 1=1 and expired='F' " 
									sqlValueKey="Code" sqlLableKey="Descr"></house:xtdmMulit>
								</li>
								<li>
									<label>施工方式</label>
									<house:xtdm id="constructType" dictCode="CONSTRUCTTYPE" style="width:160px;"></house:xtdm>
								</li>
								<li>
									<label>水电发包工人</label>
									<house:xtdm  id="isWaterItemCtrl" dictCode="YESNO"   style="width:160px;"></house:xtdm>
								</li>
								<li class="search-group-shrink" >
									<button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse" >收起</button>
									<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
									<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
								</li>
							</ul>
						</div>
					</ul>
				</form>
			</div>
		</div>
		<div class="clear_float"></div>
		<div class="btn-panel">
			<div class="btn-group-sm">
	  			<house:authorize authCode="CUSTWORKER_ADD">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTWORKER_UPDATE">
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
				</house:authorize>
 			  	<house:authorize authCode="CUSTWORKER_MODIFYWATER">
					<button type="button" class="btn btn-system" id="modifyWater">
						<span>修改水电发包</span>
					</button>
 			  	</house:authorize>
				<house:authorize authCode="CUSTWORKER_DELETE">
					<button type="button" class="btn btn-system" id="del">
						<span>删除</span>
					</button>
				</house:authorize>	
				<house:authorize authCode="CUSTWORKER_VIEW">
					<button type="button" class="btn btn-system" id="view">
						<span>查看</span>
					</button>
 			  	</house:authorize>
 			  	<house:authorize authCode="CUSTWORKER_VIEWSIGNDETAIL">
					<button type="button" class="btn btn-system" id="viewSignDetail">
						<span>查看签到</span>
					</button>
 			  	</house:authorize>
 			  	<house:authorize authCode="CUSTWORKER_VIEWSIGN">
					<button type="button" class="btn btn-system" id="viewSign">
						<span>签到明细查询</span>
					</button>
 			  	</house:authorize>
 			  	<house:authorize authCode="CUSTWORKER_WATERAFTINSITEMAPP">
					<button type="button" class="btn btn-system" id="waterAftInsItemApp">
						<span>查看水电后期安装材料</span>
					</button>
				</house:authorize>
 			  		<house:authorize authCode="CUSTWORKER_VIEWPRJPROG">
					<button type="button" class="btn btn-system" id="viewPrjprog">
						<span>查看进度</span>
					</button>
				</house:authorize>
 			  	<house:authorize authCode="CUSTWORKER_WORKERDETAIL">
					<button type="button" class="btn btn-system" id="workerDetail">
						<span>工人汇总分析</span>
					</button>
				</house:authorize>
				<house:authorize authCode="CUSTWORKER_SIGNCOMP">
					<button type="button" class="btn btn-system" id="signComp">
						<span>签到完成</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system" id="delayRemark">
					<span>延误备注</span>
				</button>
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>导出excel</span>
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</body>	
</html>
