/*
 * Copyright (c) 2006, Rafael Steil
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the
 * following  disclaimer.
 * 2)  Redistributions in binary form must reproduce the
 * above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 * 3) Neither the name of "Rafael Steil" nor
 * the names of its contributors may be used to endorse
 * or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT
 * HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE
 *
 *
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.util;

import java.sql.*;

/**
 * User: SergeMaslyukov
 * Date: 19.08.2006
 * Time: 21:50:05
 * <p/>
 * $Id: DbUtils.java,v 1.1 2006/08/20 12:19:11 sergemaslyukov Exp $
 */
public class DbUtils {

    public static void close(final Connection conn) {
        if (conn == null)
            return;

        try {
            conn.rollback();
        }
        catch (Exception e) {
            // catch rollback error
        }

        try {
            conn.close();
        }
        catch (Exception e) {
            // catch close error
        }
    }

    public static void close(final Connection conn, final ResultSet rs, final PreparedStatement ps) {
        close(rs, ps);
        close(conn);
    }

    public static void close(final Connection conn, final PreparedStatement ps) {
        close(ps);
        close(conn);
    }

    public static void close(final ResultSet rs, final Statement st) {
        if (rs != null) {
            try {
                rs.close();
            }
            catch (Exception e01) {
                // catch SQLException
            }
        }
        if (st != null) {
            try {
                st.close();
            }
            catch (Exception e02) {
                // catch SQLException
            }
        }
    }

    public static void close(final ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            }
            catch (Exception e01) {
                // catch SQLException
            }
        }
    }

    public static void close(final Statement st) {
        if (st != null) {
            try {
                st.close();
            }
            catch (SQLException e201) {
                // catch SQLException
            }
        }
    }
}
