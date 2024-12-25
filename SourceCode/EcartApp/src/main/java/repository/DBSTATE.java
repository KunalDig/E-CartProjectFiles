package repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBSTATE {
	DBConfig config = DBConfig.getInstance();
	@SuppressWarnings("static-access")
	protected Connection conn = config.getConn();
	@SuppressWarnings("static-access")
	protected PreparedStatement stmt = config.getStatement();
	@SuppressWarnings("static-access")
	protected CallableStatement cStmt = config.getCStatement();
	@SuppressWarnings("static-access")
	protected ResultSet rs = config.getResultSet();
}
