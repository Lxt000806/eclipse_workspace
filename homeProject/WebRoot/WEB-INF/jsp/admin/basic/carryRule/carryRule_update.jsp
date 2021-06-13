<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
String czylb=uc.getCzylb();
String czybh="";
if("2".equals(czylb)) czybh=uc.getCzybh();
%>
<!DOCTYPE html>
<html>
<head>
	<title>编辑</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">	
//tab分页
$(function(){    
    $("#id_detail>.tab_container .tab_content").hide(); 
    $("#id_detail>ul.tabs li:first").addClass("active").show();  
    $("#id_detail>.tab_container .tab_content:first").show();
    $("#id_detail>ul.tabs li").click(function() { 
        $("#id_detail>ul.tabs li").removeClass("active");  
        $(this).addClass("active");  
        $("#id_detail>.tab_container .tab_content").hide(); 
        var activeTab = $(this).find("a").attr("href"); 
        $(activeTab).fadeIn();
        //去掉最后一个竖线
        if($(this).find("a").attr('href')=='#tab_totalByBrand'){
        	if ($(this).attr("class")=='active'){
        		$(this).css("border-right","none");
        	}
        }else{
        	$("#id_detail>ul.tabs li").css("border-right","");
        }
        $("#id_detail>.tab_container .tab_content").find(".tab_content:first").show();
        return false;
    });
    
    chooseSupplierOrWarehouse($("#sendType").get(0));
    
}); 

function save(){
	$("#dataForm").bootstrapValidator('validate'); //验证表单
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return; //验证表单
	var ids = $("#dataTable_itemAppSend").getDataIDs();
	var ids2 = $("#dataTable").getDataIDs();
	var carrytype=$.trim($("#carryType").val());
	if((!ids)&&(!ids2)){
		art.dialog({content: "明细表中无数据！",width: 200});
		return false;
	}
	var itemType1=$.trim($("#itemType1").val());
	var itemType2=$.trim($("#itemType2").val());
	Global.JqGrid.allToJson("dataTable");
	var detailJson = Global.JqGrid.allToJson("dataTable_itemAppSend");
	var detailJson1 = Global.JqGrid.allToJson("dataTable");	
	var param = {"itemAppsendDetailJson": JSON.stringify(detailJson1),"salesInvoiceDetailJson":JSON.stringify(detailJson)};
	Global.Form.submit("dataForm","${ctx}/admin/CarryRule/doitemSetUpdate",param,function(ret){
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
};
//校验函数
$(function() {
	 $("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
			},
			fields: {  
				itemType1:{  
					validators: {  
						notEmpty: {  
							message: '材料类型1不能为空'  
						}
					}  
				},
				itemType2:{  
					validators: {  
						notEmpty: {  
							message: '材料类型2不能为空'  
						}
					}  
				},
				carryType:{  
					validators: {  
						notEmpty: {  
							message: '搬运类型不能为空'  
						}  
					}  
				},
				sendType:{  
					validators: {  
						notEmpty: {  
							message: '发货类型不能为空'  
						}  
					}  
				},
				remarks:{
					validators:{
						stringLength: {
	                           max: 200,
	                           message: '长度不超过200个字符'
	                       } 
					}
				}
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});	
	 
		$("#supplCode").openComponent_supplier({showValue:'${carryRule.supplCode}',showLabel:'${carryRule.supplDescr}',condition:{ReadAll:"1"},
		      callBack:function(){validateRefresh('openComponent_supplier_supplCode')}});
		
		$("#wHCode").openComponent_wareHouse({
		    showValue: '${carryRule.wHCode}'
		});
		$("#openComponent_wareHouse_wHCode").blur();
		
		
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
});

function validateRefresh(){
	$('#dataForm').data('bootstrapValidator')
	              .updateStatus('itemType1', 'NOT_VALIDATED',null)  
	              .validateField('itemType1')
	              .updateStatus('itemType2', 'NOT_VALIDATED',null)  
	              .validateField('itemType2')
	              .updateStatus('carryType', 'NOT_VALIDATED',null)  
		          .validateField('carryType')
		          .updateStatus('remarks', 'NOT_VALIDATED',null)  
		          .validateField('remarks');                     
};

function chooseSupplierOrWarehouse(element) {
    var supplCodeLi = $("#supplCodeLi");
    var wHCodeLi = $("#wHCodeLi");

    if (element.value === "1") {
        supplCodeLi.show();
        wHCodeLi.hide();
    } else if (element.value === "2") {
        supplCodeLi.hide();
        wHCodeLi.show();
    } else {
        supplCodeLi.hide();
        wHCodeLi.hide();
    }
}

</script>
</head>
    
<body >
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" id="saveBtn" class="btn btn-system " onclick="save()" >保存</button>
		      <button type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<ul class="ul-form">
					<div class="validate-group">
					<li>	
						<label><span class="required">*</span>编号</label>
						<input type="text" id="no" name="no"   value="${carryRule.no}" readonly="true" />
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>搬运类型</label>
						<house:xtdm id="carryType" dictCode="CarType" value="${carryRule.carryType} "></house:xtdm>													
					</li>
					</div>
					<div class="validate-group">
					<li class="form-validate">
						<label><span class="required">*</span>材料类型1</label>
						<select id="itemType1" name="itemType1" disabled="disabled"  ></select>
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>材料类型2</label>
						<select id="itemType2" name="itemType2"   disabled="disabled"></select>
					</li>
					</div>
					<div class="validate-group">
					<li>
						<label>距离类型</label>
						<house:xtdm id="DistanceType" dictCode="DistanceType" value="${carryRule.distanceType} "></house:xtdm>													
					</li>
					<li class="form-validate">
						<label>计算方式</label>
						<house:xtdm id="calType" dictCode="CarryCalType" value="${carryRule.calType}"></house:xtdm>													
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>发货类型</label>
						<house:xtdm id="sendType" dictCode="ITEMAPPSENDTYPE" value="${carryRule.sendType}"
						            onchange="chooseSupplierOrWarehouse(this)"></house:xtdm>													
					</li>
					<li id="supplCodeLi" class="form-validate" style="display:none;">
						<label>供应商</label>
						<input type="text" id="supplCode" name="supplCode" value="${carryRule.supplCode}" />
					</li>
					<li id="wHCodeLi" class="form-validate" style="display:none;">
						<label>仓库</label>
						<input type="text" id="wHCode" name="wHCode" value="${carryRule.wHCode}" />
					</li>
					<li class="form-validate">	
						<label class="control-textarea">备注</label>
						<textarea id="remarks" name="remarks">${carryRule.remarks }</textarea>
					</li>				
					<li hidden="true">
						<span class="required">*</span>11</label>
						<input type="text" id="fieldJson" name="fieldJson"   value="" />
					</li>
				</ul>
			</form>
			</div>
		</div>
		<div class="infoBox" id="infoBoxDiv"></div>
				<div class="infoBox" id="infoBoxDiv"></div>
		<div  class="container-fluid" >  
		    <ul class="nav nav-tabs" >
		        <li id="tabitemAppSend" class="active"><a href="#tab_itemAppSend" data-toggle="tab">匹配楼层</a></li>  
		        <li id="tabsalesInvoice" class=""><a href="#tab_salesInvoice" data-toggle="tab">匹配材料</a></li>
		    </ul>  
		    <div class="tab-content">  
				<div id="tab_itemAppSend"  class="tab-pane fade in active"> 
			    	<jsp:include page="CarryRule_addfloor.jsp"></jsp:include>
				</div>  
				<div id="tab_salesInvoice"  class="tab-pane fade "> 
		         	<jsp:include page="carryRuleItem3_add.jsp"></jsp:include>
				</div>
			</div>	
		</div>			

<script type="text/javascript">         

//新增楼层明细
$("#addfloor").on("click",function(){	
	var itemAppsenddetailJson = Global.JqGrid.allToJson("dataTable_itemAppSend","no");	
	var now= new Date();
    var year=now.getFullYear();
    var month=now.getMonth();
    var date=now.getDate();
    var hours=now.getHours();
    var min=now.getMinutes();
    var s=now.getSeconds();
    var time=year+"-"+(month+1)+"-"+date+" "+hours+":"+min+":"+s;        	
	var no=$("#no").val();
	var itemtype1=$("#itemType1").val();
	var itemtype2=$("#itemType2").val();	
	var itemcode1 = Global.JqGrid.allToJson("dataTable","itemtype3");
	Global.Dialog.showDialog("carryRuleItemadd",{			
		title:"添加楼层明细",		
		url:"${ctx}/admin/CarryRule/goadd",
		postData:{},		 
		height: 600,
		width:1000,
		returnFun: function(data){       	
       		if(data){    	 	
           		$.each(data,function(k,v){
	               	var json = {                      	
	                	beginfloor:v.beginFloor,
	                    endfloor:v.endFloor,
	                    cardamount:v.cardAmount,   
	                    incvalue:v.incValue,
	                    lastupdate: time,
	                    lastupdatedby:"${czy }",
	                    expired: "F",
	                    actionlog: "ADD",
	                    lastupdateby:v.lastupdateby                                                                         
	                };               
	                Global.JqGrid.addRowData("dataTable_itemAppSend",json);
				});             
			}
		}    
	}); 
});

//编辑楼层
$("#updatefloor").on("click",function(){	
	var now= new Date();
    var year=now.getFullYear();
    var month=now.getMonth();
    var date=now.getDate();
    var hours=now.getHours();
    var min=now.getMinutes();
    var s=now.getSeconds();
    var time=year+"-"+(month+1)+"-"+date+" "+hours+":"+min+":"+s;  	
	var ret = selectDataTableRow("dataTable_itemAppSend");	
    if (ret) {
	    Global.Dialog.showDialog("carryRuleUpdate",{
			title:"修改楼层",
			url:"${ctx}/admin/CarryRule/goaddUpdate",	
			postData:{
				beginFloor:ret.beginfloor,
	            endFloor:ret.endfloor,
	            cardAmount:ret.cardamount,   
	            incValue:ret.incvalue,},		 			 		  
			height:600,
			width:1000,
			returnFun:function(data){
				var id = $("#dataTable_itemAppSend").jqGrid("getGridParam","selrow");
				Global.JqGrid.delRowData("dataTable_itemAppSend",id);
				if(data){
					$.each(data,function(k,v){
						var json = {
							beginfloor:v.beginFloor,
			                endfloor:v.endFloor,
			                cardamount:v.cardAmount,   
		                    incvalue:v.incValue,
		                    lastupdate: time,
		                    lastupdatedby:"${czy }",
		                    expired: "F",
		                    actionlog: "ADD",
		                    lastupdateby:v.lastupdateby 		                      		                      			                     		                     
						  };
						  Global.JqGrid.addRowData("dataTable_itemAppSend",json);
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
//查看 楼层信息
$("#viewfloor").on("click",function(){
	var ret=selectDataTableRow("dataTable_itemAppSend");
    if(ret){
    	Global.Dialog.showDialog("itemSetView",{
			title:"查看楼层",
		   	url:"${ctx}/admin/CarryRule/goaddView",	
		  	postData:{
		  		beginFloor:ret.beginfloor,
                endFloor:ret.endfloor,
                cardAmount:ret.cardamount,   
                incValue:ret.incvalue,},		 			 
		  	height:600,
		  	width:1000,
		  	returnFun:function(data){
				var id = $("#dataTable_itemAppSend").jqGrid("getGridParam","selrow");
				Global.JqGrid.delRowData("dataTable_itemAppSend",id);
				if(data){
					$.each(data,function(k,v){
						var json = {
							beginfloor:v.beginFloor,
		                    endfloor:v.endFloor,
		                    cardamount:v.cardAmount,   
		                    incvalue:v.incValue,		                    		                   		                   
						  };
						  Global.JqGrid.addRowData("dataTable_itemAppSend",json);
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

//删除
$("#delfloor").on("click",function(){
	var id = $("#dataTable_itemAppSend").jqGrid("getGridParam","selrow");
	if(!id){
		art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
		return false;
	}
	Global.JqGrid.delRowData("dataTable_itemAppSend",id);	

});


//新增材料明细
$("#additem").on("click",function(){	
	var itemAppsenddetailJson = Global.JqGrid.allToJson("dataTable_itemAppSend","no");	
	var now= new Date();
    var year=now.getFullYear();
    var month=now.getMonth();
    var date=now.getDate();
    var hours=now.getHours();
    var min=now.getMinutes();
    var s=now.getSeconds();
    var time=year+"-"+(month+1)+"-"+date+" "+hours+":"+min+":"+s;        	
	var no=$("#no").val();
	var itemtype1=$("#itemType1").val();
	var itemtype2=$("#itemType2").val();	
	if (itemtype1==""){
		art.dialog({content: "请选择材料类型1！",width: 200});
		return false;
	};
	if (itemtype2==""){
		art.dialog({content: "请选择材料类型2！",width: 200});
		return false;
	};
	var itemcode1 = Global.JqGrid.allToJson("dataTable","itemtype3");
	Global.Dialog.showDialog("carryRuleItemadd",{			
		title:"添加材料明细",		
		url:"${ctx}/admin/CarryRule/goItemadd",
		postData:{no:no,itemType1:itemtype1,itemType2:itemtype2},		 
		height: 600,
		width:1000,
		returnFun: function(data){       	
			if(data){    	 	
				$.each(data,function(k,v){
					var json = {               	 	             	 
				    itemtype3:v.itemType3,                                     
				  	itemtype3descr:v.itemType3Descr,
				    lastupdate: time,
				    lastupdatedby:"${czy }",
				    expired: "F",
				    actionlog: "ADD",
				    lastupdateby:v.lastupdateby                                                                            
					};                
					Global.JqGrid.addRowData("dataTable",json);
			 	});      
			$("#itemType1").attr("disabled","disabled");
			$("#itemType2").attr("disabled","disabled");       
			}
		}    
	}); 
});
//编辑材料明细
$("#updateitem").on("click",function(){
	var now= new Date();
	var year=now.getFullYear();
	var month=now.getMonth();
	var date=now.getDate();
	var hours=now.getHours();
	var min=now.getMinutes();
	var s=now.getSeconds();
	var time=year+"-"+(month+1)+"-"+date+" "+hours+":"+min+":"+s;        
	var ret = selectDataTableRow();		
	var no=$("#no").val();
	var itemType1=$("#itemType1").val();
	var itemtype2=$("#itemType2").val();
    if (ret) {
    	Global.Dialog.showDialog("carryRuleUpdate",{
		title:"修改材料页面",
		url:"${ctx}/admin/CarryRule/goitemUpdate",	
		postData:{no:no,itemType1:itemType1,itemType2:itemtype2,itemType3:ret.itemtype3,itemType3Descr:ret.itemtype3descr},	                 			 		 
		height:600,
		width:1000,
		returnFun:function(data){
			var id = $("#dataTable").jqGrid("getGridParam","selrow");
			Global.JqGrid.delRowData("dataTable",id);						
			if(data){
				$.each(data,function(k,v){
					var json = {
						itemtype3:v.itemType3,       
		                itemtype3descr:v.itemType3Descr,
	                    lastupdate: time,
	                   	lastupdatedby:"${czy }",
	                   	expired: "F",
	                   	actionlog: "ADD",
	                   	lastupdateby:v.lastupdateby   		                      		                      			                     		                     
					};
					Global.JqGrid.addRowData("dataTable",json);
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

//查看材料明细
$("#viewitem").on("click",function(){
	var ret = selectDataTableRow();		
	var no=$("#no").val();
	var itemType1=$("#itemType1").val();
	var itemtype2=$("#itemType2").val();
    if (ret) {
	    Global.Dialog.showDialog("carryRuleView",{
			title:"查看材料页面",
			url:"${ctx}/admin/CarryRule/goitemView",	
			postData:{no:no,itemType1:itemType1,itemType2:itemtype2,itemType3:ret.itemtype3,itemType3Descr:ret.itemtype3descr},	                 			 		 
			height:600,
			width:1000,
			returnFun:function(data){
				var id = $("#dataTable").jqGrid("getGridParam","selrow");
				Global.JqGrid.delRowData("dataTable",id);						
				if(data){
					$.each(data,function(k,v){
						var json = {
							itemtype3:v.itemcode,
			                itemtype3descr:v.itemType3Descr,
			                lastupdate: time,
			                lastupdatedby:"${czy }",
			                expired: "F",
			                actionlog: "ADD",
			                lastupdateby:v.lastupdateby   		                      		                      			                     		                     
						};
						Global.JqGrid.addRowData("dataTable",json);
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

//删除材料明细
$("#delitem").on("click",function(){
	var id = $("#dataTable").jqGrid("getGridParam","selrow");
	if(!id){
		art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
		return false;
	}
	Global.JqGrid.delRowData("dataTable",id);	
	var ids = $("#dataTable").getDataIDs();		
	if((!ids || ids.length == 0)){
	   $("#itemType1").removeAttr("disabled","disabled");
	   $("#itemType2").removeAttr("disabled","disabled");		
	}
});	
</script>
<script type="text/javascript">
function myfun(){
	var ids = $("#dataTable").getDataIDs();			
	if(!ids){
	   $("#itemType1").removeAttr("disabled","disabled");
	   $("#itemType2").removeAttr("disabled","disabled");		
	}
}
window.onload = myfun;
</script>
</body>
</html>
