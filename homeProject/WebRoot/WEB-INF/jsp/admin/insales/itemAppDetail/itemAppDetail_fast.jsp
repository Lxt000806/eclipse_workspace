<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
</head>
    
<body>
	<div class="body-box-list">
		<div class="panelBar">
           	<ul>
               	<li>
                   	<a href="javascript:void(0)" class="a1" id="saveBtn">
				       <span>保存</span>
					</a>	
                   </li>
               	<li>
					<a href="javascript:void(0)" class="a2" id="selectallBtn">
						<span>全选</span>
					</a>
				</li>
				<li>
					<a href="javascript:void(0)" class="a3" id="unselectallBtn">
						<span>不选</span>
					</a>
				</li>
				<li id="closeBut" onclick="closeWin(false)">
					<a href="javascript:void(0)" class="a2">
						<span>关闭</span>
					</a>
				</li>				
                <li class="line"></li>
               </ul>
			<div class="clear_float"> </div>
		</div>
		<div class="query-form">
			<form action="" method="post" id="page_form" >
				<input type="hidden" id="expired" name="expired" value="${item.expired}" />
				<table cellpadding="0" cellspacing="0" width="100%">
						<col  width="82" />
						<col  width="25.4%"/>
						<col  width="82"/>
						<col  width="25.3%"/>
						<col  width="82" />
						<col  width="25.4%"/>
					<tbody>
						<tr class="td-btn">	
							<td class="td-label">材料编码</td>
							<td class="td-value">
							<input type="text" id="code" name="code" style="width:160px;"  value="${item.code}" />
							</td>
							<td class="td-label">品牌</td>
							<td class="td-value">
							<select id="sqlCode" name="sqlCode" style="width:166px;"></select>
							</td>							
							<td class="td-label">条码</td>
							<td class="td-value">
							<input type="text" id="barCode" name="barCode" style="width:160px;"  value="${item.barCode}" />
							</td>
						</tr>
						<tr>
							<td class="td-label">供应商</td>
							<td class="td-value">
							<input type="text" id="supplCode" name="supplCode" style="width:160px;"  value="${item.supplCode}" />
							</td>
							<td class="td-label">材料名称</td>
							<td class="td-value">
							<input type="text" id="descr" name="descr" style="width:160px;"  value="${item.descr}" />
							</td>
							<td class="td-label">规格</td>
							<td class="td-value">
							<input type="text" id="itemSize" name="itemSize" style="width:160px;"  value="${item.itemSize}" />
							</td>																				
						</tr>
						<tr class="td-btn">	
							<td class="td-label">型号</td>
							<td class="td-value">
							<input type="text" id="model" name="model" style="width:160px;"  value="${item.model}" />
							</td>
							<td class="td-label">材料类型1</td>
							<td class="td-value">
							<select id="itemType1" name="itemType1" style="width:166px;"></select>
							</td>
							<td class="td-label">材料类型2</td>
							<td class="td-value">
							<select id="itemType2" name="itemType2" style="width:166px;"></select>
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">材料类型3</td>
							<td class="td-value">
							<select id="itemType3" name="itemType3" style="width:166px;"></select>
							</td>
						</tr>
						<tr>
							<td colspan="6" class="td-btn">
								<div class="sch_qk_con"> 
									<input type="checkbox" id="expired_show" name="expired_show" value="${item.expired}" onclick="hideExpired(this)" ${item.expired!='T'?'checked':'' }/>隐藏过期记录&nbsp;
									<input onclick="goto_query();"  class="i-btn-s" type="button"  value="检索"/>
									<input onclick="clearForm();"  class="i-btn-s" type="button"  value="清空"/>
								</div>
						  	</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
		
	
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
}

/**初始化表格*/
$(function(){
	//初始化查询条件
	$("#supplCode").openComponent_supplier();
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1","itemType2","itemType3");
	Global.LinkSelect.initOption("sqlCode","${ctx}/admin/brand/getBrandList",{itemType2:"${itemType2}"});
	Global.LinkSelect.setSelect({firstSelect:"itemType1",firstValue:"${postParam.itemType1}",secondSelect:"itemType2",secondValue:"${postParam.itemType2}"},true);
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/item/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-70,
		postData:$("#page_form").jsonForm(),
		multiselect:true,
		colModel : [
		  {name : 'code',index : 'code',width : 80,label:'材料编号',sortable : true,align : "left"},
	      {name : 'descr',index : 'descr',width : 160,label:'名称',sortable : true,align : "left"},
	      {name : 'uom',index : 'uom',width : 100,label:'单位',sortable : true,align : "left",hidden:true},
	      {name : 'uomdescr',index : 'u.Descr',width : 100,label:'单位',sortable : true,align : "left"},
	      {name : 'itemtype1',index : 'ItemType1',width : 100,label:'材料类型1',sortable : true,align : "left",hidden:true},
	      {name : 'itemtype1descr',index : 'i1.Descr',width : 100,label:'材料类型1',sortable : true,align : "left"},
	      {name : 'itemtype2',index : 'itemType2',width : 100,label:'材料类型2',sortable : true,align : "left",hidden:true},
	      {name : 'itemtype2descr',index : 'i2.Descr',width : 100,label:'材料类型2',sortable : true,align : "left"},
	      {name : 'itemtype3',index : 'itemType3',width : 100,label:'材料类型3',sortable : true,align : "left",hidden:true},	
	      {name : 'itemtype3descr',index : 'i3.Descr',width : 100,label:'材料类型3',sortable : true,align : "left"},
	      {name : 'sqlcode',index : 'SqlCode',width : 100,label:'品牌',sortable : true,align : "left"},
	      {name : 'color',index : 'Color',width : 100,label:'颜色',sortable : true,align : "left"},
	      {name : 'model',index : 'Model',width : 100,label:'型号',sortable : true,align : "left"},
	      {name : 'itemsize',index : 'ItemSize',width : 100,label:'规格',sortable : true,align : "left"},
	      {name : 'barcode',index : 'BarCode',width : 100,label:'条码',sortable : true,align : "left"},
	      {name : 'isfixprice',index : 'IsFixPrice',width : 100,label:'是否固定价',sortable : true,align : "left",hidden:true},
	      {name : 'Isfixpricedescr',index : 'p.Note',width : 100,label:'是否固定价',sortable : true,align : "left"},
	      {name : 'price',index : 'Price',width : 100,label:'单价',sortable : true,align : "left"}
       ]
	});
    
    //保存
    $("#saveBtn").on("click",function(){
    	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
    	if(ids.length==0){
    		Global.Dialog.infoDialog("请选择一条或多条记录进行新增操作！");	
    		return;
    	}
    	var selectRows = [];
		$.each(ids,function(k,id){
			var row = $("#dataTable").jqGrid('getRowData', id);
			selectRows.push(row);
		});
		Global.Dialog.returnData = selectRows;
		closeWin();
    });

});
</script>		
</body>
</html>


