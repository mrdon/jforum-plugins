function verifyModerationCheckedTopics()
{
	var f = document.form1.topic_id;
	
	if (f.length == undefined)	 {
		if (f.checked) {
			return true;
		}
	}

	for (var i = 0; i < f.length; i++) {
		if (f[i].checked) {
			return true;
		}
	}
	
	alert("${I18n.getMessage("Moderation.SelectTopics")}");
	return false;
}

function validateModerationDelete()
{
	if (verifyModerationCheckedTopics() && confirm("${I18n.getMessage("Moderation.ConfirmDelete")}")) {
		return true;
	}
	
	return false;
}