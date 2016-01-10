import RPi.GPIO as GPIO
import time
from threading import Thread
from Tkinter import *
import tkFont

GPIO.setmode(GPIO.BCM)
top = Tk()
top.title("Robot - CM")
buttonFont = tkFont.Font(family='Monospace', size=36)

DCMR_A = 16
DCMR_B = 20
DCML_A = 24
DCML_B = 23
M_ENABLE = 26
TRIG = 18
ECHO_F = 19
ECHO_R = 4
LED = 13
forward = "w"
left = "a"
backward = "s"
right = "d"
quit = "q"
thisOption = ""
distanceFront = 0
distanceRear = 0
distLabel_R = StringVar()
distLabel_R.set("Loading...")
distLabel_F = StringVar()
distLabel_F.set("Loading...")

#Motor Setup
GPIO.setup(DCMR_A, GPIO.OUT)
GPIO.setup(DCMR_B, GPIO.OUT)
GPIO.setup(DCML_A, GPIO.OUT)
GPIO.setup(DCML_B, GPIO.OUT)
GPIO.setup(M_ENABLE, GPIO.OUT)

#IR Sensor Setup
GPIO.setup(LED,GPIO.OUT)
GPIO.setup(TRIG,GPIO.OUT)
GPIO.setup(ECHO_F,GPIO.IN)
GPIO.setup(ECHO_R,GPIO.IN)

#Enable Motors (all)
GPIO.output(M_ENABLE, True)
print "Motors initiated."

#Enable IR Sensor
GPIO.output(TRIG,0)
time.sleep(0.1)
print "IR Sensor initiated."

def driveRefresh():
	GPIO.output(DCMR_A, False)
	GPIO.output(DCMR_B, False)
	GPIO.output(DCML_A, False)
	GPIO.output(DCML_B, False)
	time.sleep(0.25)

def driveForward():
	if distanceFront > 15:
		GPIO.output(DCMR_A, True)
		GPIO.output(DCMR_B, False)
		GPIO.output(DCML_A, True)
		GPIO.output(DCML_B, False)
		GPIO.output(LED, False)
	else:
		GPIO.output(LED, True)
		driveRefresh()
		print "Unable to move forward. Front distance: %dcm" % (distanceFront)

def driveBackward():
	if distanceRear > 15:
		GPIO.output(DCMR_A, False)
		GPIO.output(DCMR_B, True)
		GPIO.output(DCML_A, False)
		GPIO.output(DCML_B, True)
		GPIO.output(LED, False)
	else:
		GPIO.output(LED, True)
		driveRefresh()
		print "Unable to move backward. Rear distance: %dcm" % (distanceRear)
def driveLeft():
	GPIO.output(DCMR_A, True)
	GPIO.output(DCMR_B, False)
	GPIO.output(DCML_A, False)
	GPIO.output(DCML_B, True)	

def driveRight():
	GPIO.output(DCMR_A, False)
	GPIO.output(DCMR_B, True)
	GPIO.output(DCML_A, True)
	GPIO.output(DCML_B, False)	

def distanceCheckLoop():
	global distanceFront
	global distanceRear
	GPIO.output(TRIG,1)
	time.sleep(0.00001)
	GPIO.output(TRIG,0)
	while GPIO.input(ECHO_F) == 0:
		pass
	startF = time.time()
	while GPIO.input(ECHO_F) == 1:
		pass
	stopF = time.time()

	GPIO.output(TRIG,1)
	time.sleep(0.00001)
	GPIO.output(TRIG,0)
	while GPIO.input(ECHO_R) == 0:
		pass
	startR = time.time()
	while GPIO.input(ECHO_R) == 1:
		pass
	stopR = time.time()

	distanceFront = ((stopF - startF) * 17000)
	distLabel_F.set("%d cm" % (distanceFront))
	distanceRear = ((stopR - startR) * 17000)
	distLabel_R.set("%d cm" % (distanceRear))
	#print thisOption
	print "Distance Front: %fcm / Distance Rear: %rcm" % (distanceFront, distanceRear)
	#print thisOption

	if thisOption is forward:
		driveForward()
	if thisOption is left:
		driveLeft()
	if thisOption is backward:
		driveBackward()
	if thisOption is right:
		driveRight()
	
	top.after(500, distanceCheckLoop)

def setForward():
	thisOption = forward
	print "Set move to forward."

def setLeft():
	thisOption = left
	print "Set move to left."

def setRight():
	thisOption = right
	print "Set move to right."

def setBackward():
	thisOption = backward
	print "Set move to backward."

driveRefresh()
GPIO.output(LED, False)

print "Drive Time..."

up_button = Button(top, text = "^", command = setForward)
up_button['font'] = buttonFont
up_button.grid(row=0,column=1)
left_button = Button(top, text = "<", command = setLeft)
left_button['font'] = buttonFont
left_button.grid(row=1,column=0)
right_button = Button(top, text = ">", command = setRight)
right_button['font'] = buttonFont
right_button.grid(row=1,column=2)
down_button = Button(top, text = "v", command = setBackward)
down_button['font'] = buttonFont
down_button.grid(row=2,column=1)
distf_label_t = Label(top, text = "Distance front:")
distf_label_t.grid(row=4,column=1)
distf_label_b = Label(top, textvariable = distLabel_F)
distf_label_b.grid(row=5,column=1)
distr_label_t = Label(top, text = "Distance rear:")
distr_label_t.grid(row=6,column=1)
distr_label_b = Label(top, textvariable = distLabel_R)
distr_label_b.grid(row=7,column=1)
top.after(0, distanceCheckLoop)
top.mainloop()			

print "Script finished"
GPIO.cleanup()
