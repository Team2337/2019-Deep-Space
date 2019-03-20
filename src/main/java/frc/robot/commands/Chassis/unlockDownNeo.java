package frc.robot.commands.Chassis;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Chassis;

/**
 * Disable lockdown mode for the Neos
 */
public class unlockDownNeo extends Command {

    /**
     * Disable lockdown mode for the Neos
     */
    public unlockDownNeo() {
        requires(Robot.Chassis);
    }

    // Stops the lockdown by setting the chassis to 0 speed
    protected void initialize() {
        Chassis.neoArcade(0, 0, false);
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return true;
    }
}
