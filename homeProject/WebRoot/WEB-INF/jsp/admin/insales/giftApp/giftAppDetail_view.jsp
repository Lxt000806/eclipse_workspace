<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>

<!DOCTYPE HTML>
<html>
<head>
<title>礼品领用明细-新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_giftToken.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">

$(function(){
    $("#itemCode").openComponent_item({showLabel:"${giftAppDetail.itemDescr}",showValue:"${giftAppDetail.itemCode}", readonly:true});
    $("#tokenPk").openComponent_giftToken({showValue:"${giftAppDetail.tokenPk}",showLabel:"${giftAppDetail.tokenNo}", readonly:true}); 
    if ("${giftAppDetail.giftAppType}"=='2'){
    	$("#useDiscAmount_li").show();
		$("#totalRemainDisc_li").show();
		getHasRemainDisc();
	}                             
});
function getHasRemainDisc() {
	$.ajax({
		url:"${ctx}/admin/discAmtTran/getMaxDiscAmount",
		type:'post',
		data:{custCode:"${giftAppDetail.custCode}"},
		dataType:'json',
		cache:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": '出错~'});
		},
		success:function(obj){
			if (obj){ 
				var remainDisc = myRound(obj.DesignerMaxDiscAmount) 
								+ myRound(obj.DirectorMaxDiscAmount) 
					+ myRound(obj.ExtraDiscChgAmount) + myRound(obj.UsedDiscAmount) + myRound(obj.LpExpense);
				// 前端剩余风险金 = 设计师风险金 + 已使用风险金(使用风险金在数据库填负数)
				var designRemainRiskFund = myRound(obj.DesignRiskFund) + myRound(obj.UsedRiskFund);
				//总优惠余额
				var hasRemainDisc = myRound(remainDisc) + myRound(designRemainRiskFund);
				$("#hasRemainDisc").val(hasRemainDisc);		
				getTotalRemainDisc();
			}						
		}
	});
};

function getTotalRemainDisc() {
	if($("#useDiscAmount").val()=="1" && $("#itemCode").val()!="" && $("#qty").val()!="" ){
		 $("#totalRemainDisc").val( 
			myRound(parseFloat($("#hasRemainDisc").val()) 
				  - parseFloat($("#qty").val()) * parseFloat($("#cost").val())
				  - parseFloat("${giftAppDetail.hasGiftUseDisc}")
		    )
		 );
	}else{
		
		 $("#totalRemainDisc").val(myRound(parseFloat($("#hasRemainDisc").val())
				-parseFloat("${giftAppDetail.hasGiftUseDisc}"))
		 ); 	
	}	
}

</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="page_form" class="form-search">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="${giftApp.m_umState}"/>
				<input type="hidden" name="hasRemainDisc" id="hasRemainDisc" value="0"/>
				<ul class="ul-form">
						<li>
							<label ><span class="required">*</span>礼品编号</label>
							<input type="text" id="itemCode" name="itemCode" style="width:160px;" value="${giftAppDetail.itemCode}" onchange="itemCodeChange"/>                    
						</li>
						<li>
							<label>成本价</label>
						    <input type="text" id="cost" name="cost" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"  value="${giftAppDetail.cost}" readonly="true"/>
							<span>元</span>                  
						</li>
						<li>
							<label ><span class="required">*</span>券号</label>
							<input type="text" id="tokenPk" name="tokenPk" style="width:160px;"  value="${giftAppDetail.tokenPk}"/>                   
						</li>
						<li>
							<label ><span class="required">*</span>数量</label>
						    <input type="text" id="qty" name="qty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"   value="${giftAppDetail.qty}" onchange="getTotalRemainDisc()"/>
						    <input type="text" id="uom" name="uom" value="${giftAppDetail.uom}" style="width:50px; outline:none; background:transparent; border:none" readonly="true"/>
						</li>
						<li>
							<label ><span class="required">*</span>已领数量</label>
						    <input type="text" id="sendQty" name="sendQty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"  value="${giftAppDetail.sendQty}"/>	
						    <input type="text" id="uom1" name="uom1" value="${giftAppDetail.uom}" style="width:50px; outline:none; background:transparent; border:none" readonly="true"/>
						</li>
						<li>
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" value="${giftAppDetail.remarks}" ></textarea>
					   </li>
					   <li hidden="true" id="useDiscAmount_li" class="form-validate">
	                        <label>使用优惠额度</label>
	                        <house:xtdm id="useDiscAmount" dictCode="YESNO" value="${giftAppDetail.useDiscAmount}" onchange="getTotalRemainDisc()" style="width:160px;"></house:xtdm>
                   		</li>
                   		<li id="totalRemainDisc_li" hidden="true">
							<label>总优惠余额</label>
							<input type="text" id="totalRemainDisc" name="totalRemainDisc" readonly="true" style="width:160px;"/>
						</li>
					   <li hidden="true">
							<label>礼品名称</label>
							<input type="text" id="itemDescr" name="itemDescr"  value="${giftAppDetail.itemDescr}"/>
					   </li>
					   <li hidden="true">
					         <label><span class="required">*</span>单价</label>
							<input type="text" id="price" name="price" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"  value="${giftAppDetail.price}"/>
							 <span>元</span>  
					    </li>
						<li hidden="true">
							<label>最后修改时间</label>
							<input type="text" id="lastUpdate" name="lastUpdate"  value="<fmt:formatDate value='${giftAppDetail.lastUpdate}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
					   </li>
							
						<li hidden="true">
							<label>最后修改人</label>
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy"  value="${giftAppDetail.lastUpdatedBy}"/>
					   </li>
						
					</ul>
			</form>
		</div>
	</div>			
</div>
</body>
</html>
