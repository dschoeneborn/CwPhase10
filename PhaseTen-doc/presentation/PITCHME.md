# Phaseten

---

## Agenda:

- Vorstellung der Gruppe
- Spielidee
- Architektur
- GUI
- Spiellogik
- AI
- Datenhaltung

---

## Vorstellung der Gruppe

<table>
  <tr>
    <td>Robin Harbecke</td>
    <td>Daniela Kaiser</td>
  </tr>
  <tr>
    <td>Sven Krefeld</td>
    <td>Björn Merschmeier</td>
  </tr>
  <tr>
    <td>Marc Mettke</td>
    <td>Tim Prange</td>
  </tr>
  <tr>
    <td>Dennis Schöneborn</td>
    <td>Sebastian Seitz</td>
  </tr>
</table>

---

## Spielidee

- Klassisches Phase 10
- Es gibt 10 Phasen mit verschiedenen Aufgaben
- Am Ende jeder Runde: Punkte zählen
- Ziel: So wenig Punkte wie möglich

Note:
- Phase 10 ist ein Spiel von Mattel (ähnlich Rommé)
- Phase 1 Aufgabe: 2 Drillinge (erst wenn ausgelegt ist, Karten anlegen)
- Punkte zählen wenn eine Person keine Karten mehr hat
- Spiel Endet wenn jemand Phase 10 erreicht

---

## Spielidee - Erweiterung

- Erweiterung um Coins
- Registrierung: 500 Coins
- Kosten: 50 Coins pro Spiel
- Jede Minute, die gespielt wird, wird belohnt
- Am Ende werden die gesetzten Coins umverteilt
  - Wenigste Punkte = Meiste Coins
  - Meiste Punkte = Wenigste Coins
  
---

## Spielidee - Erweiterung

- Künstliche Intelligenz (AI)
- genauere Informationen hierzu später

---

## Architektur
### Models

- Abbildung des realen Spiels als Klassen

---?image=https://i.imgur.com/bohBQgC.png&size=auto 90%

---

## Client-Server Kommunikation

- RPC / JMS Mischung
- Verteilung der Daten via Topic
- Bei privaten Daten CORBA


---?image=https://camo.githubusercontent.com/d3a0c54eb518f2f20237c99fa942215c1467c845/68747470733a2f2f692e696d6775722e636f6d2f6e504f73324d542e6a7067&size=auto 90%

---

## Verwendete EJBs

- Stateless:
  + AIManagmentBean
  + CoinManagementBean
  + GameManagementBean
  + GameValidationBean
  + LobbyManagementBean
  + UserManagementBean

---

## Verwendete EJBs

- Stateful:
  + UserSessionBean
- Message Driven:
  + GameMessageBean

---

## GUI

---?image=assets/image/login.png&size=auto 55%

---?image=assets/image/lobby.png&size=auto 75%

---?image=assets/image/playground.png&size=auto 85%

---

## Spiellogik

- Regeln analysiert
- Angefangen mit Aktivitätsdiagrammen
- Grundlegende Aktionen vergessen
- Validierungs-Methode für jede Spieleraktion geschrieben

Note:
- Begonnen, das Spiel mit allen einmal zu spielen, damit Grundverständnis besteht
- Regeln durchgelesen
- Festgestellt, dass jeder das Spiel ein wenig anders spielt
- Aktivitätsdiagramm für Spielablauf & Spielerzug erstellt
- Es folgt: Aktivitätsdiagramm

---?image=https://raw.githubusercontent.com/svenkrefeld/cw-phaseten-presentation/master/assets/image/PhaseTen_doMove.png&size=95% auto

---

## Spiellogik - verschiedene Piles

- Es gibt DockPiles
- Es existieren Unterklassen
- Jede Unterklasse implementiert die "canAddCardLast" und "canAddCardFirst" als Validierungsmethoden (unterschiedlich)
- Die Oberklasse prüft diese
- Tests für die enstehenden Stapel (> 20 Stück)

Note:
- Wie im Klassendiagramm gesehen, gehört eine Karte immer zu irgendeinem Pile
- Pile gliedert sich in "PlayerPile", "LifoStack", "PullStack", "DockPile"
- Verschiedene Dockpiles: "ColorDockPile", "SequenceDockPile" und "SetDockPile"
- Tests für jeden Stapel verschieden mit vielen Sonderfällen
- "Nur Joker" für ein Pile ist recht interessant, da keine Regel dafür existiert

---

## AI
- Funktion zur Deckbewertung
- Eigentliche Spielmethoden
---

### Spielmethoden

- Ziehe Karte
- Lege Phase  
- Lege Karten zu Stapel
- Karte auf Ablegestapel legen  
  
Note:

- Ziehe Karte  
  + Vergleicht Wert von Ablage- und Ziehstapel
- Lege Phase  
  + Legt mögliche Phase ab
- Lege Karten zu Stapel
  + Lege alle Karten auf mögliche Stapel
- Karte auf Ablegestapel legen  
  + Lege die Karte x mit höchstem verbleibendem Deckwert ab

---

### Kartenbewertungsfuntion

- Bewertet ein übergebenes Deck anhand von Spielfelds und Phase
- Je nach Situation wird eine andere genommen
- Phase noch nicht gelegt
  + Berechne fehlende Karten
  + 100 Minuspunkte pro fehlender Karte
- Phase schon gelegt
  + Ignoriere alle ablegbaren Karten
  + Minuspunkte jeder Karte entsprechen Kartenwert
  
---

## Datenhaltung

- Verwendung einer MySQL-Datenbank
- Anbindung über JPA
  + Implementierung EclipseLink
- Abbildung über Entities 
  + DDL über Annotationen
  + Verwendung von NamedQueries
  + Uni-/Bidirektionale Beziehungen über JoinColumns
- Entities beinhalten möglichst keine Logik 
  + Verwendung eines EntityListeners

Note:
- lokale DB bei jedem lokal installiert, äquivalent eingerichtet
- Id jeweils generiert, Vererbung zB. über MappedSuperclass () beim Pile...
- EntityListener: initial 500 Coins, Logging, erweiterbar um weitere Funktionen
- Bei Beziehungen: FetchType und Cascade situationsabhängig gewählt
---

## Schluss

Vielen Dank für Ihre Aufmerksamkeit.
