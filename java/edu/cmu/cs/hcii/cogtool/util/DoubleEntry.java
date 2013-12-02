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

import java.text.NumberFormat;
import java.util.Locale;

import org.eclipse.swt.widgets.Composite;

public class DoubleEntry extends IntegerEntry
{
    public static final int FREE_FORM = -1;

    protected int decimalPlaces = FREE_FORM;

    public DoubleEntry(Composite parent, int style)
    {
        super(parent, style);
    }

    /**
     * Acceptable characters in a double entry SWT widget:
     *  Numerics: Character.DECIMAL_DIGIT_NUMBER
     *           (apparently, this subsumes SWT.KEYPAD_0-9)
     *  Also, the string can contain at most one decimal point
     *
     * @param key the character to be tested
     * @return if the given character is acceptable as input to the Combo
     */
    @Override
    protected boolean acceptableCharacter(char key)
    {
        if (key == '-') {
            return isNegativeAcceptable();
        }

        boolean isNumber =
            (Character.getType(key) == Character.DECIMAL_DIGIT_NUMBER);

        boolean noPoint = (getText().indexOf(".") == -1);

        return isNumber || (noPoint && (key == '.'));
    }

    @Override
    protected String getValueBasePattern()
    {
        return "(\\d+\\.?\\d*)|(\\d*\\.?\\d+)";
    }

    /**
     * Avoid calling this after the DoubleEntry has been disposed;
     * for example, if it is part of a dialog box, you must call this when
     * the associated OK button is pressed and not after (when the dialog
     * has been closed/disposed).
     */
    public double getDoubleValue()
    {
        String valueStr = stripUnits(getText());

        if ((valueStr == null) || valueStr.equals("") || valueStr.equals(".")) {
            return 0.0;
        }

        String textWithNoCommas = valueStr.replaceAll(",", "");

        try {
            return Double.parseDouble(textWithNoCommas);
        }
        catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public void setDoubleValue(double newValue)
    {
        setText(Double.toString(newValue));
    }

    public int getDecimalPlaces()
    {
        return decimalPlaces;
    }

    public void setDecimalPlaces(int newPlaces)
    {
        decimalPlaces = newPlaces;
    }

    @Override
    public String isProperEntry(String newText, boolean emptyOk)
    {
        if ((newText == null) || newText.equals("")) {
            return emptyOk ? newText : null;
        }

        newText = stripUnits(newText);

        boolean isNumber = true;
        boolean sawDecimalPoint = false;

        for (int i = 0; i < newText.length(); i++) {
            char c = newText.charAt(i);

            if ((i == 0) && (c == '-')) {
                isNumber = allowsNegative();
            }
            else if (Character.getType(c) != Character.DECIMAL_DIGIT_NUMBER) {
                if (c != '.') {
                    isNumber = false;
                }
                else {
                    if (sawDecimalPoint) {
                        isNumber = false;
                    }
                    else {
                        sawDecimalPoint = true;
                    }
                }
            }
        }

        return isNumber ? newText : null;
    }

    @Override
    public void setText(String newText)
    {
     // If newText is null or "", do not format the number
        String properEntry = isProperEntry(newText, false);

        if ((properEntry != null) && (decimalPlaces != FREE_FORM)) {
            NumberFormat nFmtr = NumberFormat.getInstance(Locale.US);

            nFmtr.setMaximumFractionDigits(decimalPlaces);
            nFmtr.setMinimumFractionDigits(decimalPlaces);

            newText = nFmtr.format(Double.parseDouble(properEntry));
        }

        super.setText(newText);
    }
}