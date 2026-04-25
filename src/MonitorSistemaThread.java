package hilos;

import controlador.AppController;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class MonitorSistemaThread extends Thread {
    private boolean activo;
    private JLabel label;
    private AppController controller;

    public MonitorSistemaThread(AppController controller, JLabel label) {
        this.controller = controller;
        this.label = label;
        this.activo = true;
    }

    public void detener() {
        activo = false;
    }

    @Override
    public void run() {
        while (activo) {
            SwingUtilities.invokeLater(() -> label.setText(
                    "Usuarios activos: " + controller.getSistema().getUsuariosActivos()
                            + " | Usuarios: " + controller.getSistema().getTotalUsuarios()
                            + " | Cursos: " + controller.getSistema().getTotalCursos()
                            + " | Secciones: " + controller.getSistema().getTotalSecciones()
            ));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                activo = false;
            }
        }
    }
}
