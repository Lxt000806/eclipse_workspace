// JavaScript Document
	$(function(){
	rolltab();
	
		tab(0);
		roll();
		nav();
		
	});
	var timer=null;
	var offset=5000;
	var y=0;	
	var e=0;
	function rolltab(){				
						
		if(e>mypath){
			e=0	
		}
		
		tab(e);
		timer=window.setTimeout(rolltab,offset)		
		e++;
	}
	function rolltab2(){				
		e--;				
		if(e<0){
			e=mypath	
		}
		tab(e);
		timer=window.setTimeout(rolltab,offset)		
	}
	function tab(e){
		var t=-(mymove*e)+"%";												
		$('.mid01_box').css({'-webkit-transform':"translate("+t+")",'-webkit-transition':'200ms linear'} );						
		$('.line').each(function(){
			$(this).find('li').eq(e).addClass('on');
			$(this).find('li').eq(e).siblings().removeClass('on');
			//alert(e);
		})
		
	}
	function time(){
		
		window.clearTimeout(timer,2000);
		timer=window.setTimeout(rolltab,offset)			
	}
	function nav(n){
		$('.line li').click(function(){
			var f=$(this).index();
			time();					
			$(this).addClass('on');
			$(this).siblings().removeClass('on');
			yi=mymove*f;
			var x=-yi+"%";			
			$(this).parents('.banner_box').find('.mid01_box').css({'-webkit-transform':"translate("+x+")",'-webkit-transition':'200ms linear'} );
			e= $(this).prevAll().length;
		
		})
		
	}
	
	function roll(){
		$("#ll").click(function(){
								time();
					rolltab2();
				
								})
		
			$("#rr").click(function(){
					time();
					rolltab();
					
								})
		
		$(".roll").each(function(){				
			$(this).swipe({			
				swipeLeft:function(){
					time();
					rolltab();
					
					
				},
				swipeRight:function() {
					
					
					time();
					rolltab2();
				}
			});
		})	
	}
