(function($) {
	"use strict";
	//初始化客户编号选择组件
	$.fn.openComponent_custTypeItem = function(options){	    
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
	    	preStr : "openComponent_custTypeItem_",
	    	blurUrl : ctx+"/admin/custTypeItem/getCustTypeItem",
	    	blurCode : "pk",
	    	blurDescr : "descr",
	    	fetchUrl : ctx+"/admin/custTypeItem/goCode",
	    	fetchCode : "pk",
	    	fetchDescr : "descr",
	    	fetchTitle : "选择套餐材料编号",
	    	dropDownUrl : ctx+"/admin/custTypeItem/goItemJqGrid",
	    	dropDownCode : "pk",
	    	dropDownDescr : "descr",
	    	width: 300
	    });
		fillComponentOptions(this,options);
	};
	//设置回填组件值
	$.fn.setComponent_custTypeItem = function(options){
		if (!options) {
	       options = {showLabel:"",showValue:""};
	    }
		$.extend(options,{
	    	preStr : "openComponent_custTypeItem_"
	    });
		if (!this.length) {
	        return this;
	    }
		setComponentData(this,options);
	}
	$.fn.openComponent_custTypeItemDescr = function(options){
		options = options||{};
		$.extend(options,{
			url : ctx+"/admin/custTypeItem/goItemJqGrid",
	    	code : "descr",
	    	width: 400,
    	    style: 'line'
 		});
	    if (!this.length) {
	        return this;
	    }
	    fillSuggestOptions(this,options);
	}
}(jQuery));