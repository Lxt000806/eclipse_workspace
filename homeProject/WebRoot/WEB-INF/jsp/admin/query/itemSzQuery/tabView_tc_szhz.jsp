<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(function(){
		$("#szhzUl li label").css({"width":"160px"});
	});
</script>
	
<div class="panel-info" >  
	<div class="panel-body">
		<form action="" method="post" id="dataForm"  class="form-search">
			<ul id="szhzUl" class="ul-form">
				<li>
					<label>发包金额</label>
					<input type="text" value="${customer.fbje }" />
				</li>
				<li>
					<label>支出合计</label>
					<input type="text" value="${customer.zchj }" />
				</li>
				<li>
					<label>发包余额</label>
					<input type="text" value="${customer.fbye }" />
				</li>	
				<li>
					<label>材料成本支出</label>
					<input type="text"  value="${customer.materialcost }" />
				</li>
				<li>
					<label>工人工资支出</label>
					<input type="text" value="${customer.workcost }" />
				</li>
				<li>
					<label>人工费用支出</label>
					<input type="text"  value="${customer.workfeecost }" />
				</li>
				<li>
					<label>主套餐费</label>
					<input type="text" value="${customer.mainsetfee }" />
				</li>
				<li>
					<label>远程费</label>
					<input type="text" value="${customer.longfee }" />
				</li>	
				<li>
					<label>客户结算状态</label>
					<input type="text" value="${customer.checkstatus }" />
				</li>
				<li>
					<label>套餐外基础增项合计</label>
					<input type="text" value="${customer.setaddhj }" />
				</li>
				<li>
					<label>套餐外基础增项(预算)</label>
					<input type="text" value="${customer.setaddys }" />
				</li>
				<li>
					<label>套餐外基础增项(增减)</label>
					<input type="text" value="${customer.setaddzj }" />
				</li>	
				<li>
					<label>套餐内减项合计</label>
					<input type="text" value="${customer.setminushj }" />
				</li>
				<li>
					<label>套餐内减项(预算)</label>
					<input type="text" value="${customer.setminusys }" />
				</li>
				<li>
					<label>套餐内减项(增减)</label>
					<input type="text" value="${customer.setminuszj }" />
				</li>
				<li>
					<label>基础管理费</label>
					<input type="text" value="${customer.managefee_basehj }" />
				</li>	
				<li>
					<label>基础管理费(预算)</label>
					<input type="text"  value="${customer.managefee_baseys }" />
				</li>
				<li>
					<label>基础管理费(增减)</label>
					<input type="text" value="${customer.managefee_basezj }" />
				</li>
				<li>
					<label>主材合计</label>
					<input type="text"  value="${customer.mainhj }" />
				</li>
				<li>
					<label>主材预算</label>
					<input type="text" value="${customer.mainys }" />
				</li>
				<li>
					<label>主材增减</label>
					<input type="text" value="${customer.mainzj }" />
				</li>	
				<li>
					<label>服务性产品合计</label>
					<input type="text" value="${customer.servhj }" />
				</li>
				<li>
					<label>服务性产品预算</label>
					<input type="text" value="${customer.servys }" />
				</li>
				<li>
					<label>服务性产品增减</label>
					<input type="text" value="${customer.servzj }" />
				</li>
				<li>
					<label>集成合计</label>
					<input type="text" value="${customer.inthj }" />
				</li>	
				<li>
					<label>集成预算</label>
					<input type="text" value="${customer.intys }" />
				</li>				
				<li>
					<label>集成增减</label>
					<input type="text" value="${customer.intzj }" />
				</li>
				<li>
					<label>橱柜合计</label>
					<input type="text" value="${customer.cuphj }" />
				</li>
				<li>
					<label>橱柜预算</label>
					<input type="text" value="${customer.cupys }" />
				</li>	
				<li>
					<label>橱柜增减</label>
					<input type="text"  value="${customer.cupzj }" />
				</li>
				<li>
					<label>材料合计</label>
					<input type="text" value="${customer.materialhj }" />
				</li>
				<li>
					<label>升级扣项</label>
					<input type="text"  value="${customer.upgwithhold }" />
				</li>
				<li>
					<label>预扣</label>
					<input type="text" value="${customer.withhold }" />
				</li>
				<li>
					<label>已领</label>
					<input type="text" value="${customer.recvfee }" />
				</li>	
				<li>
					<label>质保金</label>
					<input type="text" value="${customer.qualityfee }" />
				</li>
				<li>
					<label>应发金额</label>
					<input type="text" value="${customer.mustamount }" />
				</li>
				<li>
					<label>实发金额</label>
					<input type="text" value="${customer.realamount }" />
				</li>
				<li>
					<label>是否发放</label>
					<input type="text" value="${customer.isprovide }" />
				</li>	
				<li>
					<label>发放编号</label>
					<input type="text" value="${customer.provideno }" />
				</li>
				<li>
					<label class="control-textarea">备注</label>
					<textarea>${customer.remarks }</textarea>
				</li>
			</ul>
		</form>
	</div>
</div>
