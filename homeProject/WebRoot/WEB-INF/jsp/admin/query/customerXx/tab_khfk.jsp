<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_khfk",{
			url:"${ctx}/admin/customerXx/goJqGrid_khfk",
			postData:{custCode:'${customer.code}'},
			rowNum: 10000,
			height: 230,
			caption:'付款记录明细',
			colModel : [
				{name : "date",index : "date",width : 150,label:"付款日期",sortable : true,align : "left",formatter:formatDate},
			    {name: "amount", index: "amount", width: 150, label: "付款金额", sortable: true, align: "right",sum: true},
			    {name : "documentno",index : "documentno",width : 200,label:"凭证号",sortable : true,align : "left"},
				{name: "lastupdate", index: "lastupdate", width: 185, label: "最后修改时间", sortable: true, align: "left",formatter:formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 150, label: "操作人员", sortable: true, align: "left"},
				{name: "payno", index: "payno", width:150,label: "收款单号",sortable:true,align:"left"},
				{name: "remarks", index: "remarks", width: 250, label: "备注", sortable: true, align: "left"},
				{name: "printczyname", index: "printczyname", width: 80, label: "打印人", sortable: true, align: "left"},
				{name: "printdate", index: "printdate", width: 130, label: "打印时间", sortable: true, align: "left", formatter: formatTime}
            ]
		});
		
		$("#wdz").val(myRound(parseFloat("${customerPayMap.wdz}"), 2));
});
</script>
<div class="body-box-list">
	<div class="panel">  
	    <div class="panel-body">
	    <form role="form" class="form-search" id="page_form_khfk" >
			<ul class="ul-form">
					<div style="display: none;">
					<li>
						<label>客户编号</label>
						<input type="text" id="code" name="code" style="width:160px;" value="${customerPayMap.code }" readonly="readonly"/>
					</li>
					<li>
						<label>客户名称</label>
						<input type="text" id="descr" name="descr" style="width:160px;" value="${customerPayMap.descr }" readonly="readonly"/>
					</li>
					<li>
						<label>客户状态</label>
						<input type="text" id="statusdescr" name="statusdescr" style="width:160px;" value="${customerPayMap.statusdescr }" readonly="readonly"/>
					</li>
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" value="${customerPayMap.address }" readonly="readonly"/>
					</li>
					<li>
						<label>面积</label>
						<input type="text" id="area" name="area" style="width:160px;" value="${customerPayMap.area }" readonly="readonly"/>
					</li>
					<li>
						<label>户型</label>
						<input type="text" id="layoutdescr" name="layoutdescr" style="width:160px;" value="${customerPayMap.layoutdescr }" readonly="readonly"/>
					</li>
					<li>
						<label>工程造价</label>
						<input type="text" id="contractfee" name="contractfee" style="width:160px;" value="${customerPayMap.contractfee }" readonly="readonly"/>
					</li>
					<li>
						<label>工程造价+设计费</label>
						<input type="text" id="id_contractfee" name="id_contractfee" style="width:160px;" value="${customerPayMap.contractfee+customerPayMap.designfee }" readonly="readonly"/>
					</li>
					</div>
					<li>
						<label>首批付款</label>
						<input type="text" id="firstpay" name="firstpay" style="width:160px;" value="${customerPayMap.firstpay }" readonly="readonly"/>
					</li>
					<li>
						<label>二批付款</label>
						<input type="text" id="secondpay" name="secondpay" style="width:160px;" value="${customerPayMap.secondpay }" readonly="readonly"/>
					</li>
					<li>
						<label>三批付款</label>
						<input type="text" id="thirdpay" name="thirdpay" style="width:160px;" value="${customerPayMap.thirdpay }" readonly="readonly"/>
					</li>
					<li>
						<label>尾批付款</label>
						<input type="text" id="fourpay" name="fourpay" style="width:160px;" value="${customerPayMap.fourpay }" readonly="readonly"/>
					</li>
					<li>
						<label>工程分期合计</label>
						<input type="text" id="sumpay" name="sumpay" style="width:160px;" value="${customerPayMap.sumpay }" readonly="readonly"/>
					</li>
					<div style="display: none;">
					<li>
						<label>基础直接费</label>
						<input type="text" id="basefee_dirctcontainbase" name="basefee_dirctcontainbase" style="width:160px;" value="${customerPayMap.basefee_dirctcontainbase }" readonly="readonly"/>
					</li>
					<li>
						<label>基础综合费</label>
						<input type="text" id="basefee_compcontainbase" name="basefee_compcontainbase" style="width:160px;" value="${customerPayMap.basefee_compcontainbase }" readonly="readonly"/>
					</li>
					<li>
						<label>管理费</label>
						<input type="text" id="managefeecontainbase" name="managefeecontainbase" style="width:160px;" value="${customerPayMap.managefeecontainbase }" readonly="readonly"/>
					</li>
					<li>
						<label>主材费</label>
						<input type="text" id="mainfeecontainmain" name="mainfeecontainmain" style="width:160px;" value="${customerPayMap.mainfeecontainmain }" readonly="readonly"/>
					</li>
					<li>
						<label>软装费</label>
						<input type="text" id="softfeecontainsoft" name="softfeecontainsoft" style="width:160px;" value="${customerPayMap.softfeecontainsoft }" readonly="readonly"/>
					</li>
					<li>
						<label>集成费</label>
						<input type="text" id="integratefeecontainint" name="integratefeecontainint" style="width:160px;" value="${customerPayMap.integratefeecontainint }" readonly="readonly"/>
					</li>
					<li>
						<label>橱柜费</label>
						<input type="text" id="cupboardfeecontaincup" name="cupboardfeecontaincup" style="width:160px;" value="${customerPayMap.cupboardfeecontaincup }" readonly="readonly"/>
					</li>
					<li>
						<label>服务性产品</label>
						<input type="text" id="mainservfeecontainmainserv" name="mainservfeecontainmainserv" style="width:160px;" value="${customerPayMap.mainservfeecontainmainserv }" readonly="readonly"/>
					</li>
					<li>
						<label>优惠合计</label>
						<input type="text" id="sumdisc" name="sumdisc" style="width:160px;" value="${customerPayMap.sumdisc }" readonly="readonly"/>
					</li>
					</div>
					<li>
						<label>增减总金额</label>
						<input type="text" id="zjzje" name="zjzje" style="width:160px;" value="${customerPayMap.zjzje }" readonly="readonly"/>
					</li>
					<li><label>已付款</label> <input type="text" id="haspay"
						name="haspay" style="width:160px;"
						value="${customerPayMap.haspay }" readonly="readonly" />
					</li>
					<li>
						<label>未达账</label> 
						<input type="text" id="wdz" name="wdz" style="width:160px;" readonly="readonly" /><!-- value="${customerPayMap.wdz }"  -->
					</li>
					<li><label>本期付款期数</label> <input type="text" id="nowNum"
						name="nowNum" style="width:160px;" value="${balanceMap.nowNum}"
						readonly="readonly" />
					</li>
					<li><label>本期应付余额</label> <input type="text" id="nowShouldPay"
						name="nowShouldPay" style="width:160px;"
						value="${balanceMap.nowShouldPay }" readonly="readonly" />
					</li>
					<li><label>下期应付余额</label> <input type="text"
						id="nextShouldPay" name="nextShouldPay" style="width:160px;"
						value="${balanceMap.nextShouldPay}" readonly="readonly" />
					</li>
					<li><label>设计费</label> <input type="text" id="designfee"
						name="designfee" style="width:160px;"
						value="${customerPayMap.designfee }" readonly="readonly" />
					</li>
					<li>
						<label>标准设计费</label>
						<input type="text" id="stddesignfee" name="stddesignfee" style="width:160px;" value="${customerPayMap.stddesignfee }" readonly="readonly"/>
					</li>
					<li>
						<label>设计费返还</label>
						<input type="text" id="returndesignfee" name="returndesignfee" style="width:160px;" value="${customerPayMap.returndesignfee }" readonly="readonly"/>
					</li>
					<li>
						<label class="control-textarea">优惠说明</label>
						<textarea id="discremark" name="discremark" readonly="readonly">${customerPayMap.discremark }</textarea>
					</li>
					<li>
						<label class="control-textarea"><font color="red">付款说明</font></label>
						<textarea id="payremark" name="payremark" readonly="readonly">${customerPayMap.payremark }</textarea>
					</li>
					<li>
						<label>付款方式</label>
						<input type="text" id="payType" name="payType" style="width:160px;" value="${customerPayMap.paytype }" readonly="readonly"/>
					</li>
				</ul>
		</form>
		</div>
	</div>
	<div class="pageContent">
		<table id="dataTable_khfk"></table>
	</div>
</div>
<script type="text/javascript">
$(function(){
	$("#page_form_khfk input[type='text']").each(function(){
		if ($(this).val() && !isNaN($(this).val())){
			$(this).val(parseFloat($(this).val()));
		}
	});
});
</script>
