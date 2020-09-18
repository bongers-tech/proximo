/*
 *     Copyright © 2020 bongers-tech
 *     This file is part of Proximo.
 *
 *     Proximo is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Proximo is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Proximo. If not, see <http://www.gnu.org/licenses/>.
 */
package tech.bongers.proximo.common.util;

import net.minecraft.util.IntReferenceHolder;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class FunctionalIntReferenceHolder extends IntReferenceHolder {

    private final IntSupplier getter;
    private final IntConsumer setter;

    public FunctionalIntReferenceHolder(final IntSupplier getter, final IntConsumer setter) {
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public int get() {
        return this.getter.getAsInt();
    }

    @Override
    public void set(final int value) {
        this.setter.accept(value);
    }
}
