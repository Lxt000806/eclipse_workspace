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
	<title>资料管理列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
function doExcel(){
	var url = "${ctx}/admin/custDoc/doExcel";
	doExcelAll(url);
}
$(function(){
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/custDoc/goJqGrid",
		postData:{custStatus:"4,3,2,1",custType:"${custTypes }"},
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'code',	index:'code',	width:90,	label:'客户编号',	sortable:true,align:"left"},
			{name:'documentno',	index:'documentno',	width:90,	label:'档案编号',	sortable:true,align:"left"},
			{name:'address',	index:'address',	width:120,	label:'楼盘',	sortable:true,align:"left"},
			{name:'regiondescr',index:'regiondescr',width:60,label:'片区',sortable:true,align:"left"},
			{name:'status',	index:'status',	width:75,	label:'客户状态',	sortable:true,align:"left",hidden:true},
			{name:'custstatusdescr',	index:'custstatusdescr',	width:75,	label:'客户状态',	sortable:true,align:"left"},
			{name:'custtypedescr',	index:'custtypedescr',	width:90,	label:'客户类型',	sortable:true,align:"left"},
			{name:'isinitsigndescr',	index:'isinitsigndescr',	width:80,	label:'是否草签',	sortable:true,align:"left"},
			{name:'designmandescr',	index:'designmandescr',	width:90,	label:'设计师',	sortable:true,align:"left"},
			{name:'designmandept2',	index:'designmandept2',	width:90,	label:'设计所',	sortable:true,align:"left"},
			{name: 'department1descr', index: 'department1descr', width: 90, label: '一级部门', sortable: true, align: "left"},
			{name:'layoutdescr',	index:'layoutdescr',	width:75,	label:'户型',	sortable:true,align:"left"},
			{name:'area',	index:'area',	width:70,	label:'面积',	sortable:true,align:"right"},
			{name:'innerarea',	index:'innerarea',	width:70,	label:'套内面积',	sortable:true,align:"right"},
			{name:'setdate',	index:'setdate',	width:90,	label:'下定时间',	sortable:true,align:"left" ,formatter:formatDate},
			{name:'signdate',	index:'signdate',	width:90,	label:'签订时间',	sortable:true,align:"left" ,formatter:formatDate},
			{name:'submitdate',	index:'submitdate',	width:115,	label:'提交审核时间',	sortable:true,align:"left" ,formatter:formatTime},
			{name:'passdate',	index:'passdate',	width:115,	label:'审核通过时间',	sortable:true,align:"left" ,formatter:formatTime},
			{name:'picstatusdescr',	index:'picstatusdescr',	width:80,	label:'设计图状态',	sortable:true,align:"left"},
			{name:'picstatus',	index:'picstatus',	width:90,	label:'设计图状态',	sortable:true,align:"left" ,hidden:true},
			{name:'docnum2',	index:'docnum2',	width:80,	label:'效果图数',	sortable:true,align:"right"},
			{name:'docnum3',	index:'docnum3',	width:80,	label:'平面图数',	sortable:true,align:"right"},
			{name:'docnum6',	index:'docnum6',	width:80,	label:'cad文件数',	sortable:true,align:"right"},
			{name:'docnum4',	index:'docnum4',	width:80,	label:'软装图纸数',	sortable:true,align:"right"},
			{name:'docnum5',	index:'docnum5',	width:80,	label:'集成图纸数',	sortable:true,align:"right"},
			{name:'docnum1',	index:'docnum1',	width:80,	label:'合同图片数',	sortable:true,align:"right"},
			{name:'confirmremark',	index:'confirmremark',	width:150,	label:'审核说明',	sortable:true,align:"left"},
			{name:'isfullcolordrawdescr',	index:'isfullcolordrawdescr',	width:100,	label:'图纸类型',	sortable:true,align:"left"},
			{name:'drawqty', index:'drawqty',    width:100,   label:'普通效果图数量',  sortable:true, align:"right"},
			{name:'draw3dqty', index:'draw3dqty',    width:100,   label:'3D效果图数量',  sortable:true, align:"right"},
			{name:'drawnochgdescr',	index:'drawnochgdescr',	width:80,	label:'图纸无变更',	sortable:true,align:"left"},
			{name:'isfullcolordraw',	index:'isfullcolordraw',	width:80,	label:'图纸类型',	sortable:true,align:"left",hidden:true},
			{name:'drawnochg',	index:'drawnochg',	width:80,	label:'图纸无变更',	sortable:true,align:"left" ,hidden:true},
			{name:'drawnochgdate', index:'drawnochgdate', width:120, label:'图纸无变更日期', sortable:true, align:"left", formatter: formatTime},
			{name:'chgdocconfirmdate', index:'chgdocconfirmdate', width:120, label:'变更图纸审核日期', sortable:true, align:"left", formatter: formatTime},
		],
				
	});
	
	$("#viewContract").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("viewContract",{
			title:"项目资料管理——合同资料查看",
			url:"${ctx}/admin/custDoc/goContractDocView",
			postData:{code:ret.code,confirmRemark:ret.confirmremark},
			height:700,
			width:1250,
			returnFun:goto_query,
		});
	});
	
	$("#retConfirm").on("click",function(){
		var ret=selectDataTableRow();
		if($.trim(ret.picstatus)!="4"){
			art.dialog({
				content:"已审核状态才可进行反审核",
			});
			return;
		}
		Global.Dialog.showDialog("retConfirm",{
			title:"项目资料管理——设计图纸反审核",
			url:"${ctx}/admin/custDoc/goRetConfirm",
			postData:{code:ret.code,confirmRemark:ret.confirmremark},
			height:700,
			width:1250,
			returnFun:goto_query,
			close:function(){
				$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
			}
		});
	});
	 
	$("#chgConfirm").on("click",function(){
		Global.Dialog.showDialog("chgConfirm",{
			title:"项目资料管理——变更图纸审核",
			url:"${ctx}/admin/custDoc/goChgConfirm",
			postData:{},
			height:700,
			width:1250,
			returnFun:goto_query,
			close:function(){
				$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
			}
		});
	});
	
	$("#docConfirm").on("click",function(){
		var ret=selectDataTableRow();
		if($.trim(ret.picstatus)!="2"){
			art.dialog({
				content:"待审核状态才可进行审核",
			});
			return;
		}
		Global.Dialog.showDialog("viewContract",{
			title:"项目资料管理——设计图纸审核",
			url:"${ctx}/admin/custDoc/goDocConfirm",
			postData:{code:ret.code,confirmRemark:ret.confirmremark},
			height:700,
			width:1250,
			returnFun:goto_query,
			close:function(){
				$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
			}
		});
	});
	
	$("#contract").on("click",function(){
		var ret=selectDataTableRow();
		/* if($.trim(ret.picstatus)!="0"&&$.trim(ret.picstatus)!="1"&&$.trim(ret.picstatus)!="3"){
			art.dialog({
				content:"待上传,上传中,审核退回才可进行上传操作",
			});
			return;
		} */
		Global.Dialog.showDialog("viewContract",{
			title:"项目资料管理——合同资料上传",
			url:"${ctx}/admin/custDoc/goContractDoc",
			postData:{code:ret.code,confirmRemark:ret.confirmremark},
			height:700,
			width:1250,
			returnFun:goto_query,
			close:function(){
				$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
			}
		});
	});
	
	$("#viewDesign").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("viewDesign",{
			title:"项目资料管理——设计图纸查看",
			url:"${ctx}/admin/custDoc/goDesignDocView",
			postData:{code:ret.code,confirmRemark:ret.confirmremark},
			height:700,
			width:1250,
			returnFun:goto_query,
		});
	});
	
	$("#docChg").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("docChg",{
			title:"项目资料管理——图纸变更",
			url:"${ctx}/admin/custDoc/goDocChg",
			postData:{code:ret.code,confirmRemark:ret.confirmremark,picStatus:ret.picstatus},
			height:700,
			width:1250,
			returnFun:goto_query,
		});
	});
	
	$("#design").on("click",function(){
		var ret=selectDataTableRow();
		if($.trim(ret.picstatus)!="0"&&$.trim(ret.picstatus)!="1"&&$.trim(ret.picstatus)!="3"){
			art.dialog({
				content:"待上传,上传中,审核退回才可进行上传操作",
			});
			return;
		}
		Global.Dialog.showDialog("viewDesign",{
			title:"项目资料管理——设计图纸上传",
			url:"${ctx}/admin/custDoc/goDesignDoc",
			postData:{code:ret.code,confirmRemark:ret.confirmremark,picStatus:ret.picstatus},
			height:700,
			width:1250,
			returnFun:goto_query,
			close:function(){
				$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
			}
		});
	});
	
	$("#viewSoft").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("viewSoft",{
			title:"项目资料管理——软装图纸查看",
			url:"${ctx}/admin/custDoc/goSoftDocView",
			postData:{code:ret.code,confirmRemark:ret.confirmremark},
			height:700,
			width:1250,
			returnFun:goto_query,
		});
	});
	
	$("#soft").on("click",function(){
		var ret=selectDataTableRow();
		/* if($.trim(ret.picstatus)!="0"&&$.trim(ret.picstatus)!="1"&&$.trim(ret.picstatus)!="3"){
			art.dialog({
				content:"待上传,上传中,审核退回才可进行上传操作",
			});
			return;
		} */
		Global.Dialog.showDialog("viewSoft",{
			title:"项目资料管理——软装图纸上传",
			url:"${ctx}/admin/custDoc/goSoftDoc",
			postData:{code:ret.code,confirmRemark:ret.confirmremark},
			height:700,
			width:1250,
			returnFun:goto_query,
			close:function(){
				$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
			}
		});
	});
	
	$("#viewInt").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("viewInt",{
			title:"项目资料管理——集成图纸查看",
			url:"${ctx}/admin/custDoc/goIntDocView",
			postData:{code:ret.code,confirmRemark:ret.confirmremark},
			height:700,
			width:1250,
			returnFun:goto_query,
		});
	});
	
	$("#int").on("click",function(){
		var ret=selectDataTableRow();
		/* if($.trim(ret.picstatus)!="0"&&$.trim(ret.picstatus)!="1"&&$.trim(ret.picstatus)!="3"){
			art.dialog({
				content:"待上传,上传中,审核退回才可进行上传操作",
			});
			return;
		} */
		Global.Dialog.showDialog("viewInt",{
			title:"项目资料管理——集成图纸上传",
			url:"${ctx}/admin/custDoc/goIntDoc",
			postData:{code:ret.code,confirmRemark:ret.confirmremark},
			height:700,
			width:1250,
			returnFun:goto_query,
			close:function(){
				$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
			}
		});
	});
	$("#editInnerArea").on("click",function(){
		var ret=selectDataTableRow();
		if(ret.status=="5"){
			art.dialog({
				content:"此状态客户不允许录入套内面积！",
			});
			return;
		}
		Global.Dialog.showDialog("EditInnerArea",{
			title:"项目资料管理——录入套内面积",
			url:"${ctx}/admin/custDoc/goEditInnerArea",
			postData:{code:ret.code},
			height:300,
			width:700,
			returnFun:goto_query,
		});
	});
	
	$("#editArea").on("click",function(){
		var ret=selectDataTableRow();
		
		Global.Dialog.showDialog("EditArea",{
			title:"项目资料管理——空间管理",
			url:"${ctx}/admin/custDoc/goPrePlanArea",
			postData:{custCode:ret.code,module:"custDoc"},
			height:700,
			width:1230,
			returnFun:goto_query,
		});
	});
	$("#planDiffAnaly").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("PlanDiffAnaly",{
			title:"项目资料管理——预算差异分析",
			url:"${ctx}/admin/custDoc/goPlanDiffAnaly",
			postData:{custCode:ret.code},
			height:700,
			width:1250,
			returnFun:goto_query,
		});
	});
	
	$("#planeUp").on("click", function () {
		var ret=selectDataTableRow();
		if(ret){
			Global.Dialog.showDialog("viewDesign",{
				title:"项目资料管理——平面图纸上传",
				url:"${ctx}/admin/custDoc/goPlaneUp",
				postData:{code:ret.code,confirmRemark:ret.confirmremark,picStatus:ret.picstatus},
				height:671,
				width:1250,
				returnFun:goto_query,
				close:function(){
					$("#dataTable").jqGrid("setGridParam",
						{postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
				}
			});
		} else {
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
	});

	$("#flowUp").on("click", function () {
		var ret=selectDataTableRow();
		if(ret){
			Global.Dialog.showDialog("viewDesign",{
				title:"项目资料管理——框图纸上传",
				url:"${ctx}/admin/custDoc/goFlowUp",
				postData:{code:ret.code,confirmRemark:ret.confirmremark,picStatus:ret.picstatus},
				height:671,
				width:1250,
				returnFun:goto_query,
				close:function(){
					$("#dataTable").jqGrid("setGridParam",
						{postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
				}
			});
		} else {
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
	});
	
	$("#drawNoChg").on("click", function () {
		var ret=selectDataTableRow();
		if(ret){
			if(ret.drawnochg=="1"){
				art.dialog({
					content:"该楼盘已经是图纸无变更！",
				});
				return;
			}
			art.dialog({
			 content:'确定将【'+ret.address+'】标记为图纸无变更吗?',
			 lock: true,
			 ok: function () {
				$.ajax({
					url:'${ctx}/admin/custDoc/doDrawNoChg',
					type: 'post',
					data:{custCode:ret.code},
					dataType: 'json',
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
				    },
				    success: function(obj){
						art.dialog({
							content:obj.msg,
							time:500,
							beforeunload: function () {
			    				goto_query();
						    }
						}); 
					}
				});
			},
			cancel: function () {
				return true;
			}
		 });
		} else {
			art.dialog({
				content:"请选择一条记录",
			});
			return;
		}
		
	});
	
	$("#docChgCon").on("click",function(){
		Global.Dialog.showDialog("docChgCon",{
			title:"项目资料管理——图纸变更跟踪",
			url:"${ctx}/admin/custDoc/goDocChgCon",
			postData:{},
			height:700,
			width:1050,
		});
	});
});
	function clearForm(){	
		$("#page_form input[type='text']").val('');
		$("#page_form select").val('');
		$("#custStatus").val('');
		$("#custType").val('');
		$("#status").val('');
		$("#status_NAME").val('');
		$("#custType_NAME").val('');
		$("#custStatus_NAME").val('');
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
		$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
		$.fn.zTree.getZTreeObj("tree_custStatus").checkAllNodes(false);
	} 
	

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>楼盘地址</label>
								<input type="text" id="address" name="address" style="width:160px;"/>
							</li>
							<li>
								<label>设计图状态</label>
								<house:xtdmMulit id="status" dictCode="PICPRGSTS" ></house:xtdmMulit>                     
							</li>
							<li>
								<label>合同签订时间从</label>
								<input type="text" id="signDateFrom" name="signDateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="signDateTo" name="signDateTo" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
							<li>
								<label>提交审核时间</label>
								<input type="text" id="submitDateFrom" name="submitDateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="submitDateTo" name="submitDateTo" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
							<li>
								<label>审核通过时间</label>
								<input type="text" id="confirmPassDateFrom" name="confirmPassDateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
							</li>
							<li>
								<label>至</label>
								<input type="text" id="confirmPassDateTo" name="confirmPassDateTo" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
							</li>
							<li>	
							<label>客户状态</label>
								<house:xtdmMulit id="custStatus" dictCode="CUSTOMERSTATUS" 
								selectedValue="1,2,3,4" ></house:xtdmMulit>
							</li>
							<li>
								<label>客户类型</label>
								<house:custTypeMulit id="custType"  selectedValue="${custTypes }"></house:custTypeMulit>
							</li>
							<li >
								<button type="button" class="btn  btn-sm btn-system" onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system" onclick="clearForm();"  >清空</button>
							</li>
						</ul>
				</form>
			</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent">
			<!-- panelBar -->
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
						<house:authorize authCode="CUSTDOC_DESIGNDOC">
							<button type="button" class="btn btn-system" style="margin-top:5px" id="design"><span>设计图纸上传</span> </button>
						</house:authorize>
						<house:authorize authCode="CUSTDOC_DOCCHG">
							<button type="button" class="btn btn-system" style="margin-top:5px"  id="docChg"><span>图纸变更</span> </button>
						</house:authorize>
						<house:authorize authCode="CUSTDOC_CONFIRMDESIGNDOC">
							<button type="button" class="btn btn-system" style="margin-top:5px" id="docConfirm"><span>设计图纸审核</span> </button>
						</house:authorize>
						<house:authorize authCode="CUSTDOC_CHGCONFIRM">
							<button type="button" class="btn btn-system" style="margin-top:5px" id="chgConfirm"><span>变更图纸审核</span> </button>
						</house:authorize>
						<house:authorize authCode="CUSTDOC_VIEWDESIGNDOC">
							<button type="button" class="btn btn-system" style="margin-top:5px" id="viewDesign"><span>设计图纸查看</span> </button>
						</house:authorize>
						<house:authorize authCode="CUSTDOC_SOFTDOC">
							<button type="button" class="btn btn-system" style="margin-top:5px" id="soft"><span>软装图纸上传</span> </button>
						</house:authorize>
						<house:authorize authCode="CUSTDOC_VIEWSOFTDOC">
							<button type="button" class="btn btn-system" style="margin-top:5px" id="viewSoft"><span>软装图纸查看</span> </button>
						</house:authorize>
						<house:authorize authCode="CUSTDOC_INTDOC">
							<button type="button" class="btn btn-system" style="margin-top:5px" id="int"><span>集成图纸上传</span> </button>
						</house:authorize>
						<house:authorize authCode="CUSTDOC_VIEWINTDOC">
							<button type="button" class="btn btn-system" style="margin-top:5px" id="viewInt"><span>集成图纸查看</span> </button>
						</house:authorize>
						<house:authorize authCode="CUSTDOC_VIEWCONTRACTDOC">
							<button type="button" class="btn btn-system" style="margin-top:5px" id="viewContract"><span>合同资料查看</span> </button>
						</house:authorize>
						<house:authorize authCode="CUSTDOC_CONTRACTDOC">
							<button type="button" class="btn btn-system" style="margin-top:5px" id="contract"><span>合同资料上传</span> </button>
						</house:authorize>
						<house:authorize authCode="CUSTDOC_RETCONFIRMD">
							<button type="button" class="btn btn-system" style="margin-top:5px" id="retConfirm"><span>反审核</span> </button>
						</house:authorize>
						<house:authorize authCode="CUSTDOC_EDITINNERAREA">
							<button type="button" class="btn btn-system" style="margin-top:5px" id="editInnerArea"><span>录入套内面积</span> </button>
						</house:authorize>
						<house:authorize authCode="CUSTDOC_EDITAREA">
							<button type="button" class="btn btn-system" style="margin-top:5px" id="editArea"><span>空间管理</span> </button>
						</house:authorize>
						<house:authorize authCode="CUSTDOC_PLANDIFFANALY">
							<button type="button" class="btn btn-system" style="margin-top:5px" id="planDiffAnaly"><span>预算差异分析</span> </button>
						</house:authorize>
						<house:authorize authCode="CUSTDOC_PLANEUP">
							<button type="button" class="btn btn-system" style="margin-top:5px" id="planeUp"><span>平面图上传</span> </button>
						</house:authorize>
						<house:authorize authCode="CUSTDOC_FLOWUP">
							<button type="button" class="btn btn-system" style="margin-top:5px" id="flowUp"><span>框图上传</span> </button>
						</house:authorize>
						<house:authorize authCode="CUSTDOC_DRAWNOCHG">
							<button type="button" class="btn btn-system" style="margin-top:5px" id="drawNoChg"><span>图纸无变更</span> </button>
						</house:authorize>
						<house:authorize authCode="CUSTDOC_DOCCHGCON">
							<button type="button" class="btn btn-system" style="margin-top:5px" id="docChgCon"><span>图纸变更跟踪</span> </button>
						</house:authorize>
							<button type="button" class="btn btn-system"  style="margin-top:5px" onclick="doExcel()" >
							<span>导出excel</span>
							</button>
					</div>
				</div>
					<!-- panelBar End -->
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div> 
	</body>	
</html>
