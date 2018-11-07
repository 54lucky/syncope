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
package org.apache.syncope.core.logic;

import java.lang.reflect.Method;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.syncope.common.lib.patch.UserPatch;
import org.apache.syncope.common.lib.to.EntityTO;
import org.apache.syncope.common.lib.to.PropagationTaskTO;
import org.apache.syncope.common.lib.to.UserTO;
import org.apache.syncope.common.lib.to.WorkflowTask;
import org.apache.syncope.common.lib.to.WorkflowTaskExecInput;
import org.apache.syncope.common.lib.types.FlowableEntitlement;
import org.apache.syncope.common.lib.types.StandardEntitlement;
import org.apache.syncope.core.persistence.api.dao.UserDAO;
import org.apache.syncope.core.persistence.api.entity.user.User;
import org.apache.syncope.core.provisioning.api.propagation.PropagationManager;
import org.apache.syncope.core.provisioning.api.propagation.PropagationTaskExecutor;
import org.apache.syncope.core.provisioning.api.WorkflowResult;
import org.apache.syncope.core.provisioning.api.data.UserDataBinder;
import org.apache.syncope.core.flowable.api.WorkflowTaskManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class UserWorkflowTaskLogic extends AbstractTransactionalLogic<EntityTO> {

    @Autowired
    private WorkflowTaskManager wfTaskManager;

    @Autowired
    private PropagationManager propagationManager;

    @Autowired
    private PropagationTaskExecutor taskExecutor;

    @Autowired
    private UserDataBinder binder;

    @Autowired
    private UserDAO userDAO;

    @PreAuthorize("hasRole('" + FlowableEntitlement.WORKFLOW_TASK_LIST + "') "
            + "and hasRole('" + StandardEntitlement.USER_READ + "')")
    public List<WorkflowTask> getAvailableTasks(final String key) {
        User user = userDAO.authFind(key);
        return wfTaskManager.getAvailableTasks(user.getKey());
    }

    @PreAuthorize("hasRole('" + StandardEntitlement.USER_UPDATE + "')")
    public UserTO executeNextTask(final WorkflowTaskExecInput workflowTaskExecInput) {
        WorkflowResult<String> updated = wfTaskManager.executeNextTask(workflowTaskExecInput);

        UserPatch userPatch = new UserPatch();
        userPatch.setKey(updated.getResult());

        List<PropagationTaskTO> tasks = propagationManager.getUserUpdateTasks(
                new WorkflowResult<>(
                        Pair.<UserPatch, Boolean>of(userPatch, null),
                        updated.getPropByRes(), updated.getPerformedTasks()));

        taskExecutor.execute(tasks, false);

        return binder.getUserTO(updated.getResult());
    }

    @Override
    protected EntityTO resolveReference(final Method method, final Object... args)
            throws UnresolvedReferenceException {

        throw new UnresolvedReferenceException();
    }
}
