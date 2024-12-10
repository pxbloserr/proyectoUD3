package com.example.proyectoud3;

import java.io.Serializable;

public class Producto implements Serializable {

    private String nombre;
    private double precio;
    private int foto, udRestantes;

    public Producto(String nombre, double precio, int foto, int udRestantes) {
        this.nombre = nombre;
        this.precio = precio;
        this.foto = foto;
        this.udRestantes = udRestantes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public int getUdRestantes() {
        return udRestantes;
    }

    public void setUdRestantes(int udRestantes) {
        this.udRestantes = udRestantes;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", foto=" + foto +
                ", udRestantes=" + udRestantes +
                '}';
    }

}
