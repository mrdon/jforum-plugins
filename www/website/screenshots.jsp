<html>
<head>
<title>JForum - Powering Communities</title>
<link href="jforum.css" rel="stylesheet" type="text/css" />

<script language="javascript">
function show_big(image)
{
	window.open('screenshots/' + image, 'screenshot');
}
</script>
</head>
<body>

<jsp:include page="header.htm"/>

<table width="792" align="center" border="0">
	<tr height="10">
		<td colspan="2">&nbsp;</td>
	</tr>

	<tr>
		<td valign="top" rowspan="4" width="12%">
		<img src="dot.gif"> <a href="index.jsp">Main page</a><br/>
		<img src="dot.gif"> <a href="features.jsp">Features</a><br/>
		<img src="dot.gif"> <a href="download.jsp">Download</a><br/>
		</td>
	</tr>

	<tr>
		<td width="50%" valign="top" colspan="3">
			<font style="font-family: verdana; font-size: 22px;"><b>Screenshots</b></font><br/>
			<img src="h_bar.gif"><br>
			<p>
			Here are some screenhots of JForum. Click on the images to expand.
			</p>
		</td>
	</tr>

	<tr><td>&nbsp;</td></tr>

	<tr>
		<td valign="top" colspan="3">
			<table width="100%" cellspacing="10">
				<tr>
					<td width="33%" valign="top">
						<a href="#" onClick="show_big('forum_listing_b.jpg');"><img src="screenshots/forum_listing_s.jpg"></a><br/>
						Main forum page, listing all categories and forums available to the user, as well information about the last message of each forum
					</td>
					<td width="33%" valign="top">
						<a href="#" onClick="show_big('topic_listing_b.jpg');"><img src="screenshots/topic_listing_s.jpg"></a><br/>
						Topic listing page, showing the first page of topics of some forum
					</td>
					<td width="33%" valign="top">
						<a href="#" onClick="show_big('posting_messages_b.jpg');"><img src="screenshots/posting_messages_s.jpg"></a><br/>
						Posting a new message. Note all possible text formating options
					</td>
				</tr>

				<tr><td colspan="3">&nbsp;</td></tr>

				<tr>
					<td valign="top">
						<a href="#" onClick="show_big('reading_messages_b.jpg');"><img src="screenshots/reading_messages_s.jpg"></a><br/>
						Reading a topic
					</td>
					<td valign="top">
						<a href="#" onClick="show_big('config_form_b.jpg');"><img src="screenshots/config_form_s.jpg"></a><br/>
						Board configuration options in the Administration Panel
					</td>
					<td valign="top">
						<a href="#" onClick="show_big('online_users_b.jpg');"><img src="screenshots/online_users_s.jpg"></a><br/>
						Information about current online users
					</td>
				</tr>
			</table>
		</td>
	</tr>

</table>

<jsp:include page="bottom.htm"/>

</body>
</html>