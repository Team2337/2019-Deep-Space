package frc.robot.commands.Auto.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Auto.*;
import frc.robot.commands.Auto.Common.*;

/**
 * Auton Description:
 * <ol>
 * <li>Starts with hatch in the middle of the lvl 1 platform</li>
 * <li>Drives straight to Cargo Ship Bay 4</li>
 * <li>Scores the hatch</li>
 * <li>Drives to the right load station</li>
 * <li>Intakes hatch</li>
 * <li>Drives back to Cargo Ship Bay 5</li>
 * <li>Scores the hatch</li>
 * </ol>
 * @author Bryce G.
 */
public class CGHatchMiddleShip4NearRocketLow extends CommandGroup {
  public CGHatchMiddleShip4NearRocketLow() {
   
    addSequential(new CommonMiddleToShip4());
    addSequential(new CommonLeftLoadToNearRocketLow());
    
    // addSequential(new CommonScoreHatch());
    addSequential(new autoEndAuto());
  }
}