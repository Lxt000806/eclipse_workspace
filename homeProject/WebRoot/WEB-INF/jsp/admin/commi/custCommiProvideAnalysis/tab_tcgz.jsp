<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

</script>
<div class="body-box-list">
	<div class="container-fluid" id="id_tcgz">
	    <ul class="nav nav-tabs" style="padding-top: 10px;">  
	        <li class="active"><a href="#tab_tcgz_lpgxrtcgz" data-toggle="tab" onclick="setExcelTable('dataTable_tcgz_lpgxrtcgz','楼盘干系人提成规则')">楼盘干系人提成规则</a></li>
	        <li class=""><a href="#tab_tcgz_ffbl" data-toggle="tab" onclick="setExcelTable('dataTable_tcgz_ffbl','发放比例')">发放比例</a></li>
	         <li class=""><a href="#tab_tcgz_tcjd" data-toggle="tab" onclick="setExcelTable('dataTable_tcgz_tcjd','提成节点')">提成节点</a></li>
	    </ul>  
	    <div class="tab-content">  
	        <div id="tab_tcgz_lpgxrtcgz" class="tab-pane fade in active" > 
	         	<jsp:include page="tab_tcgz_lpgxrtcgz.jsp"></jsp:include>
	        </div>
	        <div id="tab_tcgz_ffbl" class="tab-pane fade" > 
	         	<jsp:include page="tab_tcgz_ffbl.jsp"></jsp:include>
	        </div>
	        <div id="tab_tcgz_tcjd" class="tab-pane fade" > 
	         	<jsp:include page="tab_tcgz_tcjd.jsp"></jsp:include>
	        </div>
	    </div>  
	</div>
</div>
