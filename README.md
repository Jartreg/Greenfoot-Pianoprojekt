# Greenfoot-Pianoprojekt
Ein Midi-Keyboard in Greenfoot

## Klassen

### `Piano`
`Piano` fügt beim Start ein `InfoText`-Objekt und für die Klaviatur mehrere `Taste`-Objekte zur Welt hinzu.
Wenn eine Taste gedrückt wird, spielt `Piano` den Ton entweder über Midi oder die Tondateien ab.
Wenn Midi verfügbar ist, kann auch die Oktave und das Instrument geändert werden.

### `Taste`
Taste wartet auf Tastendrücke und Mausklicke und teilt Piano mit, ob die Taste gedrückt ist.
Die Klassen `TasteWeiss` und `TasteSchwarz` werden dafür benötigt, um festzulegen, dass schwarze Tasten über weißen gezeichnet werden.

### `Note`
Enthält Informationen über eine Note, wie den Namen, die Nummer und die Oktave.

### `InfoText`
Zeigt dem Benutzer nacheinander Hinweise an, wie das Keyboard benutzt wird.
Nachdem alle Hinweise durchfelaufen sind, entfernt `InfoText` sich selbst.
