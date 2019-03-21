package frc.robot.commands.Auto.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.autoDriveToEncoderTick;
import frc.robot.commands.Auto.autoPIDVisionDrive;
import frc.robot.commands.Auto.autoResetEncoders;
import frc.robot.commands.Auto.autoTurnOnLimeLightLED;
import frc.robot.commands.Auto.autoTurnToDegree;
import frc.robot.commands.Auto.autoWait;
import frc.robot.commands.Auto.pathway;
import frc.robot.commands.Auto.setpaths.autoSetPath;
import frc.robot.commands.Auto.setpaths.autoSetPathReverse;
import frc.robot.commands.Auto.setpaths.autoSetPathReverseFull;

/**
 * Driving with the limelight after the paths have finished
 * Turns the limelight on before reaching the end of the path
 * @author Bryce G.
 */
public class CGTwoDrives extends CommandGroup {
  public CGTwoDrives() {
    double[][] values = pathway.valuesPID;
    addSequential(new autoTurnOnLimeLightLED());
    // addSequential(new autoSetPath(Robot.driveOffRightLvl2ToRightRocketT, values[0], 0, 30, false));
    addSequential(new autoSetPathReverse(Robot.driveOffRightLvl2ToBackRightRocketT, values[0], 0, 90));
    addSequential(new autoResetEncoders());
    addSequential(new autoPIDVisionDrive(0.05, 0, 0, "", 3));
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(1));
    addSequential(new autoSetPathReverse(Robot.driveAwayFromBackRightRocketT, values[0], 0, 0));
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(1));
    addSequential(new autoDriveToEncoderTick(1, 50)); //drives forward to 50 inches
    addSequential(new autoResetEncoders());
    addSequential(new autoPIDVisionDrive(0.05, 0, 0, "", 7));

    /*
    addSequential(new autoTurnToAngle(-5));
    */
    // addSequential(new autoResetEncoders());
    // addSequential(new autoWait(5));
    // addSequential(new autoSetPathReverseFull(Robot.driveForwardT, values[0], 0));
    // addSequential(new autoTurnToDegree(0.2, 0, 0, 90, 5));
  }
}