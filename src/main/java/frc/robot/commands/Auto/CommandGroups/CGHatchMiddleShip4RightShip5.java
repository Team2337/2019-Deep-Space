package frc.robot.commands.Auto.CommandGroups;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;
import frc.robot.commands.Auto.Common.*;
import frc.robot.commands.Auto.setpaths.*;
import frc.robot.commands.HatchBeak.hatchBeakClose;

/**
 * Driving with the limelight after the paths have finished
 * Turns the limelight on before reaching the end of the path
 * @author Bryce G.
 */
public class CGHatchMiddleShip4RightShip5 extends CommandGroup {
  public CGHatchMiddleShip4RightShip5() {
    double[][] values = pathway.valuesPID;
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    /*
    addSequential(new autoTurnOnLimeLightLED());
    addSequential(new autoPIDVisionDrive(3.5, 0.07, 0.015, 0.6));
    addSequential(new CommonScoreHatch());
    */    
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.05));
    //TODO: take out brake when done
    addSequential(new autoTankDrive(-0.6, -0.33, 135000, 0, "left", IdleMode.kCoast));
    
    addParallel(new autoTurnOnLimeLightLED());
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.05));
    addParallel(new hatchBeakClose());
    addSequential(new autoTankDrive(0.6, 0.7, 0, 30000, "rightVision", IdleMode.kCoast));
    addSequential(new autoPIDVisionDrive(5, 0.07, 0.015, 0.65));
    addSequential(new CommonIntakeHatch());
    
    addSequential(new autoResetEncoders()); 
    addSequential(new autoWait(0.05));
    addSequential(new autoTankDrive(-0.6, -0.4, 95000, 0, "left", IdleMode.kCoast));
    addParallel(new autoTurnOnLimeLightLED());
    addSequential(new autoTankDrive(-0.5, -0.15, 145000, 0, "left", IdleMode.kBrake));
    addSequential(new autoPIDVisionDrive(5, 0.07, 0.015, 0.65));
    addSequential(new CommonScoreHatch());
    
  }
}