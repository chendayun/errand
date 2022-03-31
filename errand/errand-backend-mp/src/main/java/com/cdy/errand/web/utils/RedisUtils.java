package com.cdy.errand.web.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存工具类
 */
public class RedisUtils {
    /**
     * 指定缓存失效时间
     */
    public boolean expire(String key, long timeout) {
        try {
            if (timeout > 0) {
                redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 根据key 获取过期时间
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key 是否存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     */
    @SuppressWarnings("unchecked")
    public void delete(String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            }
        } else {
            redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(keys));
        }
    }

    /**
     * 普通缓存获取
     */
    public Object getKey(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     */
    public boolean setKey(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     */
    public boolean setKey(String key, Object value, long timeout) {
        try {
            if (timeout > 0) {
                redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     */
    public long increment(String key, long delta) {
        if (delta > 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     */
    public long decrement(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().decrement(key, -delta);
    }

    /**
     * hashGet
     */
    public Object hashGet(String key, String items) {
        return redisTemplate.opsForHash().get(key, items);
    }

    /**
     * 获取hashKey 对应的所有键值
     */
    public Map<Object, Object> hashMapGet(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * hashSet
     */
    public boolean hashMapSet(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * hashSetWithTimeOut
     */
    public boolean hashSetWithTimeOut(String key, Map<String, Object> map, long timeout) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (timeout > 0) {
                expire(key, timeout);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     */
    public void hashDelete(String key, Object... items) {
        redisTemplate.opsForHash().delete(key, items);
    }

    /**
     * 判断hash表中是否有该项的值
     */
    public boolean hasHashKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     */
    public double hashIncrement(String key, String item, double delta) {
        return redisTemplate.opsForHash().increment(key, item, delta);
    }

    /**
     * hash递减 如果不存在,就会创建一个 并把新增后的值返回
     */
    public double hashDecrement(String key, String item, double delta) {
        return redisTemplate.opsForHash().increment(key, item, -delta);
    }

    /**
     * 根据key 获取Set中的所有值
     */
    public Set<Object> setsGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value 从一个 set 中查询，是否存在
     */

    public boolean hasSets(String key, Object value) {
        try {
            return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入Set缓存
     */
    public long setSets(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将set数据放入缓存,并设置失效时间
     *
     * @return 成功个数
     */
    public long setSetsWithTimeout(String key, long timeout, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (timeout > 0) {
                expire(key, timeout);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取Set缓存的长度
     */

    public long getSetsSize(String keys) {
        try {
            return redisTemplate.opsForSet().size(keys);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    /**
     *  从一个Set中查询，是否存在
     */
    public boolean hasSetKey(String key, Object value) {
        try {
            return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从一个Set中移除
     */
    public long setRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取List缓存的内容
     */
    public List<Object> listGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key,start,end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     */
    public long getListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引获取List中的值
     */
    public Object getListValueByIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     */
    public boolean setForList(String key, List<Object> values) {
        try {
            redisTemplate.opsForList().rightPushAll(key,values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存, 并设置时间
     */
    public boolean setForListWithTimeout(String key, List<Object> values, long timeout) {
        try {
            redisTemplate.opsForList().rightPush(key,values);
            if (timeout >0) {
                expire(key, timeout);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}

