<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><!-- InstanceBegin template="/Templates/base_template.dwt" codeOutsideHTMLIsLocked="true" -->
<head>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="style_jforum.css" rel="stylesheet" type="text/css">
<!-- InstanceBeginEditable name="doctitle" -->
<title>JForum - Powering communities</title>
<!-- InstanceEndEditable -->
<!-- InstanceBeginEditable name="head" --><!-- InstanceEndEditable -->
<script type="text/javascript" src="swfobject.js"></script>
</head>

<body class="main">
<table border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="5" align="center">
		<div id="flash_contents"></div>
		<script type="text/javascript">
		var flash = new SWFObject("header.swf", "jforum_header", "766", "206", "8");
		flash.write("flash_contents");
		</script>
	</td>
	</tr>
	<tr>
		<td width="11" rowspan="3">&nbsp;</td>
		<td colspan="3">&nbsp;</td>
		<td width="11" rowspan="3">&nbsp;</td>
	</tr>
	<tr>
		<td valign="top" width="214"><table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td><table width="214" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan="3"><img src="images/dl_version.gif" width="214" height="48"></td>
						</tr>
					<tr>
						<td colspan="3" background="images/dl_bg.gif" valign="top" width="214">
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td valign="top"><div style="padding:10px;"><img src="images/product_box.gif"></div></td>
									<td valign="top">
										<div style="padding:10px;" class="white">
											Many improvements were made, and bugs were fixed.
										</div>
										<div style="padding-top:5px;" class="white"><strong><a href="/download.jsp" class="white">Details</a> | <a href="/download.jsp" class="white">Download</a></strong></div>
										<div style="padding-top:5px; padding-bottom: 5px;"><a href="download.jsp"><img src="images/dl_now.gif" border="0"></a></div>
									</td>
								</tr>
							</table>

						</td>
					</tr>
					<tr>
						<td width="10"><img src="images/dl_lower_left_corner.gif" width="10" height="11"></td>
						<td><img src="images/dl_lower_line.gif" width="194" height="11"></td>
						<td width="10"><img src="images/dl_lower_right_corner.gif" width="10" height="11"></td>
					</tr>
				</table></td>
			</tr>
			<tr>
				<td><table width="214" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="10"><img src="images/pl_top_left_corner.gif" width="10" height="10"></td>
						<td><img src="images/pl_top_line.gif" width="194" height="10"></td>
						<td width="10"><img src="images/pl_top_right_corner.gif" width="10" height="10"></td>
					</tr>
					<tr>
						<td background="images/pl_left_line.gif">&nbsp;</td>
						<td bgcolor="#3F3F3F">
							<div style="padding:5px 0px;"><img src="images/bt_pl.gif"></div>
							<div style="padding-top:5px; padding-left:10px;" class="white wiki-menu">
								<ul class="ul">
									<li class="li"><a href="/index.jsp" class="white">Home</a>
									<li class="li"><a href="/community.jsp" class="white">Forum</a>
									<li class="li"><a href="/Wiki.jsp" class="white">Documentation</a>
									<li class="li"><a href="#" class="white">How do I install JForum?</a>
									<li class="li"><a href="#" class="white">How to contribute</a>
									<li class="li"><a href="#" class="white">Getting help</a>
									<li class="li"><a href="#" class="white">Support the project</a>
								</ul>
							</div>
						</td>
						<td background="images/pl_right_line.gif">&nbsp;</td>
					</tr>
					<tr>
						<td><img src="images/pl_lower_left_corner.gif" width="10" height="9"></td>
						<td><img src="images/pl_lower_line.gif" width="194" height="9"></td>
						<td><img src="images/pl_lower_right_corner.gif" width="10" height="9"></td>
					</tr>
				</table></td>
			</tr>
			<tr>
				<td><table width="214" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table></td>
			</tr>
		</table>
		
		<div class="menu" style="padding-left:10px; ">
			© <b>JForum Team</b><br>
			Latest version is 2.1.7 <br>
			<a href="/team.jsp" class="white">Meet the team</a> <br>
		</div>
		
		
		</td>
		<td width="4" valign="top">&nbsp;</td>
		<td valign="top" width="80%"><table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="15"><img src="images/cb_left_top_corner.gif" width="15" height="15"></td>
				<td height="15" background="images/cb_top_line.gif"></td>
				<td width="15"><img src="images/cb_right_top_corner.gif" width="15" height="15"></td>
			</tr>
			<tr>
				<td background="images/cb_left_line.gif">&nbsp;</td>
				<td bgcolor="#FFFFFF" width="100%"><!-- InstanceBeginEditable name="MainContent" -->
				<div style="padding:0px 10px">
					<img src="images/bt_manual_install.gif">
						<div><img src="images/hr.gif" width="100%" height="10"></div>
						<p>Here will be shown how to manually configure and install JForum. It is assumed that the you has some knowledge on how to install / configure a Java servlet Container ( or already has one up and running ), and the database is properly configured.</p>
						
						<p><i>For automated installation, check the <a class="blue" href="install.jsp">Installation & configuration - Wizard</a> section.</i></p>
						<p>Note: These instructions are for the installation of JForum, release version 2.1.7. 
								Some of the steps here described may not be valid for older versions, which are no longer supported.
						</p>
						
						<!-- Downloading -->
						<div class="blue-title">Downloading JForum</div>
						<div><img src="images/hr.gif" width="100%" height="5"></div>
						<p>To get JForum, go to the <a class="blue" href="download.jsp">download page</a> and get the latest version.</p>
						
						<!-- Unpacking -->
						<div class="blue-title">Unpacking</div>
						<div><img src="images/hr.gif" width="100%" height="5"></div>
						<p>
							After the download, unpack the .ZIP file into your webapp's directory (or anyplace you want to put it). A directory named 
							<i>JForum-&lt;release&gt;</i> will be created, where &lt;release&gt; is the version, which may be "2.0", "2.1.7" etc... this it just for easy version identification. 
						</p>
						<p>
							You can rename the directory if you want. The next step you should do is register the JForum application within your Servlet Container, 
							like <a href="http://jakarta.apache.org/tomcat" class="blue">Tomcat</a>. This document will use the context name "jforum", but of course you can use any name you want.
						</p>
						
						<!-- Configuring -->
						<!-- Downloading -->
						<div class="blue-title">Database configuration </div>
						<div><img src="images/hr.gif" width="100%" height="5"></div>
						<p>First of all, you must have <a href="http://www.mysql.com" class="blue">MySQL</a>, <a href="http://www.oracle.com" class="blue">Oracle</a> or <a href="http://www.postgresql.org" class="blue">PostgreSQL</a> 
						installed and properly configured. <a href="http://www.hsqldb.org">HSQLDB</a> is supported as well, and has built-in support, so you don't need to download it (eg, it is an embedded database). </p>
						<p>Open the file <i>WEB-INF/config/SystemGlobals.properties</i>. Now search for a key named <i>database.driver.name</i> and configure it according to the following table: </p>
						<table align="center">
						<tr>
							<td>
								<table bgcolor="#3aa315" cellspacing="2" width="70%" align="center">
									<tr>
										<th class="th-header">Database</th>
										<th class="th-header">Key value </th>
									</tr>
					
									<tr class="fields">
										<td align="center">MySQL</td>
										<td>mysql</td>
									</tr>
					
									<tr class="fields">
										<td align="center">PostgreSQL</td>
										<td>postgresql</td>
									</tr>
					
									<tr class="fields">
										<td align="center">HSQLDB</td>
										<td>hsqldb</td>
									</tr>
					
									<tr class="fields">
										<td align="center">Oracle</td>
										<td>oracle</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					
					<p>The default value is mysql, which means JForum will try to use MySQL. Note that the value should be in lowercase. </p>
					<p>Next, you can tell JForum whether to use a Connection Pool or not. A connection pool will increase the performance of your application, but there are some situations where the use of a connection pool is not recommended or even possible, so you can change it according to your needs.</p>
					<p>By default JForum uses a connection pool, option which is specified by the key database.connection.implementation. The following table shows the possible values for this key: </p>
					
					<table align="center">
						<tr>
							<td><table bgcolor="#3aa315" cellspacing="2" width="100%" align="center">
									<tr>
										<th class="th-header">Connection Storage Type</th>
										<th class="th-header">Key value </th>
									</tr>
									<tr class="fields">
										<td align="center">Pooled Connections</td>
										<td><i>net.jforum.PooledConnection</i></td>
									</tr>
									<tr class="fields">
										<td align="center">Simple Connections</td>
										<td><i>net.jforum.SimpleConnection</i></td>
									</tr>
									<tr class="fields">
										<td align="center">DataSource Connections</td>
										<td><i>net.jforum.DataSourceConnection</i></td>
									</tr>
							</table></td>
						</tr>
					</table>

					<p>If you have chosen <i>net.jforum.DataSourceConnection</i>, then set the name of the datasource in key <i>database.datasource.name</i>, and ignore the table below. Otherwise, do the following steps:</p>
					<p>Edit the file <i>WEB-INF/config/database/&lt;DBNAME&gt;/&lt;DBNAME&gt;.properties</i>, where <i>&lt;DBNAME&gt;</i> is the database name you are using - for instance, mysql, postgresql or hsqldb. In this file there are some options you should change, according to the table below: </p>
					<table align="center">
						<tr>
							<td><table bgcolor="#3aa315" cellspacing="2" width="100%" align="center">
									<tr>
										<th class="th-header">Key Name</th>
										<th class="th-header">key Value description</th>
									</tr>
									<tr class="fields">
										<td align="center">database.connection.username</td>
										<td><i>Database username</i></td>
									</tr>
									<tr class="fields">
										<td align="center">database.connection.password</td>
										<td><i>Database password</i></td>
									</tr>
									<tr class="fields">
										<td align="center">database.connection.host</td>
										<td><i>The host where the database is located</i></td>
									</tr>
									<tr class="fields">
										<td align="center">dbname</td>
										<td><i>The database name. The default value is jforum. All JForum tables are preceded by "jforum_", so you don't need to worry about conflicting table names.</i></td>
									</tr>
							</table></td>
						</tr>
					</table>
					<p>&nbsp;</p>
					<!-- Administering -->
					<div class="blue-title">Administering the Forum</div>
					<div><img src="images/hr.gif" width="100%" height="5"></div>
					<p>Now you can login as <b>Admin</b> / &lt;the_password_you_set&gt; and click in the link "Admin Control Panel", at the end of the page. There you will be able to create Categories, Forums, Groups, and Users.
Don't forget to give write access to the webserver's user to the directories "images" and "tmp" ( as well from its subdiretories, if any ).</p>
				</div>
				<!-- InstanceEndEditable --></td>
				<td background="images/cb_right_line.gif">&nbsp;</td>
			</tr>
			<tr>
				<td><img src="images/cb_lower_left_corner.gif" width="15" height="15"></td>
				<td height="15" background="images/cb_lower_line.gif"></td>
				<td><img src="images/cb_lower_right_corner.gif" width="15" height="15"></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td valign="top" class="menu">&nbsp;</td>
		<td>&nbsp;</td>
		<td align="center" class="menu">
			<div style="padding-top:5px; padding-bottom:30px;">
				<b><a href="/index.jsp" class="white">Home</a></b> | <b><a class="white" href="/download.jsp">Download</a></b> | <b><a class="white" href="/support.jsp">Support</a></b> | <b><a href="/community.jsp" class="white">Forum</a></b> | <b><a href="/development.jsp" class="white">Development</a></b> | <b><a href="/contact.jsp" class="white">Contact</a></b>			</div>
		</td>
	</tr>
</table>
</body>
<!-- InstanceEnd --></html>
