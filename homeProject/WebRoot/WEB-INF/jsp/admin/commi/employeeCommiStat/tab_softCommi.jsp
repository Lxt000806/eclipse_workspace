<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

</script>
<div class="body-box-list">
	<div class="container-fluid" id="id_rz">
	    <ul class="nav nav-tabs" style="padding-top: 10px;">  
	        <li class="active"><a href="#tab_softBusiCommi" data-toggle="tab" >软装提成</a></li>
	        <li class=""><a href="#tab_softIndCommi" data-toggle="tab" >新软装独立销售提成</a></li>
	    	<li class=""><a href="#tab_softOldCommi" data-toggle="tab" >旧软装独立销售提成</a></li>
	    </ul>  
	    <div class="tab-content">  
	        <div id="tab_softBusiCommi" class="tab-pane fade in active" > 
	         	<jsp:include page="tab_softBusiCommi.jsp"></jsp:include>
	        </div>  
	        <div id="tab_softIndCommi" class="tab-pane fade" > 
	         	<jsp:include page="tab_softIndCommi.jsp"></jsp:include>
	        </div>
	        <div id="tab_softOldCommi" class="tab-pane fade" > 
	         	<jsp:include page="tab_softOldCommi.jsp"></jsp:include>
	        </div>
	    </div>  
	</div>
</div>
