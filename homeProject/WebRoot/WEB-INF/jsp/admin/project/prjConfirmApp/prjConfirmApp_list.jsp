<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>验收申请管理列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<%-- 	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script> --%>

<script type="text/javascript">

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#prjItem").val('');
	$.fn.zTree.getZTreeObj("tree_prjItem").checkAllNodes(false);
}

function add(){
	Global.Dialog.showDialog("prjConfirmAppAdd",{
		  title:"添加验收申请信息",
		  url:"${ctx}/admin/PrjConfirmApp/goSave",
		  height: 600,
		  width:700,
		  returnFun: goto_query 
		});
}

function view(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("prjConfirmAppView",{
		  title:"查看验收申请信息",
		  url:"${ctx}/admin/PrjConfirmApp/goDetail",
		  postData:{pk:ret.pk,address:ret.address,department1Descr:ret.department1descr,department2Descr:ret.department2descr,
		  			projectManDescr:ret.projectmandescr,date:ret.date,phone:ret.phone,confirmCZY:ret.confirmczy,
		  			prjLevelDescr:ret.prjleveldescr,workerName:ret.workername},
		  height:600, 
		  width:700
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
    	if(ret.status == '2'){
    	    art.dialog({
				content: "该记录已验收无法编辑"
			});
			return;
    	}
		Global.Dialog.showDialog("prjConfirmAppUpdate",{
		  title:"修改验收申请记录",
		  url:"${ctx}/admin/PrjConfirmApp/goUpdate?id="+ret.pk,
		  height:600,
		  width:700,
		  returnFun: goto_query
		}); 
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/PrjConfirmApp/goJqGrid",postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");

}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/PrjConfirmApp/doExcel";
	doExcelAll(url);
}
function del(){
	var ret = selectDataTableRow();
    if (ret) {
    	if(ret.status=="2"){
    		art.dialog({
    			content:"已验收记录不能删除"
    		});
    		return ;
    	}
    	art.dialog({
    		content:"确认删除该记录",
    		ok:function(){
    			$.ajax({
    				url:"${ctx}/admin/PrjConfirmApp/doDelete?pk="+ret.pk,
    				type:"post",
    				dataType:"json",
    				cache:false,
    				error:function(obj){
    					art.dialog({
    						content:"访问出错,请重试",							
    						time: 1000,
							beforeunload: function () {
								goto_query();
							}
    					});
    				},
    				success:function(obj){
    					if(obj.rs){
/* 							art.dialog({
								content: obj.msg,
								time: 1000,
								beforeunload: function () {
								}
							}); */
							goto_query();
						}else{
							$("#_form_token_uniq_id").val(obj.token.token);
							art.dialog({
								content: obj.msg,
								width: 200
							});
						}
    				}
    			});
    		},
    		cancel:function(){
    			goto_query();
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
		Global.JqGrid.initJqGrid("dataTable",{
			height:$(document).height()-$("#content-list").offset().top-80,
        	styleUI: 'Bootstrap',
			colModel : [
			  {name : 'pk',index : 'pk',width : 50,label:'pk',sortable : true,align : "left", hidden:true},
		      {name : 'address',index : 'address',width : 165,label:'楼盘',sortable : true,align : "left"},
		      {name : 'custtypedescr',index : 'custtypedescr',width : 80,label:'客户类型',sortable : true,align : "left"},
		      {name : 'regiondescr',index : 'regiondescr',width : 90,label:'一级区域',sortable : true,align : "left"},
		      {name : 'realaddress',index : 'realaddress',width : 165,label:'实际地址',sortable : true,align : "left", hidden:true},
		      {name : 'area',index : 'area',width : 65,label:'面积',sortable : true,align : "left"},
		      {name : 'prjitemdescr',index : 'prjitemdescr',width : 160,label:'施工节点',sortable : true,align : "left"},
			  {name : 'status',index : 'status',width : 50,label:'status',sortable : true,align : "left", hidden:true},
			  {name : 'statusdescr',index : 'statusdescr',width : 50,label:'状态',sortable : true,align : "left"},
		      {name : 'department1descr',index : 'department1descr',width : 110,label:'工程事业部',sortable : true,align : "left"},
		      {name : 'department2descr',index : 'department2descr',width : 85,label:'工程部',sortable : true,align : "left"},
		      {name : 'projectmandescr',index : 'projectmandescr',width : 70,label:'监理',sortable : true,align : "left"},
		      {name : 'phone',index : 'phone',width : 100,label:'电话',sortable : true,align : "left"},
		      {name : 'appdate',index : 'appdate',width : 120,label:'申报时间',sortable : true,align : "left",formatter:formatTime},
		      {name : 'remarks',index : 'remarks',width : 200,label:'备注',sortable : true,align : "left"},
		      {name : 'date',index : 'date',width : 140,label:'验收时间',sortable : true,align : "left",formatter:formatTime},
		      {name : 'confirmczy',index : 'confirmczy',width : 90,label:'验收人',sortable : true,align : "left"},
		      {name : 'prjleveldescr',index : 'prjleveldescr',width : 100,label:'验收评级',sortable : true,align : "left"},
		      {name : 'workerdescr',index : 'workerdescr',width : 100,label:'工人',sortable : true,align : "left"},
		      {name : 'workercode',index : 'workercode',width : 100,label:'验收申请工人',sortable : true,align : "left",hidden : true},
		      {name : 'workername',index : 'workername',width : 100,label:'验收申请工人',sortable : true,align : "left"},
		      {name : 'isleaveproblem',index : 'isleaveproblem',width : 100,label:'是否遗留问题',sortable : true,align : "left",hidden : true},
		      {name : 'isleaveproblemdescr',index : 'isleaveproblemdescr',width : 100,label:'是否遗留问题',sortable : true,align : "left"},
		      {name : 'leaveproblemremark',index : 'leaveproblemremark',width : 100,label:'遗留问题备注',sortable : true,align : "left"},
            ]
		});
/*    		$("#address").openComponent_customer();	 */
});
window.onload = function() { 
	setTimeout(goto_query,100);
}; 
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" value="${prjConfirmApp.address }" />
					</li>
					<li>
						<label>施工节点</label>
						<house:xtdmMulit id="prjItem" dictCode="" sql="SELECT Code,Descr FROM tPrjItem1 WHERE IsConfirm='1' AND Expired='F' ORDER BY Seq" sqlValueKey="Code" sqlLableKey="Descr" selectedValue="${prjConfirmApp.prjItem }"></house:xtdmMulit>
					</li>
					<li>							
						<label>状态</label>
						<house:xtdm id="status" dictCode="CONFMAPPSTS" value="${prjConfirmApp.status }"></house:xtdm>
					</li>
					<li>							
						<label>是否专盘</label>
						<house:xtdm id="isPrjSpc" dictCode="YESNO" value="${prjConfirmApp.isPrjSpc }"></house:xtdm>
					</li>
					<li>							
						<label>申报时间从</label>
						<input type="text" id="appDateFrom" name="appDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjConfirmApp.appDateFrom }' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>								
						<label>至</label>
						<input type="text" id="appDateTo" name="appDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${prjConfirmApp.appDateTo }' pattern='yyyy-MM-dd'/>"/>
					</li>
					
					<li>							
						<label>一级区域</label>
						<house:dict id="region" dictCode="" sql="SELECT Code,(Code+' '+Descr) Descr FROM  dbo.tRegion WHERE expired='F' ORDER BY CAST(Code as Integer) ASC" sqlValueKey="Code" sqlLableKey="Descr" value="${prjConfirmApp.region }"></house:dict>
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType" ></house:custTypeMulit>
					</li>	
					<li>							
						<label>工程大区</label>
						<house:dict id="prjRegion" dictCode="" sql="SELECT Code,(Code+' '+Descr) Descr FROM  dbo.tPrjRegion WHERE expired='F' ORDER BY CAST(Code as Integer) ASC" sqlValueKey="Code" sqlLableKey="Descr"></house:dict>
					</li>
					<li>							
						<label>工种分组</label>
						<house:dict id="workType12Dept" dictCode="" sql="SELECT Code,(Code+' '+Descr) Descr FROM  dbo.tWorkType12Dept WHERE expired='F' ORDER BY CAST(Code as Integer) ASC" sqlValueKey="Code" sqlLableKey="Descr"></house:dict>
					</li>		
					<li class="search-group">
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
					</li>
				</ul>
			</form>
		</div>
		
		<div class="clear_float"> </div>
		
		<div class="btn-panel" >
			<div class="btn-group-sm"  >
				<house:authorize authCode="PRJCONFIRMAPP_SAVE">
					<button type="button" class="btn btn-system " onclick="add()">新增</button>
				</house:authorize>
     			<house:authorize authCode="PRJCONFIRMAPP_UPDATE">
					<button type="button" class="btn btn-system " onclick="update()">编辑</button>
				</house:authorize>
				<house:authorize authCode="PRJCONFIRMAPP_VIEW">
					<button id="btnView" type="button" class="btn btn-system " onclick="view()">查看</button>
                </house:authorize>
				<house:authorize authCode="PRJCONFIRMAPP_DELETE">
					<button id="btnDel" type="button" class="btn btn-system " onclick="del()">删除</button>
                </house:authorize>
                <house:authorize authCode="PRJCONFIRMAPP_EXCEL">
					<button type="button" class="btn btn-system " onclick="doExcel()">输出至Excel</button>
                </house:authorize>
		    </div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>
