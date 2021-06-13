<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
  <title>修改ItemChg</title>
  <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  <META HTTP-EQUIV="expires" CONTENT="0"/>
  <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  <%@ include file="/commons/jsp/common.jsp" %>
  <script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
  <script src="${resourceRoot}/pub/component_fixArea.js?v=${v}" type="text/javascript"></script>
  <script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
  <script src="${resourceRoot}/pub/component_itemBatchDetail.js?v=${v}" type="text/javascript"></script>
  <script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
  <script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
  <script src="${resourceRoot}/pub/component_discToken.js?v=${v}" type="text/javascript"></script>
  <style type="text/css">
    .commiColor {
      background-color: yellow;
    }
    .SelectBG{
          background-color:#198ede!important;
            color:rgb(255,255,255)
    }
  </style>
  <script type="text/javascript">
    //责任人类型修改次数
	var clickNum=0;
    var selRowNum = -1;
    function closeWin(isCallBack, isPrevent) {
      if (!isPrevent)return;
      Global.Dialog.closeDialog(isCallBack);
    }
    
    //编辑聚焦
    var data_iRow;
    var data_iCol;
    var editRow;
    var itemdescr;
    var maxDispSeq = 0;
    var SetMinus;
    var itemAppDetail = [];
    var reqpk = [];
    var algorithmdescr = "";
    function add() {
      var datas = $("#dataForm").jsonForm();
      var ret = selectDataTableRow();
      if (ret) {
        datas.fixAreaPk = ret.fixareapk;
        datas.itemType2 = ret.itemtype2;
      }
      
      var grid = $("#dataTable");
      var reqPks = grid.getCol("reqpk");
      datas.excludedReqPks = reqPks.join(",");
      
      Global.Dialog.showDialog("quickSave", {
        title: "增减明细--快速新增",
        url: "${ctx}/admin/itemChg/goQuickSave",
        height: 750,
        width: 1380,
        resizable: false,
        postData: datas,
        returnFun: function (data) {
          if (data.length) {
            var orderBy = "${itemChg.itemType1}".trim() == 'RZ' ? 'itemtype2descr' : $("#itemType2Group").is(":checked") ? 'itemtype2descr' : 'dispseq';
            if ("${itemChg.itemType1}".trim() == 'ZC' && $("#isService").val() == '1') {
              //默认提成
              $.each(data, function (k, v) {
                maxDispSeq++;
                v.iscommi = "1";
                v.lastupdate = getNowFormatDate();
                v.lastupdatedby = '${itemChg.lastUpdatedBy}';
                v.actionlog = "ADD";
                v.dispseq = maxDispSeq;
              })
            } else {
              $.each(data, function (k, v) {
                maxDispSeq++;
                v.lastupdate = getNowFormatDate();
                v.lastupdatedby = '${itemChg.lastUpdatedBy}';
                v.actionlog = "ADD";
                v.dispseq = maxDispSeq;
              })
            }

            var params = JSON.stringify($("#dataTable").jqGrid("getRowData").concat(data));
            $("#dataTable").jqGrid("setGridParam", {
              url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
              postData: {'params': params, 'orderBy': orderBy},
              page: 1
            }).trigger("reloadGrid");
          }
          selRowNum = -1;
        }
      });
    }
    function insert() {
      var ret = selectDataTableRow(); 
      
      if (ret || selRowNum != -1) {
	      if(selRowNum != -1){
	    	  ret =  $("#dataTable").jqGrid('getRowData', selRowNum);
	      }

        var posi = $('#dataTable').jqGrid('getGridParam', 'selrow');
        if(!posi){
	        posi = selRowNum;
        }
        var arr = $("#dataTable").jqGrid("getRowData");
        var brr = arr.splice(posi, arr.length - posi);
        var datas = $("#dataForm").jsonForm();
        datas.fixAreaPk = ret.fixareapk;
        datas.itemType2 = ret.itemtype2;
        
        var grid = $("#dataTable");
        var reqPks = grid.getCol("reqpk");
        datas.excludedReqPks = reqPks.join(",");
        
        Global.Dialog.showDialog("quickSave", {
          title: "增减明细--快速新增",
          url: "${ctx}/admin/itemChg/goQuickSave",
          height: 750,
          width: 1380,
          resizable: false,
          postData: datas,
          returnFun: function (data) {
            if (data.length) {
              var orderBy = "${itemChg.itemType1}".trim() == 'RZ' ? 'itemtype2descr' : $("#itemType2Group").is(":checked") ? 'itemtype2descr' : 'dispseq';
              var current = currentDisp = ret.dispseq;
              if ("${itemChg.itemType1}".trim() == 'ZC' && $("#isService").val() == '1') {
                //默认提成
                $.each(data, function (k, v) {
                  maxDispSeq++;
                  v.iscommi = "1";
                  v.lastupdate = getNowFormatDate();
                  v.lastupdatedby = '${itemChg.lastUpdatedBy}';
                  v.actionlog = "ADD";
                  v.dispseq = ++current;
                })
              } else {
                $.each(data, function (k, v) {
                  maxDispSeq++;
                  v.lastupdate = getNowFormatDate();
                  v.lastupdatedby = '${itemChg.lastUpdatedBy}';
                  v.actionlog = "ADD";
                  v.dispseq = ++current;
                })
              }
              $.each(brr, function (k, v) {
                //按材料分类
                if (v.dispseq > currentDisp)
                  v.dispseq = parseInt(v.dispseq) + data.length;
              })
              var params = JSON.stringify(arr.concat(data).concat(brr));
              $("#dataTable").jqGrid("setGridParam", {
                url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
                postData: {'params': params, 'orderBy': orderBy},
                page: 1
              }).trigger("reloadGrid");
            }
            selRowNum = -1;
          }
        });

      } else {
        art.dialog({
          content: "请选择一条记录"
        });
      }

    }
    function doSave(status) {
      $.ajax({
        url: '${ctx}/admin/itemChg/isExistsNotSendItemList',
        type: "post",
        dataType: "json",
        async: false,
        data: {'custCode': '${itemChg.custCode}', 'gridData': JSON.stringify($("#dataTable").jqGrid("getRowData"))},
        cache: false,
        success: function (obj) {
          $("#_form_token_uniq_id").val(obj.token.token);
          if (obj.datas.length > 0) {
            itemAppDetail = obj.datas;
            Global.Dialog.showDialog("itemChg_itemAppDetail", {
              title: "未发货领料",
              url: "${ctx}/admin/itemChg/goItemChg_itemAppDetail",
              height: 600,
              width: 1000
            });
          } else {
            //验证
            var datas = $("#dataForm").jsonForm();
            if(status=="4")  datas.status="4";
            var gridDatas = $("#dataTable").jqGrid("getRowData");
            datas.detailJson = JSON.stringify(gridDatas);
            datas.chgStakeholderList = Global.JqGrid.allToJson("itemChgStakeholderDataTable").detailJson;
            $.ajax({
              url: '${ctx}/admin/itemChg/doUpdate',
              type: 'post',
              data: datas,
              dataType: 'json',
              cache: false,
              error: function (obj) {
                showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
              },
              success: function (obj) {
                if (obj.rs) {
                  art.dialog({
                    content: obj.msg,
                    time: 1000,
                    beforeunload: function () {
                      closeWin(true, true);
                    }
                  });
                } else {
                  $("#_form_token_uniq_id").val(obj.token.token);
                  art.dialog({
                    content: obj.msg,
                    width: 200,
                    beforeunload: function () {
                      closeWin(true, true);
                    }
                  });
                }
              }
            });

          }
        }
      });
    }
    
	function save(status) {
	    if($("#isAddAllInfo").val()==""){
	        art.dialog({
	            content: "常规变更不允许为空"
	        });
	        return;
	    }
	    if ("" != $("#itemChgStakeholder").val() && "1" == $("#isAddAllInfo").val()) {
	        art.dialog({
	            content: "常规变更增减单，不能设置干系人！"
	        });
	        return;
	    } else if ("" == $("#itemChgStakeholder").val() && "0" == $("#isAddAllInfo").val()) {
	        art.dialog({
	            content: "非常规变更增减单，请设置干系人！"
	        });
	        return;
	    }
	    if ($("#dataTable").jqGrid('getGridParam', 'records') == 0 /* && $("#m_umState").val() == 'A' */) {
	        art.dialog({
	            content: "不允许保存空的增减明细！"
	        });
	        return;
	    }
	    if ('${itemChg.status}' == '3' && $("#m_umState").val() == 'A') {
	        art.dialog({
	            content: "不能新增取消状态的增减项！"
	        });
	        return;
	    }
	    if (parseFloat($("#discAmount").val()) > 0 && parseFloat($("#discCost").val()) < 0) {
	        art.dialog({
	            content: "优惠金额为正，其中产品优惠不能为负！"
	        });
	        return;
	    }
	    if (parseFloat($("#discAmount").val()) < 0 && parseFloat($("#discCost").val()) > 0) {
	        art.dialog({
	            content: "优惠金额为负，其中产品优惠不能为正！"
	        });
	        return;
	    }
	    if (Math.abs($("#discAmount").val()) < Math.abs($("#discCost").val())) {
	        art.dialog({
	            content: "其中产品优惠不能超过优惠金额！"
	        });
	        return;
	    }

        // 修复之前提示数量为0时，提示框一闪而过问题
        var quantities = $("#dataTable").getCol("qty");
        if (quantities.indexOf("0") !== -1) {
            art.dialog({
                content: '本次增减单中存在数量为0的记录,是否保存？',
                lock: true,
                ok: function () {
                    promptManageFeeAndTax(status);
                },
                cancel: function () {
                    return true;
                }
            });
        } else {
            promptManageFeeAndTax(status);
        }	
	}
    
    // 添加税金与参考税金等值判断，重构之前只判断管理费与参考管理费是否等值的情况
    // 张海洋 20200608
    function promptManageFeeAndTax(status) {
        var manageFee = parseInt($("#manageFee").val())
        var manageFeePer = parseInt($("#manageFeePer").val())
        var tax = parseInt($("#tax").val())
        var referenceTax = parseInt($("#referenceTax").val())
    
        var prompt = ''
    
        if (manageFee !== manageFeePer && tax !== referenceTax) {
            prompt = '管理费与参考管理费不相等，税金与参考税金不相等，是否保存？'
        } else if (manageFee !== manageFeePer) {
            prompt = '管理费与参考管理费不相等,是否保存？'
        } else if (tax !== referenceTax) {
            prompt = '税金与参考税金不相等,是否保存？'
        }
    
        if (manageFee !== manageFeePer || tax !== referenceTax) {
            art.dialog({
                content: prompt,
                lock: true,
                ok: function() {
                    doSave(status)
                },
                cancel: function() {
                    return true
                }
            })
        } else {
            doSave(status)
        }
    }
    
    function updateDiscount() {
    if($.trim("${itemChg.itemType1}")=="RZ"){
      	 var itemSetNoList = Global.JqGrid.allToJson("dataTable","itemsetno");
     	 Global.Dialog.showDialog("itemChgUpdateDiscount", {
	        title: "批量折扣调整",
	        url: "${ctx}/admin/itemChg/goUpdateDiscountRz",
	        postData:{itemSetNoList:JSON.stringify(itemSetNoList.fieldJson)},
	        height: 600,
	        width: 1000,
	        resizable: false,
	        returnFun: function (data) { 
      			var tableDatas = Global.JqGrid.allToJson("dataTable");
      			var total = 0;
      			var disc=0.0;
      			for(var i=0;i<tableDatas.datas.length;i++){
      				if($.trim(tableDatas.datas[i].itemsetno )== $.trim(data[0])){
      			 		total+=myRound(tableDatas.datas[i].beflineamount,2);
      			 	}
      			}
				if (data.length != null) {
					var ids = $("#dataTable").jqGrid("getDataIDs");
					var lastIds="";
					var itemSetCount=0;
					var lastLineAmount;
		            $.each(ids, function (k, v) {
						var rowData = $("#dataTable").jqGrid('getRowData', v);
						if (!rowData.reqpk && $.trim(data[0]) == $.trim(rowData.itemsetno) ) {
							 var lineAmount = myRound(myRound(myRound(rowData.qty * rowData.unitprice, 4) * myRound(data[1]/total,4)) + parseFloat(rowData.processcost));
							$("#dataTable").setCell(v, 'markup', myRound(data[1]/total,4)*100);
							$("#dataTable").setCell(v, 'tmplineamount', myRound(myRound(rowData.qty * rowData.unitprice, 4) * myRound(data[1]/total,4)));
							$("#dataTable").setCell(v, 'lineamount', lineAmount);
							lastIds=v;
							itemSetCount += lineAmount;
							lastLineAmount = lineAmount;
						}
					});
					if(itemSetCount - data[1] != 0){
						var rowDatas = $("#dataTable").jqGrid('getRowData', lastIds);
						$("#dataTable").setCell(lastIds, 'lineamount', lastLineAmount - (itemSetCount - data[1]));
						$("#dataTable").setCell(lastIds, 'tmplineamount', 
							myRound(myRound(rowDatas.qty * rowDatas.unitprice, 4) * myRound((lastLineAmount - (itemSetCount - data[1]))/rowDatas.beflineamount ,4)));
						$("#dataTable").setCell(lastIds, 'markup', myRound((lastLineAmount - (itemSetCount - data[1]))/rowDatas.beflineamount ,4)*100);
					}

					var tmplineamount = myRound($("#dataTable").getCol('tmplineamount', false, 'sum'));
					var lineamount = myRound($("#dataTable").getCol('lineamount', false, 'sum'));
					var discAmount = parseFloat($("#discAmount").val());
					$("#dataTable").footerData('set', {"tmplineamount": tmplineamount});
					$("#dataTable").footerData('set', {"lineamount": lineamount});
					$("#befAmount").val(lineamount);
		            if (discAmount) {
						if (lineamount >= 0)  $("#amount").val(lineamount - discAmount);
						else $("#amount").val(lineamount + discAmount);
		            } else {
		            	$("#amount").val(lineamount);
		            }
				}
				selRowNum = -1;
	        }
	      });
      }else{
      Global.Dialog.showDialog("itemChgUpdateDiscount", {
        title: "批量折扣调整",
        url: "${ctx}/admin/itemChg/goUpdateDiscount",
        height: 600,
        width: 1000,
        resizable: false,
        returnFun: function (data) {
          if (data.length != null) {
            var ids = $("#dataTable").jqGrid("getDataIDs");
            $.each(ids, function (k, v) {
              var rowData = $("#dataTable").jqGrid('getRowData', v);
              if (!rowData.reqpk) {
                $("#dataTable").setCell(v, 'markup', data);
                $("#dataTable").setCell(v, 'tmplineamount', myRound(myRound(rowData.qty * rowData.unitprice, 4) * data / 100));
                $("#dataTable").setCell(v, 'lineamount', myRound(myRound(myRound(rowData.qty * rowData.unitprice, 4) * data / 100) + parseFloat(rowData.processcost)));
              }


            })

            var tmplineamount = myRound($("#dataTable").getCol('tmplineamount', false, 'sum'));
            var lineamount = myRound($("#dataTable").getCol('lineamount', false, 'sum'));
            var discAmount = parseFloat($("#discAmount").val());
            $("#dataTable").footerData('set', {"tmplineamount": tmplineamount});
            $("#dataTable").footerData('set', {"lineamount": lineamount});
            $("#befAmount").val(lineamount);
            if (discAmount) {
              if (lineamount >= 0)  $("#amount").val(lineamount - discAmount);
              else $("#amount").val(lineamount + discAmount);
            } else {
              $("#amount").val(lineamount);
            }
          }
          selRowNum = -1;
        }
      });
      }
    }
    function changeAmount() {
      var lineamount = myRound($("#dataTable").getCol('lineamount', false, 'sum'));
      var discAmount = parseFloat($("#discAmount").val());
      if (discAmount) {
        if (lineamount >= 0)  $("#amount").val(lineamount - discAmount);
        else $("#amount").val(lineamount + discAmount);
      } else {
        $("#amount").val(lineamount);
      }


    }
    function goTransAction() {
      var gridDatas = $("#dataTable").jqGrid("getRowData");
      reqpk = [];
      $.each(gridDatas, function (k, v) {
        if (v.reqpk && v.reqpk != '0') reqpk.push(v.reqpk);
      })
      Global.Dialog.showDialog("itemChg_transAction", {
        title: "材料增减明细--已有项变动",
        url: "${ctx}/admin/itemChg/goTransAction?itemType1=${itemChg.itemType1}&custCode=${itemChg.custCode}&isService=" + $("#isService").val() + "&isCupboard=" + $("#isCupboard").val(),
        height: 750,
        width: 1350,
        resizable: false,
        returnFun: function (data) {
          if (data.selectRows.length) {
            var orderBy = "${itemChg.itemType1}".trim() == 'RZ' ? 'itemtype2descr' : $("#itemType2Group").is(":checked") ? 'itemtype2descr' : 'dispseq';
            if ("${itemChg.itemType1}".trim() == 'ZC' && $("#isService").val() == '1') {
              //默认提成
              $.each(data.selectRows, function (k, v) {
                maxDispSeq++;
                v.iscommi = "1";
                v.lastupdate = getNowFormatDate();
                v.lastupdatedby = '${itemChg.lastUpdatedBy}';
                v.actionlog = "ADD";
                v.dispseq = maxDispSeq;
              })
            } else {
              $.each(data.selectRows, function (k, v) {
                maxDispSeq++;
                v.lastupdate = getNowFormatDate();
                v.lastupdatedby = '${itemChg.lastUpdatedBy}';
                v.actionlog = "ADD";
                v.dispseq = maxDispSeq;
              })
            }
            var params = JSON.stringify($("#dataTable").jqGrid("getRowData").concat(data.selectRows));
            $("#dataTable").jqGrid("setGridParam", {
              url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
              postData: {'params': params, 'orderBy': orderBy},
              page: 1
            }).trigger("reloadGrid");
          }
          selRowNum = -1;
        }
      });
    }
    function del() {
      var rowId = $('#dataTable').jqGrid('getGridParam', 'selrow');
      var ret = selectDataTableRow();
	  var ids = [];//$("#dataTable").jqGrid("getGridParam", "selarrrow");
	 	
	  var rowNum = $("#dataTable").jqGrid('getGridParam', 'records');
	  
      for(var i = 1; i < rowNum+1; i++){
    	  if($("#jqg_dataTable_"+i).prop("checked")){
	    	  ids[ids.length] = i;
    	  }
      }
      
	  // 排序
	  //ids = getDescendingOrder(ids);
	  var itemDescrs = "";
	  var sMsg="确定删除该记录吗？";
		if(ids && ids.length>0){
			for(var i = 0;i < ids.length; i++){
				var rowData = $("#dataTable").jqGrid('getRowData', ids[i]);			  
		  		if(rowData.reqpk>0&&rowData.refchgreqpk>0){
		  			if(itemDescrs == ""){
		  				itemDescr = rowData.itemdescr;
		  			} else {
		  				itemDescr += ","+rowData.itemdescr;
		  			}
		  		}
			}
      		if(itemDescrs != ""){
      			sMsg = "删除以下项目："+itemDescr+"同时，将删除对应的替换项目，删除所选记录吗？";
      		}
      		
      		art.dialog({
                content: sMsg,
                lock: true,
                ok: function () {
                  var ret = selectDataTableRow();
                  var rowNum = $("#dataTable").jqGrid('getGridParam', 'records');
                  //$('#dataTable').delRowData(rowId);
		            var delIds =[];
                  
                  for(var i = 0;i < ids.length; i++){
      				var data = $("#dataTable").jqGrid('getRowData', ids[i]);
      				
      		  		if(data.reqpk>0 && data.refchgreqpk>0){
	      		  		var rowData=$("#dataTable").jqGrid("getRowData");
	  		  			$.each(rowData,function(k,v){
	  		  				if(v.refchgreqpk==data.refchgreqpk){
	  		  					if(delIds.indexOf(k+1)==-1){
	  		  						delIds.push(k+1);
	  		  					}
	  		  				}		
	  		  			});
      		  		} else {
      		  			if(delIds.indexOf(ids[i])==-1){
	  						delIds.push(ids[i]);
	  					}
      		  		}
      			  }
                  
                  delIds = getDescendingOrder(delIds);

                  for(i = 0; i < delIds.length; i++){
                	  $('#dataTable').delRowData(delIds[i]);
                  }
                  
                  //$('#dataTable').delRowData(ids[i]);
                  /*
                  if(ret.reqpk>0&&ret.refchgreqpk>0){
         				var rowData=$("#dataTable").jqGrid("getRowData");
      		  			$.each(rowData,function(k,v){
      		  				if(v.refchgreqpk==ret.refchgreqpk){
      		  					$('#dataTable').delRowData(k+1);
      		  				}		
      		  			});
         		  }else{
         		  	$('#dataTable').delRowData(rowId);
         		  }*/
                  var params = JSON.stringify($("#dataTable").jqGrid("getRowData"));
                  selRowNum = -1;

                  $("#dataTable").jqGrid("setGridParam", {
                    url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
                    postData: {'params': params},
                    page: 1
                  }).trigger("reloadGrid");
                  /*
                  setTimeout(function () {
                    $("#delBtn").focus();
                  }, 100);*/
                },
                cancel: function () {
                  return true;
                }
              });
		} else {
        	art.dialog({
         	   content: "请选择一条或多条记录"
          	});
        }
		
	  /*if(ret.reqpk>0&&ret.refchgreqpk>0){
    	  sMsg="删除该项目的同时，将删除对应的替换项目,确定删除该记录吗？";
      }
      if (rowId) {
        art.dialog({
          content: sMsg,
          lock: true,
          ok: function () {
            var ret = selectDataTableRow();
            var rowNum = $("#dataTable").jqGrid('getGridParam', 'records');
            //$('#dataTable').delRowData(rowId);
            if(ret.reqpk>0&&ret.refchgreqpk>0){
   				var rowData=$("#dataTable").jqGrid("getRowData");
		  		$.each(rowData,function(k,v){
		  			if(v.refchgreqpk==ret.refchgreqpk){
		  				$('#dataTable').delRowData(k+1);
		  			}		
		  		});
   			 }else{
   			 	$('#dataTable').delRowData(rowId);
   			}
            var params = JSON.stringify($("#dataTable").jqGrid("getRowData"));
            $("#dataTable").jqGrid("setGridParam", {
              url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
              postData: {'params': params},
              page: 1
            }).trigger("reloadGrid");
            setTimeout(function () {
              moveToNext(rowId, rowNum);
              $("#delBtn").focus();
            }, 100);
          },
          cancel: function () {
            return true;
          }
        });
      } else {
        art.dialog({
          content: "请选择一条记录"
        });
      }*/
    }
    
    function getDescendingOrder(ids){
    	if(!ids && !ids.length > 0 ){
    		return ids;
    	}
    	var flag = false;
		for(var i = 0; i < ids.length; i++){
			flag = false
			for(var j = i + 1; j < ids.length; j++){
				if(myRound(ids[i]) < myRound(ids[j])){
					var x = ids[i];
					ids[i] = ids[j];
					ids[j] = x;
					flag = true;
				}
			}
		} 
		return ids;
    }
    
    function getReqPk() {

      return reqpk;
    }
    function getItemAppDetail() {

      return itemAppDetail;
    }
    function view() {
      var rowId = $('#dataTable').jqGrid('getGridParam', 'selrow');
      if (rowId || selRowNum != -1) {
    	  if(!rowId){
        	  rowId = selRowNum;
    	  }
        Global.Dialog.showDialog("itemChg_detailView", {
          title: "增减明细--查看",
          url: "${ctx}/admin/itemChg/goItemChg_detailView?itemType1=${itemChg.itemType1}&custCode=${itemChg.custCode}&isService=" + $("#isService").val() + "&rowId=" + rowId,
          height: 600,
          width: 1000,
          returnFun: function (data) {
            if (data.fixareapk) {
              $('#dataTable').setRowData(rowId, data);
              var params = JSON.stringify($("#dataTable").jqGrid("getRowData"));
              $("#dataTable").jqGrid("setGridParam", {
                url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
                postData: {'params': params},
                page: 1
              }).trigger("reloadGrid");
            }
            selRowNum = -1;
          }
        });
      } else {
        art.dialog({
          content: "请选择一条记录"
        });
      }
    }
    function edit() {
      var rowId = $('#dataTable').jqGrid('getGridParam', 'selrow');

      if (rowId || selRowNum != -1) { 
		if(!rowId){
    	  rowId = selRowNum;
		}
        var reqPk = $('#dataTable').getCell(rowId, "reqpk");
        var itemCode = $('#dataTable').getCell(rowId, "itemcode");
        var itemSetNo = $('#dataTable').getCell(rowId, "itemsetno").trim();
        if (itemSetNo) {
          art.dialog({
            content: "套餐包材料不允许编辑！"
          });
          return;
        }
        Global.Dialog.showDialog("itemChg_detailViewEdit", {
          title: "增减明细--编辑",
          url: "${ctx}/admin/itemChg/goItemChg_detailViewEdit?itemType1=${itemChg.itemType1}&custCode=${itemChg.custCode}&isOutSet=${itemChg.isOutSet}&isService=" + $("#isService").val() + "&rowId=" + rowId + "&reqPk=" + reqPk + "&itemCode=" + itemCode,
          height: 600,
          width: 1000,
          resizable: false,
          returnFun: function (data) {
            if (data.fixareapk) {
              data.lastupdatedby = '${itemChg.lastUpdatedBy}';
              $('#dataTable').setRowData(rowId, data);
              var params = JSON.stringify($("#dataTable").jqGrid("getRowData"));
              $("#dataTable").jqGrid("setGridParam", {
                url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
                postData: {'params': params},
                page: 1
              }).trigger("reloadGrid");
            }
            selRowNum = -1;
          }
        });
      } else {
        art.dialog({
          content: "请选择一条记录"
        });
      }
    }
    function showTmplineAmount() {
      Global.Dialog.showDialog("itemChg_tmplineAmountView", {
        title: "查看小计",
        url: "${ctx}/admin/itemChg/goItemChg_tmplineAmountView",
        height: 600,
        width: 1000
      });
    }
    function doConfirm(status, warn) {
      if ($("#dataTable").jqGrid('getGridParam', 'records') == 0 && $("#m_umState").val() == 'A') {
        art.dialog({
          content: "新增时不允许增减明细为空！"
        });
        return;
      }
      if ('${itemChg.status}' == '3' && $("#m_umState").val() == 'A') {
        art.dialog({
          content: "不能新增取消状态的增减项！"
        });
        return;
      }
      if (parseFloat($("#discAmount").val()) > 0 && parseFloat($("#discCost").val()) < 0) {
        art.dialog({
          content: "优惠金额为正，其中产品优惠不能为负！"
        });
        return;
      }
      if (parseFloat($("#discAmount").val()) < 0 && parseFloat($("#discCost").val()) > 0) {
        art.dialog({
          content: "优惠金额为负，其中产品优惠不能为正！"
        });
        return;
      }
      if (Math.abs($("#discAmount").val()) < Math.abs($("#discCost").val())) {
        art.dialog({
          content: "其中产品优惠不能超过优惠金额！"
        });
        return;
      }
      $.ajax({
        url: '${ctx}/admin/itemChg/isExistsNotSendItemList',
        type: "post",
        dataType: "json",
        async: false,
        data: {'custCode': '${itemChg.custCode}', 'gridData': JSON.stringify($("#dataTable").jqGrid("getRowData"))},
        cache: false,
        success: function (obj) {
          if (obj.length > 0) {
            itemAppDetail = obj;
            Global.Dialog.showDialog("itemChg_itemAppDetail", {
              title: "未发货领料",
              url: "${ctx}/admin/itemChg/goItemChg_itemAppDetail",
              height: 600,
              width: 1000
            });
          } else {
            art.dialog({
              content: "是否要审核" + warn + "该增减单?",
              ok: function () {
                var datas = $("#dataForm").jsonForm();
                datas.detailJson = JSON.stringify($("#dataTable").jqGrid("getRowData"));
                if (status == 2) url = '${ctx}/admin/itemChg/doComfirm';
                else  url = '${ctx}/admin/itemChg/doComfirmCancel';
                $.ajax({
                  url: url,
                  type: 'post',
                  data: datas,
                  dataType: 'json',
                  cache: false,
                  error: function (obj) {
                    showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
                  },
                  success: function (obj) {
                    if (obj.rs) {
                      art.dialog({
                        content: obj.msg,
                        time: 1000,
                        beforeunload: function () {
                          closeWin(true, true);
                        }
                      });
                    } else {
                      $("#_form_token_uniq_id").val(obj.token.token);
                      art.dialog({
                        content: obj.msg,
                        width: 200
                      });
                    }
                  }
                });
              },
              cancel: function () {

              }
            });

          }


        }
      });

    }
    //校验函数
    $(function () {
      $("#viewSwitchNum").hide();
    
      if("${needRefCustCode }"=="true"){
		  $(".ref").removeClass("hidden");
	  }else{
		  $(".ref").addClass("hidden");
	  }
      changeFaultType();
      $("#refCustCode").openComponent_customer({showValue:"${itemChg.refCustCode }",showLabel:"${refCustCodeDescr}",readonly:true});

      $('*', '#dataForm').bind('focus', function () {
        data_iRow = 0;
      });
      $("#itemType1").attr("disabled", "disabled");
      var gridHeight = $(document).height() - $("#content-list").offset().top - 67;
      var jqGridOption = {
        url: "${ctx}/admin/itemChgDetail/goJqGrid?no=${itemChg.no}",
        height: gridHeight,
        cellEdit: true,
        cellsubmit: 'clientArray',
        styleUI: 'Bootstrap',
		multiselect:true,
        rowNum: 10000,
		colModel : [
		  {name: "iscommi", index: "iscommi", width: 84, label: "是否提成", sortable: true, align: "left", hidden: true},
		  {name: "iscommichx", index: "iscommichx", width: 84, label: "是否提成", sortable: true, align: "left", hidden: true,formatter:commiCheckBox},
		  {name: "fixareadescr", index: "fixareadescr", width: 92, label: "区域名称", sortable: true, align: "left",editable:true,edittype:'text',editoptions:{ dataEvents:[{type:'focus',fn:function(e){ $(e.target).openComponent_fixAreaDescr({condition:{'itemType1':"${itemChg.itemType1}".trim(),'custCode':'${itemChg.custCode}','isService':$("#isService").val()},onSelect:onFixAreaSelect});}}]}},
		  {name: "itemcode", index: "itemcode", width: 60, label: "材料编号", sortable: true, align: "left"},
		  {name: "fixareapk", index: "fixareapk", width: 92, label: "区域名称", sortable: true, align: "left", hidden: true},
		  {name: "intprodpk", index: "intprodpk", width: 90, label: "集成成品", sortable: true, align: "left", hidden: true},
		  {name: "intproddescr", index: "intproddescr", width: 90, label: "集成成品", sortable: true, align: "left", hidden: true},
		  {name: "itemdescr", index: "itemdescr", width: 210, label: "材料名称", sortable: true, align: "left",classes: "itemdescr",cellattr: addCellAttr,editable:true,edittype:'text',editoptions:{ dataEvents:[{type:'focus',fn:function(e){ $(e.target).openComponent_itemDescr({condition:{'itemType1':"${itemChg.itemType1}".trim(),'containCode':"1",'custType':"${itemChg.custType}"},onSelect:onSelect});}}]}},
		  {name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
		  {name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
		  {name: "sqlcodedescr", index: "sqlcodedescr", width: 89, label: "品牌", sortable: true, align: "left", hidden: true},
		  {name: "itemtype3descr", index: "itemtype3descr", width: 85, label: "材料类型3", sortable: true, align: "left", hidden: true},
		  {name: "preqty", index: "preqty", width: 65, label: "已有数量", sortable: true, align: "right"},
		  {name: "algorithmdescr", index: "algorithmdescr", width: 90, label: "算法", sortable: true, align: "left",editable:true,hidden:($.trim('${itemChg.itemType1}')== 'RZ'|| $.trim('${itemChg.itemType1}')== 'JC')?true:false,
		  		edittype:'select',
		  		editoptions:{ 
		  			dataUrl: "${ctx}/admin/item/getAlgorithm",
					postData: function(){
						var ret = selectDataTableRow("dataTable");
						if(ret && ret.reqpk == ""){
							return {
								code: ret.itemcode
							};
						}
						return {code: ""};
					},
					buildSelect: function(e){
						var lists = JSON.parse(e);
						var html = "<option value=\"\" ></option>";
							for(var i = 0; lists && i < lists.length; i++){
								html += "<option value=\""+ lists[i].Code +"\">" + lists[i].Descr + "</option>"
							}
							return "<select style=\"font-family:宋体;\"> " + html + "</select>";
					},
		  			dataEvents:[{
	  					type:'change',
	  					fn:function(e){ 
	  						algorithmClick(e)
	  					}
		  			}, 
		  		]}
	 	},
		  {name: "qty", index: "qty", width: 70, label: "变动数量", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}, sum: true},
		  {name: "uom", index: "uom", width: 40, label: "单位", sortable: true, align: "left", hidden: true},
		  {name: "uomdescr", index: "uomdescr", width: 40, label: "单位", sortable: true, align: "left"},
		  {name: "marketprice", index: "marketprice", width: 68, label: "市场价", sortable: true, align: "right", hidden: true},
		  {name: "unitprice", index: "unitprice", width:50, label: "单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
		  {name: "beflineamount", index: "beflineamount", width: 80, label: "折扣前金额", sortable: true, align: "right", sum: true},
		  {name: "markup", index: "markup", width: 40, label: "折扣", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true,integer:false,minValue:0}},
		  {name: "tmplineamount", index: "tmplineamount", width: 65, label: "小计", sortable: true, align: "right", sum: true},
		  {name: "processcost", index: "processcost", width: 60, label: "其他费用", sortable: true, align: "right", sum: true,editable:true,edittype:'text',editrules:{number:true,required:true}},
		  {name: "lineamount", index: "lineamount", width: 70, label: "总价", sortable: true, align: "right", sum: true},
		  {name: "isoutsetdescr", index: "isoutsetdescr", width: 50, label: "套外", sortable: true, align: "left", hidden: true},
		  {name: "disccost", index: "disccost", width: 104, label: "优惠分摊成本", sortable: true, align: "left", sum: true, hidden: true},
		  {name: "remarks", index: "remarks", width: 177, label: "材料描述", sortable: true, align: "left",classes: "remarks", editable:true,edittype:'textarea'},
		  {name: "itemsetno", index: "itemsetno", width: 100, label: "套餐包 ", sortable: true, align: "left", hidden: true},
		  {name: "itemsetdescr", index: "itemsetdescr", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
		  {name: "supplpromdescr", index: "supplpromdescr", width: 118, label: "促销活动", sortable: true, align: "left",},
		  {name: "preplanareapk", index: "preplanareapk", width: 85, label: "空间pk", sortable:false, align: "left", hidden: true},
		  {name: "preplanareadescr", index: "preplanareadescr", width: 70, label: "空间名称", sortable:false, align: "left"},
		  {name: "cuttypedescr", index: "cuttypedescr", width: 70, label: "切割类型",  width: 70,sortable:false, align: "left", editable:true,hidden:($.trim('${itemChg.itemType1}')== 'RZ'|| $.trim('${itemChg.itemType1}')== 'JC')?true:false,
			  		edittype:'select',
			  		editoptions:{ 
			  			dataUrl: "${ctx}/admin/prePlanTempDetail/getQtyByCutType",
						postData: function(){
							var ret = selectDataTableRow("dataTable");
							if(ret){
								return {
									itemCode: ret.itemcode
								};
							}
							return {itemCode: ""};
						},
						buildSelect: function(e){
							var ret = selectDataTableRow("dataTable");

							var lists = JSON.parse(e);
							var html = "<option value=\"\" ></option>";
							
							for(var i = 0; lists && i < lists.length; i++){
								html += "<option value=\""+ lists[i].Code +"\">" + lists[i].fd + "</option>"
							}
							return "<select style=\"font-family:宋体;\"> " + html + "</select>";
						},
			  			dataEvents:[{
		  					type:'change',
		  					fn:function(e){ 
		  						cuttypeClick(e);
		  					}
			  			}, 
			  		]}
		 		},
		  {name: "cuttype", index: "cuttype", width: 65, label: "切割类型编号", width: 0.5,sortable:false, align: "left"},
	      {name: "algorithmper", index: "algorithmper", width: 80, label: "算法系数", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true},hidden:($.trim('${itemChg.itemType1}')== 'RZ'|| $.trim('${itemChg.itemType1}')== 'JC')?true:false},
		  {name: "algorithmdeduct", index: "algorithmdeduct", width: 80, label: "算法扣除数", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true},hidden:($.trim('${itemChg.itemType1}')== 'RZ'|| $.trim('${itemChg.itemType1}')== 'JC')?true:false},
		  {name: "lastupdate", index: "lastupdate", width: 85, label: "最后修改日期", sortable: true, align: "left", formatter: formatTime},
		  {name: "lastupdatedby", index: "lastupdatedby", width: 78, label: "修改人", sortable: true, align: "left"},
		  {name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
		  {name: "actionlog", index: "actionlog", width: 68, label: "操作", sortable: true, align: "left"},
		  {name: "reqpk", index: "reqpk", width: 0.5, label: "需求PK", sortable: false, align: "left", hidden: true},
		  {name: "isfixprice", index: "isfixprice", width: 20, label: "是否固定价格", sortable: true, align: "left", hidden: true},
		  {name: "isoutset", index: "isoutset", width: 20, label: "套餐外材料", sortable: true, align: "left", hidden: true},
		  {name: "isservice", index: "isservice", width: 20, label: "是否服务性产品", sortable: true, align: "left", hidden: true},
		  {name: "pk", index: "pk", width: 20, label: "编号", sortable: true, align: "left", hidden: true},
		  {name: "no", index: "no", width: 20, label: "增减单号", sortable: true, align: "left", hidden: true},
		  {name: "cost", index: "cost", width: 20, label: "成本", sortable: true, align: "left", hidden: true},
		  {name: "custtypeitempk", index: "custtypeitempk", width: 0.5, label: "套餐材料信息编号", sortable: true, align: "left"},
		  {name: "dispseq", index: "dispseq", width: 68, label: "操作", sortable: true, align: "left", hidden: true},
		  {name: "supplpromitempk", index: "supplpromitempk", width: 68, label: "供应商促销PK", sortable: true, align: "left",},
		  {name: "doorpk", index: "doorpk", width: 65, label: "门窗Pk",  width: 0.5,sortable:false, align: "left"},
		  {name: "algorithm", index: "algorithm", width: 0.5, label: "算法编号",  sortable:false, align: "left"},
		  {name: "refchgreqpk", index: "refchgreqpk", width: 65, label: "相关需求pk",  width: 0.5,sortable:false, align: "left"},
		  {name: "itemtype12", index: "itemtype12", width: 65, label: "itemtype12",  width: 15,sortable:false, align: "left" ,hidden:true},
		],
        grouping: false, // 是否分组,默认为false
        groupingView: {
          groupField: ['itemtype2descr'], // 按照哪一列进行分组
          groupColumnShow: [false], // 是否显示分组列
          groupText: ['<b>材料类型2：{0}({1})</b>'], // 表头显示的数据以及相应的数据量
          groupCollapse: false, // 加载数据时是否只显示分组的组信息
          groupDataSorted: true, // 分组中的数据是否排序
          groupOrder: ['asc'], // 分组后的排序
          groupSummary: [false], // 是否显示汇总.如果为true需要在colModel中进行配置
          summaryType: 'max', // 运算类型，可以为max,sum,avg</div>
          summaryTpl: '<b>Max: {0}</b>',
          showSummaryOnHide: true //是否在分组底部显示汇总信息并且当收起表格时是否隐藏下面的分组
        },
        gridComplete: function () {
        	if($("#goTop").attr("disabled")=="disabled"){
	        	$("#goTop").removeAttr("disabled","disabled");
			}
        	
			if($("#goBottom").attr("disabled")=="disabled"){
	        	$("#goBottom").removeAttr("disabled","disabled");
			}
			
        	if(selRowNum && selRowNum != -1){
	        	$("#dataTable").jqGrid('setSelection', selRowNum, true);  
        	}
          if (!maxDispSeq && $("#dataTable").jqGrid('getGridParam', 'records') > 0) {
            var rowData = $("#dataTable").jqGrid('getRowData', $("#dataTable").jqGrid('getGridParam', 'records'));
            maxDispSeq = rowData.dispseq;
          }
          var lineamount = myRound($("#dataTable").getCol('lineamount', false, 'sum'));
          var discAmount = parseFloat($("#discAmount").val());
          if (discAmount) {
            if (lineamount >= 0)  $("#amount").val(lineamount - discAmount);
            else $("#amount").val(lineamount + discAmount);
          } else {
            $("#amount").val(lineamount);
          }
          $("#befAmount").val(lineamount);
          calculateTax();
          if ($("#dataTable").jqGrid('getGridParam', 'records') > 0) {
            if ('${itemChg.itemType1}'.trim() == 'JC')  $("#isCupboardDescr").attr("disabled", "disabled");
            else $("#isServiceDescr").attr("disabled", "disabled");
            SetMinus = 0;
            var datas = $("#dataTable").jqGrid("getRowData");
            $.each(datas, function (k, v) {
              //套餐材料，不允许直接在材料名称里修改
              if (v.isoutset != "1")  $("#dataTable").jqGrid('setCell', k + 1, 'itemdescr', '', 'not-editable-cell');
              if ("${itemChg.itemType1}".trim() == 'ZC' && $("#isService").val() == '1') {
                //提成标记黄色
                if (v.iscommi == "1") $("#dataTable tbody:first tr#" + (k + 1)).find("td").addClass("commiColor");
              }
              if (parseFloat(v.qty) < 0) SetMinus += parseFloat(v.lineamount);
              if (v.isfixprice == '1' || v.isoutset == '0' || (v.reqpk && v.reqpk != 0)) {
                $("#dataTable").jqGrid('setCell', k + 1, 'unitprice', '', 'not-editable-cell');
              }
              if (v.reqpk && v.reqpk != 0) {
                $("#dataTable").jqGrid('setCell', k + 1, 'unitprice', '', 'not-editable-cell');
                $("#dataTable").jqGrid('setCell', k + 1, 'markup', '', 'not-editable-cell');
                $("#dataTable").jqGrid('setCell', k + 1, 'remarks', '', 'not-editable-cell');
              }
              if (v.reqpk) {
                $("#dataTable").jqGrid('setCell', k + 1, 'itemdescr', '', 'not-editable-cell');
                $("#dataTable").jqGrid('setCell', k + 1, 'fixareadescr', '', 'not-editable-cell');
                $("#dataTable").jqGrid('setCell', k + 1, 'algorithmdescr', '', 'not-editable-cell');
                $("#dataTable").jqGrid('setCell', k + 1, 'cuttypedescr', '', 'not-editable-cell');
              }
            })

            if ('${itemChg.itemType1}'.trim() == 'ZC' && ($("#isCupboard").val() == '0' || !$("#isCupboard").val())) {
              $("#manageFeePer").val(myRound((parseFloat($("#amount").val()) - SetMinus) *${itemChg.manageFeeMainPer} *${itemChg.chgManageFeePer}));
            } else if ('${itemChg.itemType1}'.trim() == 'ZC' && $("#isCupboard").val() == '1') {
              $("#manageFeePer").val(myRound((parseFloat($("#amount").val()) - SetMinus) *${itemChg.manageFeeServPer} *${itemChg.chgManageFeePer}));
            } else if ('${itemChg.itemType1}'.trim() == 'JC' && $("#isCupboard").val() == '1') {
              $("#manageFeePer").val(myRound((parseFloat($("#amount").val()) - SetMinus) *${itemChg.manageFeeCupPer} *${itemChg.chgManageFeePer}));

            }
          } else {
            if ('${itemChg.itemType1}'.trim() == 'JC')  $("#isCupboardDescr").removeAttr("disabled");
            if ('${itemChg.itemType1}'.trim() == 'ZC')  $("#isServiceDescr").removeAttr("disabled");
            $("#manageFeePer").val(0);
          }
          
            $('.itemdescr').on('mousedown', function(event) {
                event.stopPropagation()
            })
            
            $('.remarks').on('mousedown', function(event) {
                event.stopPropagation()
            })

            existsSetDeduction()
        },
        afterSaveCell: function (id, name, val, iRow, iCol) {
          $("#dataTable").setCell(id, 'lastupdatedby', '${itemChg.lastUpdatedBy}');
          if (name == 'itemdescr')
            $("#dataTable").setCell(id, 'itemdescr', itemdescr);
          
          $("#dataTable").setCell(id, 'algorithmdescr', algorithmdescr);
          var rowData = $("#dataTable").jqGrid('getRowData', id);
          var processcost = parseFloat(rowData.processcost);
          switch (name) {
            case 'qty':
              $("#dataTable").setCell(id, 'beflineamount', myRound(val * rowData.unitprice, 4));
              $("#dataTable").setCell(id, 'tmplineamount', myRound(myRound(val * rowData.unitprice, 4) * rowData.markup / 100));
              $("#dataTable").setCell(id, 'lineamount', myRound(myRound(myRound(val * rowData.unitprice, 4) * rowData.markup / 100) + processcost));
              $("#dataTable").footerData('set', {"qty": $("#dataTable").getCol('qty', false, 'sum')});
              if($.trim(rowData.cuttype) != "" && $.trim(rowData.isoutset)=="1"){
            	  changeAlgorithm(id,rowData.cuttype,"");
              }
              break;
            case 'unitprice':
              $("#dataTable").setCell(id, 'beflineamount', myRound(val * rowData.qty, 4));
              $("#dataTable").setCell(id, 'tmplineamount', myRound(myRound(val * rowData.qty, 4) * rowData.markup / 100));
              $("#dataTable").setCell(id, 'lineamount', myRound(myRound(myRound(val * rowData.qty, 4) * rowData.markup / 100) + processcost));
              break;
            case 'markup':
              $("#dataTable").setCell(id, 'tmplineamount', myRound(myRound(rowData.qty * rowData.unitprice, 4) * val / 100));
              $("#dataTable").setCell(id, 'lineamount', myRound(myRound(myRound(rowData.qty * rowData.unitprice, 4) * val / 100) + processcost));
              break;
            case 'processcost':
              $("#dataTable").setCell(id, 'lineamount', myRound(myRound(rowData.tmplineamount) + processcost));
              break;
            case 'algorithmper':
	           	if(rowData.algorithm != '' ){
	           		changeAlgorithm(id, rowData.algorithm, "algorithm" );			
	               }
	       		break;
    		case 'algorithmdeduct':
	        	if(rowData.algorithm !='' ){
	        		changeAlgorithm(id, rowData.algorithm, "algorithm" );			
                }
        		break;
          }
          var beflineamount = $("#dataTable").getCol('beflineamount', false, 'sum');
          var tmplineamount = $("#dataTable").getCol('tmplineamount', false, 'sum');
          var processcost = $("#dataTable").getCol('processcost', false, 'sum');
          var lineamount = $("#dataTable").getCol('lineamount', false, 'sum');
          var discAmount = parseFloat($("#discAmount").val());
          $("#dataTable").footerData('set', {"beflineamount": beflineamount});
          $("#dataTable").footerData('set', {"tmplineamount": tmplineamount});
          $("#dataTable").footerData('set', {"processcost": processcost});
          $("#dataTable").footerData('set', {"lineamount": lineamount});
          $("#befAmount").val(lineamount);
          if (discAmount) {
            if (lineamount >= 0)  $("#amount").val(lineamount - discAmount);
            else $("#amount").val(lineamount + discAmount);
          } else {
            $("#amount").val(lineamount);
          }
          calculateTax();
          if ('${itemChg.itemType1}'.trim() == 'ZC' && ($("#isCupboard").val() == '0' || !$("#isCupboard").val())) {
            $("#manageFeePer").val(myRound((parseFloat($("#amount").val()) - SetMinus) *${itemChg.manageFeeMainPer} *${itemChg.chgManageFeePer}));
          } else if ('${itemChg.itemType1}'.trim() == 'ZC' && $("#isCupboard").val() == '1') {
            $("#manageFeePer").val(myRound((parseFloat($("#amount").val()) - SetMinus) *${itemChg.manageFeeServPer} *${itemChg.chgManageFeePer}));
          } else if ('${itemChg.itemType1}'.trim() == 'JC' && $("#isCupboard").val() == '1') {
            $("#manageFeePer").val(myRound((parseFloat($("#amount").val()) - SetMinus) *${itemChg.manageFeeCupPer} *${itemChg.chgManageFeePer}));

          }
        },
        beforeEditCell: function (rowid, cellname, value, iRow, iCol) {
          data_iRow = iRow;
          data_iCol = iCol;
        },
       /* onCellSelect:function(rowid,iCol,cellcontent,e){
        	var isCheck = $("#jqg_dataTable_"+rowid).prop("checked");
        	selRowNum = rowid;
        	if(iCol != 1){
        		console.log(rowid);
	        	if(isCheck){
		        	$("#dataTable").jqGrid('setSelection', rowid, true);  
		    	} else {
		        	$("#dataTable").jqGrid('setSelection', rowid, false);  
		    	}
	    	}
        },*/
        beforeSelectRow:function (id, event){
			
			selRowNum = id;
        	var ids = $("#dataTable").jqGrid("getDataIDs");  
        	data_iCol = 0;
          	$(".search-suggest").hide();
          	itemdescr = $('#dataTable').getCell(id, "itemdescr");
          
/*           	for(var i=0;i<ids.length;i++){
				if(i!=id-1){
					$('#'+ids[i]).find("td").removeClass("SelectBG");
				}
		  	}
		  	$('#'+ids[id-1]).find("td").addClass("SelectBG"); */
		  	
		  	$('.success').removeClass('success')
            $('.SelectBG').removeClass('SelectBG')
            $('#' + id).find('td').addClass('SelectBG')
        
        	var isCheck = $("#jqg_dataTable_"+id).prop("checked");
		  	if(event.target.id != ("jqg_dataTable_"+id)){
		  		if(isCheck){
		        	$("#jqg_dataTable_"+id).prop("checked",true);
		    	} else {
		        	$("#jqg_dataTable_"+id).prop("checked",false);
		    	}
		  	}
		  	if("${itemChg.itemType1}" == "ZC"){
			  	var rowData = $("#dataTable").jqGrid('getRowData', id);  
			  	getItemType12(rowData.itemcode);
			}
		  	
		  	return false;
        }
        
      }
      //软装默认按材料类型2分类
      if ('${itemChg.itemType1}'.trim() == 'RZ') $.extend(jqGridOption, {grouping: true});
      Global.JqGrid.initJqGrid("dataTable", jqGridOption);
      
      $("#dataTable").sortableRows({update: reorderDispSeq})
      
      if ('${itemChg.itemType1}'.trim() == 'RZ') {
        $("#dataTable").setGridParam().showCol("itemsetdescr").trigger("reloadGrid");
        $(".nav-checkbox").hide();
        
        $("#dataTable").sortableRows({items: ""})
      }
      var offset = 68;
      onCollapse(offset, "", "", function () {
        $("#remarksLi").hide();
      }, function () {
        $("#remarksLi").show();
      });
      
      	if("${itemChg.itemType1}".trim()!="ZC"){
			$("#supplProm").attr("disabled","disabled");
		}
		$("#supplProm").on("click",function(){
			var rowId = $("#dataTable").jqGrid('getGridParam','selrow');
			var ret = selectDataTableRow();
			if(!ret || selRowNum == -1){
				art.dialog({
					content:"请选择一条数据",
				});
				return;
			}
			if(ret.reqpk.trim() !=""){
				art.dialog({
					content:"已有项变动材料不可做供应商促销",
				});
				return;
			}
			if(!rowId){
		    	  rowId = selRowNum;
			}
	    	if(selRowNum != -1){
				ret =  $("#dataTable").jqGrid('getRowData', selRowNum);
	    	}
			
			Global.Dialog.showDialog("SupplProm",{
				title:"材料增减——供应商促销",
				url:"${ctx}/admin/itemChg/goSupplProm",
				postData:{cost:ret.cost,itemCode:ret.itemcode},
				height:800,
				width:1050,
				returnFun: function (data) {
					if(data){
						if(!ret.cost > data.promcost){
							art.dialog({
								content:"当前成本小于活动成本，修改失败",
							});
							return;
						}
						//修改表明细数据
						$("#dataTable").setCell(rowId, 'supplpromitempk',data[0].pk );
						$("#dataTable").setCell(rowId, 'supplpromdescr',data[0].actdescr );
						$("#dataTable").setCell(rowId, 'remarks',ret.remarks + " 参与活动："+data[0].actdescr+",活动价： "+data[0].promprice);
						$("#dataTable").setCell(rowId, 'cost',data[0].promcost);
						
						var markUp = 1.0;
						if (ret.isoutset == "1"){ //套餐外材料 才修改折扣
							if(ret.unitprice==0){
								markUp = 1.0;
							}else{
								markUp = myRound(data[0].promprice/ret.unitprice,4);
							}
							if(markUp>1){
								markUp = 1.0;
							}
						}
						$("#dataTable").setCell(rowId, 'markup',markUp*100);
						$("#dataTable").setCell(rowId, 'tmplineamount', myRound(myRound(ret.qty * ret.unitprice, 4) * markUp));
						var GridLineAmount = myRound(myRound(myRound(ret.qty * ret.unitprice, 4) * markUp) + parseFloat(ret.processcost));
						$("#dataTable").setCell(rowId, 'lineamount', GridLineAmount);
						
						var tmplineamount = myRound($("#dataTable").getCol('tmplineamount', false, 'sum'));
						var lineamount = myRound($("#dataTable").getCol('lineamount', false, 'sum'));
						var discAmount = parseFloat($("#discAmount").val());
						$("#dataTable").footerData('set', {"tmplineamount": tmplineamount});
						$("#dataTable").footerData('set', {"lineamount": lineamount});
						$("#befAmount").val(lineamount);
			            if (discAmount) {
							if (lineamount >= 0)  $("#amount").val(lineamount - discAmount);
							else $("#amount").val(lineamount + discAmount);
			            } else {
			            	$("#amount").val(lineamount);
			            }
			            calculateTax();
					}
					selRowNum = -1;
				}
			});
 		});

      //查看材料状态
      $("#goSoftChgItemStatus").on("click", function () {
        var gridDatas = $("#dataTable").jqGrid("getRowData");
        itemCodes = "";
        $.each(gridDatas, function (i, v) {
          if (v.itemcode && v.itemcode != "") {
           if (i==0) itemCodes = v.itemcode;
           else itemCodes += ","+v.itemcode;
          }
        })
        Global.Dialog.showDialog("goSoftChgItemStatus", {
          title: "材料增减明细--查看材料状态",
          url: "${ctx}/admin/itemChg/goSoftChgItemStatus",
          postData: {
            itemCode: itemCodes,
            custCode: "${itemChg.custCode}",
          },
          height: 513,
          width: 809,
        });
      });
      //进入页面时，根据常规变更是否改变干系人是否可编辑
      changeStakeholderDisabled();

			//常规变更
			/*$("#isAddAllInfo").change(function () {
				changeStakeholderDisabled();
			});*/

      // 增减干系人
			var itemChgStakeholderJqGridOption = {
				url: "${ctx}/admin/itemChg/getItemChgStakeholderList",
				postData: {no: "${itemChg.no}"},
				rowNum:10000,
				colModel : [
					{name: "pk", index: "pk", width: 30, label: "pk", sortable: true, align: "left"},
					{name: "chgno", index: "chgno", width: 100, label: "材料增减单号", sortable: true, align: "left"},
					{name: "role", index: "role", width: 80, label: "角色", sortable: true, align: "left"},
					{name: "roledescr", index: "roledescr", width: 80, label: "角色", sortable: true, align: "left"},
					{name: "empcode", index: "empcode", width: 80, label: "员工编号", sortable: true, align: "left"},
					{name: "empname", index: "empname", width: 80, label: "员工姓名", sortable: true, align: "left"},
					{name: "lastupdate", index: "lastupdate", width:85, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 60, label: "最后更新人员", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 68, label: "操作标志", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 65, label: "是否过期", sortable: true, align: "left"}
				],
				gridComplete:function(){
					updateStakeholder();
				}
			};
			Global.JqGrid.initJqGrid("itemChgStakeholderDataTable",itemChgStakeholderJqGridOption);
			
			$("#itemChgStakeholderBtn").on("click",function () {
				var param = Global.JqGrid.allToJson("itemChgStakeholderDataTable");
				Global.Dialog.showDialog("chgStakeholder",{
					title: "材料增减干系人编辑",
					url: "${ctx}/admin/itemChg/goChgStakeholderEdit",
					postData: {chgStakeholderList: param.detailJson},		
					height: 458,
					width: 660,
					returnFun : function(data){
						if(data){
							$("#itemChgStakeholderDataTable").clearGridData();
							$.each(data, function(i,v){
								Global.JqGrid.addRowData("itemChgStakeholderDataTable", v);
							});
						}
					}
				});
			});
			
			if('${itemChg.itemType1}'=="RZ"){
		    	$("#discTokenNo_li").show();
		   	    $("#tokenNoSelect").openComponent_discToken({
		   			condition:{'itemType1':'${itemChg.itemType1}',custCode:'${itemChg.custCode}',
		   				containOldCustCode:'1',status:'2', hasSelectNo:'${itemChg.discTokenNo}'},
		   			callBack:function(data){  
		   			 	if(data){
		   			 		$("#discTokenNo").val(data.no); 
		   			 	    $("#discTokenAmount").val(data.amount);
		   			 	};
		   		    }
		   		 });
		    }
		    
		displayInnerArea();
		
    });
    
    function displayInnerArea() {
        var innerAreaSpan = $("#innerArea");
        var innerArea = "${itemChg.innerArea}";
        
        if (parseFloat(innerArea.substring(innerArea.lastIndexOf("."))) > 0)
            innerAreaSpan.text(innerArea + "m²");
        else
            innerAreaSpan.text(innerArea.substring(0, innerArea.lastIndexOf(".")) + "m²");
    }

	//判断是否干系人必录入
	function changeStakeholderDisabled() {
		if ("0" != $("#isAddAllInfo").val()) {
			$("#itemChgStakeholder").val("");
			$("#itemChgStakeholderDataTable").clearGridData();
			$("#itemChgStakeholderBtn").prop("disabled", true);
			$("#itemChgStakeholderRequired").prop("hidden", true);
		} else {
			$("#itemChgStakeholderBtn").prop("disabled", false);
			$("#itemChgStakeholderRequired").prop("hidden", false);
		}
	}
	//更新干系人
	function updateStakeholder() {
		var rows = $("#itemChgStakeholderDataTable").jqGrid("getRowData");
		var itemChgStakeholderName = "";
		$.each(rows, function (i, val) {
			if (0!=i) itemChgStakeholderName += ",";
			itemChgStakeholderName += val.empname;
		});
		$("#itemChgStakeholder").val(itemChgStakeholderName);
	}
	
	// 从选材批次导入
	function goImportItemBatch() {
	    var grid = $("#dataTable");
	    var reqPks = grid.getCol("reqpk");
	
	    Global.Dialog.showDialog("itemChgImportItemBatch", {
	        title: "增减明细--从选材批次导入",
	        url: "${ctx}/admin/itemChg/goImportItemBatch",
	        postData: {
	            custCode: "${itemChg.custCode}",
	            itemType1: "${itemChg.itemType1}",
	            excludedReqPks: reqPks.join(",")
	        },
	        height: 700,
	        width: 1200,
	        returnFun: function (data) {
	            var dataTable = $("#dataTable");
	
	            var orderBy = "${itemChg.itemType1}".trim() == 'RZ' ? 'itemtype2descr'
	                    : $("#itemType2Group").is(":checked") ? 'itemtype2descr' : 'dispseq';
	
	            $.each(data, function (k, v) {
	                maxDispSeq++;
	                v.iscommi = "1";
	                v.isservice = '${itemChg.isService}';
	                v.lastupdate = getNowFormatDate();
	                v.lastupdatedby = '${itemChg.lastUpdatedBy}';
	                v.actionlog = "ADD";
	                v.dispseq = maxDispSeq;
	            })

	            var params = JSON.stringify(dataTable.getRowData().concat(data));
	
	            dataTable.jqGrid("setGridParam", {
	                url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
	                postData: {'params': params, 'orderBy': orderBy},
	                page: 1
	            }).trigger("reloadGrid");
	        }
	    });
	}

    //导入excel
    function goImportExcel() {
      Global.Dialog.showDialog("itemChg_importExcel", {
        title: "增减明细--从excel导入",
        url: "${ctx}/admin/itemChg/goItemChg_importExcel?itemType1=${itemChg.itemType1}&custCode=${itemChg.custCode}&isService=" + $("#isService").val() + "&isCupboard=" + $("#isCupboard").val(),
        height: 600,
        width: 1000,
        returnFun: function (data) {
          var orderBy = "${itemChg.itemType1}".trim() == 'RZ' ? 'itemtype2descr' : $("#itemType2Group").is(":checked") ? 'itemtype2descr' : 'dispseq';
          $.each(data, function (k, v) {
            maxDispSeq++;
            v.iscommi = "1";
            v.isservice = '${itemChg.isService}';
            v.lastupdate = getNowFormatDate();
            v.lastupdatedby = '${itemChg.lastUpdatedBy}';
            v.actionlog = "ADD";
            v.dispseq = maxDispSeq;
          })
          var params = JSON.stringify($("#dataTable").jqGrid("getRowData").concat(data));
          $("#dataTable").jqGrid("setGridParam", {
            url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
            postData: {'params': params, 'orderBy': orderBy},
            page: 1
          }).trigger("reloadGrid");

        }
      });
    }
    function init() {
      if ('${itemChg.itemType1}'.trim() == 'JC') {
        $("#dataTable").setGridParam().showCol("intproddescr").trigger("reloadGrid");
        $("#isServiceLabel").text("是否橱柜");
        $("#isServiceDescr").attr("name", "isCupboardDescr");
        $("#isServiceDescr").attr("id", "isCupboardDescr");
        $("#isCupboardDescr").val('${itemChg.isCupboard}');
        if ('${itemChg.containInt}' == 0) $("#ys").show();
      } else if ('${itemChg.itemType1}'.trim() == 'RZ') {
        $("#isServiceDescr").parent().css("display", "none");
        $("#isServiceDescr").parent().prev().css("display", "none");
        $("#dataTable").setGridParam().showCol("itemtype3descr").trigger("reloadGrid");
        $("#xj").show();
        if ('${itemChg.containSoft}' == 0) $("#ys").show();
// $("#isServiceDescr").attr("disabled","disabled");
        $("#dataTable").setGridParam().showCol("sqlcodedescr").trigger("reloadGrid");
        $("#dataTable").setGridParam().showCol("marketprice").trigger("reloadGrid");
        $("#taxLi").show();
        $("#referenceTaxLi").show()
        $("#goSoftChgItemStatus").show();
      } else if ('${itemChg.itemType1}'.trim() == 'ZC') {
        if ('${itemChg.containMain}' == 0 || '${itemChg.containMainServ}' == 0) {
          $("#ys").show();
        }
        
        if ('${itemChg.constructStatus}' != '7') {//主材除了内部记账都可以自动提交
        	$("#autoConfirmBtn").show();
        }

      }
      if ('${itemChg.isOutSet}' == '2') $("#dataTable").setGridParam().showCol("isoutsetdescr").trigger("reloadGrid");
      if ('${itemChg.isService}' == '1') {
        //隐藏优惠相关信息
        $("#befAmountLi").hide();
        $("#discAmountLi").hide();
        $("#discCostLi").hide();
      }
    }
    function change(ele) {
      var boxHeight = $(".body-box-form").height();
      if (ele.name.match("isServiceDescr")) {
        $("#isService").val(ele.value);
        if (ele.value == "1") {
          $("#befAmountLi").hide();
          $("#discAmountLi").hide();
          $("#discCostLi").hide();
          $("#dataTable").setGridParam().showCol("iscommichx").trigger("reloadGrid");
        } else {
          $("#befAmountLi").show();
          $("#discAmountLi").show();
          $("#discCostLi").show();
          $("#dataTable").setGridParam().hideCol("iscommichx").trigger("reloadGrid");
        }
      }
      else $("#isCupboard").val(ele.value);
      $("#dataTable").setGridHeight($("#dataTable").jqGrid("getGridParam", "height") + boxHeight - $(".body-box-form").height());
    }
    function commiCheckBox(v, x, r) {
      if (r.iscommi == "1") return "<input type='checkbox' checked onclick='updateCommi(" + x.rowId + ",this)' />";
      else return "<input type='checkbox' onclick='updateCommi(" + x.rowId + ",this)' />";
    }
    function updateCommi(id, e) {
      if ($(e).is(':checked')) {
        $("#dataTable").setCell(id, 'iscommi', "1");
        $(e).parent().parent().find("td").addClass("commiColor");
      } else {
        $("#dataTable").setCell(id, 'iscommi', "0");
        $(e).parent().parent().find("td").removeClass("commiColor");
      }

    }
    function addCellAttr(rowId, val, rawObject, cm, rdata) {
      if ('${itemChg.itemType1}'.trim() == 'RZ')  return rawObject.expired == 'T' ? " style='color:red'" : "";
    }
    function goItemPlanAdd() {
      Global.Dialog.showDialog("itemChg_itemPlanAdd", {
        title: "材料增减明细--从预算添加",
        url: "${ctx}/admin/itemChg/goItemPlanAdd?itemType1=${itemChg.itemType1}&custCode=${itemChg.custCode}&isService=" + $("#isService").val(),
        height: 600,
        width: 1000,
        resizable: false,
        returnFun: function (data) {
          if (data.selectRows.length) {
            var orderBy = "${itemChg.itemType1}".trim() == 'RZ' ? 'itemtype2descr' : $("#itemType2Group").is(":checked") ? 'itemtype2descr' : 'dispseq';
            if ("${itemChg.itemType1}".trim() == 'ZC' && $("#isService").val() == '1') {
              //默认提成
              $.each(data.selectRows, function (k, v) {
                maxDispSeq++;
                v.iscommi = "1";
                v.lastupdate = getNowFormatDate();
                v.lastupdatedby = '${itemChg.lastUpdatedBy}';
                v.actionlog = "ADD";
                v.dispseq = maxDispSeq;
              })
            } else {
              $.each(data.selectRows, function (k, v) {
                maxDispSeq++;
                v.lastupdate = getNowFormatDate();
                v.lastupdatedby = '${itemChg.lastUpdatedBy}';
                v.actionlog = "ADD";
                v.dispseq = maxDispSeq;
              })
            }
            var params = JSON.stringify($("#dataTable").jqGrid("getRowData").concat(data.selectRows));
            $("#dataTable").jqGrid("setGridParam", {
              url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
              postData: {'params': params, 'orderBy': orderBy},
              page: 1
            }).trigger("reloadGrid");
          }
        }
      });
    }
    function refreshGroup(e) {
      var params = JSON.stringify($("#dataTable").jqGrid("getRowData"));
      if ($(e).is(":checked")) {
        $("#dataTable").jqGrid("setGridParam", {
          url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
          postData: {'params': params, 'orderBy': 'itemtype2descr'},
          page: 1,
          grouping: true
        }).trigger("reloadGrid");
        
        // 按材料类型2分组时不允许拖动排序
        $("#dataTable").sortableRows({items: ""})
      } else {
        $("#dataTable").jqGrid("setGridParam", {
          url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
          postData: {'params': params, 'orderBy': 'dispseq'},
          page: 1,
          grouping: false
        }).trigger("reloadGrid");
        
        $("#dataTable").sortableRows({update: reorderDispSeq})
      }
    }
    
    function reorderDispSeq(ev, ui) {
        var grid = $("#dataTable")
        var ids = grid.getDataIDs()
        
        for (var i = 0; i < ids.length; i++) {
            grid.setCell(ids[i], "dispseq", i + 1)
        }
    }
    
    function goTop(table) {
      if (!table)  table = "dataTable";
      var rowId = $("#" + table).jqGrid('getGridParam', 'selrow');
      var replaceId = parseInt(rowId) - 1;
      if (rowId || selRowNum != -1) {
    	  if(!rowId){
        	  rowId = selRowNum;
   		  }	else {
   			  selRowNum = rowId;
   		  }
    	  replaceId = parseInt(rowId) - 1;
        if (rowId > 1) {
	      $("#goTop").attr("disabled",true);
          var ret1 = $("#" + table).jqGrid('getRowData', rowId);
          var ret2 = $("#" + table).jqGrid('getRowData', replaceId);
          if ('${itemChg.itemType1}'.trim() == 'RZ' || $("#itemType2Group").is(":checked")) {

            if (ret1.itemtype2descr == ret2.itemtype2descr) {
              replace(rowId, replaceId);
              scrollToPosi(replaceId, table, ret1.itemtype2descr);
              $("#dataTable").setCell(rowId, 'dispseq', ret1.dispseq);
              $("#dataTable").setCell(replaceId, 'dispseq', ret2.dispseq);
            }
          } else {
            replace(rowId, replaceId);
            scrollToPosi(replaceId);
            $("#dataTable").setCell(rowId, 'dispseq', ret1.dispseq);
            $("#dataTable").setCell(replaceId, 'dispseq', ret2.dispseq);
          }
		  var params = JSON.stringify($("#dataTable").jqGrid("getRowData"));
          $("#dataTable").jqGrid("setGridParam", {
              url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
              postData: {'params': params},
              page: 1
            }).trigger("reloadGrid");
          scrollToPosi(replaceId);
          selRowNum--;
          if(selRowNum == 0){
          	selRowNum = -1;
          }
        }
      } else {
        art.dialog({
          content: "请选择一条记录"
        });
      }
    }
    function goBottom(table) {
      if (!table)  table = "dataTable";
      var rowId = $("#" + table).jqGrid('getGridParam', 'selrow');
      var replaceId = parseInt(rowId) + 1;
      var rowNum = $("#" + table).jqGrid('getGridParam', 'records');
      if (rowId || selRowNum != -1) {
    	  if(!rowId){
        	  rowId = selRowNum;
   		  }	else {
   			  selRowNum = rowId;
   		  }
    	  replaceId = parseInt(rowId) + 1;
        if (rowId < rowNum) {
	    	$("#goBottom").attr("disabled",true);
          var ret1 = $("#" + table).jqGrid('getRowData', rowId);
          var ret2 = $("#" + table).jqGrid('getRowData', replaceId);
          //是否按照材料类型2分类
          if ('${itemChg.itemType1}'.trim() == 'RZ' || $("#itemType2Group").is(":checked")) {

            if (ret1.itemtype2descr == ret2.itemtype2descr) {
              replace(rowId, replaceId);
              scrollToPosi(replaceId, table, ret1.itemtype2descr);
              $("#dataTable").setCell(rowId, 'dispseq', ret1.dispseq);
              $("#dataTable").setCell(replaceId, 'dispseq', ret2.dispseq);
            }
          } else {
            replace(rowId, replaceId);
            scrollToPosi(replaceId);
            $("#dataTable").setCell(rowId, 'dispseq', ret1.dispseq);
            $("#dataTable").setCell(replaceId, 'dispseq', ret2.dispseq);
          }
          var params = JSON.stringify($("#dataTable").jqGrid("getRowData"));
          $("#dataTable").jqGrid("setGridParam", {
              url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
              postData: {'params': params},
              page: 1
           }).trigger("reloadGrid");
          scrollToPosi(replaceId);
          selRowNum++;
        }
      } else {
        art.dialog({
          content: "请选择一条记录"
        });
      }
    }
    function onSelect(json) {
      var rowid = $("#dataTable").getGridParam("selrow");
      var rowData = $("#dataTable").jqGrid('getRowData', rowid);
      var unitprice = 0;
      if (rowData.isoutset != '0') unitprice = json.price;
      $("#dataTable").jqGrid("saveCell", rowid, data_iCol);
      $("#dataTable").setRowData(rowid, {
        'itemcode': json.code,
        'itemdescr': json.descr,
        'sqlcodedescr': json.sqldescr,
        'uomdescr': json.uomdescr,
        'itemtype2': json.itemtype2.trim(),
        'itemtype2descr': json.itemtype2descr,
        'itemtype3descr': json.itemtype3descr,
        'marketprice': json.marketprice,
        'unitprice': unitprice,
        'beflineamount': myRound(rowData.qty * unitprice, 4),
        'tmplineamount': myRound(rowData.qty * unitprice * rowData.markup / 100),
        'lineamount': myRound(myRound(rowData.qty * unitprice * rowData.markup / 100) + parseFloat(rowData.processcost)),
        'cost': json.cost,
        'remarks': json.remark
      });
      
      existsSetDeduction()
    }
    
    // 算法选择事件
    function algorithmClick(e){
    	var rowid = $("#dataTable").getGridParam("selrow");
        var rowData = $("#dataTable").jqGrid('getRowData', $(e.target).attr("rowid"));
        $("#dataTable").setRowData($(e.target).attr("rowid"), {
        	algorithm:$(e.target).val(),
        });
        changeAlgorithm($(e.target).attr("rowid"),$(e.target).val(),"algorithm");
	}
	
	//根据算法计算
	function changeAlgorithm(id,val,type){
        var ret = $("#dataTable").jqGrid('getRowData', id);

		var datas=$("#dataForm").jsonForm();
			datas.custCode=$.trim($("#custCode").val());
			datas.itemCode=ret.itemcode;

			if(type=="algorithm"){
				datas.algorithm = val;
				datas.cutType=ret.cuttype; 
			} else {
				datas.algorithm = ret.algorithm;
				datas.cutType = val; 
				datas.type = '2';
				datas.qty = ret.qty;
			}
			
			datas.doorPK=ret.doorpk;
			datas.prePlanAreaPK=ret.preplanareapk;
			datas.isOutSet=ret.isoutset;
			datas.algorithmPer=ret.algorithmper;
			datas.algorithmDeduct=ret.algorithmdeduct;
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
						if(datas.type == "2"){
							$("#dataTable").setRowData(id, {
								processcost:obj.processcost,
				              	lineamount: myRound(myRound(myRound(ret.qty * ret.unitprice, 4) * ret.markup / 100) + myRound(obj.processcost))
							});
						} else {
							$("#dataTable").setRowData(id, {
								processcost:obj.processcost,
								qty:obj.qty
							}); 
						 	$("#dataTable").setCell(id, 'beflineamount', myRound(obj.qty * ret.unitprice, 4));
			              	$("#dataTable").setCell(id, 'tmplineamount', myRound(myRound(obj.qty * ret.unitprice, 4) * ret.markup / 100));
			              	$("#dataTable").setCell(id, 'lineamount', myRound(myRound(myRound(obj.qty * ret.unitprice, 4) * ret.markup / 100) + obj.processcost));
			              	$("#dataTable").footerData('set', {"qty": $("#dataTable").getCol('qty', false, 'sum')});
						}
					}
				}
		});	
	}
    function onFixAreaSelect(json) {
      var rowid = $("#dataTable").getGridParam("selrow");
      $("#dataTable").jqGrid("saveCell", rowid, data_iCol);
      $("#dataTable").setRowData(rowid, {'fixareapk': json.PK, 'fixareadescr': json.Descr});
    }
    window.onfocus = function () {
      var colModel = $("#dataTable")[0].p.colModel;
      var index;
      $.each(colModel, function (k, v) {
        if (v.name == "qty") {
          index = k;
          return false;
        }

      })

      if (data_iCol == index && editRow) {
        $("#dataTable").jqGrid("editCell", data_iRow, data_iCol, true);

      }

    };
    window.onblur = function () {
      editRow = data_iRow;
    };
    function updateRemarks(e) {
      $("#remark").val(e.value);
      $("#remarks").val(e.value);
    }
    
    function calculateTax(){
		if ($("#amount").val()!=""){
			var dBefAmount = parseFloat($("#befAmount").val());
			var dDiscAmount = parseFloat($("#discAmount").val() == "" ? 0.0 : $("#discAmount").val());			
			if (dBefAmount < 0)  dDiscAmount = -dDiscAmount;
			$.ajax({
				url:"${ctx}/admin/itemPlan/getTax",
				type:"post",
				data:{code:"${itemChg.custCode}", contractFee:parseFloat($("#amount").val())+parseFloat($("#manageFee").val()),
					designFee:0,taxCallType:'2', discAmount:dDiscAmount,
					setAdd: 0.0, //基础套外增项
					manageFeeBase: 0.0, //预算管理费
					baseFeeComp: 0.0,
					baseFeeDirct: 0.0,
				},
				dataType:"json",
				cache:false,
				error:function(obj){
					showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
				},
				success:function(obj){
					if(obj){
						$("#referenceTax").val(obj);
					}else{
						$("#referenceTax").val(0);
					}	
				}
			}); 
		}else{
			$("#referenceTax").val(0);
		}      	
	}
	function changeManageFee(){
		calculateTax();
	}
	
	function goBaseFromTemp(){
		Global.Dialog.showDialog("chgStakeholder",{
			title: "基础增减——选择模板",
			url: "${ctx}/admin/itemChg/goItemFromTemp",
			postData: {custType:"${itemChg.custType}",custCode:"${itemChg.custCode}"},		
			height:280,
			width:430,
			returnFun : function(data){
				if(data){
			        $("#tempNo").val(data.tempNo);
			        $("#tempDescr").val(data.tempDescr);
					var orderBy = "${itemChg.itemType1}".trim() == 'RZ' ? 'itemtype2descr' : $("#itemType2Group").is(":checked") ? 'itemtype2descr' : 'dispseq';
					$("#dataTable").clearGridData();
					$.each(data.datas, function(i,v){
						if($.trim(v.isservice) ==$.trim($("#isService").val()) ){
							Global.JqGrid.addRowData("dataTable", v);
						}
					});
					var params = JSON.stringify($("#dataTable").jqGrid("getRowData"));
		            $("#dataTable").jqGrid("setGridParam", {
		              url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
		              postData: {'params': params, 'orderBy': orderBy},
		              page: 1
		            }).trigger("reloadGrid");
				}
				selRowNum = -1;
			}
		});
	}
	
	//重新生成
	function loadRegen(){
		var isService = $("#isService").val()
		var rowData=$('#dataTable').jqGrid("getRowData");
		var servRowData=$('#serviceDataTable').jqGrid("getRowData");
		var datas=$("#dataForm").jsonForm();
		datas.isService=isService;
		datas.detailJson=JSON.stringify(rowData);
	   	art.dialog({
			content:"此操作将生成新加增减明细，原有的明细降本重新计算数量，是否确定要重新生成？",
				height: 50,
			  	width:250,
			    ok: function () {
				$.ajax({
					url:"${ctx}/admin/itemChg/getRegenFromPrePlanTemp",
					type:"post",
			        dataType:"json",
			        data:datas,
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '重新生成数据出错~'});
				    },
			      success: function(obj){
					    if(obj.rs){
					  		 hasEdit=true;
						    $('#dataTable').clearGridData();
						    $('#serviceDataTable').clearGridData();
						    $.each(JSON.parse(obj.datas),function(k,v){
						    	if($.trim(v.isservice) == $.trim($("#isService").val())){
						    		$('#dataTable').addRowData($('#dataTable').jqGrid('getGridParam','records')+1, v, "last");
						    	}
						    	//if(v.isservice==1) $('#serviceDataTable').addRowData($('#serviceDataTable').jqGrid('getGridParam','records')+1, v, "last");
					  			//else $('#dataTable').addRowData($('#dataTable').jqGrid('getGridParam','records')+1, v, "last");
					  		})
					  		art.dialog({
								content: obj.msg,
								time: 1000,
							});
				  		}else{
				  			art.dialog({
								content: obj.msg
							});
				  		}
				    }
			 	});
			},
	 		cancel:function(){
	 		}
		});
	}
	
	function changeFaultType(){
		clickNum++;
		var faultType=$("#faultType").val();
		if(clickNum!=1){
			$("#faultMan").val(faultType=="1"?"${itemChg.projectMan}":"");
			$("#faultManDescr").val(faultType=="1"?"${itemChg.projectManDescr}":"");
		}
		if(faultType=="1"){
			$("#openComponent_worker_faultWorker").val("");
			$("#faultEmp").parent().removeClass("hidden");
			$("#faultWorker").parent().addClass("hidden");
			$("#faultEmp").openComponent_employee({
				showValue:clickNum==1?"${itemChg.faultMan}":"${itemChg.projectMan}",
				showLabel:clickNum==1?"${itemChg.faultManDescr}":"${itemChg.projectManDescr}",
				condition:{
					isStakeholder:"1",custCode:$("#refCustCode").val()
				},
				callBack:function(data){
					$("#faultMan").val(data.number);
					$("#faultManDescr").val(data.namechi);
					if(data.prjqualityfee!=""){
						$("#prjQualityFee").parent().removeClass("hidden");
						$("#prjQualityFee").val(data.prjqualityfee);
					}else{
						$("#prjQualityFee").parent().addClass("hidden");
					}
				}
			});
		}else if(faultType=="2"){
			$("#openComponent_employee_faultEmp").val("");
			$("#faultWorker").parent().removeClass("hidden");
			$("#faultEmp").parent().addClass("hidden");
			$("#faultWorker").openComponent_worker({
				showValue:clickNum==1?"${itemChg.faultMan}":"",
				showLabel:clickNum==1?"${itemChg.faultManDescr}":"",
				condition:{
					refCustCode:$("#refCustCode").val()
				},
				callBack:function(data){
					$("#faultMan").val(data.Code);
					$("#faultManDescr").val(data.NameChi);
				}
			});
		}else{
			$("#faultWorker").parent().addClass("hidden");
			$("#faultEmp").parent().addClass("hidden");
			$("#openComponent_worker_faultWorker").val("");
			$("#openComponent_employee_faultEmp").val("");
		}
	}
	
	function cuttypeClick(e){
    	var rowid = $("#dataTable").getGridParam("selrow");
        var rowData = $("#dataTable").jqGrid('getRowData', $(e.target).attr("rowid"));
        $("#dataTable").setRowData($(e.target).attr("rowid"), {
        	cuttype:$(e.target).val(),
        });
        
        // 选择切割方式,套餐内默认不自动计算其它费用
    	if(rowData.isoutset=='0'){
    		return;
    	}
        changeAlgorithm($(e.target).attr("rowid"),$(e.target).val(),"cutType");
    }
	
	//优惠券选择
	function discTokenSel(){
		 $("#openComponent_discToken_tokenNoSelect").next().click();
	} 
	
    //优惠券删除
    function discTokenDel(){
  		$("#discTokenNo").val("");
  		$("#discTokenAmount").val("0.0");
    }
    
    function viewSetDeductions() {
        var grid = $("#dataTable")
        var rows = grid.getRowData()
        var itemType2s = []
        
        if (!rows.length) {
            art.dialog({content: "无对应材料类型2的套内减项！"})
            return
        }
        
        // 材料类型2去重
        for (var i = 0; i < rows.length; i++) {
            var temp = rows[i].itemtype2.trim()
            if (itemType2s.indexOf(temp) >= 0) continue
            itemType2s.push(temp)
        }
    
        Global.Dialog.showDialog("viewSetDeductions", {
            title: "套内减项",
            url: "${ctx}/admin/itemChg/viewSetDeductions",
            postData: {
                custCode: "${itemChg.custCode}",
                itemType2: itemType2s.join(",")
            },
            height: 600,
            width: 1000,
        })
    }
    
    function existsSetDeduction() {
        var grid = $("#dataTable")
        var rows = grid.getRowData()
        var itemType2s = []
        var existsSetDeduction = $("#existsSetDeduction")
        
        if (!rows.length) {
            existsSetDeduction.hide()
            return
        }
        
        // 材料类型2去重
        for (var i = 0; i < rows.length; i++) {
            var temp = rows[i].itemtype2.trim()
            if (itemType2s.indexOf(temp) >= 0) continue
            itemType2s.push(temp)
        }
    
        $.ajax({
            url: "${ctx}/admin/itemChg/existsSetDeduction",
            type: "post",
            data: {
                custCode: "${itemChg.custCode}",
                itemType2: itemType2s.join(",")
            },
            dataType: "json",
            cache: false,
            success: function(existed) {
                if (existed) existsSetDeduction.show()
                else existsSetDeduction.hide()
            }
        })
    }
	
	function goViewSwitchNum(){
		var ret = selectDataTableRow();
		var rowId = $("#dataTable").jqGrid('getGridParam','selrow');
		Global.Dialog.showDialog("viewSetDeductions", {
            title: "开关数量",
            url: "${ctx}/admin/itemChg/goViewSwitch",
            postData: {
                code: "${itemChg.custCode}",
            },
            height: 600,
            width: 1000,
            returnFun: function (data) {
            	$("#dataTable").jqGrid('setCell',rowId,"qty",data);
       		}
        })
	}
	
	function getItemType12(itemCode){
		$.ajax({
            url: "${ctx}/admin/item/getItemType12ByItemCode",
            type: "post",
            data: {
                code: itemCode,
            },
            dataType: "json",
            cache: false,
            success: function(data) {
	            if("16" == $.trim(data)){
					$("#viewSwitchNum").show();
				} else {
					$("#viewSwitchNum").hide();
				}
            }
        })
	}
	
	function cancelItemChg() {
	    art.dialog({
	        content: "确定要取消此增减单吗？",
	        ok: function() {
		        $.ajax({
		            url: "${ctx}/admin/itemChg/cancel",
		            type: "post",
		            data: {
		                no: "${itemChg.no}"
		            },
		            dataType: "json",
		            cache: false,
		            success: function(obj) {
						if (obj.rs) {
						    art.dialog({
						        content: obj.msg,
						        time: 1000,
						        beforeunload: function() {
						            closeWin(true, true);
						        }
						    });
						} else {
						    art.dialog({
						        content: obj.msg
						    });
						}
		            }
		        })
	        },
	        cancel: function() {}
	    })
	}
	
  </script>

</head>

<body onload="init()">
<div class="body-box-form">
	<div class="panel panel-system">
		<div class="panel-body">
			<div class="btn-group-xs">
				<button type="button" id="saveBtn" class="btn btn-system " onclick="save()">保存</button>
				<button type="button" id="autoConfirmBtn" class="btn btn-system " onclick="save('4')" style="display: none">提交审核</button>
				<button type="button" class="btn btn-system " onclick="cancelItemChg()">取消增减</button>
				<button type="button" class="btn btn-system " onclick="viewSetDeductions()">套内减项</button>
				<button type="button" class="btn btn-system " onclick="updateDiscount()">批量调整折扣</button>
				<button type="button" class="btn btn-system " onclick="closeWin(true,true)">关闭</button>
				&nbsp&nbsp&nbsp客户付款进度:第${balanceMap.nowNum}期
				&nbsp&nbsp&nbsp本期应付余额:${balanceMap.nowShouldPay }
				<span id="existsSetDeduction" style="color:red;margin:0 20px;display:none">存在套内减项</span>
			</div>
		</div>
	</div>
	<div class="panel panel-info">
		<div class="panel-body">
			<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
				<input type="hidden" id="expired" name="expired" value="${itemChg.expired}"/>
				<input type="hidden" id="status" name="status" value="${itemChg.status}"/>
				<input type="hidden" id="appCzy" name="appCzy" style="width:160px;" value="${itemChg.appCzy}"/>
				<input type="hidden" id="confirmCzy" name="confirmCzy" style="width:160px;" value="${itemChg.confirmCzy}"/>
				<input type="hidden" id="custType" name="custType" style="width:160px;" value="${itemChg.custType}"/>
				<input type="hidden" id="isOutSet" name="isOutSet" style="width:160px;" value="${itemChg.isOutSet}"/>
				<input type="hidden" name="itemType1" style="width:160px;" value="${itemChg.itemType1}"/>
				<input type="hidden" id="isService" name="isService" value="${itemChg.isService}"/>
				<input type="hidden" id="isCupboard" name="isCupboard" style="width:160px;" value="${itemChg.isCupboard}"/>
				<input type="hidden" id="tempNo" name="tempNo" style="width:160px;" value="${itemChg.tempNo}"/>
				<input type="hidden" name="faultMan" id="faultMan" value="${itemChg.faultMan}"   />
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" readonly="readonly" value="${itemChg.address}"/>
						<span id="innerArea" style="position:absolute;top:5px;left:295px;"></span>
					</li>
					<li id="befAmountLi">
						<label>优惠前金额</label>
						<input type="text" id="befAmount" name="befAmount"  readonly="readonly" value="${itemChg.befAmount}"/>
					</li>
					<li id="discAmountLi">
						<label>优惠金额</label>
						<input type="text" id="discAmount" name="discAmount" value="${itemChg.discAmount}" onblur="changeAmount()"/>
					</li>
					<li id="discCostLi">
						<label>其中产品优惠</label>
						<input type="text" id="discCost" name="discCost" value="${itemChg.discCost}"/>
					</li>
					<li>
						 <label>优惠券</label>
						 <input type="text" id="discTokenAmount" name="discTokenAmount" value="${itemChg.discTokenAmount}" style="width: 95px;" readonly="readonly" />
						 <input type="hidden" id="discTokenNo" name="discTokenNo" value="${itemChg.discTokenNo}" style="width: 95px;" readonly="readonly" />
						 <button type="button" class="btn btn-system" id="discTokenBtn"  onclick="discTokenSel()" style="font-size: 12px;margin-top:-4px;margin-left: -5px;width: 30px;">新增</button>
			        	 <button type="button" class="btn btn-system" id="updateCustBtn" onclick="discTokenDel()" style="font-size: 12px;margin-top:-4px;margin-left: -5px;width: 30px;" >删除</button>
					</li>
					<li hidden="true">
			             <input type="text" id="tokenNoSelect" name="tokenNoSelect" />
			         </li>
					<li>
						<label>实际总价</label>
						<input type="text" id="amount"
						       name="amount"
						       readonly="readonly" value="${itemChg.amount }" />
					</li>
					<li id="manageFeeLi">
						<label>管理费</label>
						<input type="text" id="manageFee" name="manageFee" value="${itemChg.manageFee}" onchange="changeManageFee()"/>
					</li>
					<li id="manageFeePerLi">
						<label>参考管理费</label>
						<input type="text" id="manageFeePer" name="manageFeePer" readonly="readonly" value="0"/>
					</li>
					<li id="taxLi">
						<label>税金</label>
						<input type="text" id="tax" name="tax" value="${itemChg.tax}"/>
					</li>
					<li id="referenceTaxLi">
						<label>参考税金</label>
						<input type="text" id="referenceTax" name="referenceTax" readonly="readonly"/>
					</li>
					<li>
						<label id="isServiceLabel">是否服务产品</label>
						<select id="isServiceDescr" name="isServiceDescr" onchange="change(this)">
							<option value="0">否</option>
							<option value="1" ${itemChg.isService=='1' ? 'selected':'' }>是</option>
						</select>
					</li>
					<li >
						<label>计划发货日期</label>
						<input type="text" id="planArriveDate" name="planArriveDate" 
							class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							 value="<fmt:formatDate value='${itemChg.planArriveDate}' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li class="form-validate">
						<label>关联客户</label>
						<input type="text" id="refCustCode" name="refCustCode" readonly="readonly" value="${itemChg.refCustCode}"/>
					</li>
					<li class="form-validate">
						<label>关联楼盘</label>
						<input type="text" id="refAddress" name="refAddress" readonly="readonly" value="${itemChg.refAddress}" readonly="true"/>
					</li>
					<div class="ref"><!-- 有关联楼盘才显示 -->
					<li>
						<label>责任人类型</label> 
						<house:xtdm id="faultType"  dictCode="FAULTTYPE" unShowValue="4" onchange="changeFaultType();"
						value="${itemChg.faultType}"></house:xtdm>
					</li>
					<li>
						<label>员工</label> 
						<input type="text" id="faultEmp" name="faultEmp" />
					</li>
					<li class="hidden"><label>工人</label> <input type="text" id="faultWorker"
						name="faultWorker" />
					</li>
					<li>
						<label>项目经理质保余额</label> 
						<input type="text" id="prjQualityFee" name="prjQualityFee" style="width:160px;" 
									value="${ itemChg.prjQualityFee}" readonly/>
					</li>
					</div>
					<li>
						<label for="isAddAllInfo"><span class="required">*</span>常规变更</label>
						<house:xtdm id="isAddAllInfo" dictCode="YESNO" style="width:160px;" value="${itemChg.isAddAllInfo}"
							onchange="changeStakeholderDisabled()"/>
					</li>
					<li>
						<label>
						<span class="required" id="itemChgStakeholderRequired" hidden="true">*</span>业务员</label>
						<div class="input-group">
							<input type="text" class="form-control" id="itemChgStakeholder" style="width: 121px;" readonly="readonly">
							<button type="button" class="btn btn-system" id="itemChgStakeholderBtn" style="font-size: 12px;width: 40px;">编辑</button>
						</div>
					</li>
					<li>	
						<label>报价模板</label>
						<input type="text" id="tempDescr" name="tempDescr" readonly="readonly" value="${itemChg.tempDescr}"/>
					</li>
					<li id="remarksLi">
						<label class="control-textarea">备注</label>
						<textarea id="remarks" name="remarks" onchange="updateRemarks(this)">${itemChg.remarks }</textarea>
					</li>
					<li id="loadMore">
						<button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">更多</button>
					</li>
					<div class="collapse " id="collapse">
						<ul>
							<li>
								<label>批次号</label>
								<input type="text" readonly="readonly" id="no" name="no" value="${itemChg.no}"/>
							</li>
							<li>
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode" value="${itemChg.custCode}" readonly="readonly"/>
							</li>
							<li>
							<label>档案编号</label>
								<input type="text" id="documentNo" name="documentNo" value="${itemChg.documentNo}"/>
							</li>
							<li>
								<label>材料类型1</label>
								<house:DataSelect id="itemType1" className="ItemType1" keyColumn="code" valueColumn="descr"
									value="${itemChg.itemType1 }"></house:DataSelect>
							</li>
							<li>
								<label>材料增减状态</label>
								<input type="text" id="statusDescr"	name="statusDescr" readonly="readonly" value="${itemChg.statusDescr}"/>
							</li>
							<li>
								<label>申请人</label>
								<input type="text" id="appCzyDescr" name="appCzyDescr"
							         value="${itemChg.appCzy}|${itemChg.appCzyDescr}" readonly="readonly"/>
							</li>
							<li>
								<label>变更日期</label>
								<input type="text" id="date"  name="date" readonly="readonly"
							         value="<fmt:formatDate value='${itemChg.date}' pattern='yyyy-MM-dd'/>"/>
							</li>
							<li>
								<label>审核人</label>
								<input type="text" id="confirmCzyDescr"  name="confirmCzyDescr"
							         readonly="readonly" value="${itemChg.confirmCzy}${itemChg.confirmCzyDescr}"/>
							</li>
							<li>
								<label>审核日期</label>
								<input type="text" id="confirmdate"   name="confirmdate" readonly="readonly"  readonly="readonly"
							         value="<fmt:formatDate value='${itemChg.confirmdate}' pattern='yyyy-MM-dd'/>"/>
							</li>
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remark" name="remark" onchange="updateRemarks(this)">${itemChg.remarks }</textarea>
							</li>
							<li>
								<label class="control-textarea">审核异常说明</label>
								<textarea id="exceptionRemarks" name="exceptionRemarks"  readonly="readonly">${itemChg.exceptionRemarks }</textarea>
							</li>
								<li class="search-group-shrink">
								<button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">收起</button>
							</li>
						</ul>
					</div>
				</ul>
      		</form>
			<form style="display: none" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
			</form>
		</div>
	</div>
	<div class="btn-panel">
		<div class="btn-group-sm">
			<button type="button" class="btn btn-system " onclick="add()">快速新增</button>
			<button type="button" class="btn btn-system " onclick="goTransAction()">已有项变动</button>
			<button type="button" class="btn btn-system " onclick="insert()">插入</button>
			<button id="delBtn" type="button" class="btn btn-system " onclick="del()">删除</button>
			<button type="button" class="btn btn-system " onclick="view()">查看</button>
			<button type="button" class="btn btn-system " id="supplProm">供应商促销</button>
			<button type="button" class="btn btn-system " onclick="edit()">编辑</button>
			<button type="button" class="btn btn-system "
				onclick="doExcelNow('${itemChg.itemType1}'.trim()=='ZC'?'主材增减':'${itemChg.itemType1}'.trim()=='JC'?'集成增减':'软装增减','')">
       			 导出excel
			</button>
			<button type="button" class="btn btn-system " id="goTop" onclick="goTop()">向上</button>
			<button type="button" class="btn btn-system " id="goBottom" onclick="goBottom()">向下</button>
			<c:if test="${itemChg.itemType1 =='ZC' && itemChg.signQuoteType =='2' && existsTemp=='false' }">
				<button type="button" class="btn btn-system" onclick="goBaseFromTemp()">从模板导入</button>
				<button type="button" class="btn btn-system" onclick="loadRegen()">重新生成</button>
			</c:if>
			<c:if test="${itemChg.itemType1 != 'JC'}">
			    <button type="button" class="btn btn-system " onclick="goImportItemBatch()">导入选材批次</button>
			</c:if>
			<button type="button" class="btn btn-system " onclick="goImportExcel()">导入excel</button>
			<c:if test="${itemChg.itemType1 =='ZC'}">
				<button type="button" class="btn btn-system " onclick="goViewSwitchNum()" id="viewSwitchNum">读取开关数量</button>
			</c:if>
			<button style="display: none" id="ys" type="button" class="btn btn-system " onclick="goItemPlanAdd()">从预算添加
			</button>
			<button style="display: none" id="xj" type="button" class="btn btn-system " onclick="showTmplineAmount()">查看小计
			</button>
			<button style="display: none" id="goSoftChgItemStatus" type="button" class="btn btn-system">
       			 查看材料状态
			</button>
		</div>
	</div>
  <!--标签页内容 -->
	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#tab_mainDetail" data-toggle="tab">主项目</a></li>
			<li class="nav-checkbox">
				<input type="checkbox" id="itemType2Group" name="itemType2Group"
					onclick="refreshGroup(this)"><label for="itemType2Group">按材料类型2分类</label></li>
		</ul>
		<div class="tab-content">
			<div id="tab_mainDetail" class="tab-pane fade in active">
				<div id="content-list">
					<table id="dataTable"></table>
				</div>
			</div>
		</div>
	</div>
	<!-- 增减干系人列表 -->
	<div style="display: none">
  		<table id= "itemChgStakeholderDataTable"></table>
	</div>
	<div id="algorithmOptions" style="width:180px;height:22px;background-color: red;position: absolute;" hidden="true">
		<ul id="algorithmUl">
		</ul>
	</div>
</div>
</body>
</html>


