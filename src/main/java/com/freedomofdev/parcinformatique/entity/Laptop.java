package com.freedomofdev.parcinformatique.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;



@Entity(name = "BestLaptops")
public class Laptop {

    @Id
    private Long id;
    private String name;
    private String ram;
    private String processor;
    private double displayInInch;
    private String storage;
    private double rating;
    private double priceInUsd;

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRam() { return ram; }
    public void setRam(String ram) { this.ram = ram; }

    public String getProcessor() { return processor; }
    public void setProcessor(String processor) { this.processor = processor; }

    public double getDisplayInInch() { return displayInInch; }
    public void setDisplayInInch(double displayInInch) { this.displayInInch = displayInInch; }

    public String getStorage() { return storage; }
    public void setStorage(String storage) { this.storage = storage; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public double getPriceInUsd() { return priceInUsd; }
    public void setPriceInUsd(double priceInUsd) { this.priceInUsd = priceInUsd; }
}