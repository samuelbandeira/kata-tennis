import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchTest {

    private Match match;
    private Player p1 = new Player("Player 1");
    private Player p2 = new Player("Player 2");

    @BeforeEach
    void setUp() {
        match = new Match();
    }

    @Test
    void givenNoPlayers_WhenStart_ThenError() {
        Assertions.assertThrows(IllegalStateException.class, () -> match.start());
    }

    @Test
    void givenMatchNotStarted_WhenStatus_ThenNotStarted() {
        Assertions.assertEquals(Match.Status.NOT_STARTED, match.getStatus());
    }

    @Test
    void givenMatchStarted_WhenStatus_ThenStarted() {
        match.addPlayer(p1);
        match.addPlayer(p2);
        match.start();
        Assertions.assertEquals(Match.Status.STARTED, match.getStatus());
    }

    @Test
    void givenMatchFinished_WhenStatus_ThenFinished() {
        match.addPlayer(p1);
        match.addPlayer(p2);
        match.start();
        Assertions.assertEquals(Match.Status.STARTED, match.getStatus());
    }

    @Test
    void givenNoPlayers_WhenAddPlayer_ThenReturnPlayerOnList() {
        match.addPlayer(p1);
        Assertions.assertEquals(1L, match.getPlayers().size());
    }

    @Test
    void givenTwoPlayers_WhenAddPlayer_ThenReturnTwoPlayerOnList() {
        match.addPlayer(p1);
        Assertions.assertEquals(1L, match.getPlayers().size());
        match.addPlayer(p2);
        Assertions.assertEquals(2L, match.getPlayers().size());
    }

    @Test
    void givenThreePlayers_WhenAddPlayer_ThenReturnError() {
        match.addPlayer(p1);
        Assertions.assertEquals(1L, match.getPlayers().size());
        match.addPlayer(p2);
        Assertions.assertEquals(2L, match.getPlayers().size());
        Assertions.assertThrows(IllegalStateException.class, () -> match.addPlayer(p2));
    }

    @Test
    void givenPlayers_WhenStart_ThenSuccess() {
        match.addPlayer(p1);
        match.addPlayer(p2);
        match.start();
        Assertions.assertEquals(Match.Status.STARTED, match.getStatus());
    }

    @Test
    void givenPlayers_WhenStartWait_ThenFinished() {
        match.addPlayer(p1);
        match.addPlayer(p2);
        match.start();
        while (!Match.Status.FINISHED.equals(match.getStatus()));
        Assertions.assertEquals(Match.Status.FINISHED, match.getStatus());
    }

}