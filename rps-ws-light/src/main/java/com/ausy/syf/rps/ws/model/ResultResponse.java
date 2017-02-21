package com.ausy.syf.rps.ws.model;

/**
 * Created by Nelson Levert on 2/20/17.
 */
public class ResultResponse {
	private User user;
	private CardEnum opponentCardName;
	private ResultEnum result;

	public ResultResponse(User user, CardEnum opponentCardName, ResultEnum result) {
		this.user = user;
		this.opponentCardName = opponentCardName;
		this.result = result;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CardEnum getOpponentCardName() {
		return opponentCardName;
	}

	public void setOpponentCardName(CardEnum opponentCardName) {
		this.opponentCardName = opponentCardName;
	}

	public ResultEnum getResult() {
		return result;
	}

	public void setResult(ResultEnum result) {
		this.result = result;
	}
}
