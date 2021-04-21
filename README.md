# Overview
Table of contends:

* [Installation](#installation)
  * [How to run](#how-to-run)
* [Usage](#usage)
* Extra

# Installation

On Windows/Linux:

1. Download the .exe or .jar file.
2. Download and install Java 11 or higher.  
   without installing Java:  
	 1. Download the .zip and place it in the folder of the .exe or .jar.
   2. Unzip the package.
3. You are ready to go.

### How to run

Presupposes that Java 11 or higher is installed or the Java package was downloaded.

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
  Note: Never use `sudo`, because if you use `sudo` then all the data will be stored in `\root`. You can use it but then you have to always use it.
  
  <!--
add hyperlink to download
-->

# Usage

### First start

When you first start nHentaiDatabase some ressources will be prepared.
- On Windows the Folder is in `%appdata%`.
- On Linux the Folder is in `/home/User` Note: It's hidden.

The Folder structure is as following:

- nHentaiDatabase
  - randomPhotos
    - (Random pictures for the SFW mode)
  - savedPhotos
    - (All the title pictures of the doujins)
  - userData
    - (All three tables (plan to read, reading, completed) and the settings.)
    
### UI

#### Start

When you start nHentaiDatabase the following window will be prompted.

![Start](https://user-images.githubusercontent.com/77382879/115596335-48073680-a2d8-11eb-9491-abd21c20c770.png)

By clicking on `start nHentaiDatabase` all contents are getting loaded and the main GUI will be printed.

#### Main GUI

![MainGUI](https://user-images.githubusercontent.com/77382879/115596857-dd0a2f80-a2d8-11eb-852f-3a537997793a.png)

At the top you can see three sections.

- plan to read
- reading
- completed

They help you to keep your doujins organised.

#### new Entry

![newEntry](https://user-images.githubusercontent.com/77382879/115597134-3bcfa900-a2d9-11eb-9ca9-2190f2efabd3.png)

You can enter a new entry with the id or with the URL. You can also direcly choose the rating and the status.
For all users of big .txt files I made an option to import multiple id's at once. You simply choose your textfile after enabaling the `import multiple codes` option.

#### save and load

![saveLoad](https://user-images.githubusercontent.com/77382879/115597902-168f6a80-a2da-11eb-8902-50bfe7deb325.png)

save:  
You can export the current table.

load:  
Load one file in the current table. Note: All contends in the table are overwritten.

#### settings

##### stats

#### inspect

![oneEntry](https://user-images.githubusercontent.com/77382879/115598730-0461fc00-a2db-11eb-861b-f38c6833a4ac.png)

After creating a new Entry you can see the inspect Button.  
When clicking on it, a new window will be prompted.

![inspect](https://user-images.githubusercontent.com/77382879/115599209-9cf87c00-a2db-11eb-889f-6e710a1fd4bc.png)

Here you can see all data about the entry.  
By clicking on the picture you can open nHentai in your Browser.
