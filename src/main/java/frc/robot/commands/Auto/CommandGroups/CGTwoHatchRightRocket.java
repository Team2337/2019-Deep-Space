package frc.robot.commands.Auto.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;
import frc.robot.commands.Auto.Common.CommonIntakeHatch;
import frc.robot.commands.Auto.Common.CommonScoreHatch;
import frc.robot.commands.Auto.paths.PathwayRightRocketTwoHatch;
import frc.robot.commands.Auto.setpaths.*;
import frc.robot.commands.HatchBeak.*;
import frc.robot.commands.HatchLauncher.*;

/**
 * Driving with the limelight after the paths have finished
 * Turns the limelight on before reaching the end of the path
 * @author Bryce G.
 */
public class CGTwoHatchRightRocket extends CommandGroup {
  public CGTwoHatchRightRocket() {
    double[][] values = pathway.valuesPID;
    // addSequential(new autoWait(Robot.delayChooser.getSelected()));
    addSequential(new autoTurnOnLimeLightLED());
    // addSequential(new autoSetPath(Robot.driveOffRightLvl2ToRightRocketT, values[0], 0, 30, false));
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    addSequential(new autoSetPathReverse(Robot.driveOffRightLvl2ToBackRightRocketT, values[0], 0, 15));
    addSequential(new autoReadAngle());
    addSequential(new autoResetSensors());
    addSequential(new autoPIDVisionDrive(0.05, 3, 0.12, 0.025, 0.6, 8.5));
    addSequential(new CommonScoreHatch());
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.05));
    addSequential(new autoDriveToEncoderTick(-1, 20000, 0.5, 0));
    addSequential(new autoTurnToDegree(0.05, 0, 0, -Robot.autonAngle, 1));
    addSequential(new autoResetEncoders());   
    addSequential(new autoWait(0.05)); 
    addSequential(new autoDriveToEncoderTick(1, 40000, 0.5, -0.25)); 
    addSequential(new autoResetEncoders());
    addSequential(new autoPIDVisionDrive(0.05, 5.5, 0.09, 0.025, 0.6, 11));
    addSequential(new autoResetEncoders());
    addSequential(new CommonIntakeHatch());
    // addSequential(new hatchBeakOpen());
    // addSequential(new autoLiftToPosition((Robot.Lift.hatchLowScorePosition + 130)));
    addSequential(new autoDriveToEncoderTick(-1, 50, 0.5, 0));
  }
}