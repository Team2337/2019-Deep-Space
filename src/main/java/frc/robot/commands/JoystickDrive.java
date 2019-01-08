/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
public class JoystickDrive extends Command {

    // DECLARATIONS
    double moveSpeed, turnSpeed;
    boolean squaredInputs;
  
    // CONSTRUCTOR
    public JoystickDrive() {

    // VARIABLE_SETTING
          
    // REQUIRED SUBSYTEM
      requires(Robot.m_chassis);
      }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    moveSpeed = -Robot.m_oi.driverJoystick.getRawAxis(1); //Left Y
    turnSpeed = Robot.m_oi.driverJoystick.getRawAxis(4); //Right X
    squaredInputs = true;

    Robot.m_chassis.driveArcade(moveSpeed,turnSpeed,squaredInputs);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_chassis.driveArcade(0,0,true);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
