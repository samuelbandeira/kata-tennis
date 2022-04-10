import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;
	@BeforeEach
	void setup() {
		game = new Game();
	}

	@Test
	void createGame() {
		Assertions.assertEquals("Player 1 vs Player 2", game.getName());
	}

	@Test
	void givenPlayer1ScoresThenScore() {
		game.player1Scores();
		Assertions.assertEquals("15,Love", game.getScore());
	}

	@Test
	void givenPlayer2ScoresThenScore() {
		game.player2Scores();
		Assertions.assertEquals("Love,15", game.getScore());
	}

	@Test
	void givenScoreIsDeuceThenScoreReturnDeuce() {
		game = Game.createWithScore(3, 3);
		Assertions.assertEquals("Deuce", game.getScore());
	}

	@Test
	void givenScoreIs4And4ThenScoreReturnDeuce() {
		game = Game.createWithScore(4, 4);
		Assertions.assertEquals("Deuce", game.getScore());
	}

	@Test
	void givenScoreIsDeuceWhenPlayer1ScoresThenScoreReturnPlayer1Advantage() {
		game = Game.createWithScore(3, 3);
		game.player1Scores();
		Assertions.assertEquals("Player 1 Advantage", game.getScore());
	}

	@Test
	void givenScoreIsDeuceWhenPlayer2ScoresThenScoreReturnPlayer2Advantage() {
		game = Game.createWithScore(3, 3);
		game.player2Scores();
		Assertions.assertEquals("Player 2 Advantage", game.getScore());
	}

	@Test
	void givenScoreIsDeuceWhenP1ScoresAndDeuceThenScoreReturnPlayer2Advantage() {
		game = Game.createWithScore(3, 3);
		game.player1Scores();
		Assertions.assertEquals("Player 1 Advantage", game.getScore());
		game.player2Scores();
		Assertions.assertEquals("Deuce", game.getScore());
	}

	@Test
	void givenScoreIs3And1WhenPlayer1ScoresThenScoreReturnPlayer1Win() {
		game = Game.createWithScore(3, 1);
		game.player1Scores();
		Assertions.assertEquals("Game Player 1", game.getScore());
	}

	@Test
	void givenScoreIs1And3WhenPlayer2ScoresThenScoreReturnPlayer2Win() {
		game = Game.createWithScore(1, 3);
		game.player2Scores();
		Assertions.assertEquals("Game Player 2", game.getScore());
	}

	@Test
	void givenPlayerScoresAfterGameEndThenCanNotChangeGameScore() {
		game = Game.createWithScore(1, 3);
		game.player2Scores();
		game.player1Scores();
		game.player1Scores();
		game.player1Scores();
		game.player1Scores();
		game.player1Scores();
		Assertions.assertEquals("Game Player 2", game.getScore());
	}

}