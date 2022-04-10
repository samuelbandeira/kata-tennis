import java.util.Random;

public class Main {
	public static void main(String[] args) {
		Game game = new Game();
		Thread[] players = new Thread[2];
		Random radom = new Random();
		final int bound = 1000;
		players[0] = new Thread(() -> {
			while (game.isActive()) {
				try {
					Thread.sleep(radom.nextInt(bound));
					game.player1Scores();
					System.out.println(game.getScore());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		players[1] = new Thread(() -> {
			while (game.isActive()) {
				try {
					Thread.sleep(radom.nextInt(bound));
					game.player2Scores();
					System.out.println(game.getScore());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		players[0].start();
		players[1].start();
	}
}
