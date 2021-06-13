<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript"> 

$(function(){
	$("#recalPerfbusiness_span").popover({trigger:"hover"});
});

</script>

<div class="body-box-list" style="margin-top: 0;">
    <div class="panel-body">
         <ul class="ul-form">
			<li>
				<label>业绩金额</label>
				<input type="text" id="recalPerf" name="recalPerf"  value="${perfEstimateMap.RecalPerf}" readonly="readonly" />
				<span class="glyphicon glyphicon-question-sign" id="recalPerfbusiness_span" 
					data-container="body"  
					data-content="${perfEstimateMap.PerfExprRemarks}【${perfEstimateMap.PerfExprWithNum}】"  
					data-placement="bottom" trigger="hover"
					style="font-size: 16px;color:rgb(25,142,222);"></span>
			</li>
			
			<li>
			    <label style="width: 78px;">提成点</label>
				<input type="text" id="businessManCommiPer" name="businessManCommiPer"  value="${perfEstimateMap.BusinessManCommiPer}" readonly="readonly" />
			</li>
			<li>
			    <label>补贴点</label>
				<input type="text" id="businessManSubsidyPer" name="businessManSubsidyPer"  value="${perfEstimateMap.BusinessManSubsidyPer}" readonly="readonly" />
			</li>
			<li>
				<label>业绩提成金额</label>
				<input type="text" id="businessManCommi" name="businessManCommi"  value="${perfEstimateMap.BusinessManCommi}" readonly="readonly" />
			</li>
			<li>
				<label>右边销售基数</label>
				<input type="text" id="businessManRightCardinal" name="businessManRightCardinal"  value="${perfEstimateMap.BusinessManRightCardinal}" readonly="readonly" />
			</li>
			<li>
				<label>右边提成点</label>
				<input type="text" id="businessManRightCommiPer" name="businessManRightCommiPer"  value="${perfEstimateMap.BusinessManRightCommiPer}" readonly="readonly" />
			</li>
			<li>
				<label>设计费提成</label>
				<input type="text" id="businessManDesignFeeCommi" name="businessManDesignFeeCommi"  value="${perfEstimateMap.BusinessManDesignFeeCommi}" readonly="readonly" />
			</li>
			
			<li>
				<label>设计费</label>
				<input type="text" id="designFee" name="designFee"  value="${perfEstimateMap.DesignFee}" readonly="readonly" />
			</li>
			<li>
				<label>标准绘图费</label>
				<input type="text" id="stdDrawFee" name="stdDrawFee"  value="${perfEstimateMap.StdDrawFee}" readonly="readonly" />
			</li>
			<li>
				<label>标准效果图</label>
				<input type="text" id="stdColorDrawFee" name="stdColorDrawFee"  value="${perfEstimateMap.StdColorDrawFee}" readonly="readonly" />
			</li>
			 
	   </ul>
    </div>
</div>
