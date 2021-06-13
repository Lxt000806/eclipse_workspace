<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
//tab分页
$(function(){
	
});
</script>
<div class="body-box-list">
	<div class="container-fluid" id="id_jzzj">
	    <ul class="nav nav-tabs" style="padding-top: 10px;">
	        <li class="active"><a href="#tab_jzzj_jzxmzj" data-toggle="tab">基础项目增减</a></li>
	        <li class=""><a href="#tab_jzzj_htfyzj" data-toggle="tab">合同费用增减</a></li>
	    </ul>
	    <div class="tab-content">
    		<div id="tab_jzzj_jzxmzj" class="tab-pane fade in active"> 
	         	<jsp:include page="tab_jzzj_jzxmzj.jsp"></jsp:include>
	        </div>
	        <div id="tab_jzzj_htfyzj" class="tab-pane fade"> 
	         	<jsp:include page="tab_jzzj_htfyzj.jsp"></jsp:include>
	        </div>
	    </div>
	 </div>
</div>
