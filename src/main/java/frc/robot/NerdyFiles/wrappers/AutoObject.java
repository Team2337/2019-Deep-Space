package frc.robot.nerdyfiles.wrappers;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;

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