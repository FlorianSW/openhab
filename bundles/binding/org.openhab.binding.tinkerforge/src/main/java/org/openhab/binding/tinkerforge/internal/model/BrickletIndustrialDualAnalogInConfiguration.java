/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 */
package org.openhab.binding.tinkerforge.internal.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bricklet Industrial Dual Analog In Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.openhab.binding.tinkerforge.internal.model.BrickletIndustrialDualAnalogInConfiguration#getSampleRate <em>Sample Rate</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.openhab.binding.tinkerforge.internal.model.ModelPackage#getBrickletIndustrialDualAnalogInConfiguration()
 * @model
 * @generated
 */
public interface BrickletIndustrialDualAnalogInConfiguration extends TFBaseConfiguration
{
  /**
   * Returns the value of the '<em><b>Sample Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Sample Rate</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Sample Rate</em>' attribute.
   * @see #setSampleRate(Short)
   * @see org.openhab.binding.tinkerforge.internal.model.ModelPackage#getBrickletIndustrialDualAnalogInConfiguration_SampleRate()
   * @model unique="false"
   * @generated
   */
  Short getSampleRate();

  /**
   * Sets the value of the '{@link org.openhab.binding.tinkerforge.internal.model.BrickletIndustrialDualAnalogInConfiguration#getSampleRate <em>Sample Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Sample Rate</em>' attribute.
   * @see #getSampleRate()
   * @generated
   */
  void setSampleRate(Short value);

} // BrickletIndustrialDualAnalogInConfiguration
