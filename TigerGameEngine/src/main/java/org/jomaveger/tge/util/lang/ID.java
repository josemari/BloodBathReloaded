package org.jomaveger.tge.util.lang;

import java.util.concurrent.atomic.AtomicLong;

public final class ID {

	private static AtomicLong idCounter = new AtomicLong();
	
	public ID() {
	}

	public static String createID() {
	    return String.valueOf(idCounter.getAndIncrement());
	}
}
