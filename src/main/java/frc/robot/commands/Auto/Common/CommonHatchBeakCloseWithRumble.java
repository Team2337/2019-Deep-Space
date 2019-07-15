package frc.robot.commands.Auto.Common;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Auto.*;
import frc.robot.commands.HatchBeak.hatchBeakClose;
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
public class CommonHatchBeakCloseWithRumble extends CommandGroup {
    public CommonHatchBeakCloseWithRumble() {
    addSequential(new hatchBeakClose());
    addParallel(new autoSetRumble());
  }
}
