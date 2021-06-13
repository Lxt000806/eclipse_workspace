<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>查看图片</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
  </head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
			<div class="panel-body" >
				<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="saveBtn" onclick="updateView(true)">
						<span>全部可见</span>
					</button>
					<button type="button" class="btn btn-system " id="saveBtn" onclick="updateView(false)">
						<span>全不可见</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
					&nbsp;&nbsp;&nbsp;该巡检单：<span id="btn_span" style="font-weight:bold;"></span>
				</div>
			</div>
		</div>
  		<div class="infoBox" id="infoBoxDiv"></div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
					<ul style="width: 1202px;" id="picUL">
					</ul>	
				</form>
			</div>
		</div>
	</div> 
	<div id="content-list" hidden="true">
		<table id= "dataTable"></table>
		<div id="dataTablePager"></div>
	</div> 
<script type="text/javascript">
var isPushCustList;
$(function(){
	 Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjProgPhoto/goJqGrid",
		postData:{CustCode:'${prjProgConfirm.custCode }',type:'2',PrjItem:'${prjProgConfirm.prjItem}',refNo:"${prjProgConfirm.no}"},
		colModel : [
			{name: "PK", index: "PK", width: 175, label: "pk", sortable: true, align: "left",hidden:true},
			{name:'prjitemdescr',index:'prjitemdescr',width:175, label:'施工项目', sortable:true ,align:'left'},
			{name: "PhotoName", index: "PhotoName", width: 175, label: "图片名称", sortable: true, align: "left",},
			{name: "LastUpdate", index: "LastUpdate", width: 175, label: "上传图片时间", sortable: true, align: "left",formatter:formatTime},
			{name: "IsPushCust", index: "IsPushCust", width: 175, label: "IsPushCust", sortable: true, align: "left",},
			{name: "ispushcustdescr", index: "ispushcustdescr", width: 175, label: "ispushcustdescr", sortable: true, align: "left",},
		],
		gridComplete:function(){
			var photoName= $("#dataTable").getCol('PhotoName',false) ;
			var IsPushCustDescr= $("#dataTable").getCol('ispushcustdescr',false);
			var pk= $("#dataTable").getCol('PK',false);
			isPushCustList= $("#dataTable").getCol('IsPushCust',false);
			var canView="设不可见";
			var _color="red";
			for(var i=0;i<photoName.length;i++){
				if(isPushCustList[i]=="0" || $.trim(isPushCustList[i])=="" ){
					canView="设可见";
					 _color="green";
				}else{
					canView="设不可见";
					 _color="red";
				}
				var li="<li id='li_"+i+"' style='float: left;margin-right:20px;margin-top:5px'>"
						+"<div>"
							+"<div style='width:210px;height:210px'>"					//http://localhost:8080/homeProject/homePhoto/prjProg/
								+"<img style='float:center' id='conPicture"+i+"' src='"+$.trim("${photoPath }")+photoName[i].substring(0,5)+"/"+photoName[i]+"' width='210' height='210'>"
							+"</div>"
							+"<div style='margin-top:5px;padding-bottom:5px;float:bottom' >"
								+"<span>当前状态:"+"<span id='status_"+i+"' style='font-weight:bold;color:"+_color+"'>"+IsPushCustDescr[i]+"</span>"+"</span>"
								+"<botton type='button' class='btn btn-system' id='btn_"+i+"' onclick='checkPic("+pk[i]+","+i+")' style='font-weight:bold;margin-top:-5px;line-height: 8px;float:right;width:58px;height:22px'>"
									+"<span id='spn_"+i+"' style='font-size:12px;margin-left:-7px'>"+canView+"</span>"
								+"</botton>"
							+"</div>"
						+"</div>"
					+"</li>";
				$("ul").append(li);
				AutoResizeImage(210,210,'conPicture'+i);
			}
			updateBtn_Span();
		},
	});
});
	function checkPic(pk,i){
		$("#btn_"+i).attr("disabled","disabled")
		$.ajax({
			url:"${ctx}/admin/prjProg/updateIsPushCust",
			type:'post',
			data:{pk:pk,isPushCust:isPushCustList[i]},
			dataType:'json',
			cache:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
			},
			success:function(obj){
				$("#btn_"+i).removeAttr("disabled","disabled")
				if(isPushCustList[i]=='1'){
					isPushCustList[i]='0';
					$("#dataTable").jqGrid('setCell',i+1 ,"IsPushCust","0");
					$("#spn_"+i).html("设可见");
					$("#status_"+i).html("不可见");
					$("#status_"+i).css("color","green");
					
				}else{
					isPushCustList[i]='1';
					$("#dataTable").jqGrid('setCell',i+1 ,"IsPushCust","1");
					$("#spn_"+i).html("设不可见");
					$("#status_"+i).html("可见");
					$("#status_"+i).css("color","red");
				}
				updateBtn_Span();
			}
		});
	} 
	function updateView(val){
		var isPushCust ;
		if(val){
			isPushCust='1';
		}else{
			isPushCust='0';
		}
		$.ajax({
			url:"${ctx}/admin/prjProg/updateIsPushCustAll",
			type:'post',
			data:{CustCode:'${prjProgConfirm.custCode }',type:'2',
				PrjItem:'${prjProgConfirm.prjItem}',refNo:"${prjProgConfirm.no}",isPushCust:isPushCust},
			dataType:'json',
			cache:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
			},
			success:function(obj){
				for(var i = 0 ;i<isPushCustList.length;i++){
					if(!val){
						isPushCustList[i]='0';
						$("#dataTable").jqGrid('setCell',i+1 ,"IsPushCust","0");
						$("#spn_"+i).html("设可见");
						$("#status_"+i).html("不可见");
					$("#status_"+i).css("color","green");
						
					}else{
						isPushCustList[i]='1';
						$("#dataTable").jqGrid('setCell',i+1 ,"IsPushCust","1");
						$("#spn_"+i).html("设不可见");
						$("#status_"+i).html("可见");
					$("#status_"+i).css("color","red");
					}
				}
				updateBtn_Span();
			}
		});	
	}
	function updateBtn_Span(){
		for(var i = 0;i<isPushCustList.length;i++){
			if(isPushCustList[i]=="1"){
				$("#btn_span").html("可见");
				$("#btn_span").css("color","red");
				break;
			}
			$("#btn_span").html("不可见");
			$("#btn_span").css("color","green");
		}
	}
	
</script>
</body>
</html>

















