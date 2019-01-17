package frc.robot.commands.Lift;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command is mainly a placeholder command, but it can be used
 * functionally. It does just as it says: nothing.
 */
public class liftWithJoystick extends Command {
    private static Joystick liftJoystick = Robot.oi.operatorJoystick;

    // CONSTRUCTOR
    public liftWithJoystick() {

        requires(Robot.Lift);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double joy = liftJoystick.getRawAxis(2);
        joy = Math.round(joy);
        switch((int)joy) {
            case -1:
            Robot.Lift.setSetpoint(70);
            break;
            case 0:
            Robot.Lift.setSetpoint(375);
            break;
            case 1:
            Robot.Lift.setSetpoint(600);
            break;
            default:
            Robot.Lift.stop();
            break;
        }
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