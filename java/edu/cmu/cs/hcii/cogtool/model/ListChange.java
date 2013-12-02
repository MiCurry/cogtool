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

import java.util.EventObject;

/**
 * Semantic change for methods that add or remove elements from a list.
 * <p>
 * Elements can be added <em>before</em> an existing member in the
 * list or at the end of the list.  Thus, if the change
 * is an add and the specified index is not <code>AT_END</code>,
 * the newly added element goes before the existing element at that
 * index, which is zero-based.  (Thus, it is equivalent to adding
 * the new element at the end if the index is equal to the old count
 * of elements held by the list.)
 * <p>
 * It is intended that this class not be instantiated directly.
 * Instead, it should be subclassed with constructors that have
 * properly typed parameters.
 *
 * @author mlh
 */
public abstract class ListChange extends EventObject
{
    /**
     * Constant flag indicating that the add happened at end of the list.
     */
    public static final int AT_END = -1;

    /**
     * The element added or removed.  Must be properly down-cast.
     */
    public Object element;

    /**
     * The location as to where the element was added to or removed from.
     */
    public int atIndex;

    /**
     * Whether the change was an add or remove.
     */
    public boolean isAdd;

    /**
     * Initialize the semantic change relative to a specific index.
     *
     * @param srcObj    the list that was modified
     * @param eltChg    the element added or removed
     * @param index     where the element was added before or removed from
     * @param add       a flag indicating whether the change is an add
     *                  or a remove
     * @author mlh
     */
    public ListChange(Object srcObj, Object eltChg, int index, boolean add)
    {
        super(srcObj);

        element = eltChg;
        atIndex = index;
        isAdd = add;
    }

    /**
     * Initialize the semantic change representing an add at the end.
     * <p>
     * Note that it makes little sense to specify a delete from the end!
     *
     * @param srcObj    the list that was modified
     * @param eltChg    the element added
     * @author mlh
     */
    public ListChange(Object srcObj, Object eltChg)
    {
        this(srcObj, eltChg, AT_END, true);
    }
}
