package com.ausy.syf.rps.ws.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SYF_USER")
public class User implements Serializable {

	@Id
	private String name;

	private int win;

	private int lost;

	private int draw;

	private int currentWinInARaw;

	private int maxWinInARaw;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLost() {
		return lost;
	}

	public void setLost(int lost) {
		this.lost = lost;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getCurrentWinInARaw() {
		return currentWinInARaw;
	}

	public void setCurrentWinInARaw(int currentWinInARaw) {
		this.currentWinInARaw = currentWinInARaw;
	}

	public int getMaxWinInARaw() {
		return maxWinInARaw;
	}

	public void setMaxWinInARaw(int maxWinInARaw) {
		this.maxWinInARaw = maxWinInARaw;
	}
}
