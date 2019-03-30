package frc.robot.commands.Auto.CommandGroups;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.Common.*;
import frc.robot.commands.Chassis.driveAtSetSpeed;
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
public class CGHatchLeftHighNearRocketLowFarRocketLow extends CommandGroup {
  double[][] valuesPID = pathway.valuesPID;

  public CGHatchLeftHighNearRocketLowFarRocketLow() {
    
    addSequential(new CommonHighLeftToNearRocket());
    addSequential(new CommonLeftLoadToFarRocketLow());
  }
}