package frc.robot.commands.AirCompressor;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

/**
 * Enables the compressor
 */
public class compressorTurnOn extends Command {

  // CONSTRUCTOR
  public compressorTurnOn() {
    requires(Robot.AirCompressor);
  }

  // Enables the compressor
  @Override
  protected void initialize() {
    Robot.AirCompressor.enable();
  }

  // Ends the command once the compressor is enabled
  @Override
  protected boolean isFinished() {
    return true;
  }
}
