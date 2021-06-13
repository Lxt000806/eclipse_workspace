<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>材料需求查询</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp"%>
		<script src="${resourceRoot}/pub/component_brand.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
		<script type="text/javascript">
			/**初始化表格*/
			var tableId="dataTable";
			$(function(){
				Global.JqGrid.initJqGrid("dataTable",{
					height:$(document).height()-$("#content-list").offset().top-100,
					styleUI: 'Bootstrap',
					colModel : [
					    {name: "address", index: "address", width: 130, label: "楼盘", sortable: true, align: "left"},
						{name: "custtypedescr", index: "custtypedescr", width: 65, label: "客户类型", sortable: true, align: "left"},
						{name: "custdescr", index: "custdescr", width: 75, label: "客户姓名", sortable: true, align: "left"},
						{name: "mobile1", index: "mobile1", width: 100, label: "客户电话", sortable: true, align: "left", hidden: true},
						{name: "itemtype2descr", index: "itemtype2descr", width: 90, label: "材料类型2", sortable: true, align: "left"},
						{name: "fixareadescr", index: "fixareadescr", width: 100, label: "区域名称", sortable: true, align: "left"},
						{name: "itemdescr", index: "itemdescr", width: 140, label: "材料名称", sortable: true, align: "left"},
						{name: "unitdescr", index: "unitdescr", width: 50, label: "单位", sortable: true, align: "left"},
						{name: "qty", index: "qty", width: 75, label: "需求数量", sortable: true, align: "right", sum: true},
						{name: "confirmedqty", index: "confirmedqty", width: 80, label: "已确认需求数量", sortable: true, align: "right", sum: true},
						{name: "sendqty", index: "sendqty", width: 67, label: "已发货数量", sortable: true, align: "right", sum: true},
						{name: "lessqty", index: "lessqty", width: 90, label: "差额（备货）", sortable: true, align: "right", sum: true},
						{name: "unitprice", index: "unitprice", width: 60, label: "单价", sortable: true, align: "right"},
						{name: "cost", index: "cost", width: 60, label: "成本", sortable: true, align: "right", hidden: true},
						{name: "newestprice", index: "newestprice", width: 70, label: "最新单价", sortable: true, align: "right"},
						{name: "newestcost", index: "newestcost", width: 70, label: "最新成本", sortable: true, align: "right", hidden: true},
						{name: "remark", index: "remark", width: 170, label: "材料描述", sortable: true, align: "left"}
		            ]
					
				});
				if('${itemReq.costRight}'=='1'){
					$("#dataTable").jqGrid('showCol', "cost");
					$("#dataTable").jqGrid('showCol', "newestcost");
				}
				if('${itemReq.phoneRight}'=='1'){
					$("#dataTable").jqGrid('showCol', "mobile1");
				}
				$("#sqlCode").openComponent_brand();
				$("#itemCode").openComponent_item();
			});
			function goto_query(){
				var postData = $("#page_form").jsonForm();
				if(!postData.itemCode&&!postData.sqlCode){
						art.dialog({
						content: "请选择材料编号或品牌编号！"
					});
					return;
				}
				$("#"+tableId).jqGrid("setGridParam",{url:"${ctx}/admin/itemReqQuery/goJqGrid",postData:postData,page:1,sortname:''}).trigger("reloadGrid");
			}
			function doExcel(){
				var url = "${ctx}/admin/itemReqQuery/doExcel";
				doExcelAll(url,tableId);
			}
			function change(ele){
				if(ele.value=="1"){
					tableId="itemDataTable";
					$("#content-list").css("display","none");
					$("#content-list2").css("display","block");
					Global.JqGrid.initJqGrid("itemDataTable",{
						url:"${ctx}/admin/itemReqQuery/goJqGrid",
						postData:$("#page_form").jsonForm(),
						height:$(document).height()-$("#content-list2").offset().top-70,
						styleUI: 'Bootstrap',
						colModel : [
						    {name: "address", index: "address", width: 160, label: "楼盘", sortable: true, align: "left"},
							{name: "custtypedescr", index: "custtypedescr", width: 100, label: "客户类型", sortable: true, align: "left"},
							{name: "custdescr", index: "custdescr", width: 95, label: "客户姓名", sortable: true, align: "left"},
							{name: "mobile1", index: "mobile1", width: 112, label: "客户电话", sortable: true, align: "left"}
			            ]
					});
				}else{
					tableId="dataTable";
					$("#content-list2").css("display","none");
					$("#content-list").css("display","block");
				}
			}
			function clearForm(){
				$("#page_form input[type='text']").val('');
				$("#page_form select").val('');
				$("#groupBy").val('0');
				$("#sendStatus").val('');
				$.fn.zTree.getZTreeObj("tree_sendStatus").checkAllNodes(false);
				$("#customerStatus").val('');
				$.fn.zTree.getZTreeObj("tree_customerStatus").checkAllNodes(false);
			}
		</script>
	</head>
	<body>
		<div class="body-box-list">
			<div class="query-form" >
				<form action="" method="post" id="page_form" class="form-search" >
					<input type="hidden" id="expired" name="expired" value="${customer.expired}" />
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>材料编号</label>
							<input type="text" id="itemCode" name="itemCode"   />
						</li>
						<li>
							<label>品牌</label>
							<input type="text" id="sqlCode" name="sqlCode"  />
						</li>
						<li>
							<label>客户状态</label>	
							  <house:xtdmMulit id="customerStatus" dictCode="" sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='CUSTOMERSTATUS'  " selectedValue="4"></house:xtdmMulit>
						</li>
						<li>
							<label>发货状态</label>	
							  <house:xtdmMulit id="sendStatus" dictCode="" sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where id='SendStatus'  " selectedValue="0,2"></house:xtdmMulit>
						</li>
						
						<li>
							<label>是否促销</label>
							<select id="isProm" name="isProm" >
								<option value=""></option>
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</li>
						<li>			
							<label>统计方式</label>
							<select id="groupBy" name="groupBy" onchange="change(this)">
							<option value="0">明细</option>
							<option value="1">按客户汇总</option>
							</select>
						</li>
						<li class="search-group" >
								<input type="checkbox"  id="expired" name="expired"  value="${itemReq.expired=='T'?'T':'F'}" onclick="changeExpired(this)" ${itemReq.expired=='T'?'':'checked' }/><p>隐藏过期</p>
								<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
								<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
						</li>
					</ul>	
				</form>
			</div>
			
			<div class="btn-panel" >
				<div class="btn-group-sm" >
					<house:authorize authCode="ITEMREQQUERY_EXCEL">
						<button type="button" class="btn btn-system"  onclick="doExcel()">导出excel</button>
					</house:authorize>
				</div>
			</div>
			
			<div class="pageContent">
				<div id="content-list">
					<table id= "dataTable"></table>
					<div id="dataTablePager"></div>
				</div>
				<div id="content-list2"  style="display: none">
				<table id= "itemDataTable"></table>
				<div id="itemDataTablePager"></div>
			</div>
			</div> 
		</div>
	</body>
</html>


