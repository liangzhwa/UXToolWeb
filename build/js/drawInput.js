function drawInputLine(dataObj,scaleValue,left){
	if(dataObj == null){ return; }	
	var obj_Event = dataObj.Event;
	var obj_Dispatcher = dataObj.Dispatcher;	
	var canvas=document.getElementById('panel_input');
	var context=canvas.getContext('2d');
	context.clearRect(0,0,1000,50);
	context.beginPath();
	
	context.font = '11px "Segoe UI"';
	context.fillStyle = "#000000";
	context.fillText("InputReader",2,13);
	context.fillText("InputDispatcher",2,28);
	
	var index_X;
	for(var i=0;i<obj_Event.length;i++){
		index_X = ms2px(obj_Event[i]/1000,scaleValue,left);
		context.moveTo(index_X,5);
		context.lineTo(index_X,15);
	}	
	for(var i=0;i<obj_Dispatcher.length;i++){
		index_X = ms2px(obj_Event[i]/1000,scaleValue,left);
		context.moveTo(index_X,20);
		context.lineTo(index_X,30);
	}
	context.stroke();
}