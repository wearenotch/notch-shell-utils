package com.notch.utils.shell.progress;

import com.notch.utils.shell.ConsoleSequences;
import org.jline.terminal.Terminal;

/**
 * Console based implementation of progress counter.
 * Provides three public methods:<br/>
 * <ul>
 * <li>1. Display just spinner</li>
 * <li>2. Display spinner in combination with message and numeric counter.</li>
 * <li>3. Display spinner in combination with custom pattern message that contains '%c' placeholder for spinner display</li>
 * </ul>
 *
 * @author: Domagoj Madunić
 */
public class ProgressCounter {

    private Terminal terminal;

    private char[] spinner = {'|', '/', '-', '\\'};

    private String pattern = " %s: %d ";

    private boolean started = false;
    private int spinCounter = 0;

    public ProgressCounter(Terminal terminal) {
        this(terminal, null);
    }

    public ProgressCounter(Terminal terminal, String pattern) {
        this(terminal, pattern, null);
    }

    public ProgressCounter(Terminal terminal, String pattern, char[] spinner) {
        this.terminal = terminal;

        if (pattern != null) {
            this.pattern = pattern;
        }
        if (spinner != null) {
            this.spinner = spinner;
        }
    }

    public void display(int count, String message) {
        if (!started) {
            terminal.writer().println();
            started = true;
        }
        String progress = String.format(pattern, message, count);

        terminal.writer().println(ConsoleSequences.CUU + "\r" + ConsoleSequences.DL + getSpinnerChar() + progress);
        terminal.flush();
    }

    public void display(String messagePattern) {
        if (!started) {
            terminal.writer().println();
            started = true;
        }
        String progress = String.format(messagePattern, getSpinnerChar());

        terminal.writer().println(ConsoleSequences.CUU + "\r" + ConsoleSequences.DL + progress);
        terminal.flush();
    }

    public void display() {
        if (!started) {
            terminal.writer().println();
            started = true;
        }
        terminal.writer().println(ConsoleSequences.CUU + "\r" + ConsoleSequences.DL + getSpinnerChar());
        terminal.flush();
    }

    public void reset() {
        spinCounter = 0;
        started = false;
    }

    private char getSpinnerChar() {
        char spinChar = spinner[spinCounter];
        spinCounter++;
        if (spinCounter == spinner.length) {
            spinCounter = 0;
        }
        return spinChar;
    }

    //--- set / get methods ---------------------------------------------------

    public char[] getSpinner() {
        return spinner;
    }

    public void setSpinner(char[] spinner) {
        this.spinner = spinner;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public int getSpinCounter() {
        return spinCounter;
    }

    public void setSpinCounter(int spinCounter) {
        this.spinCounter = spinCounter;
    }
}
