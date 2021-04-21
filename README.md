# Overview
Table of contends:

* [Installation](#installation)
  * [How to run](#how-to-run)
* [Usage](#usage)
* Extra

# Installation

On Windows/Linux:

1. Download the .exe or .jar file.
2. Download and install java 11 or higher.  
   without installing java:  
	 1. download the .zip and place it in the folder of the .exe or .jar.
   2. unzip the package.
3. You are ready to go.

### How to run

presupposes that java 11 or higher is installed or the java package was downloadet.

Windows:

1. Run the .exe or .jar with double click or with cmd/powershell
   ```cmd
   C:\Users\PCUser\InstalationFolder> ./"nHentaiDatabase v1.4.6.exe"
   or
   C:\Users\PCUser\InstalationFolder> java -jar "nHentaiDatabase v1.4.6.jar"
   ```
   
Linux:

1. Run the .jar file with the terminal
  ```terminal
  User@HostName:~$ java -jar "nHentaiDatabase v1.4.6.jar"
  ```
  note: never use `sudo` then all the data will be stored in `\root`. You can use it but then you habe to always use it.
  
  <!--
add hyperlink to download
-->

# Usage

### First start

when you first start nHentaiDatabase some resources will be prepared.
- On Windows the Folder is in `%appdata%`.
- On Linux the Folder is in `/home/User` note: it is hidden.

The Folder structure is the following:

- nHentaiDatabase
  - randomPhotos
    - (random Pictures for the SFW mode)
  - savedPhotos
    - (all the title pictures of the doujins)
  - userData
    - (all three tables (plan to read, reading, completed) and the settings.)
    
### UI

#### Start
