// Copyright 2016 Pants project contributors (see CONTRIBUTORS.md).
// Licensed under the Apache License, Version 2.0 (see LICENSE).

package org.pantsbuild.tools.junit.impl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Datatype representing an individual test method.
 */
// TODO(zundel): This is very close to the Spec class. Refactor them into the same class?
class TestMethod implements Comparable<TestMethod> {
  final Class<?> clazz;
  final String name;

  TestMethod(Class<?> clazz, String name) {
    this.clazz = clazz;
    this.name = name;
  }

  /**
   * Searches a class for all methods that are annotated as JUnit4 style tests and returns them
   * as TestMethod instances.  TestMethod instances are returned in alphabetical order by method
   * name.
   */
  static Collection<TestMethod> fromClass(Class<?> clazz) {
    List<TestMethod> results = Lists.newArrayList();
    for (Method method :
        Iterables.filter(Arrays.asList(clazz.getMethods()), Util.IS_ANNOTATED_TEST_METHOD)) {
      results.add(new TestMethod(clazz, method.getName()));
    }
    // Order of return isn't deterministic for Class.getMethods(). Sort the results.
    Collections.sort(results);
    return results;
  }

  @Override public int compareTo(TestMethod other) {
    int result = this.clazz.getName().compareTo(other.clazz.getName());
    if (result == 0) {
      result = name.compareTo(other.name);
    }
    return result;
  }

  // Generated by IntelliJ
  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TestMethod that = (TestMethod) o;

    if (clazz != null ? !clazz.equals(that.clazz) : that.clazz != null) return false;
    return name != null ? name.equals(that.name) : that.name == null;
  }

  // Generated by IntelliJ
  @Override public int hashCode() {
    int result = clazz != null ? clazz.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }
}
