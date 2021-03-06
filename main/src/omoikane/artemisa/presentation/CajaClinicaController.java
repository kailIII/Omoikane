package omoikane.artemisa.presentation;


import java.awt.*;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.MarginBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.exception.DRException;
import omoikane.artemisa.CancelacionTransaccionDAO;
import omoikane.artemisa.PacienteRepo;
import omoikane.artemisa.entity.Abono;
import omoikane.artemisa.entity.CancelacionTransaccion;
import omoikane.artemisa.entity.Paciente;
import omoikane.artemisa.entity.Transaccion;
import omoikane.artemisa.reports.AbonoPrint;
import omoikane.artemisa.reports.CuentaSimplificadaPrint;
import omoikane.caja.presentation.ProductoModel;
import omoikane.entities.Cancelacion;
import omoikane.entities.Usuario;
import omoikane.producto.Articulo;
import omoikane.repository.CancelacionRepo;
import omoikane.sistema.Usuarios;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.number.CurrencyFormatter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.synyx.hades.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.transform.Templates;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;


public class CajaClinicaController
        implements Initializable {

    public static Logger logger = Logger.getLogger(CajaClinicaController.class);

    @Autowired
    PacienteRepo pacienteRepo;

    @Autowired
    JpaTransactionManager transactionManager;

    @Autowired
    CancelacionTransaccionDAO cancelacionTransaccionDAO;

    @PersistenceContext
    EntityManager entityManager;

    @FXML //  fx:id="abonoTxt"
    private TextField abonoTxt; // Value injected by FXMLLoader

    @FXML //  fx:id="partidaCol"
    private TableColumn<Transaccion, Long> partidaCol; // Value injected by FXMLLoader

    @FXML //  fx:id="abonosCol"
    private TableColumn<Transaccion, BigDecimal> abonosCol; // Value injected by FXMLLoader

    @FXML //  fx:id="cargosCol"
    private TableColumn<Transaccion, BigDecimal> cargosCol; // Value injected by FXMLLoader

    @FXML //  fx:id="conceptoCol"
    private TableColumn<Transaccion, String> conceptoCol; // Value injected by FXMLLoader

    @FXML //  fx:id="fechaCol"
    private TableColumn<Transaccion, Date> fechaCol; // Value injected by FXMLLoader

    @FXML //  fx:id="pacienteLbl"
    private Label pacienteLbl; // Value injected by FXMLLoader

    @FXML
    private Label idLabel;

    @FXML //  fx:id="pacientesList"
    private ListView<Paciente> pacientesList; // Value injected by FXMLLoader

    @FXML //  fx:id="tabEdoCuenta"
    private TableView<Transaccion> tabEdoCuenta; // Value injected by FXMLLoader

    @FXML
    private Label saldoTxt;

    @FXML CheckBox chkIncluirInactivos;

    @FXML //  fx:id="panelEstadoDeCuenta"
    private AnchorPane panelEstadoDeCuenta; // Value injected by FXMLLoader

    @FXML TextField txtBuscar;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert abonoTxt != null : "fx:id=\"abonoTxt\" was not injected: check your FXML file 'CajaClinicaView.fxml'.";
        assert partidaCol != null : "fx:id=\"partidaCol\" was not injected: check your FXML file 'CajaClinicaView.fxml'.";
        assert abonosCol != null : "fx:id=\"abonosCol\" was not injected: check your FXML file 'CajaClinicaView.fxml'.";
        assert cargosCol != null : "fx:id=\"cargosCol\" was not injected: check your FXML file 'CajaClinicaView.fxml'.";
        assert conceptoCol != null : "fx:id=\"conceptoCol\" was not injected: check your FXML file 'CajaClinicaView.fxml'.";
        assert fechaCol != null : "fx:id=\"fechaCol\" was not injected: check your FXML file 'CajaClinicaView.fxml'.";
        assert pacienteLbl != null : "fx:id=\"pacienteLbl\" was not injected: check your FXML file 'CajaClinicaView.fxml'.";
        assert pacientesList != null : "fx:id=\"pacientesList\" was not injected: check your FXML file 'CajaClinicaView.fxml'.";
        assert tabEdoCuenta != null : "fx:id=\"tabEdoCuenta\" was not injected: check your FXML file 'CajaClinicaView.fxml'.";
        assert saldoTxt != null : "fx:id=\"saldoTxt\" was not injected: check your FXML file 'CajaClinicaView.fxml'.";
        assert tabEdoCuenta != null : "fx:id=\"tabEdoCuenta\" was not injected: check your FXML file 'CajaClinicaView.fxml'.";

        partidaCol.setCellValueFactory(new PropertyValueFactory<Transaccion, Long>("id"));
        fechaCol.setCellValueFactory(new PropertyValueFactory<Transaccion, Date>("fecha"));
        cargosCol.setCellValueFactory(new PropertyValueFactory<Transaccion, BigDecimal>("cargo"));
        abonosCol.setCellValueFactory(new PropertyValueFactory<Transaccion, BigDecimal>("abono"));
        conceptoCol.setCellValueFactory(new PropertyValueFactory<Transaccion, String>("concepto"));

        resetPacienteListData();
        panelEstadoDeCuenta.setVisible(false);

        pacientesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Paciente>() {
            @Override
            public void changed(ObservableValue<? extends Paciente> observableValue, Paciente paciente, Paciente paciente2) {
                if(paciente2 == null)
                    panelEstadoDeCuenta.setVisible(false);
                else
                    {
                        selectPaciente(paciente2);
                        panelEstadoDeCuenta.setVisible(true);
                    }
            }
        });

        //*************************************************************
        //Configurar celda con boton eliminar transacción. Cortesía de: https://gist.github.com/jewelsea/3081826
        //*************************************************************
        TableColumn<Transaccion, Transaccion> actionCol = new TableColumn<>("Acciones");
        actionCol.setSortable(false);
        actionCol.setPrefWidth(150d);

        // define a simple boolean cell value for the action column so that the column will only be shown for non-empty rows.
        actionCol.setCellValueFactory(new PropertyValueFactory<Transaccion, Transaccion>("itself"));

        // create a cell value factory with an add button for each row in the table.
        actionCol.setCellFactory(new Callback<TableColumn<Transaccion, Transaccion>, TableCell<Transaccion, Transaccion>>() {
            @Override public TableCell<Transaccion, Transaccion> call(TableColumn<Transaccion, Transaccion> accionesTablecolumn) {
                return new ActionsCell(tabEdoCuenta);
            }
        });

        tabEdoCuenta.getColumns().add(actionCol);
        //*************************************************************

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtBuscar.requestFocus();
            }
        });
    }

    @FXML
    private void mostrarInactivosAction(ActionEvent event) {
        resetPacienteListData();
    }

    private void resetPacienteListData() {
        Boolean soloActivos = chkIncluirInactivos.isSelected();
        pacientesList.getSelectionModel().clearSelection();
        List<Paciente> pacienteList = pacienteRepo.findByLiquidadoAndNombreLike(soloActivos, "%"+txtBuscar.getText()+"%", new PageRequest(0, 10000));
        ObservableList<Paciente> pacienteObservableList = FXCollections.observableList(pacienteList);
        pacientesList.setItems(pacienteObservableList);
    }

    private TimerBusqueda timerBusqueda;
    @FXML
    private void onBusquedaKey(KeyEvent event) {
        String txtBusqueda = txtBuscar.getText();
        if ( txtBusqueda != null && !txtBusqueda.isEmpty() ) {
            if(timerBusqueda != null && timerBusqueda.isAlive()) { timerBusqueda.cancelar(); }
            this.timerBusqueda = new TimerBusqueda();
            timerBusqueda.start();
        }
    }

    List<Transaccion> transacciones;

    Paciente paciente;

    private void selectPaciente(final Paciente paciente) {

        this.paciente=paciente;
        pacienteLbl.setText(paciente.getNombre());
        idLabel.setText(paciente.getId().toString());
        transacciones = pacienteRepo.findTransaccionesOf(paciente);
        ObservableList<Transaccion> transaccions = FXCollections.observableArrayList( transacciones );
        tabEdoCuenta.setItems(transaccions);
        BigDecimal saldoPaciente = pacienteRepo.getSaldo(paciente);
        BigDecimal saldo = saldoPaciente != null ? saldoPaciente : new BigDecimal(0);
        saldoTxt.setText(NumberFormat.getCurrencyInstance().format( saldo ));
    }

    public void onAbonar(ActionEvent event) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        Object result = transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                abonar();
            }

        });
    }


    public void onImprimirX() {

        TextColumnBuilder cargoColumn, abonoColumn;

        StyleBuilder boldStyle         = stl.style().bold();
        StyleBuilder boldCenteredStyle = stl.style(boldStyle)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        StyleBuilder columnTitleStyle  = stl.style(boldCenteredStyle)
                .setBorder(stl.pen1Point())
                .setBackgroundColor(Color.LIGHT_GRAY);
        StyleBuilder titleStyle = stl.style(boldCenteredStyle)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setFontSize(15);
        StyleBuilder subTitleStyle = stl.style()
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setFontSize(15);
        AggregationSubtotalBuilder<BigDecimal> sumaA,sumaB;



        try {
            report()
                    .setPageFormat(PageType.LETTER, PageOrientation.PORTRAIT)
                    .setPageMargin(DynamicReports.margin().setRight(25).setLeft(25).setTop(25).setBottom(10))
                    .columns(
                            col.column("Fecha / Hora", "fecha", type.dateType()).setWidth(1).setHorizontalAlignment(HorizontalAlignment.LEFT),
                            col.column("Concepto", "concepto", type.stringType()).setWidth(4),
                            cargoColumn = col.column("Cargo", "cargo", type.bigDecimalType()).setWidth(1),
                            abonoColumn = col.column("Abono", "abono", type.bigDecimalType()).setWidth(1)
                    )
                    .setColumnTitleStyle(columnTitleStyle)
                    .highlightDetailEvenRows()
                    .title(
                            cmp.horizontalList()
                                    .add(
                                            cmp.image(getClass().getResourceAsStream("/omoikane/artemisa/images/LogoHA.png")).setFixedDimension(150, 70),
                                            //cmp.text("Estado de cuenta").setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.LEFT),
                                            cmp.text("Estado de cuenta").setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT))
                                    .newRow()
                                    .add(
                                            cmp.text("Paciente: " + pacienteLbl.getText()).setStyle(subTitleStyle).setHorizontalAlignment(HorizontalAlignment.LEFT),
                                            cmp.text(Calendar.getInstance().getTime()).setStyle(subTitleStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT))
                                    .newRow()
                                    .add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10)))
                    .pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))
                    .subtotalsAtSummary(
                            sumaA = sbt.sum(cargoColumn),
                            sumaB = sbt.sum(abonoColumn))
                    .summary(
                            cmp.horizontalList().add(cmp.text(new TotalPaymentExpression(sumaA, sumaB)).setHorizontalAlignment(HorizontalAlignment.RIGHT).setStyle(titleStyle))

                    )
                    .setDataSource(tabEdoCuenta.getItems())
                    .show(false);
        } catch (DRException e) {
            logger.error("Error al imprimir", e);
        }
    }


    private void abonar() {
        Abono nvoAbono = new Abono();
        Paciente paciente = pacientesList.getSelectionModel().getSelectedItem();
        if(abonoTxt.getText()==null) { return ; }
        try {
        nvoAbono.setImporte(new BigDecimal(abonoTxt.getText()));
        } catch (NumberFormatException n) { return ; }
        nvoAbono.setPaciente(paciente);

        entityManager.persist(nvoAbono);
        AbonoPrint abonoPrint = new AbonoPrint(nvoAbono);
        abonoPrint.show();
        logger.info("Abono registrado");
        abonoTxt.setText("");
        selectPaciente(paciente);
    }

    public void onImprimir() {

        CuentaSimplificadaPrint cuentaSimplificadaPrint = new CuentaSimplificadaPrint(transacciones,paciente);
        cuentaSimplificadaPrint.show();
    }

    /**
     * Celda con los botones de acción para una transacción
     */
    private class ActionsCell extends TableCell<Transaccion, Transaccion> {
        // Botón para borrar transacción
        final Button delButton       = new Button("Borrar");
        // pads and centers the add button in the cell.
        final StackPane paddedButton = new StackPane();
        // Botón para reimprimir abono
        final Button reciboButton       = new Button("Recibo");
        // Layout de los botones
        final HBox hBox;

        /**
         * ActionsCell constructor
         * @param table La tabla a la que se le agregarán los botones
         */
        ActionsCell(final TableView table) {
            hBox = new HBox();
            hBox.setSpacing(3d);

            paddedButton.setPadding(new javafx.geometry.Insets(3, 0, 0, 0));
            paddedButton.getChildren().add(hBox);

            //Relleno el HBox con los botones de acciones
            hBox.getChildren().add(delButton);
            hBox.getChildren().add(reciboButton);

            final TableCell<Transaccion, Transaccion> c = this;
            delButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {
                    TableRow tableRow = c.getTableRow();
                    Transaccion item= (Transaccion) tableRow.getTableView().getItems().get(tableRow.getIndex());
                    onEliminarTransaccion(item);
                }
            });

            reciboButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {
                    TableRow tableRow = c.getTableRow();
                    Transaccion item= (Transaccion) tableRow.getTableView().getItems().get(tableRow.getIndex());
                    AbonoPrint abonoPrint = new AbonoPrint((Abono) item);
                    abonoPrint.show();
                }
            });
        }

        /** places an add button in the row only if the row is not empty. */
        @Override protected void updateItem(Transaccion item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                if(item instanceof Abono)
                    reciboButton.setVisible(true);
                else
                    reciboButton.setVisible(false);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(paddedButton);
            }
        }
    }

    private void onEliminarTransaccion(final Transaccion item) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        Object result = transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                Transaccion t = entityManager.find(Transaccion.class, item.getId());
                entityManager.remove(t);
                registrarCancelacionTransaccion( t );
                logger.info("Transacción eliminada");
                selectPaciente( pacientesList.getSelectionModel().getSelectedItem() );
            }
        });
    }

    private void registrarCancelacionTransaccion(Transaccion quitar) {
        CancelacionTransaccion c = new CancelacionTransaccion();
        c.setConcepto(quitar.getConcepto());
        c.setPaciente(quitar.getPaciente());
        c.setImporte(quitar.getCargo().subtract(quitar.getAbono()));
        cancelacionTransaccionDAO.save(c);
    }

    public void onLiquidar() {
        Paciente selectedPaciente = pacientesList.getSelectionModel().getSelectedItem();
        BigDecimal saldo = pacienteRepo.getSaldo(selectedPaciente);
        if(saldo == null) { saldo = new BigDecimal(0); }
        if(saldo.abs().compareTo(new BigDecimal("0.01")) > 0) {
            logger.info("El saldo debe ser cero para liquidar ésta cuenta. El saldo es: "+NumberFormat.getCurrencyInstance().format(saldo));
            return;
        }
        selectedPaciente.setLiquidado(true);
        pacienteRepo.save(selectedPaciente);
        logger.info("Cuenta liquidada.");
        resetPacienteListData();
    }

    private class TotalPaymentExpression extends AbstractSimpleExpression<String> {
        private static final long serialVersionUID = 1L;
        private AggregationSubtotalBuilder<BigDecimal> sumaA,sumaB;

        public TotalPaymentExpression(AggregationSubtotalBuilder sumaA ,AggregationSubtotalBuilder sumaB){
            this.sumaA=sumaA;
            this.sumaB=sumaB;
        }
        @Override
        public String evaluate(ReportParameters reportParameters) {
            NumberFormat formater = NumberFormat.getCurrencyInstance();
            BigDecimal sa = reportParameters.getValue(sumaA);
            BigDecimal sb = reportParameters.getValue(sumaB);
            BigDecimal sc = sa.subtract(sb);
            return "Saldo: "+ formater.format(sc.doubleValue());
        }
    }

    class TimerBusqueda extends Thread
    {
        boolean busquedaActiva = true;

        public void run()
        {
            synchronized(this)
            {
                busquedaActiva = true;
                try { this.wait(500); } catch(Exception e) { logger.error("Error en el timer de búsqueda automática", e); }
                if(busquedaActiva) {
                    Platform.runLater(() -> { CajaClinicaController.this.resetPacienteListData(); });
                }
            }
        }
        void cancelar()
        {
            busquedaActiva = false;
            try { this.notify(); } catch(Exception e) {}
        }
    }

}


