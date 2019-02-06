package frc.robot.nerdyfiles.pathway;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.Robot;
import frc.robot.commands.Auto.Pathway;
import frc.robot.nerdyfiles.NeoNerdyDrive;
import frc.robot.nerdyfiles.TalonNerdyDrive;
import jaci.pathfinder.modifiers.TankModifier;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;

public class NerdyPath {
  boolean pathfinderDebug = true;

  /* --- Pathfinder Variables --- */
  private int ticksPerRev = 4096 * 3; // Gear ratio
  private double wheelDiameter = 6.0 * 0.0254;
  private double wheelBase = 20.5 * 0.0254; // old practice bot: 21.5
  private double leftOutput, rightOutput, gyro_heading, desired_heading, turn, angleDifference;

  String deployDir;
  public String filePath = "/home/lvuser/";

  public TankModifier modifier;
  public EncoderFollower rightSideFollower;
  public EncoderFollower leftSideFollower;

  public static NeoNerdyDrive neoDrive;
  public TalonNerdyDrive nerdyDrive;

  public NerdyPath() {
    deployDir = Filesystem.getDeployDirectory().toString();
  }

  /**
   * Use this in execute, if driving forward These variables are constantly being
   * updated
   */
  public void makePathForawrd() {
    leftOutput = leftSideFollower.calculate((int) Robot.Chassis.getLeftPosition());
    rightOutput = rightSideFollower.calculate((int) Robot.Chassis.getRightPosition());

    gyro_heading = Robot.Pigeon.getYaw();
    desired_heading = Pathfinder.r2d(leftSideFollower.getHeading());

    angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
    turn = 0.8 * (-1.0 / 80.0) * angleDifference;

    neoDrive.tankDrive(leftOutput + turn, rightOutput - turn, false);
    // nerdyDrive.tankDrive(leftOutput + turn, rightOutput - turn, false);
  }

  /**
   * Use this in execute, if driving in reverse These variables are constantly
   * being updated
   */
  public void makePathReverse() {
    leftOutput = leftSideFollower.calculate(-(int) Robot.Chassis.getLeftPosition());
    rightOutput = rightSideFollower.calculate(-(int) Robot.Chassis.getRightPosition());

    gyro_heading = -Robot.Pigeon.getYaw();
    desired_heading = Pathfinder.r2d(leftSideFollower.getHeading());

    angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
    // 0.8 * (-1.0/80.0) * angleDifference
    turn = 1.6 * (-1.0 / 80.0) * angleDifference;

    neoDrive.tankDrive(-(leftOutput + turn), -(rightOutput - turn), false);
    // nerdyDrive.tankDrive(-(leftOutput + turn), -(rightOutput - turn), false);
  }

  /**
   * Sets the trajectory for the robot based off of the waypoints given in the
   * Pathway.java file
   * 
   * @see Pathway.java
   * @param trajectory - set of waypoints being created in Robot.java
   * @param kP         - P value in PID, typically is around 1.0
   * @param kI         - I value in PID, typically is around 0.0
   * @param kD         - D value in PID, helpful to not overshoot, usually around
   *                   0.0 - 0.05
   * @param kA         - Beginning acceloration value, (aka feet forward),
   *                   typically around 0.0 unless you want a quick start out of
   *                   the gate
   */
  public void setTrajectory(Trajectory trajectory, double kP, double kI, double kD, double kA) {
    modifier = new TankModifier(trajectory).modify(wheelBase);

    leftSideFollower = new EncoderFollower(modifier.getLeftTrajectory());
    rightSideFollower = new EncoderFollower(modifier.getRightTrajectory());

    leftSideFollower.configurePIDVA(kP, kI, kD, 1 / Pathway.config.max_velocity, kA);
    rightSideFollower.configurePIDVA(kP, kI, kD, 1 / Pathway.config.max_velocity, kA);

    leftSideFollower.configureEncoder((int) Robot.Chassis.getLeftPosition(), ticksPerRev, wheelDiameter);
    rightSideFollower.configureEncoder((int) Robot.Chassis.getRightPosition(), ticksPerRev, wheelDiameter);
  }

  /**
   * 
   * @param fileName
   * @param trajectory
   */
  public void writeFile(String fileName, Trajectory trajectory) {
    try {
      File file = new File(filePath+fileName+".txt");
      file.createNewFile();
      FileOutputStream oFile = new FileOutputStream(file, false);
      String content = "blahhhhhhh";
      oFile.write(content.getBytes());
      oFile.flush();
      oFile.close();
      System.out.println("*****************************WOOOOT!****************\n!!!!!!!!!!!!!!!!!!!!!!!\n!!!!!!!!!!!!!!!!!!!!!\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
      Pathfinder.writeToFile(file, trajectory);
    } catch (IOException e) {
      System.out.println("error: " + e.getMessage());
      System.out.println("*****************RAWR: " + e + " *******************\n**********************\n**********************\n**************************\n*********************");
    }
  }

  /**
   * 
   * @param fileName
   * @return
   */
  public Trajectory readFile(String fileName) {
    try {
    File file = new File(filePath+fileName+".txt");
    BufferedReader br = new BufferedReader(new FileReader(file)); 
    file.createNewFile();
    file.setReadable(true, false);
    String st; 
    while ((st = br.readLine()) != null) 
    System.out.println("***************************** " + st + " ****************");
    br.close();
    return Pathfinder.readFromFile(file);
    } catch (IOException e) {
      System.out.println("error: " + e.getMessage());
      System.out.println("*****************RAWR: " + e + " *******************\n**********************\n**********************\n**************************\n*********************");
    }
    return null;
  }


  public void periodic() {
    if (pathfinderDebug) {
      // SmartDashboard.putNumber("Encoder Follower: last_error",
      // EncoderFollower.last_error);
      // SmartDashboard.putNumber("Encoder Follower: key", EncoderFollower.segment);
      // SmartDashboard.putNumber("Encoder Follower: Start position",
      // EncoderFollower.encoder_offset);
      // SmartDashboard.putNumber("Encoder Follower: kp", EncoderFollower.kp);
      // SmartDashboard.putNumber("Encoder Follower: LEFT calculated_value",
      // leftSideFollower.calculated_value);
      // SmartDashboard.putNumber("Encoder Follower: RIGHT calculated_value",
      // rightSideFollower.calculated_value);
      SmartDashboard.putNumber("Encoder Follower: LEFT error", leftSideFollower.error);
      SmartDashboard.putNumber("Encoder Follower: RIGHT error", rightSideFollower.error);
      SmartDashboard.putNumber("Encoder Follower: seg.position", rightSideFollower.seg.position);
      // SmartDashboard.putNumber("Encoder Follower: distance_covered",
      // EncoderFollower.distance_covered);
      // SmartDashboard.putNumber("Encoder Follower: LEFT error", getLeftPosition() -
      // leftSideFollower.calculated_value);
      // SmartDashboard.putNumber("Encoder Follower: RIGHT error", getRightPosition()
      // - rightSideFollower.calculated_value);

      SmartDashboard.putNumber("RightVelocity", Robot.Chassis.rightFrontMotor.getSelectedSensorVelocity());
      SmartDashboard.putNumber("LeftVelocity", Robot.Chassis.leftFrontMotor.getSelectedSensorVelocity());
      SmartDashboard.putNumber("Turn Value", this.turn);
      SmartDashboard.putNumber("AngleDifferance", this.angleDifference);
      SmartDashboard.putNumber("leftOutput", this.leftOutput);
      SmartDashboard.putNumber("rightOutput", this.rightOutput);
    }
  }
}