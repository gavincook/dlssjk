(function($){ 
$(document).ready(function () {
	 app.getJsonData(contextPath+"/stations/defaultstation",{}).pipe(function(station){
		 $("#currentStation").html(station.name).attr("data-id",station.id); 
	 });
	 
        $('body').layout({ applyDefaultStyles: true,
        north:{
        	spacing_open :6,
        	spacing_closed:6,
        	minSize:80
        }	
        });
     
        $( "#menu" ).accordion({
        	  heightStyle: "content",
        	  create: function( event, ui ) {
            		  var subMenus="";
            		  $.ajax({
            			  url:contextPath+'/menu/getSubMenus',
            			  data:{
            				  pid:ui.header.attr('id')
            			  },
            				type:'post',
            				dataType:'json',
            				success:function(response){
            					
            					$(eval(response)).each(function(index,e){
            						subMenus+="<li><a href='"+contextPath+e.url+"'>"+e.menuName+"</a></li>";
            						
            					});
            				
            					// $(ui.newPanel).menu("destroy").html(subMenus).menu();
            					$(ui.header.next()).html(subMenus).menu({
            						target:'main'
            					});
            					 
            				},
            				error:function(){
            					alert("error");
            				}
            		  });
        	  },
        	  activate: function( event, ui ) {
        		  if($(ui.newPanel).attr("role")!='menu'){
        		  var subMenus="";
        		  $.ajax({
        			  url:contextPath+'/menu/getSubMenus',
        			  data:{
        				  pid:ui.newHeader.attr('id')
        			  },
        				type:'post',
        				dataType:'json',
        				success:function(response){
        					
        					$(eval(response)).each(function(index,e){
        						subMenus+="<li><a  href='"+contextPath+e.url+"'>"+e.menuName+"</a></li>";
        						
        					});
        					// $(ui.newPanel).menu("destroy").html(subMenus).menu();
        						 $(ui.newPanel).html(subMenus).menu({
             						target:'main'
             					});
        					 
        				},
        				error:function(){
        					alert("error");
        				}
        		  });
        		  }
        	  }
        });
        
        $("#menu").accordion("activate");
        //菜单处理
    	$(".ui-corner-all","#menu").live("click",function(event){
    		$(".ui-corner-all","#menu").removeClass("ui-state-hover");
    		$(event.target).addClass("ui-state-hover");
    	});
    	
    	$(".stationlist li").live("click",function(event){
    		$(this).siblings().removeClass("active");
    		$(this).addClass("active");
    	});
    	
    	$(".stationdialog .return").click(function(event){
    		$("#stationselect").hide("slow");
    	});
    	
    	$(".stationdialog .confirm").click(function(event){
    		var selectedStation = $(".stationlist li.active:eq(0)");
    		$("#currentStation").html($(selectedStation).html()).attr("data-id",$(selectedStation).attr("data-id"));
    		$("#stationselect").hide("slow");
    	});
    	
    	$(".currentstation").click(function(event){
    		var currentStationId = $("#currentStation",$(event.target).parent()).attr("data-id");
    		 app.getJsonData(contextPath+"/stations/getStations",{}).pipe(function(json){
    			 var stationList ="";
    			 $.each(json,function(index,element){
    				 if(currentStationId==element.id)
    					 stationList += "<li data-id='"+element.id+"' class='active'>"+element.name+"</li>"; 
    				 else
    					 stationList += "<li data-id='"+element.id+"'>"+element.name+"</li>"; 
    			 });
    			 $("#stationlist").html(stationList);
    		 });
    		 $("#stationselect").show();
    	 
    	});
    	
    	$("#alarmInfo").click(function(){
    		$(this).hide("slow");
    	});
    });
})(jQuery);