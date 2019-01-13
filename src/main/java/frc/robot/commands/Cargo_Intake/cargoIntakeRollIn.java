/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Cargo_Intake;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * @author Zayd A.
 * Rolls in the cargo - COMMAND
 */
public class cargoIntakeRollIn extends Command {
private double power = 1;


  // CONSTRUCTOR
  public cargoIntakeRollIn(double power) {
this.power = power;

    requires(Robot.Example);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.CargoIntake.rollIn(this.power);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
