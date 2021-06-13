<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>WareHouse明细</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_baseItemPlanBak.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function goto_query() {
     $("#dataTable").jqGrid("setGridParam", {url:"${ctx}/admin/itemPlan/goPlanBakJqGrid",postData: $("#page_form").jsonForm(), page: 1}).trigger("reloadGrid");   
}

var hasInvalid=true;
//tab分页
$(document).ready(function() {
	if ("${baseItemPlan.mustImportTemp}"=="1"){
		$("#file").attr("disabled","disabled");
		$("#btnloadData").hide();
		$("#file_view").hide();
		$("#btnExcel").hide();	
	}else{
		$("#planBakNo_view").hide();
		$("#addressType_li").hide();	
	}
	if("${baseItemPlan.isOutSet}"!="1"){
		$("#addressType_li").hide();	
	}
	
	$("#planBakNo").openComponent_baseItemPlanBak({
    	condition: {'custCode': '${baseItemPlan.custCode}', 'custType': '${baseItemPlan.custType}',
    		'isOutSet': '${baseItemPlan.isOutSet}' },
    	callBack: function (data) {
    		$("#baseTempNo").val(data.basetempno);
    		$("#baseTempDescr").val(data.basetempdescr);
        	goto_query();
     	}  
    });   
	
	$("#sCustCode").openComponent_customer({
		condition: {'ignoreCustRight': '0' },
    	callBack: function (data) {
    		$("#sCustCode").val(data.code);
    		copy();
     	}  
	});
	
 	$('input[type=radio][name=addressType]').change(function() {
        if (this.value == '1') {
            $("#planBakNo-li").show();
            $("#sCustCode-li").hide();
        }else{
        	$("#planBakNo-li").hide();
            $("#sCustCode-li").show();
        } 
    });
     //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-72,
		rowNum:10000,
		colModel : [
			{name: "isinvaliddescr", index: "isinvaliddescr", width: 100, label: "是否有效数据", sortable: true, align: "left"},
			{name: "isinvalid", index: "isinvalid", width: 100, label: "是否有效数据", sortable: true, align: "left",hidden:true},
			{name: "fixareadescr", index: "fixareadescr", width: 166, label: "区域名称", sortable: true, align: "left"},
			{name: "fixareapk", index: "fixareapk", width: 98, label: "区域", sortable: true, align: "left",hidden:true},
			{name: "baseitemcode", index: "baseitemcode", width: 100, label: "基础项目编号", sortable: true, align: "left"},
			{name: "baseitemdescr", index: "baseitemdescr", width: 270, label: "基础项目名称", sortable: true, align: "left"},
			{name: "baseitemtype1", index: "baseitemtype1", width: 100, label: "基础材料类型1", sortable: true, align: "left",hidden:true},
			{name: "basealgorithm", index: "basealgorithm", width: 70, label: "算法", sortable:false, align: "left", hidden: true},
			{name: "basealgorithmdescr", index: "basealgorithmdescr", width: 85, label: "算法", sortable:false, align: "left"},
			{name: "qty", index: "qty", width: 76, label: "数量", sortable: true, align: "left", sum: true},
			{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left"},
			{name: "unitprice", index: "unitprice", width: 85, label: "人工单价", sortable: true, align: "left"},
			{name: "material", index: "material", width: 85, label: "材料单价", sortable: true, align: "left"},
			{name: "tempunitprice", index: "unitprice", width: 83, label: "临时人工单价", sortable: true, align: "left",hidden:true},
			{name: "tempmaterial", index: "material", width: 80, label: "临时材料单价", sortable: true, align: "left",hidden:true},
			{name: "sumunitprice", index: "sumunitprice", width: 85, label: "人工总价", sortable: true, align: "left", sum: true},
			{name: "summaterial", index: "summaterial", width: 85, label: "材料总价", sortable: true, align: "left", sum: true},
			{name: "lineamount", index: "lineamount", width: 85, label: "总价", sortable: true, align: "left", sum: true},
			{name: "remark", index: "remark", width: 187, label: "备注", sortable: true, align: "left"},
			{name: "isrequired", index: "isrequired", width: 68, label: "必选项", sortable: true, align: "left", hidden: true},
       		{name: "canreplace", index: "canreplace", width: 68, label: "可替换", sortable: true, align: "left", hidden: true},
       		{name: "canmodiqty", index: "canmodiqty", width: 78, label: "数量可修改", sortable: true, align: "left", hidden: true},
       		{name: "isrequireddescr", index: "isrequireddescr", width: 68, label: "必选项", sortable: true, align: "left"},
       		{name: "canreplacedescr", index: "canreplacedescr", width: 68, label: "可替换", sortable: true, align: "left"},
       		{name: "canmodiqtydescr", index: "canmodiqtydescr", width: 78, label: "数量可修改", sortable: true, align: "left"},
			{name: "isoutset", index: "isoutset", width: 87, label: "套餐外材料", sortable: true, align: "left", hidden: true},
		    {name: "isoutsetdescr", index: "isoutsetdescr", width: 87, label: "套餐外材料", sortable: true, align: "left", hidden: true},
		    {name: "categorydescr", index: "categorydescr", width: 88, label: "项目类型", sortable: true, align: "left", hidden: true},
		    {name: "category", index: "category", width: 107, label: "项目分类", sortable: true, align: "left",hidden:true},
		    {name: "iscalmangefee", index: "iscalmangefee", width: 80, label: "是否计算管理费", sortable: true, align: "left", hidden: true},
		    {name: "prjtype", index: "prjtype", width: 68, label: "施工类型", sortable: true, align: "left", hidden: true},
       	    {name: "preplanareapk", index: "preplanareapk", width: 68, label: "空间pk", sortable: true, align: "left", hidden: true},
		    {name: "cost", index: "cost", width: 76, label: "成本", sortable: true, align: "left" ,hidden:true},
        	{name: "autoqty", index: "autoqty", width: 80, label: "系统计算量", sortable: true, align: "left", hidden: true},
        	{name: "isfixprice", index: "isfixprice", width: 80, label: "是否固定价", sortable: true, align: "left", hidden: true},
        	{name: "tempdtpk", index: "tempdtpk", width: 68, label: "模板pk", sortable: true, align: "left", hidden: true},
        	{name: "allowpricerise", index: "allowpricerise", width: 68, label: "价格上浮", sortable: true, align: "left", hidden: true},
        	{name: "giftdescr", index: "giftdescr", width: 80, label: "赠送项目", sortable: true, align: "left"},
			{name: "giftpk", index: "giftpk", width: 0.5, label: "赠送项目编号", sortable: true, align: "left"},
			{name: "baseitemsetno", index: "baseitemsetno", width: 60, label: "套餐包", sortable: true, align: "left"},
            {name: "ismainitem", index: "ismainitem", width: 68, label: "主项目", sortable: true, align: "left",hidden: true},
            {name: "ismainitemdescr", index: "ismainitemdescr", width: 68, label: "主项目", sortable: true, align: "left"},
        ]
      
	});
		      //初始化excel模板表格
	Global.JqGrid.initJqGrid("modelDataTable",{
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
			{name: "fixareadescr", index: "fixareadescr", width: 166, label: "区域名称", sortable: true, align: "left"},
			{name: "fixareapk", index: "fixareapk", width: 98, label: "区域", sortable: true, align: "left",hidden:true},
			{name: "baseitemcode", index: "baseitemcode", width: 100, label: "基础项目编号", sortable: true, align: "left"},
			{name: "baseitemdescr", index: "baseitemdescr", width: 270, label: "基础项目名称", sortable: true, align: "left"},
			{name: "baseitemtype1", index: "baseitemtype1", width: 100, label: "基础材料类型1", sortable: true, align: "left",hidden:true},
			{name: "basealgorithm", index: "basealgorithm", width: 70, label: "算法", sortable:false, align: "left", hidden: true},
			{name: "basealgorithmdescr", index: "basealgorithmdescr", width: 85, label: "算法", sortable:false, align: "left"},
			{name: "qty", index: "qty", width: 76, label: "数量", sortable: true, align: "left"},
			{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left"},
			{name: "unitprice", index: "unitprice", width: 85, label: "人工单价", sortable: true, align: "left"},
			{name: "material", index: "material", width: 85, label: "材料单价", sortable: true, align: "left"},
			{name: "tempunitprice", index: "unitprice", width: 83, label: "临时人工单价", sortable: true, align: "left",hidden:true},
			{name: "tempmaterial", index: "material", width: 80, label: "临时材料单价", sortable: true, align: "left",hidden:true},
			{name: "sumunitprice", index: "sumunitprice", width: 85, label: "人工总价", sortable: true, align: "left"},
			{name: "summaterial", index: "summaterial", width: 85, label: "材料总价", sortable: true, align: "left"},
			{name: "lineamount", index: "lineamount", width: 85, label: "总价", sortable: true, align: "left"},
			{name: "remark", index: "remark", width: 187, label: "备注", sortable: true, align: "left"},
			{name: "isrequired", index: "isrequired", width: 68, label: "必选项", sortable: true, align: "left", hidden: true},
       		{name: "canreplace", index: "canreplace", width: 68, label: "可替换", sortable: true, align: "left", hidden: true},
       		{name: "canmodiqty", index: "canmodiqty", width: 78, label: "数量可修改", sortable: true, align: "left", hidden: true},
       		{name: "isrequireddescr", index: "isrequireddescr", width: 68, label: "必选项", sortable: true, align: "left"},
       		{name: "canreplacedescr", index: "canreplacedescr", width: 68, label: "可替换", sortable: true, align: "left"},
       		{name: "canmodiqtydescr", index: "canmodiqtydescr", width: 78, label: "数量可修改", sortable: true, align: "left"},
			{name: "isoutset", index: "isoutset", width: 87, label: "套餐外材料", sortable: true, align: "left", hidden: true},
		    {name: "isoutsetdescr", index: "isoutsetdescr", width: 87, label: "套餐外材料", sortable: true, align: "left", hidden: true},
		    {name: "categorydescr", index: "categorydescr", width: 88, label: "项目类型", sortable: true, align: "left", hidden: true},
		    {name: "category", index: "category", width: 107, label: "项目分类", sortable: true, align: "left",hidden:true},
		    {name: "iscalmangefee", index: "iscalmangefee", width: 80, label: "是否计算管理费", sortable: true, align: "left", hidden: true},
		    {name: "prjtype", index: "prjtype", width: 68, label: "施工类型", sortable: true, align: "left", hidden: true},
       	    {name: "preplanareapk", index: "preplanareapk", width: 68, label: "空间pk", sortable: true, align: "left", hidden: true},
		    {name: "cost", index: "cost", width: 76, label: "成本", sortable: true, align: "left" ,hidden:true},
		    {name: "iscalmangefee", index: "iscalmangefee", width: 80, label: "是否计算管理费", sortable: true, align: "left", hidden: true},
        	{name: "autoqty", index: "autoqty", width: 80, label: "系统计算量", sortable: true, align: "left", hidden: true},
        	{name: "isfixprice", index: "isfixprice", width: 80, label: "是否固定价", sortable: true, align: "left", hidden: true},
        	{name: "tempdtpk", index: "tempdtpk", width: 68, label: "模板pk", sortable: true, align: "left", hidden: true},
        	{name: "allowpricerise", index: "allowpricerise", width: 68, label: "价格上浮", sortable: true, align: "left", hidden: true},
        	{name: "giftdescr", index: "giftdescr", width: 80, label: "赠送项目", sortable: true, align: "left"},
			{name: "giftpk", index: "giftpk", width: 0.5, label: "赠送项目编号", sortable: true, align: "left"},
           ]
	});
	$("#modelDataTable").addRowData(1, {"fixareadescr":"入户、客厅、餐厅、休闲区","baseitemcode":"0100","baseitemdescr":"平吊顶","qty":40,"remark":"木龙骨（或轻钢龙骨基础），硅酸钙板封面（灯槽另计）"}, "last");
       return false;  
});
//加载文件验证
function check(){
	var fileName=$("#file").val();
	var reg=/^.+\.(?:xls|xlsx)$/i;
    if(fileName.length==0){
    	art.dialog({
			content: "请选择需要导入的excel文件！"
		});
        return false;
    }else if(fileName.match(reg)==null){
   		 art.dialog({
			content: "文件格式不正确！请导入正确的excel文件！"
		});
		return false;
    }
    return true;
}
//加载excel
function loadData(){
	if(check()){
	var formData = new FormData();
         formData.append("file", document.getElementById("file").files[0]);
         formData.append("custCode",'${baseItemPlan.custCode}');
         $.ajax({
               url: "${ctx}/admin/itemPlan/loadExcel",
               type: "POST",
               data: formData,
               contentType: false,
               processData: false,
               success: function (data) {
	               if(data.hasInvalid) hasInvalid=true;
	               else hasInvalid=false;
	               if (data.success == false) {
	                    art.dialog({
							content: data.returnInfo
						});         
	               }else{     
		               $("#dataTable").clearGridData();
		               $.each(data.datas,function(k,v){
		                   $("#dataTable").addRowData(k+1,v,"last");
		               })
	               }
              },
              error: function () {
                 art.dialog({
			    	content: "上传文件失败!"
			  	});
              }
        });
    }
}
//导入数据
function importData(){
	if($("#dataTable").jqGrid('getGridParam','records')==0){
			art.dialog({
				content: "请先载入要进行批量导入的预算数据！"
		});
		return;
	}
	/* if(hasInvalid){
		art.dialog({
				content: "存在无效的数据，无法导入！"
		});
		return;
	} */
	var isinvalid= Global.JqGrid.allToJson("dataTable","isinvalid");
	arr= isinvalid.fieldJson.split(",");
	var s=0;		
	for(var i=0;i<arr.length;i++){
		if(arr[i]!="0"){
			art.dialog({
					content: "存在无效的数据，无法导入！"
			});
			return;
		}
	}
	var returnData=$("#dataTable").jqGrid("getRowData");
	if("${baseItemPlan.isOutSet}"!='1'){
		var sBaseTempNo=$("#baseTempNo").val();
		var sBaseTempDescr=$("#baseTempDescr").val();
		if (sBaseTempNo!=""){
			var datas = $("#page_form").jsonForm();
			$.ajax({
				url:'${ctx}/admin/itemPlan/doUpdateTempNo',
				type: 'post',
				data: datas,
				dataType: 'json',
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
			    },
			    success: function(obj){
			    	if(obj.rs){
			    	
			    	}else{
						$("#_form_token_uniq_id").val(obj.token.token);
						art.dialog({
							content: obj.msg,
							width: 200
						});
						return;
			    	}
			    }
			});	
			if( "${baseItemPlan.m_umState}"=="C"){
				parent.document.getElementById("iframe_itemPlan_jcys_confirm").
					contentWindow.document.getElementById("baseTempNo").value=sBaseTempNo+"|"+sBaseTempDescr;
			}else{
				parent.document.getElementById("iframe_itemPlan_jcys").
					contentWindow.document.getElementById("baseTempNo").value=sBaseTempNo+"|"+sBaseTempDescr;
			} 	
		}
	}
	Global.Dialog.returnData = returnData;
	closeWin();
}

function copy(){
 	$.ajax({
		url:"${ctx}/admin/itemPlan/doCopyPlan",
		type: 'POST',
		data: $("#page_form").jsonForm(),
		cache: false,
	    success: function(obj){
	  		if(obj.rs){
	  			 $('#dataTable').clearGridData();
	  			if(obj.datas){
	  				$.each(JSON.parse(obj.datas),function(k,v){
		  				$("#dataTable").addRowData(k+1, v, "last");
		  			})
	  			}
	  		}else{
	  			art.dialog({
					content: obj.msg
				});
	  		}
	  		
	    }
	});
	
}

</script>
</head>
<body>
	<div class="body-box-form">
		<div class="btn-panel pull-left">
			<div class="btn-group-sm">
				<button id="btnloadData" type="button" class="btn btn-system " onclick="loadData()">加载数据</button>
				<button id="btnimport" type="button" class="btn btn-system " onclick="importData()">导入数据</button>
				<button  id="btnExcel" type="button" class="btn btn-system " onclick="doExcelNow('基础预算导入模板','modelDataTable')"
					style="margin-right: 15px">下载模板</button>

			</div>
		</div>
		<div class="query-form" style="padding: 0px;border: none">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">	
				<%--<house:token></house:token>
				--%><input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="custCode" name="custCode" value="${baseItemPlan.custCode}" />
				<input type="hidden" id="itemType1" name="itemType1" value="JZ" />
				<input type="hidden" id="IsService" name="IsService" value=0 />
				<input type="hidden"  id="baseTempNo" name="baseTempNo"  />	
				<input type="hidden"  id="baseTempDescr" name="baseTempDescr"  />
				<input type="hidden" id="code" name="code" value="${baseItemPlan.custCode}" />	
				<input type="hidden" id="isOutSet" name="isOutSet" value="${baseItemPlan.isOutSet}" />
				<div  id="file_view" class="form-group">
					<label for="inputfile"></label> 
						<input type="file" style="position: relative;top: -12px;" id="file"
						name="file"
						accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
				</div>
				<li id="addressType_li" style="position:absolute;left:95px;top:0px;">
					<label><input name="addressType" type="radio" value="1" checked/>本楼盘备份 </label> 
					<label><input name="addressType" type="radio" value="2" />其他楼盘预算 </label> 
				</li>
				<ul  id="planBakNo_view"  class="ul-form" style="position:absolute;left:300px;top:0px;">
					<li  id="planBakNo-li" class="form-validate">
						<label>选择备份编号</label> 
						<input type="text" style="width:160px;" id="planBakNo" name="planBakNo" readonly="readonly" />
					</li>
					<li id="sCustCode-li" hidden="true"><label>客户编号</label> 
						<input type="text" id="sCustCode" name="sCustCode" />
					</li>	
				</ul>
			</form>

		</div>
		<div class="pageContent">
			<!--panelBar-->
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
			<div style="display: none">
				<table id="modelDataTable"></table>
				<div id="modelDataTable"></div>
			</div>
		</div>
	</div>
</body>
</html>
