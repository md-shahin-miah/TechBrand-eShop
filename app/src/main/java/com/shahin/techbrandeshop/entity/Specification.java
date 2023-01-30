package com.shahin.techbrandeshop.entity;

import java.io.Serializable;

public class Specification implements Serializable {
    private String cpu;
    private String os;
    private String ram;
    private String gpu;
    private String monitor;
    private String hardDrive;
    private String connectionGate;
    private String keyboard;
    private String battery;
    private float weight;


    public Specification(String cpu, String os, String ram, String gpu, String monitor, String hardDrive, String connectionGate, String keyboard, String battery, float weight) {
        this.cpu = cpu;
        this.os = os;
        this.ram = ram;
        this.gpu = gpu;
        this.monitor = monitor;
        this.hardDrive = hardDrive;
        this.connectionGate = connectionGate;
        this.keyboard = keyboard;
        this.battery = battery;
        this.weight = weight;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getHardDrive() {
        return hardDrive;
    }

    public void setHardDrive(String hardDrive) {
        this.hardDrive = hardDrive;
    }

    public String getConnectionGate() {
        return connectionGate;
    }

    public void setConnectionGate(String connectionGate) {
        this.connectionGate = connectionGate;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
