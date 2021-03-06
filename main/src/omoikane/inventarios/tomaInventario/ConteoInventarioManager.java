package omoikane.inventarios.tomaInventario;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import omoikane.etiquetas.ImpresionEtiquetasController;
import omoikane.etiquetas.ImpresionEtiquetasModel;
import omoikane.principal.Principal;
import omoikane.sistema.Herramientas;
import omoikane.sistema.Permisos;
import omoikane.sistema.SceneOverloaded;
import org.springframework.context.ApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Octavio Ruiz
 * Date: 12/03/13
 * Time: 01:41
 * To change this template use File | Settings | File Templates.
 */
public class ConteoInventarioManager {

    private JInternalFrame frame;

    private SceneOverloaded initTomaInventarioCRUD() throws IOException {
        Platform.setImplicitExit(false);
        ApplicationContext context = Principal.applicationContext;

        SceneOverloaded scene = (SceneOverloaded)context.getBean("conteoInventarioCRUDView");

        return scene;
    }
    /*
    private SceneOverloaded initTomaInventario() throws IOException {
        Platform.setImplicitExit(false);
        ApplicationContext context = Principal.applicationContext;

        SceneOverloaded scene = (SceneOverloaded)context.getBean("tomaInventarioView");

        return scene;
    }*/

    public JInternalFrame startJFXTomaInventario() {
        JInternalFrame frame = null;
        if(omoikane.sistema.Usuarios.cerrojo(Permisos.getPMA_CREARINVENTARIO())) frame = _startJFXTomaInventario();
        return frame;
    }

    private JInternalFrame _startJFXTomaInventario() {
        frame = new JInternalFrame("Toma de inventarios");
        final JFXPanel fxPanel = new JFXPanel();

        frame.setClosable(true);
        frame.add(fxPanel);
        frame.setVisible(true);

        Herramientas.panelCatalogo(frame);
        Principal.getEscritorio().getPanelEscritorio().add(frame);
        frame.setSize(1024, 640);
        frame.setPreferredSize(new Dimension(1024, 640));
        frame.setVisible(true);
        Herramientas.centrarVentana(frame);
        Herramientas.iconificable(frame);
        frame.setResizable(true);
        frame.toFront();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                SceneOverloaded scene = null;
                try {
                    scene = initTomaInventarioCRUD();
                    ((ConteoInventarioCRUDController)scene.getController()).setJInternalFrame(frame);
                    ((ConteoInventarioCRUDController)scene.getController()).setFXPanel(fxPanel);

                    //scene.setFill(null);
                    fxPanel.setScene(scene);
                } catch (IOException e) {

                }

            }
        });

        return frame;
    }

}
