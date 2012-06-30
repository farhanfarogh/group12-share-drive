function showSubmenu(elem){
	hideAll();
	document.getElementById('Sub' + elem).style.visibility = 'visible';
	document.getElementById(elem).style.borderBottomWidth = '0px';
}

function unklick(elem){
	document.getElementById(elem).style.borderBottomWidth = '4px';
}

function hideAll(){
	document.getElementById('SubRides').style.visibility = 'hidden';
	document.getElementById('SubProfile').style.visibility = 'hidden';
	document.getElementById('SubHelp').style.visibility = 'hidden';
}

function chkPassword(passwordObj, errorDispObj) { //checking password length
	if(passwordObj.value.length < 5 ) { //show error
	   errorDispObj.innerHTML = "";
	   errorDispObj.innerHTML = "Minimum password size is 5";
	}
	else { //show nothing
	   errorDispObj.innerHTML = "";
	   errorDispObj.innerHTML = "Correct password";
	}
}

function varifyPassword(verifyPasswordObj, passwordObj, errorDispObj) { //verify the retype password
	
	if(verifyPasswordObj.value == passwordObj.value) { // show nothing as the retype pass match with the pass
	   errorDispObj.innerHTML = "";
	   errorDispObj.innerHTML = "Password matched";
	}
	else { //show error as retype pass does not match
	   errorDispObj.innerHTML = "";
	   errorDispObj.innerHTML = "Your password doesn't match";
	}
}

function validateFieldRegEmail() {
	var txtField = document.getElementById("regEmailField");
	var MsgField = document.getElementById("invalidEmailMsgField");
	var val = txtField.value.toLowerCase();
	var index = val.lastIndexOf("@");
	if(index > 0) {
		var domain = val.substring(index);
		if(domain == "@tum.de" || domain == "@in.tum.de" || domain == "@mytum.de"){
			MsgField.innerHTML = "";
			return;
		}
	}
	MsgField.innerHTML = "Please use valid email address provided by your institue.";
}
