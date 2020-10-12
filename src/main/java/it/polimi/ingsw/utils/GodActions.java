package it.polimi.ingsw.utils;

import java.util.EnumMap;

/**
 * this class is used Client-side to perform a check on the possible moves a
 * player can do in is turn, and to verify if he must end this turn
 *
 * @author Gianluca Regis, Elia Ravella
 */
public final class GodActions {

    private GodActions() {
    }

    private static final EnumMap<GodType, CommandType[]> allPossibleMoves = new EnumMap<>(GodType.class);

    /**
     * Initialize the private EnumMap with all possible actions for each God
     *
     * @author Gianluca Regis
     */
    public static void possibleActionsInit() {

        // Apollo
        allPossibleMoves.put(GodType.APOLLO, new CommandType[] { CommandType.MOVE, CommandType.BUILD });

        // Artemis
        allPossibleMoves.put(GodType.ARTEMIS,
                new CommandType[] { CommandType.MOVE, CommandType.MOVE, CommandType.BUILD });

        // Athena
        allPossibleMoves.put(GodType.ATHENA, new CommandType[] { CommandType.MOVE, CommandType.BUILD });

        // Atlas
        allPossibleMoves.put(GodType.ATLAS, new CommandType[] { CommandType.MOVE, CommandType.BUILD });

        // Charon
        allPossibleMoves.put(GodType.CHARON,
                new CommandType[] { CommandType.FORCE, CommandType.MOVE, CommandType.BUILD });

        // Chronus
        allPossibleMoves.put(GodType.CHRONUS, new CommandType[] { CommandType.MOVE, CommandType.BUILD });

        // Demeter
        allPossibleMoves.put(GodType.DEMETER,
                new CommandType[] { CommandType.MOVE, CommandType.BUILD, CommandType.BUILD });

        // Hephaestus
        allPossibleMoves.put(GodType.HEPHAESTUS,
                new CommandType[] { CommandType.MOVE, CommandType.BUILD, CommandType.BUILD });

        // Hestia
        allPossibleMoves.put(GodType.HESTIA,
                new CommandType[] { CommandType.MOVE, CommandType.BUILD, CommandType.BUILD });

        // Minotaur
        allPossibleMoves.put(GodType.MINOTAUR, new CommandType[] { CommandType.MOVE, CommandType.BUILD });

        // Pan
        allPossibleMoves.put(GodType.PAN, new CommandType[] { CommandType.MOVE, CommandType.BUILD });

        // Prometheus
        allPossibleMoves.put(GodType.PROMETHEUS,
                new CommandType[] { CommandType.BUILD, CommandType.MOVE, CommandType.BUILD });

        // Triton
        allPossibleMoves.put(GodType.TRITON, new CommandType[] { CommandType.MOVE, CommandType.BUILD });

        // Zeus
        allPossibleMoves.put(GodType.ZEUS, new CommandType[] { CommandType.MOVE, CommandType.BUILD });

    }

    /**
     * this function performs a control on a set of moves given and the one
     * available for that god
     *
     * @param playerGod   the player's god
     * @param playerMoves the player's moves so far
     * @return true if the player has no more moves to do. false otherwise
     * @author Gianluca Regis, Ravella Elia
     */
    public static boolean isTurnEnded(GodType playerGod, Object[] playerMoves) {

        CommandType[] neededMoves = allPossibleMoves.get(playerGod);

        // If the worker has not at least moved and build return false
        if (playerMoves.length <= 1)
            return false;

        // If the worker made all the action, check their validity
        if (neededMoves.length == playerMoves.length)
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

        for (int i = 0; i < neededMoves.length; i++)
            if (!neededMoves[i].equals(playerMoves[i]))
                return false;

        return true;

    }

    private static boolean canPassActionBefore(Object[] playerMoves) {

        return (playerMoves.length == 2 && playerMoves[0].equals(CommandType.MOVE)
                && (playerMoves[1].equals(CommandType.BUILD) || playerMoves[1].equals(CommandType.BUILD_DOME)));

    }

    private static boolean canPassTriton(Object[] playerMoves) {

        int len = playerMoves.length;

        return (len >= 2 && playerMoves[0].equals(CommandType.MOVE) && (playerMoves[len - 1].equals(CommandType.BUILD)
                || playerMoves[len - 1].equals(CommandType.BUILD_DOME)));

    }

}