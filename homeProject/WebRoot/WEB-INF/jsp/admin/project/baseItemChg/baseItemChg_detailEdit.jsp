<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  	<title>增减明细--编辑</title>
  	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  	<META HTTP-EQUIV="expires" CONTENT="0"/>
  	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  	<%@ include file="/commons/jsp/common.jsp" %>
  	<script src="${resourceRoot}/pub/component_baseItem.js?v=${v}" type="text/javascript"></script>
  	<script src="${resourceRoot}/pub/component_fixArea.js?v=${v}" type="text/javascript"></script>
  	<script src="${resourceRoot}/pub/component_custTypeItem.js?v=${v}" type="text/javascript"></script>
  	<script type="text/javascript">
  	$(function(){
  		getAlgorithm();
  		if("${algorithm}" != ""){
  			$("#algorithm").val("${algorithm}");
  		}
  		
  	});
    function save() {
    	validateRefresh("openComponent_fixArea_fixareapk");
    	validateRefresh("openComponent_baseItem_baseitemcode");
    	$("#dataForm").bootstrapValidator("validate");
      	if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
      	var datas = $("#dataForm").jsonForm();
      	datas.actionlog = "EDIT";
      	datas.lastupdate = new Date();
      	datas.lastupdatedby = "${czybh}";
      	Global.Dialog.returnData = datas;
      	
      	datas.algorithmdescr = $("select[name='algorithm'] option:selected").text();

      	closeWin();
    }
    
    function selectItem(data) {
    	$("#unitprice").val(data.offerpri);
    	$("#material").val(data.material);
    	$("#baseitemdescr").val(data.descr);
    	$("#uom").val(data.uom);
    	$("#iscalmangefee").val(data.iscalmangefee);
    	$("#category").val(data.category);
    	if ("${custTypeType}"=="2" && data.category!="4" && $("qty").val()!="" && parseFloat($("qty").val())<0){
    		$("#qty").val(parseFloat($("qty").val())*-1);
    	}
    	$("#qty").trigger("blur");
    	$("#algorithm").val("");
    	getAlgorithm();
	}
    
    function changeAmount(){
    	var dQty=0,dUnitPrice=0,dMaterial=0,dLineAmount=0;
    	if ($("#qty").val()!="" && $("#unitprice").val()!="" && $("#material").val()!=""){
    		dQty = parseFloat($("#qty").val());
    		dUnitPrice = parseFloat($("#unitprice").val());
    		dMaterial = parseFloat($("#material").val());
    		dLineAmount = myRound(myRound(dQty*dUnitPrice)+ myRound(dQty*dMaterial));
    		$("#lineamount").val(dLineAmount);
    	}
	}
    
    function changePress(obj){
    	if ("${custTypeType}"=="2" && $("#category").val()!="4"){
    		obj.value = obj.value.replace(/-/g,"");
        	validateRefresh("qty");
    	}
    }
    
  //加载算法
    function getAlgorithm() {
  	  var baseItemCode = $("#baseitemcode").val();
	  
    	$.ajax({
    		url : '${ctx}/admin/baseItem/getBaseAlgorithm',
    		type : 'post',
    		data : {
    			'code' :baseItemCode,
    		},
    		async:false,
    		dataType : 'json',
    		cache : false,
    		error : function(obj) {
    			showAjaxHtml({
    				"hidden" : false,
    				"msg" : '加载算法数据出错~'
    			});
    			
    		},
    		success : function(obj) {
    			var  oldAlgorithm=$("#algorithm").val(); //原预算
    			$("#algorithm").empty();
    			$("#algorithm").append($("<option/>").text("请选择...").attr("value",""));
    			if(obj.length==0){
    				$("#algorithm").attr("disabled",true);
    			}else{
    				$("#algorithm").removeAttr("disabled");
                    $.each(obj, function(i, o){    
                        $("#algorithm").append($("<option/>").text(o.Descr).attr("value",o.Code));
                     });
                }
                $("#algorithm").val(oldAlgorithm);
    		}
    	});
    }
    
    function changeAlgorithm(){
    	dUnitPrice = parseFloat($("#unitprice").val());
		dMaterial = parseFloat($("#material").val());
		
    	var datas={};
    		datas.baseItemCode=$("#baseitemcode").val();
    		datas.baseAlgorithm =$("#algorithm").val();
    		datas.prePlanAreaPK=$("#prePlanAreaPK").val();
    		datas.custCode = "${baseItemChgDetail.custCode}";
    	$.ajax({
    		url:"${ctx}/admin/baseItemPlan/getBaseItemPlanAutoQty",
    			type:"post",
    	        dataType:"json",
    	        async:false,
    	        data:datas,
    			cache: false,
    			error:function(obj){
    				showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
    			},
    			success:function(obj){
    				$("#qty").val(obj);
    				dLineAmount = myRound(myRound(obj*dUnitPrice)+ myRound(obj*dMaterial));
    				$("#lineamount").val(dLineAmount);
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
      			<c:if test="${operType!='V' }">
        		<button type="button" id="saveBtn" class="btn btn-system" onclick="save()">保存</button>
        		</c:if>
        		<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
      		</div>
    	</div>
  	</div>
  	<div class="panel panel-info">
    	<div class="panel-body">
      		<form role="form" class="form-search">
        	<ul class="ul-form">
          		<li>
            		<label>客户编号</label>
            		<input type="text" id="custCode" name="custCode" value="${baseItemChgDetail.custCode}" disabled="disabled"/>
          		</li>
        	</ul>
      	</form>
    	</div>
  	</div>
  	<div class="panel panel-info">
    	<div class="panel-body">
      		<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
      		<input type="hidden" id="custcode" name="custcode" value="${baseItemChgDetail.custCode}"/>
      		<input type="hidden" id="pk" name="pk" value="${baseItemChgDetail.pk}"/>
      		<input type="hidden" id="uom" name="uom" value="${baseItemChgDetail.uom}"/>
      		<input type="hidden" id="fixareadescr" name="fixareadescr" value="${baseItemChgDetail.fixAreaDescr}"/>
      		<input type="hidden" id="baseitemdescr" name="baseitemdescr" value="${baseItemChgDetail.baseItemDescr}"/>
      		<input type="hidden" id="category" name="category" value="${baseItemChgDetail.category}"/>
      		<input type="hidden" id="iscalmangefee" name="iscalmangefee" value="${baseItemChgDetail.isCalMangeFee}"/>
      		<input type="hidden" id="prePlanAreaPK" name="prePlanAreaPK" value="${prePlanAreaPK }"/>
        	<ul class="ul-form">
          	<div class="validate-group">
            	<li class="form-validate">
              		<label>区域</label>
              		<input type="text" id="fixareapk" name="fixareapk" value="${baseItemChgDetail.fixAreaPk}"/>
            	</li>
            	<li id="custTypeItemLi" class="form-validate">
              		<label>空间名称</label>
              		<input type="text" id="prePlanAreaDescr" name="prePlanAreaDescr" value="${prePlanAreaDescr }" readonly="true"
              		/>
            	</li>
            	<li id="itemLi" class="form-validate">
              		<label>基础项目</label>
              		<input type="text" id="baseitemcode" name="baseitemcode" value="${baseItemChgDetail.baseItemCode}"/>
            	</li>
            	
          	</div>
          	<div class="validate-group">
          		<li id="custTypeItemLi" class="form-validate">
              		<label>算法</label>
              		<select id="algorithm" name="algorithm" onchange="changeAlgorithm()"></select>
            	</li>
            	<li id="custTypeItemLi" class="form-validate">
              		<label>数量</label>
              		<input type="text" id="qty" name="qty" value="${baseItemChgDetail.qty}" 
              		onblur="changeAmount()" onkeyup="changePress(this)"/>
            	</li>
            	
          		<li class="form-validate">
              		<label>人工单价</label>
              		<input type="text" id="unitprice" name="unitprice" readonly="readonly" value="${baseItemChgDetail.unitPrice}"/>
            	</li>
          	</div>
          	<div class="validate-group">
            	<li class="form-validate">
              		<label>材料单价</label>
              		<input type="text" id="material" name="material" readonly="readonly" value="${baseItemChgDetail.material}"/>
            	</li>
            	<li class="form-validate">
              		<label>总价</label>
              		<input type="text" id="lineamount" name="lineamount" readonly="readonly" value="${baseItemChgDetail.lineAmount}"/>
            	</li>
          	</div>
          	<div class="validate-group">
            	<li class="form-validate">
              		<label class="control-textarea">备注</label>
              		<textarea id="remarks" name="remarks" maxlength="400">${baseItemChgDetail.remarks}</textarea>
            	</li>
          	</div>
        	</ul>
      		</form>
    	</div>
	</div>
</div>
<script type="text/javascript">
//校验函数
$(function () {
	if ("${baseItemChgDetail.reqPk}"!="" && "${baseItemChgDetail.reqPk}"!="0"){
		$("#fixareapk").openComponent_fixArea({showValue:"${baseItemChgDetail.fixAreaPk}",
			showLabel:"${baseItemChgDetail.fixAreaDescr}",disabled:true,condition: {
				"custCode": "${baseItemChgDetail.custCode}",
	            "itemType1": "JZ"
	        },callBack: function (data) {
	            $("#fixareadescr").val(data.Descr);
	        }
		});
		$("#baseitemcode").openComponent_baseItem({showValue:"${baseItemChgDetail.baseItemCode}",
			showLabel:"${baseItemChgDetail.baseItemDescr}",disabled:true,condition: {
				"custTypeType": "${custTypeType}",customerType:"${customerType }"
	        },callBack: function (data) {
	            selectItem(data);
	        }
		});
		$("#remarks").attr("readonly",true);
	}else{
		$("#fixareapk").openComponent_fixArea({showValue:"${baseItemChgDetail.fixAreaPk}",
			showLabel:"${baseItemChgDetail.fixAreaDescr}",condition: {
				"custCode": "${baseItemChgDetail.custCode}",
	            "itemType1": "JZ"
	        },callBack: function (data) {
	        	$("#fixareadescr").val(data.Descr);
	        }
		});
		$("#baseitemcode").openComponent_baseItem({showValue:"${baseItemChgDetail.baseItemCode}",
			showLabel:"${baseItemChgDetail.baseItemDescr}",condition: {
				"custTypeType": "${custTypeType}",customerType:"${customerType }",canUseComBaseItem:"${canUseComBaseItem }"
	        },callBack: function (data) {
	            selectItem(data);
	        }
		});
	}
	$("#dataForm").bootstrapValidator({
        message : "This value is not valid",
        feedbackIcons : {/*input状态样式图片*/
            validating : "glyphicon glyphicon-refresh"
        },
        fields: {
        	openComponent_fixArea_fixareapk: {
	      		validators: { 
		            notEmpty: { 
		            	message: "区域不能为空"  
		            }
		        }
	      	},
	      	openComponent_baseItem_baseitemcode: {
	      		validators: { 
	      			notEmpty: { 
		            	message: "材料编号不能为空"  
		            }
		        }
	      	},
	      	qty: {
		        validators: { 
		        	numeric: {
		            	message: "数量只能是数字" 
		            },
		            notEmpty: { 
		            	message: "数量不能为空"  
		            }
		        }
	      	},
	      	
      	},
        submitButtons : 'input[type="submit"]'
    });
	$("#qty").val(myRound($("#qty").val()));
	$("#unitprice").val(myRound($("#unitprice").val()));
	$("#material").val(myRound($("#material").val()));
	$("#lineamount").val(myRound($("#lineamount").val()));
	changeAmount();
	
});
</script>
</body>
</html>
