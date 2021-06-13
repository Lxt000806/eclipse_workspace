(function($) {
	"use strict";
	//初始化自定义组件
	$.fn.customComponent= function(options){
		if(!options){
			options = {};
		}
		if(!options.btnWidth){
			options.btnWidth = 26;
		}
		if(!options.btnContent || options.btnContent == ""){
			options.btnContent = "<span class=\"glyphicon glyphicon-search\"></span>";
		}else{
			options.btnContent = "<span class=\"customComponent\">"+options.btnContent+"</span>"
		}
		var div = "<div class=\"input-group\"></div>";
		$(this).parent().append(div);
		$(this).addClass("form-control").css({
			"width": (136+(26-options.btnWidth))+"px"
		});
		var button = "<button type=\"button\" class=\"btn btn-system\" style=\"width:"+options.btnWidth+"px\">"
				   + options.btnContent+"</button>";
		$(this).next("div").append($(this)).append(button);
		
		if(options.btnEvent && (typeof(options.btnEvent) == "function")){
			$(this).next("button").on("click", options.btnEvent);
		}
	}
}(jQuery));