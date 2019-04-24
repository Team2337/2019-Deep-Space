package frc.robot.commands.Auto.CommandGroups;

import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Auto.*;
import frc.robot.commands.Auto.Common.*;

/**
 * Auton Description:
 * <ol>
 * <li>Starts with hatch in the middle of the lvl 1 platform</li>
 * <li>Drives straight to Cargo Ship Bay 4</li>
 * <li>Scores the hatch</li>
 * <li>Drives to the right load station</li>
 * <li>Intakes hatch</li>
 * <li>Drives back to Cargo Ship Bay 5</li>
 * <li>Scores the hatch</li>
 * </ol>
 * @author Bryce G.
 */
public class CGHatchRightLowShip7Ship6 extends CommandGroup {
  public CGHatchRightLowShip7Ship6() {
   
    // addSequential(new autoDriveWithPitch(-.75, 30000, 5, 0));
    addSequential(new autoTankDrive(-.6, -.6, 35000, 0, "left", IdleMode.kCoast));
    addSequential(new autoTankDrive(-.48, -.7, 0, 125000, "right", IdleMode.kBrake));
    addSequential(new autoTurnOnLimeLightLED());
    addSequential(new autoTurnToDegree(0.04, 0, 0, -85, 1.5));
    addSequential(new autoPIDVisionDrive(3.5, 0.09, 0.03, 0.6));
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.05));
    addSequential(new autoTankDrive(-.5, -.5, 22000, 0, "left", IdleMode.kBrake));
    addParallel(new autoTurnOnLimeLightLED());
    addSequential(new autoTurnToDegree(0.04, 0, 0, 13, 2));
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.05));
    addSequential(new autoTankDrive(.5, .6, 0, 30000, "right", IdleMode.kCoast));
    addSequential(new autoTankDrive(.5, .6, 0, 60000, "rightVision", IdleMode.kCoast));
    addSequential(new autoPIDVisionDrive(4, 0.09, 0.03, 0.6));
    addSequential(new CommonIntakeHatch());
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.05));
    addSequential(new autoTankDrive(-.6, -.5, 30000, 0, "left", IdleMode.kCoast));
    addSequential(new autoTankDrive(-.6, -.5, 60000, 0, "left", IdleMode.kBrake));
    // addSequential(new CommonScoreHatch());
    addSequential(new autoEndAuto());
  }
}