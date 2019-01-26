package frc.robot.commands.HatchBeak;
import frc.robot.commands.HatchPuncher.*;
import edu.wpi.first.wpilibj.command.CommandGroup;


/**
 * This extends the hatch
 * 
 * @author Hunter B
 */
public class CGgrabHatch extends CommandGroup {

  // CONSTRUCTOR
  public CGgrabHatch() {
    addSequential(new hatchBeakExtend());
    addSequential(new hatchPunchRetract());
  }

}
