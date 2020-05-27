package it.polimi.ingsw.utils;

import java.util.HashMap;

public final class GodMoves {

    private static final HashMap<GodType, CommandType[]> allPossibleMoves = new HashMap<>();

    public PossibleMove setPlayersPossibleMove(String God) throws IllegalArgumentException {

        return new PossibleMove();

    }

    private void PossibleMove() {

        // Apollo
        allPossibleMoves.put(GodType.APOLLO,
                new CommandType[]{CommandType.MOVE, CommandType.BUILD});

        // Artemis
        allPossibleMoves.put(GodType.ARTEMIS,
                new CommandType[]{CommandType.MOVE, CommandType.MOVE, CommandType.BUILD});

        // Athena
        allPossibleMoves.put(GodType.ATHENA,
                new CommandType[]{CommandType.MOVE, CommandType.BUILD});

        // Atlas
        allPossibleMoves.put(GodType.ATLAS,
                new CommandType[]{CommandType.MOVE, CommandType.BUILD});

        // Charon
        allPossibleMoves.put(GodType.CHARON,
                new CommandType[]{CommandType.MOVE, CommandType.BUILD, CommandType.FORCE});

        // Chronus
        allPossibleMoves.put(GodType.CHRONUS,
                new CommandType[]{CommandType.MOVE, CommandType.BUILD});

        // Demeter
        allPossibleMoves.put(GodType.DEMETER,
                new CommandType[]{CommandType.MOVE, CommandType.BUILD, CommandType.BUILD});

        // Hephaestus
        allPossibleMoves.put(GodType.HEPHAESTUS,
                new CommandType[]{CommandType.MOVE, CommandType.BUILD, CommandType.BUILD});

        // Hestia
        allPossibleMoves.put(GodType.HESTIA,
                new CommandType[]{CommandType.MOVE, CommandType.BUILD, CommandType.BUILD});

        // Minotaur
        allPossibleMoves.put(GodType.MINOTAUR,
                new CommandType[]{CommandType.MOVE, CommandType.BUILD});

        // Pan
        allPossibleMoves.put(GodType.PAN,
                new CommandType[]{CommandType.MOVE, CommandType.BUILD});

        // Prometheus
        allPossibleMoves.put(GodType.PROMETHEUS,
                new CommandType[]{CommandType.BUILD, CommandType.MOVE, CommandType.BUILD});

        // Triton
        allPossibleMoves.put(GodType.TRITON,
                new CommandType[]{CommandType.MOVE, CommandType.BUILD});

        // Zeus
        allPossibleMoves.put(GodType.ZEUS,
                new CommandType[]{CommandType.MOVE, CommandType.BUILD});

    }

    public static CommandType[] getPossibleMove(GodType godType) {
        return allPossibleMoves.get(godType);
    }

}