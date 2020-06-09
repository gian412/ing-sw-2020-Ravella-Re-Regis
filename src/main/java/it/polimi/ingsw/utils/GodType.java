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

    public static String getTypeFromString(GodType god) {
        Ansi maker = new Ansi();
        String font = maker.font(Ansi.BLUE_B);

        switch (god) {
            case APOLLO:
                return  font +
                        "YOUR MOVE: " + Ansi.RESET +
                        "your worker may move in into an opponent Worker's space by forcing their Worker to the space yours just vacated";
            case ARTEMIS:
                return font +
                        "YOUR MOVE: " + Ansi.RESET +
                        "Your Worker may move one additional time, but not back to its initial space.";
            case ATHENA:
                return font +
                        "OPPONENT'S TURN: " + Ansi.RESET +
                        "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";
            case ATLAS:
                return font +
                        "YOUR BUILD: " + Ansi.RESET +
                        "Your Worker may build a dome at any level.";
            case CHARON:
                return font +
                        "YOUR MOVE: " + Ansi.RESET +
                        "Before your Worker moves, you may force a neighboring opponent Worker to the space directly on the other " +
                        "side of your Worker, if that space is unoccupied.";
            case CHRONUS:
                return font +
                        "CONDITION WIN: " + Ansi.RESET +
                        "You also win when there are at least five Complete Towers on the board.";
            case DEMETER:
                return font +
                        "YOUR BUILD: " + Ansi.RESET +
                        "Your Worker may build one additional time, but not on the same space.";
            case HEPHAESTUS:
                return font +
                        "YOUR BUILD: " + Ansi.RESET +
                        "Your Worker may build one additional block (not dome) on top of your first block.";
            case HESTIA:
                return font +
                        "YOUR BUILD: " + Ansi.RESET +
                        "Your Worker may build one additional time, but this cannot be on a perimeter space.";
            case MINOTAUR:
                return font +
                        "YOUR MOVE: " + Ansi.RESET +
                        "Your Worker may move into an opponent Worker’s space, if their Worker can be forced " +
                        "one space straight backwards to an unoccupied space at any level.";
            case PAN:
                return font +
                        "WIN CONDITION: " + Ansi.RESET +
                        "You also win if your Worker moves down two or more levels.";
            case PROMETHEUS:
                return font +
                        "YOUR TURN: " + Ansi.RESET +
                        "If your Worker does not move up, it may build both before and after moving.";
            case TRITON:
                return font +
                        "YOUR MOVE: " + Ansi.RESET +
                        "Each time your Worker moves into a perimeter space, it may immediately move again.";
            case ZEUS:
                return font +
                        "YOUR BUILD: " + Ansi.RESET +
                        "Your Worker may build a block under itself.";
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
