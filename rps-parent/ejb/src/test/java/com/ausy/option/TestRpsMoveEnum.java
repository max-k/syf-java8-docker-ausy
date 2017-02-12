
package com.ausy.option;

import org.junit.Assert;
import org.junit.Test;

import com.ausy.option.RpsMoveEnum;

/**
 * @author dmaldonado
 *
 */
public class TestRpsMoveEnum {

	@Test
	public void testRpsMoveEnum() {

		Assert.assertTrue(RpsMoveEnum.values().length == 3);

		Assert.assertFalse(RpsMoveEnum.PAPER.breakBy(RpsMoveEnum.PAPER));
		Assert.assertFalse(RpsMoveEnum.PAPER.breakBy(RpsMoveEnum.ROCK));
		Assert.assertTrue(RpsMoveEnum.PAPER.breakBy(RpsMoveEnum.SCISSOR));
		
		Assert.assertTrue(RpsMoveEnum.ROCK.breakBy(RpsMoveEnum.PAPER));
		Assert.assertFalse(RpsMoveEnum.ROCK.breakBy(RpsMoveEnum.ROCK));
		Assert.assertFalse(RpsMoveEnum.ROCK.breakBy(RpsMoveEnum.SCISSOR));
		
		Assert.assertFalse(RpsMoveEnum.SCISSOR.breakBy(RpsMoveEnum.PAPER));
		Assert.assertTrue(RpsMoveEnum.SCISSOR.breakBy(RpsMoveEnum.ROCK));
		Assert.assertFalse(RpsMoveEnum.SCISSOR.breakBy(RpsMoveEnum.SCISSOR));
	}

}
