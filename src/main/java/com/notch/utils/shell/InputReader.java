package com.notch.utils.shell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jline.reader.LineReader;

public class InputReader {
    public static final Character DEFAULT_MASK = '*';
    public static final String DEFAULT_INFO_COLOR = "cyan";

    public String infoColor = "";

    private Character mask;

    private LineReader lineReader;

    private ShellHelper shellHelper;

    public InputReader(LineReader lineReader, ShellHelper shellHelper) {
        this(lineReader, shellHelper, null, null);
    }

    public InputReader(LineReader lineReader, ShellHelper shellHelper, String infoColor) {
        this(lineReader, shellHelper, infoColor, null);
    }

    public InputReader(LineReader lineReader, ShellHelper shellHelper, String infoColor, Character mask) {
        this.lineReader = lineReader;
        this.shellHelper = shellHelper;
        this.infoColor = infoColor != null ? infoColor : DEFAULT_INFO_COLOR;
        this.mask = mask != null ? mask : DEFAULT_MASK;
    }

    public String prompt(String  prompt) {
        return prompt(prompt, null, true);
    }

    public String prompt(String  prompt, String defaultValue) {
        return prompt(prompt, defaultValue, true);
    }

    /**
     * Prompts user for input.
     *
     * @param prompt
     * @param defaultValue
     * @param echo
     * @return
     */
    public String prompt(String  prompt, String defaultValue, boolean echo) {
        String answer = "";

        if (echo) {
            answer = lineReader.readLine(prompt + ": ");
        } else {
            answer = lineReader.readLine(prompt + ": ", mask);
        }
        if (StringUtils.isBlank(answer)) {
            return defaultValue;
        }
        return answer;
    }

    /**
     * Loops until one of the `options` is provided. Pressing return is equivalent to
     * returning `defaultValue`.
     * <br/>
     * Passing null for defaultValue signifies that there is no default value.<br/>
     * Passing "" or null among optionsAsList means that empty answer is allowed, in these cases this method returns
     * empty String "" as the result of its execution.
     *
     *
     */
    public String promptWithOptions(String  prompt, String defaultValue, List<String> optionsAsList) {
        String answer;
        List<String> allowedAnswers = new ArrayList<>(optionsAsList);
        if (StringUtils.isNotBlank(defaultValue)) {
            allowedAnswers.add("");
        }
        do {
            answer = lineReader.readLine(String.format("%s %s: ", prompt, formatOptions(defaultValue, optionsAsList)));
        } while (!allowedAnswers.contains(answer) && !"".equals(answer));

        if (StringUtils.isBlank(answer) && allowedAnswers.contains("")) {
            return defaultValue;
        }
        return answer;
    }

    private List<String> formatOptions(String defaultValue, List<String> optionsAsList) {
        List<String> result = new ArrayList<>();
        for (String option : optionsAsList) {
            String val = option;
            if ("".equals(option) || option == null) {
                val = "''";
            }
            if (defaultValue != null ) {
                if (defaultValue.equals(option) || (defaultValue.equals("") && option == null)) {
                    val = Chalk.color(val, infoColor);
                }
            }
            result.add(val);
        }
        return result;
    }

    /**
     * Loops until one value from the list of options is selected, printing each option on its own line.
     *
     */
    public String selectFromList(String headingMessage, String promptMessage, Map<String, String> options, boolean ignoreCase, String defaultValue) {
        String answer;
        Set<String> allowedAnswers = new HashSet<>(options.keySet());
        if (defaultValue != null && !defaultValue.equals("")) {
            allowedAnswers.add("");
        }
        shellHelper.println(String.format("%s: ", headingMessage));
        do {
            for (Map.Entry<String, String> option: options.entrySet()) {
                String defaultMarker = null;
                if (defaultValue != null) {
                    if (option.getKey().equals(defaultValue)) {
                        defaultMarker = "*";
                    }
                }
                if (defaultMarker != null) {
                    shellHelper.printlnInfo(String.format("%s [%s] %s ", defaultMarker, option.getKey(), option.getValue()));
                } else {
                    shellHelper.println(String.format("  [%s] %s", option.getKey(), option.getValue()));
                }
            }
            answer = lineReader.readLine(String.format("%s: ", promptMessage));
        } while (!containsString(allowedAnswers, answer, ignoreCase) && "" != answer);

        if (StringUtils.isBlank(answer) && allowedAnswers.contains("")) {
            return defaultValue;
        }
        return answer;
    }

    private boolean containsString(Set <String> l, String s, boolean ignoreCase){
        if (!ignoreCase) {
            return l.contains(s);
        }
        Iterator<String> it = l.iterator();
        while(it.hasNext()) {
            if(it.next().equalsIgnoreCase(s))
                return true;
        }
        return false;
    }
}