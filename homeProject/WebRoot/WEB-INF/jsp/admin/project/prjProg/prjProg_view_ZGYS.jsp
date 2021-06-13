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
<script type="text/javascript">
$(function(){
 var x=-1;
	 $("#pass").css("color","gray");
	 $("#return").css("color","gray");
	 
	var lastCellRowId;
		 Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjProgPhoto/goJqGrid",
		postData:{CustCode:'${prjProg.custCode }',Type:'3',PrjItem:'${prjProg.prjItem}'},
		colModel : [
				{name: "PK", index: "PK", width: 175, label: "pk", sortable: true, align: "left",hidden:true},
				{name:'prjitemdescr',index:'prjitemdescr',width:175, label:'施工项目', sortable:true ,align:'left'},
				{name: "PhotoName", index: "PhotoName", width: 175, label: "图片名称", sortable: true, align: "left",},
				{name: "Type", index: "Type", width: 175, label: "Type", sortable: true, align: "left",},
				{name: "LastUpdate", index: "LastUpdate", width: 175, label: "上传图片时间", sortable: true, align: "left",formatter:formatTime},
			],
	});
	
	/**初始化表格*/
      	Global.JqGrid.initJqGrid("dataTable_zg",{
			url:"${ctx}/admin/prjProgPhoto/goPrjJqGrid",
			postData:{custCode:'${prjProgCheck.custCode }',type:'4',prjItem:'${prjProgCheck.prjItem}'},
			height:165,
			colModel : [
				{name : 'prjitem',index : 'PrjItem',width : 90,label:'施工项目',sortable : false,align : "center",hidden:true},
		      	{name : 'photoname',index : 'photoname',width :150,label:'图片名称',sortable : false,align : "center" ,},
		       	{name : 'prjdescr',index : 'PrjItem',width : 190,label:'施工项目',sortable : false,align : "center",},
		      	{name : 'addresscheck',index : 'addresscheck',width :230,label:'巡检地址',sortable : false,align : "center"},
		       	{name : 'checkdate',index : 'checkdate',width : 190,label:'巡检日期',sortable : false,align : "center",formatter:formatTime},
            
            ],
         });   
    //查看整改图片
	$("#view").on("click",function(){
		x=0;
			var prjphoto = Global.JqGrid.allToJson("dataTable_zg","photoname");
				arry = prjphoto.fieldJson.split(",");
			if(arry[x]){
				$.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[x],readonly:'4'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#ysPhotoPath').val(obj.ysPhotoPath);
						document.getElementById("conPicture").src = obj.ysPhotoPath;	
					}
				});
			}else{
			x--;
				art.dialog({
					content:'已经是最后一张',
				});
			}	
	});	     
         
   //下一张 工地整改
	$("#next").on("click",function(){
			x++;
			var prjphoto = Global.JqGrid.allToJson("dataTable_zg","photoname");
				arry = prjphoto.fieldJson.split(",");
			if(arry[x]){
				$.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[x],readonly:'4'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#ysPhotoPath').val(obj.ysPhotoPath);
						document.getElementById("conPicture").src = obj.ysPhotoPath;
					}
				});
			}else{
			x--;
				art.dialog({
					content:'已经是最后一张',
				});
			}
	});	
	//上一张 工地整改
	$("#pre").on("click",function(){
			x--;
			var prjphoto = Global.JqGrid.allToJson("dataTable_zg","photoname");
				arry = prjphoto.fieldJson.split(",");
			if(arry[x]){
				$.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[x],readonly:'4'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#ysPhotoPath').val(obj.ysPhotoPath);
						document.getElementById("conPicture").src = obj.ysPhotoPath;	
					}
				});
			}else{
			x++;
				art.dialog({
					content:'已经是最后一张',
				});
			}
	});
	
	//查看巡检图片
	$("#view_xjPicture").on("click",function(){
		var ret = selectDataTableRow();
		Global.Dialog.showDialog("goPicture",{
			title:"查看图片",
			url:"${ctx}/admin/prjProg/goXjPicture",
			postData:{custCode:"${prjProgCheck.custCode }",prjItem:"${prjProgCheck.prjItem}",photoName:ret.PhotoName},
			height:550,
			width:800,
			returnFun:goto_query
		});
	});
});
	


</script>
</head>
<body>
<div class="panelBar">
		   <ul>
              	<li>
				<a href="javascript:void(0)" class="a1" id="pass">
					<span>验收通过</span>
				</a>
			</li>
			<li>
				<a href="javascript:void(0)" class="a1" id="return">
					<span>退回重拍</span>
				</a>
			</li>
			<li>
				<a href="javascript:void(0)" class="a1" id="view_xjPicture">
					<span>查看巡检图片</span>
				</a>
			</li>
			
			<li id="closeBut" onclick="closeWin(false)">
				<a href="javascript:void(0)" class="a2">
					<span>关闭</span>
				</a>
			</li>
              </ul>		   
	</div>
		<form action="" method="post" id="page_form">
			<input type="hidden" name="m_umState" id="m_umState" value="A"/>
			<table cellspacing="0" cellpadding="0" width="100%">
				<col width="72"/>
				<col width="150"/>
				<tbody>
					<tr hidden="true">
						<td class="td-label">custCode</td>
						<td class="td-value">
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${prjProgCheck.custCode }" readonly="readonly"/>                                             
						</td> 
					</tr><tr >
						<td class="td-label">地址</td>
						<td class="td-value">
							<input type="text" id="address" name="address" style="width:160px;"  value="${prjProgCheck.address }" readonly="readonly"/>                                             
						</td> 
					</tr>
					<tr>
						<td class="td-label">巡检项目</td>
						<td class="td-value">
							<house:xtdm id="prjItem" dictCode="PRJITEM" value="${prjProgCheck.prjItem}" disabled="true"></house:xtdm>
						</td>
					</tr>
					<tr>
						<td class="td-label">安全问题</td>
						<td class="td-value">
							<house:xtdm id="safeProm" dictCode="CHECKPROM"  value="${prjProgCheck.safeProm}" disabled="true"></house:xtdm>
						</td>
					</tr>
					<tr>
						<td class="td-label">形象问题</td>
						<td class="td-value">
							<house:xtdm id="visualProm" dictCode="CHECKPROM"  value="${prjProgCheck.visualProm}" disabled="true"></house:xtdm>
						</td>
						
					</tr>
					<tr>
						<td class="td-label">工艺问题</td>
						<td class="td-value" > 
							<house:xtdm id="artProm" dictCode="CHECKPROM"  value="${prjProgCheck.artProm}" disabled="true"></house:xtdm>
						</td>
						
					</tr>
					<tr>
						<td class="td-label">整改限时</td>
						<td class="td-value"> 
							<input  type="text" id="modifyTime" name="modifyTime" style="width:160px;" value="${prjProgCheck.modifyTime}"/>                    
						</td>
					</tr>
					<tr>
						<td class="td-label">剩余整改限时</td>
						<td class="td-value" > 
							<input type="text" id="remainModifyTime" name="remainModifyTime" style="width:160px" value="${prjProgCheck.remainModifyTime}"/>
						</td>
					</tr>
					<tr>
						<td class="td-label">验收说明</td>
						<td class="td-value" >
							<textarea id="remarks" name="remarks" rows="2" >
								${prjProgCheck.remarks}
							</textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	
	<div >
		<div class="panelBar">
			<ul>
				<li>
					<a href="javascript:void(0)" class="a1" id="view">
						<span>查看整改图片</span>
					</a>
				</li><li>
					<a href="javascript:void(0)" class="a1" id="next">
						<span>下一张</span>
					</a>
				</li>
				<li>
					<a href="javascript:void(0)" class="a1" id="pre">
						<span>上一张</span>
					</a>
				</li>
			</ul>
		</div>
		<div hidden="true"> 
			<table  id="dataTable_zg"  ></table>
		</div>
	</div>
		<div>
		<div hidden="true"> 
			<table  id="dataTable"  ></table>
		</div>
			<input style="width:160px" value="${prjProg.ysPhotoPath }" id="ysPhotoPath" name="ysPhotoPath" hidden="true"  />
			 <div >  
           		 <div >  
             		 <img id="conPicture" src="${prjProg.ysPhotoPath }">  
	          	 </div>  
	  		 </div> 
		</div>	
	
	
</body>
</html>
