package frc.robot.commands.Auto.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;
import frc.robot.commands.Auto.setpaths.*;
import frc.robot.commands.AutoHatchKicker.hatchKickerExtend;
import frc.robot.commands.AutoHatchKicker.hatchKickerRetract;
import frc.robot.commands.Chassis.PID3DLimelight;
import frc.robot.commands.HatchBeak.hatchBeakClose;
import frc.robot.commands.HatchBeak.hatchBeakOpen;
import frc.robot.commands.HatchLauncher.hatchLauncherExtend;
import frc.robot.commands.HatchLauncher.hatchLauncherRetract;
import frc.robot.commands.Vision.limeLightLEDOn;

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
 * <p><br/></p>
 * Changes needing to be made:
 * <ul>
 * <li>TODO: Speed up the drives</li>
 * <li>TODO: Fix limelight drive</li>
 * </ul>
 * @author Bryce G.
 */
public class CGTwoHatchAutoRight extends CommandGroup {
  double[][] valuesPID = pathway.valuesPID;
  public CGTwoHatchAutoRight() {
      addSequential(new autoSetPathReverse(Robot.driveForwardT, valuesPID[0], 0.1));
      addSequential(new autoLineSensorDrive());
      addSequential(new autoLiftToPosition((Robot.Lift.getPosition() + 30), 2));
      addSequential(new autoResetEncoders());
      addSequential(new hatchKickerExtend());
      addSequential(new autoWait(.5));
      addSequential(new hatchKickerRetract());
      addSequential(new autoWaitSensorReset(0.5));
      addSequential(new autoLiftToPosition(Robot.Lift.hatchIntakePosition + 40, 2));
      addSequential(new limeLightLEDOn());
      addSequential(new autoWait(.5));
      addSequential(new autoSetPath(Robot.curveFromToHatchRightT, valuesPID[1], 2));
      addSequential(new hatchBeakClose());
      addSequential(new hatchLauncherExtend());
      addSequential(new PID3DLimelight(0.05, 0, 0, ""));
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

  }
}