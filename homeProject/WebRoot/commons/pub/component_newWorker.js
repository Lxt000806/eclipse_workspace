(function($) {
	"use strict";
	//初始化员工编号选择组件
	$.fn.openComponent_newWorker = function(options){	    
	    if (!this.length) {
	        return this;
	    }
	    if (typeof options == 'function') {
	        options = { callBack: options };
	    }else if ( options === undefined ) {
	        options = {};
	    }
	    if(options.condition == undefined){
	    	options["condition"] = {};
	    }
	    if(options.isValidate == undefined){
	    	options["isValidate"] = true;
	    }
	    $.extend(options,{
	    	preStr : "openComponent_newWorker_",
	    	blurUrl : ctx+"/admin/worker/getWorker",
	    	blurCode : "code",
	    	blurDescr : "nameChi",
	    	fetchUrl : ctx+"/admin/worker/goNewCode",
	    	fetchCode : "Code",
	    	fetchDescr :"NameChi",
	    	fetchTitle : "选择工人编号",
	    	dropDownUrl : ctx+"/admin/worker/goCodeJqGrid",
	    	dropDownCode : "Code",
	    	dropDownDescr : "namechiworktype12",
	    	dialogWidth:1300,
	    	dialogHeight:750
	    });
	    fillComponentOptions(this,options);
	};
	//设置回填组件值
	$.fn.setComponent_newWorker = function(options){
		if (!options) {
	       options = {showLabel:"",showValue:""};
	    }
		$.extend(options,{
	    	preStr : "openComponent_newWorker_"
	    });
		if (!this.length) {
	        return this;
	    }
		setComponentData(this,options);
	}
}(jQuery));