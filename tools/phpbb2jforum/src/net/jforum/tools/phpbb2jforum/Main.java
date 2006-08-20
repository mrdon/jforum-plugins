package net.jforum.tools.phpbb2jforum;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.jforum.tools.phpbb2jforum.ConfigKeys;
import net.jforum.tools.common.SystemGlobals;

/**
 * @author Rafael Steil
 * @version $Id: Main.java,v 1.1 2006/08/20 22:47:42 rafaelsteil Exp $
 */
public class Main
{
	private Connection conn;

	private Connection conn2;

	private String[][] regexps;

	public Main()
	{
		this.regexps = new String[][] { { ConfigKeys.B_REGEX, ConfigKeys.B_REPLACE },
				{ ConfigKeys.COLOR_REGEX, ConfigKeys.COLOR_REPLACE }, { ConfigKeys.I_REGEX, ConfigKeys.I_REPLACE },
				{ ConfigKeys.LIST_REGEX, ConfigKeys.LIST_REPLACE },
				{ ConfigKeys.QUOTE_REGEX, ConfigKeys.QUOTE_REPLACE },
				{ ConfigKeys.QUOTE_USERNAME_OPEN_REGEX, ConfigKeys.QUOTE_USERNAME_OPEN_REPLACE },
				{ ConfigKeys.QUOTE_USERNAME_CLOSE_REGEX, ConfigKeys.QUOTE_USERNAME_CLOSE_REPLACE },
				{ ConfigKeys.U_REGEX, ConfigKeys.U_REPLACE }, { ConfigKeys.IMG_REGEX, ConfigKeys.IMG_REPLACE },
				{ ConfigKeys.CODE_REGEX, ConfigKeys.CODE_REPLACE }, { ConfigKeys.SIZE_REGEX, ConfigKeys.SIZE_REPLACE } };
	}

	private Connection openConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName(SystemGlobals.getValue(ConfigKeys.DATABASE_DRIVER));
		return DriverManager.getConnection(SystemGlobals.getValue(ConfigKeys.DATABASE_JFORUM_URL));
	}

	private void init() throws IOException
	{
		SystemGlobals.initGlobals(Main.class.getResourceAsStream("/phpbb2jforum/SystemGlobals.properties"));
		SystemGlobals.loadQueries(Main.class.getResourceAsStream("/phpbb2jforum/"
				+ SystemGlobals.getValue(ConfigKeys.DATABASE_QUERIES)));
	}

	private void executeStatement(String name) throws SQLException
	{
		Statement s = this.conn.createStatement();
		s.executeQuery(name);
		s.close();
	}

	private void runForrestRun() throws Exception
	{
		this.cleanTables();
		this.importUsers();
		this.importTables();
		this.importPrivateMessages();
		this.importPosts();
	}

	private void importPosts() throws SQLException
	{
		System.out.println("Importing posts text. This may take a looooong time...");
		int total = this.getTotalPosts();

		if (total == 0) {
			System.out.println("ooopss. Seems like there are no posts to import. Skipping...");
			return;
		}

		System.out.println("Going to process " + total + " posts...");
		int counter = 0;

		// Arawak:
		// changed this to be forward-only so that we don't blow the
		// stack on large databases. performance will suffer :-(
		Statement s = this.conn2.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
				java.sql.ResultSet.CONCUR_READ_ONLY);
		s.setFetchSize(Integer.MIN_VALUE);

		PreparedStatement insert = this.conn.prepareStatement(SystemGlobals.getSql(ConfigKeys.QUERY_POSTS_TEXT));
		ResultSet rs = s.executeQuery(SystemGlobals.getSql(ConfigKeys.QUERY_SELECT_POSTS_TEXT));
		System.out.println("ok, here we go");
		while (rs.next()) {
			if ((++counter % 500) == 0) {
				System.out.println("Processed " + counter + " posts so far");
			}

			insert.setInt(1, rs.getInt("post_id"));
			insert.setString(2, rs.getString("post_subject"));
			insert.setString(3, this.applyRegexToPostText(rs.getString("post_text")));

			insert.executeUpdate();
		}

		rs.close();
		insert.close();
		System.out.println("Post importing done...");
	}

	private void importPrivateMessages() throws SQLException
	{
		System.out.println("Importing private messages text...");

		PreparedStatement insert = this.conn.prepareStatement(SystemGlobals.getSql(ConfigKeys.QUERY_PRIVMSGS_TEXT));
		Statement s = this.conn.createStatement();
		ResultSet rs = s.executeQuery(SystemGlobals.getSql(ConfigKeys.QUERY_SELECT_PM));

		while (rs.next()) {
			insert.setInt(1, rs.getInt("privmsgs_text_id"));
			insert.setString(2, this.applyRegexToPostText(rs.getString("privmsgs_text")));

			insert.executeUpdate();
		}

		insert.close();
		s.close();
		rs.close();

		System.out.println("Private messages text imported...");
	}

	private void importUsers() throws SQLException
	{
		System.out.println("Importing users...");

		PreparedStatement insert = this.conn.prepareStatement(SystemGlobals.getSql(ConfigKeys.QUERY_USERS));
		Statement s = this.conn.createStatement();

		ResultSet rs = s.executeQuery(SystemGlobals.getSql(ConfigKeys.QUERY_SELECT_USERS));
		while (rs.next()) {
			insert.setInt(1, rs.getInt("user_id"));
			insert.setString(2, rs.getString("user_active"));
			insert.setString(3, rs.getString("username"));
			insert.setString(4, rs.getString("user_password"));
			insert.setString(5, rs.getString("user_regdate"));
			insert.setString(6, rs.getString("user_level"));
			insert.setString(7, rs.getString("user_posts"));
			insert.setString(8, rs.getString("user_timezone"));
			insert.setString(9, rs.getString("user_style"));
			insert.setString(10, "");
			insert.setString(11, rs.getString("user_dateformat"));
			insert.setString(12, rs.getString("user_new_privmsg"));
			insert.setString(13, rs.getString("user_unread_privmsg"));
			insert.setString(14, rs.getString("user_last_privmsg"));
			insert.setString(15, rs.getString("user_viewemail"));
			insert.setString(16, rs.getString("user_attachsig"));
			insert.setString(17, rs.getString("user_allowhtml"));
			insert.setString(18, rs.getString("user_allowbbcode"));
			insert.setString(19, rs.getString("user_allowsmile"));
			insert.setString(20, rs.getString("user_allowavatar"));
			insert.setString(21, rs.getString("user_allow_pm"));
			insert.setString(22, rs.getString("user_notify"));
			insert.setString(23, rs.getString("user_notify_pm"));
			insert.setString(24, rs.getString("user_popup_pm"));
			insert.setString(25, rs.getString("user_rank"));
			insert.setString(26, rs.getString("user_avatar"));
			insert.setString(27, rs.getString("user_avatar_type"));
			insert.setString(28, rs.getString("user_email"));
			insert.setString(29, rs.getString("user_icq"));
			insert.setString(30, rs.getString("user_website"));
			insert.setString(31, rs.getString("user_from"));
			insert.setString(32, this.applyRegexToPostText(rs.getString("user_sig")));
			insert.setString(33, rs.getString("user_aim"));
			insert.setString(34, rs.getString("user_yim"));
			insert.setString(35, rs.getString("user_msnm"));
			insert.setString(36, rs.getString("user_occ"));
			insert.setString(37, rs.getString("user_interests"));
			insert.setString(38, rs.getString("user_allow_viewonline"));

			insert.executeUpdate();
		}

		s.close();
		insert.close();
		rs.close();
	}

	private String applyRegexToPostText(String text)
	{
		for (int i = 0; i < this.regexps.length; i++) {
			if (text == null) {
				text = "";
			}
			else {
				text = text.replaceAll(SystemGlobals.getValue(this.regexps[i][0]), SystemGlobals
						.getValue(this.regexps[i][1]));
			}
		}

		return text;
	}

	private int getTotalPosts() throws SQLException
	{
		int total = 0;

		Statement s = this.conn.createStatement();
		ResultSet rs = s.executeQuery(SystemGlobals.getSql(ConfigKeys.QUERY_TOTAL_POSTS));

		if (rs.next()) {
			total = rs.getInt(1);
		}

		rs.close();
		s.close();

		return total;
	}

	private void cleanTables() throws SQLException
	{
		System.out.println("Cleaning tables...");

		String[] queries = { ConfigKeys.QUERY_CLEAN_CATEGORIES, ConfigKeys.QUERY_CLEAN_FORUMS,
				ConfigKeys.QUERY_CLEAN_POSTS, ConfigKeys.QUERY_CLEAN_PRIVMSGS, ConfigKeys.QUERY_CLEAN_RANKS,
				ConfigKeys.QUERY_CLEAN_SEARCH_WORDMATCH, ConfigKeys.QUERY_CLEAN_SEARCH_WORDS,
				ConfigKeys.QUERY_CLEAN_TOPICS, ConfigKeys.QUERY_CLEAN_TOPICS_WATCH, ConfigKeys.QUERY_CLEAN_USERS,
				ConfigKeys.QUERY_CLEAN_WORDS, ConfigKeys.QUERY_CLEAN_POSTS_TEXT, ConfigKeys.QUERY_CLEAN_PRIVMSGS_TEXT };

		for (int i = 0; i < queries.length; i++) {
			System.out.println("Cleaning " + queries[i]);

			Statement s = this.conn.createStatement();
			s.executeUpdate(SystemGlobals.getSql(queries[i]));
			s.close();
		}

		System.out.println("Tables cleaned...");
	}

	private void importTables() throws SQLException
	{
		String[][] queries = { { "categories", ConfigKeys.QUERY_CATEGORIES }, { "forums", ConfigKeys.QUERY_FORUMS },
				{ "private messages", ConfigKeys.QUERY_PRIVMSGS }, { "rankings", ConfigKeys.QUERY_RANKS },
				// { "search words", ConfigKeys.QUERY_SEARCH_WORDS },
				// { "search words match", ConfigKeys.QUERY_SEARCH_WORDMATCH },
				{ "topics", ConfigKeys.QUERY_TOPICS }, { "topics watch", ConfigKeys.QUERY_TOPICS_WATCH },
				{ "words", ConfigKeys.QUERY_WORDS }, { "posts", ConfigKeys.QUERY_POSTS },
				{ "anonymous update", ConfigKeys.QUERY_UPDATE_ANONYMOUS } };

		for (int i = 0; i < queries.length; i++) {
			System.out.println("Importing " + queries[i][0] + "...");

			Statement s = this.conn.createStatement();
			s.executeUpdate(SystemGlobals.getSql(queries[i][1]));
			s.close();
		}
	}

	public static void main(String[] args)
	{
		Main m = new Main();
		boolean ok = true;

		if (args.length != 4) {
			System.out.println("Usage: phpbb2jforum <dbName> <dbUser> <dbPassword> <dbHost>");
			System.out.println("Example: phpbb2jforum jforum root rootPasswd localhost\n");
			return;
		}

		try {
			m.init();

			SystemGlobals.setValue(ConfigKeys.DATABASE_JFORUM_URL, "jdbc:mysql://" + args[3] + "/" + args[0] + "?user="
					+ args[1] + "&password=" + args[2]);

			m.conn = m.openConnection();
			m.conn.setAutoCommit(false);

			// Arawak:
			// we need another connection because the forward-only
			// query we use later will block until the query is
			// complete and so we can't write the to new table will
			// reading from the old
			m.conn2 = m.openConnection();
			m.conn2.setAutoCommit(false);

			long start = System.currentTimeMillis();
			m.runForrestRun();
			long end = System.currentTimeMillis() - start;

			System.out.println("\nDone!!!");
			System.out.println("Migration was done in about " + (end / 1000) + " seconds ");
		}
		catch (Exception e) {
			if (m.conn != null) {
				try {
					ok = false;
					m.conn.rollback();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			e.printStackTrace();
		}
		finally {
			if (m.conn != null) {
				try {
					if (ok) {
						m.conn.commit();
					}

					m.conn.close();
				}
				catch (SQLException e) {
				}
			}
			if (m.conn2 != null) {
				try {
					if (ok) {
						m.conn2.commit();
					}

					m.conn2.close();
				}
				catch (SQLException e) {
				}
			}

		}
	}
}
