package frc.robot.commands.Auto.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.Pathway;
import frc.robot.commands.Auto.autoResetEncoders;
import frc.robot.commands.Auto.autoSetPath;
import frc.robot.commands.Auto.autoSetPathReverse;
import frc.robot.commands.Auto.autoWaitSensorReset;

/**
 * This is an example command group, put commands in here to run them in auton
 */
public class CGJTurnFromLoadToCargoShipRight extends CommandGroup {
  double[][] valuesPID = Pathway.valuesPID;

  public CGJTurnFromLoadToCargoShipRight() {
    addSequential(new autoSetPathReverse(Robot.initTrajectory, valuesPID[0]));
    addSequential(new autoResetEncoders());
    addSequential(new autoWaitSensorReset(0.3));
    addSequential(new autoSetPath(Robot.curveFromToHatchRightT, valuesPID[1]));
    addSequential(new autoResetEncoders());
    addSequential(new autoWaitSensorReset(0.5));
    addSequential(new autoSetPathReverse(Robot.fromRightLoadJTurnToCargoShipT, valuesPID[2]));
    addSequential(new autoResetEncoders());
    addSequential(new autoWaitSensorReset(0.5));
    addSequential(new autoSetPath(Robot.jTurnToCargoShipRightT, valuesPID[3]));
    
  }
}
