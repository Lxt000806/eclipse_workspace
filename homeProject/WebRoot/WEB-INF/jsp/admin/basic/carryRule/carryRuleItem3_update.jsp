<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>ๆๆๅ่กจ</title>
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
		title:"ๆทปๅ?ๆๆๆ็ป",		
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
			title:"ไฟฎๆนๆๆ้กต้ข",
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
			content: "่ฏท้ๆฉไธๆก่ฎฐๅฝ"
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
			title:"ๆฅ็ๆๆ้กต้ข",
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
			content: "่ฏท้ๆฉไธๆก่ฎฐๅฝ"
		});
    }
}
/**ๅๅงๅ่กจๆ?ผ*/
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
	        //ๅๅงๅ่กจๆ?ผ
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/CarryRule/goJqGridItem3?no="+"${carryRule.no }",				
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap',
			colModel : [		
			  {name : 'pk',index : 'pk',width : 70,label:'pk',sortable : true,align : "left",frozen: true,hidden:true},
		      {name : 'no',index : 'no',width : 70,label:'็ผๅท',sortable : true,align : "left",frozen: true,hidden:true},
		      {name : 'itemtype3',index : 'itemtype3',width : 80,label:'็ผๅท',sortable : true,align : "left",frozen: true},		 
		      {name : 'itemtype3descr',index : 'itemtype3descr',width : 200,label:'ๆๆ็ฑปๅ3',sortable : true,align : "left",frozen: true},	
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'ๆๅไฟฎๆนๆถ้ด',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 60,label:'ไฟฎๆนไบบ',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 60,label:'ๆฏๅฆ่ฟๆ',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 60,label:'ๆไฝๆฅๅฟ',sortable : true,align : "left"}
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
										<span>ไฟๅญ</span>
									</a>
								</li>
								<li id="closeBut" onclick="closeWin(false)">
									<a href="javascript:void(0)" class="a2">
										<span>ๅณ้ญ</span>
									</a>
								</li>
								<li class="line"></li>
							</ul>
							<div class="clear_float"> </div>
						</div>
						<tr class="td-btn">
							<td class="td-label" ><span class="required">*</span>็ผๅท</td>
							<td class="td-value">
								<input type="text" id="no" name="no" style="width:160px;"  value="${carryRule.no }"  readonly="true"/>
							</td>
							<td class="td-label"><span class="required">*</span>ๆฌ่ฟ็ฑปๅ</td>
							<td class="td-value" colspan="3">								
								<house:xtdm id="carryType" dictCode="CarType" value="${carryRule.carryType} "></house:xtdm>													
							</td>																													
						</tr>
						<tr>
							<td class="td-label"><span class="required">*</span>้่ดง็ฑปๅ</td>
							<td class="td-value">								
								<house:xtdm id="SendType" dictCode="SendType" value="${carryRule.sendType} "></house:xtdm>													
							</td>
							<td class="td-label">่ท็ฆป็ฑปๅ</td>
							<td class="td-value" colspan="3">								
								<house:xtdm id="DistanceType" dictCode="DistanceType" value="${carryRule.distanceType} "></house:xtdm>													
							</td>	
						</tr>	
						<tr>	
							<td class="td-label"><span class="required">*</span>ๆๆ็ฑปๅ1</td>
							<td class="td-value" >
								<select id="itemType1" name="itemType1" style="width: 166px;"  value="${carryRule.itemType1} "></select>
							</td>															
							
							<td class="td-label"><span class="required">*</span>ๆๆ็ฑปๅ2</td>
							<td class="td-value"  colspan="3">
								<select id="itemType2" name="itemType2" style="width: 166px;"  value="${carryRule.itemType2} "></select>
							</td>						
						</tr>	
						
						<tr>
							<td class="td-label" >ๅคๆณจ</td>
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
					       <span>ๆทปๅ?</span>
						</a>	
                    </li>
                </house:authorize>              
				<house:authorize authCode="ItemSet_UPDATE">
					<li>
					<a href="javascript:void(0)" class="a1" onclick="update()">
						<span>็ผ่พ</span>
					</a>
					</li>
				</house:authorize>						
                <house:authorize authCode="ItemSet_DELETE">
                	<li>
						<a href="javascript:void(0)" class="a2" id="delDetail">
							<span>ๅ?้ค</span>
						</a>
					</li>
                 </house:authorize>
                 <house:authorize authCode="ItemSet_VIEW">
                 	<li>
					<a href="javascript:void(0)" class="a1" onclick="view()">
						<span>ๆฅ็</span> 
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
	if(!$("#page_form").valid()) {return false;}//่กจๅๆ?ก้ช
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

//ๅ?้ค
$("#delDetail").on("click",function(){
	var id = $("#dataTable").jqGrid("getGridParam","selrow");
	if(!id){
		art.dialog({content: "่ฏท้ๆฉไธๆก่ฎฐๅฝ่ฟ่กๅ?้คๆไฝ๏ผ",width: 200});
		return false;
	}
	Global.JqGrid.delRowData("dataTable",id);	
});
</script>	
</body>
</html>


