package frc.robot.commands.Auto.Common;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;


/**
 * @category Common Command Group
 * Drives from the right load station with a hatch to the back side of the rocket to score the second hatch
 * This is the fast version of this command
 * <br/>
 * <p>Command Steps:</p>
 * <ol>
 *  <li>Drive from right load station backwards around the right rocket</li>
 *  <li>Turn towards the low hatch scoring position</li>
 *  <li>End command</li>
 *  <li>Waits for driver input to score</li>
 * </ol>
 * 
 * @author Bryce G.
 */
public class CommonOmniLeftLoadToFarRocketLow extends CommandGroup {
  public CommonOmniLeftLoadToFarRocketLow() {
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.05));
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
     //-145 -133 -148 -166
    addSequential(new autoTankDriveWithGyroAndEncoder(145, -0.64, -0.8, 0, 65000, "right", IdleMode.kCoast));
    addSequential(new autoTankDriveWithGyroAndEncoder(133, -0.84, -0.64, 108000, 0, "left", IdleMode.kCoast));
    addParallel(new autoTurnOnLimeLightLED());
    addSequential(new autoTankDriveWithGyroAndEncoder(148, -0.65, -0.53, 160000, 0, "left", IdleMode.kBrake));
    addSequential(new autoTurnToDegreeOmni(0.02, 0, 0, 118, 2, 5)); //-118
     /*
    addSequential(new autoTankDrive(-0.8, -0.64, 55000, 0, "left", IdleMode.kCoast));
    addSequential(new autoTankDrive(-0.64, -0.85, 0, 108000, "right", IdleMode.kCoast));
    addParallel(new autoTurnOnLimeLightLED());
    addSequential(new autoTankDrive(-0.53, -0.65, 0, 160000, "right", IdleMode.kBrake));
    addSequential(new autoTurnToDegreeOmni(0.02, 0, 0, -118, 2, 5)); //-118
    // addSequential(new autoTankDrive(-0.27, 0.32, 177500, 0, "leftVision", IdleMode.kBrake));
    addSequential(new autoPIDVisionDriveOmni(4, 0.1, 0.05, 0.6));
    // addSequential(new CommonScoreHatch());
    */
    
  }
}