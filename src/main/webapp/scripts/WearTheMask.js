var strUserAgent = navigator.userAgent.toLowerCase(); 
var isIE = strUserAgent.indexOf("msie") > -1; 
var isNS6 = strUserAgent.indexOf("netscape6") > -1; 
var isNS4 = !isIE && !isNS6  && parseFloat(navigator.appVersion) < 5; 

//regular expressions
var reValidChars = /\d/;
var reValidString = /^\d*$/;
var reKeyboardChars = /[\x00\x03\x08\x0D\x16\x18\x1A]/;
var reClipboardChars = /[cvxz]/i;

//mask functions
function maskKeyPress(objEvent) {
	var iKeyCode, strKey, objInput;  
	
	if (isIE) {
	    iKeyCode = objEvent.keyCode;
		objInput = objEvent.srcElement;
	} else {
	    iKeyCode = objEvent.which;
		objInput = objEvent.target;
	}
	
	strKey = String.fromCharCode(iKeyCode);
	
	if (isValid(objInput.value)) {
		objInput.validValue = objInput.value;
		if (!reValidChars.test(strKey) && !reKeyboardChars.test(strKey) && !checkClipboardCode(objEvent, strKey)) {
			//alert("Invalid Character Detected!\nKeyCode = " + iKeyCode + "\nCharacter =" + strKey);
			return false;
		}
	} else {
		//alert("Invalid Data");
		objInput.value = objInput.validValue;
		return false;
	}
}

function checkClipboardCode(objEvent, strKey) {
  	if (isNS6)
    	return objEvent.ctrlKey && reClipboardChars.test(strKey);
  	else
    	return false;
}

function isValid(strValue) {
	return reValidString.test(strValue) || strValue.length == 0;			
}

function maskChange(objEvent) {
  	var objInput;

  	if (isIE) {
    	objInput = objEvent.srcElement; 
  	} else {
    	objInput = objEvent.target;
  	}
 
  	if (!isValid(objInput.value)) {
    	//alert("Invalid data");
		objInput.value = objInput.validValue || "";
		objInput.focus();
    	objInput.select(); 
  	} else {
		objInput.validValue = objInput.value;
	}
}

function maskPaste(objEvent) {
	var strPasteData = window.clipboardData.getData("Text");
	var objInput = objEvent.srcElement;
 		if (!isValid(strPasteData)) {
   		//alert("Invalid data");
   		objInput.focus();
		return false;
 		}
}	