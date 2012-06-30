var actElem='Start';
var i = 0;

function show(elem){
i++;
	if(actElem!='Start' || i>0){
		hide();
		
	}
	actElem=elem;			
	document.getElementById('Sub' + elem).style.visibility = 'visible';
	document.getElementById(elem).style.borderBottomWidth = '0';
}

function hide(){
	document.getElementById('Sub' + actElem).style.visibility = 'hidden';
	document.getElementById(actElem).style.borderBottomWidth = '4';
}

function hideSubmenu(){
	hide('Start');
	hide('Rides');
	hide('Profile');
	hide('Help');
}