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

package edu.cmu.cs.hcii.cogtool.view;

import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import edu.cmu.cs.hcii.cogtool.CogToolPref;
import edu.cmu.cs.hcii.cogtool.util.StatusDisplayable;
import edu.cmu.cs.hcii.cogtool.util.WindowUtil;


/**
 * A simple status bar that can display a status message either indefinitely or
 * for a specified duration.
 *
 * @author jbc
 *
 */
public class StatusBar extends Composite implements StatusDisplayable
{

    private static Map<StatusBar, Object> allStatusBars =
        new WeakHashMap<StatusBar, Object>();

    /**
     * Label to display status message
     */
    private Label label;
    private String currentMessage = "";


    /**
     * Constructor
     */
    public StatusBar(Composite parent)
    {
        super(parent, SWT.NONE);

        label = new Label(this, SWT.LEFT | SWT.SHADOW_NONE);

        setLayout(new FillLayout());

        allStatusBars.put(this, null);
    }


    /* (non-Javadoc)
     * @see edu.cmu.cs.hcii.cogtool.view.IStatusDisplayable#setStatusMessage(java.lang.String)
     */
    public void setStatusMessage(String message)
    {
        currentMessage = message;
        updateDisplay();
    }

    private void updateDisplay() {
        //TODO: replace spaces with proper indentation via layout
        if (! label.isDisposed()) {
            StringBuilder sb = new StringBuilder("  ");
            if (CogToolPref.RESEARCH.getBoolean()) {
                sb.append("[Research] ");
            }
            sb.append(currentMessage);
            label.setText(sb.toString());
        }
    }

    public static void updateStatusBars() {
        for (StatusBar s : allStatusBars.keySet()) {
            s.updateDisplay();
        }
    }

    /* (non-Javadoc)
     * @see edu.cmu.cs.hcii.cogtool.view.IStatusDisplayable#setStatusMessage(java.lang.String, int)
     */
    public void setStatusMessage(String message, int duration)
    {
        // set message
        setStatusMessage(message);

        // schedule delayed removal of message
        WindowUtil.GLOBAL_DISPLAY.timerExec(duration,
                                           new Runnable() {
                                               public void run() {
                                                   // Check to ensure control
                                                   // still exists, then clear
                                                   // status message
                                                   if (! isDisposed()) {
                                                       setStatusMessage("");
                                                   }
                                               }
                                           });
    }
}
