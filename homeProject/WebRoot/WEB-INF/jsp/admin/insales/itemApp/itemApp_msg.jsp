<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>领料管理短信通知页面</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript">
	//校验函数
	$(function() {
		if("${data.phoneList}" != "[]"){
			var phoneList = listStrToJson("${data.phoneList}");
			var sendListCount = 0;
			var allListCount = 0;
			for(var i=0;i<phoneList.length;i++){
				if(phoneList[i].IsCheck == "1"){
					addPhoneNode("sendList", "sendList_"+sendListCount, phoneList[i].Caption, phoneList[i].Mobile1, "sendSpan_"+sendListCount);
					sendListCount++;
				}else{
					addPhoneNode("allList", "allList_"+allListCount, phoneList[i].Caption, phoneList[i].Mobile1, "allSpan_"+allListCount);
					allListCount++;
				}
			}
			$("#phoneCount").val(phoneList.length);
		}
	});
	function add(){
		var phoneNumber = $("#phoneNumber").val();
		var regex = /^[1][3,4,5,7,8][0-9]{9}$/;
		if(regex.test(phoneNumber)){
			addPhoneNode("sendList", "sendList_"+(parseInt($("#phoneCount").val())+1), phoneNumber, phoneNumber, "sendSpan_"+(parseInt($("#phoneCount").val())+1));
			$("#phoneCount").val(parseInt($("#phoneCount").val())+1);
		}else{
			art.dialog({content:"不是合法的手机号码"});
		}
	}
	function addPhoneNode(parentNode, id, caption, mobile1, spanId){
		$("#"+parentNode).append("<li id=\""+id+"\">&middot;&middot;&middot;&middot;&middot;<span id=\""+spanId+"\">"+caption+"</span><input type=\"hidden\" value=\""+mobile1+"\"></li>");
		$("#"+id).on("click",function(){
			if($(this).attr("active")){
				$(this).removeAttr("active");
				$(this).css("background","");
			}else{
				$(this).attr("active",true);
				$(this).css("background","#198EDE");
			}
		});
	}
	function addToSendList(){
		var allListSelected = $("#allList li[active=true]").css("background","").removeAttr("active");
		$("#sendList").append(allListSelected);
	}
	function addToAllList(){
		var sendListSelected = $("#sendList li[active=true]").css("background","").removeAttr("active");
		$("#allList").append(sendListSelected);
	}
	function save(){
		var data = {message:$("#message").val(),no:$("#no").val()};
		var sendListSelected = $("#sendList li");

		var rows=[];
		for(var i=0;i<sendListSelected.length;i++){
			var json = {};
			var ele = $("#"+sendListSelected[i].id);
			json["pk"] = i+1;
			json["caption"] = ele.find("span").html();
			json["phone"] = ele.find("input").val();
			json["isCheck"] = "1";
			json["dispSeq"] = (i+1);
			rows.push(json);
		}
		
		$.extend(data, {datas:rows,detailJson:JSON.stringify(rows)});
		
		art.dialog({
			content:"确定给待发送号码列表用户发送通知短信!<br/><br/>短信内容为:<br/><br/>"+$("#message").val(),
			ok:function(){
				$.ajax({
					url:"${ctx}/admin/itemApp/doSaveSendSMS",
					data:data,
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					dataType: 'json',
					type: 'post',
					cache: false,
					error: function(){
				        art.dialog({
							content: '保存出现异常'
						});
				    },
				    success: function(obj){
			    		art.dialog({
							content: obj.msg,
							time: 1000,
							ok:function(){
								closeWin();
							}
						});
					}
				});
			},
			cancel:function(){}
		});
	}
	</script>
	
	</head>
    
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
	    				<button id="saveBut" type="button" class="btn btn-system "   onclick="save()">保存</button>
	    				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<input type="hidden" id="no" name="no" value="${data.no}" />
			<input type="hidden" id="custCode" name="custCode" value="${data.custCode}" />
			<input type="hidden" id="m_umState" name="m_umState" value="${data.m_umState}" />
			<input type="hidden" id="phoneCount" name="phoneCount" />
			<div style="padding-top:10px">
				<div style="float:left;width:45%;height:400px;">
					<div style="height:30px;background:#F5F5F5;border:1px solid;border-bottom:0px;text-align: center;"><b>待发送号码列表</b></div>
					<div style="height:325px;border:1px solid;overflow-x:hidden">
						<ul id="sendList">
						</ul>
					</div>
					<div style="height:45px;background:#F5F5F5;border:1px solid;border-top:0px;text-align: center;">
						<div style="padding-top:8px">
							<b>号码:</b><input id="phoneNumber" type="text" style="width:40%;height:25px"/>
							<button onclick="add()">添加</button>
						</div>
					</div>
				</div>
				<div style="float:left;width:10%;height:400px;">
					<button style="width:90%;margin:5%;margin-top:100px" onclick="addToSendList()">&#60;&#60;</button>
					<button style="width:90%;margin:5%;" onclick="addToAllList()">&#62;&#62;</button>
				</div>
				<div style="float:right;width:45%;height:400px;">
					<div style="height:30px;background:#F5F5F5;border:1px solid;border-bottom:0px;text-align: center;"><b>号码库</b></div>
					<div style="height:370px;border:1px solid;overflow-x:hidden;">
						<ul id="allList" >
						</ul>
					</div>
				</div>
			</div> 
			<label style="margin-top:10px">消息内容</label>
			<textarea id="message" name="message" style="width:100%;" rows="4">${data.message }</textarea>
		</div>
	</body>
</html>
