<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

</script>
<div class="body-box-list">
	<div class="container-fluid" id="id_rz">
	    <ul class="nav nav-tabs" style="padding-top: 10px;">  
	        <li class="active"><a href="#tab_rztc_tc" data-toggle="tab" onclick="setExcelTable('dataTable_rztc','软装提成')">软装提成</a></li>
	        <li class=""><a href="#tab_rztc_dlxstc" data-toggle="tab" onclick="setExcelTable('dataTable_rzdlxstc','软装独立销售提成')">软装独立销售提成</a></li>
	    </ul>  
	    <div class="tab-content">  
	        <div id="tab_rztc_tc" class="tab-pane fade in active" > 
	         	<jsp:include page="tab_rztc_tc.jsp"></jsp:include>
	        </div>  
	        <div id="tab_rztc_dlxstc" class="tab-pane fade" > 
	         	<jsp:include page="tab_rztc_dlxstc.jsp"></jsp:include>
	        </div>
	    </div>  
	</div>
</div>
