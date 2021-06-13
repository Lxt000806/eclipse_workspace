(function($) {
	"use strict";
	//初始化供应商结算选择组件
	$.fn.openComponent_splCheckOut = function(options){	    
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
	    	preStr : "openComponent_splCheckOut_",
	    	blurUrl : ctx+"/admin/splCheckOut/goCodeJqGrid",
	    	blurCode : "no",
	    	blurDescr : "",
	    	fetchUrl : ctx+"/admin/splCheckOut/goCode",
	    	fetchCode : "no",
	    	fetchDescr :"",
	    	fetchTitle : "选择供应商结算单号",
	    	valueOnly : true
	    });
	    
	    var $input = $(this),$input1 = $("#"+options.preStr+$input.attr("id")),
	    isSet = false,$form = $input1.parents("form").eq(0),validator = $form.data('validator');
	    var validateStr="";
	    var alertStr=$input.attr("data-bv-notempty-message");
	    if(alertStr) validateStr=" data-bv-notempty data-bv-notempty-message=\""+alertStr+"\"";
	    if(!$input1.attr("id")){
	    	var $component = "<div class=\"input-group\"><input type=\"text\" class=\"form-control\""+validateStr+"name=\""+options.preStr
    		+$input.attr("name")+"\" id=\""+options.preStr
    		+$input.attr("id")+"\"/><button type=\"button\" class=\"btn btn-system\" ><span class=\"glyphicon glyphicon-search\"></span></button></div>";
		    $input.css("display","none");					   
		    $input.before($component);
		    $input1 = $("#"+options.preStr+$input.attr("id"));
		    $form = $input1.parents("form").eq(0),validator = $form.data('validator');
		    //添加验证信息
		    if(validator){
		    	$.validator.addMethod(options.preStr+"valid",function(value,element,params){
			    	if(params === true){
				    	if($.trim(value)==''||$(element).data("data-valid")=="true"){
				    		return true;
				    	}
				    	return false;
			    	}
			    	return true;
			    },"未通过验证");
			    $input1.rules("add", {openComponent_splCheckOut_valid: true});
		    }
		    //添加事件
		    $input1.on("change",function(){
		    	$input.val('');
		    	isSet = false;
		    	$input1.data("data-valid","false");
		    }).on("keyup",function(e){//查询表单按回车提交
			    if(options.isValidate){
			    	if(e.keyCode === 13){
						if(isSet){
							if (options.valueOnly){
								$input.setComponent_splCheckOut({showValue:$.trim($input1.val()).split("|")[0]});
							}else{
								$input.setComponent_splCheckOut({showLabel:$.trim($input1.val()).split("|")[1],
									showValue:$.trim($input1.val()).split("|")[0]});
							}
						}else{
							$input.val($.trim($input1.val()).split("|")[0]);	
						}
			    	}
			    }
		    }).on("blur",function(e){//离焦事件，判断输入是否正确
			    if(options.isValidate){
			    	if($.trim($input1.val())!="" && !isSet){
			    		var inputVal = $.trim($input1.val()).split("|")[0];
			    		$input1.data("data-valid","false");
				    	$.post(options.blurUrl,{id:$.trim(inputVal)},function(ret){
				    		if(ret.code=='0'){
				    			if (options.valueOnly){
				    				$input.setComponent_splCheckOut({showValue:ret.data[options.blurCode]});
				    			}else{
				    				$input.setComponent_splCheckOut({showLabel:ret.data[options.blurDescr],
				    					showValue:ret.data[options.blurCode]});
				    			}
			    				if(options.callBack && $.isFunction(options.callBack)){
			    					options.callBack(ret.data);
			    				}
				    			isSet = true;
				    			$input1.data("data-valid","true");
				    			validator&&validator.element($input)&&validator.element($input1);
				    		}else{
				    			isSet = false;
				    			$input1.data("data-valid","false");
				    		}
				    	},"json");
			    	}else{
		    			$input1.data("data-valid","true");
		    			validator&&validator.element($input)&&validator.element($input1);
			    	}
			    }
		    });
	    }	
		$input1.removeAttr("disabled")&&$input1.next("button").attr("data-disabled","false");
	    $input1.next("button").unbind("mousedown").on("mousedown",function(){
	    	if($(this).attr("data-disabled")=="true"){
	    		options.isValidate = false;
	    	}
	    }).unbind("click").on("click",function(){
	    	if($(this).attr("data-disabled")=="true"){
	    		return;
	    	}
	    	//把condition条件转化成&字符串
	    	var conditionStr = "";
	    	$.map(options.condition,function(v,k){
	    		conditionStr += k + "=" + v + "&";
	    	});
	    	if(conditionStr.length > 0){
	    		conditionStr = conditionStr.substring(0,conditionStr.length-1);
	    	}
			Global.Dialog.showDialog("fetch_"+$input.attr("id"),{
				title:options.fetchTitle,
				url:options.fetchUrl+(conditionStr?"?"+conditionStr:""),
				height: 600,
				width:1000,
				returnFun:function(returnData){
					if(returnData){
						if (options.valueOnly){
							$input.val(returnData[options.fetchCode])&&$input1.val(returnData[options.fetchCode]);
						}else{
							$input.val(returnData[options.fetchCode])&&$input1.val(returnData[options.fetchCode]
							+'|'+returnData[options.fetchDescr]);
						}
						isSet = true;
						$input1.data("data-valid","true");
						validator&&validator.element($input)&&validator.element($input1);
	    				if(options.callBack && $.isFunction(options.callBack)){
	    					options.callBack(returnData);
	    				}
					}
				}
			});	
		});
	    //设置值
	    if(options.readonly){
	    	$input1.attr("readonly",true)&&$input1.next("button").attr("data-disabled","true");
		}else{
			if (options.readonly==false){
	    		$input1.removeAttr("readonly")&&$input1.next("button").attr("data-disabled","false");
	    	}
		}
	    if(options.disabled){
	    	$input1.attr("disabled",true)&&$input1.next("button").attr("data-disabled","true");
		}else{
			if (options.disabled==false){
	    		$input1.removeAttr("disabled")&&$input1.next("button").attr("data-disabled","false");
	    	}
		}
	    if(options.showValue){
	    	if (options.showLabel){
	    		$(this).val(options.showValue)&&$input1.val($.trim(options.showValue)
	    				+"|"+$.trim(options.showLabel))&&$input1.data("data-valid","true");
	    	}else{
	    		$(this).val(options.showValue)&&$input1.val($.trim(options.showValue))&&$input1.data("data-valid","true");
	    	}
	    	validator&&validator.element($input)&&validator.element($input1);
	    }
	};
	//设置回填组件值
	$.fn.setComponent_splCheckOut = function(options){
		if (!options) {
	       options = {showLabel:"",showValue:""};
	    }
		$.extend(options,{
	    	preStr : "openComponent_splCheckOut_"
	    });
	    if (!this.length) {
	        return this;
	    }
	    var $input1 = $("#"+options.preStr+$(this).attr("id"));
	    if(options.readonly){
	    	$input1.attr("readonly",true)&&$input1.next("button").attr("data-disabled","true");
		}else{
			if (options.readonly==false){
	    		$input1.removeAttr("readonly")&&$input1.next("button").attr("data-disabled","false");
	    	}
		}
	    if(options.disabled){
	    	$input1.attr("disabled",true)&&$input1.next("button").attr("data-disabled","true");
		}else{
			if (options.disabled==false){
	    		$input1.removeAttr("disabled")&&$input1.next("button").attr("data-disabled","false");
	    	}
		}
	    if(options.showValue){
	    	if (options.showLabel){
	    		$(this).val(options.showValue)&&$input1.val($.trim(options.showValue)
	    				+"|"+$.trim(options.showLabel))&&$input1.data("data-valid","true");
	    	}else{
	    		$(this).val(options.showValue)&&$input1.val($.trim(options.showValue))&&$input1.data("data-valid","true");
	    	}
	    }
	}
}(jQuery));