package com.heartape.util.id.snowflake;

import java.util.Random;

/**
 * SnowFlake
 * @see <a target="_blank" href="https://github.com/twitter-archive/snowflake">snowflake</a>
 */
@SuppressWarnings("ALL")
public class SnowFlakeIdentifierGenerator {
    private final long twepoch = 1288834974657L;
    private final long workerIdBits = 10L;
    private final long sequenceBits = 12L;
    private final long workerIdShift = sequenceBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits;
    private final long sequenceMask = ~(-1L << sequenceBits);
    private long workerId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;
    private static final Random RANDOM = new Random();

    public SnowFlakeIdentifierGenerator(long workerId) {
        this.workerId = workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public synchronized Long next() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;
            if (offset <= 5) {
                try {
                    wait(offset << 1);
                    timestamp = timeGen();
                    if (timestamp < lastTimestamp) {
                        return -1L;
                    }
                } catch (InterruptedException e) {
                    return -2L;
                }
            } else {
                return -3L;
            }
        }
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                //seq 为0的时候表示是下一毫秒时间开始对seq做随机
                sequence = RANDOM.nextInt(100);
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            //如果是新的ms开始
            sequence = RANDOM.nextInt(100);
        }
        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) | (workerId << workerIdShift) | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp;
        do {
            timestamp = timeGen();
        } while (timestamp <= lastTimestamp);
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

}
