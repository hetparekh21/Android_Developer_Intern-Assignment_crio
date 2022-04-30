package com.example.androiddeveloperintern_assignmentcrio.data;

import androidx.room.TypeConverter;

public class converters {

    @TypeConverter
    public String arrayToString(String array[]) {

        StringBuilder stringBuilder = new StringBuilder();

        if(array == null){

            return null;

        }

        for (int i = 0; i < array.length; i++) {

            stringBuilder.append(" " + array[i]);

        }

        return stringBuilder.toString();

    }

    @TypeConverter
    public String[] stringToArray(String image_string) {

        if(image_string == null || image_string.isEmpty()){

            return null ;

        }

        String result[] = image_string.split(" ");

        return result;

    }

}
