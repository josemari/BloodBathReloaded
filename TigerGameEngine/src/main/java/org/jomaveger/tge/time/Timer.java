package org.jomaveger.tge.time;

public final class Timer {

    private long lastLoopTime;
    private long startTime;
    private long currTime;

    public void init() {
        lastLoopTime = getTime();
        startTime = lastLoopTime;
        currTime = startTime;
    }

    public long getTime() {
        return System.currentTimeMillis();
    }

    public long getElapsedTime() {
        long time = getTime();
        long elapsedTime = time - currTime;
        currTime += elapsedTime;
        lastLoopTime = time;
        return elapsedTime;
    }

    public long getLastLoopTime() {
        return lastLoopTime;
    }
}
