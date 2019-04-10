package frc.robot.commands.Auto.Common;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;

/**
 * @category Common Command Group
 * <p><br/></p>
 * This command scores the hatch by:
 * <ol>
 *  <li>Extending the launcher</li>
 *  <li>Setting the beak to beak position</li>
 *  <li>Retracting the launchers</li>
 * </ol>
 * 
 * @author Bryce G.
 */
public class CommonRightLoadToFarRocketLowFast extends CommandGroup {
  public CommonRightLoadToFarRocketLowFast() {
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    addSequential(new autoTankDrive(-0.8, -0.64, 55000, 0, "left", IdleMode.kCoast));
    addSequential(new autoTankDrive(-0.64, -0.85, 0, 108000, "right", IdleMode.kCoast));
    addParallel(new autoTurnOnLimeLightLED());
    addSequential(new autoTankDrive(-0.51, -0.65, 0, 155000, "right", IdleMode.kCoast));
    addSequential(new autoTankDrive(-0.27, 0.32, 177500, 0, "left", IdleMode.kBrake));
    // addSequential(new autoPIDVisionDrive(4, 0.08, 0.06, 0.6));
    // addSequential(new CommonScoreHatch());
  }
}