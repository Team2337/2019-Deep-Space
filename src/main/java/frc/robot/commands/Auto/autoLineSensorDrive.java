package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Waits a given amount of time until the next command is run
 * 
 * @category AUTO
 * @author Bryce G.
 */
public class autoLineSensorDrive extends Command {
	
	private boolean finished;
	/**
	 * @param timeout
	 * Amount of time the command runs for until it is forced to terminate, in seconds
	 */
	public autoLineSensorDrive() {

	}

	protected void initialize() {

	}


	protected void execute() {
		if(Robot.Chassis.linesCrossed == 1) {
			Robot.Chassis.neoDrive.arcadeDrive(0.2, 0, false);
		}
		if(Robot.Chassis.linesCrossed == 0 && Robot.Chassis.autoLineSensor.get()) {
			Robot.Chassis.neoDrive.arcadeDrive(-0.2, 0, false);
		}
        if(!Robot.Chassis.autoLineSensor.get()) {
			finished = true;
		}
	}


	protected boolean isFinished() {
		return finished;
	}

	protected void end() {
		Robot.Chassis.neoDrive.arcadeDrive(0, 0, false);
	}


	protected void interrupted() {
		this.end();
	}
	
}