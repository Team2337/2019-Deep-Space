package frc.robot.subsystems;

import frc.robot.commands.*;
import frc.robot.commands.LED.LEDRuntime;
import frc.robot.nerdyfiles.led.*;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * Where all the LEDs are controlled
 * @author Zayd A.
 */

 public class LED extends Subsystem {
	 public Blinkin blinkin;

	public LED(){
		blinkin = new Blinkin(60);

	}
	public void initDefaultCommand() {
		setDefaultCommand(new LEDRuntime());
	}
}