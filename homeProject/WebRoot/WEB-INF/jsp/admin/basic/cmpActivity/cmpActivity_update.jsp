<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>公司活动管理</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function add(){
	var now= new Date();
    var year=now.getFullYear();
    var month=now.getMonth();
    var date=now.getDate();
    var hours=now.getHours();
    var min=now.getMinutes();
    var s=now.getSeconds();
    var time=year+"-"+(month+1)+"-"+date+" "+hours+":"+min+":"+s;
	Global.Dialog.showDialog("cmpActivityGift",{			
		title:"添加礼品明细",		
		url:"${ctx}/admin/cmpActivity/goadd",		 
		height: 600,
		width:1000,
		returnFun: function(data){       	
       		if(data){    	 	
           		$.each(data,function(k,v){
               		var json = {   
               	 		type:v.Type,
	               	 	typedescr:v.typedescr,            	 
	                    itemcode:v.itemcode,
	                    itemcodedescr:v.itemcodedescr,                     
	                    lastupdate: time,
	                    lastupdatedby:"${czy }",
	                    expired: "F",
	                    actionlog: "ADD",
	                    lastupdateby:v.lastupdateby                                            
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
	var ret = selectDataTableRow();	
    if (ret) {
    	Global.Dialog.showDialog("cmpActivityGiftupdate",{
			title:"编辑礼品明细",
		  	url:"${ctx}/admin/cmpActivity/goaddUpdate",	
		  	postData:{itemcode:ret.itemcode,type:ret.type,typedescr:ret.typedescr,itemCodeDescr:ret.itemcodedescr},		 			 		  
		  	height:600,
		  	width:1000,
		   	returnFun:function(data){
				var id = $("#dataTable").jqGrid("getGridParam","selrow");
				Global.JqGrid.delRowData("dataTable",id);
				if(data){
					$.each(data,function(k,v){
						var json = {
							type:v.Type,
			               	typedescr:v.typedescr,            	 
		                    itemcode:v.itemcode,
		                    itemcodedescr:v.itemcodedescr,                     
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
}
function View(){
	var now= new Date();
    var year=now.getFullYear();
    var month=now.getMonth();
    var date=now.getDate();
    var hours=now.getHours();
    var min=now.getMinutes();
    var s=now.getSeconds();
    var time=year+"-"+(month+1)+"-"+date+" "+hours+":"+min+":"+s;  	
	var ret = selectDataTableRow();	
    if (ret) {
    	Global.Dialog.showDialog("cmpActivityGiftupdate",{
			title:"查看礼品明细",
		  	url:"${ctx}/admin/cmpActivity/goaddView",	
		  	postData:{itemcode:ret.itemcode,type:ret.type,typedescr:ret.typedescr,itemCodeDescr:ret.itemcodedescr},		 			 		  
		  	height:600,
		  	width:1000,
		  	returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {};
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
/**初始化表格*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
$(function(){
	      //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{	
		url:"${ctx}/admin/cmpActivity/goJqGridDetail?no="+"${cmpactivity.no }",	
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',
		rowNum:100000,  
    	pager :'1',
    	loadonce: true,
		colModel : [		
	      {name : 'pk',index : 'pk',width : 70,label:'活动编号',sortable : true,align : "left",frozen: true,hidden:true},  				  
	      {name : 'type',index : 'type',width : 120,label:'活动类型',sortable : true,align : "left",frozen: true,hidden:true},
	      {name : 'typedescr',index : 'typedescr',width : 120,label:'活动类型',sortable : true,align : "left",frozen: true},
	      {name : 'itemcode',index : 'itemcode',width : 70,label:'礼品编号',sortable : true,align : "left",frozen: true},
	      {name : 'itemcodedescr',index : 'itemcodedescr',width : 220,label:'礼品名称',sortable : true,align : "left",frozen: true},		      	
	      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
	      {name : 'lastupdatedby',index : 'lastupdatedby',width : 60,label:'修改人',sortable : true,align : "left"},
	      {name : 'expired',index : 'expired',width : 60,label:'是否过期',sortable : true,align : "left"},
	      {name : 'actionlog',index : 'actionlog',width : 60,label:'操作日志',sortable : true,align : "left"}
	          ]
	});
});  
});
function check(){
	var check=document.getElementById("check").value;
	if (check=='T'){
		document.getElementById("expired_show").checked="checked";	
	}
}
$(function() {
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
		},
		fields: {  
			descr:{  
				validators: {  
					notEmpty: {  
						message: '名称不能为空'  
					}
				}  
			},
			beginDate:{  
				validators: {  
					notEmpty: {  
						message: '开始时间不能为空'  
					}  
				}  
			},
			endDate:{  
				validators: {  
					notEmpty: {  
						message: '结束时间不能为空'  
					}
				}  
			}
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
});
function save(){	
	validateRefresh();
	$("#dataForm").bootstrapValidator('validate'); //验证表单
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return; //验证表单
	var check=$.trim($("#expired_show").val());	
	var descr = $.trim($("#descr").val());	
	var beginDate = $.trim($("#beginDate").val());	
	var endDate = $.trim($("#endDate").val());	
	if(beginDate>endDate){
	art.dialog({content: "开始时间不能大于结束时间",width: 200});
		return false;
	}
	var param= Global.JqGrid.allToJson("dataTable");
	Global.Form.submit("dataForm","${ctx}/admin/cmpActivity/docmpActivityUpdate",param,function(ret){
		if(ret.rs==true){
			art.dialog({
				content:ret.msg,
				time:1000,
				beforeunload:function(){
					closeWin();						
				}
			});				
		}else{
			$("#_form_token_uniq_id").val(ret.token.token);
			art.dialog({
				content:ret.msg,
				width:200
			});
		}
	});
};
function validateRefresh(){
	$('#dataForm').data('bootstrapValidator')
                  .updateStatus('descr', 'NOT_VALIDATED',null)  
                  .validateField('descr')
                  .updateStatus('beginDate', 'NOT_VALIDATED',null)  
                  .validateField('beginDate')
                  .updateStatus('endDate', 'NOT_VALIDATED',null)  
	              .validateField('endDate');                     
};
function aaa(){
	$('#dataForm').data('bootstrapValidator')                  
                  .updateStatus('beginDate', 'NOT_VALIDATED',null)  
                  .validateField('beginDate');  
}
function bbb(){
	$('#dataForm').data('bootstrapValidator')                  
                  .updateStatus('endDate', 'NOT_VALIDATED',null)  
	              .validateField('endDate');   
}
function quickAdd(){
	var now= new Date();
    var year=now.getFullYear();
    var month=now.getMonth();
    var date=now.getDate();
    var hours=now.getHours();
    var min=now.getMinutes();
    var s=now.getSeconds();
    var time=year+"-"+(month+1)+"-"+date+" "+hours+":"+min+":"+s;
	Global.Dialog.showDialog("cmpActivityGiftQuickAdd",{			
		title:"快速新增礼品明细",		
		url:"${ctx}/admin/cmpActivity/goQuickSave",		 
		height: 680,
		width:1000,
		returnFun: function(data){       	
	   		if(data){    	 	
	   			console.log(data);
	       		$.each(data,function(k,v){
		           	var json = {   
		           		type:v.Type,
		           	 	typedescr:v.typedescr,            	 
		                itemcode:v.code,
		                itemcodedescr:v.descr,                     
		                lastupdate: time,
		                lastupdatedby:"${czy }",
		                expired: "F",
		                actionlog: "ADD",
		                lastupdateby:v.lastupdateby                                            
		            };
		            Global.JqGrid.addRowData("dataTable",json);
	        	}); 
	        	$("#itemType1").attr("disabled","disabled");               
	    	}
		}
	});
}
</script>
</head>
    
<body onload="check()">
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" id="saveBtn" class="btn btn-system "   onclick="save()">保存</button>
		      <button type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="M"/>
					<input type="hidden" id="expired" name="expired" value="${cmpactivity.expired}" />
				<ul class="ul-form">
				<div class="validate-group">
					<li>
							<label><span class="required">*</span>活动编号</label>
							<input type="text" id="no" name="no"  value="${cmpactivity.no}" readonly="true"/>
					</li>
					<li class="form-validate">
							<label><span class="required">*</span>活动名称</label>							
							<input type="text" id="descr" name="descr"  value="${cmpactivity.descr}" />
					</li>
					<li hidden="true">
						<label><span class="required">*</span>是否过期</label>							
						<input type="text" id="check" name="check"  value="${cmpactivity.expired}" />
						<label><span class="required">*</span>名称</label>							
						<input type="text" id="descr1" name="descr1"  value="${cmpactivity.descr}" />
					</li>
					
					
					<li class="form-validate">																								
						<label><span class="required">*</span>开始时间</label>
						<input type="text" id="beginDate"  onchange="aaa()" name="beginDate" class="i-date"  
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${cmpactivity.beginDate}' pattern='yyyy-MM-dd'/>"/>		
					</li>
					</div>
					<div class="validate-group">
					<li class="form-validate">
						<label><span class="required">*</span>结束时间</label>
						<input type="text" id="endDate"  onchange="bbb()"  name="endDate" class="i-date"  
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${cmpactivity.endDate}' pattern='yyyy-MM-dd'/>"/>
					</li>
					</div>
					</ul>
					<ul class="ul-form">
					<li class="form-validate">
						<label class="control-textarea" >备注：</label>
						<textarea id="remarks" name="remarks">${cmpactivity.remarks }</textarea>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${customer.expired }" onclick="checkExpired(this)" ${customer.expired=='T'?'checked':'' } ${flag=='doc'?'disabled':'' }/>是否过期
					</li>				
				</ul>
			</form>
			</div>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<div class="panel panel-system" >
			   <div class="panel-body" >
			     <div class="btn-group-xs" >
			      	<button type="button" class="btn btn-system " onclick="add()">添加</button>
			      	<button type="button" class="btn btn-system " onclick="quickAdd()">快速新增</button>
					<button type="button" class="btn btn-system "  onclick="update()">编辑</button>
					<button type="button" class="btn btn-system " id="delDetail">删除</button>
					<button type="button" class="btn btn-system "  onclick="View()">查看</button>
					
			     </div>
			  	</div>
			</div>	
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>

	
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script>
//删除
$("#delDetail").on("click",function(){
	var id = $("#dataTable").jqGrid("getGridParam","selrow");
	if(!id){
		art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
		return false;
	}
	Global.JqGrid.delRowData("dataTable",id);	
	var ids = $("#dataTable").getDataIDs();		
	if((!ids || ids.length == 0)){
	   $("#itemType1").removeAttr("disabled","disabled");		
	}
});
</script>	
</body>
</html>


