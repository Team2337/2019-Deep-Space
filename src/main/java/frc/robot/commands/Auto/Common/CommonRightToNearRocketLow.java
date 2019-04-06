
package frc.robot.commands.Auto.Common;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;
import frc.robot.commands.Chassis.driveAtSetSpeed;
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
    public class CommonRightToNearRocketLow extends CommandGroup {
      public CommonRightToNearRocketLow() {
        addSequential(new autoTurnOnLimeLightLED());
        addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
        addSequential(new autoPIDVisionDrive(3.2, 0.07, 0.03, 0.6));
        addSequential(new autoResetEncoders());
        addSequential(new CommonScoreHatch());

        //Go to load station
        addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
        addSequential(new autoTankDrive(-0.1, -0.6, 0, -25000, "right", IdleMode.kCoast)); 
        addSequential(new autoTurnOnLimeLightLED());
        addSequential(new autoResetEncoders());
        addSequential(new autoTankDrive(0.6, 0.1, 16000, 0, "leftVision", IdleMode.kCoast)); 
        // addSequential(new autoResetSensors());
        addParallel(new hatchBeakClose());
        addSequential(new autoPIDVisionDrive(4.5, 0.95, 0.06, 0.7)); //changed speed to 70 from 60
        addSequential(new CommonIntakeHatch());
        addSequential(new autoResetEncoders()); 
        addSequential(new autoWait(0.05));
      }
    }