package frc.robot.commands.Compressor;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.subsystems.AirCompressor;

/**
 * Enables the compressor
 */
public class compressor_TurnOn extends Command {

  // CONSTRUCTOR
  public compressor_TurnOn() {
    requires(Robot.AirCompressor);
  }

  // Enables the compressor
  @Override
  protected void initialize() {
    AirCompressor.enable();
  }

  // Ends the command once the compressor is enabled
  @Override
  protected boolean isFinished() {
    return true;
  }
}
