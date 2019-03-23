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
	
	public autoResetEncoders() {
		
	}

	protected void initialize() {
        Robot.Chassis.resetEncoders();
	}


	protected void execute() {

	}


	protected boolean isFinished() {
		return true;
	}

	protected void end() {
	}


	protected void interrupted() {
		this.end();
	}
	
}