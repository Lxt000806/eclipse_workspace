<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>毛利率业绩配置表</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>

<script type="text/javascript">
	function add() {
		Global.Dialog.showDialog("profitPerfAdd", {
			title : "毛利率业绩配置--增加",
			url : "${ctx}/admin/profitPerf/goSave",
			height : 500,
			width : 800,
			returnFun : goto_query
		});
	}
	

	function update() {
		var ret = selectDataTableRow();
		if (ret) {
			Global.Dialog.showDialog("profitPerfUpdate", {
				title : "毛利率业绩配置--修改",
				url : "${ctx}/admin/profitPerf/goUpdate?id=" + ret.PK,
				height : 500,
				width : 800,
				returnFun : goto_query
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	}

	function view() {
		var ret = selectDataTableRow();
		if (ret) {
			Global.Dialog.showDialog("profitPerfView", {
				title : "毛利率业绩配置",
				url : "${ctx}/admin/profitPerf/goDetail?id=" + ret.PK,
				height : 400,
				width : 800
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	}

	function del() {
		var ret = selectDataTableRow();
		if (ret) {
			art.dialog({
				content : "确认删除该记录",
				ok : function() {
					$.ajax({
						url : "${ctx}/admin/profitPerf/doDelete?pk=" + ret.PK,
						type : "post",
						dataType : "json",
						cache : false,
						error : function(obj) {
							art.dialog({
								content : "删除出错,请重试",
								time : 1000,
								beforeunload : function() {
									goto_query();
								}
							});
						},
						success : function(obj) {
							if (obj.rs) {
								goto_query();
							} else {
								$("#_form_token_uniq_id").val(obj.token.token);
								art.dialog({
									content : obj.msg,
									width : 200
								});
							}
						}
					});
				},
				cancel : function() {
					goto_query();
				}
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	}
	//导出EXCEL
	function doExcel() {
		var url = "${ctx}/admin/profitPerf/doExcel";
		doExcelAll(url);
	}
	
	function commiPer() {
		Global.Dialog.showDialog("profitPerfCommiPer", {
			title : "毛利率业绩配置--提成预发比例",
			url : "${ctx}/admin/profitPerf/goCommiPer", 
			height : 600,
			width : 800,
		});
	}
	/**初始化表格*/
	$(function() {
	    
	    Global.JqGrid.initJqGrid("dataTable", {
	        url: "${ctx}/admin/profitPerf/goJqGrid",
	        height: $(document).height() - $("#content-list").offset().top - 90,
	        styleUI: 'Bootstrap',
	        colModel: [
	            {name: 'PK', index: 'pk', width: 100, label: 'pk', sortable: true, align: "left", hidden: true},
	            {name: 'descr', index: 'descr', width: 80, label: '材料分类', sortable: true, align: "left"},
	            {name: 'FromProfit', index: 'FromProfit', width: 80, label: '毛利率从', sortable: true, align: "left"},
	            {name: 'ToProfit', index: 'ToProfit', width: 80, label: '毛利率至', sortable: true, align: "left"},
	            {name: 'note', index: 'note', width: 90, label: '是否滞销品', sortable: true, align: "left"},
	            {name: 'DesignCommiPer', index: 'DesignCommiPer', width: 100, label: '设计师提成点数', sortable: true, align: "left"},
	            {name: 'BuyerCommiPer', index: 'BuyerCommiPer', width: 100, label: '买手提成点数', sortable: true, align: "left"},
	            {name: 'Buyer2CommiPer', index: 'Buyer2CommiPer', width: 100, label: '买手2提成点数', sortable: true, align: "left",},
	            {name: 'AgainManCommiPer', index: 'AgainManCommiPer', width: 100, label: '翻单员提成点数', sortable: true, align: "left",},
	            {name: 'BusinessManCommiPer', index: 'BusinessManCommiPer', width: 100, label: '业务员提成点数', sortable: true, align: "left",},
	            {name: 'ProdMgrCommiPer', index: 'ProdMgrCommiPer', width: 100, label: '产品部经理提成点数', sortable: true, align: "left",},
	            {name: 'OutBusinessManCommiPer', index: 'OutBusinessManCommiPer', width: 100, label: '外部业务员提成点数', sortable: true, align: "left",},
	            {name: 'SoftBusinessManCommiPer', index: 'SoftBusinessManCommiPer', width: 100, label: '软装业务员提成点数', sortable: true, align: "left",},
	            {name: 'LastUpdatedBy', index: 'LastUpdatedBy', width: 100, label: '最后修改人员', sortable: true, align: "left"},
	            {name: 'LastUpdate', index: 'LastUpdate', width: 120, label: '最后修改时间', sortable: true, align: "left", formatter: formatTime},
	            {name: 'Expired', index: 'Expired', width: 100, label: '是否过期', sortable: true, align: "left"},
	            {name: 'ActionLog', index: 'ActionLog', width: 100, label: '操作日志', sortable: true, align: "left"}
	        ]
	    })
	
	})
</script>
</head>

<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>材料类型</label> <house:dict id="itemType12"
							dictCode=""
							sql="select a.Code,a.code+' '+a.descr descr  from tItemType12 a  where  a.Expired='F' and a.ItemType1='RZ'  order By Code "
							sqlValueKey="Code" sqlLableKey="Descr" value="${profitPerf.Code}">
						</house:dict>
					</li>
					<li><label>是否滞销品</label> <house:xtdm id="IsClearInv"
							dictCode="YESNO" value="${profitPerf.isClearInv}"></house:xtdm>
					</li>
					<li><label>设计师提成点数</label> <input type="text"
						id="designCommiPer" name="designCommiPer" style="width:160px;"
						value="${profitPerf.designCommiPer}" />
					</li>
					<li><label>买手提成点数</label> <input type="text"
						id="buyerCommiPer" name="buyerCommiPer" style="width:160px;"
						value="${profitPerf.buyerCommiPer}" />
					</li>
					<li class="search-group"><input type="checkbox" id="expired"
						name="expired" value="${profitPerf.expired}"
						onclick="hideExpired(this)" ${profitPerf.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button></li>
				</ul>
			</form>
		</div>

		<div class="clear_float"></div>

		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system " onclick="add()">新增</button>
				<button type="button" class="btn btn-system " onclick="update()">编辑</button>
				<house:authorize authCode="ITEMPLAN_GIFTMANAGE">
					<button type="button" class="btn btn-system " onclick="commiPer()">提成预发比例</button>
				</house:authorize>
				<button id="btnDel" type="button" class="btn btn-system "
					onclick="del()">删除</button>
				<button id="btnView" type="button" class="btn btn-system "
					onclick="view()">查看</button>	
				<button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>
