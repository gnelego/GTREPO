package org.firstinspires.ftc.teamcode;
//TELEOP WITH CODE FOR COLOR AND DISTANCE AS WELL AS TOUCH SENSOR

//package org.firstinspires.ftc.teamcode;
///import com.ftdi.j2xx.D2xxManager;
//import com.qualcomm.robotcore.robocol.PeerApp;

import android.app.Activity;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@TeleOp(name="TeleOp2", group="Iterative OpMode")  // @Autonomous(...) is the other common choice
//@Disabled

public class TELEOP extends OpMode {
    /* Declare OpMode members. */

    private ElapsedTime runtime = new ElapsedTime();

    DcMotor frontleftmotor;
    DcMotor frontrightmotor;
//    DcMotor backleftmotor;
//    DcMotor backrightmotor;

    DcMotor liftmotor;

    Servo servo0;
    Servo servo1;

    DigitalChannel digitaltouch;  // Hardware Device Object

    ColorSensor sensorcolor;
    DistanceSensor sensordistance;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {

        telemetry.addData("Status", "Initialized");

        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */
        //frontleftmotor = hardwareMap.dcMotor.get("frontleftdrive"); ADDD
        //frontrightmotor = hardwareMap.dcMotor.get("frontrightdrive"); ADDDD
        // backLeftMotor = hardwareMap.dcMotor.get("backleftdrive");
        // backRightMotor = hardwareMap.dcMotor.get("backrightdrive");

       // liftmotor = hardwareMap.dcMotor.get("liftmotor"); ADDDDDDDDDDDDDDDD

        servo0 = hardwareMap.servo.get("servo0"); //capball
        servo1 = hardwareMap.servo.get("servo1"); //capball

        // eg: Set the drive motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery

        // frontLeftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        // frontRightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        // backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        // backRightMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

       /* //telemetry.addData("Status", "Running: " + runtime.toString());
        telemetry.addData("Front Left Ticks:", frontleftmotor.getCurrentPosition());
        telemetry.addData("Front Right Ticks", frontrightmotor.getCurrentPosition());
        telemetry.addData("Motor Output", "Front Left" + frontleftmotor.getPower());
        telemetry.addData("Motor Output", "Front Right" + frontrightmotor.getPower());
        //telemetry.addData("Motor Output", "Rear Left" + backLeftMotor.getPower());
        //telemetry.addData("Motor Output", "Rear Right" + backRightMotor.getPower());

        frontleftmotor.setPower(gamepad1.left_stick_y);
        frontrightmotor.setPower(-gamepad1.right_stick_y);
        // backLeftMotor.setPower(gamepad1.left_stick_y);
        // backRightMotor.setPower(-gamepad1.right_stick_y);


        // get a reference to our digitalTouch object.
        digitaltouch = hardwareMap.get(DigitalChannel.class, "sensordigital");

        // set the digital channel to input.
        //digitaltouch.setMode(DigitalChannel.Mode.INPUT);


        // get a reference to the color sensor.
        sensorcolor = hardwareMap.get(ColorSensor.class, "sensorcolordistance");

        // get a reference to the distance sensor that shares the same name.
        sensordistance = hardwareMap.get(DistanceSensor.class, "sensorcolordistance");

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        // sometimes it helps to multiply the raw RGB values with a scale factor
        // to amplify/attentuate the measured values.
        final double SCALE_FACTOR = 255;

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        telemetry.addData("Distance (cm)",
                String.format(Locale.US, "%.02f", sensordistance.getDistance(DistanceUnit.CM)));
        telemetry.addData("Alpha", sensorcolor.alpha());
        telemetry.addData("Red  ", sensorcolor.red());
        telemetry.addData("Green", sensorcolor.green());
        telemetry.addData("Blue ", sensorcolor.blue());
        telemetry.addData("Hue", hsvValues[0]);

        // if(gamepad2.dpad_down) {

        //RED
        if (hsvValues[0] >= 300 && hsvValues[0] <= 400) {
            telemetry.addData("Hue RED ", hsvValues[0]);
        }

        //BLUE
        //if (hsvValues[0] >= 150 && hsvValues[0] <= 250) {


        if(gamepad1.right_bumper) {
            //frontleftmotor.setPower(.2);
            //frontrightmotor.setPower(-.2);
              frontleftmotor.setPower(.4);
              frontrightmotor.setPower(-.4);
        }

        //.35 was the speed for #1 lifter
        //.70 was the speed for #2 lifter
        if(gamepad1.left_bumper) {
            frontleftmotor.setPower(-.4);
            frontrightmotor.setPower(.4);
        }
        if (gamepad2.y) {
                liftmotor.setPower(.70);

        } else if (gamepad2.a) {
            liftmotor.setPower(-.70);
        } else {
            liftmotor.setPower(0);
        }
/*
        //TEST DISTANCE SENSOR TO SHUT OFF MOTOR, Must be > 6mm
        if (gamepad2.y) {
            if (sensordistance.getDistance(DistanceUnit.CM) >= 6)  {
                telemetry.addData("Distance > 9mm ", sensordistance.getDistance(DistanceUnit.CM));
                liftmotor.setPower(.70);
            } else {
                telemetry.addData("Distance <5mm ", sensordistance.getDistance(DistanceUnit.CM));
                liftmotor.setPower(0);
                 }
        }
*/
/*
        // TEST TOUCH SWITCH TURNING OFF MOTOR
        if (gamepad2.y) {
            if (digitaltouch.getState() == true) {
                telemetry.addData("Digital Touch", "Is Not Pressed");
                telemetry.addData("Digital Touch", digitaltouch.getState());
                liftmotor.setPower(.70);
            } else {
                telemetry.addData("Digital Touch", "Is Pressed");
                liftmotor.setPower(0);
            }
        } else if (gamepad2.a) {
            liftmotor.setPower(-.70);
        } else {
            liftmotor.setPower(0);
        }
*/
        if (gamepad2.x) {
            servo0.setPosition(1);
            servo1.setPosition(0);
        }
        if (gamepad2.b) {
            servo0.setPosition(0);
            servo1.setPosition(1);
        }

/*
        if (digitalTouch.getState() == true) {
            telemetry.addData("Digital Touch", "Is Not Pressed");
            telemetry.addData("Digital Touch", digitalTouch.getState());
        } else {
            telemetry.addData("Digital Touch", "Is Pressed");
        }
*/
        telemetry.update();


        //  if (gamepad2.left_bumper) {
        //     intakeLowMotor.setPower(-1);
        // } else if (gamepad2.right_bumper) {
        //     intakeHighMotor.setPower(-1);
        //} else {
        ///    intakeLowMotor.setPower(0);
        //   intakeHighMotor.setPower(0);
        // }
       }


    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}



