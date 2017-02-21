package com.ausy.syf.rps.ws.model;

import java.util.function.Function;

/**
 * Created by Nelson Levert on 2/20/17.
 */
public enum ResultEnum {
	WIN((user) -> {
		user.setWin(user.getWin() + 1);
		user.setCurrentWinInARaw(user.getCurrentWinInARaw() + 1);
		if (user.getCurrentWinInARaw() > user.getMaxWinInARaw()) {
			user.setMaxWinInARaw(user.getCurrentWinInARaw());
		}
		return null;
	}),
	LOST((user) -> {
		user.setLost(user.getLost() + 1);
		user.setCurrentWinInARaw(0);
		return null;
	}),
	DRAW((user) -> {
		user.setDraw(user.getDraw() + 1);
		user.setCurrentWinInARaw(0);
		return null;
	});

	private final Function<User, Void> f;

	ResultEnum(Function<User, Void> f) {
		this.f = f;
	}

	public ResultEnum update(User user) {
		f.apply(user);
		return this;
	}
}
