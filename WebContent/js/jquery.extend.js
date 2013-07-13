/**
 * reset all the field of form,call like $("#loginForm").reset();
 */
$.fn.reset = function(){
	$(':input',this)  
	 .not(':button, :submit, :reset, :hidden')  
	 .val('')  
	 .removeAttr('checked')  
	 .removeAttr('selected'); 
};
 
 /**
  * ajax submit form,use like this:
  *	<p>  $("#loginForm").ajaxSubmitForm("login/validate",
  *			 function(result) {
  *		        // todo the code when success
  *	             }, 
  *	         function(result) {
  *		        // todo the code when failure
  *	        });
  *</p>
  * @param url        : the form submit url
  * @param successFun : when ajax submit form success,also the response message 
  *                     is success(means:the success propertity of responesText is true),
  *                     call the successFun with the responesText parameter
  * @param failureFun : if not call the successFun,then call the failureFun with responesText parameter 
  */
 $.fn.ajaxSubmitForm = function(url,data,successFun,failureFun,errorFun){
		$.ajax({
			url:url,
			data:$(this).serialize()+$.serializeToUrl(data,"&"),
			type:'post',
			dataType:'json',
			success:function(result){
				if(result.permission=='noPermission')
				{
					alert("您没有权限执行此操作.");
					return false;
				}
				if(result.success){
					successFun(result);
				
				}
				else
					failureFun(result);
			},
			failure:function(XMLHttpRequest, textStatus, errorThrown){
				errorFun(XMLHttpRequest, textStatus, errorThrown);
			}
		});
 };
 
 /**
  * 异步获取数据
  * 以post--json的方式获取，已处理权限判断
  * @param url 请求路径
  * @param data 传递的参数
  * @param successFun  success=true或其他不等于false,0的值，则回调此方法
  * @param failureFun success=false回调方法
  * @param errorFun  异步获取出错回调
  * @param async 是否采用异步方式,默认为true
  */
 $.postData = function(url,data,successFun,failureFun,errorFun,async){
		$.ajax({
			url:url,
			data:data,
			type:'post',
			async:async!==false,
			dataType:'json',
			beforeSend:function(){
				  	   $(".loading").show();
								},
			success:function(result){
				if(result.permission=='noPermission')
				{
					alert("您没有权限执行此操作.");
					return false;
				}
				$(".loading").hide();
				if(result.success){
					successFun(result);
				}
				else{
					if(failureFun)
					failureFun(result);
				}
			},
			failure:function(XMLHttpRequest, textStatus, errorThrown){
				$(".loading").hide();
				if(errorFun)
				errorFun(XMLHttpRequest, textStatus, errorThrown);
			}
		});
};

 /**
  * to turn a new url by type
  */
 $.href=function(url,type){
	 if(typeof(type)=="undefined"||type=='current')
	  window.location.href=url;
	 else
		 if(type=="new")
			 window.open(url);
 };
 
 $.serializeToUrl=function(o,prefix){
		if(typeof(prefix)=='undefined'){
			prefix = "";
		}
	    for(var i in o){
	    	prefix+=i+"="+o[i]+"&";
	    }
		
	    return prefix.substring(0,prefix.length-1);
 };
	
	
/**
 * 自动填充表单
 */
	$.fn.autoCompleteForm = function(url,data,exclude){
		var inputs = $(':input',this) .not(':button, :submit, :reset,[name="repassword"]');
		$.ajax({
			url:url,
			data:data,
			dataType:'json',
			type:'post',
			success:function(response){
				$.each(inputs,function(index,e){
					var temp;
					var name = $(e).attr("name");
					var position = name.indexOf(".");
					if(position!=-1){
						temp = response[name.substring(0,position)][name.substring(position+1)];
					}else{
						temp =response[name];
					}
					if(typeof(temp)!="undefined"&&temp!=null&&temp!="null")
						$(e).val(temp);
				});
				
				
			}
		});
		
	};
	
	$.returnValue=function(val){
		return val;
	};
	
	
var app = app || {};	
(function($){
	app.getJsonData = function(url,params){
		params = params || {};
		var dfd = $.Deferred();
		$.ajax({
			url		: url,
			type	: params.type?params.type:'Post',
			data	: params,
			dataType:'json',
			async : true
		}).success(function(data){
			dfd.resolve(data);
		}).fail(function(data){
			alert("对不起，该工作站无法连接，请检查与工作站的连接.");
			throw " exception: Cannot get data for " + url;
		});
		return dfd.promise();
	};
	
	app.stopPropagation = function(event){
		if (window.all) { 
	        event.cancelBubble = true;  
	    }else if (event){  
	    	event.stopPropagation();  
	    }  
	};
	
	app.render = function(html,data,targetSelector,emptyParent){
		var distContent = Handlebars.compile(html)(data);
		if(typeof emptyParent == 'undefined')
			emptyParent = true;
		if(emptyParent){
			$(targetSelector).empty().html(distContent);
		}else{
			$(targetSelector).append(distContent);
		}
	};
	
	app.selector = function(opts){
		if(typeof opts =='string'){
			$("select",$(opts)).multiselect({
				 selectedList: 4,
				 selectedText: "选择#项,共 #项 ",
				 noneSelectedText: '请选择',
				 checkAllText:'全选',
				 uncheckAllText: '全不选'
				}); 
			return false;
		}
			
		opts = $.extend({multiple:true,selectedList:1}, opts);
		if(opts.selector){
			 $("select",$(opts.selector).parent()).multiselect({
				 selectedList: opts.selectedList,
				 multiple: opts.multipe,
				 selectedText: "选择#项,共 #项 ",
				 noneSelectedText: '请选择',
				 checkAllText:'全选',
				 uncheckAllText: '全不选'
				}); 
			return false;
		}
		app.render(opts.html,opts.data,opts.targetSelector,opts.emptyParent);
		 $("select",$(opts.targetSelector)).multiselect({
			 selectedList: opts.selectedList,
			 multiple:opts.multiple,
			 selectedText: "选择#项,共 #项 ",
			 noneSelectedText: '请选择',
			 checkAllText:'全选',
			 uncheckAllText: '全不选',
			 click:opts.click,
			 beforeclose:opts.beforeclose
			}); 
	};
	
	app.assertZero = function(src,message){
		if((+src)==0){
			alert(message);
			return true;
		}
		return false;
	};
	
	window.app = app;
})(jQuery);