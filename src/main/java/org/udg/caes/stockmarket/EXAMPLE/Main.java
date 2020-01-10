package org.udg.caes.stockmarket.EXAMPLE;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.udg.caes.stockmarket.BusinessService;
import org.udg.caes.stockmarket.EXTERNAL.BrokerService;
import org.udg.caes.stockmarket.EXTERNAL.PersistenceService;
import org.udg.caes.stockmarket.FAKE.Fake_BS_Impl;
import org.udg.caes.stockmarket.FAKE.Fake_PS_MySQL;
import org.udg.caes.stockmarket.UtilsService;


public class Main {

  static public void main(String args[]) throws Exception {
    Injector injector = Guice.createInjector(new TestModule());

    injector.getInstance(Demo.class).doTest();
  }

  static class TestModule extends AbstractModule {
    EventBus eb;

    @Override
    protected void configure() {
      eb = new EventBus();

      bind(BrokerService.class).to(Fake_BS_Impl.class);
      bind(PersistenceService.class).to(Fake_PS_MySQL.class);
      bind(EventBus.class).toInstance(eb);
      bind(BusinessService.class);
      bind(UtilsService.class);
      bind(Demo.class);
    }
  }

}

