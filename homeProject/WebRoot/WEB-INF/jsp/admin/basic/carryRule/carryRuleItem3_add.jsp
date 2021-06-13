<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
function viewitem(){
	var ret = selectDataTableRow();		
	var no=$("#no").val();
	var itemType1=$("#itemType1").val();
	var itemtype2=$("#itemType2").val();
    if (ret) {
    	Global.Dialog.showDialog("carryRuleView",{
			title:"查看材料页面",
		  	url:"${ctx}/admin/CarryRule/goitemView",	
		  	postData:{no:no,itemType1:itemType1,itemType2:itemtype2,itemType3:ret.itemtype3,itemType3Descr:ret.itemtype3descr},	                 			 		 
		  	height:600,
		  	width:1000,
		   	returnFun:function(data){
				var id = $("#dataTable").jqGrid("getGridParam","selrow");
				Global.JqGrid.delRowData("dataTable",id);						
			  	if(data){
					$.each(data,function(k,v){
						var json = {
						   	itemtype3:v.itemcode,
		                   	itemtype3descr:v.itemType3Descr,
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

$(function() {
	if(($.trim($("#m_umState").val())=="B")||($.trim($("#m_umState").val())=="V")){	
		var Span1=$("#additem");
		var Span2=$("#updateitem");
		var Span3=$("#delitem");
		Span1.css("display","none");
		Span2.css("display","none");
		Span3.css("display","none");		
	}  	
$("#page_form").validate({
	rules: {				
			"itemType1": {
			validIllegalChar: true,
			required: true,
			maxlength: 10
			},
			"no": {
			validIllegalChar: true,
			required: true,
			maxlength: 10
			},
			"SendType": {
			validIllegalChar: true,
			required: true,
			maxlength: 10
			},
			"carryType": {
			validIllegalChar: true,
			required: true,
			maxlength: 10
			},
			"itemType2": {
			validIllegalChar: true,
			required: true,
			maxlength: 10
			},						
		}
	});
});


/**初始化表格*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2");
	var dataSet = {
		firstSelect:"itemType1",
		secondSelect:"itemType2",
		firstValue:'${carryRule.itemType1}',
		secondValue:'${carryRule.itemType2}',
		disabled:"true"
	};
	Global.LinkSelect.setSelect(dataSet);	

$(function(){
        //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/CarryRule/goJqGridItem3?no="+"${carryRule.no }",
		ondblClickRow: function(){
               	viewitem();
               },				
		height:$(document).height()-$("#content-list").offset().top-67,		
		styleUI: 'Bootstrap',	
		colModel : [		
		  {name : 'pk',index : 'pk',width : 80,label:'pk',sortable : true,align : "left",frozen: true,hidden:true},
	      {name : 'no',index : 'no',width : 80,label:'编号',sortable : true,align : "left",frozen: true,hidden:true},
	      {name : 'itemtype3',index : 'itemtype3',width : 100,label:'编号',sortable : true,align : "left",frozen: true},		 
	      {name : 'itemtype3descr',index : 'itemtype3descr',width : 220,label:'材料类型3',sortable : true,align : "left",frozen: true},	
	      {name : 'lastupdate',index : 'lastupdate',width : 220,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
	      {name : 'lastupdatedby',index : 'lastupdatedby',width : 80,label:'修改人',sortable : true,align : "left"},
	      {name : 'expired',index : 'expired',width : 80,label:'是否过期',sortable : true,align : "left"},
	      {name : 'actionlog',index : 'actionlog',width : 80,label:'操作日志',sortable : true,align : "left"},

           ]
	});
});  
});
</script>
             
<div class="body-box-form" style="margin-top: 0px;">
	<div class="pageContent">         
		<!--query-form-->
			<!--panelBar-->
			<div class="panel panel-system" >
			   <div class="panel-body" >
			     <div class="btn-group-xs" >
			      	<button type="button" class="btn btn-system " id="additem" >添加</button>
					<button type="button" class="btn btn-system " id="updateitem" >编辑</button>
					<button type="button" class="btn btn-system " id="delitem" >删除</button>
					<button type="button" class="btn btn-system " id="viewitem" >查看</button>	
			     </div>
			  	</div>
			</div>	
			<!--panelBar end-->
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
	</div>
</div>


