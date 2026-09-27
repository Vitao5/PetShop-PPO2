/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.petshop.Util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author vitin
 */
public class ManipulaData {

    public Date string2Date(String data) {
        if (data == null || data.trim().isEmpty()) {
            return null;
        }
        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return Date.valueOf(LocalDate.parse(data.trim(), formato));
        } catch (Exception e) {
            return null;
        }
    }

    public String date2String(String data) {
        if (data == null || data.trim().isEmpty()) {
            return "";
        }
        try {
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(data);
            return new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (ParseException e) {
            return data;
        }
    }
}
