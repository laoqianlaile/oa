//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.13 at 10:40:06 ���� CST 
//


package com.suneee.core.bpmn20.entity.activiti;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.suneee.core.bpmn.entity.activiti package.
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PotentialStarter_QNAME = new QName("http://activiti.org/bpmn", "potentialStarter");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.suneee.core.bpmn.entity.activiti
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Out }
     * 
     */
    public Out createOut() {
        return new Out();
    }

    /**
     * Create an instance of {@link Field }
     * 
     */
    public Field createField() {
        return new Field();
    }

    /**
     * Create an instance of {@link FormProperty.Value }
     * 
     */
    public FormProperty.Value createFormPropertyValue() {
        return new FormProperty.Value();
    }

    /**
     * Create an instance of {@link ExecutionListener }
     * 
     */
    public ExecutionListener createExecutionListener() {
        return new ExecutionListener();
    }

    /**
     * Create an instance of {@link FormProperty }
     * 
     */
    public FormProperty createFormProperty() {
        return new FormProperty();
    }

    /**
     * Create an instance of {@link PotentialStarter }
     * 
     */
    public PotentialStarter createPotentialStarter() {
        return new PotentialStarter();
    }

    /**
     * Create an instance of {@link In }
     * 
     */
    public In createIn() {
        return new In();
    }

    /**
     * Create an instance of {@link TaskListener }
     * 
     */
    public TaskListener createTaskListener() {
        return new TaskListener();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activiti.org/bpmn", name = "potentialStarter")
    public JAXBElement<String> createPotentialStarter(String value) {
        return new JAXBElement<String>(_PotentialStarter_QNAME, String.class, null, value);
    }

}
