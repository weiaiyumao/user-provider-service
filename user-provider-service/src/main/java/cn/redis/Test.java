package cn.redis;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * http://www.cnblogs.com/0201zcr/p/5942748.html
 * @author ChuangLan
 *
 */
public class Test {

	// 注入RedisTemplate对象
		@Resource(name = "redisTemplate")
		private RedisTemplate<String, String> redisTemplate;
	
//	public static void main(String[] args) {
//		RedisTemplate<String, CreUser> redisTemplate = new RedisTemplate<String, CreUser>();
//		RedisLock lock = new RedisLock(redisTemplate, "13817367247", 1000, 0);
//		
//		try {
//			System.out.println(lock.lock());
//			// 处理加锁业务
////			if (lock.lock()) {
////				System.out.println();
////			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		} finally {
//			//为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，            //操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
//            lock.unlock();
//		}
//	}
		
		
//		@SuppressWarnings("resource")
//		public static void main(String[] args) {
//			 //创建jedis对象 172.16.4.218 6379 
//			//   Jedis jedis = new Jedis("101.132.124.69", 7001);
//            Jedis jedis = new Jedis("101.132.124.69", 7001);
//            jedis.auth("Chuanglan@%#253");
//                        //Chuanglan@%#253
//            //调用jedis对象的方法，方法名称和redis 的命令一致
//            jedis.set("key2111", "jedis test2");
//            String string = jedis.get("key2111");
//            System.out.println(string);
//
//		}
}
