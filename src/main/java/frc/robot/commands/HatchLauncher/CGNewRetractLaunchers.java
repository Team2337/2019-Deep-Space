package frc.robot.commands.HatchLauncher;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Auto.*;
import frc.robot.commands.HatchBeak.*;

/**
 * @category Common Command Group
 * <p><br/></p>
 * This command scores the hatch by:
 * <ol>
 *  <li>Retracting the launcher</li>
 *  <li>Setting the beak to beak position</li>
 *  <li>Retracting the launchers</li>
 * </ol>
 * 
 * @author Bryce G.
 */
public class CGNewRetractLaunchers extends CommandGroup {
  public CGNewRetractLaunchers() {
    addSequential(new hatchLauncherRetract());
    addSequential(new autoWait(0.5));
    addSequential(new hatchBeakOpen());
    addSequential(new autoWait(135));
  }
}
