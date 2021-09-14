function setTrBgcolor( tableClassV , headBgcolor, oddTrBgcolor, evenTrBgcolor, mouseOverBgColor ){

	try{
		var tableObj = $("."+tableClassV);
		var firstTrObj = tableObj.find("tr:eq(0)");
			//var firstTrObj = tableObj.find("tr:first");
			//var firstTrObj = tableObj.find("tr").first();
		var oddTrObj = firstTrObj.siblings("tr").filter(":even")
		var evenTrObj = firstTrObj.siblings("tr").filter(":odd")

		firstTrObj.attr("bgColor", headBgcolor );
		oddTrObj.attr("bgColor", oddTrBgcolor );
		evenTrObj.attr("bgColor", evenTrBgcolor );
		
		firstTrObj.find("td,th").css("color","white");	
		
		
		oddTrObj.hover(
			function(){
				$(this).attr("bgColor",mouseOverBgColor);
			}
			, function(){
				$(this).attr("bgColor",oddTrBgcolor);
			}
		);
		
		evenTrObj.hover(
			function(){
				$(this).attr("bgColor",mouseOverBgColor);
			}
			, function(){
				$(this).attr("bgColor",evenTrBgcolor);
			}
		);
	}
	catch(e){
		alert("setTrBgcolor 함수 호출 시 예외발생!"+ e.message );
	}
	
}