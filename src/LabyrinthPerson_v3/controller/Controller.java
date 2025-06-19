package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.*;

import model.Direction;
import model.Board;
import model.GameState;
import view.View;

/**
 * Der Controller ist Teil des MVC-Musters und fungiert als Vermittler zwischen Benutzerinteraktionen (Tastatur, Buttons)
 * und dem Spielmodell (GameState). Er verarbeitet Tasteneingaben, Mausereignisse und Button-Klicks.
 * Diese Klasse erweitert JFrame, sodass sie direkt als Hauptfenster des Spiels verwendet werden kann.
 */
public class Controller extends JFrame implements KeyListener, ActionListener, MouseListener {

	/** Der aktuelle Zustand des Spiels, der bei Benutzereingaben aktualisiert wird. */
	private GameState gameState;

	/** Liste registrierter Views. Wird aktuell nicht verwendet, aber vorgesehen. */
	private List<View> views;

	/**
	 * Konstruktor des Controllers. Initialisiert Event-Listener für Tastatur, Maus und Buttons.
	 *
	 * @param gameState Der aktuelle Spielzustand, der bei Eingaben verändert wird.
	 * @param buttons   Ein Array von Buttons, über die der Benutzer Aktionen ausführen kann.
	 */
	public Controller(GameState gameState, JButton[] buttons) {
		// Speichert den Spielzustand
		this.gameState = gameState;

		// Registriert den KeyListener, um Tastatureingaben zu verarbeiten.
		addKeyListener(this);

		// Registriert MouseListener (derzeit nicht genutzt).
		addMouseListener(this);

		// Fügt jedem übergebenen Button eine ActionListener-Logik hinzu.
		int i = 0;
		for (JButton b : buttons) {
			int finalI = i;

			b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// Rückwärts-Schritt
					if (finalI == 0) {
						gameState.stepGameState(-1);
						requestFocusInWindow();
					}
					// Vorwärts-Schritt
					if (finalI == 1) {
						gameState.stepGameState(1);
						requestFocusInWindow();
					}
					// Neustart: Fenster schließen und Main erneut aufrufen
					if (finalI == 2) {
						dispose(); // Aktuelles Fenster schließen
						Labyrinth.main(new String[0]); // Hauptmethode erneut starten
					}
				}
			});
			i++;
		}
	}

	/**
	 * Wird aufgerufen, wenn ein Key getippt wird (nicht verwendet).
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// Nicht implementiert
	}

	/////////////////// Key Events ////////////////////////////////

	/**
	 * Wird aufgerufen, wenn eine Taste gedrückt wird.
	 * Führt Bewegungen im Spiel oder Schritt-Aktionen aus.
	 *
	 * @param e Das KeyEvent-Objekt mit der gedrückten Taste.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// Reagiert auf Pfeiltasten und WASD für Richtungsbewegungen
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP, 87: // W oder Pfeil nach oben
				gameState.movePlayer(Direction.UP);
				if (gameState.isGameEnded()) {
					this.dispose(); // Spiel beendet → Fenster schließen
				}
				break;

			case KeyEvent.VK_DOWN, 83: // S oder Pfeil nach unten
				gameState.movePlayer(Direction.DOWN);
				if (gameState.isGameEnded()) {
					this.dispose();
				}
				break;

			case KeyEvent.VK_LEFT, 65: // A oder Pfeil nach links
				gameState.movePlayer(Direction.LEFT);
				if (gameState.isGameEnded()) {
					this.dispose();
				}
				break;

			case KeyEvent.VK_RIGHT, 68: // D oder Pfeil nach rechts
				gameState.movePlayer(Direction.RIGHT);
				if (gameState.isGameEnded()) {
					this.dispose();
				}
				break;

			case 79: // Taste 'O' → Schritt zurück
				gameState.stepGameState(-1);
				break;

			case 80: // Taste 'P' → Schritt vor
				gameState.stepGameState(1);
				break;
		}
	}

	/**
	 * Methode, die potenziell bei Button-Druck helfen könnte (nicht genutzt).
	 */
	public void buttonPressed() {
		this.getContentPane().getFocusTraversalPolicy();
	}

	/**
	 * Wird aufgerufen, wenn eine Taste losgelassen wird (nicht verwendet).
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// Nicht implementiert
	}

	/////////////////// Action Events ////////////////////////////////

	/**
	 * Wird aufgerufen, wenn eine allgemeine Aktion ausgelöst wird (nicht verwendet).
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Nicht implementiert
	}

	/////////////////// Mouse Events ////////////////////////////////

	/**
	 * Wird aufgerufen, wenn eine Maustaste gedrückt und losgelassen wird (nicht verwendet).
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// Nicht implementiert
	}

	/**
	 * Wird aufgerufen, wenn eine Maustaste gedrückt wird (nicht verwendet).
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// Nicht implementiert
	}

	/**
	 * Wird aufgerufen, wenn eine Maustaste losgelassen wird (nicht verwendet).
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// Nicht implementiert
	}

	/**
	 * Wird aufgerufen, wenn die Maus das Fenster betritt (nicht verwendet).
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// Nicht implementiert
	}

	/**
	 * Wird aufgerufen, wenn die Maus das Fenster verlässt (nicht verwendet).
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// Nicht implementiert
	}
}
