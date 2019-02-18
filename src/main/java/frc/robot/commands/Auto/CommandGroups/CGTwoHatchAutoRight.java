package frc.robot.commands.Auto.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.pathway;
import frc.robot.commands.Auto.autoResetEncoders;
import frc.robot.commands.Auto.setpaths.autoSetPath;
import frc.robot.commands.Auto.setpaths.autoSetPathReverse;
import frc.robot.commands.Auto.autoWaitSensorReset;

/**
 * Runs reverse off the platform, to the first right side cargo ship spot, scores, and 
 * drives in an s-curve to the right load station
 * @author Bryce G.
 */
public class CGTwoHatchAutoRight extends CommandGroup {
  double[][] valuesPID = pathway.valuesPID;
  public CGTwoHatchAutoRight() {
      addSequential(new autoSetPathReverse(Robot.driveForwardT, valuesPID[0]));
      addSequential(new autoResetEncoders());
      addSequential(new autoWaitSensorReset(0.5));
      addSequential(new autoSetPath(Robot.curveFromToHatchRightT, valuesPID[1]));
  }
}
