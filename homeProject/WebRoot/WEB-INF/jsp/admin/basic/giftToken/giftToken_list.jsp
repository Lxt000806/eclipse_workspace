<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>礼品券登记列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>	
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_cmpactivity.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
}

function add(){
	Global.Dialog.showDialog("giftTokenAdd",{
		  title:"添加礼品券信息",
		  url:"${ctx}/admin/giftToken/goAdd",
		  height: 350,
		  width:1000,
		  returnFun: goto_query,
		  close:function(){
			//$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/giftToken/goJqGrid",postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
			goto_query();
		  }
		});
}

function view(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("giftTokenView",{
		  title:"查看礼品券信息",
		  url:"${ctx}/admin/giftToken/goView",
		  postData:{
		  	pk:ret.pk,
		  	address:ret.address,
		  	custDescr:ret.custdescr,
		  	itemDescr:ret.itemdescr,
		  	statusDescr:ret.statusdescr
		  },
		  height:350,
		  width:1000
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
   
    	if(ret.status!="1"){
    		art.dialog({
    			content:"只有登记状态可以编辑"
    		});
    		return ;
    	}
		Global.Dialog.showDialog("giftTokenEdit",{
		  title:"修改礼品券信息",
		  url:"${ctx}/admin/giftToken/goEdit?pk="+ret.pk,
		  height:350,
		  width:1000,
		  returnFun: goto_query
		}); 
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/giftToken/doExcel";
	doExcelAll(url);
}
function del(){
	var ret = selectDataTableRow();
    if (ret) {
    	if(ret.status=="2" || ret.status=="3"){
    		art.dialog({
    			content:"报备和领用状态的记录不能删除"
    		});
    		return ;
    	}
    	art.dialog({
    		content:"确认删除该记录",
    		ok:function(){
    			$.ajax({
    				url:"${ctx}/admin/giftToken/doDelete?pk="+ret.pk,
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
			url:"${ctx}/admin/giftToken/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-90,
        	styleUI: "Bootstrap",
			colModel : [
			  {name : 'pk',index : 'pk',width : 50,label:'pk',sortable : true,align : "left", hidden:true},
			  {name : 'status',index : 'status',width : 50,label:'status',sortable : true,align : "left", hidden:true},
		      {name : 'custcode',index : 'custcode',width : 100,label:'客户编号',sortable : true,align : "left"},
		      {name : 'custdescr',index : 'custdescr',width : 100,label:'客户名称',sortable : true,align : "left"},
		      {name : 'address',index : 'address',width : 200,label:'楼盘',sortable : true,align : "left"},
		      {name : 'setdate',index : 'setdate',width : 80,label:'下定日期',sortable : true,align : "left",formatter : formatDate},	//增加下定日期 add by zb
		      {name : 'cmpactivitydescr',index : 'cmpactivitydescr',width : 100,label:'活动名称',sortable : true,align : "left"},
		      {name : 'tokenno',index : 'tokenno',width : 100,label:'券号',sortable : true,align : "left"},
		      {name : 'itemcode',index : 'itemcode',width : 100,label:'礼品编号',sortable : true,align : "left"},
		      {name : 'itemdescr',index : 'itemdescr',width : 100,label:'礼品名称',sortable : true,align : "left"},
		      {name : 'qty',index : 'qty',width : 80,label:'数量',sortable : true,align : "right"},
		      {name : 'statusdescr',index : 'statusdescr',width : 80,label:'状态',sortable : true,align : "left"},
		      {name : 'lastupdate',index : 'lastupdate',width : 140,label:'最后更新时间',sortable : true,align : "left",formatter:formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 120,label:'最后更新人员',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 100,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'操作日志',sortable : true,align : "left"}
            ]
		});
   		$("#custCode").openComponent_customer({
   			showValue:"${giftToken.custCode}",
   			showLabel:"${giftToken.custDescr}",
   			condition:{
   				ignoreCustRight:"1"
   			}
   		});
   		$("#itemCode").openComponent_item({
   			showValue:"${giftToken.itemCode}",
   			showLabel:"${giftToken.itemDescr}",
   			condition:{
   				itemType1:"LP"
   			}
   		});	
   		$("#no").openComponent_cmpactivity();	
});
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
						<input type="text" id="address" name="address" value="${giftToken.address }" />
					</li>
					<li>
						<label>客户编号</label>
						<input type="text" id="custCode" name="custCode" value="${giftToken.custCode }" />
					</li>
					<li>
						<label>券号</label>
						<input type="text" id="tokenNo"  name="tokenNo" value="${giftToken.tokenNo }" />
					</li>
					<li>
						<label>礼品编号</label>
						<input type="text" id="itemCode" name="itemCode" value="${giftToken.itemCode }" />
					</li>
					<li>
						<label>礼品名称</label>
						<input type="text" id="itemDescr" name="itemDescr"  value="${giftToken.itemDescr }"/>
					</li>
					<li>
						<label>状态</label>
						<house:xtdm id="status" dictCode="GIFTTOKENSTATUS" value="${giftToken.status }"></house:xtdm>
					</li>
					<li>
						<label>活动编号</label>
						<input type="text" id="no" name="no" />
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
				<house:authorize authCode="GIFTTOKEN_ADD">
					<button type="button" class="btn btn-system " onclick="add()">新增</button>
				</house:authorize>
     			<house:authorize authCode="GIFTTOKEN_EDIT">
					<button type="button" class="btn btn-system " onclick="update()">编辑</button>
				</house:authorize>
				<house:authorize authCode="GIFTTOKEN_VIEW">
					<button id="btnView" type="button" class="btn btn-system " onclick="view()">查看</button>
                </house:authorize>
				<house:authorize authCode="GIFTTOKEN_DELETE">
					<button id="btnDel" type="button" class="btn btn-system " onclick="del()">删除</button>
                </house:authorize>
                <house:authorize authCode="GIFTTOKEN_EXCEL">
					<button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>
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
