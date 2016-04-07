function drawPic(dataObj,scaleValue,left){
	var picIndexs = "";
	var ImgList = dataObj.ImgList;
	var SrcPath = drawImageData.SrcPath;
	for(var i=0;i<8;i++){
		picIndexs += getImageIndex(scaleValue,left,2*i+1)/10 + ",";
	}
	var canvas=document.getElementById('diagonal_x');
	var ctx=canvas.getContext('2d');
	ctx.clearRect(0,0,1000,165);
	drawX(scaleValue,left);
	
	ctx.beginPath();
	var imageObj=new Array();		
	var imgIndex = new Array();
	var index_X;
console.log(ImgList);
	//第一幅
	imageObj[0]=new Image();
	imgIndex[0] = getImageIndex(scaleValue,left,1);
	imageObj[0].src=(imgIndex[0] > ImgList.length-1)? "":getQueryString("casename") + "/"+SrcPath+"/"+ImgList[imgIndex[0]];

	imageObj[0].src = imageObj[0].src.replace("UXToolWeb","");
	index_X = ms2px(imgIndex[0],scaleValue,left);
	ctx.strokeStyle = "#ff0000";
	ctx.moveTo(index_X,130);ctx.lineTo(index_X,150);
	ctx.moveTo(index_X-2,146);ctx.lineTo(index_X,150);
	ctx.moveTo(index_X+2,146);ctx.lineTo(index_X,150);
	imageObj[0].onload=function(){
		ctx.drawImage(imageObj[0],0, 0, 90, 130); 
	}	
	//第二幅
	imageObj[1]=new Image();
	imgIndex[1] = getImageIndex(scaleValue,left,3);
	imageObj[1].src=(imgIndex[1] > ImgList.length-1)? "":getQueryString("casename") + "/"+SrcPath+"/"+ImgList[imgIndex[1]];

	imageObj[1].src = imageObj[1].src.replace("UXToolWeb","");
	index_X = ms2px(imgIndex[1],scaleValue,left);
	ctx.moveTo(index_X,130);ctx.lineTo(index_X,150);
	ctx.moveTo(index_X-2,146);ctx.lineTo(index_X,150);
	ctx.moveTo(index_X+2,146);ctx.lineTo(index_X,150);
	imageObj[1].onload=function(){
		ctx.drawImage(imageObj[1],130, 0, 90, 130); 
	}	
	//第三幅
	imageObj[2]=new Image();
	imgIndex[2] = getImageIndex(scaleValue,left,5);
	imageObj[2].src=(imgIndex[2] > ImgList.length-1)? "":getQueryString("casename") + "/"+SrcPath+"/"+ImgList[imgIndex[2]];

	imageObj[2].src = imageObj[2].src.replace("UXToolWeb","");
	index_X = ms2px(imgIndex[2],scaleValue,left);
	ctx.moveTo(index_X,130);ctx.lineTo(index_X,150);
	ctx.moveTo(index_X-2,146);ctx.lineTo(index_X,150);
	ctx.moveTo(index_X+2,146);ctx.lineTo(index_X,150);
	imageObj[2].onload=function(){
		ctx.drawImage(imageObj[2],260, 0, 90, 130);
	}	
	//第四幅
	imageObj[3]=new Image();
	imgIndex[3] = getImageIndex(scaleValue,left,7);
	imageObj[3].src=(imgIndex[3] > ImgList.length-1)? "":getQueryString("casename") + "/"+SrcPath+"/"+ImgList[imgIndex[3]];

	imageObj[3].src = imageObj[3].src.replace("UXToolWeb","");
	index_X = ms2px(imgIndex[3],scaleValue,left);
	ctx.moveTo(index_X,130);ctx.lineTo(index_X,150);
	ctx.moveTo(index_X-2,146);ctx.lineTo(index_X,150);
	ctx.moveTo(index_X+2,146);ctx.lineTo(index_X,150);
	imageObj[3].onload=function(){
		ctx.drawImage(imageObj[3],390, 0, 90, 130); 
	}	
	//第五幅
	imageObj[4]=new Image();
	imgIndex[4] = getImageIndex(scaleValue,left,9);
	imageObj[4].src=(imgIndex[4] > ImgList.length-1)? "":getQueryString("casename") + "/"+SrcPath+"/"+ImgList[imgIndex[4]];

	imageObj[4].src = imageObj[4].src.replace("UXToolWeb","");
	index_X = ms2px(imgIndex[4],scaleValue,left);
	ctx.moveTo(index_X,130);ctx.lineTo(index_X,150);
	ctx.moveTo(index_X-2,146);ctx.lineTo(index_X,150);
	ctx.moveTo(index_X+2,146);ctx.lineTo(index_X,150);
	ctx.stroke();
	imageObj[4].onload=function(){
		ctx.drawImage(imageObj[4],520, 0, 90, 130); 
	}	
	//第六幅
	imageObj[5]=new Image();
	imgIndex[5] = getImageIndex(scaleValue,left,11);
	imageObj[5].src=(imgIndex[5] > ImgList.length-1)? "":getQueryString("casename") + "/"+SrcPath+"/"+ImgList[imgIndex[5]];

	imageObj[5].src = imageObj[5].src.replace("UXToolWeb","");
	index_X = ms2px(imgIndex[5],scaleValue,left);
	ctx.moveTo(index_X,130);ctx.lineTo(index_X,150);
	ctx.moveTo(index_X-2,146);ctx.lineTo(index_X,150);
	ctx.moveTo(index_X+2,146);ctx.lineTo(index_X,150);	
	imageObj[5].onload=function(){
		ctx.drawImage(imageObj[5],650, 0, 90, 130); 
	}	
	//第七幅
	imageObj[6]=new Image();
	imgIndex[6] = getImageIndex(scaleValue,left,13);
	imageObj[6].src=(imgIndex[6] > ImgList.length-1)? "":getQueryString("casename") + "/"+SrcPath+"/"+ImgList[imgIndex[6]];

	imageObj[6].src = imageObj[6].src.replace("UXToolWeb","");
	index_X = ms2px(imgIndex[6],scaleValue,left);
	ctx.moveTo(index_X,130);ctx.lineTo(index_X,150);
	ctx.moveTo(index_X-2,146);ctx.lineTo(index_X,150);
	ctx.moveTo(index_X+2,146);ctx.lineTo(index_X,150);
	ctx.stroke();
	imageObj[6].onload=function(){
		ctx.drawImage(imageObj[6],780, 0, 90, 130); 
	}	
	//第八幅
	imageObj[7]=new Image();
	imgIndex[7] = getImageIndex(scaleValue,left,15);
	imageObj[7].src=(imgIndex[7] > ImgList.length-1)? "":getQueryString("casename") + "/"+SrcPath+"/"+ImgList[imgIndex[7]];

	imageObj[7].src = imageObj[7].src.replace("UXToolWeb","");
	index_X = ms2px(imgIndex[7],scaleValue,left);
	ctx.moveTo(index_X,130);ctx.lineTo(index_X,150);
	ctx.moveTo(index_X-2,146);ctx.lineTo(index_X,150);
	ctx.moveTo(index_X+2,146);ctx.lineTo(index_X,150);
	imageObj[7].onload=function(){
		ctx.drawImage(imageObj[7],910, 0, 90, 130); 
	}
	ctx.stroke();
	canvas.onclick = function(e) {
		for(var i=0;i<imageObj.length;i++){
			if(e.offsetY>0 && e.offsetY<130){
				if(e.offsetX>130*i && e.offsetX<(130*(i+1)-35)){
					console.log(imageObj[i]);
					$("#img_BigPic").attr("src",imageObj[i].src);
					$("#dialog_BigPic").dialog({
						top:50,
						width:$("#img_BigPic").width+20,
						close:function(event, ui) { }
					});
				}
			}
		}
	}
}

//------画坐标轴------
function drawX(scaleValue,left){
	var canvas=document.getElementById('diagonal_x');
	var context=canvas.getContext('2d');
	//清空canvas
	context.clearRect(0,0,1000,165);
	context.beginPath();
	//画横坐标
	context.strokeStyle = "#000000";
	context.moveTo(0,150);
	context.lineTo(1000,150);
	
	for (var i = 0; i <= 100; i++){
		//每10格刻度线就画长一点
		if(i%10==0){
			context.moveTo(10*i,150);
			context.lineTo(10*i,140);
			//标刻度值
			context.fillText(((50*i+5*left)/scaleValue)+"(ms)",10*i,165);
		}else{
			//画普通的刻度线
			context.moveTo(10*i,150);
			context.lineTo(10*i,145);
		}
	}
	context.stroke();
}