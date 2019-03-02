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
	double position, wait, timer = 0;
	
	/**
     * Moves the lift to a position
	 * @param position - the position the lift will go to 
     * @param wait - how long the lift should wait after the command has been initialized before moveing (in 20 millisecond intervals)
	 */
	public autoLiftToPosition(double position, double wait) {
        this.position = position;
        this.wait = wait;
        requires(Robot.Lift);
	}

	protected void initialize() {
        Robot.Lift.setSetpoint(position);
        timer = 0;
	}


	protected void execute() {
        
        /*
        if(timer >= wait) {
            
        } else {
            timer++;
        }*/
	}


	protected boolean isFinished() {
        return Robot.Lift.atPosition(5);
	}

	protected void end() {
	}


	protected void interrupted() {
		this.end();
	}
	
}