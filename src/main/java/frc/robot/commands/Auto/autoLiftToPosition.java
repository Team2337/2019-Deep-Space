package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Waits a given amount of time until the next command is run
 * 
 * @category AUTO
 * @author Bryce G.
 */
public class autoLiftToPosition extends Command {
	double position;

	/**
	 * Moves the lift to a position
	 * 
	 * @param position - the position the lift will go to
	 */
	public autoLiftToPosition(double position) {
		this.position = position;
		requires(Robot.Lift);
	}

	protected void initialize() {
		Robot.Lift.setSetpoint(position);
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return Robot.Lift.atPosition(10);
	}

	protected void end() {
	}

	protected void interrupted() {
		this.end();
	}

}