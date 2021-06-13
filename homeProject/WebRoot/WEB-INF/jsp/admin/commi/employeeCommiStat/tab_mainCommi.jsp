<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

</script>
<div class="body-box-list">
	<div class="container-fluid" id="id_zc">
	    <ul class="nav nav-tabs" style="padding-top: 10px;">  
	        <li class="active"><a href="#tab_mainBusiCommi" data-toggle="tab" >主材提成</a></li>
	        <li class=""><a href="#tab_mainIndCommi" data-toggle="tab">主材独立销售提成</a></li>
	    </ul>  
	    <div class="tab-content">  
	        <div id="tab_mainBusiCommi" class="tab-pane fade in active" > 
	         	<jsp:include page="tab_mainBusiCommi.jsp"></jsp:include>
	        </div>  
	        <div id="tab_mainIndCommi" class="tab-pane fade" > 
	         	<jsp:include page="tab_mainIndCommi.jsp"></jsp:include>
	        </div>
	    </div>  
	</div>
</div>
