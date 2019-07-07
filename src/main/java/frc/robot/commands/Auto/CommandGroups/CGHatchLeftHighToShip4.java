package frc.robot.commands.Auto.CommandGroups;

import frc.robot.commands.Chassis.*;
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
public class CGHatchLeftHighToShip4 extends CommandGroup {
    public CGHatchLeftHighToShip4() {

    addSequential(new autoWait(4.0));
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 1.0));
    addSequential(new autoTurnOnLimeLightLED());
    addSequential(new driveAtSpeedToAngleBrake(0, 0.8, 200000, 0.7)); //0.7
    addSequential(new autoWait(0.35));
    addSequential(new autoTurnToDegree(0.05, 0, 0, -45, 1));
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.35));
    addSequential(new autoTankDrive(0.5, 0.5, 15000, 0, "left", IdleMode.kBrake));
    addSequential(new autoWait(0.35));
    addSequential(new autoTurnToDegree(0.05, 0, 0, 0, 1));
    addSequential(new autoWait(0.35));
    addParallel(new hatchLauncherExtend());
    addSequential(new autoPIDVisionDrive(3.7, 0.09, 0.03, 0.6));
    addSequential(new autoEndAuto());


    }
}