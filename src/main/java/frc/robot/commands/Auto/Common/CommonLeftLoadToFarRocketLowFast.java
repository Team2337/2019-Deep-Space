package frc.robot.commands.Auto.Common;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;
import frc.robot.commands.HatchLauncher.*;

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
public class CommonLeftLoadToFarRocketLowFast extends CommandGroup {
  public CommonLeftLoadToFarRocketLowFast() {
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    addSequential(new autoTankDrive(-0.64, -0.8, 0, 55000, "right", IdleMode.kCoast));
    addSequential(new autoTankDrive(-0.85, -0.64, 108000, 0, "left", IdleMode.kCoast));
    addParallel(new autoTurnOnLimeLightLED());
    addSequential(new autoTankDrive(-0.65, -0.54, 153000, 0, "left", IdleMode.kCoast)); //changed from 0.51 & 155000 forest hills match 1
    addSequential(new autoTankDrive(0.32, -0.27, 0, 170000, "right", IdleMode.kBrake));
    addSequential(new hatchLauncherExtend());
    addSequential(new autoPIDVisionDrive(4, 0.09, 0.03, 0.6));
    // addSequential(new CommonScoreHatch());
  }
}