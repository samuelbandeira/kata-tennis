import com.acidmanic.consoletools.drawing.AsciiBorders;
import com.acidmanic.consoletools.table.Table;
import com.acidmanic.consoletools.table.builders.TableBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicInteger;

public class Game implements Flow.Subscriber<Player> {

    private Player player1;
    private Player player2;
    private AtomicInteger player1Score = new AtomicInteger(0);
    private AtomicInteger player2Score = new AtomicInteger(0);
    private Status status = Status.NOT_STARTED;
    private ExecutorService executor = Executors.newCachedThreadPool();

    public Game() {
        this("Player 1", "Player 2");
    }

    public Game(String player1, String player2) {
        this(new Player(player1), new Player(player2));
    }

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public static Game createWithScore(int player1Score, int player2Score) {
        Game game = new Game();
        game.defineScore(player1Score, player2Score);
        return game;
    }

    public static Game createWithScore(String player1, String player2, int player1Score, int player2Score) {
        Game game = new Game(player1, player2);
        game.defineScore(player1Score, player2Score);
        return game;
    }

    public String getName() {
        return player1.getName()
                       .trim() + " vs " + player2.getName()
                       .trim();
    }

    public void player1Scores() {
        if (isActive()) {
            player1Score.incrementAndGet();
            System.out.println(getScore());
        } else {
            status = Status.FINISHED;
        }
    }

    public void player2Scores() {
        if (isActive()) {
            player2Score.incrementAndGet();
            System.out.println(getScore());
        } else {
            status = Status.FINISHED;
        }
    }

    private void defineScore(int player1Score, int player2Score) {
        this.status = Status.STARTED;
        this.player1Score = new AtomicInteger(player1Score);
        this.player2Score = new AtomicInteger(player2Score);
        if (hasWinner()) {
            status = Status.FINISHED;
        }
    }

    public boolean isActive() {
        return (player1Score.get() <= 3 && player2Score.get() <= 3) || Math.abs(player1Score.get() - player2Score.get()) <= 1;
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

        return convertScore(player1Score.get()) + "," + convertScore(player2Score.get());
    }

    private boolean hasWinner() {
        return isTieBreak() && !hasAdvantage();
    }

    private boolean isTieBreak() {
        return player1Score.get() > 3 || player2Score.get() > 3;
    }

    private boolean hasAdvantage() {
        return isTieBreak() && Math.abs(player1Score.get() - player2Score.get()) <= 1;
    }

    private String getHighestScoredPlayer() {
        return (player1Score.get() > player2Score.get()) ? player1.getName() : player2.getName();
    }

    private boolean isDeuce() {
        return player1Score.get() == player2Score.get() && player1Score.get() >= 3;
    }

    private String convertScore(int score) {
        switch (score) {
            case 0:
                return "Love";
            case 1:
                return "15";
            case 2:
                return "30";
            default:
                return "40";
        }
    }

    public String getDisplayScore() {
        Table table = new TableBuilder().cell(
                        (TableBuilder builder)                                    /* add another cell to third row with a table as its content. */ -> builder.table(
                                        new String[][] {{player1.getName()},
                                                        {player2.getName()}}) /* this inner table will have three rows, two cell each row corresponding to given 2d array*/.padAll(
                                        1, 1)
                                .tableBorder(AsciiBorders.BOLD))
                .border()                        /* set default border for the cell */.cell(
                        (TableBuilder builder) -> builder     /* add new cell (beside last one), building a table as cell's content */.row()
                                .cell(convertScore(
                                        player1Score.get()))        /* create first row, then two cells for it with values 00 and 01 */.row()
                                .cell(convertScore(
                                        player2Score.get()))        /* create second row, then two cells for it with values 10 and 11 */.borderAll()
                                .tableBorder())         /* set default border for all cells, then set default border for table*/.build();                       /* build main table*/

        return table.render();
    }

    public void start() {
        status = Status.STARTED;
        System.out.println(getScore());
        player1.subscribe(this);
        player2.subscribe(this);
    }

    public Status getStatus() {
        if (executor.isTerminated()) {
            status = Status.FINISHED;
        }
        return status;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        executor.submit(((GameSubscription) subscription).getPublisher());
    }

    @Override
    public void onNext(Player player) {
        if (player1.equals(player)) {
            player1Scores();
        } else {
            player2Scores();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("error");
    }

    @Override
    public void onComplete() {
        System.out.println("Final: " + getScore());
    }

    public enum Status {
        NOT_STARTED, STARTED, FINISHED
    }
}
