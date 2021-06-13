<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
	UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
	String czybh=uc.getCzybh();
 %>
<!DOCTYPE html>
<html>
	<head>
		<title>领了预申请主界面</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
		<script type="text/javascript">
		function showDialog(sign, title, url, ret, m_umState){
			Global.Dialog.showDialog(sign, {
				title:title,
				url:url,
				postData:{
					no:ret.no,
					custCode:ret.custcode,
					documentNo:ret.documentno,
					address:ret.address,
					itemType1:ret.itemtype1,
					isSetItem:ret.issetitem,
					date:ret.date,
					appCzy:ret.appczy,
					appCzyDescr:ret.appczydescr,
					phone:ret.phone,
					confirmDate:ret.confirmdate,
					confirmCzy:ret.confirmczy,
					confirmCzyDescr:ret.confirmczydescr,
					workerCode:ret.workercode,
					workerName:ret.workername,
					signDate:ret.signdate,
					status:ret.status,
					endCode:ret.endcode,
					remarks:ret.remarks,
					m_umState:m_umState,
					area:ret.area
				},
				height:700,
				width:1300,
				returnFun:goto_query
			});
		}
		function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#page_form select").val("");
			$("#status").val("");
			$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
			$("#region").val("");
			$.fn.zTree.getZTreeObj("tree_region").checkAllNodes(false);
			$("#department2").val("");
			$.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
			$("#custType").val("");
			$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
		}
		function receive(){
			var ret = selectDataTableRow();
		
			if(ret){
				if(ret.status.trim() != "2"){
					art.dialog({
						content:"该领料预申请单不在提交状态,无法进行接收操作"
					});
					return ;
				}
				showDialog("itemPreAppReceive", "领料预申请--接收", "${ctx}/admin/itemPreApp/goReceive", ret, "R");
			}else{
			    art.dialog({
					content: "请选择一条记录"
				});
			}
		}
		function addll(){
			var ret = selectDataTableRow();
		
			if(ret){
				if(ret.confirmczy.trim() != $("#czybh").val().trim() && ret.confirmczy.trim() != ""){
					art.dialog({
						content:"只有接收人可以进行领料单新增操作!"
					});
					return ;
				}
				
				if(ret.status.trim() != "3" && ret.status.trim() != "4"){
					art.dialog({
						content:"只能对接收状态和部分下单进行领料单新增操作!"
					});
					return ;
				}
				Global.Dialog.showDialog("itemPreAppAddll",{
					title:"领料预申请-新增领料",
					url:"${ctx}/admin/itemPreApp/goAddll",
					postData:{
						no:ret.no
					},
					height:730,
					width:1400,
					returnFun:goto_query
				});	
			}else{
			    art.dialog({
					content: "请选择一条记录"
				});
			}
		}
		function addcl(){
			var ret = selectDataTableRow();
		
			if(ret){
				if(ret.status.trim() != "3" && ret.status.trim() != "4"){
					art.dialog({
						content:"只有接收或部分下单状态才能进行测量操作!"
					});
					return ;
				}
				showDialog("itemPreAppAddcl", "测量申请", "${ctx}/admin/itemPreApp/goAddcl", ret, "M");
			}else{
			    art.dialog({
					content: "请选择一条记录"
				});
			}
		}
		function clhxd(){
			Global.Dialog.showDialog("itemPreAppClhxd",{
				title:"领料预申请-测量后下单",
				url:"${ctx}/admin/itemPreApp/goClhxd",
				postData:{},
				height:700,
				width:1350,
				returnFun:goto_query
			});	
		}
		function qxsq(){
			var ret = selectDataTableRow();
		
			if(ret){
		 		if(ret.status.trim() > "2"){
					art.dialog({
						content:"只能对申请和提交状态进行取消申请操作"
					});
					return ;
				} 
				showDialog("itemPreAppQxsq", "领料预申请--取消申请", "${ctx}/admin/itemPreApp/goQxsq", ret, "C");
			}else{
			    art.dialog({
					content: "请选择一条记录"
				});
			}
		}
		function thtj(){
			var ret = selectDataTableRow();
		
			if(ret){
				if(ret.status.trim() != "3"){
					art.dialog({
						content:"只能对接收状态进行退回提交操作"
					});
					return ;
				} 
				showDialog("itemPreAppThtj", "领料预申请--退回提交", "${ctx}/admin/itemPreApp/goThtj", ret, "BC");
			}else{
			    art.dialog({
					content: "请选择一条记录"
				});
			}
		}
		function thsq(){
			var ret = selectDataTableRow();
		
			if(ret){
		 		if(ret.status.trim() != "2"){
					art.dialog({
						content:"只能对提交状态进行退回申请操作"
					});
					return ;
				} 
				showDialog("itemPreAppThsq", "领料预申请--退回申请", "${ctx}/admin/itemPreApp/goThsq", ret, "BA");
			}else{
			    art.dialog({
					content: "请选择一条记录"
				});
			}
		}
		function xdwc(){
			var ret = selectDataTableRow();
		
			if(ret){
		 		if(ret.status.trim() != "4"){
					art.dialog({
						content:"只能对部分下单状态进行下单完成操作"
					});
					return ;
				} 
				showDialog("itemPreAppXdwc", "领料预申请--下单完成", "${ctx}/admin/itemPreApp/goXdwc", ret, "OC");
			}else{
			    art.dialog({
					content: "请选择一条记录"
				});
			}
		}
		function view(){
			var ret = selectDataTableRow();
		
			if(ret){
				showDialog("itemPreAppView", "领料预申请管理--查看", "${ctx}/admin/itemPreApp/goView", ret, "V");
			}else{
			    art.dialog({
					content: "请选择一条记录"
				});
			}
		}
		function doExcel(){
			var url = "${ctx}/admin/itemPreApp/doExcel";
			doExcelAll(url);
		}
		function goto_query(buttonFlag){
			var datas = {
				url:"${ctx}/admin/itemPreApp/goItemPreAppManageListJqGrid",
				postData:$("#page_form").jsonForm(),
				page:1
			};
			if(buttonFlag == true){
				$(".s-ico").css("display", "none");
				datas = $.extend(datas,{
					sortname:""
				});
			}
			$("#dataTable").jqGrid("setGridParam", datas).trigger("reloadGrid");
		
		}
		/**初始化表格*/
		$(function(){
			Global.JqGrid.initJqGrid("dataTable",{
				//multiselect: true,
				height:$(document).height()-$("#content-list").offset().top-70,
				styleUI:"Bootstrap",
				colModel : [
					{name:"documentno", index:"documentno", width:70, label:"档案号" , sortable:true, align:"left"},
					{name:"itemtype1descr", index:"itemtype1descr", width:74.5, label:"材料类型1", sortable:true, align:"left"},
					{name:"custtypedescr", index:"custtypedescr", width:74.5, label:"客户类型", sortable:true, align:"left"},
					{name:"whdescr", index:"whdescr", width:80, label:"发货仓库", sortable:true, align:"left"}, 
					{name:"address", index:"address", width:150, label:"楼盘地址", sortable:true, align:"left"}, 
					{name:"regiondescr", index:"regiondescr", width:60, label:"片区", sortable:true, align:"left", hidden:true}, 
					{name:"issetitemddescr", index:"issetitemddescr", width:70, label:"套餐材料", sortable:true, align:"left"}, 
					{name:"xtdmstatus", index:"xtdmstatus", width:70, label:"状态", sortable:true, align:"left"}, 
					{name:"dept1desc", index:"dept1desc", width:80, label:"一级部门", sortable:true, align:"left", hidden:true}, 
					{name:"dept2desc", index:"dept2desc", width:80, label:"二级部门", sortable:true, align:"left"}, 
					{name:"appczydescr", index:"appczydescr", width:70, label:"申请人", sortable:true, align:"left"}, 
					{name:"date", index:"date", width:70, label:"申请日期", sortable:true, align:"left", formatter:formatTime}, 
					{name:"confirmczydescr", index:"confirmczydescr", width:70, label:"接收人", sortable:true, align:"left"}, 
					{name:"confirmdate", index:"confirmdate", width:70, label:"接收日期", sortable:true, align:"left", formatter:formatTime}, 
					{name:"mainmandescr",  index: "mainmandescr",  width: 80,  label: "主材管家",  sortable: true,  align: "left"},
					{name:"measurestatus", index:"measurestatus", width:80, label:"测量状态", sortable:true, align:"left"}, 
					{name:"remarks", index:"remarks", width:150, label:"备注", sortable:true, align:"left"}, 
					{name:"confirmremark", index:"confirmremark", width:150, label:"预审核说明", sortable:true, align:"left", formatter: filterHtml}, 
					{name:"no", index:"no", width:105, label:"领料预申请单号", sortable:true, align:"left"}, 
					{name:"endcodedesc", index:"endcodedesc", width:80, label:"处理结果", sortable:true, align:"left"}, 
					{name:"workercode", index:"workercode", width:70, label:"工人编号", sortable:true, align:"left",hidden:true}, 
					{name:"workername", index:"workername", width:70, label:"工人", sortable:true, align:"left"}, 
					{name:"lastupdate", index:"lastupdate", width:95, label:"最后更新时间", sortable:true, align:"left", formatter:formatTime}, 
					{name:"lastupdatedby", index:"lastupdatedby", width:80, label:"最后修改人", sortable:true, align:"left"}, 
					{name:"expired", index:"expired", width:70, label:"过期标志", sortable:true, align:"left"}, 
					{name:"actionlog", index:"actionlog", width:70, label:"操作日志", sortable:true, align:"left"}, 
					{name:"phone", index:"phone", width:100, label:"phone", sortable:true, align:"left", hidden:true}, 
					{name:"signdate", index:"signdate", width:100, label:"signdate", sortable:true, align:"left", formatter:formatTime, hidden:true}, 
					{name:"custcode", index:"custcode", width:100, label:"custcode", sortable:true, align:"left", hidden:true}, 
					{name:"signdate", index:"signdate", width:100, label:"signdate", sortable:true, align:"left", formatter:formatTime, hidden:true}, 
					{name:"custcode", index:"custcode", width:100, label:"custcode", sortable:true, align:"left", hidden:true}, 
					{name:"appczy", index:"appczy", width:140, label:"appczy", sortable:true, align:"left", hidden:true}, 
					{name:"confirmczy", index:"confirmczy", width:140, label:"confirmczy", sortable:true, align:"left", hidden:true}, 
					{name:"status", index:"status", width:100, label:"status", sortable:true, align:"left", hidden:true}, 
					{name:"endcode", index:"endcode", width:120, label:"endcode", sortable:true, align:"left", hidden:true} , 
					{name:"itemtype1", index:"itemtype1", width:120, label:"itemtype1", sortable:true, align:"left", hidden:true}, 
					{name:"issetitem", index:"issetitem", width:120, label:"issetitem", sortable:true, align:"left", hidden:true}, 
					{name:"area", index:"area", width:120, label:"area", sortable:true, align:"left", hidden:true}   
	            ],
	            ondblClickRow:function(rowid, iRow, iCol, e){
	            	view();
	            }
			});
	   		$("#appCzy").openComponent_employee({
	   			showValue:"${itemPreApp.appCzy}",
	   			showLabel:"${itemPreApp.appCzyDescr}"
	   		});
	   		$("#confirmCzy").openComponent_employee({
	   			showValue:"${itemPreApp.confirmCzy}",
	   			showLabel:"${itemPreApp.confirmCzyDescr}"
	   		});
	   		$("#mainManCode").openComponent_employee({
	   			showValue:"${itemPreApp.mainManCode}",
	   			showLabel:"${itemPreApp.mainManDescr}"
	   		});		
	   		onCollapse(44);
		});
		function editRemark(){
			var ret = selectDataTableRow();
		
			if(ret){
		 		if(ret.status.trim() != "3"){
					art.dialog({
						content:"下单接收状态才能录入备注"
					});
					return ;
				} 
				Global.Dialog.showDialog("inputMemo", {
					title:"领料预申请--录入备注",
					url:"${ctx}/admin/itemPreApp/goEditRemark",
					postData:{
						no:ret.no,
						from:"1",
						custCode: ret.custcode,
					},
					height:350,
					width:800,
					returnFun:goto_query
				});
			}else{
			    art.dialog({
					content: "请选择一条记录"
				});
			}
		}
		
		//自动拆单
		function autoOrder(){
			var ret = selectDataTableRow();
			var status=getStatus(ret.no);
			if(!ret){
				art.dialog({
					content: "请选择一条记录"
				});
				return;
			}
			if(status.trim()!="2" && status.trim()!="3"){
				 art.dialog({
					content: "仅提交状态及接收状态允许拆单！"
				});
				return;
			}
			Global.Dialog.showDialog("autoOrder", {
				title:"领料预申请--自动拆单",
				url:"${ctx}/admin/itemPreApp/goAutoOrder",
				postData:{
					no:ret.no
				},
				height:730,
				width:1350,
				returnFun:goto_query
			});
		}
		
		//后台拆单
		function backstageOrder(){
			var ret = selectDataTableRow();
			var status=getStatus(ret.no);
			if(!ret){
				art.dialog({
					content: "请选择一条记录"
				});
				return;
			}
			if(status.trim()!="2" && status.trim()!="3"){
				 art.dialog({
					content: "仅提交状态及接收状态允许拆单！"
				});
				return;
			}
			$.ajax({
				url:'${ctx}/admin/itemPreApp/doBackstageOrder',
				type: 'post',
				data: {
					preAppNo:ret.no
				},
				dataType: 'json',
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    		art.dialog({
							content: obj.msg,
							time: 1000,
							beforeunload: function () {
			    				goto_query();
						    }
						});
			    	}else{
			    		$("#_form_token_uniq_id").val(obj.token.token);
			    		art.dialog({
							content: obj.msg,
							width: 200
						});
			    	}
			    }
			 });
		}
		
	//查询预申请单状态
	function getStatus(no){
		var status="";
		$.ajax({
	         url : '${ctx}/admin/itemPreApp/getMeasureInfoByNo',
	         type : 'post',
	         data : {
	              'no' : no
	         },
	         async:false,
	         dataType : 'json',
	         cache : false,
	         error : function(obj) {
	              showAjaxHtml({
	                    "hidden" : false,
	                    "msg" : '保存数据出错~'
	              });
	         },
	         success : function(obj) {
	             status=obj.Status;
	         }
	    });
	    return status;
	}
	
	function batchSave(){
		Global.Dialog.showDialog("itemPreAppBatchSave",{
			title:"领料预申请-新增领料",
			url:"${ctx}/admin/itemPreApp/goBatchSave",
			postData:{
				
			},
			height:730,
			width:1400,
			returnFun:goto_query
		});	
	}
	</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="czybh" id="czybh" value="<%=czybh %>" />
					<ul class="ul-form">
						<li>
							<label>领料预申请单号</label>
							<input type="text" id="no" name="no" value="${itemPreApp.no}" />
						</li>
						<li>
							<label>材料类型1</label>
							<house:dict id="itemType1" dictCode="" 
										sql=" select cbm,note from tXTDM  where id='ITEMRIGHT' and cbm in ${itemPreApp.itemRight } order by IBM ASC " 
										sqlValueKey="cbm" sqlLableKey="note" value="${itemPreApp.itemType1}">
							</house:dict>
						</li>
						<li>
							<label>状态</label>
							<house:xtdmMulit id="status" dictCode="PREAPPSTATUS" selectedValue="${itemPreApp.status }" ></house:xtdmMulit>
						</li>
						<li>
							<label>主材管家</label>
							<input type="text" id="mainManCode" name="mainManCode" value="${itemPreApp.mainManCode}" />
						</li>
						<li>
							<label>申请日期从</label>
							<input type="text" id="appDateFrom" name="appDateFrom" class="i-date" 
							       onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							       value="<fmt:formatDate value='${itemPreApp.appDateFrom }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="appDateTo" name="appDateTo" class="i-date" 
							       onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							       value="<fmt:formatDate value='${itemPreApp.appDateTo }' pattern='yyyy-MM-dd'/>"/>
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" value="${itemPreApp.address}" />
						</li>
						<li>
							<label>接收人</label>
							<input type="text" id="confirmCzy" name="confirmCzy" value="${itemPreApp.confirmCzy}" />
						</li>							
						<li id="loadMore" >
							<button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">更多</button>
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query(true);"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
						</li>
						<div  class="collapse " id="collapse" >
							<ul>
								<li>
									<label>接收日期从</label>
									<input type="text" id="confirmDateFrom" name="confirmDateFrom" class="i-date" 
									       onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									       value="<fmt:formatDate value='${itemPreApp.confirmDateFrom }' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li>
									<label>至</label>
									<input type="text" id="confirmDateTo" name="confirmDateTo" class="i-date" 
									       onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									       value="<fmt:formatDate value='${itemPreApp.confirmDateTo }' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li>
									<label>工程部1</label>
									<house:department1 id="department1"  depType="3" value="${itemPreApp.department1}" ></house:department1>
								</li>
								<li>
									<label>工程部2</label>
									<house:xtdmMulit id="department2" dictCode="" 
													 sql=" select Code,Desc1 from tDepartment2 where DepType='3' and Expired='F' order by DispSeq "  
													 sqlValueKey="Code" sqlLableKey="Desc1" selectedValue="${itemPreApp.department2}">
									</house:xtdmMulit>
								</li>
								<li>
									<label>申请人</label>
									<input type="text" id="appCzy" name="appCzy" value="${itemPreApp.appCzy}" />
								</li>
								<li>
									<label>片区</label>
									<house:DictMulitSelect id="region" dictCode="" 
									sql="select code,descr from tRegion where 1=1 and expired='F' " sqlLableKey="Descr" sqlValueKey="Code"></house:DictMulitSelect>
								</li>
								<li>
									<label>工程大区</label>
									<house:dict id="prjRegion" dictCode="" sql="select  Code,code+' '+descr Descr from tPrjRegion where expired ='F' " 
									sqlValueKey="Code" sqlLableKey="Descr" ></house:dict>
								</li>
								<li>
									<label>客户类型</label>
									<house:custTypeMulit id="custType" ></house:custTypeMulit>
								</li>
								<li class="search-group-shrink">
									<button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">收起</button>
									<button type="button" class="btn  btn-sm btn-system " onclick="goto_query(true);"  >查询</button>
									<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							  	</li>
							</ul>
						</div>
					</ul>
				</form>
			</div>
			
			<div class="clear_float"> </div>
			
			<div class="btn-panel" >
				<div class="btn-group-sm"  >
					<house:authorize authCode="ITEMPREAPP_BATCHSAVE">
						<button type="button" class="funBtn funBtn-system" onclick="batchSave()">批量新增</button>
					</house:authorize>
					<house:authorize authCode="itemPreApp_receive">
						<button type="button" class="funBtn funBtn-system" onclick="receive()">接收</button>
					</house:authorize>
					<house:authorize authCode="itemPreApp_addll">
						<button type="button" class="funBtn funBtn-system" onclick="addll()">新增领料</button>
					</house:authorize>
					<house:authorize authCode="itemPreApp_addcl">
						<button type="button" class="funBtn funBtn-system" onclick="addcl()">新增测量</button>
					</house:authorize>
					<house:authorize authCode="itemPreApp_clhxd">
						<button type="button" class="funBtn funBtn-system" onclick="clhxd()">测量后下单</button>
					</house:authorize>
					<house:authorize authCode="itemPreApp_qxsq">
						<button type="button" class="funBtn funBtn-system" onclick="qxsq()">取消申请</button>
					</house:authorize>
					<house:authorize authCode="itemPreApp_thtj">
						<button type="button" class="funBtn funBtn-system" onclick="thtj()">退回提交</button>
					</house:authorize>
					<house:authorize authCode="itemPreApp_thsq">
						<button type="button" class="funBtn funBtn-system" onclick="thsq()">退回申请</button>
					</house:authorize>
					<house:authorize authCode="itemPreApp_xdwc">
						<button type="button" class="funBtn funBtn-system" onclick="xdwc()">下单完成</button>
					</house:authorize>
					<house:authorize authCode="itemPreApp_editremark">
						<button type="button" class="funBtn funBtn-system" onclick="editRemark()">录入备注</button>
					</house:authorize>
					<house:authorize authCode="itemPreApp_autoOrder">
						<button type="button" class="funBtn funBtn-system" onclick="autoOrder()">自动拆单</button>
					</house:authorize>
					<house:authorize authCode="itemPreApp_backStageOrder">
						<button type="button" class="funBtn funBtn-system" onclick="backstageOrder()">后台拆单</button>
					</house:authorize>
					<house:authorize authCode="itemPreApp_view">
						<button type="button" class="funBtn funBtn-system" onclick="view()">查看</button>
					</house:authorize>
					<house:authorize authCode="itemPreApp_excel">
						<button type="button" class="funBtn funBtn-system" onclick="doExcel()">输出到Excel</button>
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


