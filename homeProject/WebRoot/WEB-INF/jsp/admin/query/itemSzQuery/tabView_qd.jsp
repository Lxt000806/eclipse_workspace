<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<script>
	function shclmxGrid(){
		$("#dataTable_shclmx").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goShclmxJqGrid",postData:{custCode:$("#code").val()},page:1}).trigger("reloadGrid");
	}
</script>
<div class="container-fluid" style="margin:5px">  
	<ul id="qdUl" class="nav nav-tabs" >
	    <li><a id="a_qd_jcszxx" href="#tabView_qd_jcszxx" data-toggle="tab">基础收支信息</a></li>  
	    <li><a id="a_qd_yshfbdemx" href="#tabView_qd_yshfbdemx" data-toggle="tab">预算和发包定额明细</a></li>  
	    <li><a id="a_qd_zjhfbdemx" href="#tabView_qd_zjhfbdemx" data-toggle="tab">增减和发包定额明细</a></li>  
	    <li><a id="a_qd_zfbdemx" href="#tabView_qd_zfbdemx" data-toggle="tab">总发包定额明细</a></li>  
	    <li><a id="a_qd_clcbmx" href="#tabView_qd_clcbmx" data-toggle="tab">材料成本明细</a></li>  
	    <li><a id="a_qd_rgcbmx" href="#tabView_qd_rgcbmx" data-toggle="tab">人工成本明细</a></li> 
	    <li><a id="a_qd_rgfymx" href="#tabView_qd_rgfymx" data-toggle="tab">人工费用明细</a></li>  
	    <li><a id="a_qd_khfkmx" href="#tabView_qd_khfkmx" data-toggle="tab">客户付款明细</a></li>  
	    <li><a id="a_qd_yxxmjljl" href="#tabView_qd_yxxmjljl" data-toggle="tab">优秀项目经理奖励</a></li>  
	    <li><a id="a_qd_ykmx" href="#tabView_qd_ykmx" data-toggle="tab">预扣明细</a></li>  
	    <li><a id="a_qd_waterContractQuota" href="#tabView_qd_waterContractQuota" data-toggle="tab">成本定额明细</a></li>
	    <li><a id="a_qd_sgbt" href="#tabView_qd_sgbt" data-toggle="tab">施工补贴</a></li>
	    <li><a id="a_qd_shclmx" href="#tabView_qd_shclmx" data-toggle="tab" onclick="shclmxGrid()">售后材料明细</a></li>  
	    <li><a id="a_qd_shrgmx" href="#tabView_qd_shrgmx" data-toggle="tab">售后人工明细</a></li>   
	</ul>  
    <div class="tab-content">  
		<div id="tabView_qd_jcszxx"  class="tab-pane fade">
			<jsp:include page="tabView_qd_jcszxx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_qd_yshfbdemx"  class="tab-pane fade"> 
			<jsp:include page="tabView_qd_yshfbdemx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_qd_zjhfbdemx"  class="tab-pane fade"> 
			<jsp:include page="tabView_qd_zjhfbdemx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_qd_zfbdemx"  class="tab-pane fade"> 
			<jsp:include page="tabView_qd_zfbdemx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_qd_clcbmx"  class="tab-pane fade"> 
			<jsp:include page="tabView_qd_clcbmx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_qd_rgcbmx"  class="tab-pane fade"> 
			<jsp:include page="tabView_qd_rgcbmx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_qd_shclmx"  class="tab-pane fade"> 
			<jsp:include page="tabView_qd_shclmx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_qd_shrgmx"  class="tab-pane fade"> 
			<jsp:include page="tabView_qd_shrgmx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_qd_rgfymx"  class="tab-pane fade"> 
			<jsp:include page="tabView_qd_rgfymx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_qd_khfkmx"  class="tab-pane fade"> 
			<jsp:include page="tabView_qd_khfkmx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_qd_yxxmjljl"  class="tab-pane fade"> 
			<jsp:include page="tabView_qd_yxxmjljl.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_qd_ykmx"  class="tab-pane fade"> 
			<jsp:include page="tabView_qd_ykmx.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div> 
		<div id="tabView_qd_waterContractQuota"  class="tab-pane fade"> 
			<jsp:include page="tabView_qd_waterContractQuota.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div>
		<div id="tabView_qd_sgbt"  class="tab-pane fade"> 
			<jsp:include page="tabView_qd_sgbt.jsp">
				<jsp:param value="" name=""/>
			</jsp:include>
		</div>
	</div>	
</div>	
