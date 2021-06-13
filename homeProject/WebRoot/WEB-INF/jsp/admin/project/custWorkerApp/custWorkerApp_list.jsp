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
	<title>工程部工人安排</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_autoArr.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	function getMainSteward(data){
		if(!data) return;
		$("#mainStewardDescr").val(data.namechi);
	}
		$("#workerCode").openComponent_worker();	
		$("#custSceneDesigner").openComponent_employee();	
		$("#mainSteward").openComponent_employee({callBack:getMainSteward});	
	
	
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/CustWorkerApp/goJqGrid",
		postData:{status:"1"},
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap', 
		colModel : [
				{name:'shouldpay',	index:'shouldpay',	width:100,	label:'应付款额',	sortable:true,align:"left",hidden:true },
				{name:'prjnormday',	index:'prjnormday',	width:130,	label:'prjnormday',	sortable:true,align:"left" ,hidden:true },
				{name:'prjitem',	index:'prjitem',	width:130,	label:'prjitem',	sortable:true,align:"left" ,hidden:true },
				{name:'pk',	index:'pk',	width:130,	label:'pk',	sortable:true,align:"left",hidden:true},
				{name:'address',	index:'address',	width:130,	label:'楼盘',	sortable:true,align:"left", },
				{name:'regiondescr',	index:'regiondescr',	width:75,	label:'一级区域',	sortable:true,align:"left", },
				{name:'relcustdescr',	index:'relcustdescr',	width:75,	label:'关系客户',	sortable:true,align:"left", },
				{name:'custtype',	index:'custtype',	width:90,	label:'客户类型',	sortable:true,align:"left", hidden:true},
				{name:'custtypedescr',	index:'custtypedescr',	width:70,	label:'客户类型',	sortable:true,align:"left", },
				{name:'area',	index:'area',	width:50,	label:'面积',	sortable:true,align:"right", },
				{name:'innerarea',	index:'innerarea',	width:80,	label:'套内面积',	sortable:true,align:"right", },
				// {name:'department1',	index:'department1',	width:100,	label:'工程事业部',	sortable:true,align:"left", hidden:true},
				// {name:'department1descr',	index:'department1descr',	width:80,	label:'工程事业部',	sortable:true,align:"left", },
				{name:'department2descr',	index:'department2descr',	width:60,	label:'工程部',	sortable:true,align:"left", },
				{name:'department2',	index:'department2',	width:90,	label:'工程部',	sortable:true,align:"left", hidden:true},
				{name:'projectman',	index:'projectman',	width:90,	label:'监理',	sortable:true,align:"left", hidden:true},
				{name:'projectmandescr',	index:'projectmandescr',	width:60,	label:'监理',	sortable:true,align:"left", },
				{name:'businessphone',	index:'businessphone',	width:90,	label:'电话',	sortable:true,align:"left", },
				{name:'worktype12',	index:'worktype12',	width:90,	label:'工种',	sortable:true,align:"left",hidden:true },
				{name:'worktype12descr',	index:'worktype12descr',	width:60,	label:'工种',	sortable:true,align:"left", },
				{name:'appdate',	index:'appdate',	width:80,	label:'申报时间',	sortable:true,align:"left",formatter: formatTime},
				{name:'appcomedate',	index:'appcomedate',	width:100,	label:'申报进场时间',	sortable:true,align:"left", formatter: formatDate},
				{name:'remarks',	index:'remarks',	width:160,	label:'备注',	sortable:true,align:"left", },
				{name:'arrtimes',	index:'arrtimes',	width:70,	label:'安排次数',	sortable:true,align:"right", },
				{name:'workerdescr',	index:'workerdescr',	width:70,	label:'工人名称',	sortable:true,align:"left", },
				{name:'comedate',	index:'comedate',	width:80,	label:'进场时间',	sortable:true,align:"left",formatter: formatDate },
				{name:'workload',	index:'workload',	width:70,	label:'工期',	sortable:true,align:"right", },
				{name:'arrdate',	index:'arrdate',	width:130,	label:'安排时间',	sortable:true,align:"left",formatter:formatTime },
				{name:'comedelaytypedescr',	index:'comedelaytypedescr',	width:120,	label:'安排进场延误原因',	sortable:true,align:"left", },
				{name:'signdelaytypedescr',	index:'signdelaytypedescr',	width:93,	label:'签到延误原因',	sortable:true,align:"left", },
				{name:'issysarrangedescr',	index:'issysarrangedescr',	width:93,	label:'是否系统安排',	sortable:true,align:"left", },
				{name:'issysarrange',	index:'issysarrange',	width:93,	label:'是否系统安排',hidden:true,	sortable:true,align:"left", },
				{name:'confirmczy',	hidden:true,index:'confirmczy',	width:70,	label:'验收人',	sortable:true,align:"left", },
				{name:'confirmdescr',	index:'confirmdescr',	width:70,	label:'验收人',	sortable:true,align:"left", },
				{name:'confirmdate',	index:'confirmdate',	width:80,	label:'验收时间',	sortable:true,align:"left", formatter: formatDate},
				{name:'status',	index:'status',	width:90,	label:'状态',	sortable:true,align:"left", hidden:true},
				{name:'statusdescr',	index:'statusdescr',	width:90,	label:'状态',	sortable:true,align:"left", },
				{name:'mainstewarddescr',	index:'mainstewarddescr',	width:70,	label:'主材管家',	sortable:true,align:"left", },
				{name:'lastupdatedby',	index:'lastupdatedby',	width:90,	label:'最后更新人',	sortable:true,align:"left", },
				{name:'lastupdate',	index:'lastupdate',	width:110,	label:'最后更新时间',	sortable:true,align:"left",formatter:formatTime },
		],
	});
	
	$("#save").on("click",function(){
		Global.Dialog.showDialog("Save",{
			title:"工程部工人申请——新增",
			url:"${ctx}/admin/CustWorkerApp/goSave",
			height:600,
			width:750,
			returnFun:goto_query
		});
	});
	
	$("#print").on("click",function(){
		Global.Dialog.showDialog("Print",{
			title:"工程部工人申请——打印",
			url:"${ctx}/admin/CustWorkerApp/goPrint",
			height:350,
			width:800,
			returnFun:goto_query
		});
	});
	
	$("#saveArr").on("click",function(){
		var ret = selectDataTableRow();
		if(ret.status=='2'){
			art.dialog({
				content:'已安排状态只可修改',
			});
			return false;
		}
		if(ret.status=='3'){
			art.dialog({
				content:'取消状态不可安排',
			});
			return false;
		}
		if(ret.prjnormday==''){
			prjnormday=0;
		}else{
			prjnormday=ret.prjnormday;
		}
		Global.Dialog.showDialog("SaveArr",{
			title:"工程部工人申请——安排",
			url:"${ctx}/admin/CustWorkerApp/goSaveArr",
			postData:{pk:ret.pk,prjnormday:prjnormday,prjRegion:$.trim($("#prjRegion").val()),arrTimes:ret.arrtimes},
			height:600,
			width:750,
			returnFun:goto_query
		});
	});
	
	$("#updateApp").on("click",function(){
		var ret = selectDataTableRow();
		if(ret.status!='1'){
			art.dialog({
				content:'不是申请状态不可修改',
			});
			return false;
		}
		Global.Dialog.showDialog("UpdateApp",{
			title:"工程部工人申请——编辑",
			url:"${ctx}/admin/CustWorkerApp/goUpdateApp?pk="+ret.pk+" & prjRegion="+$.trim($("#prjRegion").val()),
			height:600,
			width:750,
			returnFun:goto_query
		});
	});
	
	$("#updateArr").on("click",function(){
		var ret = selectDataTableRow();
		if(ret.status=='1'){
			art.dialog({
				content:'申请状态不可修改安排',
			});
			return false;
		}
		if(ret.status=='3'){
			art.dialog({
				content:'取消状态不可修改安排',
			});
			return false;
		}
		if(ret.prjnormday==''){
			prjnormday=0;
		}else{
			prjnormday=ret.prjnormday;
		}
		Global.Dialog.showDialog("UpdateApp",{
			title:"工程部工人安排——编辑",
			url:"${ctx}/admin/CustWorkerApp/goUpdateArr",
			postData:{pk:ret.pk,prjnormday:prjnormday,prjRegion:$.trim($("#prjRegion").val()),arrTimes:ret.arrtimes},
			height:600,
			width:750,
			returnFun:goto_query
		});
	});
	
	$("#view").on("click",function(){
		var ret = selectDataTableRow();
		Global.Dialog.showDialog("UpdateApp",{
			title:"工程部工人安排——查看",
			url:"${ctx}/admin/CustWorkerApp/goView?pk="+ret.pk+"&arrTimes="+ret.arrtimes,
			height:600,
			width:750,
			returnFun:goto_query
		});
	});
	
	$("#workerDetail").on("click",function(){
		var ret = selectDataTableRow();
		Global.Dialog.showDialog("workerDetail",{
			title:"工程部工人安排——班组汇总",
			url:"${ctx}/admin/CustWorkerApp/goWorkerDetail",
			height:750,
			width:1000,
			returnFun:goto_query
		});
	});
	
	$("#viewAutoArr").on("click",function(){
		Global.Dialog.showDialog("viewAutoArr",{
			title:"工程部工人安排——安排结果",
			url:"${ctx}/admin/CustWorkerApp/goViewAutoArr",
			height:720,
			width:1150,
			returnFun:goto_query
		});
	});
	
	$("#viewReturn").on("click",function(){
		Global.Dialog.showDialog("viewReturn",{
			title:"工程部工人安排——退回查看",
			url:"${ctx}/admin/CustWorkerApp/goViewReturn",
			height:720,
			width:1150,
			returnFun:goto_query
		});
	});
	
	$("#doAutoArr").on("click",function(){
		art.dialog({
			content:"是否自动安排",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/CustWorkerApp/doAutoArr",
					type:'post',
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						art.dialog({
							content:'自动安排成功',
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
	
	$("#del").on("click",function(){
		var ret = selectDataTableRow();
		if(ret.status=='2'){
			art.dialog({
				content:"已安排状态不能删除",
			});
			return false;
		}		
		art.dialog({
				 content:"是否删除",
				 lock: true,
				 ok: function () {
					$.ajax({
						url:"${ctx}/admin/CustWorkerApp/doDel",
						type:'post',
						data:{pk:ret.pk},
						dataType:'json',
						cache:false,
						error:function(obj){
							showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
						},
						success:function(obj){
							art.dialog({
								content:'删除成功',
								time: 1000,
								beforeunload: function () {
									$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
							    }
							});
						}
					});
				},
				cancel: function () {
						return true;
				}
		});	
	});
	
	$("#cancel").on("click",function(){
		var ret = selectDataTableRow();
		if(ret.status=='2'){
			art.dialog({
				content:"已安排状态不能取消",
			});
			return false;
		}
		if(ret.status=='3'){
			art.dialog({
				content:"取消状态，不许要再次取消",
			});
			return false;
		}
				
		art.dialog({
				 content:"是否取消",
				 lock: true,
				 ok: function () {
					$.ajax({
						url:"${ctx}/admin/CustWorkerApp/doCancel",
						type:'post',
						data:{pk:ret.pk},
						dataType:'json',
						cache:false,
						error:function(obj){
							showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
						},
						success:function(obj){
							art.dialog({
								content:'取消成功',
								time: 1000,
								beforeunload: function () {
									$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
							    }
							});
							
						}
					});
				},
				cancel: function () {
						return true;
				}
		});	
	});
	
});
	
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	
	$("#workType12_NAME").val('');
	$("#department2_NAME").val('');
	$("#workType12").val('');
	$("#department2").val('');
	
	$("#dateTo").val('');
	$("#dateFrom").val('');
	$("#address").val('');
	$.fn.zTree.getZTreeObj("tree_workType12").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
	
	$("#intDepartment2").val('');
    $.fn.zTree.getZTreeObj("tree_intDepartment2").checkAllNodes(false);
} 
function doExcel(){
			var url = "${ctx}/admin/CustWorkerApp/doExcel";
			doExcelAll(url);
	}
function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/CustWorkerApp/goJqGrid",postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
}

function changeArrangeable(obj){
	if ($(obj).is(':checked')){
		$('#arrangeable').val('1');
	}else{
		$('#arrangeable').val('');
	}
}   

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="mainStewardDescr" id="mainStewardDescr"/>
						<ul class="ul-form">
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${custWorkerApp.address }"/>
							</li>
							<li>
								<label>状态</label>
								<house:xtdm  id="status" dictCode="WORKERAPPSTS"   style="width:160px;" value="${custWorkerApp.status}"></house:xtdm>
							</li>
							<li>
								<label>工种</label>
								<house:DictMulitSelect id="workType12" dictCode="" sql="select a.* from tWorkType12 a where  (
								(select PrjRole from tCZYBM where CZYBH='${czybm }') is null 
								or( select PrjRole from tCZYBM where CZYBH='${czybm }') ='' ) or  Code in(
									select WorkType12 From tprjroleworktype12 pr where pr.prjrole = 
									(select PrjRole from tCZYBM where CZYBH='${czybm }') or pr.prjrole = ''
									 )  order By a.DispSeq " 
								sqlValueKey="Code" sqlLableKey="Descr"  ></house:DictMulitSelect>
							</li>
							<li>
								<label>工程大区</label>
								<house:dict id="prjRegion" dictCode="" sql="select  Code,code+' '+descr Descr from tPrjRegion where expired ='F' " 
								sqlValueKey="Code" sqlLableKey="Descr" ></house:dict>
							</li>
							<li>
								<label>工人编号</label>
								<input type="text" id="workerCode" name="workerCode" style="width:160px;" value="${custWorkerApp.workerCode }"/>
							</li>
							<li>
								<label>工程部</label>
								<house:DictMulitSelect id="department2" dictCode="" sql="select a.Code, a.desc1+' '+isnull(e.NameChi,'') desc1  from dbo.tDepartment2 a
										left join tEmployee e on e.Department2=a.Code and e.IsLead='1' and e.LeadLevel='1' and e.expired='F'
										 where  a.deptype='3' and a.Expired='F' order By dispSeq " 
								sqlLableKey="desc1" sqlValueKey="code" selectedValue="${custWorkerApp.department2 }"  ></house:DictMulitSelect>
							</li>
							<li>
								<label>现场设计师</label>
								<input type="text" id="custSceneDesigner" name="custSceneDesigner" style="width:160px;" />
							</li>
							<li>
								<label>专盘</label>
								<house:xtdm  id="isPrjSpc" dictCode="YESNO"   style="width:160px;" value="${custWorkerApp.isPrjSpc}"></house:xtdm>
							</li>
							<li>
								<label>主材管家</label>
								<input type="text" id="mainSteward" name="mainSteward" style="width:160px;" />
							</li>
							<li>
								<label>一级区域</label>
								<house:dict id="region" dictCode="" sql="SELECT Code,Descr FROM tRegion WHERE 1=1 and expired='F' " sqlValueKey="Code" sqlLableKey="Descr" value="${prjConfirmApp.prjItem }"></house:dict>
							</li>
							<li>
								<label>自动安排</label>
								<house:xtdm  id="isAutoArrange" dictCode="YESNO"   style="width:160px;" value="${custWorkerApp.isAutoArrange}"></house:xtdm>
							</li>
							<li>
								<label>申请时间从</label>
								<input type="text" id="appDateFrom" name="appDateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="appDateTo" name="appDateTo" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
							<li>
								<label>进场时间</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
							<li>
			                    <label>集成部</label>
			                    <house:DictMulitSelect id="intDepartment2" dictCode=""
			                        sql="select Code SQL_VALUE_KEY, Desc1 SQL_LABEL_KEY from tDepartment2 where DepType = '6' and Expired = 'F'"></house:DictMulitSelect>
			                </li>
							<li style="width:150px">	
								<input type="checkbox" id="arrangeable" name="arrangeable" value="" onclick="changeArrangeable(this)" ${custWorkerApp=='1'?'checked':''  }/>仅显示可安排&nbsp;
 							</li>	
							<li >
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
					</ul>
				</form>
			</div>
			<div class="pageContent">
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
						<house:authorize authCode="CUSTWORKERAPP_SAVE">
							<button type="button" class="btn btn-system " id="save"><span>新增</span></button>
						</house:authorize>
						
						<house:authorize authCode="CUSTWORKERAPP_UPDATEAPP">
							<button type="button" class="btn btn-system " id="updateApp"><span>修改申请</span></button>
						</house:authorize>
						
						<house:authorize authCode="CUSTWORKERAPP_SAVEARR">
							<button type="button" class="btn btn-system " id="saveArr"><span>工人安排</span></button>
						</house:authorize>
						
						<house:authorize authCode="CUSTWORKERAPP_UPDATEARR">
							<button type="button" class="btn btn-system " id="updateArr"><span>修改安排</span></button>
						</house:authorize>

						<house:authorize authCode="CUSTWORKERAPP_AUTOARR">
							<button type="button" class="btn btn-system " id="doAutoArr"><span>自动安排</span></button>
						</house:authorize>
						
						<house:authorize authCode="CUSTWORKERAPP__DELETE">
							<button type="button" class="btn btn-system " id="del"><span>删除</span></button>
						</house:authorize>
						
						<house:authorize authCode="CUSTWORKERAPP_CANCEL">
							<button type="button" class="btn btn-system " id="cancel"><span>取消</span></button>
						</house:authorize>
						
						<house:authorize authCode="CUSTWORKERAPP_VIEW">
							<button type="button" class="btn btn-system " id="view"><span>查看</span></button>
						</house:authorize>
					
							<button type="button" class="btn btn-system " id="workerDetail"><span>班组汇总信息</span></button>
					
						<house:authorize authCode="SPCBUILDER_VIEWAUTOARR">
							<button type="button" class="btn btn-system " id="viewAutoArr"><span>查看安排结果</span></button>
						</house:authorize>
						
						<button type="button" class="btn btn-system " id="viewReturn"><span>退回查询</span></button>
						
						<button type="button" class="btn btn-system " id="print"><span>打印</span></button>
						
						<button type="button" class="btn btn-system " onclick="doExcel()" title="导出当前excel数据" >
							<span>导出Excel</span>
						</button>
					</div>
				</div>
			</div>
			
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</body>	
</html>
