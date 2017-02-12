package com.ausy.pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.ausy.dto.IPlayer;
import com.ausy.dto.Player;
import com.ausy.dto.PlayerTypeEnum;
import com.ausy.dto.RpsState;
import com.ausy.dto.RpsResultDto;
import com.ausy.facade.IStatefulRockPaperScissorFacade;
import com.ausy.option.RpsMoveEnum;
import com.ausy.pages.panel.UserPanel;

/**
 * This page is the main web component to play RPS.
 * 
 * @author dmaldonado
 *
 */
public class RpsGamePage extends WebPage {

	private static final long serialVersionUID = -4493311894240794000L;

	/**
	 * Rock Paper Scissor Form.
	 */
	private Form<Void> rpsForm;

	/**
	 * Reset Rock Paper Scissor Form.
	 */
	private Form<Void> resetForm;

	/**
	 * Player one (who is found to left)
	 */
	private IPlayer playerOne = new Player();

	/**
	 * Player two (who is found to right)
	 */
	private IPlayer playerTwo = new Player();

	/**
	 * Represents the computer move
	 */
	private String computerMove;

	/**
	 * This panel shows message to guide to the human user
	 */
	private FeedbackPanel feedbackPanel;

	/**
	 * User pane;, this panel contains the drop down with all possible moves for
	 * the player one
	 */
	private UserPanel userPanel;

	@Inject
	IStatefulRockPaperScissorFacade iRockPaperScissorFacade;

	public RpsGamePage(final PageParameters parameters) {
		super(parameters);

		this.playerOne = new Player();
		this.playerOne.setPlayerType(PlayerTypeEnum.HUMAN);

		this.playerTwo = new Player();
		this.playerTwo.setName(PlayerTypeEnum.COMPUTER.name());
		this.playerTwo.setPlayerType(PlayerTypeEnum.COMPUTER);

		this.feedbackPanel = new FeedbackPanel("feedback");
		add(feedbackPanel);
	}

	/**
	 * @see org.apache.wicket.Component#onConfigure()
	 */
	@Override
	protected void onConfigure() {
		super.onConfigure();

		this.userPanel = new UserPanel("userPanel", Model.of(playerOne));
		userPanel.setOutputMarkupId(true);

		rpsForm = newRpsForm();

		addOrReplace(rpsForm);
		rpsForm.addOrReplace(newRpsResultRefreshingView("results"));
		rpsForm.addOrReplace(this.userPanel);
		rpsForm.addOrReplace(new Label("computerMove", this.computerMove));

		this.resetForm = newResetForm();
		addOrReplace(resetForm);
	}

	/**
	 * This form allows to reset the RPS Game
	 */
	private Form<Void> newResetForm() {
		return new Form<Void>("resetForm") {

			private static final long serialVersionUID = 1166052532405020635L;

			@Override
			protected void onSubmit() {

				iRockPaperScissorFacade.cleanGame(playerOne, playerTwo);

			}
		};
	}

	/**
	 * Creates a form which corresponds to the game
	 */
	private Form<Void> newRpsForm() {
		return new Form<Void>("form") {

			private static final long serialVersionUID = 2161250580288986699L;

			@Override
			protected void onSubmit() {

				String move = userPanel.getDropdown().getModelObject();
				String userName = userPanel.getUserName().getModelObject();

				if (null == move) {

					feedbackPanel.info("Please select one move for you!!!");
					return;
				}

				if (null == userName || userName.isEmpty()) {

					feedbackPanel.info("Please write your name");
					return;
				}

				initializeUser(move, userName, PlayerTypeEnum.HUMAN, playerOne);

				iRockPaperScissorFacade.play(playerOne, playerTwo);
				RpsGamePage.this.computerMove = String.format("The computer has selected %s",
						playerTwo.getMove().name());

			}

			private void initializeUser(String move, String userName, PlayerTypeEnum userType, IPlayer player) {
				player.setMove(RpsMoveEnum.getEnum(move));
				player.setName(userName);
				player.setPlayerType(userType);
				player.setRpsState(new RpsState());
			}
		};
	}

	/**
	 * @return This view is called when a request is finished to show the result
	 *         list
	 */
	private RefreshingView<RpsResultDto> newRpsResultRefreshingView(final String wicketId) {
		return new RefreshingView<RpsResultDto>(wicketId) {

			private static final long serialVersionUID = 1L;

			@Override
			protected Iterator<IModel<RpsResultDto>> getItemModels() {
				List<IModel<RpsResultDto>> models = new ArrayList<>();
				
				// Change To Java 8
				for (RpsResultDto rpsResultDto : iRockPaperScissorFacade.getAllRpsResultsOfCurrentGame()) {
					models.add(Model.of(rpsResultDto));
				}
				return models.iterator();
			}

			@Override
			protected void populateItem(final Item<RpsResultDto> item) {
				RpsResultDto result = item.getModelObject();
				item.add(new Label("playerOne", result.getPlayerOne().getName()));
				item.add(new Label("playerOneMove", result.getPlayerOne().getMove().name()));
				item.add(new Label("playerOneResult", result.getPlayerOne().getRpsState().getState().name()));
				item.add(new Label("playerTwo", result.getPlayerTwo().getName()));
				item.add(new Label("playerTwoMove", result.getPlayerTwo().getMove().name()));
				item.add(new Label("playerTwoResult", result.getPlayerTwo().getRpsState().getState().name()));
			}
		};
	}

	/**
	 * @return the playerOne
	 */
	public IPlayer getPlayerOne() {
		return playerOne;
	}

	/**
	 * @param playerOne
	 *            the playerOne to set
	 */
	public void setPlayerOne(IPlayer playerOne) {
		this.playerOne = playerOne;
	}

	/**
	 * @return the playerTwo
	 */
	public IPlayer getPlayerTwo() {
		return playerTwo;
	}

	/**
	 * @param playerTwo
	 *            the playerTwo to set
	 */
	public void setPlayerTwo(IPlayer playerTwo) {
		this.playerTwo = playerTwo;
	}

	/**
	 * @return the rpsForm
	 */
	public Form<Void> getRpsForm() {
		return rpsForm;
	}

	/**
	 * @param rpsForm
	 *            the rpsForm to set
	 */
	public void setRpsForm(Form<Void> rpsForm) {
		this.rpsForm = rpsForm;
	}

	/**
	 * @return the resetForm
	 */
	public Form<Void> getResetForm() {
		return resetForm;
	}

	/**
	 * @param resetForm
	 *            the resetForm to set
	 */
	public void setResetForm(Form<Void> resetForm) {
		this.resetForm = resetForm;
	}

	/**
	 * @return the computerMove
	 */
	public String getComputerMove() {
		return computerMove;
	}

	/**
	 * @param computerMove
	 *            the computerMove to set
	 */
	public void setComputerMove(String computerMove) {
		this.computerMove = computerMove;
	}

	/**
	 * @return the feedbackPanel
	 */
	public FeedbackPanel getFeedbackPanel() {
		return feedbackPanel;
	}

	/**
	 * @param feedbackPanel
	 *            the feedbackPanel to set
	 */
	public void setFeedbackPanel(FeedbackPanel feedbackPanel) {
		this.feedbackPanel = feedbackPanel;
	}

	/**
	 * @return the userPanel
	 */
	public UserPanel getUserPanel() {
		return userPanel;
	}

	/**
	 * @param userPanel
	 *            the userPanel to set
	 */
	public void setUserPanel(UserPanel userPanel) {
		this.userPanel = userPanel;
	}

	/**
	 * @return the iRockPaperScissorFacade
	 */
	public IStatefulRockPaperScissorFacade getiRockPaperScissorFacade() {
		return iRockPaperScissorFacade;
	}

	/**
	 * @param iRockPaperScissorFacade
	 *            the iRockPaperScissorFacade to set
	 */
	public void setiRockPaperScissorFacade(IStatefulRockPaperScissorFacade iRockPaperScissorFacade) {
		this.iRockPaperScissorFacade = iRockPaperScissorFacade;
	}

}
