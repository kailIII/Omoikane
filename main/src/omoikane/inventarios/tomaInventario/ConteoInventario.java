package omoikane.inventarios.tomaInventario;

import omoikane.entities.Usuario;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: octavioruizcastillo
 * Date: 04/04/13
 * Time: 23:04
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class ConteoInventario {

    private Long id;

    private Date fecha;
    private Usuario usuario;

    private List<ItemConteoInventario> items;

    @PrePersist
    public void prePersist() {
        setFecha(new Date());
        setCompletado(false);
        setAplicado(false);
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @OrderColumn
    public List<ItemConteoInventario> getItems() { return items; }

    public void setItems(List<ItemConteoInventario> items) { this.items = items; }

    private Boolean completado;

    private Boolean aplicado;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConteoInventario() {
        items = new ArrayList<ItemConteoInventario>();
    }

    @Column
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Column
    @Index(name = "completadoIndex")
    public Boolean getCompletado() {
        return completado;
    }

    public void setCompletado(Boolean completado) {
        this.completado = completado;
    }

    @Column
    public Boolean getAplicado() {
        if(aplicado == null) aplicado = false;
        return aplicado;
    }

    public void setAplicado(Boolean aplicado) {
        this.aplicado = aplicado;
    }

    public String toString() {
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
        return  dateFormat.format(getFecha());
    }

}
