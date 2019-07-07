package frc.robot.commands.RoboWrangler;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Drive the robot towards the driver station and control the lassoing motor
 * that secures the buddyclimb robot
 */
public class driveWranglerByJoystick extends Command {

    // The value of the driver joystick's left up/down motion
    private double joy;

    // The value that the joystick must exceed for movement to take effect
    private double joystickDeadband = -0.25;

    /**
     * Drive the robot towards the driver station and control the lassoing motor
     * that secures the buddyclimb robot
     */
    public driveWranglerByJoystick() {
        requires(Robot.RoboWrangler);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        // The value of the driver joystick's left thumbsticks up/down motion
        joy = (Robot.oi.operatorJoystick.getRightStickY());

        /*
         * When the right joystick is moving forward, drive the robot forward using the
         * robowrangler, and when it is backwards, run the lasso motor. Otherwise, stop
         * all motors
         */
        if(joy > 0) {
            joy = 0;
        }
        if (joy < joystickDeadband) {
            Robot.RoboWrangler.drive(-joy);
        } else {
            Robot.RoboWrangler.stop();
        }

    }

    // This command does not end until the button is released
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.RoboWrangler.stop();
    }

    @Override
    protected void interrupted() {
        this.end();
    }

}