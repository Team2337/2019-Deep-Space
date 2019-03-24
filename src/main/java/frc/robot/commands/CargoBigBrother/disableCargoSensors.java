package frc.robot.commands.CargoBigBrother;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * When the switch is flipped back down, assume the cargo sensors have been
 * fixed, and set the variable accordingly
 */
public class disableCargoSensors extends InstantCommand {
  public disableCargoSensors() {
    super();
    requires(Robot.CargoBigBrother);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    Robot.CargoBigBrother.cargoSensorsBroken = false;
  }

}
