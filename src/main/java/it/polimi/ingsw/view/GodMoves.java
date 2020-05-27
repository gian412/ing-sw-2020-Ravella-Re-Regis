package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.CommandType;

import java.util.HashMap;

public final class GodMoves {

    private static final HashMap<String, CommandType[]> allPossibleMoves = new HashMap<>();

    public PossibleMove setPlayersPossibleMove(String God) throws IllegalArgumentException {

        return new PossibleMove();

    }

    private static void init() {

        // Apollo
        allPossibleMoves.put("APOLLO", new CommandType[]{CommandType.MOVE, CommandType.BUILD});

        // Artemis
        allPossibleMoves.put("ARTEMIS", new CommandType[]{CommandType.MOVE, CommandType.MOVE, CommandType.BUILD});

        // Athena
        allPossibleMoves.put("ATHENA", new CommandType[]{CommandType.MOVE, CommandType.BUILD});

        // Atlas
        allPossibleMoves.put("ATLAS", new CommandType[]{CommandType.MOVE, CommandType.BUILD});

        // Charon
        allPossibleMoves.put("CHARON", new CommandType[]{CommandType.MOVE, CommandType.BUILD, CommandType.FORCE});

        // Chronus



    }

}
