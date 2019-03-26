package frc.robot.commands.Auto.CommandGroups;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.pathway;
import frc.robot.commands.Auto.Common.CommonIntakeHatch;
import frc.robot.commands.Auto.Common.CommonScoreHatch;
import frc.robot.commands.Auto.paths.PathwaySideTwoHatchFromRight;
import frc.robot.commands.Auto.*;
import frc.robot.commands.Auto.setpaths.autoSetPath;
import frc.robot.commands.Auto.setpaths.autoSetPathReverse;
import frc.robot.commands.Chassis.driveAtSetSpeed;
import frc.robot.commands.HatchBeak.hatchBeakClose;
import frc.robot.commands.Auto.autoWaitSensorReset;

/**
 * Two hatch auto, starting on the right side of the platform backwards, 
 * running a JTurn at the end for the final hatch
 * @author Bryce G.
 */
public class CGHatchRightLowNearRocketLowNearRocketMid extends CommandGroup {
  double[][] valuesPID = pathway.valuesPID;

  public CGHatchRightLowNearRocketLowNearRocketMid() {
    
    addSequential(new autoTurnOnLimeLightLED());
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition + 15, 0.5));
    addSequential(new autoPIDVisionDrive(3, 0.11, 0.05, 0.6));
    addSequential(new autoResetEncoders());
    addSequential(new CommonScoreHatch());
    
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    addSequential(new autoTankDrive(-0.1, -0.6, 0, -25000, "right", IdleMode.kCoast));
    addSequential(new autoTurnOnLimeLightLED());
    addSequential(new autoResetEncoders());
    addSequential(new autoTankDrive(0.6, 0.1, 16000, 0, "leftVision", IdleMode.kCoast));
    // addSequential(new autoResetSensors());
    addParallel(new hatchBeakClose());
    addSequential(new autoPIDVisionDrive(4, 0.07, 0.06, 0.6));
    addSequential(new CommonIntakeHatch());
    addSequential(new autoResetEncoders()); 
    addSequential(new autoWait(0.05));
    
    addSequential(new autoTankDrive(-0.5, -0.4, 55000, 0, "left", IdleMode.kCoast));
    addParallel(new autoTurnOnLimeLightLED());
    addSequential(new autoTankDrive(-0.5, -0.1, 80000, 0, "left", IdleMode.kBrake));
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.05));
    addSequential(new autoTankDrive(0.1, 0.5, 0, 55000, "rightVision", IdleMode.kCoast));
    addSequential(new autoPIDVisionDrive(4, 0.08, 0.06, 0.6));
    addSequential(new CommonScoreHatch());
  }
}