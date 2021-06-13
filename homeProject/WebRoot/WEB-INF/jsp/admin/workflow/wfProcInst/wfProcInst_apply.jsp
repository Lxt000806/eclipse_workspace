<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>调整申请</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<style type="text/css">
		.SelectBG{
			background-color:white!important;
		}
		.SelectBlack{
			background-color:white!important;
			color:black!important;
		}
		.SelectBlue{
          background-color:#198ede!important;
            color:rgb(255,255,255)
            }
      .SelectBG_Blue{
          background-color:yellow;
         }
         .line_feed {
		   white-space: normal !important;
		   height:auto;
		   vertical-align:text-top;
		   padding-top:2px;
		 }
	</style>
	<style>
	.ui-jqgrid tr.jqgrow td {
	   white-space: normal !important;
	   height:auto;
	   vertical-align:text-top;
	   padding-top:2px;
	 }
	 .line_feed {
		   white-space: normal !important;
		   height:auto;
		   vertical-align:text-top;
		   padding-top:2px;
		 }
	</style>
<script type="text/javascript">
	var detailVerify;
	var badRequest = true;
	var mistakeMsg="";
	var returnNode ="";
	var returnOption = "";
	var elMaps = {};
	
	var gbOption = {imgObj:""}; //必须，固定变量名
    var filesArr=[]; //固定写法 这个就是上传能拿到的 files的集合，
    var fileClass = 'input-file'; //必须，固定变量名，值可自定义，就是input的 class自定义后 需要在这重新声明
    var imgObj1 = []; //可自定义，图片上传功能 时必须给的东西，传递到imgsUpload
    var upoad; //可自定义，上传功能时的  工具人，自定义然后传递到imgsUpload 就行
    var imgMaxNum=5;  //最多上传3张
    var ispre; ////反显  时的工具人属性，是一个对象，里面包含了 当前图片预览步骤，和图片数组。 这个最好固定，你要改就一起要改upload.js里面的
	
	//var btnView = ${btnView};
	$(function(){
		$("takeBackDateList").attr("disabled","true");
		
		// 控制图片上传 删除 按钮
		// ${m_umState == 'A' || (m_umState == 'C' && activityId =='applyman')}
		if("${m_umState}" == "A" || ("${m_umState}" == "C" && "${activityId}" == "applyman") ){
			$("#upload").show();
			$("#delPhoto").show();
		} else {
			$("#upload").hide();
			$("#delPhoto").hide();
		}
		
		//将模板删除掉
		/* var detailDiv = document.getElementById("detail_div");
		if(detailDiv != null){
			detailDiv.parentNode.removeChild(detailDiv);
		} */
		(function getDepartmentCode(){
			$.ajax({
				url:"${ctx}/admin/wfProcInst/getDeptCode",
				type:'post',
				data:{},
				dataType:'json',
				async: false, 
				cache:false,
				error:function(obj){
					$("#department_div").hide();
					showAjaxHtml({"hidden": false, "msg": '出错~'});
				},
				success:function(obj){
					if (obj){
						if(obj.length==1){
							$("#department_div").hide();
						}
						$("#department").val(obj[0].Department);
						for(var i=0;i<obj.length;i++){
					    	$("#departmentSelect").append("<option value='"+obj[i].Department+"'>"+obj[i].desc1+"</option>");
						}
						if("${m_umState}" != "A"){
							$("#department_div").hide();
						}
					}else{
						$("#department_div").hide();
					}
				}
			});
		})();
        //初始化表格
        if(typeof setElMap == "function"){
        	setElMap(); 
        }
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/wfProcInst/goJqGridByProcInstNo?wfProcInstNo=${wfProcInstNo }&type=${type }&procDefKey=${processDefinitionKey }&el="+encodeURIComponent(JSON.stringify(elMaps)),
			height:$(document).height()-$("#content-list").offset().top-180,
			colModel : [
			  {name : 'wfprocdescr',index : 'wfprocdescr',width : 150,label:'任务名称',sortable : false ,align : "left"},
			  {name : 'namechidescr',index : 'namechidescr',width : 200,label:'操作人员',sortable : false ,align : "left"},
			  {name : 'statusdescr',index : 'statusdescr',width : 90,label:'处理动作',sortable : false ,align : "left"},
			  {name : 'toactivitydescr',index : 'toactivitydescr',width : 90,label:'发送到',sortable : false ,align : "left"},
			  {name : 'remarks',index : 'remarks',width : 300,label:'说明',sortable : false ,align : "left"},
			  {name : 'lastupdate',index : 'lastupdate',width : 130,label:'时间',sortable : false ,align : "left",formatter:formatTime},
			  {name : 'fromactivityid',index : 'fromactivityid',width : 300,label:'fromactivityid',sortable : false ,align : "left",hidden:true},
			  {name : 'taskdefkey',index : 'taskdefkey',width : 300,label:'taskdefkey',sortable : false ,align : "left",hidden:true},
		    ],
		    gridComplete:function(){
	        	if("function" == typeof getOperator_ && "${m_umState}" != "A"){
	        		getOperator_("firstIn");//加载审批人表，得在审批记录表之后加载 否则会导致分组失败的情况
	        	}
	        	
	        	var tr = $("#dataTable td[aria-describedby='dataTable_remarks']");
	        	if(tr.length > 0 ){
		        	var cssText = $("#dataTable td[aria-describedby='dataTable_remarks']")[0].style.cssText ;
		        	cssText += " white-space: normal !important;   height:auto;   vertical-align:text-top;   padding-top:2px;";
	        		for(var i = 0; i < tr.length; i++){
	   	        		$("#dataTable td[aria-describedby='dataTable_remarks']")[i].style.cssText = cssText;
	        		}
	        	}
	        	
			},
		});
		
		Global.JqGrid.initJqGrid("dataTableManageCzy",{
			height:$(document).height()-$("#content-list").offset().top-180,
			colModel : [
			  {name : 'taskKey',index : 'taskKey',width : 120,label:'taskKey',sortable : true,align : "left",hidden:true},
			  {name : 'assignee',index : 'assignee',width : 120,label:'审批人编码',sortable : true,align : "left",hidden:true},
			  {name : 'groupDescr',index : 'groupDescr',width : 120,label:'审批人分组',sortable : true,align : "left",hidden:true},
			  {name : 'namechidescr',index : 'namechidescr',width : 250,label:'审批人',sortable : true,align : "right",},
			  {name : 'oldnamechidescr',index : 'oldnamechidescr',width : 250,label:'原审批人',sortable : true,align : "right",hidden:true},//原本的  当删除时，的内容等于原本的 则不让删除
			  {name : 'selectBtn',index : 'selectBtn',width : 65,label:'  ',sortable : true,align : "center",formatter:DiyFmatter},
			  {name : 'deleteBtn',index : 'deleteBtn',width : 65,label:'  ',sortable : true,align : "center",formatter:DelFmatter,hidden:true},
			  {name : 'Department',index : 'Department',width : 65,label:'Department',sortable : true,align : "center",hidden:true},
			  {name : 'saveFlag',index : 'saveFlag',width : 65,label:'saveFlag',sortable : true,align : "center",hidden:true,},
		    ],
		    gridComplete:function(){
	        	//1.设置选中不变蓝   
				$("#dataTableManageCzy").find("#1").find("td").addClass("SelectBlack");
				$("#dataTableManageCzy").find("td").css("line-height","27px");
			},
			grouping: true, // 是否分组,默认为false
		    groupingView: {
	          groupField: ['groupDescr'], // 按照哪一列进行分组
	          groupColumnShow: [false], // 是否显示分组列
	          groupText: ['<b>{0}({1})</b>'], // 表头显示的数据以及相应的数据量
	          groupCollapse: false, // 加载数据时是否只显示分组的组信息
	          groupDataSorted: true, // 分组中的数据是否排序
	          groupOrder: ['asc'], // 分组后的排序
	          groupSummary: [false], // 是否显示汇总.如果为true需要在colModel中进行配置
	          summaryType: 'max', // 运算类型，可以为max,sum,avg</div>
	          summaryTpl: '<b>Max: {0}</b>',
	          showSummaryOnHide: true //是否在分组底部显示汇总信息并且当收起表格时是否隐藏下面的分组
	        },
	        
	        onCellSelect: function(id,iCol,cellParam,e){ //设置选中后不变蓝
				var ids = $("#dataTableManageCzy").jqGrid("getDataIDs");  
				for(var i=0;i<ids.length;i++){
					if(i!=id-1){
						$("#dataTableManageCzy").find(ids[i]).find("td").removeClass("SelectBG");
						$("#dataTableManageCzy").find('#'+ids[id-1]).find("td").addClass("SelectBlack");
					}
				}
				$("#dataTableManageCzy").find('#'+ids[id-1]).find("td").addClass("SelectBlack");
			},
		});
		//当需要选择执行人时  格式化选择按钮
		function DiyFmatter (cellvalue, options, rowObject){ 
		    var val=cellvalue;
		    if(cellvalue == "option"){
		    	val="";
		    	val='<button type=\"button\" style=\"height:27px\;font-size:10px \" onclick=\"selectCzy('+'\''+rowObject.orderby+'\''+ ','+'\''+rowObject.taskKey +'\''+',\'option\''+')\" class=\"btn btn-system\">选择</button>';
		    }else if(cellvalue == "copy"){
		   		val="";
		    	val='<button type=\"button\" style=\"height:27px\;font-size:10px \" onclick=\"updateCopyCzy(\''+rowObject.namechidescr+'\',\''+rowObject.oldnamechidescr+'\',\''+rowObject.assignee+'\',\''+rowObject.orderby+'\')\" class=\"btn btn-system\">管理</button>';
		    }
		    return val;
		}
		
		function DelFmatter (cellvalue, options, rowObject){ 
		    var val=cellvalue;
		    if(cellvalue == "copy"){
		   		val="";
		    	val='<button type=\"button\" style=\"height:27px\;font-size:10px \" onclick=\"deleteCzy('+rowObject.orderby+',\'copy\')\" class=\"btn btn-system\">删除</button>';
		    }
		    return val;
		}
		
	});
	//选择按钮 触发事件
	function selectCzy(rowId,taskKey,taskId){
		Global.Dialog.showDialog("selectCzy",{
			title:"选人",
			url:"${ctx}/admin/employee/goCode",
			postData:{wfProcNo:"${wfProcNo }",taskKey:taskKey},
			height: 680,
			width:1000,
			returnFun:function(data){
			  	if(data){	
			  		if(taskId=="option"){
				   		$("#dataTableManageCzy").jqGrid('setCell',myRound(rowId)+1,"namechidescr",data.namechi); 
				    	$("#dataTableManageCzy").jqGrid('setCell',myRound(rowId)+1,"assignee",data.czybh); 
			  		}else if(taskId == "copy"){
			  			if($("#dataTableManageCzy").getCell(rowId+1,"namechidescr") != ""){
				    		$("#dataTableManageCzy").jqGrid('setCell',myRound(rowId)+1,"namechidescr",$("#dataTableManageCzy").getCell(rowId+1,"namechidescr")+"/"+data.namechi); 
			  			}else{
				    		$("#dataTableManageCzy").jqGrid('setCell',myRound(rowId)+1,"namechidescr",data.namechi); 
			  			}
			  			if($("#dataTableManageCzy").getCell(rowId+1,"assignee") != ""){
				    		$("#dataTableManageCzy").jqGrid('setCell',myRound(rowId)+1,"assignee",$("#dataTableManageCzy").getCell(rowId+1,"assignee")+","+data.czybh); 
			  			}else{
				    		$("#dataTableManageCzy").jqGrid('setCell',myRound(rowId)+1,"assignee",data.czybh); 
			  			}
			  		}
			  	}
			} 
		});
	}
	
	function updateCopyCzy(nameChi,oldNameChi,assignee,orderBy){
		Global.Dialog.showDialog("UpdateCopyCzy", {
			title: "管理抄送人",
			url: "${ctx }/admin/wfProcInst/goUpdateCopyCzy",
			postData:{nameChi:$("#dataTableManageCzy").getCell(myRound(orderBy)+1,"namechidescr"),oldNameChi:oldNameChi,assignee:$("#dataTableManageCzy").getCell(myRound(orderBy)+1,"assignee"),},
			height: 500,
			width: 680,
			returnFun:function(data){
				if(data[0] && data[1]){
					$("#dataTableManageCzy").jqGrid('setCell',myRound(orderBy)+1,"namechidescr",data[1]); 
					$("#dataTableManageCzy").jqGrid('setCell',myRound(orderBy)+1,"assignee",data[0]); 
				}
			}
	    });
	}
	
	function deleteCzy(rowId,taskId){
		if($("#dataTableManageCzy").getCell(rowId+1,"namechidescr").trim()=="" 
				|| $("#dataTableManageCzy").getCell(rowId+1,"namechidescr").trim() == $("#dataTableManageCzy").getCell(rowId+1,"oldnamechidescr").trim()){
			return ;
		}else{
			var descrObj = $("#dataTableManageCzy").getCell(rowId+1,"namechidescr").split('/');
			var numObj = $("#dataTableManageCzy").getCell(rowId+1,"assignee").split(',');
			if(descrObj.length == 1 ){
				$("#dataTableManageCzy").jqGrid('setCell',myRound(rowId)+1,"namechidescr","&nbsp"); 
			}else{
				$("#dataTableManageCzy").jqGrid('setCell',myRound(rowId)+1,"namechidescr",
				$("#dataTableManageCzy").getCell(rowId+1,"namechidescr").trim().split("/"+descrObj[descrObj.length-1])[0]);
			}
			if(numObj.length == 1){
				$("#dataTableManageCzy").jqGrid('setCell',myRound(rowId)+1,"assignee","&nbsp"); 
			}else{
				$("#dataTableManageCzy").jqGrid('setCell',myRound(rowId)+1,"assignee",
				$("#dataTableManageCzy").getCell(rowId+1,"assignee").trim().split(","+numObj[numObj.length-1])[0]); 
			}
		}
	}
	
	function save(flag){
  		if("A" == flag){
  			if("function" == typeof getMistakeMsg ){
   				var resMap = getMistakeMsg();
   				if(resMap.badRequest_ == true){
   					art.dialog({
   						content: resMap.mistakeMsg_,
   					});
   					return;
   				} else {
   					doApply();
   				}
  			}else {
	  			doApply();//申请
  			}
  			
  		}else if("true" == flag){
  			doPass();//同意
  		}else if("false" == flag){
  			doReject();//拒绝
  		}else if("cancel" == flag){
	  		art.dialog({
				content:"是否确定撤销",
				lock: true,
				width: 200,
				height: 100,
				ok: function () {
  					doCancel();//撤销
				},
				cancel: function () {
						return true;
				}
			});
  		}else{
  			return;
  		}
  	}
  	
  	function doApply(){
  		$("#dataForm").bootstrapValidator('validate');
  		if(!$("#dataForm").data('bootstrapValidator').isValid()){
//   			补充点击申请时不在基本信息页跳转机制，方便直观看到错误提示
  			if($("#wfProcInstApplyTabsUl li[class='active']").attr("id") != "baseInfoLi"){
				$("#baseInfoLi > a").tab("show");
  			}
  			return;
  		}
		var datas = $("#dataForm").serialize();
		if(!verifyDetail()){
			return;
		}
		
  		if(badRequest){
  			art.dialog({
  				content:mistakeMsg,
  			});
  			return;
  		}
		var param= Global.JqGrid.allToJson("dataTableManageCzy");
		if(param.datas){
			var mistake=false;
			for(var i=0;i<param.datas.length;i++){
				if(param.datas[i].taskKey != "" && param.datas[i].assignee == "" && param.datas[i].saveFlag=="option"){
					art.dialog({
						content: "请选择审批人！",
						time: 1000,
						beforeunload: function () {
							var li = document.querySelectorAll("li");
							li[0].className="";
							li[1].className="active";
							li[2].className="";
							li[3].className="";
							li[4].className="";
							li[5].className="";
							li[6].className="";
							li[7].className="";
							$("#tab_apply_manageCzy").addClass("tab-pane fade in active");
							document.getElementById("tab_apply_djxx").className='tab-pane fade';
							document.getElementById("tab_apply_spjl").className='tab-pane fade';
							document.getElementById("tab_apply_lct").className='tab-pane fade';
					    }
					});
					mistake = true;
					break;
				}
			}
			if(mistake==true){
				return;
			}
		}
		Global.Form.submit("dataForm","${ctx}/admin/wfProcInst/doApply",param,function(ret){
			if(ret.rs==true){
				if("function" == typeof saveAccount){
					saveAccount();
				}
				art.dialog({
					content:ret.msg,
					time:1000,
					beforeunload:function(){
						closeWin();
					}
				});				
			}else{
				$("#_form_token_uniq_id").val(ret.token.token);
				art.dialog({
					content:ret.msg,
					width:200
				});
			}
		});
  	}
  	
  	//同意
  	function doPass(){
  		if(!verifyDetail()){
  			return;
		}
  		art.dialog({
   			unShowOkButton:true,
   			title: "请输入同意意见",
   			padding: "0",
   			width: "666px",
   			height: "150px",
   			content: "<div style=\"padding-left:5px;padding-top:5px;padding-right:5px;\"><textarea id=\"artDialogComment\" style=\"width:666px;height:150px\">"+$("#comment").val()+"</textarea></div>",
   			lock: true,
   			ok: function(){
   				if("function" == typeof doApprovalTask_ ){
	   				doApprovalTask_();
   				}else{
					doApprovalTask();
   				}
   			},
   			cancel: function(){
   				filesArr=[];
   				imgObj1 = [];
   				$(".d-buttons").height("30");
   			}
   		});
   		
   		$(".d-buttons").append(
   		"<div id=\"uploadImgs\" style=\"margin-bottom: 20px;float:left;\">"+
        "	<label for=\"fileUpIpt\" id=\"labelId\">"+
        "		<div class=\"uploadImgs-add\">"+
        "<div class=\"\" style=\"width: 80px; height: 80px; border: 1px dotted #7c7c7c; font-size: 50px; color: #7c7c7c; text-align: center; line-height: 10px; cursor: pointer;\">"+
	    "	<div style=\"font-size:14px;margin-top:10px;margin-left:8px;color:rgb(200,200,200)\">上传附件</div>"+
	    "	<br>"+
	    "	<br>"+
		"	<div style=\"padding-left:28px;padding-top:13px;font-size:18px\">┿</div>"+
	    "	<br>"+
		"	<span style=\"font-size:18px\"></span>"+
		"</div>"+
        "</div>"+
        "	</label>"+
        "	<div class=\"type-file-div\">"+
        "		<input type=\"file\" class=\"input-file\" name='luTest[]' style=\"display: none;\">"+
        "	</div>"+
    	"</div>"
    	);
    	$(".d-buttons").height("111");
   		$(".d-buttons").append("<input type='button' class='d-button d-state-highlight' style='float:bottom;margin-top:90px' value='确定' id='doPassProc' onclick='doPassProc()'/>");
  		 imgsUpload(imgObj1,upoad,imgMaxNum); //上传时调用
       	 createPreDiv(); //创建用于图片预览的DIV结构，上传调用
	}
	
	function doPassProc(){
		$("#comment").val($("#artDialogComment").val());
  		var api = artDialog.focus;
		if(filesArr.length > 0){
			var i = filesArr.length;
			//for(var i = 0; i< filesArr.length; i++){
			showTimer = setInterval(function () {
				if(i == 0){
					clearInterval(showTimer);
  					$(".d-buttons").height("30");
					if("function" == typeof doApprovalTask_ ){
			  			doApprovalTask_();
					}else{
						doApprovalTask();
			 		}
			 		return;
				}
				i--;
				var fd = new FormData();
				fd.append("file", filesArr[i]);
           		$.ajax({
	                url: "${ctx}/admin/wfProcInst/uploadWfProcPic?wfProcInstNo=${wfProcInstNo }",
	                type: "POST",
	                data: fd,
	                processData: false,
	                contentType: false,
	                success: function (res) {
	               		console.log(res);
	                }, 
	                error: function (res) {
	               		console.log(res);
					}
				});
			}, 100);
			//}
			
			
		} else {
  			$(".d-buttons").height("30");
			if("function" == typeof doApprovalTask_ ){
	  			doApprovalTask_();
			}else{
				doApprovalTask();
	 		}
		}
 		api._click("ok");
	}
  	
  	function doApprovalTask(){
		$("#dataForm input[type='text']").removeAttr("disabled","true");
		$("#dataForm select").removeAttr("disabled","true");
		var datas = $("#dataForm").serialize();
		
  		$.ajax({
  			url:"${ctx}/admin/wfProcInst/doApprovalTask",
  			type: 'post',
  			data: datas,
  			dataType: 'json',
  			cache: false,
  			error: function(obj){
  				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
  		    },
  		    success: function(obj){
  		    	if(obj.rs){
  		    		if("function" == typeof saveAccount && "${activityId}" =="applyman"){
  						saveAccount();
  					}
      				$("#_form_token_uniq_id").val(obj.datas.token);
  		    		art.dialog({
  						content: obj.msg,
  						time: 1000,
  						beforeunload: function () {
  							closeWin();
  					    }
  					});
  		    	}else{
  		  			$("#dataForm input[type='text']").attr("disabled","true");
	  				$("#dataForm select").attr("disabled","true");
  		    		$("#_form_token_uniq_id").val(obj.token.token);
  		    		art.dialog({
  						content: obj.msg,
  						width: 200
  					});
  		    	}
  		    }
  		});
  	}
  	
  	//不同意
  	function doReject(){
  		//$("#dataForm").bootstrapValidator('validate');
  		//if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
   		art.dialog({
   			unShowOkButton:true,
   			title: "请输入退回意见",
   			padding: "0",
   			width: "420px",
   			height: "250px",
   			content: "<textarea id=\"artDialogComment\" style=\"width:420px;height:250px\">"+$("#comment").val()+"</textarea>",
   			lock: true,
   			ok: function(){
   				if(returnNode == ""){
					art.dialog({
						content:"请选择退回节点",	
					});
					return;  		
		  		}
   				$("#comment").val($("#artDialogComment").val());
				var datas = $("#dataForm").serialize();
				datas= datas+"&"+"returnNode="+returnNode;
				$.ajax({
		  			url:"${ctx}/admin/wfProcInst/doRejectTask",
		  			type: 'post',
		  			data: datas,
		  			dataType: 'json',
		  			cache: false,
		  			error: function(obj){
		  				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		  		    },
		  		    success: function(obj){
		  		    	if(obj.rs){
		      				$("#_form_token_uniq_id").val(obj.datas.token);
		  		    		art.dialog({
		  						content: obj.msg,
		  						time: 1000,
		  						beforeunload: function () {
		  							closeWin();
		  					    }
		  					});
		  		    	}else{
		  		    		$("#_form_token_uniq_id").val(obj.token.token);
		  		    		art.dialog({
		  						content: obj.msg,
		  						width: 200
		  					});
		  		    	}
		  		    }
		  		});
   			},
   			cancel: function(){
   				returnNode="";
   				returnOption = "";
   			}
   		});
   		$(".d-buttons").append("<div style='float:left' id='returnOptionDiv'>"
   			+"<select style='width:100px;' onchange='returnOptionSel()' id='returnOptionId' >"
   			+"<option value=''>请选择...</option><option value='1'>撤销</option><option value='2'>发起人</option>"
   			+"<option value='3'>上一执行人</option><option value='4'>自选</option><select></div>");
   		$(".d-buttons").append("<input type='button' class='d-button d-state-highlight' style='fload:right' value='确定' onclick='closeDialog()'/>");
  	}
  	
  	function closeDialog(){
  		var api = artDialog.focus;
		if(returnNode == ""){
			art.dialog({
				content:"请选择退回节点",	
			});
			return;  		
  		}
		$("#comment").val($("#artDialogComment").val());
		var datas = $("#dataForm").serialize();
		datas= datas+"&"+"returnNode="+returnNode;
		$.ajax({
  			url:"${ctx}/admin/wfProcInst/doRejectTask",
  			type: 'post',
  			data: datas,
  			dataType: 'json',
  			cache: false,
  			error: function(obj){
  				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
  		    },
  		    success: function(obj){
  		    	if(obj.rs){
      				$("#_form_token_uniq_id").val(obj.datas.token);
  		    		art.dialog({
  						content: obj.msg,
  						time: 1000,
  						beforeunload: function () {
  							closeWin();
  					    }
  					});
  		    	}else{
  		    		$("#_form_token_uniq_id").val(obj.token.token);
  		    		art.dialog({
  						content: obj.msg,
  						width: 200
  					});
  		    	}
  		    }
  		});
  		api._click("ok");
  	}
  	
  	function returnOptionSel(){
  		var exNode="";
  		returnNode ="";
  		returnOption = $("#returnOptionId").val();
		var detailJson = Global.JqGrid.allToJson("dataTable");
		var delDiv = document.getElementById("returnNodeId");
		if(delDiv != null){
			delDiv.parentNode.removeChild(delDiv);
		}
  		if(returnOption =="4"){
  			var html = "<select style='width:100px;margin-left:10px' id='returnNodeId' onchange='returnNodeSel()'><option value=''>请选择...</option>";
  			var sonHtml = "";
  			for(var i=0;i<detailJson.datas.length;i++){
				if(detailJson.datas[i].taskdefkey =="applyman"){
					sonHtml ="";
				}
  				if((detailJson.datas[i].taskdefkey != "" && sonHtml.indexOf("'"+detailJson.datas[i].taskdefkey+"'", 0) == -1)
  				  || (detailJson.datas[i].toactivitydescr != "" && detailJson.datas[i].taskdefkey =="")){
  					sonHtml=sonHtml+"<option value='"+detailJson.datas[i].taskdefkey+"'>"+detailJson.datas[i].wfprocdescr+"</option>";
  				}
  				if(i==detailJson.datas.length-1){
  					exNode = detailJson.datas[i].taskdefkey;
  				}
  			}
  			html=html + sonHtml + "</select>";
	  		$("#returnOptionDiv").append(html);
  		}
  		
  		if(returnOption == "1"){
  			if("${activityId}" =="applyman"){
  				returnNode = "cancel";
  			} else {
  				returnNode = "reject";
  			}
  		}
  		
  		if(returnOption == "2"){
  			var html = "<span id='returnNodeId'>&nbsp&nbsp"+detailJson.datas[0].namechidescr+"</span>";
  			$("#returnOptionDiv").append(html);
			returnNode = "applyman";
  		}

		if(returnOption == "3"){
			var html = "";
  			$("#returnOptionDiv").append(html);
			for(var i=detailJson.datas.length-1;i>=0;i--){
				if(detailJson.datas[i].taskdefkey != "" ){
					returnNode = detailJson.datas[i].taskdefkey;
					html = "<span id='returnNodeId'>&nbsp&nbsp"+"("+detailJson.datas[i].wfprocdescr+")"+detailJson.datas[i].namechidescr+"</span>";
  					$("#returnOptionDiv").append(html);
					return;
				}
			}
		}  		
  	}
  	
  	function returnNodeSel(){
  		returnNode = $("#returnNodeId").val();
  	}
  	
  	//撤销
  	function doCancel(){
  		$("#dataForm").bootstrapValidator('validate');
  		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var datas = $("#dataForm").serialize();
  		$.ajax({
  			url:"${ctx}/admin/wfProcInst/doCancelProcInst",
  			type: 'post',
  			data: datas,
  			dataType: 'json',
  			cache: false,
  			error: function(obj){
  				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
  		    },
  		    success: function(obj){
  		    	if(obj.rs){
      				$("#_form_token_uniq_id").val(obj.datas.token);
  		    		art.dialog({
  						content: obj.msg,
  						time: 1000,
  						beforeunload: function () {
  							closeWin();
  					    }
  					});
  		    	}else{
  		    		$("#_form_token_uniq_id").val(obj.token.token);
  		    		art.dialog({
  						content: obj.msg,
  						width: 200
  					});
  		    	}
  		    }
  		});
  	}
	
	//获取审批人
	function getOperator(flag,elMap){
		/* if(flag =="firstIn"){
			return;
		} */
		$("#dataTableManageCzy").jqGrid("clearGridData");
		$.ajax({
			url:"${ctx}/admin/wfProcInst/getOperator",
			type:"post",	
			data:{el:JSON.stringify(elMap),pdID:"${processDefinitionKey }",department:$("#department").val(),wfProcNo:"${wfProcNo }"},
			dataType:"json",
			cache:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": "出错~"});
			},
			success:function(obj){
				if (obj){
					if(obj.length>0){
						badRequest=false;
						mistakeMsg="页面信息有误，无法提交";
						badRequest=false;
						chgOperatorJqgrid(elMap,obj,flag);
					}else{
						badRequest=true;
						mistakeMsg=obj.msg;
						document.getElementById("iframe_design").src="${ctx }/admin/wfProcInst/getResource?resourceType=image&processDefinitionId=${processDefinitionKey}";
					}
				}
			}
		});
	}
	
	//改变审批人页签
	function chgOperatorJqgrid(elMap,obj,flag){
		if(flag != "firstIn"){
			document.getElementById("iframe_design").src="${ctx }/admin/wfProcInst/getTraceImageApply?el="+encodeURIComponent(JSON.stringify(elMap))+"&pdID="+"${processDefinitionKey }";
		}
		for(var i=0;i<obj.length;i++){
			if("object"!=typeof obj[i]){
				if(obj[i].split("__")[1].split("选错部门,直接主管不能为自己").length>1){
					badRequest = true;
					mistakeMsg ="选错部门,直接主管不能为自己";
				}
				var groupName = obj[i].split("__")[0];
				if(groupName == "抄送"){
					Global.JqGrid.addRowData("dataTableManageCzy",{orderby:i,groupDescr:obj[i].split("__")[0],namechidescr:obj[i].split("__")[1],oldnamechidescr:obj[i].split("__")[1],selectBtn:"copy",taskKey:"",deleteBtn:"copy"});
				}else{
					Global.JqGrid.addRowData("dataTableManageCzy",{orderby:i,groupDescr:obj[i].split("__")[0],namechidescr:obj[i].split("__")[1],selectBtn:"",taskKey:"",deleteBtn:""});
				}
			}else{
				if(obj[i][0].split("__")[0]=="抄送"){
					Global.JqGrid.addRowData("dataTableManageCzy",{assignee:"",orderby:i,groupDescr:obj[i][0].split("__")[0],namechidescr:obj[i][0].split("__")[1],oldnamechidescr:obj[i][0].split("__")[1],selectBtn:"copy",taskKey:obj[i][1],deleteBtn:"copy",saveFlag:"copy"});
				}else if (obj[i][0].split("__")[0]=="单据接收人"){
					Global.JqGrid.addRowData("dataTableManageCzy",{assignee:"",orderby:i,groupDescr:obj[i][0].split("__")[0],namechidescr:obj[i][0].split("__")[1],oldnamechidescr:obj[i][0].split("__")[1],selectBtn:"copy",taskKey:obj[i][1],deleteBtn:"copy",saveFlag:"copy"});
				} else {
					Global.JqGrid.addRowData("dataTableManageCzy",{orderby:i,groupDescr:obj[i][0],namechidescr:"请选择...",selectBtn:"option",taskKey:obj[i][1],deleteBtn:"",saveFlag:"option"});
				}
			}
		}
		var params = JSON.stringify($("#dataTableManageCzy").jqGrid("getRowData"));
        $("#dataTableManageCzy").jqGrid("setGridParam", {
          url: "${ctx}/admin/itemChgDetail/goTmpJqGrid",
          postData: {'params': params, 'orderBy': 'orderby'},
          page: 1,
          grouping: true,
          gridComplete:function(){
          	$("#dataTableManageCzy").find("#1").find("td").addClass("SelectBlack");
			$("#dataTableManageCzy").find("td").css("line-height","27px");
			if(typeof operatorCallBack == "function"){
				operatorCallBack();
			}
		  },
        }).trigger("reloadGrid");
        if(!flag ||flag ==""){
	        $("#dataTable").jqGrid("setGridParam",{
				url:"${ctx}/admin/wfProcInst/goJqGridByProcInstNo?wfProcInstNo=${wfProcInstNo }&type=${type }&procDefKey=${processDefinitionKey }&elMap="+encodeURIComponent(JSON.stringify(elMap)),
		       	postData:{},
		       	page:1,
		       	sortname:'',
			}).trigger("reloadGrid");
        }
	}
	
	//添加明细块
	function addDetail(flag,html,inx,fn,groupId){
		var len = 20;
		if(inx >= 0){
			len = inx;		
		}
		oldhtml = html;
		for(var i=0 ; i <= len; i++){
			html = oldhtml;
			var div = $("#"+groupId).find("#"+groupId+"_"+i);
			if(div.length == 0 ){
				if(i != 0){
					html = html.replace("addDetail_('add',-1,'"+groupId+"')","delDetail(this,'"+groupId+"')").replace("增加明细","删除明细");
				}
				if(flag == "add" && i>19){
					art.dialog({
						content:"只允许添加20条明细",
					});
					return;
				}
				html = html.replace(/temInx/g,i).replace(groupId+"_div",groupId+"_"+i);
				$("#"+groupId).append(html);
				$("#"+groupId+"_"+i).removeAttr("hidden",true);
				$("#"+groupId+"_"+i).removeAttr("disabled",true);
				if(fn){
					returnFn(flag,i,groupId);
				}
					//不是发起 也不是 查看我发起的 就移除掉 新增明细 删除明细按钮
				if("${m_umState}" !="A" && flag != "add"){
					var btn = document.getElementById("btn_"+i);
					if(btn != null){
						$("#btn_"+i).hide();
						//btn.parentNode.removeChild(btn);
					}
					/* var groupBtn = document.getElementById(groupId+"_btn_"+i);
					if(btn != null){
						$("#"+groupId+"_btn_"+i).hide();
					} */
				}
				if(flag == "add"){
					return;
				}
			}
		}
		return;
	}
	
	function chgDept(e){
		$("#department").val(e.value);
		getOperator_("");
	}
	
	//删除单条明细  e:在HTML中的this
	function delDetail(e,groupId){
		var inx = e.id.split("btn_")[1];
		art.dialog({
			content:"是否删除",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				doDelDetail(inx,groupId);
			},
			cancel: function () {
				return true;
			}
		});	
	}
	
	//删除下标为0以外的所有明细
	function delAllDetail(groupId){
		for(var i=1;i<20;i++){
			doDelDetail(i,groupId);
		}
	}
	
	function doDelDetail(index,groupId){
		var delDiv = document.getElementById(groupId+"_"+index);
		if(delDiv != null){
			delDiv.remove();
		}
		if("function" == typeof delDetalReturnFn){
			delDetalReturnFn(index, groupId);
		}
	}
	
	function ReApply(showFlag){
		$("#upload").show();
		$("#delPhoto").show();
		$("#reApplyBtn").css("display", showFlag ? "none" : "");
		$("#cancelBtn").css("display", showFlag ? "none" : "");
		$("#reApplyYesBtn").css("display", showFlag ? "" : "none");
		$("#reApplyNoBtn").css("display", showFlag ? "" : "none");
		$("#checkCzyLi").css("display", showFlag ? "" : "none");
		$("#checkProgressLi").css("display", showFlag ? "none" : "");
		$("#dataTablePic").clearGridData();
		$("#dataForm select").removeAttr("disabled","true");
		$("#wfProcInstNo").val("");
		var activeLiId = $("#wfProcInstApplyTabsUl li[class='active']").attr("id");
		$("#activityId").val("startevent");
		if(activeLiId == "checkProgressLi"){
			$("#checkCzyLi > a").tab("show");
		}else if(activeLiId == "checkCzyLi"){
			$("#checkProgressLi > a").tab("show");
		}
		if("function" == typeof callSetReadonly){
			callSetReadonly("startevent");
		}
	}
	
	function print(){
		var wfProcInstNo=$.trim("${wfProcInstNo }");
		var wfProcNo = $.trim("${wfProcNo }");
		console.log(wfProcNo);
		var opt = {
			No:wfProcInstNo,
			wfProcInstNo: wfProcInstNo,
			applyMan:"${printMan }",
			LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
			SUBREPORT_DIR: "<%=jasperPath%>" 
		}
		var jasperName="wfProcInst.jasper";
		var jasperNameObj = { // 包含所有使用单独流程报表的对象
				"017":"wfExpenseAdvance.jasper",
				"018":"wfExpenseClaim.jasper,wfExpenseClaimPayDetail.jasper",
				"023":"wfMarketExpenseClaim.jasper",
				"024":"wfTraverExpenseClaim.jasper",
				"026":"wfConstructionClaim.jasper",
				"029":"purchaseAdvance.jasper",
				"030":"purchaseExpense_main.jasper",
				"032":"wfProcAssetChg.jasper",
			}
		if(wfProcNo == "018"){
			if($.trim("${datas.fp__tWfCust_ExpenseClaimPayDtl__1__PK }") == ""){
				jasperNameObj["018"] = "wfExpenseClaim.jasper";
			}
		}
		if(jasperNameObj.hasOwnProperty(wfProcNo)){
			jasperName=jasperNameObj[wfProcNo];
		}
		Global.Print.showPrint(jasperName, opt, null,
		function(){
			art.dialog({
				content:"是否已打印报表?",
				ok:function(){
					updatePrint(wfProcInstNo);
				},
				cancel:function(){},
				okValue:"是",
				cancelValue:"否"
			});
		});
	}
	
	function doComment(){
		art.dialog({
   			title: "请输入评论内容",
   			padding: "0",
   			width: "250px",
   			height: "250px",
   			content: "<div style=\"padding-left:5px;padding-top:5px;padding-right:5px;\"><textarea id=\"empCommentDialog\" style=\"width:500px;height:250px\">"+$("#empComment").val()+"</textarea></div>",
   			lock: true,
   			ok: function(){
   				$("#empComment").val($("#empCommentDialog").val());
   				var datas = $("#dataForm").serialize();
		  		$.ajax({
		  			url:"${ctx}/admin/wfProcInst/doComment",
		  			type: 'post',
		  			data: {no:"${wfProcInstNo }",empComment:$("#empComment").val()},
		  			dataType: 'json',
		  			cache: false,
		  			error: function(obj){
		  				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		  		    },
		  		    success: function(obj){
		  		    	if(obj.rs){
		      				$("#_form_token_uniq_id").val(obj.datas.token);
		  		    		art.dialog({
		  						content: obj.msg,
		  						time: 1000,
		  						beforeunload: function () {
		  					    }
		  					});
		  		    	}else{
		  		    		$("#_form_token_uniq_id").val(obj.token.token);
		  		    		art.dialog({
		  						content: obj.msg,
		  						width: 200
		  					});
		  		    	}
		  		    }
		  		});
   			},
   			cancel: function(){}
   		});
	}
$(function(){
	var gridOptionPic = {	
		height:$(document).height()-$("#content-list").offset().top-82,
		multiselect:true,
		colModel : [
			{name:"pk",	index:"pk",	width:90,	label:"PK",	sortable:true,align:"left",hidden:true},
			{name:"photoname",	index:"photoname",	width:130,	label:"图片名称",	sortable:true,align:"left",},
			{name:"lastupdate",	index:"lastupdate",	width:95,	label:"上传时间",	sortable:true,align:"left" ,formatter:formatDate},
			{name:"lastupdatedby",	index:"lastupdatedby",	width:95,	label:"上传人员",	sortable:true,align:"left" ,},
			{name:"lastupdatedbydescr",	index:"lastupdatedbydescr",	width:95,	label:"上传人员",	sortable:true,align:"left" ,},
		],
		onCellSelect: function(id,iCol,cellParam,e){
			var ids = $("#dataTablePic").jqGrid("getDataIDs");  
			var custCode =$.trim($("#code").val());
			var photoname = Global.JqGrid.allToJson("dataTablePic","photoname");
			var url = $.trim($("#url").val());
			var arr = new Array();
				arr = photoname.fieldJson.split(",");
			var arry = new Array();
				arry = arr[id-1].split(".");
			var sufName = arry[1].toLowerCase();
			if(sufName=="png" || sufName == "jpg" || sufName == "gif" || sufName == "jpeg" || sufName == "bmp"){//jpg/gif/jpeg/png/bmp 显示预览
				document.getElementById("docPic").src =url + arr[id-1];	
				document.getElementById("docPic_a").href =url + arr[id-1];	
			}else{
				document.getElementById("docPic").src ="";	
				document.getElementById("docPic_a").href ="";	
			}
			for(var i=0;i<ids.length;i++){
				if(i!=id-1){
					$("#dataTablePic").find("#"+ids[i]).find("td").removeClass("SelectBlue");
				}
			}
			$("#dataTablePic").find("#"+ids[id-1]).find("td").addClass("SelectBlue");
		},
	}
	$.extend(gridOptionPic,{
		url:"${ctx}/admin/wfProcInst/findWfProcInstPic",
		postData:{wfProcInstNo:"${wfProcInstNo }",photoPK:$("#photoPK").val()},
	});
	Global.JqGrid.initEditJqGrid("dataTablePic",gridOptionPic);
	
	$("#download").on("click",function(){
		var docName = Global.JqGrid.allToJson("dataTablePic","photoname");
		var arr = new Array();
			arr = docName.fieldJson.split(",");
        var ids = $("#dataTablePic").jqGrid('getGridParam', 'selarrrow');
		var docNameArr = new Array();
		var i=0;
		if(ids.length==0){
        	Global.Dialog.infoDialog("请选择下载记录！");	
        	return;
       	}
		for(var i=0;i<ids.length;i++){
			docNameArr[i]=arr[ids[i]-1];
		}
		window.open("${ctx}/admin/wfProcInst/download?docNameArr="+docNameArr);
	});
	
	$("#upload").on("click",function(){
		Global.Dialog.showDialog("upload",{
			title:"文件上传",
			url:"${ctx}/admin/wfProcInst/goUpload",
			postData:{wfProcInstNo:$("#wfProcInstNo").val()},
			height:550,
			width:950,
			returnFun:function(data){
				if(data!=""){
					if($("#photoPK").val()==""){
						$("#photoPK").val(data);
					}else{
						var pkList = $("#photoPK").val();
							pkList = pkList+","+data;
						 $("#photoPK").val(pkList);
					}
					$("#dataTablePic").jqGrid("setGridParam",{url:"${ctx}/admin/wfProcInst/findWfProcInstPic",
							postData:{wfProcInstNo:$("wfProcInstNo").val(),photoPK:$("#photoPK").val()}
							,page:1,sortname:''}).trigger("reloadGrid");
				}
			}
		});
	});
	
	$("#delPhoto").on("click",function(){
		var id = $("#dataTablePic").jqGrid("getGridParam", "selrow");
		var pks= Global.JqGrid.selectToJson("dataTablePic","pk").fieldJson;
		if(pks=="" || !pks ){
			art.dialog({
				content:"请选择要删除的文件！",
			});
			return;
		}
		art.dialog({
			content:"是否确定删除",
			lock: true,
			width: 200,
			height: 100,
			ok: function () {
				$.ajax({
					url:'${ctx}/admin/wfProcInst/doDelPhoto',
					type: 'post',
					data:{pks:pks},
					dataType: 'json',
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
				    },
				    success: function(obj){
						if(obj){
							art.dialog({
								content:'删除成功',
								time:500,
							});
							$("#dataTablePic").jqGrid("setGridParam",{url:"${ctx}/admin/wfProcInst/findWfProcInstPic",
							postData:{wfProcInstNo:"${wfProcInstNo }",photoPK:$("#photoPK").val()}
							,page:1,sortname:''}).trigger("reloadGrid");
						}else{
							art.dialog({
								content:'操作失败',
								time:500,
							});
						}
				    }
				});
			},
			cancel: function () {
				return true;
			}
		});	
	});
	
	$("#goTransferInst").on("click",function(){
		Global.Dialog.showDialog("transferInst",{
			title:"流程转交",
			url:"${ctx}/admin/wfProcInst/goTransferInst",
			postData:{
				processInstanceId:"${piId }",
				taskId:"${taskId}",
				title:"",
				operDescr:""
			},
			height: 240,
			width:780,
			returnFun:function(data){
				if(data){
					top.$("#dialog_view").dialog("option","isCallBack",true);
					top.$("#dialog_view").dialog("close");
				}
			}
		});
		
	});
});	

function goPhotoView(e){
	Global.Dialog.showDialog("photoView",{
		title:"文件上传",
		url:"${ctx}/admin/wfProcInst/goPhotoView",
		postData:{url:e.src},
		height:550,
		width:950,
		returnFun:goto_query
	});
}

function setReadonly(activityId){//&& "applyman"!="${activityId}"
	var actId = "${activityId}";
	if(activityId && activityId != ""){
		actId = activityId;
	} 
	$("#dataForm input[type='text']").attr("readonly","true");
	$("#dataForm input[type='text']").attr("disabled","true");
	$("#dataForm textarea").attr("readonly","true");
	$("#dataForm select").attr("disabled","true");
	$.ajax({
		url:"${ctx}/admin/wfProcInst/getProcTaskTableStru",
		type:'post',							 	
		data:{wfProcNo:"${wfProcNo }",taskDefkey:actId },
		dataType:'json',
		async: false, 
		cache:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": '出错~'});
		},
		success:function(obj){
			var btnFlag = true;
			var divFlag = true;
			for(var i=0; i<obj.length; i++){
				//有配置明细表的PK可编辑 则显示明细表的新增按钮
				if(obj[i].mainTable=="0" && obj[i].StruCode=="PK" && obj[i].IsEdit !="1"){
					var child= $("#"+obj[i].TableCode).children();
							child.find("button").show(); //fp__tWfCust_ExpenseClaimAdvanceDtl__0__PK
					for(var j = 0; j < child.length; j++){
						if($("#fp__"+obj[i].TableCode+"__"+j+"__PK").val() && $("#fp__"+obj[i].TableCode+"__"+j+"__PK").val() != ""){
							var btn = $("#"+obj[i].TableCode+"_"+j).find("button");
							for(var k = 0; k < btn.length; k++){
								if($(btn[k]).text() == "删除明细"){
									$(btn[k]).hide();
								}
								if($(btn[k]).text() == "增加明细"){
									$(btn[k]).hide();
								}
							}
						}
					}
				}
				//有PK的查看权限为否 则隐藏整个明细
				if(obj[i].mainTable=="0" && obj[i].StruCode=="PK" && obj[i].IsVisible =="0" && divFlag){
					$("#"+obj[i].TableCode).hide();
					divFlag = false;
				}
			
				if(obj[i].IsVisible=="0"){
					if(obj[i].mainTable=="0"){
						var child= $("#"+obj[i].TableCode).children();
						for(var j = 0; j < child.length; j++){
							if(j=="0"){
								$("#"+"fp__"+obj[i].TableCode+"__temInx__"+obj[i].StruCode).parent().hide();
							}
							$("#"+"fp__"+obj[i].TableCode+"__"+j+"__"+obj[i].StruCode).parent().hide();
						}
					} else {
						$("#"+"fp__"+obj[i].TableCode+"__0__"+obj[i].StruCode).parent().hide();
					}
				} else {
					$("#"+"fp__"+obj[i].TableCode+"__0__"+obj[i].StruCode).parent().show();
				}
									
				if(obj[i].IsEdit=="1"){
					if(obj[i].mainTable == "0"){
						var detailList = ${detailList};
						if(detailList != ""){
							for (var tableName in detailList) {
								if($("#"+tableName).find("#group_temp").html()){
									if("${activityId}" == "startevent"){
										var j = 0;
										$("#"+"fp__"+obj[i].TableCode+"__"+j+"__"+obj[i].StruCode).removeAttr("disabled","true");
										$("#"+"fp__"+obj[i].TableCode+"__"+j+"__"+obj[i].StruCode).removeAttr("readonly","true");
									} else {
										for(var j=0;j<detailList[tableName];j++){
											$("#"+"fp__"+obj[i].TableCode+"__"+j+"__"+obj[i].StruCode).removeAttr("disabled","true");
											$("#"+"fp__"+obj[i].TableCode+"__"+j+"__"+obj[i].StruCode).removeAttr("readonly","true");
										}
									}
									$("#"+"fp__"+obj[i].TableCode+"__temInx__"+obj[i].StruCode).removeAttr("disabled","true");
									$("#"+"fp__"+obj[i].TableCode+"__temInx__"+obj[i].StruCode).removeAttr("disabled","true");
									$("#"+"fp__"+obj[i].TableCode+"__temInx__"+obj[i].StruCode).removeAttr("readonly","true");
								}
						    }
						}
					}else{
						$("#"+"fp__"+obj[i].TableCode+"__0__"+obj[i].StruCode).removeAttr("disabled","true");
						$("#"+"fp__"+obj[i].TableCode+"__0__"+obj[i].StruCode).removeAttr("readonly","true");
					}
				} else {
					$("#"+"fp__"+obj[i].TableCode+"__0__"+obj[i].StruCode).attr("disabled","true");
					$("#"+"fp__"+obj[i].TableCode+"__0__"+obj[i].StruCode).attr("readonly","true");
					$("#"+"fp__"+obj[i].TableCode+"__0__"+obj[i].StruCode+"NAME").attr("disabled","true");
				}
			}
		}
	});
}

function updatePrint(no){
	$.ajax({
		url:"${ctx}/admin/wfProcInst/doUpdatePrint",
		type: "post",
    	data: {
    		no:no
    	},
		dataType: "json",
		cache: false,
		error: function(obj){			    		
			art.dialog({
				content: "访问出错,请重试",
				time: 3000,
				beforeunload: function () {}
			});
		},
		success: function(obj){
			if(!obj.rs){
				art.dialog({
					content:obj.msg
				});
			}
		}
	});
}

function verifyDetail(){
	var result = true;
	if(detailVerify){
		for(var j=0;j<detailVerify.length;j++){
			var verify = detailVerify[j];
			var tableId = verify["tableId"];
			var mainTable = verify["mainTable"];
			var targetActId = verify["activityId"];
			var activityId = $("#activityId").val();
			if(!activityId  || activityId == "" ){
				activityId = "${activityId }";
			}
			if(!tableId || tableId =="" || tableId == null){
				continue;
			}
			if(mainTable && mainTable != "" && mainTable != null && targetActId && targetActId !="" && targetActId != null){
				if(targetActId == activityId){
					for (var key in verify) {
						if(key !="tableId" && key != "mainTable" && key != "activityId"){
	                    	if($("#fp__"+tableId+"__0__"+key).val()==""){
	                    		art.dialog({
	                    			content:verify[key],
	                    			beforeunload:function(){
	                    				if($("#wfProcInstApplyTabsUl li[class='active']").attr("id") != "baseInfoLi"){
	                    					$("#baseInfoLi > a").tab("show");
	                    	  			}
	                    	  			result = false
	                    				return;
									}
	                    		});
                   	  			result = false
	                    		return;
	                    	}
						} 
	                }
				}
				continue;
			}
			for(var i = 0; i<20; i++){
				var actActivitiId = "";
				if($("#"+tableId+"_"+i).length>0){
					for (var key in verify) {
						if(key == "activitiId"){
							actActivitiId == verify[key];
						}
						if(key !="tableId" && key != "activitiId"){
	                    	if($("#fp__"+tableId+"__"+i+"__"+key).val()=="" && targetActId == activityId){
	                    		art.dialog({
	                    			content:verify[key],
	                    			beforeunload:function(){
	                    				if($("#wfProcInstApplyTabsUl li[class='active']").attr("id") != "baseInfoLi"){
	                    					$("#baseInfoLi > a").tab("show");
	                    	  			}
	                    	  			result = false
	                    				return;
									}
	                    		});
                   	  			result = false
	                    		return;
	                    	}
						} 
	                }
				}
			}
		}
		return result;
	} 
	return result;
}
</script>
</head>
<body>
<div class="body-box-form">
	<div class="panel panel-system">
		<div class="panel-body" >
			<div class="btn-group-xs" id="topBtnDiv">
				<c:if test="${m_umState  == 'A'}">
					<button type="button" class="btn btn-system" onclick="save('A')">提交</button>
				</c:if>
				<c:if test="${type == '1'}">
					<button type="button" class="btn btn-system" onclick="save('true')">同意</button>
					<button type="button" class="btn btn-system" onclick="save('false')">退回</button>
					<button type="button" class="btn btn-system"  id="goTransferInst">转交</button>
				</c:if>
				<c:if test="${type == '3' && isEnd != true}">
					<button type="button" class="btn btn-system" onclick="save('cancel')" id="cancelBtn">撤销</button>
				</c:if>   
				<c:if test="${type == '3'}">
					<button type="button" class="btn btn-system" onclick="ReApply(true)" id="reApplyBtn">重新发起</button>
					<button type="button" class="btn btn-system" onclick="save('A')" id="reApplyYesBtn" style="display:none">确定</button>
					<button type="button" class="btn btn-system" onclick="ReApply()" id="reApplyNoBtn" style="display:none">取消</button>
				</c:if>
				<c:if test="${m_umState  != 'A'}">
					<button type="button" class="btn btn-system" onclick="doComment()">评论</button>
				</c:if>
				<c:if test="${m_umState  != 'A'}">
					<button type="button" class="btn btn-system" onclick="print()">打印</button>
				</c:if>
				<button type="button" class="btn btn-system" onclick="closeWin(true)">关闭</button>
			</div>
		</div>
	</div>
	<div class="container-fluid" id="id_detail">
		<ul class="nav nav-tabs" id="wfProcInstApplyTabsUl">
	    	<li class="active" id="baseInfoLi">
	    		<a href="#tab_apply_djxx" data-toggle="tab">基本信息</a>
	    	</li>
		   	<li class="" id="tab_apply_fjView_li" style="display:none">
		   		<a href="#tab_apply_fjView" data-toggle="tab">附件</a>
		   	</li>
	    	<c:if test="${m_umState == 'A' || type ==3 }">
				<li class="" id="checkCzyLi" style="display:${type ==3 ? 'none': ''}">
					<a href="#tab_apply_manageCzy" data-toggle="tab">审批人</a>
				</li>
		    </c:if>
	    	<c:if test="${m_umState != 'A' }">
				<li class="" id="checkProgressLi">
					<a href="#tab_apply_spjl" data-toggle="tab">审批记录</a>
				</li>
		    </c:if>
	    	<c:if test="${m_umState != 'V'}">
				<li class="">
					<a href="#tab_apply_lct" data-toggle="tab">流程图</a>
				</li>
	        </c:if>
	        <%-- <c:if test="${m_umState == 'A'}">
			    <li class=""><a href="#tab_apply_fjSave" data-toggle="tab">附件</a></li>
	        </c:if> --%>
	    </ul>
	    <div class="tab-content">
	   		<div id="tab_apply_djxx" class="tab-pane fade in active">
	   			<div id="department_div"style="height:43px;">
	   				<label style="width:110px;text-align:right;padding-top:15px" >选择部门</label>
		   			<select id="departmentSelect" name="departmentSelect" onchange="chgDept(this)" style="width:160px;height:24px;border-radius:5px;"></select> 
	   			</div>
	        	<jsp:include page="../wfCust/${applyPage }"></jsp:include>
	        </div>

	        <div id="tab_apply_lct" class="tab-pane fade"> 
		        <c:if test="${m_umState == 'A'}">
	       			<iframe id="iframe_design" name="iframe_design" frameborder="0" 
	       					src="${ctx }/admin/wfProcInst/getResource?resourceType=image&processDefinitionId=${processDefinitionKey}" width="100%" height="550"></iframe>
				</c:if>
				<c:if test="${m_umState != 'A'}">
	       			<iframe id="iframe_design" name="iframe_design" frameborder="0" 
	       					src="${ctx }/admin/wfProcInst/getTraceImage?processInstanceId=${piId }" width="100%" height="550"></iframe>
	       		</c:if>
	        </div>
	        
	        <div id="tab_apply_spjl" class="tab-pane fade"> 
	       		<div id="content-list">
					<table id= "dataTable"></table>
				</div>
	        </div>
	        
	        <div id="tab_apply_manageCzy" class="tab-pane fade">
	         	<div id="content-list">
					<table id= "dataTableManageCzy"></table>
				</div>
	        </div>
	        
	        <div id="tab_apply_fjSave" class="tab-pane fade">
	        	<div style="width:550px">
					<div id="easyContainer" style="border-rigth:0px"></div>
	        	</div>
	        </div> 
	        <div id="tab_apply_fjView" class="tab-pane fade">
	            <div class="panel panel-system">
	    			<div class="panel-body">
	            		<div class="btn-group-xs" >
		      				<button type="button" class="btn btn-system" id="upload">上传</button>
		      				<button type="button" class="btn btn-system" id="delPhoto">删除</button>
		      				<button type="button" class="btn btn-system" id="download">下载</button>
	      				</div>
	      			</div>
	      		</div>
	      			
				<div style="width:53%; float:left; margin-left:0px;">
		       		<div class="container-fluid" style="whith:800px">  
						<div id="content-list" style="whith:800px">
							<table id= "dataTablePic"></table>
						</div>
					</div>	
				</div>
				<div style="width:46.5%; float:right; margin-left:0px;border:1px solid black">
			    	<a data-magnify="gallery" data-caption="图片查看" id="docPic_a" href="">
			        	<img id="docPic" src=" " onload="AutoResizeImage(500,500,'docPic');" width="521" height="510">
					</a>
				</div>	
	        </div> 
    	</div>
    </div>
</div>
<script>
$(function(){
	
})
$("[data-magnify]").magnify({
	headToolbar: [
		"close"
	],
	footToolbar: [
		"zoomIn",
		"zoomOut",
		"fullscreen",
		"actualSize",
		"rotateRight"
	],
	title: false
});
</script>
</body>
</html>

