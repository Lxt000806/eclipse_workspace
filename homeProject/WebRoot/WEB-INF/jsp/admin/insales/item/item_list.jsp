<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>Item列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script> 
<script type="text/javascript">
function Print(datas){
   $.form_submit($("#page_form").get(0), {
    	"action": "${ctx}/admin/item/lablePrint",
    	"jsonString": JSON.stringify(datas)
   });  
}
function doPrint(){
	var data;
	 art.dialog({
		 content:'<input type="radio" checked="checked" id="oneSelect" name="Select" />打印选中材料标签<br/><input type="radio" id="listSelect" name="Select" />打印列表材料标签<br/> ',
		 title: '材料信息--打印标签',
		 lock: true,
		 width: 300,
		 height: 150,
		 okValue: '取消', 
	   	 button: [{
					value: "打印小标签",
					callback:function(){
						var codes = "";
						if($("#oneSelect").is(':checked')){
							var row = selectDataTableRow();
				    		if(!row){
				    			art.dialog({content: "请选择要打印的材料！",width: 200});
				    			return false;
				    		}
				    		codes =row.code;	
						}else{
							var ids = $("#dataTable").getDataIDs();
				        	if(ids.length==0){
				        		Global.Dialog.infoDialog("当前页面无材料，请先刷新数据！");
				        		return;
				        	}
				        	
				        	$.each(ids,function(k,id){
				    			var row = $("#dataTable").jqGrid('getRowData', id);
				    			codes = codes  + $.trim(row.code) + ",";		
				    				
				    		});
				        	if (codes != "") {
				    			codes = codes.substring(0,codes.length-1);
				    		}
				        	
						}
						data={codes:codes,type:"small"};
						//window.open("${ctx}/admin/item/doPrint?codes="+codes+"type='small'");
						Print(data);
						<%-- var reportName = "item_smallLabel.jasper";
			        	Global.Print.showPrint(reportName, {
							Code: codes,
							LogoPath : "<%=basePath%>jasperlogo/",
							SUBREPORT_DIR: "<%=jasperPath%>" 
						}); --%>
					}			
				},
				{
		             value: "打印大标签",
			         callback: function () {
	   					var codes = "";
						if($("#oneSelect").is(':checked')){
							var row = selectDataTableRow();
				    		if(!row){
				    			art.dialog({content: "请选择要打印的材料！",width: 200});
				    			return false;
				    		}
				    		codes =row.code;
						}else{
							var ids = $("#dataTable").getDataIDs();
				        	if(ids.length==0){
				        		Global.Dialog.infoDialog("当前页面无材料，请先刷新数据！");
				        		return;
				        	}
				        	
				        	$.each(ids,function(k,id){
				    			var row = $("#dataTable").jqGrid('getRowData', id);
				    			codes = codes + $.trim(row.code) + ",";		
				    				
				    		});
				        	if (codes != "") {
				    			codes = codes.substring(0,codes.length-1);
				    		}
				        	
						}
		
						data={codes:codes,type:"big"};
						Print(data);
						
						<%-- var reportName = "item_bigLabel.jasper";
			        	Global.Print.showPrint(reportName, {
							Code: codes,
							LogoPath : "<%=basePath%>jasperlogo/",
							SUBREPORT_DIR: "<%=jasperPath%>" 
						}); --%>
		         }
		 }],
		 ok: function () {
			closeWin();
		 }
     
   	}); 
	
}

function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
}
function add(){	
	Global.Dialog.showDialog("Add",{			
		  title:"材料信息管理--新增",
		  url:"${ctx}/admin/item/goSave",
		  height:730,
		  width:1200,
		  returnFun: goto_query
	});
}
function copy(){
	var ret = selectDataTableRow();
    if (ret) {    
      Global.Dialog.showDialog("ItemCopy",{
		  title:"材料信息管理--复制",
		  url:"${ctx}/admin/item/goCopy?id="+ret.code,
		  height:730,
		  width:1200,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function update(){
	var ret = selectDataTableRow();
    if (ret) {    
      Global.Dialog.showDialog("ItemUpdate",{
		  title:"材料信息管理--修改",
		  url:"${ctx}/admin/item/goUpdate?id="+ret.code,
		  height:730,
		  width:1200,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function updatePrePrice(){
	var ret = selectDataTableRow();
    if (ret) {    
      Global.Dialog.showDialog("ItemUpdate",{
		  title:"材料信息管理--修改预算价格",
		  url:"${ctx}/admin/item/goUpdatePrePrice?id="+ret.code,
		  height: 700,
		  width:1000,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function updateInvinfo(){
	var ret = selectDataTableRow();
    if (ret) {    
      Global.Dialog.showDialog("ItemUpdate",{
		  title:"材料信息管理--修改库存信息",
		  url:"${ctx}/admin/item/goUpdateInvinfo?id="+ret.code,
		  height: 500,
		  width:700,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function updatePerfPer(){
	var ret = selectDataTableRow();
    if (ret) {    
      Global.Dialog.showDialog("ItemUpdate",{
		  title:"材料信息管理--修改业绩比例",
		  url:"${ctx}/admin/item/goUpdatePerfPer?id="+ret.code,
		  height: 500,
		  width:700,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function importExcel(){	
	Global.Dialog.showDialog("Add",{			
		  title:"材料信息管理--新增",
		  url:"${ctx}/admin/item/goItemImportExcel",
		  height: 720,
		  width:1100,
		  returnFun: goto_query
	});
}
function batchUpdate(){	
	Global.Dialog.showDialog("BatchUpdate",{			
		  title:"材料信息管理--修改",
		  url:"${ctx}/admin/item/goUpdateBatch",
		  height: 720,
		  width:1100,
		  returnFun: goto_query
	});
}
function view(){
	var ret = selectDataTableRow();
    if (ret) {    
      Global.Dialog.showDialog("ItemView",{
		  title:"材料信息管理--查看",
		  url:"${ctx}/admin/item/goView?id="+ret.code,
		  height: 720,
		  width:1200,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function doExcel(){
	var url = "${ctx}/admin/item/doExcel"; 
	doExcelAll(url);
}
function changeItemType2(){
	Global.LinkSelect.initOption("sqlCode","${ctx}/admin/brand/getBrandList",{itemType2:$("#itemType2").val()});
}
/**初始化表格*/
$(function(){
    //初始化材料类型1，2，3三级联动
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:'${item.itemType1 }',
								secondSelect:'itemType2',
								secondValue:'${item.itemType2 }',
								thridSelect:'itemType3',
								thirdValue:'${item.itemType3 }',});
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/item/goItemMessageJqGrid",
		height:$(document).height()-$("#content-list").offset().top-80,
		styleUI: 'Bootstrap',
	/* 	multiselect : true, */
		colModel : [
	        {name: "code", index: "code", width: 66, label: "材料编码", sortable: true, align: "left"},
			{name: "descr", index: "descr", width: 120, label: "名称", sortable: true, align: "left"},
			{name: "barcode", index: "barcode", width: 100, label: "条码", sortable: true, align: "left"},
			{name: "remcode", index: "remcode", width: 80, label: "助记码", sortable: true, align: "left"},
			{name: "itemtype1descr", index: "itemtype1descr", width: 75, label: "材料类型1", sortable: true, align: "left"},
			{name: "itemtype2descr", index: "itemtype2descr", width: 94, label: "材料类型2", sortable: true, align: "left"},
			{name: "itemtype3descr", index: "itemtype3descr", width: 92, label: "材料类型3", sortable: true, align: "left"},
			{name: "sqldescr", index: "sqldescr", width: 110, label: "品牌", sortable: true, align: "left"},
			{name: "uomdescr", index: "uomdescr", width: 50, label: "单位", sortable: true, align: "left"},
			{name: "perweight", index: "perweight", width: 65, label: "单位重量", sortable: true, align: "right"},
			{name: "pernum", index: "pernum", width: 65, label: "单位数量", sortable: true, align: "right"},
			{name: "packagenum", index: "packagenum", width: 54, label: "每箱片数", sortable: true, align: "right"},
			{name: "size", index: "size", width: 50, label: "尺寸", sortable: true, align: "left"},
			{name: "sizedesc", index: "sizedesc", width: 80, label: "规格", sortable: true, align: "left"},
			{name: "model", index: "model", width: 60, label: "型号", sortable: true, align: "left"},
			{name: "color", index: "color", width: 50, label: "颜色", sortable: true, align: "left"},
			{name: "sendtypedescr", index: "sendtypedescr", width: 66, label: "发货类型", sortable: true, align: "left"},
			{name: "whdescr", index: "whdescr", width: 124, label: "仓库", sortable: true, align: "left"},
			{name: "minqty", index: "minqty", width: 53, label: "最低库存", sortable: true, align: "left"},
			{name: "supplcode", index: "supplcode", width: 91, label: "商家编码", sortable: true, align: "left"},
			{name: "splcodedescr", index: "splcodedescr", width: 111, label: "商家名称", sortable: true, align: "left"},
			{name: "price", index: "price", width: 83, label: "现价", sortable: true, align: "right"},
			{name: "marketprice", index: "marketprice", width: 80, label: "市场价", sortable: true, align: "right"},
			{name: "projectcost", index: "projectcost", width: 92, label: "项目经理结算价", sortable: true, align: "right",hidden: true},
			{name: "isfixpricedescr", index: "isfixpricedescr", width: 80, label: "是否固定价格", sortable: true, align: "left"},
			{name: "cost", index: "cost", width: 78, label: "成本", sortable: true, align: "right",hidden: true},
			{name: "avgcost", index: "avgcost", width: 82, label: "移动平均成本", sortable: true, align: "right",hidden: true},
			{name: "commitypedescr", index: "commitypedescr", width: 86, label: "提佣类型", sortable: true, align: "left"},
			{name: "commiperc", index: "commiperc", width: 83, label: "提佣比例", sortable: true, align: "left"},
			{name: "allqty", index: "allqty", width: 74, label: "总库存量", sortable: true, align: "left"},
			{name: "remark", index: "remark", width: 159, label: "材料描述", sortable: true, align: "left"},
			{name: "ispromdescr", index: "ispromdescr", width: 65, label: "是否促销", sortable: true, align: "left"},
			{name: "oldprice", index: "oldprice", width: 70, label: "原售价", sortable: true, align: "right"},
			{name: "oldcost", index: "oldcost", width: 70, label: "原进价", sortable: true, align: "right",hidden: true},
			{name: "buyer1descr", index: "buyer1descr", width: 70, label: "买手1", sortable: true, align: "left"},
			{name: "buyer2descr", index: "buyer2descr", width: 70, label: "买手2", sortable: true, align: "left"},
			{name: "profitper", index: "profitper", width: 70, label: "参考毛利率", sortable: true, align: "right",hidden: true},
			{name: "crtdate", index: "crtdate", width: 80, label: "上架时间", sortable: true, align: "left", formatter: formatTime},
			{name: "isclearinvdescr", index: "isclearinvdescr", width: 83, label: "清库存产品", sortable: true, align: "left"},
			{name: "isfeedescr", index: "isfeedescr", width: 59, label: "是否费用", sortable: true, align: "left"},
			{name: "isinvdescr", index: "isinvdescr", width: 94, label: "是否库存管理", sortable: true, align: "left"},
			{name: "hassampledescr", index: "hassampledescr", width: 72, label: "是否上样", sortable: true, align: "left"},
			{name: "perfper", index: "perfper", width: 78, label: "业绩折扣", sortable: true, align: "right"},
			{name: "iscontaintaxdescr", index: "iscontaintaxdescr", width: 72, label: "含税价", sortable: true, align: "left"},
			{name: "lampnum", index: "lampnum", width: 78, label: "灯头数", sortable: true, align: "right"},
			{name: "installfeetypedescr", index: "installfeetypedescr", width: 72, label: "安装费类型", sortable: true, align: "left"},
			{name: "installfee", index: "installfee", width: 78, label: "安装费单价", sortable: true, align: "right"},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 96, label:"最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 79, label: "是否过期", sortable: true,align:"left"},
			{name: "actionlog", index: "actionlog", width: 83, label: "操作", sortable: true,align:"left"}
           ],
           gridComplete:function(){
			  if ($.trim("${isCostRight}")=="1"){
		        	$("#dataTable").jqGrid('showCol', "cost");
					$("#dataTable").jqGrid('showCol', "avgcost");
					$("#dataTable").jqGrid('showCol', "projectcost");
					$("#dataTable").jqGrid('showCol', "oldcost");
					$("#dataTable").jqGrid('showCol', "profitper");	
				}	  
		   },
		   ondblClickRow: function () {
          		view();
           }
	});
	$("#whCode").openComponent_wareHouse();  
	$("#supplCode").openComponent_supplier({condition:{ReadAll:"1"}});
	
});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" /> 
				<input type="hidden" id="isCostRight" value="${isCostRight}" readonly="true" /> 
				<input type="hidden" id="expired" name="expired" value="${item.expired}" />
				<ul class="ul-form">
					<li><label>材料编号</label> <input type="text" id="code" name="code" value="${item.code}" />
					</li>
					<li><label>材料名称</label> <input type="text" id="descr" name="descr" value="${item.descr}" />
					</li>
					<li><label>品牌</label> <select id="sqlCode" name="sqlCode"></select></li>
					<li><label>规格</label> <input type="text" id="size" name="sizeDesc" value="${item.sizeDesc}" />
					</li>
					<li><label>型号</label> <input type="text" id="model" name="model" value="${item.model}" />
					</li>
					<li><label>是否套餐材料</label> <house:xtdm id="IsSetItem" dictCode="YESNO" style="width:160px;"></house:xtdm>
					</li>
					<li><label>材料类型1</label> <select id="itemType1" name="itemType1"></select>
					</li>
					<li><label>材料类型2</label> <select id="itemType2" name="itemType2" onchange="changeItemType2()"></select>
					</li>
					<li><label>材料类型3</label> <select id="itemType3" name="itemType3"></select>
					</li>
					<li><label>供应商</label> <input type="text" id="supplCode" name="supplCode" />
					</li>
					<li><label>发货类型</label> <house:xtdm id="sendType" dictCode="ITEMAPPSENDTYPE"></house:xtdm>
					</li>
					<li><label>仓库</label> <input type="text" id="whCode" name="whCode" />
					</li>
					<li><label>是否上样</label> <house:xtdm id="hasSample" dictCode="YESNO"></house:xtdm>
					</li>
					<li><label>业绩折扣</label> <house:xtdm id="isPrefPre" dictCode="YESNO"></house:xtdm>
					</li>
					<li class="search-group"><input type="checkbox" id="expired" name="expired"
						value="${item.expired=='T'?'T':'F'}" onclick="changeExpired(this)" ${item.expired== 'T'?'':'checked' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>

			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="btn-panel">
		<div class="btn-group-sm">
			<house:authorize authCode="ITEM_ADD">
				<button type="button" class="btn btn-system " onclick="add()">新增</button>
			</house:authorize>
			<house:authorize authCode="ITEM_COPY">
				<button type="button" class="btn btn-system " onclick="copy()">复制</button>
			</house:authorize>
			<house:authorize authCode="ITEM_UPDATE">
				<button type="button" class="btn btn-system " onclick="update()">修改</button>
			</house:authorize>
			<house:authorize authCode="ITEM_UPDATEPREPRICE">
				<button type="button" class="btn btn-system " onclick="updatePrePrice()">修改预算价格</button>
			</house:authorize>
			<house:authorize authCode="ITEM_UPDATEINVINFO">
				<button type="button" class="btn btn-system " onclick="updateInvinfo()">库存信息</button>
			</house:authorize>
			<house:authorize authCode="ITEM_UPDATEPERFPER">
				<button type="button" class="btn btn-system " onclick=" updatePerfPer()">修改业绩比例</button>
			</house:authorize>
			<house:authorize authCode="ITEM_VIEW">
				<button type="button" class="btn btn-system " onclick="view()">查看</button>
			</house:authorize>
			<house:authorize authCode="ITEM_BATCHIMPORT">
				<button type="button" class="btn btn-system " onclick="importExcel()">批量导入</button>
			</house:authorize>
			<house:authorize authCode="ITEM_UPDATEBATCH">
				<button type="button" class="btn btn-system " onclick=" batchUpdate()">批量修改</button>
			</house:authorize>
			<house:authorize authCode="ITEM_VIEW">
				<button type="button" class="btn btn-system " onclick="doPrint()">打印标签</button>
			</house:authorize>
			<house:authorize authCode="ITEM_VIEW">
				<button type="button" class="btn btn-system " onclick="doExcel()">导出Excel</button>
			</house:authorize>
		</div>
	</div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</body>
</html>


