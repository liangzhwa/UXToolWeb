var currentShowPic = 0;
var picList_video = [];
var timer = null;
function drawMethodRect(objData,scaleValue,left){
	if(objData == null) { return; }
	
	var rectValue=[];
	var canvas=document.getElementById('panel_method');
	var context=canvas.getContext('2d');
	context.clearRect(0,0,1000,200);
	context.beginPath();
	var base_Y = 0,base_Line = 0;
	for(var k=0;k<objData.Method.length;k++){
		var obj_Method = objData.Method[k].Methods;
		var startTime = objData.StartTime;		
		base_Y = base_Line;
		context.font = '11px "Segoe UI"';
		context.fillStyle = "#000000";
		context.lineWidth = 1;
		context.fillText(objData.Method[k].PidName,2,base_Y + 25); //画进程名
		var index_X;
		var index_Y;
		var rectWidth;
		var color,color_1=0,color_2=0,color_3=0,color_4=0;
		for(var i=0;i<obj_Method.length;i++){
			index_X = ms2px((obj_Method[i].start-startTime)/1000,scaleValue,left);
			rectWidth = ms2px(obj_Method[i].end/1000,scaleValue,0);
			switch (obj_Method[i].level){
				case 1:
					color = color_1 % 2 == 0 ? "#66b53b":"ff0000";
					base_Line = color_1<1 ? base_Line + 25 : base_Line;
					index_Y = 5 + base_Y;
					color_1++;
					break;
				case 2:
					color = color_2 % 2 == 0 ? "#66b53b":"ff0000";
					base_Line = color_2<1 ? base_Line + 25 : base_Line;
					index_Y = 30 + base_Y;
					color_2++;
					break;
				case 3:
					color = color_3 % 2 == 0 ? "#66b53b":"ff0000";
					base_Line = color_3<1 ? base_Line + 25 : base_Line;
					index_Y = 55 + base_Y;
					color_3++;
					break;
				case 4:
					color = color_4 % 2 == 0 ? "#66b53b":"ff0000";
					base_Line = color_4<1 ? base_Line + 25 : base_Line;
					index_Y = 80 + base_Y;
					color_4++;
					break;			
			}
			context.fillStyle = color;
			rectWidth = rectWidth < 1 ? "1" : rectWidth;
			//保存所画矩形区域，方便点击事件取值
			rectValue[i] = {"X":index_X,"Y":index_Y,"width":rectWidth,"start":Math.round((obj_Method[i].start-startTime)/1000),"end":Math.round(obj_Method[i].end/1000)};
			context.fillRect(index_X,index_Y,rectWidth,20); //画矩形
			
			if(rectWidth>7*obj_Method[i].text.length){
				context.font = '11px "Segoe UI"';
				context.fillStyle = "#000000";
				context.fillText(obj_Method[i].text,index_X,index_Y + 15); //画函数名
			}
		}
		if(k < objData.Method.length-1 ){
			context.moveTo(0,base_Line + 5);
			context.lineTo(1000,base_Line + 5);
		}				
	}
	context.stroke();
	
	//增加计算时长的事件
	var ComputeStart = 0;
	var ComputeEnd = 0;
	var drawStart = 0;
	var drawEnd = 0;		
	canvas.onmousedown = function(e) {
		if (e.which==2 || e.which==3 || (e.which==1 && e.ctrlKey==true)){
			ComputeStart = Math.round(px2ms(e.offsetX,scaleValue,left)*1000) + startTime;
			drawStart = e.offsetX;
			context.fillStyle = "#000000";
			context.fillRect(drawStart,0,1,200);
		}else if((e.which==1 && e.altKey==true)){	
			for(var i=0;i<rectValue.length;i++){
				if(e.offsetX>rectValue[i].X && e.offsetX<(rectValue[i].X+rectValue[i].width) && e.offsetY>rectValue[i].Y && e.offsetY<(rectValue[i].Y+20)){
					$("#dialog_Video").dialog({
						top:10,
						width:410,
						height:750,
						close:function(event, ui) {
							$("#imgDiv").html("");
						}
					});
					picList_video = [];
					for(var j=0;j<rectValue[i].end;j++){
						picList_video[j] = getQueryString("casename") + "/output/dst/"+drawImageData[j+rectValue[i].start];
					}
					currentShowPic = picList_video.length-1;
					PlayVideo();
					return;
				}
			}			
		}
	}
	canvas.onmouseup = function(e) {
		if (e.which==2 || e.which==3  || (e.which==1 && e.ctrlKey==true)){
			ComputeEnd = Math.round(px2ms(e.offsetX,scaleValue,left)*1000) + startTime;
			drawEnd = e.offsetX
			context.fillStyle = "#000000";
			context.fillRect(drawEnd,0,1,200);
			context.fillStyle = "#0000ff";
			context.fillRect(drawStart,50,drawEnd-drawStart,1);
			if((e.which==1 && e.ctrlKey==true)){
				console.log(navigator.plugins);
				if(navigator.plugins['UXTune']){
					console.log(navigator.plugins);
				}
				
				document.location = 'uxtool://-s '+ComputeStart+' -e '+ComputeEnd+' __PATH__'+objData.ServerPath+'/mytrace.txt__PATH__';
				setTimeout(function(){ drawMethodRect(objData,scaleValue,left); },2000);
			}else{
				$.ajax({
					type: "POST",
					url: "GetFPS.action",
					data: "startIndex="+ComputeStart+"&endIndex="+ComputeEnd,
					success: function(data){
						var dataObj=eval("("+data+")");
						$( "#lblTime" ).html("Time: " + (ComputeEnd-ComputeStart)/1000 + " ms");
						$( "#lblFPS" ).html(" FPS:" + Math.ceil(dataObj.fps/(ComputeEnd-ComputeStart)*1000000));
						var strCPUInfo =  dataObj.cpu.replace(/##BR##/g,"</br>");
						$( "#lblCPU" ).html(strCPUInfo);
						$( "#dialog" ).dialog({
							close:function(event, ui) {
								drawMethodRect(objData,scaleValue,left);
							}
						});
						ComputeStart = 0;
						ComputeEnd = 0;
					}
				});
			}
		}
	}
}
function showNext(){
	if(currentShowPic == (picList_video.length-1)){
		currentShowPic--;
		clearInterval(timer);
	}
	currentShowPic++;
	var root = location.href.substr(0,location.href.indexOf("UXToolWeb"));
	$("#imgDiv").html('<img src="'+root+picList_video[currentShowPic]+'" onclick="showNext()"/>');
}
function PlayVideo(){
	if(currentShowPic != (picList_video.length-1)){ return; }
	currentShowPic = 0;
	var root = location.href.substr(0,location.href.indexOf("UXToolWeb"));
	$("#imgDiv").html('<img src="'+root+picList_video[currentShowPic]+'" onclick="showNext()"/>');
	timer = setInterval(function(){
		$("#imgDiv img").trigger("click");
	},200);
}