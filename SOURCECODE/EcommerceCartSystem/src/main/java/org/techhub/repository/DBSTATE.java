package org.techhub.repository;

import java.sql.*;

public class DBSTATE {
	DBConfig config = DBConfig.getInstance();
	protected Connection conn = config.getConn();
	protected PreparedStatement stmt, tempStmt = config.getStatement();
	protected ResultSet rs, itemRs = config.getResultSet();
}
