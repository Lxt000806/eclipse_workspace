<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>领料管理发货单查询</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
		
		<script type="text/javascript">
		function goto_query(){
			$("#llglSendListDataTable").jqGrid("setGridParam",{
				url:"${ctx}/admin/itemApp/goJqGridLlglSendList",
				postData:$("#page_form").jsonForm(),
				page:1
			}).trigger("reloadGrid");
		}
		function doExcel(){
			var str = $("#llglSendListDataTablePager_right div").html();
			if(str != ""){
				str = str.substring(str.indexOf("共")+1,str.indexOf("条"));
				str = str.replace(/,/g,"");
				if(parseInt(str) > 100000){
					art.dialog({content:"数据超过100000条,无法导出"});
					return;
				}
				doExcelAll("${ctx}/admin/itemApp/doExcelLlglSendList", "llglSendListDataTable", "page_form"); 
			}else{
				art.dialog({
					content:"请先检索出数据"
				});
			}
		}
		function itemType1Change(){
	        $.ajax({
				url : "${ctx}/admin/itemPreApp/getItemType2Opt",
				data : {
					itemType1:$("#itemType1").val()
				},
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: "json",
				type: "post",
				cache: false,
			    success: function(obj){
			    	$("#itemType2").html(obj.html);
				}
			});
		}
		function view(){
			var ret = selectDataTableRow("llglSendListDataTable");
			if(ret){
	        	Global.Dialog.showDialog("fhDetail",{
	        	  title:"发货明细查看",
	        	  url:"${ctx}/admin/itemApp/goFhDetail",
	        	  postData:{
	        	  	fhNo:ret.no.trim()
	        	  },
	        	  height: 550,
	        	  width:1100
	        	});
			}else{
				art.dialog({
					content:"请选择一条记录"
				});
			}
		}
		function confirm(){
			var ret = selectDataTableRow("llglSendListDataTable");
			if(ret){
				$.ajax({
					url : "${ctx}/admin/itemApp/getGoArriveConfirmBefore",
					data: {
						no:ret.no.trim(),
						confirmStatus:ret.confirmstatus.trim()
					},
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					dataType: "json",
					type: "post",
					cache: false,
					error: function(){
				        art.dialog({
							content: "到货确认出现异常"
						});
				    },
				    success:function(obj){
				    	if(obj.rs){
				        	Global.Dialog.showDialog("arriveConfirm",{
				        		title:"到货确认",
								url:"${ctx}/admin/itemApp/goArriveConfirm",
								postData:{
									no:ret.no.trim(), 
									confirmStatus:ret.confirmstatus.trim(), 
									confirmReason:ret.confirmreason.trim(), 
									projectManRemark:ret.projectmanremark
								},
								height: 300,
								width:800,
								returnFun:goto_query
				        	});
				    	}else{
				    		art.dialog({
				    			content:obj.msg
				    		});
				    	}
				    }
    			});
			}else{
				art.dialog({
					content:"请选择一条记录"
				});
			}
		}
		/**初始化表格*/
		$(function(){
			$("#custCode").openComponent_customer({
				condition:{
					mobileHidden:true,
					status:"4,5",
					disabledEle:"status_NAME"
				}
			});
			$("#itemCode").openComponent_item();
		    $("#supplCode").openComponent_supplier({
		    	condition:{
		    		itemRight:"${data.itemRightForSupplier}"
		    	}
		    });
			$("#appCzy").openComponent_employee();
	        //初始化表格
			Global.JqGrid.initJqGrid("llglSendListDataTable",{
				height:$(document).height()-$("#content-list").offset().top-100,
				colModel : [
					{name: "custcode", index: "custcode", width: 95, label: "客户编号", sortable: true, align: "left"},
					{name: "documentno", index: "documentno", width: 70, label: "档案号", sortable: true, align: "left"},
					{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left"},
					{name: "mainbusinessman", index: "mainbusinessman", width: 70, label: "主材管家", sortable: true, align: "left"},
					{name: "businessmandescr", index: "businessmandescr", width: 80, label: "业务员", sortable: true, align: "left"},
					{name: "no", index: "no", width: 120, label: "发货单号", sortable: true, align: "left"},
					{name: "iano", index: "iano", width: 105, label: "领料单号", sortable: true, align: "left"},
					{name: "date", index: "date", width: 100, label: "发货日期", sortable: true, align: "left", formatter: formatTime},
					{name: "itemtype1descr", index: "itemtype1descr", width: 93, label: "材料类型1", sortable: true, align: "left"},
					{name: "itemtype2descr", index: "itemtype2descr", width: 92, label: "材料类型2", sortable: true, align: "left"},
					{name: "whcodedescr", index: "whcodedescr", width: 165, label: "仓库名称", sortable: true, align: "left"},
					{name: "supplierdescr", index: "supplierdescr", width: 98, label: "供应商", sortable: true, align: "left"},
					{name: "typedescr", index: "typedescr", width: 90, label: "领料类型", sortable: true, align: "left"},
					{name: "saleamount", index: "saleamount", width: 90, label: "销售总价", sortable: true, align: "right", sum: true},
					{name: "saleamountaftdisc", index: "saleamountaftdisc", width: 98, label: "优惠后总价", sortable: true, align: "right", sum: true},
					{name: "remarks", index: "remarks", width: 220, label: "备注", sortable: true, align: "left"},
					{name: "confirmstatusdescr", index: "confirmstatusdescr", width: 104, label: "到货确认状态", sortable: true, align: "left"},
					{name: "confirmmandescr", index: "confirmmandescr", width: 93, label: "到货确认人", sortable: true, align: "left"},
					{name: "confirmdate", index: "confirmdate", width: 105, label: "到货确认日期", sortable: true, align: "left", formatter: formatTime},
					{name: "confirmreasondescr", index: "confirmreasondescr", width: 111, label: "到货异常原因", sortable: true, align: "left"},
					{name: "projectmanremark", index: "projectmanremark", width: 220, label: "项目经理备注", sortable: true, align: "left"},
					
					{name: "confirmstatus", index: "confirmstatus", width: 220, label: "confirmstatus", sortable: true, align: "left",hidden:true},
					{name: "confirmreason", index: "confirmreason", width: 220, label: "confirmreason", sortable: true, align: "left",hidden:true}
	            ]
			});
			if("${data.confirmRight}" == "1"){
				$("#confirmBut").removeAttr("disabled");
			}
		});
		</script>
	</head>
	    
	<body>
		<div class="body-box-list" style="overflow-x:hidden">
			<div class="body-box-form">
				<div class="panel panel-system">
				    <div class="panel-body">
				    	<div class="btn-group-xs">
							<button id="confirmBut" type="button" class="btn btn-system" onclick="confirm()" disabled>到货确认</button>	
							<button id="viewBut" type="button" class="btn btn-system" onclick="view()">查看</button>	
							<button id="excelBut" type="button" class="btn btn-system" onclick="doExcel()">输出到Excel</button>	
							<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">关闭</button>	
						</div>
					</div>
				</div>
			</div>
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="m_umState" name="m_umState" value="${data.m_umState }"/>
					<input type="hidden" id="expired" name="expired" value="${data.expired }"/>
					<ul class="ul-form">
						<li>
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode"/>
						</li>
						<li>
							<label>档案号</label>
							<input type="text" id="custDocumentNo" name="custDocumentNo"/>
						</li>
						<li>
							<label>发货单号</label>
							<input type="text" id="SendNo" name="SendNo"/>
						</li>
						<li>
							<label>领料单号</label>
							<input type="text" id="no" name="no"/>
						</li>
						<li>
							<label>仓库编号</label>
							<house:DictMulitSelect id="whcode" dictCode="" 
								sql="select Desc1 fd,Code from tWareHouse" sqlValueKey="Code" sqlLableKey="fd">
							</house:DictMulitSelect>
						</li>
						<li>
							<label>发货日期从</label>
							<input type="text" id="sendDateFrom" name="sendDateFrom" class="i-date" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="sendDateTo" name="sendDateTo" class="i-date" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label>材料类型1</label>
							<house:dict id="itemType1" dictCode="" 
										sql="select Code,Descr from tItemType1 where Expired='F' and Code in ${data.itemRight} order by DispSeq" 
										sqlValueKey="Code" sqlLableKey="Descr"  onchange="itemType1Change()">
							</house:dict>
						</li>
						<li>
							<label>材料类型2</label>
							<select id="itemType2" name="itemType2"></select>
						</li>
						<li>
							<label>到货确认状态</label>
							<%-- <house:xtdmMulit id="sendCfmStatus" dictCode="SENDCFMSTATUS" selectedValue="${data.sendCfmStatus }"></house:xtdmMulit> --%>
							<house:xtdm id="sendCfmStatus" dictCode="SENDCFMSTATUS" value="${data.sendCfmStatus }"></house:xtdm>
						</li>
						<li>
							<label>多次配送原因</label>
							<house:xtdm id="manySendRsn" dictCode="MSENDRSN" value="${data.manySendRsn }"></house:xtdm>
						</li>
						<li>
							<label>供应商编号</label>
							<input type="text" id="supplCode" name="supplCode"/>
						</li>
						<li>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
						</li>
							
					</ul>
				</form>
			</div>
			<div id="content-list">
				<table id="llglSendListDataTable"></table>
				<div id="llglSendListDataTablePager"></div>
			</div> 
		</div>
	</body>
</html>


