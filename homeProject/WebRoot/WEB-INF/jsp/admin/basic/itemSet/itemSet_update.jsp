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
$(function() {
	$("#dataForm").validate({
		rules: {				
			"itemType1": {
				validIllegalChar: true,
				required: true,
				maxlength: 10
			},
			"descr": {
				validIllegalChar: true,
				required: true,
				maxlength: 20
			},			
		}
	});
});
function add(){
	var now= new Date();
	var year=now.getFullYear();
	var month=now.getMonth();s
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
	Global.Dialog.showDialog("itemSetadd",{			
		title:"添加材料套餐包明细",		
		url:"${ctx}/admin/itemSet/goadd",		 
		postData:{itemtype1:item1},		 
		height: 600,
		width:1000,
		returnFun: function(data){       	
	    	if(data){    	 	
	        	$.each(data,function(k,v){
	            	var json = {               	 
	                	itemcode:v.itemcode,
	                    itemcodedescr:v.itemcodedescr,
	                    unitprice:v.unitprice,   
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
	var month=now.getMonth();s
	var date=now.getDate();
	var hours=now.getHours();
	var min=now.getMinutes();
	var s=now.getSeconds();
	var time=year+"-"+(month+1)+"-"+date+" "+hours+":"+min+":"+s;
  	var item1 = $.trim($("#itemType1").val());
	var item1 = $.trim($("#itemType1").val());
	if(item1 ==''){
		art.dialog({content: "请选择材料类型",width: 200});			
		return false;
	}	
	var itemcode1 = Global.JqGrid.allToJson("dataTable","itemcode");
	var ret = selectDataTableRow();		
    if (ret) {
    	Global.Dialog.showDialog("goaddUpdate",{
			title:"修改材料套餐包",
		    url:"${ctx}/admin/itemSet/goaddUpdate",	
		    postData:{itemcode:ret.itemcode,unitprice:ret.unitprice,itemDescr:ret.itemcodedescr,itemtype1:item1,unSelected: itemcode1["fieldJson"]},		 			 		  
		    height:600,
		    width:1000,
		    returnFun:function(data){
				var id = $("#dataTable").jqGrid("getGridParam","selrow");
				Global.JqGrid.delRowData("dataTable",id);					
				if(data){
					$.each(data,function(k,v){
						var json = {
							itemcode:v.itemcode,
							itemcodedescr:v.itemcodedescr,
		                    unitprice:v.unitprice,		                    
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
    }else{
    	art.dialog({    	
			content: "请选择一条记录"
		});
    }
}

function view(){
	var ret = selectDataTableRow();
    if (ret) {
    	Global.Dialog.showDialog("itemSetView",{
			title:"查看材料套餐包",
		    url:"${ctx}/admin/itemSet/goaddView",	
		    postData:{itemcode:ret.itemcode,unitprice:ret.unitprice,itemDescr:ret.itemcodedescr},		 			 
		    height:600,
		    width:1000,
		    returnFun:function(data){
			    var id = $("#dataTable").jqGrid("getGridParam","selrow");
				Global.JqGrid.delRowData("dataTable",id);
				if(data){
					$.each(data,function(k,v){
						var json = {
							itemcode:v.itemcode,
			                unitprice:v.unitprice,   		                     
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

/**初始化表格*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	
	var dataSet = {
			firstSelect:"itemType1",
			firstValue:'${itemSet.itemType1}',
			disabled:"true"
		};
	Global.LinkSelect.setSelect(dataSet);
	
	$(function(){
	        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/itemSet/goJqGridDetail?no="+"${itemSet.no }",
			styleUI: 'Bootstrap',		
			height:$(document).height()-$("#content-list").offset().top-60,
			rowNum:10000000,
			colModel : [		
				{name : 'itemcode',index : 'itemcode',width : 70,label:'材料编号',sortable : true,align : "left",frozen: true},
			    {name : 'itemcodedescr',index : 'itemcodedescr',width : 220,label:'材料名称',sortable : true,align : "left",frozen: true},		     
			    {name : 'unitprice',index : 'unitprice',width : 60,label:'套餐单价',sortable : true,align : "left",frozen: true},		 	 
			    {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
			    {name : 'expired',index : 'expired',width : 60,label:'是否过期',sortable : true,align : "left"},
			    {name : 'actionlog',index : 'actionlog',width : 60,label:'操作日志',sortable : true,align : "left"}
	        ]
		});
	});  
});
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
			descr:{  
				validators: {  
					notEmpty: {  
						message: '材料名称不能为空'  
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
});
function save(){
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	var itemtype1 = $.trim($("#itemType1").val());
	var adescr = $.trim($("#descr").val());
	var adescr1 = $.trim($("#descr1").val());
	if (itemtype1==""){
		art.dialog({content: "材料类型1不能为空",width: 200});	
	};	
	if(!$("#dataForm").valid()) {return false;}//表单校验		
	var param= Global.JqGrid.allToJson("dataTable");
	Global.Form.submit("dataForm","${ctx}/admin/itemSet/doitemSetUpdate",
	param,function(ret){
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
				content:ret.msg,width:200
			});
		}
	});
};


function validateRefresh(){
	$('#dataForm').data('bootstrapValidator')
                  .updateStatus('itemType1', 'NOT_VALIDATED',null)  
                  .validateField('itemType1')
                  .updateStatus('descr', 'NOT_VALIDATED',null)  
                  .validateField('descr')
                  .updateStatus('remarks', 'NOT_VALIDATED',null)  
              	  .validateField('remarks');                     
};
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
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<ul class="ul-form">
					<li>
						<label><span class="required">*</span>编号</label>
							<input type="text" id="no" name="no"  value="${itemSet.no }" readonly="true" />
					</li>
					<li class="form-validate">
						<label>客户类型</label>
						<house:xtdm id="custType" dictCode="CUSTTYPE" value="${itemSet.custType }" onchange="changeCustType()" unShowValue="0"></house:xtdm></label>						
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>名称</label>
						<input type="text" id="descr" name="descr"  value="${itemSet.descr }" />
						</label>
					</li>
					<li class="form-validate">
						<label><span class="required">*</span>材料类型1</label>
							<select id="itemType1" name="itemType1"  disabled="disabled"></select>
						</label>	
					</li>
					</ul>
				<ul class="ul-form">
					<li class="form-validate">
						<label class="control-textarea" >备注：</label>
						<textarea id="remarks" name="remarks">${itemSet.remarks }</textarea>
					</li>				
				</ul>	
			</form>
		</div>
		</div>
		<div class="clear_float"> </div>
		<div class="pageContent">
			<div class="panel panel-system" >
			   <div class="panel-body" >
			     <div class="btn-group-xs" >
			      	<button type="button" class="btn btn-system " onclick="add()">添加</button>
					<button type="button" class="btn btn-system "  onclick="update()">编辑</button>
					<button type="button" class="btn btn-system " id="delDetail">删除</button>
					<button type="button" class="btn btn-system "  onclick="view()">查看</button>	
			     </div>
			  	</div>
			</div>	
			<div id="content-list">
				<table id= "dataTable"></table>
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


