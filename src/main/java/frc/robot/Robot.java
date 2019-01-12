package frc.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.cargoIntake;
import frc.robot.subsystems.cargoScore;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.AirCompressor;
import frc.robot.subsystems.Example_Subsystem;
import frc.robot.subsystems.Hatch_Intake;
import frc.robot.subsystems.Hatch_Score;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Shifter;
import frc.robot.subsystems.Vision;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  // DECLARATIONS
  public static OI  oi;

  public static Arm  Arm;
  public static cargoScore  CargoScore;
  public static cargoIntake  CargoIntake;
  public static Chassis Chassis;
  public static Climber  Climber;
  public static AirCompressor  AirCompressor;
  public static Hatch_Score  HatchScore;
  public static Hatch_Intake  HatchIntake;
  public static LED  LED;
  public static Lift  Lift;
  public static Shifter  Shifter;
  public static Vision  Vision;

  public static Example_Subsystem Example;


  Command  autonomousCommand;
  SendableChooser<Command>  chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {

    // CONSTRUCTORS
     oi = new OI();

     Arm = new Arm();
     CargoIntake = new cargoIntake();
     CargoScore = new cargoScore();
     Chassis = new Chassis();
     Climber = new Climber();
     AirCompressor = new AirCompressor();
     HatchIntake = new Hatch_Intake();
     HatchScore = new Hatch_Score();
     LED = new LED();
     Lift = new Lift();
     Shifter = new Shifter();
     Vision = new Vision();

     Example = new Example_Subsystem();

    
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode",  chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
     autonomousCommand =  chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if ( autonomousCommand != null) {
       autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if ( autonomousCommand != null) {
       autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
