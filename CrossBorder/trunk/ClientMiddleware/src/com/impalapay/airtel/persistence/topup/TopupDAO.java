package com.impalapay.airtel.persistence.topup;

import com.impalapay.airtel.beans.topup.Topup;
import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.persistence.GenericDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.impalapay.airtel.persistence.topup.TopupDAO;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

/**
 * Persistence for {@link Topup}.
 * <p>
 * Copyright (c) ImpalaPay LTD., Sep 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */

public class TopupDAO extends GenericDAO implements AccountTopUp {

	private static TopupDAO topupDAO;

	private Logger logger = Logger.getLogger(this.getClass());

	private BeanProcessor b = new BeanProcessor();

	/**
	 * 
	 * @return {@link TopupDAO}
	 */
	public static TopupDAO getInstance() {

		if (topupDAO == null) {
			topupDAO = new TopupDAO();
		}

		return topupDAO;
	}

	/**
     * 
     */
	public TopupDAO() {
		super();
	}

	/**
	 *
	 * @param dbName
	 * @param dbHost
	 * @param dbUsername
	 * @param dbPassword
	 * @param dbPort
	 */
	public TopupDAO(String dbName, String dbHost, String dbUsername,
			String dbPassword, int dbPort) {
		super(dbName, dbHost, dbUsername, dbPassword, dbPort);
	}

	@Override
	public List<Topup> getTopups(String uuid) {
		List<Topup> list = new LinkedList<>();

		Topup topup = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM topup WHERE uuid=?;");
			pstmt.setString(1, uuid);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				topup = b.toBean(rset, Topup.class);

				list.add(topup);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting topups with uuid "
					+ uuid);
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
				}
			}

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

		return list;
	}

	@Override
	public List<Topup> getTopups(Account account) {
		List<Topup> list = new LinkedList<>();

		Topup topup = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM topup WHERE  accountUuid=?;");
			pstmt.setString(1, account.getUuid());

			rset = pstmt.executeQuery();

			while (rset.next()) {
				topup = b.toBean(rset, Topup.class);
				//topup.setId(rset.getInt("topupId"));

				list.add(topup);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting topup of "
					+ account);
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
				}
			}

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

		return list;
	}

	@Override
	public List<Topup> getAllTopups() {
		List<Topup> list = new LinkedList<>();

		Topup topup = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM topup ;");

			rset = pstmt.executeQuery();

			while (rset.next()) {
				topup = b.toBean(rset, Topup.class);
				//topup.setId(rset.getInt("topupId"));

				list.add(topup);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting all topups");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
				}
			}

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

		return list;
	}

	@Override
	public List<Topup> getAllTopups(int fromIndex, int toIndex) {
		List<Topup> list = new LinkedList<>();

		Topup topup = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM topup ORDER BY topuptime DESC LIMIT ? OFFSET ?;");

			pstmt.setInt(1, toIndex - fromIndex);
			pstmt.setInt(2, fromIndex);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				topup = b.toBean(rset, Topup.class);
				//topup.setId(rset.getInt("topupId"));

				list.add(topup);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting topup with uuid  from "
					+ fromIndex + " to " + toIndex);
			logger.error(e.toString());

		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
				}
			}

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

		return list;
	}

	@Override
	public boolean addTopup(Topup topup) {
		boolean success = true;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("INSERT INTO topup(uuid, accountuuid,amount, "
							+ "topupTime) VALUES (?, ?, ?, ?);");

			pstmt.setString(1, topup.getUuid());
			pstmt.setString(2, topup.getAccountUuid());
			pstmt.setDouble(4, topup.getAmount());
			pstmt.setTimestamp(7, new Timestamp(topup.getTopupTime().getTime()));

			pstmt.execute();

		} catch (SQLException e) {
			logger.error("SQLException exception while adding " + topup);
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
	public List<Topup> getTopup(Account account, int fromIndex, int toIndex) {
		List<Topup> list = new LinkedList<>();

		Topup topup = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM topup WHERE  accountuuid=? ORDER BY topuptime DESC LIMIT ? OFFSET ?;");
			pstmt.setString(1, account.getUuid());
			pstmt.setInt(2, toIndex - fromIndex);
			pstmt.setInt(3, fromIndex);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				topup = b.toBean(rset, Topup.class);
				//topup.setId(rset.getInt("topupId"));

				list.add(topup);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting topup with ' "
					+ account + "' from " + fromIndex + " to " + toIndex);
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
				}
			}

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

		return list;
	}

	@Override
	public List<Topup> getTopupByUuid(Account account, String uuid,
			int fromIndex, int toIndex) {
		List<Topup> list = new LinkedList<>();

		Topup topup = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM topup WHERE uuid=? AND accountUuid=? "
							+ "ORDER BY topuptime DESC LIMIT ? OFFSET ?;");
			pstmt.setString(1, uuid);
			pstmt.setString(2, account.getUuid());
			pstmt.setInt(3, toIndex - fromIndex);
			pstmt.setInt(4, fromIndex);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				topup = b.toBean(rset, Topup.class);
				//topup.setId(rset.getInt("topupId"));

				list.add(topup);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting topup with uuid ' "
					+ uuid + "' from " + fromIndex + " to " + toIndex);
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
				}
			}

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

		return list;
	}

	@Override
	public List<Topup> getAllTopupByUuid(String uuid, int fromIndex, int toIndex) {
		List<Topup> list = new LinkedList<>();

		Topup topup = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM topup WHERE  uuid=? ORDER BY topuptime DESC LIMIT ? OFFSET ?;");
			pstmt.setString(1, uuid);
			pstmt.setInt(2, toIndex - fromIndex);
			pstmt.setInt(3, fromIndex);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				topup = b.toBean(rset, Topup.class);
				//topup.setId(rset.getInt("topupId"));

				list.add(topup);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting topup with uuid ' "
					+ uuid + "' from " + fromIndex + " to " + toIndex);
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
				}
			}

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

		return list;
	}
}
