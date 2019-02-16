package frc.robot.commands.Compressor;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

/**
 * Disables the compressor
 */
public class compressorTurnOff extends Command {

  // CONSTRUCTOR
  public compressorTurnOff() {
    requires(Robot.AirCompressor);
  }

  // Disables the compressor
  @Override
  protected void initialize() {
    Robot.AirCompressor.disable();
  }

  // Ends the command once the compressor is disabled
  @Override
  protected boolean isFinished() {
    return true;
  }
}
