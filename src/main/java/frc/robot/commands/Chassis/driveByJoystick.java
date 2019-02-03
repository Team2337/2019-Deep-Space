package frc.robot.commands.Chassis;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.subsystems.Chassis;

/**
 * Uses controller joysticks to drive the robot using ArcadeDrive
 */
public class driveByJoystick extends Command {

  // Gets the joystick to be used from OI.java
  private Joystick driverJoystick = Robot.oi.driverJoystick;
  private boolean neoDrive;

  public driveByJoystick() {
    this.neoDrive = false;
    requires(Robot.Chassis);
  }

  public driveByJoystick(boolean neoDrive) {
    this.neoDrive = neoDrive;
    requires(Robot.Chassis);
  }

  // Supplys the correct values to the arcadeDrive command to drive the robot
  protected void execute() {
    double moveSpeed = -driverJoystick.getRawAxis(1); // Left joystick's front/back movement as a number from -1 to 1
    double turnSpeed = driverJoystick.getRawAxis(4); // Right joysticks left/right movement as a number from -1 to 1

    if (this.neoDrive) {
      Chassis.neoDrive.arcadeDrive(1/*moveSpeed*/, 0, true);
    } else {
      Chassis.talonDrive.arcadeDrive(moveSpeed, turnSpeed, true);
    }
  }

  // This command is not meant to exit, so we don't ever allow it to
  protected boolean isFinished() {
    return false;
  }
}
