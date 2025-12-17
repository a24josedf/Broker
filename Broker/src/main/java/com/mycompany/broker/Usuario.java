package com.mycompany.broker;

import java.util.Objects;

/**
 *
 * @author dam2_alu04@inf.ald
 */
public class Usuario {

    private String id;
    private String nombre;
    private double saldo;
    private Operacion operacionCompra;
    private Operacion operacionVenta;

    public Usuario(String id, String nombre, double saldo) {
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;

    }

    public boolean nuevaOperacion(Usuario this, String tipo, double limite, double cantidad) {
        switch (tipo) {
            case "compra":
                if (operacionCompra == null) {
                    operacionCompra = new Operacion(this, tipo, limite, cantidad);
                } else {
                    System.out.println("Ya existe una operacion de compra para el agente " + getNombre());
                    return false;
                }
                break;
            case "venta":
                if (operacionVenta == null) {
                    operacionVenta = new Operacion(this, tipo, limite, cantidad);
                } else {
                    System.out.println("Ya existe una operacion de venta para el agente " + getNombre());
                    return false;
                }
                break;
            default:
                return false;
        }
        return true;
    }

    public Operacion getOperacionCompra() {
        return operacionCompra;
    }

    public void setOperacionCompra(Operacion operacionCompra) {
        this.operacionCompra = operacionCompra;
    }

    public Operacion getOperacionVenta() {
        return operacionVenta;
    }

    public void setOperacionVenta(Operacion operacionVenta) {
        this.operacionVenta = operacionVenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id = " + id + ", nombre = " + nombre + ", saldo = " + saldo + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        return Objects.equals(this.id, other.id);
    }

}
