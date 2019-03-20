package frc.robot.commands.Lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Lift;

/**
 * Go to the specified position on the lift
 */
public class liftToClimbTop extends Command {

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
    public liftToClimbTop(double pos) {
        position = pos;
        requires(Robot.Lift);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Lift.setSoftLimits(455, 91);
        Robot.Lift.setSetpoint(position);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

    }

    // This command is not meant to finish until the button is released, where the
    // lift will hold its current position
    @Override
    protected boolean isFinished() {
        return Robot.Lift.atPosition(10);
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