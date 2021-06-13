<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script>
	function check(itemType1){
		var ids = [];
		if(itemType1 == "ZC"){
			ids = $("#dataTable_tcnzccbmx").jqGrid("getDataIDs");
		}else if(itemType1 == "JC"){
			ids = $("#dataTable_tcnjccbmx").jqGrid("getDataIDs");
		}
		if(ids.length > 0){
			var ret = {};
			if(itemType1=="ZC"){
				ret = $("#dataTable_tcnzccbmx").jqGrid("getRowData",ids[0]);
				$("#dataTable_llmx_tcnzccbmx").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goLlmxJqGrid",postData:{no:ret.no.trim(),custCode:$("#code").val()},page:1}).trigger("reloadGrid");
			}else if(itemType1=="JC"){
				ret = $("#dataTable_tcnjccbmx").jqGrid("getRowData",ids[0]);
				$("#dataTable_llmx_tcnjccbmx").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goLlmxJqGrid",postData:{no:ret.no.trim(),custCode:$("#code").val()},page:1}).trigger("reloadGrid");
			}
		}
	}
	$(function(){
		if("${customer.type}"=="1"){
			$("#a_tc_tcnzcjc").parent().addClass("hidden");
		}else{
			$("#a_tc_tcnzcjc").parent().removeClass("hidden");
		}
	});
	function shclmxtcGrid(){
		$("#dataTable_shclmx_tc").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goShclmxJqGrid",postData:{custCode:$("#code").val()},page:1}).trigger("reloadGrid");
	}
</script>
<div  class="container-fluid" style="margin:5px">  
	<ul id="tcUl" class="nav nav-tabs" >
	    <li><a id="a_tc_jccbzcxx" href="#tabView_tc_jccbzcxx" data-toggle="tab">基础成本支出信息</a></li>  
<!-- 	    <li><a id="a_tc_szhz" href="#tabView_tc_tc_szhz" data-toggle="tab">收支汇总</a></li>   -->
	    <li><a id="a_tc_jcbg" href="#tabView_tc_jcbg" data-toggle="tab">基础变更</a></li>  
	    <li><a id="a_tc_jcclcbmx" href="#tabView_tc_jcclcbmx" data-toggle="tab">基础材料成本明细</a></li>  
	    <li><a id="a_tc_tcnzccbmx" href="#tabView_tc_tcnzccbmx" data-toggle="tab" onclick="check('ZC')">套餐内主材成本明细</a></li>  
	    <li><a id="a_tc_tcnjccbmx" href="#tabView_tc_tcnjccbmx" data-toggle="tab" onclick="check('JC')">套餐内集成成本明细</a></li>  
	    <li><a id="a_tc_rgcbmx" href="#tabView_tc_rgcbmx" data-toggle="tab">人工成本明细</a></li>  
	    <li><a id="a_tc_rgfymx" href="#tabView_tc_rgfymx" data-toggle="tab">人工费用明细</a></li>
	    <li><a id="a_tc_khfkmx" href="#tabView_tc_khfkmx" data-toggle="tab">客户付款明细</a></li>  
	    <li><a id="a_tc_ykmx" href="#tabView_tc_ykmx" data-toggle="tab">预扣明细</a></li>   
	    <li><a id="a_tc_jcxq" href="#tabView_tc_jcxq" data-toggle="tab">基础需求</a></li> 
	    <li><a id="a_tc_waterContractQuota" href="#tabView_tc_waterContractQuota" data-toggle="tab">成本定额明细</a></li>
	    <li><a id="a_tc_sgbt" href="#tabView_tc_sgbt" data-toggle="tab">施工补贴</a></li>
	    <li><a id="a_tc_tcnzcjc" href="#tabView_tc_tcnzcjc" data-toggle="tab">套餐内主材奖惩</a></li>
	    <li><a id="a_tc_shclmx" href="#tabView_tc_shclmx" data-toggle="tab" onclick="shclmxtcGrid()">售后材料明细</a></li>  
	    <li><a id="a_tc_shrgmx" href="#tabView_tc_shrgmx" data-toggle="tab">售后人工明细</a></li>   
	</ul>  
    <div class="tab-content" style="height:473px">  
		<div id="tabView_tc_jccbzcxx"  class="tab-pane fade">
			<jsp:include page="tabView_tc_jccbzcxx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
<%-- 		<div id="tabView_tc_tc_szhz"  class="tab-pane fade"> 
			<jsp:include page="tabView_tc_szhz.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div>  --%>
		<div id="tabView_tc_jcbg"  class="tab-pane fade"> 
			<jsp:include page="tabView_tc_jcbg.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_tc_jcclcbmx"  class="tab-pane fade"> 
			<jsp:include page="tabView_tc_jcclcbmx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_tc_tcnzccbmx"  class="tab-pane fade"> 
			<jsp:include page="tabView_tc_tcnzccbmx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_tc_tcnjccbmx"  class="tab-pane fade"> 
			<jsp:include page="tabView_tc_tcnjccbmx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_tc_rgcbmx"  class="tab-pane fade"> 
			<jsp:include page="tabView_tc_rgcbmx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_tc_rgfymx"  class="tab-pane fade"> 
			<jsp:include page="tabView_tc_rgfymx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_tc_shclmx"  class="tab-pane fade"> 
			<jsp:include page="tabView_tc_shclmx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_tc_shrgmx"  class="tab-pane fade"> 
			<jsp:include page="tabView_tc_shrgmx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_tc_khfkmx"  class="tab-pane fade"> 
			<jsp:include page="tabView_tc_khfkmx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_tc_ykmx"  class="tab-pane fade"> 
			<jsp:include page="tabView_tc_ykmx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_tc_jcxq"  class="tab-pane fade"> 
			<jsp:include page="tabView_tc_jcxq.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
			
		</div> 
		<div id="tabView_tc_waterContractQuota"  class="tab-pane fade"> 
			<jsp:include page="tabView_tc_waterContractQuota.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div>
		<div id="tabView_tc_tcnzcjc"  class="tab-pane fade"> 
			<jsp:include page="tabView_tc_tcnzcjc.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div>
		<div id="tabView_tc_sgbt"  class="tab-pane fade"> 
			<jsp:include page="tabView_tc_sgbt.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div>
	</div>	
</div>	
