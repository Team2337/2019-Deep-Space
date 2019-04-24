
package frc.robot.commands.Auto.Common;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;
import frc.robot.commands.HatchBeak.*;
    
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
public class CommonOmniRightToNearRocketLow extends CommandGroup {
  public CommonOmniRightToNearRocketLow() {
    addSequential(new autoTurnOnLimeLightLED());
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    addSequential(new autoPIDVisionDriveOmni(3.2, 0.1, 0.002, 0.6));
    addSequential(new autoResetEncoders());
    addSequential(new CommonScoreHatch());

    //Go to load station
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    addSequential(new autoTankDrive(-0.05, -0.5, 0, -18000, "right", IdleMode.kCoast)); 
    addSequential(new autoTurnOnLimeLightLED());
    addSequential(new autoResetEncoders());
    addSequential(new autoTankDriveWithGyro(130, 0.3, 0.05, "left", IdleMode.kBrake)); //120 low
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.05));
    addParallel(new hatchBeakClose());
    addSequential(new autoTankDrive(0.45, 0.45, 0, 8000, "right", IdleMode.kCoast)); 
    addSequential(new autoTankDriveWithGyro(144, 0.5, 0.4, "left", IdleMode.kCoast)); //144 low
    addSequential(new autoPIDVisionDriveOmni(4.5, 0.1, 0.002, 0.7)); //changed speed to 70 from 60
    addSequential(new CommonIntakeHatch());
    addSequential(new autoResetEncoders()); 
    addSequential(new autoWait(0.05));
  }
}