
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
    public class CommonHighRightToNearRocketLowFast extends CommandGroup {
    public CommonHighRightToNearRocketLowFast() {
        addSequential(new autoTurnOnLimeLightLED());
        
        //Go to load station
        addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
        addSequential(new autoTankDrive(-0.1, -1.0, 0, -25000, "right", IdleMode.kCoast));
        addSequential(new autoTurnOnLimeLightLED());
        addSequential(new autoResetEncoders());
        addSequential(new autoTankDrive(1.0, 0.1, 16000, 0, "leftVision", IdleMode.kCoast));

        
        addParallel(new hatchBeakClose());
        addSequential(new autoPIDVisionDrive(4.5, 0.09, 0.06, 0.75));
        addSequential(new CommonIntakeHatch());
        addSequential(new autoResetEncoders()); 
        addSequential(new autoWait(0.05));
        
      }
    }