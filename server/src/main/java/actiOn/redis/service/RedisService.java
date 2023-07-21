package actiOn.redis.service;

import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        // JSON 직렬화 설정 추가
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
    }

    public void saveDataToRedis(String key, String value) {
        try{
            redisTemplate.opsForValue().set(key, value);
        }catch (Exception e){
            throw new BusinessLogicException(ExceptionCode.BAD_REQUEST);
        }

    }

    public String getDataFromRedis(String key) {
        try{
            return redisTemplate.opsForValue().get(key);
        }catch (Exception e){
            return null;
        }
    }

    public void deleteDataFromRedis(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.BAD_REQUEST);
        }
    }
}
