package frc.robot.commands.Lift;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.nerdyfiles.controller.*;

/**
 * Drives the lift using the joystick
 * Current joystick is set to the forward/backward motion on the joystick
 * @category LIFT
 * @author Bryce G.
 */
public class liftWithJoystick extends Command {

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
        double joy = Robot.oi.operatorJoystick.getRawAxis(1);
        switch((int)Math.round(joy)) {
            case 1:
            Robot.Lift.setSetpoint(50);
            break;
            case 0:
            Robot.Lift.setSetpoint(250);
            break;
            case -1:
            Robot.Lift.setSetpoint(400);
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