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

    public static boolean endedAutoLED = false;
    protected void initialize() {
        if (Robot.autonomousCommand != null) {
            Robot.autonomousCommand.cancel();
        }
        endedAutoLED = true;
        // System.out.println("Auton Canceled");
    }

    protected void end() {
        endedAutoLED = false;
    }
    
}