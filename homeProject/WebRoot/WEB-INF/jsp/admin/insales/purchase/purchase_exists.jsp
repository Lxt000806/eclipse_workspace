<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>新增采购单结算</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/bootstrap/css/change.css" rel="stylesheet" />
	<style type="text/css">
	.panelBar{background: url("");}
	.query-form .ul-form li, .edit-form .ul-form li{width: 288px;}
	.table tr td.success{background-color: rgb(25, 142, 222);}
	</style>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" >
				<input type="hidden" id="unSelected" name="unSelected" value="${purchase.unSelected}">
				<table cellpadding="0" cellspacing="0" width="100%">
						<col  width="82" />
						<col  width="25.4%"/>
						<col  width="82"/>
						<col  width="25.3%"/>
						<col  width="82" />
						<col  width="25.4%"/>
					<tbody>
						<tr class="td-btn">	
							<td class="td-label">供应商编号</td>
							<td class="td-value">
							<input type="text" id="supplier" name="supplier" style="width:160px;" value="${purchase.supplier}" />
							</td>							
							<td class="td-label">楼盘</td>
							<td class="td-value">
							<input type="text" id="address" name="address" style="width:160px;" value="${purchase.address}" />
							</td>
							<td class="td-label">采购日期从</td>
							<td class="td-value">
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${purchase.dateFrom}' pattern='yyyy-MM-dd'/>" />
							</td>
						</tr>
						<tr class="td-btn">	
							<td class="td-label">至</td>
							<td class="td-value">
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${purchase.dateTo}' pattern='yyyy-MM-dd'/>" />
							</td>
							<td class="td-label">编号</td>
							<td class="td-value" colspan="1">
							<input type="text" id="documentNo" name="documentNo" style="width:160px;" value="${purchase.documentNo}" />
							</td>
							<td class="td-label">采购类型</td>
							<td class="td-value" colspan="1">
							<house:xtdm id="type" dictCode="PURCHTYPE" value="${purchase.type}"></house:xtdm>
							</td>
							<!-- add by zb on 20190529 -->
							<td class="td-label">供应商状态</td>
							<td class="td-value" colspan="1">
								<house:xtdmMulit id="splStatus" dictCode="PuSplStatus" unShowValue="3,4" ></house:xtdmMulit>
							</td>
							<td class="td-label">材料类型2</td>
							<td class="td-value" colspan="1">
								<house:DictMulitSelect id="itemType2" dictCode="" 
									sql="select rtrim(Code) Code,Descr from tItemType2 where Expired<>'T' order by DispSeq" 
									sqlLableKey="Descr" sqlValueKey="Code">
								</house:DictMulitSelect>
							</td>
						</tr class="td-btn">
						<tr>
						    <td class="td-label">客户类型</td>
                            <td class="td-value" colspan="1">
                                <house:custTypeMulit id="custType"></house:custTypeMulit>
                            </td>
						</tr>
						<tr>
							<td colspan="6" class="td-btn">
								<div class="sch_qk_con"> 
									<input onclick="searchExists()" class="i-btn-s" type="button"  value="查询"/>
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
			<!--panelBar-->
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
					<li>
                    	<a href="javascript:void(0)" class="a1" id="viewPurchase">
					       <span>查看</span>
						</a>	
                    </li>
					<li id="closeBut" onclick="saveAndExit()">
						<a href="javascript:void(0)" class="a2">
							<span>关闭</span>
						</a>
					</li>					
                    <li class="line"></li>
                </ul>
				<div class="clear_float"> </div>
			</div><!--panelBar end-->
			<div id="content-list">
				<table id= "dataTableExists"></table>
				<div id="dataTableExistsPager"></div>
			</div> 
		</div>
	</div>
	<input type="hidden" id="dataTableExists_selectRowAll" name="dataTableExists_selectRowAll" value="">
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var selectRowsAll = [];
var idsAll = [];
function searchExists(){
	var str = '${purchase.unSelected}';
	var sta = idsAll.join(',');
	if (str){
		if (sta){
			str = str + ',' + idsAll.join(',');
		}
	}else{
		if (sta){
			str = idsAll.join(',');
		}
	}
	$("#unSelected").val(str);
	var param=$("#page_form").jsonForm();
	if ($("#splStatus").val()==""){
		param.splStatus="0,1,2";
	}
	$("#dataTableExists").jqGrid("setGridParam",{url:"${ctx}/admin/purchase/goPurchaseExistsJqGrid",postData:param,page:1}).trigger("reloadGrid");
}
function saveAndExit(){
	if(selectRowsAll.length==0){
		closeWin(false);
		return;
	}
	Global.Dialog.returnData = selectRowsAll;
	closeWin();
}
/**初始化表格*/
$(function(){
		$("#page_form").setTable();
        //初始化表格
		Global.JqGrid.initEditJqGrid("dataTableExists",{
			url:"${ctx}/admin/purchase/goPurchaseExistsJqGrid",
			postData:{supplier:"${purchase.supplier}",checkOutNo:"${purchase.checkOutNo}",unSelected:"${purchase.unSelected}",splStatus:"0,1,2"},
			height:$(document).height()-$("#content-list").offset().top-80,
			multiselect:true,
			colModel : [
			  {name : 'checkoutno',index : 'checkoutno',width : 100,label:'checkoutno',sortable : true,align : "left",hidden:true},
			  {name : 'ischeckout',index : 'ischeckout',width : 100,label:'是否结帐',sortable : true,align : "left",hidden:true},
			  {name : 'no',index : 'no',width : 85,label:'采购单号',sortable : true,align : "left"},
			  {name : 'appno',index : 'appno',width : 85,label:'领料单号',sortable : true,align : "left"},
			  {name : 'custtype',index : 'custtype',width : 85,label:'客户类型编号',sortable : true,align : "left", hidden: true},
			  {name : 'custtypedescr',index : 'custtypedescr',width : 90,label:'客户类型',sortable : true,align : "left"},
			  {name : 'itemtype2',index : 'itemtype2',width : 85,label:'材料类型2',sortable : true,align : "left", hidden: true},
	  	      {name : 'itemtype2descr',index : 'itemtype2descr',width : 85,label:'材料类型2',sortable : true,align : "left"},
			  {name : 'documentno',index : 'documentno',width : 70,label:'编号',sortable : true,align : "left"},
			  {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left"},
		      {name : 'date',index : 'date',width : 75,label:'采购日期',sortable : true,align : "left",formatter:formatTime},
		      {name : 'othercost',index : 'othercost',width : 75,label:'其他费用',sortable : true,align : "left"},
		      {name : 'othercostadj',index : 'othercostadj',width : 90,label:'其他费用调整',sortable : true,align : "left"},
		      {name : 'splamount',index : 'splamount',width : 70,label:'对账金额',editable:true,editrules: {number:true},sortable : true,align : "left"},
		      {name : 'hjamount',index : 'hjamount',width : 70,label:'合计金额',sortable : true,align : "left"},
		      {name : 'amount',index : 'amount',width : 100,label:'材料金额',sortable : true,align : "left",hidden:true},
		      {name : 'projectamount',index : 'projectamount',width : 100,label:'项目经理结算价',sortable : true,align : "left",hidden:true},
		      {name : 'sumamount',index : 'sumamount',width : 100,label:'总金额',sortable : true,align : "left",hidden:true},
		      {name : 'firstamount',index : 'firstamount',width : 100,label:'已付定金',sortable : true,align : "left",hidden:true},
		      {name : 'secondamount',index : 'secondamount',width : 100,label:'secondamount',sortable : true,align : "left",hidden:true},
		      {name : 'remainamount',index : 'remainamount',width : 100,label:'应付余额',sortable : true,align : "left",hidden:true},
		      {name : 'remarks',index : 'remarks',width : 150,label:'备注',sortable : true,align : "left"},
			  {name : 'payremark',index : 'payremark',width : 150,label:'备注',sortable : true,align : "left",hidden:true},
		      {name : 'isservicedescr',index : 'isservicedescr',width : 90,label:'是否服务产品',sortable : true,align : "left"},
			  {name : 'issetitemdescr',index : 'issetitemdescr',width : 70,label:'套餐材料',sortable : true,align : "left",hidden:true},
		      {name : 'type',index : 'type',width : 80,label:'采购单类型',sortable : true,align : "left",hidden:true},
		      {name : 'typedescr',index : 'typedescr',width : 80,label:'采购单类型',sortable : true,align : "left"},
           	  {name : 'splstatus',index : 'splstatus',width : 80,label:'供应商状态',sortable : true,align : "left",hidden:true},
			  {name : 'splstatusdescr',index : 'splstatusdescr',width : 80,label:'供应商状态',sortable : true,align : "left"},
           	  {name : 'projectothercost',index : 'projectothercost',width : 80,label:'项目经理调整',sortable : true,align : "right",hidden:true}
            ],
            gridComplete:function(){
				var rowData = $("#dataTableExists").jqGrid('getRowData');
	             $.each(rowData,function(k,v){
					if(v.splstatus=='2'){
						$("#dataTableExists").jqGrid('setCell', k+1, 'splamount', '', 'not-editable-cell');
					}
	            })
			},
            beforeSaveCell:function(rowId,name,val,iRow,iCol){
    			lastCellRowId = rowId;
    		},
    		afterSaveCell:function(rowId,name,val,iRow,iCol){
    	    	var rowData = $("#dataTableExists").jqGrid("getRowData",rowId);
    	        rowData["hjamount"] = (parseFloat(rowData.splamount)+parseFloat(rowData.othercost)+parseFloat(rowData.othercostadj)).toFixed(2);
    	    	Global.JqGrid.updRowData("dataTableExists",rowId,rowData);
    	    },
			beforeSelectRow:function(id,e){
				var cellIndex = e.target.cellIndex;
				var checked = e.target.checked;
				if( checked == undefined){
					setTimeout(function(){
						if($("#dataTableExists tr[id="+id+"][class*='success']").length==1){
							if(!(cellIndex >=9 && cellIndex <= 11)){
								$("#dataTableExists tr[id="+id+"][class*='success'] td").removeClass("success");
		 						$("#dataTableExists tr[id="+id+"][class*='success']").removeClass("success").attr("aria-selected","false");
					   	    	$("#dataTableExists").setSelection(id,false);							
				   	    	}
						}else{
							$("#dataTableExists tr[id="+id+"]").addClass("success");
				   	    	$("#dataTableExists").setSelection(id,true);
						}
					},10);
				}else{
					if(!checked){					
						setTimeout(function(){
							$("#dataTableExists tr[id="+id+"] td").removeClass("success");
						},10);
					}
				}
			}
		});
		$("#supplier").openComponent_supplier();
		$("#supplier").setComponent_supplier({showValue:'${purchase.supplier}',showLabel:'${purchase.supplierDescr}',readonly: true});
        //保存
        $("#saveBtn").on("click",function(){
        	var ids = $("#dataTableExists").jqGrid('getGridParam', 'selarrrow');
        	if(ids.length==0){
        		Global.Dialog.infoDialog("请选择一条或多条记录进行新增操作！");
        		return;
        	}
    		var len = ids.length;//删除多行
    		for(var i = 0;i < len;i ++) {
    			var row = $("#dataTableExists").jqGrid('getRowData', ids[0]);
    			selectRowsAll.push(row);
    			idsAll.push(row.no);
    			$("#dataTableExists").jqGrid("delRowData", ids[0]);
    		}
    		$("#dataTableExists_selectRowAll").val(JSON.stringify(selectRowsAll));
        });
        
        //全选
        $("#selectallBtn").on("click",function(){
        	Global.JqGrid.jqGridSelectAll("dataTableExists",true);
        });
        
        //不选
        $("#unselectallBtn").on("click",function(){
        	Global.JqGrid.jqGridSelectAll("dataTableExists",false);
        });
        
      //查看采购单
        $("#viewPurchase").on("click",function(){
    		var row = selectDataTableRow('dataTableExists');
    		if(!row){
    			art.dialog({content: "请选择一条记录进行查看操作！",width: 200});
    			return false;
    		}

            //查看窗口
        	Global.Dialog.showDialog("viewPurchase",{
        	  title: "查看采购单",
        	  url: "${ctx}/admin/purchase/goView?id=" + row.no,
        	  height: 700,
        	  width: 1000,
        	  postData: row.no
        	});
        });
        $('#address').bind('keypress',function(event){
            if(event.keyCode=="13" && $.trim($('#address').val())!=''){
            	searchExists();
            }
        });
        $('#documentNo').bind('keypress',function(event){
            if(event.keyCode=="13" && $.trim($('#documentNo').val())!=''){
            	searchExists();
            }
        });
      
});
</script>
</body>
</html>


