function drawCPULine(dataObj,scaleValue,valueBase){
	if(dataObj == null){ return; }
	var obj_CPU = dataObj.CPU;
	var canvas=document.getElementById('panel_cpu');
	var context=canvas.getContext('2d');
	context.clearRect(0,0,1000,120);
	context.beginPath();
	context.font = '11px "Segoe UI"';
	context.fillStyle = "#000000";
	context.fillText("CPU&GPU workload:",2,13);
	
	context.fillStyle = "#0000";
	context.fillRect(145,4,10,10);
	context.fillText("C0",157,13);
	context.fillStyle = "#4A4AFF";
	context.fillRect(180,4,10,10);
	context.fillText("C1",192,13);
	context.fillStyle = "#00FFFF";
	context.fillRect(215,4,10,10);
	context.fillText("C2",227,13);
	context.fillStyle = "#00DB00";
	context.fillRect(250,4,10,10);
	context.fillText("C3",262,13);
	context.fillStyle = "#D9B300";
	context.fillRect(285,4,10,10);
	context.fillText("C4",297,13);
	context.fillStyle = "#BB3D00";
	context.fillRect(320,4,10,10);
	context.fillText("C5",332,13);
	context.fillStyle = "#949449";
	context.fillRect(355,4,10,10);
	context.fillText("C6",367,13);
	context.fillStyle = "#408080";
	context.fillRect(390,4,10,10);
	context.fillText("S0i3",402,13);
	context.fillStyle = "#8F4586";
	context.fillRect(439,4,10,10);
	context.fillText("S0i1",451,13);
	context.fillStyle = "#9AFF02";
	context.fillRect(488,4,10,10);
	context.fillText("LpAudio",500,13);
	context.fillStyle = "#6C6C6C";
	context.fillRect(558,4,10,10);
	context.fillText("LPMP3",570,13);
	
	context.fillStyle = "#FF0000";
	context.fillText("CPU0",2,33);
	context.fillStyle = "#FF69B4";
	context.fillText("CPU1",2,53);
	context.fillStyle = "#8B0000";
	context.fillText("CPU2",2,73);
	context.fillStyle = "#0000FF";
	context.fillText("CPU3",2,93);
	context.fillStyle = "#FF0000";
	context.fillText("GPU",2,113);
	context.fillStyle = "#A9A9A9";
	context.moveTo(0,35);context.lineTo(1000,35);
	context.moveTo(0,55);context.lineTo(1000,55);
	context.moveTo(0,75);context.lineTo(1000,75);
	context.moveTo(0,95);context.lineTo(1000,95);
	context.moveTo(0,115);context.lineTo(1000,115);
	context.fillStyle = "#000000";
	
	var c_state_table=["C0","C1","C2","C3","C4","C5","C6","S0i3","S0i1","LpAudio","LPMP3"];
//	var state_color_map=["#0000","#bbbbff","#7777ff","#5555ff","#3333ff","#1111ff","#0000ff","#0011ff","#0022ff","#0033ff","#0044ff"];
	var state_color_map=["#0000","#4A4AFF","#00FFFF","#00DB00","#D9B300","#BB3D00","#949449","#408080","#8F4586","#9AFF02","#6C6C6C"];
	var inint_X;
	var index_X;
	var width;
	for(var i=0;i<obj_CPU.CPU0.length;i++){
		if(i == 0){			
			inint_X = ms2px(obj_CPU.CPU0[0].start_ts/1000,scaleValue,valueBase);
			inint_X = inint_X <= 0 ? 0:inint_X;
			context.fillStyle = "#FF0000";
			context.fillRect(inint_X,24,1000,10);
		}
		index_X = ms2px(obj_CPU.CPU0[i].start_ts/1000,scaleValue,valueBase);
		width = ms2px((obj_CPU.CPU0[i].end_ts-obj_CPU.CPU0[i].start_ts)/1000,scaleValue,0);
		width = width < 1 ? 1:width;
		context.fillStyle = state_color_map[obj_CPU.CPU0[i].state];
		context.fillRect(index_X,24,width,10);
		if(width > c_state_table[obj_CPU.CPU0[i].state].length*7){
			context.fillStyle = "#000000";
			context.fillText(c_state_table[obj_CPU.CPU0[i].state],index_X,33);
		}
	}
	for(var i=0;i<obj_CPU.CPU1.length;i++){
		if(i == 0){
			inint_X = ms2px(obj_CPU.CPU1[0].start_ts/1000,scaleValue,valueBase);
			inint_X = inint_X <= 0 ? 0:inint_X;
			context.fillStyle = "#FF0000";
			context.fillRect(inint_X,44,1000,10);
		}
		index_X = ms2px(obj_CPU.CPU1[i].start_ts/1000,scaleValue,valueBase);
		width = ms2px((obj_CPU.CPU1[i].end_ts-obj_CPU.CPU1[i].start_ts)/1000,scaleValue,0);
		width = width < 1 ? 1:width;
		context.fillStyle = state_color_map[obj_CPU.CPU1[i].state];
		context.fillRect(index_X,44,width,10);
		if(width > c_state_table[obj_CPU.CPU1[i].state].length*7){
			context.fillStyle = "#000000";
			context.fillText(c_state_table[obj_CPU.CPU1[i].state],index_X,53);
		}
	}
	for(var i=0;i<obj_CPU.CPU2.length;i++){
		if(i == 0){
			inint_X = ms2px(obj_CPU.CPU2[0].start_ts/1000,scaleValue,valueBase);
			inint_X = inint_X <= 0 ? 0:inint_X;
			context.fillStyle = "#FF0000";
			context.fillRect(inint_X,64,1000,10);
		}
		index_X = ms2px(obj_CPU.CPU2[i].start_ts/1000,scaleValue,valueBase);
		width = ms2px((obj_CPU.CPU2[i].end_ts-obj_CPU.CPU2[i].start_ts)/1000,scaleValue,0);
		width = width < 1 ? 1:width;		
		context.fillStyle = state_color_map[obj_CPU.CPU2[i].state];
		context.fillRect(index_X,64,width,10);
		if(width > c_state_table[obj_CPU.CPU2[i].state].length*7){
			context.fillStyle = "#000000";
			context.fillText(c_state_table[obj_CPU.CPU2[i].state],index_X,73);
		}
	}
	for(var i=0;i<obj_CPU.CPU3.length;i++){
		if(i == 0){
			inint_X = ms2px(obj_CPU.CPU3[0].start_ts/1000,scaleValue,valueBase);
			inint_X = inint_X <= 0 ? 0:inint_X;
			context.fillStyle = "#FF0000";
			context.fillRect(inint_X,84,1000,10);
		}
		index_X = ms2px(obj_CPU.CPU3[i].start_ts/1000,scaleValue,valueBase);
		width = ms2px((obj_CPU.CPU3[i].end_ts-obj_CPU.CPU3[i].start_ts)/1000,scaleValue,0);
		width = width < 1 ? 1:width;		
		context.fillStyle = state_color_map[obj_CPU.CPU3[i].state];
		context.fillRect(index_X,84,width,10);
		if(width > c_state_table[obj_CPU.CPU3[i].state].length*7){
			context.fillStyle = "#000000";
			context.fillText(c_state_table[obj_CPU.CPU3[i].state],index_X,93);
		}
	}
	for(var i=0;i<obj_CPU.GPU.length;i++){
		if(i == 0){
			inint_X = ms2px(obj_CPU.GPU[0].start_ts/1000,scaleValue,valueBase);
			inint_X = inint_X <= 0 ? 0:inint_X;
			context.fillStyle = "#FF0000";
			context.fillRect(inint_X,104,1000,10);
		}
		index_X = ms2px(obj_CPU.GPU[i].start_ts/1000,scaleValue,valueBase);
		width = ms2px((obj_CPU.GPU[i].end_ts-obj_CPU.GPU[i].start_ts)/1000,scaleValue,0);
		width = width < 1 ? 1:width;
		context.fillStyle = state_color_map[obj_CPU.GPU[i].state];
		context.fillRect(index_X,104,width,10);
		if(width > c_state_table[obj_CPU.GPU[i].state].length*7){
			context.fillStyle = "#000000";
			context.fillText(c_state_table[obj_CPU.GPU[i].state],index_X,113);
		}
	}
	context.stroke();
}