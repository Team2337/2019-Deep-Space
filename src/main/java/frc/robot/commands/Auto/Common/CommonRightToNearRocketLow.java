
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
public class CommonRightToNearRocketLow extends CommandGroup {
  public CommonRightToNearRocketLow() {
    addSequential(new autoTurnOnLimeLightLED());
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    addSequential(new autoPIDVisionDrive(3.2, 0.07, 0.03, 0.6));
    addSequential(new autoResetEncoders());
    addSequential(new CommonScoreHatch());

    //Go to load station
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    addSequential(new autoTankDrive(-0.1, -0.6, 0, -25000, "right", IdleMode.kCoast)); 
    addSequential(new autoTurnOnLimeLightLED());
    addSequential(new autoResetEncoders());
    addSequential(new autoTankDrive(0.6, 0.1, 16000, 0, "leftVision", IdleMode.kCoast)); 
    // addSequential(new autoResetSensors());
    addParallel(new hatchBeakClose());
    addSequential(new autoPIDVisionDrive(4.5, 0.95, 0.06, 0.7)); //changed speed to 70 from 60
    addSequential(new CommonIntakeHatch());
    addSequential(new autoResetEncoders()); 
    addSequential(new autoWait(0.05));
  }
}