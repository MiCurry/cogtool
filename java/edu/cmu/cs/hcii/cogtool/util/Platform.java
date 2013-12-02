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

import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Display;

/**
 * Encapsulates any platform specific APIs that need to be called. At startup
 * OSUtils allocates one an instance of an IPlatform, choosing the class based
 * on the host OS. This interface maps platform independent calls to the
 * appropriate platform dependent APIs. Typically only one platform will
 * actually do anything for most of the platform independent calls, the others
 * ignoring it. It is STRONGLY RECOMMENDED that this interface not be
 * implemented directly, but rather that the abstract class PlatformAdapter
 * be extended, overriding whatever APIs need to be implemented for a platform.
 * That eases the job of anyone adding a new API to this interface.
 */
public interface Platform
{
    /**
     *  This subinterface and the following method are to support the
     * platform specific Applcation Menu that exists only on Macintosh.
     * The subinterface is methods to be called when items in that menu are
     * selected by the user.
     */
    public interface PlatformMenuActions
    {
        public void doPreferences();
        public void doAbout();
        public void doExitApplication();
    }

    /**
     * Bind the above callbacks to the Application Menu; only relevant
     * for Macintosh, other platforms should ignore this.
     */
    public void initPlatformMenu(Display d, PlatformMenuActions a);

    /**
     * Work around a bug in the Mac implementation of TransferData; this
     * needs to be here as the implementation of TransferData varies by
     * platform. Platforms other than Macintosh should ignore this.
     */
    public void initTransferData(TransferData td);
}
