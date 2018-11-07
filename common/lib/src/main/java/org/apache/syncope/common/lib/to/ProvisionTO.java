/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.syncope.common.lib.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "provision")
@XmlType
public class ProvisionTO implements EntityTO {

    private static final long serialVersionUID = 8298910216218007927L;

    private String key;

    private String anyType;

    private String objectClass;

    private final List<String> auxClasses = new ArrayList<>();

    private String syncToken;

    private boolean ignoreCaseMatch;

    private String uidOnCreate;

    private MappingTO mapping;

    private final List<String> virSchemas = new ArrayList<>();

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(final String key) {
        this.key = key;
    }

    public String getAnyType() {
        return anyType;
    }

    public void setAnyType(final String anyType) {
        this.anyType = anyType;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(final String objectClass) {
        this.objectClass = objectClass;
    }

    @XmlElementWrapper(name = "auxClasses")
    @XmlElement(name = "class")
    @JsonProperty("auxClasses")
    public List<String> getAuxClasses() {
        return auxClasses;
    }

    public String getSyncToken() {
        return syncToken;
    }

    public void setSyncToken(final String syncToken) {
        this.syncToken = syncToken;
    }

    public boolean isIgnoreCaseMatch() {
        return ignoreCaseMatch;
    }

    public void setIgnoreCaseMatch(final boolean ignoreCaseMatch) {
        this.ignoreCaseMatch = ignoreCaseMatch;
    }

    public String getUidOnCreate() {
        return uidOnCreate;
    }

    public void setUidOnCreate(final String uidOnCreate) {
        this.uidOnCreate = uidOnCreate;
    }

    public MappingTO getMapping() {
        return mapping;
    }

    public void setMapping(final MappingTO mapping) {
        this.mapping = mapping;
    }

    @XmlElementWrapper(name = "virSchemas")
    @XmlElement(name = "virSchema")
    @JsonProperty("virSchemas")
    public List<String> getVirSchemas() {
        return virSchemas;
    }

}
