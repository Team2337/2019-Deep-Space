package frc.robot.commands.Auto.Common;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Auto.*;
import frc.robot.commands.HatchBeak.hatchBeakClose;
import frc.robot.commands.HatchLauncher.*;

/**
 * @author Bryce G.
 */
public class CommonDelayAuto extends CommandGroup {

    public CommonDelayAuto(Command currentCommand) {
    // addSequential(new autoButtonDelay(currentCommand));
    // addSequential(new currentCommand());
  }
}
