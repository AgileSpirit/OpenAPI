@XmlJavaTypeAdapters({ @XmlJavaTypeAdapter(value = XmlDateTimeAdapter.class, type = DateTime.class) })
package com.agile.spirit.openapi.domain;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

import org.joda.time.DateTime;

import com.agile.spirit.openapi.utils.hibernate.XmlDateTimeAdapter;

