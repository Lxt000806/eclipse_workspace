<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript"> 

$(function(){
	$("#isContainTax").attr("disabled",true)
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.setSelect({firstSelect:"itemType1",
								firstValue:"${item.itemType1}",
								secondSelect:"itemType2",
								secondValue:"${item.itemType2}",
								thirdSelect:"itemType3",
								thirdValue:"${item.itemType3}",
	});	  
   Global.LinkSelect.initOption("sqlCode","${ctx}/admin/brand/getBrandList",{itemType2:"${item.itemType2}"},$.trim("${item.sqlCode}"));
   $("#supplCode").openComponent_supplier({showValue:'${item.supplCode}',showLabel:'${item.supplDescr}',condition:{ReadAll:"1"},
   		callBack:function(data){	
   			$("#isContainTax").removeAttr("disabled");
            $("#isContainTax").val(data.IsContainTax);
            $("#isContainTax").attr("disabled",true);  
   			validateRefresh('openComponent_supplier_supplCode')}}); 
   $("#uom").openComponent_uom({showValue:'${item.uom}',showLabel:'${item.uomDescr}',condition:{readonly:"1",ReadAll:"1"},
   		callBack:function(){validateRefresh('openComponent_uom_uomCode')}}); 
   $("#whCode").openComponent_wareHouse({showLabel:"${item.whDescr}",showValue:"${item.whCode}",condition:{czybh:'${item.lastUpdatedBy}'},
 	    callBack:function(){validateRefresh('openComponent_wareHouse_whCode')}});  
   $("#dataForm").bootstrapValidator("addField", "openComponent_wareHouse_whCode", {
        validators: {         
            remote: {
	            message: '',
	           url: '${ctx}/admin/wareHouse/getWareHouse',
	            data: getValidateVal,  
	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }
        }
    });
    $("#dataForm").bootstrapValidator("addField", "openComponent_supplier_supplCode", {  
        validators: {  
       	   notEmpty: {  
                   message: '供应商不能为空'  
             },
            remote: {
	            message: '',
	            url: '${ctx}/admin/supplier/getSupplier',
	            data: getValidateVal,  
	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }
        }  
    });
    $("#dataForm").bootstrapValidator("addField", "openComponent_uom_uomCode", {
        validators: {         
            remote: {
	            message: '',
	           url: '${ctx}/admin/uom/getUom',
	            data: getValidateVal,  
	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }
        }
    });
  	if ("${item.sendType}"=="1"){
   		$("#whCode").val('');
   		$("#openComponent_wareHouse_whCode").val('');
   		$("#whCode").openComponent_wareHouse({disabled:true});
	}
	if ($("#itemType2").val()=='RZDJ'){
		$("#lampNum").removeAttr("readonly");
		$("#lampNum").val("${item.lampNum}");	
	}else{
	   $("#lampNum").attr("readonly",true);
	   $("#lampNum").val(0);  
	}
  	$('#itemType2').change(function(){
		Global.LinkSelect.initOption("sqlCode","${ctx}/admin/brand/getBrandList",{itemType2:$("#itemType2").val()});
		calPerfPer();
		if ($("#itemType2").val()=='RZDJ'){
			$("#lampNum").removeAttr("readonly");
			$("#lampNum").val("${item.lampNum}");	
		}else{
		   $("#lampNum").attr("readonly",true);
		   $("#lampNum").val(0);  
		}
	});
	$("#wareHouseItemCode").openComponent_item({showValue:"${item.wareHouseItemCode}",showLabel:"${item.wareHouseItemDescr}",condition:{itemType1:$.trim($("#itemType1").val()),disabledEle:"itemType1"}});
});
function itemType1Change(){	
   	if ($("#itemType1").val()=='LP'){
    	$("#isInv").removeAttr("disabled");	
	}else{
	   $("#isInv").val("1");
	   $("#isInv").attr("disabled",true); 
	}
	changeIsActualItem();
}
function changeSendType(){	
   	if ($("#sendType").val()=='1'){
   		$("#whCode").val('');
   		$("#openComponent_wareHouse_whCode").val('');
   		$("#whCode").openComponent_wareHouse({disabled:true});  
	}else{
		$("#whCode").openComponent_wareHouse({disabled:false});	  
	}	
}
function priceChange(){
	calPerfPer();
}
function costChange(){
	calPerfPer();
}
function commiTypeChange(){
	if ($.trim($("#commiType").val())=="1"){
	   $("#perfPer").show();
	}else{
	   $("#commiUom_show").hide();
	}	    
}
function chgItemType2(){
	Global.LinkSelect.initOption("sqlCode","${ctx}/admin/brand/getBrandList",{itemType2:$("#itemType2").val()},$.trim("${item.sqlCode}"));
}
function changeIsActualItem(){
	if($("#isActualItem").val() == "1"){
   		$("#openComponent_item_wareHouseItemCode").val('');
   		$("#wareHouseItemCode").openComponent_item({disabled:true});
	}else{
		$("#wareHouseItemCode").openComponent_item({disabled:false});
		$("#wareHouseItemCode").openComponent_item({showValue:"${item.wareHouseItemCode}",showLabel:"${item.wareHouseItemDescr}",condition:{itemType1:$.trim($("#itemType1").val()),disabledEle:"itemType1"}});
	}
}
</script>

<div class="body-box-list" style="margin-top: 0;">
    <div class="panel-body">
        <div class="validate-group row">
            <!-- 第一列 -->
            <div class="col-sm-4">
                <ul class="ul-form">
                    <li class="form-validate">
                        <label><span class="required">*</span>材料名称</label>
                        <input type="text" style="width:160px;" id="descr" name="descr" value="${item.descr}"
                               readonly="readonly" placeholder="保存时生成"/>
                    </li>
                    <li class="form-validate">
                        <label>助记码</label>
                        <input type="text" style="width:160px;" id="remCode" name="remCode" value="${item.remCode}"/>
                    </li>
                    <li class="form-validate">
                        <label>条码</label>
                        <input type="text" style="width:160px;" id="barCode" name="barCode" value="${item.barCode}"/>
                    </li>
                    <li class="form-validate">
                        <label><span class="required">*</span>材料类型1</label>
                        <select id="itemType1" name="itemType1" onchange="itemType1Change()"></select>
                    </li>
                    <li class="form-validate">
                        <label><span class="required">*</span>材料类型2</label>
                        <select id="itemType2" name="itemType2" onchange="chgItemType2()"></select>
                    </li>
                    <li class="form-validate">
                        <label>材料类型3</label>
                        <select id="itemType3" name="itemType3"></select>
                    </li>
                    <li class="form-validate">
                        <label><span class="required">*</span>品牌</label>
                        <select id="sqlCode" name="sqlCode"></select>
                    </li>
                    <li class="form-validate">
                        <label><span class="required">*</span>供应商</label>
                        <input type="text" id="supplCode" name="supplCode" value="${item.supplCode}"
                               readonly="readonly"/>
                    </li>
                    <li class="form-validate">
                        <label>型号</label>
                        <input type="text" style="width:160px;" id="model" name="model" value="${item.model}"/>
                    </li>
                    <li class="form-validate">
                        <label>规格</label>
                        <input type="text" style="width:160px;" id="sizeDesc" name="SizeDesc" value="${item.sizeDesc}"/>
                    </li>
                    <li class="form-validate" style="padding-right: 0;">
                        <label>尺寸</label>
                        <input type="text" id="size" name="size" onkeyup="value=value.replace(/[^\d.]/g,'')"
                               value="${item.size}" style="width:100px;"/>
                        <span style="color:red">每片面积或每条长度</span>
                    </li>
                    <li class="form-validate">
                        <label>颜色</label>
                        <input type="text" style="width:160px;" id="color" name="color" value="${item.color}"/>
                    </li>
                </ul>
            </div>
            <!-- 第二列 -->
            <div class="col-sm-4">
                <ul class="ul-form">
                    <li class="form-validate">
                        <label><span class="required">*</span>单位</label>
                        <input type="text" id="uom" name="uom" style="width:160px;" value="${item.uom}"/>
                    </li>
                    <li class="form-validate">
                        <label><span class="required">*</span>单位重量</label>
                        <input type="text" id="perWeight" name="perWeight"
                               onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="${item.perWeight}"/>
                        <span>kg</span>
                    </li>
                    <li class="form-validate">
                        <label>单位片数</label>
                        <input type="text" id="perNum" name="perNum" onkeyup="value=value.replace(/[^\d.]/g,'')"
                               value="${item.perNum}"/>
                    </li>
                    <li class="form-validate">
                        <label>每箱片数</label>
                        <input type="text" style="width:160px;" id="packageNum" name="packageNum"
                               value="${item.packageNum}"/>
                    </li>
                    <li class="form-validate" id="cost_show" hidden>
                        <label><span class="required">*</span>成本</label>
                        <input type="text" id="cost" name="cost" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
                               value="${item.cost}" onchange="costChange()"/>
                        <span>元</span>
                    </li>
                    <li class="form-validate" id="projectCost_show" hidden>
                        <label>项目经理结算价</label>
                        <input type="text" id="projectCost" name="projectCost"
                               onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="${item.projectCost}"/>
                        <span>元</span>
                    </li>
                    <li class="form-validate" id="marketPrice_show">
                        <label><span class="required">*</span>市场价</label>
                        <input type="text" id="marketPrice" name="marketPrice"
                               onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="${item.marketPrice}"/>
                        <span>元</span>
                    </li>
                    <li class="form-validate" id="price_show">
                        <label><span class="required">*</span>现价</label>
                        <input type="text" id="price" name="price"
                               onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
                               value="${item.price}" onchange="priceChange()"/> <span>元</span>
                    </li>
                    <li class="form-validate">
                        <label><span class="required">*</span>发货类型</label>
                        <house:xtdm id="sendType" dictCode="ITEMAPPSENDTYPE" value="${item.sendType}"
                                    onchange="changeSendType()"></house:xtdm>
                    </li>
                    <li class="form-validate">
                        <label>仓库</label>
                        <input type="text" id="whCode" name="whCode" value="${item.whCode}"/>
                    </li>
                    <li class="form-validate">
                        <label>最小库存量</label>
                        <input type="text" id="minQty" name="minQty"
                               onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
                               value="${item.minQty}"/>
                    </li>
                    <li class="form-validate">
                        <label>是否固定价</label>
                        <house:xtdm id="isFixPrice" dictCode="ISFIXPRICE"
                                    value="${item.isFixPrice}"></house:xtdm>
                    </li>
                </ul>
            </div>
            <!-- 第三列 -->
            <div class="col-sm-4">
                <ul class="ul-form">
                    <li class="form-validate">
                        <label>是否费用</label>
                        <house:xtdm id="isFee" dictCode="YESNO" value="${item.isFee}"></house:xtdm>
                    </li>
                    <li class="form-validate">
                        <label>是否库存管理 </label>
                        <house:xtdm id="isInv" dictCode="YESNO" value="${item.isInv}"></house:xtdm>
                    </li>
                    <li class="form-validate">
                        <label>显示顺序</label>
                        <input type="text" id="dispSeq" name="dispSeq"
                               onkeyup="value=value.replace(/[^\-?\d]/g,'')"
                               value="${item.dispSeq}"/>
                    </li>
                </ul>
            </div>
        </div>

        <div class="validate-group row">
            <div class="col-sm-8">
                <ul class="ul-form">
                    <li class="form-validate">
                        <label class="control-textarea">材料描述</label>
                        <textarea id="remark" name="remark">${item.remark}</textarea>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
