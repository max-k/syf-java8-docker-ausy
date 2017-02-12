package com.ausy.state;

import org.junit.Assert;
import org.junit.Test;

import com.ausy.states.StateEnum;

/**
 * @author dmaldonado
 *
 */
public class TestStateEnum {

	
	@Test
	public void stateEnumTest() {
		
		Assert.assertTrue(StateEnum.values().length == 2);
	}
}
