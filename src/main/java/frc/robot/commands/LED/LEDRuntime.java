package frc.robot.commands.LED;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.CargoIntake;
import frc.robot.subsystems.LED;

/**
 * Sets the color according to various factors, such as if the robot was
 * intaking, climbing, etc.
 * 
 * @author Zayd A. Jack E.
 */
public class LEDRuntime extends Command {

	public LEDRuntime() {
		requires(Robot.LED);
	}

	protected void initialize() {

	}

	protected void execute() {
		if (DriverStation.getInstance().isAutonomous()) {
			LED.setColor(LED.off);
		} else if (DriverStation.getInstance().isOperatorControl()) {
			
			LED.setColor(LED.red);
		}
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
		this.end();
	}
}
