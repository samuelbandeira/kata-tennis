import java.util.Random;

public class Game {

	private String player1;
	private String player2;
	private int player1Score;
	private int player2Score;

	public Game() {
		player1 = "Player 1";
		player2 = "Player 2";
	}

	public static Game createWithScore(int player1Score, int player2Score) {
		Game game = new Game();
		game.player1Score = player1Score;
		game.player2Score = player2Score;
		return game;
	}

	public String getName() {
		return player1.trim() + " vs " + player2.trim();
	}

	public void player1Scores() {
		if (isActive()) {
			player1Score++;
		}
	}

	public void player2Scores() {
		if (isActive()) {
			player2Score++;
		}
	}

	public boolean isActive() {
		return (player1Score <= 3 && player2Score <= 3) || Math.abs(player1Score - player2Score) <= 1;
	}

	public String getScore() {
		if (isDeuce()) {
			return "Deuce";
		}
		if (hasAdvantage()) {
			return getHighestScoredPlayer() + " Advantage";
		}
		if (hasWinner()) {
			return "Game " + getHighestScoredPlayer();
		}

		return convertScore(player1Score) + "," + convertScore(player2Score);
	}

	private boolean hasWinner() {
		return isTieBreak() && !hasAdvantage();
	}

	private boolean isTieBreak() {
		return player1Score > 3 || player2Score > 3;
	}

	private boolean hasAdvantage() {
		return isTieBreak() && Math.abs(player1Score - player2Score) <= 1;
	}

	private String getHighestScoredPlayer() {
		return (player1Score > player2Score) ? player1 : player2;
	}

	private boolean isDeuce() {
		return player1Score == player2Score && player1Score >= 3;
	}

	private String convertScore(int score) {
		switch (score) {
			case 0:
				return "Love";
			case 1:
				return "15";
			case 2:
				return "30";
			case 3:
				return "40";
		}
		return "";
	}
}
