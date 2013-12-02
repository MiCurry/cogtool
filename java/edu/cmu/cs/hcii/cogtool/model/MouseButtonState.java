/*******************************************************************************
 * CogTool Copyright Notice and Distribution Terms
 * CogTool 1.2, Copyright (c) 2005-2012 Carnegie Mellon University
 * This software is distributed under the terms of the FSF Lesser
 * Gnu Public License (see LGPL.txt). 
 * 
 * CogTool is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 * 
 * CogTool is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with CogTool; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * CogTool makes use of several third-party components, with the 
 * following notices:
 * 
 * Eclipse SWT
 * Eclipse GEF Draw2D
 * 
 * Unless otherwise indicated, all Content made available by the Eclipse 
 * Foundation is provided to you under the terms and conditions of the Eclipse 
 * Public License Version 1.0 ("EPL"). A copy of the EPL is provided with this 
 * Content and is also available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * CLISP
 * 
 * Copyright (c) Sam Steingold, Bruno Haible 2001-2006
 * This software is distributed under the terms of the FSF Gnu Public License.
 * See COPYRIGHT file in clisp installation folder for more information.
 * 
 * ACT-R 6.0
 * 
 * Copyright (c) 1998-2007 Dan Bothell, Mike Byrne, Christian Lebiere & 
 *                         John R Anderson. 
 * This software is distributed under the terms of the FSF Lesser
 * Gnu Public License (see LGPL.txt).
 * 
 * Apache Jakarta Commons-Lang 2.1
 * 
 * This product contains software developed by the Apache Software Foundation
 * (http://www.apache.org/)
 * 
 * Mozilla XULRunner 1.9.0.5
 * 
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/.
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * 
 * The J2SE(TM) Java Runtime Environment
 * 
 * Copyright 2009 Sun Microsystems, Inc., 4150
 * Network Circle, Santa Clara, California 95054, U.S.A.  All
 * rights reserved. U.S.  
 * See the LICENSE file in the jre folder for more information.
 ******************************************************************************/

package edu.cmu.cs.hcii.cogtool.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.cmu.cs.hcii.cogtool.util.EnumeratedInt;
import edu.cmu.cs.hcii.cogtool.util.L10N;
import edu.cmu.cs.hcii.cogtool.util.ObjectLoader;
import edu.cmu.cs.hcii.cogtool.util.ObjectSaver;

/**
 *
 * This class is simply for organizing GUI options for Shapes.
 * Each shape contains a corresponding MouseButtonState ID.
 * the UI Model for FrameEditor takes a SHAPEID and then uses that to decide
 * how to transition the shape of the current widget to the specified one.
 *
 * Factories for performing these transitions should be housed
 * in the individual Shapes.
 *
 * @author alexeiser
 *
 */
public class MouseButtonState extends EnumeratedInt
{
    public static final int edu_cmu_cs_hcii_cogtool_model_MouseButtonState_version = 0;

    private static ObjectSaver.IDataSaver<MouseButtonState> SAVER =
        new ObjectSaver.ADataSaver<MouseButtonState>() {
            @Override
            public int getVersion()
            {
                return edu_cmu_cs_hcii_cogtool_model_MouseButtonState_version;
            }

            @Override
            public boolean isEnum()
            {
                return true;
            }
        };

    public static void registerSaver()
    {
        ObjectSaver.registerSaver(MouseButtonState.class.getName(), SAVER);
    }

    private static ObjectLoader.IEnumLoader LOADER =
        new ObjectLoader.AEnumLoader() {
            @Override
            public Object createEnum(String persistentValue)
            {
                switch (Integer.parseInt(persistentValue)) {
                    case 0: return MouseButtonState.Left;
                    case 1: return MouseButtonState.Right;
                    case 2: return MouseButtonState.Middle;
                }

                return null;
            }
        };

    public static void registerLoader()
    {
        ObjectLoader.registerEnumLoader(MouseButtonState.class.getName(),
                                        edu_cmu_cs_hcii_cogtool_model_MouseButtonState_version,
                                        LOADER);
    }

    // Various shape type definitions
    public static final MouseButtonState Left =
        new MouseButtonState(L10N.get("MBS.Left", "Left"), 0);

    public static final MouseButtonState Right =
        new MouseButtonState(L10N.get("MBS.Right", "Right"), 1);

    public static final MouseButtonState Middle =
        new MouseButtonState(L10N.get("MBS.Middle", "Middle"), 2);

    /**
     * Display ordering
     */
    public static final MouseButtonState[] DISPLAY =
        { Left, Right, Middle };

    // Constructor
    protected MouseButtonState(String lbl, int persistentValue)
    {
        super(lbl, persistentValue);
    }

    /**
     * The set of all values to support their iteration in a specific order.
     */
    protected static final MouseButtonState[] ITERATOR_ORDERING =
        { Left, Right, Middle };

    public static final List<MouseButtonState> VALUES =
        Collections.unmodifiableList(Arrays.asList(ITERATOR_ORDERING));
}