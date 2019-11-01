package org.firstinspires.ftc.teamcode;
// AUTONOMOUS BY TIME WITH COLOR, DISTANCE and TOUCH CODE

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

//package org.firstinspires.ftc.robotcontroller.external.samples;

@Autonomous(name = "AuT", group = "Sensor")
//@Disabled                            // Comment this out to add to the opmode list

public class AutonomousTimed extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    //DcMotor frontLeftMotor;
    //DcMotor frontRightMotor;
    DcMotor frontleftmotor;
    DcMotor frontrightmotor;
    DcMotor backleftmotor;
    DcMotor backrightmotor;
    DcMotor pulley;

    Servo servo0;  //Grabber
    Servo servo1;  //Grabber
    Servo servo2;  //Sensor Arm

    DigitalChannel digitaltouch;  // Hardware Device Object

    ColorSensor sensorcolor;
    DistanceSensor sensordistance;


    @Override
    public void runOpMode() throws InterruptedException {

        backleftmotor = hardwareMap.dcMotor.get("backwards_left_drive");
        backrightmotor = hardwareMap.dcMotor.get("backwards_left_drive");
        pulley = hardwareMap.dcMotor.get("Pulley_Motor");

        // get a reference to our digitalTouch object.
        digitaltouch = hardwareMap.get(DigitalChannel.class, "sensordigital");
        // set the digital channel to input.
        digitaltouch.setMode(DigitalChannel.Mode.INPUT);

        servo0 = hardwareMap.servo.get("servo0");
        servo1 = hardwareMap.servo.get("servo1");
        servo2 = hardwareMap.servo.get("servo2");

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

        // wait for the start button to be pressed.
        waitForStart();


        // LOOP LOOP LOOP
        // loop and read the RGB and distance data.
        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.

        runtime.reset();

        while (opModeIsActive()) {


            // convert the RGB values to HSV values.
            // multiply by the SCALE_FACTOR.
            // then cast it back to int (SCALE_FACTOR is a double)
            Color.RGBToHSV((int) (sensorcolor.red() * SCALE_FACTOR),
                    (int) (sensorcolor.green() * SCALE_FACTOR),
                    (int) (sensorcolor.blue() * SCALE_FACTOR),
                    hsvValues);

            // Send the info back to driver station using telemetry function.

            //telemetry.addData("Motor Output ", "Front Left" + frontleftmotor.getPower());
            //telemetry.addData("Motor Output ", "Front Right" + frontrightmotor.getPower());
            telemetry.addData("Motor Output ", "Back Left" + backleftmotor.getPower());
            telemetry.addData("Motor Output ", "Back Right" + backrightmotor.getPower());

            telemetry.addData("Distance (cm)",
                    String.format(Locale.US, "%.02f", sensordistance.getDistance(DistanceUnit.CM)));
            //telemetry.addData("Alpha", sensorcolor.alpha());
            //telemetry.addData("Red  ", sensorcolor.red());
            //telemetry.addData("Green", sensorcolor.green());
            //telemetry.addData("Blue ", sensorcolor.blue());
            telemetry.addData("Hue", hsvValues[0]);

            /*  SAMPLE TOUCH CODE
            if (digitaltouch.getState() == true) {
                telemetry.addData("Digital Touch", "Is Not Pressed");
                telemetry.addData("Digital Touch", digitaltouch.getState());
                liftmotor.setPower(.70);
            } else {
                telemetry.addData("Digital Touch", "Is Pressed");
                liftmotor.setPower(0);
            }
            */


/* */           //RESET RUNTIME
            //if(runtime.seconds() >= 0 && runtime.seconds() <= 1) {
                runtime.reset();
                runtime.startTime();
                telemetry.addData("Runtime Reset ", "Running: " + runtime.toString());

            //}
/* */
            //DROP SERVO
            if(runtime.seconds() >= 1.1 && runtime.seconds() <= 5) {
                telemetry.addData("In Drop Servo2 ", "Running: " + runtime.toString());
                servo2.setPosition(1);
            }

            //READ COLOR
            if(runtime.seconds() >= 5.1 && runtime.seconds() < 7) {
                telemetry.addData("In Read Color ", "Running: " + runtime.toString());

                // ASSUME WE ARE RED TEAM
                // ASSUME SENSOR FACING FORWARDS ON ROBOT ON RIGHT SIDE
                // CHECK IF STATEMENT FOR BLUE ONLY
                if (hsvValues[0] >= 150 && hsvValues[0] <= 250) {

                    telemetry.addData("In Blue If ", sensorcolor.blue());
                    telemetry.addData("Hue", hsvValues[0]);
                    backleftmotor.setPower(.2);     //BACK
                    backrightmotor.setPower(-.2);   //BACK
                    telemetry.addData("In BLUE Move Back ", "Running: " + runtime.toString());
                    backleftmotor.setPower(-.2);     //FORWARD TO RESET POSITION
                    backrightmotor.setPower(.2);     //FORWARD TO RESET POSITION

                } else {
                    telemetry.addData("In Red Else ", sensorcolor.red());
                    telemetry.addData("Hue", hsvValues[0]);
                    backleftmotor.setPower(-.2);     //FORWARD
                    backrightmotor.setPower(.2);     //FORWARD
                    telemetry.addData("ELSE Move Forwad ", "Running: " + runtime.toString());
                    backleftmotor.setPower(.2);     //BACK to
                    backrightmotor.setPower(-.2);   //BACK

                }
            }

//            if (hsvValues[0] >= 300 && hsvValues[0] <= 400)
//        } else if (hsvValues[0] <= 150) {


            //2 SECOND HOLD, DO NOTHING
            if(runtime.seconds() >= 7.1 && runtime.seconds() <= 9) {
                telemetry.addData("In 2 Second Do Nothing ", "Running: " + runtime.toString());
                //frontleftmotor.setPower(0);
                //frontrightmotor.setPower(0);
                backleftmotor.setPower(0);   //STOP MOTORS
                backrightmotor.setPower(0);   //STOP MOTORS

            }

            //RAISE SERVO
            if(runtime.seconds() >= 9.1 && runtime.seconds() <= 11.1) {
                telemetry.addData("In Raise Servo2 ", "Running: " + runtime.toString());
                servo2.setPosition(0);
            }

            //MOVE FORWARD
            if(runtime.seconds() >= 11.1 && runtime.seconds() <= 13) {

                telemetry.addData("In Move Forward ", "Running: " + runtime.toString());

                //frontleftmotor.setPower(.2);
                //frontrightmotor.setPower(-.2);
                backleftmotor.setPower(-.4);     //Faster FORWARD
                backrightmotor.setPower(.4);     //Faster FORWARD
            }


            //SPIN
            if(runtime.seconds() >= 13.1 && runtime.seconds() <= 15) {

                telemetry.addData("In Spin ", "Running: " + runtime.toString());

                //frontleftmotor.setPower(.2);
                //frontrightmotor.setPower(-.2);
                backleftmotor.setPower(.3);   // Slow Turn
                backrightmotor.setPower(.3);  // Slow Turn
            }

            //STOP DRIVE MOTORS
            if(runtime.seconds() >= 15.1 && runtime.seconds() <= 30) {
                //frontleftmotor.setPower(.2);
                //frontrightmotor.setPower(-.2);
                backleftmotor.setPower(0);
                backrightmotor.setPower(0);
            }



                // change the background color to match the color detected by the RGB sensor.
            // pass a reference to the hue, saturation, and value array as an argument
            // to the HSVToColor method.
            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });

            telemetry.update();
        }

        // Set the panel back to the default color
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.WHITE);
            }
        });
    }
}
