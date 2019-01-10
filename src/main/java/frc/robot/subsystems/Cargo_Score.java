/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Zayd A.
 * It allows you to open and close claw
 */
public class Cargo_Score extends Subsystem {
public Solenoid  claw;

private int PCM0 = 0;
private int Port = 0;

  public Cargo_Score() {
    this.claw = new Solenoid(PCM0, Port);
  }

  // Set the default command for a subsystem here.

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Example_Command());

  }

  /**
   * clawOpen = Opens claw; sets this to true
   */
  public void clawOpen() {
    claw.set(true);
  }

  /**
   * clawClose = Closes claw; sets this to false
   */
  public void clawClose() {
    claw.set(false);
  }

}