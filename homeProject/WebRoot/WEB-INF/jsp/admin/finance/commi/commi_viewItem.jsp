<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>主材提成材料明细查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript">
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action=""
					method="post" target="targetFrame">
					<ul class="ul-form">
						<div class="validate-group row">
							<input type="hidden" id="pk" name="pk" value="${map.pk}" />
							<li><label>档案号</label> <input type="text" id="documentNo"
								name="documentNo" style="width:160px;" value="${map.documentno}"
								readonly />
							</li>
							<li><label>楼盘</label> <input type="text" id="address"
								name="address" style="width:160px;" value="${map.address }"
								readonly />
							</li>
							<li><label>客户编号</label> <input type="text" id="custCode"
								name="custCode" style="width:160px;" value="${map.custcode }"
								readonly />
							</li>
							<li><label>提成类型</label> <input type="text" id="commiTypeDescr"
								name="commiTypeDescr" style="width:160px;" value="${map.commitypedescr }"
								readonly />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>设计师 </label> <input type="text"
								id="designManDescr" name="designManDescr" style="width:160px;"
								value="${map.designmandescr }" readonly/>
							</li>
							<li><label>设计师部门</label> <input type="text"
								id="designManDeptDescr" name="designManDeptDescr"
								style="width:160px;" value="${map.designmandeptdescr }"
								readonly />
							</li>
							<li><label>现场设计师</label> <input type="text"
								id="sceneDesignManDescr" name="sceneDesignManDescr"
								style="width:160px;" value="${map.scenedesignmandescr }" readonly/>
							</li>
							<li><label>现场设计师部门</label> <input type="text"
								id="sceneDesignManDept" name="sceneDesignManDeptDescr"
								style="width:160px;" value="${map.scenedesignmandeptdescr }"
								readonly />
							</li>
						</div>
						<div class="validate-group row">
							<li><label> 材料名称</label> <input type="text" id="itemDescr"
								name="itemDescr" style="width:160px;" value="${map.itemdescr }" readonly/>
							</li>
							<li><label> 材料类型2</label> <input type="text" id="itemType2Descr"
								name="itemType2Descr" style="width:160px;" value="${map.itemtype2descr }" readonly/>
							</li>
							<li><label> 数量</label> <input type="text" id="qty"
								name="qty" style="width:160px;" value="${map.qty }" readonly/>
							</li>
							<li><label> 单位</label> <input type="text" id="uom"
								name="uom" style="width:160px;" value="${map.uomdescr }" readonly/>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>成本单价</label> <input type="text" id="cost"
								name="cost" style="width:160px;" value="${map.cost }" readonly/>
							</li>
							<li><label>成本总价</label> <input type="text" id="costAmount"
								name="costAmount" style="width:160px;"
								value="${map.costamount }" readonly/>
							</li>
							<li><label>折扣前金额</label> <input type="text"
								id="befLineAmount" name="befLineAmount" style="width:160px;"
								value="${map.beflineamount }" readonly/>
							</li>
							<li><label>折扣</label> <input type="text" id="markup"
								name="markup" style="width:160px;" value="${map.markup }" readonly/>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>小计</label> <input type="text" id="tmpLineAmount"
								name="tmpLineAmount" style="width:160px;"
								value="${map.tmplineamount }" readonly/>
							</li>
							<li><label>其他费用</label> <input type="text" id="processCost"
								name="processCost" style="width:160px;"
								value="${map.processcost }" readonly/>
							</li>
							<li><label>毛利</label> <input type="text" id="profit"
								name="profit" style="width:160px;" value="${map.profit }" readonly />
							</li>
							<li><label>毛利率</label> <input type="text" id="profitPer"
								name="profitPer" style="width:160px;" value="${map.profitper }%" readonly/>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>总价</label> <input type="text" id="lineAmount"
								name="lineAmount" style="width:160px;"
								value="${map.lineamount }" readonly/>
							</li>
							<li><label>提成额</label> <input type="text" id="commiAmount"
								name="commiAmount" style="width:160px;"
								value="${map.commiamount }" readonly/>
							</li>
							<li><label>区域名称</label> <input type="text" id="fixAreaDescr"
								name="fixAreaDescr" style="width:160px;" value="${map.fixareadescr }" readonly/>
							</li>
							<li><label>材料描述</label> <input type="text" id="remark"
								name="remark" style="width:160px;" value="${map.remark }" readonly/>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
