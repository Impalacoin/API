package com.impalapay.airtel.persistence.simulation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.impalapay.airtel.beans.geolocation.Country;
import com.impalapay.airtel.beans.simulation.ComvivaSimulation;
import com.impalapay.airtel.persistence.GenericDAO;


public class ComvivaSimulationDAO extends GenericDAO implements AirtelComvivaSimulation {
	
	private static ComvivaSimulationDAO comvivasimulationDAO;

	private Logger logger = Logger.getLogger(this.getClass());

	private BeanProcessor beanProcessor = new BeanProcessor();

	/**
	 *
	 * @return {@link ComvivaSimulationDAO}
	 */
	public static ComvivaSimulationDAO getInstance() {
		if (comvivasimulationDAO == null) {
			comvivasimulationDAO = new ComvivaSimulationDAO();
		}

		return comvivasimulationDAO;
	}

	/**
     *
     */
	protected ComvivaSimulationDAO() {
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
	public ComvivaSimulationDAO(String dbName, String dbHost, String dbUsername,
			String dbPassword, int dbPort) {
		super(dbName, dbHost, dbUsername, dbPassword, dbPort);
	}

	@Override
	public ComvivaSimulation getError(String uuid) {
		ComvivaSimulation s = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM errorsimulation WHERE uuid = ?;");
			pstmt.setString(1, uuid);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				s = beanProcessor.toBean(rset, ComvivaSimulation.class);
				//s.setId(rset.getInt("id"));
			}

		} catch (SQLException e) {
			logger.error("SQLException while getting error simulation with uuid '"
					+ uuid + "'");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try { rset.close(); } catch (SQLException e) { }
			}

			if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException e) { }
			}

			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { 	}
			}
		}

		return s;
	}

	@Override
	public List<ComvivaSimulation> getError(ComvivaSimulation comvivasimulation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComvivaSimulation> getAllerrors() {
		List<ComvivaSimulation> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM errorsimulation ORDER BY id ASC;");

			rset = pstmt.executeQuery();

			list = beanProcessor.toBeanList(rset, ComvivaSimulation.class);
			
		} catch (SQLException e) {
			logger.error("SQLException while getting all error simulations.");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try { rset.close(); } catch (SQLException e) { }
			}

			if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException e) { }
			}

			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { 	}
			}
		}

		return list;
	}

	@Override
	public boolean putError(ComvivaSimulation comvivasimulation) {
		boolean success = true;
		Date date;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("INSERT INTO errorsimulation(uuid,mobilenumber,errorcode,"
							+ "errorname) VALUES (?, ?, ?, ?);");

			pstmt.setString(1, comvivasimulation.getUuid());
			pstmt.setString(2, comvivasimulation.getMobilenumber());
			pstmt.setString(3, comvivasimulation.getErrorcode());
			pstmt.setString(4, comvivasimulation.getErrorname());
			

			pstmt.execute();

		} catch (SQLException e) {
			logger.error("SQLException exception while adding " + comvivasimulation);
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
	public ComvivaSimulation getErrorphone(String phone) {
		ComvivaSimulation s = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM errorsimulation WHERE mobilenumber = ?;");
			pstmt.setString(1, phone);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				s = beanProcessor.toBean(rset, ComvivaSimulation.class);
				//s.setId(rset.getInt("id"));
			}

		} catch (SQLException e) {
			logger.error("SQLException while getting error simulation with mobile number'"
					+ phone + "'");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try { rset.close(); } catch (SQLException e) { }
			}

			if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException e) { }
			}

			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { 	}
			}
		}

		return s;
	}

}
