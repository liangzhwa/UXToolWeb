function drawPowerLine(dataObj,scaleValue,valueBase){
	if(dataObj == null){ return; }
	var obj_Power = dataObj.Power;
	var canvas=document.getElementById('panel_power');
	var context=canvas.getContext('2d');
	context.clearRect(0,0,1000,70);
	context.beginPath();
	context.font = '11px "Segoe UI"';
	context.fillStyle = "#000000";
	context.fillText("Power",2,13);
	context.fillStyle = "#000000";

	var index_X;
	var index_Y;
	var maxA = obj_Power[0][1];
	var minA = obj_Power[0][1];
	for(var i=0;i<obj_Power.length;i++){
		index_X = ms2px(obj_Power[i][0],scaleValue,valueBase);
		index_Y = 70*(1-obj_Power[i][1]/1000);
		maxA = maxA > obj_Power[i][1] ? maxA : obj_Power[i][1];
		minA = minA < obj_Power[i][1] ? minA : obj_Power[i][1];
		if(i < obj_Power.length-1){
			index_X_next = ms2px(obj_Power[i+1][0],scaleValue,valueBase);
			index_Y_next = 70*(1-obj_Power[i+1][1]/1000);
			context.moveTo(index_X,index_Y);
			context.lineTo(index_X_next,index_Y_next);
		}
	}
	context.fillStyle = "#FF5151";
//	context.moveTo(0,70*(1-maxA/1000));context.lineTo(1000,70*(1-maxA/1000));
//	context.moveTo(0,70*(1-minA/1000));context.lineTo(1000,70*(1-minA/1000));	
	context.fillRect(0,70*(1-maxA/1000),1000,1);
	context.fillRect(0,70*(1-minA/1000),1000,1);
	context.fillText(maxA,950,70*(1-maxA/1000)-1);
	context.fillText(minA,950,70*(1-minA/1000)-1);
	context.stroke();
	
	canvas.onmousedown = function(e) {
		if (e.which==3){
			ms_X = px2ms(e.offsetX,scaleValue,valueBase);
			for(var i=0;i<obj_Power.length;i++){
				if(i < obj_Power.length-1){
					if(ms_X>=obj_Power[i][0] && ms_X<obj_Power[i+1][0]){
						alert("Main Avg Current (mA): " + obj_Power[i][1]);
						console.log(111);
					}
				}
			}
		}
	}
	canvas.onmousemove = function(e) {
		if(e.offsetX<45 || e.offsetX>900){ return; }
		ms_X = px2ms(e.offsetX,scaleValue,valueBase);
		for(var i=0;i<obj_Power.length;i++){
			if(i < obj_Power.length-1){
				if(ms_X>=obj_Power[i][0] && ms_X<obj_Power[i+1][0]){
//					var x = e.offsetX<45 ? 45:e.offsetX;
//					x = e.offsetX>900 ? 900:e.offsetX;
					context.clearRect(45,0,900,10);
					context.fillText(obj_Power[i][1],e.offsetX,10);
				}
			}
		}
	}
	canvas.onmouseout = function(e) {
		ms_X = px2ms(e.offsetX,scaleValue,valueBase);
		for(var i=0;i<obj_Power.length;i++){
			if(i < obj_Power.length-1){
				if(ms_X>=obj_Power[i][0] && ms_X<obj_Power[i+1][0]){
//					var x = e.offsetX<45 ? 45:e.offsetX;
//					x = e.offsetX>900 ? 900:e.offsetX;
					context.clearRect(45,0,900,10);
				}
			}
		}
	}
}