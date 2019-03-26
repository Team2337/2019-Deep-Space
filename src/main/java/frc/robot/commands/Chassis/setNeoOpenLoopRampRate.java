package frc.robot.commands.Chassis;


import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;


/**
 * Set Open Loop Ramp Rate for Drive Neos
 */
public class setNeoOpenLoopRampRate extends InstantCommand {

    double rampRate;

    public setNeoOpenLoopRampRate(double rampRate) {
        this.rampRate = rampRate;
        requires(Robot.Chassis);
    }

    // Stops the lockdown by setting the chassis to 0 speed
    protected void initialize() {
        Robot.Chassis.setNeoOpenLoopRampRate(rampRate);
    }

}
