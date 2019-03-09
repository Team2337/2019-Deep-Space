package frc.robot.commands.Chassis;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.Robot;
import frc.robot.nerdyfiles.controller.NerdyUltimateXboxDriver;
import frc.robot.subsystems.Chassis;

/**
 * Uses controller joysticks to drive the robot using ArcadeDrive
 */
public class unlockDown extends Command {


    /**
     * Stops the lockdown by setting the chassis to 0 speed
     */
    public unlockDown() {
        requires(Robot.Chassis);
    }

    protected void initialize() {
        // Robot.Chassis.driveArcade(0, 0, 0);
    }

    // Supplys the correct values to the arcadeDrive command to drive the robot
    protected void execute() {

    }

    // This command is not meant to exit
    protected boolean isFinished() {
        return true;
    }
}
