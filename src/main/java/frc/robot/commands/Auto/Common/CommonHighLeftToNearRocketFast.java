package frc.robot.commands.Auto.Common;

import frc.robot.commands.Chassis.*;
import frc.robot.commands.HatchBeak.hatchBeakClose;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;

/**
 * @category Common Command Group
 * Drives off the left lvl2 towards the left near rocket, and then continues back to the left load station to intake the hatch
 * This is the fast version of the original command
 * <br/>
 * <p>Command Steps:</p>
 * <ol>
 *  <li>Drive off left lvl2</li>
 *  <li>Drive to the near left rocket</li>
 *  <li>Score the hatch on the bottom</li>
 *  <li>Drive to the left load station with vision</li>
 *  <li>Intake hatch</li>
 *  <li>End command</li>
 * </ol>
 * 
 * @author Bryce G.
 */
public class CommonHighLeftToNearRocketFast extends CommandGroup {
    public CommonHighLeftToNearRocketFast() {

    addSequential(new autoTurnOnLimeLightLED());
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 1.0));
    // addSequential(new driveAtSetSpeed(-0.4));
    addSequential(new driveAtSpeedToAngle(0, 0.5, 200000, 0.7));
    addSequential(new autoWait(0.25));

    addSequential(new autoPIDVisionDrive(3, 0.07, 0.03, 0.7));
    addSequential(new autoResetEncoders());
    addSequential(new CommonScoreHatch());
    
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    addSequential(new autoTankDrive(-1.0, -0.1, -23000, 0, "left", IdleMode.kCoast));
    addSequential(new autoTurnOnLimeLightLED());
    addSequential(new autoResetEncoders());
    addSequential(new autoTankDrive(0.1, 1.0, 0, 16000, "rightVision", IdleMode.kCoast));
    // addSequential(new autoResetSensors());
    addParallel(new hatchBeakClose());
    addSequential(new autoPIDVisionDrive(4.5, 0.09, 0.06, 0.75));
    addSequential(new CommonIntakeHatch());
    addSequential(new autoResetEncoders()); 
    addSequential(new autoWait(0.05));

    }
}