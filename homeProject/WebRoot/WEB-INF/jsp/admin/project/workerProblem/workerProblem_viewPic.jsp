<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp"%>
	
</head>
<body>
	<div class="panel panel-system">
    	<div class="panel-body" >
      		<div class="btn-group-xs" >
				<li style="float:right;"></li>
				<li style="float:right;"></li>
				<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
					<span>关闭</span>
				</button>
				<button style="float:right;margin-right: 10px;" type="button" class="btn btn-system " id="next">
					<span>下一张</span>
				</button>
				<button style="float:right;" type="button" class="btn btn-system " id="pre">
					<span>上一张</span>
				</button>
				<li style="float:right;"> 
					<span>当前第：</span>
					<input type="text" id="nowNo" name="nowNo" style="width:20px; outline:none; background:transparent; 
						border:none" readonly="true"/>张,				
				</li>
				<li style="float:right;"> 
					<span>总共：</span>
					<input type="text" id="allNo" name="allNo" style="width:20px; outline:none; background:transparent; 
						border:none" value="${workerProblem.picnum}" readonly="true"/>张,				
				</li>
			</div>
		</div>
	</div>
	<div style="height:500px; width:40%;float:left ;margin-left: 1px; class="container">
		<div class="panel panel-info" style="height: 482px;">
			<div class="panel-body" style="height: 480px;">
				<form role="form" class="form-search" id="page_form" action=""
					method="post" target="targetFrame">
					<input type="hidden" name="m_umState" id="m_umState" value="A" />
					<ul class="ul-form" style="height: 450px">
						<div>
							<li><label class="control-textarea" style="width: 60px;">反馈内容：</label>
								<textarea id="remark" name="remark" style="width: 250px;"
									rows="6" readonly="true">${workerProblem.remark}</textarea>
							</li>
						</div>
							<br><br><br><br><br><br><br><br><br><br>
						<div>
							<li><label class="control-textarea" style="width: 60px;">确认说明：</label>
								<textarea id="confirmRemark" name="confirmRemark"
									style="width: 250px;" rows="6" readonly="true">${workerProblem.confirmremark}</textarea>
							</li>
						</div>
							<br><br><br><br><br><br><br><br><br><br>
						<div>
							<li><label class="control-textarea" style="width: 60px;">处理说明：</label>
								<textarea id="dealRemark" name="dealRemark"
									style="width: 250px;" rows="6" readonly="true">${workerProblem.dealremark}</textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
	<div style="width:58%; height:500px ; float: right;margin-left: 1px;">
		<div class="panel panel-info" >  
         	<div class="panel-body">
	         	<div hidden="true">
					<table id="dataTable_picture"></table><!-- jqGrid需要和实体绑定，否则会报错 -->
				</div>
       			<img id="wkProblemPic" src=" " onload="AutoResizeImage(532.906,455,'wkProblemPic');" width="540" height="455">
			</div> 
		</div>
	</div>
<script type="text/javascript">
$(function(){
	/* 初始化当前张数参数 */
	var x=0;
	
	/* 如果没有照片 */
	if ("${workerProblem.picnum}"!=0) {
		Global.JqGrid.initJqGrid("dataTable_picture",{
		url:"${ctx}/admin/workerProblem/goPicJqGrid",
		postData:{no:"${workerProblem.no}"},
		colModel : [
			{name: "no", index: "no", width: 100, label: "no", sortable: true, align: "left",hidden:true},
			{name: "pk", index: "pk", width: 100, label: "pk", sortable: true, align: "left",hidden:true},
			{name: "url", index: "url", width: 175, label: "url", sortable: true, align: "left",},
			{name: "photoname", index: "photoname", width: 175, label: "图片名称", sortable: true, align: "left",},
			{name: "lastupdate", index: "lastupdate", width: 175, label: "上传图片时间", sortable: true, align: "left",formatter:formatTime},
			{name:"expired",index:"expired",width:100, label:"是否过期", sortable:true ,align:"left"}
		],
		gridComplete:function(){
			var a = $("#dataTable_picture").getCol("url",false);
			var b= $("#dataTable_picture").getCol("photoname",false) ;
			var photoPath= a[0]+b[0].substr(0,5)+"/"+b[0];
			document.getElementById("wkProblemPic").src = photoPath;
			if("${workerProblem.picnum}">0){
				$("#nowNo").val("1");
			}else{
				$("#nowNo").val("0");
			}
        }	
	}); 
	} else {
		$("#next").attr("disabled",true);
		$("#pre").attr("disabled",true);
		art.dialog({
			content:"没有图片",
		});
	}
		
	/* 下一张 */		
	$("#next").on("click",function(){
		x++;
		var workerProblemPic = Global.JqGrid.allToJson("dataTable_picture","photoname");
		arry = workerProblemPic.fieldJson.split(",");
		var url = Global.JqGrid.allToJson("dataTable_picture","url");
		urlArry = url.fieldJson.split(",");
		if(arry[x]){
			$("#nowNo").val(x+1);
			var photoPath= urlArry[x]+arry[x].substr(0,5)+"/"+arry[x];
			document.getElementById("wkProblemPic").src = photoPath;
		}else{
			x--;
			art.dialog({
				content:"已经是最后一张",
			});
		}
	});	
	
	/* 上一张 */
	$("#pre").on("click",function(){
		x--;
		var workerProblemPic = Global.JqGrid.allToJson("dataTable_picture","photoname");
		arry = workerProblemPic.fieldJson.split(",");
		var url = Global.JqGrid.allToJson("dataTable_picture","url");
		urlArry = url.fieldJson.split(",");
		if(arry[x]){
			$("#nowNo").val(x+1);
			var photoPath= urlArry[x]+arry[x].substr(0,5)+"/"+arry[x];
			document.getElementById("wkProblemPic").src = photoPath;
		}else{
			x++;
			art.dialog({
				content:"已经是最后一张",
			});
		}
	});	
	
});
</script>	
	
</body>
</html>
