# ***OOP_Projekt_2025***


# Inhaltsverzeichnis
- [Allgemeines](#allgemeines)
- [Aufbau des Codes](#aufbau-des-codes)
  - [Controller](#controller)
    - [Klasse Controller](#ins-controller-ins)
    - [Klasse Labyrinth](#labyrinth)
  - [Model](#ins-model-ins)
    - [Board](#board)
    - [Coordinates](#coordinates)
    - [Difficulty](#difficulty)
    - [Direction](#direction)
    - [Field](#field)
    - [GameState](#gamestate)
    - [History](#history)
    - [Player](#player)
    - [enemyPackage](#ins-enemypackage-ins)
      - [EasyEnemy](#easyenemy)
      - [Enemy](#enemy)
      - [NormalEnemy](#normalenemy)
    - [generators](#ins-generators-ins)
      - [Generator](#generator)
      - [GeneratorEmpty](#generatorempty)
      - [GeneratorFromImage](#generatorfromimage)
      - [GeneratorHilbert](#generatorhilbert)
      - [MainMaze](#mainmaze)
    - [View](#ins-view-ins)
      - [ConsoleView](#consoleview)
      - [GraphicView](#graphicview)
      - [View](#view)



# Allgemeines

OOP Abschluss Projekt von Anthony Röder und Thomas Kaczmarczyk. </br>

Für das Projekt wurde das Java Development Kit (JDK) 24
und als IDE IntelliJ IDEA Community Edition verwendet.
Weiter wurde für die Zusammenarbeit Github benutzt.

# Aufbau des Codes

Die generelle Struktur orientiert sich an der Vorlage, 
die wir zur Verfügung gestellt bekommen haben. 
Im Folgenden werden alle Packages durchgegangen, 
grob erklärt
und der Zusammenhang zu dem Rest des Projekts dargestellt.

## <ins> Controller </ins>

In dem Package Controller sind die angepassten Klassen Controller und Labyrinth. 
Generell wurde an diesen Klassen nicht viel verändert.


### *Controller:*

Der Controller funktioniert so wie uns die Vorlage ihn auch vor geliefert hat.
Während des Spiels schaut der Controller, 
ob ein Input des Users vorliegt 
und führt aufgrund des Inputs, 
spezifische Aktionen durch, 
die das Spielgeschehen beeinflussen.

Änderungen im Controller:

- Der Controller erhält zusätzlich zu dem GameState (was das ist, wird später erklärt), 
auch die Buttons mit übergeben, 
die in der GUI verwendet werden.

- Der Constructor erzeugt nun neben den Listenern für die Key- und Mouse-Inputs,
auch Listener für die Buttons des GUI. 
In diesem Schritt wird auch definiert,
welche Aktion ausgeführt werden soll,
wenn ein Button betätigt worden ist.

- Im Switch-Case wurde zusätzlich zu dem Support von den Pfeiltasten, 
auch die Unterstützung von WASD hinzugefügt 
und die Tasten "O" und "P" mit den Funktionen zurück- und vorspringen belegt.

  

### *Labyrinth:*

Die Labyrinth-Klasse, ist die Klasse, 
die das Spiel startet und muss somit ausgeführt werden,
wenn ein Spieler das Spiel spielen möchte. 
In ihr werden alle wichtigen Komponenten erstellt, 
um das Spiel auszuführen 
und somit dient sie als Main-Klasse des Projekts.

Sie funktioniert so, dass der Spieler zunächst einmal gefragt wird,
in welchem Modus er/sie das Spiel starten möchte. 
Aufgrund dieser Auswahl wird dann ein Board (Spielfeld),
mit den dazugehörigen Maßen 
und ein GameState (Spielstand) erstellt. 
Darauf folgend werden jeweils die Konsolenausgabe,
sowie die Ausgabe in der GUI erstellt und registriert.
Damit diese Ausgaben aktualisiert werden können,
wird ein Controller initialisiert, 
der zusätzlich zu dem Spielstand auch Buttons als Parameter 
übergeben bekommt, damit der Fokus nicht geteilt werden muss 
im Controller.



Änderungen im Labyrinth:

- Als erstes, wird nun über den Menü-Screen abgefragt, 
welche Schwierigkeit das Spiel haben soll. 
Aufgrund dieser Auswahl wird dann ein Board (das Spielfeld) generiert. 
Darunter fällt die Definition der width und der height des Boards 
und wo der Spieler starten soll.

- Bevor der Controller initialisiert wird, 
werden zunächst die notwendigen Buttons erstellt,
die in der GUI verwendet werden sollen 
und diese werden als zusätzlicher Parameter dem Controller übergeben.


## <ins> Model </ins>

Das Modell enthält den "Inhalt" des Spiels. 
Zunächst gehe ich auf die Klassen ein,
die direkt in dem Package Model liegen.
Im Anschluss beschreibe ich dann die Packages "enemyPackage",
"generators" und "rules".

### *Board*

Das Board bildet das Spielfeld. 
Dort werden die Informationen, 
wie die Dimensionen und der Aufbau des Feldes (Labyrinths) gespeichert.
Das Feld wird in einem zweidimensionalen Field-Array gespeichert.
Die Felder, die in diesem Array gespeichert sind, sind in einer Enum Field definiert.

Die Board-Klasse besitzt diverse Getter und Setter Methoden, die in den JavaDoc-Kommentaren
beschrieben sind.

### *Coordinates*

Die Klasse Coordinates ist leider etwas zu spät eingeführt worden,
war in einigen Funktionen allerdings sehr wichtig, wie bspw. bei "deleteAllWalls".
Sie speichert eine X und eine Y Koordinate und besitzt ebenfalls getter und setter Methoden.


### *Difficulty*

Die Enum "Difficulty" wurde erst eingeführt, um die Auswahl des Users zu berücksichtigen,
welche Schwierigkeit das Labyrinth haben soll. Sie würde allerdings expandiert,
um auch ***den QR-Code*** abzudecken. 
Dort sind also insgesamt 4 Konstanten definiert.


### *Direction*

Die Enum "Direction" wurde aus der Vorlage übernommen und nicht geändert. 
Sie enthält alle möglichen Richtungen,
in die ein Spieler gehen könnte,
mit den jeweiligen "Vektoren".


### *Field*

Die Enum "Field" enthält die verschiedenen Feldtypen, 
die in dem Board verwendet werden.


### *GameState*

Der GameState bildet im gewissen Sinne das Herzstück des Spiels.
Wir haben die Klasse "World",
die vorher das Herzstück gebildet hat,
in zwei Teile (GameState und Board) geteilt.
Zum einen in den GameState 
und zum anderen in das Board.
Der GameState (Spielstand) speichert alle relevanten Daten,
zu einem gewissen Stand des Spiels.
Dies beinhaltet unter anderem die Position des Spielers,
die Position der Gegner, das Board und den Spielverlauf,
sowie weitere wichtige Variablen und Parameter.
Durch die größe dieser Klasse,
sollten die JavaDoc-Kommentare betrachtet werden,
um die einzelnen Funktionen zu verstehen.


### *History*

Die Klasse History wird genutzt,
um eines der optionalen Features umzusetzen.
Die History speichert den gesamten Spielverlauf bis zum aktuellen Zeitpunkt.
Der Spielverlauf wird dann verwendet, 
um Züge rückgängig zu machen.
Hat man einen Zeitpunkt gefunden, von dem man weiterspielen möchte,
kann man dies dann einfach tun.
Der Spielverlauf wird in einer Array-Liste aus GameStates gespeichert.


### *Player*

Die Klasse "Player" speichert die Koordinaten des Spielers,
also der Figur, die vom User gesteuert wird.</br></br>


### <ins> EnemyPackage </ins>

Wie der Name schon vermuten lässt,
befindet sich in diesem Package alles,
was mit dem Gegner zu tun hat.


### *EasyEnemy*

In dem EasyEnemy wird die Position eines Gegners gespeichert.
Weiter beinhalten alle Gegner-Klassen eine Methode, 
die einen Zug spielen kann,
aufgrund einer Logik,
die für die Gegnertypen festgelegt worden sind.


### *Enemy*

Die Klasse "Enemy" ist eine abstrakte Klasse, 
da alle Gegnerklassen egal welche Logik für die Züge dahinter steht,
eine Position auf dem Spielfeld haben müssen.
Alle Enemy-Klassen erben von dieser Klasse.


### *NormalEnemy*

Die Klasse "NormalEnemy" funktioniert genau so, 
wie die "EasyEnemy".
Sie speichert auch die Position eines Gegners 
und hat eine funktion,
die einen Zug ausführt.
</br></br>

### <ins> Generators </ins>

In dem Package "generators" sind verschiedene Labyrinth-Generatoren zu finden.


### *Generator*

Karteileiche... (Sollte eigentlich mal eine abstrakte Klasse werden).


### *GeneratorEmpty*

Die Klasse "GeneratorEmpty" generiert ein leeres Board.


### *GeneratorFromImage*

Diese Klasse kann Labyrinthe aufgrund von Bitmaps erstellen.


### *GeneratorHilbert*

Diese Klasse generiert im Rahmen der extra Features zufällige Labyrinthe.


### *MainMaze*

Diese Klasse beinhaltet das vordefinierte Labyrinth,
dass Hauptsächlich verwendet wird.

## <ins> Resources </ins>

In resources befinden sich alle Assets
und QR-Codes die benutzt worden sind.


## <ins> View </ins>

In den folgenden Klassen ist der Hauptbestandteil der grafischen Oberflächen zu finden.

### *ConsoleView*

Die Klasse "ConsoleView" ist verantwortlich für die Darstellung des Spiels in der Konsole.
Hier findet man eine Update-Funktion, 
die mithilfe von verschachtelten For-Schleifen das Board in der Konsole ausgibt.

### *GraphicView*

Die Klasse "GraphicView" ist verantwortlich für die Darstellung des Spiels in dem GUI.
Wie auch in "ConsoleView",
existiert eine Update-Funktion,
die sich alle benötigten Informationen einholt,
damit das Spielfeld in der GUI ausgegeben werden kann.
Wenn alle Informationen eingeholt worden sind,
wird das Board in der GUI durch die paint-Funktion dargestellt.


### *MenuScreen*

Die Klasse "MenuScreen" ist verantwortlich für die Darstellung des Hauptmenüs.
Hier kann der User auswählen, 
wie schwer das Level sein soll.


### *View*

Das Interface View wird benötigt, da von ihr in "ConsoleView" und "GraphicView" geerbt wird.
Es wird genutzt, damit andere Klassen wissen,
ob sich der GameState verändert hat.
