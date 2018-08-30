package com.impalapay.airtel.persistence.accountmgmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.accountmgmt.logincount.LoginCount;
import com.impalapay.airtel.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link LoginCount}
 * <p>
 * Copyright (c) ImpalaPay LTD., Jan 22, 2015
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * 
 */


public class LoginCountDAO extends GenericDAO implements AirtelLoginCountDAO {
	private static LoginCountDAO logincountDAO;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private BeanProcessor beanProcessor = new BeanProcessor();
    
	/**
	 * 
	 * @return LoginCountDAO
	 */
	public static LoginCountDAO getInstance() {
		if (logincountDAO == null) {
			logincountDAO = new LoginCountDAO();
		}

		return logincountDAO;
	}

	
	/**
     *
     */
	protected LoginCountDAO() {
		super();
	}
	
	/**
	 * @param dbName
	 * @param dbHost
	 * @param dbUsername
	 * @param dbPassword
	 * @param dbPort
	 */
	public LoginCountDAO(String dbName, String dbHost, String dbUsername,
			String dbPassword, int dbPort) {
		super(dbName, dbHost, dbUsername, dbPassword, dbPort);
	}


	@Override
	public LoginCount getLoginCount(String uuid) {
		LoginCount a = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM logincount WHERE uuid = ?;");
			pstmt.setString(1, uuid);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				a = beanProcessor.toBean(rset, LoginCount.class);
				//a.setId(rset.getInt("accountId"));
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting logincount with uuid '" + uuid + "'");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {rset.close();} catch (SQLException e) {}
			}

			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException e) {}
			}

			if (conn != null) {
				try {conn.close();} catch (SQLException e) {}
			}
		}

		return a;
	}
	
	@Override
	public LoginCount getLoginCount(Account account) {
		LoginCount a = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM logincount WHERE accountuuid = ?;");
			pstmt.setString(1, account.getUuid());

			rset = pstmt.executeQuery();

			if (rset.next()) {
				a = beanProcessor.toBean(rset, LoginCount.class);
				//a.setId(rset.getInt("accountId"));
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting logincount with accountuuid '" + account + "'");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {rset.close();} catch (SQLException e) {}
			}

			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException e) {}
			}

			if (conn != null) {
				try {conn.close();} catch (SQLException e) {}
			}
		}

		return a;
	}

	@Override
	public List<LoginCount> getAllLoginCounts() {
		List<LoginCount> list = new ArrayList<>();
		LoginCount a = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM logincount;");
			rset = pstmt.executeQuery();

			while (rset.next()) {
				a = beanProcessor.toBean(rset, LoginCount.class);
				//a.setId(rset.getInt("accountId"));

				list.add(a);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting all logincounts.");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {rset.close();} catch (SQLException e) {}
			}

			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException e) {}
			}

			if (conn != null) {
				try {conn.close();} catch (SQLException e) {}
			}
		}

		return list;
	}


	@Override
	public boolean addLoginCount(Account account) {
		boolean success = true;

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbCredentials.getConnection();
            pstmt = conn.prepareStatement("INSERT INTO logincount(uuid, accountuuid,countlogin) VALUES (?, ?, ?);");

            pstmt.setString(1,UUID.randomUUID().toString());
            pstmt.setString(2,account.getUuid());
            pstmt.setInt(3, 0);
           
            
            pstmt.execute();

        } catch (SQLException e) {
            logger.error("SQLException exception while adding logincount for " + account);
            logger.error(ExceptionUtils.getStackTrace(e));
            success = false;
            
        } finally {
            if (pstmt != null) {
                try { pstmt.close(); } catch (SQLException e) {}
            }

            if (conn != null) {
                try { conn.close(); } catch (SQLException e) {}
            }
        }

        return success;
	}


	@Override
	public boolean updateLoginCount(String accountUuid) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean resetLoginCount(Account account) {
		boolean success = true;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("UPDATE logincount "
							+ "SET countlogin = ?"
							+ "WHERE accountUuid = (SELECT accountuuid FROM logincount WHERE accountUuid=?);");

			pstmt.setInt(1, 0);
			pstmt.setString(2, account.getUuid());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQLException exception while incrementing logincount");
			logger.error(ExceptionUtils.getStackTrace(e));
			success = false;

		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return success;
	}


	@Override
	public boolean incrementLoginCount(Account account) {
		boolean success = true;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("UPDATE logincount "
							+ "SET countlogin = (SELECT countlogin FROM logincount WHERE accountuuid = ?) "
							+ "+ ? "
							+ "WHERE accountUuid = (SELECT accountuuid FROM logincount WHERE accountUuid=?);");

			pstmt.setString(1, account.getUuid());
			pstmt.setInt(2, 1);
			pstmt.setString(3, account.getUuid());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQLException exception while incrementing logincount");
			logger.error(ExceptionUtils.getStackTrace(e));
			success = false;

		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return success;
	}


	
	

}
