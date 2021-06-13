<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
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
<script type="text/javascript"> 
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
    
    
});
		
</script>	
</head>
<body>
	<div class="body-box-form">
			<!-- panelBar -->
			<div class="panel panel-system" >
				<div class="panel-body" >
					<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="arrDetail">
							<span>到货明细</span></button>
						<button type="button" class="btn btn-system " id="purchGZMX">
							<span>采购跟踪明细</span></button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
			</div>
			</div>
			</div>
			<div class="infoBox" id="infoBoxDiv"></div>
  			<div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
								<li>
								<label><span class="required">*</span> 采购订单号</label>
									<input type="text" id="no" name="no" style="width:160px;" value="${purchase.no }" readonly="readonly"/>
								</li>
								<li>
								<label>单据状态</label>
									<house:xtdmMulit id="status" dictCode="PURCHSTATUS"  selectedValue="${purchase.status}" ></house:xtdmMulit>                     
								</li>
								<li>
								<label>采购类型</label>
									<house:xtdm id="type" dictCode="PURCHTYPE"  value="${purchase.type }" disabled="true"></house:xtdm>
								</li>
								<li>
								<label>下单员</label>
									<input type="text" id="applyMan" name="applyMan" style="width:160px;" value="${purchase.applyMan }"/> 
								</li>
								<li>
								<label>材料类型1</label>
									<select id="itemType1" name="itemType1" style="width: 160px;" disabled="disabled"  >
									</select>
								</li>
								<li>
								<label>供应商编号</label>
									<input type="text" id="supplier" name="supplier" style="width:160px;" value="${purchase.supplier }"/>
								</li>
								<li>
								<label>采购日期</label>
									<input type="text" id="date" name="date" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${purchase.date}' pattern='yyyy-MM-dd'/>" />
								</li>
								<li>
								<label>到货日期</label>
									<input type="text" id="arriveDate" name="arriveDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="${purchase.arriveDate }" />
								</li>
								<li>
								<label>仓库编号</label>
									<input type="text" id="whcode" name="whcode" style="width:160px;" value="${purchase.whcode }"/>
								</li>
								<li>
								<label>客户编号</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;" value="${purchase.custCode }"/>
								</li>
								<li>
								<label>楼盘</label>
									<input type="text" id="address" name="address" style="width:160px;" value="${purchase.address }" readonly="true"/>
								</li>
								<li>
								<label>档案号</label>
									<input type="text" id=documentNo name="documentNo"  style="width:160px;" value="${purchase.documentNo } "readonly= 'readonl' />
								</li>
								<li>
								<label>其他费用</label>
									<input type="text" id="otherCost" name="otherCost"  style="width:160px;" value="${purchase.otherCost }" />
								</li>
								<li>
								<label>其他费用调整</label>
									<input type="text" id="otherCostAdj" name="otherCostAdj"  style="width:160px;" value="${purchase.otherCostAdj } " readonly= 'readonl' />
								</li>
								<li>
								<label>材料总价</label>
									<input type="text" id="amount" name="amount"  style="width:160px;" value="${purchase.amount }" readonly= 'readonl' />
								</li>
								<li>
								<label>已付定金</label>
									<input type="text" id="firstAmount" name="firstAmount"  style="width:160px;" value="${purchase.firstAmount }" readonly='readonly'/>
								</li>
								<li>
								<label>已付到货款</label>
									<input type="text" id="secondAmount" name="secondAmount" style="width:160px;" value="${purchase.secondAmount }" readonly='readonly'/>
								</li>
								<li>
								<label> 应收余款</label>
									<input type="text" id="remainAmount" name="remainAmount"  style="width:160px;" value="${purchase.remainAmount }"  readonly='readonly'/>
								</li>
								<li>
								<label>是否结账</label>
									<house:xtdm id="isCheckOut" dictCode="YESNO"  value="${purchase.isCheckOut}" disabled='true'></house:xtdm>                     
								</li>
								<li>
								<label>结账单号</label>
									<input type="text" id="checkOutNo" name="checkOutNo" style="width:160px;" value="${purchase.checkOutNo }" readonly='readonly'/>
								</li>
								<li>
								<label>销售单号</label>
									<input type="text" id="SINo" name="SINo" style="width:160px;" value="${purchase.sino }"/>
								</li>
								<li>
								<label>项目经理</label>
									<input type="text" id="projectMan" name="projectMan"  style="width:160px;" value="${purchase.projectMan }" readonly='readonly'/>
								</li>
								<li>
								<label>电话号码</label>
									<input type="text" id="phone" name="phone"  style="width:160px;" value="${purchase.phone }" readonly='readonly'/>
								</li>
								<li>
								<label>配送地点</label>
									<house:xtdm id="delivType" dictCode="PURCHDELIVTYPE"  value="${purchase.delivType }" disabled='true'></house:xtdm>                     
								</li>
								<li>
								<label>原采购单号</label>
									<input type="text" id="oldPUNo" name="oldPUNo" style="width:160px;" value="${purchase.oldPUNo }"/>
								</li>
								<li>
								<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="2">${purchase.remarks }</textarea>
								</li>
							</ul>	
				</form>
				</div>
			</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" > 
		        <li class="active"><a href="#tab_view" data-toggle="tab">采购单明细</a></li>
		        <li class=""><a href="#tab_itemAppDetail" data-toggle="tab">领料单明细</a></li>
		        <li class=""><a href="#tab_payDetail" data-toggle="tab">付款明细</a></li>
		        <li class=""><a href="#tab_otherCostDetail" data-toggle="tab">其他费用明细</a></li>
		    </ul> 
		    <div class="tab-content">  
		        <div id="tab_view"   class="tab-pane fade in active"> 
		         	<jsp:include page="tab_view.jsp"></jsp:include>
		        </div> 
		        <div id="tab_itemAppDetail"  class="tab-pane fade "> 
		         	<jsp:include page="tab_itemAppDetail.jsp"></jsp:include>
		        </div> 
		        <div id="tab_payDetail"  class="tab-pane fade "> 
		         	<jsp:include page="tab_payDetail.jsp"></jsp:include>
		        </div>
		        <div id="tab_otherCostDetail"  class="tab-pane fade "> 
		         	<jsp:include page="tab_otherCostDetail.jsp"></jsp:include>
		        </div> 
		    </div>
		</div>
</div>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>
 <script type="text/javascript">
$("#tabs").tabs();
		$("#whcode").openComponent_wareHouse();
		$("#whcode").setComponent_wareHouse({showValue:'${purchase.whcode}',showLabel:'${purchase.WHCodeDescr}',readonly: true});
		$("#applyMan").openComponent_employee();
		$("#applyMan").setComponent_employee({showValue:'${purchase.applyMan}',showLabel:'${purchase.applyManDescr}',readonly: true});
		$("#supplier").openComponent_supplier();
		$("#supplier").setComponent_supplier({showValue:'${purchase.supplier}',showLabel:'${purchase.supDescr}',readonly: true});
	/**初始化表格*/
	$(function(){
		if('${costRight}'=='0'){
			document.getElementById('amount').setAttribute('type','password') ;
			document.getElementById('firstAmount').setAttribute('type','password') ;
			document.getElementById('secondAmount').setAttribute('type','password') ;
			document.getElementById('remainAmount').setAttribute('type','password') ;
		}
	
		if('${purchase.type}'=='S'){
			$("tbody tr:eq(12) td:lt(2)").css("display","none");
		}
		if('${purchase.delivType}'=='1'){
			$("tbody tr:eq(9) td:lt(4)").css("display","none");
		}
	
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1");
		var dataSet = {
		firstSelect:"itemType1",
		firstValue:'${purchase.itemType1}',
	};
		Global.LinkSelect.setSelect(dataSet);
	
	//延期
	$("#purchGZMX").on("click",function(){
		var ret = selectDataTableRow();
        	  if (ret) {	
            	Global.Dialog.showDialog("purchGZMX",{ 
             	  title:"采购跟踪明细",
             	  url:"${ctx}/admin/purchase/goViewGZ",
             	  postData:{no:'${purchase.no }'},
             	  height: 500,
             	  width:800,
             	  returnFun:goto_query
             	});
           } else {
           	art.dialog({
       			content: "请选择一条记录"
       		});
           }
	});
	
	//明细
	$("#arrDetail").on("click",function(){
	
		var ret = selectDataTableRow();
        	  if (ret) {	
            	Global.Dialog.showDialog("arrDetail",{ 
             	  title:"到货明细",
             	  url:"${ctx}/admin/purchase/goViewArrDetail",
             	  postData:{no:'${purchase.no }'},
             	  height: 650,
             	  width:900,
             	  returnFun:goto_query
             	});
           } else {
	           	art.dialog({
	       			content: "请选择一条记录"
	       		});
           	}
	});
	
	//查看
	$("#view").on("click",function(){
		var ret= selectDataTableRow('dataTable');
	 	if(ret){
			Global.Dialog.showDialog("AddView",{
				title:"查看采购信息",
				url:"${ctx}/admin/purchase/goAddView",
				postData:{puno:ret.puno,itcode:ret.itcode,qtyCal:ret.qtycal,unitPrice:ret.unitprice,amount:ret.amount,itdescr:ret.itdescr,
						  remarks:ret.remarks,fromPage:"${purchase.fromPage}",type:"${purchase.type }",markup: ret.markup},
				height:700,
				width:1000,
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});		
		}
	});
	if('${purchase.type}'=='R'){
		document.getElementById('jqgh_dataTable_qtycal').innerHTML="退回数量";
	}
	if("${purchase.fromPage}"=="supplierCheck"){
		if("${purchase.isService}" != ""){
			$("#isService").parent().removeAttr("hidden");
		}
		if("${purchase.delivType }" == "2"){
			$("#whcode").openComponent_wareHouse();
			$("#whcode").setComponent_wareHouse({
				showValue:"不填",
				readonly: true,
				valueOnly:true
			});
		}
		if("${purchase.type}" == "S"){
			$("#oldPUNo").parent().attr("hidden", true);
		}

		if("${purchase.delivType}" == "1"){
			$("#projectMan").parent().attr("hidden", true);
			$("#phone").parent().attr("hidden", true);
		}
	}
});

</script>		
  		
  		
  		
	</body>
</html>











