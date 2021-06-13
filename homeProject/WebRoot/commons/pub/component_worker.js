(function($) {
	"use strict";
	//初始化员工编号选择组件
	$.fn.openComponent_worker = function(options){	    
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
	    	preStr : "openComponent_worker_",
	    	blurUrl : ctx+"/admin/worker/getWorker",
	    	blurCode : "code",
	    	blurDescr : "nameChi",
	    	fetchUrl : ctx+"/admin/worker/goCode",
	    	fetchCode : "Code",
	    	fetchDescr :"NameChi",
	    	fetchTitle : "选择工人编号",
	    	dropDownUrl : ctx+"/admin/worker/goJqGrid",
	    	dropDownCode : "code",
	    	dropDownDescr : "namechi",
	    });
	    fillComponentOptions(this,options);
	};
	//设置回填组件值
	$.fn.setComponent_worker = function(options){
		if (!options) {
	       options = {showLabel:"",showValue:""};
	    }
		$.extend(options,{
	    	preStr : "openComponent_worker_"
	    });
		if (!this.length) {
	        return this;
	    }
		setComponentData(this,options);
	}
	
	$.fn.openComponent_workerDescr = function(options){
		options = options||{};
		$.extend(options,{
			url : ctx+"/admin/worker/goJqGridList",
	    	code : "description",
	    	width: 150,
    	    style: 'line'
 		});
	    if (!this.length) {
	        return this;
	    }
	    fillSuggestOptions(this,options);
	}
}(jQuery));