package it.polimi.ingsw.utils;

import it.polimi.ingsw.view.cli.Ansi;

public enum GodType {

    APOLLO("APOLLO"), ARTEMIS("ARTEMIS"), ATHENA("ATHENA"), ATLAS("ATLAS"), CHARON("CHARON"), CHRONUS("CHRONUS"), DEMETER("DEMETER"),
    HEPHAESTUS("HEPHAESTUS"), HESTIA("HESTIA"), MINOTAUR("MINOTAUR"), PAN("PAN"), PROMETHEUS("PROMETHEUS"), TRITON("TRITON"), ZEUS("ZEUS");

    private final String name;

    GodType(String name) {
        this.name = name;
    }

    public static GodType getTypeFromString(String godName) {

        godName = godName.toUpperCase();

        switch (godName) {
            case "APOLLO":
                return APOLLO;
            case "ARTEMIS":
                return ARTEMIS;
            case "ATHENA":
                return ATHENA;
            case "ATLAS":
                return ATLAS;
            case "CHARON":
                return CHARON;
            case "CHRONUS":
                return CHRONUS;
            case "DEMETER":
                return DEMETER;
            case "HEPHAESTUS":
                return HEPHAESTUS;
            case "HESTIA":
                return HESTIA;
            case "MINOTAUR":
                return MINOTAUR;
            case "PAN":
                return PAN;
            case "PROMETHEUS":
                return PROMETHEUS;
            case "TRITON":
                return TRITON;
            case "ZEUS":
                return ZEUS;
            default:
                return null;
        }

    }

    public String getName() {
        return name;
    }

    public String getCapitalizedName(){
        return name.substring(0, 1) + name.substring(1).toLowerCase();
    }
}
