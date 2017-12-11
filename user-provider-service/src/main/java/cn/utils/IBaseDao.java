package cn.utils;

import java.io.Serializable;
import java.util.List;


public interface IBaseDao<Entity extends Serializable,PK extends Serializable> {
	
	
	Entity loadById(PK id); 
    
	PK save(Entity entity);
 	
	PK deleteById(PK id);
  
	PK update(Entity entity);
    
    List<Entity> selectAll(Entity entity);

}
