
 /* Author Phesus        //////////////////////////////
 *  ORC,ACR             /////////////
 *                     /////////////
 *                    /////////////
 *                   /////////////
 * //////////////////////////////                   */

package omoikane.principal

import omoikane.*
 import omoikane.caja.CajaManager;
 import omoikane.sistema.*
import omoikane.sistema.Usuarios as SisUsuarios
import javax.swing.JFrame
 import javax.swing.JInternalFrame
 import java.awt.Rectangle;

class MenuPrincipal {
    omoikane.formularios.MenuPrincipal menuPrincipal = new omoikane.formularios.MenuPrincipal()

    void iniciar()
    { if(!SisUsuarios.cerrojo(SisUsuarios.CAPTURISTA)) {
            //Si es cajero no se lanza el menú, ingresa directo a caja
            CajaManager manager = CajaManager.getInstance();
            JInternalFrame internalFrame = manager.startJFXCaja();

    } else {
            menuPrincipal.setBounds(new Rectangle(menuPrincipal.getPreferredSize()))
            Herramientas.panelCatalogo(menuPrincipal);
            Herramientas.centrarVentana menuPrincipal

            menuPrincipal.setVisible(true)
            Principal.escritorio.getPanelEscritorio().add(menuPrincipal)
            menuPrincipal.toFront()
            menuPrincipal.requestFocusInWindow()
            Herramientas.iconificable(menuPrincipal)

            menuPrincipal.lblVersion.setText "<html><head><style type='text/css'>body { font-family: 'Roboto Thin'; font-size: 10px; }</style></head> <body>"+Herramientas.getVersion()+"</span></body></html>"

            try {
                menuPrincipal.setSelected(true)
            } catch(Exception e)
            {
                sistema.Dialogos.lanzarDialogoError(null, "Error al iniciar menu principal", Herramientas.getStackTraceString(e))
            }
            menuPrincipal.btnVender.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnUsuarios.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnArticulos.requestFocusInWindow() }
            }
            menuPrincipal.btnArticulos.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnVender.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnAlmacenes.requestFocusInWindow() }
            }
            menuPrincipal.btnAlmacenes.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnArticulos.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnUsuarios.requestFocusInWindow() }
            }
            menuPrincipal.btnUsuarios.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnAlmacenes.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnVender.requestFocusInWindow() }
            }
            menuPrincipal.btnDetallesVentas.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnReportes.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnLineas.requestFocusInWindow() }
            }
            menuPrincipal.btnLineas.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnDetallesVentas.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnMovAlmacen.requestFocusInWindow() }
            }
            menuPrincipal.btnMovAlmacen.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnLineas.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnReportes.requestFocusInWindow() }
            }
            menuPrincipal.btnCerrar.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnMepro.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnCajas.requestFocusInWindow() }
            }
            menuPrincipal.btnCortes.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnReportesAvanzados.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnGrupos.requestFocusInWindow() }
            }
            menuPrincipal.btnGrupos.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnCortes.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnTomaInventarios.requestFocusInWindow() }
            }
            menuPrincipal.btnCajas.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnCerrar.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnEtiquetas.requestFocusInWindow() }
            }
            menuPrincipal.btnReportes.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnReportes.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnReportes.requestFocusInWindow() }
            }
            menuPrincipal.btnPreferencias.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnProveedores.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnCerrar.requestFocusInWindow() }
            }
            menuPrincipal.btnTomaInventarios.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnGrupos.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnReportesAvanzados.requestFocusInWindow() }
            }
            menuPrincipal.btnEtiquetas.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnCajas.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnProveedores.requestFocusInWindow() }
            }
            menuPrincipal.btnProveedores.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnEtiquetas.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnPreferencias.requestFocusInWindow() }
            }
            menuPrincipal.btnMepro.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnCompras.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnCerrar.requestFocusInWindow() }
            }
            menuPrincipal.btnReportes.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnMovAlmacen.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnDetallesVentas.requestFocusInWindow() }
            }
            menuPrincipal.btnTraspasos.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnTomaInventarios.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnCortes.requestFocusInWindow() }
            }
            menuPrincipal.btnClientes.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnCerrar.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnCompras.requestFocusInWindow() }
            }
            menuPrincipal.btnCompras.keyReleased = {
                if(it.keyCode==it.VK_UP)   { menuPrincipal.btnClientes.requestFocusInWindow() }
                if(it.keyCode==it.VK_DOWN) { menuPrincipal.btnMepro.requestFocusInWindow() }
            }


            menuPrincipal.btnCerrar.actionPerformed = {
                menuPrincipal.dispose()
                Principal.cerrarSesion()
            }
        }
    }
}

