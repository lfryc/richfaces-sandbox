/*
 * JBoss, Home of Professional Open Source
 * Copyright , Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.richfaces.component;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import org.richfaces.cdk.annotations.Attribute;
import org.richfaces.cdk.annotations.JsfComponent;
import org.richfaces.cdk.annotations.JsfRenderer;
import org.richfaces.cdk.annotations.Tag;
import org.richfaces.cdk.annotations.TagType;
import org.richfaces.renderkit.WatermarkRendererBase;

/**
 * Adds watermark capability to HTML input and textarea elements. A watermark typically appears as light gray text within an
 * input or textarea element whenever the element is empty and does not have focus. This provides a hint to the user as to what
 * the input or textarea element is used for, or the type of input that is required.
 */
@JsfComponent(tag = @Tag(name = "watermark", type = TagType.Facelets), renderer = @JsfRenderer(family = AbstractWatermark.COMPONENT_FAMILY, type = WatermarkRendererBase.RENDERER_TYPE), attributes = { "javax.faces.component.ValueHolder.xml" })
public abstract class AbstractWatermark extends UIOutput {
    // ------------------------------ FIELDS ------------------------------

    public static final String COMPONENT_FAMILY = "org.richfaces.Watermark";

    public static final String COMPONENT_TYPE = "org.richfaces.Watermark";

    // ------------------------ INTERFACE METHODS ------------------------

    // --------------------- Interface ValueHolder ---------------------

    @Attribute(required = true)
    public abstract Object getValue();

    // -------------------------- OTHER METHODS --------------------------

    /**
     * Use this if watermark cannot be nested within come components i.e. in calendar.
     *
     * @return id of component for which watermark should be applied
     */
    @Attribute
    public abstract String getFor();

    public String getTargetClientId(FacesContext context) {
        String sid = getFor();
        String target;
        if (sid != null && !"".equals(sid)) {
            try {
                UIComponent forcomp = findComponent(sid);
                if (forcomp != null) {
                    target = forcomp.getClientId(context);
                } else {
                    target = sid;
                }
            } catch (IllegalArgumentException e) {
                target = sid;
            }
        } else {
            target = getParent().getClientId(context);
        }
        return target;
    }

    /**
     * Space-separated list of CSS style class(es) which will be applied to the target input component when watermark is
     * active.
     */
    @Attribute
    public abstract String getStyleClass();
}
