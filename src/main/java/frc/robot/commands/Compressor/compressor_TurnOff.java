package frc.robot.commands.Compressor;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.subsystems.AirCompressor;

/**
 * Enables the compressor
 */
public class compressor_TurnOff extends Command {

  // CONSTRUCTOR
  public compressor_TurnOff() {
    requires(Robot.AirCompressor);
  }

  // Disables the compressor
  @Override
  protected void initialize() {
    AirCompressor.disable();
  }

  // Ends the command once the compressor is disabled
  @Override
  protected boolean isFinished() {
    return true;
  }
}
