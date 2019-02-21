package frc.robot.commands.Lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Go to the specified position on the lift
 */
public class goToPosition extends Command {

    // The set position of the lift in encoder ticks
    double position;

    /**
     * Go to the specified position on the lift
     * Sets the setpoint on the lift to {@code pos}
     * @param pos - The position of the lift in (stringpot) encoder ticks
     * <p><br/>Example: <ul><li>{@code goToPosition(95) //Cargo Intake Position</li></ul></p>}
     */
    public goToPosition(double pos) {
        position = pos;
        requires(Robot.Lift);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.Lift.setSetpoint(position);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

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