package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.Chassis.DriveByJoystickAndVision;
import frc.robot.commands.Chassis.PIDDriveByJoystickAndVision;
import frc.robot.commands.Chassis.PIDVisionDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    // DECLARATIONS

    public Joystick driverJoystick = new Joystick(0);
    public Joystick operatorJoystick = new Joystick(1);
    public Joystick Driverstation = new Joystick(2);

    JoystickButton driver_GreenA			= new JoystickButton(driverJoystick, 1);
    JoystickButton driver_RedB              = new JoystickButton(driverJoystick, 2);
    JoystickButton driver_BlueX             = new JoystickButton(driverJoystick, 3);
    JoystickButton driver_YellowY           = new JoystickButton(driverJoystick, 4);



    public OI() {
        // CONSTRUCTORS
        driver_GreenA			.whileHeld(new DriveByJoystickAndVision());
        driver_RedB             .whileHeld(new doNothing());
        driver_BlueX            .whileHeld(new PIDDriveByJoystickAndVision(0.1, 0, 0, 0));
        driver_YellowY          .whileHeld(new PIDVisionDrive(.03, 0, 0.02));

    }

    // FUNCTIONS
    public Joystick getdriverJoystick() {
        return driverJoystick;
    }

    // FUNCTIONS
    public Joystick getOperatorJoystick() {
        return operatorJoystick;
    }


}
