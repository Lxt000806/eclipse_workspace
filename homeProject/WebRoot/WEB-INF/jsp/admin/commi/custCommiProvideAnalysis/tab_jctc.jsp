<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

</script>
<div class="body-box-list">
	<div class="container-fluid" id="id_jc">
	    <ul class="nav nav-tabs" style="padding-top: 10px;">  
	        <li class="active"><a href="#tab_jctc_tc" data-toggle="tab" onclick="setExcelTable('dataTable_jctc','集成提成','intBusiCommi/doExcel')">集成提成</a></li>
	        <li class=""><a href="#tab_jctc_dlxstc" data-toggle="tab" onclick="setExcelTable('dataTable_jcdlxstc','集成独立销售提成','intBusiCommi/doIndExcel')">集成独立销售提成</a></li>
	    </ul>  
	    <div class="tab-content">  
	        <div id="tab_jctc_tc" class="tab-pane fade in active" > 
	         	<jsp:include page="tab_jctc_tc.jsp"></jsp:include>
	        </div>  
	        <div id="tab_jctc_dlxstc" class="tab-pane fade" > 
	         	<jsp:include page="tab_jctc_dlxstc.jsp"></jsp:include>
	        </div>
	    </div>  
	</div>
</div>
