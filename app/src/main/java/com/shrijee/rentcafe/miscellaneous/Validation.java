package com.shrijee.rentcafe.miscellaneous;

import java.util.List;

public class Validation {

    public boolean stringValidation(String input) {
        if(input.trim().isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean intValidation(String input) {
        if(stringValidation(input)) {
            try
            {
                Integer.parseInt(input);
                return true;
            }
            catch (NumberFormatException e)
            {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean stringArrayValidation(String[] inputArray) {
        for(String input: inputArray) {
            if(!stringValidation(input)) {
                return false;
            }
        }
        return true;
    }

    public boolean intArrayValidation(String[] inputArray) {
        for(String input: inputArray) {
            if(!intValidation(input)) {
                return false;
            }
        }
        return true;
    }
}