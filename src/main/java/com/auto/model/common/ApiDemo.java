package com.auto.model.common;

import com.auto.model.entity.*;
import com.auto.trade.entity.DepthData;
import com.auto.trade.entity.OrderPrice;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by gof on 18/6/17.
 */
public class ApiDemo implements Api<Object> {

    @Override
    public Balance getBalance(Currency currency) {
        return null;
    }

    @Override
    public DepthData getDepthData(TradeSymbol symbol) {
        return null;
    }

    @Override
    public OrderPrice getOrderPrice(TradeSymbol symbol) {
        return null;
    }

    @Override
    public Order buy(Order order) {
        Order orderBuy = new Order(order.symbol,TradeType.buy,"3000","0.01");
        orderBuy.orderId="00000001";
        orderBuy.tradeStatus=TradeStatus.trading;
        return orderBuy;
    }

    @Override
    public Order sell(Order order) {
        Order orderSell = new Order(order.symbol,TradeType.sell,"3010","0.01");
        orderSell.orderId="00000002";
        orderSell.tradeStatus=TradeStatus.trading;
        return orderSell;
    }

    @Override
    public Order buyMarket(Order order) {
        return null;
    }

    @Override
    public Order sellMarket(Order order) {
        return null;
    }

    @Override
    public Order cancel(Order order) {
        return null;
    }

    @Override
    public Order queryOrder(Order order) {
        return null;
    }

    @Override
    public TradeContext buildTradeContext(DepthData depthData) {
        BigDecimal sellPriceInDepth = new BigDecimal(depthData.getAsk_1_price());
        BigDecimal buyPriceInDepth = new BigDecimal(depthData.getBid_1_price());
        TradeContext tradeContext = new TradeContext();
        tradeContext.canTrade=false;
         //判断是否能发起交易、抵过交易手续费就可以，价格就是卖一买一价，自己实现
        BigDecimal diff = sellPriceInDepth.subtract(buyPriceInDepth);
        BigDecimal rate = diff.divide(sellPriceInDepth,6,BigDecimal.ROUND_CEILING);
        BigDecimal rate2 = rate.divide(new BigDecimal(2),6,BigDecimal.ROUND_CEILING);
        if (rate2.compareTo(Config.totalFeeRate) > 0) {
            tradeContext.canTrade=true;
            tradeContext.buyPrice=buyPriceInDepth.toString();
            tradeContext.sellPrice=sellPriceInDepth.toString();

        }
        return tradeContext;
    }

    @Override
    public BigDecimal getTargetAmountForBuy(BigDecimal originalTargetAmount) {
        // 抵扣手续费,多买
        BigDecimal targetAmount = originalTargetAmount.multiply(new BigDecimal(1).add(Config.baseFeeRate));
        return targetAmount;
    }

    @Override
    public BigDecimal getTargetAmountForSell(BigDecimal originalTargetAmount) {
        // 普通模式，不用少卖，手续费是有base出。
        return originalTargetAmount;
    }
    @Override
    public String buildSign(String http_head, String path, Map<String, String> params, long systemTimeMillsecs) {
        return null;
    }

}
