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
    private double joystickDeadband = 0.25;

    // Joystick modifiers, they are different to account for differences in gravity
    private double joystickForwardModifier = 1;
    private double joystickBackwardModifier = 1;

    // CONSTRUCTOR
    public liftWithJoystickOverride() {
        requires(Robot.Lift);
    }

    // Disables soft limits, allowing free motion of the lift
    @Override
    protected void initialize() {
        Robot.Lift.disableSoftLimits();
    }

    @Override
    protected void execute() {
        // The value of the driver joystick's left thumbsticks up/down motion
        joy = (Robot.oi.operatorJoystick.getLeftStickY());
        System.out.println("Joystick: " + joy);
        Robot.Lift.move(joy * joystickForwardModifier);
        /*
        // If the driver wants the lift to go upwards
        if (joy > joystickDeadband) {
            // The lifts speed is the joystick value multiplied by an upward modifier
            Robot.Lift.move(joy * joystickForwardModifier);
            // If the driver wants the lift to go downwards...
        } else if (joy < -joystickDeadband) {
            // The lifts speed is the joystick value multiplied by an downward modifier
            Robot.Lift.move(joy * joystickBackwardModifier);
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
        */
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