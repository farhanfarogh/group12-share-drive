function show(elem){		
	document.getElementById('Sub' + elem).style.visibility = 'visible';
	document.getElementById(elem).style.borderBottomWidth = '0';
}

function hide(elem){
	document.getElementById('Sub' + elem).style.visibility = 'hidden';
	document.getElementById(elem).style.borderBottomWidth = '4px';
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