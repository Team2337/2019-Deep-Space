package frc.robot.commands.RoboWrangler;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class driveWrangler extends Command {

    // The value of the driver joystick's left up/down motion
    private double joy;

    // The value that the joystick must exceed for movement to take effect
    private double joystickDeadband = 0.25;

    public driveWrangler() {
        requires(Robot.RoboWrangler);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        // The value of the driver joystick's left thumbsticks up/down motion
        joy = (Robot.oi.operatorJoystick.getRightStickY());

        if (joy > joystickDeadband) {
            Robot.RoboWrangler.drive(joy);
            Robot.RoboWrangler.lasso(0);
        } else if (joy < joystickDeadband) {
            Robot.RoboWrangler.drive(0);
            Robot.RoboWrangler.lasso(joy);
        } else {
            Robot.RoboWrangler.stop();
        }

    }

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