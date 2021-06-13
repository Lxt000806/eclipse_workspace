<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

</script>
<div class="body-box-list">
	<div class="container-fluid" id="id_khhtxx">
	    <ul class="nav nav-tabs" style="padding-top: 10px;">  
	        <li class="active"><a href="#tab_khhtxx_htxx" data-toggle="tab">合同信息</a></li>
	        <li class=""><a href="#tab_khhtxx_cqht" data-toggle="tab">重签合同</a></li>
	        <li class=""><a href="#tab_khhtxx_zsxm" data-toggle="tab">赠送项目</a></li>
	        <li class=""><a href="#tab_khhtxx_discToken" data-toggle="tab">优惠券</a></li>
	    </ul>  
	    <div class="tab-content">  
	        <div id="tab_khhtxx_htxx" class="tab-pane fade in active"> 
	         	<jsp:include page="tab_khhtxx_htxx.jsp"></jsp:include>
	        </div>  
	        <div id="tab_khhtxx_cqht" class="tab-pane fade"> 
	         	<jsp:include page="tab_khhtxx_cqht.jsp"></jsp:include>
	        </div>
	        <div id="tab_khhtxx_zsxm" class="tab-pane fade"> 
	         	<jsp:include page="tab_khhtxx_zsxm.jsp"></jsp:include>
	        </div>
	        <div id="tab_khhtxx_discToken" class="tab-pane fade"> 
	         	<jsp:include page="tab_khhtxx_discToken.jsp"></jsp:include>
	        </div>
	    </div>  
	</div>
</div>
