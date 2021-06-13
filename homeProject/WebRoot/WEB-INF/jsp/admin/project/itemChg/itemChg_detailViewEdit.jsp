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
  <script src="${resourceRoot}/pub/component_item.js?v=${v}"
          type="text/javascript"></script>
  <script src="${resourceRoot}/pub/component_fixArea.js?v=${v}"
          type="text/javascript"></script>
  <script src="${resourceRoot}/pub/component_custTypeItem.js?v=${v}"
          type="text/javascript"></script>
  <script type="text/javascript">
    var ret;
    var unitprice;
    function change(name) {
      switch (name) {
        case 'qty':
          ret.qty = $("#qty").val();
          var beflineamount = myRound(ret.qty * ret.unitprice, 4);
          var tmplineamount = myRound(myRound(ret.qty * ret.unitprice, 4) * ret.markup / 100);
          var lineamount = myRound(myRound(myRound(ret.qty * ret.unitprice, 4) * ret.markup / 100) + parseFloat(ret.processcost));
          $("#beflineamount").val(beflineamount);
          $("#tmplineamount").val(tmplineamount);
          $("#lineamount").val(lineamount);
          ret.beflineamount = beflineamount;
          ret.tmplineamount = tmplineamount;
          ret.lineamount = lineamount;
          break;
        case 'processcost':
          ret.processcost = parseFloat($("#processcost").val());
          var lineamount = myRound(parseFloat(ret.tmplineamount) + ret.processcost);
          $("#lineamount").val(lineamount);
          ret.lineamount = lineamount;
          break;
        case 'unitprice':
          var beflineamount = myRound(ret.qty * ret.unitprice, 4);
          var tmplineamount = myRound(myRound(ret.qty * ret.unitprice, 4) * ret.markup / 100);
          var lineamount = myRound(myRound(myRound(ret.qty * ret.unitprice, 4) * ret.markup / 100) + parseFloat(ret.processcost));
          $("#beflineamount").val(beflineamount);
          $("#tmplineamount").val(tmplineamount);
          $("#lineamount").val(lineamount);
          ret.beflineamount = beflineamount;
          ret.tmplineamount = tmplineamount;
          ret.lineamount = lineamount;
          break;
        default:
          ret.remarks = $("#remarks").val();
      }
    }
    function save() {
        if (!$("#dataForm").data('bootstrapValidator').isValid()) return;
        var datas = $("#dataForm").jsonForm();
        ret.fixareapk = datas.openComponent_fixArea_fixareapk.substring(0, datas.openComponent_fixArea_fixareapk.indexOf("|"));
        ret.fixareadescr = datas.openComponent_fixArea_fixareapk.substring(datas.openComponent_fixArea_fixareapk.indexOf("|") + 1);
        ret.algorithm=$("#algorithm").val();
		ret.qty= $("#qty").val();   
		ret.projectqty=$("#projectqty").val();					 
		ret.processcost= $("#processcost").val();
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
			scutTypedescr = scutTypedescr.split(' ');//先按照空格分割成数组
			scutTypedescr=scutTypedescr[1];
			ret.cuttypedescr=scutTypedescr;
		}
      Global.Dialog.returnData = ret;
      closeWin();

    }

    //校验函数
    $(function () {
      if (top.$("#iframe_itemChg_Add")[0]) {
        var topFrame = "#iframe_itemChg_Add";
      } else if (top.$("#iframe_itemChgConfirm")[0]) {
        var topFrame = "#iframe_itemChgConfirm";
      } else {
        var topFrame = "#iframe_itemChgUpdate";
      }
      ret = $(top.$(topFrame)[0].contentWindow.document.getElementById("dataTable")).jqGrid("getRowData", ${itemChg.rowId});
      if (ret.isoutset != "1") {
        $("#itemLi").hide();
      } else {
        $("#custTypeItemLi").hide();
      }
      unitprice =${itemChg.amount};
      $("#unitprice").val(ret.unitprice);
      if ('${itemChg.reqPk}') {
        if (ret.isoutset != "1") {
          $("#custtypeitempk").openComponent_custTypeItem({
            showValue: ret.custtypeitempk + "|" + ret.itemdescr,
            'readonly': true,
            condition: {'itemType1': '${itemChg.itemType1}', 'disabledEle': 'itemType1'}
          });
        } else {
          $("#itemcode").openComponent_item({
            showValue: ret.itemcode + "|" + ret.itemdescr,
            'readonly': true,
            condition: {'itemType1': '${itemChg.itemType1}', 'disabledEle': 'itemType1','custCode': '${itemChg.custCode}','custType': '${itemChg.custType}'},
            callBack: function () {
              validateRefresh('openComponent_item_itemcode');
            }
          });
        }
        $("#fixareapk").openComponent_fixArea({
          condition: {
            isService: '${itemChg.isService}',
            custCode: '${itemChg.custCode}',
            itemType1: '${itemChg.itemType1}'
          }, showValue: ret.fixareapk + "|" + ret.fixareadescr, 'readonly': true
        });
        $("#remarks").attr("readonly", "readonly");
      } else {
        if (ret.isoutset != "1") {
          $("#custtypeitempk").openComponent_custTypeItem({
            showValue: ret.custtypeitempk + "|" + ret.itemdescr,
            condition: {
              'itemType1': '${itemChg.itemType1}',
              'custType': '${itemChg.custType}',
              'disabledEle': 'itemType1'
            },
            callBack: function (data) {
              validateRefresh('openComponent_custTypeItem_custtypeitempk');
              selectItem(data);
            }
          });
        } else {
          $("#itemcode").openComponent_item({
            showValue: ret.itemcode + "|" + ret.itemdescr,
            condition: {'itemType1': '${itemChg.itemType1}', 'disabledEle': 'itemType1','custCode': '${itemChg.custCode}',custType: '${itemChg.custType}'},
            callBack: function (data) {
              validateRefresh('openComponent_item_itemcode');
              selectItem(data);
            }
          });
        }

        $("#fixareapk").openComponent_fixArea({
          condition: {
            isService: '${itemChg.isService}',
            custCode: '${itemChg.custCode}',
            itemType1: '${itemChg.itemType1}'
          }, showValue: ret.fixareapk + "|" + ret.fixareadescr, callBack: function (data) {
            validateRefresh('openComponent_fixArea_fixareapk');
            $("#prePlanAreaPK").val(data.prePlanAreaPK);
      	 	$("#prePlanAreaDescr").val(data.preplanareadescr);
          }
        });
      }
      $("#isoutset").val(ret.isoutset);
      $("#qty").val(ret.qty);
      $("#beflineamount").val(ret.beflineamount);
      $("#tmplineamount").val(ret.tmplineamount);
      $("#processcost").val(ret.processcost);
      $("#markup").val(ret.markup);
      $("#lineamount").val(ret.lineamount);
      $("#remarks").val(ret.remarks);
      //if(!ret.reqpk&&'${itemChg.isOutSet}'=='2')  $("#isoutset").removeAttr("disabled");
      //if(ret.isoutset=='0')  $("#unitprice").val(0);
      if($.trim('${itemChg.itemType1}')!='ZC'){ 
	    $('#prePlanAreaDescr_show').hide();
	    $('#algorithm_show').hide();
	    $('#cutType_show').hide();
	    $('#algorithmPer_show').hide();
	    $('#algorithmDeduct_show').hide(); 
      }else{      	  
       	  $("#prePlanAreaPK").val(ret.preplanareapk);
       	  $("#prePlanAreaDescr").val(ret.preplanareadescr);
       	  $("#doorPk").val(ret.doorpk);
       	  getAlgorithm();
       	  $("#algorithm").val(ret.algorithm); 
       	  findCutType(ret.itemcode);
       	  $("#cutType").val(ret.cuttype); 
       	  $("#algorithmPer").val(ret.algorithmper);
       	  $("#algorithmDeduct").val(ret.algorithmdeduct); 
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
          }
        },
        submitButtons: 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
      }).on('success.form.bv', function (e) {
        e.preventDefault();
        save();

      });
    });
    function updateIsOutSet(event) {
      ret.isoutset = event.value;
      if (event.value == '1') {
        ret.isoutsetdescr = '是';
        ret.unitprice = unitprice;
        var beflineamount = myRound(ret.qty * ret.unitprice, 4);
        var tmplineamount = myRound(myRound(ret.qty * ret.unitprice, 4) * ret.markup / 100);
        var lineamount = myRound(myRound(myRound(ret.qty * ret.unitprice, 4) * ret.markup / 100) + parseFloat(ret.processcost));
        $("#beflineamount").val(beflineamount);
        $("#tmplineamount").val(tmplineamount);
        $("#lineamount").val(lineamount);
        $("#unitprice").val(unitprice);
        ret.beflineamount = beflineamount;
        ret.tmplineamount = tmplineamount;
        ret.lineamount = lineamount;

      }
      else {
        ret.isoutsetdescr = '否';
        var beflineamount = myRound(ret.qty * ret.unitprice, 4);
        var tmplineamount = myRound(myRound(ret.qty * ret.unitprice, 4) * ret.markup / 100);
        var lineamount = myRound(myRound(myRound(ret.qty * ret.unitprice, 4) * ret.markup / 100) + parseFloat(ret.processcost));
        $("#beflineamount").val(beflineamount);
        $("#tmplineamount").val(tmplineamount);
        $("#lineamount").val(lineamount);
        ret.beflineamount = beflineamount;
        ret.tmplineamount = tmplineamount;
        ret.lineamount = lineamount;
      }
    }
    function selectItem(data) {
      ret.itemcode = data.code;
      ret.itemdescr = data.descr;
      ret.itemtype2descr = data.itemtype2descr;
      ret.itemtype2 = data.itemtype2;
      ret.itemtype3descr = data.itemtype3descr;
      ret.unitprice = data.price;
      unitprice = data.price;
      ret.cost = data.cost;
      ret.allcost = myRound(data.cost * ret.qty, 4);
      $("#unitprice").val(unitprice);
      ret.marketprice = data.marketprice;
      change('unitprice');
      ret.sqlcodedescr = data.sqldescr;
      ret.uomdescr = data.uomdescr;
      ret.remarks = data.remark;
      ret.expired=data.expired;
      $("#remarks").val(data.remark);
      if (data.pk) ret.custtypeitempk = data.pk;
      if($.trim('${itemChg.itemType1}')=='ZC'){ 
	     getAlgorithm();  
	     changeAlgorithm();
	     findCutType(data.code);
	     change('qty'); 
      } 
    }
  	//根据算法计算对应数量
function changeAlgorithm(type){
	if (top.$("#iframe_itemChg_Add")[0]) {
      var topFrame = "#iframe_itemChg_Add";
    } else if (top.$("#iframe_itemChgConfirm")[0]) {
      var topFrame = "#iframe_itemChgConfirm";
    } else {
      var topFrame = "#iframe_itemChgUpdate";
    }
	checkIsCalCutFee();
	var datas=$("#dataForm").jsonForm();
		datas.custCode=$.trim($("#custCode").val());
		datas.itemCode=ret.itemcode;
		datas.algorithmCode=ret.algorithmcode;
		datas.CutType=$.trim($("#cutType").val()); 
		datas.doorPK=$.trim($("#doorPk").val());
		datas.prePlanAreaPK=$.trim($("#prePlanAreaPK").val());
		datas.isOutSet=$.trim($("#isoutset").val());	
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
	});	
}
//加载算法
function getAlgorithm() {
	$.ajax({
		url : '${ctx}/admin/item/getAlgorithm',
		type : 'post',
		data : {
			'code' :ret.itemcode,
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
          <li>
            <label>客户编号</label>
            <input type="text" id="custCode" name="custCode" value="${itemChg.custCode}" disabled="disabled"/>
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
							<li id="prePlanAreaDescr_show"><label>空间名称</label> <input type="text" id="prePlanAreaDescr"
								name="prePlanAreaDescr" readonly="true" /></li>
							<li id="itemLi" class="form-validate"><label>材料编号</label> <input type="text" id="itemcode"
								name="itemcode" /></li>
							<li id="custTypeItemLi" class="form-validate"><label>套餐材料编号</label> <input type="text"
								id="custtypeitempk" name="custtypeitempk" /></li>
							<li id="algorithm_show" class="form-validate"><label>算法</label> <select id="algorithm"
								name="algorithm" onchange="changeAlgorithm('1')"></select></li>
							<li id="algorithmPer_show" class="form-validate"><label>算法系数</label> 
								<input type="number" id="algorithmPer" name="algorithmPer" step="0.01" onchange="changeAlgorithm('1')"/>  
							</li>
							<li id="algorithmDeduct_show" class="form-validate"><label>算法扣除数</label> 
								<input type="number" id="algorithmDeduct" name="algorithmDeduct" step="0.01" onchange="changeAlgorithm('1')"/>  
							</li>
							<li class="form-validate"><label>套餐外材料</label> <select id="isoutset" name="isoutset"
								disabled="disabled" onchange="updateIsOutSet(this)">
									<option value="0">否</option>
									<option value="1">是</option>
							</select></li>
							<li class="form-validate"><label>数量</label> <input type="number" id="qty" step="0.01"
								onblur="change('qty')" name="qty" /></li>
							<li class="form-validate"><label>单价</label> <input type="text" id="unitprice" name="unitprice"
								disabled="disabled" /></li>
							<li class="form-validate"><label>折扣前金额</label> <input type="text" id="beflineamount"
								name="beflineamount" disabled="disabled" /></li>
							<li class="form-validate"><label>小计</label> <input type="text" id="tmplineamount"
								name="tmplineamount" disabled="disabled" /></li>
							<li id="cutType_show" class="form-validate selector"><label>切割类型</label> 
								<select id="cutType"name="cutType" onchange="changeAlgorithm('2')"></select>
							</li>
							<li class="form-validate"><label>其他费用</label> <input type="number" step="0.01"
								onblur="change('processcost')" id="processcost" name="processcost" /></li>
							<li class="form-validate"><label>折扣</label> <input type="text" id="markup" name="markup"
								disabled="disabled" /></li>
							<li class="form-validate"><label>总价</label> <input type="text" id="lineamount" name="lineamount"
								disabled="disabled" /></li>
							<li class="form-validate"><label class="control-textarea">备注</label> <textarea id="remarks"
									name="remarks" onblur="change('remarks')"></textarea></li>
							<div hidden="true" class="validate-group"  >
								<li ><label>是否固定价</label> <input type="text" id="isFixPrice" name="isFixPrice"/>
								</li>
								<li ><label>空间pk</label> <input type="text" id="prePlanAreaPK" name="prePlanAreaPK"/>
								</li>
								<li ><label>门pk</label> <input type="text" id="doorPk" name="doorPk"/>
								</li>
								<li ><label>系统计算量</label> <input type="text" id="autoQty" name="autoqty"/>
								</li>
							</div>
					</ul>
				</form>
    </div>
  </div>
</div>
</body>
</html>
