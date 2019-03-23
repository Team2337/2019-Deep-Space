package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Resets all sensors including the encoders
 * 
 * @category AUTO
 * @author Bryce G.
 */
public class autoResetSensors extends Command {
	
	public autoResetSensors() {
		
	}

	protected void initialize() {
        Robot.Chassis.resetEncoders();
        Robot.Pigeon.resetPidgey();
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