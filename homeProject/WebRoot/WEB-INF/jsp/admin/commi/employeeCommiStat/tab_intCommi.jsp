<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

</script>
<div class="body-box-list">
	<div class="container-fluid" id="id_jc">
	    <ul class="nav nav-tabs" style="padding-top: 10px;">  
	        <li class="active"><a href="#tab_intBusiCommi" data-toggle="tab">集成提成</a></li>
	        <li class=""><a href="#tab_intIndCommi" data-toggle="tab">集成独立销售提成</a></li>
	    </ul>  
	    <div class="tab-content">  
	        <div id="tab_intBusiCommi" class="tab-pane fade in active" > 
	         	<jsp:include page="tab_intBusiCommi.jsp"></jsp:include>
	        </div>  
	        <div id="tab_intIndCommi" class="tab-pane fade" > 
	         	<jsp:include page="tab_intIndCommi.jsp"></jsp:include>
	        </div>
	    </div>  
	</div>
</div>
