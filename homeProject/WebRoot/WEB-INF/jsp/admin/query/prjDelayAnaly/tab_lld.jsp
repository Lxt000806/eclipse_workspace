<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
function viewFhmx(){
	if ($("#id_lld>ul li:first").hasClass("active")==false){
		return;
	}
	var ret = selectDataTableRow("dataTable_lld_ZC");
    if (ret) {
      Global.Dialog.showDialog("itemAppSend_fhmx",{
		  title:"分批发货明细",
		  url:"${ctx}/admin/itemAppSend/goFhmx?iaNo="+ret.no,
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
	<div class="container-fluid" id="id_lld">  
	    <ul class="nav nav-tabs" style="padding-top: 10px;">  
	        <li class="active"><a href="#tab_lld_zcll" data-toggle="tab">主材领料</a></li>
	        <li class=""><a href="#tab_lld_rzll" data-toggle="tab">软装领料</a></li>
	        <li class=""><a href="#tab_lld_jcll" data-toggle="tab">集成领料</a></li>
	        <li class=""><a href="#tab_lld_jzll" data-toggle="tab">基础领料</a></li>
	        <a href="javascript:void(0)" onclick="viewFhmx()" style="position: relative;left: 10px;top: 4px;font-size: 13px;">【发货明细】</a>
	    </ul>  
	    <div class="tab-content">  
	        <div id="tab_lld_zcll" class="tab-pane fade in active"> 
	         	<jsp:include page="tab_lld_zcll.jsp">
	         		<jsp:param name="title" value="主材"/>
	         		<jsp:param name="itemType1" value="ZC" />
	         	</jsp:include>
	        </div>  
	        <div id="tab_lld_rzll" class="tab-pane fade"> 
	         	<jsp:include page="tab_lld_zcll.jsp">
	         		<jsp:param name="title" value="软装"/>
	         		<jsp:param name="itemType1" value="RZ" />
	         	</jsp:include>
	        </div>
	        <div id="tab_lld_jcll" class="tab-pane fade"> 
	         	<jsp:include page="tab_lld_zcll.jsp">
	         		<jsp:param name="title" value="集成"/>
	         		<jsp:param name="itemType1" value="JC" />
	         	</jsp:include>
	        </div>  
	        <div id="tab_lld_jzll" class="tab-pane fade"> 
	         	<jsp:include page="tab_lld_zcll.jsp">
	         		<jsp:param name="title" value="基础"/>
	         		<jsp:param name="itemType1" value="JZ" />
	         	</jsp:include>
	        </div>
	    </div>  
	</div>
</div>
