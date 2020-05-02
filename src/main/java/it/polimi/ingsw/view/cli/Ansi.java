package it.polimi.ingsw.view.cli;

public class Ansi {

    //constant which represents the code of the possible predefined colors

    //code to color Strings
    public static final String	BLACK_      = "30";
    public static final String	RED		    = "31";
    public static final String	GREEN	    = "32";
    public static final String	YELLOW	    = "33";
    public static final String	BLUE	    = "34";
    public static final String	MAGENTA	    = "35";
    public static final String	CYAN	    = "36";
    public static final String	WHITE	    = "37";

    //code to color Backgrounds
    public static final String	BACKGROUND_BLACK	= "40";
    public static final String	BACKGROUND_RED		= "41";
    public static final String	BACKGROUND_GREEN	= "42";
    public static final String	BACKGROUND_YELLOW	= "43";
    public static final String	BACKGROUND_BLUE		= "44";
    public static final String	BACKGROUND_MAGENTA	= "45";
    public static final String	BACKGROUND_CYAN		= "46";
    public static final String	BACKGROUND_WHITE	= "47";

    //code to color strings with bright colors
    public static final String	BLACK_B     = "90";
    public static final String	RED_B	    = "91";
    public static final String	GREEN_B	    = "92";
    public static final String	YELLOW_B    = "93";
    public static final String	BLUE_B	    = "94";
    public static final String	MAGENTA_B   = "95";
    public static final String	CYAN_B	    = "96";
    public static final String	WHITE_B	    = "97";

    //code to color backgrounds with bright colors
    public static final String	BACKGROUND_BLACK_B	    = "100";
    public static final String	BACKGROUND_RED_B		= "101";
    public static final String	BACKGROUND_GREEN_B	    = "102";
    public static final String	BACKGROUND_YELLOW_B	    = "103";
    public static final String	BACKGROUND_BLUE_B		= "104";
    public static final String	BACKGROUND_MAGENTA_B	= "105";
    public static final String	BACKGROUND_CYAN_B		= "106";
    public static final String	BACKGROUND_WHITE_B	    = "107";

    //utility constant
    public static final String RESET    = "\u001B[0m";

    /**
     * constructor of the class Ansi
     *
     * @authors Marco Re
     */
    public Ansi (){
    }

    /**
     * permit to create the code to change the aspect of the output
     *
     * created an Ansi code to change the color of background and font
     *
     * @authors Marco Re
     *
     * @param bg is the code of the color for the background
     * @param font is the code of the color for the font
     * @return the instruction to change output's aspect ( background and font)
     */
    public String bgAndFont(String bg, String font){
        return "\u001B[" + font + ";" + bg + "m";
    }

    /**
     * permit to create the code to change the aspect of the output
     *
     * created an Ansi code to change the color of the font
     *
     * @authors Marco Re
     *
     * @param font is the code of the color for the font
     * @return the instruction to change output's aspect (font)
     */
    public String Font(String font){
        return "\u001B[" + font + "m";
    }

    /**
     * permit to create the code to change the aspect of the output
     *
     * created an Ansi code to change the color of the background
     *
     * @authors Marco Re
     *
     * @param bg is the code of the color for the background
     * @return the instruction to change output's aspect (Background)
     */
    public String bg(String bg){
        return "\u001B[" + bg + "m";
    }

}
