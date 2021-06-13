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
/**初始化表格*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/cmpActivity/goSupplierJqGrid?no="+"${cmpactivity.no }",		
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [	
			  {name : 'PK',index : 'PK',width : 70,label:'PK',sortable : true,align : "left", hidden:true},  				  
			  {name : 'SupplCode',index : 'SupplCode',width : 85,label:'供应商编号',sortable : true,align : "left",},  				  
		      {name : 'descr',index : 'descr',width : 120,label:'供应商名称',sortable : true,align : "left",},
		      {name : 'SupplType',index : 'SupplType',width : 120,label:'供应商类型',sortable : true,align : "left",hidden:true},
		      {name : 'typedescr',index : 'typedescr',width : 120,label:'供应商类型',sortable : true,align : "left",},
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
					<button type="button" class="btn btn-system " id="add" >新增</button>
					<button type="button" class="btn btn-system "  onclick="del()">删除</button>
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
$("#add").on("click",function(){
	Global.Dialog.showDialog("Add",{			
		title:"添加活动供应商",
	    url:"${ctx}/admin/cmpActivity/goSupplAdd?id="+'${cmpactivity.no}',
	  	height: 400,
	  	width:700,
	  	returnFun: goto_query,
	});
});
function del(){	
	var ret=selectDataTableRow();
	art.dialog({
		content:"是否删除",
		lock: true,
		width: 200,
		height: 100,
		ok: function () {
			$.ajax({
				url:'${ctx}/admin/cmpActivity/doDelSuppl?pk='+ret.PK,
				type: 'post',
				dataType: 'json',
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
			    },
			    success: function(obj){
					$("#dataTable").jqGrid("setGridParam",{page:1}).trigger("reloadGrid");
			    }
			 });
		},
		cancel: function () {
			return true;
		}
	});	
}
</script>	
</body>
</html>


