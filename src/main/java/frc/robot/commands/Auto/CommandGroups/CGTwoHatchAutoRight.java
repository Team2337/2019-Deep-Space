package frc.robot.commands.Auto.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;
import frc.robot.commands.Auto.setpaths.*;
import frc.robot.commands.AutoHatchKicker.*;
import frc.robot.commands.Vision.*;

/**
 * Runs reverse off the platform, to the first right side cargo ship spot, scores, and 
 * drives in an s-curve to the right load station<p><br/></p>
 * 
 * Auton Steps: 
 * <ul>
 * <li>Drive back off the platform to the second cargo bay</li>
 * <li>Fire the hatch on the cargo ship</li>
 * <li>Curve from the cargo ship to the right load station</li>
 * <li>Intake a hatch panel</li>
 * <li>J CUrve from load station to the rocket</li>
 * <li>Drives forward to the first cargo bay</li>
 * </ul>
 * 
 * @author Bryce G.
 */
public class CGTwoHatchAutoRight extends CommandGroup {
  double[][] valuesPID = pathway.valuesPID;
  public CGTwoHatchAutoRight() {
    // addSequential(new autoWait(Robot.delayChooser.getSelected()));
    //Robot.driveForwardT
    addSequential(new autoSetPathReverse(Robot.driveForwardT, valuesPID[0], 0.1, 0));
      //use this to read from the file
      // addSequential(new autoSetPathReverseFile(Robot.NerdyPath.readFile("driveForward187"), valuesPID[0], 0.1, 0.35)); //last value is the max velocity
      addSequential(new autoLineSensorDrive());
      addSequential(new autoLiftToPosition((Robot.Lift.getPosition() + 30)));
      addSequential(new autoResetSensors());
      addSequential(new hatchKickerExtend());
      addSequential(new autoWait(1.0));
      addSequential(new hatchKickerRetract());
      addSequential(new autoWaitSensorReset(0.5));
      addSequential(new autoLiftToPosition(Robot.Lift.hatchIntakePosition + 40));
      
      addSequential(new limeLightLEDOn());
      addSequential(new autoWait(.5));
      // Robot.curveFromToHatchRightT
      addSequential(new autoSetPathFile(Robot.NerdyPath.readFile("curveFromToHatchRight"), valuesPID[1], 2, 0.35)); //last value is the max velocity
      addSequential(new hatchKickerExtend());
      addSequential(new autoWait(.4));
      addSequential(new hatchKickerRetract());
      
      // addSequential(new hatchBeakClose());
      // addSequential(new hatchLauncherExtend());
      // addSequential(new PID3DLimelight(0.05, 0, 0, ""));
      /* 
      // Needed to fix this 
      addSequential(new hatchBeakOpen());
      addSequential(new autoWait(0.1));
      addSequential(new hatchLauncherRetract());
      addSequential(new autoResetEncoders());
      addSequential(new autoWait(0.5));
      addSequential(new autoSetPathReverseFull(Robot.fromRightLoadJTurnToCargoShipT, valuesPID[2], 2));
      addSequential(new limeLightLEDOn());
      addSequential(new autoResetEncoders());
      addSequential(new autoWait(0.5));
      addSequential(new autoSetPath(Robot.jTurnToCargoShipRightT, valuesPID[2], 2));
      addSequential(new PID3DLimelight(0.05, 0, 0, ""));
      addSequential(new hatchLauncherExtend());
      */

  }
}