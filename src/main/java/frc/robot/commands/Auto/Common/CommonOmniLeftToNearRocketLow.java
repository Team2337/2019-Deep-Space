
package frc.robot.commands.Auto.Common;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;
import frc.robot.commands.HatchBeak.*;
import frc.robot.commands.HatchLauncher.hatchLauncherExtend;
    
/**
 * @category Common Command Group
 * Drives off of lvl1 to the near right rocket to score a hatch, then goes to the right load station to intake another hatch
 * This command start <strong>LOW</strong>
 * <br/>
 * <p>Command Steps:</p>
 * <ol>
 *  <li>Drive off lvl1 on the right side</li>
 *  <li>Drive to the near right rocket</li>
 *  <li>Score the hatch</li>
 *  <li>Drive back to the right load station with vision</li>
 *  <li>Intake second hatch</li>
 *  <li>End command</li>
 * </ol>
 * 
 * @author Bryce G.
 */
public class CommonOmniLeftToNearRocketLow extends CommandGroup {
  public CommonOmniLeftToNearRocketLow() {
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    addSequential(new autoTurnOnLimeLightLED());
    addParallel(new hatchLauncherExtend());
    addSequential(new autoPIDVisionDriveOmni(3.7, 0.1, 0.002, 0.6));
    addSequential(new autoResetEncoders());
    addSequential(new hatchBeakClose());
    addSequential(new autoWait(0.1));
    addSequential(new autoTankDrive(-0.7, -0.7, -8000, 0, "left", IdleMode.kCoast)); //4000

    //Go to load station
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    addSequential(new autoTurnOnLimeLightLED());
    /*
    addSequential(new autoTankDrive(-0.5, -0.05, -22000, 0, "left", IdleMode.kCoast)); 
    addSequential(new autoTankDriveWithGyro(-120, 0.05, 0.3, "right", IdleMode.kBrake)); //120 low
    */
    addSequential(new autoTurnToDegreeOmni(0.1, 0, 0, -120, 1));
    addParallel(new hatchBeakClose());
    // addSequential(new autoTankDrive(0.45, 0.45, 8000, 0, "left", IdleMode.kCoast)); 
    // addSequential(new autoTankDriveWithGyro(-138, 0.4, 0.5, "right", IdleMode.kCoast)); //144 low
    addSequential(new hatchLauncherExtend());
    addSequential(new autoPIDVisionDriveOmni(4.5, 0.1, 0.002, 0.7)); //changed speed to 70 from 60
    addSequential(new CommonIntakeHatch());
    addSequential(new autoResetEncoders()); 
    addSequential(new autoWait(0.05));
  }
}