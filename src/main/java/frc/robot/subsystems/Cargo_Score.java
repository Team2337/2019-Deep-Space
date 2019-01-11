/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

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
    setDefaultCommand(new doNothing());
  }

}