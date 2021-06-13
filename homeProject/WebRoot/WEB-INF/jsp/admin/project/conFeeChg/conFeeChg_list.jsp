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
<title>合同费用增减管理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}"type="text/javascript"></script>
<script type="text/javascript">
	function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#status").val("");
			$("#type").val("");
			$("select").val("");
			$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
		}	
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/conFeeChg/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: 'Bootstrap', 
			colModel : [
					{name: "pk", index: "pk", width: 108, label: "增减编号", sortable: true, align: "left", hidden: true},
					{name: "perfpk", index: "perfpk", width: 108, label: "业绩明细pk", sortable: true, align: "left", hidden: true},
					{name: "code", index: "code", width: 75, label: "客户编号", sortable: true, align: "left"},
					{name: "descr", index: "descr", width: 80, label: "客户名称", sortable: true, align: "left"},
					{name: "area", index: "area", width: 164, label: "area", sortable: true, align: "left", hidden: true},
					{name: "documentno", index: "documentno", width: 164, label: "documentno", sortable: true, align: "left", hidden: true},
					{name: "address", index: "address", width: 155, label: "楼盘地址", sortable: true, align: "left"},
					{name: "chgtype", index: "chgtype", width: 115, label: "费用类型", sortable: true, align: "left", hidden: true},
					{name: "chgtypedescr", index: "chgtypedescr", width: 80, label: "费用类型", sortable: true, align: "left"},
					{name: "chgamount", index: "chgamount", width: 80, label: "增减金额", sortable: true, align: "right", sum: true},
					{name: "custstatus", index: "custstatus", width: 87, label: "楼盘状态", sortable: true, align: "left", hidden: true},
					{name: "custstatusdescr", index: "custstatusdescr", width: 87, label: "楼盘状态", sortable: true, align: "left", hidden: true},
					{name: "status", index: "status", width: 87, label: "状态", sortable: true, align: "left", hidden: true},
					{name: "statusdescr", index: "statusdescr", width: 50, label: "状态", sortable: true, align: "left"},
					{name: "confirmczydescr", index: "confirmczydescr", width: 80, label: "审核人", sortable: true, align: "left"},
					{name: "confirmdate", index: "confirmdate", width: 120, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
					{name: "appczydescr", index: "appczydescr", width: 79, label: "申请人", sortable: true, align: "left"},
					{name: "date", index: "date", width: 120, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedbydescr", index: "lastupdatedbydescr", width: 85, label: "最后修改人", sortable: true, align: "left"},
					{name: "remarks", index: "remarks", width: 220, label: "备注", sortable: true, align: "left"},
					{name: "iscalperf", index: "iscalperf", width: 102, label: "是否计算业绩", sortable: true, align: "left",hidden:true},
					{name: "iscalperfdescr", index: "iscalperfdescr", width: 102, label: "是否计算业绩", sortable: true, align: "left"},
					{name: "chgno", index: "chgno", width: 102, label: "增减单号", sortable: true, align: "left"},
					{name: "itemtype1", index: "itemtype1", width: 102, label: "材料类型1", sortable: true, align: "left",hidden:true},
					{name: "isservice", index: "isservice", width: 102, label: "是否服务性产品", sortable: true, align: "left",hidden:true},
					{name: "iscupboard", index: "iscupboard", width: 102, label: "是否橱柜", sortable: true, align: "left",hidden:true},
			],
		});
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
	$("#custCode").openComponent_customer({condition:{status:"4",mobileHidden:"1",laborFeeCustStatus:"1,2,3,5"}});
	});
	function doExcel(){
		var url = "${ctx}/admin/conFeeChg/doExcel";
		doExcelAll(url);
	}
	//各按钮点击操作
	function go(btn,name){
		var postdata="";
		if(btn!='A'){
			var ret=selectDataTableRow();
			postdata={pk:ret.pk,custCode:ret.code,custDescr:ret.descr,address:ret.address,
			chgType:ret.chgtype,chgAmount:ret.chgamount,remarks:ret.remarks,m_umState:btn,
			itemType1:ret.itemtype1,isService:ret.isservice,isCupboard:ret.iscupboard,chgNo:ret.chgno};
			if(!ret){
				art.dialog({
	       			content: "请选择一条记录",
	       		});
	       		return;
			}
			if(btn=='M'){
				if($.trim(ret.status)!='OPEN'){
					art.dialog({
		       			content: "合同状态为"+ret.statusdescr+",不允许进行编辑！",
	       			});
	       			return;
				}
			}else if(btn=='C'){
				if($.trim(ret.status)!='OPEN'){
					art.dialog({
		       			content: "合同状态为"+ret.statusdescr+",不允许进行审核！",
	       			});
	       			return;
				}
			}else if(btn=='RC'){
				if($.trim(ret.status)!='CONFIRMED' && $.trim(ret.status)!='CANCEL'){
					art.dialog({
		       			content: "合同状态为"+ret.statusdescr+",不允许进行反审核！",
	       			});
	       			return;
				}
				if($.trim(ret.chgno)!=''){
					art.dialog({
		       			content: "该变更单由材料增减["+ret.chgno+"]生成,不允许进行反审核！",
	       			});
	       			return;
				}
				if(ret.custstatus=="5"){
					art.dialog({
		       			content: "楼盘状态为"+ret.custstatusdescr+",不允许进行反审核！",
	       			});
	       			return;
				}
				
			}
		}else{
			postdata={m_umState:btn};
		}
		if(btn=='V'){
			Global.Dialog.showDialog("",{
				title:"合同费用增减管理——"+name,
				url:"${ctx}/admin/conFeeChg/goView",
				postData:postdata,
			    height:400,
			    width:700,
				returnFun:goto_query
			});
		}else{
			Global.Dialog.showDialog("",{
				title:"合同费用增减管理——"+name,
				url:"${ctx}/admin/conFeeChg/goAdd",
				postData:postdata,
			    height:400,
			    width:700,
				returnFun:goto_query
			});
		}
	}

	//增减业绩
	function performance() {
		var ret=selectDataTableRow();
		$.ajax({
			url : '${ctx}/admin/conFeeChg/checkPerformance',
			type : 'post',
			data : {
				'pk' : ret.pk,
			},
			async : false,
			dataType : 'json',
			cache : false,
			error : function(obj) {
				showAjaxHtml({
					"hidden" : false,
					"msg" : '保存数据出错~'
				});
			},
			success : function(obj) {
				var data=obj[0];
				console.log(data);
				if(data.PerfPK==null){
					data['PerfPK']="";
				}
				if(!ret){
					art.dialog({
		       			content: "请选择一条记录",
		       		});
		       		return;
				}
				if(ret.status!=data.Status){
					art.dialog({
		       			content: "此增减单状态发生改变，请刷新数据！",
	       			});
	       			return;
				}
				if(ret.perfpk!=data.PerfPK){
					art.dialog({
		       			content: "此增减单对应的业绩明细发生变化，请刷新数据！",
	       			});
	       			return;
				}
				if(ret.chgtype!=data.ChgType){
					art.dialog({
		       			content: "费用类型发生变化，请刷新数据！",
	       			});
	       			return;
				}
				if($.trim(ret.status)!="CONFIRMED"){
					art.dialog({
		       			content: "只有审核状态的增减单才能设置是否计算业绩！",
	       			});
	       			return;
				}
				if(ret.perfpk!=""){
					art.dialog({
		       			content: "此增减单已计算业绩，不能进行此操作！",
	       			});
	       			return;
				}
				if(ret.chgtype=="3"){
					art.dialog({
		       			content: "实物赠送不允许计算业绩！",
	       			});
	       			return;
				}
				Global.Dialog.showDialog("performance",{
					title:"合同费用增减管理——增减业绩",
					url:"${ctx}/admin/conFeeChg/goPerformance",
					postData:{pk:ret.pk,iscalPerf:ret.iscalperf},
					height:200,
					width:500,
					returnFun:goto_query
				});
			}
		});
	}
	//打印
	function print(){  
		var date = formatTime(new Date());
		var ret = selectDataTableRow();
	   	var reportName = "conFeeChg_htfyzjgl.jasper";
	   	Global.Print.showPrint(reportName, {
	   		DocumentNo:ret.documentno,
	   		NowDate:date.substring(0,10),
	   		ChangeDate:(ret.date).substring(0,10),
	   		ChgTypeDescr:ret.chgtypedescr,
	   		Address:ret.address,
	   		Remarks:ret.remarks,
	   		ChgAmount:ret.chgamount,
	   		Area:ret.area,
			LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
			SUBREPORT_DIR: "<%=jasperPath%>" 
		});
	}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired" name="expired" value=""/>
				<ul class="ul-form">
					<li><label>客户编号</label> <input type="text" id="custCode"
						name="custCode" value="${conFeeChg.custCode}" />
					</li>
					<li><label>楼盘地址</label> <input type="text" id="address"
						name="address" style="width:160px;" value="${conFeeChg.address }" />
					</li>
					<li><label>费用类型</label> <house:xtdm id="chgType"
							dictCode="CHGTYPE" value="${conFeeChg.chgType}"></house:xtdm>
					</li>
 					<li><label>是否橱柜</label> <house:xtdm id="isCupboard"
							dictCode="YESNO" value="${conFeeChg.isCupboard}"></house:xtdm>
					</li> 
					<li><label>费用状态</label> <house:xtdmMulit id="status"
							dictCode="CHGSTATUS" selectedValue="OPEN,CONFIRMED,CANCEL"></house:xtdmMulit>
					</li>
					<li><label>材料类型1</label> <select id="itemType1"
						name="itemType1" style="width: 166px;"
						value="${conFeeChg.itemType1 }"></select> </label>
					</li>
 					<li><label>是否服务性产品</label> <house:xtdm id="isService"
							dictCode="YESNO" value="${conFeeChg.isService}"></house:xtdm>
					</li>
					<li class="search-group"><input type="checkbox"
						id="expired_show" name="expired_show"
						value="${conFeeChg.expired}" onclick="hideExpired(this)"
						${conFeeChg.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="btn-panel">
		<div class="btn-group-sm">
			<house:authorize authCode="CONFEECHG_ADD">
				<button type="button" class="btn btn-system"
					onclick="go('A','新增')">
					<span>新增</span>
				</button>
			</house:authorize>
			<house:authorize authCode="CONFEECHG_UPDATE">
				<button type="button" class="btn btn-system"
					onclick="go('M','编辑')">
					<span>编辑</span>
				</button>
			</house:authorize>
			<house:authorize authCode="CONFEECHG_CONFIRM">
				<button type="button" class="btn btn-system"
					onclick="go('C','审核')">
					<span>审核</span>
				</button>
			</house:authorize>
			<house:authorize authCode="CONFEECHG_RETURNCONFIRM">
				<button type="button" class="btn btn-system"
					onclick="go('RC','反审核')">
					<span>反审核</span>
				</button>
			</house:authorize>
			<house:authorize authCode="CONFEECHG_PERFORMANCE">
				<button type="button" class="btn btn-system"
					onclick="performance()">
					<span>增减业绩</span>
				</button>
			</house:authorize>
			<house:authorize authCode="CONFEECHG_VIEW">
				<button type="button" class="btn btn-system"
					onclick="go('V','查看')">
					<span>查看</span>
				</button>
			</house:authorize>
				<button type="button" class="btn btn-system" id="print"
					onclick="print()">
					<span>打印</span>
				</button>
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>导出excel</span>
				</button>
		</div>
	</div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</body>
</html>
