package frc.robot.nerdyfiles;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Go to the specified position on the lift
 */
public class testCommand extends Command {

    boolean isFinished;

    public testCommand(boolean isFinished) {
        this.isFinished = isFinished;
        requires(Robot.Lift);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("**********COMMAND INITIALIZED*******");
        setTimeout(5);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        System.out.println("**********COMMAND EXECUTE*******");
    }

    // This command is not meant to finish until the button is released, where the
    // lift will hold its current position
    @Override
    protected boolean isFinished() {
        System.out.println("**********COMMAND CHECKED IF IT WAS FINISHED*******");
        return isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        System.out.println("**********COMMAND ENDED*******");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        System.out.println("**********COMMAND INTURRUPTED*******");
        this.end();
    }
}