package frc.robot.commands.Auto.CommandGroups;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
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
public class CGOmniHatchMiddleShipFrontTurnLeft extends CommandGroup {
  public CGOmniHatchMiddleShipFrontTurnLeft() {
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    
    addSequential(new autoTurnOnLimeLightLED());
    addSequential(new autoPIDVisionDrive(3.5, 0.07, 0.015, 0.6));
    addSequential(new CommonScoreHatch());
    addSequential(new autoTankDrive(-0.5, -0.5, 5000, 0, "left", IdleMode.kBrake));
    addSequential(new autoTurnToDegreeOmni(0.1, 0, 0, 90, 2));
    addSequential(new autoEndAuto());
 
  }
}