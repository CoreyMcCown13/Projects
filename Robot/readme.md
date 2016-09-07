#Raspberry Pi Robot
A robot made using a Raspberry Pi and Python

##Goals
####Self Driving Capability
- Detect walls before collision. Turn to avoid anything in it’s path.
- Detect if stuck. If it gets stuck, attempt different maneuvers to dislodge itself.

####Remote Control Driving
- Still detect and stop any possible collisions.
- Easy to use GUI, using arrows and showing distances to front/rear objects.

##Hardware
- Raspberry Pi 2 Model B
- 9v AA Battery Pack (Motors)
- 5000mAh @ 5v USB Battery Pack (Pi)
- 2x Breadboard
- 2x Arduino Twin Motor Controller Shield
- 4x DC Motor/Wheel
- 2x HC-SR04 Ultrasonic Range Detector

##Software
- GUI created in Python for controllability
- Control motors and read data via GPIO pins
