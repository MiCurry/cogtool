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

import edu.cmu.cs.hcii.cogtool.util.ObjectLoader;
import edu.cmu.cs.hcii.cogtool.util.ObjectSaver;

public class TransitionScriptStep extends AScriptStep
{
    public static final int edu_cmu_cs_hcii_cogtool_model_TransitionScriptStep_version = 0;

    protected static final String transitionVAR = "transition";

    protected Transition transition;

    private static ObjectSaver.IDataSaver<TransitionScriptStep> SAVER =
        new ObjectSaver.ADataSaver<TransitionScriptStep>() {
            @Override
            public int getVersion()
            {
                return edu_cmu_cs_hcii_cogtool_model_TransitionScriptStep_version;
            }

            @Override
            public void saveData(TransitionScriptStep v, ObjectSaver saver)
                throws java.io.IOException
            {
                saver.saveObject(v.transition, transitionVAR);
            }
        };

    public static void registerSaver()
    {
        ObjectSaver.registerSaver(TransitionScriptStep.class.getName(), SAVER);
    }

    private static ObjectLoader.IObjectLoader<TransitionScriptStep> LOADER =
        new ObjectLoader.AObjectLoader<TransitionScriptStep>() {
            @Override
            public void set(TransitionScriptStep target, String variable, Object value)
            {
                if (variable != null) {
                    if (variable.equals(transitionVAR)) {
                        target.transition = (Transition) value;
                    }
                }
            }

            @Override
            public TransitionScriptStep createObject()
            {
                return new TransitionScriptStep();
            }
        };

    public static void registerLoader()
    {
        ObjectLoader.registerLoader(TransitionScriptStep.class.getName(),
                                    edu_cmu_cs_hcii_cogtool_model_TransitionScriptStep_version,
                                    LOADER);
    }

    /**
     * Always inserted by the user and always demonstrated; can't imagine a
     * transition being inserted automatically by an ICognitiveModelGenerator.
     * @param t
     */
    public TransitionScriptStep(Transition t)
    {
        super(t.getSource().getFrame());

        transition = t;
    }

    /**
     * Used during load
     */
    public TransitionScriptStep()
    {
        // For steps saved before b22:
        // this.demonstrated is set to true in AScriptStep's LOADER to true
        // because the owner will be this step.
    }

    public Transition getTransition()
    {
        return transition;
    }

    /**
     * Create a "deep" copy of this script step of an Demonstration.
     * <p>
     * It is the responsibility of the caller to "place" the copy
     * (usually by adding it to an Demonstration).
     *
     * @param duplicateScope used to find design components referred to by the
     *                       step duplicate
     * @return the script step copy
     * @author mlh
     */
    @Override
    public AScriptStep duplicate(TaskApplication.DemoDuplicateScope duplicateScope)
    {
        Transition copyTransition =
            duplicateScope.getTransition(transition);
        AScriptStep copy = new TransitionScriptStep(copyTransition);

        copy.copyState(this);

        return copy;
    }

    @Override
    public String getLocalizedString()
    {
        return transition.getAction().getLocalizedString();
    }

    @Override
    protected AAction getStepAction()
    {
        return getTransition().getAction();
    }

    /**
     * Get the focus on which this script step performs.  May return
     * <code>null</code> if the action is independent of any focus.
     */
    @Override
    public TransitionSource getStepFocus()
    {
        return transition.getSource();
    }

    /**
     * Fetch the Frame that becomes the next to be "active" once this
     * step has been performed.  This should be the same Frame as
     * getSourceFrame() if no transition occurs as a result
     * of performing the step's "action".
     */
    @Override
    public Frame getDestinationFrame()
    {
        return transition.getDestination();
    }

    @Override
    public boolean usesFrame(Frame frame)
    {
        return super.usesFrame(frame) || (frame == getDestinationFrame());
    }

    @Override
    public boolean usesTransition(Transition t)
    {
        return t == transition;
    }

    @Override
    public void accept(AScriptStep.ScriptStepVisitor visitor)
    {
        visitor.visit(this);
    }
}
