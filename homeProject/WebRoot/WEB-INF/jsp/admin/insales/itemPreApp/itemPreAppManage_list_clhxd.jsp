<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>测量后下单</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	
		<script type="text/javascript">
		function showDialog(sign, title, url, ret, height, width){
			var heightDef = 580;
			var widthDef = 1000;
			var postData = {
				pk:ret.pk
			};
			if(height>580){
				heightDef = height;
			}
			if(width>1000){
				widthDef = width;
			}
			Global.Dialog.showDialog(sign,{
				title:title,
				url:url,
				postData:postData,
				height:heightDef,
				width:widthDef,
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
		}
		function clxd(){
			var ret = selectDataTableRow("dataClhxdTable");
			
			if(ret){
		 		if(ret.status.trim() != "4" && ret.status.trim()!="7"){
					art.dialog({
						content:"只能对测量完成，和部分下单状态进行领料下单操作"
					});
					return ;
				} 
				showDialog("itemPreApp_clhxd_clxd", "领料单--新增", "${ctx}/admin/itemPreApp/goClxd_clhxd", ret, 730, 1400);
			}else{
			    art.dialog({
					content: "请选择一条记录"
				});
			}
		}
		function xdwc(){
			var ret = selectDataTableRow("dataClhxdTable");
		
			if(ret){
		 		if(ret.status.trim() != "4"&& ret.status.trim()!="7"){
					art.dialog({
						content:"只能对完成和部分完成状态的记录进行下单完成操作"
					});
					return ;
				}
				art.dialog({
					content:"是否下单完成",
					ok:function(){
					 	$.ajax({
							url:"${ctx}/admin/itemPreApp/doXdwc_clhxd",
							type: "post",
							data:{
								pk:ret.pk,
								preAppNo:ret.preappno
							},
							dataType: "json",
							cache: false,
							error: function(obj){			    		
								art.dialog({
									content: "下单完成出错,请重试",
									time: 3000,
									beforeunload: function () {
									}
								});
							},
							success: function(obj){
								if(obj.rs){
									art.dialog({
										content: obj.msg,
										time: 3000,
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
					},
					cancel:function(){
						goto_query();
					}
				}); 
			}else{
			    art.dialog({
					content: "请选择一条记录"
				});
			}
		}
		function xdqx(){
			var ret = selectDataTableRow("dataClhxdTable");
		
			if(ret){
		 		if(ret.status.trim() != "4" && ret.status.trim() != "3" && ret.status.trim() != "2" && ret.status.trim() != "1"){
					art.dialog({
						content:"只能对申请、退回、接收、完成状态的记录进行取消操作"
					});
					return ;
				} 
				showDialog("itemPreApp_clhxd_xdqx", "下单取消", "${ctx}/admin/itemPreApp/goXdqx_clhxd", ret);
			}else{
			    art.dialog({
					content: "请选择一条记录"
				});
			}
		}
		function view(){
			var ret = selectDataTableRow("dataClhxdTable");
		
			if(ret){
				showDialog("itemPreApp_clhxd_view", "测量后下单--查看", "${ctx}/admin/itemPreApp/goView_clhxd", ret);
			}else{
			    art.dialog({
					content: "请选择一条记录"
				});
			}
		}
		function doExcel(){
			doExcelAll("${ctx}/admin/itemPreApp/doExcel_clhxd", "dataClhxdTable");
		}
		function goto_query(){
			$("#dataClhxdTable").jqGrid("setGridParam",{
				url:"${ctx}/admin/itemPreApp/goClhxdJqGrid",
				postData:$("#page_form").jsonForm(),
				page:1
			}).trigger("reloadGrid");
		}
		/**初始化表格*/
		$(function(){
			Global.JqGrid.initJqGrid("dataClhxdTable", {
				//multiselect: true,
				height:$(document).height()-$("#content-list").offset().top-70,
				styleUI:"Bootstrap",
				colModel : [
					{name: "pk", index: "pk", width: 94, label: "pk", sortable: true, align: "left", hidden: true},
					{name: "custcode", index: "custcode", width: 94, label: "custcode", sortable: true, align: "left", hidden: true},
					{name: "itemtype1", index: "itemtype1", width: 94, label: "itemType1", sortable: true, align: "left", hidden: true},
					{name: "preappno", index: "preappno", width: 80, label: "预申请单号", sortable: true, align: "left"},
					{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
					{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
					{name: "itemtype1descr", index: "itemtype1descr", width: 70, label: "材料类型1", sortable: true, align: "left"},
					{name: "supplierdescr", index: "supplierdescr", width: 120, label: "供应商", sortable: true, align: "left"},
					{name: "statusdescr", index: "statusdescr", width: 50, label: "状态", sortable: true, align: "left"},
					{name: "date", index: "date", width: 70, label: "申请时间", sortable: true, align: "left", formatter: formatTime},
					{name: "recvdate", index: "recvdate", width: 70, label: "接收时间", sortable: true, align: "left", formatter: formatTime},
					{name: "confirmdate", index: "confirmdate", width: 70, label: "测量时间", sortable: true, align: "left", formatter: formatTime},
					{name: "appczydescr", index: "appczydescr", width: 70, label: "申请人", sortable: true, align: "left"},
					{name: "remarks", index: "remarks", width: 100, label: "备注", sortable: true, align: "left"},
					{name: "actualprogressitemdescr", index: "actualprogressitemdescr", width: 100, label: "实际进度", sortable: true, align: "left"},
					{name: "confirmczydescr", index: "confirmczydescr", width: 70, label: "测量人", sortable: true, align: "left"},
					{name: "measureremark", index: "measureremark", width: 100, label: "测量说明", sortable: true, align: "left"},
					{name: "chgstatusdescr", index: "chgstatusdescr", width: 100, label: "变更状态", sortable: true, align: "left"},
					{name: "informdate", index: "informdate", width: 100, label: "通知时间", sortable: true, align: "left", formatter: formatTime},
					{name: "chgcompletedate", index: "chgcompletedate", width: 100, label: "完成时间", sortable: true, align: "left", formatter: formatTime},
					{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left", hidden: true},
					{name: "projectmandescr", index: "projectmandescr", width: 70, label: "项目经理", sortable: true, align: "left"},
					{name: "prjdepartment2descr", index: "prjdepartment2descr", width: 60, label: "部门", sortable: true, align: "left"},
					{name: "completedate", index: "completedate", width: 70, label: "下单时间", sortable: true, align: "left", formatter: formatTime},
					{name: "completeczydescr", index: "completeczydescr", width: 70, label: "下单人", sortable: true, align: "left"},
					{name: "mainmandescr", index: "mainmandescr", width: 80, label: "主材管家", sortable: true, align: "left"},
					{name: "status", index: "status", width: 70, label: "状态编号", sortable: true, align: "left", hidden: true},
					{name: "cancelremark", index: "cancelremark", width: 70, label: "取消原因", sortable: true, align: "left"},
					{name: "delayresondescr", index: "delayresondescr", width: 70, label: "延误原因", sortable: true, align: "left"} 
	            ]
			});
	   		$("#appCzy").openComponent_employee({
	   			showValue:"${itemPreMeasure.appCzy}",
	   			showLabel:"${itemPreMeasure.appCzyDescr}"
	   		});
	   		$("#empCode").openComponent_employee({
	   			showValue:"${itemPreMeasure.empCode}",
	   			showLabel:"${itemPreMeasure.empCodeDescr}"
	   		});
	   		$("#supplCode").openComponent_supplier({
	   			showValue:"${itemPreMeasure.supplCode}",
	   			showLabel:"${itemPreMeasure.supplerDescr}",
	   			condition:{
	   				itemRight:"${itemPreApp.itemRightForSupplier}"
	   			}
	   		});
		});
		window.onload = function() { 
			setTimeout(goto_query, 100);
		};
		function editRemark(){
			var ret = selectDataTableRow("dataClhxdTable");
		
			if(ret){
		 		if(ret.status.trim() != "3" && ret.status.trim() != "4"){
					art.dialog({
						content:"接收和完成状态才能录入备注"
					});
					return ;
				} 
				Global.Dialog.showDialog("inputMemo", {
					title:"测量后下单--录入备注",
					url:"${ctx}/admin/itemPreApp/goEditRemark",
					postData:{
						pk:ret.pk,
						from:"2",
						custCode: ret.custcode
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
		
		function remeasure() {
		    var ret = selectDataTableRow("dataClhxdTable");
		    
		    if (!ret || ret.status.trim() !== "2") {
		        art.dialog({content: "请选择一条退回记录！"});
		        return;
		    }
		    
            art.dialog({
                content: "确定要重新测量吗？",
                lock: true,
                ok: function() {
					$.ajax({
					    url: "${ctx}/admin/itemPreApp/remeasure",
					    type: "post",
					    data: {pk: ret.pk},
					    dataType: "json",
					    cache: false,
					    error: function(obj) {
					        showAjaxHtml({"hidden": false, "msg": "保存出错"})
					    },
					    success: function(obj) {
					        if (obj.rs) {
					            art.dialog({
					                content: obj.msg,
					                time: 1000,
					                beforeunload: goto_query
					            });
					        } else {
					            art.dialog({content: obj.msg});
					        }
					    }
					})
                },
                cancel: function() {}
            })
		}
		
		</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
						<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="page_form" class="form-search">
						<%-- <house:token></house:token> --%>
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li>
								<label>预申请单号</label>
								<input type="text" id="preAppNo" name="preAppNo" value="${itemPreMeasure.preAppNo}" />
							</li>
							<li>
								<label>材料类型1</label>
								<house:dict id="itemType1" dictCode="" 
											sql="select cbm,note from tXTDM  where id='ITEMRIGHT' and cbm in ${itemPreApp.itemRight } order by IBM ASC " 
											sqlValueKey="cbm" sqlLableKey="note" value="${itemPreMeasure.itemType1}">
								</house:dict>
							</li>
							<li>
								<label>申请日期从</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
									   onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									   value="<fmt:formatDate value='${itemPreMeasure.dateFrom }' pattern='yyyy-MM-dd'/>"/>
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" class="i-date" 
								       onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								       value="<fmt:formatDate value='${itemPreMeasure.dateTo }' pattern='yyyy-MM-dd'/>"/>
							</li>
							<li>
								<label>申请人</label>
								<input type="text" id="appCzy" name="appCzy" value="${itemPreMeasure.appCzy}" />
							</li>
							<li>
								<label>供应商编号</label>
								<input type="text" id="supplCode" name="supplCode" value="${itemPreMeasure.supplCode}" />
							</li>
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" value="${itemPreMeasure.address}" />
							</li>
							<li>
								<label>主材管家</label>
								<input type="text" id="empCode" name="empCode" value="${itemPreMeasure.empCode}" />
							</li>
							<li>
								<label>状态</label>
								<house:xtdmMulit id="status" dictCode="MEASURESTATUS" selectedValue="2,4" ></house:xtdmMulit>
							</li>
							<li>
								<label>工程部2</label>
								<house:xtdmMulit id="department2" dictCode="" 
									     		 sql=" select Code,(Code+' '+Desc1) fd from tDepartment2 where Expired='F' and DepType='3' order by DispSeq "
									     		 sqlValueKey="Code" sqlLableKey="fd"></house:xtdmMulit>
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
								<label>变更状态</label>
								<house:dict id="chgStatus" dictCode="" 
											sql="select cbm,note from tXTDM  where id='MEASURECHGSTAT' order by IBM ASC " 
											sqlValueKey="cbm" sqlLableKey="note">
								</house:dict>
							</li>
							<li class="search-group">
								<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm()">清空</button>
						  	</li>
						</ul>
					</form>
				</div>
			</div>
			
			<div class="clear_float"> </div>
			
			<div class="btn-panel" >
				<div class="btn-group-sm"  >
					<button type="button" class="funBtn funBtn-system" onclick="clxd()">测量下单</button>
					<button type="button" class="funBtn funBtn-system" onclick="xdwc()">下单完成</button>
					<button type="button" class="funBtn funBtn-system" onclick="xdqx()">下单取消</button>
					<button type="button" class="funBtn funBtn-system" onclick="view()">查看</button>
					<button type="button" class="funBtn funBtn-system" onclick="doExcel()">输出到Excel</button>
					<button type="button" class="funBtn funBtn-system" onclick="editRemark()">录入备注</button>
					<button type="button" class="funBtn funBtn-system" onclick="remeasure()">重新测量</button>
				</div>
			</div>
		
			<div id="content-list">
				<table id="dataClhxdTable"></table>
				<div id="dataClhxdTablePager"></div>
			</div>
		</div>
	</body>
</html>


