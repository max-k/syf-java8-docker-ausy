package com.ausy.dao.manager;

import javax.ejb.Local;

import com.ausy.dao.PlayerDao;
import com.ausy.dao.RpsResultDao;

/**
 * @author dmaldonado
 *
 */
@Local
public interface IRpsDaoManager {
	
	PlayerDao getPlayerDao(); 
	
	RpsResultDao getRpsResultDao();

}
