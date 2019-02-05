package frc.robot.commands.Auto.setpaths;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * This command is mainly a placeholder command, but it can be used
 * functionally. It does just as it says: nothing.
 * @author Bryce G.
 */
public class autoSetPathWithFile extends Command {

  /* --- Path Weaver Variables --- */

  public TankModifier modifier;
  public Trajectory.Config config;

  public String pathName; 
  public static double kP, kI, kD, kA, printX;
  private double[] pidValues;


  private double timeout;

  // CONSTRUCTOR
  public autoSetPathWithFile(double[] pidValues, String pathName) {
    this.pidValues = pidValues;
    this.pathName = pathName;
    requires(Robot.Chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    kP = pidValues[0];
    kI = pidValues[1];
    kD = pidValues[2];
    kA = pidValues[3];
    Robot.Pigeon.resetPidgey();
    
    // Robot.Chassis.rightFrontMotor.configPeakOutputForward(1.0);
    // Robot.Chassis.rightFrontMotor.configPeakOutputReverse(-1.0);

    // Robot.Chassis.leftFrontMotor.configPeakOutputForward(1.0);
    // Robot.Chassis.leftFrontMotor.configPeakOutputReverse(-1.0);

    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kCoast);
    Robot.Chassis.resetEncoders();

    // timeout = (trajectory.length() / 10) + 1.5;//0.2;   timeout = (trajectory.length() / 50)+0.2
    // printX = ((trajectory.length()-40) / 50)+0.2;
    setTimeout(timeout);
    Robot.NerdyPath.setTrajectoryWithFile(kP, kI, kD, kA, pathName);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.NerdyPath.makePathForawrd();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;//isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("**** COMMAND ENDED ****");
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kBrake);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
