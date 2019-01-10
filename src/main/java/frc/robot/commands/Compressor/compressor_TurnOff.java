/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

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
