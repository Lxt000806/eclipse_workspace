<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

</script>
<div class="body-box-list">
	<div class="container-fluid" id="id_zc">
	    <ul class="nav nav-tabs" style="padding-top: 10px;">  
	        <li class="active"><a href="#tab_zctc_tc" data-toggle="tab" onclick="setExcelTable('dataTable_zctc','主材提成','mainBusiCommi/doExcel')">主材提成</a></li>
	        <li class=""><a href="#tab_zctc_dlxstc" data-toggle="tab" onclick="setExcelTable('dataTable_zcdlxstc','主材独立销售提成','mainBusiCommi/doIndExcel')">主材独立销售提成</a></li>
	    </ul>  
	    <div class="tab-content">  
	        <div id="tab_zctc_tc" class="tab-pane fade in active" > 
	         	<jsp:include page="tab_zctc_tc.jsp"></jsp:include>
	        </div>  
	        <div id="tab_zctc_dlxstc" class="tab-pane fade" > 
	         	<jsp:include page="tab_zctc_dlxstc.jsp"></jsp:include>
	        </div>
	    </div>  
	</div>
</div>
