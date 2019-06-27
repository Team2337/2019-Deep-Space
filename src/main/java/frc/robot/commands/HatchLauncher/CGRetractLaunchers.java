package frc.robot.commands.HatchLauncher;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Auto.*;
import frc.robot.commands.HatchBeak.*;

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
public class CGRetractLaunchers extends CommandGroup {
  public CGRetractLaunchers() {
    addSequential(new hatchBeakClose());
    addSequential(new autoWait(1));
    addSequential(new hatchLauncherRetract());
    addSequential(new autoWait(135));
  }
}
