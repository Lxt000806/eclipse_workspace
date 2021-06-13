<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
String czylb=uc.getCzylb();
String czybh="";
if("2".equals(czylb)) czybh=uc.getCzybh();
%>
<!DOCTYPE html>
<html>
<head>
	<title>查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
//tab分页
$(function(){    
	$("#id_detail>.tab_container .tab_content").hide(); 
    $("#id_detail>ul.tabs li:first").addClass("active").show();  
    $("#id_detail>.tab_container .tab_content:first").show();
    $("#id_detail>ul.tabs li").click(function() { 
    	$("#id_detail>ul.tabs li").removeClass("active");  
        $(this).addClass("active");  
        $("#id_detail>.tab_container .tab_content").hide(); 
        var activeTab = $(this).find("a").attr("href"); 
        $(activeTab).fadeIn();
        //去掉最后一个竖线
       if($(this).find("a").attr('href')=='#tab_totalByBrand'){
        	if ($(this).attr("class")=='active'){
        		$(this).css("border-right","none");
        	}
        }else{
        	$("#id_detail>ul.tabs li").css("border-right","");
        }
        $("#id_detail>.tab_container .tab_content").find(".tab_content:first").show();
        return false;
    });
    
    chooseSupplierOrWarehouse($("#sendType").get(0));
    
    $("#supplCode").openComponent_supplier({showValue:'${carryRule.supplCode}',showLabel:'${carryRule.supplDescr}',condition:{ReadAll:"1"}}); 
    
    $("#wHCode").openComponent_wareHouse({
        showValue: '${carryRule.wHCode}'
    });
    $("#openComponent_wareHouse_wHCode").blur();
    
});

function chooseSupplierOrWarehouse(element) {
    var supplCodeLi = $("#supplCodeLi");
    var wHCodeLi = $("#wHCodeLi");

    if (element.value === "1") {
        supplCodeLi.show();
        wHCodeLi.hide();
    } else if (element.value === "2") {
        supplCodeLi.hide();
        wHCodeLi.show();
    } else {
        supplCodeLi.hide();
        wHCodeLi.hide();
    }
}

</script>
</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="V"/>
				<ul class="ul-form">
					<li>	
						<label><span class="required">*</span>编号</label>
						<input type="text" id="no" name="no"   value="${carryRule.no}" readonly="true" />
					</li>
					<li>
						<label><span class="required">*</span>搬运类型</label>
						<house:xtdm id="carryType" dictCode="CarType" value="${carryRule.carryType} "></house:xtdm>													
					</li>
					<li>
						<label><span class="required">*</span>材料类型1</label>
						<select id="itemType1" name="itemType1" ></select>
					</li>
					<li>
						<label><span class="required">*</span>材料类型2</label>
						<select id="itemType2" name="itemType2"   ></select>
					</li>
					<li>
						<label>距离类型</label>
						<house:xtdm id="DistanceType" dictCode="DistanceType" value="${carryRule.distanceType} "></house:xtdm>													
					</li>
					<li class="form-validate">
						<label>计算方式</label>
						<house:xtdm id="calType" dictCode="CarryCalType" value="${carryRule.calType}" disabled="true"></house:xtdm>													
					</li>
					<li class="form-validate">
						<label>发货类型</label>
						<house:xtdm id="sendType" dictCode="ITEMAPPSENDTYPE" value="${carryRule.sendType}"
						            onchange="chooseSupplierOrWarehouse(this)"></house:xtdm>													
					</li>
					<li id="supplCodeLi" class="form-validate" style="display:none;">
						<label>供应商</label>
						<input type="text" id="supplCode" name="supplCode" value="${carryRule.supplCode}"  />
					</li>
					<li id="wHCodeLi" class="form-validate" style="display:none;">
                        <label>仓库</label>
                        <input type="text" id="wHCode" name="wHCode" value="${carryRule.wHCode}" />
                    </li>
					<li>
						<label class="control-textarea" >备注：</label>
						<textarea id="remarks" name="remarks">${carryRule.remarks }</textarea>
					</li>
					<li hidden="true">
						<span class="required">*</span>11</label>
						<input type="text" id="fieldJson" name="fieldJson"   value="" />
					</li>
				</ul>
			</form>
			</div>
		</div>
		<div class="infoBox" id="infoBoxDiv"></div>
				<div class="infoBox" id="infoBoxDiv"></div>
		<div  class="container-fluid" >  
		    <ul class="nav nav-tabs" >
		        <li id="tabitemAppSend" class="active"><a href="#tab_itemAppSend" data-toggle="tab">匹配楼层</a></li>  
		        <li id="tabsalesInvoice" class=""><a href="#tab_salesInvoice" data-toggle="tab">匹配材料</a></li>
		    </ul>  
		    <div class="tab-content">  
				<div id="tab_itemAppSend"  class="tab-pane fade in active"> 
			    	<jsp:include page="CarryRule_addfloor.jsp"></jsp:include>
				</div>  
				<div id="tab_salesInvoice"  class="tab-pane fade "> 
		         	<jsp:include page="carryRuleItem3_add.jsp"></jsp:include>
				</div>
			</div>	
		</div>		

<script type="text/javascript">  

//查看 楼层信息
$("#viewfloor").on("click",function(){
	var ret = selectDataTableRow("dataTable_itemAppSend");
    if (ret) {
    	Global.Dialog.showDialog("itemSetView",{
			title:"查看楼层",
		  	url:"${ctx}/admin/CarryRule/goaddView",	
		  	postData:{
		  		beginFloor:ret.beginfloor,
                endFloor:ret.endfloor,
                cardAmount:ret.cardamount,   
                incValue:ret.incvalue,},		 			 
		  	height:600,
		  	width:1000,
		   	returnFun:function(data){
				var id = $("#dataTable_itemAppSend").jqGrid("getGridParam","selrow");
				Global.JqGrid.delRowData("dataTable_itemAppSend",id);
				if(data){
					$.each(data,function(k,v){
						var json = {
							beginfloor:v.beginFloor,
		                    endfloor:v.endFloor,
		                    cardamount:v.cardAmount,   
		                    incvalue:v.incValue,		                    		                   		                   
						};
						Global.JqGrid.addRowData("dataTable_itemAppSend",json);
					});
				}
			}   
		});
    } else {
    	art.dialog({    	
			content: "请选择一条记录"
		});
    }
});	

//查看材料明细
$("#viewitem").on("click",function(){
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
});

</script>
</body>
</html>
