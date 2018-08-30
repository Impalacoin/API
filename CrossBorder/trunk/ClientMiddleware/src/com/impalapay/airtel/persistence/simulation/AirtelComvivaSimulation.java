package com.impalapay.airtel.persistence.simulation;

import java.util.List;

import com.impalapay.airtel.beans.simulation.ComvivaSimulation;


public interface AirtelComvivaSimulation {
	
	
	/**
	 * 
	 * @param uuid
	 * @return
	 */
	public ComvivaSimulation getError(String uuid);
	
	/**
	 * 
	 * @param phone
	 * @return
	 */
	public ComvivaSimulation getErrorphone(String phone);
	
	/**
	 * 
	 * @param comvivasimulation
	 * @return
	 */
	public List<ComvivaSimulation> getError(ComvivaSimulation comvivasimulation);
	
	/**
	 * 
	 * @return
	 */
	public List<ComvivaSimulation> getAllerrors();
	
	/**
	 * 
	 * @param comvivasimulation
	 * @return
	 */
	public boolean putError(ComvivaSimulation comvivasimulation);
	

}
