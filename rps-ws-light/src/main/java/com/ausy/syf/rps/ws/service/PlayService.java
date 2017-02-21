package com.ausy.syf.rps.ws.service;

import com.ausy.syf.rps.ws.model.CardEnum;
import com.ausy.syf.rps.ws.model.ResultResponse;

/**
 * Created by Nelson Levert on 2/20/17.
 */
public interface PlayService {
	ResultResponse play(String name, CardEnum userCardEnum);
}
