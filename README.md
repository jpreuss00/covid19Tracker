# CovidTracker

# Links

- https://enigmatic-retreat-23877.herokuapp.com/
- https://enigmatic-retreat-23877.herokuapp.com/health/

# Als CovidTracker Nutzer möchte ich informiert werden, wenn in meiner näheren Umgebung ein Covid Positiv Mensch sich aufhält.
-	Alle 5 Minuten wird ein Standort check gemacht
-	Falls User nah aneinander sind (2Meter?) Nutzer informieren – über Browser Notifications ?

# Als CovidTracker Nutzer möchte ich meinem Netzwerk darüber informieren, dass ich positiv getestet bin.
-	Standortdaten mit Userinformationen hinterlegen, um mögliche Falschmeldung rückgängig zu machen bzw. bei Heilung den Eintrag wieder löschen zu können
-	Evtl. bei Registrierung des Codes random userID generieren, wenn die echten Daten nicht eingegeben werden wollen
-	deleteCode zum löschen wird generiert

# Als CovidTracker Nutzer möchte ich informiert werden, wenn ich in den letzten 48 Stunden in der Nähe einer Person war, die vor kurzem positiv getestet wurde.
-	Standort wird alle 5-10 Sekunden überprüft, wenn er sich um mehr als 5 Meter vom vorherigen unterscheidet, wird er gespeichert

# Wie sieht das Datenmodell im Backend aus?
<table>
  <tr>
    <th><b>userID</b></th>
    <th><b>deleteCode</b></th>
  </tr>
  <tr>
    <td>7384</td>
    <td>7384#qplahg</td>
  </tr>
    <tr>
    <td>6591</td>
    <td>6591#hbgzaa</td>
  </tr>
</table>

<table>
  <tr>
    <th><b>userID</b></th>
    <th><b>Standortdaten</b></th>
  </tr>
  <tr>
    <td>7384</td>
    <td>41.40338, 2.17403.</td>
  </tr>
    <tr>
    <td>7384</td>
    <td>17.40338, 78.17403.</td>
  </tr>
   <tr>
    <td>6591</td>
    <td>17.40337, 78.17405.</td>
  </tr>
</table>

# Wie kann ich die App anonym machen? brauche ich Namen?
-	random user ID
# Wie komme ich an die GPS Daten ran?
-	Browser
# Wie berechne ich mit GPS Nähe? Was heißt Nähe? 1m entfernt?
-	https://www.kompf.de/gps/distcalc.html
# Wie kann ist sowas testen ohne GPS zu haben?
-	Standortdaten(Adresse) eingeben und mit diesen die GSP Koordinaten berechnen

# Beeing informed when covid patient is in the close
- Precondition: The user has to be registered
1. The user enters his UserID in the given panel.
2. The user presses the given button to submit his current location.
3. The System informs the user, if a covid patient is in the close.
4. The user confirms, that he wanna be informed, if a covid patient is in the close, by clicking the given checkbox.
5. The System inform the user, that his action has been registered and that he is being informed, when a covid patient is in the close.
6. Use Case ends with Success
- Postcondition: The user has been informed, if a covid patient is in the close
- Exception: The user has entered a wrong userID

# User registration 
1. The User clicks the registration button.
2. The system displays the UserID and delete code.
- Postcondition: The user has to write down the data

# User deletion
- Precondition: The user has to be registered
1. The user enters his delete code
2. The user clicks the given button
3. The system displays that the user has been deleted.
- Exception: The user has entered a wrong delete code

# The user shares his location
- Precondition: The user has to be registered
1. The user writes his userID in the given input field.
2. The user clicks the given button to submit his location.
3. The system displays that his location has been saved to the system.
- Exception: The user has entered a wrong userID

# Was ist ein Milestone im Context Software Entwicklung?
- Milestones teilen ein Projekt in mehrere Schritte mit Zwischenzielen auf.

# Erkläre den extreme programming cycle... gibts dort auch Milestones?
- normaler cycle: linear
- extreme cycle: alle Aufgaben nach und nach erledigen, es gibt keine Bedingung um eine Aufgabe zu starten.
- im extreme programming cycle wird mit releases gearbeitet, ein Release beschreibt hierbei eine Vorversion der Applikation, welche die vorher bestimmten Features beinhalten muss
- der Unterschied zum Milestone ist hierbei, dass ein Milestone das erreichen eines Ziels bestimmt und die Apllikation dann nicht schon in einer abgespeckten Version verfügbar und funktionsfähig sein muss. Ein Milestone könnte z.B. die Fertigstellung einer Registery sein, am Ende des Milestone kann man sich dann z.B. erfolgreich in die Website einloggen und hat den Milestone fertiggestellt, die Website hat jedoch noch keinerlei Funktionen und kann noch nicht benutzt werden, wie es bei einem erfolgreichen release das Ziel währe

# Lese das 4te Kapitel von Implementation Patterns "Motivation" und erkläre die Kosten eines Software-Projekts. Sind alle Projekte gleich?
- die Gesamtkosten bestehen zum einen aus den kosten, die bei der Entwicklung entstehen und zum anderen aus den Kosten, die bei der Wartung entstehen
- die Wartungskosten setzen sich aus den folgenden vier Aspekten zusammen: die aufgewendete Zeit um die App zu verstehen, die Zeit um die App zu verändern, die Zeit die zum Testen benötigt wird und die Zeit um die App Einsatzbereit zu machen

# Wann gelten die Aussagen von Kent Beck nicht? 
- Wenn keine Kommunikation zwischen den Programmierern besteht.

# Wie kannst du die Kosten deiner Software gering halten?
- Mehr in die ursprüngliche Programmierung investieren, bevor später viel Geld in der Wartung ausgegeben wird.
- klarer Code mit einer klare Architektur mit Erklärungen und umfassenden Kommentaren für alle Aufgaben.
- Probleme: "Heute ist ein Dollar mehr Wert als morgen" und die "Unsicherheit der Zeit". Meistens wird eher auf das schnelle Geld geachtet, als auf die einfache Weiterentwicklung in der Zukunft zu setzen.
