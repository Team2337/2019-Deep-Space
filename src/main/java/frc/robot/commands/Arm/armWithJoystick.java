package frc.robot.commands.Arm;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

/**
 * This command is mainly a placeholder command, but it can be used
 * functionally. It does just as it says: nothing.
 * 
 * @category ARM
 * @author Bryce G.
 */
public class armWithJoystick extends Command {
    // CONSTRUCTOR
    private boolean active;

    public armWithJoystick() {
        active = false;
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
        if(active) {
            switch((int)Math.round(joy)) {
                case 1:
                Robot.Arm.setSetpoint(50);
                break;
                case 0:
                Robot.Arm.setSetpoint(250);
                break;
                case -1:
                Robot.Arm.setSetpoint(400);
                break;
            }
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