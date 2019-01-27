package frc.robot.commands.HatchBeak;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Auto.autoWait;
import frc.robot.commands.HatchPuncher.hatchPunchExtend;
import frc.robot.commands.HatchPuncher.hatchPunchRetract;

/**
 * It will retract the hatch beak then it will punch it with the pnuematics
 * launching it
 * 
 * @author Hunter B
 */
public class CGlaunchHatch extends CommandGroup {

  // CONSTRUCTOR
  public CGlaunchHatch() {
    addSequential(new hatchBeakRetract());
    addSequential(new hatchPunchExtend());
    addSequential(new autoWait(1));
    addSequential(new hatchPunchRetract());
  }

}
