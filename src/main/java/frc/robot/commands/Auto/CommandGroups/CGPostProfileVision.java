package frc.robot.commands.Auto.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.Pathway;
import frc.robot.commands.Auto.setpaths.autoSetPath;

/**
 * Driving with the limelight after the paths have finished
 * Turns the limelight on before reaching the end of the path
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
