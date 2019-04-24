package frc.robot.commands.HatchBeak;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Auto.autoWait;
import frc.robot.commands.HatchLauncher.*;

/**
 * 1. Closes beak to avoid damaging it
 * 2. Launches the hatch away from the robot
 * 3. Waits one second (adjust accordingly)
 * 4. Retracts the launching mechanism
 * 
 * @author Hunter B
 */
public class CGNewScoreHatch extends CommandGroup {

  // CONSTRUCTOR
  public CGNewScoreHatch() {
    addSequential(new hatchLauncherExtend());
    addSequential(new autoWait(0.5));
    addSequential(new hatchBeakClose());
  }
}
