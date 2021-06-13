(function($) {
	"use strict";
	//初始化员工编号选择组件
	$.fn.openComponent_salaryEmp = function(options){	    
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
	    	preStr : "openComponent_salaryEmp_",
	    	blurUrl : ctx+"/admin/salaryEmp/getSalaryEmp",
	    	blurCode : "empcode",
	    	blurDescr : "empname",
	    	fetchUrl : ctx+"/admin/salaryEmp/goCode",
	    	fetchCode : "empcode",
	    	fetchDescr : "empname",
	    	fetchTitle : "选择薪酬人员",
	    	dropDownUrl : ctx+"/admin/salaryEmp/goJqGrid",
	    	dropDownCode : "empcode",
	    	dropDownDescr : "empname"
	    });
	    fillComponentOptions(this,options);
	};
	//设置回填组件值
	$.fn.setComponent_salaryEmp = function(options){
		if (!options) {
	       options = {showLabel:"",showValue:""};
	    }
		$.extend(options,{
	    	preStr : "openComponent_salaryEmp_"
	    });
		if (!this.length) {
	        return this;
	    }
		setComponentData(this,options);
	}
}(jQuery));