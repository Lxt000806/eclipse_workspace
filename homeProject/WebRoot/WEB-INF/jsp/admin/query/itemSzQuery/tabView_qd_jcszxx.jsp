<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var costRight="${USER_CONTEXT_KEY.costRight}";
	Global.JqGrid.initJqGrid("dataTable_jcszxx",{
		url:"${ctx}/admin/itemSzQuery/goJcszxxJqGrid",
		rowNum: 10000,
		postData:{code:$("#code").val()},
		height:330,
		styleUI: 'Bootstrap',
		colModel : [
			{name: "worktype1", index: "worktype1", width: 90, label: "工种分类1类编号", sortable: true, align: "left", hidden: true},
			{name: "worktype2", index: "worktype2", width: 90, label: "工种分类2类编号", sortable: true, align: "left",},
			{name: "worktype1name", index: "worktype1name", width: 80, label: "工种分类1", sortable: true, align: "left",cellattr: function(rowId, value, rowObject, colModel, arrData) {return 'id=\'worktype1name' + rowId + "\'";}},
			{name: "worktype2name", index: "worktype2name", width: 80, label: "工种分类2", sortable: true, align: "left"},
			{name: "preamount", index: "preamount", width: 70, label: "预算金额", sortable: true, align: "right", sum: true},
			{name: "prefbamount", index: "prefbamount", width: 90, label: "预算发包定额", sortable: true, align: "right", sum: true},
			{name: "chgamount", index: "chgamount", width: 70, label: "增减金额", sortable: true, align: "right", sum: true},
			{name: "chgfbamount", index: "chgfbamount", width: 90, label: "增减发包定额", sortable: true, align: "right", sum: true},
			{name: "allfbamount", index: "allfbamount", width: 80, label: "总发包定额", sortable: true, align: "right", sum: true},
			{name: "cbde", index: "cbde", width: 80, label: "成本定额", sortable: true, align: "right", sum: true},
			{name: "allamountinset", index: "allamountinset", width: 90, label: "套餐内发包额", sortable: true, align: "right", sum: true,hidden:costRight=="1"?false:true},
			{name: "allamountoutset", index: "allamountoutset", width: 90, label: "套餐外发包额", sortable: true, align: "right", sum: true},
			{name: "materialcostjs", index: "materialcostjs", width: 90, label: "材料结算成本", sortable: true, align: "right", sum: true},
			{name: "workcostjs", index: "workcostjs", width: 90, label: "人工结算成本", sortable: true, align: "right", sum: true},
			{name: "allamountjs", index: "allamountjs", width: 80, label: "结算总支出", sortable: true, align: "right", sum: true},
			{name: "remainamountjs", index: "remainamountjs", width: 70, label: "结算余额", sortable: true, align: "right", sum: true},
			{name: "materialcost", index: "materialcost", width: 70, label: "材料成本", sortable: true, align: "right", sum: true},
			{name: "workcost", index: "workcost", width: 70, label: "人工成本", sortable: true, align: "right", sum: true},
			{name: "allamount", index: "allamount", width: 70, label: "总支出", sortable: true, align: "right", sum: true},
			{name: "remainamount", index: "remainamount", width: 70, label: "余额", sortable: true, align: "right", sum: true},
			{name: "caltype", index: "caltype", width: 84, label: "caltype", sortable: true, align: "left", hidden: true},
			{name: "worktype1name_hidden", index: "worktype1name_hidden", width: 90, label: "工种分类1", sortable: true, align: "left",hidden: true}
        ],
        gridComplete:function(){
        	var worktype1names = $("#dataTable_jcszxx").jqGrid("getCol","worktype1name",true);
        	
        	if(worktype1names.length > 0){
	        	for(var i=0;i<worktype1names.length;i++){
					$("#dataTable_jcszxx").jqGrid("setCell",worktype1names[i].id,"worktype1name_hidden",worktype1names[i].value);
	        	}
	        	if("${customer.type}"=="1"){
	        		Merger("dataTable_jcszxx","worktype1name");
	        		//add by zzr 2017/12/19 10:29 通过模拟合并单元格后,设置默认选中行的样式 begin
					var id = $("#dataTable_jcszxx").jqGrid("getGridParam", "selrow");
					if(id > 0){
        				$("#dataTable_jcszxx tbody tr #worktype1name"+id).css({"background":"#198EDE","color":"white"});
					}
	        		//add by zzr 2017/12/19 10:29 通过模拟合并单元格后,设置默认选中行的样式 end
	        	}
        	}
        },
        ondblClickRow:function(rowid,iRow,iCol,e){
        	var ret = $("#dataTable_jcszxx").jqGrid("getRowData",rowid);
        	var thisGrid = $(this);
        	var colNames = thisGrid.getGridParam("colNames");
        	var iColName = colNames[iCol];
        	var costType = "";
        	
            // 成本类型：人工成本与人工结算成本
            if (iColName === "人工成本")
                costType = "CAL_COST";
            else if (iColName === "人工结算成本")
                costType = "CAL_PROJECT_COST";
        				
        	$("#dataTable_llmx_clcbmx").jqGrid("clearGridData");
        	
        	$("#page_form_jcszxx input[type='hidden'][name='costType']").val(costType);
        	$("#page_form_jcszxx input[type='hidden'][name='calType']").val(ret.caltype);
        	$("#page_form_jcszxx input[type='hidden'][name='workType1Name']").val(ret.worktype1name_hidden);
        	$("#page_form_jcszxx input[type='hidden'][name='workType2']").val(ret.worktype2.trim());
        				
        	goto_query("dataTable_yshfbdemx",{calType:ret.caltype,workType1Name:ret.worktype1name_hidden,custCode:$("#code").val(),workType2:ret.worktype2.trim()},"${ctx}/admin/itemSzQuery/goYshfbdemxJqGrid");
			goto_query("dataTable_zjhfbdemx",{calType:ret.caltype,workType1Name:ret.worktype1name_hidden,custCode:$("#code").val(),workType2:ret.worktype2.trim()},"${ctx}/admin/itemSzQuery/goZjhfbdemxJqGrid");
			goto_query("dataTable_zfbdemx",{calType:ret.caltype,workType1Name:ret.worktype1name_hidden,custCode:$("#code").val(),workType2:ret.worktype2.trim()},"${ctx}/admin/itemSzQuery/goZfbdemxJqGrid");
			goto_query("dataTable_clcbmx",{workType1Name:ret.worktype1name_hidden,custCode:$("#code").val(),workType2:ret.worktype2.trim()},"${ctx}/admin/itemSzQuery/goClcbmxJqGrid");
			goto_query("dataTable_waterContractQuota_qd",{custCode:$("#code").val(),workType2:ret.worktype2.trim()},"${ctx}/admin/itemSzQuery/goWaterContractQuotaJqGrid");
			goto_query("dataTable_rgcbmx_qd",{costType:costType,workType1Name:ret.worktype1name_hidden,custCode:$("#code").val(),workType2:ret.worktype2.trim()},"${ctx}/admin/itemSzQuery/goRgcbmxJqGrid");
							
        	if(iColName == "预算金额" || iColName == "预算发包定额"){
        		$("#a_qd_yshfbdemx").tab("show");
        	}else if(iColName == "增减金额" || iColName == "增减发包定额"){
        		$("#a_qd_zjhfbdemx").tab("show");
        	}else if(iColName == "总发包定额"){
        		$("#a_qd_zfbdemx").tab("show");
        	}else if(iColName == "人工结算成本" || iColName == "人工成本"){
        		$("#a_qd_rgcbmx").tab("show");
        	}else if( iColName == "成本定额"){
        		$("#a_qd_waterContractQuota").tab("show");
        	}else if(iColName == "材料结算成本" || iColName == "材料成本"){
        		$("#a_qd_clcbmx").tab("show");
        	}
        },
        
        beforeSelectRow:function(rowid, e){
            /* 取消单击加载表格方式，与套餐客户界面保持一致    张海洋 20210323
        	//update by xzy 2019-3-14 09:48:24，选择一行，然后点击其他页签 数据刷新
            var ret = $("#dataTable_jcszxx").jqGrid("getRowData",rowid);
            
            $("#page_form_jcszxx input[type='hidden'][name='calType']").val(ret.caltype);
        	$("#page_form_jcszxx input[type='hidden'][name='workType1Name']").val(ret.worktype1name_hidden);
        	$("#page_form_jcszxx input[type='hidden'][name='workType2']").val(ret.worktype2.trim());
        	
        	goto_query("dataTable_yshfbdemx",{calType:ret.caltype,workType1Name:ret.worktype1name_hidden,custCode:$("#code").val(),workType2:ret.worktype2.trim()},"${ctx}/admin/itemSzQuery/goYshfbdemxJqGrid");
			goto_query("dataTable_zjhfbdemx",{calType:ret.caltype,workType1Name:ret.worktype1name_hidden,custCode:$("#code").val(),workType2:ret.worktype2.trim()},"${ctx}/admin/itemSzQuery/goZjhfbdemxJqGrid");
			goto_query("dataTable_zfbdemx",{calType:ret.caltype,workType1Name:ret.worktype1name_hidden,custCode:$("#code").val(),workType2:ret.worktype2.trim()},"${ctx}/admin/itemSzQuery/goZfbdemxJqGrid");
			goto_query("dataTable_rgcbmx_qd",{workType1Name:ret.worktype1name_hidden,custCode:$("#code").val(),workType2:ret.worktype2.trim()},"${ctx}/admin/itemSzQuery/goRgcbmxJqGrid");
			goto_query("dataTable_clcbmx",{workType1Name:ret.worktype1name_hidden,custCode:$("#code").val(),workType2:ret.worktype2.trim()},"${ctx}/admin/itemSzQuery/goClcbmxJqGrid");
			goto_query("dataTable_waterContractQuota_qd",{custCode:$("#code").val(),workType2:ret.worktype2.trim()},"${ctx}/admin/itemSzQuery/goWaterContractQuotaJqGrid");
			*/
			
        	//update by zzr 2017/12/19 10:18 采用模拟合并单元格方式,修改选中样式 begin
        	
 /*       	$("#dataTable_jcszxx tbody tr td[id^=worktype1name]").css({"background":"white"});
         	var index = $("#dataTable_jcszxx").jqGrid("getInd",rowid);
        	var ids = $("#dataTable_jcszxx").jqGrid("getCol","worktype1name",true);
        	var i = index-1;
        	for(;i>0;i--){
        		if(ids[index-1].value != ids[i].value){
        			break;
        		}
        	}
        	$("#dataTable_jcszxx tbody tr[id=\""+(i>0?ids[i+1].id:1)+"\"] td[id=\"worktype1name"+(i>0?ids[i+1].id:1)+"\"]").css({"background":"white"});//#198EDE white */
 	      	$("#dataTable_jcszxx tbody tr td[id^=worktype1name]").css({"background":"white","color":"#333"});
        	$("#dataTable_jcszxx tbody tr #worktype1name"+rowid).css({"background":"#198EDE","color":"white"});
        	
        	//update by zzr 2017/12/19 10:18 采用模拟合并单元格方式,修改选中样式 end
			        	
        }
	});
	
	$("#jcszxxUl li input").css({"width":"110px"});
	$("#jcszxxUl li").css({"padding-left":"0px"});
	$("#jcszxxUl li label").css({"margin-left":"0px","width":"90px","margin-right":"0px"});
	$("#jcszxxUl li textarea").css({"width":"415px"});
	
});
function goto_query(tableId,postData,url){
	var options = {url:url,postData:postData,page:1};
	$("#"+tableId).jqGrid("setGridParam",options).trigger("reloadGrid");
}
</script>

<table id="dataTable_jcszxx"></table>

<div style="width:100%;height:20px;background:#EEEEEE">项目经理结算信息</div>

<div class="panel-info" >  
	<div class="panel-body">
		<form action="" method="post" id="dataForm"  class="form-search">
			<ul id="jcszxxUl" class="ul-form">
				<li>
					<label>客户结算状态</label>
					<input type="text" value="${customer.checkStatusName }" />
				</li>
				<li>
					<label>发包总价</label>
					<input type="text" value="${customer.baseCtrlAmt }" />
				</li>
				<li>
					<label>支出</label>
					<input type="text" value="${customer.cost }" />
				</li>	
				<li>
					<label>预扣</label>
					<input type="text"  value="${customer.withHold }" />
				</li>
				<li>
					<label>主材配合费</label>
					<input type="text" value="${customer.mainCoopFee }" />
				</li>
				<li>
					<label>已领</label>
					<input type="text"  value="${customer.recvFee }" />
				</li>
				<li>
					<label>已领定责</label>
					<input type="text"  value="${customer.recvFeeFixDuty}" /> 
				</li>
				<li>
					<label>质保金</label>
					<input type="text" value="${customer.qualityFee }" />
				</li>
				<li>
					<label>意外险</label>
					<input type="text" value="${customer.accidentFee }" />
				</li>	
				<li>
					<label>应发金额</label>
					<input type="text" value="${customer.mustAmount }" />
				</li>
				<li>
					<label>实发金额</label>
					<input type="text" value="${customer.realAmount }" />
				</li>
				<li>
					<label>是否发放</label>
					<input type="text" value="${customer.isProvide }" />
				</li>
				<li>
					<label>发放编号</label>
					<input type="text" value="${customer.provideNo }" />
				</li>	
				<li>
					<label>发包补贴</label>
					<input type="text" value="${customer.projectCtrlAdj }" />
				</li>
				<li>
					<label class="control-textarea">备注</label>
					<textarea>${customer.prjRemarks }</textarea>
				</li>
				<li>
					<label class="control-textarea">补贴说明</label>
					<textarea>${customer.ctrlAdjRemark }</textarea>
				</li>
			</ul>
		</form>
	</div>
</div>
