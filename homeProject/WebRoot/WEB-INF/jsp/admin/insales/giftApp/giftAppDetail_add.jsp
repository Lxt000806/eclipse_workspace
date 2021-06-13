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
//校验函数
$(function() {
	 $("#itemCode").openComponent_item({showLabel:"${giftAppDetail.itemDescr}",showValue:"${giftAppDetail.itemCode}",
 		condition:{itemType1:'LP',actNo :"${giftAppDetail.actNo}",
 		giftAppType :"${giftAppDetail.giftAppType}",readonly:"1"},
 		callBack: function (data) {
             validateRefresh('openComponent_item_itemCode');
             getData(data);
        }
 	});

    if ($.trim($("#itemCode").val())!=''){
		$("#tokenPk").openComponent_giftToken({showValue:"${giftAppDetail.tokenPk}",showLabel:"${giftAppDetail.tokenNo}" ,condition:{itemCode:"${giftAppDetail.itemCode}",custCode:"${giftAppDetail.custCode}" },
			callBack: function(data){$("#tokenNo").val(data["tokenno"]),$("#qty").val(data["qty"])}});    
	    $("#openComponent_giftToken_tokenPk").attr("readonly",true);
	}
	if ($.trim($("#giftAppType").val())!='2'){
	    $("#tokenPk").setComponent_giftToken({readonly: true})
	}else{
		$("#useDiscAmount_li").show();
		$("#totalRemainDisc_li").show();
		getHasRemainDisc();
	}

	if($.trim("${giftAppDetail.useDiscAmount}")==""){
		$("#useDiscAmount").val("0")
	}

	$("#dataForm").bootstrapValidator({
		excluded:[":disabled"],
		message: "This value is not valid",
        feedbackIcons: { /*input状态样式图片*/
			/* valid: "glyphicon glyphicon-ok",
			invalid: "glyphicon glyphicon-remove", */
			validating: "glyphicon glyphicon-refresh"
        },
        fields: {  
	    	qty: {
		        validators: { 
		            numeric: {
		            	message: '数量只能是数字' 
		            }
		        }
      		},
      		useDiscAmount: {
		        validators: { 
		        	notEmpty: { 
	            		message: '使用优惠额度不能为空'  
	            	}
		        }
	      	},
	      	openComponent_item_itemCode: {
	      		validators: {  
	            	 notEmpty: {  
	                 	message: '材料编号不能为空'  
	                 },
	                 remote: {
	     	            message: '',
	     	            url: '${ctx}/admin/item/getItem',
	     	            data: getValidateVal,  
	     	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	     	        }
	             }
	      	},
    	},
		submitButtons : 'input[type="submit"]' /*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
	
	$("#useDiscAmount").bind("input propertychange", function(){
		var totalCost = $("#qty").val()*$("#cost").val();
		var useDiscAmount = $(this).val();
		if(useDiscAmount > totalCost){
			art.dialog({
				content: "使用优惠额度不能超过成本总价",
			});
			$(this).val(totalCost);	
		}
		getHasRemainDisc();
	});
});
	
//保存
function save(){
    $("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	   
	var itemCode = $.trim($("#itemCode").val());	
	var qty =$.trim($("#qty").val());
	var price =$.trim($("#price").val());
	var sendQty =$.trim($("#sendQty").val());
	var tokenPk =$.trim($("#tokenPk").val());
	var totalRemainDisc=parseFloat($("#totalRemainDisc").val());
	var useDiscAmount=parseFloat($("#useDiscAmount").val());
	
	if (itemCode==""){
		art.dialog({content: "礼品编号不能为空",width: 200});
		return false;
	}
	if (qty==""){
		art.dialog({content: "数量不能为空",width: 200});
		return false;
	}
	if (price==""){
		art.dialog({content: "单价不能为空",width: 200});
		return false;
	}
	if (totalRemainDisc<0 && useDiscAmount != 0){
		art.dialog({content: "剩余优惠额度不能为负数"});
		return false;
	}
	
	var selectRows = [];
	var datas=$("#dataForm").jsonForm();
	selectRows.push(datas);
	Global.Dialog.returnData = selectRows;
	closeWin();
}	
	
function getData(data){
	if (!data) return;
	$('#remarks').val(data.remark);
	$('#price').val(data.price);
	$('#uom').val(data.uomdescr);
	$('#uom1').val(data.uomdescr);
	$('#itemDescr').val(data.descr);
	$('#cost').val(data.cost);
	if ($.trim($("#giftAppType").val())=='2'){
		$("#tokenPk").val(' ');  
	    $("#tokenPk").openComponent_giftToken({showValue:"${giftAppDetail.tokenPk}",showLabel:"${giftAppDetail.tokenNo}" ,
	    	condition:{itemCode:data.code,status:'1',custCode:"${giftAppDetail.custCode}",disabled:true},callBack: function(data){$("#tokenNo").val(data.tokenno),$("#qty").val(data["qty"])}}); 
		$("#openComponent_giftToken_tokenPk").attr("readonly",true);
	}
	if ("${giftAppDetail.custCode}"==''){
		$('#sendQty').val(0);
	}else{
		$.ajax({
			url:"${ctx}/admin/giftApp/getAjaxDetail",
			type:'post',
			data:{itemCode: data.code, custCode:"${giftAppDetail.custCode}"},
			dataType:'json',
			cache:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
			},
			success:function(obj){
				if (obj){
					$('#sendQty').val(obj.sendQty);
					getTotalRemainDisc();
				}
			}
		});
	}
}

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
	if( $("#itemCode").val()!="" && $("#useDiscAmount").val()!="" ){
		 $("#totalRemainDisc").val( 
			myRound(parseFloat($("#hasRemainDisc").val()) 
				  - parseFloat($("#useDiscAmount").val()) 
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
				<button type="button" class="btn btn-system "   id="saveBtn" onclick="save()">保存</button>
				<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin(false)">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="${giftAppDetail.m_umState}"/>
				<input type="hidden" name="giftAppType" id="giftAppType" value="${giftAppDetail.giftAppType}"/>
				<input type="hidden" name=hasRemainDisc id="hasRemainDisc" value="0"/>
				<ul class="ul-form">
						<li class="form-validate">
							<label ><span class="required">*</span>礼品编号</label>
							<input type="text" id="itemCode" name="itemCode" style="width:160px;" value="${giftAppDetail.itemCode}" onchange="itemCodeChange"/>                    
						</li >
						<li class="form-validate">
							<label>成本价</label>
						    <input type="text" id="cost" name="cost" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"  value="${giftAppDetail.cost}" readonly="true"/>
							 <span>元</span>                   
						</li>
						<li class="form-validate">
							<label >礼品券号</label>
						    <input type="text" id="tokenPk" name="tokenPk" style="width:160px;"  value="${giftAppDetail.tokenPk}" readonly="true"/>                   
						</li>
						<li class="form-validate">
							<label style="color:#777777" ><span class="required">*</span>已领数量</label>
						    <input type="text" id="sendQty" name="sendQty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"  value="${giftAppDetail.sendQty}" readonly="true"/>	
						    <input type="text" id="uom1" name="uom1" value="${giftAppDetail.uom}" style="width:50px; outline:none; background:transparent; border:none" readonly="true"/>
						</li>
						<li class="form-validate">
							<label ><span class="required">*</span>数量</label>
						    <input type="text" id="qty" name="qty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"  value="${giftAppDetail.qty}"/>
						    <input type="text" id="uom" name="uom" value="${giftAppDetail.uom}" style="width:50px; outline:none; background:transparent; border:none" readonly="true"/>
						</li>
						
						<li class="form-validate">
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks">${giftAppDetail.remarks}</textarea>
					   </li>
					    <li hidden="true" id="useDiscAmount_li" class="form-validate">
	                        <label>使用优惠额度</label>
	                        <input type="text" id="useDiscAmount" name="useDiscAmount"  onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;" value="${giftAppDetail.useDiscAmount}"/>
                   		</li>
                   		<li id="totalRemainDisc_li" hidden="true">
							<label>总优惠余额</label>
							<input type="text" id="totalRemainDisc" name="totalRemainDisc" readonly="true" style="width:160px;"/>
						</li>
					  
					    <li hidden="true">
							<label style="color:#777777"><span class="required">*</span>单价</label>
							<input type="text" id="price" name="price" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"  value="${giftAppDetail.price}", readonly="true"/>
					   </li>
					   <li hidden="true">
							<label>礼品名称</label>
							<input type="text" id="itemDescr" name="itemDescr"  value="${giftAppDetail.itemDescr}"/>
					   </li>
					   <li hidden="true" >
							<label>礼品券号</label>
							<input type="text" id="tokenNo" name="tokenNo"  value="${giftAppDetail.tokenNo}"/>
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
