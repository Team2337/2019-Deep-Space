package frc.robot.subsystems;

import frc.robot.commands.*;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 */
public class Cargo_Score extends Subsystem {

  public Cargo_Score() {

  }

  // Set the default command for a subsystem here.

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Do_Nothing());

  }
}
