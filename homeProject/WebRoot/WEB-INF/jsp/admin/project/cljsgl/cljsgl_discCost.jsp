<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>材料结算管理--优惠分摊</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	function goto_query(){
		var itemType1=$.trim($("#itemType1").val());
		var custCode=$.trim($("#custCode").val());
		if(itemType1!=""&&custCode!=""){
			$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/cljsgl/goJqGridDiscCost",datatype:"json",postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
		}
	}
	function getData(data){
		if (!data) return;
		goto_query();
		var dataSet = {
			firstSelect:"itemType1",
			firstValue:"${itemCheck.itemType1}",
		};
		Global.LinkSelect.setSelect(dataSet);
		$("#address").val(data.address);
	}
	/**初始化表格*/
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");						
		Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:'${itemCheck.itemType1 }',
								});
								
		Global.JqGrid.initJqGrid("dataTable",{
			height:$(document).height()-$("#content-list").offset().top-120,
			rowNum: 10000,
			styleUI:"Bootstrap",
			multiselect: true,
			colModel:[
				{name: "ischeck", index: "ischeck", width: 69, label: "勾选", sortable: true, align: "left",hidden:true},
				{name: "iscommi", index: "iscommi", width: 84, label: "是否提成", sortable: true, align: "left", hidden: true},
				{name: "pk", index: "pk", width: 84, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "fixareadescr", index: "fixareadescr", width: 107, label: "区域名称", sortable: true, align: "left"},
				{name: "fixareapk", index: "fixareapk", width: 107, label: "区域名称", sortable: true, align: "left",hidden:true},
				{name: "intprodpk", index: "intprodpk", width: 107, label: "区域名称", sortable: true, align: "left",hidden:true},
				{name: "itemtype2descr", index: "itemtype2descr", width: 105, label: "材料类型2", sortable: true, align: "left"},
				{name: "itemcode", index: "itemcode", width: 75, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 125, label: "材料名称", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 75, label: "材料类型2", sortable: true, align: "left", hidden: true},
				{name: "sqlcodedescr", index: "sqlcodedescr", width: 75, label: "品牌", sortable: true, align: "left", hidden: true},
				{name: "qty", index: "qty", width: 55, label: "数量", sortable: true, align: "right"},
				{name: "uom", index: "uom", width: 55, label: "单位", sortable: true, align: "left"},
				{name: "marketprice", index: "marketprice", width: 55, label: "市场价", sortable: true, align: "right", hidden: true},
				{name: "unitprice", index: "unitprice", width: 55, label: "单价", sortable: true, align: "right"},
				{name: "beflineamount", index: "beflineamount", width: 85, label: "折扣前金额", sortable: true, align: "right", sum: true},
				{name: "markup", index: "markup", width: 55, label: "折扣", sortable: true, align: "right"},
				{name: "disccost", index: "disccost", width: 90, label: "优惠分摊成本", sortable: true, align: "right", sum: true},
				{name: "tmplineamount", index: "tmplineamount", width: 55, label: "小计", sortable: true, align: "right", sum: true},
				{name: "processcost", index: "processcost", width: 55, label: "其他费用", sortable: true, align: "right", sum: true},
				{name: "lineamount", index: "lineamount", width: 55, label: "总价", sortable: true, align: "right", sum: true},
				{name: "disccost", index: "disccost", width: 104, label: "优惠分摊成本", sortable: true, align: "right", sum: true, hidden: true},
				{name: "remark", index: "remark", width: 177, label: "材料描述", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 126, label: "最后修改日期", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 78, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
				{name: "actionlog", index: "actionlog", width: 68, label: "操作", sortable: true, align: "left"},
				{name: "reqpk", index: "reqpk", width: 20, label: "需求PK", sortable: true, align: "left"}
			],
			gridComplete:function(){
				var disccostSum=parseFloat($("#dataTable").getCol("disccost",false,'sum')).toFixed(4);
            	$("#dataTable").footerData('set',{'disccost':myRound(disccostSum,2)});
			}
			
		});		
  		$("#custCode").openComponent_customer({callBack: getData,condition:{status:"4,5",disabled:"disabled",},valueOnly:"1"});
	});
	function save(){
		var custCode=$("#custCode").val();
		var itemType1=$("#itemType1").val();
		var discCost=$("#discCost").val();		
		var ids=$("#dataTable").jqGrid("getGridParam","selarrrow");
			if(ids.length == 0){
				art.dialog({
					content:"请选择要进行分摊的材料需求！"
				});				
				return ;
			}
		var selectRows = [];
		$.each(ids,function(i,id){
			var rowData = $("#dataTable").jqGrid("getRowData",id);
			selectRows.push(rowData);
		});
	
		var param = {"salesInvoiceDetailJson":JSON.stringify(selectRows)};
		art.dialog({
			content:"是否要进行优惠分摊",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
 				Global.Form.submit("page_form","${ctx}/admin/cljsgl/docljsglDiscCost",param,function(ret){
					if(ret.rs==true){
						art.dialog({
							content: ret.msg,
							time: 1000,
							beforeunload: function () {
								$("#discCost").val("0");
			    				goto_query();
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
 			},
			cancel: function () {
					return true;
			}
		});
	}
	
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		      <div class="btn-group-xs" >
		      	<button type="button" id="saveBtn" class="btn btn-system" onclick="save()">确认</button>
		      	<button type="button" class="btn btn-system " onclick="doExcelNow('优惠分摊成本')">导出excel</button>
				<button type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>
		      </div>
		   	</div>
		</div>
        <div class="query-form" >  
	        <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame" >
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" id="expired" name="expired" value="${itemCheck.expired}" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li >
						<label >客户编号</label>
						<input type="text" id="custCode" name="custCode" style="width:160px;" value="${itemCheck.custCode}" onchange="goto_query()"/>
					</li>
					<li >
						<label >楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" value="${itemCheck.address}" readonly="readonly"/>
					</li>	
					<li>
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1" onchange="goto_query()"></select>
					</li>			
					<li >
						<label >优惠分摊成本</label>
						<input type="text" id="discCost" name="discCost" style="width:160px;" value="${itemCheck.discCost}" />元
					</li>						
					<li hidden="true">																	
						<button type="button" class="btn btn-system btn-sm" onclick="goto_query();" >查询</button>
					</li>
				</ul>
			</form>
		</div>
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


