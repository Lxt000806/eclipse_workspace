<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>客户列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">

	/**初始化表格*/

	var postData = $("#dataForm").jsonForm();
	
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
		Global.LinkSelect.setSelect({
			firstSelect:"itemType1",
			firstValue:"${upgWithhold.itemType1}",
			});
		if("M"=="${upgWithhold.m_umState}"){
			$("#itemType1").attr("disabled","disabled"); 
			$("#code").prop("readOnly",true);
		}else if("A"=="${upgWithhold.m_umState}"){
			$("#chekbox_show").hide();
		}else if("C"=="${upgWithhold.m_umState}"){
			$("#code").val("");
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
			url:"${ctx}/admin/upgWithhold/goJqGridDetail",		
			height:$(document).height()-$("#content-list").offset().top-60,
			styleUI: "Bootstrap",
			loadonce: true,
		    rowNum:100000,  
			postData:{code:"${upgWithhold.code}",itemType1:"${upgWithhold.itemType1}"},
			rowNum:10000000,
			pager:"1",
			colModel : [		
				{name : "itemcode",index : "itemcode",width : 70,label:"材料编号",sortable : true,align : "left",frozen: true},
				{name : "itemdescr",index : "itemdescr",width : 220,label:"材料名称",sortable : true,align : "left",frozen: true},
				{name : "lastupdate",index : "lastupdate",width : 150,label:"最后修改时间",sortable : true,align : "left",formatter: formatTime},
				{name : "lastupdatedby",index : "lastupdatedby",width : 70,label:"修改人",sortable : true,align : "left"},
				{name : "expired",index : "expired",width : 70,label:"是否过期",sortable : true,align : "left"},
				{name : "actionlog",index : "actionlog",width : 70,label:"操作日志",sortable : true,align : "left"}
		     ]
		});
		
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: {
				 code:{  
					validators: {  
						notEmpty: {  
							message: '请输入完整信息'  
						}
					}  
				},
				descr:{  
					validators: {  
						notEmpty: {  
							message: '请输入完整信息'  
						}
					}  
				}, 
				areaTo:{  
					validators: {  
						notEmpty: {  
							message: '请输入完整信息'  
						}
					}  
				}, 
				area:{  
					validators: {  
						notEmpty: {  
							message: '请输入完整信息'  
						}
					}  
				}, 
				beginDate:{  
					validators: {  
						notEmpty: {  
							message: '请输入完整信息'  
						}
					}  
				}, 
				endDate:{  
					validators: {  
						notEmpty: {  
							message: '请输入完整信息'  
						}
					}  
				},
				calAmount:{  
					validators: {  
						notEmpty: {  
							message: '请输入完整信息'  
						}
					}  
				},  
				custType:{  
					validators: {  
						notEmpty: {  
							message: '请输入完整信息'  
						}
					}  
				},  
				calType:{  
					validators: {  
						notEmpty: {  
							message: '请输入完整信息'  
						}
					}  
				},  
				existCal:{  
					validators: {  
						notEmpty: {  
							message: '请输入完整信息'  
						}
					}  
				},  
				itemType1:{  
					validators: {  
						notEmpty: {  
							message: '材料类型1不能为空'  
						}
					}  
				},
			},
		});	
	}); 

	function add(){
		var now= new Date();
	    var year=now.getFullYear();
	    var month=now.getMonth();
	    var date=now.getDate();
	    var hours=now.getHours();
	    var min=now.getMinutes();
	    var s=now.getSeconds();
	    var time=year+"-"+(month+1)+"-"+date+" "+hours+":"+min+":"+s;
	  	var item1 = $.trim($("#itemType1").val());
		if(item1 ==''){
			art.dialog({content: "请选择材料类型",width: 200});			
			return false;
		}
		var upgWithholdNums = Global.JqGrid.allToJson("dataTable","upgWithholdNums");
		var arr=[];
		for(var i=0;i<upgWithholdNums.datas.length;i++){
			arr[i]=upgWithholdNums.datas[i].itemcode;
		}
		var str=JSON.stringify(arr);
		Global.Dialog.showDialog("itemSetadd",{			
			title:"升级扣项材料信息-增加",		
			url:"${ctx}/admin/upgWithhold/goUpgWithholdDetail",
			postData:{
				itemType1:item1,
				str:str
			},		 
			height: 300,
			width:400,
			returnFun: function(data){       	
	       		if(data){    	 	
	           		$.each(data,function(k,v){
	               	var json = {              	 
	                	itemcode:v.itemCode,
	                    itemdescr:v.itemDescr,
	                    lastupdate: time,
	                    expired: "F",
	                    actionlog: "ADD",
	                    lastupdatedby:v.lastUpdatedBy                                            
	                };
	                	Global.JqGrid.addRowData("dataTable",json);
	            	}); 
		        	$("#itemType1").attr("disabled","disabled");               
		        }
			}
		});
	}
	
	function update(){
		var now= new Date();
	    var year=now.getFullYear();
	    var month=now.getMonth();
	    var date=now.getDate();
	    var hours=now.getHours();
	    var min=now.getMinutes();
	    var s=now.getSeconds();
	    var time=year+"-"+(month+1)+"-"+date+" "+hours+":"+min+":"+s;  	
	  	var item1 = $.trim($("#itemType1").val());
		if(item1 ==''){
			art.dialog({content: "请选择材料类型",width: 200});			
			return false;
		}
		var upgWithholdNums = Global.JqGrid.allToJson("dataTable","upgWithholdNums");
		var arr=[];
		for(var i=0;i<upgWithholdNums.datas.length;i++){
			arr[i]=upgWithholdNums.datas[i].itemcode;
		}
		var str=JSON.stringify(arr);
		var ret = selectDataTableRow();	
	    if (ret) {
	    	Global.Dialog.showDialog("upgWithholdDetailUpdate",{
			title:"升级扣项材料信息-修改",
			url:"${ctx}/admin/upgWithhold/goUpgWithholdDetail",	
			postData:{
				itemCode:ret.itemcode,
				itemDescr:ret.itemdescr,
				itemType1:item1,
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
							itemcode:v.itemCode,
	                    	itemdescr:v.itemDescr,
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
	
	function view(){
		var ret = selectDataTableRow();
	    if (ret) {
		    Global.Dialog.showDialog("itemSetView",{
				title:"升级扣项材料信息-查看",
				url:"${ctx}/admin/upgWithhold/goUpgWithholdDetail",	
				postData:{
				m_umState:"V",
					itemCode:ret.itemcode,
					itemDescr:ret.itemdescr
				},		 			 
				height:300,
				width:400,
				returnFun:function(data){
					var id = $("#dataTable").jqGrid("getGridParam","selrow");
					Global.JqGrid.delRowData("dataTable",id);
					if(data){
						$.each(data,function(k,v){
							var json = {
								itemcode:v.itemcode
							};
							Global.JqGrid.addRowData("dataTable",json);
						});
					 }
				}   
			});
	    }else {
	    	art.dialog({    	
				content: "请选择一条记录"
			});
	    }
	}

	function save(){
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		
		var datas = $("#dataForm").jsonForm();
		var param= Global.JqGrid.allToJson("dataTable");
		if(param.datas.length == 0){
			art.dialog({content: "请先增加升级扣项规则管理明细信息！",width: 220});
			return;
		}
		$.extend(param,datas);
		$.ajax({
			url:"${ctx}/admin/upgWithhold/doSave",
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
				var ids = $("#dataTable").getDataIDs();		
				if((!ids || ids.length == 0)){
					$("#itemType1").removeAttr("disabled","disabled");		
				};
			},
			cancel: function () {
				return true;
			}
		});
	}
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
       	<button type="button" id="saveBtn" class="btn btn-system "   onclick="save()">保存</button>
		<button type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
      </div>
   	</div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
             	<input type="hidden" id="expired" name="expired" value="${upgWithhold.expired }"/>
             	<input type="hidden" id="m_umState" name="m_umState" value="${upgWithhold.m_umState}"/>
				<house:token></house:token>
				<ul class="ul-form">
					<div class="row">
						<li class="form-validate">
							<label><span class="required">*</span>编号</label>
							<input type="text" id="code" name="code"  value="${upgWithhold.code }" />
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>材料类型1</label>
							<select id="itemType1" name="itemType1"  ></select>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>客户类型</label>
							<house:xtdm id="custType" dictCode="CUSTTYPE" value="${upgWithhold.custType }" unShowValue="0"></house:xtdm></label>						
						</li>
					</div>
					<div class="row">
						<li class="form-validate">	
							<label><span class="required">*</span>名称</label>
							<input type="text" id="descr" name="descr"  value="${upgWithhold.descr }" />
						</li>
						<li class="form-validate">	
							<label><span class="required">*</span>面积小于</label>
							<input type="text" id="areaTo" name="areaTo"  value="${upgWithhold.areaTo }" />
						</li>
						<li class="form-validate">	
							<label><span class="required">*</span>面积大于等于</label>
							<input type="text" id="area" name="area"  value="${upgWithhold.area }" />
						</li>
					</div>
					<div class="row">
						<li class="form-validate">
								<label><span class="required">*</span>存在时计算</label>
								<house:xtdm id="existCal" dictCode="YESNO" value="${upgWithhold.existCal}"></house:xtdm>
						</li>
						<li class="form-validate">
							<label ><span class="required">*</span>开始时间</label>
							<input type="text" id="beginDate" name="beginDate" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${upgWithhold.beginDate}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li class="form-validate">
							<label ><span class="required">*</span>结束时间</label>
							<input type="text" id="endDate" name="endDate" class="i-date"  
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${upgWithhold.endDate}' pattern='yyyy-MM-dd'/>" />
						</li>
					</div>
					<div class="row">
						<li class="form-validate">
							<label><span class="required">*</span>计算类型</label>
							<house:xtdm id="calType" dictCode="UPGCALTYPE" style="width:160px;" value="${upgWithhold.calType }"/>
						</li>
						<li class="form-validate">	
							<label><span class="required">*</span>计算金额</label>
							<input type="text" id="calAmount" name="calAmount"  value="${upgWithhold.calAmount }" />
						</li>
						<li class="form-validate">
							<label>户型</label>
							<house:xtdm id="layout" dictCode="LAYOUT" value="${upgWithhold.layout}"></house:xtdm>
						</li>
						<li id="chekbox_show">
							<label>过期</label>
							<input type="checkbox" id="expired_show" name="expired_show" value="${upgWithhold.expired }",
								onclick="checkExpired(this)" ${upgWithhold.expired=="T"?"checked":"" }/>
						</li>
					</div>
				</ul>
			</form>
			</div>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
				<li class="active"><a href="#upgWithhold_tabView_item" data-toggle="tab">升级扣项材料明细信息</a></li>  
			</ul>
			<div class="pageContent">
				<div class="panel panel-system" >
					<div class="panel-body" >
						<div class="btn-group-xs" >
					      	<button type="button" id="add" class="btn btn-system " onclick="add()">新增</button>
							<button type="button" id="update" class="btn btn-system "  onclick="update()">编辑</button>
							<button type="button" id="delete" class="btn btn-system " onclick="delDetail()" >删除</button>
							<button type="button" id="view" class="btn btn-system "  onclick="view()">查看</button>
				    	</div>
				  	</div>
				</div>					
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>
			</div>
		</div>
	</div>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
</body>
</html>


