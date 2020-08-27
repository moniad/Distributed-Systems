function CreateCookie(name, value, days) {
    var date = new Date();
    date.setTime(date.getTime() + (days*24*60*60*1000));
    var expires = "; expires=" + date.toGMTString();
	document.cookie = name+"="+value+expires+"; path=/";
}
function ReadCookie(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for(var i=0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') c = c.substring(1, c.length);
		if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
	}
	return null;
}

window.onload = CheckCookies;

function CheckCookies() {
    if(ReadCookie('cookies_accepted') != 'WIRSERW2x') {
        var message_container = document.createElement('div');
        message_container.id = 'cookies-message-container';
        var html_code = '<div id="cookies-message" style="padding: 10px 0px; font-size: 18px; color: #424242; line-height: 22px; border-bottom: 0px solid rgb(211, 208, 208); text-align: center; position: fixed; bottom: 0px; background-color: #ff564e; width: 100%; z-index: 999;"><strong>UWAGA:</strong> W dniach 9-13.04 2020 w związku z prowadzonymi pracami serwisowymi, mogą występować przerwy w działaniu Uczelnianej Platformy e-Learningowej. Dziękujemy za wyrozumiałość.</br></br><small>Nasze serwisy wykorzystują ciasteczka (cookies). Korzystając z nich wyrażasz zgodę na używanie cookies zgodnie z aktualnymi ustawieniami Twojej przeglądarki stron WWW.</small></br></br><a href="javascript:CloseCookiesWindow();" id="accept-cookies-checkbox" name="accept-cookies" style="background-color: #393939; padding: 5px 10px; color: #FFF; border-radius: 4px; -moz-border-radius: 4px; -webkit-border-radius: 4px; display: inline-block; margin-left: 10px; text-decoration: none; cursor: pointer;">Rozumiem</a></div>';
        message_container.innerHTML = html_code;
        document.body.appendChild(message_container);
    }
}

function CloseCookiesWindow() {
    CreateCookie('cookies_accepted', 'WIRSERW2x', 365);
    document.getElementById('cookies-message-container').removeChild(document.getElementById('cookies-message'));
}