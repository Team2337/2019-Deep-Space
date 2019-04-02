package frc.robot.commands.Chassis;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Auto.*;


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
public class CGDriveToFarRocketFromLoadLeft extends CommandGroup {
  public CGDriveToFarRocketFromLoadLeft() {
    addSequential(new autoResetEncoders()); 
    addSequential(new autoWait(0.05));
    addSequential(new autoTankDrive(-0.61, -0.7, 60000, 0, "right", IdleMode.kCoast));
    addSequential(new autoTankDrive(-0.75, -0.61, 0, 110000, "left", IdleMode.kCoast));
    addSequential(new autoTankDrive(-0.65, -0.45, 0, 155000, "left", IdleMode.kCoast));
    addSequential(new autoTankDrive(0.3, -0.25, 165000, 0, "right", IdleMode.kBrake));
    addSequential(new autoWait(135));
  }
}
