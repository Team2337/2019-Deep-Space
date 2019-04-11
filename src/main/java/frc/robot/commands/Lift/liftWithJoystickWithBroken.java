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
public class liftWithJoystickWithBroken extends Command {
    // The value of the driver joystick's left up/down motion
    private double joy;

    // Joystick modifiers, they are different to account for differences in gravity
    private double joystickForwardModifier = 0.5;

    // The value that the joystick must exceed for movement to take effect
    private double joystickDeadband = 0.2;

    // Whether or not the next cycle of execute() needs to set the setpoint
    private boolean positionSet = true;

    // Setup for after the stringpot has broken is finished
    private boolean setupFinished;

    /**
     * <p>
     * Causes the lift to be controlled manually by the operator using the left
     * joystick on the xbox controller
     * </p>
     * <br/>
     * <p>
     * <strong>IF THE LIFT STRINGPOT IS BROKEN</strong> - Flip yellow switch on the
     * operator interface
     * </p>
     */
    public liftWithJoystickWithBroken() {
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
        joy = (Robot.oi.operatorJoystick.getLeftStickY());

        // When the stringpot first breaks, set up the robot to handle this
        if (Robot.stringPotBroken) {
            if (setupFinished == false) {
                Robot.Lift.disableSoftLimits();
                Robot.Lift.setSoftLimits(1000, 0);
                Robot.Lift.move(0); // Disable the setpoints
                Robot.Lift.setMinMaxSpeed(0.5, 0.5);
                setupFinished = true;
            }
        } else {
            if (setupFinished == true) {
                Robot.Lift.setSoftLimits(Robot.Lift.forwardLiftSoftLimit, Robot.Lift.reverseLiftSoftLimit);
                Robot.Lift.enableSoftLimits();
                // Positional control is re-enabled automatically
                Robot.Lift.setMinMaxSpeed(Robot.Lift.maxSpeedUp, Robot.Lift.maxSpeedDown);
                setupFinished = false;
            }
        }

        // If the joystick value exceeds a threshold
        if (Math.abs(joy) > joystickDeadband) {
            // Position needs to be overridden, but only once until the joystick is used
            positionSet = false;
            // Set the speed of the lift to the joystick multiplied by a limiter
            Robot.Lift.move(joy * joystickForwardModifier);
        } else if (Robot.stringPotBroken == false) {
            // Set the position until the joystick is used
            if (!positionSet) {
                // Set the position of the lift that the motors need to automatically hold
                Robot.Lift.setSetpoint(Robot.Lift.getPosition());
                positionSet = true;
            }
        } else {
            Robot.Lift.move(0);
            positionSet = false;
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