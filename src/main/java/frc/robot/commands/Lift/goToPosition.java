package frc.robot.commands.Lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Go to the specified position on the lift
 */
public class goToPosition extends Command {

    // The set position of the lift in stringpot ticks
    double position;

    /**
     * Go to the specified position on the lift Sets the setpoint on the lift to
     * {@code pos}
     * 
     * @param pos - The position of the lift in stringpot ticks
     *            <p>
     *            <br/>
     *            Example: {@code goToPosition(95)} //Cargo Intake Position
     *            </p>
     */
    public goToPosition(double pos) {
        position = pos;
        requires(Robot.Lift);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        if(!Robot.stringPotBroken && Robot.Lift.getPIDStatus()) {
            Robot.Lift.setSetpoint(position);
        }
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

    }

    // This command is not meant to finish until the button is released, where the
    // lift will hold its current position
    @Override
    protected boolean isFinished() {
        return Robot.Lift.atPosition(10) || !Robot.Lift.getPIDStatus();
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