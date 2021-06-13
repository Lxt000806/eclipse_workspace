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
	<title>添加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTThttp://192.168.0.4:8080/homeProjectP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
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
}); 
$(function() {
$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
		},
		fields: {  
			code:{  
				validators: {  
					notEmpty: {  
						message: '编号不能为空'  
					}
				}  
			},
			descr:{  
				validators: {  
					notEmpty: {  
						message: '工程名称不能为空'  
					}
				}  
			}
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
	});
		
function save(){
	$("#dataForm").bootstrapValidator('validate'); //验证表单
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return; //验证表单
	var ids = $("#dataTable_workType").getDataIDs();
	var ids2 = $("#dataTable").getDataIDs();
	Global.JqGrid.allToJson("dataTable");
	var carrytype=$.trim($("#carryType").val());	
	var itemType1=$.trim($("#itemType1").val());
	var itemType2=$.trim($("#itemType2").val());
	if((!ids || ids.length == 0)&&(!ids2 || ids2.length == 0)){
		art.dialog({content: "明细表中无数据！",width: 200});
		return false;
	}
	var detailJson = Global.JqGrid.allToJson("dataTable_workType");	
	var detailJson1 = Global.JqGrid.allToJson("dataTable");	
	var itemtype3 = Global.JqGrid.allToJson("dataTable","itemtype3");
	$("#fieldJson").val(itemtype3["fieldJson"]);
	var param = {"prjRoleJson": JSON.stringify(detailJson1),"salesInvoiceDetailJson":JSON.stringify(detailJson)};
	Global.Form.submit("dataForm","${ctx}/admin/prjRole/doprjRoleUpdate",param,function(ret){
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
function validateRefresh(){
	$('#dataForm').data('bootstrapValidator')
                  .updateStatus('code', 'NOT_VALIDATED',null)  
                  .validateField('code')
                  .updateStatus('descr', 'NOT_VALIDATED',null)  
                  .validateField('descr')
};
</script>
</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
         <div class="body-box-form" >
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<ul class="ul-form">
					<div class="validate-group">
					<li class="form-validate">	
						<label><span class="required">*</span>编号</label>
						<input type="text" id="code" name="code"   value="${prjRole.code}" />
					</li>
					<li class="form-validate">	
						<label><span class="required">*</span>名称</label>
						<input type="text" id="descr" name="descr"   value="${prjRole.descr}" />
					</li>
					</div>
					<li hidden="true">
						<span class="required">*</span>11</label>
						<input type="text" id="fieldJson" name="fieldJson"   value="" />
					</li>
				</ul>
			</form>
			</div>
			</div>
		</div>
		<div class="infoBox" id="infoBoxDiv"></div>
				<div class="infoBox" id="infoBoxDiv"></div>
		<div  class="container-fluid" >  
	     <ul class="nav nav-tabs" >
	        <li id="tabitemAppSend" class="active"><a href="#tab_itemAppSend" data-toggle="tab">工种分类12</a></li>  
	        <li id="tabsalesInvoice" class=""><a href="#tab_salesInvoice" data-toggle="tab">施工项目</a></li>
	    </ul>  
	    <div class="tab-content">  
			<div id="tab_itemAppSend"  class="tab-pane fade in active"> 
		    	<jsp:include page="PrjRole_WorkType12_view.jsp"></jsp:include>
			</div>  
			<div id="tab_salesInvoice"  class="tab-pane fade "> 
	         	<jsp:include page="PrjRole_PrjItem_view.jsp"></jsp:include>
			</div>
		</div>	
	</div>	
    <script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>  
<script type="text/javascript">    
//新增工种分类
$("#addworktype12").on("click",function(){		
	var prjRoleJson = Global.JqGrid.allToJson("dataTable_workType","no");	
	var now= new Date();
    var year=now.getFullYear();
    var month=now.getMonth();
    var date=now.getDate();
    var hours=now.getHours();
    var min=now.getMinutes();
    var s=now.getSeconds();
    var time=year+"-"+(month+1)+"-"+date+" "+hours+":"+min+":"+s;        	
	Global.Dialog.showDialog("carryRuleItemadd",{			
		title:"添加工种分类",		
		url:"${ctx}/admin/prjRole/goaddwork12",
		postData:{},		 
		height: 600,
		width:1000,
		returnFun: function(data){       	
			if(data){    	 	
	   			$.each(data,function(k,v){
	       	 		var json = {                 
			       	 	prjrole:v.prjRole,
			       	 	worktype12:v.workType12,     	
			            prjroledescr:v.prjRoleDescr,
			            worktype12descr:v.workType12Descr,
			            lastupdate: time,
			            lastupdatedby:"${czy }",
			            expired: "F",
			            actionlog: "ADD",
			            lastupdateby:v.lastupdateby                                                                         
	        		};               
	        	Global.JqGrid.addRowData("dataTable_workType",json);
	    		});             
			}
		}    
	}); 
});

//编辑工种分类
$("#updateworktype12").on("click",function(){	
	var now= new Date();
    var year=now.getFullYear();
    var month=now.getMonth();
    var date=now.getDate();
    var hours=now.getHours();
    var min=now.getMinutes();
    var s=now.getSeconds();
    var time=year+"-"+(month+1)+"-"+date+" "+hours+":"+min+":"+s;  	
	var ret = selectDataTableRow("dataTable_workType");	
    if (ret) {
    	Global.Dialog.showDialog("carryRuleUpdate",{
			title:"修改工种分类",
		  	url:"${ctx}/admin/prjRole/goUpdatework12",	
		  	postData:{prjRole:ret.prjrole,
                      workType12:ret.worktype12,
                      prjRoleDescr:ret.prjroledescr,
                      workType12Descr:ret.worktype12descr
                      },		 			 		  
		  	height:600,
		  	width:1000,
		   	returnFun:function(data){
				var id = $("#dataTable_workType").jqGrid("getGridParam","selrow");
				Global.JqGrid.delRowData("dataTable_workType",id);
				if(data){
					$.each(data,function(k,v){
						var json = {
							prjrole:v.prjRole,
		               	 	worktype12:v.workType12,     	
		                    prjroledescr:v.prjRoleDescr,
		                    worktype12descr:v.workType12Descr,
		                    lastupdate: time,
		                    lastupdatedby:"${czy }",
		                    expired: "F",
		                    actionlog: "ADD",
		                    lastupdateby:v.lastupdateby 		                      		                      			                     		                     
						};
						Global.JqGrid.addRowData("dataTable_workType",json);
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
//查看 工种分类
$("#viewworktype12").on("click",function(){
	var now= new Date();
    var year=now.getFullYear();
    var month=now.getMonth();
    var date=now.getDate();
    var hours=now.getHours();
    var min=now.getMinutes();
    var s=now.getSeconds();
    var time=year+"-"+(month+1)+"-"+date+" "+hours+":"+min+":"+s;  	
	var ret = selectDataTableRow("dataTable_workType");	
    if (ret) {
    	Global.Dialog.showDialog("carryRuleUpdate",{
			title:"查看工种分类",
		  	url:"${ctx}/admin/prjRole/goViewwork12",	
		  	postData:{prjRole:ret.prjrole,
                     workType12:ret.worktype12,
                     workType12Descr:ret.worktype12descr
                     },		 			 		  
		  	height:600,
		  	width:1000,
		   	returnFun:function(data){
				var id = $("#dataTable_workType").jqGrid("getGridParam","selrow");
				Global.JqGrid.delRowData("dataTable_workType",id);
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
						Global.JqGrid.addRowData("dataTable_workType",json);
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
$("#delworktype12").on("click",function(){
	var id = $("#dataTable_workType").jqGrid("getGridParam","selrow");
	if(!id){
		art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
		return false;
	}
	Global.JqGrid.delRowData("dataTable_workType",id);	
});


//新增施工项目
$("#additem").on("click",function(){	
	var prjRoleJson = Global.JqGrid.allToJson("dataTable_workType","no");	
	var now= new Date();
    var year=now.getFullYear();
    var month=now.getMonth();
    var date=now.getDate();
    var hours=now.getHours();
    var min=now.getMinutes();
    var s=now.getSeconds();
    var time=year+"-"+(month+1)+"-"+date+" "+hours+":"+min+":"+s;        	
	Global.Dialog.showDialog("carryRuleItemadd",{			
	  title:"添加施工项目",		
	  url:"${ctx}/admin/prjRole/goaddPrjItem",
	  height: 600,
	  width:1000,
	  returnFun: function(data){       	
      		 if(data){    	 	
          		 $.each(data,function(k,v){
              	 var json = {               	 	             	 
                   prjrole:v.prjRole,                                     
                  	prjroledescr:v.prjRoleDescr,
                  	prjitem:v.prjItem,                                     
                  	prjitemdescr:v.prjItemDescr,
                   lastupdate: time,
                   lastupdatedby:"${czy }",
                   expired: "F",
                   actionlog: "ADD",
                   lastupdateby:v.lastupdateby                                                                            
              	 	} ;               
               Global.JqGrid.addRowData("dataTable",json);
           	}); 
            $("#itemType1").attr("disabled","disabled");
            $("#itemType2").attr("disabled","disabled");                             
       	}
   	}    
  }); 
});
//编辑施工项目
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
	var itemcode1 = Global.JqGrid.allToJson("dataTable","itemtype3");
    if (ret) {
      Global.Dialog.showDialog("carryRuleUpdate",{
		  title:"修改施工项目",
		  url:"${ctx}/admin/prjRole/goUpdatePrjItem",	
		  postData:{prjRole:ret.prjrole,prjRoleDescr:ret.prjroledescr,prjItem:ret.prjitem,prjItemDescr:ret.prjitemdescr},	                 			 		 
		  height:600,
		  width:1000,
		   returnFun:function(data){
				var id = $("#dataTable").jqGrid("getGridParam","selrow");
						Global.JqGrid.delRowData("dataTable",id);						
				  if(data){
					  $.each(data,function(k,v){
						  var json = {
						    prjrole:v.prjRole,                                     
		                   	prjroledescr:v.prjRoleDescr,
		                   	prjitem:v.prjItem,                                     
		                   	prjitemdescr:v.prjItemDescr,
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

//查看施工项目
$("#viewitem").on("click",function(){
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
	var itemcode1 = Global.JqGrid.allToJson("dataTable","itemtype3");
    if (ret) {
      Global.Dialog.showDialog("carryRuleUpdate",{
		  title:"查看施工项目",
		  url:"${ctx}/admin/prjRole/goViewPrjItem",	
		  postData:{prjRole:ret.prjrole,prjRoleDescr:ret.prjroledescr,prjItem:ret.prjitem,prjItemDescr:ret.prjitemdescr},	                 			 		 
		  height:600,
		  width:1000,
		  returnFun:function(data){
				var id = $("#dataTable").jqGrid("getGridParam","selrow");
				Global.JqGrid.delRowData("dataTable",id);						
				if(data){
					  $.each(data,function(k,v){
						  var json = {
						    prjrole:v.prjRole,                                     
		                   	prjroledescr:v.prjRoleDescr,
		                   	prjitem:v.prjItem,                                     
		                   	prjitemdescr:v.prjItemDescr,
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

//删除施工项目
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
</body>
</html>
