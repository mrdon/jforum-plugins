// bbCode control by
// subBlue design
// www.subBlue.com

// Startup variables
var imageTag = false;
var theSelection = false;

// Check for Browser & Platform for PC & IE specific bits
// More details from: http://www.mozilla.org/docs/web-developer/sniffer/browser_type${extension}
var clientPC = navigator.userAgent.toLowerCase(); // Get client info
var clientVer = parseInt(navigator.appVersion); // Get browser version

var is_ie = ((clientPC.indexOf("msie") != -1) && (clientPC.indexOf("opera") == -1));
var is_nav  = ((clientPC.indexOf('mozilla')!=-1) && (clientPC.indexOf('spoofer')==-1)
                && (clientPC.indexOf('compatible') == -1) && (clientPC.indexOf('opera')==-1)
                && (clientPC.indexOf('webtv')==-1) && (clientPC.indexOf('hotjava')==-1));

var is_win   = ((clientPC.indexOf("win")!=-1) || (clientPC.indexOf("16bit") != -1));
var is_mac    = (clientPC.indexOf("mac")!=-1);


// Helpline messages
b_help = "${I18n.getMessage("PostForm.helplineBoldText")}: [b]${I18n.getMessage("PostForm.helplineText")}[/b]  (alt+b)";
i_help = "${I18n.getMessage("PostForm.helplineItalicText")}: [i]${I18n.getMessage("PostForm.helplineText")}[/i]  (alt+i)";
u_help = "${I18n.getMessage("PostForm.helplineUnderlineText")}: [u]${I18n.getMessage("PostForm.helplineText")}[/u]  (alt+u)";
q_help = "${I18n.getMessage("PostForm.helplineQuote")}: [quote]${I18n.getMessage("PostForm.helplineText")}[/quote]  (alt+q)";
c_help = "${I18n.getMessage("PostForm.helplineCode")}: [code]${I18n.getMessage("PostForm.helplineCode")}[/code]  (alt+c)";
l_help = "${I18n.getMessage("PostForm.helplineList")}: [list]${I18n.getMessage("PostForm.helplineText")}[/list] (alt+l)";
p_help = "${I18n.getMessage("PostForm.helplineInsertImage")}: [img]http://wwww.xxxx.com/img.ext[/img]  (alt+p)";
w_help = "${I18n.getMessage("PostForm.helplineInsertUrl")}: [url]http://url[/url] / [url=http://url]${I18n.getMessage("PostForm.helplineUrlDescription")}[/url]  (alt+w)";
a_help = "${I18n.getMessage("PostForm.helplineCloseAllMarks")}";
s_help = "${I18n.getMessage("PostForm.helplineColor")}: [color=red]${I18n.getMessage("PostForm.helplineText")}[/color]  ${I18n.getMessage("PostForm.helplineColorTip")} color=#FF0000";
f_help = "${I18n.getMessage("PostForm.helplineFont")}: [size=x-small]${I18n.getMessage("PostForm.helplineSmallText")}[/size]";

// Define the bbCode tags
bbcode = new Array();
bbtags = new Array('[b]','[/b]','[i]','[/i]','[u]','[/u]','[quote]','[/quote]','[code]','[/code]','[list]','[/list]','[img]','[/img]','[url]','[/url]');
imageTag = false;

// Shows the help messages in the helpline window
function helpline(help) {
	document.post.helpbox.value = eval(help + "_help");
}


// Replacement for arrayname.length property
function getarraysize(thearray) {
	for (i = 0; i < thearray.length; i++) {
		if ((thearray[i] == "undefined") || (thearray[i] == "") || (thearray[i] == null))
			return i;
		}
	return thearray.length;
}

// Replacement for arrayname.push(value) not implemented in IE until version 5.5
// Appends element to the array
function arraypush(thearray,value) {
	thearray[ getarraysize(thearray) ] = value;
}

// Replacement for arrayname.pop() not implemented in IE until version 5.5
// Removes and returns the last element of an array
function arraypop(thearray) {
	thearraysize = getarraysize(thearray);
	retval = thearray[thearraysize - 1];
	delete thearray[thearraysize - 1];
	return retval;
}

function emoticon(text) {
	text = ' ' + text + ' ';
	if (document.post.message.createTextRange && document.post.message.caretPos) {
		var caretPos = document.post.message.caretPos;
		caretPos.text = caretPos.text.charAt(caretPos.text.length - 1) == ' ' ? text + ' ' : text;
		document.post.message.focus();
	} else {
	document.post.message.value  += text;
	document.post.message.focus();
	}
}

function bbfontstyle(bbopen, bbclose) {
	if ((clientVer >= 4) && is_ie && is_win) {
		theSelection = document.selection.createRange().text;
		if (!theSelection) {
			document.post.message.value += bbopen + bbclose;
			document.post.message.focus();
			return;
		}
		document.selection.createRange().text = bbopen + theSelection + bbclose;
		document.post.message.focus();
		return;
	} else {
		document.post.message.value += bbopen + bbclose;
		document.post.message.focus();
		return;
	}
	storeCaret(document.post.message);
}


function bbstyle(bbnumber) {

	donotinsert = false;
	theSelection = false;
	bblast = 0;

	if (bbnumber == -1) { // Close all open tags & default button names
		while (bbcode[0]) {
			butnumber = arraypop(bbcode) - 1;
			document.post.message.value += bbtags[butnumber + 1];

			var btn = eval('document.post.addbbcode' + butnumber);
			if (btn) {
				buttext = btn.value;
				btn.value = buttext.substr(0, buttext.length - 1);
			}
		}
		imageTag = false; // All tags are closed including image tags :D
		document.post.message.focus();
		return;
	}

	if ((clientVer >= 4) && is_ie && is_win)
		theSelection = document.selection.createRange().text; // Get text selection

	if (theSelection) {
		// Add tags around selection
		document.selection.createRange().text = bbtags[bbnumber] + theSelection + bbtags[bbnumber+1];
		document.post.message.focus();
		theSelection = '';
		return;
	}

	// Find last occurance of an open tag the same as the one just clicked
	for (i = 0; i < bbcode.length; i++) {
		if (bbcode[i] == bbnumber+1) {
			bblast = i;
			donotinsert = true;
		}
	}
	
	if (donotinsert) {		// Close all open tags up to the one just clicked & default button names
		while (bbcode[bblast]) {
				butnumber = arraypop(bbcode) - 1;
				document.post.message.value += bbtags[butnumber + 1];

				btn = eval('document.post.addbbcode' + butnumber);
				if (btn) {
					buttext =  btn.value;
					btn.value = buttext.substr(0, buttext.length - 1);
				}

				imageTag = false;
			}
			document.post.message.focus();
			return;
	} else { // Open tags


		if (imageTag && (bbnumber != 14)) {		// Close image tag before adding another
			document.post.message.value += bbtags[15];
			lastValue = arraypop(bbcode) - 1;	// Remove the close image tag from the list
			document.post.addbbcode14.value = "Img";	// Return button back to normal state
			imageTag = false;
		}

		// Open tag
		document.post.message.value += bbtags[bbnumber];
		if ((bbnumber == 14) && (imageTag == false)) imageTag = 1; // Check to stop additional tags after an unclosed image tag
		arraypush(bbcode,bbnumber+1);

		btn = eval('document.post.addbbcode' + bbnumber);
		if (btn) {
			btn.value += "*";
		}

		document.post.message.focus();
		return;
	}
	storeCaret(document.post.message);
}

// Insert at Claret position. Code from
// http://www.faqts.com/knowledge_base/view.phtml/aid/1052/fid/130
function storeCaret(textEl) {
	if (textEl.createTextRange) textEl.caretPos = document.selection.createRange().duplicate();
}

function validatePostForm(f)
{
	<#if setType?default(true)>
	if (f.subject.value == "") {
		alert("${I18n.getMessage("PostForm.subjectEmpty")}");
		f.subject.focus();
		
		return false;
	}
	</#if>
	
	if (f.message.value.replace(/^\s*|\s*$/g, "").length == 0) {
		alert("${I18n.getMessage("PostForm.textEmpty")}");
		f.message.focus();
		
		return false;
	}
	
	return true;
}