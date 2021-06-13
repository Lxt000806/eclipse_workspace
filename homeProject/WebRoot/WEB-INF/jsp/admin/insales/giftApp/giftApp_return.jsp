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
				<button type="button" class="btn btn-system "   id="saveBtn">保存</button>
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
							<house:xtdm id="sendType" dictCode="GIFTAPPSENDTYPE"  value="${giftApp.sendType}" onchange="onChangeSendType()"></house:xtdm>
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

//校验函数
$(function() {
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			oldNo: {
				validators: {  
		            notEmpty: {  
		            message: '原领用单不能为空'  
		            }  
	            }  
			},
      	},
		submitButtons : 'input[type="submit"]'
	});	
 
});
 	$("#appCzy").openComponent_employee({showValue:'${giftApp.appCzy}',showLabel:'${giftApp.appCzyDescr}',readonly:true });	
	if ( '${giftApp.m_umState}'=='A'){
		 $("#oldNo").openComponent_giftapp({showValue:'${giftApp.oldNo}',callBack: getData,
		condition: {status:'SEND',outType:'1'}}); 
	}else{
	
	   $("#oldNo").openComponent_giftapp({showValue:'${giftApp.oldNo}',callBack: getData,
			condition: {status:'SEND',outType:'1'},readonly: true });
		$("#useMan").openComponent_employee({showValue:'${giftApp.useMan}',showLabel:'${giftApp.useManDescr}',readonly: true});
		$("#custCode").openComponent_customer({showValue: '${giftApp.custCode}' ,showLabel:'${giftApp.custDescr}',readonly: true});
		$("#whCode").openComponent_wareHouse({showValue:'${giftApp.whCode}',showLabel:'${giftApp.whDescr}',condition:{czybh:'${giftApp.appCzy}'},callBack:function(){validateRefresh('openComponent_wareHouse_whCode')}});
        $("#supplCode").openComponent_supplier({showValue:'${giftApp.supplCode}',showLabel:'${giftApp.supplDescr}',condition:{itemType1:'LP',readonly:"1",ReadAll:"1"},callBack:function(){validateRefresh('openComponent_supplier_supplCode')}});
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
	
	
  if( ('${giftApp.m_umState}'=='C')||('${giftApp.m_umState}'=='D')){
	 $("addDetailExistsReturn").css("color","gray");
	 $("delDetail").css("color","gray");
	 $("#addDetailExistsReturn").attr("disabled",true);
	 $("#delDetail").attr("disabled",true);
  } 
 
 function getData(data){
		if (!data) return;
		$("#useMan").openComponent_employee({showValue:data.useman,showLabel:data.usemandescr,readonly: true});
		$("#whCode").openComponent_wareHouse({showValue:data.whcode,showLabel:data.whdescr,callBack:function(){validateRefresh('openComponent_wareHouse_whCode')}});
        $("#supplCode").openComponent_supplier({showValue:data.supplcode,showLabel:data.suppldescr,callBack:function(){validateRefresh('openComponent_supplier_supplCode')}});
		$("#openComponent_cmpactivity_actNo").val('');
		$("#actNo").openComponent_cmpactivity({showValue:data.actno,showLabel:data.actdescr,readonly: true});
	   	$("#openComponent_customer_custCode").val('');
	    $("#custCode").openComponent_customer({showValue:data.custcode,showLabel:data.custdescr,readonly: true});
	
	
		if (data.supplcode){
			$('#supplCode_show').show();
	   	    $('#whCode_show').hide();
		    $("#whCode").val(' ');
		}else{
			$('#supplCode_show').hide();
	   	    $('#whCode_show').show();
		    $("#supplCode").val(' ');
		}
		$("#type").remove("disabled",true);
		$("#type").val(data.type);
		$("#type").attr("disabled",true);
		$("#setDate").val(data.setdate);
	    $("#signDate").val(data.signdate);
		$("#address").val(data.address);
		$("#phone").val(data.phone);
		$("#actNo").val(data.actno);
		$("#sendType").val(data.sendtype);

	}
	$("#dataForm").bootstrapValidator("addField", "openComponent_wareHouse_whCode", {
        validators: {         
            remote: {
	            message: '',
	           url: '${ctx}/admin/wareHouse/getWareHouse',
	            data: getValidateVal,  
	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }
        }
    });
     $("#dataForm").bootstrapValidator("addField", "openComponent_supplier_supplCode", {  
        validators: {  
            remote: {
	            message: '',
	            url: '${ctx}/admin/supplier/getSupplier',
	            data: getValidateVal,  
	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }
        }  
    });

  function onChangeSendType(){	
	   	if ($("#sendType").val()=='1'){
	    	$('#supplCode_show').show();
	   	    $('#whCode_show').hide();
		    $("#whCode").val(' ');
		}else{
		    $('#supplCode_show').hide();
	   	    $('#whCode_show').show();
		    $("#supplCode").val(' ');
		}
   	
   }
    //保存操作    	
$("#saveBtn").on("click",function(){
	if(!$("#dataForm").valid()) {return false;}//表单校验
	var ids = $("#dataTable_GiftAppDetail").getDataIDs();
	if(!ids || ids.length == 0){
		art.dialog({content: "明细表中无数据！",width: 200});
		return false;
	}
	
	if ($.trim($("#type").val())==''){
		art.dialog({content: "领用类型不能为空",width: 200});
		return false;
	}
	if ($.trim($("#type").val())=="2" && $.trim($("#custCode").val())==''){
		art.dialog({content: "客户编号不能为空",width: 200});
		return false;
	}
	if ( (  $.trim($("#type").val())=='3' ||$.trim($("#type").val())=='4') &&$.trim($("#useMan").val())==""){
		art.dialog({content: "领用人不能为空",width: 200});
		return false;
	}
	if ($.trim($("#sendType").val())==''){
		art.dialog({content: "发货类型不能为空",width: 200});
		return false;
	}
	if ($.trim($("#useMan").val())!="" && $.trim($("#phone").val())==""){
		art.dialog({content: "领用人电话不能为空",width: 200});
		return false;
	}
	if ($.trim($("#sendType").val())=='1' && $.trim($("#supplCode").val())==''){
		art.dialog({content: "供应商不能为空",width: 200});
		return false;
	}
		if ($.trim($("#sendType").val())=='2' && $.trim($("#whCode").val())==''){
		art.dialog({content: "仓库不能为空",width: 200});
		return false;
	}
		  
	var fQty = Global.JqGrid.allToJson("dataTable_GiftAppDetail","qty");
		arry = fQty.fieldJson.split(",");		
		var x = 1.00;
		for(var i = 0;i < arry.length; i++){
		  	 x = x *arry[i]; 	 
			
		}
		if(x<=0){
			art.dialog({
          			content: "退回数量应须都大于0，请重新检查!"
          		});
          		return;
		}  

	var param = Global.JqGrid.allToJson("dataTable_GiftAppDetail");
	
		Global.Form.submit("dataForm","${ctx}/admin/giftApp/doReturn",param,function(ret){
			if(ret.rs==true){
				art.dialog({
					content: ret.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
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
	});

 //新增礼品领用明细
	$("#addDetailExistsReturn").on("click",function(){
		var oldNo = $.trim($("#oldNo").val());
		if(oldNo==''){
			art.dialog({content: "请选择原领用编号",width: 200});
			return false;
		}
		var detailJson = Global.JqGrid.allToJson("dataTable_GiftAppDetail","itemcode");
		Global.Dialog.showDialog("addDetailExistsReturn",{
			  title:"礼品领用明细-新增",
			  url:"${ctx}/admin/giftApp/goGiftAppDetailExistsReturn",
			  height: 580,
			  width:900,
			  postData:{no: oldNo,unSelected: detailJson["fieldJson"]},
			  returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
						  var json = {
								itemcode:v.itemcode,
								itemdescr:v.itemdescr,
			                    tokenpk:v.tokenpk,
			                    tokenno:v.tokenno,
			                    qty:v.sendqtyed, 
			                    cost:v.cost, 
			                    sendqtyed:v.sendqtyed,  
			                    price:v.price,
			                    sendqty:v.sendqty,
			                    sumcost:v.sendqty*v.cost,
			                    remarks:v.remarks,
			                    uomdescr:v.uomdescr,
			                	usediscamount:v.usediscamount,
								// usediscamountdescr:v.usediscamountdescr,
			                   
						  };
						  Global.JqGrid.addRowData("dataTable_GiftAppDetail",json);
					  });
					   $("#oldNo").setComponent_giftapp({readonly: true})
					 
				  }
			  }
			});
	});

    //删除
	$("#delDetail").on("click",function(){
		var id = $("#dataTable_GiftAppDetail").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		Global.JqGrid.delRowData("dataTable_GiftAppDetail",id);
		//设置原领料单号可以修改
		var ids = $("#dataTable_GiftAppDetail").getDataIDs();
		if(!ids || ids.length == 0){
			$("#oldNo").setComponent_giftapp({showValue:$("#oldNo").val(),condition: {status:'SEND',outType:'1'},readonly: false});
		}
	});
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
		rowNum:100000,  
   		pager :'1',
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
		      {name : 'cost',index : 'cost',width : 60,label:'成本',sortable : true,align : "right"},	
		      {name : 'price',index : 'price',width : 60,label:'单价',sortable : true,align : "right",hidden:true},	
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
