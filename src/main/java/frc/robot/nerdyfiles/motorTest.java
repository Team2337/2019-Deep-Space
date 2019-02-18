package frc.robot.nerdyfiles;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.command.Command;

public class motorTest extends Command {

    VictorSPX victor;
    TalonSRX talon;
    CANSparkMax sparkMAX;
    double speed;

    /**
     * Runs the given talon motor at a speed
     * 
     * @param talon   - TalonSRX motor controller
     * @param talonID - CAN ID for the talon
     * @param speed   - Speed of the motor
     *                <p>
     *                <br/>
     *                Examples:
     *                <ul>
     *                <li>Full speed forward -> 1.0</li>
     *                <li>Half speed reverse -> -0.5</li>
     *                </ul>
     *                </p>
     */
    public motorTest(TalonSRX talon, double speed) {
        this.speed = speed;
        this.talon = talon;
    }

    /**
     * Runs the given victor motor at a speed
     * 
     * @param victor   - VictorSPX motor controller
     * @param victorID - CAN ID for the victor
     * @param speed    - Speed of the motor
     *                 <p>
     *                 <br/>
     *                 Examples:
     *                 <ul>
     *                 <li>Full speed forward -> 1.0</li>
     *                 <li>Half speed reverse -> -0.5</li>
     *                 </ul>
     *                 </p>
     */
    public motorTest(VictorSPX victor, double speed) {
        this.speed = speed;
        this.victor = victor;
    }

    /**
     * Runs the given spark motor at a speed
     * 
     * @param sparkMAX   - CANSparkMax motor controller
     * @param sparkMAXID - CAN ID for the Spark MAX
     * @param type    - Define the motor type, either brushless or brushed <br/>
     *                <ul>
     *                <li>MotorType.kBrushless</li>
     *                <li>MotorType.kBrushed</li>
     *                </ul>
     * @param speed   - Speed of the motor
     *                Examples:
     *                <ul>
     *                <li>Full speed forward -> 1.0</li>
     *                <li>Half speed reverse -> -0.5</li>
     *                </ul>
     */
    public motorTest(CANSparkMax sparkMAX, double speed) {
        this.speed = speed;
        this.sparkMAX = sparkMAX;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        if (talon == null && sparkMAX == null) {
            victor.set(ControlMode.PercentOutput, this.speed);
        } else if (sparkMAX == null && victor == null) {
            talon.set(ControlMode.PercentOutput, this.speed);
        } else {
            sparkMAX.set(this.speed);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        if (talon == null && sparkMAX == null) {
            victor.set(ControlMode.PercentOutput, 0);
        } else if (sparkMAX == null && victor == null) {
            talon.set(ControlMode.PercentOutput, 0);
        } else {
            sparkMAX.set(0);
        }
    }

    @Override
    protected void interrupted() {
        this.end();
    }

}