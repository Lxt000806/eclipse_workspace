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
	if("M"=="${mainCommiRule.m_umState}"){
			$("#itemType2").attr("disabled","disabled"); 
			$("#itemType3").attr("disabled","disabled"); 
			$("#no").prop("readOnly",true);
	}else if("A"=="${mainCommiRule.m_umState}"){
			$("#chekbox_show").hide();
			$("#commiType option[value=2]").prop("selected", "selected"); 
	}else{
		$("#saveBtn").hide();
		$("#add").hide();
		$("#update").hide();
		$("#delete").hide();
		$("#chekbox_show").hide();
		disabledForm("dataForm");
	}
		
	        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/mainCommiRule/goJqGridDetail",		
			height:$(document).height()-$("#content-list").offset().top-60,
			styleUI: "Bootstrap",
			postData:{no:"${mainCommiRule.no}",itemType1:"ZC"},
			rowNum:10000000,
			pager:"1",
			colModel : [		
				{name : "itemtype2",index : "itemtype2",width : 220,label:"材料类型2",sortable : true,align : "left",frozen: true,hidden:true},
				{name : "itemtype3",index : "itemtype3",width : 220,label:"材料类型3",sortable : true,align : "left",frozen: true,hidden:true},
				{name : "itemtype2descr",index : "itemtype2descr",width : 220,label:"材料类型2",sortable : true,align : "left",frozen: true},
				{name : "itemtype3descr",index : "itemtype3descr",width : 220,label:"材料类型3",sortable : true,align : "left",frozen: true},
				{name : "lastupdate",index : "lastupdate",width : 150,label:"最后修改时间",sortable : true,align : "left",formatter: formatTime},
				{name : "lastupdatedby",index : "lastupdatedby",width : 70,label:"修改人",sortable : true,align : "left"},
				{name : "expired",index : "expired",width : 70,label:"是否过期",sortable : true,align : "left"},
				{name : "actionlog",index : "actionlog",width : 70,label:"操作日志",sortable : true,align : "left"}
		     ]
		});
}); 
	$(function() {
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: {
				commiType:{  
					validators: {  
						notEmpty: {  
							message: "请选择提成类型"
						}
					}  
				},
				fromProfit:{  
					validators: {  
						notEmpty: {  
							message: "请填写毛利率从"  
						}
					}  
				}, 
				toProfit:{  
					validators: {  
						notEmpty: {  
							message: "请填写毛利率至"
						}
					}  
				}, 
				commiPerc:{  
					validators: {  
						notEmpty: {  
							message: "请填写提成比例"
						}
					}  
				}, 
			},
		});	
	});
		
	function save(){
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		
		var datas = $("#dataForm").jsonForm();
		var param= Global.JqGrid.allToJson("dataTable");
		$.extend(param,datas);
		$.ajax({
			url:"${ctx}/admin/mainCommiRule/doSave",
			type: "post",
			data: param,
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin();
					    }
					});
		    	}else{
		    		$("#_form_token_uniq_id").val(obj.token.token);
		    		art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
	    	}
		});
	};
</script>
</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" id="saveBtn" class="btn btn-system "  onclick="save()">保存</button>
		      <button type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
         <div class="body-box-form" >
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="${mainCommiRule.m_umState}"/>
				<ul class="ul-form">
					<div class="validate-group">
					<li>	
						<label><span class="required">*</span>编号</label>
						<input type="text" id="no" name="no"   value="${mainCommiRule.no}" readonly="true"  placeholder="保存时生成"/>
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>提成类型</label>
						<house:xtdm id="commiType" dictCode="COMMITYPE" value="${mainCommiRule.commiType}"></house:xtdm>													
					</li>
					</div>
					<div class="validate-group">
					<li class="form-validate">
						<label><span class="required">*</span>毛利率从</label>
						<input type="text" id="fromProfit" name="fromProfit" style="width:160px;" onkeyup="value=value.replace(/[^(\-|\+)?\d+(\.\d+)?$]/g,'')" value="${mainCommiRule.fromProfit }" />
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>毛利率至</label>
						<input type="text" id="toProfit" name="toProfit" style="width:160px;" onkeyup="value=value.replace(/[^(\-|\+)?\d+(\.\d+)?$]/g,'')" value="${mainCommiRule.toProfit }" />
					</li>
					</div>
					<div class="validate-group">
					<li class="form-validate">
						<label><span class="required">*</span>提成比例</label>
						<input type="text" id="commiPerc" name="commiPerc" style="width:160px;" onkeyup="value=value.replace(/[^(\-|\+)?\d+(\.\d+)?$]/g,'')" value="${mainCommiRule.commiPerc }" />												
					</li>
					<li class="form-validate">
						<label class="control-textarea" >备注：</label>
						<textarea id="remarks" name="remarks">${mainCommiRule.remarks }</textarea>
					</li>
					<li hidden="true">
						<span class="required">*</span>11</label>
						<input type="text" id="fieldJson" name="fieldJson"   value="" />
					</li>
				</ul>
			</form>
			</div>
			</div>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
				<li class="active"><a href="#mainCommiRule_tabView_item" data-toggle="tab">主材提成规则匹配明细</a></li>  
			</ul>
			<div class="pageContent">
				<div class="panel panel-system" >
					<div class="panel-body" >
						<div class="btn-group-xs" >
					      	<button type="button" id="add" class="btn btn-system " onclick="addDetail()">新增</button>
							<button type="button" id="update" class="btn btn-system "  onclick="updateDetail()">编辑</button>
							<button type="button" id="delete" class="btn btn-system " onclick="delDetail()" >删除</button>
							<button type="button" id="view" class="btn btn-system "  onclick="viewDetail()">查看</button>
				    	</div>
				  	</div>
				</div>					
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>
			</div>
		</div>
	</div>
		
    <script type="text/javascript">
	    function addDetail(){
		    var now= new Date();
		    var year=now.getFullYear();
		    var month=now.getMonth();
		    var date=now.getDate();
		    var hours=now.getHours();
		    var min=now.getMinutes();
		    var s=now.getSeconds();
		    var time=year+"-"+(month+1)+"-"+date+" "+hours+":"+min+":"+s;  	
			var mainCommiRuleItems = Global.JqGrid.allToJson("dataTable","mainCommiRuleItems");
			var arr=[];
			for(var i=0;i<mainCommiRuleItems.datas.length;i++){
				arr[i]=mainCommiRuleItems.datas[i].itemtype3;
			}
			var str=JSON.stringify(arr);
			Global.Dialog.showDialog("mainCommiRuleItemAdd",{			
				title:"主材提成规则匹配明细-增加",		
				url:"${ctx}/admin/mainCommiRule/goMainCommiRuleDetail",
				postData:{
					str:str
				},		 
				height: 300,
				width:400,
				returnFun: function(data){       	
		       		if(data){    	 	
		           		$.each(data,function(k,v){
			               	var json = {
			               		itemtype2:v.itemType2,
			               		itemtype3:v.itemType3,
			                    itemtype2descr:v.itemType2Descr.trim().split(/\s+/)[1],
			                    itemtype3descr:v.itemType3Descr.trim().split(/\s+/)[1],
			                    lastupdate: time,
			                    expired: "F",
			                    actionlog: "ADD",
			                    lastupdatedby:v.lastUpdatedBy                                            
			                };
			                	Global.JqGrid.addRowData("dataTable",json);
		            	}); 
			        }
				}
			});
		}
		
		function updateDetail(){
		var now= new Date();
	    var year=now.getFullYear();
	    var month=now.getMonth();
	    var date=now.getDate();
	    var hours=now.getHours();
	    var min=now.getMinutes();
	    var s=now.getSeconds();
	    var time=year+"-"+(month+1)+"-"+date+" "+hours+":"+min+":"+s;  	
		var mainCommiRuleItems = Global.JqGrid.allToJson("dataTable","mainCommiRuleItems");
		var arr=[];
		for(var i=0;i<mainCommiRuleItems.datas.length;i++){
			arr[i]=mainCommiRuleItems.datas[i].itemtype3;
		}
		var str=JSON.stringify(arr);
		var ret = selectDataTableRow();	
	    if (ret) {
	    	Global.Dialog.showDialog("mainCommiRuleItemUpdate",{
			title:"主材提成规则匹配明细-修改",
			url:"${ctx}/admin/mainCommiRule/goMainCommiRuleDetail",	
			postData:{
				m_umState:"M",
				itemType2:ret.itemtype2,
				itemType3:ret.itemtype3,
				str:str
			},		 			 		  
			height:300,
			width:400,
			returnFun:function(data){
				var id = $("#dataTable").jqGrid("getGridParam","selrow");
				Global.JqGrid.delRowData("dataTable",id);
				if(data){
					$.each(data,function(k,v){
						var json = {
							itemtype2:v.itemType2,
			               	itemtype3:v.itemType3,
			                itemtype2descr:v.itemType2Descr.trim().split(/\s+/)[1],
			                itemtype3descr:v.itemType3Descr.trim().split(/\s+/)[1],
			                lastupdate: time,
			                expired: "F",
			                actionlog: "Edit",
			                lastupdatedby:v.lastUpdatedBy   		                      		                      			                     		                     
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
	}
	
	function delDetail(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		};
		art.dialog({
			content:"是否删除该记录？",
			lock: true,
			width: 200,
			height: 80,
			ok: function () {
				Global.JqGrid.delRowData("dataTable",id);	
			},
			cancel: function () {
				return true;
			}
		});
	}
		
		function viewDetail(){
		var ret = selectDataTableRow();
	    if (ret) {
		    Global.Dialog.showDialog("mainCommiRuleItemView",{
				title:"主材提成规则匹配明细-查看",
				url:"${ctx}/admin/mainCommiRule/goMainCommiRuleDetail",	
				postData:{
					m_umState:"V",
					itemType2:ret.itemtype2,
					itemType3:ret.itemtype3,
				},		 			 
				height:300,
				width:400,
			});
	    }else {
	    	art.dialog({    	
				content: "请选择一条记录"
			});
	    }
	}
    </script>
</body>
</html>
