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
	<title>基础项目管理查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_basePlanTemp.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript"> 
		$(function (){
			$("#baseTempNo").openComponent_basePlanTemp({showValue:'${baseItem.baseTempNo}',showLabel:'${baseItem.baseTempDescr}'});
			Global.LinkSelect.initSelect("${ctx}/admin/baseItemType1/baseItemType","baseItemType1","baseItemType2");
			Global.LinkSelect.setSelect({firstSelect:"baseItemType1",
								firstValue:"${baseItem.baseItemType1}",
								secondSelect:"baseItemType2",
								secondValue:"${baseItem.baseItemType2}"});
								
	        Global.LinkSelect.initSelect("${ctx}/admin/item/itemType", "itemType1", "itemType2")
	        Global.LinkSelect.setSelect({firstSelect:"itemType1",
	                                firstValue:"${baseItem.itemType1}",
	                                secondSelect:"itemType2",
	                                secondValue:"${baseItem.itemType2}"});
		});
	</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<div class="row">
								<div class="col-xs-6">
									<li>
										<label>基础项目编号</label> 
										<input type="text" id="code" name="code" style="width:160px;" value="${baseItem.code }" readonly="readonly" />
									</li>
									<li class="form-validate">
										<label>基装项目名称</label> 
										<input type="text" id="descr" name="descr" style="width:160px;" value="${baseItem.descr }" readonly="readonly"/>
									</li>
									<li><label>助记码</label> 
										<input type="text" id="remCode" name="remCode" style="width:160px;" value="${baseItem.remCode }" readonly="readonly"/>
									</li>
									<li class="form-validate">
										<label>项目分类</label> 
										<house:xtdm id="category" dictCode="BASEITEMCAT" value="${baseItem.category}" disabled="true"></house:xtdm>
									</li>
									<li class="form-validate">
										<label>基装类型1</label>
										<select id="baseItemType1" name="baseItemType1" disabled="true"></select>
									</li>
									<li class="form-validate">
										<label>基装类型2</label>
										<select id="baseItemType2" name="baseItemType2" disabled="true"></select>
									</li>
									<li class="form-validate">
										<label>成本</label> 
										<input type="text" id="cost" name="cost" style="width:160px;" value="${baseItem.cost }" readonly="readonly"/>
									</li>
									<li class="form-validate">
										<label>市场价</label> 
										<input type="text" id="marketPrice" name="marketPrice" style="width:160px;" readonly="readonly" value="${baseItem.marketPrice }"/>
									</li>
									<li class="form-validate">
										<label>人工单价</label> 
										<input type="text" id="offerPri" name="offerPri" style="width:160px;" value="${baseItem.offerPri }" readonly="readonly"/>
									</li>
									<li class="form-validate">
										<label>材料单价</label> 
										<input type="text" id="material" name="material" style="width:160px;" value="${baseItem.material }" readonly="readonly"/>
									</li>
									<li class="form-validate">
										<label>项目经理结算价</label> 
										<input type="text" id="projectPrice" name="projectPrice" style="width:160px;" value="${baseItem.projectPrice }" readonly="readonly"/>
									</li>
									<li class="form-validate">
										<label>是否固定价</label> 
										<house:xtdm id="isFixPrice" dictCode="YESNO" value="${baseItem.isFixPrice }" disabled="true"></house:xtdm>
									</li>
									<li class="form-validate">
										<label>单位</label> 
										 <house:DataSelect id="uom" className="Uom" keyColumn="code" valueColumn="descr" value="${baseItem.uom }" disabled="true"></house:DataSelect>
									</li>
									<li class="form-validate">
										<label>施工类型</label> 
										<house:xtdm id="prjType" dictCode="BASEITEMPRJTYPE" value="${baseItem.prjType }" disabled="true"></house:xtdm>
									</li>
									<li class="form-validate">
										<label>显示顺序</label> 
										<input type="text" id="dispSeq" name="dispSeq" style="width:160px;" value="${baseItem.dispSeq }" disabled="true"/>
									</li>
									<li class="form-validate">
										<label>业绩比例</label> 
										<input type="text" id="perfPer" name="perfPer" style="width:160px;" value="${baseItem.perfPer }" disabled="true"/>
									</li>
                                    <li>
                                        <label>过期</label>
                                        <input type="checkbox" id="expired_show" name="expired_show" value="${baseItem.expired }",
                                            onclick="checkExpired(this)" ${baseItem.expired=="T"?"checked":"" }/>
                                    </li>
								</div>
								<div class="col-xs-6">
									<li>
										<label></label> 
									</li>
									<li>
										<label>发包方式</label> 
										<house:xtdm id="prjCtrlType" dictCode="PRJCTRLTYPE" value="${baseItem.prjCtrlType }" disabled="true"></house:xtdm>
									</li>
									<li class="form-validate">
										<label>人工发包</label> 
										<input type="text" id="offerCtrl" name="offerCtrl" style="width:160px;"value="${baseItem.offerCtrl}" readonly="readonly"/>
									</li>
									<li class="form-validate">
										<label>材料发包</label> 
										<input type="text" id="materialCtrl" name="materialCtrl" style="width:160px;"value="${baseItem.materialCtrl}" readonly="readonly"/>
									</li>
									<li class="form-validate">
										<label>是否计算管理费</label> 
										<house:xtdm id="isCalMangeFee" dictCode="YESNO" value="${baseItem.isCalMangeFee }"></house:xtdm>
									</li>	
									<li class="form-validate">
										 <label>客户类型</label> 
										 <house:DataSelect id="custType" className="CustType" keyColumn="code" valueColumn="desc1" value="${baseItem.custType }"></house:DataSelect>
									</li>
									<li>
                                        <label>客户类型组</label>
                                        <house:dict id="custTypeGroupNo" dictCode="" value="${baseItem.custTypeGroupNo}" disabled="true"
                                            sql="select No SQL_VALUE_KEY, Descr SQL_LABEL_KEY from tCustTypeGroup where Expired = 'F'"></house:dict>
                                    </li>
									<li class="form-validate">
										<label>允许价格上浮</label> 
										<house:xtdm id="allowPriceRise" dictCode="YESNO" value="${baseItem.allowPriceRise }"></house:xtdm>
									</li>
									<li class="form-validate">
										<label>套餐外项目</label> 
										<house:xtdm id="isOutSet" dictCode="YESNO" value="${baseItem.isOutSet }" ></house:xtdm>
									</li>
									<li class="form-validate" >
										<label><span class="required">*</span>是否基础套餐包</label> 
										<house:xtdm id="isBaseItemSet" dictCode="YESNO" value="${baseItem.isBaseItemSet}"></house:xtdm>
									</li>
									<li><label>关联模板</label> <input type="text" id="baseTempNo" name="baseTempNo" />
									</li>
									<li><label><span class="required">*</span>关联空间类型</label> <house:dict id="fixAreaType" dictCode=""
											sqlValueKey="Code" sqlLableKey="Descr" value="${baseItem.fixAreaType}"
											sql="select code,code+' '+descr descr from tFixAreaType where expired ='F'"></house:dict>
									</li>
                                    <li>
                                        <label>材料类型1</label>
                                        <select id="itemType1" name="itemType1" disabled></select>
                                    </li>
                                    <li>
                                        <label>材料类型2</label>
                                        <select id="itemType2" name="itemType2" disabled></select>
                                    </li>
									<li >
										<label class="control-textarea">备注</label>
										<textarea id="remark" style="width:160px" name="remark" rows="4" >${baseItem.remark }</textarea>
									</li>
								</div>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
