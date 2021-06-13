<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>验收确认</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
$(function(){

});
</script>

</head>
<body>
		<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					<li style="float:right;"> 
						<span>当前第：</span>
						<input type="text" id="nowNo" name="nowNo" style="width:20px; outline:none; background:transparent; 
							border:none" value="${prjProgConfirm.nowNo }" readonly="true"/>张,				
					</li>
					<li style="float:right;"> 
						总共：
						<input type="text" id="allNo" name="allNo" style="width:20px; outline:none; background:transparent; 
							border:none" value="${prjProgConfirm.allNo }" readonly="true"/>张,				
					</li>
						<button style="float:right;"  type="button" class="btn btn-system " id="pre">
							<span>上一张</span>
						</button>
						<button style="float:right;" type="button" class="btn btn-system " id="next">
							<span>下一张</span>
						</button>
				</div>
				</div>
	</div>
	
		<div  style="width:40%;float:left ;margin-left: 1px; class="container"  >
				<div class="panel panel-info" >  
         <div class="panel-body">  
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<ul class="ul-form">
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;"  value="${prjProgConfirm.address }" readonly="readonly"/>                                             
					</li>
					<li hidden="true">
						<label>CustCode</label>
						<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${prjProgConfirm.custCode }" readonly="readonly"/>                                             
					</li>
					<li>
						<label>施工项目</label>
						<house:xtdm id="prjItem" dictCode="PRJITEM"  value="${prjProgConfirm.prjItem}" disabled="true"></house:xtdm>
					</li>
					<li>
						<label>验收评级</label>
						<house:xtdm id="prjLevel"  dictCode="PRJLEVEL"  value="${prjProgConfirm.prjLevel}" disabled="true"></house:xtdm>
					</li>
					<li>
						<label>是否通过</label>
						<house:xtdm id="isPass"  dictCode="YESNO"  value="${prjProgConfirm.isPass}" disabled="true"></house:xtdm>
					</li>
					<li>
						<label>验收时间</label>
						<input type="text" id="date" name="date"  style="width:160px" class="i-date" onFocus="WdatePicker({skin:'whyGreen',timeFmt:'yyyy-MM-dd hh:mm:ss'})" value="<fmt:formatDate value='${prjProgConfirm.date}' pattern='yyyy-MM-dd hh:mm:ss'/>" disabled="disabled"/>
					</li>
					<li>
						<label>客户评分</label>
						<input type="text" id="custScoreComfirm" name="custScoreComfirm" style="width:160px;" value="${prjProgConfirm.custScoreComfirm }"  readonly="readonly"/>
					</li>
					<li>
						<label class="control-textarea" >客户评价</label>
						<textarea style="width:160px" id="custRemarks" name="custRemarks" rows="2">${prjProgConfirm.custRemarks }</textarea>
					</li>
					<li>
						<label class="control-textarea" >验收说明</label>
						<textarea  style="width:160px" id="confirmDesc" name="confirmDesc" rows="2">${prjProgConfirm.remarks }</textarea>
					</li>
					${htmlStr}
				</ul>	
		</form>
		</div>
		</div>
		
		</div>
	<div style="width:58%; height:480px ;border:1px solid #e7e7e7; float: right;margin-left: 1px; class="container" border:11px solid gray>
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
				<ul class="ul-form">
					<li hidden="true">
						<label>custcode</label>
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${prjProg.custCode}" />
					</li>
					<li hidden="true">
						<label>施工项目</label>
						<input type="text" id="prjItem" name="prjItem" style="width:220px;"  value="${prjProg.prjItem}" />
					<li hidden="true"> 
						<label>photoName</label>
						<input type="text" id="photoName" name="photoName" style="width:220px;"  value="${prjProg.photoName}" />
					</li>
				</ul>	
		</form>
	
			<div hidden="true">
				<table id="dataTable_picture"  ></table>
			</div>
			<div id="picture" style="width:48%;float: left;margin-left: 1px; class="container" >
				<input style="width:160px" value="${prjProgConfirm.photoPath }" id="photoPath" name="photoPath" hidden="true"/>
				 <div align="center" text-align:center >  
		          	 <img id="conPicture" text-align:center  src="${prjProgConfirm.photoPath }" onload="AutoResizeImage(550,450,'conPicture');" width="540" height="455">  
			  	</div> 
			</div>
		</div> 
	<div id="content-list"  hidden="true">
		<table id= "dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
<script type="text/javascript">
$(function(){
	var x=0;
	 Global.JqGrid.initJqGrid("dataTable_picture",{
		url:"${ctx}/admin/prjProgPhoto/goJqGrid",
		postData:{CustCode:'${prjProgConfirm.custCode }',type:'2',PrjItem:'${prjProgConfirm.prjItem}',refNo:"${prjProgConfirm.no}"},
		colModel : [
				{name: "PK", index: "PK", width: 175, label: "pk", sortable: true, align: "left",hidden:true},
				{name:'prjitemdescr',index:'prjitemdescr',width:175, label:'施工项目', sortable:true ,align:'left'},
				{name: "PhotoName", index: "PhotoName", width: 175, label: "图片名称", sortable: true, align: "left",},
				{name: "LastUpdate", index: "LastUpdate", width: 175, label: "上传图片时间", sortable: true, align: "left",formatter:formatTime},
			],
			gridComplete:function(){
					 var b= $("#dataTable_picture").getCol('PhotoName',false) ;
					 Name=b[0],
					 $('#photoPath').val(Name);
					 $.ajax({
						url:"${ctx}/admin/prjProg/ajaxGetPicture",
						type:'post',
						data:{photoName:b[0],readonly:'1'},
						dataType:'json',
						cache:false,
						error:function(obj){
							showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
						},
						success:function(obj){
							$('#photoPath').val(obj.photoPath);
							document.getElementById("conPicture").src =obj.photoPath;
							$('#allNo').val(b.length);
							if(b.length>0){
								$('#nowNo').val('1');
							}else{
								$('#nowNo').val('0');
							}
						}
					});
		          },	
	}); 
				
	$("#next").on("click",function(){
			x++;
			var prjphoto = Global.JqGrid.allToJson("dataTable_picture","PhotoName");
				arry = prjphoto.fieldJson.split(",");
			if(arry[x]){
				$.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[x],readonly:'1'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#photoPath').val(obj.photoPath);
						$('#nowNo').val(x+1);
						document.getElementById("conPicture").src = obj.photoPath;	
					}
				});
			}else{
			x--;
				art.dialog({
					content:'已经是最后一张',
				});
			}
	});	
	
	//上一张
	$("#pre").on("click",function(){
			x--;
			var prjphoto = Global.JqGrid.allToJson("dataTable_picture","PhotoName");
				arry = prjphoto.fieldJson.split(",");
			if(arry[x]){
				$.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[x],readonly:'1'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#photoPath').val(obj.photoPath);
						$('#nowNo').val(x+1);
						document.getElementById("conPicture").src = obj.photoPath;	
					}
				});
			}else{
			x++;
				art.dialog({
					content:'已经是最后一张',
				});
			}
	});	
	//删除
	$("#del").on("click",function(){
			var prjphoto = Global.JqGrid.allToJson("dataTable_picture","PhotoName");
				arry = prjphoto.fieldJson.split(",");
			if(arry[x]){
				$.ajax({
					url:"${ctx}/admin/prjProg/ajaxDelPicture",
					type:'post',
					data:{photoName:arry[x]},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						art.dialog({
							content:'删除成功'
						});
						$("#dataTable_picture").jqGrid("setGridParam",{postData:{CustCode:'${prjProg.custCode }',Type:'1',PrjItem:'${prjProg.prjItem}'}}).trigger("reloadGrid");
					}
				});
			}
	});	
	
	
});

</script>	
	
	 	
</body>
</html>
