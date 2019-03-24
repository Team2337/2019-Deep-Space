package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Resets the chassis encoders
 * 
 * @category AUTO
 * @author Bryce G.
 */
public class autoResetEncoders extends Command {
	
	protected void initialize() {
        Robot.Chassis.resetEncoders();
	}

	protected boolean isFinished() {
		return true;
	}
	
	protected void interrupted() {
		this.end();
	}
	
}