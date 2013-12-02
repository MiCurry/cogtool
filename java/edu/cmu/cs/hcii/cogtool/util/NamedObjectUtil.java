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

package edu.cmu.cs.hcii.cogtool.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilities for INamedObjects.
 */
public class NamedObjectUtil
{
    protected static final Pattern UNIQUE_NAME_INDEX_PATTERN =
        Pattern.compile("\\[(\\d+)\\]\\z");

    /**
     * Creates a unique name from a seed name relative to two collections of
     * named objects.  Particular care must be used in when applying this for
     * the addition of multiple items at once -- unless things are carefully
     * thought out, it is easy to get into a situation where you are testing
     * uniqueness across a Collection that does not yet contain all the names
     * it will when this one item is added.
     *
     * @param seedName the name we'd prefer to use
     * @param col1 a collection of INamedObjects with which we cannot collide
     * @param col2 another collection of INamedObjects with which we cannot
     *             collide, or <code>null</code> if we only have to check
     *             against <code>col1</code>
     * @return a name that is unique for col1 union col2, and based on seedName
     */
    public static <T extends NamedObject> String makeNameUnique(String seedName,
                                                                 Collection<T> col1,
                                                                 Collection<T> col2)
    {
        if (containsNamedObject(seedName, col1)) {
            String testName;
            int index = 1;
            Matcher m = UNIQUE_NAME_INDEX_PATTERN.matcher(seedName);

            if (m.find()) {
                seedName = seedName.substring(0, m.start(0)) + '[';
                index = Integer.parseInt(m.group(1));
            }
            else {
                seedName += " [";
            }

            do {
                testName = seedName + Integer.toString(++index) + ']';
            } while (containsNamedObject(testName, col1) ||
                     containsNamedObject(testName, col2));

            return testName;
        }

        return seedName;
    }

    /**
     * Creates a unique name from a seed name relative to a given collection
     * of named objects.  Particular care must be used in when applying this
     * for the addition of multiple items at once -- unless things are
     * carefully thought, out it is easy to get nto a situation where you are
     * testing uniqueness across a Collection that does not yet contain all
     * the names it will when this one item is added.
     * In such cases, use the three-parameter version.
     *
     * @param seedName the name we'd prefer to use
     * @param col a collection of INamedObjects with which we cannot collide
     * @return a name that is unique for col and based on seedName
     */
    public static <T extends NamedObject> String makeNameUnique(String seedName,
                                                                 Collection<T> col)
    {
        return makeNameUnique(seedName, col, null);
    }

    /**
     * Checks a Collection of INamedObjects for a specific name.
     *
     * @param name the name we're looking for
     * @param col the Collection in which to look
     * @return true iff obj.getName().equals(name) for at least one obj in col
     */
    public static <T extends NamedObject> boolean containsNamedObject(String name,
                                                                       Collection<T> col)
    {
        if (col != null) {
            for (Iterator<T> it = col.iterator(); it.hasNext(); ) {
                NamedObject obj = it.next();

                if (obj.getName().equals(name)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static final Comparator<NamedObject> NAME_COMPARATOR =
        new Comparator<NamedObject>() {
            public int compare(NamedObject o1, NamedObject o2)
            {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        };

    /**
     * Utility for sorting a collection of INamedObjects by their name
     * @author rmyers
     */
    public static <T extends NamedObject> List<T> getSortedList(Collection<T> c)
    {
        List<T> result = new ArrayList<T>(c.size());
        Iterator<T> iter = c.iterator();

        while (iter.hasNext()) {
            result.add(iter.next());
        }

        // Sort the collection alphabetically
        Collections.sort(result, NAME_COMPARATOR);

        return result;
    }
}