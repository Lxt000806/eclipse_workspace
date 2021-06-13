<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

</script>
<div class="body-box-list">
	<div class="container-fluid" id="id_clys">  
	    <ul class="nav nav-tabs" style="padding-top: 10px;">  
	        <li class="active"><a href="#tab_clys_zcys" data-toggle="tab">主材预算</a></li>
	        <li class=""><a href="#tab_clys_fwys" data-toggle="tab">服务性产品预算</a></li>
	        <li class=""><a href="#tab_clys_rzys" data-toggle="tab">软装预算</a></li>
	        <li class=""><a href="#tab_clys_jcys" data-toggle="tab">集成预算</a></li>
	    </ul>  
	    <div class="tab-content">  
	        <div id="tab_clys_zcys" class="tab-pane fade in active"> 
	         	<jsp:include page="tab_clys_zcys.jsp">
	         		<jsp:param name="itemType1" value="ZC" />
	         		<jsp:param name="isService" value="0"/>
	         	</jsp:include>
	        </div>  
	        <div id="tab_clys_fwys" class="tab-pane fade"> 
	         	<jsp:include page="tab_clys_zcys.jsp">
	         		<jsp:param name="itemType1" value="ZC" />
	         		<jsp:param name="isService" value="1"/>
	         	</jsp:include>
	        </div>
	        <div id="tab_clys_rzys" class="tab-pane fade"> 
	         	<jsp:include page="tab_clys_zcys.jsp">
	         		<jsp:param name="itemType1" value="RZ" />
	         		<jsp:param name="isService" value="0"/>
	         	</jsp:include>
	        </div>  
	        <div id="tab_clys_jcys" class="tab-pane fade"> 
	         	<jsp:include page="tab_clys_zcys.jsp">
	         		<jsp:param name="itemType1" value="JC" />
	         		<jsp:param name="isService" value="0"/>
	         	</jsp:include>
	        </div>
	    </div>  
	</div>
</div>
