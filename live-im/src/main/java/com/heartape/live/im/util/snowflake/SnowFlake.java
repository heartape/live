package com.heartape.live.im.util.snowflake;

import com.heartape.live.im.util.LongIdentifierGenerator;

/**
 * SnowFlake
 * @since 0.0.1
 * @author heartape
 */
public class SnowFlake extends LongIdentifierGenerator {

    private final SnowFlakeIdentifierGenerator snowFlakeIdentifierGenerator;

    public SnowFlake(SnowflakeHolder snowflakeHolder) {
        this.snowFlakeIdentifierGenerator = new SnowFlakeIdentifierGenerator(snowflakeHolder.getWorkerId());
    }

    public Long nextId(){
        return this.snowFlakeIdentifierGenerator.next();
    }

}
