package com.notch.utils.shell;

/**
 * Defines base Terminal (VT) control sequences.
 *
 * @author: Domagoj Madunić
 */
public class ConsoleSequences {

    //--- Cursor positioning sequences ----------------------------------------

    /**
     * ESC [ <n> A - Cursor up by n
     */
    public static final String CUU = "\u001B[A";

    /**
     * ESC [ <n> B - Cursor down by n
     */
    public static final String CUD = "\u001B[B";

    /**
     * ESC [ <n> C - Cursor forward (Right) by <n>
     */
    public static final String CUF = "\u001B[C";

    /**
     * ESC [ <n> D - Cursor backward (Left) by <n>
     */
    public static final String CUB = "\u001B[D";

    /**
     * ESC M - Reverse Index – Performs the reverse operation of \n, moves cursor up one line, maintains horizontal position, scrolls buffer if necessary*
     */
    public static final String RI = "\u001BM";

    /**
     * ESC 7 - Save Cursor Position in Memory
     */
    public static final String DECSC = "\u001B7";

    /**
     * ESC 8 - Restore Cursor Position from Memory
     */
    public static final String DECSR = "\u001B8";

    //--- Cursor Visibility ---------------------------------------------------

    /**
     * ESC [ ? 12 h - Start the cursor blinking
     */
    public static final String ATT160_1 = "\u001B[12h";

    /**
     * ESC [ ? 12 l - Stop blinking the cursor
     */
    public static final String ATT160_2 = "\u001B[12l";

    /**
     * ESC [ ? 25 h - Show the cursor
     */
    public static final String DECTCEM_1 = "\u001B[25h";

    /**
     * ESC [ ? 25 l - Hide the cursor
     */
    public static final String DECTCEM_2 = "\u001B[25l";

    // --- Text Modification --------------------------------------------------

    /**
     * ESC [ <n> M - Deletes <n> lines from the buffer, starting with the row the cursor is on.
     */
    public static final String DL = "\u001B[1M";

    /**
     * ESC [ <n> M - Deletes <n> lines from the buffer, starting with the row the cursor is on.
     */
    public static final String DL_N = "\u001B[%d1M";

}
