(function($) {
	"use strict";
	//初始化应聘人员编号选择组件
	/**处理初始传入参数*/
	$.fn.openComponent_applicant = function(options){	    
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
	  /**为options添加属性*/
	    $.extend(options,{
	    	preStr : "openComponent_applicant_",
	    	blurUrl : ctx+"/admin/applicant/getApplicant",
	    	blurCode : "pk",
	    	blurDescr : "nameChi",
	    	fetchUrl : ctx+"/admin/applicant/goCode",
	    	fetchCode : "pk",
	    	fetchDescr : "nameChi",
	    	fetchTitle : "选择应聘序号",
	    	dropDownUrl : ctx+"/admin/applicant/goJqGrid",
	    	dropDownCode : "pk",
	    	dropDownDescr : "nameChi",
	    	width: 300,
	    	valueOnly: true
	    });
		fillComponentOptions(this,options);
	};
	//设置回填组件值
	$.fn.setComponent_applicant = function(options){
		if (!options) {
	       options = {showLabel:"",showValue:""};
	    }
		$.extend(options,{
	    	preStr : "openComponent_applicant_"
	    });
		if (!this.length) {
	        return this;
	    }
		setComponentData(this,options);
	}
}(jQuery));