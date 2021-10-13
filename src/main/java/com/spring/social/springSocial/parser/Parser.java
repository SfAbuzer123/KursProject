package com.spring.social.springSocial.parser;

public class Parser {
    public static String taskParser(int number){
        if (number == 0 || (number > 4 && number < 21) || String.valueOf(number).endsWith("5") || String.valueOf(number).endsWith("6") || String.valueOf(number).endsWith("7") || String.valueOf(number).endsWith("8") || String.valueOf(number).endsWith("9") || String.valueOf(number).endsWith("0")) return "задач";
        if (String.valueOf(number).endsWith("1")) return "задачу";
        if (String.valueOf(number).endsWith("2") || String.valueOf(number).endsWith("3") || String.valueOf(number).endsWith("4")) return "задачи";
        return "задач(у, а)";
    }
}
