package app;

import controlador.AppController;
import vista.LoginFrameTop;

public class Main {
    public static void main(String[] args) {
        AppController controller = new AppController();
        new LoginFrameTop(controller);
    }
}