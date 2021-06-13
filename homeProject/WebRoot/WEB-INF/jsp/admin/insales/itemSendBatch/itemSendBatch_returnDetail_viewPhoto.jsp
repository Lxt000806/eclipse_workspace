<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>退货图片信息--查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
 	 Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/itemReturnArrive/goJqGrid2",
		postData:{no:'${itemReturn.no}'},
		colModel : [
				{name: "no", index: "no", width: 70, label: "到货编号", sortable: true, align: "left"},
				{name:"address",index:"address",width:100, label:"到货地址", sortable:true ,align:'left'},
				{name: "arrivedate", index: "arrivedate", width: 80, label: "到货日期", sortable: true, align: "left",formatter:formatDate},
				{name: "driverremark", index: "driverremark", width: 140, label: "司机反馈", sortable: true, align: "left"},
			],
	});   
});



</script>

</head>
<body>
		<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
				<li style="float:right;"></li>
				<li style="float:right;"></li>
				<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
				<li style="float:right;"> 
						<span>当前第：</span>
						<input type="text" id="nowNo" name="nowNo" style="width:20px; outline:none; background:transparent; 
							border:none" readonly="true"/>张,				
				</li>
				<li style="float:right;"> 
					<span>总共：</span>
						<input type="text" id="allNo" name="allNo" style="width:20px; outline:none; background:transparent; 
							border:none"  readonly="true"/>张,				
				</li>
					<button style="float:right;" type="button" class="btn btn-system " id="pre">
						<span>上一张</span>
					</button>
					<button style="float:right;" type="button" class="btn btn-system " id="next">
						<span>下一张</span>
					</button>
			</div>
		</div>
	</div>

	<div style="width:44%;float:left ;margin-left: 1px; class="container"  >
		<ul class="nav nav-tabs">
			<li class="active"><a href="#tab_mainDetail" data-toggle="tab">到货信息</a>
			</li>
		</ul>
		<div class="tab-content">
			<div id="tab_mainDetail" class="tab-pane fade in active">
				<div id="content-list">
					<table id="dataTable"></table>
				</div>
			</div>
		</div>
	</div>
	<div style="width:55%; height:500px ; float: right;margin-left: 1px;">
		<div class="panel panel-info" >  
         <div class="panel-body">
		<form action="" method="post" id="dataForm">
			<house:token></house:token>
			<input type="hidden" name="m_umState" id="m_umState" value="M"/>
			<table cellspacing="0" cellpadding="0" width="100%">
				<col  width="72"/>
				<col  width="150"/>
				<col  width="72"/>
				<col  width="150"/>
				<tbody>
				</tbody>
			</table>
		</form>
	
			<div hidden="true">
				<table id="dataTable_TH"  ></table>
			</div>
			<div id="picture" style="width:43%;float: left;margin-left: 1px; class="container" >
				<input style="width:160px" value="${itemAppSend.photoPath }" id="photoPath" name="photoPath" hidden="true"/>
				 <div align="center" text-align:center >  
		          	 <img id="conPicture" text-align:center  src="${itemAppSend.photoPath }" onload="AutoResizeImage(500,400,'conPicture');" width="500" height="400">  
			  	</div> 
			</div>
		</div> 
	</div>
	</div>
<script type="text/javascript">
$(function(){
	var x=-1;
	var lastCellRowId;
	/**初始化表格*/
        Global.JqGrid.initJqGrid("dataTable_TH",{
			url:"${ctx}/admin/itemAppSendPhoto/goJqGrid",
			postData:{sendNo:'${itemReturn.no}'},
			height:480,
			width:200,
		styleUI: 'Bootstrap', 
			colModel : [
		       {name : 'pk',index : 'pk',width : 90,label:'pk',sortable : false,align : "center",hidden:true},
		       {name : 'sendno',index : 'sendno',width : 90,label:'发货单号',sortable : false,align : "center",hidden:true},
		       {name : 'photoname',index : 'photoname',width :230,label:'图片名称',sortable : false,align : "center",hidden:true},
		       {name : 'type',index : 'type',width : 250,label:'类型',sortable : false,align : "center",}
            ],
             gridComplete:function(){
				 var a= $("#dataTable_TH").getCol('photoname',false) ;
				 Name=a[0],
				 $('#photoPath').val(Name);
				 $.ajax({
					url:"${ctx}/admin/itemAppSend/ajaxGetPicture",
					type:'post',
					data:{photoName:a[0]},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
					 x=0;
						$('#photoPath').val(obj.photoPath);
						document.getElementById("conPicture").src = obj.photoPath;
						$('#allNo').val(a.length);
						if(a.length>0){
							$('#nowNo').val('1');
						}else{
							$('#nowNo').val('0');
						}
					}
				});
	          },
         });
         
     //下一张
	$("#next").on("click",function(){
			x++;
			var sendphoto = Global.JqGrid.allToJson("dataTable_TH","photoname");
			var type = Global.JqGrid.allToJson("dataTable_TH","type");
				arry = sendphoto.fieldJson.split(",");
				arryy = type.fieldJson.split(",");
			if(arry[x]){
				$.ajax({
					url:"${ctx}/admin/itemAppSend/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[x]},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#photoPath').val(obj.photoPath);
						document.getElementById("conPicture").src = obj.photoPath;	
						$('#nowNo').val(x+1);
						$('#allNo').val(arry.length);
						
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
			var sendphoto = Global.JqGrid.allToJson("dataTable_TH","photoname");
				arry = sendphoto.fieldJson.split(",");
			if(arry[x]){
				$.ajax({
					url:"${ctx}/admin/itemAppSend/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[x]},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#photoPath').val(obj.photoPath);
						document.getElementById("conPicture").src = obj.photoPath;	
						$('#nowNo').val(x+1);
						
					}
				});
			}else{
			x++;
				art.dialog({
					content:'已经是最后一张',
				});
			}
	});    
         
});

</script>	
	
	 	
</body>
</html>
