<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>集成补货信息——货到登记</title>
	<%@ include file="/commons/jsp/common.jsp"%>
	<!-- 修改bootstrap中各个row的边距 -->
	<style>
		.row{
			margin: 0px;
		}
		.col-sm-3{
			padding: 0px;
		}
		.col-sm-6{
			padding: 0px;
		}
		.col-sm-9{
			padding: 0px;
		}
		.col-sm-12{
			padding: 0px;
		}
	</style>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body">
		      	<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="arrive">
						<span>到货</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin()">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="body-box-list">  
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" id="expired" name="expired"/>
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<div class="row">
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width: 160px;"/>
							</li>
							<li>
								<label>供应商</label>
								<input type="text" id="intSpl" name="intSpl" style="width:160px;" value=""/>
							</li>
							<li>
								<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
								<button id="clear" type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
		<div class="container-fluid" > 
			<div class="pageContent">
				<div id="content-list">
					<table id="dataTable"></table>
					<div id="dataTablePager"></div>
				</div>
			</div> 
		</div>
	</div>
	<script type="text/javascript">
		var postData = $("#page_form").jsonForm();
		postData.m_umState = "arrive";
		$(function(){
			$("#intSpl").openComponent_supplier({condition: {itemType1:"JC",isDisabled:"1"}});
			Global.JqGrid.initJqGrid("dataTable", {
				sortable: true,
				url: "${ctx}/admin/intReplenishDT/goJqGridByNo",
				postData:postData ,
				height: $(document).height() - $("#content-list").offset().top - 70,
				styleUI: "Bootstrap", 
				colModel: [
					{name:"pk", index:"pk", width:80, label:"PK", sortable:true, align:"left", hidden: true},
					{name:"no", index:"no", width:80, label:"补货单号", sortable:true, align:"left"},
					{name:"address", index:"address", width:140, label:"楼盘", sortable:true, align:"left"},
					{name:"iscupboard",index:"iscupboard",width:80,label:"是否橱柜",sortable:true,align:"left", hidden:true},
					{name:"iscupboarddescr",index:"iscupboarddescr",width:80,label:"是否橱柜",sortable:true,align:"left"},
					{name:"intspl", index:"intspl", width:100, label:"供应商", sortable:true, align:"left", hidden: true},
					{name:"intspldescr", index:"intspldescr", width:100, label:"供应商", sortable:true, align:"left"},
					{name:"type", index:"type", width:80, label:"补货类型", sortable:true, align:"left", hidden: true},
					{name:"typedescr", index:"typedescr", width:80, label:"补货类型", sortable:true, align:"left"},
					{name:"prearrivdate", index:"prearrivdate", width:100, label:"预计到货日期", sortable:true, align:"left", formatter:formatDate},
					{name:"remarks", index:"remarks", width:160, label:"补件详情", sortable:true, align:"left"},
					{name:"date", index:"date", width:100, label:"补货时间", sortable:true, align:"left", formatter:formatDate},
				],
				ondblClickRow:function () {
					$("#arrive").trigger("click");
				}
			});
			/* 清空下拉选择树checked状态 */
			$("#arrive").on("click",function(){
				var ret = selectDataTableRow();
				if (ret) {
					Global.Dialog.showDialog("arrive",{
						title: "集成补货信息登记——到货",
						url: "${ctx}/admin/intReplenish/goArriveItem",
						postData: {pk: ret.pk,address:ret.address,intSpl:ret.intspl,intSplDescr:ret.intspldescr},
						height: 330,
						width: 450,
						returnFun: goto_query
					});
				} else {
					art.dialog({
						content: "请选择一条记录"
					});
				}
			});
		});
		function goto_query(tableId){
			var postData = $("#page_form").jsonForm();
			postData.m_umState = "arrive";
			if (!tableId || typeof(tableId)!="string"){
				tableId = "dataTable";
			}
			$(".s-ico").css("display","none");
			$("#"+tableId).jqGrid("setGridParam",{
				postData:postData,
				page:1,
				sortname:''
			}).trigger("reloadGrid");
		}
	</script>
</body>
</html>
