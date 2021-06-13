<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>增减明细--编辑</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_fixArea.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_custTypeItem.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_intProd.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
    var ret;
    var unitprice;
    var chgunitprice;
    var chgitemCode;
    function change(name) {
      switch (name) {
          case 'chgqty':
	          ret.chgqty = $("#chgqty").val();
	          var chgbeflineamount = myRound(ret.chgqty * ret.chgunitprice, 4);
	          var chgtmplineamount = myRound(myRound(ret.chgqty * ret.chgunitprice, 4) * ret.markup / 100);
	          var chglineamount = myRound(myRound(myRound(ret.chgqty * ret.chgunitprice, 4) * ret.markup / 100) + parseFloat(ret.chgprocesscost));
	          $("#chgbeflineamount").val(chgbeflineamount);
	          $("#chgtmplineamount").val(chgtmplineamount);
	          $("#chglineamount").val(chglineamount);
	          ret.chgbeflineamount = chgbeflineamount;
	          ret.chgtmplineamount = chgtmplineamount;
	          ret.chglineamount = chglineamount;
	          break;
          case 'chgprocesscost':
	          ret.chgprocesscost = parseFloat($("#chgprocesscost").val());
	          var chglineamount = myRound(parseFloat(ret.chgtmplineamount) + ret.chgprocesscost);
	          $("#chglineamount").val(chglineamount);
	          ret.chglineamount = chglineamount;
	          break;
          case 'chgunitprice':
	          var chgbeflineamount = myRound(ret.chgqty * ret.chgunitprice, 4);
	          var chgtmplineamount = myRound(myRound(ret.chgqty * ret.chgunitprice, 4) * ret.markup / 100);
	          var chglineamount = myRound(myRound(myRound(ret.chgqty * ret.chgunitprice, 4) * ret.markup / 100) + parseFloat(ret.chgprocesscost));
	          $("#chgbeflineamount").val(chgbeflineamount);
	          $("#chgtmplineamount").val(chgtmplineamount);
	          $("#chglineamount").val(chglineamount);
	          ret.chgbeflineamount = chgbeflineamount;
	          ret.chgtmplineamount = chgtmplineamount;
	          ret.chglineamount = chglineamount;
	          break;
         default:
         ret.chgremark = $("#chgremark").val();
      }
    }
    function save() {
      if (!$("#dataForm").data('bootstrapValidator').isValid()) return;
      var datas = $("#dataForm").jsonForm();
      ret.chgfixareapk = datas.openComponent_fixArea_chgFixareapk.substring(0, datas.openComponent_fixArea_chgFixareapk.indexOf("|"));
      ret.chgfixareadescr = datas.openComponent_fixArea_chgFixareapk.substring(datas.openComponent_fixArea_chgFixareapk.indexOf("|") + 1);
	  if ($.trim($("#chgalgorithm").val())!=''){	
	 	  var schgalgorithmdescr=$("#chgalgorithm").find("option:selected").text();
		  schgalgorithmdescr = schgalgorithmdescr.split(' ');//先按照空格分割成数组
		  schgalgorithmdescr=schgalgorithmdescr[1];
		  ret.chgalgorithmdescr=schgalgorithmdescr;
	  }else{
	  	  ret.chgalgorithmdescr='';
	  }
	  ret.chgcuttype=$("#chgCutType").val();
	  if ($.trim($("#chgCutType").val())!=''){	
		 	var sChgCutTypedescr=$("#chgCutType").find("option:selected").text();
			sChgCutTypedescr = sChgCutTypedescr.split(' ');//先按照空格分割成数组
			sChgCutTypedescr=sChgCutTypedescr[1];
			ret.chgcuttypedescr=sChgCutTypedescr;
	  }else{
	   		ret.chgcuttypedescr='';
	  }
      Global.Dialog.returnData = ret;
      closeWin();
    }
    //校验函数
    $(function () {
        var topFrame = "#iframe_itemChg_transAction";	
        ret = $(top.$(topFrame)[0].contentWindow.document.getElementById("itemReplace_dataTable")).jqGrid("getRowData", ${itemChg.rowId});
        if (ret.isoutset != "1") {
             $("#itemLi").hide();
             $("#chgitemLi").hide(); 
        } else {
             $("#custTypeItemLi").hide();
             $("#chgcustTypeItemLi").hide();
        }
        unitprice =${itemChg.amount};
        $("#chgunitprice").val(ret.chgunitprice);
        if (ret.isoutset != "1") {
        	 $("#custtypeitempk").openComponent_custTypeItem({
	              showValue: ret.custtypeitempk,
	              showLabel:ret.itemdescr,
	              'readonly': true,    
	          });
	          $("#chgcusttypeitempk").openComponent_custTypeItem({
	              showValue: ret.chgcusttypeitempk,
	              showLabel:ret.chgitemdescr,
	              condition: {'itemType1': '${itemChg.itemType1}', 'disabledEle': 'itemType1','custCode': '${itemChg.custCode}',custType :'${itemChg.custType}'},
	              callBack: function (data) {
		              validateRefresh('openComponent_custTypeItem_chgcusttypeitempk');
		              selectItem(data);
           		 }
	          });
        }else {
        	 $("#itemcode").openComponent_item({
	            showValue:ret.itemcode,
	            showLabel:ret.itemdescr,
	           'readonly': true, 
            });
            $("#chgitemcode").openComponent_item({
	            showValue: ret.chgitemcode,
	            showLabel:ret.chgitemdescr,
	            condition: {'itemType1': '${itemChg.itemType1}', 'disabledEle': 'itemType1','custCode': '${itemChg.custCode}'},
	            callBack: function (data) {
	            	validateRefresh('openComponent_item_chgitemcode');
	            	selectItem(data);
	            },
            });
        }
        $("#chgFixareapk").openComponent_fixArea({
	          condition: {
	            isService: '${itemChg.isService}',
	            custCode: '${itemChg.custCode}',
	            itemType1: '${itemChg.itemType1}'
	          }, 
	          showValue:ret.chgfixareapk,
	          showLabel:ret.chgfixareadescr,
	          'readonly': true, 
	           callBack:function(data){
      	 		  $("#chgPrePlanAreaPK").val(data.prePlanAreaPK);
      	 		  $("#chgPrePlanAreaDescr").val(data.preplanareadescr);
      			  validateRefresh('openComponent_fixArea_chgFixareapk');
      			  if ($.trim('${itemChg.itemType1}')== "JC") {
			           $("#chgintprodpk").openComponent_intProd({
				          condition: {
				            isService: '${itemChg.isService}',
				            custCode:  '${itemChg.custCode}',
				            itemType1: '${itemChg.itemType1}',
				            fixAreaPk: data.PK,
				          }, 
				          callBack: function (data) {
				            validateRefresh('openComponent_intProd_chgintprodpk');
				            ret.chgintprodpk=data.PK;
				            ret.chgintproddescr=ret.Descr;
				       	  },
	     			 });		
      		  	}
      		  }
        });  
        if ($.trim('${itemChg.itemType1}')== "JC") {
        	$("#chgintprodpk").openComponent_intProd({
	          condition: {
	              isService: '${itemChg.isService}',
	              custCode:  '${itemChg.custCode}',
	              itemType1: '${itemChg.itemType1}',
	              fixAreaPk: ret.chgfixareapk,
	          }, 
	          showValue: ret.chgintprodpk, 
	          showLabel: ret.chgintproddescr,
	          callBack: function (data) {
	              validateRefresh('openComponent_intProd_chgintprodpk');
	              ret.chgintprodpk=data.PK;
	              ret.chgintproddescr=ret.Descr;
	          },
	          'readonly': true, 
	      });		
        }else{
        	 $('#chgintprodpk_show').hide();
        } 
        $("#qty").val(ret.chgqty);
		$("#processcost").val(ret.processcost);
		$("#lineamount").val(ret.lineamount);
		$("#unitprice").val(ret.unitprice);
		
		$("#isoutset").val(ret.isoutset);
		$("#chgqty").val(ret.chgqty);
		$("#chgbeflineamount").val(ret.chgbeflineamount);
		$("#chgtmplineamount").val(ret.chgtmplineamount);
		$("#chgprocesscost").val(ret.chgprocesscost);
		$("#markup").val(ret.markup);
		$("#chglineamount").val(ret.chglineamount);
		$("#chgremark").val(ret.chgremark);
		if($.trim('${itemChg.itemType1}')!='ZC' ){ 
		    $('#prePlanAreaDescr_show').hide();
		    $('#algorithm_show').hide();
		    $('#cutType_show').hide();
	    }else{      	  
	       	  $("#chgPrePlanAreaPK").val(ret.chgpreplanareapk);
	       	  $("#chgPrePlanAreaDescr").val(ret.chgpreplanareadescr);
	       	  $("#doorPk").val(ret.doorpk);
	       	  getAlgorithm();
	       	  $("#chgalgorithm").val(ret.chgalgorithm);
	       	  if(ret.chgitemcode){
	       	  	 findCutType(ret.chgitemcode);
	       	 	 $("#chgCutType").val(ret.chgcuttype);
	       	  }  	
	     } 
		 $("#dataForm").bootstrapValidator({
	        message: 'This value is not valid',
	        feedbackIcons: {
	          /*input状态样式图片*/
	          validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	          openComponent_fixArea_fixareapk: {
	            validators: {
	              notEmpty: {
	                message: '区域不能为空'
	              },
	              remote: {
	                message: '',
	                url: '${ctx}/admin/fixArea/getFixArea',
	                data: getValidateVal,
	                delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	              }
	            }
	          },
	          openComponent_item_chgitemcode: {
	            validators: {
	              notEmpty: {
	                message: '材料编号不能为空'
	              },
	              callback: {
	                message: '请输入有效的材料编号'
	              }
	            }
	          },
	          openComponent_custTypeItem_chgcusttypeitempk: {
	            validators: {
	              notEmpty: {
	                message: '套餐材料信息编号不能为空'
	              },
	              callback: {
	                message: '请输入有效的套餐材料信息编号'
	              }
	            }
	          },
	          openComponent_intProd_chgintprodpk: {
	            validators: {
	              notEmpty: {
	                message: '集成成品不能为空'
	              },
	              remote: {
	                message: '',
	                url: '${ctx}/admin/intProd/getIntProd',
	                data: getValidateVal,
	                delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	              }
	            }
          	 },
          	 chgprocesscost: {
	            validators: {
	              notEmpty: {
	                message: '其他费用不能为空'
	              },
	            }
	          },
	          chgqty: {
	            validators: {
	              notEmpty: {
	                message: '数量不能为空'
	              },
	            }
	          },
	        },
	        submitButtons: 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	      }).on('success.form.bv', function (e) {
	        e.preventDefault();
	        save();
      });
    });
  
    function selectItem(data) {
      ret.chgitemcode = data.code;
      ret.chgitemdescr = data.descr;
      ret.chgitemtype2descr = data.itemtype2descr;
      ret.chgitemtype2 = data.itemtype2;
      ret.chgitemtype3descr = data.itemtype3descr;
      ret.chgunitprice = data.price;
      chgunitprice = data.price;
      ret.chgcost = data.cost;
      ret.chgallcost = myRound(data.cost * ret.chgqty, 4);
      $("#chgunitprice").val(chgunitprice);
      ret.chgmarketprice = data.marketprice;
      ret.chgsqlcodedescr = data.sqldescr;
      ret.chguomdescr = data.uomdescr;
      ret.chgremark = data.remark;
      $("#chgremark").val(data.remark);
      change('chgunitprice');
      if (data.pk) ret.chgcusttypeitempk = data.pk;
      if($.trim('${itemChg.itemType1}')=='ZC'){ 
	     getAlgorithm();  
	     changeAlgorithm();
	     findCutType(data.code);
	     change('chgqty'); 
      } 
    }
    //根据算法计算对应数量
function changeAlgorithm(){
    var topFrame = "#iframe_itemChg_transAction";	
    rowData = $(top.$(topFrame)[0].contentWindow.document.getElementById("itemReplace_dataTable")).jqGrid("getRowData", ${itemChg.rowId});
	checkIsCalCutFee();
	var datas=$("#dataForm").jsonForm();
		datas.custCode=$.trim($("#custCode").val());
		datas.itemCode=ret.chgitemcode;
		datas.CutType=$.trim($("#chgCutType").val()); 
		datas.algorithm=$("#chgalgorithm").val();
		datas.doorPK=$.trim($("#doorPk").val());
		datas.prePlanAreaPK=$.trim($("#chgPrePlanAreaPK").val());
		datas.isOutSet=$.trim($("#isOutSet").val()); 
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
				      $("#chgqty").val(obj.qty);   				 
					  $("#chgprocesscost").val(obj.processcost);
					  ret.chgqty= $("#chgqty").val();   				 
					  ret.chgprocesscost= $("#chgprocesscost").val();
					  ret.chgalgorithm=$("#chgalgorithm").val();
			          change('chgqty');    	  
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
			'code' :ret.chgitemcode,
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
			var  oldAlgorithm=ret.chgalgorithm; //原算法
			$("#chgalgorithm").empty();
			$("#chgalgorithm").append($("<option/>").text("请选择...").attr("value",""));
			if(obj.length==0){
				$("#chgalgorithm").attr("disabled",true);
			}else{
				$("#chgalgorithm").removeAttr("disabled");
                $.each(obj, function(i, o){    
                    $("#chgalgorithm").append($("<option/>").text(o.fd).attr("value",o.Code));
                 });
            }
            $("#chgalgorithm").val(oldAlgorithm);
		}
	});
}
//根据切割类型匹配瓷砖尺寸
function findCutType(itemCode){
	$.ajax({
		url : '${ctx}/admin/prePlanTempDetail/getQtyByCutType',
		type : 'post',
		data : {
			'itemCode' :ret.chgitemcode
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
			$("#chgCutType").empty();
			$("#chgCutType").append($("<option/>").text("请选择...").attr("value",""));
			$("#chgCutType").removeAttr("disabled");
            $.each(obj, function(i, o){      
                $("#chgCutType").append($("<option/>").text(o.fd).attr("value",o.Code));
            });
		}
	});
}
function checkIsCalCutFee(){
	if($("#chgalgorithm").val()==""){
		$("#chgCutType").val("");
		$("#chgCutType").attr("disabled",true);
		return;
	}
	$.ajax({
		url : '${ctx}/admin/algorithm/checkIsCalCutFee',
		type : 'post',
		data : {
			'code' :$("#chgalgorithm").val(),
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
					$("#chgCutType").val("");
					$("#chgCutType").attr("disabled",true);
				}
				else{
					$("#chgCutType").removeAttr("disabled");
				}
			}else{
				$("#chgCutType").removeAttr("disabled");
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
			<div class="panel-body">
				<form role="form" class="form-search">
					<ul class="ul-form">
						<li hidden="true"><label>客户编号</label> <input type="text" id="custCode" name="custCode"
							value="${itemChg.custCode}" disabled="disabled" />
						</li>
						<li id="itemLi" class="form-validate">
								<label>原材料编号</label> <input type="text" id="itemcode"
								name="itemcode" />
						</li>
						<li id="custTypeItemLi" class="form-validate"><label>原套餐材料编号</label> <input type="text"
								id="custtypeitempk" name="custtypeitempk" />
						</li>
						<li class="form-validate"><label>原数量</label> <input type="number" id="qty" 
								 name="qty" disabled="disabled" />
							</li>
							<li class="form-validate"><label>原单价</label> <input type="text" id="unitprice" name="unitprice"
								disabled="disabled" />
							</li>
							<li class="form-validate"><label>原其他费用</label> <input type="number" step="0.01"
								 id="processcost" name="processcost" disabled="disabled"/>
							</li>
							<li class="form-validate"><label>原总价</label> <input type="text" id="lineamount" name="lineamount"
								disabled="disabled" />
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
							<li class="form-validate"><label>区域</label> 
								<input type="text" id="chgFixareapk" name="chgFixareapk" />
							</li>
							<li id="prePlanAreaDescr_show"><label>空间名称</label> <input type="text" id="chgPrePlanAreaDescr" name="chgPrePlanAreaDescr" readonly="true" />
							</li>
							<li  id="chgintprodpk_show" class="form-validate">
				              <label>集成成品</label>
				              <input type="text" id="chgintprodpk" name="chgintprodpk"/>
				            </li>
							<li id="chgitemLi" class="form-validate"><label>材料编号</label> <input type="text" id="chgitemcode"
								name="chgitemcode" />
							</li>
							<li id="chgcustTypeItemLi" class="form-validate"><label>套餐材料编号</label> <input type="text"
								id="chgcusttypeitempk" name="chgcusttypeitempk" />
							</li>
							<li class="form-validate"><label>套餐外材料</label> <select id="isoutset" name="isoutset"
								disabled="disabled" >
									<option value="0">否</option>
									<option value="1">是</option>
							</select>
							</li>
							<li id="algorithm_show" class="form-validate"><label>算法</label> 
								<select id="chgalgorithm" name="chgalgorithm" onchange="changeAlgorithm()" ></select>
							</li>
							<li class="form-validate"><label>数量</label> <input type="number" id="chgqty" step="0.01"
								onblur="change('chgqty')" name="chgqty" />
							</li>
							<li class="form-validate"><label>单价</label> <input type="text" id="chgunitprice" name="chgunitprice"
								disabled="disabled" />
							</li>
							<li class="form-validate"><label>折扣前金额</label> <input type="text" id="chgbeflineamount"
								name="chgbeflineamount" disabled="disabled" />
							</li>
							<li class="form-validate"><label>小计</label> <input type="text" id="chgtmplineamount"
								name="chgtmplineamount" disabled="disabled" />
							</li>
							
							<li class="form-validate"><label>折扣</label> <input type="text" id="markup" name="markup"
								disabled="disabled" />
							</li>
							<li id="cutType_show" class="form-validate selector"><label>切割类型</label> 
								<select id="chgCutType"name="chgCutType" onchange="changeAlgorithm()"></select>
							</li>
							<li class="form-validate"><label>其他费用</label> <input type="number" step="0.01"
								onblur="change('chgprocesscost')" id="chgprocesscost" name="chgprocesscost" />
							</li>
							<li class="form-validate"><label>总价</label> <input type="text" id="chglineamount" name="chglineamount"
								disabled="disabled" />
							</li>
							<li class="form-validate"><label class="control-textarea">备注</label> <textarea id="chgremark"
									name="chgremark" onblur="change('chgremark')"></textarea>
							</li>
							<li hidden="true"><label>是否固定价</label> <input type="text" id="isFixPrice" name="isFixPrice"/>
							</li>
							<li  hidden="true" ><label>空间pk</label> <input type="text" id="chgPrePlanAreaPK" name="chgPrePlanAreaPK"/>
							</li>
							<li  hidden="true" ><label>门pk</label> <input type="text" id="doorPk" name="doorPk"/>
							</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
