package frc.robot.commands.Auto.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.autoSetPath;
import frc.robot.commands.Auto.autoSetPathReverse;
import frc.robot.commands.Auto.autoWait;

/**
 * This is an example command group, put commands in here to run them in auton
 */
public class CGJTurnFromLoadToCargoShipRight extends CommandGroup {
  public CGJTurnFromLoadToCargoShipRight() {
    addSequential(new autoSetPathReverse(Robot.initTrajectory, 1.3, 0, 0.15, 0));
    addSequential(new autoWait(0.3));
    addSequential(new autoSetPath(Robot.curveFromToHatchRightT, 1.5, 0, 0, 0));
    addSequential(new autoWait(1));
    addSequential(new autoSetPathReverse(Robot.fromRightLoadJTurnToCargoShipT, 2, 0, 0, 0));
    // addSequential(new autoWait(2));
    // addSequential(new autoSetPath(Robot.jTurnToCargoShipRightT, 2, 0, 0, 0));
  }
}
