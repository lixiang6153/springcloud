package com.donwait.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
/**
 * 全局线程池工具
 * @author Administrator
 *
 */
public class PoolHelper {
	private static ExecutorService pool;
	static{
		/*pool = Executors.newWorkStealingPool();*/
		int count = Runtime.getRuntime().availableProcessors();
		pool = Executors.newFixedThreadPool(count >= 4 ? count : 4 );
	}
	/**
	 * 线程池执行任务
	 * @param runnable 任务内容
	 */
	public static void execute(Runnable runnable){
		pool.execute(runnable);
	}
	/**
	 * 线程池执行任务,并将结果返回
	 * @param task 任务内容
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws TimeoutException
	 */
	public static Object submit(Callable<?> task) throws InterruptedException, ExecutionException, TimeoutException{
		Future<?> future = pool.submit(task);
		return future.get(5000, TimeUnit.MILLISECONDS);
	}
	
}
