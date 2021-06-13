<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>基础预算--查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_fixArea.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_baseItem.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var ret;
var baseItemCode;
function change(name){
	switch(name){
		case 'qty':
		ret.qty=$("#qty").val();
		var lineamount=myRound(ret.qty*ret.unitprice)+myRound(ret.qty*ret.material);
        $("#lineamount").val(lineamount);
        ret.lineamount=lineamount;
        break;
		default:
		ret.remark=$("#remark").val();
	}
}
function changePress(obj){
  	if ("${baseItemPlan.custTypeType}"=="2" && ret.category!="4"){
  		obj.value = obj.value.replace(/-/g,"");
      	validateRefresh("qty");
  	}
}
function save(){
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
    var datas=$("#dataForm").jsonForm();
	ret.fixareapk=datas.openComponent_fixArea_fixareapk.substring(0,datas.openComponent_fixArea_fixareapk.indexOf("|"));
	ret.fixareadescr=datas.openComponent_fixArea_fixareapk.substring(datas.openComponent_fixArea_fixareapk.indexOf("|")+1);
	ret.baseitemcode=datas.openComponent_baseItem_baseitemcode.substring(0,datas.openComponent_baseItem_baseitemcode.indexOf("|"));
	ret.baseitemdescr=datas.openComponent_baseItem_baseitemcode.substring(datas.openComponent_baseItem_baseitemcode.indexOf("|")+1);
	ret.qty=$("#qty").val();
	Global.Dialog.returnData =ret;
	closeWin();	
}
//校验函数
$(function() {
	var topFrame="";
	if( "${baseItemPlan.m_umState}"=="C"){
		topFrame="#iframe_itemPlan_jcys_confirm";
	}else{
		topFrame="#iframe_itemPlan_jcys";
	}
	ret= $(top.$(topFrame)[0].contentWindow.document.getElementById("dataTable")).jqGrid("getRowData",'${baseItemPlan.rowId}');
	$("#fixareapk").openComponent_fixArea({condition: {custCode:'${baseItemPlan.custCode}',itemType1:'${baseItemPlan.itemType1}'},showValue:ret.fixareapk,showLabel:ret.fixareadescr,callBack:function(){validateRefresh('openComponent_fixArea_fixareapk');}});
	var sPrjType="";
	if (ret.basealgorithm!=""){
		 $("#fixareapk").setComponent_fixArea({readonly: true});
		 sPrjType=ret.prjtype;
	}else{
		$("#baseAlgorithm").attr("disabled",true);
	}
	var sCategory='';
	if('${baseItemPlan.custTypeType}'=='1'){
		sCategory='1';
	}
	
	$("#baseitemcode").openComponent_baseItem({showValue:ret.baseitemcode,showLabel:ret.baseitemdescr,
		condition: {category: sCategory,prjType:sPrjType,custType:"${baseItemPlan.custType}",canUseComBaseItem:"${baseItemPlan.canUseComBaseItem}"},callBack:function(data){	
	   	validateRefresh('openComponent_baseItem_baseitemcode');
	   	if(data.offerpri==undefined){
	   		ret.unitprice=data.offerPri;
	   		$("#unitprice").val(data.offerPri);
	   		ret.tempunitprice=data.offerPri;
	   	}else{
	   		ret.unitprice=data.offerpri;
	   		$("#unitprice").val(data.offerpri);
	   		ret.tempunitprice=data.offerpri;
	   	}
	   	ret.material=data.material;
	   	ret.tempmaterial=data.material;
	   	ret.lineamount=myRound(ret.qty*ret.tempunitprice)+myRound(ret.qty*ret.tempmaterial);
	   	ret.sumunitprice=myRound(ret.qty*ret.tempunitprice);
	   	ret.summaterial=myRound(ret.qty*ret.tempmaterial);
	   	ret.count="";
	   	ret.uom=data.uom;
	   	ret.categorydescr=data.categorydescr;
	   	ret.baseitemtype1=data.baseitemtype1;
	   	ret.iscalmangefee=data.iscalmangefee;
	   	ret.allowpricerise=data.allowpricerise;
	   	$("#material").val(data.material);
	   	$("#lineamount").val(ret.lineamount);
	   	$("#iscalmangefee").val(data.iscalmangefee);
	   	$("#allowpricerise").val(data.allowpricerise);
	   	if (ret.basealgorithm!=""&&ret.basealgorithm!="01"){
			getQtyFromAlgorithm();
		}
		$("#remark").val(data.remark);
		ret.remark=data.remark;
		   
    }});
	$("#custCode").val('${baseItemPlan.custCode}');
	$("#qty").val(ret.qty);
	$("#unitprice").val(ret.unitprice);
	$("#lineamount").val(ret.lineamount);
	$("#material").val(ret.material);
	$("#remark").val(ret.remark);
	$("#iscalmangefee").val(ret.iscalmangefee);
	$("#allowpricerise").val(ret.allowpricerise);
	$("#isrequired").val(ret.isrequired);
	$("#canreplace").val(ret.canreplace);
	$("#canmodiqty").val(ret.canmodiqty);
	$("#baseAlgorithm").val(ret.basealgorithm); 
	$("#baseAlgorithm").attr("disabled",true);
	$("#isrequired").attr("disabled",true);
	$("#canreplace").attr("disabled",true);
	$("#canmodiqty").attr("disabled",true);
	$("#prePlanAreaPK").val(ret.preplanareapk);
	if(ret.canmodiqty=="0"&&"${baseItemPlan.m_umState}"=="A"){
		$("#qty").attr("disabled",true);
	}
	if(ret.canreplace=="0"||ret.giftpk>0||ret.baseitemsetno!=""){
		 $("#baseitemcode").setComponent_baseItem({readonly: true});
	}
	$("#dataForm").bootstrapValidator({
	    message : 'This value is not valid',
	    feedbackIcons : {/*input状态样式图片*/
	      
	        validating : 'glyphicon glyphicon-refresh'
	    },
        fields: { 
        	qty: {
				validators: {  
		            notEmpty: {  
		            message: '数量不能为空'  
		            }  
	            }  
			}, 
	        openComponent_fixArea_fixareapk: {  
	        	validators: {  
		            notEmpty: {  
		            message: '区域不能为空'  
		        	},
		            remote: {
			            message: '',
			            url: '${ctx}/admin/fixArea/getFixArea',
			            data: getValidateVal,  
			            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
			        }     
	        	}  
		    },
		    openComponent_baseItem_baseitemcode: {  
		        validators: {  
		            notEmpty: {  
		            	message: '基础项目不能为空'  
		            },
		            remote: {
			            message: '',
			            url: '${ctx}/admin/baseItem/getBaseItem',
			            data: getValidateVal,  
			            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
			        }     
		        }  
		    } 
  		 },
   submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
   }).on('success.form.bv', function (e) {
	  e.preventDefault();
	  save();
   		
   });	
});
//根据算法获取数量
function getQtyFromAlgorithm(){
	var datas=$("#dataForm").jsonForm();
	datas.baseItemCode=datas.baseitemcode;
	datas.custCode=$.trim($("#custCode").val());
	$.ajax({
		url:"${ctx}/admin/itemPlan/getBasePlanQty",
			type:"post",
	        dataType:"json",
	        async:false,
	        data:datas,
			cache: false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
			},
			success:function(obj){
				if (obj){
				   if ($("#baseAlgorithm").val()=="20"){
				   	   $("#unitprice").val(obj.offerCtrl);
				       $("#materialCtrl").val(obj.materialCtrl); 
				   }  
				   $("#qty").val(obj.qty);
				   change("qty");
				}
			}
	});	
}


</script>

</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system " onclick="validateDataForm()">保存</button>
					<button type="button" class="btn btn-system " onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div>
				<form role="form" class="form-search" action="" method="post" target="targetFrame">
					<ul class="ul-form">
						<li><label>客户编号</label> <input type="text" id="custCode" name="custCode"
							value="${baseItemPlan.custCode}" disabled="disabled" /></li>
					</ul>
				</form>
			</div>

		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
					<input type="hidden" id="iscalmangefee" name="iscalmangefee" "/>
					<input type="hidden" id="allowpricerise" name="allowpricerise" "/>
					<ul class="ul-form">
						<div class="validate-group">
							<li class="form-validate"><label>区域</label> <input type="text" id="fixareapk" name="fixareapk" />
							</li>
							<li class="form-validate"><label>基础项目</label> <input type="text" id="baseitemcode"
								name="baseitemcode" /></li>
							
							<li class="form-validate"><label>数量</label> <input type="number" step="0.01" id="qty" name="qty"
								onblur="change('qty')" onkeyup="changePress(this)"/></li>
						</div>
						<div class="validate-group">
							<li class="form-validate"><label>人工单价</label> <input type="text" id="unitprice" name="unitprice"
								disabled="disabled" /></li>
							<li class="form-validate"><label>材料单价 </label> <input type="text" id="material" name="material"
								disabled="disabled" /></li>
							<li class="form-validate"><label>总价 </label> <input type="text" id="lineamount"
								name="lineamount" disabled="disabled" /></li>
						</div>
						<div class="validate-group">
							<li class="form-validate"><label>算法</label> 
								<house:dict id="baseAlgorithm" dictCode="" 
									sql=" select  Code,Code+' '+Descr fd from tBaseAlgorithm where Expired = 'F' " 
									sqlValueKey="Code" sqlLableKey="fd" readonly="readonly">
								</house:dict>
							</li>
							<li class="form-validate"><label>必选项</label> 
								<house:xtdm id="isrequired" dictCode="YESNO"></house:xtdm>
							</li>
							<li class="form-validate"><label>可替换</label> 
								<house:xtdm id="canreplace" dictCode="YESNO"></house:xtdm>
							</li>
						</div>
						<div class="validate-group">
						<li class="form-validate"><label>数量可修改</label> 
								<house:xtdm id="canmodiqty" dictCode="YESNO"></house:xtdm>
							</li>
							<li class="form-validate"><label class="control-textarea">备注 </label> <textarea id="remark"
									name="remark" onblur="change('remark')"></textarea></li>
						</div>
						<li hidden="true" ><label>空间pk</label> <input type="text" id="prePlanAreaPK" name="prePlanAreaPK"/>
						</li>
					</ul>
				</form>
			</div>

		</div>
	</div>
</body>
</html>
