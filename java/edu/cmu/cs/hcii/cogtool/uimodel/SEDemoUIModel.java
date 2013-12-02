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

package edu.cmu.cs.hcii.cogtool.uimodel;

import java.util.Iterator;

import org.eclipse.swt.events.MouseListener;

import edu.cmu.cs.hcii.cogtool.CogToolPref;
import edu.cmu.cs.hcii.cogtool.model.AUndertaking;
import edu.cmu.cs.hcii.cogtool.model.Demonstration;
import edu.cmu.cs.hcii.cogtool.model.Design;
import edu.cmu.cs.hcii.cogtool.model.Project;
import edu.cmu.cs.hcii.cogtool.model.Script;
import edu.cmu.cs.hcii.cogtool.model.TaskApplication;
import edu.cmu.cs.hcii.cogtool.model.TaskGroup;
import edu.cmu.cs.hcii.cogtool.util.AlertHandler;
import edu.cmu.cs.hcii.cogtool.util.IAttributed;

/**
 * @author alexeiser
 *
 */
public class SEDemoUIModel extends DefaultSEUIModel
{
    protected ScriptUIModel scriptUIModel;

    protected Script script;

    public SEDemoUIModel(Script modelScript,
                         Project scriptProject,
                         MouseListener listener,
                         AlertHandler deviceChgHandler,
                         boolean editable)
    {
        super(scriptProject, listener, deviceChgHandler, editable);

        if (modelScript == null) {
            throw new IllegalArgumentException
                ("Cannot create a SEDemoUIModel with a null Script");
        }

        // Store model for reference
        script = modelScript;
        Demonstration demo = script.getDemonstration();
        TaskApplication taskApp = demo.getTaskApplication();

        task = taskApp.getTask();
        design = taskApp.getDesign();

        scriptUIModel = new ScriptUIModel(modelScript, scriptProject);

        setCurrentFrame(demo.getStartFrame());

        design.addHandler(this,
                               Design.FrameChange.class,
                               deleteHandler);

        AlertHandler handleStepDeletion = new StepDeletionHandler(demo);

        demo.addHandler(this, Demonstration.ScriptStepChange.class, handleStepDeletion);
    } // ctor

    public ScriptUIModel getScriptUIModel()
    {
        return scriptUIModel;
    }

    /**
     * Dispose: clear the currentFrame of the window, and remove all handlers
     */
    @Override
    public void dispose()
    {
        script.removeAllHandlers(this);
        script.getDemonstration().removeAllHandlers(this);

        super.dispose();
    }

    /**
     * Enumerates values from the current override "backward" until the
     * value defined by the attributed (which might be the default value).
     */
    @Override
    public Iterator<Object> getCurrentOverrides(IAttributed attributed,
                                                String attrName)
    {
        if (CogToolPref.HCIPA.getBoolean()) {
            AUndertaking t =
                script.getDemonstration().getTaskApplication().getTask();
            TaskGroup grp = t.getTaskGroup();

            if (grp != null) {
                return new AttributeOverrideIterator(grp.getUndertakings(),
                                                     script,
                                                     currentOverride,
                                                     attributed,
                                                     attrName);
            }
        }

        return new AttributeOverrideScriptIterator(script,
                                                   currentOverride,
                                                   attributed,
                                                   attrName);
    }
}