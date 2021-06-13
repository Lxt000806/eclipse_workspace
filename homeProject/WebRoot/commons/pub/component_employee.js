(function($) {
	"use strict";
	//初始化员工编号选择组件
	$.fn.openComponent_employee = function(options){	    
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
	    	preStr : "openComponent_employee_",
	    	blurUrl : ctx+"/admin/employee/getEmployee",
	    	blurCode : "number",
	    	blurDescr : "nameChi",
	    	fetchUrl : ctx+"/admin/employee/goCode",
	    	fetchCode : "number",
	    	fetchDescr : "namechi",
	    	fetchTitle : "选择员工编号",
	    	dropDownUrl : ctx+"/admin/employee/goJqGrid",
	    	dropDownCode : "number",
	    	dropDownDescr : "namechi"
	    });
	    fillComponentOptions(this,options);
	};
	//设置回填组件值
	$.fn.setComponent_employee = function(options){
		if (!options) {
	       options = {showLabel:"",showValue:""};
	    }
		$.extend(options,{
	    	preStr : "openComponent_employee_"
	    });
		if (!this.length) {
	        return this;
	    }
		setComponentData(this,options);
	}
	
	$.fn.openComponent_employeeDescr = function(options){
		options = options||{};
		$.extend(options,{
			url : ctx+"/admin/employee/goJqGrid",
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