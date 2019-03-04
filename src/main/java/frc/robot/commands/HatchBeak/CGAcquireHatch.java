package frc.robot.commands.HatchBeak;

import frc.robot.commands.HatchLauncher.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * <ol>
 * <li>Retracts the launcher (in case it's open)</li>
 * <li>Opens the beak to acquire a hatch panel</li>
 * </ol>
 * 
 * @author Hunter B.
 */
public class CGAcquireHatch extends CommandGroup {
  
  // CONSTRUCTOR
  public CGAcquireHatch() {
    addSequential(new hatchLauncherRetract());
    addSequential(new hatchBeakOpen());
  }
}
