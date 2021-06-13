<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
$(function(){
	//$("#designMan").openComponent_employee({showLabel:"${customer.designManDescr}",showValue:"${customer.designMan}",readonly:true});
	//$("#businessMan").openComponent_employee({showLabel:"${customer.businessManDescr}",showValue:"${customer.businessMan}",readonly:true});
});
</script>
<style>

</style>
<div class="body-box-list" style="height:450px">
	<div class="panel" style="height:450px">  
	    <div class="panel-body" style="height:450px">
	    <form role="form" class="form-search"  >
			<ul class="ul-form" style="height:450px">
				<li>
					<label>客户编号</label>
					<input type="text" id="code" name="code" style="width:160px;" value="${customer.code}" readonly="readonly"/>
				</li>
				<li>
					<label>客户名称</label>
					<input type="text" id="descr" name="descr" style="width:160px;" value="${customer.descr }" readonly="readonly"/>
				</li>
				<li>
					<label>楼盘</label>
					<input type="text" id="address" name="address" value="${customer.address }" readonly="readonly"/>
				</li>
				<li>
					<label>实际地址</label>
					<input type="text" id="realAddress" name="realAddress" value="${customer.realAddress }" readonly="readonly"/>
				</li>
				<li >
					<label>项目名称</label>
					<house:dict id="custType" dictCode="" sql="select * from tBuilder " 
						sqlValueKey="Code" sqlLableKey="Descr" disabled="true" value="${customer.builderCode }" ></house:dict>
				</li>
				<li>
					<label>楼栋号</label>
					<input type="text" id="builderNum" name="builderNum" value="${customer.builderNum }" readonly="readonly"/>
				</li>
				<li >
					<label>客户类型</label>
					<house:dict id="custType" dictCode="" sql="select * from tCustType " 
						sqlValueKey="Code" sqlLableKey="Desc1" disabled="true" value="${customer.custType }" ></house:dict>
				</li>
				<li>
					<label>客户来源</label>
					<house:xtdm id="source" dictCode="CUSTOMERSOURCE" value="${customer.source}" disabled="true"></house:xtdm>
				</li>
				<li>
					<label>销售类型</label>
					<house:xtdm id="saleType" dictCode="SALETYPE" value="${customer.saleType }" disabled="true" unShowValue="0"></house:xtdm>
				</li>
				<li>
					<label>原客户号</label>
					<input type="text" id="oldCode" name="oldCode" style="width:160px;" value="${customer.oldCode}" readonly="readonly"/>
				</li>
				<li style="position: relative;">
					<label>面积</label>
					<input type="text" id="area" name="area" style="width:160px;" value="${customer.area}" readonly="readonly"/>
					<span style="position: absolute;left:290px;width: 30px;top:3px;">平方</span>
				</li>
				<li>
					<label>户型</label>
					<house:xtdm id="ayout" dictCode="LAYOUT" value="${customer.layout}" disabled="true"></house:xtdm>
				</li>
				<li>
					<label>性别</label>
					<house:xtdm id="gender" dictCode="GENDER" value="${customer.gender}" disabled="true"></house:xtdm>
				</li>
				<div style="display:${showPhone==true?'':'none'} ">
				<li>
					<label>手机号码1</label>
					<input type="text" id="mobile1" name="mobile1" style="width:160px;" value="${customer.mobile1}" readonly="readonly"/>
				</li>
				<li>
					<label>手机号码2</label>
					<input type="text" id="mobile2" name="mobile2" style="width:160px;" value="${customer.mobile2}" readonly="readonly"/>
				</li>
				<li>
					<label>QQ</label>
					<input type="text" id="qq" name="qq" style="width:160px;" value="${customer.qq}" readonly="readonly"/>
				</li>
				<li>
					<label>Email</label>
					<input type="text" id="email2" name="email2" style="width:160px;" value="${customer.email2}" readonly="readonly"/>
				</li>
				</div>
				<li>
					<label>设计师编号</label>
					<input type="text" id="designMan" name="designMan" style="width:160px;" readonly="readonly"
					value="${customer.designMan} ${customer.designManDescr}"/>
				</li>
				<li>
					<label>业务员编号</label>
					<input type="text" id="businessMan" name="businessMan" style="width:160px;" readonly="readonly"
					 value="${customer.businessMan} ${customer.businessManDescr}" />
				</li>
				<li>
					<label>创建时间</label>
					<input type="text" style="width:160px;" id="crtDate" name="crtDate" class="i-date" 
					value="<fmt:formatDate value='${customer.crtDate }' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly"/>
				</li>
				<li>
					<label>下定日期</label>
					<input type="text" style="width:160px;" id="setDate" name="setDate" class="i-date" 
					value="<fmt:formatDate value='${customer.setDate }' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly"/>
				</li>
				<li>
					<label>签单日期</label>
					<input type="text" style="width:160px;" id="signDate" name="signDate" class="i-date" 
					value="<fmt:formatDate value='${customer.signDate }' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly"/>
				</li>
				<li>
					<label>是否草签合同</label>
					<house:xtdm id="isInitSign" dictCode="YESNO" value="${customer.isInitSign}" disabled="true"></house:xtdm>
				</li>
				<li>
					<label>图纸审核日期</label>
					<input type="text" style="width:160px;" id="confirmBegin" name="confirmBegin" class="i-date" 
					value="<fmt:formatDate value='${designPicPrg.confirmDate }' pattern='yyyy-MM-dd'/>" readonly="readonly"/>
				</li>
				<li>
					<label>开工日期</label>
					<input type="text" style="width:160px;" id="confirmBegin" name="confirmBegin" class="i-date" 
					value="<fmt:formatDate value='${customer.confirmBegin }' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly"/>
				</li>
				<li>
					<label>客户结算日期</label>
					<input type="text" style="width:160px;" id="custCheckDate" name="custCheckDate" class="i-date" 
					value="<fmt:formatDate value='${customer.custCheckDate }' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly"/>
				</li>
				<li>
					<label>归档日期</label>
					<input type="text" style="width:160px;" id="endDate" name="endDate" class="i-date" 
					value="<fmt:formatDate value='${customer.endDate }' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly"/>
				</li>
				<li>
					<label>结束代码</label>
						<house:dict id="endCode" dictCode=""
							sql="select cbm ,rtrim(CBM)+' '+NOTE descr from tXTDM where ID='CUSTOMERENDCODE' ORDER BY IBM ASC  " 
							sqlValueKey="cbm" sqlLableKey="descr"  value="${customer.endCode }" disabled="true"></house:dict>
				</li>
				<li>
					<label>客户状态</label>
					<house:xtdm id="status" dictCode="CUSTOMERSTATUS" value="${customer.status }" disabled="true"></house:xtdm>
				</li>
				<li>
					<label>档案编号</label>
					<input type="text" id="documentNo" name="documentNo" style="width:160px;" value="${customer.documentNo}" readonly="readonly"/>
				</li>
				<li>
					<label>是否内部员工</label>
					<house:xtdm id="inInternal" dictCode="YESNO" value="${customer.isInternal}" disabled="true"></house:xtdm>
				</li>
				<div class="row">
				<li>
					<label>最后更新时间</label>
					<input type="text" style="width:160px;" id="lastUpdate" name="lastUpdate" class="i-date"  
					value="<fmt:formatDate value='${customer.lastUpdate }' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly"/>
				</li>
				<li>
					<label>最后更新人员</label>
					<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;" value="${customer.lastUpdatedBy}" readonly="readonly"/>
				</li>
				<li>
					<label>过期</label>
					<input type="checkbox" id="expired_show" name="expired_show" disabled="disabled" value="${customer.expired }" ${customer.expired=='T'?'checked':'' }>
				</li>
				</div>
				<div class="row">
					<li>
						<label class="control-textarea">备注</label>
						<textarea id="remarks" name="remarks" rows="6" readonly="readonly">${customer.remarks }</textarea>
					</li>
				<li>
					<label class="control-textarea">优惠政策</label>
					<textarea id="discRemark" rows="6" name="discRemark" readonly="readonly">${customer.discRemark }</textarea>
				</li>
				</div>
			</ul>
			</form>
		</div>
	</div>
</div>
