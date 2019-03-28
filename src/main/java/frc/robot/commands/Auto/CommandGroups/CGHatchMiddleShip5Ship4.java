package frc.robot.commands.Auto.CommandGroups;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;
import frc.robot.commands.Auto.Common.*;
import frc.robot.commands.HatchBeak.*;

/**
 * Auton Description:
 * <ol>
 * <li>Starts with hatch in the middle of the lvl 1 platform</li>
 * <li>Drives straight to Cargo Ship Bay 4</li>
 * <li>Scores the hatch</li>
 * <li>Drives to the right load station</li>
 * <li>Intakes hatch</li>
 * <li>Drives back to Cargo Ship Bay 5</li>
 * <li>Scores the hatch</li>
 * </ol>
 * @author Bryce G.
 */
public class CGHatchMiddleShip5Ship4 extends CommandGroup {
  public CGHatchMiddleShip5Ship4() {
    double[][] values = pathway.valuesPID;
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    
    addSequential(new autoTurnOnLimeLightLED());
    addSequential(new autoPIDVisionDrive(3.5, 0.07, 0.015, 0.6));
    addSequential(new CommonScoreHatch());

    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.05));
    //TODO: take out brake when done
    addSequential(new autoTankDrive(-0.33, -0.6, 0, 135000, "right", IdleMode.kCoast));
    
    addParallel(new autoTurnOnLimeLightLED());
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.05));
    addParallel(new hatchBeakClose());
    addSequential(new autoTankDrive(0.7, 0.6, 30000, 0, "leftVision", IdleMode.kCoast));
    addSequential(new autoPIDVisionDrive(5, 0.07, 0.015, 0.6));
    addSequential(new CommonIntakeHatch());
    
    addSequential(new autoResetEncoders()); 
    addSequential(new autoWait(0.05));
    addSequential(new autoTankDrive(-0.4, -0.5, 0, 95000, "right", IdleMode.kCoast));
    addParallel(new autoTurnOnLimeLightLED());
    addSequential(new autoTankDrive(-0.15, -0.5, 0, 145000, "right", IdleMode.kBrake));
    addSequential(new autoPIDVisionDrive(5, 0.07, 0.015, 0.6));
    addSequential(new CommonScoreHatch());
 
  }
}