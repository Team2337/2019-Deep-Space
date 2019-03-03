package frc.robot.commands.Lift;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

/**
 * Drives the lift using the joystick, the speed is set to the forward/backward
 * motion on the joystick, multiplied by a limiter
 * 
 * @category LIFT
 * @author Jack E.
 */

public class liftWithJoystickOverride extends Command {
    private double joy;
    private double joystickDeadband = 0.2;

    /**
     * Overrides the lift PID to be controlled by the joystick and not setpoints
     * <p><br/><strong>NOTE:</strong> if the string pot goes out of bounds (or breaks) the 
     * system will automatically disable the PID and change the controls from setpoints to the joystick.
     * 
     */
    public liftWithJoystickOverride() {
        requires(Robot.Lift);
    }

    // Disables soft limits, allowing free motion of the lift
    @Override
    protected void initialize() {
        Robot.Lift.disableSoftLimits();
        Robot.Lift.setMinMaxSpeed(1, -1);
    }

    @Override
    protected void execute() {
        // The value of the operator joystick's left thumbsticks up/down motion
        joy = (Robot.oi.operatorJoystick.getLeftStickY());

        // If the joystick exceeds a deadband
        if (Math.abs(joy) > joystickDeadband) {
            // The lifts speed is the joystick value
            Robot.Lift.move(joy);

            // If the joystick isn't being moved...
        } else {
            // ... and the A button is pressed
            if (Robot.oi.driverJoystick.greenA.get()) {
                // Drive it very slighty against gravity (WARNING: Could burn up motors)
                Robot.Lift.move(0.1);
                // ... and the A button is NOT pressed
            } else {
                // Don't give the motors any signal, preventing burnup
                Robot.Lift.move(0);
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