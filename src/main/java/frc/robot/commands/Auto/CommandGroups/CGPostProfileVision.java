package frc.robot.commands.Auto.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Auto.*;

/**
 * Driving with the limelight after the paths have finished
 * Turns the limelight on before reaching the end of the path
 * @author Bryce G.
 */
public class CGPostProfileVision extends CommandGroup {
  public CGPostProfileVision() {
    double[][] values = pathway.valuesPID;
    // addSequential(new autoWait(Robot.delayChooser.getSelected()));
    //addParallel(new autoTurnOnLimeLightLED());
    // addSequential(new autoSetPath(Robot.curveFromToHatchRightT, values[1], 2));
    // addSequential(new limeLightLEDOn());
    //addSequential(new PIDVisionDrive(.02, 0, 0.02, ""));
    
  }
}