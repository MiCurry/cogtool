/*******************************************************************************
 * CogTool Copyright Notice and Distribution Terms
 * CogTool 1.2, Copyright (c) 2005-2013 Carnegie Mellon University
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
 * jopt-simpler
 * 
 * Copyright (c) 2004-2013 Paul R. Holser, Jr.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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

import org.apache.commons.lang.builder.HashCodeBuilder;

import edu.cmu.cs.hcii.cogtool.util.L10N;
import edu.cmu.cs.hcii.cogtool.util.ObjectLoader;
import edu.cmu.cs.hcii.cogtool.util.ObjectSaver;

/**
 * @author alexeiser
 */
public class GraffitiAction extends TextAction
{
    public static final int edu_cmu_cs_hcii_cogtool_model_GraffitiAction_version = 0;

    protected static final String textVAR = "text";
    protected static final String isCommandVAR = "isCommand";

    protected String text;

    protected boolean isCommand;

    private int myHashCode = hashCode();

    private static ObjectSaver.IDataSaver<GraffitiAction> SAVER =
        new ObjectSaver.ADataSaver<GraffitiAction>() {
            @Override
            public int getVersion()
            {
                return edu_cmu_cs_hcii_cogtool_model_GraffitiAction_version;
            }

            @Override
            public void saveData(GraffitiAction v, ObjectSaver saver)
                throws java.io.IOException
            {
                saver.saveString(v.text, textVAR);
                saver.saveBoolean(v.isCommand, isCommandVAR);
            }
        };

    public static void registerSaver()
    {
        ObjectSaver.registerSaver(GraffitiAction.class.getName(), SAVER);
    }

    private static ObjectLoader.IObjectLoader<GraffitiAction> LOADER =
        new ObjectLoader.AObjectLoader<GraffitiAction>() {
            @Override
            public GraffitiAction createObject()
            {
                return new GraffitiAction();
            }

            @Override
            public void set(GraffitiAction target, String variable, boolean value)
            {
                if (variable != null) {
                    if (variable.equals(isCommandVAR)) {
                        target.isCommand = value;
                    }
                }
            }

            @Override
            public void set(GraffitiAction target, String variable, Object value)
            {
                if (variable != null) {
                    if (variable.equals(textVAR)) {
                        target.text = (String) value;
                    }
                }
            }
        };

    public static void registerLoader()
    {
        ObjectLoader.registerLoader(GraffitiAction.class.getName(),
                                    edu_cmu_cs_hcii_cogtool_model_GraffitiAction_version,
                                    LOADER);
    }

    // Used for loading
    protected GraffitiAction()
    {
        super(ActionType.GraffitiStroke);
    }

    public GraffitiAction(String str, boolean isCmd)
    {
        super(ActionType.GraffitiStroke);

        isCommand = isCmd;
        text = str;
    }

    /**
     * @return command string (graffiti strokes)
     */
    @Override
    public String getText()
    {
        return text;
    }

    @Override
    public void setText(String str)
    {
        text = str;
    }

    /**
     * @return is the text a command
     */
    @Override
    public boolean isCommand()
    {
        return isCommand;
    }

    @Override
    public void setIsCommand(boolean isCmd)
    {
        isCommand = isCmd;
    }

    /**
     * Create a copy of this action.
     * <p>
     * It is the responsibility of the caller to "place" the copy
     * (usually by setting it as the action of an Transition).
     *
     * @return the action copy
     * @author mlh
     */
    @Override
    public AAction duplicate()
    {
        GraffitiAction newAction =
            new GraffitiAction(text, isCommand);

        newAction.copyState(this);

        return newAction;
    }

    @Override
    public String getLocalizedString()
    {
        return L10N.get("GA.EnterGraffitiStrokes",
                        "Enter Graffiti\u00AE Strokes:") + " " + text;
    }

    /**
     * Indicate that this action does require a widget target
     */
    @Override
    public boolean requiresTarget()
    {
        return true;
    }

    @Override
    public void accept(AAction.ActionVisitor visitor)
    {
        visitor.visit(this);
    }

    // Actions must implement equals and hashCode
    protected boolean valueEquals(GraffitiAction other)
    {
        return super.valueEquals(other) &&
               (isCommand == other.isCommand()) &&
               ((text == null) ? (other.getText() == null)
                                    : text.equals(other.getText()));
    }

    @Override
    public boolean equals(Object other)
    {
        return (other != null) &&
               (other.getClass() == GraffitiAction.class) &&
               valueEquals((GraffitiAction) other);
    }

    @Override
    public int hashCode()
    {
        if (myHashCode == 0 ) {
            // Must have a unique ODD number for each class which uses
            // hashCodeBuilder.
            // this   : 102563, 4891
            myHashCode =
                new HashCodeBuilder(102563, 4891).appendSuper(super.hashCode())
                                                 .append(getText())
                                                 .toHashCode();
        }

        return myHashCode;
    }
}
