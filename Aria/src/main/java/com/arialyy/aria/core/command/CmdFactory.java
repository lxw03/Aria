/*
 * Copyright (C) 2016 AriaLyy(https://github.com/AriaLyy/Aria)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.arialyy.aria.core.command;

import com.arialyy.aria.core.inf.ITaskEntity;

/**
 * Created by Lyy on 2016/9/23.
 * 命令工厂
 */
public class CmdFactory {
  /**
   * 创建任务
   */
  public static final int TASK_CREATE = 0x122;
  /**
   * 启动任务
   */
  public static final int TASK_START = 0x123;
  /**
   * 恢复任务
   */
  public static final int TASK_RESUME = 0x127;
  /**
   * 取消任务
   */
  public static final int TASK_CANCEL = 0x124;
  /**
   * 停止任务
   */
  public static final int TASK_STOP = 0x125;
  public static final int TASK_SINGLE = 0x126;

  private static final Object LOCK = new Object();
  private static volatile CmdFactory INSTANCE = null;

  private CmdFactory() {

  }

  public static CmdFactory getInstance() {
    if (INSTANCE == null) {
      synchronized (LOCK) {
        INSTANCE = new CmdFactory();
      }
    }
    return INSTANCE;
  }

  /**
   * @param target 创建任务的对象
   * @param entity 下载实体
   * @param type 命令类型{@link #TASK_CREATE}、{@link #TASK_START}、{@link #TASK_CANCEL}、{@link
   * #TASK_STOP}
   */
  public <T extends ITaskEntity> AbsCmd createCmd(String target, T entity, int type) {
    switch (type) {
      case TASK_CREATE:
        return createAddCmd(target, entity);
      case TASK_RESUME:
      case TASK_START:
        return createStartCmd(target, entity);
      case TASK_CANCEL:
        return createCancelCmd(target, entity);
      case TASK_STOP:
        return createStopCmd(target, entity);
      case TASK_SINGLE:
        //return new SingleCmd(target, entity);
      default:
        return null;
    }
  }

  /**
   * 创建停止命令
   *
   * @return {@link StopCmd}
   */
  private <T extends ITaskEntity> StopCmd createStopCmd(String target, T entity) {
    return new StopCmd(target, entity);
  }

  /**
   * 创建下载任务命令
   *
   * @return {@link AddCmd}
   */
  private <T extends ITaskEntity> AddCmd createAddCmd(String target, T entity) {
    return new AddCmd(target, entity);
  }

  /**
   * 创建启动下载命令
   *
   * @return {@link StartCmd}
   */
  private <T extends ITaskEntity> StartCmd createStartCmd(String target, T entity) {
    return new StartCmd(target, entity);
  }

  /**
   * 创建启动下载命令
   *
   * @return {@link StartCmd}
   */
  private <T extends ITaskEntity> CancelCmd createCancelCmd(String target, T entity) {
    return new CancelCmd(target, entity);
  }
}