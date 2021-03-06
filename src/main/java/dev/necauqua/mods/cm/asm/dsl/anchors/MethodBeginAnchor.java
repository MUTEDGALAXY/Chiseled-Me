/*
 * Copyright (c) 2016-2019 Anton Bulakh <necauqua@gmail.com>
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

package dev.necauqua.mods.cm.asm.dsl.anchors;

import dev.necauqua.mods.cm.asm.dsl.ContextMethodVisitor;
import dev.necauqua.mods.cm.asm.dsl.Modifier;
import dev.necauqua.mods.cm.asm.dsl.ModifierType;

import static dev.necauqua.mods.cm.asm.dsl.ModifierType.*;

public final class MethodBeginAnchor extends Anchor {

    public static final MethodBeginAnchor INSTANCE = new MethodBeginAnchor();

    private MethodBeginAnchor() {}

    @Override
    public ContextMethodVisitor apply(ContextMethodVisitor context, Modifier modifier) {
        ModifierType type = modifier.getType();
        if (type == REPLACE) {
            throw new IllegalArgumentException("Replacing method begin has no sense");
        }
        if (modifier.getIndex() != 1) {
            throw new IllegalArgumentException("Match indices make no sense for method begin");
        }
        return new ContextMethodVisitor(context) {
            @Override
            public void visitCode() {
                if (type == INSERT_AFTER) {
                    super.visitCode();
                }
                modifier.tryRun(context, 1);
                if (type == INSERT_BEFORE) {
                    super.visitCode();
                }
            }
        };
    }

    @Override
    public String toString() {
        return "method begin";
    }
}
