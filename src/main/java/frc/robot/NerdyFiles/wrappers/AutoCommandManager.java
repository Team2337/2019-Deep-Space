package frc.robot.nerdyfiles.wrappers;


import java.awt.List;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;

import frc.robot.nerdyfiles.led.*;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * FusionManager - Manager for {@link FusionSubsystem}
 * @author Brendan & Zayd
 *
 */
public class AutoCommandManager {
	
	private static AutoCommandManager instance;
	private double color;
	private int id = 0;
	private int running = 0;
	public String state;
	private HashMap<Integer, AutoObject> commands = new HashMap<Integer, AutoObject>();
	private BlinkIn led;
		
	private NetworkTable table;
	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

	/**
	 * Returns the {@link CommandManager}, creating it if one does not exist.
	 *
	 * @return the {@link CommandManager}
	 */
	public static synchronized AutoCommandManager getInstance() {
		if (instance == null) {
			instance = new AutoCommandManager();
			
		}
		return instance;
	}
	/**
	 * init - Initalize of AutoCommandManager (in Robot)
	 */
	public void init() {
		this.table = NetworkTableInstance.getDefault().getTable("AutoCommandManager");
		
	}
	/**
	 * setBlinkin - Set color output time, BlinkIn lib is used in this case
	 * @param led
	 */
	public void setBlinkin(BlinkIn led) {
		this.led = led;
	}
	/**
	 * reset - Resets all variable tracking for all commands run in an action during auton
	 */
	public void reset() {
		color = Color.RED;
		id = 0;
		commands = new HashMap<Integer, AutoObject>();
		//Instead of making a new hasmap each time, remove all from network table THEN create a new hashmap
	}	
	
	/** 
	 * disable - The method the robot called to disable
	 */
	public void disable() {
		state = "disabled";
		NetworkTableEntry data = table.getEntry("state");		
		data.forceSetString("disabled");
	}
	public void teleop() {
		state = "teleop";
		NetworkTableEntry data = table.getEntry("state");		
		data.forceSetString("teleop");
	}
	public void auton() {
		state = "auton";
		NetworkTableEntry data = table.getEntry("state");		
		data.forceSetString("auton");
	}
	
	//Methods for AutoCommand to run
	/**
	 * init - Where the command start, send name, time started etc
	 * @param name Name of the command
	 * @return id ID given to the command
	 */
	public int init(String name) {
		String colors;
		if (color == Color.BLUE) {
			color = Color.BLUE;
			colors = "BLUE";
			SmartDashboard.putString("COLOR_COMMAND", "BLUE");
			led.setColor(color);
		} else {
			color = Color.YELLOW;
			colors = "YELLOW";
			SmartDashboard.putString("COLOR_COMMAND", "YELLOW");
			led.setColor(color);
		}
		id++;
		AutoObject object = new AutoObject(name, colors);
		commands.put(id, object);
		NetworkTableEntry data = table.getEntry(id + "_");		
		String[] info = {name, colors, String.valueOf(object.startTime.toInstant(ZoneOffset.ofHours(-5)).getEpochSecond())};
		data.forceSetStringArray(info);
		return id;
	}
	public void end(String name, int id) {
		this.running = -1;
		AutoObject object = commands.get(id);
		object.stopTime();
		double endTime = object.getTime();
		String et = endTime + "";
		String[] info = {object.commandName, object.color, String.valueOf(object.startTime.toInstant(ZoneOffset.ofHours(-5)).getEpochSecond()), et};
		NetworkTableEntry data = table.getEntry(id + "_");
		data.forceSetStringArray(info);
	}
}