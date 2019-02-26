package frc.robot.commands.Auto.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;
import frc.robot.commands.Auto.setpaths.*;

import frc.robot.commands.AutoHatchKicker.hatchKickerRetract;
import frc.robot.commands.Auto.autoWaitSensorReset;

/**
 * Runs reverse off the platform, to the first right side cargo ship spot, scores, and 
 * drives in an s-curve to the right load station
 * @author Bryce G.
 */
public class CGTwoHatchAutoRight extends CommandGroup {
  double[][] valuesPID = pathway.valuesPID;
  public CGTwoHatchAutoRight() {
      addSequential(new autoSetPathReverse(Robot.driveForwardT, valuesPID[1], 2));
      addSequential(new autoResetEncoders());
      addSequential(new autoHatchKickerExtend(1));
      addSequential(new hatchKickerRetract());
      addSequential(new autoWaitSensorReset(1));
      addSequential(new autoWait(1));
      addSequential(new autoSetPath(Robot.testSCurveT, valuesPID[1], 2));
      addSequential(new autoHatchKickerExtend(1));
      addSequential(new hatchKickerRetract());
      addSequential(new autoWaitSensorReset(1));
  }
}
