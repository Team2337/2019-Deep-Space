package frc.robot.commands.Auto.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.Pathway;
import frc.robot.commands.Auto.setpaths.autoSetPath;
import frc.robot.commands.Auto.setpaths.autoSetPathWithHold;
import frc.robot.commands.Auto.autoTurnOnLimeLightLED;
import frc.robot.commands.Chassis.PIDVisionDrive;
import frc.robot.commands.Vision.limeLightLEDBlink;
import frc.robot.commands.Vision.limeLightLEDOn;

/**
 * This is an example command group, put commands in here to run them in auton
 */
public class CGPostProfileVision extends CommandGroup {
  public CGPostProfileVision() {
    double[][] values = Pathway.valuesPID;
    //addParallel(new autoTurnOnLimeLightLED());
    addSequential(new autoSetPath(Robot.curveFromToHatchRightT, values[1]));
    // addSequential(new limeLightLEDOn());
    //addSequential(new PIDVisionDrive(.02, 0, 0.02, ""));
    
  }
}
