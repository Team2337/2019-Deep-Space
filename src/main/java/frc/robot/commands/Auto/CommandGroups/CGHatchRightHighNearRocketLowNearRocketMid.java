package frc.robot.commands.Auto.CommandGroups;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;
import frc.robot.commands.Auto.Common.*;
import frc.robot.commands.Chassis.*;
import frc.robot.commands.HatchBeak.*;

/**
 *  Auton Description:
 * <ol>
 * <li>Starts with hatch On the right side of the lvl <strong>2</strong> platform</li>
 * <li>Drives to the near end of the right rocket</li>
 * <li>Scores the hatch on low</li>
 * <li>Drives to the right load station</li>
 * <li>Intakes hatch</li>
 * <li>Drives to the near right rocket</li>
 * <li>Scores the hatch on mid</li>
 * </ol>
 * @author Bryce G.
 */
public class CGHatchRightHighNearRocketLowNearRocketMid extends CommandGroup {
  double[][] valuesPID = pathway.valuesPID;

  public CGHatchRightHighNearRocketLowNearRocketMid() {
    
    addSequential(new CommonHighRightToNearRocketLow());
    
    addSequential(new autoTankDrive(-0.6, -0.5, 55000, 0, "left", IdleMode.kCoast));
    addParallel(new autoTurnOnLimeLightLED());
    addSequential(new autoTankDrive(-0.6, -0.2, 80000, 0, "left", IdleMode.kBrake));
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.05));
    addSequential(new autoTankDrive(0.1, 0.5, 0, 55000, "rightVision", IdleMode.kCoast));
    addSequential(new autoPIDVisionDrive(4, 0.08, 0.06, 0.7));
    addSequential(new autoLiftToPosition(Robot.Lift.hatchMidScorePosition));
    // addSequential(new CommonScoreHatch());
    addSequential(new autoEndAuto());
  }
}