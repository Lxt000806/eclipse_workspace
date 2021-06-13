<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>主材预算--查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_fixArea.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_custTypeItem.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var ret;
var unitprice;
var itemCode;
function change(name){
	switch(name){
		case 'qty':
			 ret.qty=$("#qty").val();
			 var beflineamount=myRound(ret.qty*ret.unitprice,4);
			 var tmplineamount=myRound(myRound(ret.qty*ret.unitprice,4)*ret.markup/100);
			 var lineamount=myRound(myRound(myRound(ret.qty*ret.unitprice,4)*ret.markup/100)+parseFloat(ret.processcost));
			 $("#beflineamount").val(beflineamount);
	         $("#tmplineamount").val(tmplineamount);
	         $("#lineamount").val(lineamount);
	         ret.beflineamount=beflineamount;
	         ret.tmplineamount=tmplineamount;
	         ret.lineamount=lineamount;
	         if($("#cuttype").val()!=''){
	        	 changeAlgorithm('2') 
	         }
	         
        break;
        case 'unitprice':
        	 ret.unitprice=parseFloat($("#unitprice").val());
			 var beflineamount=myRound(ret.qty*ret.unitprice,4);
			 var tmplineamount=myRound(myRound(ret.qty*ret.unitprice,4)*ret.markup/100);
			 var lineamount=myRound(myRound(myRound(ret.qty*ret.unitprice,4)*ret.markup/100)+parseFloat(ret.processcost));
			 $("#beflineamount").val(beflineamount);
	         $("#tmplineamount").val(tmplineamount);
	         $("#lineamount").val(lineamount);
	         ret.beflineamount=beflineamount;
	         ret.tmplineamount=tmplineamount;
	         ret.lineamount=lineamount;
        break;
        case 'processcost':
	        ret.processcost=parseFloat($("#processcost").val());
	        var lineamount=myRound(parseFloat(ret.tmplineamount)+ret.processcost);
	        $("#lineamount").val(lineamount);
	        ret.lineamount=lineamount;
        break;
        case 'markup':
	         ret.markup=$("#markup").val();
			 var tmplineamount=myRound(myRound(ret.qty*ret.unitprice,4)*ret.markup/100);
			 var lineamount=myRound(myRound(myRound(ret.qty*ret.unitprice,4)*ret.markup/100)+parseFloat(ret.processcost));
	         $("#tmplineamount").val(tmplineamount);
	         $("#lineamount").val(lineamount);
	         ret.tmplineamount=tmplineamount;
	         ret.lineamount=lineamount;
        break;
        case 'projectqty':
        	ret.projectqty=$("#projectqty").val();
        break;
		default:
			ret.remark=$("#remark").val();
	}
}
function save(){
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
    var datas=$("#dataForm").jsonForm();
	ret.fixareapk=datas.openComponent_fixArea_fixareapk.substring(0,datas.openComponent_fixArea_fixareapk.indexOf("|"));
	ret.fixareadescr=datas.openComponent_fixArea_fixareapk.substring(datas.openComponent_fixArea_fixareapk.indexOf("|")+1);
    if( '${itemPlan.itemType1}'=='ZC' ){   //'${itemPlan.isOutSet}'=='1'
    	if($("#prePlanAreaDescr").val()!='' &&$("#algorithm").val()==""&&!$("#algorithm").prop("disabled")){
   	  		art.dialog({
				content:"有对应空间的区域，算法不能为空,请选择对应算法",	
			}); 
			return false;
    	}
	    ret.algorithm=$("#algorithm").val();
		ret.qty= $("#qty").val();   
		ret.projectqty=$("#projectqty").val();					 
		ret.processcost= $("#processcost").val();
		ret.autoqty=$("#autoQty").val();
		ret.cuttype=$("#cutType").val();
		ret.algorithmper=$("#algorithmPer").val();
		ret.algorithmdeduct=$("#algorithmDeduct").val();
		if ($.trim($("#algorithm").val())!=''){	
		 	var salgorithmdescr=$("#algorithm").find("option:selected").text();
			salgorithmdescr = salgorithmdescr.split(' ');//先按照空格分割成数组
			salgorithmdescr=salgorithmdescr[1];
			ret.algorithmdescr=salgorithmdescr;
		}
		if ($.trim($("#cutType").val())!=''){
		 	var scutTypedescr=$("#cutType").find("option:selected").text();
			//scutTypedescr = scutTypedescr.split(' ');//先按照空格分割成数组
			//scutTypedescr=scutTypedescr[1];
			ret.cuttypedescr=scutTypedescr;
		
		}
    }
	Global.Dialog.returnData =ret;
	closeWin();	
}

//校验函数
$(function() {
      var topFrame="#iframe_itemPlan_ys";
      var tableId;
      if('${itemPlan.isService}'==1){
     	 tableId="serviceDataTable";
      }else{
       	tableId="dataTable";
      }
      ret= $(top.$(topFrame)[0].contentWindow.document.getElementById(tableId)).jqGrid("getRowData",'${itemPlan.rowId}');
      if(ret.custtypeitempk>0){
          $("#itemLi").hide();
          itemCode=ret.itemcode;  
          $("#custtypeitempk").openComponent_custTypeItem({showValue:ret.custtypeitempk,showLabel:ret.itemdescr,condition:{'itemType1':'${itemPlan.itemType1}','custType':'${itemPlan.custType}',prePlanAreaPk:ret.preplanareapk, 'disabledEle':'itemType1',custCode:'${itemPlan.custCode}'},callBack:function(data){
	      	   validateRefresh('openComponent_custTypeItem_custtypeitempk');
			   selectItem(data);
     	  }});
     	  if(ret.giftpk>0){        
			  $("#custtypeitempk").setComponent_custTypeItem({readonly: true}); 		
	  	  }
      }else{
          $("#custTypeItemLi").hide();
          itemCode=ret.itemcode;
          $("#itemcode").openComponent_item({showValue:ret.itemcode,showLabel:ret.itemdescr,condition:{'itemType1':'${itemPlan.itemType1}','disabledEle':'itemType1',custCode:'${itemPlan.custCode}',custType:'${itemPlan.custType}'},callBack:function(data){
		       validateRefresh('openComponent_item_itemcode');
			   selectItem(data);
	      }});
	      if(ret.giftpk>0){
			 $("#itemcode").setComponent_item({readonly: true});		
	  	  }
      }
      unitprice=${itemPlan.unitPrice};
      $("#unitprice").val(ret.unitprice);
      $("#fixareapk").openComponent_fixArea({condition: {custCode:'${itemPlan.custCode}',itemType1:'${itemPlan.itemType1}'},showValue:ret.fixareapk+"|"+ret.fixareadescr,callBack:function(data){
      	 $("#prePlanAreaPK").val(data.prePlanAreaPK);
      	 $("#prePlanAreaDescr").val(data.preplanareadescr);
      	validateRefresh('openComponent_fixArea_fixareapk');}});
	  $("#custCode").val('${itemPlan.custCode}');
      $("#qty").val(ret.qty);
      $("#isoutset").val(ret.isoutset);
      $("#projectqty").val(ret.projectqty);
	  $("#unitprice").val(ret.unitprice);
	  $("#beflineamount").val(ret.beflineamount);
	  $("#tmplineamount").val(ret.tmplineamount);
	  $("#processcost").val(ret.processcost);
	  $("#markup").val(ret.markup);
	  $("#lineamount").val(ret.lineamount);
	  $("#remark").val(ret.remark);
	  $("#isFixPrice").val(ret.isfixprice);
	  $("#projectCost").val(ret.projectCost);
	  $("#oldProjectCost").val(ret.oldProjectCost);
	  isUpdateUnitprice();
	  if( '${itemPlan.itemType1}'!='ZC' ){  //'${itemPlan.isOutSet}'!='1' ||
	    $('#prePlanAreaDescr_show').hide();
	    $('#algorithm_show').hide();
	    $('#cutType_show').hide();
	    $('#algorithmPer_show').hide();
	    $('#algorithmDeduct_show').hide();
	    $('#canmodiqty_show').hide();
      }else{      	  
       	  $("#prePlanAreaPK").val(ret.preplanareapk);
       	  $("#prePlanAreaDescr").val(ret.preplanareadescr);
       	  $("#doorPk").val(ret.doorpk);
       	  $("#autoQty").val(ret.autoqty);
          $("#tempDtPk").val(ret.tempdtpk);
       	  getAlgorithm();
       	  $("#algorithm").val(ret.algorithm); 
       	  findCutType(itemCode);
       	  $("#cutType").val(ret.cuttype); 
       	  $("#algorithmPer").val(ret.algorithmper);
      	  $("#algorithmDeduct").val(ret.algorithmdeduct);
  		  $("#canmodiqty").val(ret.canmodiqty);
  		  $("#canmodiqty").attr("disabled",true);
	  	  if(ret.canmodiqty=="0"){
	  	  		$("#qty").attr("disabled",true);
	  	  }
	  	  
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
			processcost : {
				validators: {  
		            notEmpty: {  
		            message: '其他费用不能为空'  
		            }  
	            }  
			},
			projectqty: {
				validators: {  
		            notEmpty: {  
		            message: '预估施工量不能为空'  
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
	        openComponent_item_itemcode: {  
	        	validators: {  
		            notEmpty: {  
		            	message: '材料编号不能为空'  
		            },
		            callback: {  
		           		message: '请输入有效的材料编号'  
		            }    
	        	}  
	        },
	        openComponent_custTypeItem_custtypeitempk: {  
	            validators: {  
		            notEmpty: {  
		           	    message: '套餐材料信息编号不能为空'  
		            },
	                callback: {  
	            		message: '请输入有效的套餐材料信息编号'  
	                }  
       		    }        
   		    }, 
	        markup: {
				validators: {  
		            notEmpty: {  
		           		message: '折扣不能为空'  
		            }  
	            }  
			},
			algorithmPer: {
				validators: {  
		            notEmpty: {  
		           		message: '算法系数不能为空'  
		            }  
	            }  
			},
			algorithmDeduct: {
				validators: {  
		            notEmpty: {  
		           		message: '算法扣除数不能为空'  
		            }  
	            }  
			}, 
	        
		},
        submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
        }).on('success.form.bv', function (e) {
	   		 e.preventDefault();
	   		 save();
		});
});
function updateIsOutSet(event){
	ret.isoutset=event.value;
    if(event.value=='1'){
		ret.isoutsetdescr='是';
        ret.unitprice=unitprice;
		var beflineamount=myRound(ret.qty*ret.unitprice,4);
		var tmplineamount=myRound(myRound(ret.qty*ret.unitprice,4)*ret.markup/100);
	    var lineamount=myRound(myRound(myRound(ret.qty*ret.unitprice,4)*ret.markup/100)+parseFloat(ret.processcost));
		$("#beflineamount").val(beflineamount);
        $("#tmplineamount").val(tmplineamount);
        $("#lineamount").val(lineamount);
        $("#unitprice").val(unitprice);
        ret.beflineamount=beflineamount;
        ret.tmplineamount=tmplineamount;
        ret.lineamount=lineamount;     
     } 
     else{
        ret.isoutsetdescr='否';
        var beflineamount=myRound(ret.qty*ret.unitprice,4);
		var tmplineamount=myRound(myRound(ret.qty*ret.unitprice,4)*ret.markup/100);
	    var lineamount=myRound(myRound(myRound(ret.qty*ret.unitprice,4)*ret.markup/100)+parseFloat(ret.processcost));
		$("#beflineamount").val(beflineamount);
        $("#tmplineamount").val(tmplineamount);
        $("#lineamount").val(lineamount);
        ret.beflineamount=beflineamount;
        ret.tmplineamount=tmplineamount;
        ret.lineamount=lineamount;
     } 
}
function selectItem(data){
 	  ret.itemcode=data.code;
      ret.itemdescr=data.descr;
      ret.itemtype2descr=data.itemtype2descr;
      ret.itemtype2=data.itemtype2;
      ret.itemtype3descr=data.itemtype3descr;
      ret.unitprice=data.price;
      unitprice=data.price;
      ret.cost=data.cost;
      ret.commitype=data.commitype;
	  $("#unitprice").val(unitprice);
      ret.marketprice=data.marketprice;
      change('unitprice');
      ret.sqlcodedescr=data.sqldescr;
      ret.uom=data.uomdescr;
      // ret.remark=data.remark;
      $("#remark").val(data.remark); 
      if(data.pk) ret.custtypeitempk=data.pk;
	  $("#isFixPrice").val(data.isfixprice);
	  ret.isfixprice=data.isfixprice;
      isUpdateUnitprice();
      itemCode=data.code;
      if('${itemPlan.itemType1}'=='ZC' ){ //'${itemPlan.isOutSet}'=='1' &&
	     getAlgorithm();  
	     changeAlgorithm();
	     findCutType(itemCode);
      } 
      $("#markup ").val(100); 
      ret.markup=$("#markup ").val();
      ret.projectcost=data.projectcost;
      if($("#isoutset").val()=="1" ){
		  ret.oldprojectcost=data.projectcost; 
	  }else{
		  ret.oldprojectcost=data.oldprojectcost;  
	  }
      change('qty'); 
}
function isUpdateUnitprice(){
	//非固定价格&&是套餐外材料时,单价编辑框才可用
	if($("#isFixPrice").val()=="0" &&$("#isoutset").val()=="1" ){
		$("#unitprice").removeAttr("disabled");
	}else{
		$("#unitprice").attr("disabled",true);
	}
}
//根据算法计算对应数量
function changeAlgorithm(Type){
	//checkIsCalCutFee();
	var datas=$("#dataForm").jsonForm();
		datas.custCode=$.trim($("#custCode").val());
		datas.itemCode=itemCode;
		datas.doorPK=$.trim($("#doorPk").val());
		datas.algorithmCode=ret.chgalgorithmcode;
		datas.Type=Type;
		datas.qty=$.trim($("#qty").val());
		datas.isOutSet=$.trim($("#isoutset").val());
		datas.tempDtPk=$.trim($("#tempDtPk").val());
	datas.detailJson=getDetailGridDataJSON();
	$.ajax({
		url:"${ctx}/admin/itemPlan/getPrePlanQty",
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
					  if(Type=='2'){
						  $("#processcost").val(obj.processcost);
						  ret.processcost= $("#processcost").val();
						  change('processcost');  
					  }else{
						  $("#qty").val(obj.qty);   
					      $("#projectqty").val(obj.projectqty);					 
						  $("#processcost").val(obj.processcost);
						  $("#autoQty").val(obj.qty);
						  ret.qty= $("#qty").val();   
						  ret.projectqty=$("#projectqty").val();					 
						  ret.processcost= $("#processcost").val();
						  ret.autoQty= $("#autoQty").val();
				          change('qty');    	  
					  } 
				}
			}
	});	
}
//加载算法
function getAlgorithm() {
	$.ajax({
		url : '${ctx}/admin/item/getAlgorithm',
		type : 'post',
		data : {
			'code' :itemCode,
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
                    $("#algorithm").append($("<option/>").text(o.fd).attr("value",o.Code));
                 });
            }
            $("#algorithm").val(oldAlgorithm);
		}
	});
}
//根据切割类型匹配瓷砖尺寸
function findCutType(itemCode){
	$.ajax({
		url : '${ctx}/admin/prePlanTempDetail/getQtyByCutType',
		type : 'post',
		data : {
			'itemCode' :itemCode
		},
		async:false,
		dataType : 'json',
		cache : false,
		error : function(obj) {
			showAjaxHtml({
				"hidden" : false,
				"msg" : '匹配数据出错~'
			});
			
		},
		success : function(obj) {
			$("#cutType").empty();
			$("#cutType").append($("<option/>").text("请选择...").attr("value",""));
			$("#cutType").removeAttr("disabled");
            $.each(obj, function(i, o){      
                $("#cutType").append($("<option/>").text(o.fd).attr("value",o.Code));
            });
		}
	});
}
/*该验证已不用
function checkIsCalCutFee(){
	if($("#algorithm").val()==""){
		$("#cutType").val("");
		$("#cutType").attr("disabled",true);
		return;
	}
	$.ajax({
		url : '${ctx}/admin/algorithm/checkIsCalCutFee',
		type : 'post',
		data : {
			'code' :$("#algorithm").val(),
		},
		async:false,
		dataType : 'json',
		cache : false,
		async: false,
		error : function(obj) {
			showAjaxHtml({
				"hidden" : false,
				"msg" : '数据出错~'
			});
			
		},
		success : function(obj) {
		    if (obj[0]){
				if(obj[0].isCalCutFee!="1"){
					$("#cutType").val("");
					$("#cutType").attr("disabled",true);
				}
				else{
					$("#cutType").removeAttr("disabled");
				}
			}else{
				$("#cutType").removeAttr("disabled");
			}
		}
	});
}
*/
//获取表格json数据，在根据算法时只需要算法是3门和材料名称带门的材料
function getDetailGridDataJSON(){
  	var topFrame="#iframe_itemPlan_ys";
    var zcRowData= $(top.$(topFrame)[0].contentWindow.document.getElementById("dataTable")).jqGrid("getRowData");
    var servRowData= $(top.$(topFrame)[0].contentWindow.document.getElementById("serviceDataTable")).jqGrid("getRowData");
	var gridData =[];
	$.each(zcRowData,function(k,v){
		if(v.algorithm=='3' || v.itemdescr.indexOf("门") != -1 ||v.algorithm=='35') {
			gridData.push(v);
		}	
	});
	$.each(servRowData,function(k,v){
			if(v.algorithm=='3' || v.itemdescr.indexOf("门") != -1 || v.algorithm=='35') {
				gridData.push(v);
			}	
	});
	return JSON.stringify(gridData); 
}
</script>

</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" onclick="validateDataForm()">保存</button>
					<button type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div>
				<form role="form" class="form-search" action="" method="post" target="targetFrame">
					<ul class="ul-form">
						<li><label>客户编号</label> <input type="text" id="custCode" name="custCode"
							value="${itemPlan.custCode}" disabled="disabled" />
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
					<house:token></house:token>
					<ul class="ul-form">
							<li class="form-validate"><label>区域</label> <input type="text" id="fixareapk" name="fixareapk" />
							</li>
							<li id="prePlanAreaDescr_show"><label>空间名称</label> <input type="text" id="prePlanAreaDescr" name="prePlanAreaDescr" readonly="true" />
							</li>
							<li id="itemLi" class="form-validate"><label>材料编号</label> <input type="text" id="itemcode"
								name="itemcode" />
							</li>
							<li id="algorithm_show" class="form-validate"><label>算法</label> 
								<select id="algorithm" name="algorithm" onchange="changeAlgorithm('1')" ></select>
							</li>
							<li id="custTypeItemLi" class="form-validate"><label>套餐材料编号</label> <input type="text"
								id="custtypeitempk" name="custtypeitempk" />
							</li>
							<li class="form-validate"><label>套餐外材料</label> <select id="isoutset" name="isoutset"
								disabled="disabled" onchange="updateIsOutSet(this)">
									<option value="0">否</option>
									<option value="1">是</option>
								</select>
							</li>
							<li id="algorithmPer_show" class="form-validate"><label>算法系数</label> 
								<input type="number" id="algorithmPer" name="algorithmPer" step="0.01" onchange="changeAlgorithm('1')"/>  
							</li>
							<li id="algorithmDeduct_show" class="form-validate"><label>算法扣除数</label> 
								<input type="number" id="algorithmDeduct" name="algorithmDeduct" step="0.01" onchange="changeAlgorithm('1')"/>  
							</li>
							<li class="form-validate"><label>预估施工量</label> <input type="text" id="projectqty"
								name="projectqty" onblur="change('projectqty')" />
							</li>
							<li class="form-validate"><label>数量</label> <input type="number" id="qty" step="0.01"
								onblur="change('qty')" name="qty" />
							</li>
							
							<li class="form-validate"><label>单价</label> <input type="text" id="unitprice" name="unitprice"
								onblur="change('unitprice')" />
							</li>
	
							<li class="form-validate"><label>折扣前金额</label> <input type="text" id="beflineamount"
								name="beflineamount" disabled="disabled" />
							</li>
							<li class="form-validate"><label>折扣</label> <input type="text" id="markup" name="markup"
								onblur="change('markup')" />
							</li>
							<li class="form-validate"><label>小计</label> <input type="text" id="tmplineamount"
								name="tmplineamount" disabled="disabled" />
							</li>
					
							<li class="form-validate"><label>其他费用</label> <input type="number" onblur="change('processcost')"
								id="processcost" name="processcost" />
							</li>
							<li class="form-validate"><label>总价</label> <input type="text" id="lineamount" name="lineamount"
								disabled="disabled" />
							</li>
							<li id="cutType_show" class="form-validate selector"><label>切割类型</label> 
								<select id="cutType"name="cutType" onchange="changeAlgorithm('2')"></select>
							</li>
							<li id="canmodiqty_show" class="form-validate">
								<label>数量可修改</label> 
								<house:xtdm id="canmodiqty" dictCode="YESNO" ></house:xtdm>
							</li>
							<li class="form-validate"><label class="control-textarea">备注</label> <textarea id="remark"
									name="remark" onblur="change('remark')"></textarea>
							</li>
							
						<div hidden="true" class="validate-group"  >
							<li ><label>是否固定价</label> <input type="text" id="isFixPrice" name="isFixPrice"/>
							</li>
							<li ><label>空间pk</label> <input type="text" id="prePlanAreaPK" name="prePlanAreaPK"/>
							</li>
							<li ><label>门pk</label> <input type="text" id="doorPk" name="doorPk"/>
							</li>
							<li ><label>系统计算量</label> <input type="text" id="autoQty" name="autoqty"/>
							</li>
							<li ><label>模板pk</label> <input type="text" id="tempDtPk" name="tempDtPk"/>
							</li>
							
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
