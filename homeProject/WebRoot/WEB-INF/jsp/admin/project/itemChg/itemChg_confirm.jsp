<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
  <title>审核ItemChg</title>
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
  </style>
  <script type="text/javascript">
  	//责任人类型修改次数
	var clickNum=0;
    function closeWin(isCallBack, isPrevent) {
      if (!isPrevent)return;
      Global.Dialog.closeDialog(isCallBack);
    }
    var data_iRow;
    var data_iCol;
    var editRow;
    var itemdescr;
    var maxDispSeq = 0;
    var SetMinus;
    var itemAppDetail = [];
    var reqpk = [];
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
        }
      });
    }
    function insert() {
      var ret = selectDataTableRow();
      if (ret) {
        var posi = $('#dataTable').jqGrid('getGridParam', 'selrow');
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
          }
        });

      } else {
        art.dialog({
          content: "请选择一条记录"
        });
      }

    }
    function doSave() {
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
            var gridDatas = $("#dataTable").jqGrid("getRowData");
            datas.detailJson = JSON.stringify(gridDatas);
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
                    width: 200
                  });
                }
              }
            });

          }


        }
      });
    }
    function save() {
      var manageFee = parseInt($("#manageFee").val());
      var manageFeePer = parseInt($("#manageFeePer").val());
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
      if ($("#dataTable").jqGrid('getGridParam', 'records') > 0) {
        $.each($("#dataTable").jqGrid("getRowData"), function (k, v) {
          if (v.qty == 0 ) {
            art.dialog({
              content: '本次增减单中存在数量为0的记录,是否确定保存？',
              lock: true,
              width: 260,
              height: 100,
              ok: function () {
                if (manageFee != manageFeePer) {
                  art.dialog({
                    content: '管理费与参考管理费不相等,是否保存？',
                    lock: true,
                    width: 260,
                    height: 100,
                    ok: function () {
                      doSave();
                    },
                    cancel: function () {
                      return true;
                    }
                  });
                } else {
                  doSave();
                }

              },
              cancel: function () {
                return true;
              }
            });
            return;
          }
          if ($("#dataTable").jqGrid('getGridParam', 'records') == k + 1) {
            if ( manageFee != manageFeePer) {
              art.dialog({
                content: '管理费与参考管理费不相等,是否保存？',
                lock: true,
                width: 260,
                height: 100,
                ok: function () {
                  doSave();
                },
                cancel: function () {
                  return true;
                }
              });
            } else {
              doSave();
            }
          }
        })
      } else {
        if ( manageFee != manageFeePer) {
          art.dialog({
            content: '管理费与参考管理费不相等,是否保存？',
            lock: true,
            width: 260,
            height: 100,
            ok: function () {
              doSave();
            },
            cancel: function () {
              return true;
            }
          });
        } else {
          doSave();
        }
      }
    }
    function updateDiscount() {
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
            calculateTax();
          }

        }
      });
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
      calculateTax();

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
        }
      });
    }
    function del() {
      var rowId = $('#dataTable').jqGrid('getGridParam', 'selrow');
      if (rowId) {
        art.dialog({
          content: '删除该记录？',
          lock: true,
          ok: function () {
            var ret = selectDataTableRow();
            var rowNum = $("#dataTable").jqGrid('getGridParam', 'records');
            $('#dataTable').delRowData(rowId);
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
      }
    }
    function getReqPk() {

      return reqpk;
    }
    function getItemAppDetail() {

      return itemAppDetail;
    }
    function view() {
      var rowId = $('#dataTable').jqGrid('getGridParam', 'selrow');
      if (rowId) {
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
      if (rowId) {
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
      if (status=="2" && "${needRefCustCode }"=="true" && $("#faultAmount").val()=="") {
        art.dialog({
          content: "请填写承担金额"
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
			/*
			var amount = myRound($("#amount").val(),4);
			var checkAmount = myRound("${checkAmount }",4);
            // 审核时，如果结算金额小于0，进行提示。
            if(myRound(myRound(amount,4) + myRound(checkAmount,4)) < 0.0 && $.trim(status) == "2"){
	            art.dialog({
		            content: "总结算金额小于0，是否确定审核",
		            lock: true,
		            ok: function() {
		            	promptManageFeeAndTax(status, warn);
		            },
		            cancel: function() {
		                return true
		            }
		        })
            } else {
            	promptManageFeeAndTax(status, warn);
            }*/
            promptManageFeeAndTax(status, warn);
          }

        }
      });

    }
    
    // 添加税金与参考税金等值判断，重构之前只判断管理费与参考管理费是否等值的情况
    // 张海洋 20200608
    function promptManageFeeAndTax(status, warn) {
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
	    
	    var urlMap = {"2":"${ctx}/admin/itemChg/doComfirm",// 审核通过
	    		   "3":"${ctx}/admin/itemChg/doComfirmCancel", // 审核取消	
	    		   "4":"${ctx}/admin/itemChg/doComfirmReturn" // 审核退回
	    }
	
	    if (manageFee !== manageFeePer || tax !== referenceTax) {
	        art.dialog({
	            content: prompt,
	            lock: true,
	            ok: function() {
	                art.dialog({
	                    content: "是否要审核" + warn + "该增减单?",
	                    ok: function() {
	                        var datas = $("#dataForm").jsonForm()
	                        datas.detailJson = JSON.stringify($("#dataTable").jqGrid("getRowData"))
	                        var url = urlMap[status];
	                        $.ajax({
	                            url: url,
	                            type: 'post',
	                            data: datas,
	                            dataType: 'json',
	                            cache: false,
	                            error: function(obj) {
	                                showAjaxHtml({"hidden": false, "msg": '保存数据出错~'})
	                            },
	                            success: function(obj) {
	                                if (obj.rs) {
	                                    art.dialog({
	                                        content: obj.msg,
	                                        time: 1000,
	                                        beforeunload: function() {
	                                            closeWin(true, true)
	                                        }
	                                    })
	                                } else {
	                                    $("#_form_token_uniq_id").val(obj.token.token)
	                                    art.dialog({
	                                        content: obj.msg,
	                                        width: 200
	                                    })
	                                }
	                            }
	                        })
	                    },
	                    cancel: function() {
	                    }
	                })
	            },
	            cancel: function() {
	                return true
	            }
	        })
	    } else {
	        art.dialog({
	            content: "是否要审核" + warn + "该增减单?",
	            ok: function() {
	                var datas = $("#dataForm").jsonForm()
	                var url = urlMap[status];
	                datas.detailJson = JSON.stringify($("#dataTable").jqGrid("getRowData"))
	                $.ajax({
	                    url: url,
	                    type: 'post',
	                    data: datas,
	                    dataType: 'json',
	                    cache: false,
	                    error: function(obj) {
	                        showAjaxHtml({"hidden": false, "msg": '保存数据出错~'})
	                    },
	                    success: function(obj) {
	                        if (obj.rs) {
	                            art.dialog({
	                                content: obj.msg,
	                                time: 1000,
	                                beforeunload: function() {
	                                    closeWin(true, true)
	                                }
	                            })
	                        } else {
	                            $("#_form_token_uniq_id").val(obj.token.token)
	                            art.dialog({
	                                content: obj.msg,
	                                width: 200
	                            })
	                        }
	                    }
	                })
	            },
	            cancel: function() {
	            }
	        })
	    }
	}
    
    //校验函数
    $(function () {
	  if("${needRefCustCode }"=="true"){
		  $(".ref").removeClass("hidden");
	  }else{
		  $(".ref").addClass("hidden");
	  }
      changeFaultType();
      $("#refCustCode").openComponent_customer({showValue:"${itemChg.refCustCode }",showLabel:"${refCustCodeDescr}",readonly:true});

      if($.trim($("#itemType1").val())=="JC"){
      	checkIntCostPay();
      }
      $('*', '#dataForm').bind('focus', function () {
        data_iRow = 0;
      });
      $("#itemType1").attr("disabled", "disabled");
      var gridHeight = $(document).height() - $("#content-list").offset().top - 67;
      //if('${itemChg.isOutSet}'==2&&'${itemChg.itemType1}'.trim()!='RZ') gridHeight=$(document).height()-$("#content-list").offset().top-67; 
      var jqGridOption = {
        url: "${ctx}/admin/itemChgDetail/goJqGrid?no=${itemChg.no}",
        height: gridHeight,
        cellEdit: true,
        cellsubmit: 'clientArray',
        styleUI: 'Bootstrap',
        rowNum: 10000,
		colModel : [
		  {name: "iscommi", index: "iscommi", width: 84, label: "是否提成", sortable: true, align: "left", hidden: true},
		  {name: "iscommichx", index: "iscommichx", width: 84, label: "是否提成", sortable: true, align: "left", hidden: true,formatter:commiCheckBox},
		  {name: "fixareadescr", index: "fixareadescr", width: 92, label: "区域名称", sortable: true, align: "left",editable:true,edittype:'text',editoptions:{ dataEvents:[{type:'focus',fn:function(e){ $(e.target).openComponent_fixAreaDescr({condition:{'itemType1':"${itemChg.itemType1}".trim(),'custCode':'${itemChg.custCode}','isService':$("#isService").val()},onSelect:onFixAreaSelect});}}]}},
		  {name: "itemcode", index: "itemcode", width: 60, label: "材料编号", sortable: true, align: "left"},
		  {name: "fixareapk", index: "fixareapk", width: 92, label: "区域名称", sortable: true, align: "left", hidden: true},
		  {name: "intprodpk", index: "intprodpk", width: 80, label: "集成成品", sortable: true, align: "left", hidden: true},
		  {name: "intproddescr", index: "intproddescr", width: 90, label: "集成成品", sortable: true, align: "left", hidden: true},
		  {name: "itemdescr", index: "itemdescr", width: 210, label: "材料名称", sortable: true, align: "left",cellattr: addCellAttr,editable:true,edittype:'text',editoptions:{ dataEvents:[{type:'focus',fn:function(e){ $(e.target).openComponent_itemDescr({condition:{'itemType1':"${itemChg.itemType1}".trim(),'containCode':"1",'custType':"${itemChg.custType}"},onSelect:onSelect});}}]}},
		  {name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
		  {name: "itemtype2", index: "itemtype2", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
		  {name: "sqlcodedescr", index: "sqlcodedescr", width: 89, label: "品牌", sortable: true, align: "left", hidden: true},
		  {name: "itemtype3descr", index: "itemtype3descr", width: 85, label: "材料类型3", sortable: true, align: "left", hidden: true},
		  {name: "preqty", index: "preqty", width: 65, label: "已有数量", sortable: true, align: "right"},
		  {name: "qty", index: "qty", width: 65, label: "变动数量", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}, sum: true},
		  {name: "uom", index: "uom", width: 40, label: "单位", sortable: true, align: "left", hidden: true},
		  {name: "uomdescr", index: "uomdescr", width: 40, label: "单位", sortable: true, align: "left"},
		  {name: "marketprice", index: "marketprice", width: 68, label: "市场价", sortable: true, align: "right", hidden: true},
		  {name: "unitprice", index: "unitprice", width: 50, label: "单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
		  {name: "beflineamount", index: "beflineamount", width: 80, label: "折扣前金额", sortable: true, align: "right", sum: true},
		  {name: "markup", index: "markup", width: 40, label: "折扣", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true,integer:false,minValue:0}},
		  {name: "tmplineamount", index: "tmplineamount", width: 77, label: "小计", sortable: true, align: "right", sum: true},
		  {name: "processcost", index: "processcost", width: 60, label: "其他费用", sortable: true, align: "right", sum: true,editable:true,edittype:'text',editrules:{number:true,required:true}},
		  {name: "lineamount", index: "lineamount", width: 80, label: "总价", sortable: true, align: "right", sum: true},
		  {name: "isoutsetdescr", index: "isoutsetdescr", width: 50, label: "套外", sortable: true, align: "left", hidden: true},
		  {name: "disccost", index: "disccost", width: 104, label: "优惠分摊成本", sortable: true, align: "left", sum: true, hidden: true},
		  {name: "remarks", index: "remarks", width: 177, label: "材料描述", sortable: true, align: "left",editable:true,edittype:'textarea'},
		  {name: "itemsetno", index: "itemsetno", width: 118, label: "套餐包 ", sortable: true, align: "left", hidden: true},
		  {name: "itemsetdescr", index: "itemsetdescr", width: 100, label: "套餐包 ", sortable: true, align: "left", hidden: true},
		  {name: "algorithm", index: "algorithm", width: 85, label: "算法编号", width: 0.5, sortable:false, align: "left"},
		  {name: "algorithmdescr", index: "algorithmdescr", width: 100, label: "算法", sortable: true, align: "left",cellattr: addCellAttr,editable:true,
		  		edittype:'select',
		  		editoptions:{ 
		  			dataUrl: "${ctx}/admin/item/getAlgorithm",
					postData: function(){
						var ret = selectDataTableRow("dataTable");
						if(ret && ret.reqpk == ""){
							return {
								code:ret.itemcode
							};
						}
						return {code: ""};
					},
					buildSelect: function(e){
						var ret = selectDataTableRow("dataTable");

						var lists = JSON.parse(e);
						var html = "<option value=\"\" ></option>";
						if(ret.reqpk == ""){
							for(var i = 0; lists && i < lists.length; i++){
								html += "<option value=\""+ lists[i].Code +"\">" + lists[i].Descr + "</option>"
							}
							return "<select style=\"font-family:宋体;\"> " + html + "</select>";
						} else {
							return "<div></div>";
						}

					},
		  			dataEvents:[{
	  					type:'change',
	  					fn:function(e){ 
	  						algorithmClick(e)
	  					}
		  			}, 
		  		]}
	 	},
		 
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
		  {name: "cuttype", index: "cuttype", width: 65, label: "切割类型编号",  width: 0.5,sortable:false, align: "left"},
		  {name: "supplpromdescr", index: "supplpromdescr", width: 75, label: "促销活动", sortable: true, align: "left",editable:true,edittype:'textarea'},
	      {name: "algorithmper", index: "algorithmper", width: 80, label: "算法系数", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true},hidden:($.trim('${itemChg.itemType1}')== 'RZ'|| $.trim('${itemChg.itemType1}')== 'JC')?true:false,},
		  {name: "algorithmdeduct", index: "algorithmdeduct", width: 80, label: "算法扣除数", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true},hidden:($.trim('${itemChg.itemType1}')== 'RZ'|| $.trim('${itemChg.itemType1}')== 'JC')?true:false,},
		  {name: "lastupdate", index: "lastupdate", width: 85, label: "最后修改日期", sortable: true, align: "left", formatter: formatTime},
		  {name: "lastupdatedby", index: "lastupdatedby", width: 78, label: "修改人", sortable: true, align: "left"},
		  {name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
		  {name: "actionlog", index: "actionlog", width: 68, label: "操作", sortable: true, align: "left"},
		  {name: "reqpk", index: "reqpk", width: 0.5, label: "需求PK", sortable: false, align: "left"},
		  {name: "isfixprice", index: "isfixprice", width: 20, label: "是否固定价格", sortable: true, align: "left", hidden: true},
		  {name: "isoutset", index: "isoutset", width: 20, label: "套餐外材料", sortable: true, align: "left", hidden: true},
		  {name: "isservice", index: "isservice", width: 20, label: "是否服务性产品", sortable: true, align: "left", hidden: true},
		  {name: "pk", index: "pk", width: 20, label: "编号", sortable: true, align: "left", hidden: true},
		  {name: "no", index: "no", width: 20, label: "增减单号", sortable: true, align: "left", hidden: true},
		  {name: "custtypeitempk", index: "custtypeitempk", width: 0.5, label: "套餐材料信息编号", sortable: true, align: "left"},
		  {name: "cost", index: "cost", width: 80, label: "成本", sortable: true, align: "right", hidden: true},
		  {name: "allcost", index: "allcost", width: 80, label: "成本总价", sortable: true, align: "right", hidden: true},
		  {name: "dispseq", index: "dispseq", width: 68, label: "操作", sortable: true, align: "left", hidden: true},
		  {name: "supplpromitempk", index: "supplpromitempk", width: 68, label: "供应商促销PK", sortable: true, align: "left",},
		  {name: "doorpk", index: "doorpk", width: 65, label: "门窗Pk",  width: 0.5,sortable:false, align: "left"},
		  {name: "refchgreqpk", index: "refchgreqpk", width: 65, label: "相关需求pk",  width: 0.5,sortable:false, align: "left"},
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

            existsSetDeduction()
        },
        afterSaveCell: function (id, name, val, iRow, iCol) {
          $("#dataTable").setCell(id, 'lastupdatedby', '${itemChg.lastUpdatedBy}');
          if (name == 'itemdescr')
            $("#dataTable").setCell(id, 'itemdescr', itemdescr);
          var rowData = $("#dataTable").jqGrid('getRowData', id);
          var processcost = parseFloat(rowData.processcost);
          switch (name) {
            case 'qty':
              $("#dataTable").setCell(id, 'beflineamount', myRound(val * rowData.unitprice, 4));
              $("#dataTable").setCell(id, 'tmplineamount', myRound(myRound(val * rowData.unitprice, 4) * rowData.markup / 100));
              $("#dataTable").setCell(id, 'lineamount', myRound(myRound(myRound(val * rowData.unitprice, 4) * rowData.markup / 100) + processcost));
              $("#dataTable").setCell(id, 'allcost', myRound(val * rowData.cost, 4));
              $("#dataTable").footerData('set', {"qty": $("#dataTable").getCol('qty', false, 'sum')});
              if($.trim(rowData.cuttype) != "" && $.trim(rowData.isoutset)=='1'){
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
        beforeSelectRow: function (id) {
          data_iCol = 0;
          $(".search-suggest").hide();
          itemdescr = $('#dataTable').getCell(id, "itemdescr");
          setTimeout(function () {
            relocate(id);
          }, 100)
        },
        beforeEditCell: function (rowid, cellname, value, iRow, iCol) {
          data_iRow = iRow;
          data_iCol = iCol;
        }
      };
      //软装默认按材料类型2分类
      if ('${itemChg.itemType1}'.trim() == 'RZ') $.extend(jqGridOption, {grouping: true});
      Global.JqGrid.initJqGrid("dataTable", jqGridOption);
      if ('${itemChg.itemType1}'.trim() == 'RZ') {
        $("#dataTable").setGridParam().showCol("itemsetdescr").trigger("reloadGrid");
        $(".nav-checkbox").hide();
      }
      if ('${itemChg.isService}' == '1') {
        $("#dataTable").setGridParam().showCol("iscommichx").trigger("reloadGrid");
      }
      var offset = 68;
      var target = "collapse";
      var table = "dataTable";
      $('#' + target).on('shown.bs.collapse', function (e) {
        $.each($(".panel-title").find("a").children(), function (k, v) {
          if ($(v).is(':hidden'))  $(v).show();
          else $(v).hide();
        });
        $("#" + table).setGridHeight($("#" + table).jqGrid("getGridParam", "height") - e.currentTarget.clientHeight - offset);
        $("#loadMore").hide();
        if ($('#' + target).is(":hidden"))
          $('#' + target).show();
        else $('#' + target).hide();
        $("#remarksLi").hide();

      })
      $('#' + target).on('hide.bs.collapse', function (e) {
        $.each($(".panel-title").find("a").children(), function (k, v) {
          if ($(v).is(':hidden'))  $(v).show();
          else $(v).hide();
        });

        $("#" + table).setGridHeight($("#" + table).jqGrid("getGridParam", "height") + e.currentTarget.clientHeight + offset);
        $("#loadMore").show();
        if ($('#' + target).is(":hidden"))
          $('#' + target).show();
        else $('#' + target).hide();
        $("#remarksLi").show();
      })
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
      $("#isAddAllInfo").prop("disabled", true);

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
          postData: {chgStakeholderList: param.detailJson, m_umState: "C"},   
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
      
      if('${itemChg.itemType1}'.trim()=="RZ"){
      	$("#discTokenNo_li").show();   
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
        $("#dataTable").setGridParam().showCol("sqlcodedescr").trigger("reloadGrid");
        $("#dataTable").setGridParam().showCol("marketprice").trigger("reloadGrid");
        $("#referenceTaxLi").show()
        $("#goSoftChgItemStatus").show();
      } else if ('${itemChg.itemType1}'.trim() == 'ZC') {
        if ('${itemChg.containMain}' == 0 || '${itemChg.containMainServ}' == 0) {
          $("#ys").show();
        }
      }
      if ('${itemChg.isOutSet}' == '2') $("#dataTable").setGridParam().showCol("isoutsetdescr").trigger("reloadGrid");
      if ('${itemChg.isService}' == '1') {
        $("#befAmountLi").hide();
        $("#discAmountLi").hide();
        $("#discCostLi").hide();
      }
      if ('${itemChg.costRight}' == '1') {
        $("#dataTable").setGridParam().showCol("cost").trigger("reloadGrid");
        $("#dataTable").setGridParam().showCol("allcost").trigger("reloadGrid");
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
      } else {
        $("#dataTable").jqGrid("setGridParam", {
          url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
          postData: {'params': params, 'orderBy': 'dispseq'},
          page: 1,
          grouping: false
        }).trigger("reloadGrid");
      }
    }
    function goTop(table) {
      if (!table)  table = "dataTable";
      var rowId = $("#" + table).jqGrid('getGridParam', 'selrow');
      var replaceId = parseInt(rowId) - 1;
      if (rowId) {
        if (rowId > 1) {
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
      if (rowId) {
        if (rowId < rowNum) {
          var ret1 = $("#" + table).jqGrid('getRowData', rowId);
          var ret2 = $("#" + table).jqGrid('getRowData', replaceId);
          //是否按照材料类型2分类
          if ('${itemChg.itemType1}'.trim() == 'RZ' || $("#itemType2Group").is(":checked")) {

            if (ret1.itemtype2descr == ret2.itemtype2descr) {
              scrollToPosi(replaceId, table, ret1.itemtype2descr);
              replace(rowId, replaceId);
              $("#dataTable").setCell(rowId, 'dispseq', ret1.dispseq);
              $("#dataTable").setCell(replaceId, 'dispseq', ret2.dispseq);
            }
          } else {
            scrollToPosi(replaceId);
            replace(rowId, replaceId);
            $("#dataTable").setCell(rowId, 'dispseq', ret1.dispseq);
            $("#dataTable").setCell(replaceId, 'dispseq', ret2.dispseq);

          }

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
        'remarks': json.remark,
        'allcost': myRound(json.cost * rowData.qty, 4)
      });
      
      existsSetDeduction()
    }
    function onFixAreaSelect(json) {
      var rowid = $("#dataTable").getGridParam("selrow");
      $("#dataTable").jqGrid("saveCell", rowid, data_iCol);
      $("#dataTable").setRowData(rowid, {'fixareapk': json.PK, 'fixareadescr': json.Descr});
    }
    //document.addEventListener('visibilitychange', handleVisibilityChange, false);
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
    function checkIntCostPay(){
         $.ajax({
              url: '${ctx}/admin/itemApp/doSubmitCheck',
              type: 'post',
              data: {custCode:$("#custCode").val(),isCupboard:$("#isCupboard").val(),itemType1:$("#itemType1").val(),m_umState:'C',custType:"${itemChg.custType}",chgNo:"${itemChg.no}"},
              dataType: 'json',
              cache: false,
              error: function (obj) {
                showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
              },
              success: function (data) {
          		 	if(data.msg!="null&null"){
							var msg=data.msg.split("&")[0];
							$("#intCostTip").html("<button id=\"intCostTipBtn\" class=\"btn btn-link\" style=\"margin:0px;padding:0px;font-size:12px;color:red\">"+msg+"</button>");
							$("#intCostTipBtn").on("click",function(){
								Global.Dialog.showDialog("itemQtyTip",{
									title:"成本控制明细",
					        	  	url:"${ctx}/admin/itemPreApp/goIntCostTipClickPage",
					        	  	postData:{
					        	  		custCode:$("#custCode").val(),
										isCupboard:$("#isCupboard").val(),
										itemType1:$("#itemType1").val(),
										resultList:data.msg.split("&")[1]
					        	  	},
					        	  	height: 600,
					        	 	width:800,
					        	});
							}).mouseover(function(){
								$("#intCostTipBtn").css({
									"font-weight":"bold",
									"font-style":"italic"
								});
							}).mouseout(function(){
								$("#intCostTipBtn").css({
									"font-weight":"",
									"font-style":""
								});
							});
							//$("#intCostTip").html(data.msg);
						}
              }
         });
	} 
	function calculateTax(){
		if ($("#amount").val()!=""){
			var lineamount = parseFloat($("#befAmount").val());
			var dDiscAmount = parseFloat($("#discAmount").val() == "" ? 0.0 : $("#discAmount").val());	
		 	if (lineamount < 0)  dDiscAmount = -dDiscAmount;
			$.ajax({
				url:"${ctx}/admin/itemPlan/getTax",
				type:"post",
				data:{code:"${itemChg.custCode}", contractFee:parseFloat($("#amount").val())+parseFloat($("#manageFee").val()),
					designFee:0,taxCallType:'2',discAmount:dDiscAmount,
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
	
	function goDiscAmtTran(){
		var custCode = $.trim($("#custCode").val());
		Global.Dialog.showDialog("DiscAmtTran",{
			title:"优惠调度调整",
			url:"${ctx}/admin/itemChg/goDiscAmtTran",
			postData:{custCode:custCode},
			height:700,
			width:1050,
			returnFun:goto_query
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
				readonly:true,
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
				readonly:true,
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
			datas.algorithm = val;
			
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
	
	function discTokenSel(){
		var hasSelectNo =$("#discTokenNo").val() ;
	    Global.Dialog.showDialog("discToken",{
			title: "优惠券信息编辑",
			url: "${ctx}/admin/discTokenQuery/goDiscTokenSelect",
			postData: {
			    hasSelectNo: hasSelectNo,
	            m_umState:"V"
		    },
			height: 458,
			width: 660,
	     });  
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
	
  </script>

</head>

<body onload="init()">
<div class="body-box-form">
  <div class="panel panel-system">
    <div class="panel-body">
      <div class="btn-group-xs">
        <button type="button" id="saveBtn" class="btn btn-system " onclick="doConfirm(2,'通过')">审核通过</button>
        <button type="button" class="btn btn-system " onclick="doConfirm(3,'取消')">审核取消</button>
        <button type="button" class="btn btn-system " onclick="doConfirm(4,'退回')">审核退回</button>
        <button type="button" class="btn btn-system " onclick="goDiscAmtTran()">优惠额度调整</button>
        <button type="button" class="btn btn-system " onclick="viewSetDeductions()">套内减项</button>
        <button type="button" class="btn btn-system " onclick="closeWin(true,true)">关闭</button>
		<span id="totalRemainDisc" style="color:red;margin:0 20px">总优惠余额：${totalRemainDisc }</span>
		<span id="intCostTip" style="color:red"></span>
		<span id="existsSetDeduction" style="color:red;margin:0 200px;display:none">存在套内减项</span>
      </div>
    </div>
  </div>
  <div class="panel panel-info">
    <div class="panel-body">
      <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
        <house:token></house:token>
        <input type="hidden" name="m_umState" id="m_umState" value="C"/>
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
            <input type="text" id="address"
                   name="address"
                   readonly="readonly" value="${itemChg.address}"/>
            <span id="innerArea" style="position:absolute;top:5px;left:295px;"></span>
          </li>
          <li id="befAmountLi">
            <label>优惠前金额</label>
            <input type="text" id="befAmount"
                   name="befAmount"
                   readonly="readonly" value="${itemChg.befAmount}"/>
          </li>
          <li id="discAmountLi">

            <label>优惠金额</label>
            <input type="text" id="discAmount"
                   name="discAmount"
                   value="${itemChg.discAmount}" onblur="changeAmount()"/>
          </li>
          <li id="discCostLi">
            <label>其中产品优惠</label>
            <input type="text" id="discCost"
                   name="discCost"
                   value="${itemChg.discCost}"/>
          </li>
          <li hidden="true" id="discTokenNo_li">
			 <label>优惠券</label>
			 <input type="text" id="discTokenAmount" name="discTokenAmount" value="${itemChg.discTokenAmount}" readonly="readonly" style="width: 125px;"/>
			 <input type="hidden" id="discTokenNo" name="discTokenNo" value="${itemChg.discTokenNo}" readonly="readonly" style="width: 125px;"/>
			 <button type="button" class="btn btn-system" id="discTokenBtn"  onclick="discTokenSel()" style="font-size: 12px;margin-top:-4px;margin-left: -8px;width: 35px;">查看</button>	
		 </li>
          <li>
            <label>实际总价</label>
            <input type="text" id="amount"
                   name="amount"
                   readonly="readonly" value="${itemChg.amount }"/>
          </li>
          <li id="manageFeeLi">
            <label>管理费</label>
            <input type="text" id="manageFee" name="manageFee" value="${itemChg.manageFee}" onchange="changeManageFee()"/>
          </li>
          <li id="manageFeePerLi">
            <label>参考管理费</label>
            <input type="text" id="manageFeePer"
                   name="manageFeePer"
                   readonly="readonly" value="0"/>
          </li>
          <li>
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
              <option value="1" ${itemChg.isService=='1' ?
              'selected':'' }>是</option>
            </select>
          </li>
          <li>
            <label for="isAddAllInfo"><span class="required">*</span>常规变更</label>
            <house:xtdm id="isAddAllInfo" dictCode="YESNO" style="width:160px;" value="${itemChg.isAddAllInfo}"/>
          </li>
          <li>
            <label><span class="required" id="itemChgStakeholderRequired" hidden="true">*</span>业务员</label>
            <div class="input-group">
              <input type="text" class="form-control" id="itemChgStakeholder" style="width: 121px;" readonly="readonly">
              <button type="button" class="btn btn-system" id="itemChgStakeholderBtn" style="font-size: 12px;width: 40px;">编辑</button>
            </div>
          </li>
          <li>	
			<label>报价模板</label>
			<input type="text" id="tempDescr" name="tempDescr" readonly="readonly" value="${itemChg.tempDescr}"/>
		  </li>
          <li id="remarksLi" style="display: none">
            <label class="control-textarea">备注</label>
            <textarea id="remarks" name="remarks" onchange="updateRemarks(this)">${itemChg.remarks }</textarea>
          </li>
          <li id="loadMore" style="display: none">
            <button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">更多</button>
          </li>
          <div class="collapse in " id="collapse">
            <ul>
              <li>
                <label>批次号</label>
                <input type="text" readonly="readonly"
                       id="no" name="no"
                       value="${itemChg.no}"/>
              </li>
              <li>
                <label>客户编号</label>
                <input type="text"
                       id="custCode" name="custCode"
                       value="${itemChg.custCode}" readonly="readonly"/>
              </li>
              <li>
                <label>档案编号</label>
                <input type="text" id="documentNo"
                       name="documentNo"
                       value="${itemChg.documentNo}"/>
              </li>
              <li>
                <label>材料类型1</label>
                <house:DataSelect id="itemType1" className="ItemType1" keyColumn="code" valueColumn="descr"
                                  value="${itemChg.itemType1 }"></house:DataSelect>
              </li>
              <li>
                <label>材料增减状态</label>
                <input type="text" id="statusDescr"
                       name="statusDescr"
                       readonly="readonly" value="${itemChg.statusDescr}"/>
              </li>
              <li>
                <label>申请人</label>
                <input type="text"
                       id="appCzyDescr" name="appCzyDescr"
                       value="${itemChg.appCzy}|${itemChg.appCzyDescr}" readonly="readonly"/>
              </li>
              <li>
                <label>变更日期</label>
                <input type="text" id="date"
                       name="date" readonly="readonly"
                       value="<fmt:formatDate value='${itemChg.date}' pattern='yyyy-MM-dd'/>"/>
              </li>
              <li>
                <label>审核人</label>
                <input type="text" id="confirmCzyDescr"
                       name="confirmCzyDescr"
                       readonly="readonly" value="${itemChg.confirmCzy}${itemChg.confirmCzyDescr}"/>
              </li>


              <li>
                <label>审核日期</label>
                <input type="text" id="confirmdate"
                       name="confirmdate" readonly="readonly"
                       readonly="readonly"
                       value="<fmt:formatDate value='${itemChg.confirmdate}' pattern='yyyy-MM-dd'/>"/>
              </li>
				<li >
					<label>计划发货日期</label>
					<input type="text" id="planArriveDate" name="planArriveDate" 
					class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
                       value="<fmt:formatDate value='${itemChg.planArriveDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
				</li>
				<li class="form-validate">
		           <label>关联客户</label>
		           <input type="text" id="refCustCode"
		                  name="refCustCode"
		                  readonly="readonly" value="${itemChg.refCustCode}"/>
		        </li>
				<div class="ref"><!-- 有关联楼盘才显示 -->
					<li><label>责任人类型</label> <house:xtdm id="faultType" 
						dictCode="FAULTTYPE" unShowValue="4" onchange="changeFaultType();"
						value="${itemChg.faultType}"></house:xtdm>
					</li>
					<li><label>员工</label> <input type="text" id="faultEmp"
						name="faultEmp" />
					</li>
					<li class="hidden"><label>工人</label> <input type="text" id="faultWorker"
						name="faultWorker" />
					</li>
					<li><label>承担金额</label> <input type="text"
						id="faultAmount" name="faultAmount" style="width:160px;" />
					</li>
					<li><label>项目经理质保余额</label> <input type="text"
						id="prjQualityFee" name="prjQualityFee" style="width:160px;"
						value="${ itemChg.prjQualityFee}" readonly/>
					</li>
				</div>	
              <li>
                <label class="control-textarea">备注</label>
                <textarea id="remark" name="remark" onchange="updateRemarks(this)">${itemChg.remarks }</textarea>
              </li>
              <li>
                <label class="control-textarea">审核异常说明</label>
                <textarea id="exceptionRemarks" name="exceptionRemarks">${itemChg.exceptionRemarks }</textarea>
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
      <button type="button" class="btn btn-system " onclick="edit()">编辑</button>
      <button type="button" class="btn btn-system "
              onclick="doExcelNow('${itemChg.itemType1}'.trim()=='ZC'?'主材增减':'${itemChg.itemType1}'.trim()=='JC'?'集成增减':'软装增减','')">
        导出excel
      </button>
      <button type="button" class="btn btn-system " onclick="goTop()">向上</button>
      <button type="button" class="btn btn-system " onclick="goBottom()">向下</button>
      <button type="button" class="btn btn-system " onclick="goImportExcel()">导入excel</button>
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
      <li class="nav-checkbox"><input type="checkbox" id="itemType2Group" name="itemType2Group"
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
</div>
</body>
</html>
