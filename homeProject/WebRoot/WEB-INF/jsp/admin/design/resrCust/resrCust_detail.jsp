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
	<title>资源客户查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builderNum.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/ResrCust/sourceByAuthority","source","netChanel",null,false,true,false,true);
	Global.LinkSelect.setSelect({firstSelect:'source',
						firstValue:'${resrCust.source}',
						secondSelect:'netChanel',
						secondValue:'${resrCust.netChanel}'
						});
	$("#builderNum").openComponent_builderNum({showValue:'${resrCust.builderNum}'});
	$("#builderCode").openComponent_builder({showValue:'${resrCust.builderCode}',showLabel:'${resrCust.builderDescr}',condition:{department2:'${resrCust.department2}'}});	
	$("#openComponent_builder_builderCode").attr("readonly",true);
	$("#businessMan").openComponent_employee({showValue:'${resrCust.businessMan}',showLabel:'${resrCust.businessManDescr}'});	
	$("#crtCzy").openComponent_employee({showValue:'${resrCust.crtCzy}',showLabel:'${resrCust.crtCzyDescr}',readonly:true});	
	$("#crtCZYDept").openComponent_department({showValue:'${resrCust.crtCZYDept}',showLabel:'${resrCust.crtCzyDeptDescr}'});
	$("#custCode").openComponent_customer({showValue:'${customer.code}',showLabel:'${customer.descr}'});	
	
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/ResrCust/goConJqGrid",
		postData:{code:"${resrCust.code}",type:"1,3"},
		height:200,
		rowNum:10000000,
		styleUI: 'Bootstrap', 
		onSortColEndFlag:true,
		colModel : [
			{name:'conmandescr',	index:'conmandescr',	width:90,	label:'跟踪人',	sortable:true,align:"left",},
			{name:'condate',	index:'condate',	width:140,	label:'跟踪日期',	sortable:true,align:"left",formatter: formatTime},
			{name:'conwaydescr',	index:'conwaydescr',	width:80,	label:'跟踪方式',	sortable:true,align:"left",},
			{name:'typedescr',	index:'typedescr',	width:110,	label:'跟踪类型',	sortable:true,align:"left",},
			{name:'conduration',	index:'conduration',	width:100,	label:'通话时长（秒）',	sortable:true,align:"right",},
			{name:'remarks',	index:'remarks',	width:400,	label:'跟踪说明',	sortable:true,align:"left",},
			{name:'nextcondate',	index:'nextcondate',	width:95,	label:'下次联系时间',	sortable:true,align:"left",formatter: formatDate},
			{name:'callrecordpath',	index:'callrecordpath',	width:80,	label:'功能',	sortable:true,align:"left",formatter: formatBtns}
		],
		gridComplete:function (){
        	dataTableCheckBox("dataTable","typedescr");
		},
	});	
	initSelectStep();
	chgResrCustPoolNo();
});
function formatBtns(value, selectInfo) {
	if(value && value != "") {
		return "<a onclick=\"downloadFileView('" + selectInfo.gid + "', " + selectInfo.rowId + ", '" + "${ossCdnAccessUrl}" + value + "')\">下载</a>&nbsp;&nbsp;<a onclick=\"listenCallRecoardView('" 
				+ selectInfo.gid + "', " + selectInfo.rowId + ", '" + "${ossCdnAccessUrl}" + value + "')\">播放</a>";
	}
	return "";
}

function downloadFileView(dataTable, rowId, path) {
 	$("#downloadElem")[0].href = path;
	$("#downloadElem")[0].click(); 
}

function listenCallRecoardView(dataTable, rowId, path) {
	Global.Dialog.showDialog("CallRecord", {
		title:"录音",
		url: "${ctx}/admin/callRecord/goView",
		postData: {
			path: path
		},
		height:150,
		width:600,
	});
}

function gotoQuery(){
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/ResrCust/goConJqGrid",
			postData:{type:$("#type").val(),code:"${resrCust.code}"},page:1,sortname:''}).trigger("reloadGrid");
}

function initSelectStep(){
	if("${customer.signDate}"!=""){
		$("#point4").addClass("c-select");
		$("#line3").addClass("b-select");
	}
	if("${customer.setDate}"!=""){
		$("#point3").addClass("c-select");
		$("#line2").addClass("b-select");
	}
	if("${customer.visitDate}"!=""){
		$("#point2").addClass("c-select");
		$("#line1").addClass("b-select");
	}
	if("${customer.measureDate}"!=""){
		$("#point1").addClass("c-select");
		$("#line0").addClass("b-select");
	}
	if("${resrCust.dispatchDate}"!=""){
		$("#point0").addClass("c-select");
	}
}

function chgResrCustPoolNo(){
	var resrCustPoolNo = $("#resrCustPoolNo").val();
	var custResStat = $("#custResStat").val();
	if(resrCustPoolNo == ""){
		$("#custResStat").removeAttr("disabled","disabled");
		$("#netChanelLi").show();
		return;
	}
	$.ajax({
		url:"${ctx}/admin/ResrCust/getPoolType?resrCustPoolNo=" + resrCustPoolNo,
		type:'post',
		data:{},
		dataType:'json',
		cache:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": '出错~'});
		},
		success:function(obj){
			if (obj){
				console.log(obj.dispatchRule);
				console.log(custResStat);
				if($.trim(obj.dispatchRule)!="0" && (custResStat=="1" || custResStat =="2") ){ 
					$("#custResStat").attr("disabled","disabled");
				} else {
					$("#custResStat").removeAttr("disabled","disabled");
				}
				if($.trim(obj.isHideChannel)=="1"  && (custResStat=="0" || custResStat =="2") 
						&& $.trim("${resrCust.crtCzy}") != $.trim("${resrCust.czybh }")){
					$("#netChanelLi").hide();
				} else {
					$("#netChanelLi").show();
				}
			}
		}
	});
}
</script>
</head>
<body>
	<a id="downloadElem" download style="display: none"></a>
	<div class="body-box-list">
		<div class="panel-body" style="padding:0px 0px 0px 0px">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
				<div class="validate-group row" >
				<div style="width:900px;margin:0px 0px 5px 45px;">
					<!--  标题进度条 start-->
					<div class="content" style="margin:0px;width: 100%;">
						<div class="processBar">	
							<div class="text" style="margin:0px 0px 10px -10px;"><span class='poetry' style="font-weight:bold">派单</span></div>
							<div id="line0" class="bar">
								<div id="point0" class="c-step "></div>
							</div>
							<div class="text" style="margin: 10px -25px;"><span class='poetry' ><fmt:formatDate value='${resrCust.dispatchDate}' pattern='yyyy-MM-dd' /></span></div>
						</div>
						<div class="processBar">
							<div class="text" style="margin:0px 0px 10px -10px;"><span class='poetry' style="font-weight:bold">量房</span></div>
							<div id="line1" class="bar">
								<div id="point1" class="c-step"></div>
							</div>
							<div class="text" style="margin: 10px -25px;"><span class='poetry'><fmt:formatDate value='${customer.measureDate}' pattern='yyyy-MM-dd'/></span></div>
						</div>
						<div class="processBar">
							<div class="text" style="margin:0px 0px 10px -10px;"><span class='poetry' style="font-weight:bold">到店</span></div>
							<div id="line2" class="bar">
								<div id="point2" class="c-step"></div>
							</div>
							<div class="text" style="margin: 10px -25px;"><span class='poetry'><fmt:formatDate value='${customer.visitDate}' pattern='yyyy-MM-dd'/></span></div>
						</div>
						<div class="processBar">
							<div class="text" style="margin:0px 0px 10px -10px;"><span class='poetry' style="font-weight:bold">下定</span></div>
							<div id="line3" class="bar">
								<div id="point3" class="c-step"></div>
							</div>
							<div class="text" style="margin: 10px -25px;"><span class='poetry'></span><fmt:formatDate value='${customer.setDate}' pattern='yyyy-MM-dd'/></div>
						</div>
						<div class="processBar" style="width:20px;">
							<div class="text" style="margin:0px 0px 10px -10px;"><span class='poetry' style="font-weight:bold">签单</span></div>
							<div id="line4" class="bar" style="width: 0;">
								<div id="point4" class="c-step"></div>
							</div>
							<div class="text" style="margin: 10px -25px;"><span class='poetry'><fmt:formatDate value='${customer.signDate}' pattern='yyyy-MM-dd'/></span></div>
						</div>
					</div>
					<!--  标题进度条 end-->
				</div></div>
					 <span style="font-weight:bold;">基本信息:</span>
						<hr style="height: 2px;margin:5px 0px 5px 0px;">
						<div class="validate-group row" >
							<li class="form-validate">
								<label><span class="required">*</span>客户姓名</label>
								<input type="text" id="descr" name="descr" style="width:160px;" value="${resrCust.descr }"/>
							</li>
							<li>
								<label>性别</label>
								<house:xtdm id="gender" dictCode="GENDER" value="${resrCust.gender }"></house:xtdm>                     
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>手机号码</label>
								<input type="text" id="mobile1" name="mobile1" style="width:160px;" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" value="${resrCust.mobile1 }"/>
							</li>
							<li class="form-validate">
								<label>手机号码2</label>
								<input type="text" id="mobile3" name="mobile2" style="width:160px;" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" value="${resrCust.mobile2 }"/>
							</li>
						</div>
						<div class="validate-group row" >
							<li class="form-validate">
								<label>微信/Email</label>
								<input type="text" id="email2" name="email2" style="width:160px;" value="${resrCust.email2 }"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>客户来源</label> 
								<select id="source" name="source" ></select>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>渠道</label> 
								<select id="netChanel" name="netChanel" ></select>
							</li>
                            <li class="form-validate" id="li_extraOrderNo">
                                <label>外部订单编号</label>
                                <input type="text" id="extraOrderNo" name="extraOrderNo" style="width:160px;" value="${resrCust.extraOrderNo}"/>
                            </li>
						</div>
						<div class="validate-group row" >
							<li class="form-validate">
								<label><span class="required">*</span>客户类别</label>
								<house:xtdm id="constructType" dictCode="CUSTCLASS" value="${resrCust.constructType }"></house:xtdm>                     
							</li>
							<li class="form-validate">
                                  <label><span class="required">*</span>客户分类1</label>
                                  <house:xtdm id="custKind" dictCode="CUSTKIND" value="${resrCust.custKind }" unShowValue="${resrCust.custKind =='0'?'':'0' }"></house:xtdm>     
                            </li>
                            <li class="form-validate">
								<label><span class="required">*</span>线索池</label>
								<house:dict id="resrCustPoolNo" dictCode="" sqlValueKey="No" sqlLableKey="Descr"
								    sql="select a.No,a.Descr from tResrCustPool a
								    where (exists (select 1 from tResrcustPoolEmp in_a where in_a.ResrCustPoolNo = a.No and in_a.CZYBH = '${resrCust.czybh }')
								    or exists (select 1 from tCZYBM in_b where in_b.DefaultPoolNo = a.No and in_b.CZYBH = '${resrCust.czybh }')
								    or a.Descr = '默认线索池') and expired = 'F'" value="${resrCust.resrCustPoolNo }" onchange="chgResrCustPoolNo()"></house:dict>
							</li>
							<li class="form-validate">
								<label class="control-textarea">备注</label>
								<textarea id="remark" name="remark" rows="1" style="height:23px">${resrCust.remark}</textarea>
							</li>
						</div>
						<span style="font-weight:bold;margin-top:10px">楼盘信息:</span>
						<hr style="height: 2px;margin:5px 0px 5px 0px">
						<div class="validate-group row" >
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address"  value="${resrCust.address }"/>
							</li>
							<li class="form-validate">
								<label>面积</label>
								<input type="text" style="width:160px;" id="area" name="area" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');" value="${resrCust.area }"/>
								<span style="position: absolute;left:290px;width: 30px;top:5px;">平方</span>
							</li>
							<li class="form-validate">
								<label>项目名称</label>
								<input type="text" id="builderCode" name="builderCode" style="width:160px;" value="${resrCust.builderCode } "/>
							</li>
							<li class="form-validate">
								<label>楼栋号</label>
								<input type="text" style="width:160px;" id="builderNum" name="builderNum" value="${resrCust.builderNum }" />
							</li>
						</div>
						<div class="validate-group row" >
							<li class="form-validate">
								<label>户型</label>
								<house:xtdm id="layout" dictCode="LAYOUT" value="${resrCust.layout }" ></house:xtdm>
							</li>
							<li>
								<label>楼盘性质</label>
								<house:xtdm id="addrProperty" dictCode="ADDRPROPERTY" value="${resrCust.addrProperty }"></house:xtdm>                     
							</li>
							<li class="form-validate">
						   		<label>区域</label>
							    <house:dict id="regionCode" dictCode=""  sqlValueKey="Code" sqlLableKey="Descr" sql="select Code,Code+' '+Descr Descr from tRegion a where a.expired='F' " value="${resrCust.regionCode }"></house:dict>
							</li>
							<li class="form-validate">
								<label >装修状态</label>
								<house:xtdm id="status" dictCode="RESRCUSTSTS" value="${resrCust.status }"></house:xtdm>                     
							</li>
						</div>
						<span style="font-weight:bold;margin-top:10px">跟踪信息:</span>
						<hr style="height: 2px;margin:5px 0px 5px 0px">
						<div class="validate-group row " >
							<li class="form-validate">
                                   <label>资源客户状态</label>
                                   <house:xtdm id="custResStat" dictCode="CUSTRESSTAT" value="${resrCust.custResStat }" ></house:xtdm>                     
                               </li>
                               <li class="form-validate">
								<label><span class="required">*</span>跟单人员</label>
								<input type="text" id="businessMan" name="businessMan" style="width:160px;" value="${resrCust.businessMan }" />
							</li>
							<li class="form-validate"><label>创建人</label> <input
								type="text" id="crtCzy" name="crtCzy" style="width:160px;"
								value="${resrCust.crtCzy }" />
							</li>
							<li class="form-validate"><label>创建人部门</label> <input
								type="text" id="crtCZYDept" name="crtCZYDept"
								style="width:160px;" value="${resrCust.crtCZYDept }" />
							</li>
						</div>
						<div class="validate-group row" >
							<li class="form-validate">
                                <label>创建时间</label>
                                <input type="text" id="crtDate" name="crtDate" class="i-date" value="<fmt:formatDate value='${resrCust.crtDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly />
                            </li>
                            <li><label>意向客户编号</label> <input type="text" id="custCode"
								name="custCode" style="width:160px;" value="${customer.code }"
								readonly="readonly" />
							</li>
							<li class="form-validate"><label>意向客户状态</label> <house:xtdm
									id="custStatus" dictCode="CUSTOMERSTATUS"
									value="${customer.status }"></house:xtdm>
							</li>
							<li><label>转意向时间</label> <input type="text"
								id="custCrtDate" name="custCrtDate" class="i-date"
								value="<fmt:formatDate value='${customer.crtDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
								readonly />
							</li>
						</div>
				</ul>
			</form>
		</div>
		<hr style="height: 2px;margin:0px 0px 0px 0px">
		<div class="panel-body">
			<div id="content-list">
				<table id= "dataTable"></table>
			</div>
	    </div>
	</div>
</body>
</html>
