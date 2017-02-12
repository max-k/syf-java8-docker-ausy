package com.ausy.pages.panel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.ausy.dto.IPlayer;
import com.ausy.option.RpsMoveEnum;
import com.googlecode.wicket.jquery.ui.form.dropdown.DropDownChoice;

/**
 * This is the user Panel, this panel show the move selected by the human user
 * 
 * @author dmaldonado
 *
 */
public class UserPanel extends GenericPanel<IPlayer> {

	private static final long serialVersionUID = -6231146709088756143L;

	private DropDownChoice<String> dropdown;

	private TextField<String> userName;

	public UserPanel(String id, IModel<IPlayer> model) {
		super(id, model);

		this.userName = new TextField<String>("userName", new PropertyModel<String>(model.getObject(), "name"));
		add(this.userName.setOutputMarkupId(true));

		final List<String> moves = new ArrayList<>();

		Arrays.asList(RpsMoveEnum.values()).forEach(move -> moves.add(move.name()));
		this.dropdown = new DropDownChoice<String>("selectMove", new Model<String>(), moves);
		add(dropdown.setOutputMarkupId(true));
	}

	/**
	 * @return the dropdown
	 */
	public DropDownChoice<String> getDropdown() {
		return dropdown;
	}

	/**
	 * @param dropdown
	 *            the dropdown to set
	 */
	public void setDropdown(DropDownChoice<String> dropdown) {
		this.dropdown = dropdown;
	}

	/**
	 * @return the userName
	 */
	public TextField<String> getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(TextField<String> userName) {
		this.userName = userName;
	}

}
