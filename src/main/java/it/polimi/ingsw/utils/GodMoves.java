package it.polimi.ingsw.utils;

import java.util.HashMap;

public final class GodMoves {

    private static final HashMap<GodType, CommandType[]> allPossibleMoves = new HashMap<>();

    public PossibleMove setPlayersPossibleMove(String God) throws IllegalArgumentException {

        return new PossibleMove();

    }

    private static void init() {

        // Apollo
        allPossibleMoves.put(GodType.APOLLO, new CommandType[]{CommandType.MOVE, CommandType.BUILD});

        // Artemis
        allPossibleMoves.put(GodType.ARTEMIS, new CommandType[]{CommandType.MOVE, CommandType.MOVE, CommandType.BUILD});

        // Athena
        allPossibleMoves.put(GodType.ATHENA, new CommandType[]{CommandType.MOVE, CommandType.BUILD});

        // Atlas
        allPossibleMoves.put(GodType.ATLAS, new CommandType[]{CommandType.MOVE, CommandType.BUILD});

        // Charon
        allPossibleMoves.put(GodType.CHARON, new CommandType[]{CommandType.MOVE, CommandType.BUILD, CommandType.FORCE});

        // Chronus



    }

}
