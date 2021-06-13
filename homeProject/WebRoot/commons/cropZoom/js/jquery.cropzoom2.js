/*
CropZoom v1.0.4
Release Date: April 17, 2010

Copyright (c) 2010 Gaston Robledo

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
(function($){
    
     var _self = null;
     var $options = null;

     $.fn.cropzoom2 = function(options){
        
        $options = $.extend(true,$.fn.cropzoom.defaults, options); 
        
        return this.each(function() {  
            
            //Verificamos que esten los plugins necesarios
            if(!$.isFunction($.fn.draggable) || !$.isFunction($.fn.resizable) || !$.isFunction($.fn.slider)){
                alert("You must include ui.draggable, ui.resizable and ui.slider to use cropZoom");
                return;
            }
            
            if($options.image.source == '' ||  $options.image.width == 0 || $options.image.height == 0){
                alert('You must set the source, witdth and height of the image element');
                return;
            }
            
            _self = $(this);
            _self.empty();
            _self.css({
                'width': $options.width,
                'height': $options.height,
                'background-color':$options.bgColor,
                'overflow':'hidden',
                'position':'relative',
                'border':'1px solid #666'
            });
            
            
            setData2('image',{ 
                h: $options.image.height,
                w: $options.image.width,
                posY: 0,
                posX: 0,
                scaleX: 0,
                scaleY: 0,
                rotation: 0,
                source: $options.image.source,
                sh: $options.image.height,
                sw: $options.image.width
            });
            
           
            calculateFactor();
            getCorrectSizes();
            calculateImageScale();

            getData2('image').posX = Math.abs(($options.width / 2) - (getData2('image').w / 2));
            getData2('image').posY = Math.abs(($options.height / 2) - (getData2('image').h/ 2));
            
            setData2('selector',{
                x : $options.selector.x,
                y : $options.selector.y,
                w : ($options.selector.maxWidth != null ? ($options.selector.w > $options.selector.maxWidth ? $options.selector.maxWidth : $options.selector.w) : $options.selector.w),
                h : ($options.selector.maxHeight != null ? ($options.selector.h > $options.selector.maxHeight ? $options.selector.maxHeight : $options.selector.h) : $options.selector.h)
            });
            
            var $svg = null;
            var $image = null;
            if(!$.browser.msie){
                $svg = _self[0].ownerDocument.createElementNS('http://www.w3.org/2000/svg', 'svg');
                $svg.setAttribute('id', 'k2');
                $svg.setAttribute('width', $options.width);
                $svg.setAttribute('height', $options.height);
                $svg.setAttribute('preserveAspectRatio', 'none');
                $image = _self[0].ownerDocument.createElementNS('http://www.w3.org/2000/svg','image');
                $image.setAttributeNS('http://www.w3.org/1999/xlink', 'href', $options.image.source);
                $image.setAttribute('width', getData2('image').w);
                $image.setAttribute('height', getData2('image').h);
                $image.setAttribute('id', 'img_to_crop2');
                $image.setAttribute('preserveAspectRatio', 'none');
                $($image).attr('x', 0);
                $($image).attr('y', 0);
                $svg.appendChild($image);
            }else{
                // Add VML includes and namespace
                //_self[0].ownerDocument.namespaces.add('v', 'urn:schemas-microsoft-com:vml', "#default#VML");
                // Add required css rules
                var style = document.createStyleSheet();
                style.addRule('v\\:image', "behavior: url(#default#VML);display:inline-block");
                style.addRule('v\\:image', "antiAlias: false;");
                
                $svg = $("<div />").attr("id","k2").css({
                    'width':$options.width,
                    'height':$options.height,
                    'position':'absolute' 
                });
                $image = document.createElement('v:image');
                $image.setAttribute('id','img_to_crop2');
                $image.setAttribute('src',$options.image.source);
                $image.setAttribute('gamma','0');
                
                $($image).css({
                    'position':'absolute',
                    'left': 0,
                    'top': 0,
                    'width': getData2('image').w,
                    'height':getData2('image').h
                });
                $image.setAttribute('coordsize', '21600,21600');
                $image.outerHTML = $image.outerHTML;
                
                
                var ext = getExtensionSource();
                if(ext == 'png' || ext == 'gif')
                    $image.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+$options.image.source+"',sizingMethod='scale');"; 
                $svg.append($image);
            }
          _self.append($svg);  
          calculateTranslationAndRotation2(); 
          //Bindear el drageo a la imagen a cortar
          $($('#img_to_crop2'),$image).draggable({
                drag:function(event,ui){
                	if(!checkCanMove(getData2('image').posX>ui.position.left,
                			getData2('image').posY<ui.position.top))
                  	  return false;
                  	return false;
                    getData2('image').posY = ui.position.top;
                    getData2('image').posX = ui.position.left; 
                    calculateTranslationAndRotation2();
                    //Fire the callback
                    if($options.onImageDrag != null)
                        $options.onImageDrag($('#img_to_crop2'),getData2('image'));
                    callResize();
                }
          });
          
          
          //Creamos el selector  
          createSelector();
          callResize();
          //Cambiamos el resizable por un color solido
          _self.find('.ui-icon-gripsmall-diagonal-se').css({
              'background':'#FFF',
              'border':'1px solid #000',
              'width':8,
              'height':8
          });
          //Creamos la Capa de oscurecimiento
          createOverlay(); 
          //Creamos el Control de Zoom 
          if($options.enableZoom) 
            createZoomSlider();
          //Creamos el Control de Rotacion
          if($options.enableRotation)
            createRotationSlider(); 
          //Maintein Chaining 
          return this;
        });
       
     }
     
     function getExtensionSource(){
        var parts = $options.image.source.split('.');
        return parts[parts.length-1];    
     }
     
     
     function calculateFactor(){
        getData2('image').scaleX = parseFloat($options.width / getData2('image').w);
        getData2('image').scaleY = parseFloat($options.height / getData2('image').h); 
     }
     
     function calculateImageScale(){
    	 getData2('image').scale = getData2('image').w / getData2('image').sw;
     }
     
     function getCorrectSizes(){
           
           var scaleX = getData2('image').scaleX;
           var scaleY = getData2('image').scaleY;
           if(scaleY < scaleX){
               getData2('image').h = $options.height;
               getData2('image').w = Math.round(getData2('image').w * scaleY);
           }else{
               getData2('image').h = Math.round(getData2('image').h * scaleX); 
               getData2('image').w = $options.width;    
           }
         
     }
     
     function calculateTranslationAndRotation2(){
          var rotacion = "";
          var traslacion = "";
          if($.browser.msie){
                rotacion = getData2('image').rotation;
                $('#img_to_crop2').css({
                    'rotation': rotacion,
                    'top': getData2('image').posY,
                    'left':getData2('image').posX 
                });
          }else{
                rotacion = "rotate(" + getData2('image').rotation + "," + (getData2('image').posX + (getData2('image').w / 2 )) + "," + (getData2('image').posY + (getData2('image').h / 2))  + ")";    
                traslacion = " translate(" + getData2('image').posX + "," + getData2('image').posY + ")"; 
                rotacion += traslacion
                $('#img_to_crop2').attr("transform",rotacion);
          }
     }
     
     function createRotationSlider(){
         var rotationContainerSlider = $("<div />").css({
             'position':'absolute',
             'background-color':'#FFF',
             'z-index':3,
             'opacity':0.6,
             'width':31,
             'height': _self.height() / 2,
             'top': 5,
             'left': 5
         }).mouseover(function(){
             $(this).css('opacity',1);
         }).mouseout(function(){
             $(this).css('opacity',0.6);
         });
         
         var rotMin = $('<div />').css({
             'color':'#000',
             'font':'700 11px Arial',
             'margin':'auto',
             'width':10
         });
         var rotMax = $('<div />').css({
             'color':'#000',
             'font':'700 11px Arial',
             'margin':'auto',
             'width':21
         });
         rotMin.html("0");
         rotMax.html("360");
         
         var $slider = $("<div />");
         //Aplicamos el Slider            
         $slider.slider({
            orientation: "vertical",  
            value: 360,
            min: 0,
            max: 360,
            step: (($options.rotationSteps > 360 || $options.rotationSteps < 0) ? 1 : $options.rotationSteps),
            slide: function(event, ui) {
                getData2('image').rotation = Math.abs(360 - ui.value);
                calculateTranslationAndRotation2(); 
                if($options.onRotate != null)
                      $options.onRotate($('#img_to_crop2'),getData2('image').rotation);
            }
         })
         rotationContainerSlider.append(rotMin);
         rotationContainerSlider.append($slider);
         rotationContainerSlider.append(rotMax);
         $slider.css({
             'margin':' 7px auto',
             'height': (_self.height() / 2) - 60,
             'position':'relative',
             'width':7
         });
         _self.append(rotationContainerSlider);
     }
     
     function createZoomSlider(){
         var zoomContainerSlider = $("<div />").css({
             'position':'absolute',
             'background-color':'#FFF',
             'z-index':3,
             'opacity':0.6,
             'width':31,
             'height': (_self.height() / 2),
             'top': 5,
             'right': 5
         }).mouseover(function(){
             $(this).css('opacity',1);
         }).mouseout(function(){
             $(this).css('opacity',0.6);
         });
         
         var zoomMin = $('<div />').css({
             'color':'#000',
             'font':'700 14px Arial',
             'margin':'auto',
             'width':'100%',
             'text-align':'center'
         }).html("<b>-</b>");
         var zoomMax = $('<div />').css({
             'color':'#000',
             'font':'700 14px Arial',
             'margin':'auto',
             'width':'100%',
             'text-align':'center'
         }).html("<b>+</b>");
         
         var $slider = $("<div />");
         //Aplicamos el Slider   
         $slider.slider({
            orientation: "vertical",  
            value: getPercentOfZoom(),
            min: $options.image.minZoom,
            max: $options.image.maxZoom,
            step: (($options.zoomSteps > $options.image.maxZoom || $options.zoomSteps < 0) ? 1 : $options.zoomSteps),
            slide: function(event, ui) {
                var zoomInPx_width =  (($options.image.width * Math.abs(ui.value)) / 100);
                var zoomInPx_height =  (($options.image.height * Math.abs(ui.value)) / 100);
                if(!$.browser.msie){
                    $('#img_to_crop2').attr('width',zoomInPx_width + "px");
                    $('#img_to_crop2').attr('height',zoomInPx_height + "px");
                }else{
                    $('#img_to_crop2').css({
                        'width': zoomInPx_width + "px",
                        'height': zoomInPx_height + "px"
                    });
                }
                
                getData2('image').w = zoomInPx_width;
                getData2('image').h = zoomInPx_height;
                calculateFactor(); 
                calculateImageScale();
                getData2('image').posX =  (($options.width / 2) - (getData2('image').w / 2));   
                getData2('image').posY =  (($options.height / 2) - (getData2('image').h / 2));   
                calculateTranslationAndRotation2();
                if($options.onZoom != null){
                    $options.onZoom($('#img_to_crop2'),getData2('image'));
                }
                
                callResize();
            }
         })
         
         zoomContainerSlider.append(zoomMax);
         zoomContainerSlider.append($slider);
         zoomContainerSlider.append(zoomMin);
         $slider.css({
             'margin':' 7px auto',
             'height': (_self.height() / 2) - 60,
             'width':7,
             'position':'relative'
         });
         
         _self.append(zoomContainerSlider);
     }
     
     function checkCanMove(isRight,isUp){
    	 var image = getData2('image');
         var selector = getData2('selector');
         
         if(isRight){
        	 if(image.posX+image.w <= selector.x+selector.w){
        		 return false; 
        	 }
         }else{
        	 if(image.posX >= selector.x){
        		 return false; 
        	 }
         }
         if(isUp){
        	 if(image.posY >= selector.y){
        		 return false;
        	 }
         }else{
        	 if(image.posY+image.h <= selector.y+selector.h){
        		 return false;
        	 }
         }
         return true;
     }
     function callResize(){
         if($options.resizeImageFunc)
        	 $options.resizeImageFunc(getParameters({}));
     }
     
     function getPercentOfZoom(){
         var percent = 0;
         if(getData2('image').w > getData2('image').h){
            percent = ((getData2('image').w * 100) / $options.image.width);
         }else{
            percent = ((getData2('image').h * 100) / $options.image.height); 
         }
         return percent;
     }
     
     function createSelector(){
        if($options.selector.centered){
           getData2('selector').y = ($options.height / 2) - (getData2('selector').h / 2);  
           getData2('selector').x = ($options.width / 2) - (getData2('selector').w / 2);  
        }
        var _selector = $('<div />').attr('id','selector2').css({
            'width': getData2('selector').w,
            'height': getData2('selector').h,
            'top': getData2('selector').y + 'px',
            'left': getData2('selector').x + 'px',
            'border':'1px dashed ' + $options.selector.borderColor,
            'position':'absolute',
            'cursor':'move'
        }).mouseover(function(){
             $(this).css({
                'border':'1px dashed '+ $options.selector.borderColorHover
            })
         }).mouseout(function(){
             $(this).css({
                'border':'1px dashed '+ $options.selector.borderColor
            })
         });
        
        
        //Aplicamos el drageo al selector
        _selector.draggable({
            containment: _self,
            iframeFix: true,
            refreshPositions: true,
            drag: function(event,ui){
                //Actualizamos las posiciones de la mascara 
              if(!checkCanMove(getData2('selector').x<ui.position.left,
            		  getData2('selector').y>ui.position.top))
            	  return false;
              getData2('selector').x = ui.position.left;
              getData2('selector').y = ui.position.top;
              makeOverlayPositions(ui);
              showInfo(_selector);
              if($options.onSelectorDrag != null)
                    $options.onSelectorDrag(_selector,getData2('selector')); 
              callResize();
            },
            stop: function(event,ui){
                //Ocultar la mascara
                hideOverlay();
                if($options.onSelectorDragStop != null)
                    $options.onSelectorDragStop(_selector,getData2('selector'));
            } 
        });
        /*_selector.resizable({
            aspectRatio: $options.selector.aspectRatio,
            maxHeight: $options.selector.maxHeight, 
            maxWidth: $options.selector.maxWidth,
            minHeight : $options.selector.h,
            minWidth: $options.selector.w,
            containment: 'parent', 
            resize: function(event,ui){
                 //Actualizamos las posiciones de la mascara
               getData2('selector').w = _selector.width();
               getData2('selector').h = _selector.height();
               makeOverlayPositions(ui);
               showInfo(_selector);
               if($options.onSelectorResize != null)
                   $options.onSelectorResize(_selector,getData2('selector')); 
            },
            stop:function(event,ui){
		hideOverlay();
                if($options.onSelectorResizeStop != null)
                   $options.onSelectorResizeStop(_selector,getData2('selector'));
            }
        });*/  
        
        showInfo(_selector);
        //Agregamos el selector al objeto contenedor
        _self.append(_selector);
     };
     
     function showInfo(_selector){
         
         var _infoView = null;
         var alreadyAdded = false;
         if(_selector.find("#infoSelector").length > 0){
             _infoView = _selector.find("#infoSelector");
         }else{
             _infoView = $('<div />').attr('id','infoSelector').css({
                'position':'absolute',
                'top':0,
                'left':0,
                'background':$options.selector.bgInfoLayer,
                'opacity':0.6,
                'font-size':$options.selector.infoFontSize + 'px',
                'font-family':'Arial',
                'color':$options.selector.infoFontColor,
                'width':'100%'
            });
         }
        if($options.selector.showDimetionsOnDrag){
           _infoView.html("X:"+ getData2('selector').x + "px - Y:" + getData2('selector').y + "px");
           alreadyAdded = true;
        }
        if($options.selector.showPositionsOnDrag){
            if(alreadyAdded)
                _infoView.html(_infoView.html() + " | W:"+ getData2('selector').w + "px - H:" + getData2('selector').h + "px");
            else
                _infoView.html("W:"+ getData2('selector').w + "px - H:" + getData2('selector').h + "px");
        }
        _selector.append(_infoView);
      }
     
     function createOverlay(){
        var arr =['t2','b2','l2','r2']
        $.each(arr,function(){
            var divO = $("<div />").attr("id",this).css({
             'overflow':'hidden',
             'background':$options.overlayColor,
             'opacity':0.6,
             'position':'absolute',
             'z-index':2,
             'visibility':'visible'   
            });
            _self.append(divO);  
        });
     }
     function makeOverlayPositions(ui){
        
            $("#t2").css({
                "display":"block",
                "width":$options.width,
                'height': ui.position.top,
                'left': 0,
                'top':0
            });
            $("#b2").css({
                "display":"block",
                "width":$options.width,
                'height': $options.height,
                'top': (ui.position.top + $("#selector2").height()) + "px",
                'left': 0
            })
            $("#l2").css({
                "display":"block",
                'left':0,
                'top': ui.position.top,
                'width': ui.position.left,
                'height': $("#selector2").height()
            })
            $("#r2").css({
                "display":"block",
                'top': ui.position.top,
                'left': (ui.position.left + $("#selector2").width()) + "px",
                'width': $options.width,
                'height': $("#selector2").height() + "px"
            })
     }
     function hideOverlay(){
         $("#t2,#b2,#l2,#r2").hide();
     }
     
     function setData2(key,data){
         _self.data(key+"2",data);
     }
     function getData2(key){
        return _self.data(key+"2");
     }
     
     
     /*Code taken from jquery.svgdom.js */
    /* Support adding class names to SVG nodes. */
    var origAddClass = $.fn.addClass;

    $.fn.addClass = function(classNames) {
        classNames = classNames || '';
        return this.each(function() {
            if (isSVGElem(this)) {
                var node = this;
                $.each(classNames.split(/\s+/), function(i, className) {
                    var classes = (node.className ? node.className.baseVal : node.getAttribute('class'));
                    if ($.inArray(className, classes.split(/\s+/)) == -1) {
                        classes += (classes ? ' ' : '') + className;
                        (node.className ? node.className.baseVal = classes :
                            node.setAttribute('class',  classes));
                    }
                });
            }
            else {
                origAddClass.apply($(this), [classNames]);
            }
        });
    };

    /* Support removing class names from SVG nodes. */
    var origRemoveClass = $.fn.removeClass;

    $.fn.removeClass = function(classNames) {
        classNames = classNames || '';
        return this.each(function() {
            if (isSVGElem(this)) {
                var node = this;
                $.each(classNames.split(/\s+/), function(i, className) {
                    var classes = (node.className ? node.className.baseVal : node.getAttribute('class'));
                    classes = $.grep(classes.split(/\s+/), function(n, i) { return n != className; }).
                        join(' ');
                    (node.className ? node.className.baseVal = classes :
                        node.setAttribute('class', classes));
                });
            }
            else {
                origRemoveClass.apply($(this), [classNames]);
            }
        });
    };

    /* Support toggling class names on SVG nodes. */
    var origToggleClass = $.fn.toggleClass;

    $.fn.toggleClass = function(className, state) {
        return this.each(function() {
            if (isSVGElem(this)) {
                if (typeof state !== 'boolean') {
                    state = !$(this).hasClass(className);
                }
                $(this)[(state ? 'add' : 'remove') + 'Class'](className);
            }
            else {
                origToggleClass.apply($(this), [className, state]);
            }
        });
    };

    /* Support checking class names on SVG nodes. */
    var origHasClass = $.fn.hasClass;

    $.fn.hasClass = function(className) {
        className = className || '';
        var found = false;
        this.each(function() {
            if (isSVGElem(this)) {
                var classes = (this.className ? this.className.baseVal :
                    this.getAttribute('class')).split(/\s+/);
                found = ($.inArray(className, classes) > -1);
            }
            else {
                found = (origHasClass.apply($(this), [className]));
            }
            return !found;
        });
        return found;
    };

    /* Support attributes on SVG nodes. */
    var origAttr = $.fn.attr;

    $.fn.attr = function(name, value, type) {
        if (typeof name === 'string' && value === undefined) {
            var val = origAttr.apply(this, [name, value, type]);
            return (val && val.baseVal ? val.baseVal.valueAsString : val);
        }
        var options = name;
        if (typeof name === 'string') {
            options = {};
            options[name] = value;
        }
        return this.each(function() {
            if (isSVGElem(this)) {
                for (var n in options) {
                    this.setAttribute(n,
                        (typeof options[n] == 'function' ? options[n]() : options[n]));
                }
            }
            else {
                origAttr.apply($(this), [name, value, type]);
            }
        });
    };

    /* Support removing attributes on SVG nodes. */
    var origRemoveAttr = $.fn.removeAttr;

    $.fn.removeAttr = function(name) {
        return this.each(function() {
            if (isSVGElem(this)) {
                (this[name] && this[name].baseVal ? this[name].baseVal.value = '' :
                    this.setAttribute(name, ''));
            }
            else {
                origRemoveAttr.apply($(this), [name]);
            }
        });
    }; 

    function isSVGElem(node) {
        return (node.nodeType == 1 && node.namespaceURI == 'http://www.w3.org/2000/svg');
    }
    
    function getParameters(custom){
        var image = getData2('image');
        var selector = getData2('selector');
        var fixed_data = {
             'viewPortW': _self.width(),
             'viewPortH': _self.height(),
             'imageX':image.posX,
             'imageY': image.posY,
             'imageRotate': image.rotation,
             'imageW': image.w,
             'imageH': image.h,
             'imageSource': image.source,
             'selectorX': selector.x,
             'selectorY': selector.y,
             'selectorW': selector.w,
             'selectorH': selector.h,
             'scale': image.scale
        };
        return $.extend(fixed_data,custom);
    }
     
    /* Defaults */ 
     $.fn.cropzoom.defaults = {  
           width: 500,  
           height: 375,
           bgColor: '#000',
           overlayColor: '#000',    
           selector: {
               x:0,
               y:0,
               w:229,
               h:100,
               aspectRatio:false,
               centered:false,
               borderColor: 'yellow',
               borderColorHover: 'red',
               bgInfoLayer: '#FFF',
               infoFontSize: 10,
               infoFontColor: 'blue',
               showPositionsOnDrag: true,
               showDimetionsOnDrag: true,
               maxHeight:null,
               maxWidth:null
           },
           image: {source:'',rotation:0,width:0,height:0,minZoom:10,maxZoom:150},
           enableRotation: true,
           enableZoom: true,
           zoomSteps: 1,
           rotationSteps: 5,
           onSelectorDrag:null,
           onSelectorDragStop: null,
           onSelectorResize: null,
           onSelectorResizeStop: null,
           onZoom: null,
           onRotate:null,
           onImageDrag:null
           
     };
     
     $.fn.extend({
        //Function to set the selector position and sizes
        setSelector: function(x,y,w,h,animate){
            if(animate != undefined && animate == true){
                $('#selector2').animate({
                    'top': y,
                    'left': x,
                    'width': w,
                    'height': h
                },'slow');
            }else{
                $('#selector2').css({
                    'top': y,
                    'left': x,
                    'width': w,
                    'height': h
                });
            }
            setData2('selector',{
                x: x,
                y: y,
                w: w,
                h: h
            });
        },
        //Restore the Plugin
        restore: function(){
             _self.empty();
             setData2('image',{});
             setData2('selector',{});
            _self.cropzoom($options);
            
        },
        //Send the Data to the Server
        send : function(url,type,custom,onSuccess){
        	 var obj = getParameters(custom);
             var response = "";
             $.ajax({
                 url : url,
                 type: type,
                 data: (getParameters(custom)),
                 error:function(rs){
                	alert(rs); 
                 },
                 success:function(r){ 
                    setData2('imageResult',r);
                    if(onSuccess !== undefined && onSuccess != null)
                        onSuccess(r);
                 }
             });
         },
         getSaveData2 : function(){
        	 var imageData = getParameters({});
        	 
        	 var x = Math.round((imageData.selectorX - imageData.imageX)/imageData.scale);
    		 var y = Math.round((imageData.selectorY - imageData.imageY)/imageData.scale);
    		 var width = Math.round(imageData.selectorW/imageData.scale);
    		 var height = Math.round(imageData.selectorH/imageData.scale);
    		 return {x:x,y:y,width:width,height:height};
         },
       //判断图片是否小于截取尺寸
         checkTooSmall2 : function(){
        	 var imageData = getParameters({});
        	 var imageW = parseInt(imageData.imageW);
        	 var imageH = parseInt(imageData.imageH);
        	 var selectorW = parseInt(imageData.selectorW);
        	 var selectorH = parseInt(imageData.selectorH);
        	 if(imageW<selectorW || imageH<selectorH){
        		 return true;
        	 }
        	 return false;
         }
     });

})(jQuery);
