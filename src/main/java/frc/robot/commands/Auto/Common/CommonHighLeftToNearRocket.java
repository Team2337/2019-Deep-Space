package frc.robot.commands.Auto.Common;

import frc.robot.commands.Chassis.*;
import frc.robot.commands.HatchBeak.*;
import frc.robot.commands.HatchLauncher.*;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;

/**
 * @category Common Command Group
 * Drives off the left lvl2 towards the left near rocket, and then continues back to the left load station to intake the hatch
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
public class CommonHighLeftToNearRocket extends CommandGroup {
    public CommonHighLeftToNearRocket() {

    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 1.0));
    addSequential(new autoTurnOnLimeLightLED());
    addSequential(new driveAtSpeedToAngle(0, 0.1, 200000, 0.7)); //0.7
    addSequential(new autoWait(0.35));
    // addSequential(new autoTurnToDegree(0.05, 0, 0, 45, 1));
    // addSequential(new autoWait(0.25));
    addParallel(new hatchLauncherExtend());

    addSequential(new autoPIDVisionDrive(3.7, 0.09, 0.025, 0.7));
    addSequential(new autoResetEncoders());
    addSequential(new hatchBeakClose());
    addSequential(new autoWait(0.1));
    addSequential(new autoTankDrive(-0.7, -0.7, -4000, 0, "left", IdleMode.kCoast));
    addParallel(new hatchLauncherRetract());
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.05));
    
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    addSequential(new autoTankDrive(-0.6, -0.1, -23000, 0, "left", IdleMode.kCoast));
    addSequential(new autoTurnOnLimeLightLED());
    addSequential(new autoResetEncoders());
    addSequential(new autoTankDrive(0.2, 0.6, 0, 16000, "right", IdleMode.kCoast));
    addSequential(new autoTankDrive(0.1, 0.6, 0, 20000, "rightVision", IdleMode.kCoast));
    addParallel(new hatchLauncherExtend());
    addSequential(new hatchBeakClose());
    addSequential(new autoPIDVisionDrive(4.5, 0.09, 0.06, 0.7));
    addSequential(new CommonIntakeHatch());
    addSequential(new autoResetEncoders()); 
    addSequential(new autoWait(0.05));

    }
}