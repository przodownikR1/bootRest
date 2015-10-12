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