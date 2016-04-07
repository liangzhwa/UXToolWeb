<%@ page language="java" import="java.io.InputStream,java.io.FileInputStream,java.util.Properties" pageEncoding="utf-8"%>
<%
String path = request.getParameter("casename");
HttpSession s = request.getSession(); 
s.setAttribute("CaseName", path);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>UXFrame Tool</title>    
<meta http-equiv="pragma" content="no-cache">
<!-- meta http-equiv="cache-control" content="no-cache" -->
<link rel="stylesheet" href="JQuery/jquery-ui.css"/>
<script src="JQuery/jquery-1.6.2.min.js"></script>
<script src="JQuery/jquery-ui.js"></script>
<script src="JQuery/jquery.scrollTo-min.js"></script>
<script src="JQuery/scroll-startstop.events.jquery.js"></script>
<script src="js/common.js"></script>
<script src="js/drawPicture.js"></script>
<script src="js/drawInput.js"></script>
<script src="js/drawMethods.js"></script>
<script src="js/drawPhases.js"></script>
<script src="js/drawFrame.js"></script>
<script src="js/drawIothroughput.js"></script>
<script src="js/drawCPU.js"></script>
<script src="js/drawPower.js"></script>
<script type="text/javascript">
	var imgs;
	var context;
	var timer = null;
	var scaleValue=1;
	var drawObjData = null;
	var drawImageData = null;
	var preScrollTimestamp=0;
	var isTriggerScrollEnd=false;
	//        座标和时间换算：           5/scaleValue  [ms/px]
	//鼠标控制放大缩小
	document.onmousewheel = function(event){
		if(event.srcElement.nodeName != "CANVAS") { return; }
		if(event.wheelDeltaY>0){
			if(scaleValue<=16){
				var left = $('#div1')[0].scrollLeft;
				var tempTmValue = (event.offsetX+left)*5/scaleValue
				scaleValue=scaleValue*2;
				$('#div2').width($('#div2').width()*2);
				isTriggerScrollEnd = false;
				var scrollPxValue = (tempTmValue*scaleValue/5) > event.offsetX ? tempTmValue*scaleValue/5-event.offsetX:0;
				$('#div1').scrollTo(scrollPxValue,{axis:'x'});
			}
		}
		else if(event.wheelDeltaY<0){
			if(scaleValue>1){
				var left = $('#div1')[0].scrollLeft;
				var tempTmValue = (event.offsetX+left)*5/scaleValue
				scaleValue=scaleValue/2;
				$('#div2').width($('#div2').width()*0.5);
				isTriggerScrollEnd = false;
				var scrollPxValue = (tempTmValue*scaleValue/5) > event.offsetX ? tempTmValue*scaleValue/5-event.offsetX:0;
				$('#div1').scrollTo(scrollPxValue,{axis:'x'});				
			}
		}
		return false;
	}
	
	//----------------------------此处为上下箭头控制放大缩小-----------------------------------------
	document.onkeydown = function (event) { 
		var event = event || window.event; 
		bCtrlKey = event.ctrlKey; 
		switch (event.keyCode) {
			case 38: //缩小
			if(bCtrlKey){
				if(scaleValue<=16){
					scaleValue=scaleValue*2;
					$('#div2').width($('#div2').width()*2);
					var left = $('#div1')[0].scrollLeft;
					var divWidth=$('#div2').width();
					var valueBase=10000*left/divWidth;
					drawX(scaleValue,valueBase);
					drawInputLine(drawObjData,scaleValue,valueBase);
					drawMethodRect(drawObjData,scaleValue,valueBase);
					drawPhasesRect(drawObjData,scaleValue,valueBase);
					drawFrameLine(drawObjData,scaleValue,valueBase);
					drawIothroughputLine(drawObjData,scaleValue,valueBase);
					drawCPULine(drawObjData,scaleValue,valueBase);
					drawPowerLine(drawObjData,scaleValue,valueBase);
					drawPic(drawImageData,scaleValue,valueBase);
				}
				break;	
			}
			case 40: //放大
			if(bCtrlKey){				
				if(scaleValue>1){
					scaleValue=scaleValue/2;
					$('#div2').width($('#div2').width()*0.5);
					var left = $('#div1')[0].scrollLeft;
					var divWidth=$('#div2').width();
					var valueBase=10000*left/divWidth;
					drawX(scaleValue,valueBase);
					drawInputLine(drawObjData,scaleValue,valueBase);
					drawMethodRect(drawObjData,scaleValue,valueBase);
					drawPhasesRect(drawObjData,scaleValue,valueBase);
					drawFrameLine(drawObjData,scaleValue,valueBase);
					drawIothroughputLine(drawObjData,scaleValue,valueBase);
					drawCPULine(drawObjData,scaleValue,valueBase);
					drawPowerLine(drawObjData,scaleValue,valueBase);
					drawPic(drawImageData,scaleValue,valueBase);
				}
				break; 
			}
			break; 
		} 
		return false 
	};

	$(document).ready(function (){
    	$.ajax({
			 type: "POST",
			 url: "SelectPicUrl.action",
			 success: function(data){
				var dataObj=eval("("+data+")");
				drawImageData = dataObj;
				drawPic(drawImageData,scaleValue,0);
		 	}
		});
		$.ajax({
			type: "POST",
			url: "GetTimeStamp.action",
			data: "index="+Math.random(),
			success: function(data){
				var dataObj=eval("("+data+")");
				drawObjData = dataObj;
				drawInputLine(drawObjData,scaleValue,0);
				drawMethodRect(drawObjData,scaleValue,0);
				drawPhasesRect(drawObjData,scaleValue,0);
				drawFrameLine(drawObjData,scaleValue,0);
				drawIothroughputLine(drawObjData,scaleValue,0);
				drawCPULine(drawObjData,scaleValue,0);
				drawPowerLine(drawObjData,scaleValue,0);
				
				//show result
				if(drawObjData.Result.ReactionTime != undefined){
					var result = "<font style='color:red;'>Result:</font>  Reaction Time: " + (isNullOrEmpty(drawObjData.Result.ReactionTime) ? "" : drawObjData.Result.ReactionTime) +
								"	ms;	Response Time: " + (isNullOrEmpty(drawObjData.Result.ResponseTime) ? "" : drawObjData.Result.ResponseTime) +
								"	ms;	Power consumption: " + (isNullOrEmpty(drawObjData.Result.Power) ?  "" : drawObjData.Result.Power) +
								";	DiskIO: " + (isNullOrEmpty(drawObjData.Result.DiskIO) ? "" : drawObjData.Result.DiskIO) +
								";	NetIO: " + (isNullOrEmpty(drawObjData.Result.NetIO) ? "" : drawObjData.Result.NetIO) +
								";	Average FPS: " + (isNullOrEmpty(drawObjData.Result.AverageFPS) ? "" : drawObjData.Result.AverageFPS) +
								";	Phase Num: " + (isNullOrEmpty(drawObjData.Result.PhaseNum) ? "" : drawObjData.Result.PhaseNum);
					$("#panel_Result").html(result);
				}
			}
		});
		
		

		$("#div1").click(function(e){
		    console.log("Clicked content."); //Fired when clicking scroller as well
		});
		$("#div1").bind("scrollstop", function() {
			//return;
			if(!isTriggerScrollEnd){ return; }
			drawInputLine(drawObjData,scaleValue,$(this)[0].scrollLeft);
			drawMethodRect(drawObjData,scaleValue,$(this)[0].scrollLeft);
			drawPhasesRect(drawObjData,scaleValue,$(this)[0].scrollLeft);
			drawFrameLine(drawObjData,scaleValue,$(this)[0].scrollLeft);
			drawIothroughputLine(drawObjData,scaleValue,$(this)[0].scrollLeft);
			drawCPULine(drawObjData,scaleValue,$(this)[0].scrollLeft);
			drawPowerLine(drawObjData,scaleValue,$(this)[0].scrollLeft);
			drawPic(drawImageData,scaleValue,$(this)[0].scrollLeft);
		});
		
        $("#div1").scroll(function(e){        	
        	if(e.timeStamp - preScrollTimestamp < 300){ isTriggerScrollEnd = true; return; }        	
        	preScrollTimestamp = e.timeStamp;
			drawInputLine(drawObjData,scaleValue,$(this)[0].scrollLeft);
			drawMethodRect(drawObjData,scaleValue,$(this)[0].scrollLeft);
			drawPhasesRect(drawObjData,scaleValue,$(this)[0].scrollLeft);
			drawFrameLine(drawObjData,scaleValue,$(this)[0].scrollLeft);
			drawIothroughputLine(drawObjData,scaleValue,$(this)[0].scrollLeft);
			drawCPULine(drawObjData,scaleValue,$(this)[0].scrollLeft);
			drawPowerLine(drawObjData,scaleValue,$(this)[0].scrollLeft);
			drawPic(drawImageData,scaleValue,$(this)[0].scrollLeft);
		});
	});	
	$(window).bind('beforeunload', function (e) {
		$.ajax({
			 type: "POST",
			 url: "DeleteTempPictrues.action",
			 success: function(data){
				
		 	 }
		});
        //return "are you true?"
	});
</script>
</head>
<body oncontextmenu="return mouseContext(event);">
	<div style="width:1000px;margin:0 auto;">
		<canvas id="diagonal_x"  width="1000px" height="165px"></canvas>
		<div id="div1" style="height:20px;width: 1000px;overflow-x:scroll;overflow-y:hidden;">
			<div id="div2" style="height:5px;width:1000px;"></div>
		</div>
		<div id="panel_Switch" width="1000px" height="25" style="background-color:#E6EFFA;margin-top:5px;font-size:8pt">
			<input id="switch_IO" type="checkbox" onclick="SwitchIO(this);" /><label for="switch_IO" style="margin:3px;position:absolute;">IO</label>    
			<input id="switch_CPU" type="checkbox" checked onclick="SwitchCPU(this);" style="margin-left:30px" /><label for="switch_CPU" style="margin:3px;position:absolute;">CPU</label>
		</div>	
		<canvas id="panel_input"  width="1000px" height="35" style="background-color:#E6EFFA;margin-top:5px;"></canvas>
		<canvas id="panel_power"  width="1000px" height="70" style="background-color:#E6EFFA;margin-top:5px;"></canvas>
		<canvas id="panel_method"  width="1000px" height="200" style="background-color:#E6EFFA;margin-top:5px;"></canvas>
		<canvas id="panel_phases"  width="1000px" height="30" style="background-color:#E6EFFA;margin-top:5px;"></canvas>
		<canvas id="panel_frame"  width="1000px" height="50" style="background-color:#E6EFFA;margin-top:5px;"></canvas>
		<canvas id="panel_io"  width="1000px" height="100" style="background-color:#E6EFFA;margin-top:5px;display:none"></canvas>
		<canvas id="panel_cpu"  width="1000px" height="120" style="background-color:#E6EFFA;margin-top:5px;"></canvas>
		<div id="panel_Result" width="1000px" height="25" style="background-color:#E6EFFA;margin-top:5px;font-size:8pt"></div>			
	</div>
	<div id="dialog" title="Message" style="display:none">
		<label id="lblTime"></label><br/>
		<label id="lblFPS"></label><br/>
		<label id="lblCPU"></label>
	</div>
	<div id="dialog_Video" title="Video" style="display:none">
		<div id="imgDiv"></div>
		<div style="margin-top:5"><button id="btnReplayVideo" style="float:right;" onclick="PlayVideo();">Replay</button></div>
	</div>
	<div id="dialog_BigPic" title="" style="display:none">
	    <img id="img_BigPic" />
	</div>
</body>
</html>
