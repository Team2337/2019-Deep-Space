package frc.robot.commands.Auto;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Chassis;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * This command is mainly a placeholder command, but it can be used
 * functionally. It does just as it says: nothing.
 */
public class autoSetPathReverse extends Command {

  /* --- Path Weaver Variables --- */

  public TankModifier modifier;
  public Trajectory.Config config;
  public Trajectory trajectory;
  public EncoderFollower rightSideFollower;
  public EncoderFollower leftSideFollower;

  private double currentRightPos, currentLeftPos, rightTarget, leftTarget, rightThreshold, leftThreshold;

  // CONSTRUCTOR
  public autoSetPathReverse(Trajectory trajectoryIn) {
    this.trajectory = trajectoryIn;
    requires(Robot.Chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.Pigeon.resetPidgey();

    
    Robot.Chassis.rightFrontMotor.configPeakOutputForward(1.0);
    Robot.Chassis.rightFrontMotor.configPeakOutputReverse(-1.0);

    Robot.Chassis.leftFrontMotor.configPeakOutputForward(1.0);
    Robot.Chassis.leftFrontMotor.configPeakOutputReverse(-1.0);

    Robot.Chassis.setBrakeMode(NeutralMode.Brake);
    Robot.Chassis.resetEncoders();

    Robot.Chassis.setTrajectory(trajectory);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.Chassis.makePathReverse();
    if(Robot.Chassis.leftSideFollower.isFinished() && Robot.Chassis.rightSideFollower.isFinished()) {
      Robot.Chassis.setBrakeMode(NeutralMode.Brake);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;//(Robot.Chassis.leftSideFollower.isFinished() && Robot.Chassis.rightSideFollower.isFinished());
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.Chassis.setBrakeMode(NeutralMode.Brake);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }

}
