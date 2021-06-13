<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>材料列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
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

function mingxibiao(){
	var ids = $("#dataTable").getDataIDs();
	if((ids || ids.length != 0)){
		$("#itemType1").attr("disabled","disabled"); 
        $("#itemType2").attr("disabled","disabled"); 
        $("#carryType").attr("disabled","disabled"); 
        $("#SendType").attr("disabled","disabled"); 
        $("#DistanceType").attr("disabled","disabled");        			
	}		
}
function add(){
    var now= new Date();
    var year=now.getFullYear();
    var month=now.getMonth();
    var date=now.getDate();
    var hours=now.getHours();
    var min=now.getMinutes();
    var s=now.getSeconds();
    var time=year+"-"+(month+1)+"-"+date+" "+hours+":"+min+":"+s;        	
	var no=$("#no").val();
	var itemtype1=$("#itemType1").val();
	var itemtype2=$("#itemType2").val();	
	var itemcode1 = Global.JqGrid.allToJson("dataTable","itemtype3");
	Global.Dialog.showDialog("carryRuleItemadd",{			
		title:"添加材料明细",		
		url:"${ctx}/admin/CarryRule/goItemadd",
		postData:{no:no,itemType1:itemtype1,itemType2:itemtype2},		 
		height: 600,
		width:1000,
		returnFun: function(data){       	
       		if(data){    	 	
           		$.each(data,function(k,v){
               	var json = {               	 	             	 
                	itemtype3:v.itemType3,                                     
                   	itemtype3descr:v.itemType3Descr,
                    lastupdate: time,
                    lastupdatedby:"${czy }",
                    expired: "F",
                    actionlog: "ADD",
                    lastupdateby:v.lastupdateby                                                                            
                } ;               
                Global.JqGrid.addRowData("dataTable",json);
            });             
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
	var no=$("#no").val();
	var itemType1=$("#itemType1").val();
	var itemtype2=$("#itemType2").val();
    if (ret) {
    	Global.Dialog.showDialog("carryRuleUpdate",{
			title:"修改材料页面",
		    url:"${ctx}/admin/CarryRule/goitemUpdate",	
		    postData:{no:no,itemType1:itemType1,itemType2:itemtype2,itemType3:ret.itemtype3,itemType3Descr:ret.itemtype3descr},	                 			 		 
		    height:600,
		    width:1000,
		    returnFun:function(data){
				var id = $("#dataTable").jqGrid("getGridParam","selrow");
				Global.JqGrid.delRowData("dataTable",id);						
				if(data){
					$.each(data,function(k,v){
						var json = {
							itemtype3:v.itemType3,       
		                    itemtype3descr:v.itemcodedescr,
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
function view(){
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
		                    itemtype3descr:v.itemcodedescr,
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
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap',
			colModel : [		
			  {name : 'pk',index : 'pk',width : 70,label:'pk',sortable : true,align : "left",frozen: true,hidden:true},
		      {name : 'no',index : 'no',width : 70,label:'编号',sortable : true,align : "left",frozen: true,hidden:true},
		      {name : 'itemtype3',index : 'itemtype3',width : 80,label:'编号',sortable : true,align : "left",frozen: true},		 
		      {name : 'itemtype3descr',index : 'itemtype3descr',width : 200,label:'材料类型3',sortable : true,align : "left",frozen: true},	
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 60,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 60,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 60,label:'操作日志',sortable : true,align : "left"}
            ]
		});
	});  
});
</script>
</script>
</head>
    
<body  onload="mingxibiao()">
	<div class="body-box-list" id="infoBoxDiv">
		<div class="query-form">
			<form action="" method="post" id="page_form" >
				<input type="hidden" id="expired" name="expired" value="${itemSet.expired }" />
				<input type="hidden" name="jsonString" value="" />
				<table cellpadding="0" cellspacing="0" width="100%">
						<col  width="82" />
						<col  width="35.4%"/>
						<col  width="82"/>
						<col  width="33.3%"/>
						<col  width="82"/>
						<col  width="32.3%"/>
					<tbody>
						<div class="panelBar">
							<ul>
								<li>
									<a href="javascript:void(0)" class="a1" id="saveBtn">
										<span>保存</span>
									</a>
								</li>
								<li id="closeBut" onclick="closeWin(false)">
									<a href="javascript:void(0)" class="a2">
										<span>关闭</span>
									</a>
								</li>
								<li class="line"></li>
							</ul>
							<div class="clear_float"> </div>
						</div>
						<tr class="td-btn">
							<td class="td-label" ><span class="required">*</span>编号</td>
							<td class="td-value">
								<input type="text" id="no" name="no" style="width:160px;"  value="${carryRule.no }"  readonly="true"/>
							</td>
							<td class="td-label"><span class="required">*</span>搬运类型</td>
							<td class="td-value" colspan="3">								
								<house:xtdm id="carryType" dictCode="CarType" value="${carryRule.carryType} "></house:xtdm>													
							</td>																													
						</tr>
						<tr>
							<td class="td-label"><span class="required">*</span>送货类型</td>
							<td class="td-value">								
								<house:xtdm id="SendType" dictCode="SendType" value="${carryRule.sendType} "></house:xtdm>													
							</td>
							<td class="td-label">距离类型</td>
							<td class="td-value" colspan="3">								
								<house:xtdm id="DistanceType" dictCode="DistanceType" value="${carryRule.distanceType} "></house:xtdm>													
							</td>	
						</tr>	
						<tr>	
							<td class="td-label"><span class="required">*</span>材料类型1</td>
							<td class="td-value" >
								<select id="itemType1" name="itemType1" style="width: 166px;"  value="${carryRule.itemType1} "></select>
							</td>															
							
							<td class="td-label"><span class="required">*</span>材料类型2</td>
							<td class="td-value"  colspan="3">
								<select id="itemType2" name="itemType2" style="width: 166px;"  value="${carryRule.itemType2} "></select>
							</td>						
						</tr>	
						
						<tr>
							<td class="td-label" >备注</td>
							<td class="td-value" colspan="10">
								<textarea rows="4"  id="remarks" name="remarks" ${flag=='doc'?'readonly':'' }>${carryRule.remarks }</textarea>
							</td>
						</tr>																																											
					</tbody>
				</table>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<!--panelBar-->
			<div class="panelBar">
            	<ul>
            	<house:authorize authCode="ItemSet_ADD">
                	<li>
                    	<a href="javascript:void(0)" class="a1" onclick="add() ">
					       <span>添加</span>
						</a>	
                    </li>
                </house:authorize>              
				<house:authorize authCode="ItemSet_UPDATE">
					<li>
					<a href="javascript:void(0)" class="a1" onclick="update()">
						<span>编辑</span>
					</a>
					</li>
				</house:authorize>						
                <house:authorize authCode="ItemSet_DELETE">
                	<li>
						<a href="javascript:void(0)" class="a2" id="delDetail">
							<span>删除</span>
						</a>
					</li>
                 </house:authorize>
                 <house:authorize authCode="ItemSet_VIEW">
                 	<li>
					<a href="javascript:void(0)" class="a1" onclick="view()">
						<span>查看</span> 
					</a>
					</li>
				</house:authorize>	
							
                    <li class="line"></li>
                </ul>
				<div class="clear_float"> </div>
			</div><!--panelBar end-->
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
	var param= Global.JqGrid.allToJson("dataTable");
	Global.Form.submit("page_form","${ctx}/admin/CarryRule/doitemSave",param,function(ret){
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


