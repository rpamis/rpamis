package com.rpamis.extension.spi.loading;

import java.util.Comparator;

/**
 * 加载优先级接口
 *
 * @author benym
 * @date 2023/10/31 23:00
 */
public interface LoadPriority extends Comparable<LoadPriority> {

  /**
   * 最大优先级
   */
  int MAX_PRIORITY = Integer.MIN_VALUE;

  /**
   * 最小优先级
   */
  int MIN_PRIORITY = Integer.MAX_VALUE;

  /**
   * 默认优先级
   */
  int DEFAULT_PRIORITY = 0;

  Comparator<Object> LOAD_COMPARATOR = (objectOne, objectTwo) -> {
    boolean objectOneExtendThis = objectOne instanceof LoadPriority;
    boolean objectTwoExtendThis = objectTwo instanceof LoadPriority;
    if (objectOneExtendThis && !objectTwoExtendThis) { // 对象1具有优先级，对象2没有
      return -1;
    } else if (objectTwoExtendThis && !objectOneExtendThis) { // 对象2具有优先级，对象1没有
      return 1;
    } else if (objectOneExtendThis) { // 对象1和对象2都具有优先级
      return ((LoadPriority) objectOne).compareTo((LoadPriority) objectTwo);
    } else {
      return 0;
    }
  };

  /**
   * 覆盖优先级比较接口，分别获取Priority进行比较
   *
   * @param that the object to be compared.
   * @return int
   */
  @Override
  default int compareTo(LoadPriority that) {
    return Integer.compare(this.getPriority(), that.getPriority());
  }

  /**
   * 返回优先级
   *
   * @return int
   */
  default int getPriority() {
    return DEFAULT_PRIORITY;
  }
}
