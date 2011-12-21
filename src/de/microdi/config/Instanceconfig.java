package de.microdi.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="instance")
@XmlAccessorType(XmlAccessType.FIELD)
public class Instanceconfig
{
   @XmlAttribute(name="name")
   String name ;
   @XmlAttribute(name="classname")
   String classname ;
   public String getName()
   {
      return name;
   }
   public void setName(String name)
   {
      this.name = name;
   }
   public String getClassname()
   {
      return classname;
   }
   public void setClassname(String classname)
   {
      this.classname = classname;
   }
}
