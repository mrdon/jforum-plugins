<html>
<head>
<title>JForum - Powering Communities</title>
<link href="jforum.css" rel="stylesheet" type="text/css" />
</head>
<body>

<jsp:include page="header.htm"/>

<table width="792" align="center" border="0">
	<tr height="10">
		<td colspan="2">&nbsp;</td>
	</tr>

	<tr>
		<td valign="top" rowspan="2" width="12%">
		<img src="dot.gif"> <a href="index.jsp">Main page</a><br/>
		<img src="dot.gif"> <a href="features.jsp">Features</a><br/>
		<img src="dot.gif"> <a href="download.jsp">Download</a>
		</td>
	</tr>

	<tr>
		<td valign="top" rowspan="3">
			<a href="http://www.atlassian.com/c/NPOS/10160"><img src="jira_tile_150wx300h.gif" align="right"></a><br>

			<font style="font-family: verdana; font-size: 22px;"><b>Development</b></font><br/>
			<img src="h_bar.gif"><br>
			<p>
			As an Open-Source software, you are invited to contribute to JForum. There are many areas where you can help, like
			making documentation, new styles, buttons, mods and even helping to develop the core source-code. <br/>We'll have more
			information in this page soon, but a good start point is the forums. You can also send an email to JForum's project
			leader, Rafael Steil, at <b>rafael _[at]_ insanecorp.com</b>.
			</p>

			<p>
			JForum does not uses JSP for content display, but a template engine, which offer much more ease of development and
			maintaince. The template engine used is <a href="http://freemarker.sourceforge.net/">FreeMarker</a>.
			</p>

			<br/>

			<p>
			<img src="info.jpg" align="middle"> <b>Contributors</b><br/>
			A project only evolves with feedback of users and contribution - be it programming, documenting, making graphics and
			so other many areas - of so many valuable people. 

			<p>&nbsp</p>

			<table cellspacing="0" width="50%" align="center" bgcolor="#ff9900">
				<tr>
					<td>
						<table width="100%" bgcolor="#ffffff">
							<tr>
								<td><b>Rafael Steil</b></td>
								<td><i>project leader</i></td>
							</tr>

							<tr>
								<td><b>James Yong</b></td>
								<td><i>developer</i></td>
							</tr>

							<tr>
								<td><b>Marc Wick</b></td>
								<td><i>developer</i></td>
							</tr>

							<tr>
								<td><b>Sean Mitchell</b></td>
								<td><i>ideas, ideas, ideas</i></td>
							</tr>

							<tr>
								<td><b>Pieter Olivier</b></td>
								<td><i>developer</i></td>
							</tr>

							<tr>
								<td><b>Sérgio Umlauf</b></td>
								<td><i>Layout</i></td>
							</tr>

							<tr>
								<td><b>Pablo Marutto</b></td>
								<td><i>logo</i></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			</p>

			<p>
			People come and go all the time. Many times we receive help because an user needs some feature more quickly, or then
			he / she got some free time to contribute for a while, and is that kind of contribution which makes the project grow up.
			<b>You are welcome to join us.</b> 
			</p>

			<br/>

			<p>
			<img src="info.jpg" align="middle"> <b>Bug and Issue Tracker</b><br/>
			JForum issues are tracked by <a href="http://www.atlassian.com/c/NPOS/10160" target="_new">JIRA</a>, by Atlassian. You can see
			the current issues, ask for new features and submit bug reports at <a href="http://www.jforum.net/jira">http://www.jforum.net/jira</a>
			</p>

			<br/>

			<p>
				<img src="info.jpg" align="middle"> <b>Getting the source code from CVS</b> <br/>
				The CVS repository is hosted by <a href="http://java.net" target="_new">Java.net</a>. Below is listed the host access options:
			</p>

			<table cellspacing="0" width="50%" align="center" bgcolor="#ff9900">
				<tr>
					<td>
						<table width="100%" bgcolor="#ffffff">
							<tr>
								<td><b>Host</b></td>
								<td><i>cvs.dev.java.net</i></td>
							</tr>

							<tr>
								<td><b>Path</b></td>
								<td><i>/cvs</i></td>
							</tr>

							<tr>
								<td><b>Server Type</b></td>
								<td><i>pserver</i></td>
							</tr>

							<tr>
								<td><b>Username</b></td>
								<td><i>guest</i></td>
							</tr>

							<tr>
								<td><b>Module</b></td>
								<td><i>jforum</i></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

			<br/>

			<p>
			<img src="info.jpg" align="middle"> <b>Setting up your development environment</b><br>
			First of all, create an empty file named <i>&lt;your_username&gt;.conf</i> in the directory <i>WEB-INF/config</i>, where
			&lt;your_username&gt; is the username you login in your computer. For the sake of information, is the return of a call to

			<br/><br/>
			<i>System.getProperty("user.name")</i>
			<br/><br/>

			so, if your account is "johndoe", you should create a file named <i>johndoe.conf</i>. This file is necessary when working on
			teams, since you should not change <i>SystemGlobals.properties</i> to put your own configuration data, like passwords and 
			forum address. After creating the file, just add it to <i>.cvsignore</i>. 
			</p>

			<p>
			You can follow the install instructions <a href="install.htm">clicking here</a>
			</p>

			<p>
			The best start point is <i>src/net/jforum/JForum.java</i>, which is the Front Controller implementation. 
			<i>src/net/jforum/entities</i> contains all entities used in the system, <i>src/net/jforum/model</i> have the
			interfaces used to persist and retrieve data and <i>src/net/jforum/view</i> contains all view related actions. 
			</p>

			<br/>

			<p>
			<img src="info.jpg" align="middle"> <b>Getting help</b><br/>
			The best place to ask for help is in the forums / Community section. <a href="community.htm">Click here</a> to go there. 
			</p>
		</td>
	</tr>

</table>

<jsp:include page="bottom.htm"/>

</body>
</html>
