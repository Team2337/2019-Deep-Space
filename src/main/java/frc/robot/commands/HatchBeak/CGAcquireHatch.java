package frc.robot.commands.HatchBeak;

import frc.robot.commands.HatchLauncher.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 1. Retracts the launcher (in case it's open)
 * 2. Opens the beak to acquire a hatch panel
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
