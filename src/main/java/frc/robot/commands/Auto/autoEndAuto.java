package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * Exits the robot from auton and gives the drivers control of the robot
 * 
 * @author Bryce G.
 */
public class autoEndAuto extends InstantCommand {

    protected void initialize() {
        Robot.autonomousCommand.cancel();
    }
}