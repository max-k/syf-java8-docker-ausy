package com.ausy.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author dmaldonado
 *
 */
@XmlRootElement(namespace = "resultCounter")
public class ResultCounter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userName;

	private Long numberOfWins;

	/**
	 * @return the userName
	 */
	@XmlElement(required = true, nillable = false)
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the numberOfWins
	 */
	@XmlElement(required = true, nillable = false)
	public Long getNumberOfWins() {
		return numberOfWins;
	}

	/**
	 * @param numberOfWins
	 *            the numberOfWins to set
	 */
	public void setNumberOfWins(Long numberOfWins) {
		this.numberOfWins = numberOfWins;
	}

}
