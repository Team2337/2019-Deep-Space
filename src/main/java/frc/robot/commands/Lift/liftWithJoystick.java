package frc.robot.commands.Lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Drives the lift using the joystick Current joystick is set to the
 * forward/backward motion on the joystick
 * 
 * @category LIFT
 * @author Bryce G.
 */
public class liftWithJoystick extends Command {
    // The value of the driver joystick's left up/down motion
    private double joy;

    // Joystick modifiers, they are different to account for differences in gravity
    private double joystickForwardModifier = 0.5;
    private double joystickBackwardModifier = 0.25;

    // The value that the joystick must exceed for movement to take effect
    private double joystickDeadband = 0.25;

    // Whether or not the next cycle of execute() needs to set the setpoint
    private boolean positionSet = true;

    // Position of the lift the motors should hold
    private double holdPosition;

    /**
     * <p>
     * Causes the lift to be controlled manually by the operator using the left
     * joystick on the xbox controller
     * </p>
     * <br/>
     * <p>
     * <strong>IN CASE OF EMERGENCY</strong> - Flip yellow switch on the operator
     * interface
     * </p>
     * <br/>
     * <strong>IF THE STRINGPOT IS BROKEN</strong> - Flip the black switch as well
     */
    public liftWithJoystick() {
        requires(Robot.Lift);
    }

    @Override
    protected void initialize() {
        // Ensure that soft limits are enabled (set in case the manual drive activated)
        Robot.Lift.enableSoftLimits();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        // The value of the driver joystick's left thumbsticks up/down motion
        joy = (Robot.oi.driverJoystick.getLeftStickY());

        // If the joystick value exceeds a threshold (adjust for noise)
        if (Math.abs(joy) > joystickDeadband) {
            // Position needs to be overridden, but only once until the joystick is used
            if (positionSet) {
                positionSet = false;
            }
            // Set the speed of the lift to the joystick multiplied by a limiter
            Robot.Lift.move(joy * joystickForwardModifier);
        } else {
            // Set the position until the joystick is used
            if (!positionSet) {
                holdPosition = Robot.Lift.getPosition();
                positionSet = true;
            }
            // Set the position of the lift that the motors need to automatically hold
            Robot.Lift.setSetpoint(holdPosition);
        }
    }

    // The command will run as long as the switches are flipped
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