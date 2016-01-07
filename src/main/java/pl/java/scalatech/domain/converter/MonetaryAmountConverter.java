/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.java.scalatech.domain.converter;

import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true) // Default for MonetaryAmount properties
public class MonetaryAmountConverter
    implements AttributeConverter<MonetaryAmount, String> {

    @Override
    public String convertToDatabaseColumn(MonetaryAmount monetaryAmount) {
        return monetaryAmount.toString();
    }
    //TODO
    @Override
    public MonetaryAmount convertToEntityAttribute(String s) {
       return null;
    }
}