package com.impalapay.airtel.persistence.simulation;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

import com.impalapay.airtel.beans.geolocation.Country;
import com.impalapay.airtel.beans.simulation.ComvivaSimulation;
import com.impalapay.airtel.persistence.geolocation.CountryDAO;



public class TestComvivaSimulationDAO {
	
	final String DB_NAME = "airteldb";
	final String DB_HOST = "localhost"; 
	final String DB_USERNAME = "airtel"; 
	final String DB_PASSWD = "LignuAv7";
    final int DB_PORT = 5432;
    
    final String ERROR_UUID = "91fc8aae-cb76-4c64-ac45-48448fb5673f";
    final String ERROR_NAME = " PAYEE_DAILY_CUMULATIVE_AMT_REACHED ";
    final String ERROR_CODE = "100019";
    final String ERROR_PHONE = "254733456160";
    final int ERROR_COUNT = 3;
            
    private ComvivaSimulationDAO storage;
    
    @Ignore
	@Test
	public void testComvivaSimulationString() {
		storage = new ComvivaSimulationDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
        
		ComvivaSimulation comvivasimulation = storage.getError(ERROR_UUID);
        assertEquals(comvivasimulation.getUuid(), ERROR_UUID);
        assertEquals(comvivasimulation.getErrorname(), ERROR_NAME);
        assertEquals(comvivasimulation.getMobilenumber(), ERROR_PHONE);
          
	}
    
    @Test
	//@Ignore
	public void testGetAllComvivaSimulation() {
		storage = new ComvivaSimulationDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
        
        List<ComvivaSimulation> list = storage.getAllerrors();
        assertEquals(list.size(), ERROR_COUNT);
        
	}
    
    @Ignore
	@Test
    public void testComvivaSimulationPhone() {
		storage = new ComvivaSimulationDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
        
		ComvivaSimulation comvivasimulation = storage.getErrorphone(ERROR_PHONE);
        assertEquals(comvivasimulation.getUuid(), ERROR_UUID);
        assertEquals(comvivasimulation.getErrorname(), ERROR_NAME);
        
        
        
       
          
	}
}
