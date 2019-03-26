package frc.robot.commands.Auto.Common;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Auto.autoWait;
import frc.robot.commands.HatchBeak.hatchBeakClose;
import frc.robot.commands.HatchLauncher.hatchLauncherExtend;
import frc.robot.commands.HatchLauncher.hatchLauncherRetract;

/**
 * This command scores the hatch by setting the beak to beak position</li>
 * 
 * @category Common Command Group
 * @author Bryce G.
 */
public class CommonScoreHatchWithoutExtend extends CommandGroup {
  public CommonScoreHatchWithoutExtend() {
    addSequential(new hatchBeakClose());
    addSequential(new autoWait(0.15));
    addSequential(new hatchLauncherRetract());
    addSequential(new autoWait(0.25));
  }
}
