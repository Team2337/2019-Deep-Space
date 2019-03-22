package frc.robot.commands.Auto.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;
import frc.robot.commands.Auto.Common.CommonScoreHatch;
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

    addSequential(new autoTurnOnLimeLightLED());
    // addSequential(new autoSetPath(Robot.driveOffRightLvl2ToRightRocketT, values[0], 0, 30, false));
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    addSequential(new autoSetPathReverse(Robot.driveOffRightLvl2ToBackRightRocketT, values[0], 0, 15));
    addSequential(new autoResetEncoders());
    addSequential(new autoPIDVisionDrive(0.05, 0, 0, "", 3, 0.05, 0.025));

    addSequential(new CommonScoreHatch());
    /* Code inside Score Hatch
    addSequential(new hatchLauncherExtend());
    addSequential(new autoWait(0.75));
    addSequential(new hatchBeakClose());
    addSequential(new autoWait(0.25));
    addSequential(new hatchLauncherRetract());
    addSequential(new autoWait(1));
    */

    /*
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(1));
    addSequential(new autoSetPathReverse(Robot.driveAwayFromBackRightRocketT, values[0], 0, 0));
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(1));
    addSequential(new autoDriveToEncoderTick(1, 50, 0.75)); //drives forward to 50 inches
    addSequential(new autoResetEncoders());
    addSequential(new autoPIDVisionDrive(0.05, 0, 0, "", 7, 0.09, 0.025));
    addSequential(new autoResetEncoders());
    
    addSequential(new hatchBeakOpen());
    addSequential(new autoLiftToPosition((Robot.Lift.hatchLowScorePosition + 130)));
    addSequential(new autoDriveToEncoderTick(-1, 50, 0.5));
    */
  }
}