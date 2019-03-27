package frc.robot.commands.Auto.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;
import frc.robot.commands.Auto.Common.*;
import frc.robot.commands.Auto.setpaths.*;

/**
 * Auton Description:
 * <ol>
 * <li>Starts with hatch On the right side of the lvl 1 platform</li>
 * <li>Drives to the far end of the right rocket</li>
 * <li>Scores the hatch on low</li>
 * <li>Drives to the right load station</li>
 * <li>Intakes hatch</li>
 * <li>Drives to the near right rocket</li>
 * <li>Scores the hatch on low</li>
 * </ol>
 * @author Bryce G.
 */
public class CGHatchRightLowFarRocketLowNearRocketLow extends CommandGroup {
  public CGHatchRightLowFarRocketLowNearRocketLow() {
    double[][] values = pathway.valuesPID;
    // addSequential(new autoWait(Robot.delayChooser.getSelected()));
    addSequential(new autoTurnOnLimeLightLED());
    // addSequential(new autoSetPath(Robot.driveOffRightLvl2ToRightRocketT, values[0], 0, 30, false));
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition + 15, 0.5));
    addSequential(new autoSetPathReverse(Robot.driveOffRightLvl1ToBackRightRocketT, values[5], 0, 15));
    addSequential(new autoReadAngle());
    addSequential(new autoResetSensors());
    addSequential(new autoPIDVisionDrive(3, 0.09, 0.035, 0.6));

    addSequential(new CommonScoreHatch());
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.05));
    addSequential(new autoDriveToEncoderTick(-1, 20000, 0.5, 0, false, 0));
    addSequential(new autoTurnToDegree(0.05, 0, 0, (-Robot.autonAngle-5), 1.3));
    addSequential(new autoResetEncoders());   
    addSequential(new autoWait(0.05)); 
    addSequential(new autoTurnOnLimeLightLED());
    addSequential(new autoDriveToEncoderTick(1, 50000, 0.7, -0.25, true, 0.5)); 
    addSequential(new autoResetEncoders());
    addSequential(new autoPIDVisionDrive(6.5, 0.055, 0.01, 0.6));
    addSequential(new autoResetEncoders());
    addSequential(new CommonIntakeHatch());
    // addSequential(new hatchBeakOpen());
    // addSequential(new autoLiftToPosition((Robot.Lift.hatchLowScorePosition + 130)));
    addSequential(new autoResetEncoders()); 
    addSequential(new autoWait(0.05));
    addSequential(new autoDriveToEncoderTick(-1, 30000, 0.5, 0, false, 0));
    
  }
}