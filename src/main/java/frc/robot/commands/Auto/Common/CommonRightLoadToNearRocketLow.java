package frc.robot.commands.Auto.Common;

import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;


/**
 * @category Common Command Group
 * Drives from the right load station with a hatch to the near side of the rocket to score the second hatch
 * <br/>
 * <p>Command Steps:</p>
 * <ol>
 *  <li>Drive from right load station backwards</li>
 *  <li>Turn around to face the near face of the right rocket</li>
 *  <li>Lift to low hatch position</li>
 *  <li>End command</li>
 *  <li>Waits for driver input to score</li>
 * </ol>
 * 
 * @author Bryce G.
 */
public class CommonRightLoadToNearRocketLow extends CommandGroup {
    public CommonRightLoadToNearRocketLow() {
       
        addSequential(new autoTankDrive(-0.6, -0.5, 55000, 0, "left", IdleMode.kCoast));
    addParallel(new autoTurnOnLimeLightLED());
    addSequential(new autoTankDrive(-0.6, -0.2, 89000, 0, "left", IdleMode.kBrake));
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.05));
    addSequential(new autoTankDrive(0.1, 0.5, 0, 14000, "right", IdleMode.kCoast));
    addSequential(new autoTankDrive(0.1, 0.5, 0, 55000, "rightVision", IdleMode.kCoast));
    addSequential(new autoPIDVisionDrive(4, 0.08, 0.08, 0.7));
    addSequential(new autoLiftToPosition(Robot.Lift.hatchLowScorePosition));
    // addSequential(new CommonScoreHatch());

    }
}