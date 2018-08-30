package com.impalapay.airtel.persistence.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.LinkedList;
import java.util.List;




import com.impalapay.airtel.beans.transaction.TransactionStatus;

import com.impalapay.airtel.persistence.GenericDAO;

import org.apache.commons.dbutils.BeanProcessor;

import org.apache.commons.lang3.exception.ExceptionUtils;

import org.apache.log4j.Logger;

/**
 * Persistence for {@link TransactionStatus}
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */
public class TransactionStatusDAO extends GenericDAO implements AirtelTransactionStatus {

    private static  TransactionStatusDAO TransactionStatusDAO;
    
    private Logger logger = Logger.getLogger(this.getClass());
    
    private BeanProcessor beanProcessor = new BeanProcessor();
    /**
     * 
     * @return {@link TransactionStatusDAO}
     */
    public static TransactionStatusDAO getInstance() {
        if (TransactionStatusDAO == null) {
            TransactionStatusDAO = new TransactionStatusDAO();
        }
        return TransactionStatusDAO;
    }
    

    /**
     * 
     */
    public TransactionStatusDAO() {
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
    public TransactionStatusDAO(String dbName, String dbHost, String dbUsername, String dbPassword, int dbPort) {
        super(dbName, dbHost, dbUsername, dbPassword, dbPort);
    }

    /**
     * AirtimeGWTransactionStatus#getTransactionStatus(int)
     *
     */
    @Override
    public TransactionStatus getTransactionStatus(int status) {
        TransactionStatus TransactionStatus = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

       

        try {
            conn = dbCredentials.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM TransactionStatus WHERE status = ?;");
            pstmt.setInt(1, status);

            rset = pstmt.executeQuery();

            if (rset.next()) {
                TransactionStatus = beanProcessor.toBean(rset, TransactionStatus.class);
                //TransactionStatus.setId(rset.getInt("TransactionStatusId"));
            }

        } catch (SQLException e) {
            logger.error("SQLException exception while getting TransactionStatus with status '" + status + "'");
            logger.error(ExceptionUtils.getStackTrace(e));

        } finally {
        	if (rset != null) {
                try { rset.close(); } catch (SQLException e) { }
            }

            if (pstmt != null) { 
                try { pstmt.close(); } catch (SQLException e) { }
            }

            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { }
            }
        }

        return TransactionStatus;
    }
    

    /**
     * @see AirtelTransactionStatus#getTransactionStatus(java.lang.String) 
     *
     */
    @Override
    public TransactionStatus getTransactionStatus(String uuid) {
        TransactionStatus TransactionStatus = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        

        try {
            conn = dbCredentials.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM TransactionStatus WHERE uuid = ?;");
            pstmt.setString(1, uuid);

            rset = pstmt.executeQuery();

            if (rset.next()) {
                TransactionStatus = beanProcessor.toBean(rset, TransactionStatus.class);
               // TransactionStatus.setId(rset.getInt("TransactionStatusId"));
            }

        } catch (SQLException e) {
            logger.error("SQLException exception while getting TransactionStatus with uuid '" + uuid + "'");
            logger.error(ExceptionUtils.getStackTrace(e));

        } finally {
        	if (rset != null) {
                try { rset.close(); } catch (SQLException e) { }
            }

            if (pstmt != null) { 
                try { pstmt.close(); } catch (SQLException e) { }
            }

            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { }
            }
        }

        return TransactionStatus;
    }

    
    /**
     * @see AirtelTransactionStatus#getAllTransactionStatus()
     *
     */
    @Override
    public List<TransactionStatus> getAllTransactionStatus() {
        List<TransactionStatus> list = new LinkedList<>();

        TransactionStatus TransactionStatus = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        

        try {
            conn = dbCredentials.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM TransactionStatus ;");

            rset = pstmt.executeQuery();

            while (rset.next()) {
                TransactionStatus = beanProcessor.toBean(rset, TransactionStatus.class);
                //TransactionStatus.setId(rset.getInt("TransactionStatusId"));

                list.add(TransactionStatus);
            }

        } catch (SQLException e) {
            logger.error("SQLException exception while getting all TransactionStatus ");
            logger.error(ExceptionUtils.getStackTrace(e));

        } finally {
        	if (rset != null) {
                try { rset.close(); } catch (SQLException e) { }
            }

            if (pstmt != null) { 
                try { pstmt.close(); } catch (SQLException e) { }
            }

            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { }
            }
        }

        return list;
    }
    

    /**
     * @see AirtelTransactionStatus#addTransactionStatus(com.impalapay.airtel.beans.transaction.TransactionStatus)  
     *
     */
    @Override
    public boolean addTransactionStatus(TransactionStatus TransactionStatus) {
        boolean success = true;

        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = dbCredentials.getConnection();
            pstmt = conn.prepareStatement("INSERT INTO TransactionStatus(uuid, status, description) VALUES (?, ?, ?);");
            
            pstmt.setString(1, TransactionStatus.getUuid());
            pstmt.setString(2, TransactionStatus.getStatus());
            pstmt.setString(3, TransactionStatus.getDescription());
            
            pstmt.execute();

        } catch (SQLException e) {
            logger.error("SQLException exception while adding TransactionStatus");
            logger.error(ExceptionUtils.getStackTrace(e));

            success = false;
            
        } finally {
            if (pstmt != null) { 
                try { pstmt.close(); } catch (SQLException e) { }
            }

            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { }
            }
        }

        return success;
    }
}
