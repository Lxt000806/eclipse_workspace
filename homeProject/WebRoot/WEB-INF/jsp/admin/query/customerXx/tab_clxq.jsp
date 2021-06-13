<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

</script>
<div class="body-box-list">
	<div class="container-fluid" id="id_clxq">  
	    <ul class="nav nav-tabs" style="padding-top: 10px;">  
	        <li class="active"><a href="#tab_clxq_zcxq" data-toggle="tab">主材需求</a></li>
	        <li class=""><a href="#tab_clxq_fwxq" data-toggle="tab">服务性产品需求</a></li>
	        <li class=""><a href="#tab_clxq_rzxq" data-toggle="tab">软装需求</a></li>
	        <li class=""><a href="#tab_clxq_jcxq" data-toggle="tab">集成需求</a></li>
	    </ul>  
	    <div class="tab-content">  
	        <div id="tab_clxq_zcxq" class="tab-pane fade in active"> 
	         	<jsp:include page="tab_clxq_zcxq.jsp">
	         		<jsp:param name="itemType1" value="ZC" />
	         		<jsp:param name="isService" value="0" />
	         	</jsp:include>
	        </div>  
	        <div id="tab_clxq_fwxq" class="tab-pane fade"> 
	         	<jsp:include page="tab_clxq_zcxq.jsp">
	         		<jsp:param name="itemType1" value="ZC" />
	         		<jsp:param name="isService" value="1" />
	         	</jsp:include>
	        </div>
	        <div id="tab_clxq_rzxq" class="tab-pane fade"> 
	         	<jsp:include page="tab_clxq_zcxq.jsp">
	         		<jsp:param name="itemType1" value="RZ" />
	         		<jsp:param name="isService" value="0" />
	         	</jsp:include>
	        </div>  
	        <div id="tab_clxq_jcxq" class="tab-pane fade"> 
	         	<jsp:include page="tab_clxq_zcxq.jsp">
	         		<jsp:param name="itemType1" value="JC" />
	         		<jsp:param name="isService" value="0" />
	         	</jsp:include>
	        </div>
	    </div>  
	</div>
</div>
