import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameSubscription implements Flow.Subscription {
    private Player publisher;
    private Game subscriber;
    private AtomicBoolean terminated = new AtomicBoolean(false);

    public GameSubscription(Player publisher, Game subscriber) {
        this.publisher = publisher;
        this.subscriber = subscriber;
    }

    public Player getPublisher() {
        return publisher;
    }

    @Override
    public void request(long n) {
        if (n <= 0) {
            subscriber.onError(new IllegalArgumentException());
        }

        while (!Game.Status.FINISHED.equals(subscriber.getStatus()) && !terminated.get()) {
            subscriber.onNext(publisher);
        }

        terminated.set(true);
        subscriber.onComplete();
    }

    @Override
    public void cancel() {
        terminated.set(true);
    }
}
