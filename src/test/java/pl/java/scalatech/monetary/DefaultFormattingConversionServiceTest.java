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
package pl.java.scalatech.monetary;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.support.DefaultFormattingConversionService;

import lombok.Data;

public class DefaultFormattingConversionServiceTest {

    ConversionService conversionService = new DefaultFormattingConversionService();
    FormattingBean bean = new FormattingBean();

    @Test
    public void testJavaMoney() {
        BeanWrapper beanWrapper = new BeanWrapperImpl(bean);
        beanWrapper.setConversionService(conversionService);
        beanWrapper.setPropertyValue("monetaryAmount", "USD 100,000");
        beanWrapper.setPropertyValue("currencyUnit", "USD");
        
        assertThat(bean.getMonetaryAmount().getNumber().intValue(), is(100));
        assertThat(bean.getMonetaryAmount().getCurrency().getCurrencyCode(), is("PLN"));
        assertThat(bean.getCurrencyUnit().getCurrencyCode(), is("USD"));

        String monetaryAmountString = (String) conversionService.convert(bean.getMonetaryAmount(), beanWrapper.getPropertyTypeDescriptor("monetaryAmount"),
                TypeDescriptor.valueOf(String.class));
        assertThat(monetaryAmountString, is("USD 100"));
        String currencyUnitString = (String) conversionService.convert(bean.getCurrencyUnit(), beanWrapper.getPropertyTypeDescriptor("currencyUnit"),
                TypeDescriptor.valueOf(String.class));
        assertThat(currencyUnitString, is("USD"));
    }

    @Data
    public static class FormattingBean {
        @NumberFormat(pattern = "USD #,###")
        private MonetaryAmount monetaryAmount;
        private CurrencyUnit currencyUnit;
    }
}