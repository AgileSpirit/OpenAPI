package com.agile.spirit.openapi.utils.hibernate;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class XmlDateTimeAdapter extends XmlAdapter<String, DateTime> {
    private final DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy hh:mm:ss");

    public DateTime unmarshal(String v) throws Exception {
        return formatter.parseDateTime(v);
    }

    public String marshal(DateTime v) throws Exception {
        return formatter.print(v);
    }
}
