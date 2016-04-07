function drawFrameLine(dataObj,scaleValue,left){
	if(dataObj == null){ return; }
	var obj_Frame = dataObj.Deltas;
	var obj_Acquire = dataObj.Acquire;
	var obj_Vsyncdeltas = dataObj.Vsyncdeltas;	
	var canvas=document.getElementById('panel_frame');
	var context=canvas.getContext('2d');
	context.clearRect(0,0,1000,50);
	context.beginPath();
	
	context.font = '11px "Segoe UI"';
	context.fillStyle = "#000000";
	context.fillText("Frame",2,13);
	context.fillText("AcquireBuffer",2,28);
	context.fillText("Vsync",2,43);
	
	var index_X;
	var width;
	for(var i=0;i<obj_Frame.length;i++){
		index_X = ms2px(obj_Frame[i].start/1000,scaleValue,left);
		width = ms2px(obj_Frame[i].duration/1000,scaleValue,0);
		width = width < 1 ? 1:width;
		context.fillRect(index_X,5,width,10);
	}	
	for(var i=0;i<obj_Acquire.length;i++){
		index_X = ms2px(obj_Acquire[i].start/1000,scaleValue,left);
		width = ms2px(obj_Acquire[i].duration/1000,scaleValue,0);
		width = width < 1 ? 1:width;
		context.fillRect(index_X,20,width,10);
	}
	for(var i=0;i<obj_Vsyncdeltas.length;i++){
		index_X = ms2px(obj_Vsyncdeltas[i]/1000,scaleValue,left);
		context.fillRect(index_X,35,1,10);
	}
	context.stroke();
	
	//点击事件
	canvas.onclick = function(e) {
		for(var i=0;i<obj_Frame.length;i++){			
			if(e.offsetY > 5 && e.offsetY < 15){
				if(e.offsetX > ms2px(obj_Frame[i].start/1000,scaleValue,left) && e.offsetX < ms2px((obj_Frame[i].duration+obj_Frame[i].start)/1000,scaleValue,left)){
					$.ajax({
						type: "POST",
						url: "GetPictureByFrame.action",
						data: "frameIndex=" + i,
						success: function(data){
							var dataObj=eval("("+data+")");
							var top=Math.round((window.screen.height-685)/2);  
            				var left=Math.round((window.screen.width-395)/2); 
							window.open("iconPage.html?iconName="+dataObj.iconName,"","height=685,width=395,top="+top+",left="+left+",toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no");
						}
					});
					return;
				}
			}
		}
	}
}