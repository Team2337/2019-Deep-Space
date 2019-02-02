package frc.robot.nerdyfiles.led;

import edu.wpi.first.wpilibj.Spark;

public class BlinkIn {
	private Spark blinkin;
	private double setColor = 0;
	public BlinkIn(int pwm) {
		blinkin = new Spark(pwm);
		blinkin.setSafetyEnabled(false);
	}
	public void setColor(double color) {
		if (color != setColor) {
			blinkin.set(color);
		}
	}
	public void flow() {
		blinkin.set(-1.02);
	}
}