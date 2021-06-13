<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

</script>
<div class="body-box-list">
	<div class="container-fluid" id="id_gxr">  
	    <ul class="nav nav-tabs" style="padding-top: 10px;">  
	        <li class="active"><a href="#tab_gxr_dqgxr" data-toggle="tab">当前干系人</a></li>
	        <li class=""><a href="#tab_gxr_xgls" data-toggle="tab">修改历史</a></li>
	    </ul>  
	    <div class="tab-content">  
	        <div id="tab_gxr_dqgxr" class="tab-pane fade in active"> 
	         	<jsp:include page="tab_gxr_dqgxr.jsp"></jsp:include>
	        </div>  
	        <div id="tab_gxr_xgls" class="tab-pane fade"> 
	         	<jsp:include page="tab_gxr_xgls.jsp"></jsp:include>
	        </div>
	    </div>  
	</div>
</div>
