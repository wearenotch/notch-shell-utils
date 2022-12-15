package com.notch.utils.shell.progress;

import com.notch.utils.shell.Chalk;
import com.notch.utils.shell.ConsoleSequences;
import org.jline.terminal.Terminal;

/**
 * 
 * @author dmadunic
 */
public class ProgressBar {

    private String successColor = "GREEN";

    private String doneMarker = "=";
    private String remainsMarker = "-";
    private String leftDelimiter = "<";
    private String rightDelimiter = ">";

    Terminal terminal;

    private boolean started = false;

    public ProgressBar(Terminal terminal) {
        this.terminal = terminal;
    }

    public void display(int percentage) {
        display(percentage, null);
    }

    public void display(int percentage, String statusMessage) {
        if (!started) {
            started = true;
            terminal.writer().println();
        }
        int x = (percentage/5);
        int y = 20-x;
        String message = ((statusMessage == null) ? "" : statusMessage);

        String done = Chalk.color(new String(new char[x]).replace("\0", doneMarker), successColor);
        String remains = new String(new char[y]).replace("\0", remainsMarker);

        String progressBar = String.format("%s%s%s%s %d", leftDelimiter, done, remains, rightDelimiter, percentage);

        terminal.writer().println(ConsoleSequences.CUU + "\r" + ConsoleSequences.DL + progressBar + "% " + message);
        terminal.flush();
    }

    public void reset() {
        started = false;
    }

    //--- set / get methods ---------------------------------------------------

    public String getDoneMarker() {
        return doneMarker;
    }

    public void setDoneMarker(String doneMarker) {
        this.doneMarker = doneMarker;
    }

    public String getRemainsMarker() {
        return remainsMarker;
    }

    public void setRemainsMarker(String remainsMarker) {
        this.remainsMarker = remainsMarker;
    }

    public String getLeftDelimiter() {
        return leftDelimiter;
    }

    public void setLeftDelimiter(String leftDelimiter) {
        this.leftDelimiter = leftDelimiter;
    }

    public String getRightDelimiter() {
        return rightDelimiter;
    }

    public void setRightDelimiter(String rightDelimiter) {
        this.rightDelimiter = rightDelimiter;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public String getSuccessColor() {
        return successColor;
    }

    public void setSuccessColor(String successColor) {
        this.successColor = successColor;
    }

}
