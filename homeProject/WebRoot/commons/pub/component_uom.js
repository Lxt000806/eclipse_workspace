(function($) {
	"use strict";
	//初始化材料编号选择组件
	//修改点：1、Component_uom全文替换，2、$.extend(options) 3、注释修改
	$.fn.openComponent_uom = function(options){
		
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
	    	preStr : "openComponent_uom_",
	    	blurUrl : ctx+"/admin/uom/getUom",
	    	blurCode : "code",
	    	blurDescr : "descr",
	    	fetchUrl : ctx+"/admin/uom/goCode",
	    	fetchCode : "code",
	    	fetchDescr : "descr",
	    	fetchTitle : "度量单位选择"
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
			    $input1.rules("add", {openComponent_uom_valid: true});
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
								$input.setComponent_uom({showValue:$.trim($input1.val()).split("|")[0]});
							}else{
								$input.setComponent_uom({showLabel:$.trim($input1.val()).split("|")[1],
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
				    				$input.setComponent_uom({showValue:ret.data[options.blurCode]});
				    			}else{
				    				$input.setComponent_uom({showLabel:ret.data[options.blurDescr],
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
	$.fn.setComponent_uom = function(options){
		if (!options) {
	       options = {showLabel:"",showValue:""};
	    }
		$.extend(options,{
	    	preStr : "openComponent_uom_"
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
	$.fn.openComponent_uomDescr = function(options){
		var opt={
	    		url : ctx+"/admin/uom/goJqGrid",
	    	    code : "descr" 	
	 			};
		options=options||{};
	    if (!this.length) {
	        return this;
	    }
	    var $e=$(this);
	    if(options.condition == undefined){
	    	options["condition"] = {};
	    }
	    var conditionStr = "";
    	$.map(options.condition,function(v,k){
    		conditionStr +="&"+k + "=" + v;
    	});
    	$e.attr("condition",conditionStr);
	    $.extend(options,{
		    processData: function(json){// url 获取数据时，对数据的处理，作为 getData 的回调函数
		        if(!json || json.rows.length == 0) return false;
		        var jsonStr = "{'data':[";
		        for (var i = json.rows.length - 1; i >= 0; i--) {
		            jsonStr += "{'id':'" + i
		                + "','word':'"+ json.rows[i][opt.code]
		                + "', 'description':'" +  JSON.stringify(json.rows[i])
		                 +"'},";
		        }
		        jsonStr += "],'defaults':''}";
		 
		        //字符串转化为 js 对象
		        return json = (new Function("return "+jsonStr ))();
		    },
		    getData: function(word, parent, callback) {//数据获取方法
		        if(!word) return;
		        $.ajax({
		            type: 'POST',
		            data: opt.code+"=" + word+$e.attr("condition")+"&rows=7",
		            url:opt.url,
		            dataType: 'json',
		            timeout: 3000,
		            success: function(data) {
		                callback(parent, data);
		            },
		            error: function(data){callback(parent, data);}
		        });
		    }
		});
	    $(this).searchSuggest(options);
	}
}(jQuery));