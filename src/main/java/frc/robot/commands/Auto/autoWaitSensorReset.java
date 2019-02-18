package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Waits a given amount of time until the next command is run
 * 
 * @category AUTO
 * @author Bryce G.
 */
public class autoWaitSensorReset extends Command {
	public double timeout;
	public autoWaitSensorReset(double timeout) {
		
	}

	protected void initialize() {
        Robot.Chassis.resetEncoders();
		Robot.Pigeon.resetPidgey();
		setTimeout(timeout);
	}


	protected void execute() {

	}


	protected boolean isFinished() {
		return isTimedOut();
	}

	protected void end() {
	}


	protected void interrupted() {
		this.end();
	}
	
}