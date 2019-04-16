package frc.robot.commands.Auto.Common;

import frc.robot.commands.Auto.Common.*;
import frc.robot.commands.Chassis.*;
import frc.robot.commands.HatchBeak.hatchBeakClose;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;

public class CommonMiddleToShip4 extends CommandGroup {
    public CommonMiddleToShip4() {
        
        addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    
        addSequential(new autoTurnOnLimeLightLED());
        addSequential(new autoPIDVisionDrive(3.5, 0.07, 0.015, 0.6));
        addSequential(new CommonScoreHatch());
    
        addSequential(new autoResetEncoders());
        addSequential(new autoWait(0.05));
        //TODO: take out brake when done
        addSequential(new autoTankDrive(-0.33, -0.6, 0, 135000, "right", IdleMode.kCoast));
        
        addParallel(new autoTurnOnLimeLightLED());
        addSequential(new autoResetEncoders());
        addSequential(new autoWait(0.05));
        addParallel(new hatchBeakClose());
        addSequential(new autoTankDrive(0.7, 0.6, 30000, 0, "leftVision", IdleMode.kCoast));
        addSequential(new autoPIDVisionDrive(5, 0.07, 0.015, 0.6));
        addSequential(new CommonIntakeHatch());
        
    }
}