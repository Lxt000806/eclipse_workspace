function beforeSave(){
	return true;
}

function afterSave(){
	return true;
}
/**
 *导出文件
 *paramName：参数名称
 *paramVal：参数值
 *actionPath：请求路径
**/
function outExl(paramName,paramVal,actionPath){
	/**创建form**/
	var form = $("<form>"); 
	form.attr('style','display:none');
	form.attr('target','');  
	form.attr('method','post');  
	form.attr('action',actionPath);  
	/**创建隐藏域**/
	var input = $('<input>');  
	input.attr('type','hidden');  
	input.attr('name',paramName);  
	input.attr('value',paramVal);
	/**添加form**/
	$('body').append(form);
	/**添加input**/
	form.append(input);
	
	form.submit();
	form.remove();
	
}

function save(url){
	if(!beforeSave()){
		return ;
	}
	var datas=$("#domainForm").serialize();
	$.ajax({
		    url: url,
		    type: 'post',
		    data: datas,
		    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		    dataType: 'json',
		   	timeout: 10000,
		    error: function(){
		        alert('保存数据出错~');
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		$("#saveBtn").attr("disabled","disabled");
		    		$("#resetBtn").removeAttr("disabled");
		    		if(typeof(obj.msg) == "undefined" || obj.msg == null || obj.msg == ""){
		    			$("#resultDiv").html("保存成功！");
		    		}else{
		    			$("#resultDiv").html(obj.msg);
		    		}
		    	}else{
		    		if(typeof(obj.msg) == "undefined" || obj.msg == null || obj.msg == ""){
		    			$("#resultDiv").html("保存失败！");
		    		}else{
		    			$("#resultDiv").html(obj.msg);
		    		}
		    	}
		    	afterSave();
			}
		});
}

String.prototype.trim = function() {
    return this.replace(/(^\s+)|(\s+$)/g, "");
}

function getBodyHeight(){
  	if($.browser.msie){//IE
		return document.documentElement.scrollHeight; //
	}else{
	 	if (document.body.scrollHeight > window.innerHeight){ //FF
			yScroll = document.body.scrollHeight;
		} else {
			yScroll = window.innerHeight;
		}
		return yScroll;
	}
}

function ajax(url,data,callBack){
   		$.ajax({
		    url: url,
		    type: 'post',
		    data: data,
		    dataType: 'json',
		   	timeout: 10000,
		    error: function(){
		        alert('保存数据出错~');
		    },
		    success: function(data){
		    	if(typeof(callBack) == "function"){
		    		callBack(data)
		    	}else{
		    		eval(callBack+"()");
		    	}
		    }
		});
	}
