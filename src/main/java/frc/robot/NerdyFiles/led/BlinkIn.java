package frc.robot.nerdyfiles.led;

import edu.wpi.first.wpilibj.Spark;
/**
 * Sets the color of Blinkin to 0 using sparks
 * @author Zayd
 */
public class Blinkin {
	private Spark blinkin;
	private double setColor = 0;
	public Blinkin(int pwm) {
		blinkin = new Spark(pwm);
		blinkin.setSafetyEnabled(false);
	}
	// The color is set to a value between -1 to 1
	public void setColor(double color) {
		if (color != setColor) {
			blinkin.set(color);
		}
	}
	//Blinkin is set to -1.02
	public void flow() {
		blinkin.set(-1.02);
	}
}