<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<% String fromFlag = request.getParameter("fromFlag"); %>

<script type="text/javascript">
	$(function(){
	 	Global.JqGrid.initJqGrid("dataTable_account",{
			rowNum: 10000,
			url:"${ctx}/admin/laborFee/goLaborFeeAccountJqGrid",
			height:270,
			postData:{no:$.trim("${laborFee.no}")},
			styleUI:"Bootstrap",
			colModel:[
				{name: "pk", index: "pk", width: 117, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "actname", index: "actname", width: 117, label: "户名", sortable: true, align: "left"},
				{name: "bank", index: "bank", width: 127, label: "开户行", sortable: true, align: "left",},
				{name: "cardid", index: "cardid", width: 235, label: "卡号", sortable: true, align: "left"},
				{name: "amount", index: "amount", width: 120, label: "金额", sortable: true, align: "right", sum: true},
				{name: "deductamount", index: "deductamount", width: 120, label: "扣款", sortable: true, align: "right", sum: true, hidden: true},
				{name: "netamount", index: "netamount", width: 120, label: "实发金额", sortable: true, align: "right", sum: true, hidden: true},
			],
		});
		
		(function(action) {
		    switch (action) {
		        case "A":
		            break
		        case "M":
		            break
		        case "C":
		            $("#dataTable_account").showCol(["deductamount", "netamount"])
		            break
		        case "V":
		            $("#dataTable_account").showCol(["deductamount", "netamount"])
		            break
		        case "W": //W:出纳签字
		            $("#dataTable_account").showCol(["netamount"])
		            break
		        default:
		    }
		})("${laborFee.m_umState}")
		
	});
	
	function addAccount(){
		Global.Dialog.showDialog("addAccount",{
			title:"人工费用账号——新增",
			postData:{},
			url:"${ctx}/admin/laborFee/goAddAccount",
			height:345,
			width:730,
	        resizable: true,
			returnFun:function(data){
				 var json = {
					actname: data.actName,
					bank: data.bank,
					amount: data.amount,
					cardid: data.cardId,
					deductamount: 0
				};
				Global.JqGrid.addRowData("dataTable_account",json);
			}
		});	
	}
	
	function updateAccount(){
		var ret = selectDataTableRow("dataTable_account");
		
		if(!ret){
			art.dialog({
				content:"请选择一条记录进行编辑",
			});
			return;
		}
		
		var rowId = $("#dataTable_account").jqGrid("getGridParam","selrow");
		
		Global.Dialog.showDialog("updaetAccount",{
			title:"人工费用账号——编辑",
			postData:{
			    m_umState: "${laborFee.m_umState}",
			    actName: ret.actname,
			    bank: ret.bank,
			    cardId: ret.cardid,
			    amount:ret.amount,
			    deductAmount: ret.deductamount
			},
			url:"${ctx}/admin/laborFee/goUpdateAccount",
			height:345,
			width:730,
	        resizable: true,
			returnFun:function(data){
				 var json = {
					actname: data.actName,
					bank: data.bank,
					amount: data.amount,
					cardid: data.cardId,
					deductamount: data.deductAmount,
					netamount: parseFloat(data.amount) - parseFloat(data.deductAmount)
				};
				
				Global.JqGrid.updRowData("dataTable_account", rowId, json)
			}
		});	
	}
	
	function delRow(){
		var id = $("#dataTable_account").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		art.dialog({
			 content:"是否确认要删除账号",
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
				Global.JqGrid.delRowData("dataTable_account",id);
			},
			cancel: function () {
				return true;
			}
		});
	};
	
	function dtlImpAccount(){
		console.log(123);
		var  detailData= Global.JqGrid.allToJson("dataTable");
		var detailJson = detailData.datas;
		var actNameCardIdArry = [];
		var sum = 0;
		
		if(detailJson.length == 0){
			art.dialog({
				content:"明细表数据为空"
			});
			return;
		}
		
		var declareTable = true;
		$.each(detailJson, function(k, v){
			var json = {
				actname: v.actname,
				cardid: v.cardid,
				amount: v.amount,
			}
			var actNameCardId = $.trim(v.actname) + $.trim(v.cardid);
			// 户名和卡号同时不为空才导入
			if(v.actname != "" && v.cardid != ""){
				// 第一次导入时将原本表格里面的数据清空
				if(declareTable){
					$("#dataTable_account").jqGrid("clearGridData");
					declareTable = false;
				}
				// 导入的时候 如果已经有相同的户名账号 汇总起来
				var index = actNameCardIdArry.indexOf(actNameCardId);
			   		sum += myRound(v.amount,4);
				if(index >= 0){
					var rowData = $("#dataTable_account").jqGrid("getRowData",myRound(index)+1);
					rowData.amount = myRound(rowData.amount,4);  //四舍五入取整导致总金额不一致不能保存，保留到4位
					rowData.amount += myRound(v.amount,4); 
					//Global.JqGrid.updRowData("dataTable_account",myRound(index)+1,rowData);
					
			   		$("#dataTable_account").setRowData(myRound(index)+1,rowData);
				} else {
					var count = $("#dataTable_account").getGridParam("reccount");//当前有几行
					actNameCardIdArry.push(actNameCardId);
					$("#dataTable_account").addRowData(myRound(count)+1, json, "last");
				}
			}
		});
		// 应该是 setRowData有问题  会导致合计没重新计算
	  	$("#dataTable_account").footerData("set",{"amount":myRound(sum,4)},false);		
	}
</script>
<div class="body-box-list" style="margin-top: 0px;">
	
	<div id="content-list">
		<table id="dataTable_account"></table>
	</div>
</div>
