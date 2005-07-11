<#assign karmaEnabled = karmaEnabled?default(false)/>

function confirmDelete()
{
	if (confirm("${I18n.getMessage("Moderation.ConfirmPostDelete")}")) {
		return true;
	}
	
	return false;
}

<#if karmaEnabled>
	function karmaVote(s, postId) {
		if (s.selectedIndex == 0) {
			return;
		}

		if (confirm("${I18n.getMessage("Karma.confirmVote")}")) {
			document.location = "${contextPath}/karma/insert/${start}/" + postId + "/" + s.value + "${extension}";
		}
		else {
			s.selectedIndex = 0;
		}
	}

	function karmaPointsCombo(postId)
	{
		document.write('<select name="karma" onChange="karmaVote(this,' + postId + ')">');
		document.write('	<option value="">${I18n.getMessage("Karma.rateMessage")}</option>');
		document.write('	<option value="1">1</option>');
		document.write('	<option value="2">2</option>');
		document.write('	<option value="3">3</option>');
		document.write('	<option value="4">4</option>');
		document.write('	<option value="5">5</option>');
		document.write('</select>');
	}
</#if>

function handleBbCode(evt)
{
	var e = evt || window.event;
	var thisKey = e.which || e.keyCode;

	var ch = String.fromCharCode(thisKey).toLowerCase();
	
	if (e.altKey && ch == "b") {
		bbstyle(0);
	}
	else if (e.altKey && ch == "i") {
		bbstyle(2);
	}
	else if (e.altKey && ch == "u") {
		bbstyle(4);
	}
	else if (e.altKey && ch == "q") {
		bbstyle(6);
	}
	else if (e.altKey && ch == "c") {
		bbstyle(8);
	}
	else if (e.altKey && ch == "l") {
		bbstyle(10);
	}
	else if (e.altKey && ch == "p") {
		bbstyle(12);
	}
	else if (e.altKey && ch == "w") {
		bbstyle(14);
	}
}

function enterText(field)
{
	storeCaret(field);
	document.onkeydown = handleBbCode;
}

function leaveText()
{
	document.onkeydown = null;
}