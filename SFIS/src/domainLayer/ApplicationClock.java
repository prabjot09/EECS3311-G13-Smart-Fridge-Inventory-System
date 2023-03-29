package domainLayer;

import java.time.LocalDate;

public class ApplicationClock {
	
	private static ApplicationClock clock;
	
	private LocalDate real;
	private LocalDate simulated;
	
	
	public static void initRealClock() {
		clock = new ApplicationClock();
		clock.real = LocalDate.now();
		clock.simulated = null;
	}
	
	public static void initSimulatedClock() {
		clock = new ApplicationClock();
		clock.real = null;
		clock.simulated = LocalDate.now();
	}
	
	public static LocalDate getDate() {
		if (clock.real != null) {
			return LocalDate.now();
		}
		
		return clock.simulated;
	}
	
	
	public static void incrementClock() {
		if (clock.real != null)
			return;
		
		clock.simulated = clock.simulated.plusDays(1);
	}
}
