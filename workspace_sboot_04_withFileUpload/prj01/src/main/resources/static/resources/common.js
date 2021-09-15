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

//=====================================================================
function getRandomData( arr ){
	var randomData ="";
	var arr_cnt = arr.length;
	var random_index = Math.floor(Math.random()*arr_cnt);

	randomData = arr[random_index];

	return randomData;
}

//------------------------------------------------------------------------
function getRandomDataArr( arr, cnt ){
	//방법1: 뽑을때마다 중복되는지 확인
	//방법2: array객체를 복사한 후 뽑을 때마다 제거
	if(arr.length<cnt){
		alert("입력된 수가 배열의 길이보다 큽니다.");
		return null;
	}
	
	var randomDataArr=[];
	
	for (let i = 0; i < cnt; i++) {
		var random_index = Math.floor(Math.random()*cnt);
		
		if(randomDataArr.indexOf(arr[random_index])<0){
			randomDataArr.push(arr[random_index]);
		}else{
			cnt++;
		}
		
		if (arr[i] === undefined) {
      		i = i - 1;
   		 }
	}
	
	return randomDataArr;
}

//------------------------------------------------------------------------
function getRandomDataArr2( arr, min_cnt, max_cnt ){
	if(arr.length<min_cnt||arr.length<max_cnt){
		alert("입력된 수가 배열의 길이보다 큽니다.");
		return null;
	}
	
	if(min_cnt>max_cnt){
		alert("최소값이 최대값보다 큽니다.");
		return null;
	}else if(min_cnt==max_cnt){
		alert("최소값과 최대값이 동일합니다.");
		return null;
	}

	var randomDataArr=[];
	
	var arr_cnt = Math.floor(Math.random()*(max_cnt-min_cnt+1))+min_cnt;
	
	
	for (let i = 0; i < arr_cnt; i++) {
		var random_index = Math.floor(Math.random()*arr_cnt);
		
		if(randomDataArr.indexOf(arr[random_index])<0){
			randomDataArr.push(arr[random_index]);
		}else{
			arr_cnt++;
		}	
		
		if (arr[i] === undefined) {
      		i = i - 1;
   		}
	}
	return randomDataArr;
}



