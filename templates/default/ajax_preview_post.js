$("#previewSubject").html("${post.subject}");
$("#previewMessage").html("${post.text}");
$("#previewTable").show();

var s = document.location.toString();
var index = s.indexOf("#preview");

if (index > -1) {
	s = s.substring(0, index);
}

document.location = s + "#preview";
