package frc.robot.commands.Chassis;


import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;


/**
 * Reset Open Loop Ramp Rate for Drive Neos to 0 or off
 */
public class removeNeoOpenLoopRampRate extends InstantCommand {

    double rampRate;

    public removeNeoOpenLoopRampRate() {
        requires(Robot.Chassis);
    }

    // Stops the lockdown by setting the chassis to 0 speed
    protected void initialize() {
        Robot.Chassis.setNeoOpenLoopRampRate(0);
    }

}
