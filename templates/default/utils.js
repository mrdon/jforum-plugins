function showEmail(beforeAt, afterAt)
{
	return beforeAt + "@" + afterAt;
}

var starOn = new Image();
starOn.src = "${contextPath}/templates/${templateName}/images/star_on.gif";

var starOff = new Image();
starOff.src = "${contextPath}/templates/${templateName}/images/star_off.gif";

function writeStars(q, postId)
{
	for (var i = 0; i < 5; i++) {
		var name = "star" + postId + "_" + i;
		document.write("<img name='" + name + "'>");
		document.images[name].src = q > i ? starOn.src : starOff.src;
	}
}