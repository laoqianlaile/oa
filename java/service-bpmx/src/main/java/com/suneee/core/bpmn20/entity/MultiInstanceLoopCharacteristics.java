//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.13 at 11:13:53 ���� CST 
//


package com.suneee.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for tMultiInstanceLoopCharacteristics complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tMultiInstanceLoopCharacteristics">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.omg.org/spec/BPMN/20100524/MODEL}tLoopCharacteristics">
 *       &lt;sequence>
 *         &lt;element name="loopCardinality" type="{http://www.omg.org/spec/BPMN/20100524/MODEL}tExpression" minOccurs="0"/>
 *         &lt;element name="loopDataInputRef" type="{http://www.w3.org/2001/XMLSchema}QName" minOccurs="0"/>
 *         &lt;element name="loopDataOutputRef" type="{http://www.w3.org/2001/XMLSchema}QName" minOccurs="0"/>
 *         &lt;element name="inputDataItem" type="{http://www.omg.org/spec/BPMN/20100524/MODEL}tDataInput" minOccurs="0"/>
 *         &lt;element name="outputDataItem" type="{http://www.omg.org/spec/BPMN/20100524/MODEL}tDataOutput" minOccurs="0"/>
 *         &lt;element ref="{http://www.omg.org/spec/BPMN/20100524/MODEL}complexBehaviorDefinition" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="completionCondition" type="{http://www.omg.org/spec/BPMN/20100524/MODEL}tExpression" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="isSequential" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="behavior" type="{http://www.omg.org/spec/BPMN/20100524/MODEL}tMultiInstanceFlowCondition" default="All" />
 *       &lt;attribute name="oneBehaviorEventRef" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="noneBehaviorEventRef" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tMultiInstanceLoopCharacteristics", propOrder = {
    "loopCardinality",
    "loopDataInputRef",
    "loopDataOutputRef",
    "inputDataItem",
    "outputDataItem",
    "complexBehaviorDefinition",
    "completionCondition"
})
public class MultiInstanceLoopCharacteristics
    extends LoopCharacteristics
{

    protected Expression loopCardinality;
    protected QName loopDataInputRef;
    protected QName loopDataOutputRef;
    protected DataInput inputDataItem;
    protected DataOutput outputDataItem;
    protected List<ComplexBehaviorDefinition> complexBehaviorDefinition;
    protected Expression completionCondition;
    @XmlAttribute
    protected Boolean isSequential;
    @XmlAttribute
    protected MultiInstanceFlowCondition behavior;
    @XmlAttribute
    protected QName oneBehaviorEventRef;
    @XmlAttribute
    protected QName noneBehaviorEventRef;

    /**
     * Gets the value of the loopCardinality property.
     * 
     * @return
     *     possible object is
     *     {@link Expression }
     *     
     */
    public Expression getLoopCardinality() {
        return loopCardinality;
    }

    /**
     * Sets the value of the loopCardinality property.
     * 
     * @param value
     *     allowed object is
     *     {@link Expression }
     *     
     */
    public void setLoopCardinality(Expression value) {
        this.loopCardinality = value;
    }

    /**
     * Gets the value of the loopDataInputRef property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getLoopDataInputRef() {
        return loopDataInputRef;
    }

    /**
     * Sets the value of the loopDataInputRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setLoopDataInputRef(QName value) {
        this.loopDataInputRef = value;
    }

    /**
     * Gets the value of the loopDataOutputRef property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getLoopDataOutputRef() {
        return loopDataOutputRef;
    }

    /**
     * Sets the value of the loopDataOutputRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setLoopDataOutputRef(QName value) {
        this.loopDataOutputRef = value;
    }

    /**
     * Gets the value of the inputDataItem property.
     * 
     * @return
     *     possible object is
     *     {@link DataInput }
     *     
     */
    public DataInput getInputDataItem() {
        return inputDataItem;
    }

    /**
     * Sets the value of the inputDataItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataInput }
     *     
     */
    public void setInputDataItem(DataInput value) {
        this.inputDataItem = value;
    }

    /**
     * Gets the value of the outputDataItem property.
     * 
     * @return
     *     possible object is
     *     {@link DataOutput }
     *     
     */
    public DataOutput getOutputDataItem() {
        return outputDataItem;
    }

    /**
     * Sets the value of the outputDataItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataOutput }
     *     
     */
    public void setOutputDataItem(DataOutput value) {
        this.outputDataItem = value;
    }

    /**
     * Gets the value of the complexBehaviorDefinition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the complexBehaviorDefinition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComplexBehaviorDefinition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplexBehaviorDefinition }
     * 
     * 
     */
    public List<ComplexBehaviorDefinition> getComplexBehaviorDefinition() {
        if (complexBehaviorDefinition == null) {
            complexBehaviorDefinition = new ArrayList<ComplexBehaviorDefinition>();
        }
        return this.complexBehaviorDefinition;
    }

    /**
     * Gets the value of the completionCondition property.
     * 
     * @return
     *     possible object is
     *     {@link Expression }
     *     
     */
    public Expression getCompletionCondition() {
        return completionCondition;
    }

    /**
     * Sets the value of the completionCondition property.
     * 
     * @param value
     *     allowed object is
     *     {@link Expression }
     *     
     */
    public void setCompletionCondition(Expression value) {
        this.completionCondition = value;
    }

    /**
     * Gets the value of the isSequential property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isIsSequential() {
        if (isSequential == null) {
            return false;
        } else {
            return isSequential;
        }
    }

    /**
     * Sets the value of the isSequential property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSequential(Boolean value) {
        this.isSequential = value;
    }

    /**
     * Gets the value of the behavior property.
     * 
     * @return
     *     possible object is
     *     {@link MultiInstanceFlowCondition }
     *     
     */
    public MultiInstanceFlowCondition getBehavior() {
        if (behavior == null) {
            return MultiInstanceFlowCondition.ALL;
        } else {
            return behavior;
        }
    }

    /**
     * Sets the value of the behavior property.
     * 
     * @param value
     *     allowed object is
     *     {@link MultiInstanceFlowCondition }
     *     
     */
    public void setBehavior(MultiInstanceFlowCondition value) {
        this.behavior = value;
    }

    /**
     * Gets the value of the oneBehaviorEventRef property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getOneBehaviorEventRef() {
        return oneBehaviorEventRef;
    }

    /**
     * Sets the value of the oneBehaviorEventRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setOneBehaviorEventRef(QName value) {
        this.oneBehaviorEventRef = value;
    }

    /**
     * Gets the value of the noneBehaviorEventRef property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getNoneBehaviorEventRef() {
        return noneBehaviorEventRef;
    }

    /**
     * Sets the value of the noneBehaviorEventRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setNoneBehaviorEventRef(QName value) {
        this.noneBehaviorEventRef = value;
    }

}