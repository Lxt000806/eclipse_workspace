<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>客户查询详细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<link href="${resourceRoot}/bootstrap/css/change.css" rel="stylesheet" />
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
//tab分页
/*
$(function(){
    $("#id_detail>ul.nav li").click(function() {
        var activeTab = $(this).find("a").attr("href"); 
        if(activeTab=='#tab_khhtxx'){
        	$("#id_khhtxx").find("ul li").removeClass("active");
        	$("#id_khhtxx").find("ul li:first").addClass("active");
        }
        else if(activeTab=='#tab_gxr'){
			$("#id_gxr").find("ul li").removeClass("active");
        	$("#id_gxr").find("ul li:first").addClass("active");
        }
        else if(activeTab=='#tab_clys'){
			$("#id_clys").find("ul li").removeClass("active");
			$("#id_clys").find("ul li:first").addClass("active");
		}
        else if(activeTab=='#tab_clxq'){
			$("#id_clxq").find("ul li").removeClass("active");
			$("#id_clxq").find("ul li:first").addClass("active");
		}
        else if(activeTab=='#tab_jzzj'){
        	$("#id_jzzj").find("ul li").removeClass("active");
        	$("#id_jzzj").find("ul li:first").addClass("active");
        	var tableId = 'dataTable_jzzj_jzxmzj';
        	var ids = $("#"+tableId).jqGrid('getDataIDs');
   	    	$("#"+tableId).jqGrid("setSelection",ids[0],true);
        }
        else if(activeTab=='#tab_clzj'){
			$("#id_clzj").find("ul li").removeClass("active");
        	$("#id_clzj").find("ul li:first").addClass("active");
        	var tableId = 'dataTable_clzj_ZC_0';
        	var ids = $("#"+tableId).jqGrid('getDataIDs');
   	    	$("#"+tableId).jqGrid("setSelection",ids[0],true);
        }
        else if(activeTab=='#tab_lld'){
        	alert(activeTab);
			$("#id_lld").find("ul li").removeClass("active");
        	$("#id_lld").find("ul li:first").addClass("active");
        	var tableId = 'dataTable_lld_ZC';
        	var ids = $("#"+tableId).jqGrid('getDataIDs');
   	    	$("#"+tableId).jqGrid("setSelection",ids[0],true);
        }
        else if(activeTab=='#tab_tcmx'){
			$("#id_tcmx").find("ul li").removeClass("active");
        	$("#id_tcmx").find("ul li:first").addClass("active");
        	var tableId = 'dataTable_tcmx_main';
        	var ids = $("#"+tableId).jqGrid('getDataIDs');
   	    	$("#"+tableId).jqGrid("setSelection",ids[0],true);
        }
        else if(activeTab=='#tab_cgd'){
			$("#id_cgd").find("ul li").removeClass("active");
        	$("#id_cgd").find("ul li:first").addClass("active");
        	var tableId = 'dataTable_cgd_JZ';
        	var ids = $("#"+tableId).jqGrid('getDataIDs');
   	    	$("#"+tableId).jqGrid("setSelection",ids[0],true);
        }
        else if(activeTab=='#tab_dhfx'){
        	var tableId = 'dataTable_dhfx_main';
        	var ids = $("#"+tableId).jqGrid('getDataIDs');
   	    	$("#"+tableId).jqGrid("setSelection",ids[0],true);
        }
        return false;
    }); 
    
});
*/
function viewKcfx(){
	var ret = selectDataTableRow("dataTable_dhfx_main");
    if (ret) {
      Global.Dialog.showDialog("wareHouseAnalyseView",{
		  title:"可用库存分析",
		  url:"${ctx}/admin/wareHouseCx/goAnalyseDetail?id="+ret.itemcode,
		  height:600,
		  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function viewPhone(){
	Global.Dialog.showDialog("customerXxViewPhone",{
	  title:"客户联系方式-查看",
	  url:"${ctx}/admin/customerXx/goViewPhone?id=${customer.code}",
	  height:300,
	  width:500
	});
}
</script>
<style type="text/css">
.ui-jqgrid{
width: 2000px;
}
</style>
</head>
<body onunload="closeWin()">
<div class="body-box-form">
	<div class="panel panel-system">
	    <div class="panel-body">
	      <div class="btn-group-xs">
	      <button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
	      <c:if test="${isCustPay!='1' }">
		      <house:authorize authCode="CUSTOMERXX_PHONE|CUSTOMERXX_PHONE_CON|CUSTOMERXX_PHONE_COMPLETE" custStatus="${customer.status}">
		      <button type="button" class="btn btn-system" onclick="viewPhone()">查看联系方式</button>
		      </house:authorize>
	      </c:if>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	   </div>
	</div>
	<!--标签页内容 -->
	<div class="container-fluid" id="id_detail">
	    <ul class="nav nav-tabs" >
	        <li class="active"><a href="#tab_khjbxx" data-toggle="tab">客户基本信息</a></li>
	        <li class=""><a href="#tab_khhtxx" data-toggle="tab" onclick="go_tab('tab_khhtxx')">客户合同信息</a></li>
	        <li class=""><a href="#tab_khgz" data-toggle="tab" onclick="go_tab('tab_khgz')">客户跟踪</a></li>
	        <li class=""><a href="#tab_gxr" data-toggle="tab" onclick="go_tab('tab_gxr')">干系人</a></li>
	        <li class=""><a href="#tab_sjjd" data-toggle="tab" onclick="go_tab('tab_sjjd')">设计进度</a></li>
	        <li class=""><a href="#tab_jzys" data-toggle="tab" onclick="go_tab('tab_jzys')">基础预算</a></li>
	        <li class=""><a href="#tab_clys" data-toggle="tab" onclick="go_tab('tab_clys')">材料预算</a></li>
	        <li class=""><a href="#tab_jzxq" data-toggle="tab" onclick="go_tab('tab_jzxq')">基础需求</a></li>
	        <li class=""><a href="#tab_clxq" data-toggle="tab" onclick="">材料需求</a></li>
	        <li class=""><a href="#tab_jzzj" data-toggle="tab" onclick="go_tab('tab_jzzj')">基础增减</a></li>
	        <li class=""><a href="#tab_clzj" data-toggle="tab" onclick="go_tab('tab_clzj')">材料增减</a></li>
	        <li class=""><a href="#tab_khfk" data-toggle="tab" onclick="go_tab('tab_khfk')">客户付款</a></li>
	        <li class=""><a href="#tab_lld" data-toggle="tab" onclick="go_tab('tab_lld')">领料单</a></li>
	        <li class=""><a href="#tab_lld_lpll" data-toggle="tab" onclick="go_tab('tab_lld_lpll')">礼品领料</a></li>
	        <li class=""><a href="#tab_tcmx" data-toggle="tab" onclick="go_tab('tab_tcmx')">提成明细</a></li>
	        <li class=""><a href="#tab_gcxx" data-toggle="tab" onclick="go_tab('tab_gcxx')">工程信息</a></li>
	        <li class=""><a href="#tab_gcjd" data-toggle="tab" onclick="go_tab('tab_gcjd')">工程进度</a></li>
	        <li class=""><a href="#tab_sgjd" data-toggle="tab" onclick="go_tab('tab_sgjd')">施工进度</a></li>
	        <li class=""><a href="#tab_cgd" data-toggle="tab" onclick="go_tab('tab_cgd')">采购单</a></li>
	        <li class=""><a href="#tab_dhfx" data-toggle="tab" onclick="go_tab('tab_dhfx')">材料需求及到货分析</a></li>
	        <li class=""><a href="#tab_softInstall" data-toggle="tab" onclick="go_tab('tab_softInstall')">软装安装信息</a></li>
	    </ul>
	    <div class="tab-content">
    		<div id="tab_khjbxx" class="tab-pane fade in active"> 
	         	<jsp:include page="tab_khjbxx.jsp"></jsp:include>
	        </div>  
	        <div id="tab_khhtxx" class="tab-pane fade"> 
	        </div>
	        <div id="tab_khgz" class="tab-pane fade"> 
	        </div>
	        <div id="tab_gxr" class="tab-pane fade"> 
	        </div>
	        <div id="tab_sjjd" class="tab-pane fade"> 
	        </div>
	        <div id="tab_jzys" class="tab-pane fade"> 
	        </div>
	        <div id="tab_clys" class="tab-pane fade"> 
	        </div>
	        <div id="tab_jzxq" class="tab-pane fade"> 
	        </div>
	        <div id="tab_clxq" class="tab-pane fade"> 
	        	<jsp:include page="tab_clxq.jsp"></jsp:include>
	        </div>
	        <div id="tab_jzzj" class="tab-pane fade"> 
	        </div>
	        <div id="tab_clzj" class="tab-pane fade"> 
	        </div>
	        <div id="tab_khfk" class="tab-pane fade"> 
	        </div>
	        <div id="tab_lld" class="tab-pane fade"> 
	        </div>
	        <div id="tab_lld_lpll" class="tab-pane fade"> 
	        </div>
	        <div id="tab_tcmx" class="tab-pane fade"> 
	        </div>
	        <div id="tab_gcxx" class="tab-pane fade"> 
	        </div>
	        <div id="tab_gcjd" class="tab-pane fade"> 
	        </div>
	        <div id="tab_sgjd" class="tab-pane fade"> 
		       	<div id="tab_sgjd" class="tab-pane fade"> 
		        	<jsp:include page="tab_sgjd.jsp"></jsp:include>
		        </div>
	        </div>
	        <div id="tab_cgd" class="tab-pane fade"> 
	        </div>
	        <div id="tab_dhfx" class="tab-pane fade"> 
	        </div>
	        <div id="tab_softInstall" class="tab-pane fade"> 
	        </div>
    	</div>
    </div>
</div>
<form action="" method="post" id="page_form_excel" >
	<input type="hidden" name="jsonString" value="" />
</form>
<script type="text/javascript">
function doExcel(){
	if ($("ul li a[href='#tab_khhtxx']").parent().hasClass("active")){
		if ($("ul li a[href='#tab_khhtxx_cqht']").parent().hasClass("active")){
			doExcelNow('重签合同-${customer.address }','dataTable_khhtxx_cqht','page_form_excel');
		}
		if ($("ul li a[href='#tab_khhtxx_discToken']").parent().hasClass("active")){
			doExcelNow('优惠券信息-${customer.address }','dataTable_khhtxx_discToken','page_form_excel');
		}
	}
	else if ($("ul li a[href='#tab_khgz']").parent().hasClass("active")){
		doExcelNow('客户跟踪-${customer.address }','dataTable_khgz','page_form_excel');
	}
	else if ($("ul li a[href='#tab_gxr']").parent().hasClass("active")){
		if ($("ul li a[href='#tab_gxr_dqgxr']").parent().hasClass("active")){
			doExcelNow('当前干系人-${customer.address }','dataTable_gxr_dqgxr','page_form_excel');
		}
		if ($("ul li a[href='#tab_gxr_xgls']").parent().hasClass("active")){
			doExcelNow('修改历史-${customer.address }','dataTable_gxr_xgls','page_form_excel');
		}
	}
	else if ($("ul li a[href='#tab_gcjd']").parent().hasClass("active")){
		doExcelNow('工程进度-${customer.address }','dataTable_gcjd','page_form_excel');
	}
	else if ($("ul li a[href='#tab_sgjd']").parent().hasClass("active")){
		doExcelNow('班组施工进度-${customer.address }','dataTable_sgjd','page_form_excel');
	}
	else if ($("ul li a[href='#tab_jzys']").parent().hasClass("active")){
		doExcelNow('基础预算-${customer.address }','dataTable_jzys','page_form_excel');
	}
	else if ($("ul li a[href='#tab_clys']").parent().hasClass("active")){
		if ($("ul li a[href='#tab_clys_zcys']").parent().hasClass("active")){
			doExcelNow('主材预算-${customer.address }','dataTable_clys_ZC_0','page_form_excel');
		}
		if ($("ul li a[href='#tab_clys_fwys']").parent().hasClass("active")){
			doExcelNow('服务性产品预算-${customer.address }','dataTable_clys_ZC_1','page_form_excel');
		}
		if ($("ul li a[href='#tab_clys_rzys']").parent().hasClass("active")){
			doExcelNow('软装预算-${customer.address }','dataTable_clys_RZ_0','page_form_excel');
		}
		if ($("ul li a[href='#tab_clys_jcys']").parent().hasClass("active")){
			doExcelNow('集成预算-${customer.address }','dataTable_clys_JC_0','page_form_excel');
		}
	}
	else if ($("ul li a[href='#tab_jzxq']").parent().hasClass("active")){
		doExcelNow('基础需求-${customer.address }','dataTable_jzxq','page_form_excel');
	}
	else if ($("ul li a[href='#tab_clxq']").parent().hasClass("active")){
		if ($("ul li a[href='#tab_clxq_zcxq']").parent().hasClass("active")){
			var tableId = 'dataTable_clxq_ZC_0';
			var id = '_zcxq'+tableId.replace('dataTable_clxq','');
			var itemType2 = $("#itemType2"+id).val();
			var fixareadescr = $("#fixareadescr"+id).val();
			var itemdescr = $("#itemdescr"+id).val();
			var intproddescr = $("#intproddescr"+id).val();
			var title = "主材需求-${customer.address }";
			var url = "${ctx}/admin/customerXx/doExcelClxq?custCode=${customer.code}&itemType1=ZC&isService=0";
			url = url+"&itemType2="+itemType2+"&fixareadescr="+encodeURIComponent(fixareadescr)+"&itemdescr="
				+encodeURIComponent(itemdescr)+"&intproddescr="+encodeURIComponent(intproddescr)+"&title="+encodeURIComponent(title);
			doExcelAll(url,'dataTable_clxq_ZC_0','page_form_excel');
		}
		if ($("ul li a[href='#tab_clxq_fwxq']").parent().hasClass("active")){
			var tableId = 'dataTable_clxq_ZC_1';
			var id = '_zcxq'+tableId.replace('dataTable_clxq','');
			var itemType2 = $("#itemType2"+id).val();
			var fixareadescr = $("#fixareadescr"+id).val();
			var itemdescr = $("#itemdescr"+id).val();
			var intproddescr = $("#intproddescr"+id).val();
			var title = "服务性产品需求-${customer.address }";
			var url = "${ctx}/admin/customerXx/doExcelClxq?custCode=${customer.code}&itemType1=ZC&isService=1";
			url = url+"&itemType2="+itemType2+"&fixareadescr="+encodeURIComponent(fixareadescr)+"&itemdescr="
				+encodeURIComponent(itemdescr)+"&intproddescr="+encodeURIComponent(intproddescr)+"&title="+encodeURIComponent(title);
			doExcelAll(url,'dataTable_clxq_ZC_1','page_form_excel');
		}
		if ($("ul li a[href='#tab_clxq_rzxq']").parent().hasClass("active")){
			var tableId = 'dataTable_clxq_RZ_0';
			var id = '_zcxq'+tableId.replace('dataTable_clxq','');
			var itemType2 = $("#itemType2"+id).val();
			var fixareadescr = $("#fixareadescr"+id).val();
			var itemdescr = $("#itemdescr"+id).val();
			var intproddescr = $("#intproddescr"+id).val();
			var title = "软装需求-${customer.address }";
			var url = "${ctx}/admin/customerXx/doExcelClxq?custCode=${customer.code}&itemType1=RZ&isService=0";
			url = url+"&itemType2="+itemType2+"&fixareadescr="+encodeURIComponent(fixareadescr)+"&itemdescr="
				+encodeURIComponent(itemdescr)+"&intproddescr="+encodeURIComponent(intproddescr)+"&title="+encodeURIComponent(title);
			doExcelAll(url,'dataTable_clxq_RZ_0','page_form_excel');
		}
		if ($("ul li a[href='#tab_clxq_jcxq']").parent().hasClass("active")){
			var tableId = 'dataTable_clxq_JC_0';
			var id = '_zcxq'+tableId.replace('dataTable_clxq','');
			var itemType2 = $("#itemType2"+id).val();
			var fixareadescr = $("#fixareadescr"+id).val();
			var itemdescr = $("#itemdescr"+id).val();
			var intproddescr = $("#intproddescr"+id).val();
			var title = "集成需求-${customer.address }";
			var url = "${ctx}/admin/customerXx/doExcelClxq?custCode=${customer.code}&itemType1=JC&isService=0";
			url = url+"&itemType2="+itemType2+"&fixareadescr="+encodeURIComponent(fixareadescr)+"&itemdescr="
				+encodeURIComponent(itemdescr)+"&intproddescr="+encodeURIComponent(intproddescr)+"&title="+encodeURIComponent(title);
			doExcelAll(url,'dataTable_clxq_JC_0','page_form_excel');
		}
	}
	else if ($("ul li a[href='#tab_jzzj']").parent().hasClass("active")){
		if ($("ul li a[href='#tab_jzzj_jzxmzj']").parent().hasClass("active")){
			doExcelNow('基础项目增减-${customer.address }','dataTable_jzzj_jzxmzj','page_form_excel');
		}
		if ($("ul li a[href='#tab_jzzj_htfyzj']").parent().hasClass("active")){
			doExcelNow('合同费用增减-${customer.address }','dataTable_jzzj_htfyzj','page_form_excel');
		}
	}
	else if ($("ul li a[href='#tab_clzj']").parent().hasClass("active")){
		if ($("ul li a[href='#tab_clzj_zczj']").parent().hasClass("active")){
			doExcelNow('主材增减-${customer.address }','dataTable_clzj_ZC_0','page_form_excel');
		}
		if ($("ul li a[href='#tab_clzj_fwzj']").parent().hasClass("active")){
			doExcelNow('服务性产品增减-${customer.address }','dataTable_clzj_ZC_1','page_form_excel');
		}
		if ($("ul li a[href='#tab_clzj_rzzj']").parent().hasClass("active")){
			doExcelNow('软装增减-${customer.address }','dataTable_clzj_RZ_0','page_form_excel');
		}
		if ($("ul li a[href='#tab_clzj_jczj']").parent().hasClass("active")){
			doExcelNow('集成增减-${customer.address }','dataTable_clzj_JC_0','page_form_excel');
		}
	}
	else if ($("ul li a[href='#tab_lld']").parent().hasClass("active")){
		if ($("ul li a[href='#tab_lld_zcll']").parent().hasClass("active")){
			doExcelNow('主材领料-${customer.address }','dataTable_lld_ZC','page_form_excel');
		}
		if ($("ul li a[href='#tab_lld_rzll']").parent().hasClass("active")){
			doExcelNow('软装领料-${customer.address }','dataTable_lld_RZ','page_form_excel');
		}
		if ($("ul li a[href='#tab_lld_jcll']").parent().hasClass("active")){
			doExcelNow('集成领料-${customer.address }','dataTable_lld_JC','page_form_excel');
		}
		if ($("ul li a[href='#tab_lld_jzll']").parent().hasClass("active")){
			doExcelNow('基础领料-${customer.address }','dataTable_lld_JZ','page_form_excel');
		}
	}
	else if ($("ul li a[href='#tab_tcmx']").parent().hasClass("active")){
		doExcelNow('提成明细-${customer.address }','dataTable_tcmx_main','page_form_excel');
	}
	else if ($("ul li a[href='#tab_cgd']").parent().hasClass("active")){
		if ($("ul li a[href='#tab_cgd_jzcgd']").parent().hasClass("active")){
			doExcelNow('基础采购单-${customer.address }','dataTable_cgd_JZ','page_form_excel');
		}
		if ($("ul li a[href='#tab_cgd_zccgd']").parent().hasClass("active")){
			doExcelNow('主材采购单-${customer.address }','dataTable_cgd_ZC','page_form_excel');
		}
		if ($("ul li a[href='#tab_cgd_rzcgd']").parent().hasClass("active")){
			doExcelNow('软装采购单-${customer.address }','dataTable_cgd_RZ','page_form_excel');
		}
		if ($("ul li a[href='#tab_cgd_jccgd']").parent().hasClass("active")){
			doExcelNow('集成采购单-${customer.address }','dataTable_cgd_JC','page_form_excel');
		}
	}
	else if ($("ul li a[href='#tab_dhfx']").parent().hasClass("active")){
		doExcelNow('材料需求到货分析-${customer.address }','dataTable_dhfx_main','page_form_excel');
	}
}
function go_tab(tabId){
	$("#"+tabId).load("${ctx}/admin/customerXx/go_tab?id=${customer.code}&tabId="+tabId);
}
</script>
</body>
</html>
