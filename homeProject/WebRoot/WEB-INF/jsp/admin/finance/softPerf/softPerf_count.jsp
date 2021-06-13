<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <title>业绩计算</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
	function doExcel(){
		var url = "${ctx}/admin/softPerf/doCountExcel";
		doExcelAll(url);
	}
	
	function doCount(){
		$.ajax({
			url:"${ctx}/admin/softPerf/doCount",
			type: "post",
			data: {no:"${no}"},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
				$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
				art.dialog({
					content	:"生成数据成功",			
				});
		    }
		 });
	}
	
	function view(){
		var ret =selectDataTableRow();
		var countType=$.trim($("#countType").val());
		var no=$.trim($("#no").val());
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		Global.Dialog.showDialog("viewReport",{
			title:"查看业绩明细",
			postData:{pk:ret.pk},
			url:"${ctx}/admin/softPerf/goSoftPerfDetail",
			height: 650,
		 	width:1050,
			returnFun: goto_query 
		});
	}
	
	/**初始化表格*/
	$(function(){
		if("${view}"=="view"){
			$("#doCount").attr("disabled","disabled");
		}
		$("#custCode").openComponent_customer({condition:{status:"4,5"}});	
		$("#buyer").openComponent_employee();	
		$("#designMan").openComponent_employee();	
		$("#businessMan").openComponent_employee();	
	
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/softPerf/goCountSoftPerJqGrid",
			postData:{no:"${no}"},
			height:$(document).height()-$("#content-list").offset().top-102,
			styleUI: "Bootstrap", 
			colModel : [
			  {name : "pk",index : "pk",width : 100,label:"pk",sortable : true,align : "left",hidden:true},
			  {name : "custcode",index : "custcode",width : 75,label:"客户编号",sortable : true,align : "left",count:true},
			  {name : "custdescr",index : "custdescr",width : 75,label:"客户名称",sortable : true,align : "left"},
			  {name : "documentno",index : "documentno",width : 75,label:"档案号",sortable : true,align : "left"},
			  {name : "custtypedescr",index : "custtypedescr",width : 75,label:"客户类型",sortable : true,align : "left"},
			  {name : "address",index : "address",width : 120,label:"楼盘",sortable : true,align : "left"},
			  {name : "type",index : "type",width : 100,label:"类型编号",sortable : true,align : "left",hidden:true},
			  {name : "typedescr",index : "typedescr",width : 75,label:"发放类型",sortable : true,align : "left"},
			  {name : "iano",index : "iano",width : 75,label:"领料编号",sortable : true,align : "left"},
			  {name : "itemcode",index : "itemcode",width : 75,label:"材料编号",sortable : true,align : "left"},
			  {name : "itemdescr",index : "itemdescr",width : 75,label:"产品名称",sortable : true,align : "left"},
			  {name : "item12descr",index : "item12descr",width : 75,label:"材料分类",sortable : true,align : "left"},
			  {name : "item2descr",index : "item2descr",width : 75,label:"材料类型2",sortable : true,align : "left"},
			  {name : "qty",index : "qty",width : 60,label:"数量",sortable : true,align : "right",sum:true},
			  {name : "unitprice",index : "unitprice",width : 60,label:"单价",sortable : true,align : "right",sum:true},
			  {name : "beflineamount",index : "beflineamount",width : 65,label:"折扣前金额",sortable : true,align : "right",sum:true},
			  {name : "markup",index : "markup",width : 60,label:"折扣",sortable : true,align : "right"},
			  {name : "processcost",index : "processcost",width : 60,label:"其他费用",sortable : true,align : "right",sum:true},
			  {name : "lineamount",index : "lineamount",width : 60,label:"总价",sortable : true,align : "right",sum:true},
			  {name : "disccost",index : "disccost",width : 60,label:"优惠分摊",sortable : true,align : "right",sum:true},
			  {name : "perfamount",index : "perfamount",width : 60,label:"销售额",sortable : true,align : "right",sum:true},
			  {name : "perfper",index : "perfper",width : 80,label:"业绩比例",sortable : true,align : "right"},
			  {name : "perfcard",index : "perfcard",width : 80,label:"业绩基数",sortable : true,align : "right"},
			  {name : "cost",index : "cost",width : 60,label:"成本",sortable : true,align : "right",sum:true,hidden:${costRight=='1'?false:true}},
			  {name : "costamount",index : "costamount",width : 60,label:"成本额",sortable : true,align : "right",sum:true,hidden:${costRight=='1'?false:true}},
			  {name : "custcheckdate",index : "custcheckdate",width : 100,label:"客户结算日期",sortable : true,align : "left",formatter:formatDate},
			  {name : "confirmdate",index : "confirmdate",width : 100,label:"领料审核日期",sortable : true,align : "left",formatter:formatDate},
			  {name : "orderdate",index : "orderdate",width : 100,label:"下单日期",sortable : true,align : "left",formatter:formatDate},
			  {name : "per",index : "per",width : 105,label:"业务员发放比例",sortable : true,align : "right"},
			  {name : "otherempper",index : "otherempper",width : 120,label:"其他角色发放比例",sortable : true,align : "right"},
			  {name : "profitper",index : "profitper",width : 60,label:"毛利率",sortable : true,align : "right",hidden:${costRight=='1'?false:true}},
			  {name : "businessmandescr",index : "businessmandescr",width : 75,label:"业务员",sortable : true,align : "left"},
			  {name : "businessdept2descr",index : "businessdept2descr",width : 75,label:"业务员部门",sortable : true,align : "left"},
			  {name : "designmandescr",index : "designmandescr",width : 75,label:"设计师",sortable : true,align : "left"},
			  {name : "designleaderdescr",index : "designleaderdescr",width : 75,label:"设计所所长",sortable : true,align : "left"},
			  {name : "designdept2descr",index : "designdept2descr",width : 75,label:"设计师部门",sortable : true,align : "left"},
			  {name : "againmandescr",index : "againmandescr",width : 75,label:"翻单员",sortable : true,align : "left",sum:true},
			  {name : "buyerdescr",index : "buyerdescr",width : 75,label:"买手",sortable : true,align : "left"},
			  {name : "buyerdept2descr",index : "buyerdept2descr",width : 75,label:"买手部门",sortable : true,align : "left"},
			  {name : "buyer2descr",index : "buyer2descr",width : 75,label:"买手2",sortable : true,align : "left"},
			  {name : "buyer2dept2descr",index : "buyer2dept2descr",width : 75,label:"买手2部门",sortable : true,align : "left"},
			  {name : "designmancommiper",index : "designmancommiper",width : 90,label:"设计师抽成点数",sortable : true,align : "right"},
			  {name : "designmancommi",index : "designmancommi",width : 75,label:"设计师提成",sortable : true,align : "right",sum:true,formatter:DiyFmatter},
			  {name : "buyercommiper",index : "buyercommiper",width : 90,label:"买手抽成点数",sortable : true,align : "right"},
			  {name : "buyercommi",index : "buyercommi",width : 60,label:"买手提成",sortable : true,align : "right",sum:true,formatter:DiyFmatter},
			  {name : "buyer2commiper",index : "buyer2commiper",width : 90,label:"买手2抽成点数",sortable : true,align : "right"},
			  {name : "buyer2commi",index : "buyer2commi",width : 60,label:"买手2提成",sortable : true,align : "right",sum:true,formatter:DiyFmatter},
			  {name : "businessmancommi",index : "businessmancommi",width : 75,label:"业务员提成",sortable : true,align : "right",sum:true,formatter:DiyFmatter},
			  {name : "againmancommiper",index : "againmancommiper",width : 65,label:"翻单员提成点数",sortable : true,align : "right"},
			  {name : "againmancommi",index : "againmancommi",width : 65,label:"翻单员提成",sortable : true,align : "right",formatter:DiyFmatter,sum:true},
			  {name : "prodmgrcommiper",index : "prodmgrcommiper",width : 65,label:"产品经理提成点数",sortable : true,align : "right"},
			  {name : "prodmgrcommi",index : "prodmgrcommi",width : 65,label:"产品经理提成",sortable : true,align : "right",formatter:DiyFmatter,sum:true},
			  {name : "isclearinvdescr",index : "isclearinvdescr",width : 65,label:"是否清库存",sortable : true,align : "left"},
			  {name : "itemreqremark",index : "itemreqremark",width : 150,label:"材料需求备注",sortable : true,align : "left"},
			  {name : "isinternaldescr",index : "isinternaldescr",width : 65,label:"内部员工",sortable : true,align : "left"},
			  {name : "lastupdate",index : "lastupdate",width : 100,label:"最后更新时间",sortable : true,align : "left",formatter:formatDate},
			  {name : "lastupdatedby",index : "lastupdatedby",width : 100,label:"最后更新人",sortable : true,align : "left"},
			  {name : "expired",index : "expired",width : 100,label:"是否过期",sortable : true,align : "left"},
			  {name : "actionlog",index : "actionlog",width : 100,label:"操作类型",sortable : true,align : "left"},
			],
			ondblClickRow: function(){
				view();
			}
		});
		function DiyFmatter (cellvalue, options, rowObject){ 
		    return myRound(cellvalue,2);
		}
	});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
		    <div class="panel-body">
		      	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="doCount" onclick="doCount()">
						<span>业绩数据生成</span>
					</button>
					<button type="button" class="btn btn-system" onclick="view()">
						<span>查看</span>
					</button>
					<button type="button" class="btn btn-system" onclick="doExcel()" title="导出检索条件数据">
						<span>导出Excel</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" style="width:160px;" id="no" name="no" value="${no }"/>
				<ul class="ul-form">
					<li>
						<label>客户编号</label>
						<input type="text" style="width:160px;" id="custCode" name="custCode"/>
					</li>
					<li>
						<label>发放类型</label>
						<house:xtdm id="softPerType" dictCode="SOFTPERFTYPE" style="width:160px;"></house:xtdm>
					</li>
					<li>
						<label>设计师</label>
						<input type="text" style="width:160px;" id="designMan" name="designMan"/>
					</li>
					<li>
						<label>买手</label>
						<input type="text" style="width:160px;" id="buyer" name="buyer"/>
					</li>
					<li>
						<label>业务员</label>
						<input type="text" style="width:160px;" id="businessMan" name="businessMan"/>
					</li>					
					<li>
						<label>领料审核日期从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>	
			</form>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
