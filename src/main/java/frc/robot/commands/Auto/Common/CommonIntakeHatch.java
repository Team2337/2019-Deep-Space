package frc.robot.commands.Auto.Common;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;
import frc.robot.commands.HatchBeak.*;

/**
 * @category Common Command Group
 * <p><br/></p>
 * This command intakes the hatch by:
 * <ol>
 *  <li>Raising the lift to 130 ticks above the intake position</li>
 *  <li>Setting the beak to star position</li>
 * </ol>
 * 
 * @author Bryce G.
 */
public class CommonIntakeHatch extends CommandGroup {
  public CommonIntakeHatch() {
    addSequential(new hatchBeakOpen());
  }
}
