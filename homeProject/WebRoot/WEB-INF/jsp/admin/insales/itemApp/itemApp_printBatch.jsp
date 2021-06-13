<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
	String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
	<head>
		<title>领料管理批量打印页面</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	
		<script type="text/javascript">
		function print(){
			var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
        	if(ids.length==0){
        		Global.Dialog.infoDialog("请选择需要打印的领料单！");
        		return;
        	}
			var printSelected = $("#printTypeGroup input[type=radio][name=printType]:checked").val();

			var nos = "";
        	$.each(ids,function(k,id){
    			var row = $("#dataTable").jqGrid("getRowData", id);	
    			nos = nos + $.trim(row.no) + ",";	
    		});
    		if (nos != "") {
    			nos = nos.substring(0,nos.length-1);
    		}
			$.ajax({
				url:"${ctx}/admin/itemApp/getBeforeDoPrint",
				type: "post",
		    	data: {
		    		from:printSelected=="zx"?printSelected:printSelected+"1",//该检测和打印页面共用接口,由于批量打印页面打印发货物流时不检查单号,因此对打印标签重命名跳过检测
		    		nos:nos,
					checkFH:"F",
					whcode:$("#whcodePrint").val()
		    	},
				dataType: "json",
				cache: false,
				async:false,
				error: function(obj){			    		
					art.dialog({
						content: "访问出错,请重试",
						time: 3000,
						beforeunload: function () {}
					});
				},
				success: function(obj){
					if(obj.datas.canPrintNos != ""){
			        	Global.Print.showPrint("llgl_main.jasper", 
			        		{
								No: obj.datas.canPrintNos,
								whcode:$("#whcodePrint").val(),
								from:printSelected,
								LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
								SUBREPORT_DIR: "<%=jasperPath%>" 
							}, null, 
							function (){
								art.dialog({
									content:"是否已打印报表?",
									ok:function(){
										updatePrint(obj.datas.canPrintNos.substring(1, obj.datas.canPrintNos.length-1));
									},
									cancel:function(){},
									okValue:"是",
									cancelValue:"否"
								});
							}
						);
					}
					if(obj.datas.errorInfo && obj.datas.errorInfo != ""){
						art.dialog({
							content:obj.datas.errorInfo
						});
					}
				}
			});
		}
		$(function(){
			$("#whcodePrint").openComponent_wareHouse({callBack:function(ret){
				$("#whcodePrintDescr").val(ret.desc1);
			}});
			$("#openComponent_wareHouse_whcodePrint").attr("readonly",true);
			$("#whcode").openComponent_wareHouse();
			$("#custCode").openComponent_customer({
				condition:{
					mobileHidden:true,
					status:"4,5",
					disabledEle:"status_NAME"
				}
			});
			$("#appCzy").openComponent_employee();
			$("#printTypeGroup span").find("input[type=radio]").css("margin-top","0px");
			$("#printTypeGroup span").find("input[type=text]").css({"width":"121px","border":"0px","font-size":"14px"}).attr("readonly",true).on("click",function(){
				var radios = $("#printTypeGroup span").find("input[type=radio]");
				
				for(var i=0;i<radios.length;i++){
					radios[i].checked = false;
					$(radios[i]).removeAttr("checked");
				}
				var ele = $(this).parent().find("input[type=radio]");
				ele.attr("checked",true);
				ele.get(0).checked = true;
			});
			
			Global.JqGrid.initJqGrid("dataTable",{
				multiselect: true,
				colModel : [
					{name: "typedescr", index: "typedescr", width: 100, label: "领料类型", sortable: true, align: "left"},
					{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
					{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left"},
					{name: "no", index: "no", width: 100, label: "领料单号", sortable: true, align: "left"},
					{name: "custdescr", index: "custdescr", width: 80, label: "客户名称", sortable: true, align: "left"},
					{name: "statusdescr", index: "statusdescr", width: 120, label: "领料单状态", sortable: true, align: "left"},
					{name: "remarks", index: "remarks", width: 100, label: "备注", sortable: true, align: "left"},
					{name: "sendtypedescr", index: "sendtypedescr", width: 100, label: "发货类型", sortable: true, align: "left"},
					{name: "delivtypedescr", index: "delivtypedescr", width: 100, label: "配送方式", sortable: true, align: "left"},
					{name: "confirmname", index: "confirmname", width: 80, label: "审批人员", sortable: true, align: "left"},
					{name: "confirmdate", index: "confirmdate", width: 109, label: "审批日期", sortable: true, align: "left", formatter: formatTime},
					{name: "appname", index: "appname", width: 80, label: "申请人员", sortable: true, align: "left"},
					{name: "date", index: "date", width: 100, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
					{name: "sendname", index: "sendname", width: 80, label: "发货人员", sortable: true, align: "left"},
					{name: "senddate", index: "senddate", width: 100, label: "发货日期", sortable: true, align: "left", formatter: formatTime},
					{name: "suppldescr", index: "suppldescr", width: 100, label: "供应商", sortable: true, align: "left"},
					{name: "puno", index: "puno", width: 100, label: "采购单号", sortable: true, align: "left"},
					{name: "warehouse", index: "warehouse", width: 100, label: "仓库名称", sortable: true, align: "left"},
					{name: "projectman", index: "projectman", width: 100, label: "项目经理", sortable: true, align: "left"},
					{name: "phone", index: "phone", width: 100, label: "电话", sortable: true, align: "left"},
					{name: "printtimes", index: "printtimes", width: 80, label: "打印次数", sortable: true, align: "right"},
					{name: "printczydescr", index: "printczydescr", width: 100, label: "打印人", sortable: true, align: "left"},
					{name: "printdate", index: "printdate", width: 140, label: "打印日期", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 140, label: "最后更新人员", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 100, label: "是否过期", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 100, label: "操作日志", sortable: true, align: "left"}
	            ]
			});
		});
		function goto_query(){
			$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/itemApp/goJqGridPrintBatch",postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");
		}
		function useSupplierPlatformChange(){
			if($("#useSupplierPlatform").val() == "T"){
				$("#useSupplierPlatform").val("F");
			}else{
				$("#useSupplierPlatform").val("T");
			}
		}
		function unPrintChange(){
			if($("#unPrint").val() == "T"){
				$("#unPrint").val("F");
			}else{
				$("#unPrint").val("T");
			}
		}		
		function updatePrint(nos){
			$.ajax({
				url:"${ctx}/admin/itemApp/doUpdatePrint",
				type: "post",
		    	data: {
		    		nos:nos
		    	},
				dataType: "json",
				cache: false,
				error: function(obj){			    		
					art.dialog({
						content: "访问出错,请重试",
						time: 3000,
						beforeunload: function () {}
					});
				},
				success: function(obj){
					if(!obj.rs){
						art.dialog({
							content:obj.msg
						});
					}
				}
			});
		}
		</script>
	
	</head>
	    
	<body>
		<div class="body-box-list" >
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
	    				<button id="saveBut" type="button" class="btn btn-system "   onclick="print()">打印</button>
	    				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<div>
				<div id="printTypeGroup" style="margin-top:10px;float:left">
					<span>
						<input type="radio" id="dh" name="printType" value="dh" checked/><input type="text" value="打印订货单"/>
					</span>
					<span>
						<input type="radio" id="fh" name="printType" value="fh" /><input type="text" value="打印发货单" />
					</span>
					<span>
						<input type="radio" id="zx" name="printType" value="zx" /><input type="text" value="打印增项单" />
					</span>
					<span>
						<input type="radio" id="fhwl" name="printType" value="fhwl" /><input type="text" value="打印发货单(物流)" />
					</span>
				</div>
				<form class="form-search">
					<ul class="ul-form">
						<li>
							<label>仓库编号</label>
							<input type="text" id="whcodePrint" name="whcodePrint" />
							<input type="text" id="whcodePrintDescr" name="whcodePrintDescr" style="border:0px;background:white"/>
						</li>
					</ul>
				</form>
			</div>
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" id="expired" name="expired" value="${data.expired}" />
					<ul class="ul-form">
						<li>
							<label>领料单号</label>
							<input type="text" id="no" name="no" />
						</li>
						<li>
							<label>申请日期</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>
							<label>领料类型</label>
							<house:xtdm id="type" dictCode="ITEMAPPTYPE"></house:xtdm>
						</li>
						<li>
							<label>仓库编号</label>
							<input type="text" id="whcode" name="whcode" />
						</li>
						<li>
							<label>审核日期从</label>
							<input type="text" id="confirmDateFrom" name="confirmDateFrom" class="i-date" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="confirmDateTo" name="confirmDateTo" class="i-date" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>
							<label>配送方式</label>
							<house:xtdm id="delivType" dictCode="DELIVTYPE" />
						</li>
						<li>
							<label>材料类型1</label>
							<house:dict id="itemType1" dictCode="" 
										sql="select rtrim(Code)+' '+Descr fd,Code from tItemType1  where Expired='F' and code in ${data.itemRight }   order by DispSeq " 
										sqlValueKey="Code" sqlLableKey="fd" >
							</house:dict>
						</li>
						<li>
							<label>申请人员</label>
							<input type="text" id="appCzy" name="appCzy" />
						</li>
						<li>
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode" />
						</li>
						<li>
							<label>楼盘地址</label>
							<input type="text" id="custAddress" name="custAddress" />
						</li>
						<li>
							<label>领料单状态</label>
							<house:xtdmMulit id="status" dictCode="ITEMAPPSTATUS" selectedValue="${data.status }" unShowValue="OPEN"></house:xtdmMulit>
						</li>
						<li>
							<label>备注</label>
							<input type="text" id="remarks" name="remarks" />
						</li>
						<li class="search-group-shrink" >
							<input type="checkbox" id="expired_show" name="expired_show"
									value="${data.expired}" onclick="hideExpired(this)"
									${data.expired!='T'?'checked':'' }/><p>隐藏过期&nbsp;&nbsp;&nbsp;&nbsp;</p>
							<input type="checkbox" id="useSupplierPlatform" name="useSupplierPlatform" onclick="useSupplierPlatformChange()" value="T" checked/><p>隐藏使用供应商平台的订单&nbsp;&nbsp;&nbsp;&nbsp;</p>
							<input type="checkbox" id="unPrint" name="unPrint" onclick="unPrintChange()" value="F"/><p>只显示未打印记录</p>
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
						</li>
					</ul>
				</form>
			</div>
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</body>
</html>
