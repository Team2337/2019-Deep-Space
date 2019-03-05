package frc.robot.commands.RoboWrangler;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Drive the robot towards the driver station and control the lassoing motor
 * that secures the buddyclimb robot
 */
public class driveWranglerBySpeed extends Command {

    private double speed;

    private double timeout;

    /**
     * Drive the robot towards the driver station and control the lassoing motor
     * that secures the buddyclimb robot
     */
    public driveWranglerBySpeed(double speed, double timeout) {
        this.speed = speed;
        this.timeout = timeout;
        requires(Robot.RoboWrangler);
    }

    @Override
    protected void initialize() {
        setTimeout(timeout);
    }

    @Override
    protected void execute() {
        Robot.RoboWrangler.drive(speed);
    }

    // This command does not end until the button is released
    @Override
    protected boolean isFinished() {
        // TODO: Also return true if the line sensor detects the platform
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.RoboWrangler.stop();
        Robot.ClimberDeploy.climberPhase = 4;
    }

    @Override
    protected void interrupted() {
        Robot.RoboWrangler.stop();
        //this.end(); don't want to advance climberphase if interupted..
    }

}