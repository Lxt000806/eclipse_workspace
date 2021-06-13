<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 

<script type="text/javascript">
$(function(){
	var costRight="${USER_CONTEXT_KEY.costRight}";
	Global.JqGrid.initJqGrid("dataTable_jccbzcxx",{
		url:"${ctx}/admin/itemSzQuery/goJcszxxJqGrid",
		postData:{code:$("#code").val()},
		height:255,
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "worktype1", index: "worktype1", width: 90, label: "工种分类1类编号", sortable: true, align: "left", hidden: true},
			{name: "worktype2", index: "worktype2", width: 90, label: "工种分类2类编号", sortable: true, align: "left", hidden: true},
			{name: "worktype1name", index: "worktype1name", width: 120, label: "工种分类1", sortable: true, align: "left",cellattr: function(rowId, value, rowObject, colModel, arrData) {return 'id=\'worktype1name' + rowId + "\'";}},
			{name: "worktype2name", index: "worktype2name", width: 120, label: "工种分类2", sortable: true, align: "left"},
			{name: "cbde", index: "cbde", width: 80, label: "成本定额", sortable: true, align: "right", sum: true},
			{name: "allamountinset", index: "allamountinset", width: 90, label: "套餐内发包额", sortable: true, align: "right", sum: true,hidden:costRight=="1"?false:true},
			{name: "allamountoutset", index: "allamountoutset", width: 90, label: "套餐外发包额", sortable: true, align: "right", sum: true},
			{name: "materialcostjs", index: "materialcostjs", width: 120, label: "材料结算成本", sortable: true, align: "right", sum: true},
			{name: "workcostjs", index: "workcostjs", width: 100, label: "人工结算成本", sortable: true, align: "right", sum: true},
			{name: "allamountjs", index: "allamountjs", width: 120, label: "结算总支出", sortable: true, align: "right", sum: true},
			{name: "materialcost", index: "materialcost", width: 120, label: "材料成本", sortable: true, align: "right", sum: true},
			{name: "workcost", index: "workcost", width: 120, label: "人工成本", sortable: true, align: "right", sum: true},
			{name: "allamount", index: "allamount", width: 120, label: "总支出", sortable: true, align: "right", sum: true},
			{name: "caltype", index: "caltype", width: 84, label: "caltype", sortable: true, align: "left", hidden: true}
        ],
        gridComplete:function(){
			//if("${customer.type}"=="2"){
				Merger("dataTable_jccbzcxx","worktype1name");
	        	//add by zzr 2017/12/19 11:10 通过模拟合并单元格后,设置默认选中行的样式 begin
				var id = $("#dataTable_jccbzcxx").jqGrid("getGridParam", "selrow");
				if(id > 0){
       				$("#dataTable_jccbzcxx tbody tr #worktype1name"+id).css({"background":"#198EDE","color":"white"});
				}
			
	        	//add by zzr 2017/12/19 11:10 通过模拟合并单元格后,设置默认选中行的样式 end
			//}
        },
        ondblClickRow:function(rowid,iRow,iCol,e){
        	var ret = $("#dataTable_jccbzcxx").jqGrid("getRowData",rowid);
            var thisGrid = $(this);
            var colNames = thisGrid.getGridParam("colNames");
            var iColName = colNames[iCol];
            var costType = "";
            
            // 成本类型：人工成本与人工结算成本
            if (iColName === "人工成本")
                costType = "CAL_COST";
            else if (iColName === "人工结算成本")
                costType = "CAL_PROJECT_COST";
        	
        	$("#dataTable_llmx_jcclcbmx").jqGrid("clearGridData");
        	
        	$("#page_form_jcszxx input[type='hidden'][name='costType']").val(costType);
        	$("#page_form_jcszxx input[type='hidden'][name='workType2']").val(ret.worktype2.trim());
        	
			goto_query("dataTable_rgcbmx_tc",{costType:costType,custCode:$("#code").val(),workType2:ret.worktype2.trim()},"${ctx}/admin/itemSzQuery/goRgcbmxTcJqGrid");
			goto_query("dataTable_jcclcbmx",{custCode:$("#code").val(),workType2:ret.worktype2.trim()},"${ctx}/admin/itemSzQuery/goJcclcbmxJqGrid");
        	goto_query("dataTable_waterContractQuota_tc",{custCode:$("#code").val(),workType2:ret.worktype2.trim()},"${ctx}/admin/itemSzQuery/goWaterContractQuotaJqGrid");
        	
        	if(iColName == "人工结算成本" || iColName == "人工成本"){
        		$("#a_tc_rgcbmx").tab("show");
        	}else if(iColName == "成本定额"){
        		$("#a_tc_waterContractQuota").tab("show");
        	}else if(iColName == "材料结算成本" || iColName == "材料成本"){
        		$("#a_tc_jcclcbmx").tab("show");
        	}
        },
        beforeSelectRow:function(rowid, e){
        	//update by zzr 2017/12/19 11:11 采用模拟合并单元格方式,修改选中样式 begin
/*         	$("#dataTable_jccbzcxx tbody tr td[id^=worktype1name]").css({"background":"","color":""});
        	var index = $("#dataTable_jccbzcxx").jqGrid("getInd",rowid);
        	var ids = $("#dataTable_jccbzcxx").jqGrid("getCol","worktype1name",true);
        	var i = index-1;
        	for(;i>0;i--){
        		if(ids[index-1].value != ids[i].value){
        			break;
        		}
        	}
        	$("#dataTable_jccbzcxx tbody tr[id=\""+(i>0?ids[i+1].id:1)+"\"] td[id=\"worktype1name"+(i>0?ids[i+1].id:1)+"\"]").css({"background":"#198EDE","color":"white"}); */
 	      	$("#dataTable_jccbzcxx tbody tr td[id^=worktype1name]").css({"background":"white","color":"#333"});
        	$("#dataTable_jccbzcxx tbody tr #worktype1name"+rowid).css({"background":"#198EDE","color":"white"});
        	var ret = $("#dataTable_jccbzcxx").jqGrid("getRowData",rowid);
       		$("#page_form_jcszxx input[type='hidden'][name='workType1Name']").val(ret.worktype1name);
        	$("#page_form_jcszxx input[type='hidden'][name='workType2']").val(ret.worktype2.trim());
        	//update by zzr 2017/12/19 11:11 采用模拟合并单元格方式,修改选中样式 end
        }
	});
	
	$("#jccbzcxxUl li input[id!=fbje]").css({"width":"110px"});
	$("#jccbzcxxUl li").css({"padding-left":"0px"});
	$("#jccbzcxxUl li label").css({"margin-left":"0px","width":"90px","margin-right":"0px"});
	$("#jccbzcxxUl li textarea").css({"width":"415px"});
	$("#fbgsBtn").on("click",function () {
		Global.Dialog.showDialog("fbgs",{
			title:"查看发包公式",
			url:"${ctx}/admin/itemSzQuery/goViewFbgs",
			postData:{custCode:$("#code").val(), type:"2"},		
			height: 300,
			width:870,
		});
	});
});
function goto_query(tableId,postData,url){
	var options = {url:url,postData:postData,page:1};
	$("#"+tableId).jqGrid("setGridParam",options).trigger("reloadGrid");
}
</script>

<table id="dataTable_jccbzcxx"></table>

<div style="width:100%;height:20px;background:#EEEEEE">发包控制成本支出信息</div>

<div class="panel-info" >  
	<div class="panel-body">
		<form action="" method="post" id="dataForm"  class="form-search">
			<ul id="jccbzcxxUl" class="ul-form">
				<li>
					<label>发包金额</label>
					<div class="input-group">
						<input type="text" class="form-control" id="fbje" value="<fmt:formatNumber value="${customer.fbje }" pattern="0.00"/>" style="width: 72px;" >
						<button type="button" class="btn btn-system" id="fbgsBtn" style="font-size: 12px;width: 40px;" >公式</button>
					</div>
				</li>
				<li>
					<label>支出合计</label>
					<input type="text" value="<fmt:formatNumber value="${customer.zchj }" pattern="0.00"/>" />
				</li>
				<li>
					<label>发包余额</label>
					<input type="text" value="<fmt:formatNumber value="${customer.fbye }" pattern="0.00"/>" />
				</li>	
				<li>
					<label>主材支出</label>
					<input type="text" value="<fmt:formatNumber value="${customer.materialcost }" pattern="0.00"/>" />
				</li>
				<li>
					<label>人工费用支出</label>
					<input type="text" value="<fmt:formatNumber value="${customer.workfeecost }" pattern="0.00"/>" />
				</li>
				<li>
					<label>套餐费</label>
					<input type="text" value="<fmt:formatNumber value="${customer.mainsetfee }" pattern="0.00"/>" />
				</li>
				<li>
					<label>远程费</label>
					<input type="text" value="<fmt:formatNumber value="${customer.longfee }" pattern="0.00"/>" />
				</li>
				<li>
					<label>套餐外基础增项</label>
					<input type="text" value="<fmt:formatNumber value="${customer.setaddhj }" pattern="0.00"/>" />
				</li>	
				<li>
					<label>套餐内减项</label>
					<input type="text" value="<fmt:formatNumber value="${customer.setminushj }" pattern="0.00"/>" />
				</li>
				<li>
					<label>材料预算</label>
					<input type="text" value="<fmt:formatNumber value="${customer.materialys }" pattern="0.00"/>" />
				</li>
				<li>
					<label>基础管理费</label>
					<input type="text" value="<fmt:formatNumber value="${customer.managefee_basehj }" pattern="0.00"/>" />
				</li>
			</ul>
		</form>
	</div>
</div>
<div style="width:100%;height:20px;background:#EEEEEE">项目经理结算信息</div>

<div class="panel-info" >  
	<div class="panel-body">
		<form action="" method="post" id="dataForm"  class="form-search">
			<ul id="jccbzcxxUl" class="ul-form">
				<li>
					<label>升级预扣</label>
					<input type="text" value="${customer.UpgWithHold }" />
				</li>
				<li>
					<label>发包总价</label>
					<input type="text" value="${customer.BaseCtrlAmt }" />
				</li>
				<li>
					<label>预扣</label>
					<input type="text" value="${customer.WithHold }" />
				</li>	
				<li>
					<label>支出</label>
					<input type="text" value="${customer.Cost }" />
				</li>
				<li>
					<label>基础支出</label>
					<input type="text" value="${customer.BaseCost }" />
				</li>
				<li>
					<label>主材支出</label>
					<input type="text" value="${customer.MainCost }" />
				</li>
				<li>
					<label>已领</label>
					<input type="text" value="${customer.RecvFee }" />
				</li>
				<li>
					<label>已领定责</label>
					<input type="text" value="${customer.recvFeeFixDuty }" />
				</li>
				<li>
					<label>质保金</label>
					<input type="text" value="${customer.QualityFee }" />
				</li>	
				<li>
					<label>应发金额</label>
					<input type="text" value="${customer.MustAmount }" />
				</li>
				<li>
					<label>实发金额</label>
					<input type="text" value="${customer.RealAmount }" />
				</li>
				<li>
					<label>结算状态</label>
					<input type="text" value="${customer.CheckStatusDescr }" />
				</li>
				<li>
					<label>发包补贴</label>
					<input type="text" value="${customer.projectCtrlAdj }" />
				</li>
				<li>
					<label class="control-textarea">备注</label>
					<textarea>${customer.Remarks }</textarea>
				</li>
				<li>
					<label class="control-textarea">补贴说明</label>
					<textarea>${customer.ctrlAdjRemark }</textarea>
				</li>
			</ul>
		</form>
	</div>
</div>
