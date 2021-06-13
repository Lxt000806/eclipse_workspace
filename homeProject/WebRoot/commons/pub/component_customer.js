(function($) {
	"use strict";
	//初始化客户编号选择组件
	$.fn.openComponent_customer = function(options){	    
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
	    	preStr : "openComponent_customer_",
	    	blurUrl : ctx+"/admin/customer/getCustomer",
	    	blurCode : "code",
	    	blurDescr : "descr",
	    	fetchUrl : ctx+"/admin/customer/goCode",
	    	fetchCode : "code",
	    	fetchDescr : "descr",
	    	fetchTitle : "选择客户编号",
	    	dropDownUrl : ctx+"/admin/customer/goJqGrid",
	    	dropDownCode : "code",
	    	dropDownDescr : "address",
	    	width: 300
	    });
		fillComponentOptions(this,options);
	};
	//设置回填组件值
	$.fn.setComponent_customer = function(options){
		if (!options) {
	       options = {showLabel:"",showValue:""};
	    }
		$.extend(options,{
	    	preStr : "openComponent_customer_"
	    });
		if (!this.length) {
	        return this;
	    }
		setComponentData(this,options);
	}
}(jQuery));