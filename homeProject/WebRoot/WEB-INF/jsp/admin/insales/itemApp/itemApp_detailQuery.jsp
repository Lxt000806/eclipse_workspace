<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>领料管理明细查询页面</title>
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
			$("#detailQueryDataTable").jqGrid("setGridParam",{
				url:"${ctx}/admin/itemApp/goDetailQueryJqGrid",
				postData:$("#page_form").jsonForm(),
				page:1
			}).trigger("reloadGrid");
		}
		function doExcel(){
			var str = $("#detailQueryDataTablePager_right div").html();
			if(str != ""){
				str = str.substring(str.indexOf("共")+1,str.indexOf("条"));
				str = str.replace(/,/g,"");
				if(parseInt(str) > 100000){
					art.dialog({content:"数据超过100000条,无法导出"});
					return;
				}
				doExcelAll("${ctx}/admin/itemApp/doExcelDetailQuery", "detailQueryDataTable", "page_form"); 
			}else{
				art.dialog({
					content:"请先检索出数据"
				});
			}
		}
		function itemType1Change(){
	        $.ajax({
				url : "${ctx}/admin/itemApp/getItemType2TreeOpt",
				data : {
					itemType1:$("#itemType1").val()
				},
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: "json",
				type: "post",
				cache: false,
			    success: function(obj){
			    	setMulitOption("itemType2", obj.html);
					$("#itemCode").openComponent_item({
						condition:{
							itemType1:$("#itemType1").val()
						}
					});
				}
			});
		}
		/**初始化表格*/
		$(function(){
			$("#custCode").openComponent_customer({
				condition:{
					mobileHidden:"1",
					status:"4,5",
					disabledEle:"status_NAME"
				}
			});
			$("#itemCode").openComponent_item();
		    $("#supplCode").openComponent_supplier({
		    	condition:{
		    		itemRight:"${data.itemRightForSupplierCode}"
		    	}
		    });
			$("#appCzy").openComponent_employee();
	        //初始化表格
			Global.JqGrid.initJqGrid("detailQueryDataTable",{
				height:$(document).height()-$("#content-list").offset().top-100,
				colModel : [
					{name: "typedescr", index: "typedescr", width: 100, label: "领料类型", sortable: true, align: "left"},
					{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
					{name: "itemtype2descr", index: "itemtype2descr", width: 80, label: "材料类型2", sortable: true, align: "left"},
					{name: "documentno", index: "documentno", width: 95, label: "档案号", sortable: true, align: "left"},
					{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
					{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left"},
					{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
					{name: "no", index: "no", width: 100, label: "领料单号", sortable: true, align: "left"},
					{name: "iscupboarddescr", index: "iscupboarddescr", width: 80, label: "是否橱柜", sortable: true, align: "left"},
					{name: "statusdescr", index: "statusdescr", width: 120, label: "领料单状态", sortable: true, align: "left"},
					{name: "splremark", index: "splremark", width: 150, label: "供应商备注", sortable: true, align: "left"},
					{name: "date", index: "date", width: 100, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
					{name: "confirmdate", index: "confirmdate", width: 100, label: "审批日期", sortable: true, align: "left", formatter: formatTime},
					{name: "sendtypedescr", index: "sendtypedescr", width: 100, label: "发货类型", sortable: true, align: "left"},
					{name: "suppldescr", index: "suppldescr", width: 100, label: "供应商", sortable: true, align: "left"},
					{name: "senddate", index: "senddate", width: 100, label: "发货日期", sortable: true, align: "left", formatter: formatTime},
					{name: "whcodedescr", index: "whcodedescr", width: 97, label: "仓库名称", sortable: true, align: "left"},
					{name: "itemcode", index: "itemcode", width: 100, label: "材料编号", sortable: true, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 140, label: "材料名称", sortable: true, align: "left"},
					{name: "uomdescr", index: "uomdescr", width: 70, label: "单位", sortable: true, align: "left"},
					{name: "fixareadescr", index: "fixareadescr", width: 160, label: "装修区域", sortable: true, align: "left"},
					{name: "qty", index: "qty", width: 90, label: "采购数量", sortable: true, align: "right", sum: true},
					{name: "sendqty", index: "sendqty", width: 88, label: "发货数量", sortable: true, align: "right"},
					{name: "allweight", index: "allweight", width: 88, label: "总重量", sortable: true, align: "right"},
					{name: "cost", index: "cost", width: 90, label: "成本单价", sortable: true, align: "right", hidden: true},
					{name: "costamount", index: "costamount", width: 90, label: "成本金额", sortable: true, align: "right", sum: true, hidden: true},
					{name: "projectcost", index: "projectcost", width: 110, label: "项目经理结算价", sortable: true, align: "right", hidden: true},
					{name: "unitprice", index: "unitprice", width: 90, label: "销售单价", sortable: true, align: "right"},
					{name: "saleamount", index: "saleamount", width: 90, label: "销售总价", sortable: true, align: "right", sum: true},
					{name: "othercost", index: "othercost", width: 90, label: "其他费用", sortable: true, align: "right"},
					{name: "itemremarks", index: "itemremarks", width: 220, label: "材料备注", sortable: true, align: "left"},
					{name: "remarks", index: "remarks", width: 220, label: "备注", sortable: true, align: "left"}
	            ]
			});		
			if("${data.costRight}" == "1"){
				$("#detailQueryDataTable").jqGrid("showCol", "cost");
				$("#detailQueryDataTable").jqGrid("showCol", "costamount");
				$("#detailQueryDataTable").jqGrid("showCol", "projectcost");
			}
			onCollapse(44, "", "detailQueryDataTable"); 
		});
		</script>
	</head>
	    
	<body>
		<div class="body-box-list" style="overflow-x:hidden">
			<div class="body-box-form">
				<div class="panel panel-system">
				    <div class="panel-body">
				    	<div class="btn-group-xs">
							<button id="viewBut" type="button" class="btn btn-system" onclick="doExcel()">输出到Excel</button>	
							<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">关闭</button>	
						</div>
					</div>
				</div>
			</div>
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="m_umState" name="m_umState" value="${data.m_umState}"/>
					<input type="hidden" id="expired" name="expired" value="${data.expired}"/>
					<ul class="ul-form">
						<li>
							<label>领料单号</label>
							<input type="text" id="no" name="no"/>
						</li>
						<li>
							<label>申请日期</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label>申请人员</label>
							<input type="text" id="appCzy" name="appCzy"/>
						</li>
						<li>
							<label>领料类型</label>
							<house:xtdm id="type" dictCode="ITEMAPPTYPE"></house:xtdm>
						</li>
						<li>
							<label>配送方式</label>
							<house:xtdm id="delivType" dictCode="DELIVTYPE"/>
						</li>
						<li>
							<label>材料类型1</label>
							<house:dict id="itemType1" dictCode="" 
										sql="select Code,Descr from tItemType1 where Expired='F' and Code in ${data.itemRight} order by DispSeq" 
										sqlValueKey="Code" sqlLableKey="Descr" onchange="itemType1Change()">
							</house:dict>
						</li>
						<li>
							<label>材料类型2</label>
							<house:xtdmMulit id="itemType2" dictCode="" onCheck="checkItemType2()"></house:xtdmMulit>
						</li>
						<li>
							<label>材料类型3</label>
							<house:DictMulitSelect id="itemType3" dictCode="" sql="select code,descr from tItemType3 where 1=2" sqlLableKey="descr" sqlValueKey="code" ></house:DictMulitSelect>
						</li>
						<li>
							<label>客户类型</label>
							<house:xtdmMulit id="custType" dictCode="" 
											 sql="select NOTE,CBM from tXTDM where ID='CUSTTYPE' and IBM>0" sqlValueKey="CBM" sqlLableKey="NOTE">
							</house:xtdmMulit>
						</li>
						<li id="loadMore" >
							<button data-toggle="collapse" class="btn btn-sm btn-link" data-target="#collapse">更多</button>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
						</li>
						<div class="collapse" id="collapse">
							<ul>
								<li>
									<label>楼盘地址</label>
									<input type="text" id="custAddress" name="custAddress"/>
								</li>
								<li>
									<label>材料编号</label>
									<input type="text" id="itemCode" name="itemCode"/>
								</li>
								<li>
									<label>供应商编号</label>
									<input type="text" id="supplCode" name="supplCode"/>
								</li>
								<li>
									<label>仓库编号</label>
									<house:xtdmMulit id="whcode" dictCode="" sql="select (Code+' '+Desc1) fd,Code from tWareHouse" sqlValueKey="Code" sqlLableKey="fd"></house:xtdmMulit>
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
									<label>审核日期从</label>
									<input type="text" id="confirmDateFrom" name="confirmDateFrom" class="i-date" 
											onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
								<li>
									<label>至</label>
									<input type="text" id="confirmDateTo" name="confirmDateTo" class="i-date" 
											onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
								<li>
									<label>客户编号</label>
									<input type="text" id="custCode" name="custCode"/>
								</li>
								<li>
									<label>领料单状态</label>
									<house:xtdmMulit id="status" dictCode="ITEMAPPSTATUS" selectedValue="${data.status}"></house:xtdmMulit>
								</li>
								
								<li class="search-group-shrink" >
									<button data-toggle="collapse" class="btn btn-sm btn-link" data-target="#collapse">收起</button>
									<input type="checkbox" id="expired_show" name="expired_show"
											value="${data.expired}" onclick="hideExpired(this)"
											${data.expired!='T'?'checked':''}/><p>隐藏过期&nbsp;&nbsp;&nbsp;&nbsp;</p>
									<button type="button" class="btn  btn-sm btn-system " onclick="goto_query()">查询</button>
								</li>
							</ul>
						</div>
					</ul>
				</form>
			</div>
			<div id="content-list">
				<table id="detailQueryDataTable"></table>
				<div id="detailQueryDataTablePager"></div>
			</div> 
		</div>
	</body>
</html>


