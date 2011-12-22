package de.microdi.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="parsed-component")
@XmlAccessorType(XmlAccessType.FIELD)
public class ParsedComponent
{
   @XmlAttribute(name="classname")
   String classname ;

   public String getClassname()
   {
      return classname;
   }
   
   public void setClassname(String classname)
   {
      this.classname = classname;
   }
}
