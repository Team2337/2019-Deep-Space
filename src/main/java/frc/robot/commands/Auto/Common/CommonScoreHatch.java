package frc.robot.commands.Auto.Common;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Auto.autoWait;
import frc.robot.commands.HatchBeak.hatchBeakClose;
import frc.robot.commands.HatchLauncher.hatchLauncherExtend;
import frc.robot.commands.HatchLauncher.hatchLauncherRetract;

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
public class CommonScoreHatch extends CommandGroup {
  public CommonScoreHatch() {
    addSequential(new hatchLauncherExtend());
    addSequential(new autoWait(0.75));
    addSequential(new hatchBeakClose());
    addSequential(new autoWait(0.1));
    addSequential(new hatchLauncherRetract());
  }
}
