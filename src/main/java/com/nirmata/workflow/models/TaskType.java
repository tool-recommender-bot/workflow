/**
 * Copyright 2014 Nirmata, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nirmata.workflow.models;

import com.google.common.base.Preconditions;

/**
 * <p>
 *     Models a task type
 * </p>
 *
 * <p>
 *     Tasks can be idempotent or non-idempotent. Idempotent tasks
 *     can be restarted/re-executed when a workflow instance crashes
 *     or there is some other kind of error. non-idempotent tasks
 *     will only be attempted once.
 * </p>
 */
public class TaskType
{
    private final String type;
    private final String version;
    private final boolean isIdempotent;
    private final boolean hasDelay;

    // for backward compatibility
    public TaskType(String type, String version, boolean isIdempotent)
    {
        this(type, version, isIdempotent, false);
    }

    /**
     * @param type any value to represent the task type
     * @param version the version of this task type
     * @param isIdempotent whether or not this task is idempotent (see class description for details)
     * @param hasDelay if true, tasks are allowed to specify a delay before executing
     */
    public TaskType(String type, String version, boolean isIdempotent, boolean hasDelay)
    {
        Preconditions.checkArgument(!type.contains("/"), "type cannot contain '/'");
        Preconditions.checkArgument(!version.contains("/"), "version cannot contain '/'");

        this.version = Preconditions.checkNotNull(version, "version cannot be null");
        this.type = Preconditions.checkNotNull(type, "type cannot be null");
        this.isIdempotent = isIdempotent;
        this.hasDelay = hasDelay;
    }

    public String getVersion()
    {
        return version;
    }

    public String getType()
    {
        return type;
    }

    public boolean isIdempotent()
    {
        return isIdempotent;
    }

    public boolean hasDelay()
    {
        return hasDelay;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o)
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        TaskType taskType = (TaskType)o;

        if ( isIdempotent != taskType.isIdempotent )
        {
            return false;
        }
        if ( hasDelay != taskType.hasDelay )
        {
            return false;
        }
        if ( !type.equals(taskType.type) )
        {
            return false;
        }
        return version.equals(taskType.version);

    }

    @Override
    public int hashCode()
    {
        int result = type.hashCode();
        result = 31 * result + version.hashCode();
        result = 31 * result + (isIdempotent ? 1 : 0);
        result = 31 * result + (hasDelay ? 1 : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "TaskType{" +
            "type='" + type + '\'' +
            ", version='" + version + '\'' +
            ", isIdempotent=" + isIdempotent +
            ", hasDelay=" + hasDelay +
            '}';
    }
}
