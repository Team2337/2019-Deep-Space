package frc.robot.commands.Lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command is mainly a placeholder command, but it can be used
 * functionally. It does just as it says: nothing.
 */
public class setTargetPosition extends Command {

    // The set position of the lift in encoder ticks
    double pos;

    /**
     * @param pos - The position of the lift in (stringpot) encoder ticks
     */
    public setTargetPosition(double pos) {
        this.pos = pos;

        requires(Robot.Lift);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.Lift.targetPosition = pos;
        Robot.CargoBigBrother.inFireMode = false;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.Lift.targetPosition = pos;
        Robot.CargoBigBrother.inFireMode = false;
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.Lift.targetPosition = pos;
        Robot.CargoBigBrother.inFireMode = false;
        System.out.println("***************************************************************************bleck");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        this.end();
    }
}