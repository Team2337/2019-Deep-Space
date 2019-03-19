package frc.robot.commands.Auto.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.pathway;
import frc.robot.commands.Auto.autoResetEncoders;
import frc.robot.commands.Auto.setpaths.autoSetPath;
import frc.robot.commands.Auto.setpaths.autoSetPathReverse;
import frc.robot.commands.Auto.autoWaitSensorReset;

/**
 * Two hatch auto, starting on the right side of the platform backwards, 
 * running a JTurn at the end for the final hatch
 * @author Bryce G.
 */
public class CGJTurnFromLoadToCargoShipRight extends CommandGroup {
  double[][] valuesPID = pathway.valuesPID;

  public CGJTurnFromLoadToCargoShipRight() {
    addSequential(new autoSetPathReverse(Robot.initTrajectory, valuesPID[0], 2));
    addSequential(new autoResetEncoders());
    addSequential(new autoWaitSensorReset(0.3));
    // addSequential(new autoSetPath(Robot.curveFromToHatchRightT, valuesPID[1], 2));
    addSequential(new autoResetEncoders());
    addSequential(new autoWaitSensorReset(0.5));
    addSequential(new autoSetPathReverse(Robot.fromRightLoadJTurnToCargoShipT, valuesPID[2], 2));
    addSequential(new autoResetEncoders());
    addSequential(new autoWaitSensorReset(0.5));
    // addSequential(new autoSetPath(Robot.jTurnToCargoShipRightT, valuesPID[3], 2));
    
  }
}