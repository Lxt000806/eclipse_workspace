(function($) {
	"use strict";
	//初始化材料编号选择组件
	//修改点：1、Component_item全文替换，2、$.extend(options) 3、注释修改
	$.fn.openComponent_item = function(options){
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
	    	preStr : "openComponent_item_",
	    	blurUrl : ctx+"/admin/item/getItem",
	    	blurCode : "code",
	    	blurDescr : "descr",
	    	fetchUrl : ctx+"/admin/item/goCode",
	    	fetchCode : "code",
	    	fetchDescr : "descr",
	    	fetchTitle : "产品选择",
	    	dropDownUrl : ctx+"/admin/item/goJqGrid",
	    	dropDownCode : "code",
	    	dropDownDescr : "descr",
	    	width: 400
	    });
	    fillComponentOptions(this,options);
	};
	//设置回填组件值
	$.fn.setComponent_item = function(options){
		if (!options) {
	       options = {showLabel:"",showValue:""};
	    }
		$.extend(options,{
	    	preStr : "openComponent_item_"
	    });
		if (!this.length) {
	        return this;
	    }
		setComponentData(this,options);
	};
	$.fn.openComponent_itemDescr = function(options){
		options = options||{};
		$.extend(options,{
			url : ctx+"/admin/item/goSuggestJqGrid",
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