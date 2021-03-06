/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.mailcontrol.model.data;

import org.creek.mailcontrol.model.data.OnOffData;
import org.creek.mailcontrol.model.types.OnOffDataType;

import org.openhab.core.library.types.OnOffType;

/**
 * 
 * @author Andrey.Pereverzin
 * @since 1.7.0
 */
public class OpenhabOnOffData extends OpenhabData<OnOffDataType, OnOffData> implements OpenhabCommandTransformable<OnOffType> {
    public OpenhabOnOffData(OnOffData data) {
        super(data);
    }

    @Override
    public OnOffType getCommandValue() {
        return OnOffType.valueOf(data.name());
    }
}
