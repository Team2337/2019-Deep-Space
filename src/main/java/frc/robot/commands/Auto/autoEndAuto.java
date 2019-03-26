package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * Exits the robot from auton and gives the drivers control of the robot
 * 
 * @category AUTO
 * @author Bryce G.
 */
public class autoEndAuto extends InstantCommand {

    protected void initialize() {
        if (Robot.autonomousCommand != null) {
            Robot.autonomousCommand.cancel();
        }
        System.out.println("Auton Canceled");
    }
}