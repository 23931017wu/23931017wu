/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.iotdb.commons.trigger.service;

import org.apache.iotdb.commons.exception.StartupException;
import org.apache.iotdb.commons.executable.ExecutableManager;
import org.apache.iotdb.commons.file.SystemFileFactory;
import org.apache.iotdb.commons.service.IService;
import org.apache.iotdb.commons.service.ServiceType;

public class TriggerExecutableManager extends ExecutableManager implements IService {
  private TriggerExecutableManager(String temporaryLibRoot, String triggerLibRoot) {
    super(temporaryLibRoot, triggerLibRoot);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  // IService
  /////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public void start() throws StartupException {
    try {
      SystemFileFactory.INSTANCE.makeDirIfNecessary(temporaryLibRoot);
      SystemFileFactory.INSTANCE.makeDirIfNecessary(libRoot);
    } catch (Exception e) {
      throw new StartupException(e);
    }
  }

  @Override
  public void stop() {
    // nothing to do
  }

  @Override
  public ServiceType getID() {
    return ServiceType.TRIGGER_EXECUTABLE_MANAGER_SERVICE;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  // singleton instance holder
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static TriggerExecutableManager INSTANCE = null;

  public static synchronized TriggerExecutableManager setupAndGetInstance(
      String temporaryLibRoot, String triggerLibRoot) {
    if (INSTANCE == null) {
      INSTANCE = new TriggerExecutableManager(temporaryLibRoot, triggerLibRoot);
    }
    return INSTANCE;
  }

  public static TriggerExecutableManager getInstance() {
    return INSTANCE;
  }
}
