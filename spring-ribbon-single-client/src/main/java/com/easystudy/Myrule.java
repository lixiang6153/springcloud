package com.easystudy;

import java.util.Random;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

public class Myrule implements IRule {

    private ILoadBalancer lb;

    // 自定义规则
    @Override
    public Server choose(Object key) { 
        Random rm = new Random();
        int number = rm.nextInt(10);
        if(number < 2) {
            return lb.getAllServers().get(0);
        }else {
            return lb.getAllServers().get(1);
        }
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        this.lb=lb;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return this.lb;
    }

}