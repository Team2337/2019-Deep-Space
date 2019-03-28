package frc.robot.commands.Auto.CommandGroups;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.Common.*;
import frc.robot.commands.Auto.*;
import frc.robot.commands.HatchBeak.hatchBeakClose;

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
public class CGHatchRightLowNearRocketLowFarRocketLow extends CommandGroup {
  double[][] valuesPID = pathway.valuesPID;

  public CGHatchRightLowNearRocketLowFarRocketLow() {
    
    addSequential(new autoTurnOnLimeLightLED());
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition + 15, 0.5));
    addSequential(new autoPIDVisionDrive(3, 0.07, 0.03, 0.6));
    addSequential(new autoResetEncoders());
    addSequential(new CommonScoreHatch());
    
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    addSequential(new autoTankDrive(-0.1, -0.6, 0, -25000, "right", IdleMode.kCoast));
    addSequential(new autoTurnOnLimeLightLED());
    addSequential(new autoResetEncoders());
    addSequential(new autoTankDrive(0.6, 0.1, 16000, 0, "leftVision", IdleMode.kCoast));
    // addSequential(new autoResetSensors());
    addParallel(new hatchBeakClose());
    addSequential(new autoPIDVisionDrive(4, 0.09, 0.06, 0.6));
    addSequential(new CommonIntakeHatch());
    addSequential(new autoResetEncoders()); 
    addSequential(new autoWait(0.05));
    
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition + 15, 0.5));
    addSequential(new autoTankDrive(-0.8, -0.74, 65000, 0, "left", IdleMode.kCoast));
    addSequential(new autoTankDrive(-0.75, -0.85, 0, 113000, "right", IdleMode.kCoast));
    addSequential(new autoTankDrive(-0.45, -0.65, 0, 145000, "right", IdleMode.kCoast));
    addSequential(new autoTankDrive(-0.27, 0.32, 170000, 0, "left", IdleMode.kBrake));
    addSequential(new autoPIDVisionDrive(4, 0.08, 0.06, 0.6));
    addSequential(new CommonScoreHatch());
  }
}