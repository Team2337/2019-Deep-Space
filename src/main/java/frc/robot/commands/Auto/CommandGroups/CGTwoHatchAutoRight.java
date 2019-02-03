package frc.robot.commands.Auto.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.Pathway;
import frc.robot.commands.Auto.autoResetEncoders;
import frc.robot.commands.Auto.setpaths.autoSetPath;
import frc.robot.commands.Auto.setpaths.autoSetPathReverse;
import frc.robot.commands.Auto.autoWaitSensorReset;

/**
 * This is an example command group, put commands in here to run them in auton
 */
public class CGTwoHatchAutoRight extends CommandGroup {
  double[][] valuesPID = Pathway.valuesPID;
  public CGTwoHatchAutoRight() {
      addSequential(new autoSetPathReverse(Robot.initTrajectory, valuesPID[0]));
      addSequential(new autoResetEncoders());
      addSequential(new autoWaitSensorReset(0.3));
      addSequential(new autoSetPath(Robot.curveFromToHatchRightT, valuesPID[1]));
  }
}
