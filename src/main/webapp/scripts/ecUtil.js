function EcUtil() { }
/**
 * 
 * @see http://getahead.ltd.uk/dwr/browser/util/useloadingmessage
 */
EcUtil.popCenter = function(url,w,h){
    x=w;
    y=h;
    l=(screen.width/2)-(x/2);
    t=(screen.height/2)-(y/2);
    s="toolbar=no,location=no,status=yes,menubar=no,resizable=yes,scrollbars=yes";
    s+=" width=" + x + ", height=" + y + ", left=" + l + ", top=" + t;
    MRV=window.open(url,"",s);
}

//
EcUtil.subValidForm=function(dForm,ftype){
	if(Validator.Validate(dForm,ftype)){
		dForm.submit();
	}
}

EcUtil.checkKeyPress=function(submitId){
	if(event.keyCode==13){
		document.getElementById(submitId).click();		
	}
}

//ȫѡ
EcUtil.checkAll=function(dForm){
	var felements=dForm.elements;
	for(i=0;i<felements.length;i++){
		if(felements[i].type=='checkbox'||felements[i].type=='radio'){
			felements[i].checked=true;
		}
	}
}
//��ѡ
EcUtil.checkReverse=function(dForm){
	var felements=dForm.elements;
	for(i=0;i<felements.length;i++){
		if(felements[i].type=='checkbox'||felements[i].type=='radio'){
			if(felements[i].checked==true){
				felements[i].checked=false;
			}else{
				felements[i].checked=true;
			}
		}
	}	
}
//��ѡ
EcUtil.setSelectCheckBox=function(sName,svalue){
	var objArr=svalue.split(',');
	var objboxArr=document.getElementsByName(sName);
	for(i=0;i<objboxArr.length;i++){
		for(j=0;j<objArr.length;j++){
			if(objboxArr[i].value==objArr[j]){
				objboxArr[i].checked=true;
				break;
			}	
		}
	}
}
EcUtil.maskKeyPress=function(objEvent){
	var reValidChars = /\d/;
	var iKeyCode, strKey;
	iKeyCode = objEvent.keyCode;
	strKey = String.fromCharCode(iKeyCode);
	if (!reValidChars.test(strKey)) {
		return false;
	}	
}
