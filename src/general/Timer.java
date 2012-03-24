package general;

public class Timer {
	protected long time = 0;
	protected long start = 0;

	public Timer() {}

	// start the timer
	public void start() {
		start = System.currentTimeMillis();
	}

	// stop the timer
	public void stop() {
		time = time + System.currentTimeMillis() - start;
	}

	// reset the timer
	public void reset() {
		time = 0;
	}

	// print the current time in ms
	public long getTime() {
		return time;
	}

}
