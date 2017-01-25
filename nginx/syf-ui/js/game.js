/**
 * Called to reset my game
 */
function resetGame() {
	function placeCardAtPosition(cardName, left, onDone) {
		$('#my-cards .card-' + cardName).animate({
			opacity: 1,
			left: left,
		}, 500, onDone || function() {});
	}

	hideOpponentCard();

	placeCardAtPosition('rock', 20, function() { $(this).removeClass('selected').show(); });
	placeCardAtPosition('paper', 200, function() { $(this).removeClass('selected').show(); });
	placeCardAtPosition('scissors', 375, function() { $(this).removeClass('selected').show(); });

	// You can select only one card
	$('#my-cards').one('click', '.card', function() {
		// on card selected
		var $selectedCard = $(this);
		$selectedCard.addClass('selected');
		var cardName = $selectedCard.data('name');

		// center the selected card
		placeCardAtPosition(cardName, 210, function() {
				// hide unselected cards
				$('#my-cards .card').filter(function() {
					return this != $selectedCard.get(0);
				}).animate({
						opacity: 0,
					}, 500, function() {
						$(this).hide();
					});
		});

		$.ajax({
			dataType: "json",
			url: '/api/play',
			data: {'card':cardName,'userName':'Nelson'},
			success: function(response) {
				console.log('response', response);
				showOpponentCard(response.uicard);
				setTimeout(resetGame, 2000);
			}
		});

/*		setTimeout(function() {
			// show opponent card
			var cardName = ['rock', 'paper', 'scissors'][Math.floor(Math.random()*3)];
			showOpponentCard(cardName);
		}, 1000);*/
	});
}

function hideOpponentCard() {
	// Hide opponent card
	$('#opponent-card .card')
		.removeClass('card-rock')
		.removeClass('card-paper')
		.removeClass('card-scissors')
		.css('opacity', 0);
}

function showOpponentCard(cardName) {
	$('#opponent-card .card')
		.addClass('card-' + cardName)
		.animate({
				opacity: 1,
			}, 500, function() {});
}