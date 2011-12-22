package de.microdi.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="micro-di")
@XmlAccessorType(XmlAccessType.FIELD)
public class MicroDIConfig
{
   @XmlElement(name="instance")
   List<Instanceconfig> instances ;

   @XmlElement(name="parsed-component")
   List<ParsedComponent> parsedComponents ;

   public List<Instanceconfig> getInstances()
   {
      return instances;
   }

   public void setInstances(List<Instanceconfig> instances)
   {
      this.instances = instances;
   }

   public List<ParsedComponent> getParsedComponents()
   {
      return parsedComponents;
   }

   public void setParsedComponents(List<ParsedComponent> parsedComponents)
   {
      this.parsedComponents = parsedComponents;
   }
   
}
