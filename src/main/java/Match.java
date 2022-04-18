import java.util.ArrayList;
import java.util.List;

public class Match {

    private List<Player> playerList = new ArrayList<>();
    private volatile Match.Status status;
    private Game game;

    public Match() {
        this.status = Status.NOT_STARTED;
    }

    public Match(String p1, String p2) {
        playerList.add(new Player(p1));
        playerList.add(new Player(p2));
    }

    public void start() {
        if (!isReadyToStart()) {
            throw new IllegalStateException();
        }
        game = new Game(playerList.get(0), playerList.get(1));
        game.start();
        status = Status.STARTED;
    }

    public Match.Status getStatus() {
        if (game != null) {
            if (Game.Status.FINISHED.equals(game.getStatus())) {
                status = Status.FINISHED;
            }
        }
        return status;
    }

    public void addPlayer(Player player) {
        if (isReadyToStart()) {
            throw new IllegalStateException();
        }
        playerList.add(player);
    }

    private boolean isReadyToStart() {
        return playerList.size() == 2;
    }

    public List<Player> getPlayers() {
        return this.playerList;
    }

    public enum Status {
        NOT_STARTED, STARTED, FINISHED
    }
}
