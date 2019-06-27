package frc.robot.commands.Auto.Common;

import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;


/**
 * @category Common Command Group
 * Drives from the load station to score the second hatch on the back of the rocket
 * <br/>
 * <p>Command Steps:</p>
 * <ol>
 *  <li>Drive backwards from load station left</li>
 *  <li>Vision into target</li>
 *  <li>End command</li>
 *  <li>Waits for driver input to score</li>
 * </ol>
 * 
 * @author Bryce G.
 */
public class CommonLeftLoadToNearRocketLow extends CommandGroup {
    public CommonLeftLoadToNearRocketLow() {
       
        addSequential(new autoTankDrive(-0.5, -0.6, 0, 55000, "right", IdleMode.kCoast));
        addParallel(new autoTurnOnLimeLightLED());
        addSequential(new autoTankDrive(-0.2, -0.6, 0, 80000, "right", IdleMode.kBrake));
        addSequential(new autoResetEncoders());
        addSequential(new autoWait(0.05));
        addSequential(new autoTankDrive(0.5, 0.1, 55000, 0, "leftVision", IdleMode.kCoast));
        addSequential(new autoPIDVisionDrive(4, 0.08, 0.06, 0.7));
        addSequential(new autoLiftToPositionWithWait(Robot.Lift.hatchMidScorePosition, 0));
        // addSequential(new CommonScoreHatch());
    }
}