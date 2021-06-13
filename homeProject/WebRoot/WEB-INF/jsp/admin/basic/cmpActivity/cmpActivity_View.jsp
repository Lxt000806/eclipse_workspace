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
$(function() {
	$("#page_form").validate({
		rules: {				
				"beginDate": {
				validIllegalChar: true,
				required: true,
				maxlength: 10
				},
				"endDate": {
				validIllegalChar: true,
				required: true,
				maxlength: 20
				},
				"descr": {
				validIllegalChar: true,
				required: true,
				maxlength: 20
				},	
				"no": {
				validIllegalChar: true,
				required: true,
				maxlength: 20
				}		
			}
	});
});

function View(){
	var now= new Date();
    var year=now.getFullYear();
    var month=now.getMonth();s
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
				var id = $("#dataTable").jqGrid("getGridParam","selrow");
				Global.JqGrid.delRowData("dataTable",id);
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
			rowNum:100000,  
    		pager :'1',
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
</script>
</head>
    
<body>
	<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="M"/>
				<ul class="ul-form">
					<li>
							<label><span class="required">*</span>活动编号</label>
							<input type="text" id="no" name="no"  value="${cmpactivity.no}" readonly="true"/>
					</li>
					<li>
							<label><span class="required">*</span>活动名称</label>							
							<input type="text" id="descr" name="descr"  value="${cmpactivity.descr}" />
					</li>
					<li hidden="true">
						<label><span class="required">*</span>是否过期</label>							
						<input type="text" id="check" name="check"  value="${cmpactivity.expired}" />
						<label><span class="required">*</span>名称</label>							
						<input type="text" id="descr1" name="descr1"  value="${cmpactivity.descr}" />
					</li>
					<li>																								
						<label><span class="required">*</span>开始时间</label>
						<input type="text" id="beginDate" name="beginDate" class="i-date"  
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${cmpactivity.beginDate}' pattern='yyyy-MM-dd'/>"/>		
					</li>
					<li>
						<label><span class="required">*</span>结束时间</label>
						<input type="text" id="endDate" name="endDate" class="i-date"  
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${cmpactivity.endDate}' pattern='yyyy-MM-dd'/>"/>
					</li>
					</ul>
					<ul class="ul-form">
					<li>
						<label class="control-textarea" >备注：</label>
						<textarea id="remarks" name="remarks">${cmpactivity.remarks }</textarea>
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
$("#saveBtn").on("click",function(){	
	if(!$("#page_form").valid()) {return false;}//表单校验
	var no = $.trim($("#no").val());	
	if (no==""){
		art.dialog({content: "活动编号不能为空",width: 200});
		return false;
	}
	var descr = $.trim($("#descr").val());	
	if (descr==""){
		art.dialog({content: "活动名称不能为空",width: 200});
		return false;
	}
	var beginDate = $.trim($("#beginDate").val());	
	if (beginDate==""){
		art.dialog({content: "开始时间不能为空",width: 200});
		return false;
	}	
	var endDate = $.trim($("#endDate").val());	
	if (endDate==""){
		art.dialog({content: "结束时间不能为空",width: 200});
		return false;
	}
	if(beginDate>endDate){
	art.dialog({content: "开始时间不能大于结束时间",width: 200});
		return false;
	}
	if(!$("#page_form").valid()) {return false;}//表单校验				
	var param= Global.JqGrid.allToJson("dataTable");
	Global.Form.submit("page_form","${ctx}/admin/cmpActivity/docmpActivitySave",param,function(ret){
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
});
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


