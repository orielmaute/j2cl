/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.j2cl.ast;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.j2cl.ast.annotations.Visitable;

/** Refers a constructor in Javascript. */
@Visitable
public class JavaScriptConstructorReference extends Expression {
  @Visitable DeclaredTypeDescriptor typeDescriptor;

  public JavaScriptConstructorReference(DeclaredTypeDescriptor typeDescriptor) {
    checkArgument(
        !typeDescriptor.isArray()
            && !typeDescriptor.isIntersection()
            && !typeDescriptor.isUnion());
    this.typeDescriptor = checkNotNull(typeDescriptor);
  }

  @Override
  public DeclaredTypeDescriptor getTypeDescriptor() {
    return TypeDescriptors.get().nativeFunction;
  }

  @Override
  public boolean isIdempotent() {
    return true;
  }

  @Override
  public JavaScriptConstructorReference clone() {
    return new JavaScriptConstructorReference(typeDescriptor);
  }

  public DeclaredTypeDescriptor getReferencedTypeDescriptor() {
    return typeDescriptor;
  }

  @Override
  public Node accept(Processor processor) {
    return Visitor_JavaScriptConstructorReference.visit(processor, this);
  }
}
