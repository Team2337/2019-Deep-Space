package frc.robot.commands.Auto.Common;

import frc.robot.commands.Auto.Common.*;
import frc.robot.commands.Chassis.*;
import frc.robot.commands.HatchBeak.hatchBeakClose;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;

public class CommonMiddleToShip5 extends CommandGroup {
    public CommonMiddleToShip5() {
       
        addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    
        addSequential(new autoTurnOnLimeLightLED());
        addSequential(new autoPIDVisionDrive(3.5, 0.07, 0.015, 0.6));
        addSequential(new CommonScoreHatch());
    
        addSequential(new autoResetEncoders());
        addSequential(new autoWait(0.05));
        //TODO: take out brake when done
        addSequential(new autoTankDrive(-0.76, -0.41, 90000, 0, "left", IdleMode.kCoast));
        // addSequential(new autoTankDriveWithGyro(175, -0.60, -0.37, "left", IdleMode.kCoast));
        addSequential(new autoTankDrive(-0.60, -0.37, 125000, 0, "left", IdleMode.kCoast));

        addParallel(new autoTurnOnLimeLightLED());
        addSequential(new autoResetEncoders());
        addSequential(new autoWait(0.05));
        addParallel(new hatchBeakClose());
        // addSequential(new autoTankDrive(0.6, 0.7, 0, 30000, "rightVision", IdleMode.kBrake));
        addSequential(new autoPIDVisionDrive(5, 0.09, 0.03, 0.7));
        addSequential(new CommonIntakeHatch());

        addSequential(new autoResetEncoders());
        addSequential(new autoWait(0.05));
    }
}