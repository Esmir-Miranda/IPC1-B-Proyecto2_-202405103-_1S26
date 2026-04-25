package hilos;

import controlador.AppController;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class MonitorSesionesThread extends Thread {
    private boolean activo;
    private final AppController controller;
    private final JTextArea area;

    public MonitorSesionesThread(AppController controller, JTextArea area) {
        this.controller = controller;
        this.area = area;
        this.activo = true;
    }

    public void detener() { activo = false; }

    @Override
    public void run() {
        while (activo) {
            SwingUtilities.invokeLater(() -> area.setText("[Thread-Sesiones]\nUsuarios activos: " + controller.getSistema().getUsuariosActivos()));
            try { Thread.sleep(10000); } catch (InterruptedException e) { return; }
        }
    }
}
