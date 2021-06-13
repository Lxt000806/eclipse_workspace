var artAlert = function(content){
	art.dialog({
		 content:content,
		 time:2000,
		 width:200,
		 padding: '4px 4px'
	 });
};
function afterFail(obj){
	if(obj.msg == "用户未登入"){
		art.dialog({
		    button: [
		        {
		            value: '返回首页',
		            callback: function () {
		            	window.location.href='/wap/index';
		            	return true;
		            }
		        },
		        {
		            value: '取消',
                    callback: function () {
		            	return true;
		            }
		        }
		    ],
		    content:'sorry，您还未登录！',
		    lock:true
		});
	}else{
		artAlert(obj.msg);
	}
};