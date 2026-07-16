CINEMA MANAGEMENT SYSTEM — INTELLIJ SETUP (STEP BY STEP)
===========================================================

Ye ek CLEAN folder hai — sirf "src" folder hai isme, koi Eclipse/Gradle
junk files nahi hain. Isliye IntelliJ mein direct open ho jayega.

STEP 1: Is zip ko extract karo
--------------------------------
Kahin bhi extract karo (Desktop pe bhi chalega), jaise:
   C:\CinemaManagementSystems

STEP 2: JavaFX SDK download karo (agar pehle se nahi hai)
------------------------------------------------------------
Tumhara JDK version 17 hai, isliye JavaFX SDK bhi 17 wala lena hai:
   https://gluonhq.com/products/javafx/
   -> Version: 17.0.15 (ya koi bhi 17.0.x)
   -> OS: Windows
   -> Type: SDK
Download karke fix jagah pe extract karo, jaise:
   C:\javafx-sdk-17.0.15
(Ye folder kabhi delete/move mat karna)

STEP 3: IntelliJ mein open karo
----------------------------------
- IntelliJ ke welcome screen pe "Open" pe click karo
  (NEW PROJECT pe click NAHI karna — sirf Open)
- Extract kiya hua "CinemaManagementSystems" folder select karo
  (wo wala jisme seedha "src" folder dikh raha ho)
- "Trust Project" aaye to Trust kar dena

STEP 4: src ko Sources Root banao
------------------------------------
- Left side Project panel mein "src" folder pe right-click
- Mark Directory as -> Sources Root
(Agar ye option already grey/disabled hai, iska matlab already sources
root hai, next step pe chale jao)

STEP 5: Project SDK set karo
--------------------------------
- File -> Project Structure  (Ctrl+Alt+Shift+S)
- Project tab -> SDK me apna JDK 17 select karo
  (Agar list mein nahi hai to "Add SDK" -> JDK folder select karo)

STEP 6: JavaFX library add karo
------------------------------------
Same Project Structure window mein:
- Libraries tab -> "+" -> Java
- Jahan JavaFX SDK extract kiya tha uska "lib" folder select karo
  (e.g. C:\javafx-sdk-17.0.15\lib)
- OK -> module ke saath Add kar do
- Apply -> OK

STEP 7: Run Configuration mein VM options daalo
----------------------------------------------------
- src/cinema/Main.java file open karo
- Line number ke paas right-click -> Modify Run Configuration
- VM options field mein ye paste karo (apna path use karke):

--module-path "C:\javafx-sdk-17.0.15\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base

- Apply -> OK

STEP 8: RUN karo
--------------------
Main.java open rakh kar green Play button (▶) dabao ya Shift+F10.
Login screen aana chahiye.

===========================================================
Agar koi error aaye to uska screenshot le kar bhej dena.
