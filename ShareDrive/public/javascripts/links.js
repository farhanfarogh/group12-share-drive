var actElem='Start';

function show(elem){
/*	if(elem == 'Rides') {
		//window.location.href = 'OfferRide.html';
	}
	else if(elem == 'Project') {
		
	}
	else if(elem == 'Help') {
		
	}*/
	if(actElem!='Start'){
		hide();
	}
	actElem=elem;			
	document.getElementById('Sub' + elem).style.visibility = 'visible';
	document.getElementById(elem).style.borderBottomWidth = '0';
}

function hide() {
	document.getElementById('Sub' + actElem).style.visibility = 'hidden';
	document.getElementById(actElem).style.borderBottomWidth = '4';
}

function hideSubmenu(){
	hide('Start');
	hide('Rides');
	hide('Profile');
	hide('Help');
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