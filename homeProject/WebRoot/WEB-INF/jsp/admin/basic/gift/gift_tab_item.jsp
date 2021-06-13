<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
String czylb=uc.getCzylb();
String czybh="";
if("2".equals(czylb)) czybh=uc.getCzybh();
%>
<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script> 
<script type="text/javascript"> 
function goItem(m_umState,name){
	var ret = selectDataTableRow("dataTable_item");		
	var itemCodes = $("#dataTable_item").getCol("itemcode", false).join(",");
	var baseItemCodes = $("#dataTable_item").getCol("baseitemcode", false).join(",");
	if(!ret && m_umState!="A"){
		art.dialog({    	
			content: "请选择一条记录"
		});
		return;
	}
	if($("#quoteModule").val()==""){
		art.dialog({    	
			content: "请先选择报价模块"
		});
		return;
	}
    Global.Dialog.showDialog("goItemAdd",{
		title:"对应材料--"+name,
		url:"${ctx}/admin/gift/goItemAdd",	
		postData:{
		  	itemCode:m_umState=="A"?"":ret.itemcode,
		  	m_umState:m_umState,itemDescr:m_umState=="A"?"":ret.itemdescr,
		  	baseItemCode:m_umState=="A"?"":ret.baseitemcode,
		  	baseItemDescr:m_umState=="A"?"":ret.baseitemdescr,
		  	quoteModule:$("#quoteModule").val(),
		  	itemCodes:itemCodes,baseItemCodes:baseItemCodes
		},	                 			 		 
		height:350,
		width:500,
		rowNum:100000,
		returnFun:function(data){
			var id = $("#dataTable_item").jqGrid("getGridParam","selrow");
			if(data){
				$.each(data,function(k,v){
					var json = {
						itemcode:v.itemCode,
		                itemdescr:v.itemDescr,
		                baseitemcode:v.baseItemCode,
		                baseitemdescr:v.baseItemDescr,
		                lastupdate: new Date(),
		                lastupdatedby:"${USER_CONTEXT_KEY.czybh}",
		                expired: "F",
		                actionlog: m_umState=="M"?"EDIT":"ADD",
					};
					if(m_umState=="M"){
						$("#dataTable_item").setRowData(id, json);
					}else if(m_umState=="A"){
						Global.JqGrid.addRowData("dataTable_item",json);
						$("#quoteModule").attr("disabled",true);
					}
				});
			}
		}   
	}); 
}
$(function(){
	/**初始化表格*/
	var gridOption ={			
		url:"${ctx}/admin/gift/goItemJqGrid?pk="+"${gift.pk }",	
		ondblClickRow: function(){
        	goItem('V','查看');
        },
        styleUI: 'Bootstrap',
		height:$(document).height()-$("#content-list").offset().top-35,			
		colModel : [		
			  {name : 'pk',index : 'pk',width : 80,label:'pk',sortable : true,align : "left",hidden:true},
		      {name : 'giftpk',index : 'giftpk',width : 80,label:'giftpk',sortable : true,align : "left",hidden:true},
		      {name : 'baseitemcode',index : 'baseitemcode',width : 105,label:'基础项目编号',sortable : true,align : "left"},		 
		      {name : 'baseitemdescr',index : 'baseitemdescr',width : 105,label:'基础项目名称',sortable : true,align : "left"},	
		      {name : 'itemcode',index : 'itemcode',width : 80,label:'材料编号',sortable : true,align : "left"},		 
		      {name : 'itemdescr',index : 'itemdescr',width : 120,label:'材料名称',sortable : true,align : "left"},	
		      {name : 'lastupdate',index : 'lastupdate',width : 130,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 80,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 80,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 80,label:'操作日志',sortable : true,align : "left"},
        ], 
        gridComplete:function(){
        	if($("#dataTable_item").jqGrid('getGridParam','records')!=0){
				$("#quoteModule").attr("disabled",true);
			}
        }
    };
    Global.JqGrid.initJqGrid("dataTable_item",gridOption);
});
function itemDel(){
	var id = $("#dataTable_item").jqGrid("getGridParam","selrow");
	if(!id){
		art.dialog({content: "请选择一条记录进行删除操作！"});
		return false;
	}
	art.dialog({
		content:"是否确认要删除？",
		lock: true,
		ok: function () {
			Global.JqGrid.delRowData("dataTable_item",id);
			if($("#dataTable_item").jqGrid('getGridParam','records')==0){
				$("#quoteModule").removeAttr("disabled");
			}
		},
		cancel: function () {
			return true;
		}
	}); 
}
</script>		
<div class="body-box-list" style="margin-top: 0px;">
	<div class="pageContent">
			 <div class="panel panel-system" >
			   <div class="panel-body" >
			     <div class="btn-group-xs" >
			      	<button type="button" class="btn btn-system view" id="add" onclick="goItem('A','添加')">添加</button>
					<button type="button" class="btn btn-system view" id="update"  onclick="goItem('M','添加')">编辑</button>
					<button type="button" class="btn btn-system view" id="del" onclick="itemDel()">删除</button>
					<button type="button" class="btn btn-system " id="view" onclick="goItem('V','查看')">查看</button>
			     </div>
			  	</div>
			</div>			
		<div id="content-list">	
			<table id="dataTable_item" ></table>
		</div>
	</div>
</div>	



