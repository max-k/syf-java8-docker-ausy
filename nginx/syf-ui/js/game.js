function resetName() {
	delete window.NAME;
	while (!window.NAME || window.NAME.length == 0) {
		window.NAME = prompt('Enter your name');
		if (window.NAME) {
			window.NAME = window.NAME.trim();
		}
	}
	$('#your-name').html(window.NAME);
	resetGame();
	resetStats();
}

/**
 * Called to reset my game
 */
function resetGame() {
	function placeCardAtPosition(cardName, left, onDone) {
		$('#my-cards .card-' + cardName.toLowerCase()).animate({
			opacity: 1,
			left: left,
		}, 500, onDone || function() {});
	}

	hideOpponentCard();

	placeCardAtPosition('rock', 20, function() { $(this).removeClass('selected').show(); });
	placeCardAtPosition('paper', 200, function() { $(this).removeClass('selected').show(); });
	placeCardAtPosition('scissor', 375, function() { $(this).removeClass('selected').show(); });

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
			/*http://localhost:8180/rps-ws/rps/playRps/Nelson/PAPER*/
			url: '/api/rps-ws/rps/playRps/'+window.NAME+'/' + cardName,
			data: {},
			success: function(response) {
				console.log('response', response);
				showOpponentCard(response.opponentName, response.moveOpponent);
				showResultBox(response.result);
				resetStats();
				setTimeout(hideResultBox, 1500);
			},
			complete: function() {
				setTimeout(resetGame, 2000);
			}
		});
	});
}

function resetStats() {
	$.ajax({
		dataType: "json",
		/*http://localhost:8180/rps-ws/rps/numberOfWins/Darlys*/
		url: '/api/rps-ws/rps/numberOfWins/'+window.NAME,
		data: {},
		success: function(response) {
			console.log('response', response);
			$('#stats-counter').html(response.numberOfWins + (response.numberOfWins > 0 ? ' victoires' : 'victoire'));
		}
	});
}

function hideOpponentCard() {
	// Hide opponent card
	$('#opponent-card .card')
		.removeClass('card-rock')
		.removeClass('card-paper')
		.removeClass('card-scissor')
		.css('opacity', 0);
}

function showOpponentCard(opponentName, cardName) {
	$('#opponent-card .card')
		.addClass('card-' + cardName.toLowerCase())
		.animate({
				opacity: 1,
			}, 500, function() {});
}

function showResultBox(result) {
	$('#result-overlay').fadeIn();
	$('#result').fadeIn().html('You ' + result);
}
function hideResultBox() {
	$('#result-overlay, #result').fadeOut();
}