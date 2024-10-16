
# Serial Communication App for CNC machines

This project came about when I worked for a company making their own CNC mill and lathe machines.  We needed a program to simply upload files to our larger CNC machines making our metal parts.  We searched for a simple solution, but the existing software/hardware products in the community were terrible and outdated. 
## Authors

- [@Jeremy Tipton](https://github.com/tiptonspiderj)

## Features
- Choose serial communication parameters
- Save parameters per machine
- Edit CAM files before sending
- Save CAM files when needed
- Clear text fields when needed

## Screenshots

![main screen](https://github.com/tiptonspiderj/Serial-Communication-App/blob/main/src/images/Main_Screen.png)

## Color Reference

| Color             | Hex                                                                |
| ----------------- | ------------------------------------------------------------------ |
| Background Color | ![#0a192f](https://via.placeholder.com/10/0a192f?text=+) #0a192f |
| Background Color | ![#f8f8f8](https://via.placeholder.com/10/f8f8f8?text=+) #f8f8f8 |
| Icon Color | ![#b99855](https://via.placeholder.com/10/b99855) #b99855 |
| Example Color | ![#727272](https://via.placeholder.com/10/727272?text=+) #727272 |


## Installation for Windows

You can make an executable installer from my project using java's jpackage tool and the following CLI commands:

 jlink --module-path "Your path to JavaFX-Mods-jars" --add-modules=ALL-MODULE-PATH --output runtime
 
jpackage -t exe --name Serial-Communication --description "Serial communication program Author: Jeremy Tipton" --app-version 1.0.0 --input input 
--dest output --main-jar SerialComm.jar --win-shortcut --runtime-image runtime

You can also download the executable file in the output folder labeled SerialComm-1.0.0.exe to install the program

### Executing program

If you just want to run the program from the executable jar in the "input" folder you can use the command:

java -jar --module-path "YOUR PATH TO\javafxsdk17.0.0.1\lib" --add-modules=ALL-MODULE-PATH SerialComm.jar
    
## Feedback

If you have any feedback, please reach out to me at tiptonspiderj1@aol.com


## Badges

Add badges from somewhere like: [shields.io](https://shields.io/)

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)
[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://opensource.org/licenses/)
[![AGPL License](https://img.shields.io/badge/license-AGPL-blue.svg)](http://www.gnu.org/licenses/agpl-3.0)

