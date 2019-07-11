package frc.robot.commands.Lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Stops the lift motors from being able to move, by chaning the PID status to false (disabled)
 * @author Bryce G.
 */
public class stopLift extends Command {

    /**
     * Stops the Lift from moving 
     */
    public stopLift() {
        requires(Robot.Lift);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
       Robot.Lift.stop();
       Robot.Lift.setDisabledMode();
       Robot.Lift.setPIDStatus(false);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        this.end();
    }
}