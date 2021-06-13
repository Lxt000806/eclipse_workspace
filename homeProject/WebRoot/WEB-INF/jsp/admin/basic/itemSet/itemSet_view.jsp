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
function view(){
	var ret = selectDataTableRow();
	console.log(ret); 
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
    }else{
    	art.dialog({    	
			content: "请选择一条记录"
		});
    }
}

/**初始化表格*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");

var	dataSet = {
	firstSelect:"itemType1",
	firstValue:'${itemSet.itemType1}',
	disabled:"true"
};
Global.LinkSelect.setSelect(dataSet);

$(function(){
        //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemSet/goJqGridDetail?no="+"${itemSet.no }",		
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
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<ul class="ul-form">
					<li>
						<label><span class="required">*</span>编号</label>
							<input type="text" id="no" name="no"  value="${itemSet.no }" readonly="true" />
						<label>客户类型</label>
					</li>
					<li>
						<house:xtdm id="custType" dictCode="CUSTTYPE" value="${itemSet.custType }" onchange="changeCustType()" unShowValue="0"></house:xtdm></label>						
						<label><span class="required">*</span>名称</label>
						<input type="text" id="descr" name="descr"  value="${itemSet.descr }" />
						</label>
					</li>
					<li>
						<label><span class="required">*</span>材料类型1</label>
							<select id="itemType1" name="itemType1"  ></select>
						</label>	
					</li>
					</ul>
				<ul class="ul-form">
					<li>
						<label class="control-textarea" >备注：</label>
						<textarea id="remarks" name="remarks">${itemSet.remarks }</textarea>
					</li>				
				</ul>	
				</div>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<div class="panel panel-system" >
			   <div class="panel-body" >
			     <div class="btn-group-xs" >
					<button type="button" class="btn btn-system "  onclick="view()">查看</button>	
			     </div>
			  	</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
			</div>
		</div>
	</div>

<script>
$("#saveBtn").on("click",function(){
	if(!$("#page_form").valid()) {return false;}//表单校验				
	var param= Global.JqGrid.allToJson("dataTable");
	Global.Form.submit("page_form","${ctx}/admin/itemSet/doitemSetUpdate",param,function(ret){
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
});
</script>	
</body>
</html>


