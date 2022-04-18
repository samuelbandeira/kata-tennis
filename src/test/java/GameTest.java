import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	void givenPlayersNames_WhenGetName_ThenReturnPlayersNames() {
        game = new Game("Rafael Nadal", "Roger Federer");
		Assertions.assertEquals("Rafael Nadal vs Roger Federer", game.getName());
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

    @Test
    void givenGameScore_whenDisplayScore_thenDisplayFancyScore() {
        game = Game.createWithScore("Rafael Nadal", "Roger Federer", 1, 4);
        Assertions.assertEquals("┌───────────────┐┌────┐\n" + "│               ││┌──┐│\n" + "│ Rafael Nadal  │││15││\n"
                                + "│               ││└──┘│\n" + "│               ││┌──┐│\n"
                                + "│ Roger Federer │││40││\n" + "│               ││└──┘│\n"
                                + "└───────────────┘└────┘\n", game.getDisplayScore());
    }

    @Test
    void givenGameNotStarted_WhenGetStatus_ThenNotStarted() {
        game = new Game();
        Assertions.assertEquals(Game.Status.NOT_STARTED, game.getStatus());

    }

    @Test
    void givenGameStarted_WhenGetStatus_ThenStarted() {
        game = Game.createWithScore(3, 1);
        Assertions.assertEquals(Game.Status.STARTED, game.getStatus());
    }

    @Test
    void givenGameFinished_WhenGetStatus_ThenFinished() {
        game = Game.createWithScore(4, 1);
        Assertions.assertEquals(Game.Status.FINISHED, game.getStatus());
    }

}