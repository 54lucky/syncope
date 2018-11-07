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
import java.util.Date;
import java.util.List;
import javax.ws.rs.PathParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@XmlRootElement(name = "report")
@XmlType
public class ReportTO extends AbstractStartEndBean implements EntityTO {

    private static final long serialVersionUID = 5274568072084814410L;

    private String key;

    private String name;

    private final List<String> reportlets = new ArrayList<>();

    private String cronExpression;

    private final List<ExecTO> executions = new ArrayList<>();

    private String latestExecStatus;

    private Date lastExec;

    private Date nextExec;

    private boolean active;

    private String template;

    @Override
    public String getKey() {
        return key;
    }

    @PathParam("key")
    @Override
    public void setKey(final String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "reportlets")
    @XmlElement(name = "reportlets")
    @JsonProperty("reportlets")
    public List<String> getReportlets() {
        return reportlets;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(final String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @XmlElementWrapper(name = "executions")
    @XmlElement(name = "execution")
    @JsonProperty("executions")
    public List<ExecTO> getExecutions() {
        return executions;
    }

    public String getLatestExecStatus() {
        return latestExecStatus;
    }

    public void setLatestExecStatus(final String latestExecStatus) {
        this.latestExecStatus = latestExecStatus;
    }

    public Date getLastExec() {
        return lastExec == null ? null : new Date(lastExec.getTime());
    }

    public void setLastExec(final Date lastExec) {
        this.lastExec = lastExec == null ? null : new Date(lastExec.getTime());
    }

    public Date getNextExec() {
        return nextExec == null ? null : new Date(nextExec.getTime());
    }

    public void setNextExec(final Date nextExec) {
        this.nextExec = nextExec == null ? null : new Date(nextExec.getTime());
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(final String template) {
        this.template = template;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().
                appendSuper(super.hashCode()).
                append(key).
                append(name).
                append(reportlets).
                append(cronExpression).
                append(executions).
                append(latestExecStatus).
                append(lastExec).
                append(nextExec).
                append(active).
                append(template).
                build();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReportTO other = (ReportTO) obj;
        return new EqualsBuilder().
                appendSuper(super.equals(obj)).
                append(key, other.key).
                append(name, other.name).
                append(reportlets, other.reportlets).
                append(cronExpression, other.cronExpression).
                append(executions, other.executions).
                append(latestExecStatus, other.latestExecStatus).
                append(lastExec, other.lastExec).
                append(nextExec, other.nextExec).
                append(active, other.active).
                append(template, other.template).
                build();
    }
}
