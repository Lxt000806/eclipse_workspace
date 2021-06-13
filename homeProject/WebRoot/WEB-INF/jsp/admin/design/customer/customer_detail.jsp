<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>客户详情</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
//校验函数
$(function() {
	//客户来源，渠道联动
	Global.LinkSelect.initSelect("${ctx}/admin/ResrCust/sourceByAuthority","source","netChanel",null,false,true,false,true);
	Global.LinkSelect.setSelect({firstSelect:'source',
						firstValue:'${customer.source}',
						secondSelect:'netChanel',
						secondValue:'${customer.netChanel}'
						});
	$("#builderCode").openComponent_builder({showLabel:"${customer.builderCodeDescr}",showValue:"${customer.builderCode}"});
	if ($.trim('${customer.designMan}')){
		$("#designMan").openComponent_employee({showLabel:"${customer.designManDescr}",showValue:"${customer.designMan}",disabled:true});
	}else{
		$("#designMan").openComponent_employee({showLabel:"${customer.designManDescr}",showValue:"${customer.designMan}"});
	}
	$("#businessMan").openComponent_employee({showLabel:"${customer.businessManDescr}",showValue:"${customer.businessMan}",disabled:true});
	$("#againMan").openComponent_employee({showLabel:"${customer.againManDescr}",showValue:"${customer.againMan}",disabled:true});
	$("#oldCode").openComponent_customer({showLabel:"${customer.oldCodeDescr}",showValue:"${customer.oldCode}"});
	if ('${czybm.jslx}'=='ADMIN'){
		$("#crtDate").removeAttr("disabled");
	}else{
		$("#crtDate").attr("disabled",true);
	}
	$("#edtAgainMan").attr("readonly",true);
	$("#btnAgainMan").attr("disabled",true);
	$("#saleType").attr("disabled",true);
	if ('${czybm.saleType}'!='0'){
		$("#saleType").val('${czybm.saleType}');
	}
	if (!${hasAddress}){
		if (${hasBaseItemPlan} || ${hasItemPlan}){
			$("#custType").attr("disabled",true);
		}
		if ('${customer.status}'=='4' || '${customer.status}'=='5'){
			$("#descr").attr("readonly",true);
			$("#area").attr("readonly",true);
			$("#remarks").attr("readonly",true);
			if ('${customer.status}'=='4'){
				$("#constructType").attr("disabled",true);
			}
		}
		if (${hasCustPay}){
			$("#address").attr("readonly",true);
			$("#builderCode").openComponent_builder({showLabel:"${customer.builderCodeDescr}",showValue:"${customer.builderCode}",disabled:true});
			$("#oldCode").openComponent_customer({showLabel:"${customer.oldCodeDescr}",showValue:"${customer.oldCode}",disabled:true});
		}
	}
	if ('${customer.documentNo}'=='' && '${flag}'=='doc'){
		$("#documentNo").val(new Date().getYear());
	}
	if ($("#netChanel").val()=='1'){
		$(".againMan_show").show();
		if ('${czybm.custRight}'!='2' && '${czybm.custRight}'!='3'){
			$("#againMan").openComponent_employee({disabled:true});
		}
		if (!${hasSwt}){
			$("#netChanel").attr("disabled",true);
			if ($("#designMan").val()!=''){
				$("#designMan").openComponent_employee({disabled:true});
			}
		}
	}
	//软装客户，客户来源为网络客户的，需要填翻单员。业务员和设计师由后续跟单员填写
	if ($("#custType").val()=='2' && isNetCust() ){
		$('.againMan_show').show();
		$('.againMan_stat').show();
		$('#businessMan_star').hide();
		$('#designMan_star').hide();
		$("#businessMan").openComponent_employee({disabled:false});
	}
	if ($("#source").val()=='6'){
		$('.againMan_show').show();
		$('.againMan_stat').show();
		$("#businessMan").openComponent_employee({disabled:false});
	}
	if ('${customer.status}'=='1' || '${customer.status}'=='2'){
		$("#status").removeAttr("disabled");
	}else{
		$("#status").attr("disabled",true);
	}
	if ('${czybm.custType}'!='0'){
		$("#custType").attr("disabled",true);
	}
});

//是否网络客户
function isNetCust(){
	var source=$("#source").val();
	if(source=="3" || source=="12" || source=="13" || source=="14"){
		return true;
	}
	return false;
}
</script>
</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
            	<input type="hidden" id="expired" name="expired" value="${customer.expired }" />
					<ul class="ul-form">
						
						<div class="col-sm-4">
						<li class="form-validate">
							<label><span class="required">*</span>客户编号</label>
							<input type="text" style="width:160px;" id="code" name="code" value="${customer.code }" readonly="readonly"/>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required">*</span>销售类型</label>
							<house:xtdm id="saleType" dictCode="SALETYPE" value="${customer.saleType }" onchange="changeSaleType()" disabled="true" unShowValue="0"></house:xtdm>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required" id="oldCode_star" style="display: none">*</span>原客户号</label>
							<input type="text" id="oldCode" name="oldCode" style="width:160px;" value="${customer.oldCode}" readonly="${flag=='doc'?true:false }"/>
						</li>
						</div>
					
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required">*</span>客户类型</label>
							<house:xtdm id="custType" dictCode="CUSTTYPE" value="${customer.custType }" onchange="changeCustType()" disabled="${flag=='doc'?true:false }" unShowValue="0"></house:xtdm>
						</li>
						</div>
						<div class="col-sm-4">
							<li class="form-validate">
								<label><span class="required">*</span>客户来源</label> 
								<select id="source" name="source" ></select>
							</li>
						</div>
						<div class="col-sm-4">
							<li class="form-validate">
								<label><span class="required">*</span>渠道</label> 
								<select id="netChanel" name="netChanel" ></select>
							</li>
						</div>
					
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required">*</span>项目名称</label>
							<input type="text" id="builderCode" name="builderCode" style="width:160px;" value="${customer.builderCode}" readonly="${flag=='doc'?true:false }"/>
						</li>
						</div>
						<div class="col-sm-4">
							<li class="form-validate">
								<label><span class="required">*</span>楼栋号</label>
								<input type="text" style="width:160px;" id="builderNum" name="builderNum" value="${customer.builderNum }" />
							</li>
						</div>
						<div class="col-sm-4">
						<li class="form-validate">
							<label><span class="required">*</span>楼盘</label>
							<input type="text" style="width:160px;" id="address" name="address" value="${customer.address }" ${flag=='doc'?'readonly':'' }/>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required">*</span>户型</label>
							<house:xtdm id="layout" dictCode="LAYOUT" value="${customer.layout }" disabled="${flag=='doc'?true:false }"></house:xtdm>
						</li>
						</div>
						<div class="col-sm-4">
            			<li style="position: relative;">
							<label><span class="required">*</span>面积</label>
							<input type="text" style="width:160px;" id="area" name="area" value="${customer.area }" ${flag=='doc'?'readonly':'' }/>
							<span style="position: absolute;left:290px;width: 30px;top:3px;">平方</span>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required" id="designMan_star">*</span>设计师编号</label>
							<input type="text" id="designMan" name="designMan" style="width:160px;" value="${customer.designMan}" readonly="${flag=='doc'?true:false }"/>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required" id="businessMan_star">*</span>业务员编号</label>
							<input type="text" id="businessMan" name="businessMan" style="width:160px;" value="${customer.businessMan}" readonly="${flag=='doc'?true:false }"/>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="againMan_show">
							<label><div><span class="required againMan_stat">*</span>翻单员</div></label>
							<input type="text" id="againMan" name="againMan" style="width:160px;" value="${customer.againMan}" readonly="${flag=='doc'?true:false }"/>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required">*</span>客户名称</label>
							<input type="text" style="width:160px;" id="descr" name="descr" value="${customer.descr }" ${flag=='doc'?'readonly':'' }/>
						</li>
						</div>
					
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required">*</span>性别</label>
							<house:xtdm id="gender" dictCode="GENDER" value="${customer.gender }" disabled="${flag=='doc'?true:false }"></house:xtdm>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required">*</span>手机号码1</label>
							<c:if test="${czybm.emnum==customer.businessMan}">
							<input type="text" style="width:160px;" id="mobile1" name="mobile1" maxlength="11" value="${customer.mobile1 }" ${flag=='doc'?'readonly':'' }/>
							</c:if>
							<c:if test="${czybm.emnum!=customer.businessMan}">
							<input type="password" style="width:160px;" id="mobile1" name="mobile1" maxlength="11" value="${customer.mobile1 }" readonly="readonly"/>
							</c:if>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label>手机号码2</label>
							<c:if test="${czybm.emnum==customer.businessMan}">
							<input type="text" style="width:160px;" id="mobile2" name="mobile2" maxlength="11" value="${customer.mobile2 }" ${flag=='doc'?'readonly':'' }/>
							</c:if>
							<c:if test="${czybm.emnum!=customer.businessMan}">
							<input type="password" style="width:160px;" id="mobile2" name="mobile2" maxlength="11" value="${customer.mobile2 }" readonly="readonly"/>
							</c:if>
						</li>
						</div>
					
						<div class="col-sm-4">
            			<li class="form-validate">
							<label>QQ</label>
							<input type="text" style="width:160px;" id="qq" name="qq" value="${customer.qq }" ${flag=='doc'?'readonly':'' }/>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label>微信/Email</label>
							<input type="text" style="width:160px;" id="email2" name="email2" value="${customer.email2 }" ${flag=='doc'?'readonly':'' }/>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required">*</span>施工方式</label>
							<house:xtdm id="constructType" dictCode="CONSTRUCTTYPE" value="${customer.constructType }" disabled="${flag=='doc'?true:false }"></house:xtdm>
						</li>
						</div>
					
						<div class="col-sm-4">
            			<li class="form-validate">
							<label>设计风格</label>
							<house:xtdm id="designStyle" dictCode="DESIGNSTYLE" value="${customer.designStyle }" disabled="${flag=='doc'?true:false }"></house:xtdm>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label>开工时间</label>
							<input type="text" style="width:160px;" id="beginDate" name="beginDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.beginDate }' pattern='yyyy-MM-dd'/>" disabled="disabled" />
						</li>
						</div>
						<div class="col-sm-4">
            			<li style="position: relative;">
							<label>意向金额</label>
							<input type="text" style="width:160px;" id="planAmount" name="planAmount" value="${customer.planAmount }" ${flag=='doc'?'readonly':'' }/>
							<span style="position: absolute;left:285px;width: 30px;top:3px;">元</span>
						</li>
						</div>
					
						<div class="col-sm-4">
            			<li class="form-validate">
							<label>到店次数</label>
							<input type="text" style="width:160px;" id="comeTimes" name="comeTimes" value="${customer.comeTimes }"/>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label>创建时间</label>
							<input type="text" style="width:160px;" id="crtDate" name="crtDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.crtDate }' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly" ${flag=='doc'?'disabled':'' }/>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label>档案号</label>
							<input type="text" style="width:160px;" id="documentNo" name="documentNo" maxlength="8" value="${customer.documentNo }" ${flag=='doc'?'':'readonly' }/>
						</li>
						</div>
						<div class="col-sm-4">
            			<li class="form-validate">
							<label><span class="required">*</span>客户状态</label>
							<house:xtdm id="status" dictCode="CUSTOMERSTATUS" unShowValue="${unShowValue }" value="${customer.status }" 
							disabled="${flag=='doc'?true:false }" onchange="changeStatus()"></house:xtdm>
						</li>
						</div>
						<div class="col-sm-4">
						<li>
							<label>最后更新时间</label>
							<input type="text" style="width:160px;" id="lastUpdate" name="lastUpdate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate value='${customer.lastUpdate }' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly"/>
						</li>
						</div>
						<div class="col-sm-4">
						<li>
							<label>最后更新人员</label>
							<input type="text" style="width:160px;" id="lastUpdatedBy" name="lastUpdatedBy" value="${customer.lastUpdatedBy }" readonly="readonly"/>
						</li>
						</div>
						<div class="col-sm-4">
						<li>
							<label>到店时间</label>
							<input type="text" style="width:160px;" id="visitDate" name="visitDate" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.visitDate }' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly"/>
						</li>
						</div>
						<div class="col-sm-8">
            			<li class="form-validate">
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" ${flag=='doc'?'readonly':'' }>${customer.remarks }</textarea>
						</li>
						</div>
						<div class="col-sm-4">
            			<li>
							<label>过期</label>
							<input type="checkbox" id="expired_show" name="expired_show" value="${customer.expired }" onclick="checkExpired(this)" ${customer.expired=='T'?'checked':'' } ${flag=='doc'?'disabled':'' }>
						</li>
						</div>
						
					</ul>
            	</form>
            </div>
         </div>
</div>
</body>
</html>
