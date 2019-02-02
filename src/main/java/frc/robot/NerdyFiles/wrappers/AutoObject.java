package frc.robot.nerdyfiles.wrappers;

import java.time.Duration;
import java.time.LocalDateTime;
/**
 * Taking local time and date and setting start and end time
 * @author Zayd
 */

public class AutoObject {
	public String commandName;
	public String color;
	public LocalDateTime startTime;
	public LocalDateTime endTime;
	public AutoObject(String commandName, String color) {
		this.commandName = commandName;
		this.color = color;
		startTime = LocalDateTime.now();
	}
	public void stopTime() {
		endTime = LocalDateTime.now();
	}
	public double getTime() {
		return Duration.between(startTime, endTime).getSeconds();
	}
}