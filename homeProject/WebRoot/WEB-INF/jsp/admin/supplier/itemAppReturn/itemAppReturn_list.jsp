<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>退货列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
}
function del(){
	var url = "${ctx}/admin/supplierItemAppReturn/doDelete";
	beforeDel(url);
}
function doExcel(){
	var url = "${ctx}/admin/supplierItemAppReturn/doExcel";
	doExcelAll(url);
}
function doPrint(){
	Global.Print.showPrint("reportSql.jasper",
			{sino:"",from:"1"});
	
}
/**初始化表格*/
$(function(){
	    $("#supplCode").openComponent_supplier();
		//$("#custCode").openComponent_customer();
		$("#appCzy").openComponent_employee();

		//初始化材料类型1，2，3三级联动
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1","itemType2");

        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/supplierItemAppReturn/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
		    styleUI: 'Bootstrap',
			colModel : [
			  {name : 'no',index : 'no',width : 100,label:'退货单号',sortable : true,align : "left"},
			  {name : 'address',index : 'address',width : 200,label:'楼盘',sortable : true,align : "left"},
		      {name : 'custcode',index : 'custcode',width : 100,label:'客户编号',sortable : true,align : "left",hidden:true},
		      {name : 'custdescr',index : 'custdescr',width : 100,label:'客户名称',sortable : true,align : "left"},
		      {name : 'status',index : 'status',width : 100,label:'退货单状态',sortable : true,align : "left",hidden:true},
		      {name : 'statusdescr',index : 'statusdescr',width : 100,label:'退货单状态',sortable : true,align : "left"},
		      {name : 'amount',index : 'amount',width : 100,label:'退货材料总价',sortable : true,align : "left",sum:true,hidden:true},
		      {name : 'othercost',index : 'othercost',width : 100,label:'其他费用',sortable : true,align : "right",sum:true},
		      {name : 'remarks',index : 'remarks',width : 100,label:'备注',sortable : true,align : "left"},
		      {name : 'appczy',index : 'appczy',width : 100,label:'申请人员',sortable : true,align : "left",hidden:true},
		      {name : 'appczydescr',index : 'appczydescr',width : 100,label:'申请人员',sortable : true,align : "left"},
		      {name : 'date',index : 'date',width : 150,label:'申请日期',sortable : true,align : "left",formatter:formatTime},
		      {name : 'confirmczy',index : 'confirmczy',width : 100,label:'审批人员',sortable : true,align : "left",hidden:true},
		      {name : 'confirmczydescr',index : 'confirmczydescr',width : 100,label:'审批人员',sortable : true,align : "left"},
		      {name : 'confirmdate',index : 'confirmdate',width : 150,label:'审批日期',sortable : true,align : "left",formatter:formatTime},
		      {name : 'type',index : 'type',width : 100,label:'领料类型',sortable : true,align : "left",hidden:true},
		      {name : 'typedescr',index : 'typedescr',width : 100,label:'领料类型',sortable : true,align : "left",count:true},
		      {name : 'itemtype1',index : 'itemtype1',width : 100,label:'材料类型1',sortable : true,align : "left",hidden:true},
		      {name : 'itemtype1descr',index : 'itemtype1descr',width : 100,label:'材料类型1',sortable : true,align : "left"},
		      {name : 'itemtype2',index : 'itemtype2',width : 100,label:'材料类型2',sortable : true,align : "left",hidden:true},
		      {name : 'itemtype2descr',index : 'itemtype2descr',width : 100,label:'材料类型2',sortable : true,align : "left"},
		      {name : 'issetitem',index : 'issetitem',width : 100,label:'是否套餐材料',sortable : true,align : "left",hidden:true},
		      {name : 'issetitemdescr',index : 'issetitemdescr',width : 100,label:'是否套餐材料',sortable : true,align : "left"},
		      {name : 'documentno',index : 'documentno',width : 100,label:'档案编号',sortable : true,align : "left"},
		      
		      {name : 'sendtype',index : 'sendtype',width : 100,label:'发货类型',sortable : true,align : "left",hidden:true},
		      {name : 'sendtypedescr',index : 'sendtypedescr',width : 100,label:'发货类型',sortable : true,align : "left",hidden:true},
		      {name : 'delivtype',index : 'delivtype',width : 100,label:'配送方式',sortable : true,align : "left",hidden:true},
		      {name : 'delivtypedescr',index : 'delivtypedescr',width : 100,label:'配送方式',sortable : true,align : "left",hidden:true},
		      {name : 'projectamount',index : 'projectamount',width : 100,label:'项目经理结算总价',sortable : true,align : "left",sum:true,hidden:true},
		      {name : 'sendczy',index : 'sendczy',width : 100,label:'发货人员',sortable : true,align : "left",hidden:true},
		      {name : 'sendczydescr',index : 'sendczydescr',width : 100,label:'发货人员',sortable : true,align : "left",hidden:true},
		      {name : 'senddate',index : 'senddate',width : 150,label:'发货日期',sortable : true,align : "left",formatter:formatTime,hidden:true},
		      {name : 'supplcode',index : 'supplcode',width : 100,label:'供应商',sortable : true,align : "left",hidden:true},
		      {name : 'supplcodedescr',index : 'supplcodedescr',width : 100,label:'供应商',sortable : true,align : "left",hidden:true},
		      {name: 'pusplstatusdescr', index: 'pusplstatusdescr', width: 100, label: '供应商结算状态', sortable: true, align: "left"},
		      {name : 'puno',index : 'puno',width : 100,label:'采购单号',sortable : true,align : "left",hidden:true},
		      {name : 'whcode',index : 'whcode',width : 100,label:'仓库编号',sortable : true,align : "left",hidden:true},
		      {name : 'whcodedescr',index : 'whcodedescr',width : 100,label:'仓库名称',sortable : true,align : "left",hidden:true},
		      {name : 'projectman',index : 'projectman',width : 100,label:'项目经理',sortable : true,align : "left",hidden:true},
		      {name : 'phone',index : 'phone',width : 100,label:'电话',sortable : true,align : "left",hidden:true},
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后更新日期',sortable : true,align : "left",formatter:formatTime,hidden:true},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'最后更新人员',sortable : true,align : "left",hidden:true},
		      {name : 'expired',index : 'expired',width : 100,label:'是否过期',sortable : true,align : "left",hidden:true},
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'操作',sortable : true,align : "left",hidden:true}
            ]
		});
        
        
        //退货申请
        $("#addItemApp").on("click",function(){
            //新增窗口
        	Global.Dialog.showDialog("itemAppReturnAdd",{
        	  title:"退货申请",
        	  url:"${ctx}/admin/supplierItemAppReturn/goSave",
        	  height: 700,
        	  width:1000,
        	  resizable:false, 
        	  returnFun:goto_query
        	});
        });
        //退货修改
        $("#updateItemApp").on("click",function(){
        	var ret = selectDataTableRow();
            if (ret) {
            	if ($.trim(ret.status)!='OPEN' && $.trim(ret.status)!='CONRETURN'){
            		art.dialog({
            			content: "只有已申请或审核退回的退货单才能修改"
            		});
            		return;
            	}
            	Global.Dialog.showDialog("itemAppReturnUpdate",{
              	  title:"退货修改",
              	  url:"${ctx}/admin/supplierItemAppReturn/goUpdate?id="+ret.no,
              	  height: 700,
              	  width:1010,
              	  resizable:false, 
              	  returnFun:goto_query
              	});
            } else {
            	art.dialog({
        			content: "请选择一条记录"
        		});
            }
        });
      //退货查看
        $("#viewItemApp").on("click",function(){
        	var ret = selectDataTableRow();
            if (ret) {
            	Global.Dialog.showDialog("itemAppReturnView",{
              	  title:"退货查看",
              	  url:"${ctx}/admin/supplierItemAppReturn/goDetail?id="+ret.no,
              	  height: 700,
              	  resizable:false, 
              	  width:1010
              	});
            } else {
            	art.dialog({
        			content: "请选择一条记录"
        		});
            }
        });
      //打印
        $("#printItemApp").on("click",function(){
			var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录进行打印操作！",width: 200});
    			return false;
    		} 
        	
        	var reportName = "itemAppReturn_main.jasper";
        	Global.Print.showPrint(reportName, {
				No: "'" + $.trim(row.no) + "'",
				LogoPath : "<%=basePath%>jasperlogo/",
				SUBREPORT_DIR: "<%=jasperPath%>" 
			});
        });
        //结算申请
        $("#checkApp").on("click",function(){
        	var row = selectDataTableRow();
    		if(!row){
    			art.dialog({content: "请选择一条记录进行结算申请操作！"});
    			return false;
    		}
    		if(row.status.trim()!="RETURN"){
    			art.dialog({content: "请选择已退回的退货单！"});
    			return false;
    		}
        	Global.Dialog.showDialog("checkApp",{
          	  title: "结算申请",
          	  url: "${ctx}/admin/supplierItemAppReturn/goCheckApp",
          	  postData:{id:row.no},
          	  height: 700,
          	  width: 1000,
          	  returnFun: goto_query
          	});
        });
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired" name="expired" value="${itemApp.expired}" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
							<li>
							<label>退货单号</label>
						
							<input type="text" id="no" name="no"   value="${itemApp.no}" />
							</li>
							<li>
							<label>申请日期</label>
							
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.date}' pattern='yyyy-MM-dd'/>" />
							</li>						
							<li>
							<label>至</label>
							
							<input type="text" id="dateTo" name="dateTo" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemApp.date}' pattern='yyyy-MM-dd'/>" />
							</li>
						<!--  
						<tr class="td-btn" style="display: none">
							<li><label>供应商编号</label>
							<td class="td-value">
							<input type="text" id="supplCode" name="supplCode" style="width:160px;"  value="${itemApp.supplCode}" />
							</label>
							<li><label>领料类型</label>
							<td class="td-value">
							<house:xtdm id="type" dictCode="ITEMAPPTYPE"></house:xtdm>
							</label>
							<li><label>配送方式</label>
							<td class="td-value">
							<house:xtdm id="delivType" dictCode="ITEMAPPSENDTYPE" style="width:166px;"/>
							</label>														
						</tr>
						<tr class="td-btn" style="display: none">
							<li><label>材料类型1</label>
							<td class="td-value">
							<select id="itemType1" name="itemType1" style="width:166px;"></select>
							</label>
							<li><label>材料类型2</label>
							<td class="td-value">
							<select id="itemType2" name="itemType2" style="width:166px;"></select>
							</label>	
							<li><label>申请人员</label>
							<td class="td-value">
							<input type="text" id="appCzy" name="appCzy" style="width:160px;"  value="${itemApp.confirmDate}" />
							</label>																			
						</tr>
						-->
							<li>
							<label>楼盘地址</label>
							
							<input type="text" id="custAddress" name="custAddress"   value="${itemApp.custAddress}" />
							</li>
							<li>
							<label>退货单状态</label>
							
							<house:xtdmMulit id="status" dictCode="ITEMAPPSTATUS" selectedValue="${itemApp.status}"></house:xtdmMulit>
							</li>																			
							<li><label>供应商结算状态</label> <house:xtdm id="puSplStatus"
									dictCode="PuSplStatus" value="${itemApp.puSplStatus}" ></house:xtdm>
							</li>
						
					<li class="search-group" >
				
						<input type="checkbox"  id="expired" name="expired"  value="${itemApp.expired=='T'?'T':'F'}" onclick="changeExpired(this)" ${itemApp.expired=='T'?'':'checked' }/><p>隐藏过期</p>
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
					</li>
						</ul>
			</form>
		</div>
	 <div class="btn-panel" >
   
      <div class="btn-group-sm"  >
  	<house:authorize authCode="SUPPLIER_ITEMAPPRETURN_APPLY">
      <button id="addItemApp" type="button" class="btn btn-system " >退货申请</button>
      </house:authorize>
   <house:authorize authCode="SUPPLIER_ITEMAPPRETURN_UPDATE">
      <button id="updateItemApp" type="button" class="btn btn-system " >退货修改</button>
     </house:authorize>
    
      <button id="viewItemApp" type="button" class="btn btn-system " >退货查看</button>
    
     
      <button id="itemAppPrint" onclick="doPrint()" style="display: none;" type="button" class="btn btn-system " >打印</button>
     
      <button id="printItemApp" type="button" class="btn btn-system "  >打印</button>
      <button  id="itemAppExcel" style="display: none;"  type="button" class="btn btn-system "  >输出至excel</button>
        <house:authorize authCode="SUPPLIER_ITEMAPPRETURN_CHECKAPP">
      <button id="checkApp" type="button" class="btn btn-system " >结算申请</button>
     </house:authorize>
      </div>
       
  
   
	</div>
       <div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
</div>
</body>
</html>


