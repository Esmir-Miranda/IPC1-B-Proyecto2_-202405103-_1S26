package hilos;

import controlador.AppController;

import javax.swing.*;

public class MonitorSesionesThread extends Thread {
    private boolean activo = true;
    private final JTextArea area;
    private final AppController controller;

    public MonitorSesionesThread(AppController controller, JTextArea area) {
        this.controller = controller;
        this.area = area;
    }

    public void detener() {
        activo = false;
    }

    @Override
    public void run() {
        while (activo) {
            SwingUtilities.invokeLater(() -> area.setText("[Thread-Sesiones] Usuarios activos: " + controller.getSistema().getUsuariosActivos()));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}