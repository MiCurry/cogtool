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

import java.util.List;

public interface TaskParent
{
    /**
     * Indicates that a new design or task should be added to the end.
     */
    public static final int AT_END = ListChange.AT_END;

    /**
     * Semantic change for <code>addUndertaking</code> and
     * <code>removeUndertaking</code>.
     * <p>
     * Undertakings can be added <em>before</em> an existing undertaking in the
     * parent or at the end of the list of undertakings.  Thus, if the change
     * is an add and the specified index is not <code>AT_END</code>,
     * the newly added undertaking goes before the existing undertaking at that
     * index, which is zero-based.  (Thus, it is equivalent to adding
     * the new undertaking at the end if the index is equal to the old count
     * of undertakings held by the parent.)
     *
     * @author mlh
     * @see ListChange
     */
    public static class TaskChange extends ListChange
    {
        /**
         * Initialize the semantic change relative to a specific index.
         *
         * @param tskParent the parent that was modified
         * @param taskChg   the undertaking added or removed
         * @param index     where the undertaking was added before or
         *                  removed from
         * @param add       a flag indicating whether the change is an add
         *                  or a remove
         * @author mlh
         */
        public TaskChange(TaskParent tskParent,
                          AUndertaking taskChg,
                          int index,
                          boolean add)
        {
            super(tskParent, taskChg, index, add);
        }

        /**
         * Initialize the semantic change representing an add at the end.
         * <p>
         * Note that it makes little sense to specify a delete from the end!
         *
         * @param tskParent the group that was modified
         * @param taskChg   the undertaking added
         * @author mlh
         */
        public TaskChange(TaskParent tskParent, AUndertaking taskChg)
        {
            super(tskParent, taskChg);
        }
    }

    /**
     * Fetch the entire list of undertakings (tasks and task groups), in order.
     *
     * HOWEVER this is only the first level.
     * <p>
     * Each element in the returned <code>List</code> is an instance
     * of <code>AUndertaking</code>.  Use the <code>isGroup</code> method
     * on the returned instance to determine if it is, in fact, an instance
     * of <code>Task</code> or <code>TaskGroup</code>.  (Note: One could
     * also use Java's reflection mechanism!)
     *
     * @return the parent's undertakings
     * @author mlh
     */
    public List<AUndertaking> getUndertakings();

    /**
     * Fetch the task or task group of the parent of the specified name.
     * <p>
     * A parent's tasks must have mutually distinct names.
     * <p>
     * If the task is not found, <code>null</code> is returned.
     *
     * @param taskName  the name of the task to find
     * @return          the task or task group of the given name held by
     *                  the parent, or <code>null</code> if not found
     * @author mlh
     */
    public AUndertaking getUndertaking(String undertakingName);

    /**
     * Add the given undertaking to the end of the parent's list of
     * undertakings.
     * <p>
     * Each implementation must check for undertaking name uniqueness.
     * <p>
     * When done, registered alert handlers are notified with
     * a <code>TaskChange</code> instance.
     * <p>
     * Throws <code>IllegalArgumentException</code> if given undertaking
     * has the same name as one already held by the parent.
     *
     * @param newUndertaking the undertaking to add
     * @exception IllegalArgumentException
     * @author mlh
     */
    public void addUndertaking(AUndertaking newUndertaking);

    /**
     * Add the given undertaking in the parent's list of undertakings before
     * the undertaking at the given index.
     * <p>
     * Each implementation must check for undertaking name uniqueness.
     * <p>
     * When done, registered alert handlers are notified with
     * a <code>TaskChange</code> instance.
     * <p>
     * Throws <code>IllegalArgumentException</code> if given undertaking
     * has the same name as one already held by the parent.
     *
     * @param index          the index indicating which undertaking before
     *                       which the new undertaking should be inserted;
     *                       may be AT_END
     * @param newUndertaking  the undertaking to add
     * @exception IllegalArgumentException
     * @author mlh
     */
    public void addUndertaking(int index, AUndertaking newUndertaking);

    /**
     * Find the undertaking of the given name and, if found, remove from
     * the parent's list of undertakings.
     * <p>
     * When done, registered alert handlers are notified with
     * a <code>TaskChange</code> instance.
     *
     * @param undertakingName the name of the undertaking to remove
     * @return                true iff the undertaking was successfully removed
     * @author mlh
     */
    public boolean removeUndertaking(String undertakingName);

    /**
     * Remove the given undertaking from the parent's list of undertakings,
     * if it contains that undertaking.
     * <p>
     * When done, registered alert handlers are notified with
     * a <code>TaskChange</code> instance.
     *
     * @param undertakingToRemove  the undertaking to remove
     * @return                     true iff the undertaking was
     *                             successfully removed
     * @author mlh
     */
    public boolean removeUndertaking(AUndertaking undertakingToRemove);
}