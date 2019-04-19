package frc.robot.commands.Auto.CommandGroups;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.Common.*;
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
public class CGHatchRightLowNearRocketLowNearRocketMid extends CommandGroup {
  double[][] valuesPID = pathway.valuesPID;

  public CGHatchRightLowNearRocketLowNearRocketMid() {
    
    addSequential(new CommonRightToNearRocketLow());
    
    addSequential(new autoTankDrive(-0.5, -0.4, 55000, 0, "left", IdleMode.kCoast));
    addParallel(new autoTurnOnLimeLightLED());
    addSequential(new autoTankDrive(-0.5, -0.1, 80000, 0, "left", IdleMode.kBrake));
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.05));
    addSequential(new autoTankDrive(0.1, 0.5, 0, 55000, "rightVision", IdleMode.kCoast));
    addSequential(new autoPIDVisionDrive(4, 0.08, 0.06, 0.6));
    addSequential(new autoLiftToPositionWithWait(Robot.Lift.hatchMidScorePosition, 0));
    // addSequential(new CommonScoreHatch());
    addSequential(new autoEndAuto());
  }
}