/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package omoikane.entities;

import omoikane.producto.Articulo;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author octavioruizcastillo
 */
@Entity
@Table(name = "ventas_detalles")
@XmlRootElement

public class LegacyVentaDetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_renglon")
    private int idRenglon;
    @ManyToOne
    @JoinColumn(name = "id_venta")
    private LegacyVenta venta;
    @Basic(optional = false)
    @Column(name = "id_caja")
    private int idCaja;
    @Basic(optional = false)
    @Column(name = "id_almacen")
    private int idAlmacen;
    @Column(name = "id_articulo")
    private Integer idArticulo;
    @Basic(optional = false)
    @Column(name = "precio")
    private double precio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad")
    private Double cantidad;
    @Column(name = "tipo_salida")
    private String tipoSalida;
    @Column(name = "subtotal")
    private Double subtotal;
    @Column(name = "impuestos")
    private Double impuestos;
    @Column(name = "descuento")
    private Double descuento;
    @Column(name = "total")
    private Double total;
    @Column(name = "id_linea")
    private Integer idLinea;

    /*@ElementCollection()
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name="ventas_detalles_impuestos",
            joinColumns=@JoinColumn(name="id_renglon")
    ) */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "legacyVentaDetalle", targetEntity = VentaDetalleImpuesto.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<VentaDetalleImpuesto> ventaDetalleImpuestos;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_articulo", insertable = false, updatable = false)
    Articulo producto;

    public Articulo getProducto() {
        return producto;
    }

    public void setProducto(Articulo p) {
        producto = p;
    }

    public LegacyVentaDetalle() {

    }

    public Integer getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Integer idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getIdRenglon() {
        return idRenglon;
    }

    public void setIdRenglon(int idRenglon) {
        this.idRenglon = idRenglon;
    }

    public LegacyVenta getVenta() {
        return venta;
    }

    public void setVenta(LegacyVenta venta) {
        this.venta = venta;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoSalida() {
        return tipoSalida;
    }

    public void setTipoSalida(String tipoSalida) {
        this.tipoSalida = tipoSalida;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(Integer idLinea) {
        this.idLinea = idLinea;
    }

    public void setVentaDetalleImpuestos(List<VentaDetalleImpuesto> ventaDetalleImpuestos) {
        this.ventaDetalleImpuestos = ventaDetalleImpuestos;
    }

    public List<VentaDetalleImpuesto> getVentaDetalleImpuestos() {
        return ventaDetalleImpuestos;
    }

    public Double getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(Double impuestos) {
        this.impuestos = impuestos;
    }
}
