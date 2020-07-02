package it.polimi.ingsw.utils;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static it.polimi.ingsw.utils.GodActions.isTurnEnded;
import static it.polimi.ingsw.utils.GodActions.possibleActionsInit;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GodActionsTest {

    // Apollo
    @Test
    @DisplayName("apolloNotEndAfterMove")
    public void allGodsNotEndAfterMove() {

        possibleActionsInit();
        CommandType[] action = new CommandType[] {CommandType.MOVE};

        boolean result = false;

        for (GodType god : GodType.values()) {
            result = result || isTurnEnded(god, action);
        }

        assertFalse("The result must be false", result);

    }

    @Test
    @DisplayName("allGodsEndAfterMoveAndBuildExceptDemeterHephaestusHestia")
    public void allGodsEndAfterMoveAndBuildExceptDemeterHephaestusHestia() {

        possibleActionsInit();
        CommandType[] actions = new CommandType[] {CommandType.MOVE, CommandType.BUILD};

        boolean result = false;

        for (GodType god : GodType.values()) {
            if (!god.equals(GodType.DEMETER) && !god.equals(GodType.HEPHAESTUS) && !god.equals(GodType.HESTIA)) {
                result = result || !isTurnEnded(god, actions);
            }
        }

        assertFalse("The result must be false", result);

    }

    @Test
    @DisplayName("DemeterHephaestusHestiaNotEndAfterMoveBuild")
    public void DemeterHephaestusHestiaNotEndAfterMoveBuild() {

        possibleActionsInit();
        GodType[] gods = new GodType[] {GodType.DEMETER, GodType.HEPHAESTUS, GodType.HESTIA};
        CommandType[] actions = new CommandType[] {CommandType.MOVE, CommandType.BUILD};

        boolean result = false;

        for (GodType god : gods) {
            result = result || isTurnEnded(god, actions);
        }

        assertFalse("The result must be false", result);

    }

    @Test
    @DisplayName("allGodsEndAfterMoveAndBuildExceptDemeterHephaestusHestia")
    public void DemeterHephaestusHestiaEndAfterMoveBuildBuild() {

        possibleActionsInit();
        GodType[] gods = new GodType[] {GodType.DEMETER, GodType.HEPHAESTUS, GodType.HESTIA};
        CommandType[] actions = new CommandType[] {CommandType.MOVE, CommandType.BUILD, CommandType.BUILD};

        boolean result = false;

        for (GodType god : gods) {
            result = result || !isTurnEnded(god, actions);
        }

        assertFalse("The result must be false", result);

    }

    @Test
    @DisplayName("ArtemisEndAfterMoveMoveBuild")
    public void ArtemisEndAfterMoveMoveBuild() {

        possibleActionsInit();
        CommandType[] actions = new CommandType[] {CommandType.MOVE, CommandType.MOVE, CommandType.BUILD};

        boolean result = isTurnEnded(GodType.ARTEMIS, actions);;

        assertTrue("The result must be true", result);

    }

    @Test
    @DisplayName("CharonEndAfterForceMoveBuild")
    public void CharonEndAfterForceMoveBuild() {

        possibleActionsInit();
        CommandType[] actions = new CommandType[] {CommandType.FORCE, CommandType.MOVE, CommandType.BUILD};

        boolean result = isTurnEnded(GodType.CHARON, actions);;

        assertTrue("The result must be true", result);

    }

    @Test
    @DisplayName("PrometheusEndAfterBuildMoveBuild")
    public void PrometheusEndAfterBuildMoveBuild() {

        possibleActionsInit();
        CommandType[] actions = new CommandType[] {CommandType.BUILD, CommandType.MOVE, CommandType.BUILD};

        boolean result = isTurnEnded(GodType.PROMETHEUS, actions);;

        assertTrue("The result must be true", result);

    }

    @Test
    @DisplayName("TritonEndAfterSomeMoveBuild")
    public void TritonEndAfterSomeMoveBuild() {

        possibleActionsInit();

        CommandType[] actions = new CommandType[] {CommandType.MOVE, CommandType.MOVE, CommandType.MOVE, CommandType.BUILD};

        boolean result = isTurnEnded(GodType.TRITON, actions);;

        assertTrue("The result must be true", result);

    }

    @Test
    @DisplayName("TritonNotEndAfterMoveMove")
    public void TritonNotEndAfterMoveMove() {

        possibleActionsInit();

        CommandType[] actions = new CommandType[] {CommandType.MOVE, CommandType.MOVE};

        boolean result = isTurnEnded(GodType.TRITON, actions);;

        assertFalse("The result must be false", result);

    }

}
