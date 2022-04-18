import java.util.concurrent.Flow;

public class GameSubscription implements Flow.Subscription {
    private Player publisher;
    private Game subscriber;

    public GameSubscription(Player publisher, Game subscriber) {
        this.publisher = publisher;
        this.subscriber = subscriber;
    }

    public Game getSubscriber() {
        return subscriber;
    }

    @Override
    public void request(long n) {
        if (n < 0) {
            throw new IllegalStateException();
        }
        subscriber.onNext(publisher);
    }

    @Override
    public void cancel() {

    }
}
