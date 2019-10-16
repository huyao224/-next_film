package com.next.huyao.film.test.bucket;

public class TokenBucket {
    private int bucketNums = 10; //令牌桶的流量
    private int rate = 1; //令牌流入速率
    private int nowTokens; //当前令牌数量
    private long timestamp = getNowTime(); //时间

    /*
    判断当前是否有令牌可用
    **/
    public  boolean hasToken(){
        //记录当前拿取令牌的时间
        long nowTime = getNowTime();
        //添加令牌[延迟加载令牌]
        nowTokens = nowTokens + (int)((nowTime-timestamp)*rate);
        /*
        如果超出上限，返回一百
        如果小于上限，返回原值
        * */
        nowTokens = describeTokens(nowTokens);
        System.out.println(nowTokens);
        //更新最后拿取时间
        timestamp = nowTime;

        if(nowTokens >= 1){
            nowTokens -= 1;
            return true;
        }
        return false;
    }

    /*
    判断令牌数是否超出桶的上限
    * */
    private int describeTokens(int tokenNum){
        if(bucketNums > tokenNum){
            return tokenNum;
        }else{
            return bucketNums;
        }
    }

    private long getNowTime(){
        return System.currentTimeMillis();
    }
}
