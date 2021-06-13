(function($){
	var _element ;
	var method;
	var onClickMethod;
	var clickMethod
  //不区分大小写的jQuery:包含选择器
  $.expr[":"].searchableSelectContains = $.expr.createPseudo(function(arg) {
    return function( elem ) {
      return $(elem).text().toUpperCase().indexOf(arg.toUpperCase()) >= 0;
    };
  });
  
  $.searchableSelect = function(element, options) {
	  
	  _element = element;
	//如果不是select 标签 不初始化
	if(!(_element[0].tagName === 'SELECT')){
		return;
	}
	
	//没有在form表单底下，不触发
	if(!$("#"+_element[0].id).parents('form')[0]){
		return;
	}
    var targetNode = _element[0];//content监听的元素id
	//options：监听的属性
	var option = {attributes: true ,};
	//回调事件
	function callback(mutationsList, observer) { 
		if(mutationsList[0].target.disabled == true){
			//当动态设置为disabled的时候  变灰色
			$("#searchable-select-holder-"+targetNode.id).css("background-color","rgb(212,208,200)");
		}else{
			$("#searchable-select-holder-"+targetNode.id).css("background-color","white");
		}
	}
	var mutationObserver = new MutationObserver(callback);
	mutationObserver.observe(targetNode, option);
	
	this.keyFlag = true;
	
	_element.unbind('keyup').on('keyup', function(event){
		setTimeout(function(){
			console.log(123);
			if(_this.element.val() != "" && _this.keyFlag){
				_this.keyFlag = false;
				_this.element.attr("value",_this.element.val());
				_this.keyFlag = true;
			}
		 },700);
    });
	
	//同一个标签重新加载  先删除原来的元素
	var holderDiv = document.getElementById("searchable-select-"+_element[0].id);
	var selectDiv = document.getElementById("searchable-select-dropdown-"+_element[0].id);
	if(holderDiv){
		this.status="hide";
		holderDiv.parentNode.removeChild(holderDiv);
	}
	if(selectDiv){
		selectDiv.parentNode.removeChild(selectDiv);
	}
	
    this.element = element;
    this.options = options || {};
    this.init();
    var _this = this;
    
    _this.stauts = "hide";
    
    this.searchableElement.click(function(event){
    	if(_this.onClickMethod){
    		_this.element[0].onclick();
    	}
    	var holder = document.getElementsByClassName("searchable-select-dropdown");
    	var className = _this.dropdown[0].className;
    	for(var i = 0 ;i<holder.length; i++){
    		holder[i].className = "searchable-select-dropdown searchable-select-hide";
    	}
    	if(className == "searchable-select-dropdown searchable-select-hide"){
    		_this.show();
    	}else{
    		_this.hide();
    	}
    }).on('keydown', function(event){
      if (event.which === 13 || event.which === 40 || event.which == 38){
        event.preventDefault();
        _this.show();
      }
    });
	$(document).on('click', null, function(event){
		if(event.toElement && (!(event.toElement.className === 'searchable-select-input')) && (!(event.toElement.className === 'searchable-select-holder')) 
				&& event.toElement.id != _this.element[0].id){
			_this.hide();
		} 
	});
    
	//输入框点击时事件
    this.input.on('click',function(){
    	this.style.borderColor = "rgb(102, 175, 233)";
    });
    
    //输入框离焦事件
    this.input.on('blur',function(){
    	this.style.borderColor = "rgb(212, 208, 200)";
    });
    
    //change事件 改变界面显示的文字
    $("#"+_this.element[0].id).on('change',function(data){
    	var item = null;
    	var items  = _this.items[0].children;
    	for(var i=0;i<items.length;i++){
    		if($(items[i])[0].dataset.value.trim() == $.trim($("#"+_this.element[0].id).val())){
    			_this.holder.text($(items[i]).text());
    			return;
    		}
    	}
    });
    
    //搜索框事件
    this.input.on('keydown', function(event){
      event.stopPropagation();
      if(event.which === 13){         //回车事件
        event.preventDefault();
        _this.selectCurrentHoverItem();
        _this.hide();
      } else if (event.which == 27) { //esc事件
        _this.hide();
      } else if (event.which == 40) { //down
        _this.hoverNextItem();
      } else if (event.which == 38) { //up
        _this.hoverPreviousItem();
      }
    }).on('keyup', function(event){
      if(event.which != 13 && event.which != 27 && event.which != 38 && event.which != 40)
        _this.filter();
    });
    
    $("#searchable-select-"+this.element[0].id).hide();
    
    //点击下拉框 不出现原本该下拉的内容
    $("#"+this.element[0].id).attr("onmousedown","javascript:return false;");
    
    _this.clickMethod = this.element[0].click;

	$("#"+this.element[0].id)[0].addEventListener("click",function(event){
		if(_this.onClickMethod){
			_this.element[0].onclick();
		}
		var holder = document.getElementsByClassName("searchable-select-dropdown");
		var className = _this.dropdown[0].className;
		for(var i = 0 ;i<holder.length; i++){
			holder[i].className = "searchable-select-dropdown searchable-select-hide";
		}
		if(_this.status == "hide" || !_this.status){
			_this.show();
		}else{
			_this.hide();
		}
	});
  }

  var $sS = $.searchableSelect;

  $sS.fn = $sS.prototype = {
    version: '0.0.1'
  };

  $sS.fn.extend = $sS.extend = $.extend;

  $sS.fn.extend({
    init: function(){
      var id = _element[0].id;
      var left = _element[0].parentNode.offsetLeft+132; //距离当前页面左边距离
      var _this = this;
      var top = $(window).height() - $("#"+id).offset().top-20;//距离当前页面顶部距离
      var voffset = $("#"+id).offset(); 
      var bodyHeight = parseInt(window.document.body.clientHeight);
      
      left = voffset.left;
      top = voffset.top + $("#"+id).outerHeight();
     
      _this.onClickMethod = this.element[0].onclick;
      _this.clickMethod = this.element[0].click;
      
      var body = $("body");
      var ulNode = $(_this.element[0].parentNode.parentNode);
      this.searchableElement = $('<div tabindex="0" class="searchable-select" id="searchable-select-'+id+'" style="z-index:9999;"></div>');
      this.holder = $('<div class="searchable-select-holder" id="searchable-select-holder-'+id+'"></div>');
      this.dropdown = $('<div class="searchable-select-dropdown searchable-select-hide" id="searchable-select-dropdown-'+id+'" style="top:'+top+'px;left:'+left+'px;"></div>');
      this.input = $('<input type="text" class="searchable-select-input" style="height:20px;width:100%" placeholder="搜索..."/> ');
      this.items = $('<div class="searchable-select-items"></div>');
      this.caret = $('<span class="caret"></span>');

      this.scrollPart = $('<div class="searchable-scroll"></div>');
      this.hasPrivious = $('<div class="searchable-has-privious">...</div>');
      this.hasNext = $('<div class="searchable-has-next">...</div>');

      this.hasNext.on('mouseenter', function(){

        var f = function(){
          var scrollTop = _this.items.scrollTop();
          _this.items.scrollTop(scrollTop + 20);
          _this.hasNextTimer = setTimeout(f, 50);
        }

        f();
      }).on('mouseleave', function(event) {
        clearTimeout(_this.hasNextTimer);
      });

      this.hasPrivious.on('mouseenter', function(){
        _this.hasPriviousTimer = null;

        var f = function(){
          var scrollTop = _this.items.scrollTop();
          _this.items.scrollTop(scrollTop - 20);
          _this.hasPriviousTimer = setTimeout(f, 50);
        }

        f();
      }).on('mouseleave', function(event) {
        clearTimeout(_this.hasPriviousTimer);
      });
      
      this.dropdown.append(this.input);
      this.dropdown.append(this.scrollPart);

      //this.scrollPart.append(this.hasPrivious);
      this.scrollPart.append(this.items);
      //this.scrollPart.append(this.hasNext);
      
      this.searchableElement.append(this.caret);
      this.searchableElement.append(this.holder);
      body.append(this.dropdown);
      this.element.after(this.searchableElement);

      this.buildItems();
      this.setPriviousAndNextVisibility();
    },

    filter: function(){
      var text = this.input.val();
      this.items.find('.searchable-select-item').addClass('searchable-select-hide');
      this.items.find('.searchable-select-item:searchableSelectContains('+text+')').removeClass('searchable-select-hide');
      if(this.currentSelectedItem.hasClass('searchable-select-hide') && this.items.find('.searchable-select-item:not(.searchable-select-hide)').length > 0){
        this.hoverFirstNotHideItem();
      }

      this.setPriviousAndNextVisibility();
    },

    hoverFirstNotHideItem: function(){
      this.hoverItem(this.items.find('.searchable-select-item:not(.searchable-select-hide)').first());
    },

    selectCurrentHoverItem: function(){
      if(!this.currentHoverItem.hasClass('searchable-select-hide'))
        this.selectItem(this.currentHoverItem);
    },

    hoverPreviousItem: function(){
      if(!this.hasCurrentHoverItem())
        this.hoverFirstNotHideItem();
      else{
        var prevItem = this.currentHoverItem.prevAll('.searchable-select-item:not(.searchable-select-hide):first')
        if(prevItem.length > 0)
          this.hoverItem(prevItem);
      }
    },

    hoverNextItem: function(){
      if(!this.hasCurrentHoverItem())
        this.hoverFirstNotHideItem();
      else{
        var nextItem = this.currentHoverItem.nextAll('.searchable-select-item:not(.searchable-select-hide):first')
        if(nextItem.length > 0)
          this.hoverItem(nextItem);
      }
    },

    buildItems: function(){
      var _this = this;
      var selectDiv = this.element;
      if(selectDiv[0].onchange){
    	  _this.method =  selectDiv[0].onchange ;
      }else{
    	  _this.method = "";
      }
      this.element.find('option').each(function(){
        var item = $('<div class="searchable-select-item" id="item_'+_this.element[0].id+'" data-value="'
        			+$(this).attr('value')+'" onclick="'+_this.method+'">'+$(this).text()+'</div>');

        if(this.selected){
          _this.selectItem(item);
          _this.hoverItem(item);
        }

        item.on('mouseenter', function(){
          $(this).addClass('hover');
        }).on('mouseleave', function(){
          $(this).removeClass('hover');
        }).click(function(event){
          event.stopPropagation();
          //值未改变时,不触发change方法 add by zzr 2020/08/24
          if($("#"+_this.element[0].id).val() != $(this)[0].dataset.value) {
        	  _this.selectItem($(this),"select");
          }
          _this.hide();
        });
        _this.items.append(item);
      });

      this.items.on('scroll', function(){
        _this.setPriviousAndNextVisibility();
      })
    },
    show: function(){ 
      if(this.element[0].disabled == true){
    	return;
      }
      var id = this.element[0].id;
      //当显示的时候 重新计算下拉框显示的位置
      var left = _element[0].parentNode.offsetLeft+132;
      var _this = this;
      var top = $(window).height() - $("#"+id).offset().top-20;
      var voffset = $("#"+id).offset();
      var bodyHeight = parseInt(window.document.body.clientHeight);
      left = voffset.left;
      top = voffset.top + $("#"+id).outerHeight();
      this.dropdown[0].style.left = left + "px";
      this.dropdown[0].style.top = top +'px'
      this.dropdown[0].style.display = "inline-block";
      this.dropdown.removeClass('searchable-select-hide');
      this.input.focus();
      this.input[0].style.borderColor = "rgb(102, 175, 233)";
      this.filter();
      this.status = 'show';
      this.setPriviousAndNextVisibility();
    },

    hide: function(){
      this.input.val('');
      this.dropdown[0].style.display = "none";
      if(this.items.find(':not(.searchable-select-hide)').length === 0)
          this.input.val('');
      this.dropdown.addClass('searchable-select-hide');
      this.searchableElement.trigger('focus');
      this.status = 'hide';
    },
    
    hasCurrentSelectedItem: function(){
      return this.currentSelectedItem && this.currentSelectedItem.length > 0;
    },

    //选择后处理事件
    selectItem: function(item,flag){
      if(this.hasCurrentSelectedItem()){
    	  this.currentSelectedItem.removeClass('selected');
      }

      this.currentSelectedItem = item;
      item.addClass('selected');

      this.hoverItem(item);
      
      var value = item.data('value');
      this.element.val(value);
      if(value || value === "0" || value === 0){
    	  this.setDivValue(value,flag);
      } else {
    	  if($("#"+this.element[0].id).attr("data-bv-field") && flag && flag == 'select'){
   			 var name = this.element[0].id;
   	  		 var formId = $("#"+this.element[0].id).parents('form')[0].id;
   	  		 if(!formId){
   	  			 formId = "dataForm";
   	  		 }
   	  		var st = "NOT_VALIDATED";
   	  		var dt = formId?formId:"dataForm";
   	  		if ($('#'+dt) && $('#'+dt).data('bootstrapValidator') 
   	  				&& $('#'+name).attr("data-bv-field")==name){
   	  			$('#'+dt).data('bootstrapValidator').updateStatus(name, st).validateField(name);
   	  		}
   	  		// validateRefresh(name);
   	  		
   	  		var $events= $._data($("#"+this.element[0].id)[0],'events');
   	  		if(flag && flag == 'select' && $events && $events['change']){
   	  			this.element.change();
			}
   	  	 }else{
	   	  	 if(flag && flag == 'select'){
				  this.element.change();
			 }
   	  	 }
		 
      }
      
      
      this.holder.text(item.text());
      if(this.options.afterSelectItem){
        this.options.afterSelectItem.apply(this);
      }
    },
    
    //设置界面显示
    setDivValue:function(value,flag){
      var formId = $("#"+this.element[0].id).parents('form')[0].id;
      $("#"+this.element[0].id)[0].value = $.trim(value);
  	  $("#"+this.element[0].id).attr("value",value);
  	  var holderClassList = document.getElementsByClassName("searchable-select-holder");
      
  	  //有方法的，标签中onchange=xxx()
  	  if(this.method && this.method != ""){
  		  //选择的时候才触发改变事件 设置默认值的时候不触发
  		  if(flag && flag == 'select'){
  			  this.element.change();
  		  }
  		  for(var i= 0 ;i < holderClassList.length ; i++){
  			  var id = holderClassList[i].id.split('searchable-select-holder-')[1];
  			  var items = document.getElementById("searchable-select-dropdown-"+id).getElementsByClassName("searchable-select-item");
  			  for(var j=0;j<items.length;j++){
  				  if($(items[j])[0].dataset.value.trim()!="" &&  $(items[j])[0].dataset.value.trim() == $.trim($("#"+id).val())){
  					  if($(items[j]).text()!=""){
	  					  $("#"+holderClassList[i].id).text($(items[j]).text());
  					  }
  				  }
  			  }
  		  }
  	  }else{//没有方法的，通过js $(xxx).change 绑定
  		 var $events= $._data($("#"+this.element[0].id)[0],'events');
  		 if(flag && flag == 'select' && $events && $events['change']){
			  this.element.change();
		 }
  	  }
  	  
  	  if(this.onClickMethod){
  		  if(flag && flag == 'select'){
			  this.element[0].onclick();
		  }
  		  for(var i= 0 ;i < holderClassList.length ; i++){
  			  var id = holderClassList[i].id.split('searchable-select-holder-')[1];
  			  var items = document.getElementById("searchable-select-dropdown-"+id).getElementsByClassName("searchable-select-item");
  			  for(var j=0;j<items.length;j++){
  				  if($(items[j])[0].dataset.value.trim() == $.trim($("#"+id).val())){
  					  $("#"+holderClassList[i].id).text($(items[j]).text());
  				  }
  			  }
  		  }
  	  }
  	  
      var objEvt = $._data($("#"+this.element[0].id)[0], "events");
  	  if(objEvt && objEvt["click"]){
  		$("#"+this.element[0].id)[0].click();
  	  }
  	  
  	  if($("#"+this.element[0].id).attr("data-bv-field") && flag && flag == 'select'){
  			var name = this.element[0].id;
  	  		var formId = $("#"+this.element[0].id).parents('form')[0].id;
  	  		var st = "NOT_VALIDATED";
  	  		var dt = formId?formId:"dataForm";
  	  		if ($('#'+dt) && $('#'+dt).data('bootstrapValidator') 
  	  				&& $('#'+name).attr("data-bv-field")==name){
  	  			$('#'+dt).data('bootstrapValidator').updateStatus(name, st).validateField(name);
  	  		}
  	  		// validateRefresh(name);
  	  	 }
    },

    hasCurrentHoverItem: function(){
      return this.currentHoverItem && this.currentHoverItem.length > 0;
    },

    hoverItem: function(item){
      if(this.hasCurrentHoverItem())
        this.currentHoverItem.removeClass('hover');

      if(item.outerHeight() + item.position().top > this.items.height())
        this.items.scrollTop(this.items.scrollTop() + item.outerHeight() + item.position().top - this.items.height());
      else if(item.position().top < 0)
        this.items.scrollTop(this.items.scrollTop() + item.position().top);

      this.currentHoverItem = item;
      item.addClass('hover');
    },

    setPriviousAndNextVisibility: function(){
      if(this.items.scrollTop() === 0){
        this.hasPrivious.addClass('searchable-select-hide');
        this.scrollPart.removeClass('has-privious');
      } else {
      //  this.hasPrivious.removeClass('searchable-select-hide');
      //  this.scrollPart.addClass('has-privious');
      }

      if(this.items.scrollTop() + this.items.innerHeight() >= this.items[0].scrollHeight){
        this.hasNext.addClass('searchable-select-hide');
        this.scrollPart.removeClass('has-next');
      } else {
       // this.hasNext.removeClass('searchable-select-hide');
        //this.scrollPart.addClass('has-next');
      }
    }
  });

  $.fn.searchableSelect = function(options){
    this.each(function(){
      var sS = new $sS($(this), options);
    });

    return this;
  };

})(jQuery);
