<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
	<title>领用退回申请</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
    
<body>
<div class="body-box-form" >
	<div class="content-form">
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="${giftApp.m_umState}"/>
				<ul class="ul-form">
						<li class="form-validate">
							<label style="color:#777777"><span class="required">*</span>领用单号</label>
							<input type="text" id="no" name="no"  placeholder="保存自动生成" value="${giftApp.no }" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label style="color:#777777">领用人</label>
							<input type="text" id="useMan" name="useMan"  value="${giftApp.useMan }"/>
						</li>
						<li class="form-validate">
							<label style="color:#777777"><span class="required">*</span>状态</label>
							<house:xtdm id="status" dictCode="GIFTAPPSTATUS"  value="${giftApp.status}" disabled="true"></house:xtdm>                
						</li>
						<li class="form-validate">
							<label style="color:#777777"><span class="required">*</span>领用类型</label>
							<house:xtdm id="type" dictCode="GIFTAPPTYPE"  value="${giftApp.type }" readonly="readonly"></house:xtdm>
						</li>
						<li class="form-validate">
							<label style="color:#777777">领用人电话</label>
							 <input type="text" id="phone" name="phone"  value="${giftApp.phone }" readonly="readonly"/>
						</li>
						<li class="form-validate">
							<label style="color:#777777"><span class="required">*</span>开单日期</label>
							<input type="text"  id="date" name="date" class="i-date" value="<fmt:formatDate value='${giftApp.date}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true'/>
						</li>		
						<li class="form-validate">
							<label style="color:#777777"><span class="required">*</span>出库类型</label>
							<house:xtdm id="outType" dictCode="GIFTAPPOUTTYPE"  value="${giftApp.outType} " disabled="true"></house:xtdm>
						</li>	
						<li class="form-validate">
							<label style="color:#777777">活动</label>
							<input type="text" id="actNo" name="actNo"  value="${giftApp.actNo}" disabled="true"/>
						</li>	
						<li class="form-validate">
							<label style="color:#777777"><span class="required">*</span>开单人</label>
							<input type="text" id="appCzy" name="appCzy"  value="${giftApp.appCzy }"  readonly="readonly"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>退回类型</label>
							<house:xtdm id="sendType" dictCode="GIFTAPPSENDTYPE"  value="${giftApp.sendType}"></house:xtdm>
						</li>
						<li class="form-validate">
							<label style="color:#777777">客户编号</label>
							<input type="text" id="custCode" name="custCode"  value="${giftApp.custCode}" readonly="readonly" />
						</li>	
						<li class="form-validate">
							<label style="color:#777777">楼盘</label>
							<input type="text" id="address" name="address"  value="${giftApp.address}" readonly="readonly"/>
						</li>
						<li  class="form-validate" id="whCode_show" >
							<label >仓库</label>
							<input type="text"  id="whCode" name="whCode"  value="${giftApp.whCode }" readonly="readonly"/>
						</li class="form-validate">
						<li  class="form-validate" id="supplCode_show">
							<label >供应商</label>
							<input type="text" id="supplCode" name="supplCode"  value="${giftApp.supplCode}" readonly="readonly" />
						</li>
					
						<li class="form-validate">
							<label style="color:#777777">下定时间</label>
							<input type="text"  id="setDate" name="setDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.setDate }' pattern='yyyy-MM-dd'/>" readonly="readonly" disabled='true'/>
						</li>
		
						<li class="form-validate">
							<label style="color:#777777">签单时间</label>
							<input type="text"  id="signDate" name="signDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${giftApp.signDate}' pattern='yyyy-MM-dd'/>" readonly="readonly"disabled='true' />
						</li>
						<li class="form-validate">
							<label  >原领料单号</label>
							<input type="text" id="oldNo" name="oldNo" style="width:160px;" value="${giftApp.oldNo }" readonly="readonly"/>                  
						</li>
						
						<li class="form-validate">
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks"  >${whCheckOut.remarks}</textarea>
				      	</li>
					</ul>
			</form>
		</div>
		</div>
		<div class="btn-panel" >
   
      <div class="btn-group-sm"  >
    
      <button type="button" class="btn btn-system " id="addDetailExistsReturn">新增</button>

      <button type="button" class="btn btn-system "  id="delDetail">删除</button>

      <button type="button" class="btn btn-system " id="detailExcel">导出excel</button>
    
      </div>
	</div>
		<div id="content-list">
				<table id="dataTable_GiftAppDetail"></table>
				<div id="dataTable_GiftAppDetailPager"></div>
			</div>
	</div>

	</div>
</div>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>  
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_giftapp.js?v=${v}" type="text/javascript"></script>
    <script src="${resourceRoot}/pub/component_cmpactivity.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">


 	$("#appCzy").openComponent_employee({showValue:'${giftApp.appCzy}',showLabel:'${giftApp.appCzyDescr}',readonly:true });	
	if ( '${giftApp.m_umState}'=='A'){
		 $("#oldNo").openComponent_giftapp({showValue:'${giftApp.oldNo}',
		condition: {status:'SEND',outType:'1' } ,readonly:true}); 
	}else{
		 $("#oldNo").openComponent_giftapp({showValue:'${giftApp.oldNo}',readonly: true});
		$("#useMan").openComponent_employee({showValue:'${giftApp.useMan}',showLabel:'${giftApp.useManDescr}',readonly: true});
		$("#custCode").openComponent_customer({showValue: '${giftApp.custCode}' ,showLabel:'${giftApp.custDescr}',readonly: true});
		$("#whCode").openComponent_wareHouse({showValue:'${giftApp.whCode}',showLabel:'${giftApp.whDescr}',readonly:true});
        $("#supplCode").openComponent_supplier({showValue:'${giftApp.supplCode}',showLabel:'${giftApp.supplDescr}',readonly:true});
        $("#actNo").openComponent_cmpactivity({showValue:'${giftApp.actNo}',showLabel:'${giftApp.actDescr}',readonly: true}); 
 
    }
	if ($("#sendType").val()=='1'){
    	$('#supplCode_show').show();
   	    $('#whCode_show').hide();
	    $("#whCode").val('');
	}else{
	    $('#supplCode_show').hide();
   	    $('#whCode_show').show();
	    $("#supplCode").val('');
	}
	 $("addDetailExistsReturn").css("color","gray");
	 $("delDetail").css("color","gray");
	 $("#addDetailExistsReturn").attr("disabled",true);
	 $("#delDetail").attr("disabled",true);
 
   //输出到Excel
	$("#detailExcel").on("click",function(){
		Global.JqGrid.exportExcel("dataTable_GiftAppDetail","${ctx}/admin/giftApp/doGiftAppDetailExcel","礼品退回明细单导出","targetForm");
	});
	
 $(function(){
	var lastCellRowId;
	var gridOption = {
		url:"${ctx}/admin/giftApp/goJqGridGiftAppDetail?",
		postData:{no:$.trim($("#no").val()),oldNo:$.trim($("#oldNo").val())},
		height:$(document).height()-$("#content-list").offset().top-130,
		styleUI: 'Bootstrap',
		cellEdit:true,	
		colModel : [
		  	  {name : 'pk',index : 'pk',width : 70,label:'pk',sortable : true,align : "left",frozen: true,hidden:true},
		      {name : 'no',index : 'no',width : 70,label:'编号',sortable : true,align : "left",hidden:true},
		      {name : 'itemcode',index : 'itemcode',width : 70,label:'礼品编号',sortable : true,align : "left"},
		      {name : 'itemdescr',index : 'itemdescr',width : 120,label:'礼品名称',sortable : true,align : "left"},
		      {name : 'tokenpk',index : 'tokenpk',width : 60,label:'券号',sortable : true,align : "left",hidden:true},
		      {name : 'tokenno',index : 'tokenno',width : 60,label:'券号',sortable : true,align : "left"},	
		      {name : 'qty',index : 'qty',width : 60,label:'退回数量',editable:true,editrules: {number:true,required:true},sortable : true,sortable : true,align : "right"},		
		      {name : 'sendqtyed',index : 'sendqtyed',width : 60,label:'已发货数量',sortable : true,align : "right"},
		      {name : 'uomdescr',index : 'uomdescr',width : 60,label:'单位',sortable : true,align : "right"},	
		      {name : 'cost',index : 'cost',width : 60,label:'移动成本',sortable : true,align : "right"},	
		      {name : 'price',index : 'price',width : 60,label:'单价',sortable : true,align : "right"},	
		      {name : 'sendqty',index : 'sendqty',width : 50,label:'已领数量',sortable : true,align : "right",hidden:true},
		      {name : 'sumcost',index : 'sumcost',width : 50,label:'总金额',sortable : true,align : "right",sum: true},
		      {name : 'usediscamount',index : 'usediscamount',width : 80,label:'使用优惠额度',sortable : true,align : "left"},
		      // {name : 'usediscamountdescr',index : 'usediscamountdescr',width : 80,label:'使用优惠额度',sortable : true,align : "left"},
		      {name : 'remarks',index : 'remarks',width : 200,label:'备注',sortable : true,align : "left"},	     	 	
		          ],
		beforeSaveCell:function(rowId,name,val,iRow,iCol){
			lastCellRowId = rowId;
		},
		afterSaveCell:function(rowId,name,val,iRow,iCol){
	    	var rowData = $("#dataTable_GiftAppDetail").jqGrid("getRowData",rowId);
	    	rowData["sumcost"] = (rowData.qty*rowData.price).toFixed(2); 
	    	Global.JqGrid.updRowData("dataTable_GiftAppDetail",rowId,rowData);
	    },
	    beforeSelectRow:function(id){
         	   relocate(id,"dataTable_GiftAppDetail");
	       
        }
	};
	
	//初始化领料申请明细
	Global.JqGrid.initEditJqGrid("dataTable_GiftAppDetail",gridOption);
	

});
</script>
</body>
</html>
