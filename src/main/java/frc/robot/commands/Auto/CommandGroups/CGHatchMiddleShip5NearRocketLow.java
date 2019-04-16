package frc.robot.commands.Auto.CommandGroups;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;
import frc.robot.commands.Auto.Common.*;
import frc.robot.commands.HatchBeak.*;

/**
 * Auton Description:
 * <ol>
 * <li>Starts with hatch in the middle of the lvl 1 platform</li>
 * <li>Drives straight to Cargo Ship Bay 4</li>
 * <li>Scores the hatch</li>
 * <li>Drives to the right load station</li>
 * <li>Intakes hatch</li>
 * <li>Drives back to Cargo Ship Bay 5</li>
 * <li>Scores the hatch</li>
 * </ol>
 * @author Bryce G.
 */
public class CGHatchMiddleShip5NearRocketLow extends CommandGroup {
    public CGHatchMiddleShip5NearRocketLow() {
    double[][] values = pathway.valuesPID;
   
    addSequential(new CommonMiddleToShip5());
    addSequential(new CommonRightLoadToNearRocketLow());
    
    // addSequential(new CommonScoreHatch());
    addSequential(new autoEndAuto());
  }
}