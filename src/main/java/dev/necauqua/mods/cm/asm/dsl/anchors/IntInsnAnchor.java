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
import org.objectweb.asm.util.Printer;

public final class IntInsnAnchor extends Anchor {

    private final int opcode;
    private final int operand;

    public IntInsnAnchor(int opcode, int operand) {
        this.opcode = opcode;
        this.operand = operand;
    }

    @Override
    public ContextMethodVisitor apply(ContextMethodVisitor context, Modifier modifier) {
        return new ContextMethodVisitor(context) {
            private int n = 0;

            @Override
            public void visitIntInsn(int _opcode, int _operand) {
                visit(modifier, context,
                    () -> super.visitIntInsn(_opcode, _operand),
                    () -> _opcode == opcode && _operand == operand,
                    () -> ++n
                );
            }
        };
    }

    @Override
    public String toString() {
        return Printer.OPCODES[opcode] + " " + operand;
    }
}
