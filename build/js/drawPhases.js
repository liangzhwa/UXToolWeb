var phasePanel_currentShowPic = 0;
var phasePanel_picList_video = [];
function drawPhasesRect(dataObj,scaleValue,left){
	if(dataObj == null){ return; }
	var obj_Phases = dataObj.Result.phaseList;
	var canvas=document.getElementById('panel_phases');
	var context=canvas.getContext('2d');
	context.clearRect(0,0,1000,30);
	context.beginPath();
	
	context.font = '11px "Segoe UI"';
	context.fillStyle = "#000000";
	context.fillText("Phases",2,13);
	
	var index_X;
	var width;
	for(var i=0;i<obj_Phases.length;i++){
		obj_Phases[i].sort(function(a,b){return a>b?1:-1});
		index_X = ms2px(obj_Phases[i][0],scaleValue,left);
		width = ms2px(obj_Phases[i][obj_Phases[i].length-1]-obj_Phases[i][0],scaleValue,0);
		width = width < 1 ? 1:width;
		context.fillRect(index_X,5,width,10);
		phasePanel_picList_video [i] = obj_Phases[i];
	}
	context.stroke();
	
	//点击事件
	canvas.onclick = function(e) {		
		if(e.offsetY > 5 && e.offsetY < 15){
			for(var k=0;k<obj_Phases.length;k++){
				if(e.offsetX > ms2px(obj_Phases[k][0],scaleValue,left) && e.offsetX < ms2px(obj_Phases[k][obj_Phases[k].length-1],scaleValue,left)){
					$("#dialog_Video").dialog({
						top:10,
						width:410,
						height:750,
						close:function(event, ui) {
							$("#imgDiv").html("");
						}
					});
					phasePanel_picList_video = [];
					for(var j=0;j<obj_Phases[k].length;j++){
						phasePanel_picList_video[j] = getQueryString("casename") + "/output/dst/"+ (obj_Phases[k][j] + ".jpg").charLeftAll('0',9);
					}
					phasePanel_currentShowPic = phasePanel_picList_video.length-1;
					PlayVideo()
				}
			}
		}
	}
}
function showNext(){
	if(phasePanel_currentShowPic == (phasePanel_picList_video.length-1)){
		phasePanel_currentShowPic--;
		clearInterval(timer);
	}
	phasePanel_currentShowPic++;
	var root = location.href.substr(0,location.href.indexOf("UXToolWeb"));
	$("#imgDiv").html('<img src="'+root+phasePanel_picList_video[phasePanel_currentShowPic]+'" onclick="showNext()"/>');
}
function PlayVideo(){
	if(phasePanel_currentShowPic != (phasePanel_picList_video.length-1)){ return; }
	phasePanel_currentShowPic = 0;
	var root = location.href.substr(0,location.href.indexOf("UXToolWeb"));
	$("#imgDiv").html('<img src="'+root+phasePanel_picList_video[phasePanel_currentShowPic]+'" onclick="showNext()"/>');
	timer = setInterval(function(){
		$("#imgDiv img").trigger("click");
	},200);
}