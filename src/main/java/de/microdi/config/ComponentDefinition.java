package de.microdi.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="component")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComponentDefinition
{
   @XmlAttribute(name="classname")
   String classname ;
   
   @XmlAttribute(name="name")
   String name ;

   public String getClassname()
   {
      return classname;
   }
   
   public void setClassname(String classname)
   {
      this.classname = classname;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }
}
