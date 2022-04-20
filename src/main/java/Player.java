import java.util.concurrent.Flow;

public class Player implements Flow.Publisher<Player>, Runnable {

    private String name;
    private GameSubscription subscription;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Player> subscriber) {
        subscription = new GameSubscription(this, (Game) subscriber);
        subscriber.onSubscribe(subscription);
    }

    @Override
    public void run() {
        startPlay();
    }

    public void startPlay() {
        subscription.request(1);
    }
}
