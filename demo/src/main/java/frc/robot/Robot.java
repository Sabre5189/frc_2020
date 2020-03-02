/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.XboxController;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private final Joystick m_stick = new Joystick(0);
  private Timer m_timer = new Timer();

  public Spark motorLeft;
  public Spark motorRight;

  public XboxController driver = new XboxController(0);
  public JoystickButton aButton = new JoystickButton(driver, 0);
  public JoystickButton xButton = new JoystickButton(driver, 2);
  public JoystickButton bJoystickButton = new JoystickButton(driver, 1);
  public JoystickButton yButton = new JoystickButton(driver, 3);

  /* the channel number of each motor */
  /*
   * private int LEFT_MOTOR=7; private int RIGHT_MOTOR=9;
   */

  private final DifferentialDrive foobar = new DifferentialDrive(new Spark(7), new Spark(9));

  private long nanoCorrection = 1000000;
  private long delayTime = 500; // * nanoCorrection;
  private long lapTime;

  private double xCorrection = -1.0;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    /*
     * motorLeft = new Spark(LEFT_MOTOR); motorRight = new Spark(RIGHT_MOTOR);
     */

    m_timer.reset();
    m_timer.start();

    // lapTime = System.nanoTime();
    lapTime = System.currentTimeMillis();
  }

  /**
   * This function is run once each time the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      foobar.arcadeDrive(0.5, 0.0); // drive forwards half speed
    } else {
      foobar.stopMotor(); // stop robot
    }
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
  }

  
  /**
   * This function is called periodically during teleoperated mode.
   */
  @Override
  public void teleopPeriodic() {
    double x = m_stick.getX();
    double xSpeed = x * xCorrection;
    double foo = m_stick.getY();

    
    foobar.arcadeDrive(m_stick.getY(), xSpeed);
  }

  /**
   * This function is called periodically during test mode.
   *
   * Testing the functionality of foobar drive train and testing simple movement
   * function
   */
  @Override
  public void testPeriodic() {
    double xSpeed = 0.2 * xCorrection;
    int zRotation = 0;

    long currentTime = System.currentTimeMillis();

    if (currentTime - lapTime > delayTime) {

      foobar.arcadeDrive(0, 0);

      lapTime = currentTime;
      System.out.println(lapTime);
    }

    foobar.arcadeDrive(xSpeed, zRotation);

  }

  // if(currentTime - lapTime > delayTime){
  // lapTime = currentTime;

  // foobar.arcadeDrive(0,0);

  // } else{
  // foobar.arcadeDrive(xSpeed, zRotation);
  // }

}
