package me.kavin.fastticket.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Multithreading {

	private static ExecutorService es = Executors.newFixedThreadPool(128);

	public static void runAsync(Runnable runnable) {
		es.execute(runnable);
	}
}