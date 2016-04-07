/**
 * COMMON DHTML FUNCTIONS
 * These are handy functions I use all the time.
 *
 * By Seth Banks (webmaster at subimage dot com)
 * http://www.subimage.com/
 *
 * Up to date code can be found at http://www.subimage.com/dhtml/
 *
 * This code is free for you to use anywhere, just keep this comment block.
 */

/**
 * X-browser event handler attachment and detachment
 * TH: Switched first true to false per http://www.onlinetools.org/articles/unobtrusivejavascript/chapter4.html
 *
 * @argument obj - the object to attach event to
 * @argument evType - name of the event - DONT ADD "on", pass only "mouseover", etc
 * @argument fn - function to call
 */


String.prototype.charLeftAll = function (bchar,alength){
    var xchar = ''+this;
    for(var i=0;i<alength;i++){
    	if(xchar.length>=alength) break; 
    	xchar = bchar+xchar;
    }
	return(xchar);
} 

function addEvent(obj, evType, fn){
 if (obj.addEventListener){
    obj.addEventListener(evType, fn, false);
    return true;
 } else if (obj.attachEvent){
    var r = obj.attachEvent("on"+evType, fn);
    return r;
 } else {
    return false;
 }
}
function removeEvent(obj, evType, fn, useCapture){
  if (obj.removeEventListener){
    obj.removeEventListener(evType, fn, useCapture);
    return true;
  } else if (obj.detachEvent){
    var r = obj.detachEvent("on"+evType, fn);
    return r;
  } else {
    alert("Handler could not be removed");
  }
}

/**
 * Code below taken from - http://www.evolt.org/article/document_body_doctype_switching_and_more/17/30655/
 *
 * Modified 4/22/04 to work with Opera/Moz (by webmaster at subimage dot com)
 *
 * Gets the full width/height because it's different for most browsers.
 */
function getViewportHeight() {
	if (window.innerHeight!=window.undefined) return window.innerHeight;
	if (document.compatMode=='CSS1Compat') return document.documentElement.clientHeight;
	if (document.body) return document.body.clientHeight; 
	return window.undefined; 
}
function getViewportWidth() {
	if (window.innerWidth!=window.undefined) return window.innerWidth; 
	if (document.compatMode=='CSS1Compat') return document.documentElement.clientWidth; 
	if (document.body) return document.body.clientWidth; 
	return window.undefined; 
}

function isNullOrEmpty(str){
	if(str == undefined || str == "undefined" || str == null || str == "null"){
		return true;
	}else{
		return false;
	}
}

function mouseContext(e){
	if(e.srcElement.nodeName == "CANVAS"){
		return false;
	}
}

function SwitchIO(obj){
	if($(obj).attr("checked") == "checked"){
		$("#panel_io").show();
	}else{
		$("#panel_io").hide();
	}
}
function SwitchCPU(obj){
	if($(obj).attr("checked") == "checked"){
		$("#panel_cpu").show();
	}else{
		$("#panel_cpu").hide();
	}
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null){
    	return unescape(r[2]);
	}	
	return null;
}

//画图函数
function ms2px(ms,scaleValue,left){
	return ms*scaleValue/5-left;
}
function px2ms(px,scaleValue,left){
	return (px+left)*5/scaleValue;
}
function getImageIndex(scaleValue,left,index){
	return Math.round((5*left+5000/16*index)/scaleValue);
}