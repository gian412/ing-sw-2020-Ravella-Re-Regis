package it.polimi.ingsw.model;

public enum Height {
    GROUND, FIRST_FLOOR, SECOND_FLOOR, THIRD_FLOOR, DOME;

    /**
     * calculate the difference of height between two cells
     *
     * @author Gianluca Regis
     * @param that the cell to compare
     * @return the difference between the two cells
     */
    // method that return the difference of height between two heights
    public byte getDifference( Height that){

        switch (this){
            case GROUND:
                switch (that) {
                    case GROUND:
                        return 0;
                    case FIRST_FLOOR:
                        return 1;
                    case SECOND_FLOOR:
                        return 2;
                    case THIRD_FLOOR:
                        return 3;
                    case DOME:
                        return 4;
                    default:
                        return 100;
                }
            case FIRST_FLOOR:
                switch (that) {
                    case GROUND:
                        return -1;
                    case FIRST_FLOOR:
                        return 0;
                    case SECOND_FLOOR:
                        return 1;
                    case THIRD_FLOOR:
                        return 2;
                    case DOME:
                        return 3;
                    default:
                        return 100;
                }
            case SECOND_FLOOR:
                switch (that) {
                    case GROUND:
                        return -2;
                    case FIRST_FLOOR:
                        return -1;
                    case SECOND_FLOOR:
                        return 0;
                    case THIRD_FLOOR:
                        return 1;
                    case DOME:
                        return 2;
                    default:
                        return 100;
                }
            case THIRD_FLOOR:
                switch (that) {
                    case GROUND:
                        return -3;
                    case FIRST_FLOOR:
                        return -2;
                    case SECOND_FLOOR:
                        return -1;
                    case THIRD_FLOOR:
                        return 0;
                    case DOME:
                        return 1;
                    default:
                        return 100;
                }
            case DOME:
                switch (that) {
                    case GROUND:
                        return -4;
                    case FIRST_FLOOR:
                        return -3;
                    case SECOND_FLOOR:
                        return -2;
                    case THIRD_FLOOR:
                        return -1;
                    case DOME:
                        return 0;
                    default:
                        return 100;
                }
            default:
                return 100;
        }

    }
}
