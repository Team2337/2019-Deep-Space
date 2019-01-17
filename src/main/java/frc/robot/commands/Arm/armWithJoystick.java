package frc.robot.commands.Arm;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

/**
 * This command is mainly a placeholder command, but it can be used
 * functionally. It does just as it says: nothing.
 */
public class armWithJoystick extends Command {
    // CONSTRUCTOR
    public armWithJoystick() {
        requires(Robot.Arm);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double joy = OI.operatorJoystick.getRawAxis(2);
            if(joy > 0.9)
            Robot.Arm.setSetpoint(40);
            else if(joy < 0.05 && joy > -0.05)
            Robot.Arm.setSetpoint(250);
            else if(joy < -0.9)
            Robot.Arm.setSetpoint(400);
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