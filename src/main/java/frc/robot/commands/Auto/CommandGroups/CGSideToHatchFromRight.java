package frc.robot.commands.Auto.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.pathway;
import frc.robot.commands.Auto.paths.PathwaySideTwoHatchFromRight;
import frc.robot.commands.Auto.autoResetEncoders;
import frc.robot.commands.Auto.setpaths.autoSetPath;
import frc.robot.commands.Auto.setpaths.autoSetPathReverse;
import frc.robot.commands.Auto.autoWaitSensorReset;

/**
 * Two hatch auto, starting on the right side of the platform backwards, 
 * running a JTurn at the end for the final hatch
 * @author Bryce G.
 */
public class  CGSideToHatchFromRight extends CommandGroup {
  double[][] valuesPID = PathwaySideTwoHatchFromRight.valuesPID;

  public CGSideToHatchFromRight() {
    addSequential(new autoSetPathReverse(Robot.sideTwoHatchFromRightT, valuesPID[0], 2, 0));
 
    
  }
}