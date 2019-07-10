package me.kavin.fastticket.botlist;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class BotListPoster {

	private ObjectArrayList<Runnable> postTasks = new ObjectArrayList<>();

	public BotListPoster(ObjectArrayList<Runnable> postTasks) {
		this.postTasks = postTasks;
	}

	public void initialize() {
		setupTimer();
	}

	private void setupTimer() {
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				postTasks.forEach(task -> {
					task.run();
				});
			}
		}, 0, TimeUnit.MINUTES.toMillis(5));
	}

}
