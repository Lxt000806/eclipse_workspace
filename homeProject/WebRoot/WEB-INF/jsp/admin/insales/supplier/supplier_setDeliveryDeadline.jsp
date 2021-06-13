<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	<style type="text/css">
		.panel-info {
			margin-bottom: 10px;
		}
		.btn-panel>.btn-group-sm {
			padding-top: 5px;
		}
	</style>
	<script type="text/javascript" defer>
		var czybh = "${sessionScope.USER_CONTEXT_KEY.czybh}";
		var code = "${supplier.code}";
		$(function(){
			var originalDataTable;
			var gridOption = {
				url: "${ctx}/admin/supplier/goSupTimeJqGrid",
				postData: {code:code},
				height: $(document).height()-$("#content-list").offset().top - 35,
				rowNum:100000,
				loadonce:true,
				colModel: [
					{name: "no", index: "no", width: 90, label: "发货时限编号", sortable: true, align: "left"},
					{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
					{name: "producttypedescr", index: "producttypedescr", width: 80, label: "产品类型", sortable: true, align: "left"},
					{name: "issetitemdescr", index: "issetitemdescr", width: 90, label: "是否限制材料", sortable: true, align: "left"},
					{name: "sendday", index: "sendday", width: 70, label: "送货天数", sortable: true, align: "right"},
					{name: "prior", index: "prior", width: 70, label: "优先级", sortable: true, align: "right"},
					{name: "remarks", index: "remarks", width: 220, label: "备注", sortable: true, align: "left"},
					{name: "lastupdate",index: "lastupdate",width: 120,label: "最后修改时间",sortable: true,align: "left",formatter: formatTime,hidden: true},
					{name: "lastupdatedby",index: "lastupdatedby",width: 90,label: "修改人",sortable: true,align: "left",hidden: true},
					{name: "expired",index: "expired",width: 70,label: "过期标志",sortable: true,align: "left",hidden: true},
					{name: "actionlog",index: "actionlog",width: 70,label: "修改操作",sortable: true,align: "left",hidden: true},
				],
				ondblClickRow: function(){
					view();
				},
				loadComplete: function(){
					originalDataTable = Global.JqGrid.allToJson("dataTable").detailJson;
				},
			};
			Global.JqGrid.initJqGrid("dataTable", gridOption);

			$("#save").on("click", function() {
				var nos = Global.JqGrid.allToJson("dataTable","no");
				Global.Dialog.showDialog("add",{
					title:"供应商时限明细",
					url:"${ctx}/admin/supplier/goDDD",
					postData:{
						m_umState:"A",
						keys:nos.keys,
					},
					width:500,
					height:300,
					returnFun: function(data){
						if(data){
							$.ajax({
								url:"${ctx}/admin/supplier/getDDD",
								type:"post",
								data:{code:code,sendTimeNo:data.no},
								dataType:"json",
								error:function(obj){
									showAjaxHtml({"hidden": false, "msg": "获取数据出错"});
								},
								success:function(obj) {
									if (obj) {
										var json = {
											no : obj.no,
											itemtype1descr : obj.itemtype1descr,
											producttypedescr : obj.producttypedescr,
											issetitemdescr : obj.issetitemdescr,
											sendday : obj.sendday,
											prior : obj.prior,
											remarks : obj.remarks,
											lastupdate : new Date(),
											lastupdatedby : czybh,
											expired : "F",
											actionlog : "ADD",
									  	};
						  				Global.JqGrid.addRowData("dataTable",json);
									}
								}
							});
						}
					}
				});
			});

			$("#update").on("click", function() {
				var ret=selectDataTableRow();
				var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
				if(!ret){
					art.dialog({
						content:"请选择一条记录",
					});
					return;
				}
				var nos = Global.JqGrid.allToJson("dataTable","no");
				var keys = nos.keys;
				var index = keys.indexOf(ret.no);// 获得下标值
				if (index > -1) keys.splice(index, 1);//去掉选择的no
				Global.Dialog.showDialog("update",{
					title:"供应商时限明细",
					url:"${ctx}/admin/supplier/goDDD",
					postData:{
						m_umState:"M",
						no:ret.no,
						keys:keys,
					},
					width:500,
					height:300,
					returnFun: function(data){
						if(data){
							$.ajax({
								url:"${ctx}/admin/supplier/getDDD",
								type:"post",
								data:{code:code,sendTimeNo:data.no},
								dataType:"json",
								error:function(obj){
									showAjaxHtml({"hidden": false, "msg": "获取数据出错"});
								},
								success:function(obj) {
									if (obj) {
										var json = {
											no : obj.no,
											itemtype1descr : obj.itemtype1descr,
											producttypedescr : obj.producttypedescr,
											issetitemdescr : obj.issetitemdescr,
											sendday : obj.sendday,
											prior : obj.prior,
											remarks : obj.remarks,
											lastupdate : new Date(),
											lastupdatedby : czybh,
											expired : "F",
											actionlog : "Edit",
									  	};
									  	$("#dataTable").setRowData(rowId,json);
									}
								}
							});
						}
					}
				});
			});

			// 子表格删除
			$("#delete").on("click",function(){
				var id = $("#dataTable").jqGrid("getGridParam","selrow");
				if(!id){
					art.dialog({content: "请选择一条记录进行删除操作",width: 220});
					return false;
				}
				art.dialog({
					content:"是否删除该记录？",
					lock: true,
					width: 200,
					height: 80,
					ok: function () {
						Global.JqGrid.delRowData("dataTable",id);
						// 删完后选中第一行
						var rowIds = $("#dataTable").jqGrid("getDataIDs");
						$("#dataTable").jqGrid("setSelection",rowIds[0]);
					},
					cancel: function () {
						return true;
					}
				});
				
			});

			$("#closeBut").on("click",function(){
				var changeDataTable= Global.JqGrid.allToJson("dataTable").detailJson;
				if (originalDataTable != changeDataTable) {
					art.dialog({
						content: "数据已变动,是否保存？",
						width: 200,
						okValue: "确定",
						ok: function () {
							doSave();
						},
						cancelValue: "取消",
						cancel: function () {
							closeWin();
						}
					});
				} else {
					closeWin();
				}
			});

		});


		function view() {
			var ret=selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条记录",
				});
				return;
			}
			Global.Dialog.showDialog("view",{
				title:"供应商时限明细",
				url:"${ctx}/admin/supplier/goDDD",
				postData:{
					m_umState:"V",
					no:ret.no,
				},
				width:500,
				height:300,
			});
		}

		function doSave() {
			var param= Global.JqGrid.allToJson("dataTable");
			var url = "${ctx}/admin/supplier/doSetDeliveryDeadline";
			/*if (param.datas.length == 0) {
				art.dialog({content: "详情表格数据为空",width: 200});
				return;
			}*/
			Global.Form.submit("page_form", url, param, function(ret){
				if(ret.rs==true){
					art.dialog({
						content: ret.msg,
						time: 500,
						beforeunload: function () {
							closeWin();
						}
					});
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content: ret.msg,
						width: 200
					});
				}
			});
		}
	</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn" onclick="doSave();">
						<span>保存</span>
					</button>
					<button class="btn btn-system" id="excel" onclick="doExcelNow('供应商发货时限')">
						<span>输出到Excel</span>
					</button>		
					<button type="button" class="btn btn-system" id="closeBut">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<li class="form-validate">
							<label for="code">商家编号</label>
							<input type="text" id="code" name="code" style="width:160px;" value="${supplier.code}" 
								placeholder="保存自动生成" readonly/>
						</li>
						<li class="form-validate">
							<label for="descr">商家名称</label>
							<input type="text" id="descr" name="descr" style="width:160px;" value="${supplier.descr}" 
								readonly/>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#detail" data-toggle="tab">详情</a></li>
			</ul>
		    <div class="tab-content">  
				<div id="detail"  class="tab-pane fade in active"> 
					<div class="pageContent">
				    	<div class="btn-panel">
							<div class="btn-group-sm">
								<button type="button" class="btn btn-system" id="save">
									<span>新增</span>
								</button>
								<button type="button" class="btn btn-system" id="update">
									<span>编辑</span>
								</button>
								<button type="button" class="btn btn-system" id="delete">
									<span>删除</span>
								</button>
								<button type="button" class="btn btn-system" id="view" onclick="view()">
									<span>查看</span>
								</button>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable"></table>
						</div>
					</div>
				</div>
			</div>	
		</div>
	</div>
</body>	
</html>
