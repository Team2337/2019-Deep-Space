
package frc.robot.commands.Auto.Common;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;
import frc.robot.commands.Chassis.*;
import frc.robot.commands.HatchBeak.*;
    
    /**
     * @category Common Command Group
     * <p><br/></p>
     * This command intakes the hatch by:
     * <ol>
     *  <li>Raising the lift to 130 ticks above the intake position</li>
     *  <li>Setting the beak to star position</li>
     * </ol>
     * 
     * @author Bryce G.
     */
    public class CommonHighRightToNearRocketLow extends CommandGroup {
      public CommonHighRightToNearRocketLow() {
        addSequential(new autoTurnOnLimeLightLED());
        addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 1.0));
        // addSequential(new driveAtSetSpeed(0.4));
        addSequential(new driveAtSpeedToAngle(0, 0.5, 200000, 0.7));
        addSequential(new autoWait(0.25));

        addSequential(new autoPIDVisionDrive(3, 0.09, 0.03, 0.7)); //changed high p to 0.025 from 0.03
        addSequential(new autoResetEncoders());
        addSequential(new CommonScoreHatch());

        //Go to load station
        addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
        addSequential(new autoTankDrive(-0.1, -0.6, 0, -23000, "right", IdleMode.kCoast));
        addSequential(new autoTurnOnLimeLightLED());
        addSequential(new autoResetEncoders());
        addSequential(new autoTankDrive(0.6, 0.1, 16000, 0, "left", IdleMode.kCoast));
        addSequential(new autoTankDrive(0.6, 0.1, 20000, 0, "leftVision", IdleMode.kCoast));
        addParallel(new hatchBeakClose());
        addSequential(new autoPIDVisionDrive(4.5, 0.09, 0.06, 0.7)); //p values after practice matches 0.09, 0.06
        addSequential(new CommonIntakeHatch());
        addSequential(new autoResetEncoders()); 
        addSequential(new autoWait(0.05));
      }
    }