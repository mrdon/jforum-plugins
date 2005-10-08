<jsp:include page="header.jsp"/>

<script type="text/javascript">
function show_big(image)
{
	window.open('screenshots/' + image, 'screenshot');
}
</script>

<table>
	<tr>
		<td>
			<h1>Screenshots</h1>

			<p>Here are some screenhots of JForum. Click on the images to expand.</p>
		</td>
	</tr>

	<tr><td>&nbsp;</td></tr>

	<tr>
		<td valign="top" colspan="3">
			<table width="100%" cellspacing="10">
				<tr>
					<td width="33%" valign="top">
						<a href="javascript:show_big('forum_listing_b.jpg');"><img src="screenshots/forum_listing_s.jpg" class="image-border"></a><br/>
						Main forum page, listing all categories and forums available to the user, as well information about the last message of each forum
					</td>
					<td width="33%" valign="top">
						<a href="javascript:show_big('topic_listing_b.jpg');"><img src="screenshots/topic_listing_s.jpg" class="image-border"></a><br/>
						Topic listing page, showing the first page of topics of some forum
					</td>
					<td width="33%" valign="top">
						<a href="javascript:show_big('posting_messages_b.jpg')"><img src="screenshots/posting_messages_s.jpg" class="image-border"></a><br/>
						Posting a new message. Note the text formating options and the "Attach Files" button
					</td>
				</tr>

				<tr><td colspan="3">&nbsp;</td></tr>

				<tr>
					<td valign="top">
						<a href="javascript:show_big('reading_messages_b.jpg');"><img src="screenshots/reading_messages_s.jpg" class="image-border"></a><br/>
						Reading a topic
					</td>
					<td valign="top">
						<a href="javascript:show_big('config_form_b.jpg');"><img src="screenshots/config_form_s.jpg" class="image-border"></a><br/>
						Board configuration options in the Administration Panel
					</td>
					<td valign="top">
						<a href="javascript:show_big('online_users_b.jpg');"><img src="screenshots/online_users_s.jpg" class="image-border"></a><br/>
						Information about current online users
					</td>
				</tr>

				<tr><td colspan="3">&nbsp;</td></tr>

				<tr>
					<td valign="top">
						<a href="javascript:show_big('attaching_files_b.jpg');"><img src="screenshots/attaching_files_s.jpg" class="image-border"></a><br/>
						Attaching files to the message
					</td>
					<td valign="top">
						<a href="javascript:show_big('post_attach_b.jpg');"><img src="screenshots/post_attach_s.jpg" class="image-border"></a><br/>
						Message displaying the File Download box of an attached file
					</td>
					<td valign="top">
						<a href="javascript:show_big('member_listing_b.jpg');"><img src="screenshots/member_listing_s.jpg" class="image-border"></a><br/>
						Listing of registered members
					</td>
				</tr>
			</table>
		</td>
	</tr>

</table>

<jsp:include page="bottom.jsp"/>
