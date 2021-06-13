<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
function viewDhmx(){
	var tableId = 'dataTable_cgd_JZ';
	if ($("#id_cgd>ul li:eq(0)").hasClass("active")==true){
		tableId = 'dataTable_cgd_JZ';
	}else if($("#id_cgd>ul li:eq(1)").hasClass("active")==true){
		tableId = 'dataTable_cgd_ZC';
	}else if($("#id_cgd>ul li:eq(2)").hasClass("active")==true){
		tableId = 'dataTable_cgd_RZ';
	}else if($("#id_cgd>ul li:eq(3)").hasClass("active")==true){
		tableId = 'dataTable_cgd_JC';
	}
	var ret = selectDataTableRow(tableId);
    if (ret) {
      Global.Dialog.showDialog("purchArr_dhmx",{
		  title:"到货明细",
		  url:"${ctx}/admin/purchArr/goDhmx?puno="+ret.no,
		  height:600,
		  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
</script>
<div class="body-box-list">
	<div class="container-fluid" id="id_cgd">  
	    <ul class="nav nav-tabs" style="padding-top: 10px;"> 
	    	<li class="active"><a href="#tab_cgd_jzcgd" data-toggle="tab">基础采购单</a></li> 
	        <li class=""><a href="#tab_cgd_zccgd" data-toggle="tab">主材采购单</a></li>
	        <li class=""><a href="#tab_cgd_rzcgd" data-toggle="tab">软装采购单</a></li>
	        <li class=""><a href="#tab_cgd_jccgd" data-toggle="tab">集成采购单</a></li>
	        <a href="javascript:void(0)" onclick="viewDhmx()" style="position: relative;left: 10px;top: 4px;font-size: 13px;">【到货明细】</a>
	    </ul>  
	    <div class="tab-content"> 
	    	<div id="tab_cgd_jzcgd" class="tab-pane fade in active"> 
	         	<jsp:include page="tab_cgd_jzcgd.jsp">
	         		<jsp:param name="title" value="基础"/>
	         		<jsp:param name="itemType1" value="JZ" />
	         	</jsp:include>
	        </div> 
	        <div id="tab_cgd_zccgd" class="tab-pane fade"> 
	         	<jsp:include page="tab_cgd_jzcgd.jsp">
	         		<jsp:param name="title" value="主材"/>
	         		<jsp:param name="itemType1" value="ZC" />
	         	</jsp:include>
	        </div>  
	        <div id="tab_cgd_rzcgd" class="tab-pane fade"> 
	         	<jsp:include page="tab_cgd_jzcgd.jsp">
	         		<jsp:param name="title" value="软装"/>
	         		<jsp:param name="itemType1" value="RZ" />
	         	</jsp:include>
	        </div>
	        <div id="tab_cgd_jccgd" class="tab-pane fade"> 
	         	<jsp:include page="tab_cgd_jzcgd.jsp">
	         		<jsp:param name="title" value="集成"/>
	         		<jsp:param name="itemType1" value="JC" />
	         	</jsp:include>
	        </div>  
	    </div>  
	</div>
</div>
