
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

### Dependencies

The dependencies are JavaFX v17 and the external jars included in the "lib" folder
The runnable jar file is located in the "input" folder
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

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/javafx-%23FF0000.svg?style=for-the-badge&logo=javafx&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Windows Terminal](https://img.shields.io/badge/Windows%20Terminal-%234D4D4D.svg?style=for-the-badge&logo=windows-terminal&logoColor=white)

