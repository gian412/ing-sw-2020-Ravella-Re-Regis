package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.Command;

import java.util.HashMap;

public final class GodMoves {

    private static final HashMap<GodType, CommandType[]> allPossibleMoves = new HashMap<>();

    public static void PossibleMoveInit() {

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

        // Demeterpo
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


    public PossibleMove setPlayersPossibleMove(String God) throws IllegalArgumentException {
        return new PossibleMove();
    }

    public static boolean isTurnEnded(GodType playerGod, Object[] playerMoves){

        CommandType[] neededMoves = allPossibleMoves.get(playerGod);

        // If the worker has not at least moved and build return false
        if (playerMoves.length <=1 )
            return false;

        // If the worker made all the action, check their validity
        if(neededMoves.length == playerMoves.length)
            return analyzeCompleteActions(neededMoves, playerMoves);

        switch (playerGod) {
            case ARTEMIS:
            case CHARON:
            case PROMETHEUS:
                return canPassActionBefore(playerMoves);
            case TRITON:
                return canPassTriton(playerMoves);
            default:
                return false;
        }


    }

    private static boolean analyzeCompleteActions(CommandType[] neededMoves, Object[] playerMoves) {

        for(int i = 0; i < neededMoves.length; i++)
            if(!neededMoves[i].equals(playerMoves[i]))
                return false;

        return true;

    }

    private static boolean canPassActionBefore(Object[] playerMoves){

        return  (playerMoves.length == 2 && playerMoves[0].equals(CommandType.MOVE) && (playerMoves[1].equals(CommandType.BUILD) || playerMoves[1].equals(CommandType.BUILD_DOME)));

    }

    private static boolean canPassTriton(Object[] playerMoves) {

        int len = playerMoves.length;

        return  (len>=2 && playerMoves[0].equals(CommandType.MOVE) && (playerMoves[len-1].equals(CommandType.BUILD) || playerMoves[len-1].equals(CommandType.BUILD_DOME)));

    }


}