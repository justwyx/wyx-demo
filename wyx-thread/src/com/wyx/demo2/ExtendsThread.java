package com.wyx.demo2;


public class ExtendsThread extends Thread{

	public ExtendsThread(String name) {
		super(name);
	}

	@Override
	public void run() {
		while (!interrupted()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName());
		}


	}





	public static void main(String[] args) {
		ExtendsThread t1 = new ExtendsThread("t1");
		ExtendsThread t2 = new ExtendsThread("t2");
//		t1.setDaemon(true);
//		t2.setDaemon(true);

		t1.start();
		t2.start();

		t1.interrupt();
		t2.interrupt();

	}
}



















