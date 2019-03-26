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
public class CGHatchRightLowFarRocketLowNearRocketLow extends CommandGroup {
  public CGHatchRightLowFarRocketLowNearRocketLow() {
    double[][] values = pathway.valuesPID;
    // addSequential(new autoWait(Robot.delayChooser.getSelected()));
    addSequential(new autoTurnOnLimeLightLED());
    // addSequential(new autoSetPath(Robot.driveOffRightLvl2ToRightRocketT, values[0], 0, 30, false));
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition + 15, 0.5));
    addSequential(new autoSetPathReverse(Robot.driveOffRightLvl1ToBackRightRocketT, values[5], 0, 10));
    addSequential(new autoReadAngle());
    addSequential(new autoResetSensors());
    addSequential(new autoPIDVisionDrive(3, 0.11, 0.05, 0.6));

    addSequential(new CommonScoreHatch());
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.05));
    addSequential(new autoDriveToEncoderTick(-1, 20000, 0.5, 0, false, 0));
    addSequential(new autoTurnToDegree(0.05, 0, 0, (-Robot.autonAngle-5), 1.3));
    addSequential(new autoResetEncoders());   
    addSequential(new autoWait(0.05)); 
    addSequential(new autoTurnOnLimeLightLED());
    addSequential(new autoDriveToEncoderTick(1, 50000, 0.7, -0.25, true, 0.5)); 
    addSequential(new autoResetEncoders());
    addSequential(new autoPIDVisionDrive(6.5, 0.07, 0.015, 0.6));
    addSequential(new autoResetEncoders());
    addSequential(new CommonIntakeHatch());
    // addSequential(new hatchBeakOpen());
    // addSequential(new autoLiftToPosition((Robot.Lift.hatchLowScorePosition + 130)));
    addSequential(new autoResetEncoders()); 
    addSequential(new autoWait(0.05));
    addSequential(new autoDriveToEncoderTick(-1, 30000, 0.5, 0, false, 0));
    
  }
}