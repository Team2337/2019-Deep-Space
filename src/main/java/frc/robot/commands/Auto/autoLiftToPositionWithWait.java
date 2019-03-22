package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Waits a given amount of time until the next command is run
 * 
 * @category AUTO
 * @author Bryce G.
 */
public class autoLiftToPositionWithWait extends Command {
	double position, wait, timer = 0;

	/**
	 * Moves the lift to a position
	 * 
	 * @param position - the position the lift will go to
	 * @param wait     - how long the lift should wait after the command has been
	 *                 initialized before moveing (in 20 millisecond intervals)
	 */
	public autoLiftToPositionWithWait(double position, double wait) {
		this.position = position;
		this.wait = wait * 50;
		requires(Robot.Lift);
	}

	protected void initialize() {
		timer = 0;
	}

	protected void execute() {
		timer++;
	}

	protected boolean isFinished() {
		return timer >= wait;
	}

	protected void end() {
		Robot.Lift.setSetpoint(position);
	}

	protected void interrupted() {
		this.end();
	}

}