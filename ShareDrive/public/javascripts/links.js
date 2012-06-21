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