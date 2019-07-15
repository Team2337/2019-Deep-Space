package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Waits a given amount of time until the next command is run
 * 
 * @category AUTO
 * @author Bryce G.
 */
public class autoButtonDelay extends Command {
    boolean isWaiting;

    /**
     * @param timeout Amount of time the command runs for until it is forced to
     *                terminate, in seconds
     */
    public autoButtonDelay(boolean isWaiting) {
        this.isWaiting = isWaiting;
	}

	protected void initialize() {
        Robot.isWaitingAuton = isWaiting;
	}


	protected void execute() {

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