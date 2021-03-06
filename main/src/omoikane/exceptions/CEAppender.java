/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package omoikane.exceptions;

import javafx.application.Platform;
import omoikane.principal.Principal;
import omoikane.sistema.Dialogos;
import omoikane.sistema.Herramientas;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 *
 * @author mora
 */
public class CEAppender extends AppenderSkeleton {

    @Override
    public void close() {
        //Nothing
    }

    @Override
    public boolean requiresLayout() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void append(final LoggingEvent event) {
        if ( event.getLevel().isGreaterOrEqual(Priority.WARN) ) {
            errorWindow(event);
        } else if(event.getLevel().isGreaterOrEqual(Priority.INFO)) {
            Toolkit.getDefaultToolkit().beep();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(null,
                            event.getMessage(),
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });

        } else if(Principal.DEBUG) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,
                    event.getMessage(),
                    "Información de depuración",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void errorWindow(final LoggingEvent event) {

        final Object monitor = new Object();

        synchronized (monitor) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    synchronized (monitor) {
                        try {
                            //System.out.println(Misc.getStackTraceString(event.getThrowableInformation().getThrowable()));
                            Frame frames[] = JFrame.getFrames();
                            JFrame mainJFrame = frames.length > 1 ? (JFrame) frames[0] : null;
                            String stackTrace = "";
                            if(event.getThrowableInformation() != null && event.getThrowableInformation().getThrowable() != null)
                                stackTrace = Misc.getStackTraceString(event.getThrowableInformation().getThrowable());
                            else
                                stackTrace = "Sin stacktrace";
                            Dialogos.lanzarDialogoError(mainJFrame, event.getMessage(), stackTrace);

                            monitor.notify();
                        } catch (Exception e) {
                            monitor.notify();
                            e.printStackTrace();
                        }
                    }
                }
            });
            /*if(!SwingUtilities.isEventDispatchThread())
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }          */
        }

    }

}
