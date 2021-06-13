<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加giftApp</title>
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
				<button type="button" class="btn btn-system "   id="saveBtn">保存</button>
				<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search" target="targetFrame">
				<input type="hidden" name="m_umState" id="m_umState" value="${giftApp.m_umState}"/>
				<house:token></house:token>
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
							<input type="text" id="custCode" name="custCode"  value="${giftApp.custCode}"  readonly="true"  />
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
	    </ul>  
	    <div class="tab-content">  
			<div id="tab_GiftAppDetail"  class="tab-pane fade in active"> 
		    	<jsp:include page="tab_GiftAppDetail.jsp"></jsp:include>
			</div>  
			<div id="tab_GiftStakeholder"  class="tab-pane fade "> 
	         	<jsp:include page="tab_GiftStakeholder.jsp"></jsp:include>
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
		
	 $("#useMan").openComponent_employee({showValue:'${giftApp.useMan}',showLabel:'${giftApp.useManDescr}',
	 	callBack: function(data){$("#phone").val(data.phone),validateRefresh('openComponent_employee_useMan')}});
	 $("#supplCode").openComponent_supplier({showValue:'${giftApp.supplCode}',showLabel:'${giftApp.supplDescr}',condition:{itemType1:'LP',readonly:"1",ReadAll:"1"},
	 	 callBack:function(){validateRefresh('openComponent_supplier_supplCode')}});  
	 $("#appCzy").openComponent_employee({showValue:'${giftApp.appCzy}',showLabel:'${giftApp.appCzyDescr}',readonly:true });  
	 $("#whCode").openComponent_wareHouse({showLabel:"${giftApp.whDescr}",showValue:"${giftApp.whCode}",condition:{czybh:'${giftApp.appCzy}'},
	 	callBack:function(){validateRefresh('openComponent_wareHouse_whCode')}});
				
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
	    $("#custCode").openComponent_customer({showLabel:"${giftApp.custDescr}",showValue:"${giftApp.custCode}", callBack: getData});
		$("#actNo").openComponent_cmpactivity({showValue:'${giftApp.actNo}',showLabel:'${giftApp.actDescr}',condition:{type:'${giftApp.type}'},
	 	callBack:function(){validateRefresh('openComponent_cmpactivity_actNo')}});
	}
	function getData(data){
		if (!data) return;
		$("#address").val(data.address),
		$("#setDate").val(data.setdate),
		$("#signDate").val(data.signdate),
		validateRefresh('openComponent_customer_custCode'); 
		var postData = $("#page_form").jsonForm();
		postData.custCode=data.code,   
		postData.type = $("#type").val();     
		$("#dataTable_GiftStakeholder").jqGrid("setGridParam",{postData:postData,
			page:1,url: "${ctx}/admin/giftApp/goJqGridStakeholderByCustCode"}).trigger("reloadGrid"); //根据客户号获取干系人

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
    $("#dataForm").bootstrapValidator("addField", "openComponent_employee_useMan", {
        validators: {
            remote: {
	            message: '',
	            url: '${ctx}/admin/employee/getEmployee',
	            data: getValidateVal,  
	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }
        }
    });
    $("#dataForm").bootstrapValidator("addField", "openComponent_cmpactivity_actNo", {
        validators: {
            remote: {
	            message: '',
	            url: '${ctx}/admin/cmpActivity/getCmpActivity',
	            data: getValidateVal,  
	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }
        }
    });
    $("#dataForm").bootstrapValidator("addField", "openComponent_customer_custCode", {  
        validators: {  
            remote: {
	            message: '',
	            url: '${ctx}/admin/customer/getCustomer',
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
	//校验函数
	 $(function() {
	 	$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {
				validating : 'glyphicon glyphicon-refresh'
			},
			fields: {  
			
				status: {
					validators: {  
			            notEmpty: {  
			            message: '状态不能为空'  
			            }  
		            }  
				},
				phone: {
		        validators: { 
		            digits: { 
		            	message: '手机号码1只能输入数字'  
		            },
		            stringLength:{
	               	 	min: 0,
	          			max: 11,
	               		message:'手机号码长度必须在0-11之间' 
	               	}
		        }
	      	},
			},
			submitButtons : 'input[type="submit"]'
		});	
		
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
		$("#dataForm").bootstrapValidator("validate");
		if(!$("#dataForm").valid()) {return false;}//表单校验
		var ids = $("#dataTable_GiftAppDetail").getDataIDs();
		var ids2 = $("#dataTable_GiftStakeholder").getDataIDs();
		if((!ids || ids.length == 0)){
			art.dialog({content: "礼品明细表中无数据！",width: 200});
			return false;
		}
		if((!ids2 || ids2.length == 0)&&(( $.trim($("#type").val())=='1'||$.trim($("#type").val())=='2' ))){
			art.dialog({content: "干系人表中无数据！",width: 200});
			return false;
		}  
		if ($.trim($("#type").val())!="1" ){
			var countRole = Global.JqGrid.allToJson("dataTable_GiftStakeholder","role");
			arry = countRole.fieldJson.split(",");		
			var x = 0;
			for(var i = 0;i < arry.length; i++){
			   if (arry[i]=='00'){
			  	 x = x +1; 	
			   }
				
			}
			if(x>=2){
				art.dialog({
           			content: "设计师只能有一个!"
           		});
           		return;
			}
	
	    } 
	    if(ids2){                                                             
	     	var fShareper = Global.JqGrid.allToJson("dataTable_GiftStakeholder","shareper");
			arry = fShareper.fieldJson.split(",");	
			var x = 0.00;
			for(var i = 0;i < arry.length; i++){
			  	 x = x + parseFloat(arry[i]); 	 
				
			}
			if(x>=1.01||x<=0.991){
				art.dialog({
	          			content: "总分摊比例不等于1!"
	          		});
	          		return;
			} 
		} 

		if ($.trim($("#type").val())==''){
			art.dialog({content: "领用类型不能为空",width: 200});
			return false;
		}
		if ($.trim($("#type").val())=="2" && $.trim($("#custCode").val())==''){
			art.dialog({content: "客户编号不能为空",width: 200});
			return false;
		}
		if ($.trim($("#type").val())=="2" && $.trim($("#actNo").val())==''){
			art.dialog({content: "活动编号不能为空",width: 200});
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
		var giftAppDetailJson = Global.JqGrid.allToJson("dataTable_GiftAppDetail");
		var giftStakeholderJson = Global.JqGrid.allToJson("dataTable_GiftStakeholder");
		var param = {"giftAppDetailJson": JSON.stringify(giftAppDetailJson),"giftStakeholderJson":JSON.stringify(giftStakeholderJson)};
		Global.Form.submit("dataForm", "${ctx}/admin/giftApp/doSave", param, function(ret){
			if(ret.rs==true){
				$("#_form_token_uniq_id").val(ret.token.token);
				art.dialog({
					content: ret.msg,
					time: 1000,
					beforeunload: function () {
	    				if(($.trim($("#m_umState").val())=="A")&&( $.trim($("#type").val())=='1'||$.trim($("#type").val())=='2' )){
	    					$("#dataTable_GiftAppDetail").jqGrid("clearGridData");
	    					$("#dataTable_GiftStakeholder").jqGrid("clearGridData");
							$("#useMan").val("");
							$("#phone").val("");
							$("#address").val("");
							$("#signDate").val("");
							$("#setDate").val("");
							/* $("#custCode").setComponent_customer({readonly: false}), */
				           	$("#openComponent_customer_custCode").removeAttr("readonly");
							$("#openComponent_customer_custCode").next().attr("data-disabled", false).css({
											"color":""
							});
				            $("#actNo").setComponent_cmpactivity({readonly: false}); 
			            	$("#openComponent_employee_useMan").val("");
			            	$("#openComponent_customer_custCode").val("");	 
				            $("#custCode_show").css("color","black");
							$("#actNo_show").css("color","black"); 	
						 }else{
						 	$("#dataForm").bootstrapValidator("resetForm");
						 	  closeWin(); 	
						 }  	
				    },
				    
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
	$("#addGiftAppDetail").on("click",function(){
		if ($.trim($("#type").val())=="2"){
		    if($.trim($("#actNo").val())=="" ){
				art.dialog({content: "活动不能为空",width: 200});
					return false;
			}
			if($.trim($("#custCode").val())=="" ){
				art.dialog({content: "客户不能为空",width: 200});
					return false;
			}	
		}
		var detailJson = Global.JqGrid.allToJson("dataTable_GiftAppDetail","itemcode");
		var giftUseDisc=getGiftUseDisc();
		Global.Dialog.showDialog("GiftAppDetailAdd",{			
			  title:"添加礼品领用明细",		
			  url:"${ctx}/admin/giftApp/goGiftAppDetailAdd",
			  postData:{actNo:$.trim($("#actNo").val()),giftAppType:$.trim($("#type").val()),
				  custCode:$.trim($("#custCode").val()),thisGiftUseDisc:giftUseDisc},		 
			  height: 500,
			  width:800,
			  returnFun: function(data){       	
	       		 if(data){    	 	
	           		 $.each(data,function(k,v){
	               	 var json = {                     	
	                    itemcode:v.itemCode,
	                    itemdescr :v.itemDescr,
	                    tokenpk:v.tokenPk,
	                    tokenno:v.tokenNo,
	                    qty:v.qty,   
	                    price:v.price,
	                    cost:v.cost,
	                    sendqty:v.sendQty,
	                    sumcost:v.qty*v.cost,
	                    remarks:v.remarks,
	                    uomdescr:v.uom,
	                    expired: "F",
	                    actionlog: "ADD",
	                    lastupdate:v.lastUpdate,
						lastupdatedby:v.lastUpdatedBy,
						usediscamount:v.useDiscAmount,
	                } ;                     
	                Global.JqGrid.addRowData("dataTable_GiftAppDetail",json);
	            });
	            // $("#custCode").setComponent_customer({readonly: true}),
	             $("#openComponent_customer_custCode").attr("readonly", true);
	             $("#openComponent_customer_custCode").next().attr("data-disabled", true);
	             $("#actNo").setComponent_cmpactivity({readonly: true});  
	             $("#custCode_show").css("color","gray");
				 $("#actNo_show").css("color","gray");           
	        }
	    }    
	  }); 
	});
	
	//编辑礼品领用明细
	$("#updateGiftAppDetail").on("click",function(){	
		var ret = selectDataTableRow("dataTable_GiftAppDetail");
	    if (ret) {
	      var giftUseDisc=getGiftUseDisc(ret);
	      Global.Dialog.showDialog("GiftAppDetailUpdate",{
			  title:"修改礼品领用明细",
			  url:"${ctx}/admin/giftApp/goGiftAppDetailAdd",	
			  postData:{itemCode:ret.itemcode,
			            itemDescr:ret.itemdescr,
	                    tokenPk:ret.tokenpk,
	                    tokenNo:ret.tokenno,
	                    qty:ret.qty,  
	                    price:ret.price ,
	                    cost:ret.cost, 
	                    sendQty:ret.sendqty,
	                    remarks:ret.remarks,
	            		actNo:$.trim($("#actNo").val()),
	            		giftAppType:$.trim($("#type").val()),
	            		custCode:$.trim($("#custCode").val()),
	            		useDiscAmount:ret.usediscamount,
	            		thisGiftUseDisc:giftUseDisc,
	                  },       
			  height:500,
			  width:800,
			   returnFun:function(data){
					var id = $("#dataTable_GiftAppDetail").jqGrid("getGridParam","selrow");
							Global.JqGrid.delRowData("dataTable_GiftAppDetail",id);
					  if(data){
						  $.each(data,function(k,v){
							  var json = {
								   itemcode:v.itemCode,
				                   itemdescr :v.itemDescr,
				                   tokenpk:v.tokenPk,
				                   tokenno:v.tokenNo,
				                   qty:v.qty,   
				                   price:v.price,
				                   cost:v.cost,
				                   sendqty:v.sendQty,
				                   sumcost:v.qty*v.cost,
				                   remarks:v.remarks,
				                   uomdescr:v.uom,
				                   expired: "F",
				                   actionlog: "ADD",
				                   lastupdate:v.lastUpdate,
								   lastupdatedby:v.lastUpdatedBy,
								   usediscamount:v.useDiscAmount,
							  };
							  Global.JqGrid.addRowData("dataTable_GiftAppDetail",json);
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
		var id2= $("#dataTable_GiftAppDetail").jqGrid("getGridParam","selrow");
		if((!id2)&&( $.trim($("#type").val())!='3')&&($.trim($("#type").val())!='4' )){
	        $("#actNo").setComponent_cmpactivity({readonly: false}),
	        $("#custCode_show").css("color","black");
		    $("#actNo_show").css("color","black");
		    $("#openComponent_customer_custCode").removeAttr("readonly");
			$("#openComponent_customer_custCode").next().attr("data-disabled", false).css({
							"color":""
			});
		}	
	
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
	                    shareper:v.sharePer,
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
	                    department2:ret.department2,
	                    sharePer:ret.shareper,
		                    
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
	                                shareper:v.sharePer,
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
		                    sharePer:ret.shareper,        
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
