package frc.robot;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.commands.Auto.*;
import frc.robot.commands.Auto.CommandGroups.*;
import frc.robot.commands.LED.LEDRuntime;
import frc.robot.nerdyfiles.pathway.*;
import frc.robot.subsystems.*;
import jaci.pathfinder.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * Specifies whether or not the Robot is the competition robot
   */
  public static boolean isComp = false;
  public static boolean stringPotBroken = false;

  public double[][] valuesPID = pathway.valuesPID;

  // DECLARATIONS
  public static AirCompressor AirCompressor;
  public static AutoHatchKicker AutoHatchKicker;
  public static CargoBigBrother CargoBigBrother;
  public static CargoDrawbridge CargoDrawbridge;
  public static CargoIntake CargoIntake;
  public static CargoEscalator CargoEscalator;
  public static CargoScore CargoScore;
  public static Chassis Chassis;
  public static ClimberDeploy ClimberDeploy;
  public static Constants Constants;
  public static HatchLauncher HatchLauncher;
  public static HatchBeak HatchBeak;
  public static LED LED;
  public static Lift Lift;
  public static NerdyPath NerdyPath;
  public static OI oi;
  public static Pigeon Pigeon;
  public static Recorder Recorder;
  public static RoboWrangler RoboWrangler;
  public static PowerDistributionPanel PDP;
  public static Shifter Shifter;
  public static TRexArms TRexArms;
  public static Vision Vision;

  public static Command autonomousCommand;
  SendableChooser<String> autonChooser = new SendableChooser<>();
 
  public static Trajectory driveForwardT, curveFromToHatchRightT, fromRightLoadJTurnToCargoShipT, jTurnToCargoShipRightT;
  public static Trajectory driveForwardFile;
  public static Trajectory testSCurveT;
  public static Trajectory driveOffRightLvl2ToRightRocketT, driveOffRightLvl1ToBackRightRocketT, driveAwayFromBackRightRocketT;
  public static Trajectory sideTwoHatchFromRightT;

  public static boolean pathsLoaded = false;
  public static boolean logger;

  private String chosenAuton;
  public String mac;

  public static double rampRate = 0.2;
  public static double autonAngle = 0;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    mac = "xx:xx:xx:xx:xx:xx";
    // Attempt to get the MAC address of the robot
    try {
      NetworkInterface network = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());

      byte[] address = network.getHardwareAddress();

      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < address.length; i++) {
        sb.append(String.format("%02X%s", address[i], (i < address.length - 1) ? ":" : ""));
      }
      mac = sb.toString();
      System.out.println(mac);
    } catch (UnknownHostException e) {
      System.out.println("Unknown Host Exception - " + e);
    } catch (SocketException e) {
      System.out.println("Socket Exception - " + e);
    }
    /// Determines what robot we are using
    
    if (mac.equals("00:80:2F:17:89:85")) {
      System.out.println("PracticeBot " + mac);
      isComp = false;
    } else {
      // If we are not using PracticeBot, assume we are using CompBot (this also will
      // cover if there is an error while getting the MAC address)
      System.out.println("CompBot " + mac);
      isComp = true;
    }
    
    isComp = true;

    // CONSTRUCTORS
    // Keep above other subsystems, as these have dependencies for other subsystems
    // to be instantiated first.
    Constants = new Constants();

    AirCompressor = new AirCompressor();
    AutoHatchKicker = new AutoHatchKicker();
    CargoDrawbridge = new CargoDrawbridge();
    CargoEscalator = new CargoEscalator();
    CargoIntake = new CargoIntake();
    CargoScore = new CargoScore();
    Chassis = new Chassis();
    ClimberDeploy = new ClimberDeploy();
    HatchBeak = new HatchBeak();
    HatchLauncher = new HatchLauncher();
    LED = new LED();
    Lift = new Lift();
    Pigeon = new Pigeon();
    Recorder = new Recorder();
    RoboWrangler = new RoboWrangler();
    // PDP = new PowerDistributionPanel();
    Pigeon = new Pigeon();
    Shifter = new Shifter();
    TRexArms = new TRexArms();
    Vision = new Vision();

    /*
     * Keep below other subsystems as these have dependencies for other subsystems
     * to be instantiated first.
     */

    NerdyPath = new NerdyPath();
    CargoBigBrother = new CargoBigBrother();

    // Turn off the Limelight LED if it is on.
    Vision.setLEDMode(1);
    LED.setColor(Robot.LED.off);
    
    // Writing a trajectory to a file (keep commented out until needed)
    // Robot.NerdyPath.writeFile("driveForward184", driveForwardT); //187

    oi = new OI();

    autonChooser.setDefaultOption("Auton Do Nothing", "Default");
    autonChooser.addOption("Hatch Lvl1 Right - Far Rocket Low - Near Rocket Low", "Hatch Lvl1 Right Far Rocket Low Near Rocket Low");
    // autonChooser.addOption("Hatch Lvl2 Right - Far Rocket Low - Near Rocket Low", "Hatch Lvl2 Right Far Rocket Low Near Rocket Low");
    autonChooser.addOption("Hatch Lvl1 Right - Near Rocket Low - Near Rocket Mid", "Hatch Lvl1 Right Near Rocket Low Near Rocket Mid");
    autonChooser.addOption("Hatch Lvl2 Right - Near Rocket Low - Near Rocket Mid", "Hatch Lvl2 Right Near Rocket Low Near Rocket Mid");
    autonChooser.addOption("Hatch Lvl1 Right - Near Rocket Low - Far Rocket Low", "Hatch Lvl1 Right Near Rocket Low Far Rocket Low");
    autonChooser.addOption("Hatch Lvl2 Right - Near Rocket Low - Far Rocket Low", "Hatch Lvl2 Right Near Rocket Low Far Rocket Low");
    autonChooser.addOption("Hatch Lvl1 Mid - Ship 4 - Ship 5", "Hatch Lvl1 Mid Ship 4 Ship 5");

    autonChooser.addOption("Hatch Lvl1 Left - Near Rocket Low - Near Rocket Mid", "Hatch Lvl1 Left Near Rocket Low Near Rocket Mid");
    autonChooser.addOption("Hatch Lvl2 Left - Near Rocket Low - Near Rocket Mid", "Hatch Lvl2 Left Near Rocket Low Near Rocket Mid");
    autonChooser.addOption("Hatch Lvl1 Left - Near Rocket Low - Far Rocket Low", "Hatch Lvl1 Left Near Rocket Low Far Rocket Low");
    autonChooser.addOption("Hatch Lvl2 Left - Near Rocket Low - Far Rocket Low", "Hatch Lvl2 Left Near Rocket Low Far Rocket Low");
    autonChooser.addOption("Hatch Lvl1 Mid - Ship 5 - Ship 4", "Hatch Lvl1 Mid Ship 5 Ship 4");
    
    Robot.Chassis.resetEncoders();
    Robot.Pigeon.resetPidgey();
    SmartDashboard.putData("Auto mode", autonChooser);
    Vision.streamMode(2);
    // Hold the current lift position so that the lift doesn't move on startup
    Robot.Lift.setSetpoint(Robot.Lift.getPosition());
    // Disable the air compressor so it doesn't run every time we start the robot.
    // Robot.AirCompressor.disable();
    Robot.ClimberDeploy.undeployClimber();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putBoolean("Logger", logger);
    if (Robot.Lift.getPosition() < Robot.Lift.minValue || Robot.Lift.getPosition() > Robot.Lift.maxValue) {
      stringPotBroken = true;
    } else {
      stringPotBroken = false;
    }

    
    /*
     * Using the auton chooser to load trajectories
     * Only loads trajectories when it detects a change in the auton chooser on the dashboard
     */
    if(!autonChooser.getSelected().equals(chosenAuton)) {
      chosenAuton = autonChooser.getSelected();
      pathsLoaded = false;
      switch(autonChooser.getSelected()) {
        case "Hatch Lvl1 Right Far Rocket Low Near Rocket Low":
          driveOffRightLvl1ToBackRightRocketT = pathway.driveOffRightLvl1ToBackRightRocket();
          driveAwayFromBackRightRocketT = pathway.driveAwayFromBackRightRocket();
          pathsLoaded = true;
        break;
        default:
          //Don't put anything in here because we don't want the robot to move if we don't have an auton with a pathway selected
        break;
      }
    }

    /* --- Driver Dashboard Items --- */
    SmartDashboard.putBoolean("Driver/String Pot Broken", stringPotBroken);
    SmartDashboard.putBoolean("Driver/Trolley Sensor", Robot.CargoBigBrother.cargoTrolleySensor.get());
    SmartDashboard.putBoolean("Driver/Intake sensor", Robot.CargoBigBrother.cargoIntakeSensor.get());
    SmartDashboard.putBoolean("Driver/Escalator sensor", !Robot.CargoBigBrother.cargoEscalatorSensor.get());
    SmartDashboard.putNumber("Driver/Air Pressure (PSI)", Robot.AirCompressor.getPressure());
    SmartDashboard.putBoolean("is Comp", isComp);
    SmartDashboard.putNumber("Driver/Neo_LF_Temp",
        (((Robot.Chassis.neoLeftFrontMotor.getMotorTemperature() * 9) / 5) + 32));
    SmartDashboard.putNumber("Driver/Neo_LR_Temp",
        (((Robot.Chassis.neoLeftRearMotor.getMotorTemperature() * 9) / 5) + 32));
    SmartDashboard.putNumber("Driver/Neo_RF_Temp",
        (((Robot.Chassis.neoRightFrontMotor.getMotorTemperature() * 9) / 5) + 32));
    SmartDashboard.putNumber("Driver/Neo_RR_Temp",
        (((Robot.Chassis.neoRightRearMotor.getMotorTemperature() * 9) / 5) + 32));
    SmartDashboard.putNumber("Driver/Right_Encoder", Robot.Chassis.getRightPosition());
    SmartDashboard.putNumber("Driver/Left_Encoder", Robot.Chassis.getLeftPosition());
    SmartDashboard.putNumber("Driver/Compass_Heading", Robot.Pigeon.getYaw());
    SmartDashboard.putNumber("Driver/Lift_Position", Robot.Lift.getPosition());
    SmartDashboard.putBoolean("Driver/Compressor On?", Robot.AirCompressor.status());
    SmartDashboard.putBoolean("Driver/Auto_Line_Sensor", Robot.Chassis.autoLineSensor.get());
    SmartDashboard.putBoolean("Driver/Climber Line Sensor", Robot.ClimberDeploy.climberLineSensor.get());

    SmartDashboard.putNumber("Right Distance Inch", (Robot.Chassis.getRightPosition() / 13988) * 20);
    SmartDashboard.putNumber("Left Distance Inch", (Robot.Chassis.getLeftPosition() / 13988) * 20);

    SmartDashboard.putNumber("Sticky Faults", Robot.Chassis.neoLeftRearMotor.getStickyFaults());
    SmartDashboard.putNumber("Faults", Robot.Chassis.neoLeftRearMotor.getFaults());

    SmartDashboard.putNumber("Climber phase", Robot.ClimberDeploy.climberPhase);
    SmartDashboard.putBoolean("Ready to climb", Robot.ClimberDeploy.readyToClimb);

    SmartDashboard.putNumber("autonAngle", autonAngle);
    SmartDashboard.putBoolean("Paths Loaded", pathsLoaded);

    SmartDashboard.putBoolean("Beaked", Robot.HatchBeak.status());
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You
   * can use it to reset any subsystem information you want to clear when the
   * robot is disabled.
   */
  @Override
  public void disabledInit() {
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kCoast);
    Robot.Vision.setLEDMode(1);
    logger = false;
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString code to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons to
   * the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    //Selects the auton command being run based off of the chosen auton
    switch(autonChooser.getSelected()) {
      case "Hatch Lvl1 Right Far Rocket Low Near Rocket Low":
        autonomousCommand = new CGHatchRightLowFarRocketLowNearRocketLow();
      break;
      case "Hatch Lvl2 Right Far Rocket Low Near Rocket Low":
        autonomousCommand = new CGHatchRightHighFarRocketLowNearRocketLow();
      break;
      case "Hatch Lvl1 Right Near Rocket Low Near Rocket Mid":
        autonomousCommand = new CGHatchRightLowNearRocketLowNearRocketMid();
      break;
      case "Hatch Lvl2 Right Near Rocket Low Near Rocket Mid":
        autonomousCommand = new CGHatchRightHighNearRocketLowNearRocketMid();
      break;
      case "Hatch Lvl1 Right Near Rocket Low Far Rocket Low":
        autonomousCommand = new CGHatchRightLowNearRocketLowFarRocketLow();
      break;
      case "Hatch Lvl2 Right Near Rocket Low Far Rocket Low":
        autonomousCommand = new CGHatchRightHighNearRocketLowFarRocketLow();
      break;
      case "Hatch Lvl1 Mid Ship 4 Ship 5":
        autonomousCommand = new CGHatchMiddleShip4Ship5();
      break;
      case "Hatch Lvl1 Left Near Rocket Low Near Rocket Mid":
        autonomousCommand = new CGHatchLeftLowNearRocketLowNearRocketMid();
      break;
      case "Hatch Lvl1 Left Near Rocket Low Far Rocket Low":
        autonomousCommand = new CGHatchLeftLowNearRocketLowFarRocketLow();
      break;
      case "Hatch Lvl2 Left Near Rocket Low Far Rocket Low":
        autonomousCommand = new CGHatchLeftHighNearRocketLowFarRocketLow();
      break;
      case "Hatch Lvl1 Mid Ship 5 Ship 4":
        autonomousCommand = new CGHatchMiddleShip5Ship4();
      break;
      default:
        autonomousCommand = new autoDoNothing();
      break;
    }
    Robot.Lift.setSetpoint(Robot.Lift.getPosition());

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
     * switch(autoSelected) { case "My Auto": autonomousCommand = new
     * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
     * ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (autonomousCommand != null) {
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
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kCoast);
    Robot.Lift.setSetpoint(Robot.Lift.getPosition());

    /*
     * This makes sure that the autonomous stops running when teleop starts running.
     * If you want the autonomous to continue until interrupted by another command,
     * remove this line or comment it out.
     */
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
    logger = true;
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