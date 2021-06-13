<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>签单排行</title>
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<style type="text/css">
  th.ui-th-column div{
      white-space:normal !important;
      height:auto !important;
      padding:0px;
  }
</style>
<script type="text/javascript">
function view() {
    	var ret = selectDataTableRow();
 		if(!ret){
 			art.dialog({content: "请选择一条记录进行查看！",width: 200});
 			return false;
 		}
 	    var params=$("#page_form").jsonForm();
 	    params.statistcsMethod=$("#statisticalType").val();
 	    if ($("#statisticalType").val() == '1') {
       		params.empCode=ret.number;
       		$.ajax({
				url : "${ctx}/admin/employeeDdph/doCheckView",
				data : {number: ret.number},
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: "json",
				type: "post",
				cache: false,
				error: function(){
			        art.dialog({
						content: '出现异常，无法查看'
					});
			    },
			    success: function(obj){
			    	if(obj.rs){
			        	goDetailView(params)		    		
			    	} else {
			    		art.dialog({
							content: obj.msg
						});
			    	}
				}
			});  
     	}else if ($("#statisticalType").val() == '2') {
    		params.department1=ret.department1;
    		params.department2="";
    		goDetailView(params)
   		}else  if ($("#statisticalType").val() == '3'){
   			params.department1="";
   			params.department2=ret.department2;
   			goDetailView(params)
   		} 
}
function goDetailView(params){
	Global.Dialog.showDialog("goDetail", {
    	  title: "查看签单明细",
    	  url: "${ctx}/admin/employeeDdph/goDetail",
    	  postData:params,
             height:650,
             width:1200,
    	  returnFun: goto_query
	}); 	
}      
//导出EXCEL
function doExcel(){
    //var url = "${ctx}/admin/employeeDdph/doExcel";
	doExcelNow("签单排行");
}
function goto_query(){
	if($.trim($("#dateFrom").val())==''){
			art.dialog({content: "统计开始日期不能为空",width: 200});
			return false;
	} 
	if($.trim($("#dateTo").val())==''){
			art.dialog({content: "统计结束日期不能为空",width: 200});
			return false;
	}
     var dateStart = Date.parse($.trim($("#dateFrom").val()));
     var dateEnd = Date.parse($.trim($("#dateTo").val()));
     if(dateStart>dateEnd){
    	 art.dialog({content: "统计开始日期不能大于结束日期",width: 200});
			return false;
     } 
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/employeeDdph/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,fromServer: true}).trigger("reloadGrid");
}
		
		
/**初始化表格*/
$(function(){
		var postData=$("#page_form").jsonForm();
		postData.custType="${customer.custType}",
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/employeeDdph/goJqGrid",
			postData: postData,
			height:$(document).height()-$("#content-list").offset().top-90,
			colModel : [
			 	{name: "pk", index: "pk", width: 50, label: "名次", sortable: true, align: "left", count: true},
				{name: "number", index: "number", width: 67, label: "员工编号", sortable: true, align: "left"},
				{name: "namechi", index: "namechi", width: 55, label: "设计师", sortable: true, align: "left"},
				{name: "depart1descr", index: "depart1descr", width: 67, label: "一级部门", sortable: true, align: "left"},
				{name: "depart2descr", index: "depart2descr", width: 77, label: "二级部门", sortable: true, align: "left"},
				{name: "depart3descr", index: "depart3descr", width: 77, label: "三级部门", sortable: true, align: "left"},
				{name: "department1", index: "department1", width: 77, label: "一级部门", sortable: true, align: "left",hidden :true},
				{name: "department2", index: "department2", width: 77, label: "二级部门", sortable: true, align: "left",hidden :true},
				{name: "teamdescr", index: "teamdescr", width: 77, label: "团队", sortable: true, align: "left"},
				{name: "builderdescr", index: "builderdescr", width: 120, label: "项目名称", sortable: true, align: "left"},
				{name: "mdescr", index: "mdescr", width: 100, label: "月份", sortable: true, align: "left",sorttype:"date" },
				{name: "regiondescr", index: "regiondescr", width: 76, label: "区域", sortable: true, align: "left"},
				{name: "delivdate", index: "delivdate", width: 87, label: "交房时间", sortable: true, align: "left", formatter: formatTime},
				{name: "buildertypedescr", index: "buildertypedescr", width: 70, label: "项目类型", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 77, label: "客户类型", sortable: true, align: "left"},
				{name: "sourcedescr", index: "sourcedescr", width: 77, label: "客户来源", sortable: true, align: "left"},
				{name: "sumcontractfee", index: "sumcontractfee", width: 72, label: "签单金额", sortable: true,sorttype:'float', align: "right", sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0} },
				{name: "contractfeeamount", index: "contractfeeamount", width: 85, label: "到账合同额", sortable: true, align: "right",sum:true},
				{name: "chgcontractfeeamount", index: "chgcontractfeeamount", width: 85, label: "增减合同额", sortable: true, align: "right",sum:true},
				{name: "totalcontractamount", index: "totalcontractamount", width: 85, label: "合计合同额", sortable: true, align: "right",sum:true,formatter:getTotalContractAmount},
				{name: "signcount", index: "signcount", width: 85, label: "签单数量(C)", sortable: true,sorttype:'float', align: "right", sum: true},
				{name: "setcount", index: "setcount", width: 85, label: "下定数量(A)", sortable: true,sorttype:'float', align: "right", sum: true},
				{name: "crtcount", index: "crtcount", width: 85, label: "接待数量(B)", sortable: true, sorttype:'float',align: "right", sum: true},
				{name: "setcrtpercent", index: "setcrtpercent", width: 80, label: "当期下定率", sortable: true,sorttype:'number', align: "right",formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2, suffix: "%"}},
				{name: "cursetcount", index: "cursetcount", width: 75, label: "当月来客下定数", sortable: true,sorttype:'number', align: "right",sum: true},
				{name: "cursigncount", index: "cursigncount", width: 75, label: "当月来客签单数", sortable: true,sorttype:'number', align: "right",sum: true},
				{name: "cursetsigncount", index: "cursetsigncount", width: 75, label: "当月下定签单数", sortable: true,sorttype:'number', align: "right",sum: true},
				{name: "signsetcount", index: "signsetcount", width: 80, label: "统计期存单总数(D)", sortable: true,sorttype:'number', align: "right", sum: true},
				{name: "signsetpercent", index: "signsetpercent", width: 100, label: "订单转换率(C/D*100%)", sortable: true,sorttype:'number', align: "right",formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2, suffix: "%"}},
				{name: "nowsigncount", index: "nowsigncount", width:75, label: "当前有效存单数", sortable: true, sorttype:'number',align: "right", sum: true},
				{name: "cursignperc", index: "cursignperc", width: 80, label: "当期签单率", sortable: true, sorttype:'number',align: "right",formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2, suffix: "%"} },
				{name: "sucpercent", index: "sucpercent", width: 80, label: "当期成功率", sortable: true, sorttype:'number',align: "right" ,formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: " ", decimalPlaces: 2, suffix: "%"}},
				{name: "curconfirmbegincount", index: "curconfirmbegincount", width: 75, label: "当月来客开工数", sortable: true,sorttype:'number', align: "right",sum: true},
				{name: "unsubcount", index: "unsubcount", width: 70, label: "退定数", sortable: true,sorttype:'number', align: "right",sum: true},
				{name: "transfercount", index: "transfercount", width: 70, label: "结转数", sortable: true,sorttype:'number', align: "right",sum: true},
				{name: "confirmcancelcount", index: "confirmcancelcount", width: 80, label: "施工取消数", sortable: true,sorttype:'number', align: "right",sum: true},
				{name: "totalrealnum", index: "totalrealnum", width: 75, label: "部门总人数", sortable: true,sorttype:'number', align: "right"},
				{name: "desimannum", index: "desimannum", width: 75, label: "设计师人数", sortable: true,sorttype:'number', align: "right"},
				{name: "busimannum", index: "busimannum", width: 75, label: "业务员人数", sortable: true,sorttype:'number', align: "right"},
				{name: "drawmannum", index: "drawmannum", width: 75, label: "绘图员人数", sortable: true,sorttype:'number', align: "right"},
		        {name: "area", index: "area", width: 75, label: "面积", sortable: true,sorttype:'number', align: "right"},
				{name: "returncontractfeeamount", index: "returncontractfeeamount", width: 85, label: "退单合同额", sortable: true, align: "right",sum:true},
				{name: "realperfamount", index: "realperfamount", width: 72, label: "签单业绩", sortable: true,sorttype:'float', align: "right", sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0} },
				{name: "chgperfamount", index: "chgperfamount", width: 72, label: "增减业绩", sortable: true,sorttype:'float', align: "right", sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0} },
				{name: "sumperfamount", index: "sumperfamount", width: 72, label: "合计业绩", sortable: true,sorttype:'float', align: "right", sum: true,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0} },
		    ],    
	    loadonce: true,
	    rowNum:100000,  
	    pager :'1',
	    onSortCol:function(index, iCol, sortorder){
			if(index=="mdescr"){//JqGrid自带排序月份失效，单独处理
				var rows = $("#dataTable").jqGrid("getRowData");
	  			rows.sort(function (a, b) {
		  			var reg = /^[0-9]+.?[0-9]*$/;
					if(reg.test(a[index])){
		  					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
					}else{
		  					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
					}  
	  			});    
	  			Global.JqGrid.clearJqGrid("dataTable"); 
	  			$.each(rows,function(k,v){
					Global.JqGrid.addRowData("dataTable", v);
				});
			}
		},
	  	gridComplete:function(){
	  		 if (($("#statistcsMethod").val() == '1'||$("#statistcsMethod").val() == '2'||$("#statistcsMethod").val() == '3') && $("#role").val() != "00,01") {
	        	$("#btnView").show(); 	
	         }else{
	       		$("#btnView").hide(); 	
	         }
	         $("#statisticalType").val($("#statistcsMethod").val());
		     var tableId='dataTable';
		     if($("#role").val() == "00,01"){
					$("#"+tableId).jqGrid('hideCol', "setcrtpercent");	
					$("#"+tableId).jqGrid('hideCol', "signsetcount");	
					$("#"+tableId).jqGrid('hideCol', "signsetpercent");	
					$("#"+tableId).jqGrid('hideCol', "nowsigncount");	
					$("#"+tableId).jqGrid('hideCol', "cursignperc");	
					$("#"+tableId).jqGrid('hideCol', "sucpercent");	
					$("#"+tableId).jqGrid('hideCol', "curconfirmbegincount");	
					$("#"+tableId).jqGrid('hideCol', "unsubcount");	
					$("#"+tableId).jqGrid('hideCol', "transfercount");	
					$("#"+tableId).jqGrid('hideCol', "confirmcancelcount");	
					$("#"+tableId).jqGrid('hideCol', "totalrealnum");	
					$("#"+tableId).jqGrid('hideCol', "desimannum");	
					$("#"+tableId).jqGrid('hideCol', "busimannum");	
					$("#"+tableId).jqGrid('hideCol', "drawmannum");	
					$("#"+tableId).jqGrid('hideCol', "area");	
			 } else {
				 $("#"+tableId).jqGrid('hideCol', "setcrtpercent");	
					$("#"+tableId).jqGrid('showCol', "signsetcount");	
					$("#"+tableId).jqGrid('showCol', "signsetpercent");	
					$("#"+tableId).jqGrid('showCol', "nowsigncount");	
					$("#"+tableId).jqGrid('showCol', "cursignperc");	
					$("#"+tableId).jqGrid('showCol', "sucpercent");	
					$("#"+tableId).jqGrid('showCol', "curconfirmbegincount");	
					$("#"+tableId).jqGrid('showCol', "unsubcount");	
					$("#"+tableId).jqGrid('showCol', "transfercount");	
					$("#"+tableId).jqGrid('showCol', "confirmcancelcount");	
					$("#"+tableId).jqGrid('showCol', "totalrealnum");	
					$("#"+tableId).jqGrid('showCol', "desimannum");	
					$("#"+tableId).jqGrid('showCol', "busimannum");	
					$("#"+tableId).jqGrid('showCol', "drawmannum");	
					$("#"+tableId).jqGrid('showCol', "area"); 
			 }
	         if ($("#statistcsMethod").val() == '1') {
	        	$("#"+tableId).jqGrid('showCol', "number");
	       	 	$("#"+tableId).jqGrid('showCol', "namechi");
	       	 	$("#"+tableId).jqGrid('showCol', "depart1descr");
	       		$("#"+tableId).jqGrid('showCol', "depart2descr");
	       		$("#"+tableId).jqGrid('showCol', "depart3descr");
				$("#"+tableId).jqGrid('hideCol', "teamdescr");
				$("#"+tableId).jqGrid('hideCol', "builderdescr");
				$("#"+tableId).jqGrid('hideCol', "custtypedescr");
				$("#"+tableId).jqGrid('hideCol', "sourcedescr");
			    $("#"+tableId).jqGrid('hideCol', "totalrealnum");
				$("#"+tableId).jqGrid('hideCol', "desimannum");
				$("#"+tableId).jqGrid('hideCol', "busimannum");
				$("#"+tableId).jqGrid('hideCol', "drawmannum");
				$("#"+tableId).jqGrid('hideCol', "builderdescr");
				$("#"+tableId).jqGrid('hideCol', "regiondescr");
				$("#"+tableId).jqGrid('hideCol', "delivdate");
				$("#"+tableId).jqGrid('hideCol', "buildertypedescr");
				$("#"+tableId).jqGrid('hideCol', "mdescr");	
				$("#"+tableId).jqGrid('hideCol', "area");	
			 }else if ($("#statistcsMethod").val() == '2') {
				$("#"+tableId).jqGrid('showCol', "depart1descr");
				$("#"+tableId).jqGrid('hideCol', "number");
				$("#"+tableId).jqGrid('hideCol', "namechi");
				$("#"+tableId).jqGrid('hideCol', "depart2descr");
				$("#"+tableId).jqGrid('hideCol', "depart3descr");
				$("#"+tableId).jqGrid('hideCol', "teamdescr");
				$("#"+tableId).jqGrid('hideCol', "builderdescr");
				$("#"+tableId).jqGrid('hideCol', "custtypedescr");
				$("#"+tableId).jqGrid('hideCol', "sourcedescr");
			    $("#"+tableId).jqGrid('hideCol', "totalrealnum");
				$("#"+tableId).jqGrid('hideCol', "desimannum");
				$("#"+tableId).jqGrid('hideCol', "busimannum");
				$("#"+tableId).jqGrid('hideCol', "drawmannum");
				$("#"+tableId).jqGrid('hideCol', "builderdescr");
				$("#"+tableId).jqGrid('hideCol', "regiondescr");
				$("#"+tableId).jqGrid('hideCol', "delivdate");
				$("#"+tableId).jqGrid('hideCol', "buildertypedescr");
				$("#"+tableId).jqGrid('hideCol', "mdescr");	
				$("#"+tableId).jqGrid('hideCol', "area");	
				
			}else if ($("#statistcsMethod").val() == '3') {
				$("#"+tableId).jqGrid('showCol', "depart1descr");
				$("#"+tableId).jqGrid('showCol', "depart2descr");
				$("#"+tableId).jqGrid('hideCol', "number");
				$("#"+tableId).jqGrid('hideCol', "namechi");
				$("#"+tableId).jqGrid('hideCol', "depart3descr");
				$("#"+tableId).jqGrid('hideCol', "teamdescr");
				$("#"+tableId).jqGrid('hideCol', "builderdescr");
				$("#"+tableId).jqGrid('hideCol', "custtypedescr");
				$("#"+tableId).jqGrid('hideCol', "sourcedescr");
				$("#"+tableId).jqGrid('showCol', "totalrealnum");
				$("#"+tableId).jqGrid('showCol', "desimannum");
				$("#"+tableId).jqGrid('showCol', "busimannum");
				$("#"+tableId).jqGrid('showCol', "drawmannum");
				$("#"+tableId).jqGrid('hideCol', "builderdescr");
				$("#"+tableId).jqGrid('hideCol', "regiondescr");
				$("#"+tableId).jqGrid('hideCol', "delivdate");	
				$("#"+tableId).jqGrid('hideCol', "buildertypedescr");	
				$("#"+tableId).jqGrid('hideCol', "mdescr");	
				$("#"+tableId).jqGrid('hideCol', "area");	
			}else if ($("#statistcsMethod").val() == '4') {
				$("#"+tableId).jqGrid('hideCol', "depart1descr");
				$("#"+tableId).jqGrid('hideCol', "number");
				$("#"+tableId).jqGrid('hideCol', "namechi");
				$("#"+tableId).jqGrid('hideCol', "depart2descr");
				$("#"+tableId).jqGrid('hideCol', "depart3descr");
				$("#"+tableId).jqGrid('showCol', "teamdescr");
				$("#"+tableId).jqGrid('hideCol', "builderdescr");
				$("#"+tableId).jqGrid('hideCol', "custtypedescr");
				$("#"+tableId).jqGrid('hideCol', "sourcedescr");
				 $("#"+tableId).jqGrid('hideCol', "totalrealnum");
				$("#"+tableId).jqGrid('hideCol', "desimannum");
				$("#"+tableId).jqGrid('hideCol', "busimannum");
				$("#"+tableId).jqGrid('hideCol', "drawmannum");
				$("#"+tableId).jqGrid('hideCol', "builderdescr");
				$("#"+tableId).jqGrid('hideCol', "regiondescr");
				$("#"+tableId).jqGrid('hideCol', "delivdate");
				$("#"+tableId).jqGrid('hideCol', "buildertypedescr");
				$("#"+tableId).jqGrid('hideCol', "mdescr");	
				$("#"+tableId).jqGrid('hideCol', "area");		
			}else if ($("#statistcsMethod").val() == '5') {
				$("#"+tableId).jqGrid('hideCol', "depart1descr");
				$("#"+tableId).jqGrid('hideCol', "number");
				$("#"+tableId).jqGrid('hideCol', "namechi");
				$("#"+tableId).jqGrid('hideCol', "depart2descr");
				$("#"+tableId).jqGrid('hideCol', "depart3descr");
				$("#"+tableId).jqGrid('hideCol', "teamdescr");
				$("#"+tableId).jqGrid('showCol', "builderdescr");
				$("#"+tableId).jqGrid('hideCol', "custtypedescr");
				$("#"+tableId).jqGrid('hideCol', "sourcedescr");
				$("#"+tableId).jqGrid('hideCol', "totalrealnum");
				$("#"+tableId).jqGrid('hideCol', "desimannum");
				$("#"+tableId).jqGrid('hideCol', "busimannum");
				$("#"+tableId).jqGrid('hideCol', "drawmannum");
				$("#"+tableId).jqGrid('showCol', "builderdescr");
				$("#"+tableId).jqGrid('showCol', "regiondescr");
				$("#"+tableId).jqGrid('showCol', "delivdate");
				$("#"+tableId).jqGrid('showCol', "buildertypedescr");
				$("#"+tableId).jqGrid('hideCol', "mdescr");	
				$("#"+tableId).jqGrid('hideCol', "area");		
			}else if ($("#statistcsMethod").val() == '6' ||$("#statistcsMethod").val() == '9') {
				$("#"+tableId).jqGrid('hideCol', "depart1descr");
				$("#"+tableId).jqGrid('hideCol', "number");
				$("#"+tableId).jqGrid('hideCol', "namechi");
				$("#"+tableId).jqGrid('hideCol', "depart2descr");
				$("#"+tableId).jqGrid('hideCol', "depart3descr");
				$("#"+tableId).jqGrid('hideCol', "teamdescr");
				$("#"+tableId).jqGrid('hideCol', "builderdescr");
				$("#"+tableId).jqGrid('showCol', "custtypedescr");
				$("#"+tableId).jqGrid('hideCol', "sourcedescr");
				$("#"+tableId).jqGrid('hideCol', "totalrealnum");
				$("#"+tableId).jqGrid('hideCol', "desimannum");
				$("#"+tableId).jqGrid('hideCol', "busimannum");
				$("#"+tableId).jqGrid('hideCol', "drawmannum");
				$("#"+tableId).jqGrid('hideCol', "builderdescr");
				$("#"+tableId).jqGrid('hideCol', "regiondescr");
				$("#"+tableId).jqGrid('hideCol', "delivdate");
				$("#"+tableId).jqGrid('hideCol', "buildertypedescr");
				$("#"+tableId).jqGrid('hideCol', "mdescr");	
				$("#"+tableId).jqGrid('showCol', "area");		
			}else if ($("#statistcsMethod").val() == '7' ) {
				$("#"+tableId).jqGrid('hideCol', "depart1descr");
				$("#"+tableId).jqGrid('hideCol', "number");
				$("#"+tableId).jqGrid('hideCol', "namechi");
				$("#"+tableId).jqGrid('hideCol', "depart2descr");
				$("#"+tableId).jqGrid('hideCol', "depart3descr");
				$("#"+tableId).jqGrid('hideCol', "teamdescr");
				$("#"+tableId).jqGrid('hideCol', "builderdescr");
				$("#"+tableId).jqGrid('hideCol', "custtypedescr");
				$("#"+tableId).jqGrid('showCol', "sourcedescr");
				$("#"+tableId).jqGrid('hideCol', "totalrealnum");
				$("#"+tableId).jqGrid('hideCol', "desimannum");
				$("#"+tableId).jqGrid('hideCol', "busimannum");
				$("#"+tableId).jqGrid('hideCol', "drawmannum");
				$("#"+tableId).jqGrid('hideCol', "builderdescr");
				$("#"+tableId).jqGrid('hideCol', "regiondescr");
				$("#"+tableId).jqGrid('hideCol', "delivdate");
				$("#"+tableId).jqGrid('hideCol', "buildertypedescr");
				$("#"+tableId).jqGrid('hideCol', "mdescr");	
				$("#"+tableId).jqGrid('hideCol', "area");		
			}else if ($("#statistcsMethod").val() == '8') {
				$("#"+tableId).jqGrid('hideCol', "depart1descr");
				$("#"+tableId).jqGrid('hideCol', "number");
				$("#"+tableId).jqGrid('hideCol', "namechi");
				$("#"+tableId).jqGrid('hideCol', "depart2descr");
				$("#"+tableId).jqGrid('hideCol', "depart3descr");
				$("#"+tableId).jqGrid('hideCol', "teamdescr");
				$("#"+tableId).jqGrid('hideCol', "builderdescr");
				$("#"+tableId).jqGrid('hideCol', "custtypedescr");
				$("#"+tableId).jqGrid('hideCol', "sourcedescr");
				$("#"+tableId).jqGrid('hideCol', "totalrealnum");
				$("#"+tableId).jqGrid('hideCol', "desimannum");
				$("#"+tableId).jqGrid('hideCol', "busimannum");
				$("#"+tableId).jqGrid('hideCol', "drawmannum");
				$("#"+tableId).jqGrid('hideCol', "builderdescr");
				$("#"+tableId).jqGrid('hideCol', "regiondescr");
				$("#"+tableId).jqGrid('hideCol', "delivdate");
				$("#"+tableId).jqGrid('hideCol', "buildertypedescr");
				$("#"+tableId).jqGrid('showCol', "mdescr");	
				$("#"+tableId).jqGrid('hideCol', "area");		
			}
			if ($("#role").val()==="01") {
	        	$("#dataTable").jqGrid('setLabel','namechi','业务员');	
	        }else if ($("#role").val()==="24") {
	         	$("#dataTable").jqGrid('setLabel','namechi','翻单员');	
	        }else{
	         	$("#dataTable").jqGrid('setLabel','namechi','设计师');	
	        }
			$("#builderCode").openComponent_builder();
			$("#stakeholder").openComponent_employee({condition:{"role":"00"}});
	     }     	
	});
});

function getTotalContractAmount (cellvalue, options, rowObject){ 
	
    return myRound(rowObject.chgcontractfeeamount)+myRound(rowObject.contractfeeamount);
}

function chgRole(){
	var role = $("#role").val();
	if(role == "00,01"){
		$("#statistcsMethod").val("2");
	}
}

function chgStatistcsMethod(){
	var statistcsMethod = $("#statistcsMethod").val();
	var role = $("#role").val();
	
	if(statistcsMethod != "2" && role == "00,01"){
		$("#role").val("00");
	}
}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
				<input type="hidden" id="expired" name="expired" value="F" />
				<input type="hidden" id="statisticalType" name="statisticalType" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
						<li>
							<label>一级部门</label>
							<house:DictMulitSelect id="department1" dictCode="" sql="select code,desc1 from tDepartment1 where Expired='F'" 
							sqlLableKey="desc1" sqlValueKey="code" selectedValue="${customer.department1 }" onCheck="checkDepartment1()"></house:DictMulitSelect>
						</li>
						<li>
							<label>二级部门</label>
							<house:DictMulitSelect id="department2" dictCode="" sql="select code,desc1 from tDepartment2 where 1=2" 
							sqlLableKey="desc1" sqlValueKey="code" selectedValue="${customer.department2 }" onCheck="checkDepartment2()"></house:DictMulitSelect>
						</li>
						<li>
							<label>三级部门</label>
							<house:DictMulitSelect id="department3" dictCode="" sql="select code,desc1 from tDepartment3 where 1=2" sqlLableKey="desc1" sqlValueKey="code" selectedValue="${customer.department3 }"></house:DictMulitSelect>
						</li>
						<li>
							<label>团队</label>
							<house:DataSelect id="team" className="Team" keyColumn="code" valueColumn="desc1" filterValue="Expired='F'"></house:DataSelect>
						</li>
						<li>
							<label>客户类型</label>
							<house:custTypeMulit id="custType" selectedValue="${customer.custType}" ></house:custTypeMulit>
						</li>
						<li>
							<label>项目名称</label>
							<input type="text" id="builderCode" name="builderCode" />
						</li>
						<li>
							<label>统计日期</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li><label>是否包含绘图员</label> <house:xtdm id="isContainDraftsMan" dictCode="YESNO"></house:xtdm>			
						</li>
						<li>
							<label>排行条件</label>
							<select id="orderBy" name="orderBy" >
								<option value="SumContractFee">按签单金额排行</option>
								<option value="SumPerfAmount">按合计业绩排行</option>
								<option value="SetCrtPercent">按下定率排行</option>
								<option value="SignSetPercent">按签单率排行</option>
								<option value="SignSetCount">按存单数排行</option>
							</select>
						</li>
						<li>
							<label>排行角色</label>
							<select id="role" name="role" onchange="chgRole()">
								<option value="00">按设计师排行</option>
								<option value="01">按业务员排行</option>
								<option value="24">按翻单员</option>
								<option value="00,01">设计师+业务员排行</option>
							</select>
						</li>
						<li> 
						    <label>统计方式</label>
							<select id="statistcsMethod" name="statistcsMethod" onchange="chgStatistcsMethod()">
								<option value="1">按个人</option>
								<option value="2">按一级部门</option>
								<option value="3">按二级部门</option>
								<option value="4">按团队</option>
								<option value="5">按项目名称</option>
								<option value="6">按客户类型</option>
								<option value="7">按客户来源</option>
								<option value="8">按月份</option>
								<option value="9">按客户类型-软装</option>
							</select>
					</li>
					<li>
					   <label>一级区域</label>
					   <house:xtdmMulit id="region" dictCode="" sql="select code SQL_VALUE_KEY,descr SQL_LABEL_KEY  from tRegion a where a.expired='F' " ></house:xtdmMulit>
					</li>	
					<li>
						<label>干系人</label>
						<input type="text" id="stakeholder" name="stakeholder" />
					</li>						
					<li style="width:95px">
						<input type="checkbox" id="expired_show" name="expired_show"
										value="${customer.expired}" onclick="hideExpired(this)"
										${customer.expired!='T' ?'checked':'' }/>隐藏过期&nbsp; 
					</li>				
					<li id="loadMore" >
							<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
					</li>		
			
				</ul>
			</form>
		</div>
		<div class="btn-panel" >
		       <div class="btn-group-sm"  >
               	   <house:authorize authCode="QDPH_VIEW">
               	        <button id="btnView" type="button" class="btn btn-system " onclick="view()">查看</button>	
					</house:authorize>	
	                <house:authorize authCode="QDPH_EXCEL">
	                     <button type="button" class="btn btn-system " onclick="doExcel()">导出excel</button>
					</house:authorize>
				
				</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


