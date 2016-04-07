function drawIothroughputLine(dataObj,scaleValue,valueBase){
	if(dataObj == null){ return; }
	var obj_IO = dataObj.IO;
	var canvas=document.getElementById('panel_io');
	var context=canvas.getContext('2d');
	context.clearRect(0,0,1000,100);
	context.beginPath();
	context.font = '11px "Segoe UI"';
	context.fillStyle = "#000000";
	context.fillText("IO:",2,13);
	context.fillStyle = "#FF0000";
	context.fillText("Network Tran 0 Kbit",2,33);
	context.fillStyle = "#FF69B4";
	context.fillText("Network Recv 0 Kbit",2,53);
	context.fillStyle = "#8B0000";
	context.fillText("Storage Write 0 Kbit",2,73);
	context.fillStyle = "#0000FF";
	context.fillText("Storage Read 0 Kbit",2,93);
	context.fillStyle = "#A9A9A9";
	context.moveTo(0,35);context.lineTo(1000,35);
	context.moveTo(0,55);context.lineTo(1000,55);
	context.moveTo(0,75);context.lineTo(1000,75);
	context.moveTo(0,95);context.lineTo(1000,95);
	context.fillStyle = "#000000";
	for(var i=0;i<obj_IO.IO1.length;i++){
		context.fillText(obj_IO.IO1[i].text, ms2px(obj_IO.IO1[i].X2/1000,scaleValue,valueBase), 33);
//		context.moveTo(obj_IO.IO1[i].X1, obj_IO.IO1[i].Y1);
//		context.lineTo(obj_IO.IO1[i].X2, obj_IO.IO1[i].Y2);
	}
	for(var i=0;i<obj_IO.IO2.length;i++){
		context.fillText(obj_IO.IO2[i].text, ms2px(obj_IO.IO2[i].X2/1000,scaleValue,valueBase),53);
//		context.moveTo(obj_IO.IO2[i].X1, obj_IO.IO2[i].Y1);
//		context.lineTo(obj_IO.IO2[i].X2, obj_IO.IO2[i].Y2);
	}
	for(var i=0;i<obj_IO.IO3.length;i++){
		context.fillText(obj_IO.IO3[i].text, ms2px(obj_IO.IO3[i].X2/1000,scaleValue,valueBase), 73);
//		context.moveTo(obj_IO.IO3[i].X1, obj_IO.IO3[i].Y1);
//		context.lineTo(obj_IO.IO3[i].X2, obj_IO.IO3[i].Y2);
	}
	for(var i=0;i<obj_IO.IO4.length;i++){
		context.fillText(obj_IO.IO4[i].text, ms2px(obj_IO.IO4[i].X2/1000,scaleValue,valueBase), 93);
//		context.moveTo(obj_IO.IO4[i].X1, obj_IO.IO4[i].Y1);
//		context.lineTo(obj_IO.IO4[i].X2, obj_IO.IO4[i].Y2);
	}
	context.stroke();
}