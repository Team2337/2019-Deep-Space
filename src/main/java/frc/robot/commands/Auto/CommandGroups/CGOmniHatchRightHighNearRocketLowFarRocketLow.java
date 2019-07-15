package frc.robot.commands.Auto.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Auto.Common.*;
import frc.robot.commands.Chassis.driveAtSpeedToAngle;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;

/**
 *  Auton Description:
 * <ol>
 * <li>Starts with hatch On the right side of the lvl 1 platform</li>
 * <li>Drives to the near end of the right rocket</li>
 * <li>Scores the hatch on low</li>
 * <li>Drives to the right load station</li>
 * <li>Intakes hatch</li>
 * <li>Drives to the near right rocket</li>
 * <li>Scores the hatch</li>
 * </ol>
 * @author Bryce G.
 */
public class CGOmniHatchRightHighNearRocketLowFarRocketLow extends CommandGroup {
  double[][] valuesPID = pathway.valuesPID;

  public CGOmniHatchRightHighNearRocketLowFarRocketLow() {
    addSequential(new autoTurnOnLimeLightLED());
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 1.0));
    addSequential(new driveAtSpeedToAngle(0, 0.17, 200000, 1.0));
    addSequential(new autoWait(0.2));
    // addSequential(new autoTurnToDegreeOmni(0.1, 0, 0, 0, 0.5));
    addSequential(new CommonOmniRightToNearRocketLow());
    addSequential(new CommonOmniRightLoadToFarRocketLow());
    addSequential(new autoEndAuto());
  }
}