package frc.robot.commands.CargoBigBrother;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * When the switch is flipped, assume the intake sensors are broken, and set the
 * variable in CargoBigBrother accordingly
 */
public class enableCargoSensors extends InstantCommand {
  public enableCargoSensors() {
    super();
    requires(Robot.CargoBigBrother);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    Robot.CargoBigBrother.cargoSensorsBroken = true;
  }

}
