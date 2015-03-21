//David, keep track of which libaries you import - you imported .CANTalon three times before I fixed it . . .
package org.usfirst.frc.team5431.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team5431.robot.commands.ExampleCommand;
import org.usfirst.frc.team5431.robot.subsystems.ExampleSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot 
{

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	Joystick joy;
	Compressor com;
	Solenoid sol;
	Solenoid sol2;
    Command autonomousCommand;
    CANTalon rightDriveMotor;
    CANTalon leftDriveMotor;
    CANTalon LiftMotor;
    CANTalon RightFlywheel;
    CANTalon LeftFlywheel;
    RobotDrive Robots;
    

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() 
    {
    	joy = new Joystick(0);
    	com = new Compressor();
    	sol = new Solenoid(0);
    	sol2 = new Solenoid(1);
		oi = new OI();
		rightDriveMotor = new CANTalon(4); //Rightmotor in port 4
		leftDriveMotor  = new CANTalon(5); //Leftmotor in port 5
		LiftMotor  = new CANTalon(3);		//Liftmotor in port 3
		RightFlywheel  = new CANTalon(2);	//Rightflywheel in port 2
		LeftFlywheel  = new CANTalon(1);	//Leftflywheel in port 1
    	Robots = new RobotDrive(leftDriveMotor, rightDriveMotor);
    	
		
        // instantiate the command used for the autonomous period
        autonomousCommand = new ExampleCommand();
        
        
    }
    
	
	public void disabledPeriodic() 
	{
		Scheduler.getInstance().run();
	}

    public void autonomousInit() 
    {
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() 
    {
        Scheduler.getInstance().run();
    }

    public void teleopInit() 
    {
    	com.start();
        if (autonomousCommand != null) autonomousCommand.cancel();
        
    	while(true)
    	{
	    	if(joy.getRawButton(1) == true && joy.getRawButton(2) == false)
	    	{
	    		sol.set(true);
	    		sol2.set(false);
	
	    	}
	    	if(joy.getRawButton(1) == false && joy.getRawButton(2) == false||joy.getRawButton(1) == true && joy.getRawButton(2) == true)
	    	{
	    		sol.set(false);
	    		sol.set(false);
	    	}
	    	if(joy.getRawButton(2) == true && joy.getRawButton(1) == false)
	    	{
	    		sol.set(false);
	    		sol2.set(true);
	    	}
	    	if(joy.getRawAxis(5) > .1) //Runs the right motor forward if Y-Axis of the right joystick is above 0.1
	    	{
	    		rightDriveMotor.set(1);//Makes Motor run full speed forward
	    	}
	    	if(joy.getRawAxis(2) > .1)//Runs the left motor forward if Y-Axis of the left joystick is above 0.1
	    	{
	    		leftDriveMotor.set(1);//Makes left Motor run full speed forward
	    	}
	    	if((joy.getRawAxis(5) >= -.1) || (joy.getRawAxis(5) <= .1) ) //If joystick is not moved, turn off rightmotor
	    	{
	    		rightDriveMotor.set(0);
	    	}
	    	if((joy.getRawAxis(2) >= -.1) || (joy.getRawAxis(2) <= .1) ) //If joystick is not moved, turn off leftmotor
	    	{
	    		leftDriveMotor.set(0);
	    	}
	    	Timer.delay(0.1);
	    	sol.set(false);
	    	sol2.set(false);
    	}
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.

    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit()
    {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() 
    {
        LiveWindow.run();
    }
}
