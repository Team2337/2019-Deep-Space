package frc.robot.commands.Auto.CommandGroups;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.Common.*;
import frc.robot.commands.HatchLauncher.*;
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
 * <li>Scores the hatch on mid</li>
 * </ol>
 * @author Bryce G.
 */
public class CGHatchLeftHighNearRocketLowNearRocketMid extends CommandGroup {
  double[][] valuesPID = pathway.valuesPID;

  public CGHatchLeftHighNearRocketLowNearRocketMid() {
    
    addSequential(new CommonHighLeftToNearRocket());
    
    addSequential(new autoTankDrive(-0.5, -0.6, 0, 55000, "right", IdleMode.kCoast));
    addParallel(new autoTurnOnLimeLightLED());
    addSequential(new autoTankDrive(-0.2, -0.6, 0, 80000, "right", IdleMode.kBrake));
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.05));
    addSequential(new autoTankDrive(0.5, 0.1, 55000, 0, "leftVision", IdleMode.kCoast));
    addSequential(new autoPIDVisionDrive(4, 0.08, 0.06, 0.7));
    addSequential(new autoLiftToPositionWithWait(Robot.Lift.hatchMidScorePosition, 0));
    addSequential(new hatchLauncherExtend());
    // addSequential(new CommonScoreHatch());

    addSequential(new autoEndAuto());
  }
}