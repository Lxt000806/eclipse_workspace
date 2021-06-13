<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加whCheckOut</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script type="text/javascript">
</script>
</head>
    
<body>
<div class="body-box-form" >
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
							<input type="text" id="No" name="No"  placeholder="保存自动生成" value="${giftApp.no }" readonly="readonly"/>                                             
						</li>
						<li class="form-validate">
							<label>领用人</label>
							<input type="text" id="useMan" name="useMan"  value="${giftApp.useMan }"/>
						</li>
						<li class="form-validate">
							<label style="color:#777777"><span class="required">*</span>状态</label>
							<house:xtdm id="status" dictCode="GIFTAPPSTATUS"  value="${giftApp.status}" disabled="true"></house:xtdm>                
						</li>
						<li class="form-validate">
							<label style="color:#777777"><span class="required">*</span>领用类型</label>
							<house:xtdm id="type" dictCode="GIFTAPPTYPE"  value="${giftApp.type }" disabled="true"></house:xtdm>
						</li>
						<li class="form-validate">
							<label>领用人电话</label>
							 <input type="text" id="phone" name="phone" maxlength="11" value="${giftApp.phone }"/>
						</li>
						<li class="form-validate">
							<label style="color:#777777"><span class="required">*</span>开单日期</label>
							<input type="text"  id="date" name="date" class="i-date" value="<fmt:formatDate value='${giftApp.date}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true'/>
						</li>		
						<li class="form-validate">
							<label style="color:#777777"><span class="required">*</span>出库类型</label>
							<house:xtdm id="outType" dictCode="GIFTAPPOUTTYPE"  value="${giftApp.outType} " disabled="true"></house:xtdm>
						</li>	
						<li class="form-validate" id="actNo_show" >
							<label>活动</label>
							<input type="text" id="actNo" name="actNo"  value="${giftApp.actNo}" disabled="true"/>
						</li>	
						<li class="form-validate">
							<label style="color:#777777"><span class="required">*</span>开单人</label>
							<input type="text" id="appCzy" name="appCzy"  value="${giftApp.appCzy }"  readonly="readonly"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>发货类型</label>
							<house:xtdm id="sendType" dictCode="GIFTAPPSENDTYPE"  value="${giftApp.sendType}" onchange="onChangeSendType()"></house:xtdm>
						</li>
						<li class="form-validate" id="custCode_show">
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode"  value="${giftApp.custCode}"  readonly="readonly" />
						</li>	
						<li class="form-validate">
							<label style="color:#777777">楼盘</label>
							<input type="text" id="address" name="address"  value="${giftApp.address}" readonly="readonly"/>
						</li>
						<li class="form-validate" id="whCode_show" >
							<label><span class="required">*</span>仓库</label>
							<input type="text"  id="whCode" name="whCode"  value="${giftApp.whCode }" readonly="readonly"/>
						</li>
						<li class="form-validate"  id="supplCode_show">
							<label><span class="required">*</span>供应商</label>
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
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" >${giftApp.remarks}</textarea>
					    </li>
					</ul>
			</form>
		</div>
	</div>
	
	<div  class="container-fluid" >  
	     <ul class="nav nav-tabs" >
	        <li id="tabGiftAppDetail" class="active"><a href="#tab_GiftAppDetail" data-toggle="tab">礼品领用明细</a></li>  
	        <li id="tabGiftStakeholder" class=""><a href="#tab_GiftStakeholder" data-toggle="tab">礼品领用干系人</a></li>
	        <li id="tabGiftIssue" class=""><a href="#tab_GiftIssue" data-toggle="tab">已领礼品明细</a></li>
	    </ul>  
	    <div class="tab-content">  
			<div id="tab_GiftAppDetail"  class="tab-pane fade in active"> 
		    	<jsp:include page="tab_GiftAppDetail.jsp"></jsp:include>
			</div>  
			<div id="tab_GiftStakeholder"  class="tab-pane fade "> 
	         	<jsp:include page="tab_GiftStakeholder.jsp"></jsp:include>
			</div>
			<div id="tab_GiftIssue"  class="tab-pane fade "> 
	         	<jsp:include page="tab_GiftIssue.jsp"></jsp:include>
			</div>
		</div>	
	</div>	
    <script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>  
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_cmpactivity.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
		
 $("#useMan").openComponent_employee({showValue:'${giftApp.useMan}',showLabel:'${giftApp.useManDescr}',readonly:true});
 $("#supplCode").openComponent_supplier({showValue:'${giftApp.supplCode}',showLabel:'${giftApp.supplDescr}',readonly:true});  
 $("#appCzy").openComponent_employee({showValue:'${giftApp.appCzy}',showLabel:'${giftApp.appCzyDescr}',readonly:true });  
 $("#whCode").openComponent_wareHouse({showLabel:"${giftApp.whDescr}",showValue:"${giftApp.whCode}",readonly:true});
			
 if ($.trim($("#sendType").val())=='1' ){
		$('#supplCode_show').show();
   	    $('#whCode_show').hide();
	    $("#whCode").val(' ');
 }else{
	    $('#supplCode_show').hide();
   	    $('#whCode_show').show();
	    $("#supplCode").val(' ');
}

if ( $.trim($("#type").val())=='3'||$.trim($("#type").val())=='4' ){
	$("#custCode").openComponent_customer({disabled:true});
	$("#actNo").openComponent_cmpactivity({disabled:true});
	$("#custCode_show").css("color","gray");
	$("#actNo_show").css("color","gray");
}else{
    $("#custCode").openComponent_customer({showLabel:"${giftApp.custDescr}",showValue:"${giftApp.custCode}",readonly:true});
	$("#actNo").openComponent_cmpactivity({ showValue:'${giftApp.actNo}',showLabel:'${giftApp.actDescr}',ondition:{type:'${giftApp.type}'},readonly:true});

}
	
//查看 礼品明细
$("#viewGiftAppDetail").on("click",function(){
	var ret = selectDataTableRow("dataTable_GiftAppDetail");
    if (ret) {
      var giftUseDisc=getGiftUseDisc(ret);
      Global.Dialog.showDialog("GiftAppDetailView",{
		  title:"查看礼品明细",
		  url:"${ctx}/admin/giftApp/goGiftAppDetailView",	
		  postData:{itemCode:ret.itemcode,
		            itemDescr:ret.itemdescr,
                    tokenPk:ret.tokenpk,
                    tokenNo:ret.tokenno,
                    qty:ret.qty,   
                    price:ret.price ,
                    cost:ret.cost, 
                    sendQty:ret.sendqty,
                    remarks:ret.remarks,
                	giftAppType:$.trim($("#type").val()),
            		custCode:$.trim($("#custCode").val()),
            		useDiscAmount:ret.usediscamount,
            		thisGiftUseDisc:giftUseDisc,
                    },		 			 
		  height:500,
		  width:800, 
		});
    } else {
    	art.dialog({    	
			content: "请选择一条记录"
		});
    }
});	

//删除
$("#delGiftAppDetail").on("click",function(){
	var id = $("#dataTable_GiftAppDetail").jqGrid("getGridParam","selrow");
	if(!id){
		art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
		return false;
	}
	Global.JqGrid.delRowData("dataTable_GiftAppDetail",id);	

});


//新增礼品干系人
$("#addGiftStakeholder").on("click",function(){		
	Global.Dialog.showDialog("GiftStakeholderAdd",{			
		  title:"新增礼品干系人",		
		  url:"${ctx}/admin/giftApp/goGiftStakeholderAdd",
		  postData:{},		 
		  height: 320,
		  width:500,
		  returnFun: function(data){       	
       		 if(data){    	 	
           		 $.each(data,function(k,v){
               	 var json = {                     	
                    role:v.role,
                    empcode:v.empCode,
                    department1:v.department1,   
                    department2:v.department2, 
                    roledescr:v.roleDescr,
                    empdescr:v. empDescr,
                    department1descr :v.department1Descr, 
                    department2descr :v.department2Descr,
                    expired: "F",
                    actionlog: "ADD",
                    lastupdate:v.lastUpdate,
					lastupdatedby:v.lastUpdatedBy		                                                                  
                } ;                     
                Global.JqGrid.addRowData("dataTable_GiftStakeholder",json);
            });             
        }
    }    
  }); 
});


  //编辑礼品干系人
	$("#updateGiftStakeholder").on("click",function(){	
	var ret = selectDataTableRow("dataTable_GiftStakeholder");
    if (ret) {
      Global.Dialog.showDialog("GiftStakeholderUpdate",{
		  title:"修改礼品干系人",
		  url:"${ctx}/admin/giftApp/goGiftStakeholderAdd",	
		  postData:{role:ret.role,
		            roleDescr:ret.roledescr,
                    empCode:ret.empcode,
                    empDescr:ret.empdescr,
                    department1:ret.department1,   
                    department2:ret.department2
          
                    },	   
		   height: 320,
		   width:500,
		   returnFun:function(data){
				var id = $("#dataTable_GiftStakeholder").jqGrid("getGridParam","selrow");
						Global.JqGrid.delRowData("dataTable_GiftStakeholder",id);
						
				  if(data){
					  $.each(data,function(k,v){
						  var json = {
								role:v.role,
			                    empcode:v.empCode,
			                    department1:v.department1,   
			                    department2:v.department2, 
			                    roledescr:v.roleDescr,
			                    empdescr:v. empDescr, 
			                    department1descr :v.department1Descr, 
                                department2descr :v.department2Descr,
			                    expired: "F",
			                    actionlog: "ADD",
			                    lastupdate:v.lastUpdate,
								lastupdatedby:v.lastUpdatedBy		                      		                      			                     		                     
						  };
						  Global.JqGrid.addRowData("dataTable_GiftStakeholder",json);
					  });
				  }
			  }   
		});
    } else {
    	art.dialog({    	
			content: "请选择一条记录"
		});
    }
});

	//查看礼品干系人
	$("#viewGiftStakeholder").on("click",function(){
	var ret = selectDataTableRow("dataTable_GiftStakeholder");		
    if (ret) {
	     Global.Dialog.showDialog("GiftStakeholderView",{
			  title:"查看礼品干系人",
			  url:"${ctx}/admin/giftApp/goGiftStakeholderView",		
			  postData:{role:ret.role,
			            roleDescr:ret.roledescr,
	                    empCode:ret.empcode,
	                    empDescr:ret.empdescr,
	                    department1:ret.department1,   
	                    department2:ret.department2,
	                    
			  },	                 			 		 
		     height: 320,
	 	     width:500,
			   
		 });
    } else {
    	art.dialog({    	
			content: "请选择一条记录"
		});
    }	
});

//删除材料明细
	$("#delGiftStakeholder").on("click",function(){
	var id = $("#dataTable_GiftStakeholder").jqGrid("getGridParam","selrow");
	if(!id){
		art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
		return false;
	}
	Global.JqGrid.delRowData("dataTable_GiftStakeholder",id);
});	


    //明细单输出到Excel
	$("#giftAppDetailExcel").on("click",function(){
		Global.JqGrid.exportExcel("dataTable_GiftAppDetail","${ctx}/admin/giftApp/doGiftAppDetailExcel","礼品领用明细单导出","targetForm");
	});
		
	//干系人输出到Excel
	$("#giftStakeholderExcel").on("click",function(){
		Global.JqGrid.exportExcel("dataTable_GiftStakeholder","${ctx}/admin/giftApp/doGiftStakeholderExcel","礼品干系人导出","targetForm");
	});
	
	$(function(){
		if($.trim("${viewStatus }")!="C"){
			$("#tabGiftIssue").hide();
		}
	});
		
	function getGiftUseDisc(ret){	
		var rowData=$('#dataTable_GiftAppDetail').jqGrid("getRowData");
		var giftUseDisc=0;
		$.each(rowData,function(k,v){
			if(v.usediscamount && parseFloat(v.usediscamount)!=0.0 ){
				giftUseDisc=giftUseDisc+parseFloat(v.usediscamount);	
			}
		});
		
		if(ret){
			if (ret.usediscamount && parseFloat(ret.usediscamount!=0.0)){
				giftUseDisc=giftUseDisc-parseFloat(ret.usediscamount);
			} 
		}
		return giftUseDisc;
	}
	
</script>
</body>
</html>
