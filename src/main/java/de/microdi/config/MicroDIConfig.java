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

   @XmlElement(name="component")
   List<ComponentDefinition> componentDefinitions ;

   public List<Instanceconfig> getInstances()
   {
      return instances;
   }

   public void setInstances(List<Instanceconfig> instances)
   {
      this.instances = instances;
   }

   public List<ComponentDefinition> getComponentDefinitions()
   {
      return componentDefinitions;
   }

   public void setComponentDefinitions(List<ComponentDefinition> definedComponents)
   {
      this.componentDefinitions = definedComponents;
   }
   
}
