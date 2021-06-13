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
function viewfloor(){
	var ret = selectDataTableRow("dataTable_workType");
    if (ret) {
    	Global.Dialog.showDialog("itemSetView",{
			title:"查看楼层",
		  	url:"${ctx}/admin/CarryRule/goaddView",	
		  	postData:{beginFloor:ret.beginfloor,
                     endFloor:ret.endfloor,
                     cardAmount:ret.cardamount,   
                     incValue:ret.incvalue,},		 			 
		  	height:600,
		  	width:1000,
		   	returnFun:function(data){
				var id = $("#dataTable_workType").jqGrid("getGridParam","selrow");
				Global.JqGrid.delRowData("dataTable_workType",id);
				if(data){
					$.each(data,function(k,v){
						var json = {
							beginfloor:v.beginFloor,
		                    endfloor:v.endFloor,
		                    cardamount:v.cardAmount,   
		                    incvalue:v.incValue,		                    		                   		                   
						};
						Global.JqGrid.addRowData("dataTable_workType",json);
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
$(function(){
    if(($.trim($("#m_umState").val())=="C")||($.trim($("#m_umState").val())=="B")||($.trim($("#m_umState").val())=="V")){
		 var Span1=$("#addfloor");
		 var Span2=$("#updatefloor");
		 var Span3=$("#delfloor");
		 Span1.css("display","none");
		 Span2.css("display","none");
		 Span3.css("display","none");		
	}  	
	var lastCellRowId;
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	/**初始化表格*/
	var gridOption ={			
			//url:"${ctx}/admin/CarryRule/goJqGridDetailadd?no="+"${carryRule.no }",	
            styleUI: 'Bootstrap',
			height:300,			
			colModel : [		
			  {name : 'pk',index : 'pk',width : 70,label:'pk',sortable : true,align : "left",frozen: true,hidden:true},
		      {name : 'prjrole',index : 'prjrole',width : 70,label:'工程角色',sortable : true,align : "left",frozen: true,hidden:true},
		      {name : 'worktype12',index : 'worktype12',width : 110,label:'工种分类12',sortable : true,align : "left",frozen: true,hidden:true},
		      {name : 'prjroledescr',index : 'prjroledescr',width : 70,label:'工程角色',sortable : true,align : "left",frozen: true,hidden:true},
		      {name : 'worktype12descr',index : 'worktype12descr',width : 110,label:'工种分类12',sortable : true,align : "left",frozen: true},
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 60,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 60,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 60,label:'操作日志',sortable : true,align : "left"}		     
			     
            ], 
			beforeSelectRow:function(id){
				setTimeout(function(){
			    relocate(id,"dataTable_workType");
				},100)
			},
	  		beforeSaveCell:function(rowId,name,val,iRow,iCol){
				lastCellRowId = rowId;
			},
 		}; 	     
       //初始化送货申请明细
	   Global.JqGrid.initEditJqGrid("dataTable_workType",gridOption);
	   if("<%=czylb %>"!="1"){
            $("#dataTable_workType").jqGrid('hideCol', "itemsumcost");
            $("#dataTable_workType").jqGrid('hideCol', "projectothercost");
            $("#dataTable_workType").jqGrid('hideCol', "sumcost");
            $("#dataTable_workType").jqGrid('hideCol', "sumprojectamount");
         }              
});
</script>		
		<!--query-form-->
<div class="body-box-list" style="margin-top: 0px;">
	<div class="pageContent">
			 <div class="panel panel-system" >
			   <div class="panel-body" >
			     <div class="btn-group-xs" >
			      	<button type="button" class="btn btn-system " id="ksaddwork" >快速添加</button>
					<button type="button" class="btn btn-system " id="delworktype12" >删除</button>
			     </div>
			  	</div>
			</div>				
		<table id="dataTable_workType" style="overflow: auto;"></table>
	</div>
</div>	
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>



