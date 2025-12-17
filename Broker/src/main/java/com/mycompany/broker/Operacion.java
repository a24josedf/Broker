package com.mycompany.broker;

/**
 *
 * @author dam2_alu04@inf.ald
 */
public class Operacion implements Runnable {

    private String tipo;
    private double limite;
    private double cantidad;
    private Usuario usuario;
    private Thread hiloEjecutor;

    public Operacion(Usuario usuario, String tipo, double limite, double cantidad) {
        this.usuario= usuario;
        setTipo(tipo);
        this.limite = limite;
        this.cantidad = cantidad;
        hiloEjecutor = new Thread(this);
        hiloEjecutor.start();
    }    

    @Override
    public void run() {
        //bucle
            //comprobar precio broker
                //si 
                    //pedir el stock 
                    //sumar o restar 
                    //liberar stock
                //no
                    //duermo
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        if (tipo.equalsIgnoreCase("compra")||tipo.equalsIgnoreCase("venta")) {
            this.tipo = tipo;
        }
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

}
