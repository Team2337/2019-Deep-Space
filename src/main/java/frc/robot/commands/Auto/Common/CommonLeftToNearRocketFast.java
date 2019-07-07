package frc.robot.commands.Auto.Common;

import frc.robot.commands.HatchBeak.hatchBeakClose;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;


/**
 * @category Common Command Group
 * Drives off the left lvl1 towards the left near rocket, and then continues back to the left load station to intake the hatch
 * This is the fast version of this command
 * This command starts <strong>LOW</strong>
 * <br/>
 * <p>Command Steps:</p>
 * <ol>
 *  <li>Drive off left lvl1</li>
 *  <li>Drive to the near left rocket</li>
 *  <li>Score the hatch on the bottom</li>
 *  <li>Drive to the left load station with vision</li>
 *  <li>Intake hatch</li>
 *  <li>End command</li>
 * </ol>
 * 
 * @author Bryce G.
 */
public class CommonLeftToNearRocketFast extends CommandGroup {
    public CommonLeftToNearRocketFast() {

    addSequential(new autoTurnOnLimeLightLED());
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    addSequential(new autoPIDVisionDrive(3.2, 0.07, 0.02, 0.6));
    addSequential(new autoResetEncoders());
    addSequential(new CommonScoreHatch());
    
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    addSequential(new autoTankDrive(-0.6, -0.1, -23000, 0, "left", IdleMode.kCoast)); 
    addSequential(new autoTurnOnLimeLightLED());
    addSequential(new autoResetEncoders());
    addSequential(new autoTankDrive(0.1, 0.6, 0, 16000, "rightVision", IdleMode.kCoast)); 
    // addSequential(new autoResetSensors());
    addParallel(new hatchBeakClose());
    addSequential(new autoPIDVisionDrive(4, 0.09, 0.06, 0.7)); //changed speed to 70 from 60
    addSequential(new CommonIntakeHatch());
    addSequential(new autoResetEncoders()); 
    addSequential(new autoWait(0.05));

    }
}