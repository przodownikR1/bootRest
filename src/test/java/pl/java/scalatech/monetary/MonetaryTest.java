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

import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.RoundedMoney;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class MonetaryTest {
    @Test
  public void shouldMonetaryPrint(){
              CurrencyUnit currency = Monetary.getCurrency(Locale.US);
              MonetaryAmount money = Money.of(10, currency);
              MonetaryAmount money2 = FastMoney.of(20, currency);
              //MonetaryAmount money3 = RoundedMoney.of(30, currency, MonetaryOperators.rounding());
               log.info("money : {}",money.toString());//USD 10
              log.info("money2 :{}",money2.toString());
              
              MonetaryAmount result = Money.parse(money.toString());
              MonetaryAmount result2 = FastMoney.parse(money2.toString());
              

          
      
  }
}
